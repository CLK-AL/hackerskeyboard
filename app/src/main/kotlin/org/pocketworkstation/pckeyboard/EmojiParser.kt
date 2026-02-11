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
     * Represents a color with hex value and name.
     */
    data class EmojiColor(
        val hex: String,
        val name: String
    ) {
        /** Returns the color as an Android color int (with alpha). */
        fun toColorInt(): Int = android.graphics.Color.parseColor(hex)

        override fun toString(): String = "$name ($hex)"
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
        /** The color associated with this emoji, if any. */
        val color: EmojiColor?
            get() = detectColor()

        private fun detectColor(): EmojiColor? {
            // Check for skin tone modifier in sequence
            for (cp in codePoints) {
                SKIN_TONE_COLORS[cp]?.let { return it }
            }
            // Check for color in name
            return detectColorFromName(name)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is EmojiEntry) return false
            return codePoints.contentEquals(other.codePoints)
        }

        override fun hashCode(): Int = codePoints.contentHashCode()

        override fun toString(): String = "$emoji $name"
    }

    enum class Status(val value: String) {
        COMPONENT("component"),
        FULLY_QUALIFIED("fully-qualified"),
        MINIMALLY_QUALIFIED("minimally-qualified"),
        UNQUALIFIED("unqualified");

        companion object {
            fun fromString(s: String): Status? = entries.find { it.value == s.trim() }
        }
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

        // Skin tone modifier codepoints mapped to their approximate colors
        // Based on Fitzpatrick scale
        @JvmField
        val SKIN_TONE_COLORS = mapOf(
            0x1F3FB to EmojiColor("#FFDFC4", "light skin tone"),
            0x1F3FC to EmojiColor("#F0D5BE", "medium-light skin tone"),
            0x1F3FD to EmojiColor("#D2A67D", "medium skin tone"),
            0x1F3FE to EmojiColor("#A26D49", "medium-dark skin tone"),
            0x1F3FF to EmojiColor("#5C3D2E", "dark skin tone")
        )

        // Common color names mapped to hex values
        @JvmField
        val COLOR_NAME_MAP = mapOf(
            "red" to EmojiColor("#FF0000", "red"),
            "orange" to EmojiColor("#FFA500", "orange"),
            "yellow" to EmojiColor("#FFFF00", "yellow"),
            "green" to EmojiColor("#00FF00", "green"),
            "blue" to EmojiColor("#0000FF", "blue"),
            "light blue" to EmojiColor("#87CEEB", "light blue"),
            "purple" to EmojiColor("#800080", "purple"),
            "pink" to EmojiColor("#FFC0CB", "pink"),
            "brown" to EmojiColor("#8B4513", "brown"),
            "black" to EmojiColor("#000000", "black"),
            "white" to EmojiColor("#FFFFFF", "white"),
            "grey" to EmojiColor("#808080", "grey"),
            "gray" to EmojiColor("#808080", "gray")
        )

        /**
         * Detects color from emoji name by looking for color keywords.
         */
        @JvmStatic
        fun detectColorFromName(name: String): EmojiColor? {
            val lower = name.lowercase()
            // Check for "light blue" before "blue" (longer match first)
            if (lower.contains("light blue")) return COLOR_NAME_MAP["light blue"]
            // Check other colors
            for ((colorName, color) in COLOR_NAME_MAP) {
                if (lower.contains(colorName)) return color
            }
            return null
        }

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
        return byColor[hex.uppercase()] ?: byColor[hex.lowercase()] ?: emptyList()
    }

    /** Get all emoji that have any color associated. */
    fun getColoredEmoji(): List<EmojiEntry> {
        return allFullyQualified.filter { it.color != null }
    }

    /** Get all emoji with skin tone modifiers. */
    fun getSkinToneEmoji(): List<EmojiEntry> {
        return allFullyQualified.filter { entry ->
            entry.codePoints.any { it in SKIN_TONE_COLORS }
        }
    }

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

    /** Convert hex color to hue for sorting. */
    private fun colorToHue(hex: String): Float {
        return try {
            val color = android.graphics.Color.parseColor(hex)
            val hsv = FloatArray(3)
            android.graphics.Color.colorToHSV(color, hsv)
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
            Log.i(TAG, "Loaded ${allFullyQualified.size} fully-qualified emoji in ${groups.size} groups ($coloredCount with color)")
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
