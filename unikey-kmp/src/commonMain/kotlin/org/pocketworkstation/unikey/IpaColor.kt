package org.pocketworkstation.unikey

/**
 * IPA-based HSL color system for visual rhyme matching.
 * Words with similar IPA endings get similar hues.
 */
object IpaColor {

    /**
     * English word ending patterns mapped to IPA.
     * Order matters - more specific patterns first.
     */
    private val EN_ENDINGS = listOf(
        Regex("ing$") to "IN",
        Regex("tion$") to "shEn",
        Regex("ting$") to "tIN",
        Regex("ring$") to "rIN",
        Regex("ling$") to "lIN",
        Regex("ning$") to "nIN",
        Regex("ent$") to "Ent",
        Regex("ant$") to "ant",
        Regex("ine$") to "ajn",
        Regex("ate$") to "ejt",
        Regex("ight$") to "ajt",
        Regex("ite$") to "ajt",
        Regex("ake$") to "ejk",
        Regex("ade$") to "ejd",
        Regex("age$") to "Ij",
        Regex("ound$") to "awnd",
        Regex("own$") to "awn",
        Regex("out$") to "awt",
        Regex("ash$") to "ash",
        Regex("ush$") to "ush",
        Regex("ay$") to "ej",
        Regex("ey$") to "ej",
        Regex("oy$") to "oj",
        Regex("er$") to "Er",
        Regex("or$") to "Er",
        Regex("ar$") to "ar",
        Regex("le$") to "El",
        Regex("al$") to "El",
        Regex("ed$") to "d",
        Regex("es$") to "z",
        Regex("ly$") to "li",
        Regex("ck$") to "k",
        Regex("ss$") to "s",
        Regex("ll$") to "l"
    )

    /**
     * Convert English word to IPA ending (2-4 chars).
     */
    fun enIpa(word: String): String {
        val w = word.lowercase().replace(Regex("[^a-z]"), "")
        if (w.isEmpty()) return ""

        for ((pattern, ipa) in EN_ENDINGS) {
            if (pattern.containsMatchIn(w)) return ipa
        }
        return w.takeLast(3)
    }

    /**
     * Simplified consonant IPA for color hashing (ASCII-friendly).
     */
    private val CONSONANT_IPA = mapOf(
        '\u05D0' to "",    // alef - silent
        '\u05D1' to "v",   // bet
        '\u05D2' to "g",   // gimel
        '\u05D3' to "d",   // dalet
        '\u05D4' to "h",   // he
        '\u05D5' to "v",   // vav
        '\u05D6' to "z",   // zayin
        '\u05D7' to "x",   // chet
        '\u05D8' to "t",   // tet
        '\u05D9' to "j",   // yod
        '\u05DA' to "x",   // kaf sofit
        '\u05DB' to "x",   // kaf
        '\u05DC' to "l",   // lamed
        '\u05DD' to "m",   // mem sofit
        '\u05DE' to "m",   // mem
        '\u05DF' to "n",   // nun sofit
        '\u05E0' to "n",   // nun
        '\u05E1' to "s",   // samech
        '\u05E2' to "",    // ayin - silent
        '\u05E3' to "f",   // pe sofit
        '\u05E4' to "f",   // pe
        '\u05E5' to "ts",  // tsadi sofit
        '\u05E6' to "ts",  // tsadi
        '\u05E7' to "k",   // qof
        '\u05E8' to "r",   // resh
        '\u05E9' to "sh",  // shin
        '\u05EA' to "t"    // tav
    )

    /**
     * Dagesh changes for BGDKPT letters.
     */
    private val DAGESH_IPA = mapOf(
        '\u05D1' to "b",   // bet -> b
        '\u05DB' to "k",   // kaf -> k
        '\u05E4' to "p"    // pe -> p
    )

    /**
     * Nikud (vowel marks) to simplified IPA for coloring.
     */
    private val NIKUD_IPA = mapOf(
        0x05B0 to "@",   // shva
        0x05B1 to "e",   // hataf segol
        0x05B2 to "a",   // hataf patach
        0x05B3 to "o",   // hataf kamatz
        0x05B4 to "i",   // chirik
        0x05B5 to "e",   // tzere
        0x05B6 to "E",   // segol
        0x05B7 to "a",   // patach
        0x05B8 to "a",   // kamatz
        0x05B9 to "o",   // cholam
        0x05BA to "o",   // cholam haser
        0x05BB to "u"    // kubutz
    )

