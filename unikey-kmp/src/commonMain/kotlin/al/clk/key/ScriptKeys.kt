package al.clk.key

// ═══════════════════════════════════════════════════════════════════════════════
// Hebrew: Use HebrewLetter (HebrewLetter.kt) and NikudVowel (MorphoEnum.kt)
// Arabic: Use ArabicLetter (MorphoEnum.kt)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Greek letters
 */
enum class GreekKey(
    override val char: String,
    val upper: String,
    override val ipa: String,
    override val displayName: String,
    val qwerty: String
) : ILayoutKey {
    ALPHA("α", "Α", "a", "alpha", "a"),
    BETA("β", "Β", "v", "beta", "b"),
    GAMMA("γ", "Γ", "ɣ", "gamma", "g"),
    DELTA("δ", "Δ", "ð", "delta", "d"),
    EPSILON("ε", "Ε", "e", "epsilon", "e"),
    ZETA("ζ", "Ζ", "z", "zeta", "z"),
    ETA("η", "Η", "i", "eta", "h"),
    THETA("θ", "Θ", "θ", "theta", "u"),
    IOTA("ι", "Ι", "i", "iota", "i"),
    KAPPA("κ", "Κ", "k", "kappa", "k"),
    LAMBDA("λ", "Λ", "l", "lambda", "l"),
    MU("μ", "Μ", "m", "mu", "m"),
    NU("ν", "Ν", "n", "nu", "n"),
    XI("ξ", "Ξ", "ks", "xi", "j"),
    OMICRON("ο", "Ο", "o", "omicron", "o"),
    PI("π", "Π", "p", "pi", "p"),
    RHO("ρ", "Ρ", "r", "rho", "r"),
    SIGMA("σ", "Σ", "s", "sigma", "s"),
    SIGMA_FINAL("ς", "Σ", "s", "sigma-final", "w"),
    TAU("τ", "Τ", "t", "tau", "t"),
    UPSILON("υ", "Υ", "i", "upsilon", "y"),
    PHI("φ", "Φ", "f", "phi", "f"),
    CHI("χ", "Χ", "x", "chi", "x"),
    PSI("ψ", "Ψ", "ps", "psi", "c"),
    OMEGA("ω", "Ω", "o", "omega", "v");

    companion object {
        private val byQwerty = entries.associateBy { it.qwerty }
        fun fromQwerty(key: String): GreekKey? = byQwerty[key]
    }
}

/**
 * Cyrillic (Russian) letters
 */
enum class CyrillicKey(
    override val char: String,
    val upper: String,
    override val ipa: String,
    override val displayName: String,
    val qwerty: String
) : ILayoutKey {
    A("а", "А", "a", "a", "f"),
    BE("б", "Б", "b", "be", ","),
    VE("в", "В", "v", "ve", "d"),
    GE("г", "Г", "ɡ", "ge", "u"),
    DE("д", "Д", "d", "de", "l"),
    YE("е", "Е", "je", "ye", "t"),
    YO("ё", "Ё", "jo", "yo", "`"),
    ZHE("ж", "Ж", "ʒ", "zhe", ";"),
    ZE("з", "З", "z", "ze", "p"),
    I("и", "И", "i", "i", "b"),
    SHORT_I("й", "Й", "j", "short-i", "q"),
    KA("к", "К", "k", "ka", "r"),
    EL("л", "Л", "l", "el", "k"),
    EM("м", "М", "m", "em", "v"),
    EN("н", "Н", "n", "en", "y"),
    O("о", "О", "o", "o", "j"),
    PE("п", "П", "p", "pe", "g"),
    ER("р", "Р", "r", "er", "h"),
    ES("с", "С", "s", "es", "c"),
    TE("т", "Т", "t", "te", "n"),
    U("у", "У", "u", "u", "e"),
    EF("ф", "Ф", "f", "ef", "a"),
    HA("х", "Х", "x", "ha", "["),
    TSE("ц", "Ц", "ts", "tse", "w"),
    CHE("ч", "Ч", "tʃ", "che", "x"),
    SHA("ш", "Ш", "ʃ", "sha", "i"),
    SHCHA("щ", "Щ", "ʃtʃ", "shcha", "o"),
    HARD_SIGN("ъ", "Ъ", "", "hard-sign", "]"),
    YERU("ы", "Ы", "ɨ", "yeru", "s"),
    SOFT_SIGN("ь", "Ь", "", "soft-sign", "m"),
    E("э", "Э", "e", "e", "'"),
    YU("ю", "Ю", "ju", "yu", "."),
    YA("я", "Я", "ja", "ya", "z");

    companion object {
        private val byQwerty = entries.associateBy { it.qwerty }
        private val byChar = entries.associateBy { it.char.firstOrNull() }
        fun fromQwerty(key: String): CyrillicKey? = byQwerty[key]
        fun fromChar(c: Char): CyrillicKey? = byChar[c]
    }
}

