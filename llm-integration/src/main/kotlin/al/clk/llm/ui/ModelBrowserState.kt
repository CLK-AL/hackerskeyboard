package al.clk.llm.ui

import al.clk.llm.modelzoo.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * UI state management for the model browser.
 */
class ModelBrowserState(
    private val downloader: ModelDownloader = ModelDownloader()
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val client = HuggingFaceClient()

    // UI State
    private val _uiState = MutableStateFlow(ModelBrowserUiState())
    val uiState: StateFlow<ModelBrowserUiState> = _uiState.asStateFlow()

    // JSON serializer for JS interop
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    init {
        // Observe download states
        scope.launch {
            downloader.downloadStates.collect { states ->
                _uiState.update { current ->
                    current.copy(downloadStates = states.mapValues { (_, state) ->
                        state.toUiState()
                    })
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Actions
    // ─────────────────────────────────────────────────────────────────────────────

    /** Load featured models */
    fun loadFeatured() {
        _uiState.update { it.copy(
            view = BrowserView.FEATURED,
            models = ModelCatalog.featuredModels.map { it.toUiModel() },
            isLoading = false
        )}
    }

    /** Load models by category */
    fun loadCategory(category: ModelCategory) {
        val models = ModelCatalog.byCategory[category] ?: emptyList()
        _uiState.update { it.copy(
            view = BrowserView.CATEGORY,
            selectedCategory = category,
            models = models.map { it.toUiModel() },
            isLoading = false
        )}
    }

    /** Load Hebrew models */
    fun loadHebrewModels() {
        _uiState.update { it.copy(
            view = BrowserView.HEBREW,
            models = ModelCatalog.hebrewModels.map { it.toUiModel() },
            isLoading = false
        )}
    }

    /** Search models (local catalog + HuggingFace) */
    fun search(query: String) {
        if (query.isBlank()) {
            loadFeatured()
            return
        }

        _uiState.update { it.copy(
            view = BrowserView.SEARCH,
            searchQuery = query,
            isLoading = true
        )}

        scope.launch {
            try {
                // Search local catalog first
                val localResults = ModelCatalog.search(query)

                // Then search HuggingFace
                val hfResults = client.searchModels(query, limit = 20)

                _uiState.update { it.copy(
                    models = localResults.map { it.toUiModel() } +
                        hfResults.map { it.toUiModel() },
                    isLoading = false
                )}
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Search failed: ${e.message}"
                )}
            }
        }
    }

    /** Load cached/downloaded models */
    fun loadCached() {
        val cached = downloader.getCachedModels()
        _uiState.update { it.copy(
            view = BrowserView.CACHED,
            cachedModels = cached.map { it.toUiCached() },
            isLoading = false
        )}
    }

    /** Select a model to view details */
    fun selectModel(modelId: String) {
        val model = _uiState.value.models.find { it.id == modelId }
        if (model != null) {
            _uiState.update { it.copy(selectedModel = model) }

            // Load GGUF files for the model
            scope.launch {
                try {
                    val files = client.listGgufFiles(model.repoId)
                    _uiState.update { it.copy(
                        selectedModelFiles = files.map { it.toUiFile() }
                    )}
                } catch (e: Exception) {
                    _uiState.update { it.copy(
                        error = "Failed to load files: ${e.message}"
                    )}
                }
            }
        }
    }

    /** Clear model selection */
    fun clearSelection() {
        _uiState.update { it.copy(
            selectedModel = null,
            selectedModelFiles = emptyList()
        )}
    }

    /** Download a model file */
    fun downloadModel(repoId: String, filename: String) {
        scope.launch {
            downloader.download(repoId, filename).collect { progress ->
                // Progress is automatically tracked via downloadStates flow
            }
        }
    }

    /** Download catalog model with recommended quantization */
    fun downloadCatalogModel(modelId: String) {
        val model = ModelCatalog.findById(modelId) ?: return
        scope.launch {
            downloader.downloadCatalogModel(model).collect { }
        }
    }

    /** Cancel a download */
    fun cancelDownload(repoId: String, filename: String) {
        downloader.cancel(repoId, filename)
    }

    /** Delete a cached model */
    fun deleteCachedModel(repoId: String, filename: String) {
        downloader.deleteCachedModel(repoId, filename)
        loadCached() // Refresh cached list
    }

    /** Clear error */
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // JSON API for JS/HTML UI
    // ─────────────────────────────────────────────────────────────────────────────

    /** Get current state as JSON */
    fun getStateJson(): String = json.encodeToString(
        ModelBrowserUiState.serializer(),
        _uiState.value
    )

    /** Get categories as JSON */
    fun getCategoriesJson(): String = json.encodeToString(
        kotlinx.serialization.builtins.ListSerializer(CategoryInfo.serializer()),
        ModelCategory.entries.map { CategoryInfo(it.name, it.displayName, it.description) }
    )

    fun close() {
        scope.cancel()
        downloader.close()
        client.close()
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Converters
    // ─────────────────────────────────────────────────────────────────────────────

    private fun CatalogModel.toUiModel() = UiModel(
        id = id,
        name = name,
        repoId = repoId,
        description = description,
        category = category.name,
        languages = languages,
        parameterCount = parameterCount,
        contextLength = contextLength,
        estimatedSize = estimatedSize,
        featured = featured,
        isCatalog = true
    )

    private fun HfModelInfo.toUiModel() = UiModel(
        id = id,
        name = displayName,
        repoId = id,
        description = "Downloads: ${downloads}, Likes: $likes",
        category = "HUGGINGFACE",
        languages = emptyList(),
        parameterCount = "",
        contextLength = 0,
        estimatedSize = "",
        featured = false,
        isCatalog = false
    )

    private fun GgufFileInfo.toUiFile() = UiGgufFile(
        filename = filename,
        size = size,
        displaySize = displaySize,
        quantization = quantization.name,
        quantizationDesc = quantization.description,
        downloadUrl = downloadUrl
    )

    private fun CachedModel.toUiCached() = UiCachedModel(
        repoId = repoId,
        filename = filename,
        path = path.toString(),
        displaySize = displaySize
    )

    private fun DownloadState.toUiState() = when (this) {
        is DownloadState.NotStarted -> UiDownloadState("not_started", 0, null, null)
        is DownloadState.Queued -> UiDownloadState("queued", 0, null, null)
        is DownloadState.Downloading -> UiDownloadState("downloading", percentInt, null, null)
        is DownloadState.Completed -> UiDownloadState("completed", 100, path.toString(), null)
        is DownloadState.Failed -> UiDownloadState("failed", 0, null, error)
        is DownloadState.Cancelled -> UiDownloadState("cancelled", 0, null, null)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// UI State Data Classes (Serializable for JS)
// ─────────────────────────────────────────────────────────────────────────────

@Serializable
data class ModelBrowserUiState(
    val view: BrowserView = BrowserView.FEATURED,
    val searchQuery: String = "",
    val selectedCategory: ModelCategory? = null,
    val models: List<UiModel> = emptyList(),
    val selectedModel: UiModel? = null,
    val selectedModelFiles: List<UiGgufFile> = emptyList(),
    val cachedModels: List<UiCachedModel> = emptyList(),
    val downloadStates: Map<String, UiDownloadState> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@Serializable
enum class BrowserView {
    FEATURED,
    CATEGORY,
    HEBREW,
    SEARCH,
    CACHED
}

@Serializable
data class UiModel(
    val id: String,
    val name: String,
    val repoId: String,
    val description: String,
    val category: String,
    val languages: List<String>,
    val parameterCount: String,
    val contextLength: Int,
    val estimatedSize: String,
    val featured: Boolean,
    val isCatalog: Boolean
)

@Serializable
data class UiGgufFile(
    val filename: String,
    val size: Long,
    val displaySize: String,
    val quantization: String,
    val quantizationDesc: String,
    val downloadUrl: String
)

@Serializable
data class UiCachedModel(
    val repoId: String,
    val filename: String,
    val path: String,
    val displaySize: String
)

@Serializable
data class UiDownloadState(
    val status: String,
    val progress: Int,
    val path: String?,
    val error: String?
)

@Serializable
data class CategoryInfo(
    val id: String,
    val name: String,
    val description: String
)
