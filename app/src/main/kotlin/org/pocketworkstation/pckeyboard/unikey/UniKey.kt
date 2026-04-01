package org.pocketworkstation.pckeyboard.unikey

/**
 * UniKey - Universal Keyboard Key with Hebrew/English/IPA mapping
 * Based on UniKeyboard HTML/JS implementation
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
    val final: Boolean = false
) {
    /**
     * Get display label based on mode and modifiers
     */
    fun label(mode: KeyMode, mods: Modifiers = Modifiers()): String {
        // Ctrl = IPA mode
        if (mods.ctrl) return ipa

        // Alt = Multi-gender mode
        if (mods.alt && mg != null) {
            val mf = mods.shift  // female-first when shift held
            return when (mode) {
                KeyMode.EN, KeyMode.en -> if (mf) mg.fm_en else mg.mf_en
                KeyMode.he -> if (mf) mg.fm else mg.mf
            }
        }

        // Shift without Alt = syllables indicator or uppercase
        if (mods.shift && !mods.alt) {
            return when (mode) {
                KeyMode.he -> if (!syl.isNullOrEmpty()) "$he·" else he
                KeyMode.en -> if (!enSyl.isNullOrEmpty()) "$en·" else (EN.takeIf { it.isNotEmpty() } ?: en.uppercase())
                KeyMode.EN -> shift ?: EN
            }
        }

        // Normal mode
        return when (mode) {
            KeyMode.en -> en
            KeyMode.EN -> EN.takeIf { it.isNotEmpty() } ?: en.uppercase()
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
    val text: String,      // Letter + nikud
    val ipa: String,       // IPA transcription
    val name: String       // Nikud name
)

/**
 * English syllable/vowel combination
 */
data class EnglishSyllable(
    val syl: String,       // Syllable text
    val ipa: String,       // IPA transcription
    val ex: String? = null // Example word
)

/**
 * Multi-gender Hebrew (Ivrita) forms
 */
data class MultiGender(
    val m: String,         // Male form (Hebrew)
    val f: String,         // Female form (Hebrew)
    val mf: String,        // Male/Female combined (Hebrew)
    val fm: String,        // Female/Male combined (Hebrew)
    val m_en: String,      // Male form (English)
    val f_en: String,      // Female form (English)
    val mf_en: String,     // Male/Female combined (English)
    val fm_en: String      // Female/Male combined (English)
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
