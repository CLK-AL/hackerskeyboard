package al.clk.llm.modelzoo

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

/**
 * HuggingFace Hub API client for browsing and downloading models.
 */
class HuggingFaceClient(
    private val token: String? = System.getenv("HF_TOKEN"),
    private val cacheDir: Path = defaultCacheDir()
) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 30_000
            requestTimeoutMillis = 300_000
        }
        defaultRequest {
            token?.let { header("Authorization", "Bearer $it") }
        }
    }

    companion object {
        const val HF_API_BASE = "https://huggingface.co/api"
        const val HF_CDN_BASE = "https://huggingface.co"

        fun defaultCacheDir(): Path {
            val home = System.getProperty("user.home")
            return Path.of(home, ".cache", "huggingface", "hub")
        }
    }

    /**
     * Search for GGUF models on HuggingFace.
     */
    suspend fun searchModels(
        query: String = "",
        filter: ModelFilter = ModelFilter(),
        limit: Int = 50,
        offset: Int = 0
    ): List<HfModelInfo> = withContext(Dispatchers.IO) {
        val params = buildList {
            if (query.isNotBlank()) add("search=$query")
            add("filter=gguf")
            if (filter.author != null) add("author=${filter.author}")
            if (filter.language != null) add("language=${filter.language}")
            add("limit=$limit")
            add("skip=$offset")
            add("sort=downloads")
            add("direction=-1")
        }.joinToString("&")

        val response = httpClient.get("$HF_API_BASE/models?$params")
        val body = response.bodyAsText()
        json.decodeFromString<List<HfModelInfo>>(body)
    }

    /**
     * Get detailed model information including files.
     */
    suspend fun getModelInfo(repoId: String): HfModelDetails = withContext(Dispatchers.IO) {
        val response = httpClient.get("$HF_API_BASE/models/$repoId")
        json.decodeFromString<HfModelDetails>(response.bodyAsText())
    }

    /**
     * List files in a model repository.
     */
    suspend fun listFiles(repoId: String, revision: String = "main"): List<HfFileInfo> =
        withContext(Dispatchers.IO) {
            val response = httpClient.get("$HF_API_BASE/models/$repoId/tree/$revision")
            json.decodeFromString<List<HfFileInfo>>(response.bodyAsText())
        }

    /**
     * List only GGUF files in a repository.
     */
    suspend fun listGgufFiles(repoId: String): List<GgufFileInfo> {
        val files = listFiles(repoId)
        return files
            .filter { it.path.endsWith(".gguf") }
            .map { file ->
                val quantization = extractQuantization(file.path)
                GgufFileInfo(
                    filename = file.path,
                    size = file.size,
                    quantization = quantization,
                    downloadUrl = "$HF_CDN_BASE/$repoId/resolve/main/${file.path}"
                )
            }
            .sortedBy { it.quantization.bitsPerWeight }
    }

    private fun extractQuantization(filename: String): Quantization {
        val lower = filename.lowercase()
        return Quantization.entries.find { lower.contains(it.suffix.lowercase()) }
            ?: Quantization.Q4_K_M
    }

    /**
     * Download a file with progress reporting.
     */
    fun downloadFile(
        url: String,
        destination: Path,
        overwrite: Boolean = false
    ): Flow<DownloadProgress> = flow {
        if (destination.exists() && !overwrite) {
            emit(DownloadProgress.Completed(destination.toFile()))
            return@flow
        }

        destination.parent?.createDirectories()

        val response = httpClient.prepareGet(url).execute { httpResponse ->
            val contentLength = httpResponse.contentLength() ?: 0L
            val channel = httpResponse.bodyAsChannel()

            emit(DownloadProgress.Started(contentLength))

            val file = destination.toFile()
            var downloaded = 0L

            file.outputStream().use { output ->
                val buffer = ByteArray(8192)
                while (!channel.isClosedForRead) {
                    val read = channel.readAvailable(buffer)
                    if (read == -1) break
                    output.write(buffer, 0, read)
                    downloaded += read
                    emit(DownloadProgress.InProgress(downloaded, contentLength))
                }
            }

            emit(DownloadProgress.Completed(file))
        }
    }

    /**
     * Download a GGUF model file.
     */
    fun downloadModel(
        repoId: String,
        filename: String,
        destinationDir: Path = cacheDir
    ): Flow<DownloadProgress> {
        val url = "$HF_CDN_BASE/$repoId/resolve/main/$filename"
        val destination = destinationDir.resolve(repoId.replace("/", "--")).resolve(filename)
        return downloadFile(url, destination)
    }

    /**
     * Get cached model path if exists.
     */
    fun getCachedModelPath(repoId: String, filename: String): Path? {
        val path = cacheDir.resolve(repoId.replace("/", "--")).resolve(filename)
        return if (path.exists()) path else null
    }

    /**
     * List cached models.
     */
    fun listCachedModels(): List<CachedModel> {
        if (!cacheDir.exists()) return emptyList()

        return cacheDir.toFile().listFiles()
            ?.filter { it.isDirectory }
            ?.flatMap { repoDir ->
                repoDir.listFiles()
                    ?.filter { it.extension == "gguf" }
                    ?.map { file ->
                        CachedModel(
                            repoId = repoDir.name.replace("--", "/"),
                            filename = file.name,
                            path = file.toPath(),
                            sizeBytes = file.length()
                        )
                    }
                    ?: emptyList()
            }
            ?: emptyList()
    }

    /**
     * Delete a cached model.
     */
    fun deleteCachedModel(repoId: String, filename: String): Boolean {
        val path = cacheDir.resolve(repoId.replace("/", "--")).resolve(filename)
        return path.toFile().delete()
    }

    fun close() {
        httpClient.close()
    }
}

