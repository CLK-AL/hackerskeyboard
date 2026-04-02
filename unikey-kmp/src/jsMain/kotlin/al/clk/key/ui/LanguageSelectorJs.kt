@file:JsExport
package al.clk.key.ui

import al.clk.key.*

/**
 * JavaScript API for Language Selector UI.
 * Exposes state management to HTML/JS UI layer.
 */

// ═══════════════════════════════════════════════════════════════════════════════
// Language Selector API
// ═══════════════════════════════════════════════════════════════════════════════

@JsName("getLanguageSelectorState")
fun getLanguageSelectorState(): dynamic {
    val state = LanguageUI.languageSelector.state
    return stateToJs(state)
}

@JsName("selectLanguage")
fun selectLanguage(code: String): dynamic {
    val state = LanguageUI.languageSelector.dispatch(LanguageSelectorAction.SelectLanguage(code))
    return stateToJs(state)
}

@JsName("filterLanguages")
fun filterLanguages(filter: String): dynamic {
    val state = LanguageUI.languageSelector.dispatch(LanguageSelectorAction.UpdateFilter(filter))
    return stateToJs(state)
}

@JsName("toggleGroupByScript")
fun toggleGroupByScript(enabled: Boolean): dynamic {
    val state = LanguageUI.languageSelector.dispatch(LanguageSelectorAction.ToggleGroupByScript(enabled))
    return stateToJs(state)
}

@JsName("clearLanguageSelection")
fun clearLanguageSelection(): dynamic {
    val state = LanguageUI.languageSelector.dispatch(LanguageSelectorAction.ClearSelection)
    return stateToJs(state)
}

@JsName("getAllLanguageItems")
fun getAllLanguageItems(): Array<dynamic> {
    return LanguageUI.allLanguages.map { langToJs(it) }.toTypedArray()
}

