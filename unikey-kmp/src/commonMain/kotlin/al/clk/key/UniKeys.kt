package al.clk.key

/**
 * UNIKEYS - Unified keyboard key mappings (Multiplatform)
 * Maps physical keyboard keys to Hebrew/English/IPA.
 * UniKey implements ILayoutKey for unified interface.
 */
object UniKeys {
    // Hebrew letters - generated from HebrewLetter enum
    private val hebrewLetterKeys: Map<String, UniKey> = HebrewLetter.entries
        .filter { it.qwerty.isNotEmpty() }
        .associate { it.qwerty to it.toUniKey() }

    // Punctuation keys with shift variants
    private val punctuationKeys: Map<String, UniKey> = mapOf(
        ";" to UniKey(id = ";", en = ";", EN = ":", he = "\u05E3", ipa = "f", shift = ":", heShift = ":"),
        "." to UniKey(id = ".", en = ".", EN = ">", he = "\u05E5", ipa = "ts", shift = ">", heShift = ">"),
        "," to UniKey(id = ",", en = ",", EN = "<", he = "\u05EA", ipa = "t", shift = "<", heShift = "<"),
        "/" to UniKey(id = "/", en = "/", EN = "?", he = ".", shift = "?", heShift = "?"),
        "'" to UniKey(id = "'", en = "'", EN = "\"", he = ",", shift = "\"", heShift = "\""),
        "`" to UniKey(id = "`", en = "`", EN = "~", he = "`", shift = "~", heShift = "~")
    )

    // Non-Hebrew letters
    private val nonHebrewKeys: Map<String, UniKey> = mapOf(
        "q" to UniKey(id = "q", en = "q", EN = "Q", he = "/", ipa = "kw"),
        "w" to UniKey(id = "w", en = "w", EN = "W", he = "'", ipa = "w")
    )

    // Numbers
    private val numberKeys: Map<String, UniKey> = mapOf(
        "1" to UniKey(id = "1", en = "1", EN = "!", he = "1", shift = "!", heShift = "!"),
        "2" to UniKey(id = "2", en = "2", EN = "@", he = "2", shift = "@", heShift = "@"),
        "3" to UniKey(id = "3", en = "3", EN = "#", he = "3", shift = "#", heShift = "#"),
        "4" to UniKey(id = "4", en = "4", EN = "$", he = "4", shift = "$", heShift = "\u20AA"),
        "5" to UniKey(id = "5", en = "5", EN = "%", he = "5", shift = "%", heShift = "%"),
        "6" to UniKey(id = "6", en = "6", EN = "^", he = "6", shift = "^", heShift = "^"),
        "7" to UniKey(id = "7", en = "7", EN = "&", he = "7", shift = "&", heShift = "&"),
        "8" to UniKey(id = "8", en = "8", EN = "*", he = "8", shift = "*", heShift = "*"),
        "9" to UniKey(id = "9", en = "9", EN = "(", he = "9", shift = "(", heShift = ")"),
        "0" to UniKey(id = "0", en = "0", EN = ")", he = "0", shift = ")", heShift = "(")
    )

    // Symbols
    private val symbolKeys: Map<String, UniKey> = mapOf(
        "-" to UniKey(id = "-", en = "-", EN = "_", he = "-", shift = "_", heShift = "_"),
        "=" to UniKey(id = "=", en = "=", EN = "+", he = "=", shift = "+", heShift = "+"),
        "[" to UniKey(id = "[", en = "[", EN = "{", he = "[", shift = "{", heShift = "{"),
        "]" to UniKey(id = "]", en = "]", EN = "}", he = "]", shift = "}", heShift = "}"),
        "\\" to UniKey(id = "\\", en = "\\", EN = "|", he = "\\", shift = "|", heShift = "|")
    )

    // All keys combined - Hebrew letters from enum + other keys
    // Type is Map<String, UniKey> but UniKey implements ILayoutKey
    val keys: Map<String, UniKey> = hebrewLetterKeys + punctuationKeys + nonHebrewKeys + numberKeys + symbolKeys

    // Also expose as ILayoutKey for unified interface
    val layoutKeys: Map<String, ILayoutKey> get() = keys

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
