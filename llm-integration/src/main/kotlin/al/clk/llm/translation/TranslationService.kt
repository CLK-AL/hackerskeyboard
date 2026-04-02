package al.clk.llm.translation

import al.clk.llm.*
import al.clk.llm.config.LlmConfig
import al.clk.llm.provider.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Poetry translation service supporting multiple LLM backends.
 * Designed for bilingual poetry translation with IPA-based rhyme matching.
 */
class TranslationService(
    private val provider: LlmProvider,
    private val config: TranslationConfig = TranslationConfig.DEFAULT
) {
    /**
     * Translate a single line of poetry.
     */
    suspend fun translateLine(
        sourceLine: String,
        sourceLanguage: Language,
        targetLanguage: Language,
        context: TranslationContext = TranslationContext()
    ): TranslationResult {
        val prompt = buildTranslationPrompt(
            text = sourceLine,
            source = sourceLanguage,
            target = targetLanguage,
            context = context,
            mode = TranslationMode.LINE
        )

        val response = provider.complete(
            CompletionRequest(
                messages = prompt,
                maxTokens = config.maxTokensPerLine,
                temperature = config.temperature,
                topP = config.topP
            )
        )

        return parseTranslationResult(response.content, targetLanguage)
    }

    /**
     * Translate a stanza (multiple lines) preserving rhyme scheme.
     */
    suspend fun translateStanza(
        stanza: List<String>,
        sourceLanguage: Language,
        targetLanguage: Language,
        context: TranslationContext = TranslationContext()
    ): StanzaTranslation {
        val prompt = buildTranslationPrompt(
            text = stanza.joinToString("\n"),
            source = sourceLanguage,
            target = targetLanguage,
            context = context,
            mode = TranslationMode.STANZA
        )

        val response = provider.complete(
            CompletionRequest(
                messages = prompt,
                maxTokens = config.maxTokensPerStanza,
                temperature = config.temperature,
                topP = config.topP
            )
        )

        return parseStanzaTranslation(response.content, stanza.size, targetLanguage)
    }

    /**
     * Generate multiple translation paths for a stanza.
     * Returns 6 alternatives: IPA Echo, Literal, Cultural, Emotional, Idiom, Compress
     */
    suspend fun generatePaths(
        stanza: List<String>,
        sourceLanguage: Language,
        targetLanguage: Language,
        existingTranslation: List<String>? = null,
        context: TranslationContext = TranslationContext()
    ): List<TranslationPath> {
        val paths = mutableListOf<TranslationPath>()

        for (pathType in PathType.entries) {
            val prompt = buildPathPrompt(
                stanza = stanza,
                source = sourceLanguage,
                target = targetLanguage,
                pathType = pathType,
                existingTranslation = existingTranslation,
                context = context
            )

            val response = provider.complete(
                CompletionRequest(
                    messages = prompt,
                    maxTokens = config.maxTokensPerPath,
                    temperature = pathType.temperature,
                    topP = config.topP
                )
            )

            paths.add(
                TranslationPath(
                    type = pathType,
                    lines = parsePathLines(response.content, stanza.size),
                    rationale = extractRationale(response.content)
                )
            )
        }

        return paths
    }

    /**
     * Analyze source text for translation preparation (Stage 1).
     */
    suspend fun analyze(
        stanza: List<String>,
        sourceLanguage: Language,
        targetLanguage: Language
    ): AnalysisResult {
        val prompt = buildAnalysisPrompt(stanza, sourceLanguage, targetLanguage)

        val response = provider.complete(
            CompletionRequest(
                messages = prompt,
                maxTokens = config.maxTokensPerAnalysis,
                temperature = 0.3f, // Lower temperature for analysis
                topP = 0.9f
            )
        )

        return parseAnalysisResult(response.content)
    }

    /**
     * Stream translation token by token.
     */
    fun translateLineStream(
        sourceLine: String,
        sourceLanguage: Language,
        targetLanguage: Language,
        context: TranslationContext = TranslationContext()
    ): Flow<String> {
        val prompt = buildTranslationPrompt(
            text = sourceLine,
            source = sourceLanguage,
            target = targetLanguage,
            context = context,
            mode = TranslationMode.LINE
        )

        return provider.completeStream(
            CompletionRequest(
                messages = prompt,
                maxTokens = config.maxTokensPerLine,
                temperature = config.temperature,
                topP = config.topP,
                stream = true
            )
        )
    }

    private fun buildTranslationPrompt(
        text: String,
        source: Language,
        target: Language,
        context: TranslationContext,
        mode: TranslationMode
    ): List<ChatMessage> {
        val systemPrompt = """
            |You are an expert poetry translator specializing in ${source.nativeName} to ${target.nativeName} translation.
            |
            |Guidelines:
            |1. Preserve the poetic rhythm and meter where possible
            |2. Maintain rhyme schemes - prioritize sonic similarity (IPA endings)
            |3. Capture emotional resonance and cultural connotations
            |4. ${if (source == Language.HEBREW) "Pay attention to nikud (vowel marks) for precise pronunciation" else ""}
            |5. ${if (target == Language.HEBREW) "Use appropriate register (biblical, modern, etc.)" else ""}
            |
            |${context.additionalInstructions ?: ""}
            |
            |Respond with ONLY the translation, no explanations.
        """.trimMargin()

        val userPrompt = when (mode) {
            TranslationMode.LINE -> "Translate this line:\n$text"
            TranslationMode.STANZA -> "Translate this stanza, preserving line breaks:\n$text"
        }

        return listOf(
            ChatMessage(ChatMessage.Role.SYSTEM, systemPrompt),
            ChatMessage(ChatMessage.Role.USER, userPrompt)
        )
    }

    private fun buildPathPrompt(
        stanza: List<String>,
        source: Language,
        target: Language,
        pathType: PathType,
        existingTranslation: List<String>?,
        context: TranslationContext
    ): List<ChatMessage> {
        val systemPrompt = """
            |You are an expert poetry translator creating a ${pathType.displayName} translation.
            |
            |Translation Strategy: ${pathType.description}
            |
            |Source language: ${source.nativeName}
            |Target language: ${target.nativeName}
            |
            |${existingTranslation?.let { "Reference translation:\n${it.joinToString("\n")}\n" } ?: ""}
            |
            |Provide the translation followed by a brief rationale.
            |Format:
            |TRANSLATION:
            |[your translation lines]
            |
            |RATIONALE:
            |[brief explanation of choices]
        """.trimMargin()

        val userPrompt = "Source stanza:\n${stanza.joinToString("\n")}"

        return listOf(
            ChatMessage(ChatMessage.Role.SYSTEM, systemPrompt),
            ChatMessage(ChatMessage.Role.USER, userPrompt)
        )
    }

    private fun buildAnalysisPrompt(
        stanza: List<String>,
        source: Language,
        target: Language
    ): List<ChatMessage> {
        val systemPrompt = """
            |You are a ${source.nativeName} linguist and ${target.nativeName} poet analyzing text for translation.
            |
            |For each word, provide:
            |1. Original word
            |2. IPA pronunciation
            |3. 3-5 translation synonyms with IPA endings
            |4. Mark sonic matches (similar IPA to source)
            |
            |Also identify:
            |- Rhyme pairs in the source
            |- Target IPA endings to match
            |- Cultural/contextual notes
            |
            |Format as JSON.
        """.trimMargin()

        val userPrompt = "Analyze this stanza:\n${stanza.joinToString("\n")}"

        return listOf(
            ChatMessage(ChatMessage.Role.SYSTEM, systemPrompt),
            ChatMessage(ChatMessage.Role.USER, userPrompt)
        )
    }

    private fun parseTranslationResult(content: String, language: Language): TranslationResult {
        return TranslationResult(
            text = content.trim(),
            language = language,
            confidence = 0.9f // TODO: Implement confidence scoring
        )
    }

    private fun parseStanzaTranslation(
        content: String,
        expectedLines: Int,
        language: Language
    ): StanzaTranslation {
        val lines = content.trim().lines()
            .filter { it.isNotBlank() }
            .take(expectedLines)

        return StanzaTranslation(
            lines = lines.map { TranslationResult(it, language, 0.9f) },
            overallConfidence = 0.9f
        )
    }

    private fun parsePathLines(content: String, expectedLines: Int): List<String> {
        val translationSection = content
            .substringAfter("TRANSLATION:", content)
            .substringBefore("RATIONALE:", content)
            .trim()

        return translationSection.lines()
            .filter { it.isNotBlank() }
            .take(expectedLines)
    }

    private fun extractRationale(content: String): String {
        return content
            .substringAfter("RATIONALE:", "")
            .trim()
            .ifEmpty { "No rationale provided" }
    }

    private fun parseAnalysisResult(content: String): AnalysisResult {
        // TODO: Parse JSON analysis result
        return AnalysisResult(
            words = emptyList(),
            rhymePairs = emptyList(),
            targetIpas = emptyList(),
            notes = content
        )
    }

    companion object {
        /** Create service with LM Studio backend (default) */
        fun withLmStudio(
            modelConfig: ModelConfig = ModelConfig.DICTA_LM3,
            llmConfig: LlmConfig = LlmConfig.DEFAULT,
            translationConfig: TranslationConfig = TranslationConfig.DEFAULT
        ): TranslationService {
            return TranslationService(
                LmStudioProvider(modelConfig, llmConfig),
                translationConfig
            )
        }

        /** Create service with JLama (local GGUF) */
        fun withJLama(
            modelConfig: ModelConfig = ModelConfig.DICTA_LM3,
            llmConfig: LlmConfig = LlmConfig.DEFAULT,
            translationConfig: TranslationConfig = TranslationConfig.DEFAULT
        ): TranslationService {
            return TranslationService(
                JLamaProvider(modelConfig, llmConfig),
                translationConfig
            )
        }

        /** Create service with LangChain4j */
        fun withLangChain4j(
            modelConfig: ModelConfig = ModelConfig.DICTA_LM3,
            llmConfig: LlmConfig = LlmConfig.DEFAULT,
            translationConfig: TranslationConfig = TranslationConfig.DEFAULT
        ): TranslationService {
            return TranslationService(
                LangChain4jProvider.lmStudio(modelConfig, llmConfig),
                translationConfig
            )
        }

        /** Create service with Ollama */
        fun withOllama(
            modelConfig: ModelConfig = ModelConfig.LLAMA_3_1_8B,
            llmConfig: LlmConfig = LlmConfig.DEFAULT,
            translationConfig: TranslationConfig = TranslationConfig.DEFAULT
        ): TranslationService {
            return TranslationService(
                LangChain4jProvider.ollama(modelConfig, llmConfig),
                translationConfig
            )
        }
    }
}