/**
 * Korean Hangul Jamo (for constructing syllables)
 */
enum class HangulInitial(
    override val char: String,
    override val ipa: String,  // IPA when in final position (for completeness)
    override val displayName: String,
    val code: Int,
    val initialIpa: String = ipa  // IPA when in initial position (silent ㅇ)
) : ILayoutKey {
    GIYEOK("ㄱ", "k", "giyeok", 0, "ɡ"),
    SSANG_GIYEOK("ㄲ", "k", "ssang-giyeok", 1),
    NIEUN("ㄴ", "n", "nieun", 2),
    DIGEUT("ㄷ", "t", "digeut", 3, "d"),
    SSANG_DIGEUT("ㄸ", "t", "ssang-digeut", 4),
    RIEUL("ㄹ", "l", "rieul", 5, "r"),
    MIEUM("ㅁ", "m", "mieum", 6),
    BIEUP("ㅂ", "p", "bieup", 7, "b"),
    SSANG_BIEUP("ㅃ", "p", "ssang-bieup", 8),
    SIOT("ㅅ", "s", "siot", 9),
    SSANG_SIOT("ㅆ", "s", "ssang-siot", 10),
    IEUNG("ㅇ", "ŋ", "ieung", 11, ""),  // silent in initial position
    JIEUT("ㅈ", "t", "jieut", 12, "dʒ"),
    SSANG_JIEUT("ㅉ", "t", "ssang-jieut", 13, "tʃ"),
    CHIEUT("ㅊ", "t", "chieut", 14, "tʃʰ"),
    KIEUK("ㅋ", "k", "kieuk", 15, "kʰ"),
    TIEUT("ㅌ", "t", "tieut", 16, "tʰ"),
    PIEUP("ㅍ", "p", "pieup", 17, "pʰ"),
    HIEUT("ㅎ", "h", "hieut", 18);

    companion object {
        private val byCode = entries.associateBy { it.code }
        fun fromCode(code: Int): HangulInitial? = byCode[code]
    }
}

enum class HangulMedial(
    override val char: String,
    override val ipa: String,
    override val displayName: String,
    val code: Int
) : ILayoutKey {
    A("ㅏ", "a", "a", 0),
    AE("ㅐ", "ɛ", "ae", 1),
    YA("ㅑ", "ja", "ya", 2),
    YAE("ㅒ", "jɛ", "yae", 3),
    EO("ㅓ", "ʌ", "eo", 4),
    E("ㅔ", "e", "e", 5),
    YEO("ㅕ", "jʌ", "yeo", 6),
    YE("ㅖ", "je", "ye", 7),
    O("ㅗ", "o", "o", 8),
    WA("ㅘ", "wa", "wa", 9),
    WAE("ㅙ", "wɛ", "wae", 10),
    OE("ㅚ", "we", "oe", 11),
    YO("ㅛ", "jo", "yo", 12),
    U("ㅜ", "u", "u", 13),
    WEO("ㅝ", "wʌ", "weo", 14),
    WE("ㅞ", "we", "we", 15),
    WI("ㅟ", "wi", "wi", 16),
    YU("ㅠ", "ju", "yu", 17),
    EU("ㅡ", "ɨ", "eu", 18),
    UI("ㅢ", "ɰi", "ui", 19),
    I("ㅣ", "i", "i", 20);

    companion object {
        private val byCode = entries.associateBy { it.code }
        fun fromCode(code: Int): HangulMedial? = byCode[code]
    }
}

/**
 * Korean Hangul Final Jamo (종성)
 * Code 0 means no final consonant
 */
