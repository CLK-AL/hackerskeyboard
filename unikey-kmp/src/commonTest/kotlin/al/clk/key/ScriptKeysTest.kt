package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CurrencyTest {

    @Test
    fun testCurrencyForRegion() {
        assertEquals(Currency.DOLLAR, Currency.forRegion("en"))
        assertEquals(Currency.DOLLAR, Currency.forRegion("en-us"))
        assertEquals(Currency.POUND, Currency.forRegion("en-gb"))
        assertEquals(Currency.EURO, Currency.forRegion("de"))
        assertEquals(Currency.EURO, Currency.forRegion("fr"))
        assertEquals(Currency.RUPEE, Currency.forRegion("hi"))
        assertEquals(Currency.SHEKEL, Currency.forRegion("he"))
        assertEquals(Currency.YEN, Currency.forRegion("ja"))
        assertEquals(Currency.YEN, Currency.forRegion("zh"))
        assertEquals(Currency.WON, Currency.forRegion("ko"))
        assertEquals(Currency.RUBLE, Currency.forRegion("ru"))
        assertEquals(Currency.LIRA, Currency.forRegion("tr"))
        assertEquals(Currency.ZLOTY, Currency.forRegion("pl"))
        assertEquals(Currency.KRONE, Currency.forRegion("da"))
        assertEquals(Currency.KRONE, Currency.forRegion("no"))
        assertEquals(Currency.KRONE, Currency.forRegion("sv"))
        assertEquals(Currency.REAL, Currency.forRegion("pt-br"))
        assertEquals(Currency.RINGGIT, Currency.forRegion("ms"))
        assertEquals(Currency.SHILLING, Currency.forRegion("sw"))
    }

    @Test
    fun testCurrencyDefaultsToDollar() {
        assertEquals(Currency.DOLLAR, Currency.forRegion("unknown"))
        assertEquals(Currency.DOLLAR, Currency.forRegion(""))
    }

    @Test
    fun testCurrencySymbols() {
        assertEquals("$", Currency.DOLLAR.symbol)
        assertEquals("£", Currency.POUND.symbol)
        assertEquals("€", Currency.EURO.symbol)
        assertEquals("₹", Currency.RUPEE.symbol)
        assertEquals("₪", Currency.SHEKEL.symbol)
        assertEquals("¥", Currency.YEN.symbol)
        assertEquals("₩", Currency.WON.symbol)
        assertEquals("₽", Currency.RUBLE.symbol)
        assertEquals("₺", Currency.LIRA.symbol)
        assertEquals("zł", Currency.ZLOTY.symbol)
        assertEquals("kr", Currency.KRONE.symbol)
        assertEquals("R$", Currency.REAL.symbol)
        assertEquals("RM", Currency.RINGGIT.symbol)
        assertEquals("TSh", Currency.SHILLING.symbol)
    }

    @Test
    fun testAllCurrenciesHaveDisplayName() {
        Currency.entries.forEach { currency ->
            assertTrue(currency.displayName.isNotEmpty(), "${currency.name} should have displayName")
        }
    }

    @Test
    fun testAllCurrenciesHaveRegions() {
        Currency.entries.forEach { currency ->
            assertTrue(currency.regions.isNotEmpty(), "${currency.name} should have regions")
        }
    }
}

class HebrewLetterKeyTest {

    @Test
    fun testHebrewLetterFromQwerty() {
        val alef = HebrewLetter.fromQwerty("t")
        assertNotNull(alef)
        assertEquals(HebrewLetter.ALEF, alef)
        assertEquals("א", alef.char)
        assertEquals("ʔ", alef.ipa)
    }

    @Test
    fun testHebrewLetterFromChar() {
        val bet = HebrewLetter.fromChar('ב')
        assertNotNull(bet)
        assertEquals(HebrewLetter.BET, bet)
        assertEquals("v", bet.ipa)
        assertTrue(bet.isBgdkpt)
        assertEquals("b", bet.ipaDagesh)
    }

    @Test
    fun testHebrewFinalLetters() {
        assertTrue(HebrewLetter.KAF_SOFIT.isFinalForm)
        assertTrue(HebrewLetter.MEM_SOFIT.isFinalForm)
        assertTrue(HebrewLetter.NUN_SOFIT.isFinalForm)
        assertTrue(HebrewLetter.PE_SOFIT.isFinalForm)
        assertTrue(HebrewLetter.TSADI_SOFIT.isFinalForm)
    }

    @Test
    fun testHebrewBgdkptLetters() {
        assertTrue(HebrewLetter.BET.isBgdkpt)
        assertTrue(HebrewLetter.KAF.isBgdkpt)
        assertTrue(HebrewLetter.PE.isBgdkpt)
    }

    @Test
    fun testAllHebrewLettersImplementILayoutKey() {
        HebrewLetter.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}

class NikudVowelTest {

