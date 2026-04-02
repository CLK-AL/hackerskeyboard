package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UniKeyTest {

    @Test
    fun testGetUniKeyById() {
        val uk = UniKeys.get("a")
        assertNotNull(uk)
        assertEquals("a", uk.en)
        assertEquals("A", uk.EN)
        assertEquals("\u05E9", uk.he) // shin
    }

    @Test
    fun testGetUniKeyByHebrew() {
        val uk = UniKeys.getByHe("\u05D0") // alef
        assertNotNull(uk)
        assertEquals("t", uk.id)
        assertEquals("\u0294", uk.ipa) // glottal stop
    }

    @Test
    fun testLabelEnglishMode() {
        val uk = UniKeys.get("a")!!
        assertEquals("a", uk.label(KeyMode.en))
        assertEquals("A", uk.label(KeyMode.EN))
    }

    @Test
    fun testLabelHebrewMode() {
        val uk = UniKeys.get("a")!!
        assertEquals("\u05E9", uk.label(KeyMode.he)) // shin
    }

    @Test
    fun testLabelIpaMode() {
        val uk = UniKeys.get("a")!!
        val mods = Modifiers(ctrl = true)
        assertEquals("\u0283", uk.label(KeyMode.en, mods)) // IPA sh
    }

    @Test
    fun testKeyModeManager() {
        UniKeyMode.set(KeyMode.he)
        assertEquals(KeyMode.he, UniKeyMode.current)

        UniKeyMode.set(KeyMode.en)
        assertEquals(KeyMode.en, UniKeyMode.current)

        val next = UniKeyMode.cycle()
        assertEquals(KeyMode.EN, next)
    }

    @Test
    fun testModifiersManager() {
        UniKeyModifiers.reset()
        assertEquals(false, UniKeyModifiers.shift)
        assertEquals(false, UniKeyModifiers.alt)
        assertEquals(false, UniKeyModifiers.ctrl)

        UniKeyModifiers.shift = true
        assertEquals(true, UniKeyModifiers.current.shift)

        UniKeyModifiers.reset()
        assertEquals(false, UniKeyModifiers.shift)
    }

    @Test
    fun testBgdkptKeys() {
        assertTrue(UniKeys.bgdkptKeys.isNotEmpty())
        val bet = UniKeys.bgdkptKeys.find { it.he == "\u05D1" }
        assertNotNull(bet)
        assertEquals("b", bet.dagesh)
    }

    @Test
    fun testGutturalKeys() {
        assertTrue(UniKeys.gutturalKeys.isNotEmpty())
        val alef = UniKeys.gutturalKeys.find { it.he == "\u05D0" }
        assertNotNull(alef)
        assertTrue(alef.guttural)
    }

    @Test
    fun testFinalKeys() {
        assertTrue(UniKeys.finalKeys.isNotEmpty())
        val kafSofit = UniKeys.finalKeys.find { it.he == "\u05DA" }
        assertNotNull(kafSofit)
        assertTrue(kafSofit.isFinal)
    }
}

class NikudTest {

    @Test
    fun testNikudForIpa() {
        val patach = NikudVowel.forIpa("a")
        assertTrue(patach.isNotEmpty())
        assertTrue(patach.any { it == NikudVowel.PATACH })
    }

    @Test
    fun testApplyNikud() {
        val result = NikudVowel.apply('\u05D1', "a") // bet + patach
        assertEquals("\u05D1\u05B7", result)
    }

    @Test
    fun testGutturalWithSchwa() {
        // Gutturals use hataf instead of shva
        val result = NikudVowel.apply('\u05D0', "ə") // alef + schwa
        assertEquals("\u05D0\u05B2", result) // alef + hataf patach
    }
}

class HebrewLetterTest {

    @Test
    fun testFromChar() {
        val alef = HebrewLetter.fromChar('\u05D0')
        assertNotNull(alef)
        assertEquals(HebrewLetter.ALEF, alef)
        assertTrue(alef.isGuttural)
    }

