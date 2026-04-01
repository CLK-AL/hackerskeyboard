package org.pocketworkstation.pckeyboard.unikey

/**
 * IPA vowel quality indicators
 */
enum class IpaQuality {
    EXACT,       // Perfect match between Hebrew and English
    NEAR_EXACT,  // Very close match
    APPROX       // Approximation (warning needed)
}

/**
 * IPA vowels with Hebrew/English mappings
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
    // Front vowels - EXACT
    I("i", "close front", "ִי", "חִירִיק", listOf("ee", "ea", "ie"), IpaQuality.EXACT),
    E("e", "close-mid front", "ֵ", "צֵירֵי", listOf("ay", "ei", "a_e"), IpaQuality.EXACT),
    EPSILON("ɛ", "open-mid front", "ֶ", "סֶגוֹל", listOf("e"), IpaQuality.EXACT),

    // Front vowels - APPROXIMATE
    SMALL_I("ɪ", "near-close front", "ִ", "חִירִיק קצר", listOf("i"), IpaQuality.APPROX, "קירוב"),
    ASH("æ", "near-open front", "ֶ", "—", listOf("a"), IpaQuality.APPROX, "לא קיים בעברית"),

    // Central vowels
    SCHWA("ə", "schwa", "ְ", "שְׁוָא נָע", listOf("a", "u"), IpaQuality.EXACT),
    TURNED_A("ɐ", "near-open central", "ַ", "פַּתַח", listOf("u"), IpaQuality.NEAR_EXACT),

    // Back vowels - EXACT
    U("u", "close back", "ֻ", "שׁוּרוּק/קוּבּוּץ", listOf("oo", "u"), IpaQuality.EXACT),
    O("o", "close-mid back", "ֹ", "חוֹלָם", listOf("o", "oa"), IpaQuality.EXACT),
    A("a", "open", "ַ", "פַּתַח", listOf("a"), IpaQuality.EXACT),

    // Back vowels - APPROXIMATE
    SMALL_U("ʊ", "near-close back", "ֻ", "קוּבּוּץ קצר", listOf("oo"), IpaQuality.APPROX, "קירוב"),
    OPEN_O("ɔ", "open-mid back", "ָ", "קָמָץ קָטָן", listOf("aw", "au"), IpaQuality.APPROX),
    SCRIPT_A("ɑ", "open back", "ָ", "קָמָץ/פַּתַח", listOf("a", "ar"), IpaQuality.NEAR_EXACT),
    WEDGE("ʌ", "open-mid back unrounded", "ַ", "פַּתַח", listOf("u"), IpaQuality.APPROX, "קירוב"),

    // Diphthongs - NEAR-EXACT
    AI("aɪ", "diphthong", "ַי", "פַּתַח+יוֹד", listOf("i", "igh", "y"), IpaQuality.NEAR_EXACT),
    AU("aʊ", "diphthong", "ָאוּ", "פַּתַח+וָו", listOf("ou", "ow"), IpaQuality.NEAR_EXACT),
    OI("ɔɪ", "diphthong", "וֹי", "חוֹלָם+יוֹד", listOf("oi", "oy"), IpaQuality.NEAR_EXACT),
    EI("eɪ", "diphthong", "ֵי", "צֵירֵי+יוֹד", listOf("ay", "ai", "ei"), IpaQuality.NEAR_EXACT),
    OU("oʊ", "diphthong", "וֹ", "חוֹלָם+וָו", listOf("o", "ow", "oa"), IpaQuality.APPROX);

    companion object {
        private val byIpa = entries.associateBy { it.ipa }
        fun fromIpa(ipa: String): IpaVowel? = byIpa[ipa]
    }
}

/**
 * IPA consonants with Hebrew/English mappings
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
    // Stops
    P("p", "voiceless bilabial", "פּ", "p"),
    B("b", "voiced bilabial", "בּ", "b"),
    T("t", "voiceless alveolar", "ת/ט", "t"),
    D("d", "voiced alveolar", "ד", "d"),
    K("k", "voiceless velar", "כּ/ק", "k"),
    G("g", "voiced velar", "ג", "g"),
    GLOTTAL_STOP("ʔ", "glottal stop", "א/ע", "—"),

    // Fricatives
    F("f", "voiceless labiodental", "פ", "f"),
    V("v", "voiced labiodental", "ב/ו", "v"),
    S("s", "voiceless alveolar", "ס/שׂ", "s"),
    Z("z", "voiced alveolar", "ז", "z"),
    SH("ʃ", "voiceless postalveolar", "שׁ", "sh"),
    ZH("ʒ", "voiced postalveolar", "ז׳", "si/su", geresh = true),
    H("h", "voiceless glottal", "ה", "h"),

    // Geresh sounds (English→Hebrew)
    TH_VOICELESS("θ", "voiceless dental", "ת׳", "th", geresh = true, warning = "NO Hebrew equivalent!"),
    TH_VOICED("ð", "voiced dental", "ד׳", "th", geresh = true, warning = "NO Hebrew equivalent!"),
    CH("tʃ", "voiceless postalveolar affricate", "צ׳", "ch", geresh = true),
    J("dʒ", "voiced postalveolar affricate", "ג׳", "j", geresh = true),

    // Hebrew unique sounds
    X("x", "voiceless velar fricative", "כ/ח", "kh'", heUnique = true),
    PHARYNGEAL_H("ħ", "voiceless pharyngeal", "ח", "ḥ'", heUnique = true),
    PHARYNGEAL_AYIN("ʕ", "voiced pharyngeal", "ע", "'", heUnique = true),
    TS("ts", "voiceless alveolar affricate", "צ", "ts'", heUnique = true),
    UVULAR_R("ʁ", "uvular fricative", "ר", "r'", heUnique = true),

    // Nasals
    M("m", "bilabial nasal", "מ", "m"),
    N("n", "alveolar nasal", "נ", "n"),
    NG("ŋ", "velar nasal", "נג", "ng"),

    // Approximants
    L("l", "alveolar lateral", "ל", "l"),
    R("r", "alveolar approximant", "ר׳", "r"),
    Y("j", "palatal approximant", "י", "y"),
    W("w", "labio-velar", "ו׳", "w", geresh = true, warning = "W ≠ V!");

    companion object {
        private val byIpa = entries.associateBy { it.ipa }
        fun fromIpa(ipa: String): IpaConsonant? = byIpa[ipa]
    }
}

/**
 * Geresh marking rules for sounds that need special notation
 */
