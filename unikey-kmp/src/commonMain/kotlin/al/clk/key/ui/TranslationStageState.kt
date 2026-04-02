package al.clk.key.ui

import al.clk.key.*

/**
 * Translation stage/path enum.
 * The 6 paths for refining poetry translations.
 */
enum class TranslationStage(
    val index: Int,
    val label: String,
    val shortLabel: String,
    val description: String,
    val icon: String,
    val hue: Int  // For color coding
) {
    IPA_ECHO(
        index = 1,
        label = "IPA Echo",
        shortLabel = "IPA",
        description = "Pick synonyms whose IPA ending most closely echoes the source",
        icon = "🔊",
        hue = 0  // Red
    ),
    LITERAL_ANCHOR(
        index = 2,
        label = "Literal Anchor",
        shortLabel = "LIT",
        description = "Pick the most denotatively accurate synonym",
        icon = "⚓",
        hue = 60  // Yellow
    ),
    CULTURAL_CHARGE(
        index = 3,
        label = "Cultural Charge",
        shortLabel = "CUL",
        description = "Pick the synonym with the strongest cultural/register charge",
        icon = "🏛️",
        hue = 120  // Green
    ),
    EMOTIONAL_REGISTER(
        index = 4,
        label = "Emotional Register",
        shortLabel = "EMO",
        description = "Pick synonyms matching emotional intensity",
        icon = "💖",
        hue = 180  // Cyan
    ),
    TARGET_IDIOM(
        index = 5,
        label = "Target Idiom",
        shortLabel = "IDM",
        description = "Most natural target language, zero source-isms",
        icon = "🎯",
        hue = 240  // Blue
    ),
    COMPRESSION(
        index = 6,
        label = "Compression",
        shortLabel = "CMP",
        description = "Shortest/hardest synonym; fewest syllables",
        icon = "📦",
        hue = 300  // Purple
    );

    /** HSL color string for this stage */
    val color: String get() = "hsl($hue, 70%, 65%)"

    /** Light background color */
    val bgColor: String get() = "hsl($hue, 70%, 95%)"

    /** Formatted label with index */
    val numberedLabel: String get() = "$index. $label"

    companion object {
        /** All stages in order */
        val ALL = values().toList()

        /** Get stage by index (1-6) */
        fun fromIndex(index: Int): TranslationStage? = ALL.find { it.index == index }

        /** Get stage by short label */
        fun fromShortLabel(label: String): TranslationStage? =
            ALL.find { it.shortLabel.equals(label, ignoreCase = true) }
    }
}

/**
 * UI state for a single translation stage result.
 */
data class StageResultItem(
    val stage: TranslationStage,
    val words: List<String>,        // Selected words for this path
    val ipa: String,                // Combined IPA for the result
    val score: Float = 0f,          // Quality score (0-1)
    val notes: String = ""          // AI notes/reasoning
) {
    /** Stage index (1-6) */
    val index: Int get() = stage.index

    /** Display label */
    val label: String get() = stage.label

    /** HSL color */
    val color: String get() = stage.color

    /** Word count */
    val wordCount: Int get() = words.size

    /** Formatted words */
    val formattedWords: String get() = words.joinToString(" ")
}

/**
 * UI state for the translation stage workflow.
 */
data class TranslationStageState(
    val pair: TranslationPairItem? = null,
    val sourceText: String = "",
    val currentStage: TranslationStage? = null,
    val completedStages: Set<TranslationStage> = emptySet(),
    val results: Map<TranslationStage, StageResultItem> = emptyMap(),
    val selectedStage: TranslationStage? = null,
    val isProcessing: Boolean = false
) {
    /** All stages with completion status */
    val stageItems: List<TranslationStageItem>
        get() = TranslationStage.ALL.map { stage ->
            TranslationStageItem(
                stage = stage,
                isCompleted = stage in completedStages,
                isCurrent = stage == currentStage,
                isSelected = stage == selectedStage,
                result = results[stage]
            )
        }

    /** Progress percentage (0-100) */
    val progressPercent: Int
        get() = (completedStages.size * 100) / TranslationStage.ALL.size

    /** Is workflow complete? */
    val isComplete: Boolean
        get() = completedStages.size == TranslationStage.ALL.size

    /** Next incomplete stage */
    val nextStage: TranslationStage?
        get() = TranslationStage.ALL.firstOrNull { it !in completedStages }

    /** Get result for a stage */
    fun resultFor(stage: TranslationStage): StageResultItem? = results[stage]

    /** Best result (highest score) */
    val bestResult: StageResultItem?
        get() = results.values.maxByOrNull { it.score }

    companion object {
        fun initial(): TranslationStageState = TranslationStageState()

        /** Create with translation pair */
        fun forPair(srcCode: String, tgtCode: String): TranslationStageState? {
            val pairItem = LanguageUI.createPairItem(srcCode, tgtCode) ?: return null
            return TranslationStageState(pair = pairItem)
        }
    }
}

/**
 * UI-friendly stage item with status.
 */
data class TranslationStageItem(
    val stage: TranslationStage,
    val isCompleted: Boolean = false,
    val isCurrent: Boolean = false,
    val isSelected: Boolean = false,
    val result: StageResultItem? = null
) {
    val index: Int get() = stage.index
    val label: String get() = stage.label
    val shortLabel: String get() = stage.shortLabel
    val description: String get() = stage.description
    val icon: String get() = stage.icon
    val color: String get() = stage.color
    val bgColor: String get() = stage.bgColor

    /** Status icon */
    val statusIcon: String get() = when {
        isCompleted -> "✓"
        isCurrent -> "▶"
        else -> "○"
    }
}

