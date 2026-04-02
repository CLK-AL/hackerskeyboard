package al.clk.key

/**
 * Language codes with script association.
 * This is the single source of truth for language identification in forms maps.
 */
enum class Lang(val code: String, val script: Script, val baseLang: Lang? = null) {
    // Base languages
    HE("he", Script.HEBREW),
    EN("en", Script.LATIN),
    AR("ar", Script.ARABIC),
    EL("el", Script.GREEK),
    RU("ru", Script.CYRILLIC),
    HI("hi", Script.DEVANAGARI),
    JA("ja", Script.HIRAGANA),
    KO("ko", Script.HANGUL),
    ZH("zh", Script.CJK),
    // Latin-script languages
    DA("da", Script.LATIN),
    DE("de", Script.LATIN),
    ES("es", Script.LATIN),
    FI("fi", Script.LATIN),
    FR("fr", Script.LATIN),
    IT("it", Script.LATIN),
    MS("ms", Script.LATIN),
    NL("nl", Script.LATIN),
    NO("no", Script.LATIN),
    PL("pl", Script.LATIN),
    PT("pt", Script.LATIN),
    SV("sv", Script.LATIN),
    SW("sw", Script.LATIN),
    TR("tr", Script.LATIN),
    // Regional variants
    EN_GB("en-gb", Script.LATIN, EN),
    EN_AU("en-au", Script.LATIN, EN),
    EN_CA("en-ca", Script.LATIN, EN),
    EN_IN("en-in", Script.LATIN, EN),
    PT_BR("pt-br", Script.LATIN, PT),
    ES_419("es-419", Script.LATIN, ES);

    /** Is this a regional variant? */
    val isVariant: Boolean get() = baseLang != null

    companion object {
        private val byCode = entries.associateBy { it.code }
        fun fromCode(code: String): Lang? = byCode[code.lowercase()]

        /** Get base languages only (no regional variants) */
        val baseLanguages: List<Lang> get() = entries.filter { !it.isVariant }
    }
}

/**
 * Script types for keyboard layouts
 */
enum class Script {
    HEBREW, LATIN, ARABIC, GREEK, DEVANAGARI, CYRILLIC, HANGUL, HIRAGANA, CJK, UNKNOWN
}

/**
 * Common interface for keyboard keys with phonetic properties.
 * Enums implement this to be the single source of truth for key data.
 */
interface ILayoutKey {
    val char: String
    val ipa: String
    val displayName: String

    /** Key to use when shift is pressed (default: uppercase char) */
    val shiftKey: ILayoutKey? get() = null

    /** Key to use when ctrl is pressed */
    val ctrlKey: ILayoutKey? get() = null

    /** Key to use when alt is pressed */
    val altKey: ILayoutKey? get() = null

    /** Get key for modifier */
    fun withModifier(mod: Modifier): ILayoutKey? = when (mod) {
        Modifier.SHIFT -> shiftKey
        Modifier.CTRL -> ctrlKey
        Modifier.ALT -> altKey
        else -> null  // Combined modifiers not supported by default
    }
}

/**
 * Interface for spelling pattern enums across all languages.
 * Extends ILayoutKey to integrate with keyboard layouts.
 */
interface ISpellingPattern : ILayoutKey {
    /** The written spelling (grapheme) */
    val pattern: String

    /** Example words demonstrating this pattern */
    val examples: List<String>

    /** Where in a word this pattern typically occurs */
    val position: PatternPosition

    /** The char property defaults to pattern */
    override val char: String get() = pattern
}

/**
 * Hebrew spelling pattern supporting male (full), haser (defective), and unpointed variants.
 * Nikud male is the base/canonical form since it contains the most information.
 */
interface IHebrewPattern : ISpellingPattern {
    /** Base form with nikud male (full spelling with mater lectionis) */
    val male: String

    /** Haser form (defective spelling without mater lectionis) */
    val haser: String

    /** Unpointed form (consonants only, no nikud) */
    val unpointed: String

    /** Default pattern is male form (canonical) */
    override val pattern: String get() = male
}

/**
 * Arabic spelling pattern supporting forms with and without harakat (vowel marks).
 */
interface IArabicPattern : ISpellingPattern {
    /** Form with full harakat (vowel diacritics) */
    val withHarakat: String

    /** Form without harakat (consonants only) */
    val withoutHarakat: String

    /** Default pattern is with harakat (canonical) */
    override val pattern: String get() = withHarakat
}

/**
 * Form of Hebrew nikud spelling
 */
enum class NikudForm {
    MALE,      // Full spelling with mater lectionis (שָׁלוֹם)
    HASER,     // Defective without mater lectionis (שָׁלֹם)
    UNPOINTED  // No nikud marks (שלום)
}

/**
 * Generic pattern category for spelling patterns across all languages.
 */
enum class PatternCategory {
    VOWEL,      // Vowel patterns (nikud, harakat, diphthongs)
    CONSONANT,  // Consonant combinations (clusters, digraphs)
    PREFIX,     // Prefix patterns
    SUFFIX,     // Suffix patterns
    ROOT,       // Root/stem patterns (verb forms, etc.)
    WORD        // Common word patterns
}

/**
 * Simple implementation of ILayoutKey for ad-hoc keys (punctuation, numbers)
 */
data class SimpleKey(
    override val char: String,
    override val ipa: String,
    override val displayName: String,
    override val shiftKey: ILayoutKey? = null
) : ILayoutKey

/**
 * Currency symbols by region for keyboard layouts
 */
enum class Currency(
    val symbol: String,
    val displayName: String,
    val regions: List<String>
) {
    DOLLAR("$", "dollar", listOf("en", "en-us", "en-au", "en-ca", "es-419")),
    POUND("£", "pound", listOf("en-gb")),
    EURO("€", "euro", listOf("de", "el", "es", "fi", "fr", "it", "nl", "pt")),
    RUPEE("₹", "rupee", listOf("hi", "en-in")),
    SHEKEL("₪", "shekel", listOf("he")),
    YEN("¥", "yen", listOf("ja", "zh")),
    WON("₩", "won", listOf("ko")),
    RUBLE("₽", "ruble", listOf("ru")),
    LIRA("₺", "lira", listOf("tr")),
    ZLOTY("zł", "zloty", listOf("pl")),
    KRONE("kr", "krone", listOf("da", "no", "sv")),
    REAL("R$", "real", listOf("pt-br")),
    RINGGIT("RM", "ringgit", listOf("ms")),
    SHILLING("TSh", "shilling", listOf("sw"));

    companion object {
        private val byRegion = entries.flatMap { c -> c.regions.map { it to c } }.toMap()
        fun forRegion(code: String): Currency = byRegion[code] ?: DOLLAR
    }
}
