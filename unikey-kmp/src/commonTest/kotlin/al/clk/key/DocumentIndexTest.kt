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
    fun testDocTagInlineFormatting() {
        // Test B, I, U and other inline tags
        assertNotNull(DocTag.fromName("b"))
        assertNotNull(DocTag.fromName("i"))
        assertNotNull(DocTag.fromName("u"))
        assertNotNull(DocTag.fromName("s"))
        assertNotNull(DocTag.fromName("del"))
        assertNotNull(DocTag.fromName("ins"))
        assertNotNull(DocTag.fromName("mark"))
        assertNotNull(DocTag.fromName("sub"))
        assertNotNull(DocTag.fromName("sup"))
        assertNotNull(DocTag.fromName("small"))
        assertNotNull(DocTag.fromName("kbd"))
        assertNotNull(DocTag.fromName("code"))

        // These should be inline (not block)
        assertFalse(DocTag.B.isBlock)
        assertFalse(DocTag.I.isBlock)
        assertFalse(DocTag.U.isBlock)
        assertFalse(DocTag.SPAN.isBlock)
    }

    @Test
    fun testDocTagBidirectional() {
        // BDO and BDI for RTL/LTR text
        assertNotNull(DocTag.fromName("bdo"))
        assertNotNull(DocTag.fromName("bdi"))
        assertFalse(DocTag.BDO.isBlock)
        assertFalse(DocTag.BDI.isBlock)
    }

    // ═══ ScriptAttrs Tests ═══

    @Test
    fun testScriptAttrsBasic() {
        val hebrew = ScriptAttrs.HEBREW
        assertEquals("he", hebrew.lang)
        assertEquals(TextDir.RTL, hebrew.dir)

        val english = ScriptAttrs.ENGLISH
        assertEquals("en", english.lang)
        assertEquals(TextDir.LTR, english.dir)
    }

    @Test
    fun testScriptAttrsFormatted() {
        val attrs = ScriptAttrs("he", TextDir.RTL)
        assertEquals("@lang=he@dir=rtl", attrs.formatted)

        val langOnly = ScriptAttrs("ar", null)
        assertEquals("@lang=ar", langOnly.formatted)

        val dirOnly = ScriptAttrs(null, TextDir.RTL)
        assertEquals("@dir=rtl", dirOnly.formatted)
    }

    @Test
    fun testScriptAttrsParse() {
        val parsed = ScriptAttrs.parse("@lang=he@dir=rtl")
        assertEquals("he", parsed.lang)
        assertEquals(TextDir.RTL, parsed.dir)

        val partial = ScriptAttrs.parse("@dir=ltr")
        assertNull(partial.lang)
        assertEquals(TextDir.LTR, partial.dir)
    }

    @Test
    fun testScriptAttrsFromAttributes() {
        val attrs = mapOf("lang" to "he", "dir" to "rtl", "class" to "verse")
        val script = ScriptAttrs.fromAttributes(attrs)
        assertEquals("he", script.lang)
        assertEquals(TextDir.RTL, script.dir)
    }

    @Test
    fun testTextDirFromValue() {
        assertEquals(TextDir.LTR, TextDir.fromValue("ltr"))
        assertEquals(TextDir.RTL, TextDir.fromValue("rtl"))
        assertEquals(TextDir.AUTO, TextDir.fromValue("auto"))
        assertNull(TextDir.fromValue("invalid"))
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
            ScriptAttrs.EMPTY,
            mapOf("data-index" to "5")
        )
        assertEquals("P3.verse=data-index=5", segmentWithAttrs.formatted)
    }

    @Test
    fun testIndexSegmentWithScriptAttrs() {
        val segment = IndexSegment(
            DocTag.P, 1,
            listOf("verse"),
            ScriptAttrs.HEBREW,
            mapOf("data-index" to "1")
        )
        assertEquals("P1.verse@lang=he@dir=rtl=data-index=1", segment.formatted)
        assertEquals("he", segment.lang)
        assertEquals(TextDir.RTL, segment.dir)
        assertTrue(segment.isRtl)
    }

    @Test
    fun testIndexSegmentParseWithScriptAttrs() {
        val segment = IndexSegment.parse("P1.verse@lang=he@dir=rtl=data-index=1")
        assertNotNull(segment)
        assertEquals(DocTag.P, segment.tag)
        assertEquals(1, segment.instance)
        assertEquals(listOf("verse"), segment.cssClasses)
        assertEquals("he", segment.lang)
        assertEquals(TextDir.RTL, segment.dir)
        assertEquals("1", segment.dataAttrs["data-index"])
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
    fun testParseHtmlWithScriptAttrs() {
        val html = """<p lang="he" dir="rtl" class="verse">שלום</p>"""
        val parser = DocumentParser()
        val elements = parser.parseHtml(html)

        val pElement = elements.first { it.tag == DocTag.P }
        assertEquals("he", pElement.attributes["lang"])
        assertEquals("rtl", pElement.attributes["dir"])

        // Check that IndexSegment has script attrs
        val segment = pElement.index.leaf
        assertNotNull(segment)
        assertEquals("he", segment.lang)
        assertEquals(TextDir.RTL, segment.dir)
        assertTrue(segment.isRtl)
    }

    @Test
    fun testParseHtmlInlineTags() {
        val html = """<p>This is <b>bold</b> and <i>italic</i> and <u>underlined</u></p>"""
        val parser = DocumentParser()
        val elements = parser.parseHtml(html)

        assertTrue(elements.any { it.tag == DocTag.B })
        assertTrue(elements.any { it.tag == DocTag.I })
        assertTrue(elements.any { it.tag == DocTag.U })
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

        // Ordered list items should be in OL container with full path
        assertTrue(elements[0].index.formatted.contains("OL"))
        assertTrue(elements[0].index.formatted.contains("LI"))
        assertTrue(elements[1].index.formatted.contains("OL"))

        // Unordered list items should be in UL container
        assertTrue(elements[2].index.formatted.contains("UL"))
        assertTrue(elements[2].index.formatted.contains("LI"))
        assertTrue(elements[3].index.formatted.contains("UL"))
    }

    @Test
    fun testPlainTextParserIndices() {
        val text = "Line one\nLine two\nLine three"
        val parser = PlainTextParser()
        val indices = parser.parseIndices(text)

        assertEquals(3, indices.size)
        // All paths start with HTML1/BODY1
        assertTrue(indices[0].formatted.startsWith("HTML1/BODY1"))
        assertTrue(indices[1].formatted.startsWith("HTML1/BODY1"))
        assertTrue(indices[2].formatted.startsWith("HTML1/BODY1"))
        // Each should end with P tag
        assertTrue(indices[0].formatted.contains("/P"))
        assertTrue(indices[1].formatted.contains("/P"))
        assertTrue(indices[2].formatted.contains("/P"))
    }

    @Test
    fun testPlainTextParserFullPath() {
        // Test complete nested paths
        val text = """
            MAIN TITLE
            1. First item
            2. Second item
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        // Header should have DIV container: HTML1/BODY1/DIV1/H1
        val header = elements[0]
        assertEquals(DocTag.H1, header.tag)
        assertTrue(header.index.formatted.contains("DIV"))
        assertTrue(header.index.formatted.contains("H1"))

        // List items should have OL container: HTML1/BODY1/DIV1/OL1/LI1
        val li1 = elements[1]
        assertEquals(DocTag.LI, li1.tag)
        assertTrue(li1.index.formatted.contains("DIV"))
        assertTrue(li1.index.formatted.contains("OL"))
        assertTrue(li1.index.formatted.contains("LI"))
    }

    @Test
    fun testPlainTextParserTable() {
        val text = """
            NAME|AGE|CITY
            John|30|NYC
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        assertEquals(2, elements.size)

        // Table header: HTML1/BODY1/TABLE1/TR1/TH1
        val th = elements[0]
        assertEquals(DocTag.TH, th.tag)
        assertTrue(th.index.formatted.contains("TABLE"))
        assertTrue(th.index.formatted.contains("TR"))
        assertTrue(th.index.formatted.contains("TH"))

        // Table row: HTML1/BODY1/TABLE1/TR2/TD1
        val td = elements[1]
        assertEquals(DocTag.TD, td.tag)
        assertTrue(td.index.formatted.contains("TABLE"))
        assertTrue(td.index.formatted.contains("TR"))
        assertTrue(td.index.formatted.contains("TD"))
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
