package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class UniKeyWasmTest {

    @Test
    fun testWasmGetDisplay() {
        val display = wasm_getDisplay("a", "en")
        assertEquals("a", display)
    }

    @Test
    fun testWasmModeManagement() {
        wasm_setUkMode("he")
        assertEquals("he", wasm_getUkMode())

        wasm_setUkMode("en")
        assertEquals("en", wasm_getUkMode())
    }

    @Test
    fun testWasmCycleMode() {
        wasm_setUkMode("en")
        val next = wasm_cycleUkMode()
        assertEquals("EN", next)
    }

    @Test
    fun testWasmIpaColor() {
        val hue = wasm_ipaHue("IN")
        assertTrue(hue in 0..360)

        val color = wasm_hsl(180, 50, 70)
        assertEquals("hsl(180, 50%, 70%)", color)
    }

    @Test
    fun testWasmHeIpa() {
        val ipa = wasm_heIpa("שלום")
        assertTrue(ipa.isNotEmpty())
    }

    @Test
    fun testWasmEnIpa() {
        val ipa = wasm_enIpa("raging")
        assertEquals("IN", ipa)
    }

    @Test
    fun testWasmDetectScript() {
        assertEquals("HEBREW", wasm_detectScript("שלום"))
        assertEquals("LATIN", wasm_detectScript("hello"))
    }

    @Test
    fun testWasmWordHue() {
        val hue1 = wasm_wordHue("raging", false)
        val hue2 = wasm_wordHue("staging", false)
        // Rhyming words should have similar hue
        assertTrue(kotlin.math.abs(hue1 - hue2) < 30)
    }

    @Test
    fun testWasmRhymes() {
        assertTrue(wasm_rhymes("raging", false, "staging", false))
        assertTrue(wasm_rhymes("night", false, "light", false))
    }

    @Test
    fun testWasmRhymeDistance() {
        val dist = wasm_rhymeDistance("raging", false, "staging", false)
        assertEquals(0, dist, "Perfect rhyme should have distance 0")
    }

    @Test
    fun testWasmToIpaJson() {
        val json = wasm_toIpa("hello")
        assertTrue(json.startsWith("["))
        assertTrue(json.endsWith("]"))
        assertTrue(json.contains("consonant"))
        assertTrue(json.contains("vowel"))
    }

    @Test
    fun testWasmParseHebrewSyllables() {
        val json = wasm_parseHebrewSyllables("שלום")
        assertTrue(json.startsWith("["))
    }

    @Test
    fun testWasmParseEnglishSyllables() {
        val json = wasm_parseEnglishSyllables("raging")
        assertTrue(json.contains("\"consonant\":\"r\""))
    }

    @Test
    fun testWasmCreateVerseIndex() {
        val json = wasm_createVerseIndex(1, 2)
        assertTrue(json.contains("\"vType\":1"))
        assertTrue(json.contains("\"lineIdx\":2"))
        assertTrue(json.contains("\"formatted\":\"V1.L3\""))
    }

    @Test
    fun testWasmInsertLineOrder() {
        val order = wasm_insertLineOrder(1.0f, 2.0f)
        assertEquals(1.5f, order)
    }

    @Test
    fun testWasmCreateHierarchicalIndex() {
        val json = wasm_createHierarchicalIndex(1, 1, 2, 3)
        assertTrue(json.contains("\"depth\":4"))
        assertTrue(json.contains("\"formatted\":\"V1.L2.W3.S4\""))
    }

    @Test
    fun testWasmParseHierarchicalIndex() {
        val json = wasm_parseHierarchicalIndex("V1.L2.W3.S4")
        assertTrue(json.contains("\"vType\":1"))
        assertTrue(json.contains("\"lineIdx\":1"))
        assertTrue(json.contains("\"wordIdx\":2"))
        assertTrue(json.contains("\"sylIdx\":3"))
    }

    @Test
    fun testWasmParseHierarchicalIndexInvalid() {
        val json = wasm_parseHierarchicalIndex("invalid")
        assertTrue(json.contains("error"))
    }

    @Test
    fun testWasmSyllableBoundaries() {
        val json = wasm_syllableBoundaries("hello")
        assertTrue(json.startsWith("["))
        assertTrue(json.contains("0"))
    }

    @Test
    fun testWasmCreateCursorState() {
        val json = wasm_createCursorState("raging", 0)
        assertTrue(json.contains("\"pos\":0"))
        assertTrue(json.contains("\"sylIdx\":0"))
    }

    @Test
    fun testWasmLanguageCount() {
        assertEquals(23, wasm_getLanguageCount())
    }

    @Test
    fun testWasmGetLanguageCodes() {
        val json = wasm_getLanguageCodes()
        assertTrue(json.contains("\"he\""))
        assertTrue(json.contains("\"en\""))
    }

    @Test
    fun testWasmGetKeyLanguages() {
        val json = wasm_getKeyLanguages()
        assertTrue(json.startsWith("["))
        assertTrue(json.contains("\"code\":\"he\""))
        assertTrue(json.contains("\"englishName\":\"Hebrew\""))
    }

    @Test
    fun testWasmGetKeyLanguage() {
        val json = wasm_getKeyLanguage("he")
        assertTrue(json.contains("\"code\":\"he\""))
        assertTrue(json.contains("\"englishName\":\"Hebrew\""))
        assertTrue(json.contains("\"direction\":\"RTL\""))

        val invalid = wasm_getKeyLanguage("invalid")
        assertTrue(invalid.contains("error"))
    }

    @Test
    fun testWasmIsValidPair() {
        assertTrue(wasm_isValidPair("en", "he"))
        assertFalse(wasm_isValidPair("en", "en"))
        assertFalse(wasm_isValidPair("invalid", "he"))
    }

    @Test
    fun testWasmGetTranslationPair() {
        val json = wasm_getTranslationPair("en", "he")
        assertTrue(json.contains("\"pairId\":\"en→he\""))
        assertTrue(json.contains("\"srcCode\":\"en\""))

        val invalid = wasm_getTranslationPair("en", "en")
        assertTrue(invalid.contains("error"))
    }

    @Test
    fun testWasmGenerateAnalysisPrompt() {
        val prompt = wasm_generateAnalysisPrompt("en", "he", "Poetry")
        assertTrue(prompt.contains("English"))
        assertTrue(prompt.contains("Hebrew"))
    }

    @Test
    fun testWasmGeneratePathPrompt() {
        val prompt = wasm_generatePathPrompt("en", "he")
        assertTrue(prompt.contains("6 PATHS"))
        assertTrue(prompt.contains("IPA ECHO"))
    }

    @Test
    fun testWasmStageCount() {
        assertEquals(6, wasm_getStageCount())
    }

    @Test
    fun testWasmGetAllStages() {
        val json = wasm_getAllStages()
        assertTrue(json.startsWith("["))
        assertTrue(json.contains("\"label\":\"IPA Echo\""))
        assertTrue(json.contains("\"label\":\"Compression\""))
    }

    @Test
    fun testWasmGetStageByIndex() {
        val json = wasm_getStageByIndex(1)
        assertTrue(json.contains("\"label\":\"IPA Echo\""))
        assertTrue(json.contains("\"shortLabel\":\"IPA\""))

        val invalid = wasm_getStageByIndex(0)
        assertTrue(invalid.contains("error"))
    }

    @Test
    fun testWasmGetStagePrompt() {
        val prompt = wasm_getStagePrompt(1, "en", "he")
        assertTrue(prompt.contains("IPA"))
        assertTrue(prompt.contains("English"))
    }

    @Test
    fun testWasmCreateWordState() {
        val json = wasm_createWordState("hello")
        assertTrue(json.contains("\"text\":\"hello\""))
        assertTrue(json.contains("\"order\":1"))
        assertTrue(json.contains("\"sylBounds\""))
        assertTrue(json.contains("\"syllables\""))
        assertTrue(json.contains("\"hue\":"))
    }

    @Test
    fun testWasmWordHueForLang() {
        val hue = wasm_wordHueForLang("hello", "en")
        assertTrue(hue in 0..360)
    }

    @Test
    fun testWasmToIpaForLang() {
        val json = wasm_toIpaForLang("hello", "en")
        assertTrue(json.startsWith("["))
        assertTrue(json.contains("consonant"))
    }
}