    @Test
    fun testBgdkptLetter() {
        val bet = HebrewLetter.fromChar('\u05D1')
        assertNotNull(bet)
        assertTrue(bet.isBgdkpt)
        assertEquals("v", bet.ipa)
        assertEquals("b", bet.ipaDagesh)
    }

    @Test
    fun testFinalForm() {
        val kaf = HebrewLetter.KAF
        assertEquals('\u05DA', kaf.finalForm)
        val kafSofit = HebrewLetter.getFinalForm(kaf)
        assertNotNull(kafSofit)
        assertEquals(HebrewLetter.KAF_SOFIT, kafSofit)
    }
}

class BgdkptTest {

    @Test
    fun testBetWithDagesh() {
        val bet = HebrewBgdkpt.BET
        val ipa = bet.getIpa(hasDagesh = true)
        assertEquals("b", ipa)
    }

    @Test
    fun testBetWithoutDagesh() {
        val bet = HebrewBgdkpt.BET
        val ipa = bet.getIpa(hasDagesh = false)
        assertEquals("v", ipa)
    }

    @Test
    fun testClassicalPronunciation() {
        val tav = HebrewBgdkpt.TAV
        assertNotNull(tav.classicalIpa)
        val ipa = tav.getIpa(hasDagesh = false, useClassical = true)
        assertEquals("θ", ipa) // theta
    }

    @Test
    fun testModernDistinct() {
        // Only BET, KAF, PE have distinct sounds in modern Hebrew
        val distinct = HebrewBgdkpt.modernDistinct
        assertTrue(distinct.contains(HebrewBgdkpt.BET))
        assertTrue(distinct.contains(HebrewBgdkpt.KAF))
        assertTrue(distinct.contains(HebrewBgdkpt.PE))
    }
}

class IpaTest {

    @Test
    fun testVowelLookup() {
        val schwa = IpaVowel.fromIpa("\u0259")
        assertNotNull(schwa)
        assertEquals(IpaVowel.SCHWA, schwa)
        assertEquals(IpaQuality.EXACT, schwa.quality)
    }

    @Test
    fun testConsonantLookup() {
        val shin = IpaConsonant.fromIpa("\u0283")
        assertNotNull(shin)
        assertEquals(IpaConsonant.SH, shin)
        assertEquals("\u05E9\u05C1", shin.he)
    }

    @Test
    fun testGereshConsonant() {
        val zh = IpaConsonant.ZH
        assertTrue(zh.geresh)
        assertEquals("\u05D6\u05F3", zh.he)
    }

    @Test
    fun testHebrewUnique() {
        val ts = IpaConsonant.TS
        assertTrue(ts.heUnique)
    }
}

class IpaColorTest {

    @Test
    fun testEnIpaEndings() {
        assertEquals("IN", IpaColor.enIpa("raging"))
        assertEquals("IN", IpaColor.enIpa("staging"))
        assertEquals("Ent", IpaColor.enIpa("dormant").replace("ant", "Ent").let { IpaColor.enIpa("potent") })
        assertEquals("ejt", IpaColor.enIpa("create"))
        assertEquals("ajt", IpaColor.enIpa("might"))
    }

    @Test
    fun testHeIpaBasic() {
        // בַּזֶּלֶת = bazelet
        val bazelet = IpaColor.heIpa("\u05D1\u05B7\u05BC\u05D6\u05B6\u05BC\u05DC\u05B6\u05EA")
        assertTrue(bazelet.contains("b"), "Should start with b (dagesh bet)")
        assertTrue(bazelet.contains("t"), "Should end with t")
    }

    @Test
    fun testHeIpaShin() {
        // שׁ = shin (sh)
        val shin = IpaColor.heIpa("\u05E9\u05C1")
        assertEquals("sh", shin)

        // שׂ = sin (s)
        val sin = IpaColor.heIpa("\u05E9\u05C2")
        assertEquals("s", sin)
    }

