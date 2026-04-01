package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MorphoKeyTest {

    @Test
    fun testHebrewMorphoKeyBase() {
        val bet = HebrewMorphoKeys.BET
        assertEquals("ב", bet.char)
        assertEquals("v", bet.ipa)
        assertEquals("bet", bet.displayName)
    }

    @Test
    fun testHebrewBetDagesh() {
        val bet = HebrewMorphoKeys.BET
        val dagesh = bet.withModifier(Modifier.SHIFT)
        assertNotNull(dagesh)
        assertEquals("בּ", dagesh.char)
        assertEquals("b", dagesh.ipa)
    }

    @Test
    fun testHebrewPrefixPosition() {
        val betPrefix = HebrewMorphoKeys.BET_PREFIX
        val prefix = betPrefix.forPosition(SyllablePosition.PREFIX)
        assertEquals("בְּ", prefix.char)
        assertEquals("be", prefix.ipa)
        assertEquals("bet-prefix-in", prefix.displayName)
    }

    @Test
    fun testHebrewSuffixPosition() {
        val mem = HebrewMorphoKeys.MEM
        val suffix = mem.forPosition(SyllablePosition.SUFFIX)
        assertEquals("ם", suffix.char)
        assertEquals("m", suffix.ipa)
        assertEquals("mem-sofit", suffix.displayName)
    }

    @Test
    fun testHebrewKafSofit() {
        val kaf = HebrewMorphoKeys.KAF
        // Middle position is regular kaf
        val middle = kaf.forPosition(SyllablePosition.MIDDLE)
        assertEquals("כ", middle.char)
        // Suffix position is kaf sofit
        val suffix = kaf.forPosition(SyllablePosition.SUFFIX)
        assertEquals("ך", suffix.char)
    }

    @Test
    fun testHebrewMascPluralSuffix() {
        val mascPlural = HebrewMorphoKeys.YOD_MEM_SUFFIX
        val plural = mascPlural.forNumber(Number.PLURAL)
        assertEquals("ים", plural.char)
        assertEquals("im", plural.ipa)
    }

    @Test
    fun testHebrewFemPluralSuffix() {
        val femPlural = HebrewMorphoKeys.VAV_TAV_SUFFIX
        val plural = femPlural.forNumber(Number.PLURAL)
        assertEquals("ות", plural.char)
        assertEquals("ot", plural.ipa)
    }

    @Test
    fun testHebrewFemSingularSuffix() {
        val femSuffix = HebrewMorphoKeys.VAV_TAV_SUFFIX
        val singular = femSuffix.forNumber(Number.SINGULAR)
        assertEquals("ה", singular.char)
        assertEquals("a", singular.ipa)
    }

    @Test
    fun testHebrewHePrefixArticle() {
        val he = HebrewMorphoKeys.HE_PREFIX
        val prefix = he.forPosition(SyllablePosition.PREFIX)
        assertEquals("הַ", prefix.char)
        assertEquals("ha", prefix.ipa)
        assertEquals("he-prefix-the", prefix.displayName)
    }

    @Test
    fun testHebrewVavPrefixConjunction() {
        val vav = HebrewMorphoKeys.VAV_PREFIX
        val prefix = vav.forPosition(SyllablePosition.PREFIX)
        assertEquals("וְ", prefix.char)
        assertEquals("ve", prefix.ipa)
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
        assert("ב" in prefixChars)
        assert("ל" in prefixChars)
        assert("מ" in prefixChars)
        assert("כ" in prefixChars)
        assert("ה" in prefixChars)
        assert("ו" in prefixChars)
        assert("ש" in prefixChars)
    }

    @Test
    fun testArabicMorphoKeys() {
        val ba = ArabicMorphoKeys.BA
        assertEquals("ب", ba.char)
        assertEquals("b", ba.ipa)
    }

    @Test
    fun testArabicAlPrefix() {
        val al = ArabicMorphoKeys.AL_PREFIX
        val prefix = al.forPosition(SyllablePosition.PREFIX)
        assertEquals("الـ", prefix.char)
        assertEquals("al", prefix.ipa)
    }

    @Test
    fun testArabicMascPluralSuffix() {
        val mascPlural = ArabicMorphoKeys.UUN_SUFFIX
        val plural = mascPlural.forNumber(Number.PLURAL)
        assertEquals("ون", plural.char)
        assertEquals("uːn", plural.ipa)
    }

    @Test
    fun testArabicFemPluralSuffix() {
        val femPlural = ArabicMorphoKeys.AAT_SUFFIX
        val plural = femPlural.forNumber(Number.PLURAL)
        assertEquals("ات", plural.char)
        assertEquals("aːt", plural.ipa)
    }

    @Test
    fun testArabicTaMarbuta() {
        val ta = ArabicMorphoKeys.TA_MARBUTA
        val fem = ta.forGender(Gender.FEMININE)
        assertEquals("ة", fem.char)
        assertEquals("a", fem.ipa)
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
        val (masc, fem) = RomanceMorphoKeys.forLanguage("es")!!

        assertEquals("o", masc.forNumber(Number.SINGULAR).char)
        assertEquals("os", masc.forNumber(Number.PLURAL).char)

        assertEquals("a", fem.forNumber(Number.SINGULAR).char)
        assertEquals("as", fem.forNumber(Number.PLURAL).char)
    }

    @Test
    fun testItalianGenderSuffixes() {
        val (masc, fem) = RomanceMorphoKeys.forLanguage("it")!!

        assertEquals("o", masc.forNumber(Number.SINGULAR).char)
        assertEquals("i", masc.forNumber(Number.PLURAL).char)  // Italian uses -i for masc plural

        assertEquals("a", fem.forNumber(Number.SINGULAR).char)
        assertEquals("e", fem.forNumber(Number.PLURAL).char)  // Italian uses -e for fem plural
    }

    @Test
    fun testFrenchGenderSuffixes() {
        val (masc, fem) = RomanceMorphoKeys.forLanguage("fr")!!

        assertEquals("", masc.forNumber(Number.SINGULAR).char)
        assertEquals("s", masc.forNumber(Number.PLURAL).char)

        assertEquals("e", fem.forNumber(Number.SINGULAR).char)
        assertEquals("es", fem.forNumber(Number.PLURAL).char)
    }

    @Test
    fun testGermanArticle() {
        val der = GermanMorphoKeys.DER

        assertEquals("der", der.forGender(Gender.MASCULINE).char)
        assertEquals("die", der.forGender(Gender.FEMININE).char)
        assertEquals("das", der.forGender(Gender.NEUTRAL).char)
        assertEquals("die", der.forNumber(Number.PLURAL).char)
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
        val shin = HebrewMorphoKeys.SHIN_PREFIX
        assertEquals("ש", shin.char)
        assertEquals("ʃ", shin.ipa)

        // Sin variant via shift modifier
        val sin = shin.withModifier(Modifier.SHIFT)
        assertNotNull(sin)
        assertEquals("שׂ", sin.char)
        assertEquals("s", sin.ipa)
    }

    @Test
    fun testMorphoVariantInterface() {
        // MorphoKey implements ILayoutKey
        val key: ILayoutKey = HebrewMorphoKeys.ALEF
        assertEquals("א", key.char)
        assertEquals("ʔ", key.ipa)
        assertEquals("alef", key.displayName)
    }
}
