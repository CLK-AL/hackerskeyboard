package al.clk.key

/**
 * Morphological Key System - Unified keyboard layout with syllable positions,
 * gender, number, and modifier support for all 23 Chatterbox languages.
 *
 * Each key can produce different output based on:
 * - Position: PREFIX (preposition), MIDDLE (root), SUFFIX (ending)
 * - Gender: MASCULINE, FEMININE
 * - Number: SINGULAR, PLURAL, DUAL
 * - Modifiers: SHIFT, CTRL, ALT
 */

/**
 * Position in syllable/word structure
 */
enum class SyllablePosition {
    PREFIX,   // Preposition/article attached to start (Hebrew: ב,ל,מ,כ,ה,ו,ש)
    MIDDLE,   // Root/stem of word
    SUFFIX    // Ending (plurals, gender markers, verb conjugations)
}

/**
 * Grammatical gender
 */
enum class Gender {
    MASCULINE,
    FEMININE,
    NEUTRAL   // For languages without grammatical gender
}

/**
 * Grammatical number
 */
enum class Number {
    SINGULAR,
    PLURAL,
    DUAL      // For Hebrew/Arabic dual forms
}

/**
 * Full morphological variant of a character.
 * Implements ILayoutKey for unified interface.
 */
data class MorphoVariant(
    override val char: String,
    override val ipa: String,
    override val displayName: String
) : ILayoutKey

/**
 * A single morphological key with all variants
 */
data class MorphoKey(
    val base: MorphoVariant,
    val position: Map<SyllablePosition, MorphoVariant> = emptyMap(),
    val gender: Map<Gender, MorphoVariant> = emptyMap(),
    val number: Map<Number, MorphoVariant> = emptyMap(),
    val modifiers: Map<Modifier, MorphoKey> = emptyMap()
) : ILayoutKey {
    override val char: String get() = base.char
    override val ipa: String get() = base.ipa
    override val displayName: String get() = base.displayName

    /**
     * Get variant for position
     */
    fun forPosition(pos: SyllablePosition): MorphoVariant = position[pos] ?: base

    /**
     * Get variant for gender
     */
    fun forGender(g: Gender): MorphoVariant = gender[g] ?: base

    /**
     * Get variant for number
     */
    fun forNumber(n: Number): MorphoVariant = number[n] ?: base

    /**
     * Get key with modifier
     */
    override fun withModifier(mod: Modifier): MorphoKey? = modifiers[mod]

    /**
     * Get shifted variant
     */
    val shift: MorphoKey? get() = modifiers[Modifier.SHIFT]
}

// ═══════════════════════════════════════════════════════════════════════════════
// Morphological Keys objects replaced by enums in MorphoEnum.kt
// Use HebrewMorphoEnum, ArabicMorphoEnum, RomanceMorphoEnum, GermanMorphoEnum
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Unified morphological keyboard layout
 */
data class MorphoLayout(
    val lang: Lang,
    val name: String,
    val nativeName: String,
    val script: Script = lang.script,
    val keys: Map<String, MorphoKey>,
    val prefixes: List<MorphoKey> = emptyList(),
    val suffixes: Map<Gender, MorphoKey> = emptyMap(),
    val pluralSuffixes: Map<Gender, MorphoKey> = emptyMap()
) {
    /** ISO 639-1 language code (derived from Lang) */
    val code: String get() = lang.code

    /**
     * Get key by QWERTY position
     */
    operator fun get(qwerty: String): MorphoKey? = keys[qwerty]

    /**
     * Get plural suffix for gender
     */
    fun getPluralSuffix(gender: Gender): MorphoKey? = pluralSuffixes[gender]
}

/**
 * All morphological layouts - using enum-based generators
 */
object MorphoLayouts {

    val HE = MorphoLayout(
        lang = Lang.HE,
        name = "Hebrew",
        nativeName = "עברית",
        keys = HebrewMorphoEnum.keys,
        prefixes = HebrewMorphoEnum.prefixes,
        pluralSuffixes = HebrewMorphoEnum.pluralSuffixes
    )

    val AR = MorphoLayout(
        lang = Lang.AR,
        name = "Arabic",
        nativeName = "العربية",
        keys = ArabicMorphoEnum.keys,
        prefixes = ArabicMorphoEnum.prefixes,
        pluralSuffixes = ArabicMorphoEnum.pluralSuffixes
    )

    /**
     * All layouts - used to generate layouts map
     */
    private val allLayouts: List<MorphoLayout> = listOf(HE, AR)

    /**
     * All layouts by Lang enum - primary lookup method
     */
    val byLang: Map<Lang, MorphoLayout> = allLayouts.associateBy { it.lang }

    /**
     * All layouts by language code - for string-based lookup
     */
    val layouts: Map<String, MorphoLayout> = allLayouts.associateBy { it.code }

    /**
     * Get layout by Lang enum (preferred)
     */
    operator fun get(lang: Lang): MorphoLayout? = byLang[lang]

    /**
     * Get layout by code (deprecated)
     */
    @Deprecated("Use get(Lang) instead", ReplaceWith("get(Lang.fromCode(code)!!)"))
    operator fun get(code: String): MorphoLayout? = layouts[code.lowercase()]
}
