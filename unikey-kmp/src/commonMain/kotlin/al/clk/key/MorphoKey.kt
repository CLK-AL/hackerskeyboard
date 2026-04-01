package al.clk.key

/**
 * Morphological Key System - Unified keyboard layout with syllable positions,
 * gender, number, and modifier support for all 23 Chatterbox languages.
 *
 * Each key can produce different output based on:
 * - Position: PREFIX (preposition), MIDDLE (root), SUFFIX (ending)
 * - Gender: MASCULINE, FEMININE
 * - Number: SINGULAR, PLURAL, DUAL
 * - Modifiers: SHIFT, CTRL, ALT
 */

/**
 * Position in syllable/word structure
 */
enum class SyllablePosition {
    PREFIX,   // Preposition/article attached to start (Hebrew: ב,ל,מ,כ,ה,ו,ש)
    MIDDLE,   // Root/stem of word
    SUFFIX    // Ending (plurals, gender markers, verb conjugations)
}

/**
 * Grammatical gender
 */
enum class Gender {
    MASCULINE,
    FEMININE,
    NEUTRAL   // For languages without grammatical gender
}

/**
 * Grammatical number
 */
enum class Number {
    SINGULAR,
    PLURAL,
    DUAL      // For Hebrew/Arabic dual forms
}

/**
 * Full morphological variant of a character
 */
data class MorphoVariant(
    val char: String,
    val ipa: String,
    val displayName: String
)

/**
 * A single morphological key with all variants
 */
data class MorphoKey(
    val base: MorphoVariant,
    val position: Map<SyllablePosition, MorphoVariant> = emptyMap(),
    val gender: Map<Gender, MorphoVariant> = emptyMap(),
    val number: Map<Number, MorphoVariant> = emptyMap(),
    val modifiers: Map<Modifier, MorphoKey> = emptyMap()
) : ILayoutKey {
    override val char: String get() = base.char
    override val ipa: String get() = base.ipa
    override val displayName: String get() = base.displayName

    /**
     * Get variant for position
     */
    fun forPosition(pos: SyllablePosition): MorphoVariant = position[pos] ?: base

    /**
     * Get variant for gender
     */
    fun forGender(g: Gender): MorphoVariant = gender[g] ?: base

    /**
     * Get variant for number
     */
    fun forNumber(n: Number): MorphoVariant = number[n] ?: base

    /**
     * Get key with modifier
     */
    fun withModifier(mod: Modifier): MorphoKey? = modifiers[mod]

    /**
     * Get shifted variant
     */
    val shift: MorphoKey? get() = modifiers[Modifier.SHIFT]
}

/**
 * Hebrew morphological keys with prefix/suffix system
 */
object HebrewMorphoKeys {

    // ═══ Hebrew Prefix Letters (prepositions) ═══
    // These letters attach to the beginning of words

    val BET_PREFIX = MorphoKey(
        base = MorphoVariant("ב", "v", "bet"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("בְּ", "be", "bet-prefix-in"),
            SyllablePosition.MIDDLE to MorphoVariant("ב", "v", "bet"),
            SyllablePosition.SUFFIX to MorphoVariant("ב", "v", "bet")
        ),
        modifiers = mapOf(
            Modifier.SHIFT to MorphoKey(
                base = MorphoVariant("בּ", "b", "bet-dagesh")
            )
        )
    )

    val LAMED_PREFIX = MorphoKey(
        base = MorphoVariant("ל", "l", "lamed"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("לְ", "le", "lamed-prefix-to"),
            SyllablePosition.MIDDLE to MorphoVariant("ל", "l", "lamed"),
            SyllablePosition.SUFFIX to MorphoVariant("ל", "l", "lamed")
        )
    )

    val MEM_PREFIX = MorphoKey(
        base = MorphoVariant("מ", "m", "mem"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("מְ", "mi", "mem-prefix-from"),
            SyllablePosition.MIDDLE to MorphoVariant("מ", "m", "mem"),
            SyllablePosition.SUFFIX to MorphoVariant("ם", "m", "mem-sofit")
        )
    )