    @Test
    fun testNikudVowelFromCodePoint() {
        val patach = NikudVowel.fromCodePoint(0x05B7)
        assertNotNull(patach)
        assertEquals(NikudVowel.PATACH, patach)
        assertEquals("a", patach.ipa)
    }

    @Test
    fun testNikudVowelIpa() {
        assertEquals("ə", NikudVowel.SHVA.ipa)
        assertEquals("i", NikudVowel.CHIRIK.ipa)
        assertEquals("e", NikudVowel.TZERE.ipa)
        assertEquals("a", NikudVowel.PATACH.ipa)
        assertEquals("o", NikudVowel.CHOLAM.ipa)
        assertEquals("u", NikudVowel.KUBUTZ.ipa)
    }

    @Test
    fun testAllNikudVowelsImplementILayoutKey() {
        NikudVowel.entries.forEach { vowel ->
            assertTrue(vowel.char.isNotEmpty(), "${vowel.name} should have char")
            assertTrue(vowel.ipa.isNotEmpty(), "${vowel.name} should have ipa")
            assertTrue(vowel.displayName.isNotEmpty(), "${vowel.name} should have displayName")
        }
    }
}

class ArabicLetterKeyTest {

    @Test
    fun testArabicLetterFromQwerty() {
        val ba = ArabicLetter.fromQwerty('f')
        assertNotNull(ba)
        assertEquals(ArabicLetter.BA, ba)
        assertEquals("ب", ba.char)
        assertEquals("b", ba.ipa)
    }

    @Test
    fun testArabicEmphaticConsonants() {
        assertEquals("sˤ", ArabicLetter.SAD.ipa)
        assertEquals("dˤ", ArabicLetter.DAD.ipa)
        assertEquals("tˤ", ArabicLetter.TAA.ipa)
        assertEquals("ðˤ", ArabicLetter.ZAA.ipa)
    }

    @Test
    fun testArabicPharyngealConsonants() {
        assertEquals("ħ", ArabicLetter.HA.ipa)
        assertEquals("ʕ", ArabicLetter.AIN.ipa)
    }

    @Test
    fun testAllArabicLettersImplementILayoutKey() {
        ArabicLetter.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}

class GreekKeyTest {

    @Test
    fun testGreekKeyFromQwerty() {
        val alpha = GreekKey.fromQwerty("a")
        assertNotNull(alpha)
        assertEquals(GreekKey.ALPHA, alpha)
        assertEquals("α", alpha.char)
        assertEquals("a", alpha.ipa)
    }

    @Test
    fun testGreekVowels() {
        assertEquals("a", GreekKey.ALPHA.ipa)
        assertEquals("e", GreekKey.EPSILON.ipa)
        assertEquals("i", GreekKey.IOTA.ipa)
        assertEquals("o", GreekKey.OMICRON.ipa)
        assertEquals("i", GreekKey.UPSILON.ipa) // Modern Greek
    }

    @Test
    fun testAllGreekKeysImplementILayoutKey() {
        GreekKey.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}

class CyrillicKeyTest {

    @Test
    fun testCyrillicKeyFromQwerty() {
        val a = CyrillicKey.fromQwerty("f")
        assertNotNull(a)
        assertEquals(CyrillicKey.A, a)
        assertEquals("а", a.char)
        assertEquals("a", a.ipa)
    }

    @Test
    fun testCyrillicSoftSign() {
        val softSign = CyrillicKey.SOFT_SIGN
        assertEquals("ь", softSign.char)
        assertEquals("", softSign.ipa) // No sound
    }

    @Test
    fun testCyrillicHardSign() {
        val hardSign = CyrillicKey.HARD_SIGN
        assertEquals("ъ", hardSign.char)
        assertEquals("", hardSign.ipa) // No sound
    }

    @Test
    fun testAllCyrillicKeysImplementILayoutKey() {
        CyrillicKey.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}

class HangulKeyTest {

    @Test
    fun testHangulInitialFromCode() {
        val giyeok = HangulInitial.fromCode(0)
        assertNotNull(giyeok)
        assertEquals(HangulInitial.GIYEOK, giyeok)
        assertEquals("ㄱ", giyeok.char)
        assertEquals("k", giyeok.ipa)
    }

    @Test
    fun testHangulMedialFromCode() {
        val a = HangulMedial.fromCode(0)
        assertNotNull(a)
        assertEquals(HangulMedial.A, a)
        assertEquals("ㅏ", a.char)
        assertEquals("a", a.ipa)
    }

    @Test
    fun testHangulTenseConsonants() {
        assertEquals("k͈", HangulInitial.SSANG_GIYEOK.ipa)
        assertEquals("t͈", HangulInitial.SSANG_DIGEUT.ipa)
        assertEquals("p͈", HangulInitial.SSANG_BIEUP.ipa)
        assertEquals("s͈", HangulInitial.SSANG_SIOT.ipa)
    }

