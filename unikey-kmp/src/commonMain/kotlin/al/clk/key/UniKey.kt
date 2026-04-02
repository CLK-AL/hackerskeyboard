package al.clk.key

/**
 * UniKey - Universal Keyboard Key with IPA as the hub.
 * Language-specific forms stored as ILayoutKey instances.
 * Implements ILayoutKey for unified keyboard interface.
 */
data class UniKey(
    val id: String,
    override val ipa: String,
    val forms: Map<Lang, ILayoutKey> = emptyMap(),  // Lang enum -> key form
    val syl: List<Syllable>? = null,
    val enSyl: List<EnglishSyllable>? = null,
    val mg: MultiGender? = null,
    val mgSyl: List<MgSyllable>? = null,
    val properties: Set<KeyProperty> = emptySet()
) : ILayoutKey {
    // Primary form is the first available or IPA
    override val char: String get() = forms.values.firstOrNull()?.char ?: ipa
    override val displayName: String get() = id
    override val shiftKey: ILayoutKey? get() = forms.values.firstOrNull()?.shiftKey

    /** Get form for Lang enum */
    fun forLang(lang: Lang): ILayoutKey? = forms[lang]

    /** Get form for language code string (convenience) */
    fun forLang(langCode: String): ILayoutKey? = Lang.fromCode(langCode)?.let { forms[it] }

    /** Get char for Lang */
    fun charFor(lang: Lang): String = forms[lang]?.char ?: ipa

    /** Get char for language code string (convenience) */
    fun charFor(langCode: String): String = Lang.fromCode(langCode)?.let { forms[it]?.char } ?: ipa

    /** Check property */
    fun has(prop: KeyProperty): Boolean = prop in properties

    // Legacy accessors for backward compatibility
    val he: String get() = forms[Lang.HE]?.char ?: ""
    val en: String get() = forms[Lang.EN]?.char ?: id
    val EN: String get() = forms[Lang.EN]?.shiftKey?.char ?: en.uppercase()
    val dagesh: String? get() = (forms[Lang.HE]?.shiftKey as? SimpleKey)?.ipa?.takeIf { it.isNotEmpty() }
    val guttural: Boolean get() = has(KeyProperty.GUTTURAL)
    val isFinal: Boolean get() = has(KeyProperty.FINAL_FORM)

    /**
     * Get display label based on mode and modifiers
     */
    fun label(mode: KeyMode, mods: Modifiers = Modifiers()): String {
        // Ctrl = IPA mode
        if (mods.ctrl) return ipa

        // Alt = Multi-gender mode
        if (mods.alt && mg != null) {
            val mf = mods.shift
            return when (mode) {
                KeyMode.EN, KeyMode.en -> if (mf) mg.fm_en else mg.mf_en
                KeyMode.he -> if (mf) mg.fm else mg.mf
            }
        }

        val langKey = when (mode) {
            KeyMode.he -> forms[Lang.HE]
            KeyMode.en, KeyMode.EN -> forms[Lang.EN]
        }

        // Shift without Alt
        if (mods.shift && !mods.alt) {
            return langKey?.shiftKey?.char ?: langKey?.char?.uppercase() ?: ipa
        }

        // Normal mode
        return when (mode) {
            KeyMode.EN -> langKey?.shiftKey?.char ?: langKey?.char?.uppercase() ?: ipa
            else -> langKey?.char ?: ipa
        }
    }

    /**
     * Get syllables for the given mode
     */
    fun getSyllables(mode: KeyMode): List<Any> {
        return when (mode) {
            KeyMode.he -> syl ?: emptyList()
            KeyMode.en, KeyMode.EN -> enSyl ?: emptyList()
        }
    }

    /**
     * Get multi-gender info if available
     */
    fun getMg(mode: KeyMode, femaleFirst: Boolean = false): MgDisplay? {
        val mg = this.mg ?: return null
        val isEn = mode == KeyMode.en || mode == KeyMode.EN
        return MgDisplay(
            display = if (femaleFirst) (if (isEn) mg.fm_en else mg.fm) else (if (isEn) mg.mf_en else mg.mf),
            male = if (isEn) mg.m_en else mg.m,
            female = if (isEn) mg.f_en else mg.f
        )
    }

    companion object {
        /** Create UniKey from IPA with language forms (Lang enum keys) */
        fun fromIpa(
            id: String,
            ipa: String,
            vararg langForms: Pair<Lang, ILayoutKey>,
            properties: Set<KeyProperty> = emptySet()
        ) = UniKey(id, ipa, langForms.toMap(), properties = properties)
    }
}

/** Key properties (replaces hardcoded boolean flags) */
enum class KeyProperty {
    GUTTURAL,
    FINAL_FORM,
    BGDKPT,
    WEAK,
    EMPHATIC
}

/**
 * Hebrew syllable with nikud
 */
data class Syllable(
    val text: String,
    val ipa: String,
    val name: String
)

/**
 * English syllable/vowel combination
 */
data class EnglishSyllable(
    val syl: String,
    val ipa: String,
    val ex: String? = null
)

/**
 * Multi-gender Hebrew (Ivrita) forms
 */
data class MultiGender(
    val m: String,
    val f: String,
    val mf: String,
    val fm: String,
    val m_en: String,
    val f_en: String,
    val mf_en: String,
    val fm_en: String
)

/**
 * Multi-gender syllable
 */
data class MgSyllable(
    val m: Syllable,
    val f: Syllable
)

/**
 * Display info for multi-gender
 */
data class MgDisplay(
    val display: String,
    val male: String,
    val female: String
)

/**
 * Keyboard mode
 */
enum class KeyMode {
    he,  // Hebrew
    en,  // English lowercase
    EN   // English UPPERCASE
}

/**
 * Modifier keys state
 */
data class Modifiers(
    val shift: Boolean = false,
    val alt: Boolean = false,
    val ctrl: Boolean = false
)
