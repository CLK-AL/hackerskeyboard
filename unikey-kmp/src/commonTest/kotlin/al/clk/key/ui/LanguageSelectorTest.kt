package al.clk.key.ui

import al.clk.key.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

// ═══════════════════════════════════════════════════════════════════════════════
// Language Selector State Tests
// ═══════════════════════════════════════════════════════════════════════════════

class LanguageSelectorStateTest {

    @Test
    fun testInitialState() {
        val state = LanguageSelectorState.initial()
        assertEquals(23, state.languages.size, "Should have 23 languages")
        assertNull(state.selectedLanguage)
        assertEquals("", state.filter)
        assertTrue(state.groupByScript)
    }

    @Test
    fun testFilteredLanguagesEmpty() {
        val state = LanguageSelectorState.initial()
        assertEquals(23, state.filteredLanguages.size, "Empty filter returns all")
    }

    @Test
    fun testFilteredLanguagesByEnglishName() {
        val state = LanguageSelectorState.initial().copy(filter = "Hebrew")
        val filtered = state.filteredLanguages
        assertEquals(1, filtered.size)
        assertEquals("he", filtered[0].code)
    }

    @Test
    fun testFilteredLanguagesByNativeName() {
        val state = LanguageSelectorState.initial().copy(filter = "עברית")
        val filtered = state.filteredLanguages
        assertEquals(1, filtered.size)
        assertEquals("he", filtered[0].code)
    }

    @Test
    fun testFilteredLanguagesByCode() {
        val state = LanguageSelectorState.initial().copy(filter = "en")
        val filtered = state.filteredLanguages
        assertTrue(filtered.any { it.code == "en" })
    }

    @Test
    fun testFilterCaseInsensitive() {
        val state = LanguageSelectorState.initial().copy(filter = "ENGLISH")
        val filtered = state.filteredLanguages
        assertTrue(filtered.any { it.code == "en" })
    }

