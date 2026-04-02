package al.clk.key

/**
 * Keyboard Layout - defines character mappings for each language
 * Supports all 23 Chatterbox multilingual TTS languages.
 * Uses ILayoutKey interface so enums can be used directly as keys.
 */
data class KeyboardLayout(
    val lang: Lang,             // Language enum (source of truth)
    val name: String,           // Display name
    val nativeName: String,     // Name in native script
    val script: Script = lang.script,  // Derived from Lang
    val keys: Map<String, ILayoutKey>
) {
    /** ISO 639-1 language code (derived from Lang) */
    val code: String get() = lang.code
}

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
        lang = Lang.AR,
        name = "Arabic",
        nativeName = "العربية",
        keys = ArabicLetter.keys
    )

    // ═══ Danish (da) ═══
    val DA = KeyboardLayout(
        lang = Lang.DA,
        name = "Danish",
        nativeName = "Dansk",
        keys = buildLatinLayout("kr", "krone", mapOf(
            ";" to key("æ", "ɛ", "ae", "Æ", "ɛ", "AE"),
            "'" to key("ø", "ø", "oe", "Ø", "ø", "OE"),
            "[" to key("å", "ɔ", "aa", "Å", "ɔ", "AA")
        ))
    )

    // ═══ German (de) ═══
    val DE = KeyboardLayout(
        lang = Lang.DE,
        name = "German",
        nativeName = "Deutsch",
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ö", "ø", "o-umlaut", "Ö", "ø", "O-umlaut"),
            "'" to key("ä", "ɛ", "a-umlaut", "Ä", "ɛ", "A-umlaut"),
            "[" to key("ü", "y", "u-umlaut", "Ü", "y", "U-umlaut"),
            "]" to key("ß", "s", "eszett", "ẞ", "s", "Eszett")
        ))
    )

    // ═══ Greek (el) ═══
    val EL = KeyboardLayout(
        lang = Lang.EL,
        name = "Greek",
        nativeName = "Ελληνικά",
        keys = GreekKey.keys
    )

    // ═══ English US (en) ═══
    val EN = KeyboardLayout(
        lang = Lang.EN,
        name = "English (US)",
        nativeName = "English",
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English GB (en-gb) ═══
    val EN_GB = KeyboardLayout(
        lang = Lang.EN_GB,
        name = "English (UK)",
        nativeName = "English",
        keys = buildLatinLayout("£", "pound", mapOf(
            "3" to key("3", "", "three", "£", "", "pound")
        ))
    )

    // ═══ Spanish (es) ═══
    val ES = KeyboardLayout(
        lang = Lang.ES,
        name = "Spanish",
        nativeName = "Español",
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ñ", "ɲ", "ene", "Ñ", "ɲ", "Ene"),
            "'" to key("´", "", "acute", "¨", "", "diaeresis"),
            "[" to key("¿", "", "inverted-question", "¡", "", "inverted-exclamation")
        ))
    )

    // ═══ Finnish (fi) ═══
    val FI = KeyboardLayout(
        lang = Lang.FI,
        name = "Finnish",
        nativeName = "Suomi",
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ö", "ø", "o-umlaut", "Ö", "ø", "O-umlaut"),
            "'" to key("ä", "æ", "a-umlaut", "Ä", "æ", "A-umlaut")
        ))
    )

    // ═══ French (fr) ═══
    val FR = KeyboardLayout(
        lang = Lang.FR,
        name = "French",
        nativeName = "Français",
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("é", "e", "e-acute", "É", "e", "E-acute"),
            "'" to key("è", "ɛ", "e-grave", "È", "ɛ", "E-grave"),
            "[" to key("ç", "s", "c-cedilla", "Ç", "s", "C-cedilla"),
            "]" to key("à", "a", "a-grave", "À", "a", "A-grave")
        ))
    )

    // ═══ Hebrew (he) ═══
    val HE = KeyboardLayout(
        lang = Lang.HE,
        name = "Hebrew",
        nativeName = "עברית",
        keys = HebrewLetter.keys + mapOf("4" to key("₪", "", "shekel", "$", "", "dollar"))
    )

    // ═══ Hindi (hi) ═══
    val HI = KeyboardLayout(
        lang = Lang.HI,
        name = "Hindi",
        nativeName = "हिन्दी",
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
        lang = Lang.IT,
        name = "Italian",
        nativeName = "Italiano",
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
        lang = Lang.JA,
        name = "Japanese",
        nativeName = "日本語",
        keys = HiraganaKey.keys + mapOf(
            "l" to key("ー", "ː", "long-vowel"),
            "q" to key("。", "", "period"),
            "/" to key("・", "", "middle-dot"),
            "4" to key("¥", "", "yen", "$", "", "dollar")
        )
    )

    // ═══ Korean (ko) ═══
    val KO = KeyboardLayout(
        lang = Lang.KO,
        name = "Korean",
        nativeName = "한국어",
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
        lang = Lang.MS,
        name = "Malay",
        nativeName = "Bahasa Melayu",
        keys = buildLatinLayout("RM", "ringgit", emptyMap())
    )

    // ═══ Dutch (nl) ═══
    val NL = KeyboardLayout(
        lang = Lang.NL,
        name = "Dutch",
        nativeName = "Nederlands",
        keys = buildLatinLayout("€", "euro", mapOf(
            "'" to key("´", "", "acute", "¨", "", "diaeresis"),
            "[" to key("ij", "ɛi", "ij", "IJ", "ɛi", "IJ")
        ))
    )

    // ═══ Norwegian (no) ═══
    val NO = KeyboardLayout(
        lang = Lang.NO,
        name = "Norwegian",
        nativeName = "Norsk",
        keys = buildLatinLayout("kr", "krone", mapOf(
            ";" to key("ø", "ø", "oe", "Ø", "ø", "OE"),
            "'" to key("æ", "æ", "ae", "Æ", "æ", "AE"),
            "[" to key("å", "ɔ", "aa", "Å", "ɔ", "AA")
        ))
    )

    // ═══ Polish (pl) ═══
    val PL = KeyboardLayout(
        lang = Lang.PL,
        name = "Polish",
        nativeName = "Polski",
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
        lang = Lang.PT,
        name = "Portuguese",
        nativeName = "Português",
        keys = buildLatinLayout("€", "euro", mapOf(
            ";" to key("ç", "s", "c-cedilla", "Ç", "s", "C-cedilla"),
            "'" to key("~", "", "tilde", "^", "", "circumflex"),
            "[" to key("´", "", "acute", "`", "", "grave"),
            "]" to key("ã", "ɐ̃", "a-tilde", "Ã", "ɐ̃", "A-tilde")
        ))
    )

    // ═══ Portuguese Brazil (pt-br) ═══
    val PT_BR = KeyboardLayout(
        lang = Lang.PT_BR,
        name = "Portuguese (Brazil)",
        nativeName = "Português (Brasil)",
        keys = buildLatinLayout("R$", "real", mapOf(
            ";" to key("ç", "s", "c-cedilla", "Ç", "s", "C-cedilla"),
            "'" to key("~", "", "tilde", "^", "", "circumflex"),
            "[" to key("´", "", "acute", "`", "", "grave"),
            "]" to key("ã", "ɐ̃", "a-tilde", "Ã", "ɐ̃", "A-tilde")
        ))
    )

    // ═══ Russian (ru) ═══
    val RU = KeyboardLayout(
        lang = Lang.RU,
        name = "Russian",
        nativeName = "Русский",
        keys = CyrillicKey.keys + mapOf("4" to key("₽", "", "ruble", "$", "", "dollar"))
    )

    // ═══ Swedish (sv) ═══
    val SV = KeyboardLayout(
        lang = Lang.SV,
        name = "Swedish",
        nativeName = "Svenska",
        keys = buildLatinLayout("kr", "krona", mapOf(
            ";" to key("ö", "ø", "o-umlaut", "Ö", "ø", "O-umlaut"),
            "'" to key("ä", "ɛ", "a-umlaut", "Ä", "ɛ", "A-umlaut"),
            "[" to key("å", "ɔ", "a-ring", "Å", "ɔ", "A-ring")
        ))
    )

    // ═══ Swahili (sw) ═══
    val SW = KeyboardLayout(
        lang = Lang.SW,
        name = "Swahili",
        nativeName = "Kiswahili",
        keys = buildLatinLayout("TSh", "shilling", emptyMap())
    )

    // ═══ Turkish (tr) ═══
    val TR = KeyboardLayout(
        lang = Lang.TR,
        name = "Turkish",
        nativeName = "Türkçe",
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
        lang = Lang.ZH,
        name = "Chinese",
        nativeName = "中文",
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
        lang = Lang.EN_AU,
        name = "English (Australia)",
        nativeName = "English",
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English Canada (en-ca) ═══
    val EN_CA = KeyboardLayout(
        lang = Lang.EN_CA,
        name = "English (Canada)",
        nativeName = "English",
        keys = buildLatinLayout("$", "dollar", emptyMap())
    )

    // ═══ English India (en-in) ═══
    val EN_IN = KeyboardLayout(
        lang = Lang.EN_IN,
        name = "English (India)",
        nativeName = "English",
        keys = buildLatinLayout("₹", "rupee", emptyMap())
    )

    // ═══ Spanish Latin America (es-419) ═══
    val ES_419 = KeyboardLayout(
        lang = Lang.ES_419,
        name = "Spanish (Latin America)",
        nativeName = "Español (Latinoamérica)",
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
     * All layouts by Lang enum - primary lookup method
     */
    val byLang: Map<Lang, KeyboardLayout> = allLayouts.associateBy { it.lang }

    /**
     * All layouts by language code - for string-based lookup
     * Includes regional variants (en-gb, en-au, etc.)
     */
    val layouts: Map<String, KeyboardLayout> = allLayouts.associateBy { it.code } +
        mapOf("en-us" to EN)  // US English alias

    /**
     * Get layout by Lang enum (preferred)
     */
    fun get(lang: Lang): KeyboardLayout? = byLang[lang]

    /**
     * Get layout by language code (case-insensitive)
     */
    @Deprecated("Use get(Lang) instead", ReplaceWith("get(Lang.fromCode(code)!!)"))
    fun get(code: String): KeyboardLayout? = layouts[code.lowercase()]

    /**
     * Get all supported languages (base languages only)
     */
    val supportedLangs: List<Lang> = Lang.baseLanguages

    /**
     * Get all supported language codes (base languages only)
     */
    val supportedLanguages: List<String> = supportedLangs.map { it.code }.sorted()

    /**
     * Get all supported language codes including all variants
     */
    val allLanguageCodes: List<String> = layouts.keys.toList().sorted()
}