    /**
     * Convert Hebrew word to IPA using nikud and consonants.
     */
    fun heIpa(word: String): String {
        val w = word.replace(Regex("[,.\\-;:!?\\s]+$"), "")
        val result = StringBuilder()
        var lastConsonant: Char? = null
        var lastConsonantPos = -1

        for (c in w) {
            val cp = c.code
            when {
                // Hebrew letters (consonants)
                cp in 0x05D0..0x05EA -> {
                    lastConsonant = c
                    lastConsonantPos = result.length
                    result.append(CONSONANT_IPA[c] ?: "")
                }
                // Dagesh
                cp == 0x05BC -> {
                    lastConsonant?.let { lc ->
                        DAGESH_IPA[lc]?.let { dageshIpa ->
                            if (lastConsonantPos >= 0) {
                                val before = result.substring(0, lastConsonantPos)
                                val consonantLen = CONSONANT_IPA[lc]?.length ?: 0
                                val after = if (lastConsonantPos + consonantLen <= result.length)
                                    result.substring(lastConsonantPos + consonantLen) else ""
                                result.clear()
                                result.append(before)
                                result.append(dageshIpa)
                                result.append(after)
                            }
                        }
                    }
                }
                // Shin dot (no change needed, default is shin)
                cp == 0x05C1 -> {}
                // Sin dot - change sh to s
                cp == 0x05C2 -> {
                    if (lastConsonantPos >= 0 && lastConsonant == '\u05E9') {
                        val before = result.substring(0, lastConsonantPos)
                        val after = if (lastConsonantPos + 2 <= result.length)
                            result.substring(lastConsonantPos + 2) else ""
                        result.clear()
                        result.append(before)
                        result.append("s")
                        result.append(after)
                    }
                }
                // Nikud (vowels)
                else -> {
                    NIKUD_IPA[cp]?.let { vowelIpa ->
                        result.append(vowelIpa)
                    }
                }
            }
        }
        return result.toString()
    }

    /**
     * Get IPA ending of last word in a line.
     */
    fun lineEndIpa(line: String, isHebrew: Boolean): String {
        val cleaned = line.replace(Regex("[,.\\-;:!?\\s]+$"), "").trim()
        val words = cleaned.split(Regex("\\s+"))
        val lastWord = words.lastOrNull() ?: return ""

        val ipa = if (isHebrew) heIpa(lastWord) else enIpa(lastWord)
        return if (isHebrew) ipa.takeLast(4).ifEmpty { ipa } else ipa
    }

    /**
     * Compute hue (0-360) from IPA string.
     * Similar IPA endings produce similar hues for visual rhyme matching.
     */
    fun ipaHue(ipa: String): Int {
        if (ipa.isEmpty()) return 0

        // Last 3 chars = rhyme core (consonant+vowel+consonant)
        val core = ipa.takeLast(3)
        var hc = 5381L
        for (c in core) {
            hc = ((hc * 33) xor c.code.toLong()) and 0xFFFFFFFFL
        }

        // Prefix = minor tint
        val prefix = ipa.dropLast(3.coerceAtMost(ipa.length))
        var hp = 5381L
        for (c in prefix) {
            hp = ((hp * 33) xor c.code.toLong()) and 0xFFFFFFFFL
        }

        return ((hc % 360) * 0.85 + (hp % 360) * 0.15).toInt() % 360
    }

    /**
     * HSL color as CSS string.
     */
    fun hsl(hue: Int, saturation: Int, lightness: Int): String {
        return "hsl($hue, $saturation%, $lightness%)"
    }

    /**
     * Get color for IPA with default saturation/lightness for line endings.
     */
    fun ipaEndColor(ipa: String): String {
        val hue = ipaHue(ipa)
        return hsl(hue, 80, 72)
    }

    /**
     * Get color for IPA with muted saturation for non-ending words.
     */
    fun ipaMidColor(ipa: String): String {
        val hue = ipaHue(ipa)
        return hsl(hue, 45, 55)
    }

    /**
     * Rhyme scheme item with letter, IPA, and hue.
     */
    data class RhymeSchemeItem(
        val letter: Char,
        val ipa: String,
        val hue: Int
    )

    /**
     * Detect rhyme scheme from list of IPA endings.
     * Returns letters like A, B, A, B for rhyming lines.
     */
    fun rhymeScheme(ipas: List<String>): List<RhymeSchemeItem> {
        val groups = mutableListOf<Pair<String, Char>>() // (key, letter)
        val letters = "ABCDEFGHIJKLM"

        return ipas.map { ipa ->
            if (ipa.isEmpty()) {
                RhymeSchemeItem('x', "?", 0)
            } else {
                val key = ipa.takeLast(3)
                val hue = ipaHue(ipa)

                // Check if we've seen this rhyme pattern
                val existing = groups.find { it.first == key }
                if (existing != null) {
                    RhymeSchemeItem(existing.second, key, hue)
                } else {
                    val letter = letters.getOrElse(groups.size) { '?' }
                    groups.add(key to letter)
                    RhymeSchemeItem(letter, key, hue)
                }
            }
        }
    }
}