    @Test
    fun testLanguagesByScript() {
        val state = LanguageSelectorState.initial()
        val grouped = state.languagesByScript

        assertTrue(grouped.containsKey(Script.LATIN), "Should have Latin script")
        assertTrue(grouped.containsKey(Script.HEBREW), "Should have Hebrew script")
        assertTrue(grouped.containsKey(Script.ARABIC), "Should have Arabic script")

        // Hebrew should be in Hebrew script
        val hebrewGroup = grouped[Script.HEBREW]
        assertNotNull(hebrewGroup)
        assertTrue(hebrewGroup.any { it.code == "he" })
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Language Item Tests
// ═══════════════════════════════════════════════════════════════════════════════

class LanguageItemTest {

    @Test
    fun testFromKeyLanguage() {
        val keyLang = KeyLanguage.fromCode("he")!!
        val item = LanguageItem.from(keyLang)

        assertEquals("he", item.code)
        assertEquals("עברית", item.nativeName)
        assertEquals("Hebrew", item.englishName)
        assertEquals(Script.HEBREW, item.script)
        assertEquals(TextDirection.RTL, item.direction)
    }

    @Test
    fun testIsRtl() {
        val hebrew = LanguageItem.from(KeyLanguage.fromCode("he")!!)
        val english = LanguageItem.from(KeyLanguage.fromCode("en")!!)
        val arabic = LanguageItem.from(KeyLanguage.fromCode("ar")!!)

        assertTrue(hebrew.isRtl, "Hebrew should be RTL")
        assertFalse(english.isRtl, "English should be LTR")
        assertTrue(arabic.isRtl, "Arabic should be RTL")
    }

    @Test
    fun testScriptName() {
        val hebrew = LanguageItem.from(KeyLanguage.fromCode("he")!!)
        assertEquals("Hebrew", hebrew.scriptName)

        val english = LanguageItem.from(KeyLanguage.fromCode("en")!!)
        assertEquals("Latin", english.scriptName)
    }

    @Test
    fun testFlags() {
        val hebrew = LanguageItem.from(KeyLanguage.fromCode("he")!!)
        val english = LanguageItem.from(KeyLanguage.fromCode("en")!!)
        val japanese = LanguageItem.from(KeyLanguage.fromCode("ja")!!)

        assertEquals("🇮🇱", hebrew.flag)
        assertEquals("🇬🇧", english.flag)
        assertEquals("🇯🇵", japanese.flag)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Pair Selector State Tests
// ═══════════════════════════════════════════════════════════════════════════════

class TranslationPairSelectorStateTest {

    @Test
    fun testInitialState() {
        val state = TranslationPairSelectorState.initial()
        assertNull(state.sourceLanguage)
        assertNull(state.targetLanguage)
        assertEquals(23, state.availableTargets.size)
        assertFalse(state.isValidPair)
        assertNull(state.pairId)
    }

    @Test
    fun testIsValidPair() {
        val en = LanguageItem.from(KeyLanguage.fromCode("en")!!)
        val he = LanguageItem.from(KeyLanguage.fromCode("he")!!)

        // No source/target
        val state1 = TranslationPairSelectorState()
        assertFalse(state1.isValidPair)

        // Only source
        val state2 = TranslationPairSelectorState(sourceLanguage = en)
        assertFalse(state2.isValidPair)

        // Valid pair
        val state3 = TranslationPairSelectorState(sourceLanguage = en, targetLanguage = he)
        assertTrue(state3.isValidPair)

        // Same language (invalid)
        val state4 = TranslationPairSelectorState(sourceLanguage = en, targetLanguage = en)
        assertFalse(state4.isValidPair)
    }

    @Test
    fun testPairId() {
        val en = LanguageItem.from(KeyLanguage.fromCode("en")!!)
        val he = LanguageItem.from(KeyLanguage.fromCode("he")!!)

        val state = TranslationPairSelectorState(sourceLanguage = en, targetLanguage = he)
        assertEquals("en→he", state.pairId)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Pair Item Tests
// ═══════════════════════════════════════════════════════════════════════════════

class TranslationPairItemTest {

    @Test
    fun testFromTranslationPair() {
        val pair = TranslationPair.EN_TO_HE
        val item = TranslationPairItem.from(pair)

        assertEquals("en→he", item.pairId)
        assertEquals("en", item.sourceCode)
        assertEquals("he", item.targetCode)
        assertEquals("English", item.sourceNative)
        assertEquals("עברית", item.targetNative)
    }

    @Test
    fun testLabel() {
        val item = TranslationPairItem.from(TranslationPair.EN_TO_HE)
        assertEquals("🇬🇧 → 🇮🇱", item.label)
    }

    @Test
    fun testFullLabel() {
        val item = TranslationPairItem.from(TranslationPair.EN_TO_HE)
        assertEquals("English → עברית", item.fullLabel)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Language Selector Manager Tests
// ═══════════════════════════════════════════════════════════════════════════════

class LanguageSelectorManagerTest {

    @Test
    fun testInitialState() {
        val manager = LanguageSelectorManager()
        assertEquals(23, manager.state.languages.size)
        assertNull(manager.state.selectedLanguage)
    }

    @Test
    fun testSelectLanguage() {
        val manager = LanguageSelectorManager()
        manager.dispatch(LanguageSelectorAction.SelectLanguage("he"))

        assertNotNull(manager.state.selectedLanguage)
        assertEquals("he", manager.state.selectedLanguage?.code)
    }

    @Test
    fun testSelectInvalidLanguage() {
        val manager = LanguageSelectorManager()
        manager.dispatch(LanguageSelectorAction.SelectLanguage("invalid"))

        assertNull(manager.state.selectedLanguage)
    }

    @Test
    fun testUpdateFilter() {
        val manager = LanguageSelectorManager()
        manager.dispatch(LanguageSelectorAction.UpdateFilter("Hebrew"))

        assertEquals("Hebrew", manager.state.filter)
        assertEquals(1, manager.state.filteredLanguages.size)
    }

    @Test
    fun testToggleGroupByScript() {
        val manager = LanguageSelectorManager()
        assertTrue(manager.state.groupByScript)

        manager.dispatch(LanguageSelectorAction.ToggleGroupByScript(false))
        assertFalse(manager.state.groupByScript)

        manager.dispatch(LanguageSelectorAction.ToggleGroupByScript(true))
        assertTrue(manager.state.groupByScript)
    }

    @Test
    fun testClearSelection() {
        val manager = LanguageSelectorManager()
        manager.dispatch(LanguageSelectorAction.SelectLanguage("he"))
        assertNotNull(manager.state.selectedLanguage)

        manager.dispatch(LanguageSelectorAction.ClearSelection)
        assertNull(manager.state.selectedLanguage)
    }

    @Test
    fun testGetSelectedKeyLanguage() {
        val manager = LanguageSelectorManager()
        assertNull(manager.getSelectedKeyLanguage())

        manager.dispatch(LanguageSelectorAction.SelectLanguage("he"))
        val keyLang = manager.getSelectedKeyLanguage()
        assertNotNull(keyLang)
        assertEquals("he", keyLang.code)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Pair Manager Tests
// ═══════════════════════════════════════════════════════════════════════════════

class TranslationPairManagerTest {

    @Test
    fun testInitialState() {
        val manager = TranslationPairManager()
        assertNull(manager.state.sourceLanguage)
        assertNull(manager.state.targetLanguage)
        assertFalse(manager.state.isValidPair)
    }

    @Test
    fun testSelectSource() {
        val manager = TranslationPairManager()
        manager.dispatch(TranslationPairAction.SelectSource("en"))

        assertNotNull(manager.state.sourceLanguage)
        assertEquals("en", manager.state.sourceLanguage?.code)
        // Available targets should exclude source
        assertFalse(manager.state.availableTargets.any { it.code == "en" })
    }

    @Test
    fun testSelectTarget() {
        val manager = TranslationPairManager()
        manager.dispatch(TranslationPairAction.SelectSource("en"))
        manager.dispatch(TranslationPairAction.SelectTarget("he"))

        assertTrue(manager.state.isValidPair)
        assertEquals("en→he", manager.state.pairId)
    }

    @Test
    fun testSelectTargetSameAsSource() {
        val manager = TranslationPairManager()
        manager.dispatch(TranslationPairAction.SelectSource("en"))
        manager.dispatch(TranslationPairAction.SelectTarget("en"))

        // Target should still be set but pair is invalid
        // Actually, after SelectSource, "en" is removed from availableTargets
        // So SelectTarget("en") won't find it
        assertFalse(manager.state.isValidPair)
    }

    @Test
    fun testSelectPair() {
        val manager = TranslationPairManager()
        manager.dispatch(TranslationPairAction.SelectPair("en→he"))

        assertEquals("en", manager.state.sourceLanguage?.code)
        assertEquals("he", manager.state.targetLanguage?.code)
        assertTrue(manager.state.isValidPair)
    }

    @Test
    fun testSwapLanguages() {
        val manager = TranslationPairManager()
        manager.dispatch(TranslationPairAction.SelectPair("en→he"))
        manager.dispatch(TranslationPairAction.SwapLanguages)

        assertEquals("he", manager.state.sourceLanguage?.code)
        assertEquals("en", manager.state.targetLanguage?.code)
        assertEquals("he→en", manager.state.pairId)
    }

    @Test
    fun testSwapWithNoSelection() {
        val manager = TranslationPairManager()
        manager.dispatch(TranslationPairAction.SwapLanguages)

        // Should be no-op
        assertNull(manager.state.sourceLanguage)
        assertNull(manager.state.targetLanguage)
    }

    @Test
    fun testClearSelection() {
        val manager = TranslationPairManager()
        manager.dispatch(TranslationPairAction.SelectPair("en→he"))
        assertTrue(manager.state.isValidPair)

        manager.dispatch(TranslationPairAction.ClearSelection)
        assertNull(manager.state.sourceLanguage)
        assertNull(manager.state.targetLanguage)
        assertFalse(manager.state.isValidPair)
    }

    @Test
    fun testGetSelectedPair() {
        val manager = TranslationPairManager()
        assertNull(manager.getSelectedPair())

        manager.dispatch(TranslationPairAction.SelectPair("en→he"))
        val pair = manager.getSelectedPair()
        assertNotNull(pair)
        assertEquals("en", pair.srcCode)
        assertEquals("he", pair.tgtCode)
    }

    @Test
    fun testRecentPairs() {
        val manager = TranslationPairManager()

        // Select first pair
        manager.dispatch(TranslationPairAction.SelectSource("en"))
        manager.dispatch(TranslationPairAction.SelectTarget("he"))

        // Recent pairs should include en→he
        assertTrue(manager.state.recentPairs.any { it.pairId == "en→he" })
    }

    @Test
    fun testPopularPairs() {
        val manager = TranslationPairManager()
        val popular = manager.getPopularPairs()

        assertTrue(popular.isNotEmpty())
        assertTrue(popular.any { it.pairId == "en→he" })
        assertTrue(popular.any { it.pairId == "he→en" })
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// LanguageUI Singleton Tests
// ═══════════════════════════════════════════════════════════════════════════════

class LanguageUITest {

    @Test
    fun testAllLanguages() {
        assertEquals(23, LanguageUI.allLanguages.size)
    }

    @Test
    fun testGetLanguage() {
        val hebrew = LanguageUI.getLanguage("he")
        assertNotNull(hebrew)
        assertEquals("Hebrew", hebrew.englishName)

        val invalid = LanguageUI.getLanguage("invalid")
        assertNull(invalid)
    }

    @Test
    fun testPopularPairs() {
        val popular = LanguageUI.popularPairs
        assertTrue(popular.isNotEmpty())
    }

    @Test
    fun testCreatePairItem() {
        val item = LanguageUI.createPairItem("en", "he")
        assertNotNull(item)
        assertEquals("en→he", item.pairId)

        val invalid = LanguageUI.createPairItem("en", "en")
        assertNull(invalid, "Same language pair should be null")
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Stage Tests (6 Paths)
// ═══════════════════════════════════════════════════════════════════════════════

class TranslationStageTest {

    @Test
    fun testAllStages() {
        assertEquals(6, TranslationStage.ALL.size)
        assertEquals(1, TranslationStage.IPA_ECHO.index)
        assertEquals(6, TranslationStage.COMPRESSION.index)
    }

    @Test
    fun testStageFromIndex() {
        assertEquals(TranslationStage.IPA_ECHO, TranslationStage.fromIndex(1))
        assertEquals(TranslationStage.LITERAL_ANCHOR, TranslationStage.fromIndex(2))
        assertEquals(TranslationStage.COMPRESSION, TranslationStage.fromIndex(6))
        assertNull(TranslationStage.fromIndex(0))
        assertNull(TranslationStage.fromIndex(7))
    }

    @Test
    fun testStageFromShortLabel() {
        assertEquals(TranslationStage.IPA_ECHO, TranslationStage.fromShortLabel("IPA"))
        assertEquals(TranslationStage.COMPRESSION, TranslationStage.fromShortLabel("cmp"))
        assertNull(TranslationStage.fromShortLabel("invalid"))
    }

    @Test
    fun testStageColors() {
        val stage = TranslationStage.IPA_ECHO
        assertTrue(stage.color.startsWith("hsl("))
        assertTrue(stage.bgColor.startsWith("hsl("))
        assertEquals(0, stage.hue)  // Red for IPA Echo
    }

    @Test
    fun testStageLabels() {
        val stage = TranslationStage.LITERAL_ANCHOR
        assertEquals("Literal Anchor", stage.label)
        assertEquals("LIT", stage.shortLabel)
        assertEquals("2. Literal Anchor", stage.numberedLabel)
    }
}

class StageResultItemTest {

    @Test
    fun testStageResultProperties() {
        val result = StageResultItem(
            stage = TranslationStage.IPA_ECHO,
            words = listOf("word1", "word2", "word3"),
            ipa = "wɜːd",
            score = 0.85f,
            notes = "Good match"
        )

        assertEquals(1, result.index)
        assertEquals("IPA Echo", result.label)
        assertEquals(3, result.wordCount)
        assertEquals("word1 word2 word3", result.formattedWords)
        assertEquals(0.85f, result.score)
    }
}

class TranslationStageStateTest {

    @Test
    fun testInitialState() {
        val state = TranslationStageState.initial()
        assertNull(state.pair)
        assertEquals("", state.sourceText)
        assertNull(state.currentStage)
        assertTrue(state.completedStages.isEmpty())
        assertEquals(0, state.progressPercent)
        assertFalse(state.isComplete)
    }

    @Test
    fun testForPair() {
        val state = TranslationStageState.forPair("en", "he")
        assertNotNull(state)
        assertNotNull(state!!.pair)
        assertEquals("en→he", state.pair!!.pairId)
    }

    @Test
    fun testForPairInvalid() {
        val state = TranslationStageState.forPair("en", "en")
        assertNull(state)
    }

    @Test
    fun testStageItems() {
        val state = TranslationStageState.initial()
        assertEquals(6, state.stageItems.size)

        val first = state.stageItems.first()
        assertEquals(1, first.index)
        assertFalse(first.isCompleted)
        assertFalse(first.isCurrent)
    }

    @Test
    fun testProgress() {
        val state = TranslationStageState(
            completedStages = setOf(TranslationStage.IPA_ECHO, TranslationStage.LITERAL_ANCHOR)
        )
        assertEquals(33, state.progressPercent)  // 2/6 = 33%
        assertFalse(state.isComplete)
    }

    @Test
    fun testIsComplete() {
        val state = TranslationStageState(
            completedStages = TranslationStage.ALL.toSet()
        )
        assertEquals(100, state.progressPercent)
        assertTrue(state.isComplete)
    }

    @Test
    fun testNextStage() {
        val state1 = TranslationStageState.initial()
        assertEquals(TranslationStage.IPA_ECHO, state1.nextStage)

        val state2 = TranslationStageState(
            completedStages = setOf(TranslationStage.IPA_ECHO)
        )
        assertEquals(TranslationStage.LITERAL_ANCHOR, state2.nextStage)

        val state3 = TranslationStageState(
            completedStages = TranslationStage.ALL.toSet()
        )
        assertNull(state3.nextStage)
    }

    @Test
    fun testBestResult() {
        val result1 = StageResultItem(TranslationStage.IPA_ECHO, listOf("a"), "", 0.5f)
        val result2 = StageResultItem(TranslationStage.LITERAL_ANCHOR, listOf("b"), "", 0.8f)
        val result3 = StageResultItem(TranslationStage.CULTURAL_CHARGE, listOf("c"), "", 0.6f)

        val state = TranslationStageState(
            results = mapOf(
                TranslationStage.IPA_ECHO to result1,
                TranslationStage.LITERAL_ANCHOR to result2,
                TranslationStage.CULTURAL_CHARGE to result3
            )
        )

        val best = state.bestResult
        assertNotNull(best)
        assertEquals(TranslationStage.LITERAL_ANCHOR, best!!.stage)
        assertEquals(0.8f, best.score)
    }
}

class TranslationStageItemTest {

    @Test
    fun testStageItemProperties() {
        val item = TranslationStageItem(
            stage = TranslationStage.EMOTIONAL_REGISTER,
            isCompleted = true,
            isCurrent = false,
            isSelected = true
        )

        assertEquals(4, item.index)
        assertEquals("Emotional Register", item.label)
        assertEquals("EMO", item.shortLabel)
        assertEquals("💖", item.icon)
        assertTrue(item.isCompleted)
        assertFalse(item.isCurrent)
        assertTrue(item.isSelected)
    }

    @Test
    fun testStatusIcon() {
        val completed = TranslationStageItem(TranslationStage.IPA_ECHO, isCompleted = true)
        assertEquals("✓", completed.statusIcon)

        val current = TranslationStageItem(TranslationStage.IPA_ECHO, isCurrent = true)
        assertEquals("▶", current.statusIcon)

        val pending = TranslationStageItem(TranslationStage.IPA_ECHO)
        assertEquals("○", pending.statusIcon)
    }
}

class TranslationStageManagerTest {

    @Test
    fun testInitialState() {
        val manager = TranslationStageManager()
        assertNull(manager.state.pair)
        assertFalse(manager.state.isProcessing)
    }

    @Test
    fun testSetPair() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.SetPair("en", "he"))

        assertNotNull(manager.state.pair)
        assertEquals("en→he", manager.state.pair?.pairId)
    }

    @Test
    fun testSetSourceText() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.SetSourceText("Hello world"))

        assertEquals("Hello world", manager.state.sourceText)
    }

    @Test
    fun testSelectStage() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.SelectStage(TranslationStage.CULTURAL_CHARGE))

        assertEquals(TranslationStage.CULTURAL_CHARGE, manager.state.selectedStage)
    }

    @Test
    fun testStartStage() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.StartStage(TranslationStage.IPA_ECHO))

        assertEquals(TranslationStage.IPA_ECHO, manager.state.currentStage)
        assertTrue(manager.state.isProcessing)
    }

    @Test
    fun testCompleteStage() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.StartStage(TranslationStage.IPA_ECHO))

        val result = StageResultItem(
            stage = TranslationStage.IPA_ECHO,
            words = listOf("echo", "sound"),
            ipa = "ɛkəʊ",
            score = 0.9f
        )
        manager.dispatch(TranslationStageAction.CompleteStage(result))

        assertTrue(TranslationStage.IPA_ECHO in manager.state.completedStages)
        assertNotNull(manager.state.resultFor(TranslationStage.IPA_ECHO))
        assertNull(manager.state.currentStage)
        assertFalse(manager.state.isProcessing)
    }

    @Test
    fun testNextStage() {
        val manager = TranslationStageManager()

        // Complete first stage
        val result = StageResultItem(TranslationStage.IPA_ECHO, listOf("a"), "")
        manager.dispatch(TranslationStageAction.CompleteStage(result))

        // Move to next
        manager.dispatch(TranslationStageAction.NextStage)

        assertEquals(TranslationStage.LITERAL_ANCHOR, manager.state.currentStage)
        assertTrue(manager.state.isProcessing)
    }

    @Test
    fun testReset() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.SetPair("en", "he"))
        manager.dispatch(TranslationStageAction.SetSourceText("Test"))
        manager.dispatch(TranslationStageAction.CompleteStage(
            StageResultItem(TranslationStage.IPA_ECHO, listOf("a"), "")
        ))

        manager.dispatch(TranslationStageAction.Reset)

        assertEquals("", manager.state.sourceText)
        assertTrue(manager.state.completedStages.isEmpty())
        // Pair should be preserved
        assertNotNull(manager.state.pair)
    }

    @Test
    fun testGetTranslationPair() {
        val manager = TranslationStageManager()
        assertNull(manager.getTranslationPair())

        manager.dispatch(TranslationStageAction.SetPair("en", "he"))
        val pair = manager.getTranslationPair()
        assertNotNull(pair)
        assertEquals("en", pair!!.srcCode)
        assertEquals("he", pair.tgtCode)
    }

    @Test
    fun testGeneratePrompts() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.SetPair("en", "he"))

        val analysisPrompt = manager.generateAnalysisPrompt()
        assertNotNull(analysisPrompt)
        assertTrue(analysisPrompt!!.contains("English"))
        assertTrue(analysisPrompt.contains("Hebrew"))

        val pathPrompt = manager.generatePathPrompt()
        assertNotNull(pathPrompt)
        assertTrue(pathPrompt!!.contains("6 PATHS"))
    }

