package org.pocketworkstation.unikey

/**
 * IPA vowel quality indicators
 */
enum class IpaQuality {
    EXACT,
    NEAR_EXACT,
    APPROX
}

/**
 * IPA vowels with Hebrew/English mappings - Multiplatform
 */
enum class IpaVowel(
    val ipa: String,
    val displayName: String,
    val heNikud: String,
    val heName: String,
    val enSpellings: List<String>,
    val quality: IpaQuality,
    val warning: String? = null
) {
    I("i", "close front", "\u05B4\u05D9", "\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7", listOf("ee", "ea", "ie"), IpaQuality.EXACT),
    E("e", "close-mid front", "\u05B5", "\u05E6\u05B5\u05D9\u05E8\u05B5\u05D9", listOf("ay", "ei", "a_e"), IpaQuality.EXACT),
    EPSILON("\u025B", "open-mid front", "\u05B6", "\u05E1\u05B6\u05D2\u05D5\u05B9\u05DC", listOf("e"), IpaQuality.EXACT),
    SMALL_I("\u026A", "near-close front", "\u05B4", "\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7 \u05E7\u05E6\u05E8", listOf("i"), IpaQuality.APPROX),
    ASH("\u00E6", "near-open front", "\u05B6", "\u2014", listOf("a"), IpaQuality.APPROX),
    SCHWA("\u0259", "schwa", "\u05B0", "\u05E9\u05B0\u05C1\u05D5\u05B8\u05D0 \u05E0\u05B8\u05E2", listOf("a", "u"), IpaQuality.EXACT),
    U("u", "close back", "\u05BB", "\u05E9\u05C1\u05D5\u05BC\u05E8\u05D5\u05BC\u05E7/\u05E7\u05BB\u05D1\u05BC\u05D5\u05BC\u05E5", listOf("oo", "u"), IpaQuality.EXACT),
    O("o", "close-mid back", "\u05B9", "\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD", listOf("o", "oa"), IpaQuality.EXACT),
    A("a", "open", "\u05B7", "\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7", listOf("a"), IpaQuality.EXACT),
    AI("a\u026A", "diphthong", "\u05B7\u05D9", "\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7+\u05D9\u05D5\u05B9\u05D3", listOf("i", "igh", "y"), IpaQuality.NEAR_EXACT),
    AU("a\u028A", "diphthong", "\u05B8\u05D0\u05D5\u05BC", "\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7+\u05D5\u05B8\u05D5", listOf("ou", "ow"), IpaQuality.NEAR_EXACT),
    OI("\u0254\u026A", "diphthong", "\u05D5\u05B9\u05D9", "\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD+\u05D9\u05D5\u05B9\u05D3", listOf("oi", "oy"), IpaQuality.NEAR_EXACT),
    EI("e\u026A", "diphthong", "\u05B5\u05D9", "\u05E6\u05B5\u05D9\u05E8\u05B5\u05D9+\u05D9\u05D5\u05B9\u05D3", listOf("ay", "ai", "ei"), IpaQuality.NEAR_EXACT);

    companion object {
        private val byIpa = entries.associateBy { it.ipa }
        fun fromIpa(ipa: String): IpaVowel? = byIpa[ipa]
    }
}

/**
 * IPA consonants with Hebrew/English mappings - Multiplatform
 */
enum class IpaConsonant(
    val ipa: String,
    val displayName: String,
    val he: String,
    val en: String,
    val geresh: Boolean = false,
    val heUnique: Boolean = false,
    val warning: String? = null
) {
    P("p", "voiceless bilabial", "\u05E4\u05BC", "p"),
    B("b", "voiced bilabial", "\u05D1\u05BC", "b"),
    T("t", "voiceless alveolar", "\u05EA/\u05D8", "t"),
    D("d", "voiced alveolar", "\u05D3", "d"),
    K("k", "voiceless velar", "\u05DB\u05BC/\u05E7", "k"),
    G("g", "voiced velar", "\u05D2", "g"),
    GLOTTAL_STOP("\u0294", "glottal stop", "\u05D0/\u05E2", "\u2014"),
    F("f", "voiceless labiodental", "\u05E4", "f"),
    V("v", "voiced labiodental", "\u05D1/\u05D5", "v"),
    S("s", "voiceless alveolar", "\u05E1/\u05E9\u05C2", "s"),
    Z("z", "voiced alveolar", "\u05D6", "z"),
    SH("\u0283", "voiceless postalveolar", "\u05E9\u05C1", "sh"),
    ZH("\u0292", "voiced postalveolar", "\u05D6\u05F3", "si/su", geresh = true),
    H("h", "voiceless glottal", "\u05D4", "h"),
    TH_VOICELESS("\u03B8", "voiceless dental", "\u05EA\u05F3", "th", geresh = true, warning = "NO Hebrew equivalent!"),
    TH_VOICED("\u00F0", "voiced dental", "\u05D3\u05F3", "th", geresh = true, warning = "NO Hebrew equivalent!"),
    CH("t\u0283", "voiceless postalveolar affricate", "\u05E6\u05F3", "ch", geresh = true),
    J("d\u0292", "voiced postalveolar affricate", "\u05D2\u05F3", "j", geresh = true),
    X("x", "voiceless velar fricative", "\u05DB/\u05D7", "kh'", heUnique = true),
    TS("ts", "voiceless alveolar affricate", "\u05E6", "ts'", heUnique = true),
    UVULAR_R("\u0281", "uvular fricative", "\u05E8", "r'", heUnique = true),
    M("m", "bilabial nasal", "\u05DE", "m"),
    N("n", "alveolar nasal", "\u05E0", "n"),
    NG("\u014B", "velar nasal", "\u05E0\u05D2", "ng"),
    L("l", "alveolar lateral", "\u05DC", "l"),
    R("r", "alveolar approximant", "\u05E8\u05F3", "r"),
    Y("j", "palatal approximant", "\u05D9", "y"),
    W("w", "labio-velar", "\u05D5\u05F3", "w", geresh = true, warning = "W \u2260 V!");

    companion object {
        private val byIpa = entries.associateBy { it.ipa }
        fun fromIpa(ipa: String): IpaConsonant? = byIpa[ipa]
    }
}