// Data classes

@Serializable
data class HfModelInfo(
    val id: String,
    val modelId: String? = null,
    val author: String? = null,
    val downloads: Long = 0,
    val likes: Int = 0,
    val tags: List<String> = emptyList(),
    val pipeline_tag: String? = null,
    val lastModified: String? = null
) {
    val displayName: String get() = modelId ?: id.substringAfterLast("/")
    val isGguf: Boolean get() = tags.any { it.equals("gguf", ignoreCase = true) }
}

@Serializable
data class HfModelDetails(
    val id: String,
    val author: String? = null,
    val downloads: Long = 0,
    val likes: Int = 0,
    val tags: List<String> = emptyList(),
    val cardData: HfCardData? = null,
    val siblings: List<HfSibling> = emptyList()
)

@Serializable
data class HfCardData(
    val language: List<String>? = null,
    val license: String? = null,
    val model_name: String? = null
)

@Serializable
data class HfSibling(
    val rfilename: String
)

@Serializable
data class HfFileInfo(
    val path: String,
    val size: Long = 0,
    val type: String = "file"
)

data class GgufFileInfo(
    val filename: String,
    val size: Long,
    val quantization: Quantization,
    val downloadUrl: String
) {
    val sizeGb: Double get() = size / (1024.0 * 1024.0 * 1024.0)
    val displaySize: String get() = String.format("%.1f GB", sizeGb)
}

data class CachedModel(
    val repoId: String,
    val filename: String,
    val path: Path,
    val sizeBytes: Long
) {
    val displaySize: String get() = String.format("%.1f GB", sizeBytes / (1024.0 * 1024.0 * 1024.0))
}

data class ModelFilter(
    val author: String? = null,
    val language: String? = null,
    val minDownloads: Long? = null
)

sealed class DownloadProgress {
    data class Started(val totalBytes: Long) : DownloadProgress()
    data class InProgress(val downloadedBytes: Long, val totalBytes: Long) : DownloadProgress() {
        val percent: Float get() = if (totalBytes > 0) downloadedBytes.toFloat() / totalBytes else 0f
        val percentInt: Int get() = (percent * 100).toInt()
    }
    data class Completed(val file: File) : DownloadProgress()
    data class Error(val message: String, val exception: Throwable? = null) : DownloadProgress()
}

enum class Quantization(val suffix: String, val bitsPerWeight: Float, val description: String) {
    F16("F16", 16f, "Full precision (large)"),
    Q8_0("Q8_0", 8f, "8-bit (high quality)"),
    Q6_K("Q6_K", 6.5f, "6-bit (very good)"),
    Q5_K_M("Q5_K_M", 5.5f, "5-bit medium"),
    Q5_K_S("Q5_K_S", 5.5f, "5-bit small"),
    Q4_K_M("Q4_K_M", 4.5f, "4-bit medium (recommended)"),
    Q4_K_S("Q4_K_S", 4.5f, "4-bit small"),
    Q4_0("Q4_0", 4f, "4-bit legacy"),
    Q3_K_M("Q3_K_M", 3.5f, "3-bit medium"),
    Q3_K_S("Q3_K_S", 3.5f, "3-bit small"),
    Q2_K("Q2_K", 2.5f, "2-bit (fast, lower quality)"),
    IQ4_XS("IQ4_XS", 4.25f, "iQuant 4-bit extra small"),
    IQ4_NL("IQ4_NL", 4f, "iQuant 4-bit"),
    IQ3_M("IQ3_M", 3.5f, "iQuant 3-bit medium"),
    IQ3_S("IQ3_S", 3.5f, "iQuant 3-bit small"),
    IQ2_M("IQ2_M", 2.5f, "iQuant 2-bit medium"),
    IQ2_S("IQ2_S", 2.5f, "iQuant 2-bit small");
}
