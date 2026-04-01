package org.pocketworkstation.pckeyboard.unikey

/**
 * Hebrew nikud (vowel marks) - נִיקוּד
 */
enum class Nikud(
    val mark: String,
    val displayName: String,
    val ipa: String,
    val ipaAlt: String? = null,
    val modifier: String? = null
) {
    // תנועות גדולות - Major vowels
    KAMATZ("ָ", "קָמָץ", "a", "o"),
    PATACH("ַ", "פַּתַח", "a"),
    TZERE("ֵ", "צֵירֵי", "e"),
    SEGOL("ֶ", "סֶגוֹל", "ɛ"),
    CHIRIK("ִ", "חִירִיק", "i"),
    CHOLAM("ֹ", "חוֹלָם", "o"),
    KUBUTZ("ֻ", "קֻבּוּץ", "u"),

    // תנועות עם אם קריאה - Full vowels (with mater lectionis)
    CHIRIK_MALE("ִי", "חִירִיק מָלֵא", "i"),
    CHOLAM_MALE("וֹ", "חוֹלָם מָלֵא", "o"),
    SHURUK("וּ", "שׁוּרוּק", "u"),

    // שווא וחטפים - Shva and hataf
    SHVA("ְ", "שְׁוָא", "ə"),
    HATAF_PATACH("ֲ", "חֲטַף פַּתַח", "a"),
    HATAF_SEGOL("ֱ", "חֲטַף סֶגוֹל", "ɛ"),
    HATAF_KAMATZ("ֳ", "חֲטַף קָמָץ", "o"),

    // דגש ונקודות - Dagesh and dots
    DAGESH("ּ", "דָּגֵשׁ", "", modifier = "double/hard"),
    SHIN_DOT("ׁ", "נְקֻדַּת שִׁין", "ʃ"),
    SIN_DOT("ׂ", "נְקֻדַּת שִׂין", "s"),
    RAFE("ֿ", "רָפֶה", "", modifier = "soft"),
    MAPPIQ("ּ", "מַפִּיק", "h", modifier = "pronounced ה");

    companion object {
        /**
         * Get nikud options for an IPA sound
         */
        fun forIpa(ipa: String): List<Nikud> = when (ipa) {
            "a" -> listOf(PATACH, KAMATZ, HATAF_PATACH)
            "e" -> listOf(TZERE, SEGOL, HATAF_SEGOL)
            "ɛ" -> listOf(SEGOL, HATAF_SEGOL)
            "i" -> listOf(CHIRIK, CHIRIK_MALE)
            "o" -> listOf(CHOLAM, CHOLAM_MALE, KAMATZ, HATAF_KAMATZ)
            "u" -> listOf(KUBUTZ, SHURUK)
            "ə" -> listOf(SHVA)
            "ɪ" -> listOf(CHIRIK)
            "ʊ" -> listOf(KUBUTZ)
            "æ" -> listOf(SEGOL, PATACH)
            "ʌ" -> listOf(PATACH)
            "ɔ" -> listOf(KAMATZ)
            "ɑ" -> listOf(KAMATZ)
            else -> emptyList()
        }

        /**
         * Apply nikud to a letter
         */
        fun apply(letter: Char, ipa: String, useMale: Boolean = false): String {
            val nikudList = forIpa(ipa)
            if (nikudList.isEmpty()) return letter.toString()

            // Use hataf for gutturals with schwa
            if (isGuttural(letter) && ipa == "ə") {
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
            return letter in listOf('א', 'ה', 'ח', 'ע', 'ר')
        }
    }
}
