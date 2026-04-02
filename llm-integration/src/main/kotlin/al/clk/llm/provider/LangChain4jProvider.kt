package al.clk.llm.provider

import al.clk.llm.*
import al.clk.llm.config.LlmConfig
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.UserMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.Duration

/**
 * LangChain4j-based LLM provider.
 * Supports Ollama, OpenAI-compatible APIs, and LM Studio.
 */
class LangChain4jProvider(
    override val modelConfig: ModelConfig,
    private val config: LlmConfig = LlmConfig.DEFAULT,
    private val apiType: ApiType = ApiType.OLLAMA
) : LlmProvider {

    enum class ApiType {
        OLLAMA,
        OPENAI_COMPATIBLE,
        LM_STUDIO
    }

    override val backend = LlmBackend.LANGCHAIN4J

    private var chatModel: ChatLanguageModel? = null

    override suspend fun isReady(): Boolean = chatModel != null

    override suspend fun initialize() = withContext(Dispatchers.IO) {
        chatModel = when (apiType) {
            ApiType.OLLAMA -> createOllamaModel()
            ApiType.OPENAI_COMPATIBLE -> createOpenAiModel()
            ApiType.LM_STUDIO -> createLmStudioModel()
        }
    }

    private fun createOllamaModel(): ChatLanguageModel {
        return OllamaChatModel.builder()
            .baseUrl(config.ollama.baseUrl)
            .modelName(modelConfig.modelId)
            .temperature(modelConfig.temperature.toDouble())
            .topP(modelConfig.topP.toDouble())
            .numCtx(modelConfig.contextLength)
            .timeout(Duration.ofMillis(config.ollama.requestTimeoutMs))
            .build()
    }

    private fun createOpenAiModel(): ChatLanguageModel {
        return OpenAiChatModel.builder()
            .baseUrl(config.lmStudio.baseUrl + config.lmStudio.apiPath)
            .apiKey("not-needed") // Local APIs often don't require keys
            .modelName(modelConfig.modelId)
            .temperature(modelConfig.temperature.toDouble())
            .topP(modelConfig.topP.toDouble())
            .maxTokens(modelConfig.maxTokens)
            .timeout(Duration.ofMillis(config.lmStudio.requestTimeoutMs))
            .build()
    }

    private fun createLmStudioModel(): ChatLanguageModel {
        // LM Studio uses OpenAI-compatible API
        return OpenAiChatModel.builder()
            .baseUrl(config.lmStudio.baseUrl + config.lmStudio.apiPath)
            .apiKey("lm-studio") // LM Studio accepts any key
            .modelName(config.lmStudio.defaultModel ?: modelConfig.modelId)
            .temperature(modelConfig.temperature.toDouble())
            .topP(modelConfig.topP.toDouble())
            .maxTokens(modelConfig.maxTokens)
            .timeout(Duration.ofMillis(config.lmStudio.requestTimeoutMs))
            .build()
    }

    override suspend fun complete(request: CompletionRequest): CompletionResponse =
        withContext(Dispatchers.IO) {
            val model = chatModel ?: throw IllegalStateException("Provider not initialized")

            val messages = request.messages.map { msg ->
                when (msg.role) {
                    ChatMessage.Role.SYSTEM -> SystemMessage.from(msg.content)
                    ChatMessage.Role.USER -> UserMessage.from(msg.content)
                    ChatMessage.Role.ASSISTANT -> AiMessage.from(msg.content)
                }
            }

            try {
                val response = model.generate(messages)
                CompletionResponse(
                    content = response.content().text(),
                    finishReason = CompletionResponse.FinishReason.STOP,
                    usage = null // LangChain4j doesn't always expose token counts
                )
            } catch (e: Exception) {
                CompletionResponse(
                    content = "Error: ${e.message}",
                    finishReason = CompletionResponse.FinishReason.ERROR
                )
            }
        }

    override fun completeStream(request: CompletionRequest): Flow<String> = flow {
        // LangChain4j streaming requires StreamingChatLanguageModel
        // For now, emit the full response
        val response = complete(request)
        emit(response.content)
    }

    override suspend fun shutdown() {
        chatModel = null
    }

    companion object {
        /** Create provider for Ollama backend */
        fun ollama(
            modelConfig: ModelConfig = ModelConfig.LLAMA_3_1_8B,
            config: LlmConfig = LlmConfig.DEFAULT
        ) = LangChain4jProvider(modelConfig, config, ApiType.OLLAMA)

        /** Create provider for LM Studio */
        fun lmStudio(
            modelConfig: ModelConfig = ModelConfig.DICTA_LM3,
            config: LlmConfig = LlmConfig.DEFAULT
        ) = LangChain4jProvider(modelConfig, config, ApiType.LM_STUDIO)

        /** Create provider for any OpenAI-compatible API */
        fun openAiCompatible(
            modelConfig: ModelConfig,
            config: LlmConfig = LlmConfig.DEFAULT
        ) = LangChain4jProvider(modelConfig, config, ApiType.OPENAI_COMPATIBLE)
    }
}