object Geresh {
    /**
     * Hebrew sounds that need ' in English transcription
     */
    val heToEn = mapOf(
        'צ' to GereshInfo("ts", "ts'", "צִפּוֹר→ts'ipor"),
        'ח' to GereshInfo("ħ", "ḥ'", "חַיִים→ḥ'ayim"),
        'כ' to GereshInfo("x", "kh'", "מֶלֶךְ→melekh'"),
        'ע' to GereshInfo("ʕ", "'", "עַיִן→'ayin"),
        'ר' to GereshInfo("ʁ", "r'", "רֹאשׁ→r'osh"),
        'ק' to GereshInfo("k", "k'", "קוֹל→k'ol")
    )

    /**
     * English sounds that need ׳ in Hebrew transcription
     */
    val enToHe = mapOf(
        "θ" to GereshInfo("th (voiceless)", "ת׳", "think→ת׳ִינק"),
        "ð" to GereshInfo("th (voiced)", "ד׳", "the→ד׳ָה"),
        "tʃ" to GereshInfo("ch", "צ׳", "chair→צ׳ֵיר"),
        "dʒ" to GereshInfo("j", "ג׳", "jam→ג׳ֶם"),
        "ʒ" to GereshInfo("zh", "ז׳", "vision→וִיז׳ָן"),
        "w" to GereshInfo("w", "ו׳", "water→ווֹטֶר")
    )

    fun needsGeresh(sound: String, direction: GereshDirection): Boolean {
        return when (direction) {
            GereshDirection.HE_TO_EN -> sound.firstOrNull()?.let { heToEn.containsKey(it) } ?: false
            GereshDirection.EN_TO_HE -> enToHe.containsKey(sound)
        }
    }
}

data class GereshInfo(
    val source: String,
    val target: String,
    val example: String
)

enum class GereshDirection {
    HE_TO_EN,
    EN_TO_HE
}
