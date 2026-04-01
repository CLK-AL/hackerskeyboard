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
 * A single key in a layout - immutable phonetic key definition
 * Modifiers (shift, ctrl, alt) reference other LayoutKeys
 */
data class LayoutKey(
    val char: String,                    // The character output
    val ipa: String,                     // IPA pronunciation (required)
    val name: String,                    // Name of the character (required)
    val modifiers: Map<Modifier, LayoutKey> = emptyMap()  // Modified versions
) {
    /**
     * Get the key for a given modifier combination
     */
    fun withModifier(mod: Modifier): LayoutKey? = modifiers[mod]

    /**
     * Get the key for shift modifier (convenience)
     */
    val shift: LayoutKey? get() = modifiers[Modifier.SHIFT]

    /**
     * Get the key for ctrl modifier (convenience)
     */
    val ctrl: LayoutKey? get() = modifiers[Modifier.CTRL]

    /**
     * Get the key for alt modifier (convenience)
     */
    val alt: LayoutKey? get() = modifiers[Modifier.ALT]

    companion object {
        /**
         * Create a simple key without modifiers
         */
        fun of(char: String, ipa: String, name: String) = LayoutKey(char, ipa, name)

        /**
         * Create a key with shift modifier
         */
        fun withShift(
            char: String, ipa: String, name: String,
            shiftChar: String, shiftIpa: String, shiftName: String
        ) = LayoutKey(
            char, ipa, name,
            mapOf(Modifier.SHIFT to LayoutKey(shiftChar, shiftIpa, shiftName))
        )
    }
}

/**
 * Keyboard modifiers that can alter key output
 */
enum class Modifier {
    SHIFT,
    CTRL,
    ALT,
    CTRL_SHIFT,
    ALT_SHIFT,
    CTRL_ALT
}

/**
 * All keyboard layouts for Chatterbox 23 languages
 */
object KeyboardLayouts {

    private val QWERTY_ROW1 = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
    private val QWERTY_ROW2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l")
    private val QWERTY_ROW3 = listOf("z", "x", "c", "v", "b", "n", "m")

    /**
     * Helper to create a LayoutKey with optional shift modifier
     * @param char Base character
     * @param ipa IPA pronunciation
     * @param name Character name
     * @param shiftChar Shifted character (null if none)
     * @param shiftIpa Shifted IPA (defaults to same as base)
     * @param shiftName Shifted name (defaults to "shift-" + name)
     */
    private fun key(
        char: String,
        ipa: String,
        name: String,
        shiftChar: String? = null,
        shiftIpa: String? = null,
        shiftName: String? = null
    ): LayoutKey {
        val modifiers = if (shiftChar != null) {
            mapOf(Modifier.SHIFT to LayoutKey(
                shiftChar,
                shiftIpa ?: ipa,
                shiftName ?: "shift-$name"
            ))
        } else emptyMap()
        return LayoutKey(char, ipa, name, modifiers)
    }

    /**
     * Helper for simple key without shift
     */
    private fun key(char: String, ipa: String, name: String): LayoutKey =
        LayoutKey(char, ipa, name)

