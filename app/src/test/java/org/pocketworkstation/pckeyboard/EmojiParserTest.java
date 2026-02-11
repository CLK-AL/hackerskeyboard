/*
 * Copyright (C) 2025 Hacker's Keyboard Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pocketworkstation.pckeyboard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Unit tests for {@link EmojiParser}.
 * Tests parsing of the Unicode emoji-test.txt format (UTS #51).
 */
public class EmojiParserTest {

    private EmojiParser parser;

    @Before
    public void setUp() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("emoji-test-sample.txt");
        assertNotNull("Test resource emoji-test-sample.txt should exist", stream);
        parser = EmojiParser.parseFrom(stream);
    }

    // ==================== Group Parsing Tests ====================

    @Test
    public void testGroupCount() {
        assertEquals("Should parse 5 groups", 5, parser.getGroups().size());
    }

    @Test
    public void testGroupNames() {
        List<String> names = parser.getGroupNames();
        assertEquals("Smileys & Emotion", names.get(0));
        assertEquals("People & Body", names.get(1));
        assertEquals("Component", names.get(2));
        assertEquals("Animals & Nature", names.get(3));
        assertEquals("Symbols", names.get(4));
    }

    @Test
    public void testGroupLookupByName() {
        EmojiParser.EmojiGroup smileys = parser.getGroup("Smileys & Emotion");
        assertNotNull("Should find Smileys & Emotion group", smileys);
        assertEquals("Smileys & Emotion", smileys.getName());
    }

    @Test
    public void testGroupLookupMissing() {
        assertNull("Should return null for unknown group", parser.getGroup("Nonexistent"));
    }

    // ==================== Subgroup Parsing Tests ====================

    @Test
    public void testSubgroupCount() {
        EmojiParser.EmojiGroup smileys = parser.getGroup("Smileys & Emotion");
        assertNotNull(smileys);
        assertEquals("Smileys & Emotion should have 3 subgroups", 3, smileys.getSubgroups().size());
    }

    @Test
    public void testSubgroupNames() {
        EmojiParser.EmojiGroup smileys = parser.getGroup("Smileys & Emotion");
        assertNotNull(smileys);
        assertEquals("face-smiling", smileys.getSubgroups().get(0).getName());
        assertEquals("face-affection", smileys.getSubgroups().get(1).getName());
        assertEquals("face-neutral-skeptical", smileys.getSubgroups().get(2).getName());
    }

    @Test
    public void testSubgroupGroupReference() {
        EmojiParser.EmojiGroup smileys = parser.getGroup("Smileys & Emotion");
        assertNotNull(smileys);
        for (EmojiParser.EmojiSubgroup sub : smileys.getSubgroups()) {
            assertEquals("Subgroup should reference parent group",
                    "Smileys & Emotion", sub.getGroup());
        }
    }

    // ==================== Entry Parsing Tests ====================

    @Test
    public void testFullyQualifiedCount() {
        // 3 (face-smiling) + 3 (face-affection fully-qualified) + 1 (face-neutral fq)
        // + 2 (hand-fingers-open) + 2 (animal-mammal) + 8 (hearts) + 2 (circles) = 21
        assertEquals("Should have 21 fully-qualified emoji", 21, parser.getAllFullyQualified().size());
    }

    @Test
    public void testSimpleEmojiEntry() {
        EmojiParser.EmojiEntry grinning = parser.getAllFullyQualified().get(0);
        assertEquals("grinning face", grinning.getName());
        assertEquals("E1.0", grinning.getVersion());
        assertEquals(EmojiParser.Status.FULLY_QUALIFIED, grinning.getStatus());
        assertEquals("Smileys & Emotion", grinning.getGroup());
        assertEquals("face-smiling", grinning.getSubgroup());
        assertArrayEquals(new int[]{0x1F600}, grinning.getCodePoints());
    }

    @Test
    public void testEmojiString() {
        EmojiParser.EmojiEntry grinning = parser.getAllFullyQualified().get(0);
        assertEquals("Emoji string should match code point",
                new String(new int[]{0x1F600}, 0, 1), grinning.getEmoji());
    }

    @Test
    public void testMultiCodePointEmoji() {
        // "263A FE0F" - smiling face with variation selector
        EmojiParser.EmojiGroup smileys = parser.getGroup("Smileys & Emotion");
        assertNotNull(smileys);
        List<EmojiParser.EmojiEntry> affection = smileys.getSubgroups().get(1).getEntries();
        // Third entry in face-affection is "263A FE0F"
        EmojiParser.EmojiEntry smilingFace = affection.get(2);
        assertArrayEquals("Should parse multi-codepoint",
                new int[]{0x263A, 0xFE0F}, smilingFace.getCodePoints());
        assertEquals(EmojiParser.Status.FULLY_QUALIFIED, smilingFace.getStatus());
    }

    @Test
    public void testZWJSequence() {
        // "1F636 200D 1F32B FE0F" - face in clouds (ZWJ sequence)
        EmojiParser.EmojiGroup smileys = parser.getGroup("Smileys & Emotion");
        assertNotNull(smileys);
        List<EmojiParser.EmojiEntry> neutral = smileys.getSubgroups().get(2).getEntries();
        EmojiParser.EmojiEntry faceInClouds = neutral.get(0);
        assertArrayEquals("Should parse ZWJ sequence",
                new int[]{0x1F636, 0x200D, 0x1F32B, 0xFE0F}, faceInClouds.getCodePoints());
        assertEquals("face in clouds", faceInClouds.getName());
        assertEquals("E13.1", faceInClouds.getVersion());
    }

    @Test
    public void testSkinToneModifier() {
        // "1F44B 1F3FB" - waving hand: light skin tone
        EmojiParser.EmojiGroup people = parser.getGroup("People & Body");
        assertNotNull(people);
        EmojiParser.EmojiEntry wavingLight = people.getSubgroups().get(0).getEntries().get(1);
        assertArrayEquals(new int[]{0x1F44B, 0x1F3FB}, wavingLight.getCodePoints());
        assertEquals("waving hand: light skin tone", wavingLight.getName());
    }

    // ==================== Status Tests ====================

    @Test
    public void testStatusParsing() {
        assertEquals(EmojiParser.Status.FULLY_QUALIFIED,
                EmojiParser.Status.Companion.fromString("fully-qualified"));
        assertEquals(EmojiParser.Status.MINIMALLY_QUALIFIED,
                EmojiParser.Status.Companion.fromString("minimally-qualified"));
        assertEquals(EmojiParser.Status.UNQUALIFIED,
                EmojiParser.Status.Companion.fromString("unqualified"));
        assertEquals(EmojiParser.Status.COMPONENT,
                EmojiParser.Status.Companion.fromString("component"));
    }

    @Test
    public void testUnqualifiedNotInFullyQualified() {
        // Unqualified "263A" should not appear in fully-qualified list
        for (EmojiParser.EmojiEntry entry : parser.getAllFullyQualified()) {
            assertNotEquals("Unqualified should not be in fully-qualified list",
                    EmojiParser.Status.UNQUALIFIED, entry.getStatus());
        }
    }

    @Test
    public void testMinimallyQualifiedNotInFullyQualified() {
        for (EmojiParser.EmojiEntry entry : parser.getAllFullyQualified()) {
            assertNotEquals("Minimally-qualified should not be in fully-qualified list",
                    EmojiParser.Status.MINIMALLY_QUALIFIED, entry.getStatus());
        }
    }

    @Test
    public void testComponentNotInFullyQualified() {
        for (EmojiParser.EmojiEntry entry : parser.getAllFullyQualified()) {
            assertNotEquals("Component should not be in fully-qualified list",
                    EmojiParser.Status.COMPONENT, entry.getStatus());
        }
    }

    @Test
    public void testComponentEntriesInGroup() {
        EmojiParser.EmojiGroup component = parser.getGroup("Component");
        assertNotNull(component);
        assertEquals(1, component.getSubgroups().size());
        assertEquals("skin-tone", component.getSubgroups().get(0).getName());
        assertEquals(2, component.getSubgroups().get(0).getEntries().size());
        assertEquals(EmojiParser.Status.COMPONENT,
                component.getSubgroups().get(0).getEntries().get(0).getStatus());
    }

    // ==================== Search Tests ====================

    @Test
    public void testSearchByName() {
        List<EmojiParser.EmojiEntry> results = parser.search("grinning");
        assertEquals("Should find 3 grinning emoji", 3, results.size());
    }

    @Test
    public void testSearchCaseInsensitive() {
        List<EmojiParser.EmojiEntry> results = parser.search("GRINNING");
        assertEquals("Search should be case-insensitive", 3, results.size());
    }

    @Test
    public void testSearchNoResults() {
        List<EmojiParser.EmojiEntry> results = parser.search("zzzznotfound");
        assertTrue("Should return empty for no match", results.isEmpty());
    }

    @Test
    public void testSearchPartialMatch() {
        List<EmojiParser.EmojiEntry> results = parser.search("heart");
        // 2 face with hearts + 8 colored hearts = 10
        assertEquals("Should find 10 heart-related emoji", 10, results.size());
    }

    // ==================== AllEmoji Convenience Tests ====================

    @Test
    public void testGroupAllEmoji() {
        EmojiParser.EmojiGroup smileys = parser.getGroup("Smileys & Emotion");
        assertNotNull(smileys);
        // 3 (face-smiling) + 4 (face-affection incl unqualified) + 2 (face-neutral) = 9
        assertEquals("allEmoji should aggregate all subgroup entries", 9, smileys.getAllEmoji().size());
    }

    // ==================== EmojiEntry Equality Tests ====================

    @Test
    public void testEntryEquality() {
        EmojiParser.EmojiEntry a = parser.getAllFullyQualified().get(0);
        EmojiParser.EmojiEntry b = parser.getAllFullyQualified().get(0);
        assertEquals("Same entries should be equal", a, b);
        assertEquals("Same entries should have same hashCode", a.hashCode(), b.hashCode());
    }

    @Test
    public void testEntryToString() {
        EmojiParser.EmojiEntry entry = parser.getAllFullyQualified().get(0);
        String str = entry.toString();
        assertTrue("toString should contain emoji", str.contains(entry.getEmoji()));
        assertTrue("toString should contain name", str.contains("grinning face"));
    }

    // ==================== Color Tests ====================

    @Test
    public void testColorDetectionFromName() {
        // "red heart" should have red color
        List<EmojiParser.EmojiEntry> redHearts = parser.search("red heart");
        assertFalse("Should find red heart", redHearts.isEmpty());
        EmojiParser.EmojiColor color = redHearts.get(0).getColor();
        assertNotNull("Red heart should have color", color);
        assertEquals("red", color.getName());
        assertEquals("#FF0000", color.getHex());
    }

    @Test
    public void testColorDetectionFromSkinTone() {
        // "waving hand: light skin tone" should have light skin tone color
        List<EmojiParser.EmojiEntry> results = parser.search("light skin tone");
        assertFalse("Should find light skin tone emoji", results.isEmpty());
        EmojiParser.EmojiColor color = results.get(0).getColor();
        assertNotNull("Skin tone emoji should have color", color);
        assertEquals("light skin tone", color.getName());
        assertTrue("Skin tone hex should start with #", color.getHex().startsWith("#"));
    }

    @Test
    public void testGetColors() {
        List<EmojiParser.EmojiColor> colors = parser.getColors();
        assertFalse("Should have some colors", colors.isEmpty());
        // Should have at least: red, orange, yellow, green, blue, purple, black, white, light skin tone
        assertTrue("Should have multiple colors", colors.size() >= 8);
    }

    @Test
    public void testGetEmojiByColor() {
        List<EmojiParser.EmojiEntry> redEmoji = parser.getEmojiByColor("#FF0000");
        assertFalse("Should find red emoji", redEmoji.isEmpty());
        // Should include red heart and red circle
        assertTrue("Should have at least 2 red emoji", redEmoji.size() >= 2);
    }

    @Test
    public void testGetColoredEmoji() {
        List<EmojiParser.EmojiEntry> colored = parser.getColoredEmoji();
        assertFalse("Should have colored emoji", colored.isEmpty());
        for (EmojiParser.EmojiEntry entry : colored) {
            assertNotNull("Colored emoji should have color", entry.getColor());
        }
    }

    @Test
    public void testGetSkinToneEmoji() {
        List<EmojiParser.EmojiEntry> skinTone = parser.getSkinToneEmoji();
        assertFalse("Should have skin tone emoji", skinTone.isEmpty());
        // "waving hand: light skin tone" is the only one in test data
        assertEquals("Should find 1 skin tone emoji in test data", 1, skinTone.size());
    }

    @Test
    public void testGetColorGroup() {
        EmojiParser.EmojiGroup colorGroup = parser.getColorGroup();
        assertEquals("Color", colorGroup.getName());
        assertFalse("Color group should have subgroups", colorGroup.getSubgroups().isEmpty());
        // Each subgroup is a color
        for (EmojiParser.EmojiSubgroup sub : colorGroup.getSubgroups()) {
            assertFalse("Color subgroup should have entries", sub.getEntries().isEmpty());
        }
    }

    @Test
    public void testEmojiColorToString() {
        EmojiParser.EmojiColor color = new EmojiParser.EmojiColor("#FF0000", "red");
        assertEquals("red (#FF0000)", color.toString());
    }

    @Test
    public void testDetectColorFromNameStatic() {
        EmojiParser.EmojiColor red = EmojiParser.detectColorFromName("red heart");
        assertNotNull(red);
        assertEquals("red", red.getName());

        EmojiParser.EmojiColor lightBlue = EmojiParser.detectColorFromName("light blue heart");
        assertNotNull(lightBlue);
        assertEquals("light blue", lightBlue.getName());

        EmojiParser.EmojiColor none = EmojiParser.detectColorFromName("grinning face");
        assertNull("Non-color emoji should return null", none);
    }

    // ==================== Filter / Breadcrumb Tests ====================

    @Test
    public void testFilterByGroup() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .group("Smileys & Emotion")
                .results();
        assertEquals("Should find 7 fully-qualified in Smileys & Emotion", 7, results.size());
        for (EmojiParser.EmojiEntry entry : results) {
            assertEquals("Smileys & Emotion", entry.getGroup());
        }
    }

    @Test
    public void testFilterBySubgroup() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .subgroup("face-smiling")
                .results();
        assertEquals("Should find 3 in face-smiling", 3, results.size());
        for (EmojiParser.EmojiEntry entry : results) {
            assertEquals("face-smiling", entry.getSubgroup());
        }
    }

    @Test
    public void testFilterByGroupAndSubgroup() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .group("Smileys & Emotion")
                .subgroup("face-affection")
                .results();
        assertEquals("Should find 3 fully-qualified in face-affection", 3, results.size());
    }

    @Test
    public void testFilterByColorName() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .colorName(EmojiParser.ColorName.RED)
                .results();
        assertEquals("Should find 2 red emoji (heart + circle)", 2, results.size());
        for (EmojiParser.EmojiEntry entry : results) {
            assertEquals(EmojiParser.ColorName.RED, entry.getColorName());
        }
    }

    @Test
    public void testFilterBySkinTone() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .skinTone(EmojiParser.SkinTone.LIGHT)
                .results();
        assertEquals("Should find 1 light skin tone emoji", 1, results.size());
        assertEquals(EmojiParser.SkinTone.LIGHT, results.get(0).getSkinTone());
    }

    @Test
    public void testFilterByQuery() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .query("heart")
                .results();
        assertEquals("Should find 10 heart emoji", 10, results.size());
    }

    @Test
    public void testFilterChainedGroupAndColor() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .group("Symbols")
                .colorName(EmojiParser.ColorName.RED)
                .results();
        assertEquals("Should find 2 red Symbols (heart + circle)", 2, results.size());
    }

    @Test
    public void testFilterChainedColorAndQuery() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .colorName(EmojiParser.ColorName.RED)
                .query("heart")
                .results();
        assertEquals("Should find 1 red heart", 1, results.size());
        assertEquals("red heart", results.get(0).getName());
    }

    @Test
    public void testFilterCount() {
        int count = parser.filter()
                .group("Symbols")
                .count();
        assertEquals("Should count 10 Symbols", 10, count);
    }

    @Test
    public void testFilterAvailableGroups() {
        List<String> groups = parser.filter()
                .colorName(EmojiParser.ColorName.RED)
                .availableGroups();
        assertEquals("Red emoji should be in 1 group", 1, groups.size());
        assertEquals("Symbols", groups.get(0));
    }

    @Test
    public void testFilterAvailableSubgroups() {
        List<String> subgroups = parser.filter()
                .group("Symbols")
                .availableSubgroups();
        assertEquals("Symbols should have 2 subgroups", 2, subgroups.size());
        assertTrue(subgroups.contains("heart"));
        assertTrue(subgroups.contains("geometric"));
    }

    @Test
    public void testFilterAvailableColorNames() {
        List<EmojiParser.ColorName> colors = parser.filter()
                .group("Symbols")
                .subgroup("heart")
                .availableColorNames();
        // heart subgroup has: red, orange, yellow, green, blue, purple, black, white
        assertEquals("Should have 8 color names in hearts", 8, colors.size());
    }

    @Test
    public void testFilterAvailableSkinTones() {
        List<EmojiParser.SkinTone> tones = parser.filter()
                .group("People & Body")
                .availableSkinTones();
        assertEquals("Should have 1 skin tone in test data", 1, tones.size());
        assertEquals(EmojiParser.SkinTone.LIGHT, tones.get(0));
    }

    @Test
    public void testFilterAvailableColors() {
        List<EmojiParser.EmojiColor> colors = parser.filter()
                .subgroup("geometric")
                .availableColors();
        assertEquals("Geometric should have 2 colors (red, blue)", 2, colors.size());
    }

    @Test
    public void testFilterBreadcrumbs() {
        EmojiParser.EmojiFilter criteria = parser.filter()
                .group("Symbols")
                .subgroup("heart")
                .colorName(EmojiParser.ColorName.RED)
                .query("heart")
                .getCriteria();
        List<String> breadcrumbs = criteria.toBreadcrumbs();
        assertEquals(4, breadcrumbs.size());
        assertEquals("Symbols", breadcrumbs.get(0));
        assertEquals("heart", breadcrumbs.get(1));
        assertEquals("red", breadcrumbs.get(2));
        assertEquals("\"heart\"", breadcrumbs.get(3));
    }

    @Test
    public void testFilterToString() {
        String str = parser.filter()
                .group("Symbols")
                .colorName(EmojiParser.ColorName.RED)
                .getCriteria()
                .toString();
        assertEquals("Symbols > red", str);
    }

    @Test
    public void testGetSubgroups() {
        List<EmojiParser.EmojiSubgroup> subgroups = parser.getSubgroups("Smileys & Emotion");
        assertEquals(3, subgroups.size());
        assertEquals("face-smiling", subgroups.get(0).getName());
    }

    @Test
    public void testGetSubgroupNames() {
        List<String> names = parser.getSubgroupNames("Symbols");
        assertEquals(2, names.size());
        assertTrue(names.contains("heart"));
        assertTrue(names.contains("geometric"));
    }

    @Test
    public void testGetAvailableSkinTones() {
        List<EmojiParser.SkinTone> tones = parser.getAvailableSkinTones();
        assertEquals("Test data has 1 skin tone emoji", 1, tones.size());
        assertEquals(EmojiParser.SkinTone.LIGHT, tones.get(0));
    }

    @Test
    public void testGetAvailableColorNames() {
        List<EmojiParser.ColorName> colors = parser.getAvailableColorNames();
        // Test data has: red, orange, yellow, green, blue, purple, black, white
        assertEquals("Test data has 8 color names", 8, colors.size());
    }

    @Test
    public void testFilterEmptyResults() {
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .group("Symbols")
                .skinTone(EmojiParser.SkinTone.DARK)
                .results();
        assertTrue("No dark skin tone in Symbols", results.isEmpty());
    }

    @Test
    public void testFilterNoFilters() {
        List<EmojiParser.EmojiEntry> results = parser.filter().results();
        assertEquals("No filters should return all fully-qualified", 21, results.size());
    }

    // ==================== Unified EmojiColorValue Tests ====================

    @Test
    public void testEmojiColorValueNamed() {
        EmojiParser.EmojiColorValue.Named red = new EmojiParser.EmojiColorValue.Named(EmojiParser.ColorName.RED);
        assertEquals("#FF0000", red.getHex());
        assertEquals("red", red.getDisplayName());
        assertFalse(red.isSkinTone());
    }

    @Test
    public void testEmojiColorValueSkin() {
        EmojiParser.EmojiColorValue.Skin light = new EmojiParser.EmojiColorValue.Skin(EmojiParser.SkinTone.LIGHT);
        assertEquals("#FFDFC4", light.getHex());
        assertEquals("light skin tone", light.getDisplayName());
        assertTrue(light.isSkinTone());
    }

    @Test
    public void testEmojiColorValueAll() {
        List<EmojiParser.EmojiColorValue> all = EmojiParser.EmojiColorValue.all();
        // 12 named colors + 5 skin tones = 17
        assertEquals(17, all.size());
    }

    @Test
    public void testEmojiColorValueToEmojiColor() {
        EmojiParser.EmojiColorValue.Named red = new EmojiParser.EmojiColorValue.Named(EmojiParser.ColorName.RED);
        EmojiParser.EmojiColor color = red.toEmojiColor();
        assertEquals("#FF0000", color.getHex());
        assertEquals("red", color.getName());
    }

    @Test
    public void testFilterByColorValue() {
        EmojiParser.EmojiColorValue redValue = new EmojiParser.EmojiColorValue.Named(EmojiParser.ColorName.RED);
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .color(redValue)
                .results();
        assertEquals("Should find 2 red emoji", 2, results.size());
    }

    @Test
    public void testFilterByColorValueSkin() {
        EmojiParser.EmojiColorValue skinValue = new EmojiParser.EmojiColorValue.Skin(EmojiParser.SkinTone.LIGHT);
        List<EmojiParser.EmojiEntry> results = parser.filter()
                .color(skinValue)
                .results();
        assertEquals("Should find 1 light skin tone emoji", 1, results.size());
    }

    @Test
    public void testEntryColorValue() {
        // Red heart should have Named color value
        List<EmojiParser.EmojiEntry> redHearts = parser.search("red heart");
        assertFalse(redHearts.isEmpty());
        EmojiParser.EmojiColorValue colorValue = redHearts.get(0).getColorValue();
        assertNotNull(colorValue);
        assertTrue(colorValue instanceof EmojiParser.EmojiColorValue.Named);
        assertEquals(EmojiParser.ColorName.RED, ((EmojiParser.EmojiColorValue.Named) colorValue).getColor());
    }

    @Test
    public void testEntryColorValueSkinTone() {
        // Waving hand with light skin tone should have Skin color value
        List<EmojiParser.EmojiEntry> results = parser.search("light skin tone");
        assertFalse(results.isEmpty());
        EmojiParser.EmojiColorValue colorValue = results.get(0).getColorValue();
        assertNotNull(colorValue);
        assertTrue(colorValue instanceof EmojiParser.EmojiColorValue.Skin);
        assertEquals(EmojiParser.SkinTone.LIGHT, ((EmojiParser.EmojiColorValue.Skin) colorValue).getTone());
    }

    @Test
    public void testGetAvailableColorValues() {
        List<EmojiParser.EmojiColorValue> values = parser.getAvailableColorValues();
        // 8 named colors + 1 skin tone in test data = 9
        assertEquals("Test data has 9 color values", 9, values.size());
    }

    @Test
    public void testFilterAvailableColorValues() {
        List<EmojiParser.EmojiColorValue> values = parser.filter()
                .group("Symbols")
                .availableColorValues();
        // Symbols has 8 colored hearts + 2 colored circles (same colors)
        assertEquals("Symbols should have 8 color values", 8, values.size());
        // All should be Named (no skin tones in Symbols)
        for (EmojiParser.EmojiColorValue value : values) {
            assertFalse("Symbols colors should not be skin tones", value.isSkinTone());
        }
    }

    @Test
    public void testFilterCriteriaColorAccessors() {
        EmojiParser.EmojiFilter criteria = parser.filter()
                .colorName(EmojiParser.ColorName.RED)
                .getCriteria();
        assertEquals(EmojiParser.ColorName.RED, criteria.getColorName());
        assertNull(criteria.getSkinTone());
    }

    @Test
    public void testFilterCriteriaSkinToneAccessors() {
        EmojiParser.EmojiFilter criteria = parser.filter()
                .skinTone(EmojiParser.SkinTone.LIGHT)
                .getCriteria();
        assertEquals(EmojiParser.SkinTone.LIGHT, criteria.getSkinTone());
        assertNull(criteria.getColorName());
    }

    // ==================== Symbol Parsing Tests ====================

    @Test
    public void testSymbolParsing() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        assertNotNull("Test resource symbols-test-sample.txt should exist", symbolStream);
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        // Should have 7 groups: IPA Phonetics, Arrows, Mathematics, Currency, Punctuation, Geometric Shapes, Dingbats
        assertEquals("Should parse 7 symbol groups", 7, symbolParser.getGroups().size());
    }

    @Test
    public void testSymbolGroupNames() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<String> names = symbolParser.getGroupNames();
        assertTrue(names.contains("Arrows"));
        assertTrue(names.contains("Mathematics"));
        assertTrue(names.contains("Currency"));
        assertTrue(names.contains("Punctuation"));
        assertTrue(names.contains("Geometric Shapes"));
        assertTrue(names.contains("Dingbats"));
    }

    @Test
    public void testSymbolEntry() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        // Search for arrow
        List<EmojiParser.EmojiEntry> arrows = symbolParser.search("leftwards arrow");
        assertFalse("Should find leftwards arrow", arrows.isEmpty());
        EmojiParser.EmojiEntry arrow = arrows.get(0);
        assertEquals("←", arrow.getEmoji());
        assertEquals("Arrows", arrow.getGroup());
        assertEquals("arrows-basic", arrow.getSubgroup());
    }

    @Test
    public void testSymbolMathOperators() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<EmojiParser.EmojiEntry> results = symbolParser.filter()
                .group("Mathematics")
                .subgroup("math-operators")
                .results();
        assertEquals("Should find 3 math operators", 3, results.size());
    }

    @Test
    public void testSymbolCurrency() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<EmojiParser.EmojiEntry> currency = symbolParser.filter()
                .group("Currency")
                .results();
        assertEquals("Should find 4 currency symbols", 4, currency.size());
    }

    @Test
    public void testSymbolGreekLetters() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<EmojiParser.EmojiEntry> greek = symbolParser.search("greek");
        assertEquals("Should find 3 greek letters", 3, greek.size());
    }

    @Test
    public void testCombinedEmojiAndSymbols() {
        InputStream emojiStream = getClass().getClassLoader().getResourceAsStream("emoji-test-sample.txt");
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        assertNotNull(emojiStream);
        assertNotNull(symbolStream);

        EmojiParser combinedParser = EmojiParser.parseFrom(emojiStream, symbolStream);

        // Should have emoji groups + symbol groups
        // Emoji: Smileys & Emotion, People & Body, Component, Animals & Nature, Symbols (5)
        // Symbols: IPA Phonetics, Arrows, Mathematics, Currency, Punctuation, Geometric Shapes, Dingbats (7)
        assertEquals("Combined should have 12 groups", 12, combinedParser.getGroups().size());

        // Should be able to search both emoji and symbols
        List<EmojiParser.EmojiEntry> hearts = combinedParser.search("heart");
        assertFalse("Should find hearts from emoji", hearts.isEmpty());

        List<EmojiParser.EmojiEntry> arrows = combinedParser.search("arrow");
        assertFalse("Should find arrows from symbols", arrows.isEmpty());
    }

    @Test
    public void testSymbolCheckmarks() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<EmojiParser.EmojiEntry> checkmarks = symbolParser.filter()
                .subgroup("dingbats-checkmarks")
                .results();
        assertEquals("Should find 4 checkmark symbols", 4, checkmarks.size());

        // Verify specific symbols
        List<EmojiParser.EmojiEntry> checkMark = symbolParser.search("check mark");
        assertTrue("Should find check mark", checkMark.size() >= 2);
    }

    @Test
    public void testSymbolGeometric() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        // Squares
        List<EmojiParser.EmojiEntry> squares = symbolParser.search("square");
        assertEquals("Should find 2 squares", 2, squares.size());

        // Circles
        List<EmojiParser.EmojiEntry> circles = symbolParser.search("circle");
        assertEquals("Should find 2 circles", 2, circles.size());
    }

    // ==================== IPA Phonetics Tests ====================

    @Test
    public void testIPAPhoneticsGroup() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        // Should have IPA Phonetics as a group
        List<String> groups = symbolParser.getGroupNames();
        assertTrue("Should have IPA Phonetics group", groups.contains("IPA Phonetics"));
    }

    @Test
    public void testIPAVowels() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        // Front vowels
        List<EmojiParser.EmojiEntry> frontVowels = symbolParser.filter()
                .subgroup("ipa-vowels-front")
                .results();
        assertEquals("Should find 4 front vowels", 4, frontVowels.size());

        // Schwa search
        List<EmojiParser.EmojiEntry> schwa = symbolParser.search("schwa");
        assertEquals("Should find 2 schwa variants", 2, schwa.size());
    }

    @Test
    public void testIPAConsonants() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        // Plosives
        List<EmojiParser.EmojiEntry> plosives = symbolParser.filter()
                .subgroup("ipa-consonants-plosives")
                .results();
        assertEquals("Should find 3 plosives", 3, plosives.size());

        // Fricatives
        List<EmojiParser.EmojiEntry> fricatives = symbolParser.filter()
                .subgroup("ipa-consonants-fricatives")
                .results();
        assertEquals("Should find 4 fricatives", 4, fricatives.size());
    }

    @Test
    public void testIPASuprasegmentals() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<EmojiParser.EmojiEntry> suprasegmentals = symbolParser.filter()
                .subgroup("ipa-suprasegmentals")
                .results();
        assertEquals("Should find 3 suprasegmentals", 3, suprasegmentals.size());

        // Search for stress markers
        List<EmojiParser.EmojiEntry> stress = symbolParser.search("stress");
        assertEquals("Should find 2 stress markers", 2, stress.size());
    }

    @Test
    public void testIPADiacritics() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<EmojiParser.EmojiEntry> diacritics = symbolParser.filter()
                .subgroup("ipa-diacritics-modifiers")
                .results();
        assertEquals("Should find 3 diacritics/modifiers", 3, diacritics.size());

        // Search for aspirated
        List<EmojiParser.EmojiEntry> aspirated = symbolParser.search("aspirated");
        assertFalse("Should find aspirated modifier", aspirated.isEmpty());
        assertEquals("ʰ", aspirated.get(0).getEmoji());
    }

    @Test
    public void testIPASubgroups() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        List<String> subgroups = symbolParser.getSubgroupNames("IPA Phonetics");
        assertEquals("IPA Phonetics should have 6 subgroups", 6, subgroups.size());
        assertTrue(subgroups.contains("ipa-vowels-front"));
        assertTrue(subgroups.contains("ipa-vowels-central"));
        assertTrue(subgroups.contains("ipa-consonants-plosives"));
        assertTrue(subgroups.contains("ipa-consonants-fricatives"));
        assertTrue(subgroups.contains("ipa-suprasegmentals"));
        assertTrue(subgroups.contains("ipa-diacritics-modifiers"));
    }

    @Test
    public void testIPASpecificSymbols() {
        InputStream symbolStream = getClass().getClassLoader().getResourceAsStream("symbols-test-sample.txt");
        EmojiParser symbolParser = EmojiParser.parseFrom(symbolStream);

        // Glottal stop
        List<EmojiParser.EmojiEntry> glottal = symbolParser.search("glottal stop");
        assertFalse("Should find glottal stop", glottal.isEmpty());
        assertEquals("ʔ", glottal.get(0).getEmoji());

        // Esh (sh sound)
        List<EmojiParser.EmojiEntry> esh = symbolParser.search("postalveolar fricative");
        assertEquals("Should find 2 postalveolar fricatives", 2, esh.size());
    }
}
