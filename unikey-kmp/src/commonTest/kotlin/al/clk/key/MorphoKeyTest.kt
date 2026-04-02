package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MorphoKeyTest {

    @Test
    fun testHebrewMorphoKeyBase() {
        val bet = HebrewLetter.BET.toMorphoKey()
        assertEquals("ב", bet.char)
        assertEquals("v", bet.ipa)
    }

    @Test
    fun testHebrewBetDagesh() {
        val bet = HebrewLetter.BET.toMorphoKey()
        val dagesh = bet.withModifier(Modifier.SHIFT)
        assertNotNull(dagesh)
        assertEquals("בּ", dagesh.char)
        assertEquals("b", dagesh.ipa)
    }

    @Test
    fun testHebrewPrefixPosition() {
        val betPrefix = HebrewPrefix.BE.toMorphoKey()
        val prefix = betPrefix.forPosition(SyllablePosition.PREFIX)
        assertEquals("בְּ", prefix.char)
        assertEquals("be", prefix.ipa)
    }

    @Test
    fun testHebrewSuffixPosition() {
        val mem = HebrewLetter.MEM.toMorphoKey()
        val suffix = mem.forPosition(SyllablePosition.SUFFIX)
        assertEquals("ם", suffix.char)
        assertEquals("m", suffix.ipa)
    }

    @Test
    fun testHebrewKafSofit() {
        val kaf = HebrewLetter.KAF.toMorphoKey()
        // Middle position is regular kaf (base)
        val middle = kaf.forPosition(SyllablePosition.MIDDLE)
        assertEquals("כ", middle.char)
        // Suffix position is kaf sofit
        val suffix = kaf.forPosition(SyllablePosition.SUFFIX)
        assertEquals("ך", suffix.char)
    }

    @Test
    fun testHebrewMascPluralSuffix() {
        val mascPlural = HebrewSuffix.MASC_PLURAL.toMorphoKey()
        assertEquals("ים", mascPlural.char)
        assertEquals("im", mascPlural.ipa)
    }

    @Test
    fun testHebrewFemPluralSuffix() {
        val femPlural = HebrewSuffix.FEM_PLURAL.toMorphoKey()
        assertEquals("ות", femPlural.char)
        assertEquals("ot", femPlural.ipa)
    }

    @Test
    fun testHebrewLayoutLookup() {
        val layout = MorphoLayouts.HE
        assertEquals("he", layout.code)
        assertEquals("Hebrew", layout.name)
        assertEquals("עברית", layout.nativeName)

        val alef = layout["t"]
        assertNotNull(alef)
        assertEquals("א", alef.char)
    }

    @Test
    fun testHebrewPrefixesList() {
        val layout = MorphoLayouts.HE
        val prefixes = layout.prefixes
        assertEquals(7, prefixes.size)
        // Check that bet, lamed, mem, kaf, he, vav, shin are present
        val prefixChars = prefixes.map { it.char }
        assertTrue("ב" in prefixChars)
        assertTrue("ל" in prefixChars)
        assertTrue("מ" in prefixChars)
        assertTrue("כ" in prefixChars)
        assertTrue("ה" in prefixChars)
        assertTrue("ו" in prefixChars)
        assertTrue("ש" in prefixChars)
    }

    @Test
    fun testArabicMorphoKeys() {
        val ba = ArabicLetter.BA.toMorphoKey()
        assertEquals("ب", ba.char)
        assertEquals("b", ba.ipa)
    }

    @Test
    fun testArabicAlPrefix() {
        val al = ArabicMorphoEnum.alPrefix
        val prefix = al.forPosition(SyllablePosition.PREFIX)
        assertEquals("الـ", prefix.char)
        assertEquals("al", prefix.ipa)
    }

    @Test
    fun testArabicMascPluralSuffix() {
        val mascPlural = ArabicSuffix.MASC_PLURAL_NOM.toMorphoKey()
        assertEquals("ون", mascPlural.char)
        assertEquals("uːn", mascPlural.ipa)
    }

    @Test
    fun testArabicFemPluralSuffix() {
        val femPlural = ArabicSuffix.FEM_PLURAL.toMorphoKey()
        assertEquals("ات", femPlural.char)
        assertEquals("aːt", femPlural.ipa)
    }

    @Test
    fun testArabicLayoutLookup() {
        val layout = MorphoLayouts.AR
        assertEquals("ar", layout.code)
        assertEquals("Arabic", layout.name)
        assertEquals("العربية", layout.nativeName)

        val ba = layout["f"]
        assertNotNull(ba)
        assertEquals("ب", ba.char)
    }

    @Test
    fun testSpanishGenderSuffixes() {
        val mascSing = RomanceMorphoEnum.spanishEnding(Gender.MASCULINE, Number.SINGULAR)
        val mascPlur = RomanceMorphoEnum.spanishEnding(Gender.MASCULINE, Number.PLURAL)
        val femSing = RomanceMorphoEnum.spanishEnding(Gender.FEMININE, Number.SINGULAR)
        val femPlur = RomanceMorphoEnum.spanishEnding(Gender.FEMININE, Number.PLURAL)

        assertEquals("o", mascSing.char)
        assertEquals("os", mascPlur.char)
        assertEquals("a", femSing.char)
        assertEquals("as", femPlur.char)
    }

    @Test
    fun testItalianGenderSuffixes() {
        val mascSing = RomanceMorphoEnum.italianEnding(Gender.MASCULINE, Number.SINGULAR)
        val mascPlur = RomanceMorphoEnum.italianEnding(Gender.MASCULINE, Number.PLURAL)
        val femSing = RomanceMorphoEnum.italianEnding(Gender.FEMININE, Number.SINGULAR)
        val femPlur = RomanceMorphoEnum.italianEnding(Gender.FEMININE, Number.PLURAL)

        assertEquals("o", mascSing.char)
        assertEquals("i", mascPlur.char)  // Italian uses -i for masc plural
        assertEquals("a", femSing.char)
        assertEquals("e", femPlur.char)   // Italian uses -e for fem plural
    }

    @Test
    fun testFrenchGenderSuffixes() {
        val mascSing = RomanceMorphoEnum.frenchEnding(Gender.MASCULINE, Number.SINGULAR)
        val mascPlur = RomanceMorphoEnum.frenchEnding(Gender.MASCULINE, Number.PLURAL)
        val femSing = RomanceMorphoEnum.frenchEnding(Gender.FEMININE, Number.SINGULAR)
        val femPlur = RomanceMorphoEnum.frenchEnding(Gender.FEMININE, Number.PLURAL)

        assertEquals("", mascSing.char)
        assertEquals("s", mascPlur.char)
        assertEquals("e", femSing.char)
        assertEquals("es", femPlur.char)
    }

    @Test
    fun testGermanArticle() {
        val derMasc = GermanMorphoEnum.article(Gender.MASCULINE, Number.SINGULAR)
        val dieFem = GermanMorphoEnum.article(Gender.FEMININE, Number.SINGULAR)
        val dasNeut = GermanMorphoEnum.article(Gender.NEUTRAL, Number.SINGULAR)
        val diePlur = GermanMorphoEnum.article(Gender.NEUTRAL, Number.PLURAL)

        assertEquals("der", derMasc.char)
        assertEquals("die", dieFem.char)
        assertEquals("das", dasNeut.char)
        assertEquals("die", diePlur.char)
    }

    @Test
    fun testMorphoLayoutsGet() {
        val he = MorphoLayouts["he"]
        assertNotNull(he)
        assertEquals("Hebrew", he.name)

        val ar = MorphoLayouts["ar"]
        assertNotNull(ar)
        assertEquals("Arabic", ar.name)

        val unknown = MorphoLayouts["xx"]
        assertNull(unknown)
    }

    @Test
    fun testHebrewShinVariants() {
        val shin = HebrewPrefix.SHE.toMorphoKey()
        assertEquals("ש", shin.char)
        assertEquals("ʃ", shin.ipa)
    }

    @Test
    fun testMorphoVariantInterface() {
        // MorphoKey implements ILayoutKey
        val key: ILayoutKey = HebrewLetter.ALEF.toMorphoKey()
        assertEquals("א", key.char)
        assertEquals("ʔ", key.ipa)
    }
}