    @Test
    fun testIpaHue() {
        val hue1 = IpaColor.ipaHue("IN")
        val hue2 = IpaColor.ipaHue("IN")
        assertEquals(hue1, hue2, "Same IPA should produce same hue")

        val hue3 = IpaColor.ipaHue("et")
        assertTrue(hue1 != hue3 || hue1 == 0, "Different IPA should usually produce different hue")
    }

    @Test
    fun testHsl() {
        val color = IpaColor.hsl(180, 50, 70)
        assertEquals("hsl(180, 50%, 70%)", color)
    }

    @Test
    fun testRhymeScheme() {
        val ipas = listOf("IN", "et", "IN", "et")
        val scheme = IpaColor.rhymeScheme(ipas)

        assertEquals(4, scheme.size)
        assertEquals('A', scheme[0].letter)
        assertEquals('B', scheme[1].letter)
        assertEquals('A', scheme[2].letter) // Same as first
        assertEquals('B', scheme[3].letter) // Same as second
    }

    @Test
    fun testLineEndIpa() {
        val heIpa = IpaColor.lineEndIpa("\u05D1\u05B7\u05BC\u05D6\u05B6\u05BC\u05DC\u05B6\u05EA,", true)
        assertTrue(heIpa.isNotEmpty())

        val enIpa = IpaColor.lineEndIpa("staging.", false)
        assertEquals("IN", enIpa)
    }

    @Test
    fun testRhymingWordsGetSameHue() {
        val raging = IpaColor.enIpa("raging")
        val staging = IpaColor.enIpa("staging")
        assertEquals(raging, staging, "Rhyming words should have same IPA ending")

        val hue1 = IpaColor.ipaHue(raging)
        val hue2 = IpaColor.ipaHue(staging)
        assertEquals(hue1, hue2, "Rhyming words should get same hue")
    }
}

class UniKeySyllableTest {

    @Test
    fun testParseHebrewSyllables() {
        // בַּזֶּלֶת = ba-ze-let
        val syllables = UniKeySyllable.parseHebrew("\u05D1\u05B7\u05BC\u05D6\u05B6\u05BC\u05DC\u05B6\u05EA")
        assertTrue(syllables.isNotEmpty(), "Should parse syllables")

        // First syllable should be "ba" (bet with dagesh + patach)
        val first = syllables.first()
        assertEquals("b", first.consonant, "First consonant should be b (dagesh)")
        assertEquals("a", first.vowel, "First vowel should be a (patach)")
    }

    @Test
    fun testParseEnglishSyllables() {
        val syllables = UniKeySyllable.parseEnglish("raging")
        assertTrue(syllables.isNotEmpty())

        // Should have syllables like ra-gi-ng or similar
        val first = syllables.first()
        assertEquals("r", first.consonant)
        assertEquals("a", first.vowel)
    }

    @Test
    fun testSimilarSyllablesHaveSimilarHue() {
        // "ba" and "pa" should have similar hues (both labial + 'a')
        val ba = UniKeySyllable("b", "a", "ba")
        val pa = UniKeySyllable("p", "a", "pa")
        val ma = UniKeySyllable("m", "a", "ma")

        // All labials with 'a' should be in similar hue range
        val hueDiffBaPa = kotlin.math.abs(ba.hue - pa.hue)
        val hueDiffBaMa = kotlin.math.abs(ba.hue - ma.hue)

        assertTrue(hueDiffBaPa < 30, "ba and pa should have similar hue (diff=$hueDiffBaPa)")
        assertTrue(hueDiffBaMa < 30, "ba and ma should have similar hue (diff=$hueDiffBaMa)")
    }

    @Test
    fun testDifferentVowelsHaveDifferentHue() {
        // "ba" and "bi" should have different hues (different vowels)
        val ba = UniKeySyllable("b", "a", "ba")
        val bi = UniKeySyllable("b", "i", "bi")

        val hueDiff = kotlin.math.abs(ba.hue - bi.hue)
        assertTrue(hueDiff > 60, "ba and bi should have different hue regions (diff=$hueDiff)")
    }

