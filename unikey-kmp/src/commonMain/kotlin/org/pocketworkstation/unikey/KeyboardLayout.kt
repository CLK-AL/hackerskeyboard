package org.pocketworkstation.unikey

/**
 * Keyboard Layout - defines character mappings for each language
 * Supports all 23 Chatterbox multilingual TTS languages
 */
data class KeyboardLayout(
    val code: String,           // ISO 639-1 language code
    val name: String,           // Display name
    val nativeName: String,     // Name in native script
    val script: UniKeySyllable.Companion.Script,
    val keys: Map<String, LayoutKey>
)

/**
 * A single key in a layout
 */
data class LayoutKey(
    val char: String,           // The character
    val shift: String? = null,  // Shifted character
    val ipa: String = "",       // IPA pronunciation
    val name: String? = null    // Name of the character (for non-Latin)
)

/**
 * All keyboard layouts for Chatterbox 23 languages
 */
object KeyboardLayouts {

    private val QWERTY_ROW1 = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
    private val QWERTY_ROW2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l")
    private val QWERTY_ROW3 = listOf("z", "x", "c", "v", "b", "n", "m")

    // ═══ Arabic (ar) ═══
    val AR = KeyboardLayout(
        code = "ar",
        name = "Arabic",
        nativeName = "العربية",
        script = UniKeySyllable.Companion.Script.ARABIC,
        keys = mapOf(
            "q" to LayoutKey("ض", "َ", "dˤ", "dad"),
            "w" to LayoutKey("ص", "ً", "sˤ", "sad"),
            "e" to LayoutKey("ث", "ُ", "θ", "tha"),
            "r" to LayoutKey("ق", "ٌ", "q", "qaf"),
            "t" to LayoutKey("ف", "ِ", "f", "fa"),
            "y" to LayoutKey("غ", "ٍ", "ɣ", "ghain"),
            "u" to LayoutKey("ع", "ّ", "ʕ", "ain"),
            "i" to LayoutKey("ه", "ْ", "h", "ha"),
            "o" to LayoutKey("خ", "ـ", "x", "kha"),
            "p" to LayoutKey("ح", "؛", "ħ", "ha"),
            "a" to LayoutKey("ش", "\\", "ʃ", "shin"),
            "s" to LayoutKey("س", null, "s", "sin"),
            "d" to LayoutKey("ي", "]", "j", "ya"),
            "f" to LayoutKey("ب", "[", "b", "ba"),
            "g" to LayoutKey("ل", "لأ", "l", "lam"),
            "h" to LayoutKey("ا", "أ", "", "alif"),
            "j" to LayoutKey("ت", "ـ", "t", "ta"),
            "k" to LayoutKey("ن", "،", "n", "nun"),
            "l" to LayoutKey("م", "/", "m", "mim"),
            "z" to LayoutKey("ئ", "~", "ʔ", "hamza"),
            "x" to LayoutKey("ء", "ْ", "ʔ", "hamza"),
            "c" to LayoutKey("ؤ", "}", "ʔ", "hamza"),
            "v" to LayoutKey("ر", "{", "r", "ra"),
            "b" to LayoutKey("لا", "لآ", "la", "lam-alif"),
            "n" to LayoutKey("ى", "آ", "aː", "alif-maqsura"),
            "m" to LayoutKey("ة", "'", "a", "ta-marbuta"),
            "," to LayoutKey("و", ",", "w", "waw"),
            "." to LayoutKey("ز", ".", "z", "zay"),
            "/" to LayoutKey("ظ", "؟", "ðˤ", "za")
        )
    )

