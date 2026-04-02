package al.clk.key

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
         * Covers all vowels from 23 Chatterbox languages.
         */
        private val VOWEL_HUE = mapOf(
            // Low vowels - warm (red/orange) [0-40]
            "a" to 0,
            "A" to 5,
            "ɑ" to 10,
            "æ" to 15,
            "aː" to 5,      // Hindi long a
            "ɛː" to 35,     // Hindi long e

            // Mid-low vowels - orange/yellow [40-80]
            "ɛ" to 40,
            "E" to 45,
            "e" to 50,
            "eː" to 55,     // Hindi long e

            // High front - green [80-140]
            "i" to 120,
            "ɪ" to 125,
            "I" to 120,
            "iː" to 115,    // Hindi long i
            "y" to 130,     // French/German ü

            // Mid back - cyan/teal [160-200]
            "o" to 180,
            "ɔ" to 185,
            "O" to 180,
            "oː" to 175,    // Hindi long o
            "ɔː" to 190,    // Hindi long ɔ
            "ø" to 160,     // French eu, German ö

            // High back - blue [220-280]
            "u" to 240,
            "ʊ" to 245,
            "U" to 240,
            "uː" to 235,    // Hindi long u
            "ɯ" to 250,     // Japanese u
            "ü" to 255,     // Chinese ü

            // Central - purple [280-340]
            "ə" to 300,
            "@" to 300,
            "ʌ" to 310,
            "ɨ" to 290,     // Russian ы, Korean ㅡ

            // Diphthongs (use ending vowel hue)
            "ja" to 10,
            "jɛ" to 45,
            "jʌ" to 315,
            "je" to 55,
            "jo" to 185,
            "ju" to 245,
            "wa" to 5,
            "wɛ" to 40,
            "wʌ" to 315,
            "we" to 50,
            "wi" to 125,
            "ɰi" to 120,

            // Pinyin finals
            "ai" to 120,
            "ei" to 50,
            "ao" to 180,
            "ou" to 240,
            "an" to 0,
            "en" to 50,
            "ang" to 5,
            "eng" to 55,
            "ong" to 185
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
            // Labials (place=0)
            "p" to ConsonantFeatures(0, 0, false),
            "b" to ConsonantFeatures(0, 0, true),
            "pʰ" to ConsonantFeatures(0, 0, false),  // Hindi aspirated
            "bʱ" to ConsonantFeatures(0, 0, true),   // Hindi breathy
            "m" to ConsonantFeatures(0, 2, true),
            "f" to ConsonantFeatures(0, 1, false),
            "v" to ConsonantFeatures(0, 1, true),
            "ɸ" to ConsonantFeatures(0, 1, false),   // Japanese fu
            "ʋ" to ConsonantFeatures(0, 3, true),    // Hindi va
            "w" to ConsonantFeatures(0, 3, true),

            // Dentals (place=1)
            "θ" to ConsonantFeatures(1, 1, false),   // Arabic tha, Greek theta
            "ð" to ConsonantFeatures(1, 1, true),    // Arabic dhal, Greek delta

            // Alveolars (place=2)
            "t" to ConsonantFeatures(2, 0, false),
            "d" to ConsonantFeatures(2, 0, true),
            "tʰ" to ConsonantFeatures(2, 0, false),  // Hindi/Korean aspirated
            "dʱ" to ConsonantFeatures(2, 0, true),   // Hindi breathy
            "n" to ConsonantFeatures(2, 2, true),
            "s" to ConsonantFeatures(2, 1, false),
            "z" to ConsonantFeatures(2, 1, true),
            "l" to ConsonantFeatures(2, 3, true),
            "r" to ConsonantFeatures(2, 4, true),
            "ɾ" to ConsonantFeatures(2, 4, true),    // Japanese/Spanish flap
            "ts" to ConsonantFeatures(2, 5, false),

            // Retroflex (place=2.5, using 2)
            "ʈ" to ConsonantFeatures(2, 0, false),   // Hindi retroflex t
            "ʈʰ" to ConsonantFeatures(2, 0, false),
            "ɖ" to ConsonantFeatures(2, 0, true),    // Hindi retroflex d
            "ɖʱ" to ConsonantFeatures(2, 0, true),
            "ɳ" to ConsonantFeatures(2, 2, true),    // Hindi retroflex n
            "ʂ" to ConsonantFeatures(2, 1, false),   // Hindi retroflex sh

            // Palatals (place=3)
            "ʃ" to ConsonantFeatures(3, 1, false),
            "sh" to ConsonantFeatures(3, 1, false),
            "ʒ" to ConsonantFeatures(3, 1, true),
            "ɕ" to ConsonantFeatures(3, 1, false),   // Japanese sh
            "j" to ConsonantFeatures(3, 3, true),
            "y" to ConsonantFeatures(3, 3, true),
            "ɲ" to ConsonantFeatures(3, 2, true),    // Japanese ny, Hindi ña
            "ç" to ConsonantFeatures(3, 1, false),   // Japanese hi
            "tʃ" to ConsonantFeatures(3, 5, false),
            "tʃʰ" to ConsonantFeatures(3, 5, false), // Hindi/Korean aspirated
            "ch" to ConsonantFeatures(3, 5, false),
            "dʒ" to ConsonantFeatures(3, 5, true),
            "dʒʱ" to ConsonantFeatures(3, 5, true),  // Hindi breathy
            "tɕ" to ConsonantFeatures(3, 5, false),  // Japanese chi

            // Velars (place=4)
            "k" to ConsonantFeatures(4, 0, false),
            "kʰ" to ConsonantFeatures(4, 0, false),  // Hindi/Korean aspirated
            "g" to ConsonantFeatures(4, 0, true),
            "ɡ" to ConsonantFeatures(4, 0, true),    // IPA g
            "ɡʱ" to ConsonantFeatures(4, 0, true),   // Hindi breathy
            "x" to ConsonantFeatures(4, 1, false),   // Hebrew chet, Greek chi
            "ɣ" to ConsonantFeatures(4, 1, true),    // Arabic ghain, Greek gamma
            "ŋ" to ConsonantFeatures(4, 2, true),

            // Uvulars (place=4.5, using 4)
            "q" to ConsonantFeatures(4, 0, false),   // Arabic qaf

            // Pharyngeals (place=5)
            "ħ" to ConsonantFeatures(5, 1, false),   // Arabic ḥa
            "ʕ" to ConsonantFeatures(5, 1, true),    // Arabic ain

            // Glottals (place=5)
            "h" to ConsonantFeatures(5, 1, false),
            "ɦ" to ConsonantFeatures(5, 1, true),    // Hindi voiced h
            "ʔ" to ConsonantFeatures(5, 0, false),   // glottal stop
            "" to ConsonantFeatures(5, 0, false),    // silent (alef/ayin)

            // Emphatics (Arabic) - treated as alveolar
            "sˤ" to ConsonantFeatures(2, 1, false),
            "dˤ" to ConsonantFeatures(2, 0, true),
            "tˤ" to ConsonantFeatures(2, 0, false),
            "ðˤ" to ConsonantFeatures(1, 1, true),

            // Clusters
            "ks" to ConsonantFeatures(4, 5, false),  // Greek xi
            "ps" to ConsonantFeatures(0, 5, false),  // Greek psi
            "ʃtʃ" to ConsonantFeatures(3, 5, false), // Russian shch
            "zh" to ConsonantFeatures(3, 5, false)   // Chinese zh
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
            val hebrewLetter = HebrewLetter.fromChar(letter) ?: return ""

            // Check for dagesh after letter (can be at pos+1 or pos+2 if vowel comes first)
            val hasDagesh = (pos + 1 < word.length && word[pos + 1].code == 0x05BC) ||
                            (pos + 2 < word.length && word[pos + 2].code == 0x05BC)

            // Special handling for shin/sin dot
            if (hebrewLetter == HebrewLetter.SHIN) {
                val hasSinDot = (pos + 1 < word.length && word[pos + 1].code == 0x05C2) ||
                                (pos + 2 < word.length && word[pos + 2].code == 0x05C2)
                return if (hasSinDot) "s" else "sh"
            }

            // For bgdkpt letters with dagesh, use dagesh IPA
            return if (hasDagesh && hebrewLetter.colorIpaDagesh != null) {
                hebrewLetter.colorIpaDagesh!!
            } else {
                hebrewLetter.colorIpa
            }
        }

        private data class VowelResult(val ipa: String, val length: Int, val original: String)

        private fun collectVowels(word: String, start: Int): VowelResult {
            val result = StringBuilder()
            val original = StringBuilder()
            var i = start

            while (i < word.length) {
                val cp = word[i].code
                val vowelIpa = when {
                    cp in 0x05B0..0x05BB -> NikudVowel.fromCodePoint(cp)?.colorIpa
                    cp == 0x05BC -> null  // dagesh - skip but include
                    cp == 0x05C1 -> null  // shin dot
                    cp == 0x05C2 -> null  // sin dot
                    else -> break         // Not a vowel/diacritic, exit loop
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
            if (vowels.isEmpty()) return ""
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
                else -> vowels.firstOrNull()?.toString() ?: ""
            }
        }

        // ═══ Arabic (ar) ═══
        fun parseArabic(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            val w = word.replace(Regex("[\\s,.!?]+"), "")

            var i = 0
            while (i < w.length) {
                val cp = w[i].code
                if (cp in 0x0600..0x06FF) {
                    val consonant = arabicConsonantIpa(w[i])
                    val (vowel, len) = collectArabicVowel(w, i + 1)
                    syllables.add(UniKeySyllable(consonant, vowel, w.substring(i, minOf(i + 1 + len, w.length))))
                    i += 1 + len
                } else {
                    i++
                }
            }
            return syllables
        }

        private fun arabicConsonantIpa(c: Char): String =
            ArabicLetter.fromChar(c)?.ipa ?: ""

        private fun collectArabicVowel(word: String, start: Int): Pair<String, Int> {
            if (start >= word.length) return "a" to 0
            val cp = word[start].code
            return when (cp) {
                0x064E -> "a" to 1  // fatha
                0x064F -> "u" to 1  // damma
                0x0650 -> "i" to 1  // kasra
                0x0651 -> "a" to 1  // shadda (gemination)
                0x0652 -> "" to 1   // sukun (no vowel)
                else -> "a" to 0    // default short a
            }
        }

        // ═══ Greek (el) ═══
        fun parseGreek(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            val w = word.lowercase()
            var i = 0
            while (i < w.length) {
                val cp = w[i].code
                if (cp in 0x03B1..0x03C9 || cp in 0x0386..0x03CE) { // Greek letters
                    val (cons, vowel, len) = parseGreekChar(w, i)
                    syllables.add(UniKeySyllable(cons, vowel, w.substring(i, i + len)))
                    i += len
                } else {
                    i++
                }
            }
            return syllables
        }

        private fun parseGreekChar(word: String, pos: Int): Triple<String, String, Int> {
            val c = word[pos]
            return when (c) {
                'α', 'ά' -> Triple("", "a", 1)
                'ε', 'έ' -> Triple("", "e", 1)
                'η', 'ή' -> Triple("", "i", 1)
                'ι', 'ί', 'ϊ', 'ΐ' -> Triple("", "i", 1)
                'ο', 'ό' -> Triple("", "o", 1)
                'υ', 'ύ', 'ϋ', 'ΰ' -> Triple("", "i", 1)
                'ω', 'ώ' -> Triple("", "o", 1)
                'β' -> Triple("v", "", 1)
                'γ' -> Triple("ɣ", "", 1)
                'δ' -> Triple("ð", "", 1)
                'ζ' -> Triple("z", "", 1)
                'θ' -> Triple("θ", "", 1)
                'κ' -> Triple("k", "", 1)
                'λ' -> Triple("l", "", 1)
                'μ' -> Triple("m", "", 1)
                'ν' -> Triple("n", "", 1)
                'ξ' -> Triple("ks", "", 1)
                'π' -> Triple("p", "", 1)
                'ρ' -> Triple("r", "", 1)
                'σ', 'ς' -> Triple("s", "", 1)
                'τ' -> Triple("t", "", 1)
                'φ' -> Triple("f", "", 1)
                'χ' -> Triple("x", "", 1)
                'ψ' -> Triple("ps", "", 1)
                else -> Triple("", "", 1)
            }
        }

        // ═══ Devanagari/Hindi (hi) ═══
        fun parseDevanagari(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            var i = 0
            while (i < word.length) {
                val cp = word[i].code
                if (cp in 0x0915..0x0939) { // consonants
                    val cons = devanagariConsonantIpa(word[i])
                    val (vowel, len) = collectDevanagariVowel(word, i + 1)
                    syllables.add(UniKeySyllable(cons, vowel, word.substring(i, minOf(i + 1 + len, word.length))))
                    i += 1 + len
                } else if (cp in 0x0905..0x0914) { // independent vowels
                    val vowel = devanagariVowelIpa(word[i])
                    syllables.add(UniKeySyllable("", vowel, word[i].toString()))
                    i++
                } else {
                    i++
                }
            }
            return syllables
        }

        private fun devanagariConsonantIpa(c: Char): String = when (c) {
            'क' -> "k"
            'ख' -> "kʰ"
            'ग' -> "ɡ"
            'घ' -> "ɡʱ"
            'ङ' -> "ŋ"
            'च' -> "tʃ"
            'छ' -> "tʃʰ"
            'ज' -> "dʒ"
            'झ' -> "dʒʱ"
            'ञ' -> "ɲ"
            'ट' -> "ʈ"
            'ठ' -> "ʈʰ"
            'ड' -> "ɖ"
            'ढ' -> "ɖʱ"
            'ण' -> "ɳ"
            'त' -> "t"
            'थ' -> "tʰ"
            'द' -> "d"
            'ध' -> "dʱ"
            'न' -> "n"
            'प' -> "p"
            'फ' -> "pʰ"
            'ब' -> "b"
            'भ' -> "bʱ"
            'म' -> "m"
            'य' -> "j"
            'र' -> "r"
            'ल' -> "l"
            'व' -> "ʋ"
            'श' -> "ʃ"
            'ष' -> "ʂ"
            'स' -> "s"
            'ह' -> "ɦ"
            else -> ""
        }

        private fun devanagariVowelIpa(c: Char): String = when (c) {
            'अ' -> "ə"
            'आ', 'ा' -> "aː"
            'इ', 'ि' -> "ɪ"
            'ई', 'ी' -> "iː"
            'उ', 'ु' -> "ʊ"
            'ऊ', 'ू' -> "uː"
            'ए', 'े' -> "eː"
            'ऐ', 'ै' -> "ɛː"
            'ओ', 'ो' -> "oː"
            'औ', 'ौ' -> "ɔː"
            else -> "ə"
        }

        private fun collectDevanagariVowel(word: String, start: Int): Pair<String, Int> {
            if (start >= word.length) return "ə" to 0
            val c = word[start]
            val cp = c.code
            // Matras (vowel signs)
            if (cp in 0x093E..0x094C) {
                return devanagariVowelIpa(c) to 1
            }
            // Virama (halant) - no vowel
            if (cp == 0x094D) return "" to 1
            return "ə" to 0 // inherent schwa
        }

        // ═══ Cyrillic/Russian (ru) ═══
        fun parseCyrillic(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            val w = word.lowercase()
            var i = 0
            while (i < w.length) {
                val cp = w[i].code
                if (cp in 0x0430..0x044F || cp in 0x0451..0x0451) { // Cyrillic lowercase
                    val (cons, vowel) = cyrillicCharIpa(w[i])
                    syllables.add(UniKeySyllable(cons, vowel, w[i].toString()))
                    i++
                } else {
                    i++
                }
            }
            return syllables
        }

        private fun cyrillicCharIpa(c: Char): Pair<String, String> {
            val key = CyrillicKey.fromChar(c) ?: return "" to ""
            val ipa = key.ipa
            // Split IPA into consonant onset and vowel nucleus
            return when {
                ipa.isEmpty() -> "" to ""  // hard/soft signs
                ipa in listOf("a", "e", "i", "o", "u", "ɨ") -> "" to ipa  // pure vowels
                ipa.startsWith("j") && ipa.length > 1 -> "j" to ipa.drop(1)  // я, ю, е, ё
                else -> ipa to ""  // consonants
            }
        }

        // ═══ Hangul/Korean (ko) ═══
        fun parseHangul(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            for (c in word) {
                val cp = c.code
                // Hangul syllable block (AC00-D7AF)
                if (cp in 0xAC00..0xD7AF) {
                    val (cons, vowel, final) = decomposeHangul(cp)
                    syllables.add(UniKeySyllable(cons, vowel, c.toString()))
                    if (final.isNotEmpty()) {
                        syllables.add(UniKeySyllable(final, "", ""))
                    }
                }
            }
            return syllables
        }

        private fun decomposeHangul(syllable: Int): Triple<String, String, String> {
            val base = syllable - 0xAC00
            val initial = base / 588
            val medial = (base % 588) / 28
            val finalC = base % 28

            val initIpa = hangulInitialIpa(initial)
            val medIpa = hangulMedialIpa(medial)
            val finIpa = if (finalC > 0) hangulFinalIpa(finalC) else ""

            return Triple(initIpa, medIpa, finIpa)
        }

        private fun hangulInitialIpa(i: Int): String = when (i) {
            0 -> "ɡ"   // ㄱ
            1 -> "k"   // ㄲ
            2 -> "n"   // ㄴ
            3 -> "d"   // ㄷ
            4 -> "t"   // ㄸ
            5 -> "r"   // ㄹ
            6 -> "m"   // ㅁ
            7 -> "b"   // ㅂ
            8 -> "p"   // ㅃ
            9 -> "s"   // ㅅ
            10 -> "s"  // ㅆ
            11 -> ""   // ㅇ (silent)
            12 -> "dʒ" // ㅈ
            13 -> "tʃ" // ㅉ
            14 -> "tʃʰ"// ㅊ
            15 -> "kʰ" // ㅋ
            16 -> "tʰ" // ㅌ
            17 -> "pʰ" // ㅍ
            18 -> "h"  // ㅎ
            else -> ""
        }

        private fun hangulMedialIpa(m: Int): String = when (m) {
            0 -> "a"   // ㅏ
            1 -> "ɛ"   // ㅐ
            2 -> "ja"  // ㅑ
            3 -> "jɛ"  // ㅒ
            4 -> "ʌ"   // ㅓ
            5 -> "e"   // ㅔ
            6 -> "jʌ"  // ㅕ
            7 -> "je"  // ㅖ
            8 -> "o"   // ㅗ
            9 -> "wa"  // ㅘ
            10 -> "wɛ" // ㅙ
            11 -> "we" // ㅚ
            12 -> "jo" // ㅛ
            13 -> "u"  // ㅜ
            14 -> "wʌ" // ㅝ
            15 -> "we" // ㅞ
            16 -> "wi" // ㅟ
            17 -> "ju" // ㅠ
            18 -> "ɨ"  // ㅡ
            19 -> "ɰi" // ㅢ
            20 -> "i"  // ㅣ
            else -> ""
        }

        private fun hangulFinalIpa(f: Int): String = when (f) {
            1 -> "k"   // ㄱ
            2 -> "k"   // ㄲ
            3 -> "k"   // ㄳ
            4 -> "n"   // ㄴ
            5 -> "n"   // ㄵ
            6 -> "n"   // ㄶ
            7 -> "t"   // ㄷ
            8 -> "l"   // ㄹ
            9 -> "k"   // ㄺ
            10 -> "m"  // ㄻ
            11 -> "p"  // ㄼ
            12 -> "l"  // ㄽ
            13 -> "l"  // ㄾ
            14 -> "l"  // ㄿ
            15 -> "p"  // ㅀ
            16 -> "m"  // ㅁ
            17 -> "p"  // ㅂ
            18 -> "p"  // ㅄ
            19 -> "t"  // ㅅ
            20 -> "t"  // ㅆ
            21 -> "ŋ"  // ㅇ
            22 -> "t"  // ㅈ
            23 -> "t"  // ㅊ
            24 -> "k"  // ㅋ
            25 -> "t"  // ㅌ
            26 -> "p"  // ㅍ
            27 -> "t"  // ㅎ
            else -> ""
        }

        // ═══ Japanese (ja) - Hiragana/Katakana ═══
        fun parseJapanese(word: String): List<UniKeySyllable> {
            val syllables = mutableListOf<UniKeySyllable>()
            var i = 0
            while (i < word.length) {
                val c = word[i]
                val cp = c.code
                when {
                    cp in 0x3040..0x309F -> { // Hiragana
                        val (cons, vowel) = hiraganaIpa(c)
                        syllables.add(UniKeySyllable(cons, vowel, c.toString()))
                    }
                    cp in 0x30A0..0x30FF -> { // Katakana
                        val (cons, vowel) = katakanaIpa(c)
                        syllables.add(UniKeySyllable(cons, vowel, c.toString()))
                    }
                }
                i++
            }
            return syllables
        }

        private fun hiraganaIpa(c: Char): Pair<String, String> {
            val key = HiraganaKey.fromChar(c) ?: return "" to ""
            val ipa = key.ipa
            // Split IPA into consonant onset and vowel nucleus
            val vowels = listOf("a", "i", "ɯ", "e", "o")
            return when {
                ipa.isEmpty() -> "" to ""
                ipa in vowels -> "" to ipa  // pure vowels
                ipa == "n" -> "n" to ""     // syllabic n
                else -> {
                    // Find vowel at end and split
                    val vowel = vowels.find { ipa.endsWith(it) } ?: ""
                    val consonant = if (vowel.isNotEmpty()) ipa.dropLast(vowel.length) else ipa
                    consonant to vowel
                }
            }
        }

        private fun katakanaIpa(c: Char): Pair<String, String> =
            hiraganaIpa(c)  // HiraganaKey.fromChar handles both hiragana and katakana

        // ═══ CJK/Chinese (zh) ═══
        fun parseCjk(word: String): List<UniKeySyllable> {
            // For CJK, we can't reliably map characters to IPA without a dictionary.
            // Return syllables based on character count with neutral phonetic values.
            // TTS systems use pinyin/pronunciation dictionaries for actual phonetics.
            val syllables = mutableListOf<UniKeySyllable>()
            for (c in word) {
                val cp = c.code
                if (cp in 0x4E00..0x9FFF) {
                    // Use a hash-based pseudo-phonetic for consistent coloring
                    val hash = c.hashCode() % 25
                    val consonant = CJK_CONSONANTS.getOrElse(hash % CJK_CONSONANTS.size) { "" }
                    val vowel = CJK_VOWELS.getOrElse(hash % CJK_VOWELS.size) { "a" }
                    syllables.add(UniKeySyllable(consonant, vowel, c.toString()))
                }
            }
            return syllables
        }

        private val CJK_CONSONANTS = listOf("", "b", "p", "m", "f", "d", "t", "n", "l", "g", "k", "h", "j", "q", "x", "zh", "ch", "sh", "r", "z", "c", "s")
        private val CJK_VOWELS = listOf("a", "o", "e", "i", "u", "ü", "ai", "ei", "ao", "ou", "an", "en", "ang", "eng", "ong")

        /**
         * Script type for input text conversion to IPA.
         * Covers all 23 Chatterbox multilingual TTS languages:
         * ar, da, de, el, en, es, fi, fr, he, hi, it, ja, ko, ms, nl, no, pl, pt, ru, sv, sw, tr, zh
         */
        enum class Script {
            HEBREW,     // he
            LATIN,      // da, de, en, es, fi, fr, it, ms, nl, no, pl, pt, sv, sw, tr
            ARABIC,     // ar
            GREEK,      // el
            DEVANAGARI, // hi
            CYRILLIC,   // ru
            HANGUL,     // ko
            HIRAGANA,   // ja (also covers katakana)
            CJK,        // zh, ja (kanji)
            UNKNOWN
        }

        /**
         * Detect script type from Unicode code points.
         * This is only used to select the correct IPA converter.
         * All downstream processing uses IPA as the universal representation.
         */
        fun detectScript(text: String): Script {
            for (c in text) {
                val cp = c.code
                // Hebrew: letters 0x05D0-0x05EA, nikud 0x05B0-0x05C7
                if (cp in 0x05D0..0x05EA || cp in 0x05B0..0x05C7) {
                    return Script.HEBREW
                }
                // Arabic: 0x0600-0x06FF, 0x0750-0x077F (supplement)
                if (cp in 0x0600..0x06FF || cp in 0x0750..0x077F) {
                    return Script.ARABIC
                }
                // Greek: 0x0370-0x03FF
                if (cp in 0x0370..0x03FF) {
                    return Script.GREEK
                }
                // Devanagari (Hindi): 0x0900-0x097F
                if (cp in 0x0900..0x097F) {
                    return Script.DEVANAGARI
                }
                // Cyrillic (Russian): 0x0400-0x04FF
                if (cp in 0x0400..0x04FF) {
                    return Script.CYRILLIC
                }
                // Hangul (Korean): 0xAC00-0xD7AF (syllables), 0x1100-0x11FF (jamo)
                if (cp in 0xAC00..0xD7AF || cp in 0x1100..0x11FF) {
                    return Script.HANGUL
                }
                // Hiragana: 0x3040-0x309F, Katakana: 0x30A0-0x30FF
                if (cp in 0x3040..0x309F || cp in 0x30A0..0x30FF) {
                    return Script.HIRAGANA
                }
                // CJK (Chinese/Japanese Kanji): 0x4E00-0x9FFF
                if (cp in 0x4E00..0x9FFF) {
                    return Script.CJK
                }
                // Latin: A-Z, a-z, also extended Latin (0x00C0-0x024F for accented chars)
                if (cp in 0x0041..0x005A || cp in 0x0061..0x007A || cp in 0x00C0..0x024F) {
                    return Script.LATIN
                }
            }
            return Script.UNKNOWN
        }

        /**
         * Convert any text to IPA syllables (auto-detects script).
         * IPA is the universal phonetic representation.
         */
        fun toIpa(word: String): List<UniKeySyllable> {
            return when (detectScript(word)) {
                Script.HEBREW -> parseHebrew(word)
                Script.LATIN -> parseEnglish(word)
                Script.ARABIC -> parseArabic(word)
                Script.GREEK -> parseGreek(word)
                Script.DEVANAGARI -> parseDevanagari(word)
                Script.CYRILLIC -> parseCyrillic(word)
                Script.HANGUL -> parseHangul(word)
                Script.HIRAGANA -> parseJapanese(word)
                Script.CJK -> parseCjk(word)
                Script.UNKNOWN -> parseEnglish(word) // fallback
            }
        }

        /**
         * Get hue for word based on IPA (auto-detects script for conversion).
         */
        fun wordHue(word: String): Int {
            val syllables = toIpa(word)
            return syllables.lastOrNull()?.hue ?: 0
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
         * Get color for word ending (IPA-based, auto-detects script).
         */
        fun wordEndColor(word: String): String {
            return hsl(wordHue(word), 80, 72)
        }

        /**
         * Get color for word ending (for rhyme visualization).
         */
        fun wordEndColor(word: String, isHebrew: Boolean): String {
            return hsl(wordHue(word, isHebrew), 80, 72)
        }

        /**
         * Get rhyme key - the IPA pattern of ending syllable(s).
         * This is the universal representation for rhyme matching.
         */
        fun rhymeKey(word: String, syllableCount: Int = 1): String {
            val syllables = toIpa(word)
            return syllables.takeLast(syllableCount)
                .joinToString("-") { "${it.consonant}${it.vowel}" }
        }

        /**
         * Get rhyme key for a word - the IPA pattern of its ending syllable(s).
         * Words with same rhyme key rhyme together regardless of language.
         */
        fun rhymeKey(word: String, isHebrew: Boolean, syllableCount: Int = 1): String {
            val syllables = if (isHebrew) parseHebrew(word) else parseEnglish(word)
            return syllables.takeLast(syllableCount)
                .joinToString("-") { "${it.consonant}${it.vowel}" }
        }

        /**
         * Check if two words rhyme (same ending IPA pattern).
         */
        fun rhymes(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Boolean {
            val key1 = rhymeKey(word1, isHebrew1)
            val key2 = rhymeKey(word2, isHebrew2)
            return key1 == key2
        }

        /**
         * Get rhyme distance - how similar two word endings are.
         * 0 = perfect rhyme, higher = less similar.
         */
        fun rhymeDistance(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Int {
            val syl1 = if (isHebrew1) parseHebrew(word1) else parseEnglish(word1)
            val syl2 = if (isHebrew2) parseHebrew(word2) else parseEnglish(word2)

            if (syl1.isEmpty() || syl2.isEmpty()) return 100

            val last1 = syl1.last()
            val last2 = syl2.last()

            // Perfect rhyme = same consonant + same vowel
            if (last1.consonant == last2.consonant && last1.vowel == last2.vowel) return 0

            // Same vowel, different consonant = near rhyme (but not for empty vowels)
            if (last1.vowel == last2.vowel && last1.vowel.isNotEmpty()) return 10 + consonantDistance(last1.consonant, last2.consonant)

            // Different vowel = assonance at best
            return 50 + vowelDistance(last1.vowel, last2.vowel)
        }

        private fun consonantDistance(c1: String, c2: String): Int {
            val f1 = CONSONANT_FEATURES[c1]
            val f2 = CONSONANT_FEATURES[c2]
            if (f1 == null || f2 == null) return 10

            // Same place = close, same manner = close
            var dist = 0
            if (f1.place != f2.place) dist += kotlin.math.abs(f1.place - f2.place) * 2
            if (f1.manner != f2.manner) dist += kotlin.math.abs(f1.manner - f2.manner)
            if (f1.voiced != f2.voiced) dist += 1
            return dist
        }

        private fun vowelDistance(v1: String, v2: String): Int {
            val h1 = VOWEL_HUE[v1] ?: 0
            val h2 = VOWEL_HUE[v2] ?: 0
            return kotlin.math.min(
                kotlin.math.abs(h1 - h2),
                360 - kotlin.math.abs(h1 - h2)
            ) / 10
        }
    }
}
