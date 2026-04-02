package al.clk.key

/**
 * Language configuration for bilingual poetry translation.
 * Supports all 23 Chatterbox languages as both source and target.
 * Uses IPA as the universal phonetic hub for cross-language rhyme matching.
 *
 * This enables the poetry editor to support any (source, target) language pair.
 */
data class PoetryLanguage(
    val lang: Lang,
    val nativeName: String,
    val englishName: String,
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
        // ═══════════════════════════════════════════════════════════════════════════
        // NON-LATIN SCRIPTS (9 languages)
        // ═══════════════════════════════════════════════════════════════════════════

        val HEBREW = PoetryLanguage(
            lang = Lang.HE,
            nativeName = "עברית",
            englishName = "Hebrew",
            direction = TextDirection.RTL,
            syllableParser = { UniKeySyllable.parseHebrew(it) },
            ipaConverter = { IpaColor.heIpa(it) },
            aiPromptHints = LanguagePromptHints.HEBREW
        )

        val ARABIC = PoetryLanguage(
            lang = Lang.AR,
            nativeName = "العربية",
            englishName = "Arabic",
            direction = TextDirection.RTL,
            syllableParser = { UniKeySyllable.parseArabic(it) },
            ipaConverter = { syllablesToIpa(UniKeySyllable.parseArabic(it)) },
            aiPromptHints = LanguagePromptHints.ARABIC
        )

        val RUSSIAN = PoetryLanguage(
            lang = Lang.RU,
            nativeName = "Русский",
            englishName = "Russian",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseCyrillic(it) },
            ipaConverter = { syllablesToIpa(UniKeySyllable.parseCyrillic(it)) },
            aiPromptHints = LanguagePromptHints.RUSSIAN
        )

        val GREEK = PoetryLanguage(
            lang = Lang.EL,
            nativeName = "Ελληνικά",
            englishName = "Greek",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseGreek(it) },
            ipaConverter = { syllablesToIpa(UniKeySyllable.parseGreek(it)) },
            aiPromptHints = LanguagePromptHints.GREEK
        )

        val HINDI = PoetryLanguage(
            lang = Lang.HI,
            nativeName = "हिन्दी",
            englishName = "Hindi",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseDevanagari(it) },
            ipaConverter = { syllablesToIpa(UniKeySyllable.parseDevanagari(it)) },
            aiPromptHints = LanguagePromptHints.HINDI
        )

        val JAPANESE = PoetryLanguage(
            lang = Lang.JA,
            nativeName = "日本語",
            englishName = "Japanese",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseJapanese(it) },
            ipaConverter = { syllablesToIpa(UniKeySyllable.parseJapanese(it)) },
            aiPromptHints = LanguagePromptHints.JAPANESE
        )

        val KOREAN = PoetryLanguage(
            lang = Lang.KO,
            nativeName = "한국어",
            englishName = "Korean",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseHangul(it) },
            ipaConverter = { syllablesToIpa(UniKeySyllable.parseHangul(it)) },
            aiPromptHints = LanguagePromptHints.KOREAN
        )

        val CHINESE = PoetryLanguage(
            lang = Lang.ZH,
            nativeName = "中文",
            englishName = "Chinese",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseCjk(it) },
            ipaConverter = { syllablesToIpa(UniKeySyllable.parseCjk(it)) },
            aiPromptHints = LanguagePromptHints.CHINESE
        )

        // ═══════════════════════════════════════════════════════════════════════════
        // LATIN SCRIPTS (14 languages)
        // ═══════════════════════════════════════════════════════════════════════════

        val ENGLISH = PoetryLanguage(
            lang = Lang.EN,
            nativeName = "English",
            englishName = "English",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseEnglish(it) },
            ipaConverter = { IpaColor.enIpa(it) },
            aiPromptHints = LanguagePromptHints.ENGLISH
        )

        val GERMAN = PoetryLanguage(
            lang = Lang.DE,
            nativeName = "Deutsch",
            englishName = "German",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.DE) },
            ipaConverter = { latinIpa(it, GermanPattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.GERMAN
        )

        val FRENCH = PoetryLanguage(
            lang = Lang.FR,
            nativeName = "Français",
            englishName = "French",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.FR) },
            ipaConverter = { latinIpa(it, FrenchPattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.FRENCH
        )

        val SPANISH = PoetryLanguage(
            lang = Lang.ES,
            nativeName = "Español",
            englishName = "Spanish",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.ES) },
            ipaConverter = { latinIpa(it, SpanishPattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.SPANISH
        )

        val ITALIAN = PoetryLanguage(
            lang = Lang.IT,
            nativeName = "Italiano",
            englishName = "Italian",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.IT) },
            ipaConverter = { latinIpa(it, ItalianPattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.ITALIAN
        )

        val PORTUGUESE = PoetryLanguage(
            lang = Lang.PT,
            nativeName = "Português",
            englishName = "Portuguese",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.PT) },
            ipaConverter = { latinIpa(it, PortuguesePattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.PORTUGUESE
        )

        val DUTCH = PoetryLanguage(
            lang = Lang.NL,
            nativeName = "Nederlands",
            englishName = "Dutch",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.NL) },
            ipaConverter = { latinIpa(it, DutchPattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.DUTCH
        )

        val POLISH = PoetryLanguage(
            lang = Lang.PL,
            nativeName = "Polski",
            englishName = "Polish",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.PL) },
            ipaConverter = { latinIpa(it, PolishPattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.POLISH
        )

        val TURKISH = PoetryLanguage(
            lang = Lang.TR,
            nativeName = "Türkçe",
            englishName = "Turkish",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.TR) },
            ipaConverter = { latinIpa(it, TurkishPattern::matchEnding) },
            aiPromptHints = LanguagePromptHints.TURKISH
        )

        val DANISH = PoetryLanguage(
            lang = Lang.DA,
            nativeName = "Dansk",
            englishName = "Danish",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.DA) },
            ipaConverter = { it.lowercase().takeLast(3) },
            aiPromptHints = LanguagePromptHints.DANISH
        )

        val FINNISH = PoetryLanguage(
            lang = Lang.FI,
            nativeName = "Suomi",
            englishName = "Finnish",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.FI) },
            ipaConverter = { it.lowercase().takeLast(3) },
            aiPromptHints = LanguagePromptHints.FINNISH
        )

        val NORWEGIAN = PoetryLanguage(
            lang = Lang.NO,
            nativeName = "Norsk",
            englishName = "Norwegian",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.NO) },
            ipaConverter = { it.lowercase().takeLast(3) },
            aiPromptHints = LanguagePromptHints.NORWEGIAN
        )

        val SWEDISH = PoetryLanguage(
            lang = Lang.SV,
            nativeName = "Svenska",
            englishName = "Swedish",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.SV) },
            ipaConverter = { it.lowercase().takeLast(3) },
            aiPromptHints = LanguagePromptHints.SWEDISH
        )

        val MALAY = PoetryLanguage(
            lang = Lang.MS,
            nativeName = "Bahasa Melayu",
            englishName = "Malay",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.MS) },
            ipaConverter = { it.lowercase().takeLast(3) },
            aiPromptHints = LanguagePromptHints.MALAY
        )

        val SWAHILI = PoetryLanguage(
            lang = Lang.SW,
            nativeName = "Kiswahili",
            englishName = "Swahili",
            direction = TextDirection.LTR,
            syllableParser = { UniKeySyllable.parseLatin(it, Lang.SW) },
            ipaConverter = { it.lowercase().takeLast(3) },
            aiPromptHints = LanguagePromptHints.SWAHILI
        )

        /** All 23 supported languages */
        val ALL: List<PoetryLanguage> = listOf(
            // Non-Latin scripts
            HEBREW, ARABIC, RUSSIAN, GREEK, HINDI, JAPANESE, KOREAN, CHINESE,
            // Latin scripts
            ENGLISH, GERMAN, FRENCH, SPANISH, ITALIAN, PORTUGUESE, DUTCH, POLISH,
            TURKISH, DANISH, FINNISH, NORWEGIAN, SWEDISH, MALAY, SWAHILI
        )

        /** Count of supported languages */
        val COUNT: Int get() = ALL.size

        private val byCode = ALL.associateBy { it.code }
        private val byLang = ALL.associateBy { it.lang }

        /** Get language by ISO code */
        fun fromCode(code: String): PoetryLanguage? = byCode[code.lowercase()]

        /** Get language by Lang enum */
        fun fromLang(lang: Lang): PoetryLanguage? = byLang[lang]

        /** Get all valid translation pairs (23 x 22 = 506 pairs) */
        fun allPairs(): List<TranslationPair> = ALL.flatMap { src ->
            ALL.filter { it != src }.map { tgt -> TranslationPair(src, tgt) }
        }

        // === Helper functions ===

        private fun syllablesToIpa(syllables: List<UniKeySyllable>): String =
            syllables.joinToString("") { it.consonant + it.vowel }

        private fun latinIpa(word: String, matcher: (String) -> ISpellingPattern?): String {
            val w = word.lowercase()
            val pattern = matcher(w)
            return pattern?.ipa ?: w.takeLast(3)
        }
    }
}

