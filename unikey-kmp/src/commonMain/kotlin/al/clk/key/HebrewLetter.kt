package al.clk.key

/**
 * Hebrew letters with phonetic information - Multiplatform
 * Implements ILayoutKey for keyboard layout integration
 */
enum class HebrewLetter(
    val letter: Char,
    override val ipa: String,
    val ipaDagesh: String? = null,
    override val displayName: String,
    val type: LetterType = LetterType.REGULAR,
    val en: String,
    val enDagesh: String? = null,
    val finalForm: Char? = null,
    val silentEnd: Boolean = false,
    val classical: String? = null,
    val qwerty: String = ""  // QWERTY key mapping
) : ILayoutKey {
    ALEF('\u05D0', "\u0294", displayName = "\u05D0\u05B8\u05DC\u05B6\u05E3", type = LetterType.GUTTURAL, en = "'", silentEnd = true, qwerty = "t"),
    BET('\u05D1', "v", "b", "\u05D1\u05B5\u05BC\u05D9\u05EA", LetterType.BGDKPT, "v", "b", qwerty = "c"),
    GIMEL('\u05D2', "g", displayName = "\u05D2\u05B4\u05BC\u05D9\u05DE\u05B6\u05DC", type = LetterType.BGDKPT, en = "g", classical = "\u0263", qwerty = "d"),
    DALET('\u05D3', "d", displayName = "\u05D3\u05B8\u05BC\u05DC\u05B6\u05EA", type = LetterType.BGDKPT, en = "d", classical = "\u00F0", qwerty = "s"),
    HE('\u05D4', "h", displayName = "\u05D4\u05B5\u05D0", type = LetterType.GUTTURAL, en = "h", silentEnd = true, qwerty = "v"),
    VAV('\u05D5', "v", displayName = "\u05D5\u05B8\u05D5", type = LetterType.WEAK, en = "v", qwerty = "u"),
    ZAYIN('\u05D6', "z", displayName = "\u05D6\u05B7\u05D9\u05B4\u05DF", en = "z", qwerty = "z"),
    CHET('\u05D7', "\u0127", displayName = "\u05D7\u05B5\u05D9\u05EA", type = LetterType.GUTTURAL, en = "\u1E25'", qwerty = "j"),
    TET('\u05D8', "t", displayName = "\u05D8\u05B5\u05D9\u05EA", type = LetterType.EMPHATIC, en = "t", qwerty = "y"),
    YOD('\u05D9', "j", displayName = "\u05D9\u05D5\u05B9\u05D3", type = LetterType.WEAK, en = "y", qwerty = "h"),
    KAF('\u05DB', "x", "k", "\u05DB\u05B7\u05BC\u05E3", LetterType.BGDKPT, "kh'", "k", '\u05DA', qwerty = "f"),
    LAMED('\u05DC', "l", displayName = "\u05DC\u05B8\u05DE\u05B6\u05D3", en = "l", qwerty = "k"),
    MEM('\u05DE', "m", displayName = "\u05DE\u05B5\u05DD", en = "m", finalForm = '\u05DD', qwerty = "n"),
    NUN('\u05E0', "n", displayName = "\u05E0\u05D5\u05BC\u05DF", en = "n", finalForm = '\u05DF', qwerty = "b"),
    SAMECH('\u05E1', "s", displayName = "\u05E1\u05B8\u05DE\u05B6\u05DA", en = "s", qwerty = "x"),
    AYIN('\u05E2', "\u0295", displayName = "\u05E2\u05B7\u05D9\u05B4\u05DF", type = LetterType.GUTTURAL, en = "'", qwerty = "g"),
    PE('\u05E4', "f", "p", "\u05E4\u05B5\u05BC\u05D0", LetterType.BGDKPT, "f", "p", '\u05E3', qwerty = "p"),
    TSADI('\u05E6', "ts", displayName = "\u05E6\u05B8\u05D3\u05B4\u05D9", type = LetterType.EMPHATIC, en = "ts'", finalForm = '\u05E5', qwerty = "m"),
    QOF('\u05E7', "k", displayName = "\u05E7\u05D5\u05B9\u05E3", type = LetterType.EMPHATIC, en = "k'", qwerty = "e"),
    RESH('\u05E8', "\u0281", displayName = "\u05E8\u05B5\u05D9\u05E9\u05C1", type = LetterType.GUTTURAL, en = "r'", qwerty = "r"),
    SHIN('\u05E9', "\u0283", displayName = "\u05E9\u05C1\u05B4\u05D9\u05DF/\u05E9\u05C2\u05B4\u05D9\u05DF", type = LetterType.SHIN, en = "sh", qwerty = "a"),
    TAV('\u05EA', "t", displayName = "\u05EA\u05B8\u05BC\u05D5", type = LetterType.BGDKPT, en = "t", classical = "\u03B8", qwerty = ","),

    // Final letters
    KAF_SOFIT('\u05DA', "x", displayName = "\u05DB\u05B7\u05BC\u05E3 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "kh'", qwerty = "l"),
    MEM_SOFIT('\u05DD', "m", displayName = "\u05DE\u05B5\u05DD \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "m", qwerty = "o"),
    NUN_SOFIT('\u05DF', "n", displayName = "\u05E0\u05D5\u05BC\u05DF \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "n", qwerty = "i"),
    PE_SOFIT('\u05E3', "f", displayName = "\u05E4\u05B5\u05BC\u05D0 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "f", qwerty = ";"),
    TSADI_SOFIT('\u05E5', "ts", displayName = "\u05E6\u05B8\u05D3\u05B4\u05D9 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "ts'", qwerty = ".");

    override val char: String get() = letter.toString()

    /** Shift shows letter with dagesh (if applicable) */
    override val shiftKey: ILayoutKey? get() = if (ipaDagesh != null && ipaDagesh != ipa) {
        SimpleKey("$letter\u05BC", ipaDagesh, "$displayName-dagesh")
    } else null

    val isFinalForm: Boolean get() = this in listOf(KAF_SOFIT, MEM_SOFIT, NUN_SOFIT, PE_SOFIT, TSADI_SOFIT)
    val isGuttural: Boolean get() = type == LetterType.GUTTURAL
    val isBgdkpt: Boolean get() = type == LetterType.BGDKPT

    /** ASCII-friendly IPA for color hashing (gutturals silent, sh for shin, etc.) */
    val colorIpa: String get() = when (ipa) {
        "\u0294", "\u0295" -> ""      // ʔ (alef), ʕ (ayin) -> silent
        "\u0283" -> "sh"               // ʃ (shin) -> sh
        "\u0127" -> "x"                // ħ (chet) -> x
        "\u0281" -> "r"                // ʁ (resh) -> r
        else -> ipa
    }

    /** ASCII-friendly dagesh IPA for color hashing */
    val colorIpaDagesh: String? get() = ipaDagesh

    fun getIpa(hasDagesh: Boolean = false): String = if (hasDagesh && ipaDagesh != null) ipaDagesh else ipa
    fun getEn(hasDagesh: Boolean = false): String = if (hasDagesh && enDagesh != null) enDagesh else en

    companion object {
        private val byLetter = entries.associateBy { it.letter }
        private val byQwerty = entries.associateBy { it.qwerty }
        fun fromChar(c: Char): HebrewLetter? = byLetter[c]
        fun fromQwerty(key: String): HebrewLetter? = byQwerty[key]
        fun getFinalForm(letter: HebrewLetter): HebrewLetter? = letter.finalForm?.let { fromChar(it) }
    }
}

enum class LetterType {
    REGULAR,
    GUTTURAL,
    BGDKPT,
    WEAK,
    EMPHATIC,
    SHIN
}
