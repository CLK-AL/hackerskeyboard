package al.clk.llm.provider

import al.clk.llm.*
import al.clk.llm.config.LlmConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * LM Studio provider using OpenAI-compatible REST API.
 * Supports any model loaded in LM Studio including:
 * - DictaLM3 (Hebrew)
 * - Llama 3.x
 * - Mistral
 * - Qwen2
 * - And any other GGUF model
 */
class LmStudioProvider(
    override val modelConfig: ModelConfig,
    private val config: LlmConfig = LlmConfig.DEFAULT
) : LlmProvider, EmbeddingProvider {

    override val backend = LlmBackend.LM_STUDIO

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = config.lmStudio.connectTimeoutMs
            requestTimeoutMillis = config.lmStudio.requestTimeoutMs
        }
    }

    override suspend fun isReady(): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.get(config.lmStudio.modelsEndpoint)
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun initialize() {
        // LM Studio doesn't require initialization
        // Just verify connection
        if (!isReady()) {
            throw IllegalStateException(
                "Cannot connect to LM Studio at ${config.lmStudio.baseUrl}. " +
                "Please ensure LM Studio is running with a model loaded."
            )
        }
    }

    override suspend fun complete(request: CompletionRequest): CompletionResponse =
        withContext(Dispatchers.IO) {
            try {
                val apiRequest = OpenAiChatRequest(
                    model = config.lmStudio.defaultModel ?: modelConfig.modelId,
                    messages = request.messages.map { msg ->
                        OpenAiMessage(
                            role = msg.role.name.lowercase(),
                            content = msg.content
                        )
                    },
                    max_tokens = request.maxTokens,
                    temperature = request.temperature,
                    top_p = request.topP,
                    stream = false,
                    stop = request.stop.ifEmpty { null }
                )

                val response = httpClient.post(config.lmStudio.chatEndpoint) {
                    contentType(ContentType.Application.Json)
                    setBody(json.encodeToString(OpenAiChatRequest.serializer(), apiRequest))
                }

                val responseBody = response.bodyAsText()
                val parsed = json.decodeFromString(OpenAiChatResponse.serializer(), responseBody)

                CompletionResponse(
                    content = parsed.choices.firstOrNull()?.message?.content ?: "",
                    finishReason = when (parsed.choices.firstOrNull()?.finish_reason) {
                        "stop" -> CompletionResponse.FinishReason.STOP
                        "length" -> CompletionResponse.FinishReason.LENGTH
                        else -> CompletionResponse.FinishReason.STOP
                    },
                    usage = parsed.usage?.let { u ->
                        TokenUsage(u.prompt_tokens, u.completion_tokens, u.total_tokens)
                    }
                )
            } catch (e: Exception) {
                CompletionResponse(
                    content = "LM Studio error: ${e.message}",
                    finishReason = CompletionResponse.FinishReason.ERROR
                )
            }
        }

    override fun completeStream(request: CompletionRequest): Flow<String> = flow {
        val apiRequest = OpenAiChatRequest(
            model = config.lmStudio.defaultModel ?: modelConfig.modelId,
            messages = request.messages.map { msg ->
                OpenAiMessage(
                    role = msg.role.name.lowercase(),
                    content = msg.content
                )
            },
            max_tokens = request.maxTokens,
            temperature = request.temperature,
            top_p = request.topP,
            stream = true,
            stop = request.stop.ifEmpty { null }
        )

        val response = httpClient.post(config.lmStudio.chatEndpoint) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(OpenAiChatRequest.serializer(), apiRequest))
        }

        // Parse SSE stream
        val channel = response.bodyAsChannel()
        val buffer = StringBuilder()

        while (!channel.isClosedForRead) {
            val line = channel.readUTF8Line() ?: break

            if (line.startsWith("data: ")) {
                val data = line.removePrefix("data: ").trim()
                if (data == "[DONE]") break

                try {
                    val chunk = json.decodeFromString(OpenAiStreamChunk.serializer(), data)
                    chunk.choices.firstOrNull()?.delta?.content?.let { content ->
                        emit(content)
                    }
                } catch (e: Exception) {
                    // Skip malformed chunks
                }
            }
        }
    }

    // EmbeddingProvider implementation
    override suspend fun embed(text: String): FloatArray = withContext(Dispatchers.IO) {
        val request = OpenAiEmbeddingRequest(
            input = text,
            model = "text-embedding-ada-002" // LM Studio embedding model
        )

        val response = httpClient.post(config.lmStudio.embeddingsEndpoint) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(OpenAiEmbeddingRequest.serializer(), request))
        }

        val parsed = json.decodeFromString(
            OpenAiEmbeddingResponse.serializer(),
            response.bodyAsText()
        )

        parsed.data.firstOrNull()?.embedding?.toFloatArray()
            ?: FloatArray(0)
    }

    override suspend fun embedBatch(texts: List<String>): List<FloatArray> {
        return texts.map { embed(it) }
    }

    override suspend fun shutdown() {
        httpClient.close()
    }

    /** Get list of models currently loaded in LM Studio */
    suspend fun listModels(): List<String> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.get(config.lmStudio.modelsEndpoint)
            val parsed = json.decodeFromString(
                OpenAiModelsResponse.serializer(),
                response.bodyAsText()
            )
            parsed.data.map { it.id }
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        /** Create LM Studio provider with default config */
        fun create(
            modelConfig: ModelConfig = ModelConfig.DICTA_LM3,
            config: LlmConfig = LlmConfig.DEFAULT
        ) = LmStudioProvider(modelConfig, config)

        /** Create provider for specific model loaded in LM Studio */
        fun forModel(
            modelName: String,
            config: LlmConfig = LlmConfig.DEFAULT
        ) = LmStudioProvider(
            ModelConfig(modelId = modelName),
            config.copy(
                lmStudio = config.lmStudio.copy(defaultModel = modelName)
            )
        )
    }
}