/** Backward compatibility alias */
typealias TargetLanguage = PoetryLanguage

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
        // ═══════════════════════════════════════════════════════════════════════════
        // NON-LATIN SCRIPTS
        // ═══════════════════════════════════════════════════════════════════════════

        val HEBREW = LanguagePromptHints(
            linguistRole = "Hebrew linguist",
            culturalNotes = "Consider Biblical vs Modern Hebrew register, Sephardic vs Ashkenazi pronunciation",
            rhymeNotes = "Hebrew rhymes on final syllable stress; nikud affects vowel quality",
            grammarNotes = "Root-based morphology (shoresh); verb binyanim affect meaning",
            genderNotes = "Grammatical gender affects all nouns, adjectives, verbs",
            scriptNotes = "Nikud (vowel points) optional but affects pronunciation; dagesh changes consonants"
        )

        val ARABIC = LanguagePromptHints(
            linguistRole = "Arabic linguist",
            culturalNotes = "Consider Classical vs Modern Standard vs dialectal register",
            rhymeNotes = "Arabic poetry has rich rhyme traditions (qafiya); root echoes matter",
            grammarNotes = "Triliteral root system; verb forms (awzan) carry semantic weight",
            genderNotes = "Grammatical gender; dual number exists",
            scriptNotes = "Harakat (short vowels) usually omitted; affects pronunciation"
        )

        val RUSSIAN = LanguagePromptHints(
            linguistRole = "Russian linguist",
            culturalNotes = "Consider formal vs informal register; Church Slavonic influences in poetry",
            rhymeNotes = "Russian favors rich rhymes; stress patterns crucial (ударение)",
            grammarNotes = "Case system; aspect pairs; word order flexible",
            genderNotes = "Three genders; affects adjective/verb agreement"
        )

        val GREEK = LanguagePromptHints(
            linguistRole = "Greek linguist",
            culturalNotes = "Consider Ancient vs Modern Greek; Katharevousa vs Demotic",
            rhymeNotes = "Stress-based rhythm; final syllable rhymes common",
            grammarNotes = "Cases (4 in Modern); verb aspect; article usage",
            genderNotes = "Three genders; neuter plurals take singular verbs"
        )

        val HINDI = LanguagePromptHints(
            linguistRole = "Hindi linguist",
            culturalNotes = "Sanskrit-derived vs Perso-Arabic vocabulary; formal vs casual",
            rhymeNotes = "Hindi poetry has strong rhyme traditions; nasalization matters",
            grammarNotes = "SOV order; postpositions; ergativity in past tense",
            genderNotes = "Two genders; affects verbs in past tense",
            scriptNotes = "Devanagari; conjunct consonants; inherent 'a' vowel"
        )

        val JAPANESE = LanguagePromptHints(
            linguistRole = "Japanese linguist",
            culturalNotes = "Consider keigo (politeness levels); literary vs colloquial",
            rhymeNotes = "Japanese poetry favors mora count (5-7-5); less focus on end rhyme",
            grammarNotes = "SOV order; particles mark grammatical function; topic vs subject",
            scriptNotes = "Kanji + hiragana + katakana; kanji choice affects tone"
        )

        val KOREAN = LanguagePromptHints(
            linguistRole = "Korean linguist",
            culturalNotes = "Speech levels (존댓말 vs 반말); honorifics",
            rhymeNotes = "Korean has syllable blocks; final consonants affect rhyme",
            grammarNotes = "SOV order; particles; verb endings carry much meaning",
            scriptNotes = "Hangul is alphabetic but arranged in syllable blocks"
        )

        val CHINESE = LanguagePromptHints(
            linguistRole = "Chinese linguist",
            culturalNotes = "Classical vs Modern Chinese; literary allusions important",
            rhymeNotes = "Tones affect meaning; classical poetry has strict tonal patterns",
            grammarNotes = "No inflection; aspect markers; classifier system",
            scriptNotes = "Characters carry meaning; simplified vs traditional"
        )

        // ═══════════════════════════════════════════════════════════════════════════
        // LATIN SCRIPTS
        // ═══════════════════════════════════════════════════════════════════════════

        val ENGLISH = LanguagePromptHints(
            linguistRole = "English linguist",
            culturalNotes = "Consider British vs American register; formal vs colloquial",
            rhymeNotes = "English has rich rhyme traditions; stress-timed rhythm; slant rhymes accepted",
            grammarNotes = "SVO order; minimal inflection; phrasal verbs important"
        )

        val GERMAN = LanguagePromptHints(
            linguistRole = "German linguist",
            culturalNotes = "Consider formal (Sie) vs informal (du); regional variation",
            rhymeNotes = "German allows compound words for creative rhyming",
            grammarNotes = "Case system (4 cases); verb-final in subordinate clauses",
            genderNotes = "Three genders (der/die/das); affects all articles/adjectives"
        )

        val FRENCH = LanguagePromptHints(
            linguistRole = "French linguist",
            culturalNotes = "Distinguish formal literary vs conversational register",
            rhymeNotes = "Classical French poetry: rime riche, alternating masculine/feminine endings",
            grammarNotes = "Liaison affects pronunciation; subjunctive mood matters",
            genderNotes = "Two genders; affects articles, adjectives, past participles"
        )

        val SPANISH = LanguagePromptHints(
            linguistRole = "Spanish linguist",
            culturalNotes = "Consider Castilian vs Latin American; voseo regions",
            rhymeNotes = "Asonancia (assonance) common; stress affects rhyme",
            grammarNotes = "Subjunctive heavily used; ser vs estar",
            genderNotes = "Two genders; some nouns change meaning by gender"
        )

        val ITALIAN = LanguagePromptHints(
            linguistRole = "Italian linguist",
            culturalNotes = "Rich poetic tradition (Dante, Petrarch); formal vs informal",
            rhymeNotes = "Italian has abundant rhymes due to vowel-final words",
            grammarNotes = "Double consonants affect meaning; subjunctive important",
            genderNotes = "Two genders; irregular plurals common"
        )

        val PORTUGUESE = LanguagePromptHints(
            linguistRole = "Portuguese linguist",
            culturalNotes = "Consider European vs Brazilian Portuguese; formal vs colloquial",
            rhymeNotes = "Portuguese has nasal vowels; rich lyrical tradition (Camões, Pessoa)",
            grammarNotes = "Personal infinitive unique to Portuguese; subjunctive important",
            genderNotes = "Two genders; augmentative/diminutive suffixes common"
        )

        val DUTCH = LanguagePromptHints(
            linguistRole = "Dutch linguist",
            culturalNotes = "Consider Netherlandic vs Flemish; formal vs informal (u/jij)",
            rhymeNotes = "Dutch has guttural sounds (g, ch); compound words for rhyming",
            grammarNotes = "V2 word order; diminutives (-je) very common",
            genderNotes = "Common/neuter gender (de/het)"
        )

        val POLISH = LanguagePromptHints(
            linguistRole = "Polish linguist",
            culturalNotes = "Rich literary tradition; formal vs familiar",
            rhymeNotes = "Polish has fixed penultimate stress; consonant clusters affect rhyme",
            grammarNotes = "Complex case system (7 cases); aspect pairs; flexible word order",
            genderNotes = "Three genders in singular; virile/non-virile in plural"
        )

        val TURKISH = LanguagePromptHints(
            linguistRole = "Turkish linguist",
            culturalNotes = "Ottoman literary heritage; formal vs casual register",
            rhymeNotes = "Vowel harmony affects suffixes; agglutinative morphology enables rhyming",
            grammarNotes = "SOV order; agglutinative; vowel harmony; no grammatical gender",
            scriptNotes = "Latin script since 1928; some Turkish-specific letters (ğ, ş, ı)"
        )

        val DANISH = LanguagePromptHints(
            linguistRole = "Danish linguist",
            culturalNotes = "Nordic literary tradition; formal vs informal",
            rhymeNotes = "Stød (glottal stop) affects rhyme; vowel-rich endings",
            grammarNotes = "V2 word order; definite article suffixed; two genders",
            genderNotes = "Common/neuter gender (en/et)"
        )

        val FINNISH = LanguagePromptHints(
            linguistRole = "Finnish linguist",
            culturalNotes = "Kalevala meter tradition; unique among European languages",
            rhymeNotes = "Vowel harmony; long/short vowels matter; alliteration traditional",
            grammarNotes = "15 cases; agglutinative; no grammatical gender; no articles",
            scriptNotes = "Uses ä, ö; double letters indicate length"
        )

        val NORWEGIAN = LanguagePromptHints(
            linguistRole = "Norwegian linguist",
            culturalNotes = "Bokmål vs Nynorsk; rich folk poetry tradition",
            rhymeNotes = "Pitch accent affects meaning; similar to Swedish/Danish rhyme",
            grammarNotes = "V2 word order; definite article suffixed; three genders (or two)",
            genderNotes = "Masculine/feminine/neuter (or common/neuter)"
        )

        val SWEDISH = LanguagePromptHints(
            linguistRole = "Swedish linguist",
            culturalNotes = "Nobel Prize language; formal vs casual",
            rhymeNotes = "Pitch accent (acute/grave); vowel length matters",
            grammarNotes = "V2 word order; definite article suffixed; two genders",
            genderNotes = "Common/neuter gender (en/ett)"
        )

        val MALAY = LanguagePromptHints(
            linguistRole = "Malay/Indonesian linguist",
            culturalNotes = "Pantun (quatrain) poetic tradition; formal vs informal register",
            rhymeNotes = "Pantun has strict ABAB rhyme; often witty/allusive",
            grammarNotes = "No inflection; affixes modify meaning; no grammatical gender"
        )

        val SWAHILI = LanguagePromptHints(
            linguistRole = "Swahili linguist",
            culturalNotes = "Bantu language with Arabic influences; East African literary tradition",
            rhymeNotes = "Shairi poetry has strict meter; vowel-final words easy to rhyme",
            grammarNotes = "Noun class system (18 classes); agglutinative; SVO order"
        )
    }
}

