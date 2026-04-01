package al.clk.key

/**
 * Optimized Morphological Key System using enums with computed properties.
 * Each language has self-contained enums - no shared interfaces per language.
 */

// ═══════════════════════════════════════════════════════════════════════════════
// HEBREW ENUMS WITH NIKUD
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Hebrew prefix forms - extend existing HebrewLetter with morphological data
 */
enum class HebrewPrefix(
    val letter: HebrewLetter,
    val form: String,         // Full prefix with nikud
    val ipa: String,
    val meaning: String
) {
    BE(HebrewLetter.BET, "בְּ", "be", "in/with"),
    LE(HebrewLetter.LAMED, "לְ", "le", "to/for"),
    MI(HebrewLetter.MEM, "מִ", "mi", "from"),
    KE(HebrewLetter.KAF, "כְּ", "ke", "like/as"),
    HA(HebrewLetter.HE, "הַ", "ha", "the"),
    VE(HebrewLetter.VAV, "וְ", "ve", "and"),
    SHE(HebrewLetter.SHIN, "שֶׁ", "ʃe", "that/which");

    companion object {
        private val byLetter = entries.associateBy { it.letter }
        fun fromLetter(l: HebrewLetter): HebrewPrefix? = byLetter[l]
    }
}

/**
 * Hebrew vowels (nikud) with IPA and color hue for visualization.
 * Named NikudVowel to avoid conflict with existing Nikud enum.
 */
enum class NikudVowel(
    val mark: String,
    val ipa: String,
    val unicode: Int,
    val hue: Int,          // HSL hue for visualization
    val isShort: Boolean   // Short vs long vowel
) {
    // A-class vowels (warm hues)
    KAMATZ("ָ", "a", 0x05B8, 0, false),
    PATACH("ַ", "a", 0x05B7, 15, true),
    HATAF_PATACH("ֲ", "a", 0x05B2, 30, true),

    // E-class vowels (yellow-green)
    TZERE("ֵ", "e", 0x05B5, 60, false),
    SEGOL("ֶ", "ɛ", 0x05B6, 75, true),
    HATAF_SEGOL("ֱ", "ɛ", 0x05B1, 90, true),

    // I-class vowels (green)
    CHIRIK("ִ", "i", 0x05B4, 120, true),
    CHIRIK_MALE("ִי", "iː", 0x05B4, 135, false),  // With yod

    // O-class vowels (blue)
    CHOLAM("ֹ", "o", 0x05B9, 200, false),
    CHOLAM_MALE("וֹ", "oː", 0x05B9, 210, false),  // With vav
    HATAF_KAMATZ("ֳ", "o", 0x05B3, 220, true),
    KAMATZ_KATAN("ָ", "o", 0x05B8, 230, true),    // Same char as kamatz, context-dependent

    // U-class vowels (purple)
    KUBUTZ("ֻ", "u", 0x05BB, 280, true),
    SHURUK("וּ", "uː", 0x05BC, 290, false),       // Dagesh in vav

    // Reduced vowel (gray)
    SHVA("ְ", "ə", 0x05B0, 0, true),
    SHVA_NA("ְ", "e", 0x05B0, 60, true);          // Mobile shva - same char, context

    /** Is this a hataf (reduced) vowel */
    val isHataf: Boolean get() = this in setOf(HATAF_PATACH, HATAF_SEGOL, HATAF_KAMATZ)

    /** Is this a full (male) vowel with mater lectionis */
    val isMale: Boolean get() = this in setOf(CHIRIK_MALE, CHOLAM_MALE, SHURUK)

    companion object {
        // Use first entry for each unicode (primary vowel, not variants like CHIRIK_MALE)
        private val byCode: Map<Int, NikudVowel> = buildMap {
            for (v in NikudVowel.entries) {
                if (v.unicode !in this.keys) put(v.unicode, v)
            }
        }

        fun fromCodePoint(cp: Int): NikudVowel? = byCode[cp]

        /**
         * Get nikud options for an IPA sound
         */
        fun forIpa(ipa: String): List<NikudVowel> = when (ipa) {
            "a" -> listOf(PATACH, KAMATZ, HATAF_PATACH)
            "e" -> listOf(TZERE, SEGOL, HATAF_SEGOL)
            "ɛ" -> listOf(SEGOL, HATAF_SEGOL)
            "i" -> listOf(CHIRIK, CHIRIK_MALE)
            "o" -> listOf(CHOLAM, CHOLAM_MALE, KAMATZ, HATAF_KAMATZ)
            "u" -> listOf(KUBUTZ, SHURUK)
            "ə" -> listOf(SHVA)
            "ɪ" -> listOf(CHIRIK)
            "ʊ" -> listOf(KUBUTZ)
            "æ" -> listOf(SEGOL, PATACH)
            "ʌ" -> listOf(PATACH)
            "ɔ" -> listOf(KAMATZ)
            "ɑ" -> listOf(KAMATZ)
            else -> emptyList()
        }

        /**
         * Apply nikud to a letter
         */
        fun apply(letter: Char, ipa: String, useMale: Boolean = false): String {
            val nikudList = forIpa(ipa)
            if (nikudList.isEmpty()) return letter.toString()

            // Use hataf for gutturals with schwa
            val gutturals = setOf('\u05D0', '\u05D4', '\u05D7', '\u05E2', '\u05E8')
            if (letter in gutturals && ipa == "ə") {
                return "$letter${HATAF_PATACH.mark}"
            }

            val nikud = nikudList.first()

            // Use male form if requested
            if (useMale) {
                return when (ipa) {
                    "i" -> "$letter${CHIRIK_MALE.mark}"
                    "o" -> "$letter${CHOLAM_MALE.mark}"
                    "u" -> "$letter${SHURUK.mark}"
                    else -> "$letter${nikud.mark}"
                }
            }

            return "$letter${nikud.mark}"
        }
    }
}

