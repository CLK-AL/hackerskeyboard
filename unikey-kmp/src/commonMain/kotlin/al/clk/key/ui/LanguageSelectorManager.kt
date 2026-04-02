package al.clk.key.ui

import al.clk.key.*

/**
 * State manager for language selector UI.
 * Platform-agnostic business logic for language selection.
 */
class LanguageSelectorManager {
    private var _state = LanguageSelectorState.initial()
    val state: LanguageSelectorState get() = _state

    /** Process an action and update state */
    fun dispatch(action: LanguageSelectorAction): LanguageSelectorState {
        _state = reduce(_state, action)
        return _state
    }

    /** Pure reducer function */
    private fun reduce(state: LanguageSelectorState, action: LanguageSelectorAction): LanguageSelectorState {
        return when (action) {
            is LanguageSelectorAction.SelectLanguage -> {
                val selected = state.languages.find { it.code == action.code }
                state.copy(selectedLanguage = selected)
            }
            is LanguageSelectorAction.UpdateFilter -> {
                state.copy(filter = action.filter)
            }
            is LanguageSelectorAction.ToggleGroupByScript -> {
                state.copy(groupByScript = action.enabled)
            }
            is LanguageSelectorAction.ClearSelection -> {
                state.copy(selectedLanguage = null)
            }
        }
    }

    /** Get KeyLanguage for selected item */
    fun getSelectedKeyLanguage(): KeyLanguage? {
        return _state.selectedLanguage?.let { KeyLanguage.fromCode(it.code) }
    }
}

/**
 * State manager for translation pair selector UI.
 */
class TranslationPairManager {
    private var _state = TranslationPairSelectorState.initial()
    val state: TranslationPairSelectorState get() = _state

    private val _recentPairs = mutableListOf<TranslationPairItem>()

    /** Process an action and update state */
    fun dispatch(action: TranslationPairAction): TranslationPairSelectorState {
        _state = reduce(_state, action)
        return _state
    }

    /** Pure reducer function */
    private fun reduce(state: TranslationPairSelectorState, action: TranslationPairAction): TranslationPairSelectorState {
        return when (action) {
            is TranslationPairAction.SelectSource -> {
                val source = state.availableTargets.find { it.code == action.code }
                val targets = state.availableTargets.filter { it.code != action.code }
                // Clear target if it was the same as new source
                val target = if (state.targetLanguage?.code == action.code) null else state.targetLanguage
                state.copy(
                    sourceLanguage = source,
                    targetLanguage = target,
                    availableTargets = targets
                )
            }
            is TranslationPairAction.SelectTarget -> {
                val target = KeyLanguage.ALL.map { LanguageItem.from(it) }
                    .find { it.code == action.code }
                val newState = state.copy(targetLanguage = target)
                // Add to recent pairs if valid
                if (newState.isValidPair) {
                    addToRecent(newState)
                }
                newState
            }
            is TranslationPairAction.SelectPair -> {
                val parts = action.pairId.split("→")
                if (parts.size == 2) {
                    val srcCode = parts[0]
                    val tgtCode = parts[1]
                    val allLangs = KeyLanguage.ALL.map { LanguageItem.from(it) }
                    val source = allLangs.find { it.code == srcCode }
                    val target = allLangs.find { it.code == tgtCode }
                    state.copy(
                        sourceLanguage = source,
                        targetLanguage = target,
                        availableTargets = allLangs.filter { it.code != srcCode }
                    )
                } else state
            }
            is TranslationPairAction.SwapLanguages -> {
                if (state.sourceLanguage != null && state.targetLanguage != null) {
                    val allLangs = KeyLanguage.ALL.map { LanguageItem.from(it) }
                    state.copy(
                        sourceLanguage = state.targetLanguage,
                        targetLanguage = state.sourceLanguage,
                        availableTargets = allLangs.filter { it.code != state.targetLanguage!!.code }
                    )
                } else state
            }
            is TranslationPairAction.ClearSelection -> {
                TranslationPairSelectorState.initial().copy(recentPairs = _recentPairs.toList())
            }
        }
    }

    private fun addToRecent(state: TranslationPairSelectorState) {
        val pair = TranslationPair.fromCodes(
            state.sourceLanguage!!.code,
            state.targetLanguage!!.code
        ) ?: return

        val item = TranslationPairItem.from(pair)
        // Remove if already exists, then add to front
        _recentPairs.removeAll { it.pairId == item.pairId }
        _recentPairs.add(0, item)
        // Keep only last 10
        while (_recentPairs.size > 10) {
            _recentPairs.removeAt(_recentPairs.lastIndex)
        }
        _state = _state.copy(recentPairs = _recentPairs.toList())
    }

    /** Get TranslationPair for current selection */
    fun getSelectedPair(): TranslationPair? {
        return if (_state.isValidPair) {
            TranslationPair.fromCodes(
                _state.sourceLanguage!!.code,
                _state.targetLanguage!!.code
            )
        } else null
    }

    /** Get common/popular translation pairs */
    fun getPopularPairs(): List<TranslationPairItem> {
        return listOf(
            TranslationPair.EN_TO_HE,
            TranslationPair.HE_TO_EN,
            TranslationPair.EN_TO_FR,
            TranslationPair.EN_TO_DE,
            TranslationPair.EN_TO_ES,
            TranslationPair.EN_TO_JA,
            TranslationPair.EN_TO_ZH,
            TranslationPair.FR_TO_EN,
            TranslationPair.DE_TO_EN
        ).map { TranslationPairItem.from(it) }
    }
}

/**
 * Singleton instance for global access.
 */
object LanguageUI {
    val languageSelector = LanguageSelectorManager()
    val pairSelector = TranslationPairManager()

    /** Quick access to all language items */
    val allLanguages: List<LanguageItem>
        get() = languageSelector.state.languages

    /** Quick access to popular pairs */
    val popularPairs: List<TranslationPairItem>
        get() = pairSelector.getPopularPairs()

    /** Get language item by code */
    fun getLanguage(code: String): LanguageItem? =
        allLanguages.find { it.code == code }

    /** Create pair item from codes */
    fun createPairItem(srcCode: String, tgtCode: String): TranslationPairItem? {
        val pair = TranslationPair.fromCodes(srcCode, tgtCode) ?: return null
        return TranslationPairItem.from(pair)
    }
}
