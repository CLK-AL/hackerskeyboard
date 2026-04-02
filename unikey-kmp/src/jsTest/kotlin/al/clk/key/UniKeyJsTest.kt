package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UniKeyJsTest {

    @Test
    fun testGetUniKeyJs() {
        val uk = getUniKey("a")
        assertNotNull(uk)
        assertEquals("a", uk.en)
    }

    @Test
    fun testGetDisplayJs() {
        val display = getDisplay("a", "en")
        assertEquals("a", display)

        val heDisplay = getDisplay("a", "he")
        assertEquals("\u05E9", heDisplay) // shin

        val ipaDisplay = getDisplay("a", "en", ctrl = true)
        assertEquals("\u0283", ipaDisplay) // IPA sh
    }

    @Test
    fun testModeManagement() {
        setUkMode("he")
        assertEquals("he", getUkMode())

        setUkMode("en")
        assertEquals("en", getUkMode())

        val next = cycleUkMode()
        assertEquals("EN", next)
    }

    @Test
    fun testModifierManagement() {
        resetModifiers()
        val mods = getModifiers()
        assertEquals(false, mods["shift"])
        assertEquals(false, mods["alt"])
        assertEquals(false, mods["ctrl"])

        setModifier("shift", true)
        val mods2 = getModifiers()
        assertEquals(true, mods2["shift"])

        resetModifiers()
    }

    @Test
    fun testGetAllKeys() {
        val keys = getAllKeys()
        assertTrue(keys.isNotEmpty())
        assertTrue(keys.contains("a"))
        assertTrue(keys.contains("1"))
    }

    @Test
    fun testGetLetterKeys() {
        val letters = getLetterKeys()
        assertTrue(letters.isNotEmpty())
        assertTrue(letters.contains("a"))
        assertTrue(!letters.contains("1"))
    }

    @Test
    fun testNikudHelpers() {
        val nikudList = getNikudForIpa("a")
        assertTrue(nikudList.isNotEmpty())

        val applied = applyNikud("\u05D1", "a")
        assertEquals("\u05D1\u05B7", applied) // bet + patach
    }

    @Test
    fun testHebrewLetterInfo() {
        val info = getHebrewLetterInfo("\u05D0") // alef
        assertNotNull(info)
        assertEquals("\u05D0", info["letter"])
        assertEquals(true, info["isGuttural"])
    }

    @Test
    fun testBgdkptHelpers() {
        assertTrue(isBgdkpt("\u05D1")) // bet
        assertTrue(!isBgdkpt("\u05DC")) // lamed

        val sound = getBgdkptSound("\u05D1", true)
        assertNotNull(sound)
        assertEquals("b", sound["ipa"])
    }

    @Test
    fun testIpaHelpers() {
        val vowelInfo = getIpaVowelInfo("\u0259") // schwa
        assertNotNull(vowelInfo)
        assertEquals("schwa", vowelInfo["name"])

        val consonantInfo = getIpaConsonantInfo("\u0283") // sh
        assertNotNull(consonantInfo)
        assertEquals("sh", consonantInfo["en"])
    }

    // ═══ KeyLanguage API Tests ═══

    @Test
    fun testGetKeyLanguages() {
        val languages = getKeyLanguages()
        assertEquals(23, languages.size, "Should have 23 languages")

        // Check first language has required fields
        val first = languages[0]
        assertNotNull(first["code"])
        assertNotNull(first["nativeName"])
        assertNotNull(first["englishName"])
        assertNotNull(first["direction"])
        assertNotNull(first["script"])
    }

    @Test
    fun testGetLanguageCount() {
        assertEquals(23, getLanguageCount())
    }

    @Test
    fun testGetKeyLanguage() {
        val hebrew = getKeyLanguage("he")
        assertNotNull(hebrew)
        assertEquals("he", hebrew["code"])
        assertEquals("עברית", hebrew["nativeName"])
        assertEquals("Hebrew", hebrew["englishName"])
        assertEquals("RTL", hebrew["direction"])
        assertEquals("HEBREW", hebrew["script"])

        val english = getKeyLanguage("en")
        assertNotNull(english)
        assertEquals("LTR", english["direction"])
        assertEquals("LATIN", english["script"])
    }

    @Test
    fun testGetKeyLanguageReturnsNullForInvalid() {
        val invalid = getKeyLanguage("invalid")
        assertEquals(null, invalid)
    }

    @Test
    fun testToIpaForLang() {
        val hebrewIpa = toIpaForLang("שלום", "he")
        assertTrue(hebrewIpa.isNotEmpty(), "Should produce Hebrew IPA syllables")

        val englishIpa = toIpaForLang("hello", "en")
        assertTrue(englishIpa.isNotEmpty(), "Should produce English IPA syllables")
    }

    @Test
    fun testWordHueForLang() {
        val hue = wordHueForLang("hello", "en")
        assertTrue(hue in 0..360, "Hue should be 0-360")
    }

    @Test
    fun testWordEndColorForLang() {
        val color = wordEndColorForLang("hello", "en")
        assertTrue(color.startsWith("hsl("), "Should return HSL color string")
    }

    @Test
    fun testRhymeKeyForLang() {
        val key = rhymeKeyForLang("hello", "en", 1)
        assertTrue(key.isNotEmpty(), "Should produce rhyme key")
    }

    @Test
    fun testParseSyllablesForLang() {
        val syllables = parseSyllablesForLang("hello", "en")
        assertTrue(syllables.isNotEmpty(), "Should parse syllables")

        val first = syllables[0]
        assertNotNull(first["consonant"])
        assertNotNull(first["vowel"])
        assertNotNull(first["original"])
        assertNotNull(first["hue"])
    }

    @Test
    fun testGetLanguagePromptHints() {
        val hints = getLanguagePromptHints("he")
        assertNotNull(hints)
        assertNotNull(hints["linguistRole"])
        assertNotNull(hints["culturalNotes"])
        assertNotNull(hints["rhymeNotes"])
        assertNotNull(hints["grammarNotes"])
    }

    @Test
    fun testGenerateAnalysisPrompt() {
        val prompt = generateAnalysisPrompt("en", "he", "Poetry translation")
        assertTrue(prompt.contains("English"), "Should mention source language")
        assertTrue(prompt.contains("Hebrew"), "Should mention target language")
        assertTrue(prompt.contains("Poetry translation"), "Should include context")
    }

    @Test
    fun testGeneratePathPrompt() {
        val prompt = generatePathPrompt("en", "he", "")
        assertTrue(prompt.contains("6 PATHS"), "Should mention 6 paths")
        assertTrue(prompt.contains("IPA ECHO"), "Should list path types")
    }

    @Test
    fun testGetTranslationPair() {
        val pair = getTranslationPair("en", "he")
        assertNotNull(pair)
        assertEquals("en→he", pair["pairId"])
        assertEquals("en", pair["srcCode"])
        assertEquals("he", pair["tgtCode"])
        assertEquals("English", pair["srcEnglishName"])
        assertEquals("Hebrew", pair["tgtEnglishName"])
    }

    @Test
    fun testGetTranslationPairRejectsSame() {
        val same = getTranslationPair("en", "en")
        assertEquals(null, same, "Same language pair should be rejected")
    }

    @Test
    fun testGetAllTranslationPairIds() {
        val ids = getAllTranslationPairIds()
        assertEquals(506, ids.size, "Should have 23x22=506 pairs")
        assertTrue(ids.contains("en→he"), "Should contain en→he")
        assertTrue(ids.contains("he→en"), "Should contain he→en")
    }

    // ═══ Hierarchical Index API Tests (V1.L2.W3.S4) ═══

    @Test
    fun testCreateHierarchicalIndexVerseOnly() {
        val idx = createHierarchicalIndex(1)
        assertEquals(1, idx["vType"])
        assertEquals(null, idx["lineIdx"])
        assertEquals(1, idx["depth"])
        assertEquals("V1", idx["formatted"])
    }

    @Test
    fun testCreateHierarchicalIndexFull() {
        val idx = createHierarchicalIndex(1, 1, 2, 3)
        assertEquals(1, idx["vType"])
        assertEquals(1, idx["lineIdx"])
        assertEquals(2, idx["wordIdx"])
        assertEquals(3, idx["sylIdx"])
        assertEquals(4, idx["depth"])
        assertEquals("V1.L2.W3.S4", idx["formatted"])
    }

    @Test
    fun testCreateHierarchicalIndexWithOrders() {
        val idx = createHierarchicalIndex(2, 0, null, null, 1.5f, 2.0f, 0f)
        assertEquals(2, idx["vType"])
        assertEquals(0, idx["lineIdx"])
        assertEquals(1.5f, idx["lineOrder"])
        assertEquals(2.0f, idx["wordOrder"])
    }

    @Test
    fun testParseHierarchicalIndexVerseLine() {
        val idx = parseHierarchicalIndex("V2.L3")
        assertNotNull(idx)
        assertEquals(2, idx["vType"])
        assertEquals(2, idx["lineIdx"])  // L3 = index 2 (0-based)
        assertEquals(null, idx["wordIdx"])
        assertEquals(2, idx["depth"])
    }

    @Test
    fun testParseHierarchicalIndexFull() {
        val idx = parseHierarchicalIndex("V1.L2.W3.S4")
        assertNotNull(idx)
        assertEquals(1, idx["vType"])
        assertEquals(1, idx["lineIdx"])  // L2 = index 1
        assertEquals(2, idx["wordIdx"])  // W3 = index 2
        assertEquals(3, idx["sylIdx"])   // S4 = index 3
        assertEquals(4, idx["depth"])
    }

    @Test
    fun testParseHierarchicalIndexInvalid() {
        val idx = parseHierarchicalIndex("invalid")
        assertEquals(null, idx)
    }

    @Test
    fun testParseHierarchicalIndexCaseInsensitive() {
        val idx = parseHierarchicalIndex("v1.l2.w3.s4")
        assertNotNull(idx)
        assertEquals(1, idx["vType"])
    }

    @Test
    fun testCreateWordState() {
        val word = createWordState("hello")
        assertEquals("hello", word["text"])
        assertEquals(1f, word["order"])
        assertTrue((word["sylBounds"] as Array<*>).isNotEmpty())
        assertTrue((word["hue"] as Int) in 0..360)
    }

    @Test
    fun testCreateWordStateWithOrder() {
        val word = createWordState("world", 2.5f)
        assertEquals("world", word["text"])
        assertEquals(2.5f, word["order"])
    }

    @Test
    fun testCreateWordStateSyllables() {
        val word = createWordState("raging")
        val syllables = word["syllables"]
        assertNotNull(syllables)

        // Access first syllable properties through dynamic word object
        val firstText = word["syllables"].unsafeCast<Array<dynamic>>()[0]["text"]
        val firstStart = word["syllables"].unsafeCast<Array<dynamic>>()[0]["startOffset"]
        val firstEnd = word["syllables"].unsafeCast<Array<dynamic>>()[0]["endOffset"]
        val firstHue = word["syllables"].unsafeCast<Array<dynamic>>()[0]["hue"]

        assertNotNull(firstText)
        assertNotNull(firstStart)
        assertNotNull(firstEnd)
        assertNotNull(firstHue)
    }

    @Test
    fun testCursorToIndex() {
        val idx = cursorToIndex("hello", 2, 3, 1, 2)
        assertEquals(1, idx["vType"])
        assertEquals(2, idx["lineIdx"])
        assertEquals(3, idx["wordIdx"])
        assertNotNull(idx["sylIdx"])
        assertEquals(4, idx["depth"])
        assertTrue((idx["formatted"] as String).startsWith("V1.L3.W4.S"))
    }

    @Test
    fun testCursorToIndexWithOrders() {
        val idx = cursorToIndex("world", 0, 1, 2, 0, 1.5f, 2.0f)
        assertEquals(2, idx["vType"])
        assertEquals(0, idx["lineIdx"])
        assertEquals(1, idx["wordIdx"])
    }

    @Test
    fun testHierarchicalInsertOrder() {
        val order = hierarchicalInsertOrder(1.0f, 2.0f)
        assertEquals(1.5f, order)

        val order2 = hierarchicalInsertOrder(1.5f, 2.0f)
        assertEquals(1.75f, order2)
    }

    @Test
    fun testCursorStateSylIdx() {
        val cursor = createCursorState("raging", 0)
        assertNotNull(cursor["sylIdx"])
        assertEquals(0, cursor["sylIdx"], "Cursor at start should be in syllable 0")
    }

    @Test
    fun testCursorStateSylIdxMid() {
        val cursor = createCursorState("hello", 3)
        assertTrue((cursor["sylIdx"] as Int) >= 0)
    }
}