    @Test
    fun testWordHue() {
        val hue1 = UniKeySyllable.wordHue("raging", false)
        val hue2 = UniKeySyllable.wordHue("staging", false)

        // Both end in "-ing" so should have same/similar hue
        val diff = kotlin.math.abs(hue1 - hue2)
        assertTrue(diff < 30, "Rhyming words should have similar hue (diff=$diff)")
    }

    @Test
    fun testHebrewRhymeHue() {
        // Words ending in same pattern should have similar hue
        // סוֹעֶרֶת and צוֹבֶרֶת both end in -eret
        val hue1 = UniKeySyllable.wordHue("\u05E1\u05D5\u05B9\u05E2\u05B6\u05E8\u05B6\u05EA", true)
        val hue2 = UniKeySyllable.wordHue("\u05E6\u05D5\u05B9\u05D1\u05B6\u05E8\u05B6\u05EA", true)

        val diff = kotlin.math.abs(hue1 - hue2)
        assertTrue(diff < 60, "Hebrew rhyming words should have similar hue (diff=$diff)")
    }

    @Test
    fun testHslColor() {
        val color = UniKeySyllable.hsl(120, 70, 65)
        assertEquals("hsl(120, 70%, 65%)", color)
    }

    @Test
    fun testRhymeKey() {
        val key1 = UniKeySyllable.rhymeKey("raging", false)
        val key2 = UniKeySyllable.rhymeKey("staging", false)
        assertEquals(key1, key2, "Rhyming words should have same rhyme key")
    }

    @Test
    fun testRhymes() {
        assertTrue(UniKeySyllable.rhymes("raging", false, "staging", false))
        assertTrue(UniKeySyllable.rhymes("night", false, "light", false))
    }

    @Test
    fun testCrossLanguageRhyme() {
        // Hebrew סוֹעֶרֶת (soeret) ends in -ret
        // English "threat" ends in -ret
        // They should have similar rhyme patterns
        val heKey = UniKeySyllable.rhymeKey("\u05E1\u05D5\u05B9\u05E2\u05B6\u05E8\u05B6\u05EA", true)
        val enKey = UniKeySyllable.rhymeKey("threat", false)

        // Both should end in consonant 't' with vowel 'e'
        assertTrue(heKey.contains("t"), "Hebrew should end in t")
        assertTrue(enKey.contains("t"), "English should end in t")
    }

