package al.clk.llm.config

import al.clk.llm.LlmBackend
import al.clk.llm.ModelConfig
import kotlinx.serialization.Serializable
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.createDirectories

/**
 * Configuration for LLM integration.
 */
@Serializable
data class LlmConfig(
    /** Default backend to use */
    val defaultBackend: LlmBackend = LlmBackend.LM_STUDIO,

    /** LM Studio configuration */
    val lmStudio: LmStudioConfig = LmStudioConfig(),

    /** Ollama configuration */
    val ollama: OllamaConfig = OllamaConfig(),

    /** JLama (local GGUF) configuration */
    val jlama: JLamaConfig = JLamaConfig(),

    /** DJL configuration */
    val djl: DjlConfig = DjlConfig(),

    /** Model cache directory */
    val modelCacheDir: String = defaultModelCacheDir(),

    /** Enable GPU acceleration if available */
    val enableGpu: Boolean = true,

    /** Number of threads for CPU inference */
    val threads: Int = Runtime.getRuntime().availableProcessors()
) {
    companion object {
        fun defaultModelCacheDir(): String {
            val home = System.getProperty("user.home")
            return Path(home, ".cache", "unikey-llm", "models").toString()
        }

        /** Default configuration */
        val DEFAULT = LlmConfig()
    }

    /** Ensure model cache directory exists */
    fun ensureCacheDir(): Path {
        val path = Path(modelCacheDir)
        if (!path.exists()) {
            path.createDirectories()
        }
        return path
    }
}

/**
 * LM Studio API configuration.
 */
@Serializable
data class LmStudioConfig(
    /** LM Studio server URL */
    val baseUrl: String = "http://localhost:1234",

    /** API endpoint path */
    val apiPath: String = "/v1",

    /** Connection timeout in milliseconds */
    val connectTimeoutMs: Long = 30_000,

    /** Request timeout in milliseconds */
    val requestTimeoutMs: Long = 300_000,

    /** Default model to use (if multiple loaded) */
    val defaultModel: String? = null
) {
    val chatEndpoint: String get() = "$baseUrl$apiPath/chat/completions"
    val modelsEndpoint: String get() = "$baseUrl$apiPath/models"
    val embeddingsEndpoint: String get() = "$baseUrl$apiPath/embeddings"
}

/**
 * Ollama configuration.
 */
@Serializable
data class OllamaConfig(
    /** Ollama server URL */
    val baseUrl: String = "http://localhost:11434",

    /** Connection timeout in milliseconds */
    val connectTimeoutMs: Long = 30_000,

    /** Request timeout in milliseconds */
    val requestTimeoutMs: Long = 300_000,

    /** Default model to use */
    val defaultModel: String = "llama3.1"
) {
    val generateEndpoint: String get() = "$baseUrl/api/generate"
    val chatEndpoint: String get() = "$baseUrl/api/chat"
    val embeddingsEndpoint: String get() = "$baseUrl/api/embeddings"
    val tagsEndpoint: String get() = "$baseUrl/api/tags"
}

/**
 * JLama (local GGUF inference) configuration.
 */
@Serializable
data class JLamaConfig(
    /** Directory containing GGUF model files */
    val modelsDir: String = defaultModelsDir(),

    /** Default model to load */
    val defaultModel: String = "dictalm2-instruct",

    /** Use memory mapping for model loading */
    val useMemoryMapping: Boolean = true,

    /** Enable Flash Attention if supported */
    val enableFlashAttention: Boolean = true,

    /** Batch size for inference */
    val batchSize: Int = 512
) {
    companion object {
        fun defaultModelsDir(): String {
            val home = System.getProperty("user.home")
            return Path(home, ".cache", "jlama", "models").toString()
        }
    }
}

/**
 * DJL (Deep Java Library) configuration.
 */
@Serializable
data class DjlConfig(
    /** Model cache directory */
    val modelDir: String = defaultModelDir(),

    /** Engine to use (PyTorch, OnnxRuntime, etc.) */
    val engine: String = "PyTorch",

    /** Device to use (cpu, gpu, etc.) */
    val device: String = "cpu",

    /** Default model for embeddings */
    val embeddingModel: String = "sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2"
) {
    companion object {
        fun defaultModelDir(): String {
            val home = System.getProperty("user.home")
            return Path(home, ".djl.ai").toString()
        }
    }
}

/**
 * Predefined model profiles for different use cases.
 */
object ModelProfiles {
    /** Hebrew-optimized translation profile */
    val HEBREW_TRANSLATION = ModelConfig.DICTA_LM3.copy(
        temperature = 0.5f,
        topP = 0.85f,
        repeatPenalty = 1.15f
    )

    /** Fast translation with smaller model */
    val FAST_TRANSLATION = ModelConfig.MISTRAL_7B.copy(
        maxTokens = 1024,
        temperature = 0.6f
    )

    /** High-quality multilingual translation */
    val QUALITY_TRANSLATION = ModelConfig.QWEN2_7B.copy(
        temperature = 0.4f,
        topP = 0.9f,
        maxTokens = 4096
    )

    /** Poetry-specific profile with creative settings */
    val POETRY_CREATIVE = ModelConfig.LLAMA_3_1_8B.copy(
        temperature = 0.8f,
        topP = 0.95f,
        repeatPenalty = 1.2f,
        maxTokens = 2048
    )
}
