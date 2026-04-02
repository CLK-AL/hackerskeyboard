package al.clk.key.ui

import al.clk.key.*

/**
 * UI state for language selection.
 * Platform-agnostic state model for language picker UI components.
 */
data class LanguageSelectorState(
    val languages: List<LanguageItem> = emptyList(),
    val selectedLanguage: LanguageItem? = null,
    val filter: String = "",
    val groupByScript: Boolean = true
) {
    /** Filtered languages based on search term */
    val filteredLanguages: List<LanguageItem>
        get() = if (filter.isEmpty()) languages
        else languages.filter {
            it.englishName.contains(filter, ignoreCase = true) ||
            it.nativeName.contains(filter, ignoreCase = true) ||
            it.code.contains(filter, ignoreCase = true)
        }

    /** Languages grouped by script type */
    val languagesByScript: Map<Script, List<LanguageItem>>
        get() = filteredLanguages.groupBy { it.script }

    companion object {
        /** Create initial state with all 23 languages */
        fun initial(): LanguageSelectorState = LanguageSelectorState(
            languages = KeyLanguage.ALL.map { LanguageItem.from(it) }
        )
    }
}

/**
 * Lightweight language item for UI display.
 * Avoids exposing full KeyLanguage with lambdas to UI layer.
 */
data class LanguageItem(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val script: Script,
    val direction: TextDirection,
    val flag: String  // Emoji flag or script indicator
) {
    /** Is this a right-to-left language? */
    val isRtl: Boolean get() = direction == TextDirection.RTL

    /** Script display name */
    val scriptName: String get() = script.name.lowercase().replaceFirstChar { it.uppercase() }

    companion object {
        fun from(lang: KeyLanguage): LanguageItem = LanguageItem(
            code = lang.code,
            nativeName = lang.nativeName,
            englishName = lang.englishName,
            script = lang.script,
            direction = lang.direction,
            flag = flagForLang(lang.lang)
        )

        private fun flagForLang(lang: Lang): String = when (lang) {
            Lang.HE -> "🇮🇱"
            Lang.EN -> "🇬🇧"
            Lang.AR -> "🇸🇦"
            Lang.RU -> "🇷🇺"
            Lang.EL -> "🇬🇷"
            Lang.HI -> "🇮🇳"
            Lang.JA -> "🇯🇵"
            Lang.KO -> "🇰🇷"
            Lang.ZH -> "🇨🇳"
            Lang.DE -> "🇩🇪"
            Lang.FR -> "🇫🇷"
            Lang.ES -> "🇪🇸"
            Lang.IT -> "🇮🇹"
            Lang.PT -> "🇵🇹"
            Lang.NL -> "🇳🇱"
            Lang.PL -> "🇵🇱"
            Lang.TR -> "🇹🇷"
            Lang.DA -> "🇩🇰"
            Lang.FI -> "🇫🇮"
            Lang.NO -> "🇳🇴"
            Lang.SV -> "🇸🇪"
            Lang.MS -> "🇲🇾"
            Lang.SW -> "🇰🇪"
            else -> "🌐"
        }
    }
}

/**
 * UI state for translation pair selection.
 */
data class TranslationPairSelectorState(
    val sourceLanguage: LanguageItem? = null,
    val targetLanguage: LanguageItem? = null,
    val availableTargets: List<LanguageItem> = emptyList(),
    val recentPairs: List<TranslationPairItem> = emptyList()
) {
    /** Is a valid pair selected? */
    val isValidPair: Boolean
        get() = sourceLanguage != null && targetLanguage != null && sourceLanguage != targetLanguage

    /** Current pair ID if valid */
    val pairId: String?
        get() = if (isValidPair) "${sourceLanguage!!.code}→${targetLanguage!!.code}" else null

    companion object {
        fun initial(): TranslationPairSelectorState = TranslationPairSelectorState(
            availableTargets = KeyLanguage.ALL.map { LanguageItem.from(it) }
        )
    }
}

/**
 * Lightweight translation pair item for UI display.
 */
data class TranslationPairItem(
    val pairId: String,
    val sourceCode: String,
    val targetCode: String,
    val sourceNative: String,
    val targetNative: String,
    val sourceFlag: String,
    val targetFlag: String
) {
    /** Display label (e.g., "🇬🇧 → 🇮🇱") */
    val label: String get() = "$sourceFlag → $targetFlag"

    /** Full display (e.g., "English → עברית") */
    val fullLabel: String get() = "$sourceNative → $targetNative"

    companion object {
        fun from(pair: TranslationPair): TranslationPairItem = TranslationPairItem(
            pairId = pair.pairId,
            sourceCode = pair.srcCode,
            targetCode = pair.tgtCode,
            sourceNative = pair.source.nativeName,
            targetNative = pair.target.nativeName,
            sourceFlag = LanguageItem.from(pair.source).flag,
            targetFlag = LanguageItem.from(pair.target).flag
        )
    }
}

/**
 * Actions/events for language selector.
 */
sealed class LanguageSelectorAction {
    data class SelectLanguage(val code: String) : LanguageSelectorAction()
    data class UpdateFilter(val filter: String) : LanguageSelectorAction()
    data class ToggleGroupByScript(val enabled: Boolean) : LanguageSelectorAction()
    object ClearSelection : LanguageSelectorAction()
}

/**
 * Actions/events for translation pair selector.
 */
sealed class TranslationPairAction {
    data class SelectSource(val code: String) : TranslationPairAction()
    data class SelectTarget(val code: String) : TranslationPairAction()
    data class SelectPair(val pairId: String) : TranslationPairAction()
    object SwapLanguages : TranslationPairAction()
    object ClearSelection : TranslationPairAction()
}
