package org.pocketworkstation.unikey

/**
 * UNIKEYS - Unified keyboard key mappings (Multiplatform)
 * Maps physical keyboard keys to Hebrew/English/IPA
 */
object UniKeys {
    val keys: Map<String, UniKey> = mapOf(
        // Letters - Hebrew alphabet mapped to QWERTY
        "t" to UniKey(id = "t", en = "t", EN = "T", he = "\u05D0", ipa = "\u0294", guttural = true),
        "c" to UniKey(id = "c", en = "c", EN = "C", he = "\u05D1", ipa = "v", dagesh = "b"),
        "d" to UniKey(id = "d", en = "d", EN = "D", he = "\u05D2", ipa = "g"),
        "s" to UniKey(id = "s", en = "s", EN = "S", he = "\u05D3", ipa = "d"),
        "v" to UniKey(id = "v", en = "v", EN = "V", he = "\u05D4", ipa = "h", guttural = true),
        "u" to UniKey(id = "u", en = "u", EN = "U", he = "\u05D5", ipa = "v"),
        "z" to UniKey(id = "z", en = "z", EN = "Z", he = "\u05D6", ipa = "z"),
        "j" to UniKey(id = "j", en = "j", EN = "J", he = "\u05D7", ipa = "\u0127", guttural = true),
        "y" to UniKey(id = "y", en = "y", EN = "Y", he = "\u05D8", ipa = "t"),
        "h" to UniKey(id = "h", en = "h", EN = "H", he = "\u05D9", ipa = "j"),
        "f" to UniKey(id = "f", en = "f", EN = "F", he = "\u05DB", ipa = "x", dagesh = "k"),
        "l" to UniKey(id = "l", en = "l", EN = "L", he = "\u05DA", ipa = "x", isFinal = true),
        "k" to UniKey(id = "k", en = "k", EN = "K", he = "\u05DC", ipa = "l"),
        "n" to UniKey(id = "n", en = "n", EN = "N", he = "\u05DE", ipa = "m"),
        "o" to UniKey(id = "o", en = "o", EN = "O", he = "\u05DD", ipa = "m", isFinal = true),
        "b" to UniKey(id = "b", en = "b", EN = "B", he = "\u05E0", ipa = "n"),
        "i" to UniKey(id = "i", en = "i", EN = "I", he = "\u05DF", ipa = "n", isFinal = true),
        "x" to UniKey(id = "x", en = "x", EN = "X", he = "\u05E1", ipa = "s"),
        "g" to UniKey(id = "g", en = "g", EN = "G", he = "\u05E2", ipa = "\u0295", guttural = true),
        "p" to UniKey(id = "p", en = "p", EN = "P", he = "\u05E4", ipa = "f", dagesh = "p"),
        ";" to UniKey(id = ";", en = ";", EN = ":", he = "\u05E3", ipa = "f", shift = ":", heShift = ":", isFinal = true),
        "m" to UniKey(id = "m", en = "m", EN = "M", he = "\u05E6", ipa = "ts"),
        "." to UniKey(id = ".", en = ".", EN = ">", he = "\u05E5", ipa = "ts", shift = ">", heShift = ">", isFinal = true),
        "e" to UniKey(id = "e", en = "e", EN = "E", he = "\u05E7", ipa = "k"),
        "r" to UniKey(id = "r", en = "r", EN = "R", he = "\u05E8", ipa = "\u0281"),
        "a" to UniKey(id = "a", en = "a", EN = "A", he = "\u05E9", ipa = "\u0283"),
        "," to UniKey(id = ",", en = ",", EN = "<", he = "\u05EA", ipa = "t", shift = "<", heShift = "<"),

        // Non-Hebrew letters
        "q" to UniKey(id = "q", en = "q", EN = "Q", he = "/", ipa = "kw"),
        "w" to UniKey(id = "w", en = "w", EN = "W", he = "'", ipa = "w"),

        // Punctuation
        "/" to UniKey(id = "/", en = "/", EN = "?", he = ".", shift = "?", heShift = "?"),
        "'" to UniKey(id = "'", en = "'", EN = "\"", he = ",", shift = "\"", heShift = "\""),
        "`" to UniKey(id = "`", en = "`", EN = "~", he = "`", shift = "~", heShift = "~"),

        // Numbers
        "1" to UniKey(id = "1", en = "1", EN = "!", he = "1", shift = "!", heShift = "!"),
        "2" to UniKey(id = "2", en = "2", EN = "@", he = "2", shift = "@", heShift = "@"),
        "3" to UniKey(id = "3", en = "3", EN = "#", he = "3", shift = "#", heShift = "#"),
        "4" to UniKey(id = "4", en = "4", EN = "$", he = "4", shift = "$", heShift = "\u20AA"),
        "5" to UniKey(id = "5", en = "5", EN = "%", he = "5", shift = "%", heShift = "%"),
        "6" to UniKey(id = "6", en = "6", EN = "^", he = "6", shift = "^", heShift = "^"),
        "7" to UniKey(id = "7", en = "7", EN = "&", he = "7", shift = "&", heShift = "&"),
        "8" to UniKey(id = "8", en = "8", EN = "*", he = "8", shift = "*", heShift = "*"),
        "9" to UniKey(id = "9", en = "9", EN = "(", he = "9", shift = "(", heShift = ")"),
        "0" to UniKey(id = "0", en = "0", EN = ")", he = "0", shift = ")", heShift = "("),

        // Symbols
        "-" to UniKey(id = "-", en = "-", EN = "_", he = "-", shift = "_", heShift = "_"),
        "=" to UniKey(id = "=", en = "=", EN = "+", he = "=", shift = "+", heShift = "+"),
        "[" to UniKey(id = "[", en = "[", EN = "{", he = "[", shift = "{", heShift = "{"),
        "]" to UniKey(id = "]", en = "]", EN = "}", he = "]", shift = "}", heShift = "}"),
        "\\" to UniKey(id = "\\", en = "\\", EN = "|", he = "\\", shift = "|", heShift = "|")
    )

