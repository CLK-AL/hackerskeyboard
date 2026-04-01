package org.pocketworkstation.unikey

/**
 * UniKey Syllable - phonetic minimal word unit.
 * A syllable is a consonant followed by vowel(s)/nikud.
 * Hue is computed from phonetic feature embedding so similar sounds cluster.
 */
data class UniKeySyllable(
    val consonant: String,  // IPA consonant (or empty for vowel-only)
    val vowel: String,      // IPA vowel
    val original: String    // Original text (Hebrew or English)
) {
    /**
     * Compute hue (0-360) from phonetic features.
     * Similar syllables get similar hues via feature embedding.
     */
    val hue: Int by lazy { computeHue() }

    private fun computeHue(): Int {
        // Vowel determines primary hue region (60° each)
        val vowelHue = VOWEL_HUE[vowel] ?: VOWEL_HUE[vowel.firstOrNull()?.toString()] ?: 0

        // Consonant offsets within vowel region based on features
        val consonantOffset = computeConsonantOffset()

        return (vowelHue + consonantOffset) % 360
    }

    private fun computeConsonantOffset(): Int {
        if (consonant.isEmpty()) return 0
        val features = CONSONANT_FEATURES[consonant] ?: return (consonant.hashCode() % 30)

        // Place: 0-5 mapped to 0-30° offset
        // Manner: 0-5 mapped to slight variation
        // Voicing: small shift
        val placeOffset = features.place * 5
        val mannerOffset = features.manner * 2
        val voiceOffset = if (features.voiced) 0 else 1

        return (placeOffset + mannerOffset + voiceOffset) % 50
    }

    companion object {
        /**
         * Primary hue by vowel quality (6 vowel regions, 60° each).
         */
        private val VOWEL_HUE = mapOf(
            // Low vowels - warm (red/orange)
            "a" to 0,
            "A" to 5,
            "ɑ" to 10,
            "æ" to 15,

            // Mid-low vowels - orange/yellow
            "ɛ" to 40,
            "E" to 45,
            "e" to 50,

            // High front - green
            "i" to 120,
            "ɪ" to 125,
            "I" to 120,

            // Mid back - cyan/teal
            "o" to 180,
            "ɔ" to 185,
            "O" to 180,

            // High back - blue
            "u" to 240,
            "ʊ" to 245,
            "U" to 240,

            // Central/schwa - purple
            "ə" to 300,
            "@" to 300,
            "ʌ" to 310
        )

        /**
         * Consonant phonetic features for embedding.
         */
        data class ConsonantFeatures(
            val place: Int,    // 0=labial, 1=dental, 2=alveolar, 3=palatal, 4=velar, 5=glottal
            val manner: Int,   // 0=stop, 1=fricative, 2=nasal, 3=approximant, 4=trill, 5=affricate
            val voiced: Boolean
        )

        private val CONSONANT_FEATURES = mapOf(
            // Labials
            "p" to ConsonantFeatures(0, 0, false),
            "b" to ConsonantFeatures(0, 0, true),
            "m" to ConsonantFeatures(0, 2, true),
            "f" to ConsonantFeatures(0, 1, false),
            "v" to ConsonantFeatures(0, 1, true),

            // Dentals/Alveolars
            "t" to ConsonantFeatures(2, 0, false),
            "d" to ConsonantFeatures(2, 0, true),
            "n" to ConsonantFeatures(2, 2, true),
            "s" to ConsonantFeatures(2, 1, false),
            "z" to ConsonantFeatures(2, 1, true),
            "l" to ConsonantFeatures(2, 3, true),
            "r" to ConsonantFeatures(2, 4, true),
            "ts" to ConsonantFeatures(2, 5, false),

            // Palatals
            "ʃ" to ConsonantFeatures(3, 1, false),
            "sh" to ConsonantFeatures(3, 1, false),
            "ʒ" to ConsonantFeatures(3, 1, true),
            "j" to ConsonantFeatures(3, 3, true),
            "y" to ConsonantFeatures(3, 3, true),
            "tʃ" to ConsonantFeatures(3, 5, false),
            "ch" to ConsonantFeatures(3, 5, false),
            "dʒ" to ConsonantFeatures(3, 5, true),

            // Velars
            "k" to ConsonantFeatures(4, 0, false),
            "g" to ConsonantFeatures(4, 0, true),
            "x" to ConsonantFeatures(4, 1, false),  // Hebrew chet/khaf
            "ŋ" to ConsonantFeatures(4, 2, true),

            // Glottals
            "h" to ConsonantFeatures(5, 1, false),
            "ʔ" to ConsonantFeatures(5, 0, false),
            "" to ConsonantFeatures(5, 0, false)  // silent (alef/ayin)
        )

        /**
         * Parse Hebrew word into UniKey syllables.
         */
        fun parseHebrew(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            val w = word.replace(Regex("[,.\\-;:!?\\s]+$"), "")

            var i = 0
            while (i < w.length) {
                val c = w[i]
                val cp = c.code

                // Hebrew letter?
                if (cp in 0x05D0..0x05EA) {
                    val consonantIpa = getHebrewConsonantIpa(c, w, i)
                    val (vowelIpa, vowelLen, originalVowels) = collectVowels(w, i + 1)

                    syllables.add(UniKeySyllable(
                        consonant = consonantIpa,
                        vowel = vowelIpa,
                        original = c.toString() + originalVowels
                    ))

                    i += 1 + vowelLen
                } else {
                    i++
                }
            }

            return syllables
        }

        /**
         * Parse English word into UniKey syllables.
         */
        fun parseEnglish(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            val w = word.lowercase().replace(Regex("[^a-z]"), "")

            var i = 0
            while (i < w.length) {
                // Collect consonant cluster
                val consonantStart = i
                while (i < w.length && w[i] !in "aeiou") i++
                val consonants = w.substring(consonantStart, i)

                // Collect vowel cluster
                val vowelStart = i
                while (i < w.length && w[i] in "aeiou") i++
                val vowels = w.substring(vowelStart, i)

                if (consonants.isNotEmpty() || vowels.isNotEmpty()) {
                    syllables.add(UniKeySyllable(
                        consonant = mapEnglishConsonant(consonants),
                        vowel = mapEnglishVowel(vowels),
                        original = consonants + vowels
                    ))
                }
            }

            return syllables
        }

        private fun getHebrewConsonantIpa(letter: Char, word: String, pos: Int): String {
            // Check for dagesh after letter
            val hasDagesh = pos + 1 < word.length && word[pos + 1].code == 0x05BC

            return when (letter) {
                '\u05D0' -> ""     // alef - silent
                '\u05D1' -> if (hasDagesh) "b" else "v"
                '\u05D2' -> "g"
                '\u05D3' -> "d"
                '\u05D4' -> "h"
                '\u05D5' -> "v"
                '\u05D6' -> "z"
                '\u05D7' -> "x"    // chet
                '\u05D8' -> "t"
                '\u05D9' -> "j"
                '\u05DA', '\u05DB' -> if (hasDagesh) "k" else "x"
                '\u05DC' -> "l"
                '\u05DD', '\u05DE' -> "m"
                '\u05DF', '\u05E0' -> "n"
                '\u05E1' -> "s"
                '\u05E2' -> ""     // ayin - silent
                '\u05E3', '\u05E4' -> if (hasDagesh) "p" else "f"
                '\u05E5', '\u05E6' -> "ts"
                '\u05E7' -> "k"
                '\u05E8' -> "r"
                '\u05E9' -> {
                    // Check for sin dot
                    val hasSinDot = (pos + 1 < word.length && word[pos + 1].code == 0x05C2) ||
                                    (pos + 2 < word.length && word[pos + 2].code == 0x05C2)
                    if (hasSinDot) "s" else "sh"
                }
                '\u05EA' -> "t"
                else -> ""
            }
        }

        private data class VowelResult(val ipa: String, val length: Int, val original: String)

        private fun collectVowels(word: String, start: Int): VowelResult {
            val result = StringBuilder()
            val original = StringBuilder()
            var i = start

            while (i < word.length) {
                val cp = word[i].code
                val vowelIpa = when (cp) {
                    0x05B0 -> "@"   // shva
                    0x05B1 -> "e"   // hataf segol
                    0x05B2 -> "a"   // hataf patach
                    0x05B3 -> "o"   // hataf kamatz
                    0x05B4 -> "i"   // chirik
                    0x05B5 -> "e"   // tzere
                    0x05B6 -> "E"   // segol
                    0x05B7 -> "a"   // patach
                    0x05B8 -> "a"   // kamatz
                    0x05B9 -> "o"   // cholam
                    0x05BA -> "o"   // cholam haser
                    0x05BB -> "u"   // kubutz
                    0x05BC -> null  // dagesh - skip but include
                    0x05C1 -> null  // shin dot
                    0x05C2 -> null  // sin dot
                    else -> break   // Not a vowel/diacritic
                }

                original.append(word[i])
                if (vowelIpa != null) {
                    result.append(vowelIpa)
                }
                i++
            }

            return VowelResult(
                result.toString().ifEmpty { "a" },  // Default to 'a' if no vowel
                i - start,
                original.toString()
            )
        }

        private fun mapEnglishConsonant(consonants: String): String {
            return when (consonants) {
                "sh" -> "sh"
                "ch" -> "ch"
                "th" -> "t"
                "ph" -> "f"
                "wh" -> "w"
                "ck" -> "k"
                "ng" -> "ŋ"
                else -> consonants.take(1)
            }
        }

        private fun mapEnglishVowel(vowels: String): String {
            return when (vowels) {
                "a" -> "a"
                "e" -> "e"
                "i" -> "i"
                "o" -> "o"
                "u" -> "u"
                "ai", "ay" -> "e"
                "ea", "ee" -> "i"
                "oa", "ow" -> "o"
                "oo" -> "u"
                "ou" -> "a"
                "oi", "oy" -> "o"
                else -> vowels.firstOrNull()?.toString() ?: "a"
            }
        }

        /**
         * Get hue for a complete word (uses last syllable for rhyme coloring).
         */
        fun wordHue(word: String, isHebrew: Boolean): Int {
            val syllables = if (isHebrew) parseHebrew(word) else parseEnglish(word)
            return syllables.lastOrNull()?.hue ?: 0
        }

        /**
         * HSL color string.
         */
        fun hsl(hue: Int, saturation: Int = 70, lightness: Int = 65): String {
            return "hsl($hue, $saturation%, $lightness%)"
        }

        /**
         * Get color for word ending (for rhyme visualization).
         */
        fun wordEndColor(word: String, isHebrew: Boolean): String {
            return hsl(wordHue(word, isHebrew), 80, 72)
        }
    }
}
