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

    // Helper to create key with language forms
    private fun key(id: String, ipa: String, he: String, en: String, heShift: String? = null, enShift: String? = null): UniKey {
        val heKey: ILayoutKey = if (heShift != null) SimpleKey(he, ipa, id, SimpleKey(heShift, "", "$id-shift")) else SimpleKey(he, ipa, id)
        val enKey: ILayoutKey = SimpleKey(en, ipa, id, enShift?.let { SimpleKey(it, "", "$id-shift") })
        return UniKey(id = id, ipa = ipa, forms = mapOf(Lang.HE to heKey, Lang.EN to enKey))
    }

    // Punctuation keys with shift variants
    private val punctuationKeys: Map<String, UniKey> = mapOf(
        ";" to key(";", "f", "\u05E3", ";", ":", ":"),
        "." to key(".", "ts", "\u05E5", ".", ">", ">"),
        "," to key(",", "t", "\u05EA", ",", "<", "<"),
        "/" to key("/", "", ".", "/", "?", "?"),
        "'" to key("'", "", ",", "'", "\"", "\""),
        "`" to key("`", "", "`", "`", "~", "~")
    )

    // Non-Hebrew letters
    private val nonHebrewKeys: Map<String, UniKey> = mapOf(
        "q" to key("q", "kw", "/", "q", null, "Q"),
        "w" to key("w", "w", "'", "w", null, "W")
    )

    // Numbers
    private val numberKeys: Map<String, UniKey> = mapOf(
        "1" to key("1", "", "1", "1", "!", "!"),
        "2" to key("2", "", "2", "2", "@", "@"),
        "3" to key("3", "", "3", "3", "#", "#"),
        "4" to key("4", "", "4", "4", "\u20AA", "$"),
        "5" to key("5", "", "5", "5", "%", "%"),
        "6" to key("6", "", "6", "6", "^", "^"),
        "7" to key("7", "", "7", "7", "&", "&"),
        "8" to key("8", "", "8", "8", "*", "*"),
        "9" to key("9", "", "9", "9", ")", "("),
        "0" to key("0", "", "0", "0", "(", ")")
    )

    // Symbols
    private val symbolKeys: Map<String, UniKey> = mapOf(
        "-" to key("-", "", "-", "-", "_", "_"),
        "=" to key("=", "", "=", "=", "+", "+"),
        "[" to key("[", "", "[", "[", "{", "{"),
        "]" to key("]", "", "]", "]", "}", "}"),
        "\\" to key("\\", "", "\\", "\\", "|", "|")
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
