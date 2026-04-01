package org.pocketworkstation.unikey

/**
 * UniKey - Universal Keyboard Key with Hebrew/English/IPA mapping
 * Kotlin Multiplatform implementation
 */
data class UniKey(
    val id: String,
    val en: String,
    val EN: String,
    val he: String,
    val ipa: String = "",
    val shift: String? = null,
    val heShift: String? = null,
    val syl: List<Syllable>? = null,
    val enSyl: List<EnglishSyllable>? = null,
    val mg: MultiGender? = null,
    val mgSyl: List<MgSyllable>? = null,
    val dagesh: String? = null,
    val guttural: Boolean = false,
    val isFinal: Boolean = false
) {
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

        // Shift without Alt = syllables indicator or uppercase
        if (mods.shift && !mods.alt) {
            return when (mode) {
                KeyMode.he -> if (!syl.isNullOrEmpty()) "$he\u00B7" else he
                KeyMode.en -> if (!enSyl.isNullOrEmpty()) "$en\u00B7" else EN.ifEmpty { en.uppercase() }
                KeyMode.EN -> shift ?: EN
            }
        }

        // Normal mode
        return when (mode) {
            KeyMode.en -> en
            KeyMode.EN -> EN.ifEmpty { en.uppercase() }
            KeyMode.he -> he
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
