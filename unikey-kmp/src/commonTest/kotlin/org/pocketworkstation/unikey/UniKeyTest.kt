package org.pocketworkstation.unikey

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
        val patach = Nikud.forIpa("a")
        assertTrue(patach.isNotEmpty())
        assertTrue(patach.any { it == Nikud.PATACH })
    }

    @Test
    fun testApplyNikud() {
        val result = Nikud.apply('\u05D1', "a") // bet + patach
        assertEquals("\u05D1\u05B7", result)
    }

    @Test
    fun testGutturalWithSchwa() {
        // Gutturals use hataf instead of shva
        val result = Nikud.apply('\u05D0', "\u0259") // alef + schwa
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
        val bet = Bgdkpt.BET
        val withDagesh = bet.getSound(hasDagesh = true)
        assertEquals("b", withDagesh.ipa)
        assertEquals("b", withDagesh.en)
    }

    @Test
    fun testBetWithoutDagesh() {
        val bet = Bgdkpt.BET
        val withoutDagesh = bet.getSound(hasDagesh = false)
        assertEquals("v", withoutDagesh.ipa)
        assertEquals("v", withoutDagesh.en)
    }

    @Test
    fun testClassicalPronunciation() {
        val tav = Bgdkpt.TAV
        assertNotNull(tav.classical)
        val classical = tav.getSound(hasDagesh = false, useClassical = true)
        assertEquals("\u03B8", classical.ipa) // theta
    }

    @Test
    fun testModernDistinct() {
        // Only BET, KAF, PE have distinct sounds in modern Hebrew
        val distinct = Bgdkpt.modernDistinct
        assertTrue(distinct.contains(Bgdkpt.BET))
        assertTrue(distinct.contains(Bgdkpt.KAF))
        assertTrue(distinct.contains(Bgdkpt.PE))
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