    // ═══ Arabic (ar) ═══
    val AR = KeyboardLayout(
        code = "ar",
        name = "Arabic",
        nativeName = "العربية",
        script = UniKeySyllable.Companion.Script.ARABIC,
        keys = mapOf(
            "q" to key("ض", "dˤ", "dad", "َ", "a", "fatha"),
            "w" to key("ص", "sˤ", "sad", "ً", "an", "tanwin-fath"),
            "e" to key("ث", "θ", "tha", "ُ", "u", "damma"),
            "r" to key("ق", "q", "qaf", "ٌ", "un", "tanwin-damm"),
            "t" to key("ف", "f", "fa", "ِ", "i", "kasra"),
            "y" to key("غ", "ɣ", "ghain", "ٍ", "in", "tanwin-kasr"),
            "u" to key("ع", "ʕ", "ain", "ّ", "", "shadda"),
            "i" to key("ه", "h", "ha", "ْ", "", "sukun"),
            "o" to key("خ", "x", "kha", "ـ", "", "tatweel"),
            "p" to key("ح", "ħ", "ha", "؛", "", "semicolon"),
            "a" to key("ش", "ʃ", "shin", "\\", "", "backslash"),
            "s" to key("س", "s", "sin"),
            "d" to key("ي", "j", "ya", "]", "", "bracket-right"),
            "f" to key("ب", "b", "ba", "[", "", "bracket-left"),
            "g" to key("ل", "l", "lam", "لأ", "laʔ", "lam-hamza"),
            "h" to key("ا", "", "alif", "أ", "ʔ", "alif-hamza"),
            "j" to key("ت", "t", "ta", "ـ", "", "tatweel"),
            "k" to key("ن", "n", "nun", "،", "", "comma"),
            "l" to key("م", "m", "mim", "/", "", "slash"),
            "z" to key("ئ", "ʔ", "hamza-ya", "~", "", "tilde"),
            "x" to key("ء", "ʔ", "hamza", "ْ", "", "sukun"),
            "c" to key("ؤ", "ʔ", "hamza-waw", "}", "", "brace-right"),
            "v" to key("ر", "r", "ra", "{", "", "brace-left"),
            "b" to key("لا", "la", "lam-alif", "لآ", "laː", "lam-alif-madda"),
            "n" to key("ى", "aː", "alif-maqsura", "آ", "ʔaː", "alif-madda"),
            "m" to key("ة", "a", "ta-marbuta", "'", "", "quote"),
            "," to key("و", "w", "waw", ",", "", "comma"),
            "." to key("ز", "z", "zay", ".", "", "period"),
            "/" to key("ظ", "ðˤ", "za", "؟", "", "question")
        )
    )

    // ═══ Danish (da) ═══
    val DA = KeyboardLayout(
        code = "da",
        name = "Danish",
        nativeName = "Dansk",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("kr", "krone", mapOf(
            ";" to key("æ", "ɛ", "ae", "Æ", "ɛ", "AE"),
            "'" to key("ø", "ø", "oe", "Ø", "ø", "OE"),
            "[" to key("å", "ɔ", "aa", "Å", "ɔ", "AA")
        ))
    )