    val KAF_PREFIX = MorphoKey(
        base = MorphoVariant("כ", "x", "kaf"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("כְּ", "ke", "kaf-prefix-like"),
            SyllablePosition.MIDDLE to MorphoVariant("כ", "x", "kaf"),
            SyllablePosition.SUFFIX to MorphoVariant("ך", "x", "kaf-sofit")
        ),
        modifiers = mapOf(
            Modifier.SHIFT to MorphoKey(
                base = MorphoVariant("כּ", "k", "kaf-dagesh")
            )
        )
    )

    val HE_PREFIX = MorphoKey(
        base = MorphoVariant("ה", "h", "he"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("הַ", "ha", "he-prefix-the"),
            SyllablePosition.MIDDLE to MorphoVariant("ה", "h", "he"),
            SyllablePosition.SUFFIX to MorphoVariant("ה", "a", "he-suffix-fem")  // Feminine marker
        ),
        gender = mapOf(
            Gender.FEMININE to MorphoVariant("ה", "a", "he-fem-marker")
        )
    )

    val VAV_PREFIX = MorphoKey(
        base = MorphoVariant("ו", "v", "vav"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("וְ", "ve", "vav-prefix-and"),
            SyllablePosition.MIDDLE to MorphoVariant("ו", "v", "vav"),
            SyllablePosition.SUFFIX to MorphoVariant("ו", "v", "vav")
        )
    )

    val SHIN_PREFIX = MorphoKey(
        base = MorphoVariant("ש", "ʃ", "shin"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("שֶׁ", "ʃe", "shin-prefix-that"),
            SyllablePosition.MIDDLE to MorphoVariant("ש", "ʃ", "shin"),
            SyllablePosition.SUFFIX to MorphoVariant("ש", "ʃ", "shin")
        ),
        modifiers = mapOf(
            Modifier.SHIFT to MorphoKey(
                base = MorphoVariant("שׂ", "s", "sin")  // Sin dot variant
            )
        )
    )

    // ═══ Hebrew Suffix Patterns ═══

    val YOD_MEM_SUFFIX = MorphoKey(
        base = MorphoVariant("ים", "im", "yod-mem"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("", "", "none"),
            Number.PLURAL to MorphoVariant("ים", "im", "masc-plural"),
            Number.DUAL to MorphoVariant("יים", "ajim", "dual")
        ),
        gender = mapOf(
            Gender.MASCULINE to MorphoVariant("ים", "im", "masc-plural")
        )
    )

    val VAV_TAV_SUFFIX = MorphoKey(
        base = MorphoVariant("ות", "ot", "vav-tav"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("ה", "a", "fem-singular"),
            Number.PLURAL to MorphoVariant("ות", "ot", "fem-plural")
        ),
        gender = mapOf(
            Gender.FEMININE to MorphoVariant("ות", "ot", "fem-plural")
        )
    )

    // ═══ Full Hebrew Consonant Set ═══

    val ALEF = MorphoKey(base = MorphoVariant("א", "ʔ", "alef"))

    val BET = MorphoKey(
        base = MorphoVariant("ב", "v", "bet"),
        modifiers = mapOf(
            Modifier.SHIFT to MorphoKey(base = MorphoVariant("בּ", "b", "bet-dagesh"))
        )
    )

    val GIMEL = MorphoKey(base = MorphoVariant("ג", "g", "gimel"))
    val DALET = MorphoKey(base = MorphoVariant("ד", "d", "dalet"))
    val HE = HE_PREFIX
    val VAV = VAV_PREFIX
    val ZAYIN = MorphoKey(base = MorphoVariant("ז", "z", "zayin"))
    val CHET = MorphoKey(base = MorphoVariant("ח", "x", "chet"))
    val TET = MorphoKey(base = MorphoVariant("ט", "t", "tet"))
    val YOD = MorphoKey(base = MorphoVariant("י", "j", "yod"))

