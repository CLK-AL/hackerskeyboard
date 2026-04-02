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

    /** Is this a vowel? */
    val isVowel: Boolean get() = ipa in listOf("a", "e", "i", "o")

    /** Shift key returns uppercase */
    override val shiftKey: ILayoutKey get() = SimpleKey(upper, ipa, displayName.replaceFirstChar { it.uppercase() })

    companion object {
        private val byQwerty = entries.associateBy { it.qwerty }
        private val byChar = entries.associateBy { it.char.firstOrNull() }
        private val byIpa = entries.groupBy { it.ipa }
        // Accented vowels mapped to base forms
        private val accentedToBase = mapOf(
            'ά' to 'α', 'έ' to 'ε', 'ή' to 'η',
            'ί' to 'ι', 'ϊ' to 'ι', 'ΐ' to 'ι',
            'ό' to 'ο', 'ύ' to 'υ', 'ϋ' to 'υ', 'ΰ' to 'υ',
            'ώ' to 'ω'
        )

        /** Keyboard layout keys by QWERTY position */
        val keys: Map<String, ILayoutKey> = entries.associate { it.qwerty to it as ILayoutKey } +
            mapOf("q" to SimpleKey(";", "", "semicolon", SimpleKey(":", "", "colon")))

        fun fromQwerty(key: String): GreekKey? = byQwerty[key]
        fun fromChar(c: Char): GreekKey? = byChar[c] ?: byChar[accentedToBase[c]]
        fun fromIpa(ipa: String): List<GreekKey> = byIpa[ipa] ?: emptyList()
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

    /** Shift key returns uppercase */
    override val shiftKey: ILayoutKey get() = SimpleKey(upper, ipa, displayName.replaceFirstChar { it.uppercase() })

    companion object {
        private val byQwerty = entries.associateBy { it.qwerty }
        private val byChar = entries.associateBy { it.char.firstOrNull() }
        private val byIpa = entries.groupBy { it.ipa }

        /** Keyboard layout keys by QWERTY position */
        val keys: Map<String, ILayoutKey> = entries.associate { it.qwerty to it as ILayoutKey }

        fun fromQwerty(key: String): CyrillicKey? = byQwerty[key]
        fun fromChar(c: Char): CyrillicKey? = byChar[c]
        fun fromIpa(ipa: String): List<CyrillicKey> = byIpa[ipa] ?: emptyList()
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

    /** Shift key returns katakana */
    override val shiftKey: ILayoutKey get() = SimpleKey(katakana, ipa, "$displayName-katakana")

    companion object {
        private val byRomaji = entries.associateBy { it.romaji }
        private val byChar = entries.associateBy { it.char.firstOrNull() }
        private val byKatakana = entries.associateBy { it.katakana.firstOrNull() }
        private val byIpa = entries.groupBy { it.ipa }

        /** Keyboard layout keys by consonant romaji */
        val keys: Map<String, ILayoutKey> = entries
            .filter { it.romaji.length == 1 || it.romaji in listOf("ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa") }
            .associate {
                val key = when (it.romaji) {
                    "a" -> "a"; "i" -> "i"; "u" -> "u"; "e" -> "e"; "o" -> "o"
                    "ka" -> "k"; "sa" -> "s"; "ta" -> "t"; "na" -> "n"
                    "ha" -> "h"; "ma" -> "m"; "ya" -> "y"; "ra" -> "r"; "wa" -> "w"
                    else -> it.romaji
                }
                key to it as ILayoutKey
            }

        fun fromRomaji(r: String): HiraganaKey? = byRomaji[r]
        fun fromChar(c: Char): HiraganaKey? = byChar[c] ?: byKatakana[c]
        fun fromIpa(ipa: String): List<HiraganaKey> = byIpa[ipa] ?: emptyList()
    }
}

/**
 * Devanagari (Hindi) letters - vowels include their matra (vowel sign) form
 */
enum class DevanagariKey(
    override val char: String,
    override val ipa: String,
    override val displayName: String,
    val isVowel: Boolean = false,
    val matra: Char? = null  // vowel sign form (null for consonants and अ)
) : ILayoutKey {
    // Vowels with their matra forms
    A("अ", "ə", "a", true),  // no matra - inherent vowel
    AA("आ", "aː", "aa", true, 'ा'),
    I("इ", "ɪ", "i", true, 'ि'),
    II("ई", "iː", "ii", true, 'ी'),
    U("उ", "ʊ", "u", true, 'ु'),
    UU("ऊ", "uː", "uu", true, 'ू'),
    E("ए", "eː", "e", true, 'े'),
    AI("ऐ", "ɛː", "ai", true, 'ै'),
    O("ओ", "oː", "o", true, 'ो'),
    AU("औ", "ɔː", "au", true, 'ौ'),

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
        // Matra lookup derived from vowel entries
        private val byMatra = entries.filter { it.matra != null }.associateBy { it.matra }
        fun fromChar(c: Char): DevanagariKey? = byChar[c]
        fun fromMatra(c: Char): DevanagariKey? = byMatra[c]
        fun matraToIpa(c: Char): String? = byMatra[c]?.ipa
        fun vowelIpa(c: Char): String = byChar[c]?.ipa ?: byMatra[c]?.ipa ?: "ə"
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

    /** Shift key returns uppercase */
    override val shiftKey: ILayoutKey get() = SimpleKey(upper, ipa, displayName.uppercase())

    companion object {
        private val byChar = entries.associateBy { it.char }
        private val byIpa = entries.groupBy { it.ipa }

        /** Keyboard layout keys by letter */
        val keys: Map<String, ILayoutKey> = entries.associate { it.char to it as ILayoutKey }

        fun fromChar(c: String): LatinKey? = byChar[c.lowercase()]
        fun fromIpa(ipa: String): List<LatinKey> = byIpa[ipa] ?: emptyList()
    }
}

/**
 * English spelling patterns (graphemes) with IPA pronunciations.
 * These are common letter combinations that represent specific sounds.
 * Ordered by specificity - longer/more specific patterns first.
 */
enum class EnglishPattern(
    val pattern: String,
    override val ipa: String,
    override val displayName: String,
    val examples: List<String>,
    val position: PatternPosition = PatternPosition.ANY
) : ILayoutKey {
    // === OUGH patterns (most variable in English) ===
    OUGHT("ought", "ɔːt", "ought", listOf("bought", "thought", "fought", "brought"), PatternPosition.END),
    OUGH_O("ough", "oʊ", "ough-long-o", listOf("though", "dough", "although"), PatternPosition.END),
    OUGH_UFF("ough", "ʌf", "ough-uff", listOf("rough", "tough", "enough"), PatternPosition.END),
    OUGH_OFF("ough", "ɒf", "ough-off", listOf("cough", "trough")),
    OUGH_OO("ough", "uː", "ough-oo", listOf("through", "throughout")),
    OUGH_OW("ough", "aʊ", "ough-ow", listOf("bough", "plough", "drought")),

    // === AUGH patterns ===
    AUGHT("aught", "ɔːt", "aught", listOf("caught", "taught", "daughter", "naughty"), PatternPosition.END),
    AUGH("augh", "ɔː", "augh", listOf("laugh"), PatternPosition.END), // Note: "laugh" is actually /æf/
    LAUGH("augh", "æf", "laugh", listOf("laugh", "laughter")),

    // === EIGH patterns ===
    EIGHT("eight", "eɪt", "eight", listOf("eight", "weight", "freight")),
    EIGH("eigh", "eɪ", "eigh", listOf("weigh", "sleigh", "neigh", "neighbor")),
    HEIGHT("eight", "aɪt", "height", listOf("height")), // Irregular

    // === IGHT patterns ===
    IGHT("ight", "aɪt", "ight", listOf("light", "night", "right", "might", "fight", "sight")),

    // === IGH patterns ===
    IGH("igh", "aɪ", "igh", listOf("high", "sigh", "thigh")),

    // === TION/SION patterns ===
    TION("tion", "ʃən", "tion", listOf("nation", "station", "motion", "action")),
    SION_ZH("sion", "ʒən", "sion-zh", listOf("vision", "decision", "television")),
    SION_SH("sion", "ʃən", "sion-sh", listOf("mission", "passion", "session")),

    // === ING patterns ===
    ING("ing", "ɪŋ", "ing", listOf("sing", "ring", "king", "thing")),
    TING("ting", "tɪŋ", "ting", listOf("sitting", "hitting", "cutting")),
    RING_VERB("ring", "rɪŋ", "ring", listOf("bring", "spring", "string")),

    // === OU patterns ===
    OUND("ound", "aʊnd", "ound", listOf("sound", "round", "ground", "found")),
    OUNT("ount", "aʊnt", "ount", listOf("count", "mount", "amount")),
    OUT("out", "aʊt", "out", listOf("out", "about", "shout", "scout")),
    OUR("our", "aʊər", "our", listOf("our", "hour", "sour")),
    OUR_ER("our", "ər", "our-er", listOf("colour", "favour", "honour")), // British
    OUSE("ouse", "aʊs", "ouse", listOf("house", "mouse", "blouse")),
    OUSE_Z("ouse", "aʊz", "ouse-z", listOf("house", "rouse")), // Verb forms
    OULD("ould", "ʊd", "ould", listOf("could", "would", "should")),
    OUP("oup", "uːp", "oup", listOf("soup", "group", "coup")),

    // === OW patterns ===
    OWN_OWN("own", "oʊn", "own-long", listOf("own", "grown", "shown", "known")),
    OWN_OWN2("own", "aʊn", "own-down", listOf("town", "down", "brown", "crown")),
    OW_LONG("ow", "oʊ", "ow-long", listOf("low", "show", "know", "grow", "slow")),
    OW_OW("ow", "aʊ", "ow-ow", listOf("now", "how", "cow", "bow", "wow")),

    // === EA patterns ===
    EAR_EER("ear", "ɪər", "ear-eer", listOf("ear", "hear", "near", "fear", "dear")),
    EAR_AIR("ear", "eər", "ear-air", listOf("bear", "wear", "pear", "swear")),
    EAR_ER("ear", "ɜːr", "ear-er", listOf("earth", "learn", "early", "search")),
    EAT("eat", "iːt", "eat", listOf("eat", "beat", "heat", "meat", "seat")),
    EAD_EED("ead", "iːd", "ead-eed", listOf("read", "lead", "bead")),
    EAD_ED("ead", "ɛd", "ead-ed", listOf("head", "dead", "bread", "spread")),
    EAK("eak", "iːk", "eak", listOf("speak", "weak", "peak", "sneak")),
    EAL("eal", "iːl", "eal", listOf("deal", "meal", "real", "steal")),
    EAM("eam", "iːm", "eam", listOf("team", "dream", "stream", "cream")),
    EAN("ean", "iːn", "ean", listOf("mean", "clean", "bean", "lean")),
    EAP("eap", "iːp", "eap", listOf("heap", "leap", "cheap")),
    EAS("eas", "iːz", "eas", listOf("peas", "please", "ease")),
    EAST("east", "iːst", "east", listOf("east", "beast", "feast", "least")),
    EATH("eath", "iːθ", "eath", listOf("beneath", "wreath")),
    EATH_SHORT("eath", "ɛθ", "eath-short", listOf("death", "breath")),

    // === OO patterns ===
    OOK("ook", "ʊk", "ook", listOf("book", "look", "cook", "hook", "took")),
    OOD_GOOD("ood", "ʊd", "ood-good", listOf("good", "wood", "stood", "hood")),
    OOD_FOOD("ood", "uːd", "ood-food", listOf("food", "mood", "brood")),
    OOL("ool", "uːl", "ool", listOf("pool", "cool", "school", "tool", "fool")),
    OOM("oom", "uːm", "oom", listOf("room", "boom", "doom", "zoom", "bloom")),
    OON("oon", "uːn", "oon", listOf("moon", "soon", "noon", "spoon")),
    OOP("oop", "uːp", "oop", listOf("loop", "hoop", "troop")),
    OOR("oor", "ɔːr", "oor", listOf("door", "floor", "poor")),
    OOT_FOOT("oot", "ʊt", "oot-foot", listOf("foot", "soot")),
    OOT_BOOT("oot", "uːt", "oot-boot", listOf("boot", "root", "shoot", "hoot")),
    OOTH("ooth", "uːθ", "ooth", listOf("tooth", "ooth", "booth")),

    // === AY/EY patterns ===
    AY("ay", "eɪ", "ay", listOf("day", "say", "way", "play", "stay")),
    EY("ey", "eɪ", "ey", listOf("they", "grey", "hey", "prey")),
    EY_I("ey", "i", "ey-i", listOf("key", "money", "honey", "monkey")),

    // === OY/OI patterns ===
    OY("oy", "ɔɪ", "oy", listOf("boy", "toy", "joy", "enjoy")),
    OI("oi", "ɔɪ", "oi", listOf("oil", "coin", "join", "point")),
    OISE("oise", "ɔɪz", "oise", listOf("noise", "poise")),
    OICE("oice", "ɔɪs", "oice", listOf("voice", "choice", "rejoice")),

    // === AWE/AW patterns ===
    AWE("awe", "ɔː", "awe", listOf("awe", "awesome")),
    AW("aw", "ɔː", "aw", listOf("saw", "law", "raw", "draw", "jaw")),
    AWN("awn", "ɔːn", "awn", listOf("dawn", "lawn", "yawn", "spawn")),
    AWL("awl", "ɔːl", "awl", listOf("crawl", "shawl", "brawl")),

    // === AIR/ARE patterns ===
    AIR("air", "eər", "air", listOf("air", "hair", "fair", "pair", "chair")),
    ARE("are", "eər", "are", listOf("care", "share", "rare", "bare", "dare")),

    // === ER/IR/UR patterns ===
    ER("er", "ər", "er", listOf("her", "father", "water", "better")),
    IR("ir", "ɜːr", "ir", listOf("bird", "girl", "first", "shirt", "sir")),
    UR("ur", "ɜːr", "ur", listOf("turn", "burn", "hurt", "nurse", "fur")),

    // === AL/ALL patterns ===
    ALL("all", "ɔːl", "all", listOf("all", "ball", "call", "fall", "tall", "wall")),
    ALK("alk", "ɔːk", "alk", listOf("walk", "talk", "chalk")),
    ALT("alt", "ɔːlt", "alt", listOf("salt", "halt", "malt")),

    // === Silent letter patterns ===
    KN("kn", "n", "kn", listOf("know", "knee", "knife", "knock", "knight")),
    WR("wr", "r", "wr", listOf("write", "wrong", "wrap", "wrist")),
    GN("gn", "n", "gn", listOf("gnaw", "gnat", "sign", "design")),
    MB("mb", "m", "mb", listOf("lamb", "climb", "thumb", "bomb", "comb"), PatternPosition.END),
    GH_SILENT("gh", "", "gh-silent", listOf("high", "sigh", "weigh", "though")),

    // === Common endings ===
    ABLE("able", "əbəl", "able", listOf("able", "table", "capable"), PatternPosition.END),
    IBLE("ible", "ɪbəl", "ible", listOf("possible", "visible", "terrible"), PatternPosition.END),
    NESS("ness", "nəs", "ness", listOf("happiness", "darkness", "kindness"), PatternPosition.END),
    MENT("ment", "mənt", "ment", listOf("moment", "element", "government"), PatternPosition.END),
    ENCE("ence", "əns", "ence", listOf("silence", "difference", "presence"), PatternPosition.END),
    ANCE("ance", "əns", "ance", listOf("dance", "chance", "balance"), PatternPosition.END),
    ENT("ent", "ənt", "ent", listOf("sent", "tent", "moment"), PatternPosition.END),
    ANT("ant", "ənt", "ant", listOf("ant", "plant", "giant"), PatternPosition.END),
    LY("ly", "li", "ly", listOf("really", "quickly", "slowly"), PatternPosition.END),
    FUL("ful", "fəl", "ful", listOf("beautiful", "wonderful", "careful"), PatternPosition.END),
    LESS("less", "ləs", "less", listOf("helpless", "careless", "endless"), PatternPosition.END),
    ED_T("ed", "t", "ed-t", listOf("walked", "jumped", "helped"), PatternPosition.END),
    ED_D("ed", "d", "ed-d", listOf("called", "played", "loved"), PatternPosition.END),
    ED_ID("ed", "ɪd", "ed-id", listOf("wanted", "needed", "added"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        /** Find patterns matching a spelling */
        fun fromPattern(pattern: String): List<EnglishPattern> =
            byPattern[pattern.lowercase()] ?: emptyList()

        /** Find patterns by IPA sound */
        fun fromIpa(ipa: String): List<EnglishPattern> =
            byIpa[ipa] ?: emptyList()

        /** Find best matching pattern for a word ending */
        fun matchEnding(word: String): EnglishPattern? {
            val w = word.lowercase()
            // Try longest patterns first
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }

        /** Find all patterns that could match in a word */
        fun findInWord(word: String): List<EnglishPattern> {
            val w = word.lowercase()
            return entries.filter { w.contains(it.pattern) }
        }
    }
}

/**
 * Where in a word this pattern typically occurs
 */
enum class PatternPosition {
    START,  // Beginning of word (kn-, wr-)
    END,    // End of word (-tion, -ight)
    ANY     // Can occur anywhere
}

/**
 * Chinese Pinyin finals (韵母) for rhyme matching.
 * Chinese poetry rhymes by matching finals (vowel + optional nasal coda).
 * These are the standard Pinyin finals mapped to IPA.
 */
enum class PinyinFinal(
    val pinyin: String,
    override val ipa: String,
    override val displayName: String,
    val examples: List<String> = emptyList()
) : ILayoutKey {
    // Simple finals (单韵母)
    A("a", "a", "a", listOf("大", "他", "妈")),
    O("o", "o", "o", listOf("波", "泼", "摸")),
    E("e", "ɤ", "e", listOf("得", "乐", "可")),
    I("i", "i", "i", listOf("一", "七", "西")),
    U("u", "u", "u", listOf("五", "不", "路")),
    V("ü", "y", "ü", listOf("女", "绿", "雨")),

    // Compound finals (复韵母)
    AI("ai", "aɪ", "ai", listOf("爱", "太", "来")),
    EI("ei", "eɪ", "ei", listOf("北", "给", "美")),
    AO("ao", "aʊ", "ao", listOf("高", "好", "老")),
    OU("ou", "oʊ", "ou", listOf("走", "后", "狗")),
    IA("ia", "ia", "ia", listOf("家", "下", "花")),
    IE("ie", "iɛ", "ie", listOf("写", "别", "夜")),
    IU("iu", "ioʊ", "iu", listOf("六", "久", "有")),
    UA("ua", "ua", "ua", listOf("瓜", "话", "挂")),
    UO("uo", "uo", "uo", listOf("多", "过", "国")),
    UE("üe", "yɛ", "üe", listOf("月", "学", "雪")),
    UI("ui", "ueɪ", "ui", listOf("水", "回", "对")),

    // Nasal finals (鼻韵母)
    AN("an", "an", "an", listOf("安", "三", "看")),
    EN("en", "ən", "en", listOf("人", "分", "很")),
    IN("in", "in", "in", listOf("今", "心", "金")),
    UN("un", "un", "un", listOf("春", "论", "昆")),
    VN("ün", "yn", "ün", listOf("军", "群", "云")),
    ANG("ang", "aŋ", "ang", listOf("上", "忙", "长")),
    ENG("eng", "əŋ", "eng", listOf("风", "灯", "能")),
    ING("ing", "iŋ", "ing", listOf("星", "听", "名")),
    ONG("ong", "uŋ", "ong", listOf("中", "红", "东")),
    IANG("iang", "iaŋ", "iang", listOf("亮", "想", "将")),
    IONG("iong", "yŋ", "iong", listOf("穷", "用", "雄")),
    UANG("uang", "uaŋ", "uang", listOf("黄", "光", "王")),
    UENG("ueng", "uəŋ", "ueng", listOf("翁")),

    // Er finals (儿化韵)
    ER("er", "ɤɻ", "er", listOf("二", "而", "耳"));

    override val char: String get() = pinyin
    override val shiftKey: ILayoutKey? get() = null

    companion object {
        private val byPinyin = entries.associateBy { it.pinyin }
        private val byIpa = entries.groupBy { it.ipa }

        /** Find final by Pinyin spelling */
        fun fromPinyin(pinyin: String): PinyinFinal? = byPinyin[pinyin.lowercase()]

        /** Find finals by IPA */
        fun fromIpa(ipa: String): List<PinyinFinal> = byIpa[ipa] ?: emptyList()

        /** Extract final from a Pinyin syllable (e.g., "guang" → UANG) */
        fun matchFinal(syllable: String): PinyinFinal? {
            val s = syllable.lowercase()
            // Try longest finals first
            return entries
                .sortedByDescending { it.pinyin.length }
                .firstOrNull { s.endsWith(it.pinyin) }
        }

        /** Check if two syllables rhyme (same final) */
        fun rhymes(syllable1: String, syllable2: String): Boolean {
            val f1 = matchFinal(syllable1)
            val f2 = matchFinal(syllable2)
            return f1 != null && f1 == f2
        }
    }
}

/**
 * Chinese Pinyin initials (声母) for completeness.
 */
enum class PinyinInitial(
    val pinyin: String,
    override val ipa: String,
    override val displayName: String
) : ILayoutKey {
    B("b", "p", "b"),
    P("p", "pʰ", "p"),
    M("m", "m", "m"),
    F("f", "f", "f"),
    D("d", "t", "d"),
    T("t", "tʰ", "t"),
    N("n", "n", "n"),
    L("l", "l", "l"),
    G("g", "k", "g"),
    K("k", "kʰ", "k"),
    H("h", "x", "h"),
    J("j", "tɕ", "j"),
    Q("q", "tɕʰ", "q"),
    X("x", "ɕ", "x"),
    ZH("zh", "tʂ", "zh"),
    CH("ch", "tʂʰ", "ch"),
    SH("sh", "ʂ", "sh"),
    R("r", "ɻ", "r"),
    Z("z", "ts", "z"),
    C("c", "tsʰ", "c"),
    S("s", "s", "s"),
    Y("y", "j", "y"),
    W("w", "w", "w"),
    ZERO("", "", "zero-initial");  // For syllables starting with vowels

    override val char: String get() = pinyin
    override val shiftKey: ILayoutKey? get() = null

    companion object {
        private val byPinyin = entries.associateBy { it.pinyin }

        /** Find initial by Pinyin spelling */
        fun fromPinyin(pinyin: String): PinyinInitial? = byPinyin[pinyin.lowercase()]

        /** Extract initial from a Pinyin syllable */
        fun matchInitial(syllable: String): PinyinInitial {
            val s = syllable.lowercase()
            // Try two-letter initials first
            for (init in listOf(ZH, CH, SH)) {
                if (s.startsWith(init.pinyin)) return init
            }
            // Then single-letter initials
            return entries.firstOrNull { it.pinyin.length == 1 && s.startsWith(it.pinyin) } ?: ZERO
        }
    }
}