    // ═══ German (de) ═══
    val DE = KeyboardLayout(
        code = "de",
        name = "German",
        nativeName = "Deutsch",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ö", "ø", "o-umlaut", "Ö", "ø", "O-umlaut"),
            "'" to key("ä", "ɛ", "a-umlaut", "Ä", "ɛ", "A-umlaut"),
            "[" to key("ü", "y", "u-umlaut", "Ü", "y", "U-umlaut"),
            "]" to key("ß", "s", "eszett", "ẞ", "s", "Eszett")
        ))
    )

    // ═══ Greek (el) ═══
    val EL = KeyboardLayout(
        code = "el",
        name = "Greek",
        nativeName = "Ελληνικά",
        script = UniKeySyllable.Companion.Script.GREEK,
        keys = mapOf(
            "q" to key(";", "", "semicolon", ":", "", "colon"),
            "w" to key("ς", "s", "final-sigma", "΅", "", "dialytika-tonos"),
            "e" to key("ε", "e", "epsilon", "Ε", "e", "Epsilon"),
            "r" to key("ρ", "r", "rho", "Ρ", "r", "Rho"),
            "t" to key("τ", "t", "tau", "Τ", "t", "Tau"),
            "y" to key("υ", "i", "upsilon", "Υ", "i", "Upsilon"),
            "u" to key("θ", "θ", "theta", "Θ", "θ", "Theta"),
            "i" to key("ι", "i", "iota", "Ι", "i", "Iota"),
            "o" to key("ο", "o", "omicron", "Ο", "o", "Omicron"),
            "p" to key("π", "p", "pi", "Π", "p", "Pi"),
            "a" to key("α", "a", "alpha", "Α", "a", "Alpha"),
            "s" to key("σ", "s", "sigma", "Σ", "s", "Sigma"),
            "d" to key("δ", "ð", "delta", "Δ", "ð", "Delta"),
            "f" to key("φ", "f", "phi", "Φ", "f", "Phi"),
            "g" to key("γ", "ɣ", "gamma", "Γ", "ɣ", "Gamma"),
            "h" to key("η", "i", "eta", "Η", "i", "Eta"),
            "j" to key("ξ", "ks", "xi", "Ξ", "ks", "Xi"),
            "k" to key("κ", "k", "kappa", "Κ", "k", "Kappa"),
            "l" to key("λ", "l", "lambda", "Λ", "l", "Lambda"),
            "z" to key("ζ", "z", "zeta", "Ζ", "z", "Zeta"),
            "x" to key("χ", "x", "chi", "Χ", "x", "Chi"),
            "c" to key("ψ", "ps", "psi", "Ψ", "ps", "Psi"),
            "v" to key("ω", "o", "omega", "Ω", "o", "Omega"),
            "b" to key("β", "v", "beta", "Β", "v", "Beta"),
            "n" to key("ν", "n", "nu", "Ν", "n", "Nu"),
            "m" to key("μ", "m", "mu", "Μ", "m", "Mu")
        )
    )

    // ═══ English US (en) ═══
    val EN = KeyboardLayout(
        code = "en",
        name = "English (US)",
        nativeName = "English",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English GB (en-gb) ═══
    val EN_GB = KeyboardLayout(
        code = "en-gb",
        name = "English (UK)",
        nativeName = "English",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("£", "pound", mapOf(
            "3" to key("3", "", "three", "£", "", "pound")
        ))
    )

    // ═══ Spanish (es) ═══
    val ES = KeyboardLayout(
        code = "es",
        name = "Spanish",
        nativeName = "Español",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ñ", "ɲ", "ene", "Ñ", "ɲ", "Ene"),
            "'" to key("´", "", "acute", "¨", "", "diaeresis"),
            "[" to key("¿", "", "inverted-question", "¡", "", "inverted-exclamation")
        ))
    )

    // ═══ Finnish (fi) ═══
    val FI = KeyboardLayout(
        code = "fi",
        name = "Finnish",
        nativeName = "Suomi",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ö", "ø", "o-umlaut", "Ö", "ø", "O-umlaut"),
            "'" to key("ä", "æ", "a-umlaut", "Ä", "æ", "A-umlaut")
        ))
    )

    // ═══ French (fr) ═══
    val FR = KeyboardLayout(
        code = "fr",
        name = "French",
        nativeName = "Français",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("é", "e", "e-acute", "É", "e", "E-acute"),
            "'" to key("è", "ɛ", "e-grave", "È", "ɛ", "E-grave"),
            "[" to key("ç", "s", "c-cedilla", "Ç", "s", "C-cedilla"),
            "]" to key("à", "a", "a-grave", "À", "a", "A-grave")
        ))
    )

    // ═══ Hebrew (he) ═══
    val HE = KeyboardLayout(
        code = "he",
        name = "Hebrew",
        nativeName = "עברית",
        script = UniKeySyllable.Companion.Script.HEBREW,
        keys = mapOf(
            "t" to key("א", "ʔ", "alef"),
            "c" to key("ב", "v", "bet"),
            "d" to key("ג", "g", "gimel"),
            "s" to key("ד", "d", "dalet"),
            "v" to key("ה", "h", "he"),
            "u" to key("ו", "v", "vav"),
            "z" to key("ז", "z", "zayin"),
            "j" to key("ח", "x", "chet"),
            "y" to key("ט", "t", "tet"),
            "h" to key("י", "j", "yod"),
            "l" to key("ך", "x", "kaf-sofit"),
            "f" to key("כ", "x", "kaf"),
            "k" to key("ל", "l", "lamed"),
            "o" to key("ם", "m", "mem-sofit"),
            "n" to key("מ", "m", "mem"),
            "i" to key("ן", "n", "nun-sofit"),
            "b" to key("נ", "n", "nun"),
            "x" to key("ס", "s", "samech"),
            "g" to key("ע", "ʕ", "ayin"),
            ";" to key("ף", "f", "pe-sofit"),
            "p" to key("פ", "f", "pe"),
            "." to key("ץ", "ts", "tsadi-sofit"),
            "m" to key("צ", "ts", "tsadi"),
            "e" to key("ק", "k", "qof"),
            "r" to key("ר", "ʁ", "resh"),
            "a" to key("ש", "ʃ", "shin"),
            "," to key("ת", "t", "tav"),
            "4" to key("₪", "", "shekel", "$", "", "dollar")
        )
    )

    // ═══ Hindi (hi) ═══
    val HI = KeyboardLayout(
        code = "hi",
        name = "Hindi",
        nativeName = "हिन्दी",
        script = UniKeySyllable.Companion.Script.DEVANAGARI,
        keys = mapOf(
            // Vowels (with matra shifts)
            "q" to key("औ", "ɔː", "au", "ौ", "ɔː", "au-matra"),
            "w" to key("ऐ", "ɛː", "ai", "ै", "ɛː", "ai-matra"),
            "e" to key("आ", "aː", "aa", "ा", "aː", "aa-matra"),
            "r" to key("ई", "iː", "ii", "ी", "iː", "ii-matra"),
            "t" to key("ऊ", "uː", "uu", "ू", "uː", "uu-matra"),
            // Consonants row 1
            "y" to key("भ", "bʱ", "bha"),
            "u" to key("ङ", "ŋ", "nga"),
            "i" to key("घ", "ɡʱ", "gha"),
            "o" to key("ध", "dʱ", "dha"),
            "p" to key("झ", "dʒʱ", "jha"),
            // Consonants row 2
            "a" to key("ओ", "oː", "o", "ो", "oː", "o-matra"),
            "s" to key("ए", "eː", "e", "े", "eː", "e-matra"),
            "d" to key("अ", "ə", "a"),
            "f" to key("इ", "ɪ", "i", "ि", "ɪ", "i-matra"),
            "g" to key("उ", "ʊ", "u", "ु", "ʊ", "u-matra"),
            "h" to key("प", "p", "pa"),
            "j" to key("र", "r", "ra", "्", "", "virama"),
            "k" to key("क", "k", "ka"),
            "l" to key("त", "t", "ta"),
            // Consonants row 3
            "z" to key("ॉ", "ɔ", "candra-o"),
            "x" to key("ँ", "̃", "chandrabindu"),
            "c" to key("म", "m", "ma"),
            "v" to key("न", "n", "na"),
            "b" to key("ब", "b", "ba"),
            "n" to key("ल", "l", "la"),
            "m" to key("स", "s", "sa"),
            "," to key("व", "ʋ", "va", "ृ", "ɻ", "ri-matra"),
            "." to key("य", "j", "ya"),
            "/" to key("ज", "dʒ", "ja"),
            "4" to key("₹", "", "rupee", "$", "", "dollar")
        )
    )

    // ═══ Italian (it) ═══
    val IT = KeyboardLayout(
        code = "it",
        name = "Italian",
        nativeName = "Italiano",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ò", "ɔ", "o-grave", "Ò", "ɔ", "O-grave"),
            "'" to key("à", "a", "a-grave", "À", "a", "A-grave"),
            "[" to key("è", "ɛ", "e-grave", "È", "ɛ", "E-grave"),
            "]" to key("ù", "u", "u-grave", "Ù", "u", "U-grave"),
            "\\" to key("ì", "i", "i-grave", "Ì", "i", "I-grave")
        ))
    )

    // ═══ Japanese (ja) ═══
    val JA = KeyboardLayout(
        code = "ja",
        name = "Japanese",
        nativeName = "日本語",
        script = UniKeySyllable.Companion.Script.HIRAGANA,
        keys = mapOf(
            // Hiragana with katakana shift
            "a" to key("あ", "a", "a", "ア", "a", "a-katakana"),
            "i" to key("い", "i", "i", "イ", "i", "i-katakana"),
            "u" to key("う", "ɯ", "u", "ウ", "ɯ", "u-katakana"),
            "e" to key("え", "e", "e", "エ", "e", "e-katakana"),
            "o" to key("お", "o", "o", "オ", "o", "o-katakana"),
            "k" to key("か", "k", "ka", "カ", "k", "ka-katakana"),
            "s" to key("さ", "s", "sa", "サ", "s", "sa-katakana"),
            "t" to key("た", "t", "ta", "タ", "t", "ta-katakana"),
            "n" to key("な", "n", "na", "ナ", "n", "na-katakana"),
            "h" to key("は", "h", "ha", "ハ", "h", "ha-katakana"),
            "m" to key("ま", "m", "ma", "マ", "m", "ma-katakana"),
            "y" to key("や", "j", "ya", "ヤ", "j", "ya-katakana"),
            "r" to key("ら", "ɾ", "ra", "ラ", "ɾ", "ra-katakana"),
            "w" to key("わ", "w", "wa", "ワ", "w", "wa-katakana"),
            "g" to key("が", "ɡ", "ga", "ガ", "ɡ", "ga-katakana"),
            "z" to key("ざ", "z", "za", "ザ", "z", "za-katakana"),
            "d" to key("だ", "d", "da", "ダ", "d", "da-katakana"),
            "b" to key("ば", "b", "ba", "バ", "b", "ba-katakana"),
            "p" to key("ぱ", "p", "pa", "パ", "p", "pa-katakana"),
            "f" to key("ふ", "ɸ", "fu", "フ", "ɸ", "fu-katakana"),
            "j" to key("じ", "dʒ", "ji", "ジ", "dʒ", "ji-katakana"),
            "c" to key("ち", "tɕ", "chi", "チ", "tɕ", "chi-katakana"),
            "x" to key("っ", "", "small-tsu", "ッ", "", "small-tsu-katakana"),
            "v" to key("ん", "n", "n", "ン", "n", "n-katakana"),
            "l" to key("ー", "ː", "long-vowel"),
            "q" to key("。", "", "period"),
            "/" to key("・", "", "middle-dot"),
            "4" to key("¥", "", "yen", "$", "", "dollar")
        )
    )

    // ═══ Korean (ko) ═══
    val KO = KeyboardLayout(
        code = "ko",
        name = "Korean",
        nativeName = "한국어",
        script = UniKeySyllable.Companion.Script.HANGUL,
        keys = mapOf(
            // Consonants (초성) with tensed shift
            "q" to key("ㅂ", "p", "bieup", "ㅃ", "p͈", "ssang-bieup"),
            "w" to key("ㅈ", "tɕ", "jieut", "ㅉ", "t͈ɕ", "ssang-jieut"),
            "e" to key("ㄷ", "t", "digeut", "ㄸ", "t͈", "ssang-digeut"),
            "r" to key("ㄱ", "k", "giyeok", "ㄲ", "k͈", "ssang-giyeok"),
            "t" to key("ㅅ", "s", "siot", "ㅆ", "s͈", "ssang-siot"),
            "a" to key("ㅁ", "m", "mieum"),
            "s" to key("ㄴ", "n", "nieun"),
            "d" to key("ㅇ", "ŋ", "ieung"),
            "f" to key("ㄹ", "ɾ", "rieul"),
            "g" to key("ㅎ", "h", "hieut"),
            "z" to key("ㅋ", "kʰ", "kieuk"),
            "x" to key("ㅌ", "tʰ", "tieut"),
            "c" to key("ㅊ", "tɕʰ", "chieut"),
            "v" to key("ㅍ", "pʰ", "pieup"),
            // Vowels (중성)
            "y" to key("ㅛ", "jo", "yo"),
            "u" to key("ㅕ", "jʌ", "yeo"),
            "i" to key("ㅑ", "ja", "ya"),
            "o" to key("ㅐ", "ɛ", "ae", "ㅒ", "jɛ", "yae"),
            "p" to key("ㅔ", "e", "e", "ㅖ", "je", "ye"),
            "h" to key("ㅗ", "o", "o"),
            "j" to key("ㅓ", "ʌ", "eo"),
            "k" to key("ㅏ", "a", "a"),
            "l" to key("ㅣ", "i", "i"),
            "b" to key("ㅠ", "ju", "yu"),
            "n" to key("ㅜ", "u", "u"),
            "m" to key("ㅡ", "ɨ", "eu"),
            "4" to key("₩", "", "won", "$", "", "dollar")
        )
    )

    // ═══ Malay (ms) ═══
    val MS = KeyboardLayout(
        code = "ms",
        name = "Malay",
        nativeName = "Bahasa Melayu",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("RM", "ringgit", emptyMap())
    )

    // ═══ Dutch (nl) ═══
    val NL = KeyboardLayout(
        code = "nl",
        name = "Dutch",
        nativeName = "Nederlands",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("€", "euro", mapOf(
            "'" to key("´", "", "acute", "¨", "", "diaeresis"),
            "[" to key("ij", "ɛi", "ij", "IJ", "ɛi", "IJ")
        ))
    )

    // ═══ Norwegian (no) ═══
    val NO = KeyboardLayout(
        code = "no",
        name = "Norwegian",
        nativeName = "Norsk",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("kr", "krone", mapOf(
            ";" to key("ø", "ø", "oe", "Ø", "ø", "OE"),
            "'" to key("æ", "æ", "ae", "Æ", "æ", "AE"),
            "[" to key("å", "ɔ", "aa", "Å", "ɔ", "AA")
        ))
    )

    // ═══ Polish (pl) ═══
    val PL = KeyboardLayout(
        code = "pl",
        name = "Polish",
        nativeName = "Polski",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("zł", "zloty", mapOf(
            "a" to key("a", "a", "a", "ą", "ɔ̃", "a-ogonek"),
            "c" to key("c", "ts", "c", "ć", "tɕ", "c-acute"),
            "e" to key("e", "e", "e", "ę", "ɛ̃", "e-ogonek"),
            "l" to key("l", "l", "l", "ł", "w", "l-stroke"),
            "n" to key("n", "n", "n", "ń", "ɲ", "n-acute"),
            "o" to key("o", "o", "o", "ó", "u", "o-acute"),
            "s" to key("s", "s", "s", "ś", "ɕ", "s-acute"),
            "z" to key("z", "z", "z", "ż", "ʐ", "z-dot"),
            "x" to key("x", "ks", "x", "ź", "ʑ", "z-acute")
        ))
    )

    // ═══ Portuguese (pt) ═══
    val PT = KeyboardLayout(
        code = "pt",
        name = "Portuguese",
        nativeName = "Português",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ç", "s", "c-cedilla", "Ç", "s", "C-cedilla"),
            "'" to key("~", "", "tilde", "^", "", "circumflex"),
            "[" to key("´", "", "acute", "`", "", "grave"),
            "]" to key("ã", "ɐ̃", "a-tilde", "Ã", "ɐ̃", "A-tilde")
        ))
    )

    // ═══ Portuguese Brazil (pt-br) ═══
    val PT_BR = KeyboardLayout(
        code = "pt-br",
        name = "Portuguese (Brazil)",
        nativeName = "Português (Brasil)",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("R$", "real", mapOf(
            ";" to key("ç", "s", "c-cedilla", "Ç", "s", "C-cedilla"),
            "'" to key("~", "", "tilde", "^", "", "circumflex"),
            "[" to key("´", "", "acute", "`", "", "grave"),
            "]" to key("ã", "ɐ̃", "a-tilde", "Ã", "ɐ̃", "A-tilde")
        ))
    )

    // ═══ Russian (ru) ═══
    val RU = KeyboardLayout(
        code = "ru",
        name = "Russian",
        nativeName = "Русский",
        script = UniKeySyllable.Companion.Script.CYRILLIC,
        keys = mapOf(
            "q" to key("й", "j", "short-i", "Й", "j", "Short-I"),
            "w" to key("ц", "ts", "tse", "Ц", "ts", "Tse"),
            "e" to key("у", "u", "u", "У", "u", "U"),
            "r" to key("к", "k", "ka", "К", "k", "Ka"),
            "t" to key("е", "je", "ye", "Е", "je", "Ye"),
            "y" to key("н", "n", "en", "Н", "n", "En"),
            "u" to key("г", "ɡ", "ge", "Г", "ɡ", "Ge"),
            "i" to key("ш", "ʃ", "sha", "Ш", "ʃ", "Sha"),
            "o" to key("щ", "ʃtʃ", "shcha", "Щ", "ʃtʃ", "Shcha"),
            "p" to key("з", "z", "ze", "З", "z", "Ze"),
            "[" to key("х", "x", "kha", "Х", "x", "Kha"),
            "]" to key("ъ", "", "hard-sign", "Ъ", "", "Hard-sign"),
            "a" to key("ф", "f", "ef", "Ф", "f", "Ef"),
            "s" to key("ы", "ɨ", "yeru", "Ы", "ɨ", "Yeru"),
            "d" to key("в", "v", "ve", "В", "v", "Ve"),
            "f" to key("а", "a", "a", "А", "a", "A"),
            "g" to key("п", "p", "pe", "П", "p", "Pe"),
            "h" to key("р", "r", "er", "Р", "r", "Er"),
            "j" to key("о", "o", "o", "О", "o", "O"),
            "k" to key("л", "l", "el", "Л", "l", "El"),
            "l" to key("д", "d", "de", "Д", "d", "De"),
            ";" to key("ж", "ʒ", "zhe", "Ж", "ʒ", "Zhe"),
            "'" to key("э", "e", "e", "Э", "e", "E"),
            "z" to key("я", "ja", "ya", "Я", "ja", "Ya"),
            "x" to key("ч", "tʃ", "che", "Ч", "tʃ", "Che"),
            "c" to key("с", "s", "es", "С", "s", "Es"),
            "v" to key("м", "m", "em", "М", "m", "Em"),
            "b" to key("и", "i", "i", "И", "i", "I"),
            "n" to key("т", "t", "te", "Т", "t", "Te"),
            "m" to key("ь", "", "soft-sign", "Ь", "", "Soft-sign"),
            "," to key("б", "b", "be", "Б", "b", "Be"),
            "." to key("ю", "ju", "yu", "Ю", "ju", "Yu"),
            "4" to key("₽", "", "ruble", "$", "", "dollar")
        )
    )

    // ═══ Swedish (sv) ═══
    val SV = KeyboardLayout(
        code = "sv",
        name = "Swedish",
        nativeName = "Svenska",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("kr", "krona", mapOf(
            ";" to key("ö", "ø", "o-umlaut", "Ö", "ø", "O-umlaut"),
            "'" to key("ä", "ɛ", "a-umlaut", "Ä", "ɛ", "A-umlaut"),
            "[" to key("å", "ɔ", "a-ring", "Å", "ɔ", "A-ring")
        ))
    )

    // ═══ Swahili (sw) ═══
    val SW = KeyboardLayout(
        code = "sw",
        name = "Swahili",
        nativeName = "Kiswahili",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("TSh", "shilling", emptyMap())
    )

    // ═══ Turkish (tr) ═══
    val TR = KeyboardLayout(
        code = "tr",
        name = "Turkish",
        nativeName = "Türkçe",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("₺", "lira", mapOf(
            "i" to key("ı", "ɯ", "dotless-i", "I", "ɯ", "Dotless-I"),
            ";" to key("ş", "ʃ", "s-cedilla", "Ş", "ʃ", "S-cedilla"),
            "'" to key("i", "i", "dotted-i", "İ", "i", "Dotted-I"),
            "[" to key("ğ", "ɣ", "g-breve", "Ğ", "ɣ", "G-breve"),
            "]" to key("ü", "y", "u-umlaut", "Ü", "y", "U-umlaut"),
            "\\" to key("ç", "tʃ", "c-cedilla", "Ç", "tʃ", "C-cedilla"),
            "/" to key("ö", "ø", "o-umlaut", "Ö", "ø", "O-umlaut")
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
            "b" to key("b", "p", "bo"),
            "p" to key("p", "pʰ", "po"),
            "m" to key("m", "m", "mo"),
            "f" to key("f", "f", "fo"),
            "d" to key("d", "t", "de"),
            "t" to key("t", "tʰ", "te"),
            "n" to key("n", "n", "ne"),
            "l" to key("l", "l", "le"),
            "g" to key("g", "k", "ge"),
            "k" to key("k", "kʰ", "ke"),
            "h" to key("h", "x", "he"),
            "j" to key("j", "tɕ", "ji"),
            "q" to key("q", "tɕʰ", "qi"),
            "x" to key("x", "ɕ", "xi"),
            "z" to key("z", "ts", "zi"),
            "c" to key("c", "tsʰ", "ci"),
            "s" to key("s", "s", "si"),
            "r" to key("r", "ɻ", "ri"),
            "y" to key("y", "j", "yi"),
            "w" to key("w", "w", "wu"),
            // Pinyin finals
            "a" to key("a", "a", "a"),
            "o" to key("o", "o", "o"),
            "e" to key("e", "ə", "e"),
            "i" to key("i", "i", "i"),
            "u" to key("u", "u", "u"),
            "v" to key("ü", "y", "u-umlaut"),
            "4" to key("¥", "", "yuan", "$", "", "dollar")
        )
    )

    // ═══ English Australia (en-au) ═══
    val EN_AU = KeyboardLayout(
        code = "en-au",
        name = "English (Australia)",
        nativeName = "English",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English Canada (en-ca) ═══
    val EN_CA = KeyboardLayout(
        code = "en-ca",
        name = "English (Canada)",
        nativeName = "English",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English India (en-in) ═══
    val EN_IN = KeyboardLayout(
        code = "en-in",
        name = "English (India)",
        nativeName = "English",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("₹", "rupee", emptyMap())
    )

    // ═══ Spanish Latin America (es-419) ═══
    val ES_419 = KeyboardLayout(
        code = "es-419",
        name = "Spanish (Latin America)",
        nativeName = "Español (Latinoamérica)",
        script = UniKeySyllable.Companion.Script.LATIN,
        keys = buildLatinLayout("$", "dollar", mapOf(
            ";" to key("ñ", "ɲ", "ene", "Ñ", "ɲ", "Ene"),
            "'" to key("´", "", "acute", "¨", "", "diaeresis"),
            "[" to key("¿", "", "inverted-question", "¡", "", "inverted-exclamation")
        ))
    )

    /**
     * Build a Latin-based layout with currency and overrides
     */
    private fun buildLatinLayout(
        currency: String,
        currencyName: String,
        overrides: Map<String, LayoutKey>
    ): Map<String, LayoutKey> {
        val base = mutableMapOf<String, LayoutKey>()

        // Standard QWERTY letters with shift for uppercase
        for (c in 'a'..'z') {
            val lower = c.toString()
            val upper = lower.uppercase()
            val ipa = getLatinIpa(c)
            base[lower] = key(lower, ipa, lower, upper, ipa, upper)
        }

        // Numbers and symbols - currency on shift+4
        base["1"] = key("1", "", "one", "!", "", "exclamation")
        base["2"] = key("2", "", "two", "@", "", "at")
        base["3"] = key("3", "", "three", "#", "", "hash")
        base["4"] = key("4", "", "four", currency, "", currencyName)
        base["5"] = key("5", "", "five", "%", "", "percent")
        base["6"] = key("6", "", "six", "^", "", "caret")
        base["7"] = key("7", "", "seven", "&", "", "ampersand")
        base["8"] = key("8", "", "eight", "*", "", "asterisk")
        base["9"] = key("9", "", "nine", "(", "", "left-paren")
        base["0"] = key("0", "", "zero", ")", "", "right-paren")

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
     * Includes regional variants (en-gb, en-au, etc.)
     */
    val layouts: Map<String, KeyboardLayout> = mapOf(
        // Core 23 Chatterbox languages
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
        "zh" to ZH,
        // Regional variants
        "en-us" to EN,        // US English (alias)
        "en-gb" to EN_GB,     // UK English
        "en-au" to EN_AU,     // Australian English
        "en-ca" to EN_CA,     // Canadian English
        "en-in" to EN_IN,     // Indian English
        "pt-br" to PT_BR,     // Brazilian Portuguese
        "es-419" to ES_419    // Latin American Spanish
    )

    /**
     * Get layout by language code (case-insensitive)
     */
    fun get(code: String): KeyboardLayout? = layouts[code.lowercase()]

    /**
     * Get all supported language codes (base languages only)
     */
    val supportedLanguages: List<String> = layouts.keys.filter { !it.contains("-") || it == "en-gb" || it == "pt-br" || it == "es-419" }.sorted()

    /**
     * Get all supported language codes including all variants
     */
    val allLanguageCodes: List<String> = layouts.keys.toList().sorted()
}
