package al.clk.key

/**
 * Keyboard Layout - defines character mappings for each language
 * Supports all 23 Chatterbox multilingual TTS languages.
 * Uses ILayoutKey interface so enums can be used directly as keys.
 */
data class KeyboardLayout(
    val code: String,           // ISO 639-1 language code
    val name: String,           // Display name
    val nativeName: String,     // Name in native script
    val script: Script,
    val keys: Map<String, ILayoutKey>
)

/**
 * A single key in a layout - immutable phonetic key definition.
 * Implements ILayoutKey and adds support for modifier-specific keys.
 * Use SimpleKey for basic cases, LayoutKey for complex modifier mappings.
 */
data class LayoutKey(
    override val char: String,
    override val ipa: String,
    override val displayName: String,
    private val modifiers: Map<Modifier, ILayoutKey> = emptyMap()
) : ILayoutKey {
    // Legacy alias
    val name: String get() = displayName

    // Legacy constructor for compatibility - convert LayoutKey map to ILayoutKey map
    @Suppress("UNCHECKED_CAST")
    constructor(char: String, ipa: String, name: String, modifiers: Map<Modifier, LayoutKey>) :
        this(char, ipa, name, modifiers as Map<Modifier, ILayoutKey>)

    override fun withModifier(mod: Modifier): ILayoutKey? = modifiers[mod]
    override val shiftKey: ILayoutKey? get() = modifiers[Modifier.SHIFT]
    override val ctrlKey: ILayoutKey? get() = modifiers[Modifier.CTRL]
    override val altKey: ILayoutKey? get() = modifiers[Modifier.ALT]

    // Convenience properties (return LayoutKey for compatibility where needed)
    val shift: LayoutKey? get() = modifiers[Modifier.SHIFT] as? LayoutKey
    val ctrl: LayoutKey? get() = modifiers[Modifier.CTRL] as? LayoutKey
    val alt: LayoutKey? get() = modifiers[Modifier.ALT] as? LayoutKey

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
        script = Script.ARABIC,
        keys = ArabicLetter.keys
    )

    // ═══ Danish (da) ═══
    val DA = KeyboardLayout(
        code = "da",
        name = "Danish",
        nativeName = "Dansk",
        script = Script.LATIN,
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
        script = Script.LATIN,
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
        script = Script.GREEK,
        keys = GreekKey.keys
    )

    // ═══ English US (en) ═══
    val EN = KeyboardLayout(
        code = "en",
        name = "English (US)",
        nativeName = "English",
        script = Script.LATIN,
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English GB (en-gb) ═══
    val EN_GB = KeyboardLayout(
        code = "en-gb",
        name = "English (UK)",
        nativeName = "English",
        script = Script.LATIN,
        keys = buildLatinLayout("£", "pound", mapOf(
            "3" to key("3", "", "three", "£", "", "pound")
        ))
    )

    // ═══ Spanish (es) ═══
    val ES = KeyboardLayout(
        code = "es",
        name = "Spanish",
        nativeName = "Español",
        script = Script.LATIN,
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
        script = Script.LATIN,
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
        script = Script.LATIN,
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
        script = Script.HEBREW,
        keys = HebrewLetter.keys + mapOf("4" to key("₪", "", "shekel", "$", "", "dollar"))
    )

    // ═══ Hindi (hi) ═══
    val HI = KeyboardLayout(
        code = "hi",
        name = "Hindi",
        nativeName = "हिन्दी",
        script = Script.DEVANAGARI,
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
        script = Script.LATIN,
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
        script = Script.HIRAGANA,
        keys = HiraganaKey.keys + mapOf(
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
        script = Script.HANGUL,
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
        script = Script.LATIN,
        keys = buildLatinLayout("RM", "ringgit", emptyMap())
    )

    // ═══ Dutch (nl) ═══
    val NL = KeyboardLayout(
        code = "nl",
        name = "Dutch",
        nativeName = "Nederlands",
        script = Script.LATIN,
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
        script = Script.LATIN,
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
        script = Script.LATIN,
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
        script = Script.LATIN,
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
        script = Script.LATIN,
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
        script = Script.CYRILLIC,
        keys = CyrillicKey.keys + mapOf("4" to key("₽", "", "ruble", "$", "", "dollar"))
    )

    // ═══ Swedish (sv) ═══
    val SV = KeyboardLayout(
        code = "sv",
        name = "Swedish",
        nativeName = "Svenska",
        script = Script.LATIN,
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
        script = Script.LATIN,
        keys = buildLatinLayout("TSh", "shilling", emptyMap())
    )

    // ═══ Turkish (tr) ═══
    val TR = KeyboardLayout(
        code = "tr",
        name = "Turkish",
        nativeName = "Türkçe",
        script = Script.LATIN,
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
        script = Script.CJK,
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
        script = Script.LATIN,
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English Canada (en-ca) ═══
    val EN_CA = KeyboardLayout(
        code = "en-ca",
        name = "English (Canada)",
        nativeName = "English",
        script = Script.LATIN,
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English India (en-in) ═══
    val EN_IN = KeyboardLayout(
        code = "en-in",
        name = "English (India)",
        nativeName = "English",
        script = Script.LATIN,
        keys = buildLatinLayout("₹", "rupee", emptyMap())
    )

    // ═══ Spanish Latin America (es-419) ═══
    val ES_419 = KeyboardLayout(
        code = "es-419",
        name = "Spanish (Latin America)",
        nativeName = "Español (Latinoamérica)",
        script = Script.LATIN,
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
            val ipa = LatinKey.fromChar(lower)?.ipa ?: ""
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

    /**
     * All layouts - used to generate layouts map
     */
    private val allLayouts: List<KeyboardLayout> = listOf(
        AR, DA, DE, EL, EN, ES, FI, FR, HE, HI, IT, JA, KO, MS, NL, NO, PL, PT, RU, SV, SW, TR, ZH,
        EN_GB, EN_AU, EN_CA, EN_IN, PT_BR, ES_419
    )

    /**
     * All layouts by language code - generated from allLayouts list
     * Includes regional variants (en-gb, en-au, etc.)
     */
    val layouts: Map<String, KeyboardLayout> = allLayouts.associateBy { it.code } +
        mapOf("en-us" to EN)  // US English alias

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