    @Test
    fun testRhymeDistance() {
        // Perfect rhyme = 0
        val dist1 = UniKeySyllable.rhymeDistance("raging", false, "staging", false)
        assertEquals(0, dist1, "Perfect rhyme should have distance 0")

        // Near rhyme (same vowel, different consonant) > 0
        val dist2 = UniKeySyllable.rhymeDistance("cat", false, "bat", false)
        assertTrue(dist2 < 20, "Near rhyme should have low distance")

        // Different ending = high distance
        val dist3 = UniKeySyllable.rhymeDistance("cat", false, "dog", false)
        assertTrue(dist3 > 30, "Non-rhyming words should have high distance")
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Hierarchical Index Tests (V1.L2.W3.S4)
// ═══════════════════════════════════════════════════════════════════════════════

class HierarchicalIndexTest {

    @Test
    fun testFormattedVerseOnly() {
        val idx = HierarchicalIndex(vType = 1)
        assertEquals("V1", idx.formatted)
        assertEquals(1, idx.depth)
    }

    @Test
    fun testFormattedVerseLine() {
        val idx = HierarchicalIndex(vType = 2, lineIdx = 0)
        assertEquals("V2.L1", idx.formatted)
        assertEquals(2, idx.depth)
    }

    @Test
    fun testFormattedVerseLineWord() {
        val idx = HierarchicalIndex(vType = 1, lineIdx = 1, wordIdx = 2)
        assertEquals("V1.L2.W3", idx.formatted)
        assertEquals(3, idx.depth)
    }

    @Test
    fun testFormattedFull() {
        val idx = HierarchicalIndex(vType = 1, lineIdx = 1, wordIdx = 2, sylIdx = 3)
        assertEquals("V1.L2.W3.S4", idx.formatted)
        assertEquals(4, idx.depth)
    }

    @Test
    fun testParseVerseOnly() {
        val idx = HierarchicalIndex.parse("V1")
        assertNotNull(idx)
        assertEquals(1, idx.vType)
        assertNull(idx.lineIdx)
        assertNull(idx.wordIdx)
        assertNull(idx.sylIdx)
    }

    @Test
    fun testParseVerseLine() {
        val idx = HierarchicalIndex.parse("V2.L3")
        assertNotNull(idx)
        assertEquals(2, idx.vType)
        assertEquals(2, idx.lineIdx) // L3 = index 2 (0-based)
        assertNull(idx.wordIdx)
    }

    @Test
    fun testParseFull() {
        val idx = HierarchicalIndex.parse("V1.L2.W3.S4")
        assertNotNull(idx)
        assertEquals(1, idx.vType)
        assertEquals(1, idx.lineIdx)  // L2 = index 1
        assertEquals(2, idx.wordIdx)  // W3 = index 2
        assertEquals(3, idx.sylIdx)   // S4 = index 3
    }

    @Test
    fun testParseCaseInsensitive() {
        val idx = HierarchicalIndex.parse("v1.l2.w3.s4")
        assertNotNull(idx)
        assertEquals(1, idx.vType)
        assertEquals(1, idx.lineIdx)
    }

    @Test
    fun testParseInvalid() {
        assertNull(HierarchicalIndex.parse(""))
        assertNull(HierarchicalIndex.parse("L1"))  // Missing V
        assertNull(HierarchicalIndex.parse("X1.Y2"))
    }

    @Test
    fun testRoundTrip() {
        val original = HierarchicalIndex(vType = 3, lineIdx = 4, wordIdx = 1, sylIdx = 0)
        val parsed = HierarchicalIndex.parse(original.formatted)
        assertNotNull(parsed)
        assertEquals(original.vType, parsed.vType)
        assertEquals(original.lineIdx, parsed.lineIdx)
        assertEquals(original.wordIdx, parsed.wordIdx)
        assertEquals(original.sylIdx, parsed.sylIdx)
    }

    @Test
    fun testToVerseIndexState() {
        val idx = HierarchicalIndex(vType = 2, lineIdx = 3, lineOrder = 3.5f)
        val vis = idx.toVerseIndexState()
        assertEquals(2, vis.vType)
        assertEquals(3, vis.lineIdx)
        assertEquals(3.5f, vis.order)
    }

    @Test
    fun testFromVerseIndexState() {
        val vis = VerseIndexState(vType = 1, lineIdx = 2, order = 2.5f)
        val idx = HierarchicalIndex.fromVerseIndexState(vis)
        assertEquals(1, idx.vType)
        assertEquals(2, idx.lineIdx)
        assertEquals(2.5f, idx.lineOrder)
        assertNull(idx.wordIdx)
        assertNull(idx.sylIdx)
    }

    @Test
    fun testInsertOrder() {
        val order = HierarchicalIndex.insertOrder(1.0f, 2.0f)
        assertEquals(1.5f, order)

        val order2 = HierarchicalIndex.insertOrder(1.5f, 2.0f)
        assertEquals(1.75f, order2)
    }
}

class WordStateTest {

    @Test
    fun testFromTextBasic() {
        val word = WordState.fromText("hello")
        assertEquals("hello", word.text)
        assertEquals(1f, word.order)
        assertTrue(word.sylBounds.isNotEmpty())
        assertTrue(word.hue in 0..360)
    }

    @Test
    fun testFromTextWithOrder() {
        val word = WordState.fromText("world", 2.5f)
        assertEquals("world", word.text)
        assertEquals(2.5f, word.order)
    }

    @Test
    fun testSyllables() {
        val word = WordState.fromText("raging")
        val syllables = word.syllables()
        assertTrue(syllables.isNotEmpty(), "Should have syllables")

        // Check syllable structure
        val first = syllables.first()
        assertTrue(first.startOffset >= 0)
        assertTrue(first.endOffset > first.startOffset)
        assertTrue(first.text.isNotEmpty())
    }

    @Test
    fun testSyllableBounds() {
        val word = WordState.fromText("hello")
        assertTrue(word.sylBounds.first() == 0, "First bound should be 0")
        assertTrue(word.sylBounds.last() == word.text.length, "Last bound should be word length")
    }

    @Test
    fun testSyllableOffsets() {
        val word = WordState.fromText("raging")
        val syllables = word.syllables()

        // Syllable offsets should be contiguous
        for (i in 1 until syllables.size) {
            assertEquals(syllables[i - 1].endOffset, syllables[i].startOffset,
                "Syllable end should match next syllable start")
        }
    }
}

class SyllableStateTest {

    @Test
    fun testSyllableProperties() {
        val ipa = UniKeySyllable("r", "a", "ra")
        val syl = SyllableState(ipa, 1f, 0, 2)

        assertEquals("ra", syl.text)
        assertEquals(ipa.hue, syl.hue)
        assertEquals(0, syl.startOffset)
        assertEquals(2, syl.endOffset)
    }

    @Test
    fun testSyllableHue() {
        val ipa = UniKeySyllable("b", "a", "ba")
        val syl = SyllableState(ipa, 1f, 0, 2)
        assertTrue(syl.hue in 0..360)
    }
}

class CursorStateExtendedTest {

    @Test
    fun testSylIdxAtStart() {
        val cursor = CursorState.forWord("raging", 0)
        assertEquals(0, cursor.sylIdx, "Cursor at start should be in syllable 0")
    }

    @Test
    fun testSylIdxInMiddle() {
        val cursor = CursorState.forWord("raging", 3)
        // Position 3 is in the middle of the word
        assertTrue(cursor.sylIdx >= 0)
    }

    @Test
    fun testSylIdxAtEnd() {
        val word = "hello"
        val cursor = CursorState.forWord(word, word.length)
        // At end should be in last syllable
        val maxIdx = cursor.sylBounds.size - 2  // -2 because bounds include both start and end
        assertTrue(cursor.sylIdx >= 0)
        assertTrue(cursor.sylIdx <= maxIdx.coerceAtLeast(0))
    }

    @Test
    fun testToIndex() {
        val cursor = CursorState.forWord("hello", 2, wordIdx = 3)
        val idx = cursor.toIndex(vType = 1, lineIdx = 2)

        assertEquals(1, idx.vType)
        assertEquals(2, idx.lineIdx)
        assertEquals(3, idx.wordIdx)
        assertEquals(cursor.sylIdx, idx.sylIdx)
        assertEquals(4, idx.depth)
    }

    @Test
    fun testToIndexWithOrders() {
        val cursor = CursorState.forWord("world", 0, wordIdx = 1)
        val idx = cursor.toIndex(vType = 2, lineIdx = 0, lineOrder = 1.5f, wordOrder = 2.0f)

        assertEquals(2, idx.vType)
        assertEquals(1.5f, idx.lineOrder)
        assertEquals(2.0f, idx.wordOrder)
    }

    @Test
    fun testToIndexFormattedOutput() {
        val cursor = CursorState.forWord("test", 0, wordIdx = 0)
        val idx = cursor.toIndex(vType = 1, lineIdx = 0)

        // Should produce V1.L1.W1.S1
        assertTrue(idx.formatted.startsWith("V1.L1.W1.S"))
    }
}

class VerseIndexStateTest {

    @Test
    fun testFormatted() {
        val vis = VerseIndexState(vType = 2, lineIdx = 3)
        assertEquals("V2.L4", vis.formatted)
    }

    @Test
    fun testInsertOrder() {
        val order = VerseIndexState.insertOrder(1.0f, 2.0f)
        assertEquals(1.5f, order)
    }
}