enum class HangulFinal(
    override val char: String,
    override val ipa: String,
    override val displayName: String,
    val code: Int
) : ILayoutKey {
    NONE("", "", "none", 0),
    GIYEOK("ㄱ", "k", "giyeok", 1),
    SSANG_GIYEOK("ㄲ", "k", "ssang-giyeok", 2),
    GIYEOK_SIOT("ㄳ", "k", "giyeok-siot", 3),
    NIEUN("ㄴ", "n", "nieun", 4),
    NIEUN_JIEUT("ㄵ", "n", "nieun-jieut", 5),
    NIEUN_HIEUT("ㄶ", "n", "nieun-hieut", 6),
    DIGEUT("ㄷ", "t", "digeut", 7),
    RIEUL("ㄹ", "l", "rieul", 8),
    RIEUL_GIYEOK("ㄺ", "k", "rieul-giyeok", 9),
    RIEUL_MIEUM("ㄻ", "m", "rieul-mieum", 10),
    RIEUL_BIEUP("ㄼ", "p", "rieul-bieup", 11),
    RIEUL_SIOT("ㄽ", "l", "rieul-siot", 12),
    RIEUL_TIEUT("ㄾ", "l", "rieul-tieut", 13),
    RIEUL_PIEUP("ㄿ", "l", "rieul-pieup", 14),
    RIEUL_HIEUT("ㅀ", "p", "rieul-hieut", 15),
    MIEUM("ㅁ", "m", "mieum", 16),
    BIEUP("ㅂ", "p", "bieup", 17),
    BIEUP_SIOT("ㅄ", "p", "bieup-siot", 18),
    SIOT("ㅅ", "t", "siot", 19),
    SSANG_SIOT("ㅆ", "t", "ssang-siot", 20),
    IEUNG("ㅇ", "ŋ", "ieung", 21),
    JIEUT("ㅈ", "t", "jieut", 22),
    CHIEUT("ㅊ", "t", "chieut", 23),
    KIEUK("ㅋ", "k", "kieuk", 24),
    TIEUT("ㅌ", "t", "tieut", 25),
    PIEUP("ㅍ", "p", "pieup", 26),
    HIEUT("ㅎ", "t", "hieut", 27);

    companion object {
        private val byCode = entries.associateBy { it.code }
        fun fromCode(code: Int): HangulFinal? = byCode[code]
    }
}

/**
 * Japanese Hiragana
 */
enum class HiraganaKey(
    override val char: String,
    val katakana: String,
    override val ipa: String,
    override val displayName: String,
    val romaji: String
) : ILayoutKey {
    A("あ", "ア", "a", "a", "a"),
    I("い", "イ", "i", "i", "i"),
    U("う", "ウ", "ɯ", "u", "u"),
    E("え", "エ", "e", "e", "e"),
    O("お", "オ", "o", "o", "o"),
    KA("か", "カ", "ka", "ka", "k"),
    KI("き", "キ", "ki", "ki", "ki"),
    KU("く", "ク", "kɯ", "ku", "ku"),
    KE("け", "ケ", "ke", "ke", "ke"),
    KO("こ", "コ", "ko", "ko", "ko"),
    SA("さ", "サ", "sa", "sa", "s"),
    SHI("し", "シ", "ɕi", "shi", "si"),
    SU("す", "ス", "sɯ", "su", "su"),
    SE("せ", "セ", "se", "se", "se"),
    SO("そ", "ソ", "so", "so", "so"),
    TA("た", "タ", "ta", "ta", "t"),
    CHI("ち", "チ", "tɕi", "chi", "ti"),
    TSU("つ", "ツ", "tsɯ", "tsu", "tu"),
    TE("て", "テ", "te", "te", "te"),
    TO("と", "ト", "to", "to", "to"),
    NA("な", "ナ", "na", "na", "n"),
    NI("に", "ニ", "ɲi", "ni", "ni"),
    NU("ぬ", "ヌ", "nɯ", "nu", "nu"),
    NE("ね", "ネ", "ne", "ne", "ne"),
    NO("の", "ノ", "no", "no", "no"),
    HA("は", "ハ", "ha", "ha", "h"),
    HI("ひ", "ヒ", "çi", "hi", "hi"),
    FU("ふ", "フ", "ɸɯ", "fu", "hu"),
    HE("へ", "ヘ", "he", "he", "he"),
    HO("ほ", "ホ", "ho", "ho", "ho"),
    MA("ま", "マ", "ma", "ma", "m"),
    MI("み", "ミ", "mi", "mi", "mi"),
    MU("む", "ム", "mɯ", "mu", "mu"),
    ME("め", "メ", "me", "me", "me"),
    MO("も", "モ", "mo", "mo", "mo"),
    YA("や", "ヤ", "ja", "ya", "y"),
    YU("ゆ", "ユ", "jɯ", "yu", "yu"),
    YO("よ", "ヨ", "jo", "yo", "yo"),
    RA("ら", "ラ", "ɾa", "ra", "r"),
    RI("り", "リ", "ɾi", "ri", "ri"),
    RU("る", "ル", "ɾɯ", "ru", "ru"),
    RE("れ", "レ", "ɾe", "re", "re"),
    RO("ろ", "ロ", "ɾo", "ro", "ro"),
    WA("わ", "ワ", "wa", "wa", "w"),
    WO("を", "ヲ", "o", "wo", "wo"),
    N("ん", "ン", "n", "n", "nn");

    companion object {
        private val byRomaji = entries.associateBy { it.romaji }
        private val byChar = entries.associateBy { it.char.firstOrNull() }
        private val byKatakana = entries.associateBy { it.katakana.firstOrNull() }
        fun fromRomaji(r: String): HiraganaKey? = byRomaji[r]
        fun fromChar(c: Char): HiraganaKey? = byChar[c] ?: byKatakana[c]
    }
}