    @Test
    fun testHangulDiphthongs() {
        assertEquals("ja", HangulMedial.YA.ipa)
        assertEquals("jʌ", HangulMedial.YEO.ipa)
        assertEquals("wa", HangulMedial.WA.ipa)
    }

    @Test
    fun testAllHangulKeysImplementILayoutKey() {
        HangulInitial.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
        HangulMedial.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}

class HiraganaKeyTest {

    @Test
    fun testHiraganaKeyFromRomaji() {
        val a = HiraganaKey.fromRomaji("a")
        assertNotNull(a)
        assertEquals(HiraganaKey.A, a)
        assertEquals("あ", a.char)
        assertEquals("a", a.ipa)
    }

    @Test
    fun testHiraganaVowels() {
        assertEquals("a", HiraganaKey.A.ipa)
        assertEquals("i", HiraganaKey.I.ipa)
        assertEquals("ɯ", HiraganaKey.U.ipa) // Japanese u is unrounded
        assertEquals("e", HiraganaKey.E.ipa)
        assertEquals("o", HiraganaKey.O.ipa)
    }

    @Test
    fun testHiraganaSyllables() {
        assertEquals("ka", HiraganaKey.KA.ipa)
        assertEquals("sa", HiraganaKey.SA.ipa)
        assertEquals("ta", HiraganaKey.TA.ipa)
        assertEquals("na", HiraganaKey.NA.ipa)
    }

    @Test
    fun testAllHiraganaKeysImplementILayoutKey() {
        HiraganaKey.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.ipa.isNotEmpty(), "${key.name} should have ipa")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}

class DevanagariKeyTest {

    @Test
    fun testDevanagariVowels() {
        assertTrue(DevanagariKey.A.isVowel)
        assertTrue(DevanagariKey.AA.isVowel)
        assertTrue(DevanagariKey.I.isVowel)
        assertTrue(DevanagariKey.II.isVowel)
        assertTrue(DevanagariKey.U.isVowel)
        assertTrue(DevanagariKey.UU.isVowel)
    }

    @Test
    fun testDevanagariVowelIpa() {
        assertEquals("ə", DevanagariKey.A.ipa)
        assertEquals("aː", DevanagariKey.AA.ipa)
        assertEquals("ɪ", DevanagariKey.I.ipa)
        assertEquals("iː", DevanagariKey.II.ipa)
        assertEquals("ʊ", DevanagariKey.U.ipa)
        assertEquals("uː", DevanagariKey.UU.ipa)
    }

    @Test
    fun testDevanagariConsonants() {
        assertEquals("k", DevanagariKey.KA.ipa)
        assertEquals("kʰ", DevanagariKey.KHA.ipa)
        assertEquals("ɡ", DevanagariKey.GA.ipa)
        assertEquals("ɡʱ", DevanagariKey.GHA.ipa)
    }

    @Test
    fun testDevanagariConsonantsFiltered() {
        assertTrue(DevanagariKey.consonants.isNotEmpty())
        assertTrue(DevanagariKey.consonants.none { it.isVowel })
    }

    @Test
    fun testDevanagariVowelsFiltered() {
        assertTrue(DevanagariKey.vowels.isNotEmpty())
        assertTrue(DevanagariKey.vowels.all { it.isVowel })
    }

    @Test
    fun testAllDevanagariKeysImplementILayoutKey() {
        DevanagariKey.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}

class LatinKeyTest {

    @Test
    fun testLatinKeyFromChar() {
        val a = LatinKey.fromChar("a")
        assertNotNull(a)
        assertEquals(LatinKey.A, a)
        assertEquals("a", a.char)
        assertEquals("a", a.ipa)
    }

    @Test
    fun testLatinKeyFromCharUppercase() {
        val a = LatinKey.fromChar("A")
        assertNotNull(a)
        assertEquals(LatinKey.A, a)
    }

    @Test
    fun testLatinVowels() {
        assertEquals("a", LatinKey.A.ipa)
        assertEquals("e", LatinKey.E.ipa)
        assertEquals("i", LatinKey.I.ipa)
        assertEquals("o", LatinKey.O.ipa)
        assertEquals("u", LatinKey.U.ipa)
    }

    @Test
    fun testLatinConsonants() {
        assertEquals("b", LatinKey.B.ipa)
        assertEquals("k", LatinKey.C.ipa) // Default IPA for C
        assertEquals("d", LatinKey.D.ipa)
        assertEquals("f", LatinKey.F.ipa)
    }

    @Test
    fun testAllLatinKeysImplementILayoutKey() {
        LatinKey.entries.forEach { key ->
            assertTrue(key.char.isNotEmpty(), "${key.name} should have char")
            assertTrue(key.ipa.isNotEmpty(), "${key.name} should have ipa")
            assertTrue(key.displayName.isNotEmpty(), "${key.name} should have displayName")
        }
    }
}
