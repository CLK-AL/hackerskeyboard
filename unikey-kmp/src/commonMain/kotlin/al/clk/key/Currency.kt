package al.clk.key

/**
 * Language codes with script association.
 * This is the single source of truth for language identification in forms maps.
 */
enum class Lang(val code: String, val script: Script) {
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
    TR("tr", Script.LATIN);

    companion object {
        private val byCode = entries.associateBy { it.code }
        fun fromCode(code: String): Lang? = byCode[code.lowercase()]
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