/**
 * Devanagari (Hindi) consonants
 */
enum class DevanagariKey(
    override val char: String,
    override val ipa: String,
    override val displayName: String,
    val isVowel: Boolean = false
) : ILayoutKey {
    // Vowels
    A("अ", "ə", "a", true),
    AA("आ", "aː", "aa", true),
    I("इ", "ɪ", "i", true),
    II("ई", "iː", "ii", true),
    U("उ", "ʊ", "u", true),
    UU("ऊ", "uː", "uu", true),
    E("ए", "eː", "e", true),
    AI("ऐ", "ɛː", "ai", true),
    O("ओ", "oː", "o", true),
    AU("औ", "ɔː", "au", true),

    // Consonants
    KA("क", "k", "ka"),
    KHA("ख", "kʰ", "kha"),
    GA("ग", "ɡ", "ga"),
    GHA("घ", "ɡʱ", "gha"),
    NGA("ङ", "ŋ", "nga"),
    CHA("च", "tʃ", "cha"),
    CHHA("छ", "tʃʰ", "chha"),
    JA("ज", "dʒ", "ja"),
    JHA("झ", "dʒʱ", "jha"),
    NYA("ञ", "ɲ", "nya"),
    TA("ट", "ʈ", "ta"),
    THA("ठ", "ʈʰ", "tha"),
    DA("ड", "ɖ", "da"),
    DHA("ढ", "ɖʱ", "dha"),
    NA_R("ण", "ɳ", "na-retroflex"),
    TA_D("त", "t", "ta-dental"),
    THA_D("थ", "tʰ", "tha-dental"),
    DA_D("द", "d", "da-dental"),
    DHA_D("ध", "dʱ", "dha-dental"),
    NA("न", "n", "na"),
    PA("प", "p", "pa"),
    PHA("फ", "pʰ", "pha"),
    BA("ब", "b", "ba"),
    BHA("भ", "bʱ", "bha"),
    MA("म", "m", "ma"),
    YA("य", "j", "ya"),
    RA("र", "r", "ra"),
    LA("ल", "l", "la"),
    VA("व", "ʋ", "va"),
    SHA("श", "ʃ", "sha"),
    SHA_R("ष", "ʂ", "sha-retroflex"),
    SA("स", "s", "sa"),
    HA("ह", "ɦ", "ha");

    companion object {
        val consonants = entries.filter { !it.isVowel }
        val vowels = entries.filter { it.isVowel }
        private val byChar = entries.associateBy { it.char.firstOrNull() }
        // Matra (vowel sign) to IPA mapping
        private val matraIpa = mapOf(
            'ा' to "aː", 'ि' to "ɪ", 'ी' to "iː",
            'ु' to "ʊ", 'ू' to "uː", 'े' to "eː",
            'ै' to "ɛː", 'ो' to "oː", 'ौ' to "ɔː"
        )
        fun fromChar(c: Char): DevanagariKey? = byChar[c]
        fun matraToIpa(c: Char): String? = matraIpa[c]
        fun vowelIpa(c: Char): String = byChar[c]?.ipa ?: matraIpa[c] ?: "ə"
    }
}

/**
 * Latin alphabet with IPA
 */
enum class LatinKey(
    override val char: String,
    val upper: String,
    override val ipa: String,
    override val displayName: String
) : ILayoutKey {
    A("a", "A", "a", "a"),
    B("b", "B", "b", "b"),
    C("c", "C", "k", "c"),
    D("d", "D", "d", "d"),
    E("e", "E", "e", "e"),
    F("f", "F", "f", "f"),
    G("g", "G", "ɡ", "g"),
    H("h", "H", "h", "h"),
    I("i", "I", "i", "i"),
    J("j", "J", "dʒ", "j"),
    K("k", "K", "k", "k"),
    L("l", "L", "l", "l"),
    M("m", "M", "m", "m"),
    N("n", "N", "n", "n"),
    O("o", "O", "o", "o"),
    P("p", "P", "p", "p"),
    Q("q", "Q", "k", "q"),
    R("r", "R", "r", "r"),
    S("s", "S", "s", "s"),
    T("t", "T", "t", "t"),
    U("u", "U", "u", "u"),
    V("v", "V", "v", "v"),
    W("w", "W", "w", "w"),
    X("x", "X", "ks", "x"),
    Y("y", "Y", "j", "y"),
    Z("z", "Z", "z", "z");

    companion object {
        private val byChar = entries.associateBy { it.char }
        fun fromChar(c: String): LatinKey? = byChar[c.lowercase()]
    }
}