    val KAF = MorphoKey(
        base = MorphoVariant("כ", "x", "kaf"),
        position = mapOf(
            SyllablePosition.SUFFIX to MorphoVariant("ך", "x", "kaf-sofit")
        ),
        modifiers = mapOf(
            Modifier.SHIFT to MorphoKey(base = MorphoVariant("כּ", "k", "kaf-dagesh"))
        )
    )

    val LAMED = LAMED_PREFIX

    val MEM = MorphoKey(
        base = MorphoVariant("מ", "m", "mem"),
        position = mapOf(
            SyllablePosition.SUFFIX to MorphoVariant("ם", "m", "mem-sofit")
        )
    )

    val NUN = MorphoKey(
        base = MorphoVariant("נ", "n", "nun"),
        position = mapOf(
            SyllablePosition.SUFFIX to MorphoVariant("ן", "n", "nun-sofit")
        )
    )

    val SAMECH = MorphoKey(base = MorphoVariant("ס", "s", "samech"))
    val AYIN = MorphoKey(base = MorphoVariant("ע", "ʕ", "ayin"))

    val PE = MorphoKey(
        base = MorphoVariant("פ", "f", "pe"),
        position = mapOf(
            SyllablePosition.SUFFIX to MorphoVariant("ף", "f", "pe-sofit")
        ),
        modifiers = mapOf(
            Modifier.SHIFT to MorphoKey(base = MorphoVariant("פּ", "p", "pe-dagesh"))
        )
    )

    val TSADI = MorphoKey(
        base = MorphoVariant("צ", "ts", "tsadi"),
        position = mapOf(
            SyllablePosition.SUFFIX to MorphoVariant("ץ", "ts", "tsadi-sofit")
        )
    )

    val QOF = MorphoKey(base = MorphoVariant("ק", "k", "qof"))
    val RESH = MorphoKey(base = MorphoVariant("ר", "ʁ", "resh"))
    val SHIN = SHIN_PREFIX
    val TAV = MorphoKey(base = MorphoVariant("ת", "t", "tav"))

    /**
     * All Hebrew morpho keys
     */
    val all: List<MorphoKey> = listOf(
        ALEF, BET, GIMEL, DALET, HE, VAV, ZAYIN, CHET, TET, YOD,
        KAF, LAMED, MEM, NUN, SAMECH, AYIN, PE, TSADI, QOF, RESH, SHIN, TAV
    )

    /**
     * Hebrew prefix letters
     */
    val prefixes: List<MorphoKey> = listOf(
        BET_PREFIX, LAMED_PREFIX, MEM_PREFIX, KAF_PREFIX, HE_PREFIX, VAV_PREFIX, SHIN_PREFIX
    )

    /**
     * Hebrew plural suffixes
     */
    val pluralSuffixes = mapOf(
        Gender.MASCULINE to YOD_MEM_SUFFIX,
        Gender.FEMININE to VAV_TAV_SUFFIX
    )
}

/**
 * Arabic morphological keys with prefix/suffix system
 */
object ArabicMorphoKeys {

    // ═══ Arabic Definite Article ═══
    val AL_PREFIX = MorphoKey(
        base = MorphoVariant("ال", "al", "al-definite"),
        position = mapOf(
            SyllablePosition.PREFIX to MorphoVariant("الـ", "al", "al-prefix-the")
        )
    )

    // ═══ Arabic Plural Suffixes ═══
    val UUN_SUFFIX = MorphoKey(
        base = MorphoVariant("ون", "uːn", "waw-nun"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("", "", "none"),
            Number.PLURAL to MorphoVariant("ون", "uːn", "masc-plural-nom")
        ),
        gender = mapOf(
            Gender.MASCULINE to MorphoVariant("ون", "uːn", "masc-plural")
        )
    )

    val AAT_SUFFIX = MorphoKey(
        base = MorphoVariant("ات", "aːt", "alif-ta"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("ة", "a", "ta-marbuta"),
            Number.PLURAL to MorphoVariant("ات", "aːt", "fem-plural")
        ),
        gender = mapOf(
            Gender.FEMININE to MorphoVariant("ات", "aːt", "fem-plural")
        )
    )

