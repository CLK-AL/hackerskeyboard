package org.pocketworkstation.unikey

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
}
