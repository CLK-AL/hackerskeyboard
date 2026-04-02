package al.clk.key

/**
 * Target language configuration for bilingual poetry translation.
 * Abstracts the source→target language pair and provides language-specific
 * IPA conversion, syllable parsing, and AI prompt generation.
 *
 * This enables the poetry editor to support any (source, target) language pair
 * beyond the original English→Hebrew POC.
 */
data class TargetLanguage(
    val lang: Lang,
    val nativeName: String,
    val direction: TextDirection = TextDirection.LTR,
    val syllableParser: (String) -> List<UniKeySyllable>,
    val ipaConverter: (String) -> String,
    val aiPromptHints: LanguagePromptHints
) {
    /** ISO 639-1 code */
    val code: String get() = lang.code

    /** Script type for this language */
    val script: Script get() = lang.script

    /** Parse word into IPA syllables */
    fun parseSyllables(word: String): List<UniKeySyllable> = syllableParser(word)

    /** Convert word to IPA string for rhyme matching */
    fun toIpa(word: String): String = ipaConverter(word)

    /** Get hue for word ending (for visual rhyme coloring) */
    fun wordHue(word: String): Int {
        val ipa = toIpa(word)
        return IpaColor.ipaHue(ipa)
    }

    /** Get HSL color for word ending */
    fun wordEndColor(word: String): String {
        val hue = wordHue(word)
        return IpaColor.hsl(hue, 70, 65)
    }

    companion object {
        /** Pre-configured target languages */
        val HEBREW = TargetLanguage(
            lang = Lang.HE,
            nativeName = "עברית",
            direction = TextDirection.RTL,
            syllableParser = { UniKeySyllable.parseHebrew(it) },
            ipaConverter = { IpaColor.heIpa(it) },
            aiPromptHints = LanguagePromptHints.HEBREW
        )

        val ARABIC = TargetLanguage(
            lang = Lang.AR,
            nativeName = "العربية",
            direction = TextDirection.RTL,
            syllableParser = { UniKeySyllable.parseArabic(it) },
            ipaConverter = { arIpa(it) },
            aiPromptHints = LanguagePromptHints.ARABIC
        )

        val RUSSIAN = TargetLanguage(
            lang = Lang.RU,
            nativeName = "Русский",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseCyrillic(it) },
            ipaConverter = { ruIpa(it) },
            aiPromptHints = LanguagePromptHints.RUSSIAN
        )

        val GREEK = TargetLanguage(
            lang = Lang.EL,
            nativeName = "Ελληνικά",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseGreek(it) },
            ipaConverter = { elIpa(it) },
            aiPromptHints = LanguagePromptHints.GREEK
        )

        val GERMAN = TargetLanguage(
            lang = Lang.DE,
            nativeName = "Deutsch",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.DE) },
            ipaConverter = { deIpa(it) },
            aiPromptHints = LanguagePromptHints.GERMAN
        )

        val FRENCH = TargetLanguage(
            lang = Lang.FR,
            nativeName = "Français",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.FR) },
            ipaConverter = { frIpa(it) },
            aiPromptHints = LanguagePromptHints.FRENCH
        )

        val SPANISH = TargetLanguage(
            lang = Lang.ES,
            nativeName = "Español",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.ES) },
            ipaConverter = { esIpa(it) },
            aiPromptHints = LanguagePromptHints.SPANISH
        )

        val ITALIAN = TargetLanguage(
            lang = Lang.IT,
            nativeName = "Italiano",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.IT) },
            ipaConverter = { itIpa(it) },
            aiPromptHints = LanguagePromptHints.ITALIAN
        )

        val JAPANESE = TargetLanguage(
            lang = Lang.JA,
            nativeName = "日本語",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseJapanese(it) },
            ipaConverter = { jaIpa(it) },
            aiPromptHints = LanguagePromptHints.JAPANESE
        )

        val KOREAN = TargetLanguage(
            lang = Lang.KO,
            nativeName = "한국어",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseHangul(it) },
            ipaConverter = { koIpa(it) },
            aiPromptHints = LanguagePromptHints.KOREAN
        )

        val HINDI = TargetLanguage(
            lang = Lang.HI,
            nativeName = "हिन्दी",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseDevanagari(it) },
            ipaConverter = { hiIpa(it) },
            aiPromptHints = LanguagePromptHints.HINDI
        )

        val CHINESE = TargetLanguage(
            lang = Lang.ZH,
            nativeName = "中文",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseCjk(it) },
            ipaConverter = { zhIpa(it) },
            aiPromptHints = LanguagePromptHints.CHINESE
        )

        /** All supported target languages */
        val ALL = listOf(
            HEBREW, ARABIC, RUSSIAN, GREEK, GERMAN, FRENCH,
            SPANISH, ITALIAN, JAPANESE, KOREAN, HINDI, CHINESE
        )

        private val byCode = ALL.associateBy { it.code }
        private val byLang = ALL.associateBy { it.lang }

        /** Get target language by ISO code */
        fun fromCode(code: String): TargetLanguage? = byCode[code.lowercase()]

        /** Get target language by Lang enum */
        fun fromLang(lang: Lang): TargetLanguage? = byLang[lang]

        // === IPA converters for each language ===

        private fun arIpa(word: String): String {
            val syls = UniKeySyllable.parseArabic(word)
            return syls.joinToString("") { it.consonant + it.vowel }
        }

        private fun ruIpa(word: String): String {
            val syls = UniKeySyllable.parseCyrillic(word)
            return syls.joinToString("") { it.consonant + it.vowel }
        }

        private fun elIpa(word: String): String {
            val syls = UniKeySyllable.parseGreek(word)
            return syls.joinToString("") { it.consonant + it.vowel }
        }

        private fun deIpa(word: String): String {
            val w = word.lowercase()
            val pattern = GermanPattern.matchEnding(w)
            return pattern?.ipa ?: w.takeLast(3)
        }

        private fun frIpa(word: String): String {
            val w = word.lowercase()
            val pattern = FrenchPattern.matchEnding(w)
            return pattern?.ipa ?: w.takeLast(3)
        }

        private fun esIpa(word: String): String {
            val w = word.lowercase()
            val pattern = SpanishPattern.matchEnding(w)
            return pattern?.ipa ?: w.takeLast(3)
        }

        private fun itIpa(word: String): String {
            val w = word.lowercase()
            val pattern = ItalianPattern.matchEnding(w)
            return pattern?.ipa ?: w.takeLast(3)
        }

        private fun jaIpa(word: String): String {
            val syls = UniKeySyllable.parseJapanese(word)
            return syls.joinToString("") { it.consonant + it.vowel }
        }

        private fun koIpa(word: String): String {
            val syls = UniKeySyllable.parseHangul(word)
            return syls.joinToString("") { it.consonant + it.vowel }
        }

        private fun hiIpa(word: String): String {
            val syls = UniKeySyllable.parseDevanagari(word)
            return syls.joinToString("") { it.consonant + it.vowel }
        }

        private fun zhIpa(word: String): String {
            val syls = UniKeySyllable.parseCjk(word)
            return syls.joinToString("") { it.consonant + it.vowel }
        }
    }
}

