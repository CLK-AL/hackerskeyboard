/*
 * Copyright (C) 2025 The Hacker's Keyboard Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pocketworkstation.pckeyboard

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.google.common.collect.HashBiMap
import com.google.common.collect.ImmutableBiMap
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Parses the Unicode emoji-test.txt file (UTS #51) and provides structured
 * access to emoji data organized by group and subgroup.
 *
 * File format (Unicode Emoji 17.0):
 * ```
 * # group: Smileys & Emotion
 * # subgroup: face-smiling
 * 1F600                ; fully-qualified     # 😀 E1.0 grinning face
 * ```
 *
 * @see <a href="https://unicode.org/Public/emoji/latest/emoji-test.txt">emoji-test.txt</a>
 */
class EmojiParser private constructor() {

    /**
     * Skin tone modifiers based on Fitzpatrick scale.
     */
    enum class SkinTone(val codePoint: Int, val hex: String) {
        LIGHT(0x1F3FB, "#FFDFC4"),
        MEDIUM_LIGHT(0x1F3FC, "#F0D5BE"),
        MEDIUM(0x1F3FD, "#D2A67D"),
        MEDIUM_DARK(0x1F3FE, "#A26D49"),
        DARK(0x1F3FF, "#5C3D2E");

        val colorInt: Int get() = Color.parseColor(hex)
        val displayName: String get() = name.lowercase().replace('_', '-') + " skin tone"

        companion object {
            private val byCodePoint = entries.associateBy { it.codePoint }
            private val byHex = entries.associateBy { it.hex }

            fun fromCodePoint(cp: Int): SkinTone? = byCodePoint[cp]
            fun fromHex(hex: String): SkinTone? = byHex[hex.uppercase()]
        }
    }

    /**
     * Named colors commonly used in emoji.
     */
    enum class ColorName(val hex: String, val keywords: List<String> = listOf()) {
        RED("#FF0000", listOf("red")),
        ORANGE("#FFA500", listOf("orange")),
        YELLOW("#FFFF00", listOf("yellow")),
        GREEN("#00FF00", listOf("green")),
        BLUE("#0000FF", listOf("blue")),
        LIGHT_BLUE("#87CEEB", listOf("light blue")),
        PURPLE("#800080", listOf("purple")),
        PINK("#FFC0CB", listOf("pink")),
        BROWN("#8B4513", listOf("brown")),
        BLACK("#000000", listOf("black")),
        WHITE("#FFFFFF", listOf("white")),
        GREY("#808080", listOf("grey", "gray"));

        val colorInt: Int get() = Color.parseColor(hex)
        val displayName: String get() = name.lowercase().replace('_', ' ')

        companion object {
            private val byHex = entries.associateBy { it.hex }
            // Sort by keyword length descending to match "light blue" before "blue"
            private val byKeyword: Map<String, ColorName> = entries
                .flatMap { color -> color.keywords.map { it to color } }
                .sortedByDescending { it.first.length }
                .toMap()

            fun fromHex(hex: String): ColorName? = byHex[hex.uppercase()]

            fun fromName(name: String): ColorName? {
                val lower = name.lowercase()
                for ((keyword, color) in byKeyword) {
                    if (lower.contains(keyword)) return color
                }
                return null
            }
        }
    }

    /**
     * Represents a color with hex value and display name.
     * Unifies SkinTone and ColorName for consistent API.
     */
    data class EmojiColor(
        val hex: String,
        val name: String
    ) {
        val colorInt: Int get() = Color.parseColor(hex)

        override fun toString(): String = "$name ($hex)"

        companion object {
            fun fromSkinTone(tone: SkinTone) = EmojiColor(tone.hex, tone.displayName)
            fun fromColorName(color: ColorName) = EmojiColor(color.hex, color.displayName)
        }
    }

    /**
     * Unified color value merging SkinTone and ColorName.
     * SkinTone acts as an optional modifier for emoji with skin tone variants.
     */
    sealed interface EmojiColorValue {
        val hex: String
        val displayName: String
        val colorInt: Int get() = Color.parseColor(hex)
        val isSkinTone: Boolean

