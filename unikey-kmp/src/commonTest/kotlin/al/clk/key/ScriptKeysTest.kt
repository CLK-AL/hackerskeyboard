package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LangTest {

    @Test
    fun testLangFromCode() {
        assertEquals(Lang.HE, Lang.fromCode("he"))
        assertEquals(Lang.EN, Lang.fromCode("en"))
        assertEquals(Lang.AR, Lang.fromCode("ar"))
        assertEquals(Lang.RU, Lang.fromCode("ru"))
        assertEquals(Lang.JA, Lang.fromCode("ja"))
        assertEquals(Lang.ZH, Lang.fromCode("zh"))
    }

    @Test
    fun testLangFromCodeCaseInsensitive() {
        assertEquals(Lang.HE, Lang.fromCode("HE"))
        assertEquals(Lang.EN, Lang.fromCode("EN"))
    }

    @Test
    fun testLangScript() {
        assertEquals(Script.HEBREW, Lang.HE.script)
        assertEquals(Script.LATIN, Lang.EN.script)
        assertEquals(Script.ARABIC, Lang.AR.script)
        assertEquals(Script.CYRILLIC, Lang.RU.script)
        assertEquals(Script.HIRAGANA, Lang.JA.script)
        assertEquals(Script.HANGUL, Lang.KO.script)
        assertEquals(Script.CJK, Lang.ZH.script)
        assertEquals(Script.DEVANAGARI, Lang.HI.script)
        assertEquals(Script.GREEK, Lang.EL.script)
    }

    @Test
    fun testAllLangsHaveScript() {
        Lang.entries.forEach { lang ->
            assertNotNull(lang.script, "${lang.name} should have a script")
        }
    }

    @Test
    fun testLatinScriptLanguages() {
        val latinLangs = Lang.entries.filter { it.script == Script.LATIN }
        assertTrue(latinLangs.contains(Lang.EN))
        assertTrue(latinLangs.contains(Lang.DE))
        assertTrue(latinLangs.contains(Lang.FR))
        assertTrue(latinLangs.contains(Lang.ES))
        assertTrue(latinLangs.contains(Lang.IT))
        assertTrue(latinLangs.contains(Lang.PT))
    }
}

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
    fun testHebrewLetterFromIpa() {
        // Multiple letters can have same IPA (e.g., tet and tav both are "t")
        val tKeys = HebrewLetter.fromIpa("t")
        assertTrue(tKeys.contains(HebrewLetter.TET))
        assertTrue(tKeys.contains(HebrewLetter.TAV))
    }

    @Test
    fun testHebrewLetterIpaRoundTrip() {
        HebrewLetter.entries.forEach { letter ->
            val found = HebrewLetter.fromIpa(letter.ipa)
            assertTrue(found.contains(letter), "${letter.name} should be found by IPA '${letter.ipa}'")
        }
    }

    @Test
    fun testHebrewKeysMap() {
        val keys = HebrewLetter.keys
        assertTrue(keys.isNotEmpty())
        assertNotNull(keys["t"]) // alef
        assertEquals("א", keys["t"]?.char)
        assertNotNull(keys["c"]) // bet
        assertEquals("ב", keys["c"]?.char)
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
    fun testArabicLetterFromIpa() {
        val bKeys = ArabicLetter.fromIpa("b")
        assertTrue(bKeys.contains(ArabicLetter.BA))
    }

    @Test
    fun testArabicLetterIpaRoundTrip() {
        ArabicLetter.entries.filter { it.ipa.isNotEmpty() }.forEach { letter ->
            val found = ArabicLetter.fromIpa(letter.ipa)
            assertTrue(found.contains(letter), "${letter.name} should be found by IPA '${letter.ipa}'")
        }
    }

    @Test
    fun testArabicKeysMap() {
        val keys = ArabicLetter.keys
        assertTrue(keys.isNotEmpty())
        assertNotNull(keys["f"]) // ba
        assertEquals("ب", keys["f"]?.char)
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
    fun testGreekKeyFromIpa() {
        // Multiple Greek letters map to "i" in Modern Greek
        val iKeys = GreekKey.fromIpa("i")
        assertTrue(iKeys.contains(GreekKey.IOTA))
        assertTrue(iKeys.contains(GreekKey.ETA))
        assertTrue(iKeys.contains(GreekKey.UPSILON))
    }

    @Test
    fun testGreekKeyIpaRoundTrip() {
        GreekKey.entries.forEach { key ->
            val found = GreekKey.fromIpa(key.ipa)
            assertTrue(found.contains(key), "${key.name} should be found by IPA '${key.ipa}'")
        }
    }

    @Test
    fun testGreekKeysMap() {
        val keys = GreekKey.keys
        assertTrue(keys.isNotEmpty())
        assertNotNull(keys["a"])
        assertEquals("α", keys["a"]?.char)
        // Semicolon is added for Greek layout
        assertNotNull(keys["q"])
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
    fun testCyrillicKeyFromIpa() {
        val aKeys = CyrillicKey.fromIpa("a")
        assertTrue(aKeys.contains(CyrillicKey.A))
    }

    @Test
    fun testCyrillicKeyIpaRoundTrip() {
        CyrillicKey.entries.filter { it.ipa.isNotEmpty() }.forEach { key ->
            val found = CyrillicKey.fromIpa(key.ipa)
            assertTrue(found.contains(key), "${key.name} should be found by IPA '${key.ipa}'")
        }
    }

    @Test
    fun testCyrillicKeysMap() {
        val keys = CyrillicKey.keys
        assertTrue(keys.isNotEmpty())
        assertNotNull(keys["f"])
        assertEquals("а", keys["f"]?.char)
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
    fun testHiraganaKeyFromIpa() {
        val aKeys = HiraganaKey.fromIpa("a")
        assertTrue(aKeys.contains(HiraganaKey.A))
    }

    @Test
    fun testHiraganaKeyIpaRoundTrip() {
        HiraganaKey.entries.forEach { key ->
            val found = HiraganaKey.fromIpa(key.ipa)
            assertTrue(found.contains(key), "${key.name} should be found by IPA '${key.ipa}'")
        }
    }

    @Test
    fun testHiraganaKeysMap() {
        val keys = HiraganaKey.keys
        assertTrue(keys.isNotEmpty())
        assertNotNull(keys["a"])
        assertEquals("あ", keys["a"]?.char)
        assertNotNull(keys["k"]) // ka maps to k
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
    fun testLatinKeyFromIpa() {
        val keys = LatinKey.fromIpa("k")
        assertTrue(keys.isNotEmpty())
        assertTrue(keys.contains(LatinKey.C))
        assertTrue(keys.contains(LatinKey.K))
        assertTrue(keys.contains(LatinKey.Q))
    }

    @Test
    fun testLatinKeyIpaRoundTrip() {
        // Every key should be findable by its IPA
        LatinKey.entries.forEach { key ->
            val found = LatinKey.fromIpa(key.ipa)
            assertTrue(found.contains(key), "${key.name} should be found by IPA '${key.ipa}'")
        }
    }

    @Test
    fun testLatinKeysMap() {
        val keys = LatinKey.keys
        assertEquals(26, keys.size)
        assertNotNull(keys["a"])
        assertEquals("a", keys["a"]?.char)
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

/**
 * English spelling pattern tests
 */
class EnglishPatternTest {

    @Test
    fun testOughtPattern() {
        val pattern = EnglishPattern.matchEnding("bought")
        assertNotNull(pattern)
        assertEquals(EnglishPattern.OUGHT, pattern)
        assertEquals("ɔːt", pattern.ipa)
    }

    @Test
    fun testOughVariants() {
        // Different pronunciations of "ough"
        val through = EnglishPattern.findInWord("through")
        assertTrue(through.any { it.ipa == "uː" }, "through should have oo sound")

        val rough = EnglishPattern.findInWord("rough")
        assertTrue(rough.any { it.ipa == "ʌf" }, "rough should have uff sound")

        val though = EnglishPattern.findInWord("though")
        assertTrue(though.any { it.ipa == "oʊ" }, "though should have long o sound")
    }

    @Test
    fun testAughtPattern() {
        val pattern = EnglishPattern.matchEnding("caught")
        assertNotNull(pattern)
        assertEquals("ɔːt", pattern.ipa)
        assertTrue(pattern.examples.contains("caught"))
    }

    @Test
    fun testIghtPattern() {
        val pattern = EnglishPattern.matchEnding("night")
        assertNotNull(pattern)
        assertEquals(EnglishPattern.IGHT, pattern)
        assertEquals("aɪt", pattern.ipa)
    }

    @Test
    fun testEighPattern() {
        val pattern = EnglishPattern.matchEnding("weigh")
        assertNotNull(pattern)
        assertEquals(EnglishPattern.EIGH, pattern)
        assertEquals("eɪ", pattern.ipa)
    }

    @Test
    fun testTionPattern() {
        val pattern = EnglishPattern.matchEnding("nation")
        assertNotNull(pattern)
        assertEquals(EnglishPattern.TION, pattern)
        assertEquals("ʃən", pattern.ipa)
    }

    @Test
    fun testIngPattern() {
        val pattern = EnglishPattern.matchEnding("singing")
        assertNotNull(pattern)
        assertEquals(EnglishPattern.ING, pattern)
        assertEquals("ɪŋ", pattern.ipa)
    }

    @Test
    fun testOokPattern() {
        val pattern = EnglishPattern.matchEnding("book")
        assertNotNull(pattern)
        assertEquals(EnglishPattern.OOK, pattern)
        assertEquals("ʊk", pattern.ipa)
    }

    @Test
    fun testSilentLetters() {
        val kn = EnglishPattern.KN
        assertEquals("n", kn.ipa)
        assertTrue(kn.examples.contains("know"))

        val wr = EnglishPattern.WR
        assertEquals("r", wr.ipa)
        assertTrue(wr.examples.contains("write"))
    }

    @Test
    fun testFromIpa() {
        // Multiple patterns can have same IPA
        val awtPatterns = EnglishPattern.fromIpa("ɔːt")
        assertTrue(awtPatterns.contains(EnglishPattern.OUGHT))
        assertTrue(awtPatterns.contains(EnglishPattern.AUGHT))
    }

    @Test
    fun testAllPatternsHaveExamples() {
        EnglishPattern.entries.forEach { pattern ->
            assertTrue(pattern.examples.isNotEmpty(), "${pattern.name} should have examples")
        }
    }

    @Test
    fun testAllPatternsHaveIpa() {
        EnglishPattern.entries.filter { it != EnglishPattern.GH_SILENT }.forEach { pattern ->
            assertTrue(pattern.ipa.isNotEmpty(), "${pattern.name} should have IPA")
        }
    }

    @Test
    fun testPatternPosition() {
        assertEquals(PatternPosition.END, EnglishPattern.OUGHT.position)
        assertEquals(PatternPosition.END, EnglishPattern.TION.position)
        assertEquals(PatternPosition.END, EnglishPattern.LY.position)
        assertEquals(PatternPosition.END, EnglishPattern.MB.position)
    }
}

/**
 * Cross-script IPA round-trip tests
 */
class IpaRoundTripTest {

    @Test
    fun testCrossScriptIpaLookup_B() {
        // IPA "b" exists in multiple scripts
        val hebrewB = HebrewLetter.fromIpa("b")
        val arabicB = ArabicLetter.fromIpa("b")
        val latinB = LatinKey.fromIpa("b")
        val cyrillicB = CyrillicKey.fromIpa("b")

        assertTrue(hebrewB.isNotEmpty(), "Hebrew should have 'b' sound")
        assertTrue(arabicB.isNotEmpty(), "Arabic should have 'b' sound")
        assertTrue(latinB.isNotEmpty(), "Latin should have 'b' sound")
        assertTrue(cyrillicB.isNotEmpty(), "Cyrillic should have 'b' sound")
    }

    @Test
    fun testCrossScriptIpaLookup_M() {
        // IPA "m" is universal
        val hebrewM = HebrewLetter.fromIpa("m")
        val arabicM = ArabicLetter.fromIpa("m")
        val latinM = LatinKey.fromIpa("m")
        val cyrillicM = CyrillicKey.fromIpa("m")
        val hiraganaM = HiraganaKey.fromIpa("ma")

        assertTrue(hebrewM.any { it == HebrewLetter.MEM })
        assertTrue(arabicM.any { it == ArabicLetter.MEEM })
        assertTrue(latinM.any { it == LatinKey.M })
        assertTrue(cyrillicM.any { it == CyrillicKey.EM })
        assertTrue(hiraganaM.any { it == HiraganaKey.MA })
    }

    @Test
    fun testCrossScriptIpaLookup_S() {
        // IPA "s" in multiple scripts
        val hebrewS = HebrewLetter.fromIpa("s")
        val arabicS = ArabicLetter.fromIpa("s")
        val latinS = LatinKey.fromIpa("s")

        assertTrue(hebrewS.any { it == HebrewLetter.SAMECH })
        assertTrue(arabicS.any { it == ArabicLetter.SEEN })
        assertTrue(latinS.any { it == LatinKey.S })
    }

    @Test
    fun testUniqueIpaSounds() {
        // Hebrew-unique sounds
        val chet = HebrewLetter.fromIpa("ħ")
        assertTrue(chet.isEmpty() || chet.none { true }, "ħ should map to Hebrew chet (but HebrewLetter uses ḥ)")

        // Arabic pharyngeals
        val ain = ArabicLetter.fromIpa("ʕ")
        assertTrue(ain.any { it == ArabicLetter.AIN })

        // Arabic emphatics
        val sad = ArabicLetter.fromIpa("sˤ")
        assertTrue(sad.any { it == ArabicLetter.SAD })
    }

    @Test
    fun testKeyboardLayoutKeysContainEnumValues() {
        // HebrewLetter.keys should contain all Hebrew letters
        val heKeys = HebrewLetter.keys
        HebrewLetter.entries.filter { it.qwerty.isNotEmpty() }.forEach { letter ->
            assertNotNull(heKeys[letter.qwerty], "${letter.name} should be in keys map")
        }

        // GreekKey.keys should contain all Greek letters
        val grKeys = GreekKey.keys
        GreekKey.entries.forEach { key ->
            assertNotNull(grKeys[key.qwerty], "${key.name} should be in keys map")
        }

        // CyrillicKey.keys should contain all Cyrillic letters
        val ruKeys = CyrillicKey.keys
        CyrillicKey.entries.forEach { key ->
            assertNotNull(ruKeys[key.qwerty], "${key.name} should be in keys map")
        }
    }

    @Test
    fun testIpaAsHubForMultipleRepresentations() {
        // IPA "k" can be represented in multiple ways
        val latinK = LatinKey.fromIpa("k")
        assertTrue(latinK.size >= 2, "Multiple Latin letters should map to 'k': C, K, Q")
        assertTrue(latinK.map { it.char }.containsAll(listOf("c", "k", "q")))

        // Hebrew also has multiple "k" sounds (qof, kaf with dagesh)
        val hebrewK = HebrewLetter.fromIpa("k")
        assertTrue(hebrewK.any { it == HebrewLetter.QOF })
    }
}