    // ═══ Danish (da) ═══
    val DA = KeyboardLayout(
        code = "da",
        name = "Danish",
        nativeName = "Dansk",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("æ", "Æ", "ɛ"),
            "'" to LayoutKey("ø", "Ø", "ø"),
            "[" to LayoutKey("å", "Å", "ɔ")
        ))
    )

    // ═══ German (de) ═══
    val DE = KeyboardLayout(
        code = "de",
        name = "German",
        nativeName = "Deutsch",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("ö", "Ö", "ø"),
            "'" to LayoutKey("ä", "Ä", "ɛ"),
            "[" to LayoutKey("ü", "Ü", "y"),
            "]" to LayoutKey("ß", "ẞ", "s")
        ))
    )

    // ═══ Greek (el) ═══
    val EL = KeyboardLayout(
        code = "el",
        name = "Greek",
        nativeName = "Ελληνικά",
        script = UniKeySyllable.Companion.Script.GREEK,
        keys = mapOf(
            "q" to LayoutKey(";", ":", "", "semicolon"),
            "w" to LayoutKey("ς", "΅", "s", "final sigma"),
            "e" to LayoutKey("ε", "Ε", "e", "epsilon"),
            "r" to LayoutKey("ρ", "Ρ", "r", "rho"),
            "t" to LayoutKey("τ", "Τ", "t", "tau"),
            "y" to LayoutKey("υ", "Υ", "i", "upsilon"),
            "u" to LayoutKey("θ", "Θ", "θ", "theta"),
            "i" to LayoutKey("ι", "Ι", "i", "iota"),
            "o" to LayoutKey("ο", "Ο", "o", "omicron"),
            "p" to LayoutKey("π", "Π", "p", "pi"),
            "a" to LayoutKey("α", "Α", "a", "alpha"),
            "s" to LayoutKey("σ", "Σ", "s", "sigma"),
            "d" to LayoutKey("δ", "Δ", "ð", "delta"),
            "f" to LayoutKey("φ", "Φ", "f", "phi"),
            "g" to LayoutKey("γ", "Γ", "ɣ", "gamma"),
            "h" to LayoutKey("η", "Η", "i", "eta"),
            "j" to LayoutKey("ξ", "Ξ", "ks", "xi"),
            "k" to LayoutKey("κ", "Κ", "k", "kappa"),
            "l" to LayoutKey("λ", "Λ", "l", "lambda"),
            "z" to LayoutKey("ζ", "Ζ", "z", "zeta"),
            "x" to LayoutKey("χ", "Χ", "x", "chi"),
            "c" to LayoutKey("ψ", "Ψ", "ps", "psi"),
            "v" to LayoutKey("ω", "Ω", "o", "omega"),
            "b" to LayoutKey("β", "Β", "v", "beta"),
            "n" to LayoutKey("ν", "Ν", "n", "nu"),
            "m" to LayoutKey("μ", "Μ", "m", "mu")
        )
    )

    // ═══ English (en) ═══
    val EN = KeyboardLayout(
        code = "en",
        name = "English",
        nativeName = "English",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(emptyMap())
    )

    // ═══ Spanish (es) ═══
    val ES = KeyboardLayout(
        code = "es",
        name = "Spanish",
        nativeName = "Español",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("ñ", "Ñ", "ɲ"),
            "'" to LayoutKey("´", "¨", ""),
            "[" to LayoutKey("¿", "¡", "")
        ))
    )

    // ═══ Finnish (fi) ═══
    val FI = KeyboardLayout(
        code = "fi",
        name = "Finnish",
        nativeName = "Suomi",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("ö", "Ö", "ø"),
            "'" to LayoutKey("ä", "Ä", "æ")
        ))
    )

    // ═══ French (fr) ═══
    val FR = KeyboardLayout(
        code = "fr",
        name = "French",
        nativeName = "Français",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("é", "É", "e"),
            "'" to LayoutKey("è", "È", "ɛ"),
            "[" to LayoutKey("ç", "Ç", "s"),
            "]" to LayoutKey("à", "À", "a")
        ))
    )

    // ═══ Hebrew (he) ═══
    val HE = KeyboardLayout(
        code = "he",
        name = "Hebrew",
        nativeName = "עברית",
        script = UniKeySyllable.Companion.Script.HEBREW,
        keys = mapOf(
            "t" to LayoutKey("א", null, "ʔ", "alef"),
            "c" to LayoutKey("ב", null, "v", "bet"),
            "d" to LayoutKey("ג", null, "g", "gimel"),
            "s" to LayoutKey("ד", null, "d", "dalet"),
            "v" to LayoutKey("ה", null, "h", "he"),
            "u" to LayoutKey("ו", null, "v", "vav"),
            "z" to LayoutKey("ז", null, "z", "zayin"),
            "j" to LayoutKey("ח", null, "x", "chet"),
            "y" to LayoutKey("ט", null, "t", "tet"),
            "h" to LayoutKey("י", null, "j", "yod"),
            "l" to LayoutKey("ך", null, "x", "kaf-sofit"),
            "f" to LayoutKey("כ", null, "x", "kaf"),
            "k" to LayoutKey("ל", null, "l", "lamed"),
            "o" to LayoutKey("ם", null, "m", "mem-sofit"),
            "n" to LayoutKey("מ", null, "m", "mem"),
            "i" to LayoutKey("ן", null, "n", "nun-sofit"),
            "b" to LayoutKey("נ", null, "n", "nun"),
            "x" to LayoutKey("ס", null, "s", "samech"),
            "g" to LayoutKey("ע", null, "ʕ", "ayin"),
            ";" to LayoutKey("ף", null, "f", "pe-sofit"),
            "p" to LayoutKey("פ", null, "f", "pe"),
            "." to LayoutKey("ץ", null, "ts", "tsadi-sofit"),
            "m" to LayoutKey("צ", null, "ts", "tsadi"),
            "e" to LayoutKey("ק", null, "k", "qof"),
            "r" to LayoutKey("ר", null, "ʁ", "resh"),
            "a" to LayoutKey("ש", null, "ʃ", "shin"),
            "," to LayoutKey("ת", null, "t", "tav")
        )
    )

    // ═══ Hindi (hi) ═══
    val HI = KeyboardLayout(
        code = "hi",
        name = "Hindi",
        nativeName = "हिन्दी",
        script = UniKeySyllable.Companion.Script.DEVANAGARI,
        keys = mapOf(
            // Vowels
            "q" to LayoutKey("औ", "ौ", "ɔː", "au"),
            "w" to LayoutKey("ऐ", "ै", "ɛː", "ai"),
            "e" to LayoutKey("आ", "ा", "aː", "aa"),
            "r" to LayoutKey("ई", "ी", "iː", "ii"),
            "t" to LayoutKey("ऊ", "ू", "uː", "uu"),
            // Consonants row 1
            "y" to LayoutKey("भ", null, "bʱ", "bha"),
            "u" to LayoutKey("ङ", null, "ŋ", "nga"),
            "i" to LayoutKey("घ", null, "ɡʱ", "gha"),
            "o" to LayoutKey("ध", null, "dʱ", "dha"),
            "p" to LayoutKey("झ", null, "dʒʱ", "jha"),
            // Consonants row 2
            "a" to LayoutKey("ओ", "ो", "oː", "o"),
            "s" to LayoutKey("ए", "े", "eː", "e"),
            "d" to LayoutKey("अ", null, "ə", "a"),
            "f" to LayoutKey("इ", "ि", "ɪ", "i"),
            "g" to LayoutKey("उ", "ु", "ʊ", "u"),
            "h" to LayoutKey("प", null, "p", "pa"),
            "j" to LayoutKey("र", "्", "r", "ra"),
            "k" to LayoutKey("क", null, "k", "ka"),
            "l" to LayoutKey("त", null, "t", "ta"),
            // Consonants row 3
            "z" to LayoutKey("ॉ", null, "ɔ", "candra-o"),
            "x" to LayoutKey("ँ", null, "̃", "chandrabindu"),
            "c" to LayoutKey("म", null, "m", "ma"),
            "v" to LayoutKey("न", null, "n", "na"),
            "b" to LayoutKey("ब", null, "b", "ba"),
            "n" to LayoutKey("ल", null, "l", "la"),
            "m" to LayoutKey("स", null, "s", "sa"),
            "," to LayoutKey("व", "ृ", "ʋ", "va"),
            "." to LayoutKey("य", null, "j", "ya"),
            "/" to LayoutKey("ज", null, "dʒ", "ja")
        )
    )

    // ═══ Italian (it) ═══
    val IT = KeyboardLayout(
        code = "it",
        name = "Italian",
        nativeName = "Italiano",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("ò", "Ò", "ɔ"),
            "'" to LayoutKey("à", "À", "a"),
            "[" to LayoutKey("è", "È", "ɛ"),
            "]" to LayoutKey("ù", "Ù", "u"),
            "\\" to LayoutKey("ì", "Ì", "i")
        ))
    )

    // ═══ Japanese (ja) ═══
    val JA = KeyboardLayout(
        code = "ja",
        name = "Japanese",
        nativeName = "日本語",
        script = UniKeySyllable.Companion.Script.HIRAGANA,
        keys = mapOf(
            // Hiragana mapped to romaji input
            "a" to LayoutKey("あ", "ア", "a", "a"),
            "i" to LayoutKey("い", "イ", "i", "i"),
            "u" to LayoutKey("う", "ウ", "ɯ", "u"),
            "e" to LayoutKey("え", "エ", "e", "e"),
            "o" to LayoutKey("お", "オ", "o", "o"),
            "k" to LayoutKey("か", "カ", "k", "ka"),
            "s" to LayoutKey("さ", "サ", "s", "sa"),
            "t" to LayoutKey("た", "タ", "t", "ta"),
            "n" to LayoutKey("な", "ナ", "n", "na"),
            "h" to LayoutKey("は", "ハ", "h", "ha"),
            "m" to LayoutKey("ま", "マ", "m", "ma"),
            "y" to LayoutKey("や", "ヤ", "j", "ya"),
            "r" to LayoutKey("ら", "ラ", "ɾ", "ra"),
            "w" to LayoutKey("わ", "ワ", "w", "wa"),
            "g" to LayoutKey("が", "ガ", "ɡ", "ga"),
            "z" to LayoutKey("ざ", "ザ", "z", "za"),
            "d" to LayoutKey("だ", "ダ", "d", "da"),
            "b" to LayoutKey("ば", "バ", "b", "ba"),
            "p" to LayoutKey("ぱ", "パ", "p", "pa"),
            "f" to LayoutKey("ふ", "フ", "ɸ", "fu"),
            "j" to LayoutKey("じ", "ジ", "dʒ", "ji"),
            "c" to LayoutKey("ち", "チ", "tɕ", "chi"),
            "x" to LayoutKey("っ", "ッ", "", "small-tsu"),
            "v" to LayoutKey("ん", "ン", "n", "n"),
            "l" to LayoutKey("ー", "ー", "ː", "long-vowel"),
            "q" to LayoutKey("。", "。", "", "period"),
            "/" to LayoutKey("・", "・", "", "middle-dot")
        )
    )

    // ═══ Korean (ko) ═══
    val KO = KeyboardLayout(
        code = "ko",
        name = "Korean",
        nativeName = "한국어",
        script = UniKeySyllable.Companion.Script.HANGUL,
        keys = mapOf(
            // Consonants (초성)
            "q" to LayoutKey("ㅂ", "ㅃ", "p", "bieup"),
            "w" to LayoutKey("ㅈ", "ㅉ", "tɕ", "jieut"),
            "e" to LayoutKey("ㄷ", "ㄸ", "t", "digeut"),
            "r" to LayoutKey("ㄱ", "ㄲ", "k", "giyeok"),
            "t" to LayoutKey("ㅅ", "ㅆ", "s", "siot"),
            "a" to LayoutKey("ㅁ", null, "m", "mieum"),
            "s" to LayoutKey("ㄴ", null, "n", "nieun"),
            "d" to LayoutKey("ㅇ", null, "ŋ", "ieung"),
            "f" to LayoutKey("ㄹ", null, "ɾ", "rieul"),
            "g" to LayoutKey("ㅎ", null, "h", "hieut"),
            "z" to LayoutKey("ㅋ", null, "kʰ", "kieuk"),
            "x" to LayoutKey("ㅌ", null, "tʰ", "tieut"),
            "c" to LayoutKey("ㅊ", null, "tɕʰ", "chieut"),
            "v" to LayoutKey("ㅍ", null, "pʰ", "pieup"),
            // Vowels (중성)
            "y" to LayoutKey("ㅛ", null, "jo", "yo"),
            "u" to LayoutKey("ㅕ", null, "jʌ", "yeo"),
            "i" to LayoutKey("ㅑ", null, "ja", "ya"),
            "o" to LayoutKey("ㅐ", "ㅒ", "ɛ", "ae"),
            "p" to LayoutKey("ㅔ", "ㅖ", "e", "e"),
            "h" to LayoutKey("ㅗ", null, "o", "o"),
            "j" to LayoutKey("ㅓ", null, "ʌ", "eo"),
            "k" to LayoutKey("ㅏ", null, "a", "a"),
            "l" to LayoutKey("ㅣ", null, "i", "i"),
            "b" to LayoutKey("ㅠ", null, "ju", "yu"),
            "n" to LayoutKey("ㅜ", null, "u", "u"),
            "m" to LayoutKey("ㅡ", null, "ɨ", "eu")
        )
    )

    // ═══ Malay (ms) ═══
    val MS = KeyboardLayout(
        code = "ms",
        name = "Malay",
        nativeName = "Bahasa Melayu",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(emptyMap())
    )

    // ═══ Dutch (nl) ═══
    val NL = KeyboardLayout(
        code = "nl",
        name = "Dutch",
        nativeName = "Nederlands",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            "'" to LayoutKey("´", "¨", ""),
            "[" to LayoutKey("ij", "IJ", "ɛi")
        ))
    )

    // ═══ Norwegian (no) ═══
    val NO = KeyboardLayout(
        code = "no",
        name = "Norwegian",
        nativeName = "Norsk",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("ø", "Ø", "ø"),
            "'" to LayoutKey("æ", "Æ", "æ"),
            "[" to LayoutKey("å", "Å", "ɔ")
        ))
    )

    // ═══ Polish (pl) ═══
    val PL = KeyboardLayout(
        code = "pl",
        name = "Polish",
        nativeName = "Polski",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            "a" to LayoutKey("a", "ą", "a"),
            "c" to LayoutKey("c", "ć", "ts"),
            "e" to LayoutKey("e", "ę", "e"),
            "l" to LayoutKey("l", "ł", "w"),
            "n" to LayoutKey("n", "ń", "ɲ"),
            "o" to LayoutKey("o", "ó", "u"),
            "s" to LayoutKey("s", "ś", "ɕ"),
            "z" to LayoutKey("z", "ż", "ʐ"),
            "x" to LayoutKey("x", "ź", "ʑ")
        ))
    )

    // ═══ Portuguese (pt) ═══
    val PT = KeyboardLayout(
        code = "pt",
        name = "Portuguese",
        nativeName = "Português",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("ç", "Ç", "s"),
            "'" to LayoutKey("~", "^", ""),
            "[" to LayoutKey("´", "`", ""),
            "]" to LayoutKey("ã", "Ã", "ɐ̃")
        ))
    )

    // ═══ Russian (ru) ═══
    val RU = KeyboardLayout(
        code = "ru",
        name = "Russian",
        nativeName = "Русский",
        script = UniKeySyllable.Companion.Script.CYRILLIC,
        keys = mapOf(
            "q" to LayoutKey("й", "Й", "j", "short-i"),
            "w" to LayoutKey("ц", "Ц", "ts", "tse"),
            "e" to LayoutKey("у", "У", "u", "u"),
            "r" to LayoutKey("к", "К", "k", "ka"),
            "t" to LayoutKey("е", "Е", "je", "ye"),
            "y" to LayoutKey("н", "Н", "n", "en"),
            "u" to LayoutKey("г", "Г", "ɡ", "ge"),
            "i" to LayoutKey("ш", "Ш", "ʃ", "sha"),
            "o" to LayoutKey("щ", "Щ", "ʃtʃ", "shcha"),
            "p" to LayoutKey("з", "З", "z", "ze"),
            "[" to LayoutKey("х", "Х", "x", "kha"),
            "]" to LayoutKey("ъ", "Ъ", "", "hard-sign"),
            "a" to LayoutKey("ф", "Ф", "f", "ef"),
            "s" to LayoutKey("ы", "Ы", "ɨ", "yeru"),
            "d" to LayoutKey("в", "В", "v", "ve"),
            "f" to LayoutKey("а", "А", "a", "a"),
            "g" to LayoutKey("п", "П", "p", "pe"),
            "h" to LayoutKey("р", "Р", "r", "er"),
            "j" to LayoutKey("о", "О", "o", "o"),
            "k" to LayoutKey("л", "Л", "l", "el"),
            "l" to LayoutKey("д", "Д", "d", "de"),
            ";" to LayoutKey("ж", "Ж", "ʒ", "zhe"),
            "'" to LayoutKey("э", "Э", "e", "e"),
            "z" to LayoutKey("я", "Я", "ja", "ya"),
            "x" to LayoutKey("ч", "Ч", "tʃ", "che"),
            "c" to LayoutKey("с", "С", "s", "es"),
            "v" to LayoutKey("м", "М", "m", "em"),
            "b" to LayoutKey("и", "И", "i", "i"),
            "n" to LayoutKey("т", "Т", "t", "te"),
            "m" to LayoutKey("ь", "Ь", "", "soft-sign"),
            "," to LayoutKey("б", "Б", "b", "be"),
            "." to LayoutKey("ю", "Ю", "ju", "yu")
        )
    )

    // ═══ Swedish (sv) ═══
    val SV = KeyboardLayout(
        code = "sv",
        name = "Swedish",
        nativeName = "Svenska",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            ";" to LayoutKey("ö", "Ö", "ø"),
            "'" to LayoutKey("ä", "Ä", "ɛ"),
            "[" to LayoutKey("å", "Å", "ɔ")
        ))
    )

    // ═══ Swahili (sw) ═══
    val SW = KeyboardLayout(
        code = "sw",
        name = "Swahili",
        nativeName = "Kiswahili",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(emptyMap())
    )

    // ═══ Turkish (tr) ═══
    val TR = KeyboardLayout(
        code = "tr",
        name = "Turkish",
        nativeName = "Türkçe",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout(mapOf(
            "i" to LayoutKey("ı", "I", "ɯ"),  // dotless i
            ";" to LayoutKey("ş", "Ş", "ʃ"),
            "'" to LayoutKey("i", "İ", "i"),  // dotted i
            "[" to LayoutKey("ğ", "Ğ", "ɣ"),
            "]" to LayoutKey("ü", "Ü", "y"),
            "\\" to LayoutKey("ç", "Ç", "tʃ"),
            "/" to LayoutKey("ö", "Ö", "ø")
        ))
    )

    // ═══ Chinese (zh) ═══
    val ZH = KeyboardLayout(
        code = "zh",
        name = "Chinese",
        nativeName = "中文",
        script = UniKeySyllable.Companion.Script.CJK,
        keys = mapOf(
            // Pinyin initials
            "b" to LayoutKey("b", null, "p", "bo"),
            "p" to LayoutKey("p", null, "pʰ", "po"),
            "m" to LayoutKey("m", null, "m", "mo"),
            "f" to LayoutKey("f", null, "f", "fo"),
            "d" to LayoutKey("d", null, "t", "de"),
            "t" to LayoutKey("t", null, "tʰ", "te"),
            "n" to LayoutKey("n", null, "n", "ne"),
            "l" to LayoutKey("l", null, "l", "le"),
            "g" to LayoutKey("g", null, "k", "ge"),
            "k" to LayoutKey("k", null, "kʰ", "ke"),
            "h" to LayoutKey("h", null, "x", "he"),
            "j" to LayoutKey("j", null, "tɕ", "ji"),
            "q" to LayoutKey("q", null, "tɕʰ", "qi"),
            "x" to LayoutKey("x", null, "ɕ", "xi"),
            "z" to LayoutKey("z", null, "ts", "zi"),
            "c" to LayoutKey("c", null, "tsʰ", "ci"),
            "s" to LayoutKey("s", null, "s", "si"),
            "r" to LayoutKey("r", null, "ɻ", "ri"),
            "y" to LayoutKey("y", null, "j", "yi"),
            "w" to LayoutKey("w", null, "w", "wu"),
            // Pinyin finals (as combinations)
            "a" to LayoutKey("a", null, "a", "a"),
            "o" to LayoutKey("o", null, "o", "o"),
            "e" to LayoutKey("e", null, "ə", "e"),
            "i" to LayoutKey("i", null, "i", "i"),
            "u" to LayoutKey("u", null, "u", "u"),
            "v" to LayoutKey("ü", null, "y", "ü")
        )
    )

    /**
     * Build a Latin-based layout with overrides
     */
    private fun buildLatinLayout(overrides: Map<String, LayoutKey>): Map<String, LayoutKey> {
        val base = mutableMapOf<String, LayoutKey>()

        // Standard QWERTY
        for (c in 'a'..'z') {
            val key = c.toString()
            base[key] = LayoutKey(key, key.uppercase(), getLatinIpa(c))
        }

        // Numbers and symbols
        base["1"] = LayoutKey("1", "!", "")
        base["2"] = LayoutKey("2", "@", "")
        base["3"] = LayoutKey("3", "#", "")
        base["4"] = LayoutKey("4", "$", "")
        base["5"] = LayoutKey("5", "%", "")
        base["6"] = LayoutKey("6", "^", "")
        base["7"] = LayoutKey("7", "&", "")
        base["8"] = LayoutKey("8", "*", "")
        base["9"] = LayoutKey("9", "(", "")
        base["0"] = LayoutKey("0", ")", "")

        // Apply overrides
        base.putAll(overrides)

        return base
    }

    private fun getLatinIpa(c: Char): String = when (c) {
        'a' -> "a"
        'b' -> "b"
        'c' -> "k"
        'd' -> "d"
        'e' -> "e"
        'f' -> "f"
        'g' -> "ɡ"
        'h' -> "h"
        'i' -> "i"
        'j' -> "dʒ"
        'k' -> "k"
        'l' -> "l"
        'm' -> "m"
        'n' -> "n"
        'o' -> "o"
        'p' -> "p"
        'q' -> "k"
        'r' -> "r"
        's' -> "s"
        't' -> "t"
        'u' -> "u"
        'v' -> "v"
        'w' -> "w"
        'x' -> "ks"
        'y' -> "j"
        'z' -> "z"
        else -> ""
    }

    /**
     * All layouts by language code
     */
    val layouts: Map<String, KeyboardLayout> = mapOf(
        "ar" to AR,
        "da" to DA,
        "de" to DE,
        "el" to EL,
        "en" to EN,
        "es" to ES,
        "fi" to FI,
        "fr" to FR,
        "he" to HE,
        "hi" to HI,
        "it" to IT,
        "ja" to JA,
        "ko" to KO,
        "ms" to MS,
        "nl" to NL,
        "no" to NO,
        "pl" to PL,
        "pt" to PT,
        "ru" to RU,
        "sv" to SV,
        "sw" to SW,
        "tr" to TR,
        "zh" to ZH
    )

    /**
     * Get layout by language code
     */
    fun get(code: String): KeyboardLayout? = layouts[code.lowercase()]

    /**
     * Get all supported language codes
     */
    val supportedLanguages: List<String> = layouts.keys.toList().sorted()
}
