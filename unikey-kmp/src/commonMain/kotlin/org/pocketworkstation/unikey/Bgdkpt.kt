package org.pocketworkstation.unikey

/**
 * BGDKPT - Letters affected by dagesh (Multiplatform)
 */
enum class Bgdkpt(
    val letter: Char,
    val withDagesh: BgdkptSound,
    val withoutDagesh: BgdkptSound,
    val classical: BgdkptSound? = null
) {
    BET('\u05D1', BgdkptSound("\u05D1\u05BC", "b", "b"), BgdkptSound("\u05D1", "v", "v")),
    GIMEL('\u05D2', BgdkptSound("\u05D2\u05BC", "g", "g"), BgdkptSound("\u05D2", "g", "g"), BgdkptSound("\u05D2", "\u0263", "gh")),
    DALET('\u05D3', BgdkptSound("\u05D3\u05BC", "d", "d"), BgdkptSound("\u05D3", "d", "d"), BgdkptSound("\u05D3", "\u00F0", "th")),
    KAF('\u05DB', BgdkptSound("\u05DB\u05BC", "k", "k"), BgdkptSound("\u05DB", "x", "kh'")),
    PE('\u05E4', BgdkptSound("\u05E4\u05BC", "p", "p"), BgdkptSound("\u05E4", "f", "f")),
    TAV('\u05EA', BgdkptSound("\u05EA\u05BC", "t", "t"), BgdkptSound("\u05EA", "t", "t"), BgdkptSound("\u05EA", "\u03B8", "th"));

    fun getSound(hasDagesh: Boolean, useClassical: Boolean = false): BgdkptSound {
        return when {
            hasDagesh -> withDagesh
            useClassical && classical != null -> classical
            else -> withoutDagesh
        }
    }

    val hasDistinctModernSounds: Boolean get() = withDagesh.ipa != withoutDagesh.ipa

    companion object {
        private val byLetter = entries.associateBy { it.letter }
        fun fromChar(c: Char): Bgdkpt? = byLetter[c]
        fun isBgdkpt(c: Char): Boolean = byLetter.containsKey(c)
        val modernDistinct = entries.filter { it.hasDistinctModernSounds }
    }
}

data class BgdkptSound(
    val letter: String,
    val ipa: String,
    val en: String
)

/**
 * Geresh marking rules
 */
object Geresh {
    val heToEn = mapOf(
        '\u05E6' to GereshInfo("ts", "ts'"),
        '\u05D7' to GereshInfo("\u0127", "\u1E25'"),
        '\u05DB' to GereshInfo("x", "kh'"),
        '\u05E2' to GereshInfo("\u0295", "'"),
        '\u05E8' to GereshInfo("\u0281", "r'"),
        '\u05E7' to GereshInfo("k", "k'")
    )

    val enToHe = mapOf(
        "\u03B8" to GereshInfo("th (voiceless)", "\u05EA\u05F3"),
        "\u00F0" to GereshInfo("th (voiced)", "\u05D3\u05F3"),
        "t\u0283" to GereshInfo("ch", "\u05E6\u05F3"),
        "d\u0292" to GereshInfo("j", "\u05D2\u05F3"),
        "\u0292" to GereshInfo("zh", "\u05D6\u05F3"),
        "w" to GereshInfo("w", "\u05D5\u05F3")
    )

    fun needsGeresh(sound: String, direction: GereshDirection): Boolean {
        return when (direction) {
            GereshDirection.HE_TO_EN -> sound.firstOrNull()?.let { heToEn.containsKey(it) } ?: false
            GereshDirection.EN_TO_HE -> enToHe.containsKey(sound)
        }
    }
}

data class GereshInfo(val source: String, val target: String)

enum class GereshDirection { HE_TO_EN, EN_TO_HE }
