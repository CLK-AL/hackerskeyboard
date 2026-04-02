package al.clk.key

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
        val languages: List<Lang>  // Languages where this sound exists
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
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("b", "voiced bilabial plosive", Place.BILABIAL, Manner.PLOSIVE, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("pʰ", "aspirated bilabial plosive", Place.BILABIAL, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf(Lang.HI, Lang.KO, Lang.ZH)),
        Consonant("bʱ", "breathy bilabial plosive", Place.BILABIAL, Manner.PLOSIVE, true, breathy = true,
            languages = listOf(Lang.HI)),

        // Alveolar
        Consonant("t", "voiceless alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, false,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("d", "voiced alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("tʰ", "aspirated alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf(Lang.HI, Lang.KO, Lang.ZH)),
        Consonant("dʱ", "breathy alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, true, breathy = true,
            languages = listOf(Lang.HI)),

        // Retroflex
        Consonant("ʈ", "voiceless retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, false,
            languages = listOf(Lang.HI)),
        Consonant("ɖ", "voiced retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, true,
            languages = listOf(Lang.HI)),
        Consonant("ʈʰ", "aspirated retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf(Lang.HI)),
        Consonant("ɖʱ", "breathy retroflex plosive", Place.RETROFLEX, Manner.PLOSIVE, true, breathy = true,
            languages = listOf(Lang.HI)),

        // Palatal
        Consonant("c", "voiceless palatal plosive", Place.PALATAL, Manner.PLOSIVE, false,
            languages = listOf()),
        Consonant("ɟ", "voiced palatal plosive", Place.PALATAL, Manner.PLOSIVE, true,
            languages = listOf()),

        // Velar
        Consonant("k", "voiceless velar plosive", Place.VELAR, Manner.PLOSIVE, false,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("ɡ", "voiced velar plosive", Place.VELAR, Manner.PLOSIVE, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR)),
        Consonant("kʰ", "aspirated velar plosive", Place.VELAR, Manner.PLOSIVE, false, aspirated = true,
            languages = listOf(Lang.HI, Lang.KO, Lang.ZH)),
        Consonant("ɡʱ", "breathy velar plosive", Place.VELAR, Manner.PLOSIVE, true, breathy = true,
            languages = listOf(Lang.HI)),

        // Uvular
        Consonant("q", "voiceless uvular plosive", Place.UVULAR, Manner.PLOSIVE, false,
            languages = listOf(Lang.AR)),
        Consonant("ɢ", "voiced uvular plosive", Place.UVULAR, Manner.PLOSIVE, true,
            languages = listOf()),

        // Glottal
        Consonant("ʔ", "glottal stop", Place.GLOTTAL, Manner.PLOSIVE, false,
            languages = listOf(Lang.AR, Lang.HE, Lang.DE)),

        // ═══ NASALS ═══
        Consonant("m", "bilabial nasal", Place.BILABIAL, Manner.NASAL, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("n", "alveolar nasal", Place.ALVEOLAR, Manner.NASAL, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("ɳ", "retroflex nasal", Place.RETROFLEX, Manner.NASAL, true,
            languages = listOf(Lang.HI)),
        Consonant("ɲ", "palatal nasal", Place.PALATAL, Manner.NASAL, true,
            languages = listOf(Lang.ES, Lang.IT, Lang.JA, Lang.HI)),
        Consonant("ŋ", "velar nasal", Place.VELAR, Manner.NASAL, true,
            languages = listOf(Lang.EN, Lang.DE, Lang.NL, Lang.JA, Lang.KO, Lang.ZH, Lang.HI, Lang.SW)),
        Consonant("ɴ", "uvular nasal", Place.UVULAR, Manner.NASAL, true,
            languages = listOf(Lang.JA)),

        // ═══ TRILLS ═══
        Consonant("r", "alveolar trill", Place.ALVEOLAR, Manner.TRILL, true,
            languages = listOf(Lang.ES, Lang.IT, Lang.RU, Lang.PL, Lang.FI, Lang.AR)),
        Consonant("ʀ", "uvular trill", Place.UVULAR, Manner.TRILL, true,
            languages = listOf(Lang.DE, Lang.FR)),

        // ═══ TAPS/FLAPS ═══
        Consonant("ɾ", "alveolar tap", Place.ALVEOLAR, Manner.TAP, true,
            languages = listOf(Lang.ES, Lang.IT, Lang.JA, Lang.KO, Lang.PT)),
        Consonant("ɽ", "retroflex flap", Place.RETROFLEX, Manner.TAP, true,
            languages = listOf(Lang.HI)),

        // ═══ FRICATIVES ═══
        // Labiodental
        Consonant("f", "voiceless labiodental fricative", Place.LABIODENTAL, Manner.FRICATIVE, false,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("v", "voiced labiodental fricative", Place.LABIODENTAL, Manner.FRICATIVE, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.FI, Lang.FR, Lang.HE, Lang.IT, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.TR)),

        // Dental
        Consonant("θ", "voiceless dental fricative", Place.DENTAL, Manner.FRICATIVE, false,
            languages = listOf(Lang.AR, Lang.EL, Lang.EN)),
        Consonant("ð", "voiced dental fricative", Place.DENTAL, Manner.FRICATIVE, true,
            languages = listOf(Lang.AR, Lang.EL, Lang.EN)),

        // Alveolar
        Consonant("s", "voiceless alveolar fricative", Place.ALVEOLAR, Manner.FRICATIVE, false,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("z", "voiced alveolar fricative", Place.ALVEOLAR, Manner.FRICATIVE, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.FI, Lang.FR, Lang.HE, Lang.IT, Lang.JA, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.TR)),

        // Postalveolar
        Consonant("ʃ", "voiceless postalveolar fricative", Place.POSTALVEOLAR, Manner.FRICATIVE, false,
            languages = listOf(Lang.AR, Lang.DE, Lang.EN, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.PL, Lang.PT, Lang.RU, Lang.TR)),
        Consonant("ʒ", "voiced postalveolar fricative", Place.POSTALVEOLAR, Manner.FRICATIVE, true,
            languages = listOf(Lang.EN, Lang.FR, Lang.PL, Lang.PT, Lang.RU, Lang.TR)),

        // Retroflex
        Consonant("ʂ", "voiceless retroflex fricative", Place.RETROFLEX, Manner.FRICATIVE, false,
            languages = listOf(Lang.HI, Lang.PL, Lang.ZH)),
        Consonant("ʐ", "voiced retroflex fricative", Place.RETROFLEX, Manner.FRICATIVE, true,
            languages = listOf(Lang.PL, Lang.ZH)),

        // Palatal
        Consonant("ɕ", "voiceless alveolo-palatal fricative", Place.PALATAL, Manner.FRICATIVE, false,
            languages = listOf(Lang.JA, Lang.KO, Lang.PL, Lang.ZH)),
        Consonant("ʑ", "voiced alveolo-palatal fricative", Place.PALATAL, Manner.FRICATIVE, true,
            languages = listOf(Lang.JA, Lang.PL)),
        Consonant("ç", "voiceless palatal fricative", Place.PALATAL, Manner.FRICATIVE, false,
            languages = listOf(Lang.DE, Lang.JA)),

        // Velar
        Consonant("x", "voiceless velar fricative", Place.VELAR, Manner.FRICATIVE, false,
            languages = listOf(Lang.AR, Lang.DE, Lang.EL, Lang.HE, Lang.NL, Lang.PL, Lang.RU, Lang.ZH)),
        Consonant("ɣ", "voiced velar fricative", Place.VELAR, Manner.FRICATIVE, true,
            languages = listOf(Lang.AR, Lang.EL, Lang.NL)),

        // Uvular
        Consonant("χ", "voiceless uvular fricative", Place.UVULAR, Manner.FRICATIVE, false,
            languages = listOf(Lang.DE, Lang.NL, Lang.HE)),
        Consonant("ʁ", "voiced uvular fricative", Place.UVULAR, Manner.FRICATIVE, true,
            languages = listOf(Lang.DE, Lang.FR, Lang.HE, Lang.NL, Lang.PT)),

        // Pharyngeal
        Consonant("ħ", "voiceless pharyngeal fricative", Place.PHARYNGEAL, Manner.FRICATIVE, false,
            languages = listOf(Lang.AR)),
        Consonant("ʕ", "voiced pharyngeal fricative", Place.PHARYNGEAL, Manner.FRICATIVE, true,
            languages = listOf(Lang.AR, Lang.HE)),

        // Glottal
        Consonant("h", "voiceless glottal fricative", Place.GLOTTAL, Manner.FRICATIVE, false,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EN, Lang.FI, Lang.HE, Lang.HI, Lang.JA, Lang.KO, Lang.NL, Lang.NO, Lang.SV, Lang.SW, Lang.TR)),
        Consonant("ɦ", "voiced glottal fricative", Place.GLOTTAL, Manner.FRICATIVE, true,
            languages = listOf(Lang.HI, Lang.NL)),

        // ═══ APPROXIMANTS ═══
        Consonant("ʋ", "labiodental approximant", Place.LABIODENTAL, Manner.APPROXIMANT, true,
            languages = listOf(Lang.HI, Lang.NL)),
        Consonant("ɹ", "alveolar approximant", Place.ALVEOLAR, Manner.APPROXIMANT, true,
            languages = listOf(Lang.EN)),
        Consonant("ɻ", "retroflex approximant", Place.RETROFLEX, Manner.APPROXIMANT, true,
            languages = listOf(Lang.ZH)),
        Consonant("j", "palatal approximant", Place.PALATAL, Manner.APPROXIMANT, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("w", "labio-velar approximant", Place.VELAR, Manner.APPROXIMANT, true,
            languages = listOf(Lang.AR, Lang.EN, Lang.ES, Lang.FR, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.PT, Lang.SW, Lang.ZH)),
        Consonant("ɰ", "velar approximant", Place.VELAR, Manner.APPROXIMANT, true,
            languages = listOf(Lang.KO)),

        // ═══ LATERAL APPROXIMANTS ═══
        Consonant("l", "alveolar lateral approximant", Place.ALVEOLAR, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Consonant("ɭ", "retroflex lateral approximant", Place.RETROFLEX, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf(Lang.HI)),
        Consonant("ʎ", "palatal lateral approximant", Place.PALATAL, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf(Lang.ES, Lang.IT, Lang.PT)),
        Consonant("ʟ", "velar lateral approximant", Place.VELAR, Manner.LATERAL_APPROXIMANT, true,
            languages = listOf()),

        // ═══ AFFRICATES ═══
        Consonant("ts", "voiceless alveolar affricate", Place.ALVEOLAR, Manner.AFFRICATE, false,
            languages = listOf(Lang.DE, Lang.HE, Lang.IT, Lang.JA, Lang.KO, Lang.PL, Lang.RU, Lang.ZH)),
        Consonant("dz", "voiced alveolar affricate", Place.ALVEOLAR, Manner.AFFRICATE, true,
            languages = listOf(Lang.IT, Lang.JA, Lang.PL)),
        Consonant("tʃ", "voiceless postalveolar affricate", Place.POSTALVEOLAR, Manner.AFFRICATE, false,
            languages = listOf(Lang.EN, Lang.ES, Lang.IT, Lang.RU, Lang.TR)),
        Consonant("dʒ", "voiced postalveolar affricate", Place.POSTALVEOLAR, Manner.AFFRICATE, true,
            languages = listOf(Lang.EN, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.TR)),
        Consonant("tɕ", "voiceless alveolo-palatal affricate", Place.PALATAL, Manner.AFFRICATE, false,
            languages = listOf(Lang.JA, Lang.KO, Lang.PL, Lang.ZH)),
        Consonant("dʑ", "voiced alveolo-palatal affricate", Place.PALATAL, Manner.AFFRICATE, true,
            languages = listOf(Lang.JA, Lang.KO, Lang.PL)),
        Consonant("tɕʰ", "aspirated alveolo-palatal affricate", Place.PALATAL, Manner.AFFRICATE, false, aspirated = true,
            languages = listOf(Lang.KO, Lang.ZH)),

        // Emphatic consonants (Arabic)
        Consonant("sˤ", "emphatic voiceless alveolar fricative", Place.ALVEOLAR, Manner.FRICATIVE, false, emphatic = true,
            languages = listOf(Lang.AR)),
        Consonant("dˤ", "emphatic voiced alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, true, emphatic = true,
            languages = listOf(Lang.AR)),
        Consonant("tˤ", "emphatic voiceless alveolar plosive", Place.ALVEOLAR, Manner.PLOSIVE, false, emphatic = true,
            languages = listOf(Lang.AR)),
        Consonant("ðˤ", "emphatic voiced dental fricative", Place.DENTAL, Manner.FRICATIVE, true, emphatic = true,
            languages = listOf(Lang.AR)),

        // Bilabial fricative
        Consonant("ɸ", "voiceless bilabial fricative", Place.BILABIAL, Manner.FRICATIVE, false,
            languages = listOf(Lang.JA)),
        Consonant("β", "voiced bilabial fricative", Place.BILABIAL, Manner.FRICATIVE, true,
            languages = listOf(Lang.ES))
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
        val languages: List<Lang>
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
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Vowel("iː", "long close front unrounded", Height.CLOSE, Backness.FRONT, false, long = true,
            languages = listOf(Lang.DE, Lang.FI, Lang.HI, Lang.JA)),
        Vowel("y", "close front rounded", Height.CLOSE, Backness.FRONT, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.FI, Lang.FR, Lang.NL, Lang.NO, Lang.SV, Lang.TR, Lang.ZH)),
        Vowel("yː", "long close front rounded", Height.CLOSE, Backness.FRONT, true, long = true,
            languages = listOf(Lang.DE, Lang.FI)),
        Vowel("ɨ", "close central unrounded", Height.CLOSE, Backness.CENTRAL, false,
            languages = listOf(Lang.KO, Lang.PL, Lang.RU)),
        Vowel("ʉ", "close central rounded", Height.CLOSE, Backness.CENTRAL, true,
            languages = listOf(Lang.NO, Lang.SV)),
        Vowel("ɯ", "close back unrounded", Height.CLOSE, Backness.BACK, false,
            languages = listOf(Lang.JA, Lang.KO, Lang.TR)),
        Vowel("u", "close back rounded", Height.CLOSE, Backness.BACK, true,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Vowel("uː", "long close back rounded", Height.CLOSE, Backness.BACK, true, long = true,
            languages = listOf(Lang.DE, Lang.FI, Lang.HI, Lang.JA)),

        // ═══ NEAR-CLOSE VOWELS ═══
        Vowel("ɪ", "near-close front unrounded", Height.NEAR_CLOSE, Backness.FRONT, false,
            languages = listOf(Lang.DE, Lang.EN, Lang.HI, Lang.NL)),
        Vowel("ʏ", "near-close front rounded", Height.NEAR_CLOSE, Backness.FRONT, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.NL, Lang.NO, Lang.SV)),
        Vowel("ʊ", "near-close back rounded", Height.NEAR_CLOSE, Backness.BACK, true,
            languages = listOf(Lang.DE, Lang.EN, Lang.HI, Lang.NL)),

        // ═══ CLOSE-MID VOWELS ═══
        Vowel("e", "close-mid front unrounded", Height.CLOSE_MID, Backness.FRONT, false,
            languages = listOf(Lang.DA, Lang.DE, Lang.EL, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Vowel("eː", "long close-mid front unrounded", Height.CLOSE_MID, Backness.FRONT, false, long = true,
            languages = listOf(Lang.DE, Lang.FI, Lang.HI, Lang.JA)),
        Vowel("ø", "close-mid front rounded", Height.CLOSE_MID, Backness.FRONT, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.FI, Lang.FR, Lang.NL, Lang.NO, Lang.SV, Lang.TR)),
        Vowel("øː", "long close-mid front rounded", Height.CLOSE_MID, Backness.FRONT, true, long = true,
            languages = listOf(Lang.DE, Lang.FI)),
        Vowel("ɘ", "close-mid central unrounded", Height.CLOSE_MID, Backness.CENTRAL, false,
            languages = listOf()),
        Vowel("ɵ", "close-mid central rounded", Height.CLOSE_MID, Backness.CENTRAL, true,
            languages = listOf(Lang.NO, Lang.SV)),
        Vowel("ɤ", "close-mid back unrounded", Height.CLOSE_MID, Backness.BACK, false,
            languages = listOf(Lang.KO, Lang.ZH)),
        Vowel("o", "close-mid back rounded", Height.CLOSE_MID, Backness.BACK, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.EL, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Vowel("oː", "long close-mid back rounded", Height.CLOSE_MID, Backness.BACK, true, long = true,
            languages = listOf(Lang.DE, Lang.FI, Lang.HI, Lang.JA)),

        // ═══ MID VOWELS ═══
        Vowel("ə", "schwa", Height.MID, Backness.CENTRAL, false,
            languages = listOf(Lang.DA, Lang.DE, Lang.EN, Lang.FR, Lang.HI, Lang.NL, Lang.PT, Lang.RU, Lang.ZH)),

        // ═══ OPEN-MID VOWELS ═══
        Vowel("ɛ", "open-mid front unrounded", Height.OPEN_MID, Backness.FRONT, false,
            languages = listOf(Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.KO, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.SV, Lang.TR)),
        Vowel("ɛː", "long open-mid front unrounded", Height.OPEN_MID, Backness.FRONT, false, long = true,
            languages = listOf(Lang.DE, Lang.FI, Lang.HI)),
        Vowel("œ", "open-mid front rounded", Height.OPEN_MID, Backness.FRONT, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.FR, Lang.NL)),
        Vowel("œː", "long open-mid front rounded", Height.OPEN_MID, Backness.FRONT, true, long = true,
            languages = listOf(Lang.DE)),
        Vowel("ɜ", "open-mid central unrounded", Height.OPEN_MID, Backness.CENTRAL, false,
            languages = listOf(Lang.EN)),
        Vowel("ʌ", "open-mid back unrounded", Height.OPEN_MID, Backness.BACK, false,
            languages = listOf(Lang.EN, Lang.KO)),
        Vowel("ɔ", "open-mid back rounded", Height.OPEN_MID, Backness.BACK, true,
            languages = listOf(Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.FI, Lang.FR, Lang.IT, Lang.KO, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.SV, Lang.TR)),
        Vowel("ɔː", "long open-mid back rounded", Height.OPEN_MID, Backness.BACK, true, long = true,
            languages = listOf(Lang.DE, Lang.EN, Lang.FI, Lang.HI)),

        // ═══ NEAR-OPEN VOWELS ═══
        Vowel("æ", "near-open front unrounded", Height.NEAR_OPEN, Backness.FRONT, false,
            languages = listOf(Lang.DA, Lang.EN, Lang.FI, Lang.NO, Lang.SV)),
        Vowel("ɐ", "near-open central", Height.NEAR_OPEN, Backness.CENTRAL, false,
            languages = listOf(Lang.DE, Lang.PT)),

        // ═══ OPEN VOWELS ═══
        Vowel("a", "open front unrounded", Height.OPEN, Backness.FRONT, false,
            languages = listOf(Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI, Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV, Lang.SW, Lang.TR, Lang.ZH)),
        Vowel("aː", "long open front unrounded", Height.OPEN, Backness.FRONT, false, long = true,
            languages = listOf(Lang.DE, Lang.FI, Lang.HI, Lang.JA)),
        Vowel("ɑ", "open back unrounded", Height.OPEN, Backness.BACK, false,
            languages = listOf(Lang.EN, Lang.NL, Lang.NO)),
        Vowel("ɑː", "long open back unrounded", Height.OPEN, Backness.BACK, false, long = true,
            languages = listOf(Lang.EN, Lang.NL)),
        Vowel("ɒ", "open back rounded", Height.OPEN, Backness.BACK, true,
            languages = listOf(Lang.EN)),

        // ═══ NASAL VOWELS ═══
        Vowel("ɛ̃", "nasal open-mid front unrounded", Height.OPEN_MID, Backness.FRONT, false, nasal = true,
            languages = listOf(Lang.FR, Lang.PT)),
        Vowel("œ̃", "nasal open-mid front rounded", Height.OPEN_MID, Backness.FRONT, true, nasal = true,
            languages = listOf(Lang.FR)),
        Vowel("ɔ̃", "nasal open-mid back rounded", Height.OPEN_MID, Backness.BACK, true, nasal = true,
            languages = listOf(Lang.FR, Lang.PT)),
        Vowel("ã", "nasal open front unrounded", Height.OPEN, Backness.FRONT, false, nasal = true,
            languages = listOf(Lang.PT)),
        Vowel("ĩ", "nasal close front unrounded", Height.CLOSE, Backness.FRONT, false, nasal = true,
            languages = listOf(Lang.PT)),
        Vowel("ũ", "nasal close back rounded", Height.CLOSE, Backness.BACK, true, nasal = true,
            languages = listOf(Lang.PT))
    )

    // ═══════════════════════════════════════════════════════════════════════
    // LOOKUP FUNCTIONS
    // ═══════════════════════════════════════════════════════════════════════

    private val consonantByIpa = consonants.associateBy { it.ipa }
    private val vowelByIpa = vowels.associateBy { it.ipa }

    fun getConsonant(ipa: String): Consonant? = consonantByIpa[ipa]
    fun getVowel(ipa: String): Vowel? = vowelByIpa[ipa]

    fun getConsonantsForLanguage(lang: Lang): List<Consonant> =
        consonants.filter { lang in it.languages }

    fun getVowelsForLanguage(lang: Lang): List<Vowel> =
        vowels.filter { lang in it.languages }

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

    fun getInventory(lang: Lang): LanguageInventory {
        return LanguageInventory(
            consonants = getConsonantsForLanguage(lang),
            vowels = getVowelsForLanguage(lang)
        )
    }

    /**
     * All supported language codes
     */
    val supportedLanguages = listOf(
        Lang.AR, Lang.DA, Lang.DE, Lang.EL, Lang.EN, Lang.ES, Lang.FI, Lang.FR, Lang.HE, Lang.HI,
        Lang.IT, Lang.JA, Lang.KO, Lang.MS, Lang.NL, Lang.NO, Lang.PL, Lang.PT, Lang.RU, Lang.SV,
        Lang.SW, Lang.TR, Lang.ZH
    )
}
