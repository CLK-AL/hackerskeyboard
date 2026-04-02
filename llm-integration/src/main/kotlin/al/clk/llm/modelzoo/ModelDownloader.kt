package al.clk.llm.modelzoo

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

/**
 * Model downloader with queue management and progress tracking.
 */
class ModelDownloader(
    private val client: HuggingFaceClient = HuggingFaceClient(),
    private val maxConcurrentDownloads: Int = 2
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val activeDownloads = ConcurrentHashMap<String, DownloadJob>()
    private val _downloadStates = MutableStateFlow<Map<String, DownloadState>>(emptyMap())

    /** Observable download states for all models */
    val downloadStates: StateFlow<Map<String, DownloadState>> = _downloadStates.asStateFlow()

    /**
     * Start downloading a model file.
     */
    fun download(
        repoId: String,
        filename: String,
        destinationDir: Path? = null
    ): Flow<DownloadProgress> {
        val key = "$repoId/$filename"

        // Check if already downloading
        activeDownloads[key]?.let { job ->
            return job.progressFlow
        }

        // Check if already cached
        client.getCachedModelPath(repoId, filename)?.let { path ->
            return flowOf(DownloadProgress.Completed(path.toFile()))
        }

        // Start new download
        val progressFlow = MutableSharedFlow<DownloadProgress>(replay = 1)
        val job = scope.launch {
            try {
                updateState(key, DownloadState.Queued)

                // Wait for slot if too many concurrent downloads
                while (activeDownloads.size >= maxConcurrentDownloads) {
                    delay(500)
                }

                updateState(key, DownloadState.Downloading(0f))

                client.downloadModel(repoId, filename, destinationDir ?: client.cacheDir)
                    .collect { progress ->
                        progressFlow.emit(progress)
                        when (progress) {
                            is DownloadProgress.InProgress -> {
                                updateState(key, DownloadState.Downloading(progress.percent))
                            }
                            is DownloadProgress.Completed -> {
                                updateState(key, DownloadState.Completed(progress.file.toPath()))
                            }
                            is DownloadProgress.Error -> {
                                updateState(key, DownloadState.Failed(progress.message))
                            }
                            else -> {}
                        }
                    }
            } catch (e: Exception) {
                progressFlow.emit(DownloadProgress.Error(e.message ?: "Download failed", e))
                updateState(key, DownloadState.Failed(e.message ?: "Unknown error"))
            } finally {
                activeDownloads.remove(key)
            }
        }

        activeDownloads[key] = DownloadJob(job, progressFlow)
        return progressFlow
    }

    /**
     * Download recommended quantization for a catalog model.
     */
    fun downloadCatalogModel(model: CatalogModel): Flow<DownloadProgress> {
        return flow {
            // First, list available GGUF files
            val files = client.listGgufFiles(model.repoId)

            // Find the recommended quantization or closest available
            val targetFile = files.find { it.quantization == model.recommendedQuant }
                ?: files.minByOrNull {
                    kotlin.math.abs(it.quantization.bitsPerWeight - model.recommendedQuant.bitsPerWeight)
                }
                ?: throw IllegalStateException("No GGUF files found for ${model.repoId}")

            // Download it
            emitAll(download(model.repoId, targetFile.filename))
        }
    }

    /**
     * Cancel a download.
     */
    fun cancel(repoId: String, filename: String) {
        val key = "$repoId/$filename"
        activeDownloads[key]?.let { job ->
            job.job.cancel()
            activeDownloads.remove(key)
            updateState(key, DownloadState.Cancelled)
        }
    }

    /**
     * Cancel all downloads.
     */
    fun cancelAll() {
        activeDownloads.forEach { (key, job) ->
            job.job.cancel()
            updateState(key, DownloadState.Cancelled)
        }
        activeDownloads.clear()
    }

    /**
     * Get current state of a download.
     */
    fun getState(repoId: String, filename: String): DownloadState {
        val key = "$repoId/$filename"

        // Check if cached
        client.getCachedModelPath(repoId, filename)?.let {
            return DownloadState.Completed(it)
        }

        return _downloadStates.value[key] ?: DownloadState.NotStarted
    }

    /**
     * Check if a model is downloaded.
     */
    fun isDownloaded(repoId: String, filename: String): Boolean {
        return client.getCachedModelPath(repoId, filename) != null
    }

    /**
     * Get all cached models.
     */
    fun getCachedModels(): List<CachedModel> = client.listCachedModels()

    /**
     * Delete a cached model.
     */
    fun deleteCachedModel(repoId: String, filename: String): Boolean {
        val result = client.deleteCachedModel(repoId, filename)
        if (result) {
            updateState("$repoId/$filename", DownloadState.NotStarted)
        }
        return result
    }

    private fun updateState(key: String, state: DownloadState) {
        _downloadStates.update { current ->
            current + (key to state)
        }
    }

    fun close() {
        cancelAll()
        scope.cancel()
        client.close()
    }

    private data class DownloadJob(
        val job: Job,
        val progressFlow: MutableSharedFlow<DownloadProgress>
    )
}

/**
 * Download state for a model.
 */
sealed class DownloadState {
    object NotStarted : DownloadState()
    object Queued : DownloadState()
    data class Downloading(val progress: Float) : DownloadState() {
        val percentInt: Int get() = (progress * 100).toInt()
    }
    data class Completed(val path: Path) : DownloadState()
    data class Failed(val error: String) : DownloadState()
    object Cancelled : DownloadState()

    val isActive: Boolean get() = this is Queued || this is Downloading
    val isComplete: Boolean get() = this is Completed
}
