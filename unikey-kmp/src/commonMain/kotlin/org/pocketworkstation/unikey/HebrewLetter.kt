package org.pocketworkstation.unikey

/**
 * Hebrew letters with phonetic information - Multiplatform
 */
enum class HebrewLetter(
    val letter: Char,
    val ipa: String,
    val ipaDagesh: String? = null,
    val displayName: String,
    val type: LetterType = LetterType.REGULAR,
    val en: String,
    val enDagesh: String? = null,
    val finalForm: Char? = null,
    val silentEnd: Boolean = false,
    val classical: String? = null
) {
    ALEF('\u05D0', "\u0294", displayName = "\u05D0\u05B8\u05DC\u05B6\u05E3", type = LetterType.GUTTURAL, en = "'", silentEnd = true),
    BET('\u05D1', "v", "b", "\u05D1\u05B5\u05BC\u05D9\u05EA", LetterType.BGDKPT, "v", "b"),
    GIMEL('\u05D2', "g", displayName = "\u05D2\u05B4\u05BC\u05D9\u05DE\u05B6\u05DC", type = LetterType.BGDKPT, en = "g", classical = "\u0263"),
    DALET('\u05D3', "d", displayName = "\u05D3\u05B8\u05BC\u05DC\u05B6\u05EA", type = LetterType.BGDKPT, en = "d", classical = "\u00F0"),
    HE('\u05D4', "h", displayName = "\u05D4\u05B5\u05D0", type = LetterType.GUTTURAL, en = "h", silentEnd = true),
    VAV('\u05D5', "v", displayName = "\u05D5\u05B8\u05D5", type = LetterType.WEAK, en = "v"),
    ZAYIN('\u05D6', "z", displayName = "\u05D6\u05B7\u05D9\u05B4\u05DF", en = "z"),
    CHET('\u05D7', "\u0127", displayName = "\u05D7\u05B5\u05D9\u05EA", type = LetterType.GUTTURAL, en = "\u1E25'"),
    TET('\u05D8', "t", displayName = "\u05D8\u05B5\u05D9\u05EA", type = LetterType.EMPHATIC, en = "t"),
    YOD('\u05D9', "j", displayName = "\u05D9\u05D5\u05B9\u05D3", type = LetterType.WEAK, en = "y"),
    KAF('\u05DB', "x", "k", "\u05DB\u05B7\u05BC\u05E3", LetterType.BGDKPT, "kh'", "k", '\u05DA'),
    LAMED('\u05DC', "l", displayName = "\u05DC\u05B8\u05DE\u05B6\u05D3", en = "l"),
    MEM('\u05DE', "m", displayName = "\u05DE\u05B5\u05DD", en = "m", finalForm = '\u05DD'),
    NUN('\u05E0', "n", displayName = "\u05E0\u05D5\u05BC\u05DF", en = "n", finalForm = '\u05DF'),
    SAMECH('\u05E1', "s", displayName = "\u05E1\u05B8\u05DE\u05B6\u05DA", en = "s"),
    AYIN('\u05E2', "\u0295", displayName = "\u05E2\u05B7\u05D9\u05B4\u05DF", type = LetterType.GUTTURAL, en = "'"),
    PE('\u05E4', "f", "p", "\u05E4\u05B5\u05BC\u05D0", LetterType.BGDKPT, "f", "p", '\u05E3'),
    TSADI('\u05E6', "ts", displayName = "\u05E6\u05B8\u05D3\u05B4\u05D9", type = LetterType.EMPHATIC, en = "ts'", finalForm = '\u05E5'),
    QOF('\u05E7', "k", displayName = "\u05E7\u05D5\u05B9\u05E3", type = LetterType.EMPHATIC, en = "k'"),
    RESH('\u05E8', "\u0281", displayName = "\u05E8\u05B5\u05D9\u05E9\u05C1", type = LetterType.GUTTURAL, en = "r'"),
    SHIN('\u05E9', "\u0283", displayName = "\u05E9\u05C1\u05B4\u05D9\u05DF/\u05E9\u05C2\u05B4\u05D9\u05DF", type = LetterType.SHIN, en = "sh"),
    TAV('\u05EA', "t", displayName = "\u05EA\u05B8\u05BC\u05D5", type = LetterType.BGDKPT, en = "t", classical = "\u03B8"),

    // Final letters
    KAF_SOFIT('\u05DA', "x", displayName = "\u05DB\u05B7\u05BC\u05E3 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "kh'"),
    MEM_SOFIT('\u05DD', "m", displayName = "\u05DE\u05B5\u05DD \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "m"),
    NUN_SOFIT('\u05DF', "n", displayName = "\u05E0\u05D5\u05BC\u05DF \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "n"),
    PE_SOFIT('\u05E3', "f", displayName = "\u05E4\u05B5\u05BC\u05D0 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "f"),
    TSADI_SOFIT('\u05E5', "ts", displayName = "\u05E6\u05B8\u05D3\u05B4\u05D9 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA", en = "ts'");

    val isFinalForm: Boolean get() = this in listOf(KAF_SOFIT, MEM_SOFIT, NUN_SOFIT, PE_SOFIT, TSADI_SOFIT)
    val isGuttural: Boolean get() = type == LetterType.GUTTURAL
    val isBgdkpt: Boolean get() = type == LetterType.BGDKPT

    fun getIpa(hasDagesh: Boolean = false): String = if (hasDagesh && ipaDagesh != null) ipaDagesh else ipa
    fun getEn(hasDagesh: Boolean = false): String = if (hasDagesh && enDagesh != null) enDagesh else en

    companion object {
        private val byLetter = entries.associateBy { it.letter }
        fun fromChar(c: Char): HebrewLetter? = byLetter[c]
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
