package org.pocketworkstation.unikey

/**
 * Full IPA Matrix - Complete phoneme inventory with features
 * Covers all sounds across 23 Chatterbox languages
 */
object IpaMatrix {

    // ═══════════════════════════════════════════════════════════════════════
    // CONSONANTS
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Place of articulation
     */
    enum class Place(val code: Int, val displayName: String) {
        BILABIAL(0, "Bilabial"),
        LABIODENTAL(1, "Labiodental"),
        DENTAL(2, "Dental"),
        ALVEOLAR(3, "Alveolar"),
        POSTALVEOLAR(4, "Postalveolar"),
        RETROFLEX(5, "Retroflex"),
        PALATAL(6, "Palatal"),
        VELAR(7, "Velar"),
        UVULAR(8, "Uvular"),
        PHARYNGEAL(9, "Pharyngeal"),
        GLOTTAL(10, "Glottal")
    }

    /**
     * Manner of articulation
     */
    enum class Manner(val code: Int, val displayName: String) {
        PLOSIVE(0, "Plosive"),
        NASAL(1, "Nasal"),
        TRILL(2, "Trill"),
        TAP(3, "Tap/Flap"),
        FRICATIVE(4, "Fricative"),
        LATERAL_FRICATIVE(5, "Lateral Fricative"),
        APPROXIMANT(6, "Approximant"),
        LATERAL_APPROXIMANT(7, "Lateral Approximant"),
        AFFRICATE(8, "Affricate")
    }

    /**
     * A consonant phoneme with all features
     */
    data class Consonant(
        val ipa: String,
        val name: String,
        val place: Place,
        val manner: Manner,
        val voiced: Boolean,
        val aspirated: Boolean = false,
        val breathy: Boolean = false,
        val emphatic: Boolean = false,
        val languages: List<String>  // ISO codes where this sound exists
    ) {
        val hue: Int by lazy { computeHue() }

        private fun computeHue(): Int {
            // Place determines primary region (36° each for 10 places)
            val placeHue = place.code * 36
            // Manner adds offset (0-20)
            val mannerOffset = manner.code * 2
            // Voicing shifts slightly
            val voiceOffset = if (voiced) 0 else 18
            return (placeHue + mannerOffset + voiceOffset) % 360
        }
    }