    // Full consonant set
    val ALIF = MorphoKey(base = MorphoVariant("ا", "", "alif"))
    val BA = MorphoKey(base = MorphoVariant("ب", "b", "ba"))
    val TA = MorphoKey(base = MorphoVariant("ت", "t", "ta"))
    val THA = MorphoKey(base = MorphoVariant("ث", "θ", "tha"))
    val JEEM = MorphoKey(base = MorphoVariant("ج", "dʒ", "jeem"))
    val HA = MorphoKey(base = MorphoVariant("ح", "ħ", "ha"))
    val KHA = MorphoKey(base = MorphoVariant("خ", "x", "kha"))
    val DAL = MorphoKey(base = MorphoVariant("د", "d", "dal"))
    val DHAL = MorphoKey(base = MorphoVariant("ذ", "ð", "dhal"))
    val RA = MorphoKey(base = MorphoVariant("ر", "r", "ra"))
    val ZAY = MorphoKey(base = MorphoVariant("ز", "z", "zay"))
    val SEEN = MorphoKey(base = MorphoVariant("س", "s", "seen"))
    val SHEEN = MorphoKey(base = MorphoVariant("ش", "ʃ", "sheen"))
    val SAD = MorphoKey(base = MorphoVariant("ص", "sˤ", "sad"))
    val DAD = MorphoKey(base = MorphoVariant("ض", "dˤ", "dad"))
    val TAA = MorphoKey(base = MorphoVariant("ط", "tˤ", "taa"))
    val ZAA = MorphoKey(base = MorphoVariant("ظ", "ðˤ", "zaa"))
    val AIN = MorphoKey(base = MorphoVariant("ع", "ʕ", "ain"))
    val GHAIN = MorphoKey(base = MorphoVariant("غ", "ɣ", "ghain"))
    val FA = MorphoKey(base = MorphoVariant("ف", "f", "fa"))
    val QAF = MorphoKey(base = MorphoVariant("ق", "q", "qaf"))
    val KAF = MorphoKey(base = MorphoVariant("ك", "k", "kaf"))
    val LAM = MorphoKey(base = MorphoVariant("ل", "l", "lam"))
    val MEEM = MorphoKey(base = MorphoVariant("م", "m", "meem"))
    val NOON = MorphoKey(base = MorphoVariant("ن", "n", "noon"))
    val HA_END = MorphoKey(base = MorphoVariant("ه", "h", "ha"))
    val WAW = MorphoKey(base = MorphoVariant("و", "w", "waw"))
    val YA = MorphoKey(base = MorphoVariant("ي", "j", "ya"))
    val HAMZA = MorphoKey(base = MorphoVariant("ء", "ʔ", "hamza"))
    val TA_MARBUTA = MorphoKey(
        base = MorphoVariant("ة", "a", "ta-marbuta"),
        gender = mapOf(
            Gender.FEMININE to MorphoVariant("ة", "a", "fem-marker")
        )
    )

    val all: List<MorphoKey> = listOf(
        ALIF, BA, TA, THA, JEEM, HA, KHA, DAL, DHAL, RA, ZAY,
        SEEN, SHEEN, SAD, DAD, TAA, ZAA, AIN, GHAIN, FA, QAF,
        KAF, LAM, MEEM, NOON, HA_END, WAW, YA, HAMZA, TA_MARBUTA
    )
}

/**
 * Romance language morphological keys (Spanish, French, Italian, Portuguese)
 */
object RomanceMorphoKeys {

    // ═══ Spanish gender suffixes ═══
    val SPANISH_MASC = MorphoKey(
        base = MorphoVariant("o", "o", "masc-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("o", "o", "masc-sing"),
            Number.PLURAL to MorphoVariant("os", "os", "masc-plural")
        )
    )

    val SPANISH_FEM = MorphoKey(
        base = MorphoVariant("a", "a", "fem-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("a", "a", "fem-sing"),
            Number.PLURAL to MorphoVariant("as", "as", "fem-plural")
        )
    )