/**
 * Actions for translation stage workflow.
 */
sealed class TranslationStageAction {
    data class SetPair(val srcCode: String, val tgtCode: String) : TranslationStageAction()
    data class SetSourceText(val text: String) : TranslationStageAction()
    data class SelectStage(val stage: TranslationStage) : TranslationStageAction()
    data class StartStage(val stage: TranslationStage) : TranslationStageAction()
    data class CompleteStage(val result: StageResultItem) : TranslationStageAction()
    object NextStage : TranslationStageAction()
    object Reset : TranslationStageAction()
}

/**
 * State manager for translation stages.
 */
class TranslationStageManager {
    private var _state = TranslationStageState.initial()
    val state: TranslationStageState get() = _state

    /** Process an action and update state */
    fun dispatch(action: TranslationStageAction): TranslationStageState {
        _state = reduce(_state, action)
        return _state
    }

    private fun reduce(state: TranslationStageState, action: TranslationStageAction): TranslationStageState {
        return when (action) {
            is TranslationStageAction.SetPair -> {
                val pairItem = LanguageUI.createPairItem(action.srcCode, action.tgtCode)
                state.copy(pair = pairItem)
            }
            is TranslationStageAction.SetSourceText -> {
                state.copy(sourceText = action.text)
            }
            is TranslationStageAction.SelectStage -> {
                state.copy(selectedStage = action.stage)
            }
            is TranslationStageAction.StartStage -> {
                state.copy(
                    currentStage = action.stage,
                    isProcessing = true
                )
            }
            is TranslationStageAction.CompleteStage -> {
                val newCompleted = state.completedStages + action.result.stage
                val newResults = state.results + (action.result.stage to action.result)
                state.copy(
                    completedStages = newCompleted,
                    results = newResults,
                    currentStage = null,
                    isProcessing = false
                )
            }
            is TranslationStageAction.NextStage -> {
                val next = state.nextStage
                if (next != null) {
                    state.copy(currentStage = next, isProcessing = true)
                } else {
                    state.copy(isProcessing = false)
                }
            }
            is TranslationStageAction.Reset -> {
                TranslationStageState.initial().copy(pair = state.pair)
            }
        }
    }

    /** Get the translation pair if set */
    fun getTranslationPair(): TranslationPair? {
        val pairItem = _state.pair ?: return null
        return TranslationPair.fromCodes(pairItem.sourceCode, pairItem.targetCode)
    }

    /** Generate analysis prompt for current pair */
    fun generateAnalysisPrompt(context: String = ""): String? {
        return getTranslationPair()?.generateAnalysisPrompt(context)
    }

    /** Generate path prompt for current pair */
    fun generatePathPrompt(context: String = ""): String? {
        return getTranslationPair()?.generatePathPrompt(context)
    }

    /** Get prompt hints for a specific stage */
    fun getStagePrompt(stage: TranslationStage): String {
        val pair = getTranslationPair() ?: return ""
        val srcName = pair.source.englishName
        val tgtName = pair.target.englishName
        val tgtNative = pair.target.nativeName

        return when (stage) {
            TranslationStage.IPA_ECHO ->
                "Pick synonyms whose IPA ending most closely echoes the $srcName source IPA"
            TranslationStage.LITERAL_ANCHOR ->
                "Pick the most denotatively accurate $tgtName synonym"
            TranslationStage.CULTURAL_CHARGE ->
                "Pick the synonym with the strongest $srcName cultural/register charge"
            TranslationStage.EMOTIONAL_REGISTER ->
                "Pick synonyms matching the emotional intensity of the original"
            TranslationStage.TARGET_IDIOM ->
                "Most natural $tgtNative, zero ${srcName}isms"
            TranslationStage.COMPRESSION ->
                "Shortest/hardest synonym; fewest syllables"
        }
    }
}

/**
 * Singleton for global access.
 */
object TranslationStageUI {
    val manager = TranslationStageManager()

    /** All available stages */
    val allStages: List<TranslationStage> get() = TranslationStage.ALL

    /** Current state */
    val state: TranslationStageState get() = manager.state

    /** Quick access to stage items with status */
    val stageItems: List<TranslationStageItem> get() = state.stageItems

    /** Get stage by index (1-6) */
    fun getStage(index: Int): TranslationStage? = TranslationStage.fromIndex(index)

    /** Start workflow for a language pair */
    fun startWorkflow(srcCode: String, tgtCode: String, sourceText: String = ""): TranslationStageState {
        manager.dispatch(TranslationStageAction.SetPair(srcCode, tgtCode))
        if (sourceText.isNotEmpty()) {
            manager.dispatch(TranslationStageAction.SetSourceText(sourceText))
        }
        return manager.state
    }

    /** Complete a stage with results */
    fun completeStage(
        stage: TranslationStage,
        words: List<String>,
        ipa: String = "",
        score: Float = 0f,
        notes: String = ""
    ): TranslationStageState {
        val result = StageResultItem(stage, words, ipa, score, notes)
        return manager.dispatch(TranslationStageAction.CompleteStage(result))
    }
}