    /**
     * All consonants in IPA
     */
    val consonants = listOf(
        // ═══ PLOSIVES ═══
        // Bilabial
        Consonant("p", "voiceless bilabial plosive", Place.BILABIAL, Manner.PLOSIVE, false,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("b", "voiced bilabial plosive", Place.BILABIAL, Manner.PLOSIVE, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("pʰ", "aspirated bilabial plosive", Place.BILABIAL, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf("hi", "ko", "zh")),
        Consonant("bʱ", "breathy bilabial plosive", Place.BILABIAL, Manner.PLOSIVE, true, breathy = true,
            languages = listOf("hi")),

        // Alveolar
        Consonant("t", "voiceless alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, false,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("d", "voiced alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("tʰ", "aspirated alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf("hi", "ko", "zh")),
        Consonant("dʱ", "breathy alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, true, breathy = true,
            languages = listOf("hi")),

        // Retroflex
        Consonant("ʈ", "voiceless retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, false,
            languages = listOf("hi")),
        Consonant("ɖ", "voiced retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, true,
            languages = listOf("hi")),
        Consonant("ʈʰ", "aspirated retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf("hi")),
        Consonant("ɖʱ", "breathy retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, true, breathy = true,
            languages = listOf("hi")),

        // Palatal
        Consonant("c", "voiceless palatal plosive", Place.PALATAL, Manner.PLOSIVE, false,
            languages = listOf()),
        Consonant("ɟ", "voiced palatal plosive", Place.PALATAL, Manner.PLOSIVE, true,
            languages = listOf()),

        // Velar
        Consonant("k", "voiceless velar plosive", Place.VELAR, Manner.PLOSIVE, false,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("ɡ", "voiced velar plosive", Place.VELAR, Manner.PLOSIVE, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr")),
        Consonant("kʰ", "aspirated velar plosive", Place.VELAR, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf("hi", "ko", "zh")),
        Consonant("ɡʱ", "breathy velar plosive", Place.VELAR, Manner.PLOSIVE, true, breathy = true,
            languages = listOf("hi")),

        // Uvular
        Consonant("q", "voiceless uvular plosive", Place.UVULAR, Manner.PLOSIVE, false,
            languages = listOf("ar")),
        Consonant("ɢ", "voiced uvular plosive", Place.UVULAR, Manner.PLOSIVE, true,
            languages = listOf()),

        // Glottal
        Consonant("ʔ", "glottal stop", Place.GLOTTAL, Manner.PLOSIVE, false,
            languages = listOf("ar", "he", "de")),

        // ═══ NASALS ═══
        Consonant("m", "bilabial nasal", Place.BILABIAL, Manner.NASAL, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("n", "alveolar nasal", Place.ALVEOLAR, Manner.NASAL, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("ɳ", "retroflex nasal", Place.RETROFLEX, Manner.NASAL, true,
            languages = listOf("hi")),
        Consonant("ɲ", "palatal nasal", Place.PALATAL, Manner.NASAL, true,
            languages = listOf("es", "it", "ja", "hi")),
        Consonant("ŋ", "velar nasal", Place.VELAR, Manner.NASAL, true,
            languages = listOf("en", "de", "nl", "ja", "ko", "zh", "hi", "sw")),
        Consonant("ɴ", "uvular nasal", Place.UVULAR, Manner.NASAL, true,
            languages = listOf("ja")),

        // ═══ TRILLS ═══
        Consonant("r", "alveolar trill", Place.ALVEOLAR, Manner.TRILL, true,
            languages = listOf("es", "it", "ru", "pl", "fi", "ar")),
        Consonant("ʀ", "uvular trill", Place.UVULAR, Manner.TRILL, true,
            languages = listOf("de", "fr")),

        // ═══ TAPS/FLAPS ═══
        Consonant("ɾ", "alveolar tap", Place.ALVEOLAR, Manner.TAP, true,
            languages = listOf("es", "it", "ja", "ko", "pt")),
        Consonant("ɽ", "retroflex flap", Place.RETROFLEX, Manner.TAP, true,
            languages = listOf("hi")),

        // ═══ FRICATIVES ═══
        // Labiodental
        Consonant("f", "voiceless labiodental fricative", Place.LABIODENTAL, Manner.FRICATIVE, false,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("v", "voiced labiodental fricative", Place.LABIODENTAL, Manner.FRICATIVE, true,
            languages = listOf("da", "de", "el", "en", "fi", "fr", "he", "it", "nl", "no", "pl", "pt", "ru", "sv", "tr")),

        // Dental
        Consonant("θ", "voiceless dental fricative", Place.DENTAL, Manner.FRICATIVE, false,
            languages = listOf("ar", "el", "en")),
        Consonant("ð", "voiced dental fricative", Place.DENTAL, Manner.FRICATIVE, true,
            languages = listOf("ar", "el", "en")),

        // Alveolar
        Consonant("s", "voiceless alveolar fricative", Place.ALVEOLAR, Manner.FRICATIVE, false,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("z", "voiced alveolar fricative", Place.ALVEOLAR, Manner.FRICATIVE, true,
            languages = listOf("da", "de", "el", "en", "fi", "fr", "he", "it", "ja", "nl", "no", "pl", "pt", "ru", "sv", "tr")),

        // Postalveolar
        Consonant("ʃ", "voiceless postalveolar fricative", Place.POSTALVEOLAR, Manner.FRICATIVE, false,
            languages = listOf("ar", "de", "en", "fr", "he", "hi", "it", "pl", "pt", "ru", "tr")),
        Consonant("ʒ", "voiced postalveolar fricative", Place.POSTALVEOLAR, Manner.FRICATIVE, true,
            languages = listOf("en", "fr", "pl", "pt", "ru", "tr")),

        // Retroflex
        Consonant("ʂ", "voiceless retroflex fricative", Place.RETROFLEX, Manner.FRICATIVE, false,
            languages = listOf("hi", "pl", "zh")),
        Consonant("ʐ", "voiced retroflex fricative", Place.RETROFLEX, Manner.FRICATIVE, true,
            languages = listOf("pl", "zh")),

        // Palatal
        Consonant("ɕ", "voiceless alveolo-palatal fricative", Place.PALATAL, Manner.FRICATIVE, false,
            languages = listOf("ja", "ko", "pl", "zh")),
        Consonant("ʑ", "voiced alveolo-palatal fricative", Place.PALATAL, Manner.FRICATIVE, true,
            languages = listOf("ja", "pl")),
        Consonant("ç", "voiceless palatal fricative", Place.PALATAL, Manner.FRICATIVE, false,
            languages = listOf("de", "ja")),

        // Velar
        Consonant("x", "voiceless velar fricative", Place.VELAR, Manner.FRICATIVE, false,
            languages = listOf("ar", "de", "el", "he", "nl", "pl", "ru", "zh")),
        Consonant("ɣ", "voiced velar fricative", Place.VELAR, Manner.FRICATIVE, true,
            languages = listOf("ar", "el", "nl")),

        // Uvular
        Consonant("χ", "voiceless uvular fricative", Place.UVULAR, Manner.FRICATIVE, false,
            languages = listOf("de", "nl", "he")),
        Consonant("ʁ", "voiced uvular fricative", Place.UVULAR, Manner.FRICATIVE, true,
            languages = listOf("de", "fr", "he", "nl", "pt")),

        // Pharyngeal
        Consonant("ħ", "voiceless pharyngeal fricative", Place.PHARYNGEAL, Manner.FRICATIVE, false,
            languages = listOf("ar")),
        Consonant("ʕ", "voiced pharyngeal fricative", Place.PHARYNGEAL, Manner.FRICATIVE, true,
            languages = listOf("ar", "he")),

        // Glottal
        Consonant("h", "voiceless glottal fricative", Place.GLOTTAL, Manner.FRICATIVE, false,
            languages = listOf("ar", "da", "de", "en", "fi", "he", "hi", "ja", "ko", "nl", "no", "sv", "sw", "tr")),
        Consonant("ɦ", "voiced glottal fricative", Place.GLOTTAL, Manner.FRICATIVE, true,
            languages = listOf("hi", "nl")),

        // ═══ APPROXIMANTS ═══
        Consonant("ʋ", "labiodental approximant", Place.LABIODENTAL, Manner.APPROXIMANT, true,
            languages = listOf("hi", "nl")),
        Consonant("ɹ", "alveolar approximant", Place.ALVEOLAR, Manner.APPROXIMANT, true,
            languages = listOf("en")),
        Consonant("ɻ", "retroflex approximant", Place.RETROFLEX, Manner.APPROXIMANT, true,
            languages = listOf("zh")),
        Consonant("j", "palatal approximant", Place.PALATAL, Manner.APPROXIMANT, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("w", "labio-velar approximant", Place.VELAR, Manner.APPROXIMANT, true,
            languages = listOf("ar", "en", "es", "fr", "it", "ja", "ko", "ms", "nl", "pt", "sw", "zh")),
        Consonant("ɰ", "velar approximant", Place.VELAR, Manner.APPROXIMANT, true,
            languages = listOf("ko")),

        // ═══ LATERAL APPROXIMANTS ═══
        Consonant("l", "alveolar lateral approximant", Place.ALVEOLAR, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Consonant("ɭ", "retroflex lateral approximant", Place.RETROFLEX, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf("hi")),
        Consonant("ʎ", "palatal lateral approximant", Place.PALATAL, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf("es", "it", "pt")),
        Consonant("ʟ", "velar lateral approximant", Place.VELAR, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf()),

        // ═══ AFFRICATES ═══
        Consonant("ts", "voiceless alveolar affricate", Place.ALVEOLAR, Manner.AFFRICATE, false,
            languages = listOf("de", "he", "it", "ja", "ko", "pl", "ru", "zh")),
        Consonant("dz", "voiced alveolar affricate", Place.ALVEOLAR, Manner.AFFRICATE, true,
            languages = listOf("it", "ja", "pl")),
        Consonant("tʃ", "voiceless postalveolar affricate", Place.POSTALVEOLAR, Manner.AFFRICATE, false,
            languages = listOf("en", "es", "it", "ru", "tr")),
        Consonant("dʒ", "voiced postalveolar affricate", Place.POSTALVEOLAR, Manner.AFFRICATE, true,
            languages = listOf("en", "hi", "it", "ja", "ko", "tr")),
        Consonant("tɕ", "voiceless alveolo-palatal affricate", Place.PALATAL, Manner.AFFRICATE, false,
            languages = listOf("ja", "ko", "pl", "zh")),
        Consonant("dʑ", "voiced alveolo-palatal affricate", Place.PALATAL, Manner.AFFRICATE, true,
            languages = listOf("ja", "ko", "pl")),
        Consonant("tɕʰ", "aspirated alveolo-palatal affricate", Place.PALATAL, Manner.AFFRICATE, false, aspirated = true,
            languages = listOf("ko", "zh")),

        // Emphatic consonants (Arabic)
        Consonant("sˤ", "emphatic voiceless alveolar fricative", Place.ALVEOLAR, Manner.FRICATIVE, false, emphatic = true,
            languages = listOf("ar")),
        Consonant("dˤ", "emphatic voiced alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, true, emphatic = true,
            languages = listOf("ar")),
        Consonant("tˤ", "emphatic voiceless alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, false, emphatic = true,
            languages = listOf("ar")),
        Consonant("ðˤ", "emphatic voiced dental fricative", Place.DENTAL, Manner.FRICATIVE, true, emphatic = true,
            languages = listOf("ar")),

        // Bilabial fricative
        Consonant("ɸ", "voiceless bilabial fricative", Place.BILABIAL, Manner.FRICATIVE, false,
            languages = listOf("ja")),
        Consonant("β", "voiced bilabial fricative", Place.BILABIAL, Manner.FRICATIVE, true,
            languages = listOf("es"))
    )

    // ═══════════════════════════════════════════════════════════════════════
    // VOWELS
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Vowel height
     */
    enum class Height(val code: Int, val displayName: String) {
        CLOSE(0, "Close"),
        NEAR_CLOSE(1, "Near-close"),
        CLOSE_MID(2, "Close-mid"),
        MID(3, "Mid"),
        OPEN_MID(4, "Open-mid"),
        NEAR_OPEN(5, "Near-open"),
        OPEN(6, "Open")
    }

    /**
     * Vowel backness
     */
    enum class Backness(val code: Int, val displayName: String) {
        FRONT(0, "Front"),
        CENTRAL(1, "Central"),
        BACK(2, "Back")
    }

    /**
     * A vowel phoneme with all features
     */
    data class Vowel(
        val ipa: String,
        val name: String,
        val height: Height,
        val backness: Backness,
        val rounded: Boolean,
        val long: Boolean = false,
        val nasal: Boolean = false,
        val languages: List<String>
    ) {
        val hue: Int by lazy { computeHue() }

        private fun computeHue(): Int {
            // Backness determines primary region (120° each)
            val backnessHue = backness.code * 120
            // Height adds offset (0-60)
            val heightOffset = height.code * 8
            // Rounding shifts
            val roundOffset = if (rounded) 10 else 0
            return (backnessHue + heightOffset + roundOffset) % 360
        }
    }

    /**
     * All vowels in IPA
     */
    val vowels = listOf(
        // ═══ CLOSE VOWELS ═══
        Vowel("i", "close front unrounded", Height.CLOSE, Backness.FRONT, false,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Vowel("iː", "long close front unrounded", Height.CLOSE, Backness.FRONT, false, long = true,
            languages = listOf("de", "fi", "hi", "ja")),
        Vowel("y", "close front rounded", Height.CLOSE, Backness.FRONT, true,
            languages = listOf("da", "de", "fi", "fr", "nl", "no", "sv", "tr", "zh")),
        Vowel("yː", "long close front rounded", Height.CLOSE, Backness.FRONT, true, long = true,
            languages = listOf("de", "fi")),
        Vowel("ɨ", "close central unrounded", Height.CLOSE, Backness.CENTRAL, false,
            languages = listOf("ko", "pl", "ru")),
        Vowel("ʉ", "close central rounded", Height.CLOSE, Backness.CENTRAL, true,
            languages = listOf("no", "sv")),
        Vowel("ɯ", "close back unrounded", Height.CLOSE, Backness.BACK, false,
            languages = listOf("ja", "ko", "tr")),
        Vowel("u", "close back rounded", Height.CLOSE, Backness.BACK, true,
            languages = listOf("ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Vowel("uː", "long close back rounded", Height.CLOSE, Backness.BACK, true, long = true,
            languages = listOf("de", "fi", "hi", "ja")),

        // ═══ NEAR-CLOSE VOWELS ═══
        Vowel("ɪ", "near-close front unrounded", Height.NEAR_CLOSE, Backness.FRONT, false,
            languages = listOf("de", "en", "hi", "nl")),
        Vowel("ʏ", "near-close front rounded", Height.NEAR_CLOSE, Backness.FRONT, true,
            languages = listOf("da", "de", "nl", "no", "sv")),
        Vowel("ʊ", "near-close back rounded", Height.NEAR_CLOSE, Backness.BACK, true,
            languages = listOf("de", "en", "hi", "nl")),

        // ═══ CLOSE-MID VOWELS ═══
        Vowel("e", "close-mid front unrounded", Height.CLOSE_MID, Backness.FRONT, false,
            languages = listOf("da", "de", "el", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Vowel("eː", "long close-mid front unrounded", Height.CLOSE_MID, Backness.FRONT, false, long = true,
            languages = listOf("de", "fi", "hi", "ja")),
        Vowel("ø", "close-mid front rounded", Height.CLOSE_MID, Backness.FRONT, true,
            languages = listOf("da", "de", "fi", "fr", "nl", "no", "sv", "tr")),
        Vowel("øː", "long close-mid front rounded", Height.CLOSE_MID, Backness.FRONT, true, long = true,
            languages = listOf("de", "fi")),
        Vowel("ɘ", "close-mid central unrounded", Height.CLOSE_MID, Backness.CENTRAL, false,
            languages = listOf()),
        Vowel("ɵ", "close-mid central rounded", Height.CLOSE_MID, Backness.CENTRAL, true,
            languages = listOf("no", "sv")),
        Vowel("ɤ", "close-mid back unrounded", Height.CLOSE_MID, Backness.BACK, false,
            languages = listOf("ko", "zh")),
        Vowel("o", "close-mid back rounded", Height.CLOSE_MID, Backness.BACK, true,
            languages = listOf("da", "de", "el", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Vowel("oː", "long close-mid back rounded", Height.CLOSE_MID, Backness.BACK, true, long = true,
            languages = listOf("de", "fi", "hi", "ja")),

        // ═══ MID VOWELS ═══
        Vowel("ə", "schwa", Height.MID, Backness.CENTRAL, false,
            languages = listOf("da", "de", "en", "fr", "hi", "nl", "pt", "ru", "zh")),

        // ═══ OPEN-MID VOWELS ═══
        Vowel("ɛ", "open-mid front unrounded", Height.OPEN_MID, Backness.FRONT, false,
            languages = listOf("da", "de", "el", "en", "fi", "fr", "he", "hi", "it", "ko", "nl", "no", "pl", "pt", "sv", "tr")),
        Vowel("ɛː", "long open-mid front unrounded", Height.OPEN_MID, Backness.FRONT, false, long = true,
            languages = listOf("de", "fi", "hi")),
        Vowel("œ", "open-mid front rounded", Height.OPEN_MID, Backness.FRONT, true,
            languages = listOf("da", "de", "fr", "nl")),
        Vowel("œː", "long open-mid front rounded", Height.OPEN_MID, Backness.FRONT, true, long = true,
            languages = listOf("de")),
        Vowel("ɜ", "open-mid central unrounded", Height.OPEN_MID, Backness.CENTRAL, false,
            languages = listOf("en")),
        Vowel("ʌ", "open-mid back unrounded", Height.OPEN_MID, Backness.BACK, false,
            languages = listOf("en", "ko")),
        Vowel("ɔ", "open-mid back rounded", Height.OPEN_MID, Backness.BACK, true,
            languages = listOf("da", "de", "el", "en", "fi", "fr", "it", "ko", "nl", "no", "pl", "pt", "sv", "tr")),
        Vowel("ɔː", "long open-mid back rounded", Height.OPEN_MID, Backness.BACK, true, long = true,
            languages = listOf("de", "en", "fi", "hi")),

        // ═══ NEAR-OPEN VOWELS ═══
        Vowel("æ", "near-open front unrounded", Height.NEAR_OPEN, Backness.FRONT, false,
            languages = listOf("da", "en", "fi", "no", "sv")),
        Vowel("ɐ", "near-open central", Height.NEAR_OPEN, Backness.CENTRAL, false,
            languages = listOf("de", "pt")),

        // ═══ OPEN VOWELS ═══
        Vowel("a", "open front unrounded", Height.OPEN, Backness.FRONT, false,
            languages = listOf("ar", "da", "de", "el", "es", "fi", "fr", "he", "hi", "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv", "sw", "tr", "zh")),
        Vowel("aː", "long open front unrounded", Height.OPEN, Backness.FRONT, false, long = true,
            languages = listOf("de", "fi", "hi", "ja")),
        Vowel("ɑ", "open back unrounded", Height.OPEN, Backness.BACK, false,
            languages = listOf("en", "nl", "no")),
        Vowel("ɑː", "long open back unrounded", Height.OPEN, Backness.BACK, false, long = true,
            languages = listOf("en", "nl")),
        Vowel("ɒ", "open back rounded", Height.OPEN, Backness.BACK, true,
            languages = listOf("en")),

        // ═══ NASAL VOWELS ═══
        Vowel("ɛ̃", "nasal open-mid front unrounded", Height.OPEN_MID, Backness.FRONT, false, nasal = true,
            languages = listOf("fr", "pt")),
        Vowel("œ̃", "nasal open-mid front rounded", Height.OPEN_MID, Backness.FRONT, true, nasal = true,
            languages = listOf("fr")),
        Vowel("ɔ̃", "nasal open-mid back rounded", Height.OPEN_MID, Backness.BACK, true, nasal = true,
            languages = listOf("fr", "pt")),
        Vowel("ã", "nasal open front unrounded", Height.OPEN, Backness.FRONT, false, nasal = true,
            languages = listOf("pt")),
        Vowel("ĩ", "nasal close front unrounded", Height.CLOSE, Backness.FRONT, false, nasal = true,
            languages = listOf("pt")),
        Vowel("ũ", "nasal close back rounded", Height.CLOSE, Backness.BACK, true, nasal = true,
            languages = listOf("pt"))
    )

    // ═══════════════════════════════════════════════════════════════════════
    // LOOKUP FUNCTIONS
    // ═══════════════════════════════════════════════════════════════════════

    private val consonantByIpa = consonants.associateBy { it.ipa }
    private val vowelByIpa = vowels.associateBy { it.ipa }

    fun getConsonant(ipa: String): Consonant? = consonantByIpa[ipa]
    fun getVowel(ipa: String): Vowel? = vowelByIpa[ipa]

    fun getConsonantsForLanguage(langCode: String): List<Consonant> =
        consonants.filter { langCode in it.languages }

    fun getVowelsForLanguage(langCode: String): List<Vowel> =
        vowels.filter { langCode in it.languages }

    fun getPhonemeHue(ipa: String): Int {
        consonantByIpa[ipa]?.let { return it.hue }
        vowelByIpa[ipa]?.let { return it.hue }
        return ipa.hashCode() % 360
    }

    /**
     * Get IPA inventory for a language
     */
    data class LanguageInventory(
        val consonants: List<Consonant>,
        val vowels: List<Vowel>
    )

    fun getInventory(langCode: String): LanguageInventory {
        return LanguageInventory(
            consonants = getConsonantsForLanguage(langCode),
            vowels = getVowelsForLanguage(langCode)
        )
    }

    /**
     * All supported language codes
     */
    val supportedLanguages = listOf(
        "ar", "da", "de", "el", "en", "es", "fi", "fr", "he", "hi",
        "it", "ja", "ko", "ms", "nl", "no", "pl", "pt", "ru", "sv",
        "sw", "tr", "zh"
    )
}
