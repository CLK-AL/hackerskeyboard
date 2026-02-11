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
}
