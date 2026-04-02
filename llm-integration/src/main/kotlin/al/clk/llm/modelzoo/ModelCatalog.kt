package al.clk.llm.modelzoo

/**
 * Curated catalog of recommended GGUF models for poetry translation.
 */
object ModelCatalog {

    /**
     * All curated models organized by category.
     */
    val allModels: List<CatalogModel> = listOf(
        // Hebrew Specialists
        CatalogModel(
            id = "dictalm3-12b",
            name = "DictaLM 3.0 Nemotron 12B",
            repoId = "dicta-il/DictaLM-3.0-Nemotron-12B-Instruct-GGUF",
            description = "Best Hebrew LLM. 12B parameters, hybrid architecture.",
            category = ModelCategory.HEBREW,
            languages = listOf("he", "en"),
            parameterCount = "12B",
            contextLength = 8192,
            recommendedQuant = Quantization.Q4_K_M,
            featured = true
        ),
        CatalogModel(
            id = "dictalm3-24b-thinking",
            name = "DictaLM 3.0 24B Thinking",
            repoId = "dicta-il/DictaLM-3.0-24B-Thinking-GGUF",
            description = "Flagship reasoning model with chain-of-thought.",
            category = ModelCategory.HEBREW,
            languages = listOf("he", "en"),
            parameterCount = "24B",
            contextLength = 32768,
            recommendedQuant = Quantization.Q4_K_M,
            featured = true
        ),

        // Multilingual Translation
        CatalogModel(
            id = "qwen2.5-7b",
            name = "Qwen 2.5 7B Instruct",
            repoId = "Qwen/Qwen2.5-7B-Instruct-GGUF",
            description = "Excellent multilingual model with strong Hebrew support.",
            category = ModelCategory.MULTILINGUAL,
            languages = listOf("en", "zh", "he", "ar", "de", "fr", "es", "it", "pt", "ru", "ja", "ko"),
            parameterCount = "7B",
            contextLength = 32768,
            recommendedQuant = Quantization.Q4_K_M,
            featured = true
        ),
        CatalogModel(
            id = "qwen2.5-14b",
            name = "Qwen 2.5 14B Instruct",
            repoId = "Qwen/Qwen2.5-14B-Instruct-GGUF",
            description = "Larger Qwen model for higher quality translation.",
            category = ModelCategory.MULTILINGUAL,
            languages = listOf("en", "zh", "he", "ar", "de", "fr", "es", "it", "pt", "ru", "ja", "ko"),
            parameterCount = "14B",
            contextLength = 32768,
            recommendedQuant = Quantization.Q4_K_M
        ),
        CatalogModel(
            id = "aya-23-8b",
            name = "Aya 23 8B",
            repoId = "bartowski/aya-23-8B-GGUF",
            description = "Cohere's 23-language translation model.",
            category = ModelCategory.MULTILINGUAL,
            languages = listOf("en", "he", "ar", "de", "fr", "es", "it", "pt", "ru", "ja", "ko", "zh", "hi", "tr", "pl", "nl", "sv", "da", "fi", "no", "el", "cs", "ro"),
            parameterCount = "8B",
            contextLength = 8192,
            recommendedQuant = Quantization.Q4_K_M
        ),
        CatalogModel(
            id = "aya-23-35b",
            name = "Aya 23 35B",
            repoId = "bartowski/aya-23-35B-GGUF",
            description = "Large Cohere multilingual model.",
            category = ModelCategory.MULTILINGUAL,
            languages = listOf("en", "he", "ar", "de", "fr", "es", "it", "pt", "ru", "ja", "ko", "zh"),
            parameterCount = "35B",
            contextLength = 8192,
            recommendedQuant = Quantization.Q4_K_M
        ),

        // General Purpose
        CatalogModel(
            id = "llama-3.1-8b",
            name = "Llama 3.1 8B Instruct",
            repoId = "bartowski/Meta-Llama-3.1-8B-Instruct-GGUF",
            description = "Meta's latest open model. Good all-around performance.",
            category = ModelCategory.GENERAL,
            languages = listOf("en", "de", "fr", "es", "it", "pt", "hi", "th"),
            parameterCount = "8B",
            contextLength = 131072,
            recommendedQuant = Quantization.Q4_K_M,
            featured = true
        ),
        CatalogModel(
            id = "llama-3.1-70b",
            name = "Llama 3.1 70B Instruct",
            repoId = "bartowski/Meta-Llama-3.1-70B-Instruct-GGUF",
            description = "Large Llama model for maximum quality.",
            category = ModelCategory.GENERAL,
            languages = listOf("en", "de", "fr", "es", "it", "pt", "hi", "th"),
            parameterCount = "70B",
            contextLength = 131072,
            recommendedQuant = Quantization.Q4_K_M
        ),
        CatalogModel(
            id = "mistral-7b-v0.3",
            name = "Mistral 7B Instruct v0.3",
            repoId = "bartowski/Mistral-7B-Instruct-v0.3-GGUF",
            description = "Fast and capable. Good for quick iterations.",
            category = ModelCategory.GENERAL,
            languages = listOf("en", "de", "fr", "es", "it"),
            parameterCount = "7B",
            contextLength = 32768,
            recommendedQuant = Quantization.Q4_K_M
        ),
        CatalogModel(
            id = "gemma-2-9b",
            name = "Gemma 2 9B Instruct",
            repoId = "bartowski/gemma-2-9b-it-GGUF",
            description = "Google's efficient model with good multilingual support.",
            category = ModelCategory.GENERAL,
            languages = listOf("en", "de", "fr", "es", "it", "pt", "nl", "pl", "ru"),
            parameterCount = "9B",
            contextLength = 8192,
            recommendedQuant = Quantization.Q4_K_M
        ),

        // Small/Fast Models
        CatalogModel(
            id = "phi-3-mini",
            name = "Phi-3 Mini 3.8B Instruct",
            repoId = "bartowski/Phi-3-mini-4k-instruct-GGUF",
            description = "Microsoft's small but capable model. Fast inference.",
            category = ModelCategory.SMALL,
            languages = listOf("en"),
            parameterCount = "3.8B",
            contextLength = 4096,
            recommendedQuant = Quantization.Q4_K_M
        ),
        CatalogModel(
            id = "qwen2.5-3b",
            name = "Qwen 2.5 3B Instruct",
            repoId = "Qwen/Qwen2.5-3B-Instruct-GGUF",
            description = "Small Qwen model. Good balance of speed and quality.",
            category = ModelCategory.SMALL,
            languages = listOf("en", "zh", "he"),
            parameterCount = "3B",
            contextLength = 32768,
            recommendedQuant = Quantization.Q4_K_M
        ),

        // Poetry/Creative
        CatalogModel(
            id = "mythomax-l2-13b",
            name = "MythoMax L2 13B",
            repoId = "TheBloke/MythoMax-L2-13B-GGUF",
            description = "Creative writing focused. Good for poetic expression.",
            category = ModelCategory.CREATIVE,
            languages = listOf("en"),
            parameterCount = "13B",
            contextLength = 4096,
            recommendedQuant = Quantization.Q4_K_M
        )
    )

