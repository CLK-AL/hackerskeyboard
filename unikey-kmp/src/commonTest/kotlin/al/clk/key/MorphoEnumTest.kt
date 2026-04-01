package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class MorphoEnumTest {

    // ═══ Hebrew Prefix Tests ═══

    @Test
    fun testHebrewPrefixForms() {
        assertEquals("בְּ", HebrewPrefix.BE.form)
        assertEquals("לְ", HebrewPrefix.LE.form)
        assertEquals("מִ", HebrewPrefix.MI.form)
        assertEquals("כְּ", HebrewPrefix.KE.form)
        assertEquals("הַ", HebrewPrefix.HA.form)
        assertEquals("וְ", HebrewPrefix.VE.form)
        assertEquals("שֶׁ", HebrewPrefix.SHE.form)
    }

    @Test
    fun testHebrewPrefixIpa() {
        assertEquals("be", HebrewPrefix.BE.ipa)
        assertEquals("le", HebrewPrefix.LE.ipa)
        assertEquals("mi", HebrewPrefix.MI.ipa)
        assertEquals("ha", HebrewPrefix.HA.ipa)
    }

    @Test
    fun testHebrewPrefixFromLetter() {
        assertEquals(HebrewPrefix.BE, HebrewPrefix.fromLetter(HebrewLetter.BET))
        assertEquals(HebrewPrefix.LE, HebrewPrefix.fromLetter(HebrewLetter.LAMED))
        assertNull(HebrewPrefix.fromLetter(HebrewLetter.ALEF))  // Not a prefix letter
    }

    // ═══ NikudVowel Tests ═══

    @Test
    fun testNikudVowelBasics() {
        assertEquals("ַ", NikudVowel.PATACH.mark)
        assertEquals("a", NikudVowel.PATACH.ipa)
        assertEquals(15, NikudVowel.PATACH.hue)
        assertTrue(NikudVowel.PATACH.isShort)
    }

    @Test
    fun testNikudVowelHataf() {
        assertTrue(NikudVowel.HATAF_PATACH.isHataf)
        assertTrue(NikudVowel.HATAF_SEGOL.isHataf)
        assertTrue(NikudVowel.HATAF_KAMATZ.isHataf)
        assertFalse(NikudVowel.PATACH.isHataf)
    }

    @Test
    fun testNikudVowelMale() {
        assertTrue(NikudVowel.CHIRIK_MALE.isMale)
        assertTrue(NikudVowel.CHOLAM_MALE.isMale)
        assertTrue(NikudVowel.SHURUK.isMale)
        assertFalse(NikudVowel.CHIRIK.isMale)
    }

    @Test
    fun testNikudVowelLookup() {
        assertEquals(NikudVowel.PATACH, NikudVowel.fromCodePoint(0x05B7))
        assertEquals(NikudVowel.KAMATZ, NikudVowel.fromCodePoint(0x05B8))
    }

    // ═══ Hebrew Suffix Tests ═══

    @Test
    fun testHebrewSuffixPlural() {
        val mascPlural = HebrewSuffix.plural(Gender.MASCULINE)
        assertEquals("ים", mascPlural.chars)
        assertEquals("im", mascPlural.ipa)

        val femPlural = HebrewSuffix.plural(Gender.FEMININE)
        assertEquals("ות", femPlural.chars)
        assertEquals("ot", femPlural.ipa)
    }

    @Test
    fun testHebrewSuffixPossessive() {
        assertEquals("י", HebrewSuffix.POSS_1S.chars)
        assertEquals("ו", HebrewSuffix.POSS_3MS.chars)
        assertEquals("נו", HebrewSuffix.POSS_1P.chars)
    }

    // ═══ Arabic Letter Tests ═══

    @Test
    fun testArabicLetterBasics() {
        assertEquals("ب", ArabicLetter.BA.isolated)
        assertEquals("b", ArabicLetter.BA.ipa)
        assertEquals('f', ArabicLetter.BA.qwerty)
    }

    @Test
    fun testArabicLetterForms() {
        assertEquals("بـ", ArabicLetter.BA.initial)
        assertEquals("ـبـ", ArabicLetter.BA.medial)
        assertEquals("ـب", ArabicLetter.BA.finalForm)
    }

    @Test
    fun testArabicLetterPosition() {
        assertEquals("بـ", ArabicLetter.BA.forPosition(SyllablePosition.PREFIX))
        assertEquals("ـبـ", ArabicLetter.BA.forPosition(SyllablePosition.MIDDLE))
        assertEquals("ـب", ArabicLetter.BA.forPosition(SyllablePosition.SUFFIX))
    }

    // ═══ Haraka Tests ═══

    @Test
    fun testHarakaBasics() {
        assertEquals('َ', Haraka.FATHA.char)
        assertEquals("a", Haraka.FATHA.ipa)
        assertEquals('ً', Haraka.FATHA.tanwin)
        assertEquals("an", Haraka.FATHA.tanwinIpa)
    }

    @Test
    fun testHarakaSukun() {
        assertEquals('ْ', Haraka.SUKUN.char)
        assertEquals("", Haraka.SUKUN.ipa)
        assertNull(Haraka.SUKUN.tanwin)
    }

    // ═══ Arabic Suffix Tests ═══

    @Test
    fun testArabicSuffixPlural() {
        val mascPlural = ArabicSuffix.plural(Gender.MASCULINE, "nom")
        assertEquals("ون", mascPlural.chars)
        assertEquals("uːn", mascPlural.ipa)

        val femPlural = ArabicSuffix.plural(Gender.FEMININE)
        assertEquals("ات", femPlural.chars)
        assertEquals("aːt", femPlural.ipa)
    }

    // ═══ Spanish Suffix Tests ═══

    @Test
    fun testSpanishSuffixes() {
        assertEquals("o", SpanishSuffix.ending(Gender.MASCULINE, Number.SINGULAR).chars)
        assertEquals("os", SpanishSuffix.ending(Gender.MASCULINE, Number.PLURAL).chars)
        assertEquals("a", SpanishSuffix.ending(Gender.FEMININE, Number.SINGULAR).chars)
        assertEquals("as", SpanishSuffix.ending(Gender.FEMININE, Number.PLURAL).chars)
    }

    // ═══ French Suffix Tests ═══

    @Test
    fun testFrenchSuffixes() {
        assertEquals("", FrenchSuffix.ending(Gender.MASCULINE, Number.SINGULAR).chars)
        assertEquals("s", FrenchSuffix.ending(Gender.MASCULINE, Number.PLURAL).chars)
        assertEquals("e", FrenchSuffix.ending(Gender.FEMININE, Number.SINGULAR).chars)
        assertEquals("es", FrenchSuffix.ending(Gender.FEMININE, Number.PLURAL).chars)
    }

    // ═══ Italian Suffix Tests ═══

    @Test
    fun testItalianSuffixes() {
        assertEquals("o", ItalianSuffix.ending(Gender.MASCULINE, Number.SINGULAR).chars)
        assertEquals("i", ItalianSuffix.ending(Gender.MASCULINE, Number.PLURAL).chars)
        assertEquals("a", ItalianSuffix.ending(Gender.FEMININE, Number.SINGULAR).chars)
        assertEquals("e", ItalianSuffix.ending(Gender.FEMININE, Number.PLURAL).chars)
    }

    // ═══ Portuguese Suffix Tests ═══

    @Test
    fun testPortugueseSuffixes() {
        assertEquals("o", PortugueseSuffix.ending(Gender.MASCULINE, Number.SINGULAR).chars)
        assertEquals("os", PortugueseSuffix.ending(Gender.MASCULINE, Number.PLURAL).chars)
        assertEquals("a", PortugueseSuffix.ending(Gender.FEMININE, Number.SINGULAR).chars)
        assertEquals("as", PortugueseSuffix.ending(Gender.FEMININE, Number.PLURAL).chars)

        // IPA differs from Spanish
        assertEquals("u", PortugueseSuffix.MASC_SING.ipa)
        assertEquals("uʃ", PortugueseSuffix.MASC_PLURAL.ipa)
    }

    // ═══ German Article Tests ═══

    @Test
    fun testGermanArticles() {
        assertEquals("der", GermanArticle.definite(Gender.MASCULINE, Number.SINGULAR, "nom").chars)
        assertEquals("die", GermanArticle.definite(Gender.FEMININE, Number.SINGULAR, "nom").chars)
        assertEquals("das", GermanArticle.definite(Gender.NEUTRAL, Number.SINGULAR, "nom").chars)
        assertEquals("die", GermanArticle.definite(Gender.NEUTRAL, Number.PLURAL, "nom").chars)
    }

    @Test
    fun testGermanArticleCases() {
        // Accusative
        assertEquals("den", GermanArticle.definite(Gender.MASCULINE, Number.SINGULAR, "acc").chars)

        // Dative
        assertEquals("dem", GermanArticle.definite(Gender.MASCULINE, Number.SINGULAR, "dat").chars)
        assertEquals("der", GermanArticle.definite(Gender.FEMININE, Number.SINGULAR, "dat").chars)

        // Genitive
        assertEquals("des", GermanArticle.definite(Gender.MASCULINE, Number.SINGULAR, "gen").chars)
    }

    // ═══ German Plural Tests ═══

    @Test
    fun testGermanPlural() {
        assertEquals("e", GermanPlural.E.chars)
        assertFalse(GermanPlural.E.umlaut)

        assertEquals("e", GermanPlural.E_UMLAUT.chars)
        assertTrue(GermanPlural.E_UMLAUT.umlaut)

        assertEquals("er", GermanPlural.ER.chars)
        assertEquals("en", GermanPlural.EN.chars)
    }

    // ═══ Extension Function Tests ═══

    @Test
    fun testHebrewWithPrefix() {
        val word = "דבר".withHebrewPrefix(HebrewPrefix.BE)
        assertEquals("בְּדבר", word)
    }

    @Test
    fun testHebrewWithSuffix() {
        val base = "ספר"  // sefer (book)
        val plural = base.withHebrewSuffix(HebrewSuffix.MASC_PLURAL)
        assertEquals("ספרים", plural)
    }

    @Test
    fun testIpaToHue() {
        // Vowels
        assertEquals(0, ipaToHue("a"))
        assertEquals(60, ipaToHue("e"))
        assertEquals(120, ipaToHue("i"))
        assertEquals(200, ipaToHue("o"))
        assertEquals(280, ipaToHue("u"))

        // Consonants
        assertEquals(45, ipaToHue("ʃ"))  // Sibilant
        assertEquals(180, ipaToHue("b"))  // Default consonant

        // Empty
        assertEquals(0, ipaToHue(""))
    }
}