        /** Wrap a named color. */
        @JvmInline
        value class Named(val color: ColorName) : EmojiColorValue {
            override val hex: String get() = color.hex
            override val displayName: String get() = color.displayName
            override val isSkinTone: Boolean get() = false
        }

        /** Wrap a skin tone modifier. */
        @JvmInline
        value class Skin(val tone: SkinTone) : EmojiColorValue {
            override val hex: String get() = tone.hex
            override val displayName: String get() = tone.displayName
            override val isSkinTone: Boolean get() = true
        }

        fun toEmojiColor(): EmojiColor = EmojiColor(hex, displayName)

        companion object {
            /** All color values: named colors first, then skin tones. */
            @JvmStatic
            fun all(): List<EmojiColorValue> =
                ColorName.entries.map { Named(it) } + SkinTone.entries.map { Skin(it) }

            /** All named colors. */
            @JvmStatic
            fun allNamed(): List<Named> = ColorName.entries.map { Named(it) }

            /** All skin tones. */
            @JvmStatic
            fun allSkinTones(): List<Skin> = SkinTone.entries.map { Skin(it) }

            /** Create from SkinTone. */
            @JvmStatic
            fun from(tone: SkinTone): Skin = Skin(tone)

            /** Create from ColorName. */
            @JvmStatic
            fun from(color: ColorName): Named = Named(color)
        }
    }

    /**
     * Emoji qualification status per UTS #51.
     */
    enum class Status(val value: String) {
        COMPONENT("component"),
        FULLY_QUALIFIED("fully-qualified"),
        MINIMALLY_QUALIFIED("minimally-qualified"),
        UNQUALIFIED("unqualified");

        companion object {
            private val byValue = entries.associateBy { it.value }
            fun fromString(s: String): Status? = byValue[s.trim()]
        }
    }

    // ==================== IPA Syllable Mapping ====================

    /**
     * Supported keyboard language tags for IPA syllable mappings.
     * These correspond to common keyboard locales.
     */
    enum class Language(val tag: String, val displayName: String) {
        EN("en", "English"),
        DE("de", "German"),
        FR("fr", "French"),
        ES("es", "Spanish"),
        IT("it", "Italian"),
        PT("pt", "Portuguese"),
        NL("nl", "Dutch"),
        PL("pl", "Polish"),
        RU("ru", "Russian"),
        JA("ja", "Japanese"),
        ZH("zh", "Chinese"),
        KO("ko", "Korean"),
        AR("ar", "Arabic"),
        HI("hi", "Hindi"),
        TR("tr", "Turkish"),
        VI("vi", "Vietnamese"),
        TH("th", "Thai"),
        EL("el", "Greek"),
        HE("he", "Hebrew"),
        UK("uk", "Ukrainian");

        companion object {
            private val byTag = entries.associateBy { it.tag.lowercase() }
            fun fromTag(tag: String): Language? = byTag[tag.lowercase()]
        }
    }

    /**
     * A language-tagged syllable representation for an IPA symbol.
     * Format: "lang:syllable" (e.g., "en:sh", "de:sch")
     */
    data class IPASyllable(
        val language: Language,
        val syllable: String,
        val ipa: String
    ) {
        /** The full key for BiMap: "lang:syllable" */
        val key: String get() = "${language.tag}:$syllable"

        override fun toString(): String = "$key -> $ipa"

        companion object {
            /**
             * Parse a syllable key like "en:sh" into language and syllable parts.
             */
            fun parseKey(key: String): Pair<Language, String>? {
                val parts = key.split(":", limit = 2)
                if (parts.size != 2) return null
                val lang = Language.fromTag(parts[0]) ?: return null
                return lang to parts[1]
            }
        }
    }

    /**
     * IPA symbol with its language-specific syllable representations.
     */
    data class IPAMapping(
        val ipa: String,
        val codePoint: Int,
        val name: String,
        val syllables: Map<Language, List<String>>
    ) {
        /** All syllable keys for this IPA symbol (e.g., ["en:sh", "de:sch"]) */
        val allKeys: List<String>
            get() = syllables.flatMap { (lang, syls) -> syls.map { "${lang.tag}:$it" } }

        /** Get syllables for a specific language */
        fun syllablesFor(lang: Language): List<String> = syllables[lang] ?: emptyList()

        /** Check if this IPA has a syllable mapping for a language */
        fun hasLanguage(lang: Language): Boolean = syllables.containsKey(lang)
    }

