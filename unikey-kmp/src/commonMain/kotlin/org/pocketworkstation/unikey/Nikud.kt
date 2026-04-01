package org.pocketworkstation.unikey

/**
 * Hebrew nikud (vowel marks) - Multiplatform
 */
enum class Nikud(
    val mark: String,
    val displayName: String,
    val ipa: String,
    val ipaAlt: String? = null,
    val modifier: String? = null
) {
    // Major vowels
    KAMATZ("\u05B8", "\u05E7\u05B8\u05DE\u05B8\u05E5", "a", "o"),
    PATACH("\u05B7", "\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7", "a"),
    TZERE("\u05B5", "\u05E6\u05B5\u05D9\u05E8\u05B5\u05D9", "e"),
    SEGOL("\u05B6", "\u05E1\u05B6\u05D2\u05D5\u05B9\u05DC", "\u025B"),
    CHIRIK("\u05B4", "\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7", "i"),
    CHOLAM("\u05B9", "\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD", "o"),
    KUBUTZ("\u05BB", "\u05E7\u05BB\u05D1\u05BC\u05D5\u05BC\u05E5", "u"),

    // Full vowels (with mater lectionis)
    CHIRIK_MALE("\u05B4\u05D9", "\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7 \u05DE\u05B8\u05DC\u05B5\u05D0", "i"),
    CHOLAM_MALE("\u05D5\u05B9", "\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD \u05DE\u05B8\u05DC\u05B5\u05D0", "o"),
    SHURUK("\u05D5\u05BC", "\u05E9\u05C1\u05D5\u05BC\u05E8\u05D5\u05BC\u05E7", "u"),

    // Shva and hataf
    SHVA("\u05B0", "\u05E9\u05B0\u05C1\u05D5\u05B8\u05D0", "\u0259"),
    HATAF_PATACH("\u05B2", "\u05D7\u05B2\u05D8\u05B7\u05E3 \u05E4\u05B7\u05BC\u05EA\u05B7\u05D7", "a"),
    HATAF_SEGOL("\u05B1", "\u05D7\u05B2\u05D8\u05B7\u05E3 \u05E1\u05B6\u05D2\u05D5\u05B9\u05DC", "\u025B"),
    HATAF_KAMATZ("\u05B3", "\u05D7\u05B2\u05D8\u05B7\u05E3 \u05E7\u05B8\u05DE\u05B8\u05E5", "o"),

    // Dagesh and dots
    DAGESH("\u05BC", "\u05D3\u05B8\u05BC\u05D2\u05B5\u05E9\u05C1", "", modifier = "double/hard"),
    SHIN_DOT("\u05C1", "\u05E0\u05B0\u05E7\u05BB\u05D3\u05B7\u05BC\u05EA \u05E9\u05C1\u05B4\u05D9\u05DF", "\u0283"),
    SIN_DOT("\u05C2", "\u05E0\u05B0\u05E7\u05BB\u05D3\u05B7\u05BC\u05EA \u05E9\u05C2\u05B4\u05D9\u05DF", "s"),
    RAFE("\u05BF", "\u05E8\u05B8\u05E4\u05B6\u05D4", "", modifier = "soft"),
    MAPPIQ("\u05BC", "\u05DE\u05B7\u05E4\u05B4\u05BC\u05D9\u05E7", "h", modifier = "pronounced");

    companion object {
        /**
         * Get nikud options for an IPA sound
         */
        fun forIpa(ipa: String): List<Nikud> = when (ipa) {
            "a" -> listOf(PATACH, KAMATZ, HATAF_PATACH)
            "e" -> listOf(TZERE, SEGOL, HATAF_SEGOL)
            "\u025B" -> listOf(SEGOL, HATAF_SEGOL)
            "i" -> listOf(CHIRIK, CHIRIK_MALE)
            "o" -> listOf(CHOLAM, CHOLAM_MALE, KAMATZ, HATAF_KAMATZ)
            "u" -> listOf(KUBUTZ, SHURUK)
            "\u0259" -> listOf(SHVA)
            "\u026A" -> listOf(CHIRIK)
            "\u028A" -> listOf(KUBUTZ)
            "\u00E6" -> listOf(SEGOL, PATACH)
            "\u028C" -> listOf(PATACH)
            "\u0254" -> listOf(KAMATZ)
            "\u0251" -> listOf(KAMATZ)
            else -> emptyList()
        }

        /**
         * Apply nikud to a letter
         */
        fun apply(letter: Char, ipa: String, useMale: Boolean = false): String {
            val nikudList = forIpa(ipa)
            if (nikudList.isEmpty()) return letter.toString()

            // Use hataf for gutturals with schwa
            if (isGuttural(letter) && ipa == "\u0259") {
                return "$letter${HATAF_PATACH.mark}"
            }

            val nikud = nikudList.first()

            // Use male form if requested
            if (useMale) {
                return when (ipa) {
                    "i" -> "$letter${CHIRIK_MALE.mark}"
                    "o" -> "$letter${CHOLAM_MALE.mark}"
                    "u" -> "$letter${SHURUK.mark}"
                    else -> "$letter${nikud.mark}"
                }
            }

            return "$letter${nikud.mark}"
        }

        private fun isGuttural(letter: Char): Boolean {
            return letter in listOf('\u05D0', '\u05D4', '\u05D7', '\u05E2', '\u05E8')
        }
    }
}