    // ═══ French gender suffixes ═══
    val FRENCH_MASC = MorphoKey(
        base = MorphoVariant("", "", "masc-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("", "", "masc-sing"),
            Number.PLURAL to MorphoVariant("s", "s", "masc-plural")
        )
    )

    val FRENCH_FEM = MorphoKey(
        base = MorphoVariant("e", "ə", "fem-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("e", "ə", "fem-sing"),
            Number.PLURAL to MorphoVariant("es", "əz", "fem-plural")
        )
    )

    // ═══ Italian gender suffixes ═══
    val ITALIAN_MASC = MorphoKey(
        base = MorphoVariant("o", "o", "masc-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("o", "o", "masc-sing"),
            Number.PLURAL to MorphoVariant("i", "i", "masc-plural")
        )
    )

    val ITALIAN_FEM = MorphoKey(
        base = MorphoVariant("a", "a", "fem-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("a", "a", "fem-sing"),
            Number.PLURAL to MorphoVariant("e", "e", "fem-plural")
        )
    )

    // ═══ Portuguese gender suffixes ═══
    val PORTUGUESE_MASC = MorphoKey(
        base = MorphoVariant("o", "u", "masc-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("o", "u", "masc-sing"),
            Number.PLURAL to MorphoVariant("os", "uʃ", "masc-plural")
        )
    )

    val PORTUGUESE_FEM = MorphoKey(
        base = MorphoVariant("a", "ɐ", "fem-ending"),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("a", "ɐ", "fem-sing"),
            Number.PLURAL to MorphoVariant("as", "ɐʃ", "fem-plural")
        )
    )

    /**
     * Get gender suffixes by language
     */
    fun forLanguage(code: String): Pair<MorphoKey, MorphoKey>? = when (code) {
        "es", "es-419" -> SPANISH_MASC to SPANISH_FEM
        "fr" -> FRENCH_MASC to FRENCH_FEM
        "it" -> ITALIAN_MASC to ITALIAN_FEM
        "pt", "pt-br" -> PORTUGUESE_MASC to PORTUGUESE_FEM
        else -> null
    }
}

/**
 * German morphological keys with case/gender/number
 */
object GermanMorphoKeys {

    // German article declensions
    val DER = MorphoKey(
        base = MorphoVariant("der", "deːɐ̯", "der-masc-nom"),
        gender = mapOf(
            Gender.MASCULINE to MorphoVariant("der", "deːɐ̯", "der-masc"),
            Gender.FEMININE to MorphoVariant("die", "diː", "die-fem"),
            Gender.NEUTRAL to MorphoVariant("das", "das", "das-neut")
        ),
        number = mapOf(
            Number.SINGULAR to MorphoVariant("der", "deːɐ̯", "der-sing"),
            Number.PLURAL to MorphoVariant("die", "diː", "die-plural")
        )
    )

    // German plural suffixes
    val E_PLURAL = MorphoKey(
        base = MorphoVariant("e", "ə", "e-plural"),
        number = mapOf(
            Number.PLURAL to MorphoVariant("e", "ə", "plural-e")
        )
    )

    val EN_PLURAL = MorphoKey(
        base = MorphoVariant("en", "ən", "en-plural"),
        number = mapOf(
            Number.PLURAL to MorphoVariant("en", "ən", "plural-en")
        )
    )

    val ER_PLURAL = MorphoKey(
        base = MorphoVariant("er", "ɐ", "er-plural"),
        number = mapOf(
            Number.PLURAL to MorphoVariant("er", "ɐ", "plural-er")
        )
    )

    val S_PLURAL = MorphoKey(
        base = MorphoVariant("s", "s", "s-plural"),
        number = mapOf(
            Number.PLURAL to MorphoVariant("s", "s", "plural-s")
        )
    )
}

/**
 * Unified morphological keyboard layout
 */
