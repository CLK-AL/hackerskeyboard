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