/**
 * Text direction for layout
 */
enum class TextDirection {
    LTR,  // Left-to-right (English, German, etc.)
    RTL   // Right-to-left (Hebrew, Arabic)
}

/**
 * Language-specific hints for AI translation prompts.
 * These guide Claude's analysis and translation for each target language.
 */
data class LanguagePromptHints(
    val linguistRole: String,
    val culturalNotes: String,
    val rhymeNotes: String,
    val grammarNotes: String,
    val genderNotes: String? = null,
    val scriptNotes: String? = null
) {
    companion object {
        val HEBREW = LanguagePromptHints(
            linguistRole = "Hebrew linguist and English poet",
            culturalNotes = "Consider Biblical vs Modern Hebrew register, Sephardic vs Ashkenazi pronunciation",
            rhymeNotes = "Hebrew rhymes on final syllable stress; nikud affects vowel quality",
            grammarNotes = "Root-based morphology (shoresh); verb binyanim affect meaning",
            genderNotes = "Grammatical gender affects all nouns, adjectives, verbs",
            scriptNotes = "Nikud (vowel points) optional but affects pronunciation; dagesh changes consonants"
        )

        val ARABIC = LanguagePromptHints(
            linguistRole = "Arabic linguist and English poet",
            culturalNotes = "Consider Classical vs Modern Standard vs dialectal register",
            rhymeNotes = "Arabic poetry has rich rhyme traditions (qafiya); root echoes matter",
            grammarNotes = "Triliteral root system; verb forms (awzan) carry semantic weight",
            genderNotes = "Grammatical gender; dual number exists",
            scriptNotes = "Harakat (short vowels) usually omitted; affects pronunciation"
        )

        val RUSSIAN = LanguagePromptHints(
            linguistRole = "Russian linguist and English poet",
            culturalNotes = "Consider formal vs informal register; Church Slavonic influences in poetry",
            rhymeNotes = "Russian favors rich rhymes; stress patterns crucial (ударение)",
            grammarNotes = "Case system; aspect pairs; word order flexible",
            genderNotes = "Three genders; affects adjective/verb agreement"
        )

        val GREEK = LanguagePromptHints(
            linguistRole = "Greek linguist and English poet",
            culturalNotes = "Consider Ancient vs Modern Greek; Katharevousa vs Demotic",
            rhymeNotes = "Stress-based rhythm; final syllable rhymes common",
            grammarNotes = "Cases (4 in Modern); verb aspect; article usage",
            genderNotes = "Three genders; neuter plurals take singular verbs"
        )

        val GERMAN = LanguagePromptHints(
            linguistRole = "German linguist and English poet",
            culturalNotes = "Consider formal (Sie) vs informal (du); regional variation",
            rhymeNotes = "German allows compound words for creative rhyming",
            grammarNotes = "Case system (4 cases); verb-final in subordinate clauses",
            genderNotes = "Three genders (der/die/das); affects all articles/adjectives"
        )

        val FRENCH = LanguagePromptHints(
            linguistRole = "French linguist and English poet",
            culturalNotes = "Distinguish formal literary vs conversational register",
            rhymeNotes = "Classical French poetry: rime riche, alternating masculine/feminine endings",
            grammarNotes = "Liaison affects pronunciation; subjunctive mood matters",
            genderNotes = "Two genders; affects articles, adjectives, past participles"
        )

        val SPANISH = LanguagePromptHints(
            linguistRole = "Spanish linguist and English poet",
            culturalNotes = "Consider Castilian vs Latin American; voseo regions",
            rhymeNotes = "Asonancia (assonance) common; stress affects rhyme",
            grammarNotes = "Subjunctive heavily used; ser vs estar",
            genderNotes = "Two genders; some nouns change meaning by gender"
        )

        val ITALIAN = LanguagePromptHints(
            linguistRole = "Italian linguist and English poet",
            culturalNotes = "Rich poetic tradition (Dante, Petrarch); formal vs informal",
            rhymeNotes = "Italian has abundant rhymes due to vowel-final words",
            grammarNotes = "Double consonants affect meaning; subjunctive important",
            genderNotes = "Two genders; irregular plurals common"
        )

        val JAPANESE = LanguagePromptHints(
            linguistRole = "Japanese linguist and English poet",
            culturalNotes = "Consider keigo (politeness levels); literary vs colloquial",
            rhymeNotes = "Japanese poetry favors mora count (5-7-5); less focus on end rhyme",
            grammarNotes = "SOV order; particles mark grammatical function; topic vs subject",
            scriptNotes = "Kanji + hiragana + katakana; kanji choice affects tone"
        )

        val KOREAN = LanguagePromptHints(
            linguistRole = "Korean linguist and English poet",
            culturalNotes = "Speech levels (존댓말 vs 반말); honorifics",
            rhymeNotes = "Korean has syllable blocks; final consonants affect rhyme",
            grammarNotes = "SOV order; particles; verb endings carry much meaning",
            scriptNotes = "Hangul is alphabetic but arranged in syllable blocks"
        )

        val HINDI = LanguagePromptHints(
            linguistRole = "Hindi linguist and English poet",
            culturalNotes = "Sanskrit-derived vs Perso-Arabic vocabulary; formal vs casual",
            rhymeNotes = "Hindi poetry has strong rhyme traditions; nasalization matters",
            grammarNotes = "SOV order; postpositions; ergativity in past tense",
            genderNotes = "Two genders; affects verbs in past tense",
            scriptNotes = "Devanagari; conjunct consonants; inherent 'a' vowel"
        )

        val CHINESE = LanguagePromptHints(
            linguistRole = "Chinese linguist and English poet",
            culturalNotes = "Classical vs Modern Chinese; literary allusions important",
            rhymeNotes = "Tones affect meaning; classical poetry has strict tonal patterns",
            grammarNotes = "No inflection; aspect markers; classifier system",
            scriptNotes = "Characters carry meaning; simplified vs traditional"
        )
    }
}