    @Test
    fun testGetStagePrompt() {
        val manager = TranslationStageManager()
        manager.dispatch(TranslationStageAction.SetPair("en", "he"))

        val prompt = manager.getStagePrompt(TranslationStage.IPA_ECHO)
        assertTrue(prompt.contains("IPA"))

        val literalPrompt = manager.getStagePrompt(TranslationStage.LITERAL_ANCHOR)
        assertTrue(literalPrompt.contains("denotatively"))
    }
}

class TranslationStageUITest {

    @Test
    fun testAllStages() {
        assertEquals(6, TranslationStageUI.allStages.size)
    }

    @Test
    fun testGetStage() {
        assertEquals(TranslationStage.IPA_ECHO, TranslationStageUI.getStage(1))
        assertEquals(TranslationStage.COMPRESSION, TranslationStageUI.getStage(6))
        assertNull(TranslationStageUI.getStage(0))
    }

    @Test
    fun testStartWorkflow() {
        val state = TranslationStageUI.startWorkflow("en", "he", "Test text")

        assertNotNull(state.pair)
        assertEquals("en→he", state.pair?.pairId)
        assertEquals("Test text", state.sourceText)
    }

    @Test
    fun testCompleteStage() {
        TranslationStageUI.manager.dispatch(TranslationStageAction.Reset)
        TranslationStageUI.startWorkflow("en", "he")

        val state = TranslationStageUI.completeStage(
            stage = TranslationStage.IPA_ECHO,
            words = listOf("word1", "word2"),
            ipa = "test",
            score = 0.75f
        )

        assertTrue(TranslationStage.IPA_ECHO in state.completedStages)
        assertEquals(16, state.progressPercent)  // 1/6 ≈ 16%
    }
}
