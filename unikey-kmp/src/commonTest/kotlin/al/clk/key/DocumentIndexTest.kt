package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class DocumentIndexTest {

    // ═══ DocTag Tests ═══

    @Test
    fun testDocTagFromName() {
        assertEquals(DocTag.P, DocTag.fromName("p"))
        assertEquals(DocTag.P, DocTag.fromName("P"))
        assertEquals(DocTag.H1, DocTag.fromName("h1"))
        assertEquals(DocTag.LI, DocTag.fromName("LI"))
        assertNull(DocTag.fromName("unknown"))
    }

    @Test
    fun testDocTagPatterns() {
        val h1 = DocTag.H1
        assertNotNull(h1.htmlRegex())
        assertNotNull(h1.mdRegex())
        assertTrue(h1.hasMdPattern)

        val span = DocTag.SPAN
        assertFalse(span.hasMdPattern)
        assertNull(span.mdRegex())
    }

    @Test
    fun testDocTagProperties() {
        assertTrue(DocTag.P.isBlock)
        assertFalse(DocTag.SPAN.isBlock)
        assertTrue(DocTag.DIV.isContainer)
        assertFalse(DocTag.P.isContainer)
    }

    // ═══ TagPattern Tests ═══

    @Test
    fun testTagPatternToRegex() {
        val pattern = TagPattern("<h1", "[^>]*", ">")
        val regex = pattern.toRegex("H1")

        val match = regex.find("<h1 class=\"title\">")
        assertNotNull(match)

        val groups = match.groups
        assertNotNull(groups["H1_prefix"])
        assertNotNull(groups["H1_middle"])
        assertNotNull(groups["H1_suffix"])
    }

    // ═══ IndexSegment Tests ═══

    @Test
    fun testIndexSegmentFormatted() {
        val segment = IndexSegment(DocTag.P, 1)
        assertEquals("P1", segment.formatted)

        val segmentWithClass = IndexSegment(DocTag.P, 2, listOf("intro", "poem"))
        assertEquals("P2.intro.poem", segmentWithClass.formatted)

        val segmentWithAttrs = IndexSegment(
            DocTag.P, 3,
            listOf("verse"),
            mapOf("data-index" to "5")
        )
        assertEquals("P3.verse=data-index=5", segmentWithAttrs.formatted)
    }

    @Test
    fun testIndexSegmentParse() {
        val segment1 = IndexSegment.parse("P1")
        assertNotNull(segment1)
        assertEquals(DocTag.P, segment1.tag)
        assertEquals(1, segment1.instance)
        assertTrue(segment1.cssClasses.isEmpty())

        val segment2 = IndexSegment.parse("LI23.item.active")
        assertNotNull(segment2)
        assertEquals(DocTag.LI, segment2.tag)
        assertEquals(23, segment2.instance)
        assertEquals(listOf("item", "active"), segment2.cssClasses)

        val segment3 = IndexSegment.parse("DIV1.container=data-id=abc")
        assertNotNull(segment3)
        assertEquals(DocTag.DIV, segment3.tag)
        assertEquals(1, segment3.instance)
        assertEquals(listOf("container"), segment3.cssClasses)
        assertEquals("abc", segment3.dataAttrs["data-id"])
    }

    @Test
    fun testIndexSegmentParseInvalid() {
        assertNull(IndexSegment.parse(""))
        assertNull(IndexSegment.parse("P"))        // Missing instance
        assertNull(IndexSegment.parse("UNKNOWN1")) // Unknown tag
        assertNull(IndexSegment.parse("123"))      // No tag name
    }

    // ═══ DocumentIndex Tests ═══

    @Test
    fun testDocumentIndexFormatted() {
        val index = DocumentIndex(listOf(
            IndexSegment(DocTag.HTML, 1),
            IndexSegment(DocTag.BODY, 1),
            IndexSegment(DocTag.P, 1, listOf("intro"))
        ))

        assertEquals("HTML1/BODY1/P1.intro", index.formatted)
        assertEquals(3, index.depth)
    }

    @Test
    fun testDocumentIndexParse() {
        val index = DocumentIndex.parse("HTML1/BODY1/DIV1.container/P1.verse=data-index=1")
        assertNotNull(index)
        assertEquals(4, index.depth)

        val leaf = index.leaf
        assertNotNull(leaf)
        assertEquals(DocTag.P, leaf.tag)
        assertEquals(1, leaf.instance)
        assertEquals(listOf("verse"), leaf.cssClasses)
        assertEquals("1", leaf.dataAttrs["data-index"])
    }

    @Test
    fun testDocumentIndexParseInvalid() {
        assertNull(DocumentIndex.parse(""))
        assertNull(DocumentIndex.parse("HTML1/UNKNOWN1/P1"))
    }

    @Test
    fun testDocumentIndexParent() {
        val index = DocumentIndex.parse("HTML1/BODY1/P1")
        assertNotNull(index)

        val parent = index.parent()
        assertNotNull(parent)
        assertEquals("HTML1/BODY1", parent.formatted)

        val grandparent = parent.parent()
        assertNotNull(grandparent)
        assertEquals("HTML1", grandparent.formatted)

        assertNull(grandparent.parent())
    }

    @Test
    fun testDocumentIndexChild() {
        val parent = DocumentIndex.parse("HTML1/BODY1")
        assertNotNull(parent)

        val segment = IndexSegment(DocTag.P, 1, listOf("verse"))
        val child = parent.child(segment)

        assertEquals("HTML1/BODY1/P1.verse", child.formatted)
        assertEquals(3, child.depth)
    }

    @Test
    fun testDocumentIndexIsDescendantOf() {
        val ancestor = DocumentIndex.parse("HTML1/BODY1")
        val descendant = DocumentIndex.parse("HTML1/BODY1/DIV1/P1")
        val notDescendant = DocumentIndex.parse("HTML1/HEAD1/TITLE1")

        assertNotNull(ancestor)
        assertNotNull(descendant)
        assertNotNull(notDescendant)

        assertTrue(descendant.isDescendantOf(ancestor))
        assertFalse(notDescendant.isDescendantOf(ancestor))
        assertFalse(ancestor.isDescendantOf(descendant))
    }

    @Test
    fun testDocumentIndexRoot() {
        val root = DocumentIndex.root()
        assertEquals(0, root.depth)
        assertEquals("", root.formatted)
        assertNull(root.leaf)
    }

    // ═══ DocumentParser Tests ═══

    @Test
    fun testParseHtmlBasic() {
        val html = "<html><body><p>Hello</p></body></html>"
        val parser = DocumentParser()
        val elements = parser.parseHtml(html)

        assertTrue(elements.isNotEmpty())

        val pElement = elements.find { it.tag == DocTag.P }
        assertNotNull(pElement)
        assertEquals("Hello", pElement.content)
    }

    @Test
    fun testParseHtmlWithClasses() {
        val html = """<div class="container wrap"><p class="intro verse">Text</p></div>"""
        val parser = DocumentParser()
        val elements = parser.parseHtml(html)

        val divElement = elements.find { it.tag == DocTag.DIV }
        assertNotNull(divElement)
        assertEquals(listOf("container", "wrap"), divElement.index.leaf?.cssClasses)

        val pElement = elements.find { it.tag == DocTag.P }
        assertNotNull(pElement)
        assertEquals(listOf("intro", "verse"), pElement.index.leaf?.cssClasses)
    }

    @Test
    fun testParseHtmlWithDataAttrs() {
        val html = """<p data-index="5" data-verse="2">Content</p>"""
        val parser = DocumentParser()
        val elements = parser.parseHtml(html)

        val pElement = elements.first { it.tag == DocTag.P }
        assertEquals("5", pElement.attributes["data-index"])
        assertEquals("2", pElement.attributes["data-verse"])
    }

    @Test
    fun testParseMarkdownHeadings() {
        val md = """
            # Heading 1
            ## Heading 2
            ### Heading 3
            Normal paragraph
        """.trimIndent()

        val parser = DocumentParser()
        val elements = parser.parseMarkdown(md)

        val h1 = elements.find { it.tag == DocTag.H1 }
        val h2 = elements.find { it.tag == DocTag.H2 }
        val h3 = elements.find { it.tag == DocTag.H3 }

        assertNotNull(h1)
        assertNotNull(h2)
        assertNotNull(h3)

        assertEquals("H11", h1.index.formatted)
        assertEquals("H21", h2.index.formatted)
        assertEquals("H31", h3.index.formatted)
    }

    // ═══ DocumentIndexBuilder Tests ═══

    @Test
    fun testDocumentIndexBuilder() {
        val builder = DocumentIndexBuilder()

        val htmlIndex = builder.push(DocTag.HTML)
        assertEquals("HTML1", htmlIndex.formatted)

        val bodyIndex = builder.push(DocTag.BODY)
        assertEquals("HTML1/BODY1", bodyIndex.formatted)

        val p1Index = builder.push(DocTag.P, listOf("intro"))
        assertEquals("HTML1/BODY1/P1.intro", p1Index.formatted)

        builder.pop()

        val p2Index = builder.push(DocTag.P, listOf("verse"))
        assertEquals("HTML1/BODY1/P2.verse", p2Index.formatted)

        assertEquals(3, builder.depth())
    }

    @Test
    fun testDocumentIndexBuilderReset() {
        val builder = DocumentIndexBuilder()

        builder.push(DocTag.HTML)
        builder.push(DocTag.BODY)
        assertEquals(2, builder.depth())

        builder.reset()
        assertEquals(0, builder.depth())

        val newIndex = builder.push(DocTag.HTML)
        assertEquals("HTML1", newIndex.formatted)
    }

    // ═══ LineType Tests ═══

    @Test
    fun testLineTypeDetectHeaders() {
        assertEquals(LineType.HEADER1, LineType.detect("ALL CAPS HEADER"))
        assertEquals(LineType.HEADER2, LineType.detect("Title With Colon:"))
        assertEquals(LineType.HEADER3, LineType.detect("1. Numbered Title"))
        assertEquals(LineType.HEADER4, LineType.detect("1.1. Sub-numbered"))
        assertEquals(LineType.HEADER5, LineType.detect("a) lettered item"))
        assertEquals(LineType.HEADER6, LineType.detect("(a) parenthesized"))
    }

    @Test
    fun testLineTypeDetectLists() {
        assertEquals(LineType.OL_ITEM, LineType.detect("1. First item"))
        assertEquals(LineType.OL_ITEM, LineType.detect("23) Item 23"))
        assertEquals(LineType.UL_ITEM, LineType.detect("- Bullet item"))
        assertEquals(LineType.UL_ITEM, LineType.detect("* Star item"))
        assertEquals(LineType.UL_ITEM, LineType.detect("+ Plus item"))
    }

    @Test
    fun testLineTypeDetectPre() {
        assertEquals(LineType.PRE, LineType.detect("    indented code"))
        assertEquals(LineType.PRE, LineType.detect("\tindented with tab"))
    }

    @Test
    fun testLineTypeDetectParagraph() {
        assertEquals(LineType.PARAGRAPH, LineType.detect("Normal text line"))
        assertEquals(LineType.PARAGRAPH, LineType.detect("Mixed case text here"))
    }

    // ═══ PlainTextParser Tests ═══

    @Test
    fun testPlainTextParserBasic() {
        val text = """
            TITLE
            First paragraph
            Second paragraph
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        assertTrue(elements.isNotEmpty())

        // First line should be detected as header
        val first = elements.first()
        assertEquals(DocTag.H1, first.tag)
        assertTrue(first.content.contains("TITLE"))

        // Check path includes HTML/BODY
        assertTrue(first.index.formatted.startsWith("HTML1/BODY1"))
    }

    @Test
    fun testPlainTextParserLists() {
        val text = """
            1. First
            2. Second
            - Bullet one
            - Bullet two
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        assertEquals(4, elements.size)

        // Ordered list items should be in OL container
        assertTrue(elements[0].index.formatted.contains("OL1"))
        assertTrue(elements[1].index.formatted.contains("OL1"))

        // Unordered list items should be in UL container
        assertTrue(elements[2].index.formatted.contains("UL1"))
        assertTrue(elements[3].index.formatted.contains("UL1"))
    }

    @Test
    fun testPlainTextParserIndices() {
        val text = "Line one\nLine two\nLine three"
        val parser = PlainTextParser()
        val indices = parser.parseIndices(text)

        assertEquals(3, indices.size)
        assertEquals("HTML1/BODY1/P1", indices[0].formatted)
        assertEquals("HTML1/BODY1/P2", indices[1].formatted)
        assertEquals("HTML1/BODY1/P3", indices[2].formatted)
    }

    @Test
    fun testPlainTextParserHebrewPoetry() {
        // Test with Hebrew poetry document format
        val text = """
            # ש.צ. — שירי צבא
            **מחבר:** תֹּאמַר בַּת-שְׁלֹמֹה
            ## 1. אני - רוצה להתגייס אבל...
            אני רוצה - להתגייס,
            אבל אני - רק מתבאס.
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        assertTrue(elements.isNotEmpty())
        // Should have indices for each non-blank line
        assertTrue(elements.size >= 5)
    }
}
