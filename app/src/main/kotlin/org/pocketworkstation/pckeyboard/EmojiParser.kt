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

    data class EmojiEntry(
        val codePoints: IntArray,
        val status: Status,
        val emoji: String,
        val version: String,
        val name: String,
        val group: String,
        val subgroup: String
    ) {
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

    private fun load(context: Context) {
        try {
            context.assets.open(ASSET_FILE).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                    parse(reader)
                }
            }
            Log.i(TAG, "Loaded ${allFullyQualified.size} fully-qualified emoji in ${groups.size} groups")
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
