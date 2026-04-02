package al.clk.llm

import kotlinx.coroutines.flow.Flow

/**
 * Supported LLM backends for poetry translation.
 */
enum class LlmBackend {
    LANGCHAIN4J,      // LangChain4j orchestration
    CAMEL_AI,         // Apache Camel AI routes
    JLAMA,            // Pure Java GGUF inference
    DJL,              // Deep Java Library
    LM_STUDIO,        // LM Studio local API
    OLLAMA,           // Ollama local API
    OPENAI_COMPATIBLE // Any OpenAI-compatible API
}

/**
 * Model configuration for GGUF and other local models.
 */
data class ModelConfig(
    val modelId: String,
    val modelPath: String? = null,           // Local path to GGUF file
    val huggingFaceRepo: String? = null,     // HuggingFace repo ID
    val quantization: Quantization = Quantization.Q4_K_M,
    val contextLength: Int = 4096,
    val maxTokens: Int = 2048,
    val temperature: Float = 0.7f,
    val topP: Float = 0.9f,
    val repeatPenalty: Float = 1.1f,
    val gpuLayers: Int = -1,                 // -1 = auto, 0 = CPU only
    val threads: Int = Runtime.getRuntime().availableProcessors()
) {
    companion object {
        /** DictaLM3 - Hebrew language model from Dicta */
        val DICTA_LM3 = ModelConfig(
            modelId = "dictalm3",
            huggingFaceRepo = "dicta-il/dictalm2.0-instruct-GGUF",
            quantization = Quantization.Q4_K_M,
            contextLength = 8192
        )

        /** DictaLM 2.0 Instruct */
        val DICTA_LM2_INSTRUCT = ModelConfig(
            modelId = "dictalm2-instruct",
            huggingFaceRepo = "dicta-il/dictalm2.0-instruct-GGUF",
            quantization = Quantization.Q4_K_M
        )

        /** Llama 3.1 8B - General multilingual */
        val LLAMA_3_1_8B = ModelConfig(
            modelId = "llama-3.1-8b",
            huggingFaceRepo = "bartowski/Meta-Llama-3.1-8B-Instruct-GGUF",
            quantization = Quantization.Q4_K_M,
            contextLength = 8192
        )

        /** Mistral 7B v0.3 - Good for translation */
        val MISTRAL_7B = ModelConfig(
            modelId = "mistral-7b",
            huggingFaceRepo = "bartowski/Mistral-7B-Instruct-v0.3-GGUF",
            quantization = Quantization.Q4_K_M
        )

        /** Qwen2 7B - Strong multilingual including Hebrew */
        val QWEN2_7B = ModelConfig(
            modelId = "qwen2-7b",
            huggingFaceRepo = "Qwen/Qwen2-7B-Instruct-GGUF",
            quantization = Quantization.Q4_K_M,
            contextLength = 32768
        )

        /** Aya 23 8B - Multilingual by Cohere */
        val AYA_23_8B = ModelConfig(
            modelId = "aya-23-8b",
            huggingFaceRepo = "bartowski/aya-23-8B-GGUF",
            quantization = Quantization.Q4_K_M
        )
    }
}

/**
 * GGUF quantization levels.
 */
enum class Quantization(val suffix: String, val bitsPerWeight: Float) {
    F16("f16", 16f),
    Q8_0("q8_0", 8f),
    Q6_K("q6_K", 6.5f),
    Q5_K_M("q5_K_M", 5.5f),
    Q5_K_S("q5_K_S", 5.5f),
    Q4_K_M("q4_K_M", 4.5f),
    Q4_K_S("q4_K_S", 4.5f),
    Q4_0("q4_0", 4f),
    Q3_K_M("q3_K_M", 3.5f),
    Q3_K_S("q3_K_S", 3.5f),
    Q2_K("q2_K", 2.5f),
    IQ4_NL("iq4_nl", 4f),
    IQ3_M("iq3_m", 3.5f),
    IQ2_M("iq2_m", 2.5f);

    fun ggufFilename(modelName: String): String = "$modelName-$suffix.gguf"
}

/**
 * Chat message for LLM conversation.
 */
data class ChatMessage(
    val role: Role,
    val content: String
) {
    enum class Role { SYSTEM, USER, ASSISTANT }
}

/**
 * LLM completion request.
 */
data class CompletionRequest(
    val messages: List<ChatMessage>,
    val maxTokens: Int = 2048,
    val temperature: Float = 0.7f,
    val topP: Float = 0.9f,
    val stop: List<String> = emptyList(),
    val stream: Boolean = false
)

/**
 * LLM completion response.
 */
data class CompletionResponse(
    val content: String,
    val finishReason: FinishReason,
    val usage: TokenUsage? = null
) {
    enum class FinishReason { STOP, LENGTH, ERROR }
}

/**
 * Token usage statistics.
 */
data class TokenUsage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int
)

/**
 * Base interface for LLM providers.
 * All implementations support both synchronous and streaming completion.
 */
interface LlmProvider {
    /** Provider backend type */
    val backend: LlmBackend

    /** Current model configuration */
    val modelConfig: ModelConfig

    /** Check if provider is ready */
    suspend fun isReady(): Boolean

    /** Initialize/load the model */
    suspend fun initialize()

    /** Generate completion (blocking) */
    suspend fun complete(request: CompletionRequest): CompletionResponse

    /** Generate completion with streaming */
    fun completeStream(request: CompletionRequest): Flow<String>

    /** Shutdown and release resources */
    suspend fun shutdown()
}

/**
 * Provider for embedding generation (for semantic similarity).
 */
interface EmbeddingProvider {
    /** Generate embeddings for text */
    suspend fun embed(text: String): FloatArray

    /** Generate embeddings for multiple texts */
    suspend fun embedBatch(texts: List<String>): List<FloatArray>

    /** Compute cosine similarity between two texts */
    suspend fun similarity(text1: String, text2: String): Float {
        val emb1 = embed(text1)
        val emb2 = embed(text2)
        return cosineSimilarity(emb1, emb2)
    }

    companion object {
        fun cosineSimilarity(a: FloatArray, b: FloatArray): Float {
            require(a.size == b.size) { "Vectors must have same dimension" }
            var dot = 0f
            var normA = 0f
            var normB = 0f
            for (i in a.indices) {
                dot += a[i] * b[i]
                normA += a[i] * a[i]
                normB += b[i] * b[i]
            }
            return if (normA == 0f || normB == 0f) 0f
            else dot / (kotlin.math.sqrt(normA) * kotlin.math.sqrt(normB))
        }
    }
}
