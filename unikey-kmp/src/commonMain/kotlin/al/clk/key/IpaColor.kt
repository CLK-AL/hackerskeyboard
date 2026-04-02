package al.clk.key

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
     * Consonant IPA for color hashing - generated from HebrewLetter enum.
     */
    private val CONSONANT_IPA: Map<Char, String> = HebrewLetter.entries
        .associateBy({ it.letter }, { it.colorIpa })

    /**
     * Dagesh changes for BGDKPT letters - generated from HebrewLetter enum.
     */
    private val DAGESH_IPA: Map<Char, String> = HebrewLetter.entries
        .filter { it.colorIpaDagesh != null }
        .associateBy({ it.letter }, { it.colorIpaDagesh!! })

    /**
     * Nikud (vowel marks) to simplified IPA - generated from NikudVowel enum.
     */
    private val NIKUD_IPA: Map<Int, String> = NikudVowel.entries
        .associateBy({ it.unicode }, { it.colorIpa })

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