// Supporting data classes

enum class Language(val code: String, val nativeName: String, val englishName: String) {
    HEBREW("he", "עברית", "Hebrew"),
    ENGLISH("en", "English", "English"),
    ARABIC("ar", "العربية", "Arabic"),
    RUSSIAN("ru", "Русский", "Russian"),
    GERMAN("de", "Deutsch", "German"),
    FRENCH("fr", "Français", "French"),
    SPANISH("es", "Español", "Spanish"),
    ITALIAN("it", "Italiano", "Italian"),
    PORTUGUESE("pt", "Português", "Portuguese"),
    DUTCH("nl", "Nederlands", "Dutch"),
    POLISH("pl", "Polski", "Polish"),
    TURKISH("tr", "Türkçe", "Turkish"),
    GREEK("el", "Ελληνικά", "Greek"),
    JAPANESE("ja", "日本語", "Japanese"),
    KOREAN("ko", "한국어", "Korean"),
    CHINESE("zh", "中文", "Chinese"),
    HINDI("hi", "हिन्दी", "Hindi")
}

enum class TranslationMode {
    LINE,
    STANZA
}

enum class PathType(
    val displayName: String,
    val description: String,
    val temperature: Float
) {
    IPA_ECHO(
        "IPA Echo",
        "Maximum sound fidelity to source language. Prioritize IPA similarity in word endings.",
        0.5f
    ),
    LITERAL(
        "Literal Anchor",
        "Denotatively accurate translation. Preserve exact meaning over sound.",
        0.4f
    ),
    CULTURAL(
        "Cultural Charge",
        "Capture source language register and cultural connotations.",
        0.6f
    ),
    EMOTIONAL(
        "Emotional Arc",
        "Match the poem's emotional journey and intensity.",
        0.7f
    ),
    IDIOM(
        "Target Idiom",
        "Natural target language with zero source language traces.",
        0.6f
    ),
    COMPRESS(
        "Compression",
        "Shortest form with hardest consonants. Punchy and direct.",
        0.5f
    )
}