// OpenAI-compatible API request/response models

@Serializable
internal data class OpenAiChatRequest(
    val model: String,
    val messages: List<OpenAiMessage>,
    val max_tokens: Int = 2048,
    val temperature: Float = 0.7f,
    val top_p: Float = 0.9f,
    val stream: Boolean = false,
    val stop: List<String>? = null
)

@Serializable
internal data class OpenAiMessage(
    val role: String,
    val content: String
)

@Serializable
internal data class OpenAiChatResponse(
    val id: String = "",
    val choices: List<OpenAiChoice> = emptyList(),
    val usage: OpenAiUsage? = null
)

@Serializable
internal data class OpenAiChoice(
    val index: Int = 0,
    val message: OpenAiMessage? = null,
    val finish_reason: String? = null
)

@Serializable
internal data class OpenAiUsage(
    val prompt_tokens: Int = 0,
    val completion_tokens: Int = 0,
    val total_tokens: Int = 0
)

@Serializable
internal data class OpenAiStreamChunk(
    val choices: List<OpenAiStreamChoice> = emptyList()
)

@Serializable
internal data class OpenAiStreamChoice(
    val delta: OpenAiDelta? = null,
    val finish_reason: String? = null
)

@Serializable
internal data class OpenAiDelta(
    val content: String? = null
)

@Serializable
internal data class OpenAiEmbeddingRequest(
    val input: String,
    val model: String = "text-embedding-ada-002"
)

@Serializable
internal data class OpenAiEmbeddingResponse(
    val data: List<OpenAiEmbedding> = emptyList()
)

@Serializable
internal data class OpenAiEmbedding(
    val embedding: List<Float> = emptyList()
)

@Serializable
internal data class OpenAiModelsResponse(
    val data: List<OpenAiModel> = emptyList()
)

@Serializable
internal data class OpenAiModel(
    val id: String
)