/**
 * Translation pair configuration for source→target language.
 */
data class TranslationPair(
    val source: Lang,
    val target: TargetLanguage
) {
    /** Generate the AI system prompt for Stage 1 analysis */
    fun generateAnalysisPrompt(context: String = ""): String {
        val hints = target.aiPromptHints
        return buildString {
            appendLine("You are a ${hints.linguistRole}.")
            appendLine()
            appendLine("Context: ${context.ifEmpty { "Poetry translation" }}")
            appendLine()
            appendLine("Cultural considerations: ${hints.culturalNotes}")
            appendLine("Rhyme notes: ${hints.rhymeNotes}")
            appendLine("Grammar: ${hints.grammarNotes}")
            hints.genderNotes?.let { appendLine("Gender: $it") }
            hints.scriptNotes?.let { appendLine("Script: $it") }
            appendLine()
            appendLine("Analyze the ${target.nativeName} source text and provide:")
            appendLine("A) WORDS: 3-5 key content words with IPA, synonyms, and cultural notes")
            appendLine("B) PAIRS: viable end-rhyme pairs from the synonym pool")
            appendLine("C) TARGETS: top English words matching the ${target.nativeName} IPA endings")
        }
    }

    /** Generate the AI system prompt for Stage 2 path building */
    fun generatePathPrompt(context: String = ""): String {
        val hints = target.aiPromptHints
        return buildString {
            appendLine("You are an English poet refining a translation from ${target.nativeName}.")
            appendLine()
            appendLine(context)
            appendLine()
            appendLine("THE 6 PATHS:")
            appendLine("1 IPA ECHO — pick synonyms whose IPA end most closely echoes the ${target.nativeName} source IPA")
            appendLine("2 LITERAL ANCHOR — pick the most denotatively accurate synonym")
            appendLine("3 CULTURAL CHARGE — pick the synonym with the strongest ${target.nativeName} cultural/register charge")
            appendLine("4 EMOTIONAL REGISTER — pick synonyms matching emotional intensity")
            appendLine("5 ENGLISH IDIOM — most natural English, zero ${target.nativeName}isms")
            appendLine("6 COMPRESSION — shortest/hardest synonym; fewest syllables")
            appendLine()
            appendLine("Rhyme rules for ${target.nativeName}: ${hints.rhymeNotes}")
        }
    }

    companion object {
        /** Common translation pairs */
        val EN_TO_HE = TranslationPair(Lang.EN, TargetLanguage.HEBREW)
        val EN_TO_AR = TranslationPair(Lang.EN, TargetLanguage.ARABIC)
        val EN_TO_RU = TranslationPair(Lang.EN, TargetLanguage.RUSSIAN)
        val EN_TO_DE = TranslationPair(Lang.EN, TargetLanguage.GERMAN)
        val EN_TO_FR = TranslationPair(Lang.EN, TargetLanguage.FRENCH)
        val EN_TO_ES = TranslationPair(Lang.EN, TargetLanguage.SPANISH)
        val EN_TO_IT = TranslationPair(Lang.EN, TargetLanguage.ITALIAN)
        val EN_TO_JA = TranslationPair(Lang.EN, TargetLanguage.JAPANESE)
        val EN_TO_ZH = TranslationPair(Lang.EN, TargetLanguage.CHINESE)
    }
}
