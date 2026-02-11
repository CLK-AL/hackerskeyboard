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
        private const val ASSET_FILE = "emoji-test.txt"

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
    }

    private val groups = mutableListOf<EmojiGroup>()
    private val allFullyQualified = mutableListOf<EmojiEntry>()
    private val byGroup = mutableMapOf<String, EmojiGroup>()

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
     */
    data class EmojiFilter(
        val group: String? = null,
        val subgroup: String? = null,
        val skinTone: SkinTone? = null,
        val colorName: ColorName? = null,
        val query: String? = null
    ) {
        /** Returns a human-readable breadcrumb trail. */
        fun toBreadcrumbs(): List<String> = buildList {
            group?.let { add(it) }
            subgroup?.let { add(it) }
            skinTone?.let { add(it.displayName) }
            colorName?.let { add(it.displayName) }
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
     *     .colorName(ColorName.RED)
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

        /** Filter by skin tone. */
        fun skinTone(tone: SkinTone) = apply { criteria = criteria.copy(skinTone = tone) }

        /** Filter by named color. */
        fun colorName(color: ColorName) = apply { criteria = criteria.copy(colorName = color) }

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

        /** Get available colors (unified) given current filters. */
        fun availableColors(): List<EmojiColor> {
            return applyFilters()
                .mapNotNull { it.color }
                .distinctBy { it.hex }
                .sortedBy { colorToHue(it.hex) }
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

            criteria.skinTone?.let { tone ->
                result = result.filter { it.skinTone == tone }
            }

            criteria.colorName?.let { color ->
                result = result.filter { it.colorName == color }
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

    private fun load(context: Context) {
        try {
            context.assets.open(ASSET_FILE).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                    parse(reader)
                }
            }
            val coloredCount = allFullyQualified.count { it.color != null }
            val skinToneCount = allFullyQualified.count { it.skinTone != null }
            Log.i(TAG, "Loaded ${allFullyQualified.size} fully-qualified emoji in ${groups.size} groups ($coloredCount colored, $skinToneCount with skin tone)")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load $ASSET_FILE", e)
        }
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
        val name: String
        if (match != null) {
            version = "E${match.groupValues[1]}"
            name = match.groupValues[2]
        } else {
            version = ""
            name = comment
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
}
