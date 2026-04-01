package org.pocketworkstation.pckeyboard.unikey

/**
 * UNIKEYS - Unified keyboard key mappings
 * Maps physical keyboard keys to Hebrew/English/IPA
 *
 * Layout follows standard Hebrew QWERTY keyboard mapping
 */
object UniKeys {
    /**
     * All UniKey entries indexed by key ID
     */
    val keys: Map<String, UniKey> = mapOf(
        // Letters - Hebrew alphabet mapped to QWERTY
        "t" to UniKey(id = "t", en = "t", EN = "T", he = "א", ipa = "ʔ", guttural = true),
        "c" to UniKey(id = "c", en = "c", EN = "C", he = "ב", ipa = "v", dagesh = "b"),
        "d" to UniKey(id = "d", en = "d", EN = "D", he = "ג", ipa = "g"),
        "s" to UniKey(id = "s", en = "s", EN = "S", he = "ד", ipa = "d"),
        "v" to UniKey(id = "v", en = "v", EN = "V", he = "ה", ipa = "h", guttural = true),
        "u" to UniKey(id = "u", en = "u", EN = "U", he = "ו", ipa = "v"),
        "z" to UniKey(id = "z", en = "z", EN = "Z", he = "ז", ipa = "z"),
        "j" to UniKey(id = "j", en = "j", EN = "J", he = "ח", ipa = "ħ", guttural = true),
        "y" to UniKey(id = "y", en = "y", EN = "Y", he = "ט", ipa = "t"),
        "h" to UniKey(id = "h", en = "h", EN = "H", he = "י", ipa = "j"),
        "f" to UniKey(id = "f", en = "f", EN = "F", he = "כ", ipa = "x", dagesh = "k"),
        "l" to UniKey(id = "l", en = "l", EN = "L", he = "ך", ipa = "x", final = true),
        "k" to UniKey(id = "k", en = "k", EN = "K", he = "ל", ipa = "l"),
        "n" to UniKey(id = "n", en = "n", EN = "N", he = "מ", ipa = "m"),
        "o" to UniKey(id = "o", en = "o", EN = "O", he = "ם", ipa = "m", final = true),
        "b" to UniKey(id = "b", en = "b", EN = "B", he = "נ", ipa = "n"),
        "i" to UniKey(id = "i", en = "i", EN = "I", he = "ן", ipa = "n", final = true),
        "x" to UniKey(id = "x", en = "x", EN = "X", he = "ס", ipa = "s"),
        "g" to UniKey(id = "g", en = "g", EN = "G", he = "ע", ipa = "ʕ", guttural = true),
        "p" to UniKey(id = "p", en = "p", EN = "P", he = "פ", ipa = "f", dagesh = "p"),
        ";" to UniKey(id = ";", en = ";", EN = ":", he = "ף", ipa = "f", shift = ":", heShift = ":", final = true),
        "m" to UniKey(id = "m", en = "m", EN = "M", he = "צ", ipa = "ts"),
        "." to UniKey(id = ".", en = ".", EN = ">", he = "ץ", ipa = "ts", shift = ">", heShift = ">", final = true),
        "e" to UniKey(id = "e", en = "e", EN = "E", he = "ק", ipa = "k"),
        "r" to UniKey(id = "r", en = "r", EN = "R", he = "ר", ipa = "ʁ"),
        "a" to UniKey(id = "a", en = "a", EN = "A", he = "ש", ipa = "ʃ"),
        "," to UniKey(id = ",", en = ",", EN = "<", he = "ת", ipa = "t", shift = "<", heShift = "<"),

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
        "4" to UniKey(id = "4", en = "4", EN = "$", he = "4", shift = "$", heShift = "₪"),  // Hebrew uses Shekel
        "5" to UniKey(id = "5", en = "5", EN = "%", he = "5", shift = "%", heShift = "%"),
        "6" to UniKey(id = "6", en = "6", EN = "^", he = "6", shift = "^", heShift = "^"),
        "7" to UniKey(id = "7", en = "7", EN = "&", he = "7", shift = "&", heShift = "&"),
        "8" to UniKey(id = "8", en = "8", EN = "*", he = "8", shift = "*", heShift = "*"),
        "9" to UniKey(id = "9", en = "9", EN = "(", he = "9", shift = "(", heShift = ")"),  // RTL swap
        "0" to UniKey(id = "0", en = "0", EN = ")", he = "0", shift = ")", heShift = "("),  // RTL swap

        // Symbols
        "-" to UniKey(id = "-", en = "-", EN = "_", he = "-", shift = "_", heShift = "_"),
        "=" to UniKey(id = "=", en = "=", EN = "+", he = "=", shift = "+", heShift = "+"),
        "[" to UniKey(id = "[", en = "[", EN = "{", he = "[", shift = "{", heShift = "{"),
        "]" to UniKey(id = "]", en = "]", EN = "}", he = "]", shift = "}", heShift = "}"),
        "\\" to UniKey(id = "\\", en = "\\", EN = "|", he = "\\", shift = "|", heShift = "|")
    )

    /**
     * Get UniKey by key ID
     */
    fun get(id: String): UniKey? = keys[id]

    /**
     * Get UniKey by Hebrew letter
     */
    fun getByHe(he: String): UniKey? = keys.values.find { it.he == he }

    /**
     * Get UniKey by English letter
     */
    fun getByEn(en: String): UniKey? = keys.values.find { it.en == en }

    /**
     * Get display text for a key based on mode and modifiers
     */
    fun getDisplay(key: String, mode: KeyMode, mods: Modifiers = Modifiers()): String? {
        val uk = get(key) ?: getByHe(key) ?: getByEn(key) ?: return null
        return uk.label(mode, mods)
    }

    /**
     * All letter keys (excluding punctuation and numbers)
     */
    val letterKeys: List<UniKey> = keys.values.filter {
        it.id.length == 1 && it.id[0].isLetter()
    }

    /**
     * All Hebrew letter keys
     */
    val hebrewKeys: List<UniKey> = keys.values.filter {
        it.he.length == 1 && it.he[0] in 'א'..'ת'
    }

    /**
     * BGDKPT letters (affected by dagesh)
     */
    val bgdkptKeys: List<UniKey> = keys.values.filter { it.dagesh != null }

    /**
     * Guttural letters
     */
    val gutturalKeys: List<UniKey> = keys.values.filter { it.guttural }

    /**
     * Final form letters (sofit)
     */
    val finalKeys: List<UniKey> = keys.values.filter { it.final }
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