/**
 * Hebrew diacritical modifiers (non-vowel marks)
 */
enum class HebrewModifier(
    val mark: String,
    val unicode: Int,
    val ipa: String,
    val effect: String
) {
    DAGESH("ּ", 0x05BC, "", "double/hard"),
    SHIN_DOT("ׁ", 0x05C1, "ʃ", "shin"),
    SIN_DOT("ׂ", 0x05C2, "s", "sin"),
    RAFE("ֿ", 0x05BF, "", "soft"),
    MAPPIQ("ּ", 0x05BC, "h", "pronounced-he");

    companion object {
        private val byCode = entries.associateBy { it.unicode }
        fun fromCodePoint(cp: Int): HebrewModifier? = byCode[cp]
    }
}

/**
 * BGDKPT letters - consonants affected by dagesh
 */
enum class HebrewBgdkpt(
    val letter: Char,
    val withDagesh: String,
    val ipaDagesh: String,
    val withoutDagesh: String,
    val ipaWithout: String,
    val classical: String? = null,
    val classicalIpa: String? = null
) {
    BET('\u05D1', "בּ", "b", "ב", "v", null, null),
    GIMEL('\u05D2', "גּ", "g", "ג", "g", "ג", "ɣ"),
    DALET('\u05D3', "דּ", "d", "ד", "d", "ד", "ð"),
    KAF('\u05DB', "כּ", "k", "כ", "x", null, null),
    PE('\u05E4', "פּ", "p", "פ", "f", null, null),
    TAV('\u05EA', "תּ", "t", "ת", "t", "ת", "θ");

    /** Whether this letter has distinct modern sounds with/without dagesh */
    val hasDistinctModernSounds: Boolean get() = ipaDagesh != ipaWithout

    /** Get IPA for dagesh state */
    fun getIpa(hasDagesh: Boolean, useClassical: Boolean = false): String = when {
        hasDagesh -> ipaDagesh
        useClassical && classicalIpa != null -> classicalIpa
        else -> ipaWithout
    }

    /** Get letter form for dagesh state */
    fun getForm(hasDagesh: Boolean): String = if (hasDagesh) withDagesh else withoutDagesh

    companion object {
        private val byLetter = entries.associateBy { it.letter }
        fun fromChar(c: Char): HebrewBgdkpt? = byLetter[c]
        fun isBgdkpt(c: Char): Boolean = byLetter.containsKey(c)
        val modernDistinct = entries.filter { it.hasDistinctModernSounds }
    }
}