/**
 * Translation pair configuration for source→target language.
 * Supports any of the 23×22 = 506 possible language pairs.
 */
data class TranslationPair(
    val source: PoetryLanguage,
    val target: PoetryLanguage
) {
    /** Source language code */
    val srcCode: String get() = source.code

    /** Target language code */
    val tgtCode: String get() = target.code

    /** Pair identifier (e.g., "en→he") */
    val pairId: String get() = "${source.code}→${target.code}"

    /** Generate the AI system prompt for Stage 1 analysis */
    fun generateAnalysisPrompt(context: String = ""): String {
        val srcHints = source.aiPromptHints
        val tgtHints = target.aiPromptHints
        return buildString {
            appendLine("You are a bilingual ${source.englishName}-${target.englishName} linguist and poet.")
            appendLine()
            appendLine("Context: ${context.ifEmpty { "Poetry translation from ${source.nativeName} to ${target.nativeName}" }}")
            appendLine()
            appendLine("SOURCE LANGUAGE (${source.nativeName}):")
            appendLine("  Cultural: ${srcHints.culturalNotes}")
            appendLine("  Rhyme: ${srcHints.rhymeNotes}")
            appendLine("  Grammar: ${srcHints.grammarNotes}")
            srcHints.genderNotes?.let { appendLine("  Gender: $it") }
            srcHints.scriptNotes?.let { appendLine("  Script: $it") }
            appendLine()
            appendLine("TARGET LANGUAGE (${target.nativeName}):")
            appendLine("  Cultural: ${tgtHints.culturalNotes}")
            appendLine("  Rhyme: ${tgtHints.rhymeNotes}")
            appendLine("  Grammar: ${tgtHints.grammarNotes}")
            tgtHints.genderNotes?.let { appendLine("  Gender: $it") }
            tgtHints.scriptNotes?.let { appendLine("  Script: $it") }
            appendLine()
            appendLine("Analyze the ${source.nativeName} source text and provide:")
            appendLine("A) WORDS: 3-5 key content words with IPA, ${target.englishName} synonyms, and cultural notes")
            appendLine("B) PAIRS: viable end-rhyme pairs from the synonym pool")
            appendLine("C) TARGETS: top ${target.englishName} words matching the ${source.nativeName} IPA endings")
        }
    }

    /** Generate the AI system prompt for Stage 2 path building */
    fun generatePathPrompt(context: String = ""): String {
        val tgtHints = target.aiPromptHints
        return buildString {
            appendLine("You are a ${target.englishName} poet refining a translation from ${source.nativeName}.")
            appendLine()
            appendLine(context)
            appendLine()
            appendLine("THE 6 PATHS:")
            appendLine("1 IPA ECHO — pick synonyms whose IPA end most closely echoes the ${source.nativeName} source IPA")
            appendLine("2 LITERAL ANCHOR — pick the most denotatively accurate synonym")
            appendLine("3 CULTURAL CHARGE — pick the synonym with the strongest ${source.nativeName} cultural/register charge")
            appendLine("4 EMOTIONAL REGISTER — pick synonyms matching emotional intensity")
            appendLine("5 ${target.englishName.uppercase()} IDIOM — most natural ${target.englishName}, zero ${source.englishName}isms")
            appendLine("6 COMPRESSION — shortest/hardest synonym; fewest syllables")
            appendLine()
            appendLine("Rhyme rules for ${target.nativeName}: ${tgtHints.rhymeNotes}")
        }
    }

    companion object {
        /** Create pair from language codes */
        fun fromCodes(srcCode: String, tgtCode: String): TranslationPair? {
            val src = PoetryLanguage.fromCode(srcCode) ?: return null
            val tgt = PoetryLanguage.fromCode(tgtCode) ?: return null
            if (src == tgt) return null
            return TranslationPair(src, tgt)
        }

        // === Common pairs (for convenience) ===

        // English as source
        val EN_TO_HE = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.HEBREW)
        val EN_TO_AR = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.ARABIC)
        val EN_TO_RU = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.RUSSIAN)
        val EN_TO_DE = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.GERMAN)
        val EN_TO_FR = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.FRENCH)
        val EN_TO_ES = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.SPANISH)
        val EN_TO_IT = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.ITALIAN)
        val EN_TO_JA = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.JAPANESE)
        val EN_TO_ZH = TranslationPair(PoetryLanguage.ENGLISH, PoetryLanguage.CHINESE)

        // Hebrew as source (original POC direction reversed)
        val HE_TO_EN = TranslationPair(PoetryLanguage.HEBREW, PoetryLanguage.ENGLISH)

        // Other common pairs
        val FR_TO_EN = TranslationPair(PoetryLanguage.FRENCH, PoetryLanguage.ENGLISH)
        val DE_TO_EN = TranslationPair(PoetryLanguage.GERMAN, PoetryLanguage.ENGLISH)
        val ES_TO_EN = TranslationPair(PoetryLanguage.SPANISH, PoetryLanguage.ENGLISH)
        val RU_TO_EN = TranslationPair(PoetryLanguage.RUSSIAN, PoetryLanguage.ENGLISH)
        val JA_TO_EN = TranslationPair(PoetryLanguage.JAPANESE, PoetryLanguage.ENGLISH)
        val ZH_TO_EN = TranslationPair(PoetryLanguage.CHINESE, PoetryLanguage.ENGLISH)
        val AR_TO_EN = TranslationPair(PoetryLanguage.ARABIC, PoetryLanguage.ENGLISH)
    }
}