data class TranslationConfig(
    val maxTokensPerLine: Int = 256,
    val maxTokensPerStanza: Int = 1024,
    val maxTokensPerPath: Int = 512,
    val maxTokensPerAnalysis: Int = 2048,
    val temperature: Float = 0.7f,
    val topP: Float = 0.9f
) {
    companion object {
        val DEFAULT = TranslationConfig()

        val CREATIVE = TranslationConfig(
            temperature = 0.8f,
            topP = 0.95f
        )

        val PRECISE = TranslationConfig(
            temperature = 0.4f,
            topP = 0.85f
        )
    }
}

data class TranslationContext(
    val poemTitle: String? = null,
    val poemAuthor: String? = null,
    val stanzaIndex: Int? = null,
    val previousLines: List<String>? = null,
    val rhymeScheme: String? = null,
    val additionalInstructions: String? = null
)

data class TranslationResult(
    val text: String,
    val language: Language,
    val confidence: Float
)

data class StanzaTranslation(
    val lines: List<TranslationResult>,
    val overallConfidence: Float
)

data class TranslationPath(
    val type: PathType,
    val lines: List<String>,
    val rationale: String
)

data class WordAnalysis(
    val original: String,
    val ipa: String,
    val synonyms: List<SynonymEntry>
)

data class SynonymEntry(
    val word: String,
    val ipaEnding: String,
    val isSonicMatch: Boolean
)

data class RhymePair(
    val word1: String,
    val word2: String,
    val sharedIpa: String,
    val isHebrewEcho: Boolean
)

data class AnalysisResult(
    val words: List<WordAnalysis>,
    val rhymePairs: List<RhymePair>,
    val targetIpas: List<String>,
    val notes: String
)