    fun get(id: String): UniKey? = keys[id]
    fun getByHe(he: String): UniKey? = keys.values.find { it.he == he }
    fun getByEn(en: String): UniKey? = keys.values.find { it.en == en }

    fun getDisplay(key: String, mode: KeyMode, mods: Modifiers = Modifiers()): String? {
        val uk = get(key) ?: getByHe(key) ?: getByEn(key) ?: return null
        return uk.label(mode, mods)
    }

    val letterKeys: List<UniKey> = keys.values.filter { it.id.length == 1 && it.id[0].isLetter() }
    val hebrewKeys: List<UniKey> = keys.values.filter { it.he.length == 1 && it.he[0] in '\u05D0'..'\u05EA' }
    val bgdkptKeys: List<UniKey> = keys.values.filter { it.dagesh != null }
    val gutturalKeys: List<UniKey> = keys.values.filter { it.guttural }
    val finalKeys: List<UniKey> = keys.values.filter { it.isFinal }
}

/**
 * Keyboard mode state manager
 */
object UniKeyMode {
    private val modes = listOf(KeyMode.he, KeyMode.en, KeyMode.EN)
    private var currentIndex = 0

    val current: KeyMode get() = modes[currentIndex]

    fun set(mode: KeyMode) {
        currentIndex = modes.indexOf(mode).takeIf { it >= 0 } ?: 0
    }

    fun cycle(): KeyMode {
        currentIndex = (currentIndex + 1) % modes.size
        return current
    }
}

/**
 * Modifier state manager
 */
object UniKeyModifiers {
    var shift: Boolean = false
    var alt: Boolean = false
    var ctrl: Boolean = false

    val current: Modifiers get() = Modifiers(shift, alt, ctrl)

    fun reset() {
        shift = false
        alt = false
        ctrl = false
    }
}