data class MorphoLayout(
    val code: String,
    val name: String,
    val nativeName: String,
    val script: UniKeySyllable.Companion.Script,
    val keys: Map<String, MorphoKey>,
    val prefixes: List<MorphoKey> = emptyList(),
    val suffixes: Map<Gender, MorphoKey> = emptyMap(),
    val pluralSuffixes: Map<Gender, MorphoKey> = emptyMap()
) {
    /**
     * Get key by QWERTY position
     */
    operator fun get(qwerty: String): MorphoKey? = keys[qwerty]

    /**
     * Get plural suffix for gender
     */
    fun getPluralSuffix(gender: Gender): MorphoKey? = pluralSuffixes[gender]
}

/**
 * All morphological layouts
 */
object MorphoLayouts {

    val HE = MorphoLayout(
        code = "he",
        name = "Hebrew",
        nativeName = "עברית",
        script = UniKeySyllable.Companion.Script.HEBREW,
        keys = mapOf(
            "t" to HebrewMorphoKeys.ALEF,
            "c" to HebrewMorphoKeys.BET,
            "d" to HebrewMorphoKeys.GIMEL,
            "s" to HebrewMorphoKeys.DALET,
            "v" to HebrewMorphoKeys.HE,
            "u" to HebrewMorphoKeys.VAV,
            "z" to HebrewMorphoKeys.ZAYIN,
            "j" to HebrewMorphoKeys.CHET,
            "y" to HebrewMorphoKeys.TET,
            "h" to HebrewMorphoKeys.YOD,
            "f" to HebrewMorphoKeys.KAF,
            "k" to HebrewMorphoKeys.LAMED,
            "n" to HebrewMorphoKeys.MEM,
            "b" to HebrewMorphoKeys.NUN,
            "x" to HebrewMorphoKeys.SAMECH,
            "g" to HebrewMorphoKeys.AYIN,
            "p" to HebrewMorphoKeys.PE,
            "m" to HebrewMorphoKeys.TSADI,
            "e" to HebrewMorphoKeys.QOF,
            "r" to HebrewMorphoKeys.RESH,
            "a" to HebrewMorphoKeys.SHIN,
            "," to HebrewMorphoKeys.TAV
        ),
        prefixes = HebrewMorphoKeys.prefixes,
        pluralSuffixes = HebrewMorphoKeys.pluralSuffixes
    )

    val AR = MorphoLayout(
        code = "ar",
        name = "Arabic",
        nativeName = "العربية",
        script = UniKeySyllable.Companion.Script.ARABIC,
        keys = mapOf(
            "h" to ArabicMorphoKeys.ALIF,
            "f" to ArabicMorphoKeys.BA,
            "j" to ArabicMorphoKeys.TA,
            "e" to ArabicMorphoKeys.THA,
            "p" to ArabicMorphoKeys.HA,
            "o" to ArabicMorphoKeys.KHA,
            "v" to ArabicMorphoKeys.RA,
            "." to ArabicMorphoKeys.ZAY,
            "s" to ArabicMorphoKeys.SEEN,
            "a" to ArabicMorphoKeys.SHEEN,
            "w" to ArabicMorphoKeys.SAD,
            "q" to ArabicMorphoKeys.DAD,
            "u" to ArabicMorphoKeys.AIN,
            "y" to ArabicMorphoKeys.GHAIN,
            "t" to ArabicMorphoKeys.FA,
            "r" to ArabicMorphoKeys.QAF,
            "k" to ArabicMorphoKeys.KAF,
            "g" to ArabicMorphoKeys.LAM,
            "l" to ArabicMorphoKeys.MEEM,
            "," to ArabicMorphoKeys.WAW,
            "d" to ArabicMorphoKeys.YA,
            "x" to ArabicMorphoKeys.HAMZA
        ),
        prefixes = listOf(ArabicMorphoKeys.AL_PREFIX),
        pluralSuffixes = mapOf(
            Gender.MASCULINE to ArabicMorphoKeys.UUN_SUFFIX,
            Gender.FEMININE to ArabicMorphoKeys.AAT_SUFFIX
        )
    )

    /**
     * All layouts by language code
     */
    val layouts: Map<String, MorphoLayout> = mapOf(
        "he" to HE,
        "ar" to AR
    )

    /**
     * Get layout by code
     */
    operator fun get(code: String): MorphoLayout? = layouts[code.lowercase()]
}
