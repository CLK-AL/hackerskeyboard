package org.pocketworkstation.pckeyboard.unikey

/**
 * Hebrew letters with phonetic information
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
    ALEF('א', "ʔ", displayName = "אָלֶף", type = LetterType.GUTTURAL, en = "'", silentEnd = true),
    BET('ב', "v", "b", "בֵּית", LetterType.BGDKPT, "v", "b"),
    GIMEL('ג', "g", displayName = "גִּימֶל", type = LetterType.BGDKPT, en = "g", classical = "ɣ"),
    DALET('ד', "d", displayName = "דָּלֶת", type = LetterType.BGDKPT, en = "d", classical = "ð"),
    HE('ה', "h", displayName = "הֵא", type = LetterType.GUTTURAL, en = "h", silentEnd = true),
    VAV('ו', "v", displayName = "וָו", type = LetterType.WEAK, en = "v"),
    ZAYIN('ז', "z", displayName = "זַיִן", en = "z"),
    CHET('ח', "ħ", displayName = "חֵית", type = LetterType.GUTTURAL, en = "ḥ'"),
    TET('ט', "t", displayName = "טֵית", type = LetterType.EMPHATIC, en = "t"),
    YOD('י', "j", displayName = "יוֹד", type = LetterType.WEAK, en = "y"),
    KAF('כ', "x", "k", "כַּף", LetterType.BGDKPT, "kh'", "k", 'ך'),
    LAMED('ל', "l", displayName = "לָמֶד", en = "l"),
    MEM('מ', "m", displayName = "מֵם", en = "m", finalForm = 'ם'),
    NUN('נ', "n", displayName = "נוּן", en = "n", finalForm = 'ן'),
    SAMECH('ס', "s", displayName = "סָמֶך", en = "s"),
    AYIN('ע', "ʕ", displayName = "עַיִן", type = LetterType.GUTTURAL, en = "'"),
    PE('פ', "f", "p", "פֵּא", LetterType.BGDKPT, "f", "p", 'ף'),
    TSADI('צ', "ts", displayName = "צָדִי", type = LetterType.EMPHATIC, en = "ts'", finalForm = 'ץ'),
    QOF('ק', "k", displayName = "קוֹף", type = LetterType.EMPHATIC, en = "k'"),
    RESH('ר', "ʁ", displayName = "רֵישׁ", type = LetterType.GUTTURAL, en = "r'"),
    SHIN('ש', "ʃ", displayName = "שִׁין/שִׂין", type = LetterType.SHIN, en = "sh"),
    TAV('ת', "t", displayName = "תָּו", type = LetterType.BGDKPT, en = "t", classical = "θ"),

    // Final letters - סופיות
    KAF_SOFIT('ך', "x", displayName = "כַּף סוֹפִית", en = "kh'"),
    MEM_SOFIT('ם', "m", displayName = "מֵם סוֹפִית", en = "m"),
    NUN_SOFIT('ן', "n", displayName = "נוּן סוֹפִית", en = "n"),
    PE_SOFIT('ף', "f", displayName = "פֵּא סוֹפִית", en = "f"),
    TSADI_SOFIT('ץ', "ts", displayName = "צָדִי סוֹפִית", en = "ts'");

    val isFinal: Boolean get() = this in listOf(KAF_SOFIT, MEM_SOFIT, NUN_SOFIT, PE_SOFIT, TSADI_SOFIT)
    val isGuttural: Boolean get() = type == LetterType.GUTTURAL
    val isBgdkpt: Boolean get() = type == LetterType.BGDKPT

    /**
     * Get IPA with optional dagesh
     */
    fun getIpa(hasDagesh: Boolean = false): String {
        return if (hasDagesh && ipaDagesh != null) ipaDagesh else ipa
    }

    /**
     * Get English transcription with optional dagesh
     */
    fun getEn(hasDagesh: Boolean = false): String {
        return if (hasDagesh && enDagesh != null) enDagesh else en
    }

    companion object {
        private val byLetter = entries.associateBy { it.letter }

        fun fromChar(c: Char): HebrewLetter? = byLetter[c]

        fun getFinalForm(letter: HebrewLetter): HebrewLetter? {
            return letter.finalForm?.let { fromChar(it) }
        }

        fun getBaseForm(finalLetter: HebrewLetter): HebrewLetter? {
            return when (finalLetter) {
                KAF_SOFIT -> KAF
                MEM_SOFIT -> MEM
                NUN_SOFIT -> NUN
                PE_SOFIT -> PE
                TSADI_SOFIT -> TSADI
                else -> null
            }
        }
    }
}

/**
 * Letter type categories
 */
enum class LetterType {
    REGULAR,
    GUTTURAL,  // א,ה,ח,ע,ר - use hataf vowels
    BGDKPT,    // בג"ד כפ"ת - affected by dagesh
    WEAK,      // ו,י - can be vowel markers
    EMPHATIC,  // ט,צ,ק - emphatic sounds
    SHIN       // ש - shin/sin distinction
}