@JsName("getLanguagesByScript")
fun getLanguagesByScript(): dynamic {
    val state = LanguageUI.languageSelector.state
    val obj = js("{}")
    state.languagesByScript.forEach { (script, langs) ->
        obj[script.name] = langs.map { langToJs(it) }.toTypedArray()
    }
    return obj
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Pair Selector API
// ═══════════════════════════════════════════════════════════════════════════════

@JsName("getPairSelectorState")
fun getPairSelectorState(): dynamic {
    val state = LanguageUI.pairSelector.state
    return pairStateToJs(state)
}

@JsName("selectSourceLanguage")
fun selectSourceLanguage(code: String): dynamic {
    val state = LanguageUI.pairSelector.dispatch(TranslationPairAction.SelectSource(code))
    return pairStateToJs(state)
}

@JsName("selectTargetLanguage")
fun selectTargetLanguage(code: String): dynamic {
    val state = LanguageUI.pairSelector.dispatch(TranslationPairAction.SelectTarget(code))
    return pairStateToJs(state)
}

@JsName("selectTranslationPair")
fun selectTranslationPair(pairId: String): dynamic {
    val state = LanguageUI.pairSelector.dispatch(TranslationPairAction.SelectPair(pairId))
    return pairStateToJs(state)
}

@JsName("swapLanguages")
fun swapLanguages(): dynamic {
    val state = LanguageUI.pairSelector.dispatch(TranslationPairAction.SwapLanguages)
    return pairStateToJs(state)
}

@JsName("clearPairSelection")
fun clearPairSelection(): dynamic {
    val state = LanguageUI.pairSelector.dispatch(TranslationPairAction.ClearSelection)
    return pairStateToJs(state)
}

@JsName("getPopularPairs")
fun getPopularPairs(): Array<dynamic> {
    return LanguageUI.popularPairs.map { pairItemToJs(it) }.toTypedArray()
}

@JsName("getRecentPairs")
fun getRecentPairs(): Array<dynamic> {
    return LanguageUI.pairSelector.state.recentPairs.map { pairItemToJs(it) }.toTypedArray()
}

@JsName("getCurrentTranslationPair")
fun getCurrentTranslationPair(): dynamic? {
    val pair = LanguageUI.pairSelector.getSelectedPair() ?: return null
    val obj = js("{}")
    obj["pairId"] = pair.pairId
    obj["srcCode"] = pair.srcCode
    obj["tgtCode"] = pair.tgtCode
    obj["srcNativeName"] = pair.source.nativeName
    obj["tgtNativeName"] = pair.target.nativeName
    obj["srcEnglishName"] = pair.source.englishName
    obj["tgtEnglishName"] = pair.target.englishName
    obj["srcDirection"] = pair.source.direction.name
    obj["tgtDirection"] = pair.target.direction.name
    return obj
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper conversion functions
// ═══════════════════════════════════════════════════════════════════════════════

private fun stateToJs(state: LanguageSelectorState): dynamic {
    val obj = js("{}")
    obj["languages"] = state.languages.map { langToJs(it) }.toTypedArray()
    obj["filteredLanguages"] = state.filteredLanguages.map { langToJs(it) }.toTypedArray()
    obj["selectedLanguage"] = state.selectedLanguage?.let { langToJs(it) }
    obj["filter"] = state.filter
    obj["groupByScript"] = state.groupByScript
    obj["languageCount"] = state.languages.size
    obj["filteredCount"] = state.filteredLanguages.size
    return obj
}

private fun langToJs(lang: LanguageItem): dynamic {
    val obj = js("{}")
    obj["code"] = lang.code
    obj["nativeName"] = lang.nativeName
    obj["englishName"] = lang.englishName
    obj["script"] = lang.script.name
    obj["scriptName"] = lang.scriptName
    obj["direction"] = lang.direction.name
    obj["isRtl"] = lang.isRtl
    obj["flag"] = lang.flag
    return obj
}

private fun pairStateToJs(state: TranslationPairSelectorState): dynamic {
    val obj = js("{}")
    obj["sourceLanguage"] = state.sourceLanguage?.let { langToJs(it) }
    obj["targetLanguage"] = state.targetLanguage?.let { langToJs(it) }
    obj["availableTargets"] = state.availableTargets.map { langToJs(it) }.toTypedArray()
    obj["recentPairs"] = state.recentPairs.map { pairItemToJs(it) }.toTypedArray()
    obj["isValidPair"] = state.isValidPair
    obj["pairId"] = state.pairId
    return obj
}

private fun pairItemToJs(item: TranslationPairItem): dynamic {
    val obj = js("{}")
    obj["pairId"] = item.pairId
    obj["sourceCode"] = item.sourceCode
    obj["targetCode"] = item.targetCode
    obj["sourceNative"] = item.sourceNative
    obj["targetNative"] = item.targetNative
    obj["sourceFlag"] = item.sourceFlag
    obj["targetFlag"] = item.targetFlag
    obj["label"] = item.label
    obj["fullLabel"] = item.fullLabel
    return obj
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Stage API (6 Paths)
// ═══════════════════════════════════════════════════════════════════════════════

@JsName("getTranslationStageState")
fun getTranslationStageState(): dynamic {
    return stageStateToJs(TranslationStageUI.state)
}

@JsName("getAllStages")
fun getAllStages(): Array<dynamic> {
    return TranslationStage.ALL.map { stageToJs(it) }.toTypedArray()
}

@JsName("getStageByIndex")
fun getStageByIndex(index: Int): dynamic? {
    val stage = TranslationStage.fromIndex(index) ?: return null
    return stageToJs(stage)
}

@JsName("getStageItems")
fun getStageItems(): Array<dynamic> {
    return TranslationStageUI.stageItems.map { stageItemToJs(it) }.toTypedArray()
}

@JsName("startTranslationWorkflow")
fun startTranslationWorkflow(srcCode: String, tgtCode: String, sourceText: String = ""): dynamic {
    val state = TranslationStageUI.startWorkflow(srcCode, tgtCode, sourceText)
    return stageStateToJs(state)
}

@JsName("setStageSourceText")
fun setStageSourceText(text: String): dynamic {
    val state = TranslationStageUI.manager.dispatch(TranslationStageAction.SetSourceText(text))
    return stageStateToJs(state)
}

@JsName("selectTranslationStage")
fun selectTranslationStage(index: Int): dynamic {
    val stage = TranslationStage.fromIndex(index) ?: return stageStateToJs(TranslationStageUI.state)
    val state = TranslationStageUI.manager.dispatch(TranslationStageAction.SelectStage(stage))
    return stageStateToJs(state)
}

@JsName("startTranslationStage")
fun startTranslationStage(index: Int): dynamic {
    val stage = TranslationStage.fromIndex(index) ?: return stageStateToJs(TranslationStageUI.state)
    val state = TranslationStageUI.manager.dispatch(TranslationStageAction.StartStage(stage))
    return stageStateToJs(state)
}

@JsName("completeTranslationStage")
fun completeTranslationStage(
    index: Int,
    words: Array<String>,
    ipa: String = "",
    score: Float = 0f,
    notes: String = ""
): dynamic {
    val stage = TranslationStage.fromIndex(index) ?: return stageStateToJs(TranslationStageUI.state)
    val state = TranslationStageUI.completeStage(stage, words.toList(), ipa, score, notes)
    return stageStateToJs(state)
}

@JsName("nextTranslationStage")
fun nextTranslationStage(): dynamic {
    val state = TranslationStageUI.manager.dispatch(TranslationStageAction.NextStage)
    return stageStateToJs(state)
}

@JsName("resetTranslationStages")
fun resetTranslationStages(): dynamic {
    val state = TranslationStageUI.manager.dispatch(TranslationStageAction.Reset)
    return stageStateToJs(state)
}

@JsName("getStagePrompt")
fun getStagePrompt(index: Int): String {
    val stage = TranslationStage.fromIndex(index) ?: return ""
    return TranslationStageUI.manager.getStagePrompt(stage)
}

@JsName("getStageAnalysisPrompt")
fun getStageAnalysisPrompt(context: String = ""): String {
    return TranslationStageUI.manager.generateAnalysisPrompt(context) ?: ""
}

@JsName("getStagePathPrompt")
fun getStagePathPrompt(context: String = ""): String {
    return TranslationStageUI.manager.generatePathPrompt(context) ?: ""
}

@JsName("getStageResult")
fun getStageResult(index: Int): dynamic? {
    val stage = TranslationStage.fromIndex(index) ?: return null
    val result = TranslationStageUI.state.resultFor(stage) ?: return null
    return stageResultToJs(result)
}

@JsName("getBestStageResult")
fun getBestStageResult(): dynamic? {
    val result = TranslationStageUI.state.bestResult ?: return null
    return stageResultToJs(result)
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Stage Helper Functions
// ═══════════════════════════════════════════════════════════════════════════════

private fun stageToJs(stage: TranslationStage): dynamic {
    val obj = js("{}")
    obj["index"] = stage.index
    obj["label"] = stage.label
    obj["shortLabel"] = stage.shortLabel
    obj["description"] = stage.description
    obj["icon"] = stage.icon
    obj["hue"] = stage.hue
    obj["color"] = stage.color
    obj["bgColor"] = stage.bgColor
    obj["numberedLabel"] = stage.numberedLabel
    return obj
}

private fun stageItemToJs(item: TranslationStageItem): dynamic {
    val obj = stageToJs(item.stage)
    obj["isCompleted"] = item.isCompleted
    obj["isCurrent"] = item.isCurrent
    obj["isSelected"] = item.isSelected
    obj["statusIcon"] = item.statusIcon
    obj["result"] = item.result?.let { stageResultToJs(it) }
    return obj
}

private fun stageResultToJs(result: StageResultItem): dynamic {
    val obj = js("{}")
    obj["index"] = result.index
    obj["label"] = result.label
    obj["color"] = result.color
    obj["words"] = result.words.toTypedArray()
    obj["ipa"] = result.ipa
    obj["score"] = result.score
    obj["notes"] = result.notes
    obj["wordCount"] = result.wordCount
    obj["formattedWords"] = result.formattedWords
    return obj
}

private fun stageStateToJs(state: TranslationStageState): dynamic {
    val obj = js("{}")
    obj["pair"] = state.pair?.let { pairItemToJs(it) }
    obj["sourceText"] = state.sourceText
    obj["currentStage"] = state.currentStage?.let { stageToJs(it) }
    obj["selectedStage"] = state.selectedStage?.let { stageToJs(it) }
    obj["completedStages"] = state.completedStages.map { it.index }.toTypedArray()
    obj["stageItems"] = state.stageItems.map { stageItemToJs(it) }.toTypedArray()
    obj["results"] = state.results.map { (stage, result) ->
        val r = stageResultToJs(result)
        r["stageIndex"] = stage.index
        r
    }.toTypedArray()
    obj["progressPercent"] = state.progressPercent
    obj["isComplete"] = state.isComplete
    obj["isProcessing"] = state.isProcessing
    obj["nextStage"] = state.nextStage?.let { stageToJs(it) }
    obj["bestResult"] = state.bestResult?.let { stageResultToJs(it) }
    return obj
}