    data class EmojiEntry(
        val codePoints: IntArray,
        val status: Status,
        val emoji: String,
        val version: String,
        val name: String,
        val group: String,
        val subgroup: String
    ) {
        /** The skin tone of this emoji, if it has one. */
        val skinTone: SkinTone?
            get() = codePoints.firstNotNullOfOrNull { SkinTone.fromCodePoint(it) }

        /** The named color of this emoji, if detectable from name. */
        val colorName: ColorName?
            get() = ColorName.fromName(name)

        /** The color associated with this emoji (skin tone takes priority). */
        val color: EmojiColor?
            get() = skinTone?.let { EmojiColor.fromSkinTone(it) }
                ?: colorName?.let { EmojiColor.fromColorName(it) }

        /** Unified color value (skin tone takes priority). */
        val colorValue: EmojiColorValue?
            get() = skinTone?.let { EmojiColorValue.Skin(it) }
                ?: colorName?.let { EmojiColorValue.Named(it) }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is EmojiEntry) return false
            return codePoints.contentEquals(other.codePoints)
        }

        override fun hashCode(): Int = codePoints.contentHashCode()

        override fun toString(): String = "$emoji $name"
    }

    data class EmojiGroup(
        val name: String,
        val subgroups: List<EmojiSubgroup>
    ) {
        val allEmoji: List<EmojiEntry>
            get() = subgroups.flatMap { it.entries }
    }

    data class EmojiSubgroup(
        val name: String,
        val group: String,
        val entries: List<EmojiEntry>
    )

    companion object {
        private const val TAG = "HK/EmojiParser"
        private const val EMOJI_FILE = "emoji-test.txt"
        private const val SYMBOLS_FILE = "symbols-test.txt"
        private const val IPA_GROUP = "IPA Phonetics"

        /** Asset files to load (emoji + symbols). */
        private val ASSET_FILES = listOf(EMOJI_FILE, SYMBOLS_FILE)

        /** Pattern to match syllable mappings: [en:sh,de:sch] */
        private val SYLLABLE_PATTERN = Regex("""\[([^\]]+)\]\s*$""")

        private const val GROUP_PREFIX = "# group: "
        private const val SUBGROUP_PREFIX = "# subgroup: "

        // Legacy compatibility - maps for direct lookup
        @JvmField
        val SKIN_TONE_COLORS: Map<Int, EmojiColor> =
            SkinTone.entries.associateBy({ it.codePoint }, { EmojiColor.fromSkinTone(it) })

        @JvmField
        val COLOR_NAME_MAP: Map<String, EmojiColor> =
            ColorName.entries.flatMap { color ->
                color.keywords.map { keyword -> keyword to EmojiColor.fromColorName(color) }
            }.toMap()

        /**
         * Detects color from emoji name by looking for color keywords.
         */
        @JvmStatic
        fun detectColorFromName(name: String): EmojiColor? =
            ColorName.fromName(name)?.let { EmojiColor.fromColorName(it) }

        @Volatile
        private var instance: EmojiParser? = null

        @JvmStatic
        fun getInstance(context: Context): EmojiParser {
            return instance ?: synchronized(this) {
                instance ?: EmojiParser().also {
                    it.load(context)
                    instance = it
                }
            }
        }

        /**
         * Create a parser from an arbitrary [InputStream].
         * Useful for testing or loading from non-asset sources.
         */
        @JvmStatic
        fun parseFrom(inputStream: InputStream): EmojiParser {
            return EmojiParser().also {
                BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                    it.parse(reader)
                }
            }
        }

        /**
         * Create a parser from multiple [InputStream]s.
         * Useful for loading both emoji and symbol files in tests.
         */
        @JvmStatic
        fun parseFrom(vararg inputStreams: InputStream): EmojiParser {
            return EmojiParser().also { parser ->
                for (stream in inputStreams) {
                    BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { reader ->
                        parser.parse(reader)
                    }
                }
            }
        }
    }

    private val groups = mutableListOf<EmojiGroup>()
    private val allFullyQualified = mutableListOf<EmojiEntry>()
    private val byGroup = mutableMapOf<String, EmojiGroup>()

    // IPA syllable mappings
    private val ipaMappings = mutableMapOf<String, IPAMapping>()  // IPA symbol -> mapping
    private val syllableToIpa = HashBiMap.create<String, String>()  // "lang:syllable" <-> IPA

    /** All parsed groups in file order. */
    fun getGroups(): List<EmojiGroup> = groups

    /** All fully-qualified emoji entries. */
    fun getAllFullyQualified(): List<EmojiEntry> = allFullyQualified

    /** Look up a group by name (e.g. "Smileys & Emotion"). */
    fun getGroup(name: String): EmojiGroup? = byGroup[name]

    /** Get group names in file order. */
    fun getGroupNames(): List<String> = groups.map { it.name }

    /** Search emoji by name substring, returning fully-qualified matches. */
    fun search(query: String): List<EmojiEntry> {
        val lower = query.lowercase()
        return allFullyQualified.filter { it.name.lowercase().contains(lower) }
    }

    // ==================== Color-based Access ====================

    /** All emoji grouped by their associated color hex value. */
    private val byColor: Map<String, List<EmojiEntry>> by lazy {
        allFullyQualified
            .filter { it.color != null }
            .groupBy { it.color!!.hex }
    }

    /** All emoji grouped by skin tone. */
    private val bySkinTone: Map<SkinTone, List<EmojiEntry>> by lazy {
        allFullyQualified
            .filter { it.skinTone != null }
            .groupBy { it.skinTone!! }
    }

    /** All emoji grouped by color name. */
    private val byColorName: Map<ColorName, List<EmojiEntry>> by lazy {
        allFullyQualified
            .filter { it.colorName != null }
            .groupBy { it.colorName!! }
    }

    /** Get all unique colors found in the emoji set, sorted by hue. */
    fun getColors(): List<EmojiColor> {
        val colors = mutableSetOf<EmojiColor>()
        for (entry in allFullyQualified) {
            entry.color?.let { colors.add(it) }
        }
        return colors.sortedBy { colorToHue(it.hex) }
    }

    /** Get all emoji that have a specific color (by hex). */
    fun getEmojiByColor(hex: String): List<EmojiEntry> {
        return byColor[hex.uppercase()] ?: byColor[hex] ?: emptyList()
    }

    /** Get all emoji with a specific skin tone. */
    fun getEmojiBySkinTone(tone: SkinTone): List<EmojiEntry> =
        bySkinTone[tone] ?: emptyList()

    /** Get all emoji with a specific named color. */
    fun getEmojiByColorName(color: ColorName): List<EmojiEntry> =
        byColorName[color] ?: emptyList()

    /** Get all emoji that have any color associated. */
    fun getColoredEmoji(): List<EmojiEntry> =
        allFullyQualified.filter { it.color != null }

    /** Get all emoji with skin tone modifiers. */
    fun getSkinToneEmoji(): List<EmojiEntry> =
        allFullyQualified.filter { it.skinTone != null }

    /** Get a virtual "Color" group that organizes emoji by their color. */
    fun getColorGroup(): EmojiGroup {
        val subgroups = getColors().map { color ->
            EmojiSubgroup(
                name = color.name,
                group = "Color",
                entries = getEmojiByColor(color.hex)
            )
        }.filter { it.entries.isNotEmpty() }

        return EmojiGroup("Color", subgroups)
    }

    // ==================== Filter / Breadcrumb API ====================

    /**
     * Filter criteria for narrowing emoji search results.
     * Use with [filter] to create breadcrumb-style navigation.
     *
     * @property color Unified color filter (can be [EmojiColorValue.Named] or [EmojiColorValue.Skin])
     */
    data class EmojiFilter(
        val group: String? = null,
        val subgroup: String? = null,
        val color: EmojiColorValue? = null,
        val query: String? = null
    ) {
        /** Skin tone filter (if color is a skin tone). */
        val skinTone: SkinTone?
            get() = (color as? EmojiColorValue.Skin)?.tone

        /** Color name filter (if color is a named color). */
        val colorName: ColorName?
            get() = (color as? EmojiColorValue.Named)?.color

        /** Returns a human-readable breadcrumb trail. */
        fun toBreadcrumbs(): List<String> = buildList {
            group?.let { add(it) }
            subgroup?.let { add(it) }
            color?.let { add(it.displayName) }
            query?.let { add("\"$it\"") }
        }

        override fun toString(): String = toBreadcrumbs().joinToString(" > ")
    }

    /**
     * Builder for progressively filtering emoji with breadcrumb-style navigation.
     *
     * Usage:
     * ```
     * parser.filter()
     *     .group("Smileys & Emotion")
     *     .subgroup("face-smiling")
     *     .results()
     *
     * parser.filter()
     *     .color(EmojiColorValue.Named(ColorName.RED))
     *     .query("heart")
     *     .results()
     * ```
     */
    inner class EmojiFilterBuilder {
        private var criteria = EmojiFilter()

        /** Filter by group (category). */
        fun group(name: String) = apply { criteria = criteria.copy(group = name) }

        /** Filter by subgroup (subcategory). */
        fun subgroup(name: String) = apply { criteria = criteria.copy(subgroup = name) }

        /** Filter by unified color value (skin tone or named color). */
        fun color(value: EmojiColorValue) = apply { criteria = criteria.copy(color = value) }

        /** Filter by skin tone (convenience for [color] with [EmojiColorValue.Skin]). */
        fun skinTone(tone: SkinTone) = color(EmojiColorValue.Skin(tone))

        /** Filter by named color (convenience for [color] with [EmojiColorValue.Named]). */
        fun colorName(colorName: ColorName) = color(EmojiColorValue.Named(colorName))

        /** Filter by name substring (description). */
        fun query(text: String) = apply { criteria = criteria.copy(query = text) }

        /** Get the current filter criteria. */
        fun getCriteria(): EmojiFilter = criteria

        /** Get available groups given current filters. */
        fun availableGroups(): List<String> {
            return applyFilters()
                .map { it.group }
                .distinct()
        }

        /** Get available subgroups given current filters. */
        fun availableSubgroups(): List<String> {
            return applyFilters()
                .map { it.subgroup }
                .distinct()
        }

        /** Get available skin tones given current filters. */
        fun availableSkinTones(): List<SkinTone> {
            return applyFilters()
                .mapNotNull { it.skinTone }
                .distinct()
        }

        /** Get available color names given current filters. */
        fun availableColorNames(): List<ColorName> {
            return applyFilters()
                .mapNotNull { it.colorName }
                .distinct()
        }

        /** Get available color values (unified) given current filters. */
        fun availableColorValues(): List<EmojiColorValue> {
            return applyFilters()
                .mapNotNull { it.colorValue }
                .distinctBy { it.hex }
                .sortedBy { colorToHue(it.hex) }
        }

        /** Get available colors (legacy) given current filters. */
        fun availableColors(): List<EmojiColor> {
            return availableColorValues().map { it.toEmojiColor() }
        }

        /** Get the count of results without fetching all entries. */
        fun count(): Int = applyFilters().size

        /** Get filtered results. */
        fun results(): List<EmojiEntry> = applyFilters()

        private fun applyFilters(): List<EmojiEntry> {
            var result: List<EmojiEntry> = allFullyQualified

            criteria.group?.let { g ->
                result = result.filter { it.group == g }
            }

            criteria.subgroup?.let { s ->
                result = result.filter { it.subgroup == s }
            }

            criteria.color?.let { colorValue ->
                result = when (colorValue) {
                    is EmojiColorValue.Skin -> result.filter { it.skinTone == colorValue.tone }
                    is EmojiColorValue.Named -> result.filter { it.colorName == colorValue.color }
                }
            }

            criteria.query?.let { q ->
                val lower = q.lowercase()
                result = result.filter { it.name.lowercase().contains(lower) }
            }

            return result
        }
    }

    /** Create a new filter builder for breadcrumb-style navigation. */
    fun filter(): EmojiFilterBuilder = EmojiFilterBuilder()

    /**
     * Get subgroups for a specific group.
     * Useful for breadcrumb navigation at category level.
     */
    fun getSubgroups(groupName: String): List<EmojiSubgroup> {
        return byGroup[groupName]?.subgroups ?: emptyList()
    }

    /**
     * Get subgroup names for a specific group.
     * Useful for breadcrumb UI.
     */
    fun getSubgroupNames(groupName: String): List<String> {
        return getSubgroups(groupName).map { it.name }
    }

    /**
     * Get all available skin tones that have emoji in the dataset.
     */
    fun getAvailableSkinTones(): List<SkinTone> {
        return SkinTone.entries.filter { bySkinTone.containsKey(it) }
    }

    /**
     * Get all available color names that have emoji in the dataset.
     */
    fun getAvailableColorNames(): List<ColorName> {
        return ColorName.entries.filter { byColorName.containsKey(it) }
    }

    /**
     * Get all available color values (both skin tones and named colors) that have emoji.
     */
    fun getAvailableColorValues(): List<EmojiColorValue> {
        val result = mutableListOf<EmojiColorValue>()
        for (color in ColorName.entries) {
            if (byColorName.containsKey(color)) {
                result.add(EmojiColorValue.Named(color))
            }
        }
        for (tone in SkinTone.entries) {
            if (bySkinTone.containsKey(tone)) {
                result.add(EmojiColorValue.Skin(tone))
            }
        }
        return result
    }

    /** Convert hex color to hue for sorting. */
    private fun colorToHue(hex: String): Float {
        return try {
            val color = Color.parseColor(hex)
            val hsv = FloatArray(3)
            Color.colorToHSV(color, hsv)
            hsv[0] // hue
        } catch (e: Exception) {
            0f
        }
    }

    // ==================== IPA Syllable Access ====================

    /**
     * Get the IPA symbol for a syllable key.
     * @param key Syllable key in format "lang:syllable" (e.g., "en:sh")
     * @return The IPA symbol (e.g., "ʃ") or null if not found
     */
    fun getIpaFromSyllable(key: String): String? = syllableToIpa[key.lowercase()]

    /**
     * Get the IPA symbol for a language and syllable.
     * @param language The language
     * @param syllable The syllable text (e.g., "sh")
     * @return The IPA symbol or null if not found
     */
    fun getIpaFromSyllable(language: Language, syllable: String): String? =
        syllableToIpa["${language.tag}:${syllable.lowercase()}"]

    /**
     * Get all syllable keys for an IPA symbol.
     * @param ipa The IPA symbol (e.g., "ʃ")
     * @return List of syllable keys (e.g., ["en:sh", "de:sch"])
     */
    fun getSyllablesFromIpa(ipa: String): List<String> {
        val mapping = ipaMappings[ipa] ?: return emptyList()
        return mapping.allKeys
    }

    /**
     * Get syllables for an IPA symbol in a specific language.
     * @param ipa The IPA symbol
     * @param language The target language
     * @return List of syllables in that language
     */
    fun getSyllablesFromIpa(ipa: String, language: Language): List<String> {
        val mapping = ipaMappings[ipa] ?: return emptyList()
        return mapping.syllablesFor(language)
    }

    /**
     * Get IPA mapping for a symbol.
     * @param ipa The IPA symbol
     * @return The full IPAMapping or null
     */
    fun getIpaMapping(ipa: String): IPAMapping? = ipaMappings[ipa]

    /**
     * Get all IPA mappings that have syllables for a language.
     * @param language The language to filter by
     * @return List of IPAMappings with syllables in that language
     */
    fun getIpaMappingsForLanguage(language: Language): List<IPAMapping> =
        ipaMappings.values.filter { it.hasLanguage(language) }

    /**
     * Get IPA suggestions for text input in a specific language.
     * Returns IPA symbols that match syllable prefixes.
     *
     * @param text The text being typed
     * @param language The current keyboard language
     * @param maxResults Maximum number of suggestions to return
     * @return List of pairs (IPA symbol, syllable that matched)
     */
    fun getIpaSuggestions(text: String, language: Language, maxResults: Int = 5): List<Pair<String, String>> {
        if (text.isEmpty()) return emptyList()

        val lowerText = text.lowercase()
        val prefix = "${language.tag}:"

        return syllableToIpa.entries
            .filter { (key, _) ->
                key.startsWith(prefix) && key.removePrefix(prefix).startsWith(lowerText)
            }
            .take(maxResults)
            .map { (key, ipa) -> ipa to key.removePrefix(prefix) }
    }

    /**
     * Get IPA suggestions matching exact syllable in any language.
     * @param syllable The syllable to look up
     * @return List of pairs (IPA symbol, language tag)
     */
    fun getIpaSuggestionsAllLanguages(syllable: String): List<Pair<String, Language>> {
        val lowerSyllable = syllable.lowercase()
        return Language.entries.mapNotNull { lang ->
            val key = "${lang.tag}:$lowerSyllable"
            syllableToIpa[key]?.let { ipa -> ipa to lang }
        }
    }

    /**
     * Check if a syllable key exists in the mapping.
     */
    fun hasSyllable(key: String): Boolean = syllableToIpa.containsKey(key.lowercase())

    /**
     * Check if a syllable exists for a language.
     */
    fun hasSyllable(language: Language, syllable: String): Boolean =
        syllableToIpa.containsKey("${language.tag}:${syllable.lowercase()}")

    /**
     * Get an immutable view of the syllable-to-IPA BiMap.
     */
    fun getSyllableToIpaBiMap(): ImmutableBiMap<String, String> =
        ImmutableBiMap.copyOf(syllableToIpa)

    /**
     * Get all IPA mappings.
     */
    fun getAllIpaMappings(): Collection<IPAMapping> = ipaMappings.values

    /**
     * Get count of IPA symbols with syllable mappings.
     */
    fun getIpaMappingCount(): Int = ipaMappings.size

    /**
     * Get count of total syllable keys.
     */
    fun getSyllableCount(): Int = syllableToIpa.size

    private fun load(context: Context) {
        for (assetFile in ASSET_FILES) {
            try {
                context.assets.open(assetFile).use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                        parse(reader)
                    }
                }
                Log.i(TAG, "Loaded $assetFile")
            } catch (e: Exception) {
                Log.w(TAG, "Failed to load $assetFile: ${e.message}")
            }
        }
        val coloredCount = allFullyQualified.count { it.color != null }
        val skinToneCount = allFullyQualified.count { it.skinTone != null }
        Log.i(TAG, "Total: ${allFullyQualified.size} fully-qualified entries in ${groups.size} groups ($coloredCount colored, $skinToneCount with skin tone)")
    }

    private fun parse(reader: BufferedReader) {
        var currentGroup = ""
        var currentSubgroup = ""

        // Accumulate subgroups for the current group
        val currentSubgroups = mutableListOf<EmojiSubgroup>()
        val currentEntries = mutableListOf<EmojiEntry>()

        reader.forEachLine { line ->
            when {
                line.startsWith(GROUP_PREFIX) -> {
                    // Flush previous subgroup
                    if (currentSubgroup.isNotEmpty() && currentEntries.isNotEmpty()) {
                        currentSubgroups.add(EmojiSubgroup(currentSubgroup, currentGroup, currentEntries.toList()))
                        currentEntries.clear()
                    }
                    // Flush previous group
                    if (currentGroup.isNotEmpty() && currentSubgroups.isNotEmpty()) {
                        val group = EmojiGroup(currentGroup, currentSubgroups.toList())
                        groups.add(group)
                        byGroup[currentGroup] = group
                        currentSubgroups.clear()
                    }
                    currentGroup = line.removePrefix(GROUP_PREFIX).trim()
                    currentSubgroup = ""
                }

                line.startsWith(SUBGROUP_PREFIX) -> {
                    // Flush previous subgroup entries
                    if (currentSubgroup.isNotEmpty() && currentEntries.isNotEmpty()) {
                        currentSubgroups.add(EmojiSubgroup(currentSubgroup, currentGroup, currentEntries.toList()))
                        currentEntries.clear()
                    }
                    currentSubgroup = line.removePrefix(SUBGROUP_PREFIX).trim()
                }

                line.isNotEmpty() && !line.startsWith("#") -> {
                    parseLine(line, currentGroup, currentSubgroup)?.let { entry ->
                        currentEntries.add(entry)
                        if (entry.status == Status.FULLY_QUALIFIED) {
                            allFullyQualified.add(entry)
                        }
                    }
                }
            }
        }

        // Flush final subgroup and group
        if (currentSubgroup.isNotEmpty() && currentEntries.isNotEmpty()) {
            currentSubgroups.add(EmojiSubgroup(currentSubgroup, currentGroup, currentEntries.toList()))
        }
        if (currentGroup.isNotEmpty() && currentSubgroups.isNotEmpty()) {
            val group = EmojiGroup(currentGroup, currentSubgroups.toList())
            groups.add(group)
            byGroup[currentGroup] = group
        }
    }

    /**
     * Parse a single data line.
     *
     * Format: `1F600 ; fully-qualified # 😀 E1.0 grinning face`
     *
     * IPA entries may include syllable mappings:
     * `0283 ; fully-qualified # ʃ E1.0 voiceless postalveolar fricative [en:sh,de:sch,fr:ch]`
     */
    private fun parseLine(line: String, group: String, subgroup: String): EmojiEntry? {
        // Split on semicolon: "1F600" and "fully-qualified # 😀 E1.0 grinning face"
        val semiIdx = line.indexOf(';')
        if (semiIdx < 0) return null

        val codePointsStr = line.substring(0, semiIdx).trim()
        val rest = line.substring(semiIdx + 1).trim()

        // Split rest on '#': "fully-qualified" and "😀 E1.0 grinning face"
        val hashIdx = rest.indexOf('#')
        if (hashIdx < 0) return null

        val statusStr = rest.substring(0, hashIdx).trim()
        val comment = rest.substring(hashIdx + 1).trim()

        val status = Status.fromString(statusStr) ?: return null

        // Parse code points
        val codePoints = try {
            codePointsStr.split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toInt(16) }
                .toIntArray()
        } catch (e: NumberFormatException) {
            Log.w(TAG, "Bad code points: $codePointsStr")
            return null
        }

        // Build the emoji string from code points
        val emoji = String(codePoints, 0, codePoints.size)

        // Parse comment: "😀 E1.0 grinning face"
        // The emoji character(s) come first, then version Exx.x, then name
        val versionRegex = Regex("""E(\d+\.\d+)\s+(.+)""")
        val match = versionRegex.find(comment)
        val version: String
        var name: String
        if (match != null) {
            version = "E${match.groupValues[1]}"
            name = match.groupValues[2]
        } else {
            version = ""
            name = comment
        }

        // Check for IPA syllable mappings: [en:sh,de:sch,fr:ch]
        val syllableMatch = SYLLABLE_PATTERN.find(name)
        if (syllableMatch != null && group == IPA_GROUP) {
            val syllablesStr = syllableMatch.groupValues[1]
            name = name.substring(0, syllableMatch.range.first).trim()

            // Parse syllable mappings and build BiMap
            parseIpaSyllables(emoji, codePoints[0], name, syllablesStr)
        }

        return EmojiEntry(
            codePoints = codePoints,
            status = status,
            emoji = emoji,
            version = version,
            name = name,
            group = group,
            subgroup = subgroup
        )
    }

    /**
     * Parse IPA syllable mappings from the bracketed format.
     * Format: "en:sh,de:sch,fr:ch"
     */
    private fun parseIpaSyllables(ipa: String, codePoint: Int, name: String, syllablesStr: String) {
        val syllablesByLang = mutableMapOf<Language, MutableList<String>>()

        for (part in syllablesStr.split(",")) {
            val trimmed = part.trim()
            if (trimmed.isEmpty()) continue

            val colonIdx = trimmed.indexOf(':')
            if (colonIdx < 0) continue

            val langTag = trimmed.substring(0, colonIdx)
            val syllable = trimmed.substring(colonIdx + 1)

            val language = Language.fromTag(langTag) ?: continue

            syllablesByLang.getOrPut(language) { mutableListOf() }.add(syllable)

            // Add to BiMap: "lang:syllable" -> IPA
            val key = "${language.tag}:${syllable.lowercase()}"
            if (!syllableToIpa.containsKey(key)) {
                syllableToIpa[key] = ipa
            } else {
                Log.w(TAG, "Duplicate syllable key: $key (already maps to ${syllableToIpa[key]}, ignoring $ipa)")
            }
        }

        if (syllablesByLang.isNotEmpty()) {
            val mapping = IPAMapping(
                ipa = ipa,
                codePoint = codePoint,
                name = name,
                syllables = syllablesByLang.mapValues { it.value.toList() }
            )
            ipaMappings[ipa] = mapping
        }
    }
}
