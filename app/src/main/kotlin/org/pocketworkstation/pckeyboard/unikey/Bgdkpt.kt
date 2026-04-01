package org.pocketworkstation.pckeyboard.unikey

/**
 * BGDKPT (בג"ד כפ"ת) - Letters affected by dagesh
 *
 * These six letters have two pronunciations:
 * - With dagesh (hard/plosive): בּ גּ דּ כּ פּ תּ
 * - Without dagesh (soft/fricative): ב ג ד כ פ ת
 *
 * In modern Hebrew, only ב כ פ have distinct sounds.
 * In classical Hebrew, all six had distinct sounds.
 */
enum class Bgdkpt(
    val letter: Char,
    val withDagesh: BgdkptSound,
    val withoutDagesh: BgdkptSound,
    val classical: BgdkptSound? = null
) {
    BET(
        'ב',
        BgdkptSound("בּ", "b", "b", "בַּיִת→bayit"),
        BgdkptSound("ב", "v", "v", "אָב→av")
    ),
    GIMEL(
        'ג',
        BgdkptSound("גּ", "g", "g", "גַּם→gam"),
        BgdkptSound("ג", "g", "g", "same in modern"),
        BgdkptSound("ג", "ɣ", "gh", "classical fricative")
    ),
    DALET(
        'ד',
        BgdkptSound("דּ", "d", "d", "דָּג→dag"),
        BgdkptSound("ד", "d", "d", "same in modern"),
        BgdkptSound("ד", "ð", "th", "like English 'the'")
    ),
    KAF(
        'כ',
        BgdkptSound("כּ", "k", "k", "כֵּן→ken"),
        BgdkptSound("כ", "x", "kh'", "מֶלֶךְ→melekh'")
    ),
    PE(
        'פ',
        BgdkptSound("פּ", "p", "p", "פֹּה→po"),
        BgdkptSound("פ", "f", "f", "סוֹפֵר→sofer")
    ),
    TAV(
        'ת',
        BgdkptSound("תּ", "t", "t", "תַּם→tam"),
        BgdkptSound("ת", "t", "t", "same in modern"),
        BgdkptSound("ת", "θ", "th", "like English 'think'")
    );

    /**
     * Get the sound based on dagesh presence
     */
    fun getSound(hasDagesh: Boolean, useClassical: Boolean = false): BgdkptSound {
        return when {
            hasDagesh -> withDagesh
            useClassical && classical != null -> classical
            else -> withoutDagesh
        }
    }

    /**
     * Check if this letter has distinct modern sounds
     */
    val hasDistinctModernSounds: Boolean
        get() = withDagesh.ipa != withoutDagesh.ipa

    companion object {
        private val byLetter = entries.associateBy { it.letter }

        fun fromChar(c: Char): Bgdkpt? = byLetter[c]

        fun isBgdkpt(c: Char): Boolean = byLetter.containsKey(c)

        /**
         * Letters with distinct sounds in modern Hebrew
         */
        val modernDistinct = entries.filter { it.hasDistinctModernSounds }
    }
}

/**
 * Sound information for a BGDKPT letter
 */
data class BgdkptSound(
    val letter: String,  // String because dagesh letters are 2 code points (e.g., "בּ")
    val ipa: String,
    val en: String,
    val example: String
)

/**
 * Mappiq - הּ at end of word
 *
 * Makes final ה pronounced (usually silent at end)
 * Common in:
 * - Possessive suffixes: שֶׁלָּהּ (hers)
 * - Construct state: גָּבֹהַּ (high)
 */
object Mappiq {
    const val MARK = "הּ"
    const val IPA = "h"
    const val RULE = "ה נשמעת בסוף מילה (סמיכות, כינויים)"

    val withMappiq = listOf(
        MappiqExample("שֶׁלָּהּ", "shela'", "שלה (נקבה)", "ʃelaʰ"),
        MappiqExample("גָּבֹהַּ", "gavoah'", "גבוה", "gavoaʰ"),
        MappiqExample("בֵּיתָהּ", "beta'", "ביתה (שלה)", "betaʰ"),
        MappiqExample("אֱלוֹהַּ", "eloah'", "אלוה", "eloaʰ")
    )

    val withoutMappiq = listOf(
        MappiqExample("שֶׁלָּה", "shela", "שלה (מקום)", note = "ה silent"),
        MappiqExample("יָפָה", "yafa", "יפה", note = "ה silent"),
        MappiqExample("גְּדוֹלָה", "gdola", "גדולה", note = "ה silent"),
        MappiqExample("בַּיְתָה", "bayta", "הביתה (כיוון)", note = "ה silent")
    )
}

data class MappiqExample(
    val he: String,
    val en: String,
    val meaning: String,
    val ipa: String? = null,
    val note: String? = null
)
