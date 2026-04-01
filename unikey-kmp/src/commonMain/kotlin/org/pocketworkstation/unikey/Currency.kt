package org.pocketworkstation.unikey

/**
 * Common interface for keyboard keys with phonetic properties
 */
interface ILayoutKey {
    val char: String
    val ipa: String
    val displayName: String
}

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