/**
 * Hebrew plural/gender suffixes
 */
enum class HebrewSuffix(
    val chars: String,
    val ipa: String,
    val gender: Gender,
    val number: Number
) {
    // Masculine
    MASC_PLURAL("ים", "im", Gender.MASCULINE, Number.PLURAL),
    MASC_DUAL("יים", "ajim", Gender.MASCULINE, Number.DUAL),

    // Feminine
    FEM_SINGULAR("ה", "a", Gender.FEMININE, Number.SINGULAR),
    FEM_PLURAL("ות", "ot", Gender.FEMININE, Number.PLURAL),
    FEM_DUAL("תיים", "tajim", Gender.FEMININE, Number.DUAL),

    // Possessive suffixes
    POSS_1S("י", "i", Gender.NEUTRAL, Number.SINGULAR),      // My
    POSS_2MS("ך", "xa", Gender.MASCULINE, Number.SINGULAR),  // Your (m)
    POSS_2FS("ך", "ex", Gender.FEMININE, Number.SINGULAR),   // Your (f)
    POSS_3MS("ו", "o", Gender.MASCULINE, Number.SINGULAR),   // His
    POSS_3FS("ה", "a", Gender.FEMININE, Number.SINGULAR),    // Her
    POSS_1P("נו", "enu", Gender.NEUTRAL, Number.PLURAL),     // Our
    POSS_2MP("כם", "xem", Gender.MASCULINE, Number.PLURAL),  // Your (m.pl)
    POSS_2FP("כן", "xen", Gender.FEMININE, Number.PLURAL),   // Your (f.pl)
    POSS_3MP("הם", "em", Gender.MASCULINE, Number.PLURAL),   // Their (m)
    POSS_3FP("הן", "en", Gender.FEMININE, Number.PLURAL);    // Their (f)

    companion object {
        fun plural(gender: Gender): HebrewSuffix = when (gender) {
            Gender.MASCULINE -> MASC_PLURAL
            Gender.FEMININE -> FEM_PLURAL
            Gender.NEUTRAL -> MASC_PLURAL
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ARABIC ENUMS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Arabic consonants with all positional forms (using strings for multi-char forms)
 */
enum class ArabicLetter(
    val isolated: String,
    val initial: String,
    val medial: String,
    val finalForm: String,
    val ipa: String,
    val qwerty: Char
) {
    ALIF("ا", "ا", "ـا", "ـا", "", 'h'),
    BA("ب", "بـ", "ـبـ", "ـب", "b", 'f'),
    TA("ت", "تـ", "ـتـ", "ـت", "t", 'j'),
    THA("ث", "ثـ", "ـثـ", "ـث", "θ", 'e'),
    JEEM("ج", "جـ", "ـجـ", "ـج", "dʒ", '/'),
    HA("ح", "حـ", "ـحـ", "ـح", "ħ", 'p'),
    KHA("خ", "خـ", "ـخـ", "ـخ", "x", 'o'),
    DAL("د", "د", "ـد", "ـد", "d", 's'),
    DHAL("ذ", "ذ", "ـذ", "ـذ", "ð", '/'),
    RA("ر", "ر", "ـر", "ـر", "r", 'v'),
    ZAY("ز", "ز", "ـز", "ـز", "z", '.'),
    SEEN("س", "سـ", "ـسـ", "ـس", "s", 's'),
    SHEEN("ش", "شـ", "ـشـ", "ـش", "ʃ", 'a'),
    SAD("ص", "صـ", "ـصـ", "ـص", "sˤ", 'w'),
    DAD("ض", "ضـ", "ـضـ", "ـض", "dˤ", 'q'),
    TAA("ط", "طـ", "ـطـ", "ـط", "tˤ", '/'),
    ZAA("ظ", "ظـ", "ـظـ", "ـظ", "ðˤ", '/'),
    AIN("ع", "عـ", "ـعـ", "ـع", "ʕ", 'u'),
    GHAIN("غ", "غـ", "ـغـ", "ـغ", "ɣ", 'y'),
    FA("ف", "فـ", "ـفـ", "ـف", "f", 't'),
    QAF("ق", "قـ", "ـقـ", "ـق", "q", 'r'),
    KAF("ك", "كـ", "ـكـ", "ـك", "k", 'k'),
    LAM("ل", "لـ", "ـلـ", "ـل", "l", 'g'),
    MEEM("م", "مـ", "ـمـ", "ـم", "m", 'l'),
    NOON("ن", "نـ", "ـنـ", "ـن", "n", 'n'),
    HA_END("ه", "هـ", "ـهـ", "ـه", "h", 'i'),
    WAW("و", "و", "ـو", "ـو", "w", ','),
    YA("ي", "يـ", "ـيـ", "ـي", "j", 'd'),
    HAMZA("ء", "ء", "ء", "ء", "ʔ", 'x'),
    TA_MARBUTA("ة", "ة", "ـة", "ـة", "a", ']');

    /** Get form for position */
    fun forPosition(pos: SyllablePosition): String = when (pos) {
        SyllablePosition.PREFIX -> initial
        SyllablePosition.MIDDLE -> medial
        SyllablePosition.SUFFIX -> finalForm
    }

    companion object {
        private val byIsolated = entries.associateBy { it.isolated }
        private val byQwerty = entries.associateBy { it.qwerty }

        fun fromChar(s: String): ArabicLetter? = byIsolated[s]
        fun fromQwerty(c: Char): ArabicLetter? = byQwerty[c]
    }
}

/**
 * Arabic vowels (harakat)
 */
enum class Haraka(
    val char: Char,
    val ipa: String,
    val tanwin: Char?,    // Nunation form
    val tanwinIpa: String?
) {
    FATHA('َ', "a", 'ً', "an"),
    KASRA('ِ', "i", 'ٍ', "in"),
    DAMMA('ُ', "u", 'ٌ', "un"),
    SUKUN('ْ', "", null, null),
    SHADDA('ّ', "ː", null, null);  // Gemination

    companion object {
        private val byChar = entries.associateBy { it.char }
        fun fromChar(c: Char): Haraka? = byChar[c]
    }
}

/**
 * Arabic plural suffixes
 */
enum class ArabicSuffix(
    val chars: String,
    val ipa: String,
    val gender: Gender,
    val number: Number,
    val grammaticalCase: String  // nom/acc/gen
) {
    // Sound masculine plural
    MASC_PLURAL_NOM("ون", "uːn", Gender.MASCULINE, Number.PLURAL, "nom"),
    MASC_PLURAL_ACC("ين", "iːn", Gender.MASCULINE, Number.PLURAL, "acc"),

    // Sound feminine plural
    FEM_PLURAL("ات", "aːt", Gender.FEMININE, Number.PLURAL, "all"),

    // Dual
    DUAL_NOM("ان", "aːn", Gender.NEUTRAL, Number.DUAL, "nom"),
    DUAL_ACC("ين", "ajn", Gender.NEUTRAL, Number.DUAL, "acc");

    companion object {
        fun plural(gender: Gender, case: String = "nom"): ArabicSuffix = when {
            gender == Gender.FEMININE -> FEM_PLURAL
            case == "nom" -> MASC_PLURAL_NOM
            else -> MASC_PLURAL_ACC
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ROMANCE LANGUAGE ENUMS (Spanish, French, Italian, Portuguese)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Spanish morphological suffixes
 */
enum class SpanishSuffix(
    val chars: String,
    val ipa: String,
    val gender: Gender,
    val number: Number
) {
    MASC_SING("o", "o", Gender.MASCULINE, Number.SINGULAR),
    MASC_PLURAL("os", "os", Gender.MASCULINE, Number.PLURAL),
    FEM_SING("a", "a", Gender.FEMININE, Number.SINGULAR),
    FEM_PLURAL("as", "as", Gender.FEMININE, Number.PLURAL);

    companion object {
        fun ending(gender: Gender, number: Number): SpanishSuffix = when {
            gender == Gender.MASCULINE && number == Number.SINGULAR -> MASC_SING
            gender == Gender.MASCULINE && number == Number.PLURAL -> MASC_PLURAL
            gender == Gender.FEMININE && number == Number.SINGULAR -> FEM_SING
            else -> FEM_PLURAL
        }
    }
}

/**
 * French morphological suffixes
 */
enum class FrenchSuffix(
    val chars: String,
    val ipa: String,
    val gender: Gender,
    val number: Number
) {
    MASC_SING("", "", Gender.MASCULINE, Number.SINGULAR),
    MASC_PLURAL("s", "", Gender.MASCULINE, Number.PLURAL),  // Silent s
    FEM_SING("e", "ə", Gender.FEMININE, Number.SINGULAR),
    FEM_PLURAL("es", "ə", Gender.FEMININE, Number.PLURAL);

    companion object {
        fun ending(gender: Gender, number: Number): FrenchSuffix = when {
            gender == Gender.MASCULINE && number == Number.SINGULAR -> MASC_SING
            gender == Gender.MASCULINE && number == Number.PLURAL -> MASC_PLURAL
            gender == Gender.FEMININE && number == Number.SINGULAR -> FEM_SING
            else -> FEM_PLURAL
        }
    }
}

/**
 * Italian morphological suffixes
 */
enum class ItalianSuffix(
    val chars: String,
    val ipa: String,
    val gender: Gender,
    val number: Number
) {
    MASC_SING("o", "o", Gender.MASCULINE, Number.SINGULAR),
    MASC_PLURAL("i", "i", Gender.MASCULINE, Number.PLURAL),
    FEM_SING("a", "a", Gender.FEMININE, Number.SINGULAR),
    FEM_PLURAL("e", "e", Gender.FEMININE, Number.PLURAL);

    companion object {
        fun ending(gender: Gender, number: Number): ItalianSuffix = when {
            gender == Gender.MASCULINE && number == Number.SINGULAR -> MASC_SING
            gender == Gender.MASCULINE && number == Number.PLURAL -> MASC_PLURAL
            gender == Gender.FEMININE && number == Number.SINGULAR -> FEM_SING
            else -> FEM_PLURAL
        }
    }
}

/**
 * Portuguese morphological suffixes
 */
enum class PortugueseSuffix(
    val chars: String,
    val ipa: String,
    val gender: Gender,
    val number: Number
) {
    MASC_SING("o", "u", Gender.MASCULINE, Number.SINGULAR),
    MASC_PLURAL("os", "uʃ", Gender.MASCULINE, Number.PLURAL),
    FEM_SING("a", "ɐ", Gender.FEMININE, Number.SINGULAR),
    FEM_PLURAL("as", "ɐʃ", Gender.FEMININE, Number.PLURAL);

    companion object {
        fun ending(gender: Gender, number: Number): PortugueseSuffix = when {
            gender == Gender.MASCULINE && number == Number.SINGULAR -> MASC_SING
            gender == Gender.MASCULINE && number == Number.PLURAL -> MASC_PLURAL
            gender == Gender.FEMININE && number == Number.SINGULAR -> FEM_SING
            else -> FEM_PLURAL
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// GERMAN ENUMS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * German articles with case/gender/number
 */
enum class GermanArticle(
    val chars: String,
    val ipa: String,
    val gender: Gender,
    val number: Number,
    val case: String  // nom/acc/dat/gen
) {
    // Definite nominative
    DER("der", "deːɐ̯", Gender.MASCULINE, Number.SINGULAR, "nom"),
    DIE("die", "diː", Gender.FEMININE, Number.SINGULAR, "nom"),
    DAS("das", "das", Gender.NEUTRAL, Number.SINGULAR, "nom"),
    DIE_PL("die", "diː", Gender.NEUTRAL, Number.PLURAL, "nom"),

    // Definite accusative
    DEN("den", "deːn", Gender.MASCULINE, Number.SINGULAR, "acc"),
    DIE_ACC("die", "diː", Gender.FEMININE, Number.SINGULAR, "acc"),
    DAS_ACC("das", "das", Gender.NEUTRAL, Number.SINGULAR, "acc"),

    // Definite dative
    DEM("dem", "deːm", Gender.MASCULINE, Number.SINGULAR, "dat"),
    DER_DAT("der", "deːɐ̯", Gender.FEMININE, Number.SINGULAR, "dat"),
    DEM_N("dem", "deːm", Gender.NEUTRAL, Number.SINGULAR, "dat"),
    DEN_PL("den", "deːn", Gender.NEUTRAL, Number.PLURAL, "dat"),

    // Definite genitive
    DES("des", "dɛs", Gender.MASCULINE, Number.SINGULAR, "gen"),
    DER_GEN("der", "deːɐ̯", Gender.FEMININE, Number.SINGULAR, "gen"),
    DES_N("des", "dɛs", Gender.NEUTRAL, Number.SINGULAR, "gen");

    companion object {
        fun definite(gender: Gender, number: Number, case: String = "nom"): GermanArticle {
            return entries.find { it.gender == gender && it.number == number && it.case == case }
                ?: DER
        }
    }
}

/**
 * German plural suffixes
 */
enum class GermanPlural(
    val chars: String,
    val ipa: String,
    val umlaut: Boolean  // Requires umlaut on stem
) {
    E("e", "ə", false),
    E_UMLAUT("e", "ə", true),      // -e with umlaut
    EN("en", "ən", false),
    ER("er", "ɐ", false),
    ER_UMLAUT("er", "ɐ", true),    // -er with umlaut
    S("s", "s", false),            // Foreign words
    NONE("", "", false),           // No change
    NONE_UMLAUT("", "", true);     // Umlaut only
}

// ═══════════════════════════════════════════════════════════════════════════════
// UTILITY EXTENSIONS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Extension to add Hebrew prefix to a word
 */
fun String.withHebrewPrefix(prefix: HebrewPrefix): String {
    return prefix.form + this
}

/**
 * Extension to build Hebrew word with suffix
 */
fun String.withHebrewSuffix(suffix: HebrewSuffix): String {
    // Handle final letter transformation
    val base = if (this.isNotEmpty()) {
        val lastChar = this.last()
        val letter = HebrewLetter.fromChar(lastChar)
        // If last char is final form, convert back to regular form before adding suffix
        if (letter?.finalForm != null && letter.letter == lastChar) {
            // Already in regular form
            this
        } else if (letter?.isFinalForm == true) {
            // It's a final form letter, need to find regular form
            val regular = when (letter) {
                HebrewLetter.KAF_SOFIT -> HebrewLetter.KAF
                HebrewLetter.MEM_SOFIT -> HebrewLetter.MEM
                HebrewLetter.NUN_SOFIT -> HebrewLetter.NUN
                HebrewLetter.PE_SOFIT -> HebrewLetter.PE
                HebrewLetter.TSADI_SOFIT -> HebrewLetter.TSADI
                else -> null
            }
            if (regular != null && suffix.number != Number.SINGULAR) {
                this.dropLast(1) + regular.letter
            } else {
                this
            }
        } else {
            this
        }
    } else this
    return base + suffix.chars
}

/**
 * Get IPA hue for visualization (matching poetry.html logic)
 */
fun ipaToHue(ipa: String): Int {
    if (ipa.isEmpty()) return 0
    return when (ipa.first()) {
        'a', 'ɑ', 'æ' -> 0      // Red
        'e', 'ɛ', 'ə' -> 60     // Yellow
        'i', 'ɪ' -> 120         // Green
        'o', 'ɔ' -> 200         // Blue
        'u', 'ʊ' -> 280         // Purple
        else -> when (ipa) {
            "ʃ", "ʒ" -> 45      // Orange (sibilants)
            "ts", "dʒ", "tʃ" -> 30
            else -> 180         // Cyan (default consonant)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// MORPHO KEY GENERATORS - Convert enums to MorphoKey instances
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Generate MorphoKey from HebrewLetter with position/modifier variants
 */
fun HebrewLetter.toMorphoKey(): MorphoKey {
    val base = MorphoVariant(letter.toString(), ipa, displayName)

    // Position map (final form for suffix)
    val position = if (finalForm != null) {
        mapOf(SyllablePosition.SUFFIX to MorphoVariant(finalForm.toString(), ipa, "$displayName sofit"))
    } else emptyMap()

    // Modifiers (dagesh for bgdkpt)
    val modifiers = if (ipaDagesh != null && ipaDagesh != ipa) {
        mapOf(Modifier.SHIFT to MorphoKey(
            base = MorphoVariant("$letter\u05BC", ipaDagesh!!, "$displayName-dagesh")
        ))
    } else emptyMap()

    return MorphoKey(base, position, emptyMap(), emptyMap(), modifiers)
}

/**
 * Generate prefix MorphoKey from HebrewPrefix
 */
fun HebrewPrefix.toMorphoKey(): MorphoKey {
    val base = MorphoVariant(letter.letter.toString(), letter.ipa, letter.displayName)
    val position = mapOf(
        SyllablePosition.PREFIX to MorphoVariant(form, ipa, "${letter.displayName}-prefix-$meaning"),
        SyllablePosition.MIDDLE to base,
        SyllablePosition.SUFFIX to if (letter.finalForm != null)
            MorphoVariant(letter.finalForm.toString(), letter.ipa, "${letter.displayName}-sofit")
        else base
    )

    // Add dagesh modifier for bgdkpt
    val modifiers = if (letter.ipaDagesh != null && letter.ipaDagesh != letter.ipa) {
        mapOf(Modifier.SHIFT to MorphoKey(
            base = MorphoVariant("${letter.letter}\u05BC", letter.ipaDagesh!!, "${letter.displayName}-dagesh")
        ))
    } else emptyMap()

    return MorphoKey(base, position, emptyMap(), emptyMap(), modifiers)
}

/**
 * Generate suffix MorphoKey from HebrewSuffix
 */
fun HebrewSuffix.toMorphoKey(): MorphoKey {
    val base = MorphoVariant(chars, ipa, name.lowercase().replace('_', '-'))
    val numberMap = mapOf(number to base)
    val genderMap = mapOf(gender to base)
    return MorphoKey(base, emptyMap(), genderMap, numberMap, emptyMap())
}

/**
 * Generate MorphoKey from ArabicLetter
 */
fun ArabicLetter.toMorphoKey(): MorphoKey {
    val base = MorphoVariant(isolated, ipa, name.lowercase())
    val position = mapOf(
        SyllablePosition.PREFIX to MorphoVariant(initial, ipa, "${name.lowercase()}-initial"),
        SyllablePosition.MIDDLE to MorphoVariant(medial, ipa, "${name.lowercase()}-medial"),
        SyllablePosition.SUFFIX to MorphoVariant(finalForm, ipa, "${name.lowercase()}-final")
    )
    return MorphoKey(base, position, emptyMap(), emptyMap(), emptyMap())
}

/**
 * Generate suffix MorphoKey from ArabicSuffix
 */
fun ArabicSuffix.toMorphoKey(): MorphoKey {
    val base = MorphoVariant(chars, ipa, name.lowercase().replace('_', '-'))
    val numberMap = mapOf(number to base)
    val genderMap = mapOf(gender to base)
    return MorphoKey(base, emptyMap(), genderMap, numberMap, emptyMap())
}

/**
 * Hebrew keyboard layout using enums
 */
object HebrewMorphoEnum {
    // All consonants from HebrewLetter
    val consonants = HebrewLetter.entries
        .filter { !it.isFinalForm }
        .associateBy { it.letter }
        .mapValues { it.value.toMorphoKey() }

    // Prefix forms
    val prefixes = HebrewPrefix.entries.map { it.toMorphoKey() }

    // Plural suffixes by gender
    val pluralSuffixes = mapOf(
        Gender.MASCULINE to HebrewSuffix.MASC_PLURAL.toMorphoKey(),
        Gender.FEMININE to HebrewSuffix.FEM_PLURAL.toMorphoKey()
    )

    // QWERTY mapping
    val qwertyMap = mapOf(
        "t" to HebrewLetter.ALEF,
        "c" to HebrewLetter.BET,
        "d" to HebrewLetter.GIMEL,
        "s" to HebrewLetter.DALET,
        "v" to HebrewLetter.HE,
        "u" to HebrewLetter.VAV,
        "z" to HebrewLetter.ZAYIN,
        "j" to HebrewLetter.CHET,
        "y" to HebrewLetter.TET,
        "h" to HebrewLetter.YOD,
        "f" to HebrewLetter.KAF,
        "k" to HebrewLetter.LAMED,
        "n" to HebrewLetter.MEM,
        "b" to HebrewLetter.NUN,
        "x" to HebrewLetter.SAMECH,
        "g" to HebrewLetter.AYIN,
        "p" to HebrewLetter.PE,
        "m" to HebrewLetter.TSADI,
        "e" to HebrewLetter.QOF,
        "r" to HebrewLetter.RESH,
        "a" to HebrewLetter.SHIN,
        "," to HebrewLetter.TAV
    )

    val keys: Map<String, MorphoKey> = qwertyMap.mapValues { it.value.toMorphoKey() }
}

/**
 * Arabic keyboard layout using enums
 */
object ArabicMorphoEnum {
    val consonants = ArabicLetter.entries
        .associateBy { it.isolated }
        .mapValues { it.value.toMorphoKey() }

    // AL prefix
    val alPrefix = MorphoKey(
        base = MorphoVariant("ال", "al", "al-definite"),
        position = mapOf(SyllablePosition.PREFIX to MorphoVariant("الـ", "al", "al-prefix"))
    )

    val prefixes = listOf(alPrefix)

    val pluralSuffixes = mapOf(
        Gender.MASCULINE to ArabicSuffix.MASC_PLURAL_NOM.toMorphoKey(),
        Gender.FEMININE to ArabicSuffix.FEM_PLURAL.toMorphoKey()
    )

    val qwertyMap = mapOf(
        "h" to ArabicLetter.ALIF,
        "f" to ArabicLetter.BA,
        "j" to ArabicLetter.TA,
        "e" to ArabicLetter.THA,
        "p" to ArabicLetter.HA,
        "o" to ArabicLetter.KHA,
        "v" to ArabicLetter.RA,
        "." to ArabicLetter.ZAY,
        "s" to ArabicLetter.SEEN,
        "a" to ArabicLetter.SHEEN,
        "w" to ArabicLetter.SAD,
        "q" to ArabicLetter.DAD,
        "u" to ArabicLetter.AIN,
        "y" to ArabicLetter.GHAIN,
        "t" to ArabicLetter.FA,
        "r" to ArabicLetter.QAF,
        "k" to ArabicLetter.KAF,
        "g" to ArabicLetter.LAM,
        "l" to ArabicLetter.MEEM,
        "," to ArabicLetter.WAW,
        "d" to ArabicLetter.YA,
        "x" to ArabicLetter.HAMZA
    )

    val keys: Map<String, MorphoKey> = qwertyMap.mapValues { it.value.toMorphoKey() }
}

/**
 * Romance language suffixes using enums
 */
object RomanceMorphoEnum {
    fun spanishEnding(gender: Gender, number: Number): MorphoKey =
        SpanishSuffix.ending(gender, number).let {
            MorphoKey(base = MorphoVariant(it.chars, it.ipa, it.name.lowercase()))
        }

    fun frenchEnding(gender: Gender, number: Number): MorphoKey =
        FrenchSuffix.ending(gender, number).let {
            MorphoKey(base = MorphoVariant(it.chars, it.ipa, it.name.lowercase()))
        }

    fun italianEnding(gender: Gender, number: Number): MorphoKey =
        ItalianSuffix.ending(gender, number).let {
            MorphoKey(base = MorphoVariant(it.chars, it.ipa, it.name.lowercase()))
        }

    fun portugueseEnding(gender: Gender, number: Number): MorphoKey =
        PortugueseSuffix.ending(gender, number).let {
            MorphoKey(base = MorphoVariant(it.chars, it.ipa, it.name.lowercase()))
        }
}

/**
 * German articles and plurals using enums
 */
object GermanMorphoEnum {
    fun article(gender: Gender, number: Number, case: String = "nom"): MorphoKey =
        GermanArticle.definite(gender, number, case).let {
            MorphoKey(base = MorphoVariant(it.chars, it.ipa, it.name.lowercase()))
        }

    fun pluralSuffix(type: GermanPlural): MorphoKey =
        MorphoKey(base = MorphoVariant(type.chars, type.ipa, type.name.lowercase()))
}