    /** Featured models for quick access */
    val featuredModels: List<CatalogModel> = allModels.filter { it.featured }

    /** Models by category */
    val byCategory: Map<ModelCategory, List<CatalogModel>> = allModels.groupBy { it.category }

    /** Hebrew-capable models */
    val hebrewModels: List<CatalogModel> = allModels.filter { "he" in it.languages }

    /** Find model by ID */
    fun findById(id: String): CatalogModel? = allModels.find { it.id == id }

    /** Find model by repo ID */
    fun findByRepoId(repoId: String): CatalogModel? = allModels.find { it.repoId == repoId }

    /** Search models by name or description */
    fun search(query: String): List<CatalogModel> {
        val q = query.lowercase()
        return allModels.filter {
            it.name.lowercase().contains(q) ||
            it.description.lowercase().contains(q) ||
            it.languages.any { lang -> lang.contains(q) }
        }
    }

    /** Get models supporting a specific language */
    fun forLanguage(langCode: String): List<CatalogModel> {
        return allModels.filter { langCode in it.languages }
    }
}

/**
 * Model category for organization.
 */
enum class ModelCategory(val displayName: String, val description: String) {
    HEBREW("Hebrew", "Optimized for Hebrew language"),
    MULTILINGUAL("Multilingual", "Strong support for many languages"),
    GENERAL("General Purpose", "Good all-around models"),
    SMALL("Small/Fast", "Efficient models for quick inference"),
    CREATIVE("Creative Writing", "Optimized for poetry and creative text")
}

/**
 * Curated model entry.
 */
data class CatalogModel(
    val id: String,
    val name: String,
    val repoId: String,
    val description: String,
    val category: ModelCategory,
    val languages: List<String>,
    val parameterCount: String,
    val contextLength: Int,
    val recommendedQuant: Quantization = Quantization.Q4_K_M,
    val featured: Boolean = false
) {
    /** Estimated size in GB for recommended quantization */
    val estimatedSizeGb: Double get() {
        val params = parameterCount.replace("B", "").toDoubleOrNull() ?: 7.0
        return params * recommendedQuant.bitsPerWeight / 8.0
    }

    val estimatedSize: String get() = String.format("~%.1f GB", estimatedSizeGb)

    /** Language display string */
    val languagesDisplay: String get() = languages.take(5).joinToString(", ") +
        if (languages.size > 5) " +${languages.size - 5}" else ""
}
