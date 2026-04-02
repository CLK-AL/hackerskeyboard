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
