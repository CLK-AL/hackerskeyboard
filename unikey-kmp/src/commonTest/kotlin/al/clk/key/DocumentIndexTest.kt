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
        val pattern = TagPattern.html("h1")
        val regex = pattern.toRegex("H1")

        val match = regex.find("<h1 class=\"title\">")
        assertNotNull(match)

        val groups = match.groups
        assertNotNull(groups["H1_prefix"])
        assertNotNull(groups["H1_attr"])
        assertNotNull(groups["H1_suffix"])
    }

    @Test
    fun testTagPatternHtml() {
        val pattern = TagPattern.html("p")
        assertEquals("<p", pattern.prefix)
        assertEquals("</p>", pattern.closeTag)

        val selfClosing = TagPattern.html("br", selfClosing = true)
        assertEquals("<br", selfClosing.prefix)
        assertEquals("", selfClosing.closeTag)
    }

    @Test
    fun testTagPatternMd() {
        val pattern = TagPattern.md("^#\\s+", ".*", "$")
        assertEquals("^#\\s+", pattern.prefix)
        assertEquals("", pattern.closeTag)
    }

    @Test
    fun testTagPatternCloseRegex() {
        val pattern = TagPattern.html("div")
        val closeRegex = pattern.closeRegex("DIV")
        assertNotNull(closeRegex)
        assertTrue(closeRegex.matches("</div>"))
        assertTrue(closeRegex.matches("</DIV>"))
    }

    // ═══ AttrPattern Tests ═══

    @Test
    fun testAttrPatternParseAll() {
        val attrs = AttrPattern.parseAll("""lang="he" dir="rtl" class="verse poem" data-index="5"""")
        assertEquals("he", attrs["lang"])
        assertEquals("rtl", attrs["dir"])
        assertEquals("verse poem", attrs["class"])
        assertEquals("5", attrs["data-index"])
    }

    @Test
    fun testAttrPatternParseScriptAttrs() {
        val script = AttrPattern.parseScriptAttrs("""lang="he" dir="rtl" class="test"""")
        assertEquals("he", script.lang)
        assertEquals(TextDir.RTL, script.dir)
    }

    @Test
    fun testAttrPatternParseClasses() {
        val classes = AttrPattern.parseClasses("""id="main" class="container wrap fluid"""")
        assertEquals(listOf("container", "wrap", "fluid"), classes)
    }

    @Test
    fun testAttrPatternParseDataAttrs() {
        val data = AttrPattern.parseDataAttrs("""data-index="5" data-verse="2" class="test"""")
        assertEquals("5", data["data-index"])
        assertEquals("2", data["data-verse"])
        assertEquals(2, data.size)  // Only data-* attributes
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

    // ═══ ScriptDetector Tests ═══

    @Test
    fun testScriptDetectorHebrew() {
        assertTrue(ScriptDetector.isHebrew("שלום"))
        assertTrue(ScriptDetector.isRtl("שלום עולם"))
        assertFalse(ScriptDetector.isHebrew("Hello"))

        val attrs = ScriptDetector.detect("שלום")
        assertEquals("he", attrs.lang)
        assertEquals(TextDir.RTL, attrs.dir)
    }

    @Test
    fun testScriptDetectorArabic() {
        assertTrue(ScriptDetector.isArabic("مرحبا"))
        assertTrue(ScriptDetector.isRtl("مرحبا"))

        val attrs = ScriptDetector.detect("مرحبا")
        assertEquals("ar", attrs.lang)
        assertEquals(TextDir.RTL, attrs.dir)
    }

    @Test
    fun testScriptDetectorEnglish() {
        assertFalse(ScriptDetector.isRtl("Hello World"))

        val attrs = ScriptDetector.detect("Hello World")
        assertEquals("en", attrs.lang)
        assertEquals(TextDir.LTR, attrs.dir)
    }

    @Test
    fun testScriptDetectorMixed() {
        // Dominant script wins
        val hebrewDominant = ScriptDetector.detect("שלום Hello עולם")
        assertEquals("he", hebrewDominant.lang)

        val englishDominant = ScriptDetector.detect("Hello World שלום")
        assertEquals("en", englishDominant.lang)
    }

    // ═══ LineType DT/DD Tests ═══

    @Test
    fun testLineTypeDefinitionList() {
        // DD item (: description)
        assertEquals(LineType.DD_ITEM, LineType.detect(": This is a description"))

        // DD item (indented)
        assertEquals(LineType.DD_ITEM, LineType.detect("  Indented description"))

        // Inline DT:DD format (term: description)
        assertEquals(LineType.DT_DD_INLINE, LineType.detect("term: description"))
        assertEquals(LineType.DT_DD_INLINE, LineType.detect("**bold term**: value"))
        assertEquals(LineType.DT_DD_INLINE, LineType.detect("מחבר: תֹּאמַר"))  // Hebrew
    }

    @Test
    fun testPlainTextParserInlineDefinition() {
        val text = """
            **מחבר:** תֹּאמַר בַּת-שְׁלֹמֹה
            **מקור:** ARMYSNGE.WRI
            **ספר:** חוב אש פרחי הזית
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        // Should create DL elements with paired DT/DD
        val dlElements = elements.filter { it.tag == DocTag.DL }
        assertTrue(dlElements.isNotEmpty())
        assertEquals(3, dlElements.size)  // 3 definition pairs

        // Check that pairs are stored in the index
        val firstDl = dlElements.first()
        val pairs = firstDl.index.leaf?.defPairs
        assertNotNull(pairs)
        assertTrue(pairs.isNotEmpty())

        // Check term and description are paired
        val firstPair = pairs.first()
        assertTrue(firstPair.term.contains("מחבר"))
        assertTrue(firstPair.description.contains("תֹּאמַר"))
    }

    // ═══ DefinitionPair Tests ═══

    @Test
    fun testDefinitionPairFromLine() {
        val pair = DefinitionPair.fromLine("**מחבר:** תֹּאמַר בַּת-שְׁלֹמֹה")
        assertNotNull(pair)
        assertEquals("מחבר", pair.term)
        assertEquals("תֹּאמַר בַּת-שְׁלֹמֹה", pair.description)
        assertEquals("he", pair.termScript.lang)
        assertEquals("he", pair.descScript.lang)
    }

    @Test
    fun testDefinitionPairFormatted() {
        val pair = DefinitionPair("term", "value")
        assertEquals("[term=value]", pair.formatted)
    }

    @Test
    fun testDefinitionPairParse() {
        val pair = DefinitionPair.parse("[author=John Doe]")
        assertNotNull(pair)
        assertEquals("author", pair.term)
        assertEquals("John Doe", pair.description)
    }

    @Test
    fun testIndexSegmentWithPairs() {
        val pairs = listOf(
            DefinitionPair("מחבר", "תֹּאמַר", ScriptAttrs.HEBREW, ScriptAttrs.HEBREW),
            DefinitionPair("מקור", "file.txt", ScriptAttrs.HEBREW, ScriptAttrs.ENGLISH)
        )
        val segment = IndexSegment(DocTag.DL, 1, emptyList(), ScriptAttrs.EMPTY, emptyMap(), pairs)

        assertEquals("תֹּאמַר", segment.getDefinition("מחבר"))
        assertEquals("file.txt", segment.getDefinition("מקור"))
        assertEquals(2, segment.definitions.size)
        assertTrue(segment.formatted.contains("[מחבר=תֹּאמַר]"))
    }

    @Test
    fun testLineTypeMdHeaders() {
        assertEquals(LineType.MD_H1, LineType.detect("# Heading 1"))
        assertEquals(LineType.MD_H2, LineType.detect("## Heading 2"))
        assertEquals(LineType.MD_H3, LineType.detect("### Heading 3"))
        assertEquals(LineType.MD_H4, LineType.detect("#### Heading 4"))
        assertEquals(LineType.MD_H5, LineType.detect("##### Heading 5"))
        assertEquals(LineType.MD_H6, LineType.detect("###### Heading 6"))
    }

    @Test
    fun testLineTypeCodeFence() {
        assertEquals(LineType.FENCE_START, LineType.detect("```kotlin"))
        assertEquals(LineType.FENCE_END, LineType.detect("```"))
    }

    @Test
    fun testLineTypeBlockquote() {
        assertEquals(LineType.BLOCKQUOTE, LineType.detect("> This is a quote"))
    }

    @Test
    fun testLineTypeWithScript() {
        val (type, script) = LineType.detectWithScript("# שלום")
        assertEquals(LineType.MD_H1, type)
        assertEquals("he", script.lang)
        assertEquals(TextDir.RTL, script.dir)
    }

    // ═══ RfcPattern Tests ═══

    @Test
    fun testRfcPatternKeywords() {
        assertTrue(RfcPattern.hasRfc2119Keywords("You MUST do this"))
        assertTrue(RfcPattern.hasRfc2119Keywords("This SHOULD work"))
        assertTrue(RfcPattern.hasRfc2119Keywords("You MAY skip this"))
        assertFalse(RfcPattern.hasRfc2119Keywords("No keywords here"))
    }

    @Test
    fun testRfcPatternRefs() {
        val refs = RfcPattern.findRfcRefs("See RFC 2119 and RFC 7230 for details")
        assertEquals(2, refs.size)
        assertTrue(refs.contains("RFC 2119"))
        assertTrue(refs.contains("RFC 7230"))
    }

    // ═══ PlainTextParser Script Detection Tests ═══

    @Test
    fun testPlainTextParserScriptDetection() {
        val text = """
            # שלום עולם
            This is English
            זהו טקסט בעברית
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        // Hebrew header should have RTL
        val hebrewHeader = elements.find { it.content.contains("שלום") }
        assertNotNull(hebrewHeader)
        assertEquals("rtl", hebrewHeader.attributes["dir"])

        // English paragraph should have LTR
        val englishPara = elements.find { it.content.contains("English") }
        assertNotNull(englishPara)
        assertEquals("ltr", englishPara.attributes["dir"])
    }

    // ═══ ContentStripper Tests ═══

    @Test
    fun testContentStripperHeaders() {
        // Markdown headers
        assertEquals("Title", ContentStripper.stripHeader("# Title"))
        assertEquals("Subtitle", ContentStripper.stripHeader("## Subtitle"))
        assertEquals("Deep Header", ContentStripper.stripHeader("###### Deep Header"))

        // With bold
        assertEquals("Bold Title", ContentStripper.stripHeader("# **Bold Title**"))
        assertEquals("Title", ContentStripper.stripHeader("**Title**"))
    }

    @Test
    fun testContentStripperListItems() {
        assertEquals("Item one", ContentStripper.stripListItem("- Item one"))
        assertEquals("Item two", ContentStripper.stripListItem("* Item two"))
        assertEquals("Numbered", ContentStripper.stripListItem("1. Numbered"))
        assertEquals("Also numbered", ContentStripper.stripListItem("23) Also numbered"))
    }

    @Test
    fun testContentStripperBlockquote() {
        assertEquals("Quoted text", ContentStripper.stripBlockquote("> Quoted text"))
        assertEquals("Multiple >", ContentStripper.stripBlockquote("> Multiple >"))
    }

    @Test
    fun testContentStripperFull() {
        assertEquals("Plain text", ContentStripper.strip("**Plain text**"))
        assertEquals("Code here", ContentStripper.strip("`Code here`"))
        assertEquals("Strike", ContentStripper.strip("~~Strike~~"))
    }

    @Test
    fun testContentStripperIsUnderline() {
        assertTrue(ContentStripper.isUnderline("==="))
        assertTrue(ContentStripper.isUnderline("---"))
        assertTrue(ContentStripper.isUnderline("====="))
        assertFalse(ContentStripper.isUnderline("=="))  // Too short
        assertFalse(ContentStripper.isUnderline("text"))
    }

    @Test
    fun testContentStripperGetDecoration() {
        assertEquals("#", ContentStripper.getDecoration("# Title")?.trim())
        assertEquals(">", ContentStripper.getDecoration("> Quote"))
        assertEquals("===", ContentStripper.getDecoration("==="))
        assertNull(ContentStripper.getDecoration("Plain text"))
    }

    @Test
    fun testPlainTextParserStripsDecorations() {
        val text = """
            # שלום עולם
            - List item
            > Quote
        """.trimIndent()

        val parser = PlainTextParser()
        val elements = parser.parse(text)

        // Header content should be stripped of # prefix
        val h1 = elements.find { it.tag == DocTag.H1 }
        assertNotNull(h1)
        assertEquals("שלום עולם", h1.content)
        assertNotNull(h1.attributes["decoration"])

        // List item content should be stripped of - prefix
        val li = elements.find { it.tag == DocTag.LI }
        assertNotNull(li)
        assertEquals("List item", li.content)
    }

    // ═══ DefinitionPair and IndexSegment Tests ═══

    @Test
    fun testDefinitionPairFromLine() {
        val pair = DefinitionPair.fromLine("**מחבר:** תֹּאמַר בַּת-שְׁלֹמֹה")
        assertNotNull(pair)
        assertEquals("מחבר", pair.term)
        assertEquals("תֹּאמַר בַּת-שְׁלֹמֹה", pair.description)
    }

    @Test
    fun testDefinitionPairFormatted() {
        val pair = DefinitionPair("term", "value")
        assertEquals("[term=value]", pair.formatted)
    }

    @Test
    fun testIndexSegmentWithPairs() {
        val pairs = listOf(
            DefinitionPair("מחבר", "תֹּאמַר", ScriptAttrs.HEBREW, ScriptAttrs.HEBREW),
            DefinitionPair("מקור", "file.txt", ScriptAttrs.HEBREW, ScriptAttrs.ENGLISH)
        )
        val segment = IndexSegment(DocTag.DL, 1, emptyList(), ScriptAttrs.EMPTY, emptyMap(), pairs)

        assertEquals("תֹּאמַר", segment.getDefinition("מחבר"))
        assertEquals("file.txt", segment.getDefinition("מקור"))
        assertEquals(2, segment.definitions.size)
    }

    // ═══ LineSeparator Tests ═══

    @Test
    fun testLineSeparatorNormalize() {
        // CRLF -> LF
        assertEquals("line1\nline2", LineSeparator.normalize("line1\r\nline2"))

        // CR -> LF
        assertEquals("line1\nline2", LineSeparator.normalize("line1\rline2"))

        // BR -> LF
        assertEquals("line1\nline2", LineSeparator.normalize("line1<br>line2"))
        assertEquals("line1\nline2", LineSeparator.normalize("line1<br/>line2"))
        assertEquals("line1\nline2", LineSeparator.normalize("line1<br />line2"))
        assertEquals("line1\nline2", LineSeparator.normalize("line1<BR>line2"))

        // Mixed -> all LF
        assertEquals("a\nb\nc\nd", LineSeparator.normalize("a\r\nb\rc<br>d"))
    }

    @Test
    fun testLineSeparatorSplitLines() {
        val text = "line1\r\nline2\rline3<br>line4\nline5"
        val lines = LineSeparator.splitLines(text)
        assertEquals(5, lines.size)
        assertEquals("line1", lines[0])
        assertEquals("line2", lines[1])
        assertEquals("line3", lines[2])
        assertEquals("line4", lines[3])
        assertEquals("line5", lines[4])
    }

    @Test
    fun testLineSeparatorHasMixedEndings() {
        assertTrue(LineSeparator.hasMixedEndings("line1\r\nline2\nline3"))
        assertFalse(LineSeparator.hasMixedEndings("line1\nline2\nline3"))
        assertFalse(LineSeparator.hasMixedEndings("line1\r\nline2\r\nline3"))
    }

    @Test
    fun testLineSeparatorDetectStyle() {
        assertEquals("\n", LineSeparator.detectStyle("a\nb\nc"))
        assertEquals("\r\n", LineSeparator.detectStyle("a\r\nb\r\nc"))
        assertEquals("\r", LineSeparator.detectStyle("a\rb\rc"))
    }

    @Test
    fun testPlainTextParserWithBrTags() {
        val text = "First line<br>Second line<br/>Third line"
        val parser = PlainTextParser()
        val elements = parser.parse(text)

        // Should parse as 3 separate paragraph elements
        val paragraphs = elements.filter { it.tag == DocTag.P }
        assertEquals(3, paragraphs.size)
        assertEquals("First line", paragraphs[0].content)
        assertEquals("Second line", paragraphs[1].content)
        assertEquals("Third line", paragraphs[2].content)
    }

    @Test
    fun testPlainTextParserWithMixedLineEndings() {
        val text = "Line 1\r\nLine 2\rLine 3\nLine 4"
        val parser = PlainTextParser()
        val elements = parser.parse(text)

        val paragraphs = elements.filter { it.tag == DocTag.P }
        assertEquals(4, paragraphs.size)
    }

    // ═══ CellRef Tests ═══

    @Test
    fun testCellRefFormat() {
        assertEquals("A1", CellRef(0, 0).formatted)
        assertEquals("B1", CellRef(1, 0).formatted)
        assertEquals("Z1", CellRef(25, 0).formatted)
        assertEquals("AA1", CellRef(26, 0).formatted)
        assertEquals("AB1", CellRef(27, 0).formatted)
        assertEquals("A10", CellRef(0, 9).formatted)
    }

    @Test
    fun testCellRefParse() {
        val a1 = CellRef.parse("A1")
        assertNotNull(a1)
        assertEquals(0, a1.col)
        assertEquals(0, a1.row)

        val z10 = CellRef.parse("Z10")
        assertNotNull(z10)
        assertEquals(25, z10.col)
        assertEquals(9, z10.row)

        val aa1 = CellRef.parse("AA1")
        assertNotNull(aa1)
        assertEquals(26, aa1.col)
        assertEquals(0, aa1.row)

        assertNull(CellRef.parse("invalid"))
        assertNull(CellRef.parse("1A"))
    }

    @Test
    fun testCellRefRange() {
        val a1 = CellRef(0, 0)
        val b5 = CellRef(1, 4)
        assertEquals("A1:B5", a1.rangeTo(b5))
    }

    // ═══ CellValue Tests ═══

    @Test
    fun testCellValueParse() {
        assertTrue(CellValue.parse("") is CellValue.Empty)
        assertTrue(CellValue.parse("  ") is CellValue.Empty)

        val text = CellValue.parse("Hello")
        assertTrue(text is CellValue.Text)
        assertEquals("Hello", text.raw)

        val num = CellValue.parse("42")
        assertTrue(num is CellValue.Number)
        assertEquals("42", num.raw)

        val decimal = CellValue.parse("3.14")
        assertTrue(decimal is CellValue.Number)
        assertEquals(3.14, (decimal as CellValue.Number).value)

        val formula = CellValue.parse("=SUM(A1:A10)")
        assertTrue(formula is CellValue.Formula)
        assertEquals("SUM(A1:A10)", (formula as CellValue.Formula).expression)
    }

    // ═══ TableData Tests ═══

    @Test
    fun testTableDataParseHtml() {
        val html = """
            <table>
                <caption>Sales Data</caption>
                <tr><th>Product</th><th>Price</th><th>Qty</th></tr>
                <tr><td>Apple</td><td>1.50</td><td>10</td></tr>
                <tr><td>Banana</td><td>0.75</td><td>20</td></tr>
            </table>
        """.trimIndent()

        val table = TableData.parseHtml(html)
        assertNotNull(table)
        assertEquals("Sales Data", table.name)
        assertEquals(3, table.headers.size)
        assertEquals("Product", table.headers[0])
        assertEquals(2, table.rowCount)
        assertEquals("Apple", table[CellRef(0, 0)].raw)
        assertEquals("1.50", table["B1"].raw)
    }

    @Test
    fun testTableDataParseMd() {
        val md = """
            | Name | Age | City |
            |------|-----|------|
            | John | 30  | NYC  |
            | Jane | 25  | LA   |
        """.trimIndent()

        val table = TableData.parseMarkdown(md)
        assertNotNull(table)
        assertEquals(3, table.colCount)
        assertEquals(2, table.rowCount)
        assertEquals("John", table["A1"].raw)
        assertEquals("25", table["B2"].raw)
    }

    @Test
    fun testTableDataParseTsv() {
        val tsv = "Name\tAge\tCity\nJohn\t30\tNYC\nJane\t25\tLA"
        val table = TableData.parseTsv(tsv)

        assertEquals(3, table.colCount)
        assertEquals(2, table.rowCount)
        assertEquals("Name", table.headers[0])
        assertEquals("John", table["A1"].raw)
    }

    @Test
    fun testTableDataToTsv() {
        val table = TableData(
            headers = listOf("A", "B"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Number(1.0), CellValue.Number(2.0))),
                TableRow(1, listOf(CellValue.Number(3.0), CellValue.Number(4.0)))
            )
        )

        val tsv = table.toTsv()
        assertTrue(tsv.contains("A\tB"))
        assertTrue(tsv.contains("1\t2"))
        assertTrue(tsv.contains("3\t4"))
    }

    @Test
    fun testTableDataToJson() {
        val table = TableData(
            headers = listOf("name", "value"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Text("foo"), CellValue.Number(42.0)))
            )
        )

        val jsonArray = table.toJsonArray()
        assertTrue(jsonArray.contains("[\"name\",\"value\"]"))
        assertTrue(jsonArray.contains("[\"foo\",\"42\"]"))

        val jsonObjects = table.toJsonObjects()
        assertTrue(jsonObjects.contains("\"name\":\"foo\""))
        assertTrue(jsonObjects.contains("\"value\":\"42\""))
    }

    // ═══ FormulaEvaluator Tests ═══

    @Test
    fun testFormulaEvaluatorSum() {
        val table = TableData(
            headers = listOf("A", "B"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Number(10.0), CellValue.Number(20.0))),
                TableRow(1, listOf(CellValue.Number(30.0), CellValue.Number(40.0))),
                TableRow(2, listOf(CellValue.Formula("SUM(A1:A2)"), CellValue.Empty))
            )
        )

        val evaluator = FormulaEvaluator(table)
        val result = evaluator.evaluate("SUM(A1:A2)")
        assertTrue(result is CellValue.Number)
        assertEquals(40.0, (result as CellValue.Number).value)
    }

    @Test
    fun testFormulaEvaluatorAvg() {
        val table = TableData(
            headers = listOf("A"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Number(10.0))),
                TableRow(1, listOf(CellValue.Number(20.0))),
                TableRow(2, listOf(CellValue.Number(30.0)))
            )
        )

        val evaluator = FormulaEvaluator(table)
        val result = evaluator.evaluate("AVG(A1:A3)")
        assertTrue(result is CellValue.Number)
        assertEquals(20.0, (result as CellValue.Number).value)
    }

    @Test
    fun testFormulaEvaluatorArithmetic() {
        val table = TableData(
            headers = listOf("A", "B"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Number(10.0), CellValue.Number(5.0)))
            )
        )

        val evaluator = FormulaEvaluator(table)

        val sum = evaluator.evaluate("A1+B1")
        assertEquals(15.0, (sum as CellValue.Number).value)

        val diff = evaluator.evaluate("A1-B1")
        assertEquals(5.0, (diff as CellValue.Number).value)

        val product = evaluator.evaluate("A1*B1")
        assertEquals(50.0, (product as CellValue.Number).value)

        val quotient = evaluator.evaluate("A1/B1")
        assertEquals(2.0, (quotient as CellValue.Number).value)
    }

    @Test
    fun testFormulaEvaluatorMinMax() {
        val table = TableData(
            headers = listOf("A"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Number(5.0))),
                TableRow(1, listOf(CellValue.Number(15.0))),
                TableRow(2, listOf(CellValue.Number(10.0)))
            )
        )

        val evaluator = FormulaEvaluator(table)
        assertEquals(5.0, (evaluator.evaluate("MIN(A1:A3)") as CellValue.Number).value)
        assertEquals(15.0, (evaluator.evaluate("MAX(A1:A3)") as CellValue.Number).value)
    }

    @Test
    fun testFormulaEvaluatorIf() {
        val table = TableData(
            headers = listOf("A"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Number(100.0)))
            )
        )

        val evaluator = FormulaEvaluator(table)

        val trueResult = evaluator.evaluate("IF(A1>50,1,0)")
        assertEquals(1.0, (trueResult as CellValue.Number).value)

        val falseResult = evaluator.evaluate("IF(A1<50,1,0)")
        assertEquals(0.0, (falseResult as CellValue.Number).value)
    }

    // ═══ Workbook Tests ═══

    @Test
    fun testWorkbookFromHtml() {
        val html = """
            <html>
            <table><caption>Sheet1</caption><tr><th>A</th></tr><tr><td>1</td></tr></table>
            <table><caption>Sheet2</caption><tr><th>B</th></tr><tr><td>2</td></tr></table>
            </html>
        """.trimIndent()

        val workbook = Workbook.fromHtml(html)
        assertEquals(2, workbook.sheetCount)
        assertEquals("Sheet1", workbook.sheetNames[0])
        assertEquals("Sheet2", workbook.sheetNames[1])

        val sheet1 = workbook["Sheet1"]
        assertNotNull(sheet1)
        assertEquals("1", sheet1["A1"].raw)
    }

    @Test
    fun testWorkbookAddSheet() {
        var workbook = Workbook()
        assertEquals(0, workbook.sheetCount)

        val table = TableData(listOf("Col1"), listOf(TableRow(0, listOf(CellValue.Text("data")))), "MySheet")
        workbook = workbook.addTable(table)

        assertEquals(1, workbook.sheetCount)
        assertEquals("MySheet", workbook[0]?.name)
    }

    @Test
    fun testWorkbookEvaluateAll() {
        val table = TableData(
            headers = listOf("A", "B", "Sum"),
            rows = listOf(
                TableRow(0, listOf(CellValue.Number(10.0), CellValue.Number(20.0), CellValue.Formula("A1+B1")))
            ),
            name = "Calc"
        )

        var workbook = Workbook().addTable(table)
        workbook = workbook.evaluateAll()

        val result = workbook["Calc"]?.get("C1")
        assertTrue(result is CellValue.Number)
        assertEquals(30.0, (result as CellValue.Number).value)
    }

    // ═══ TextBlock Tests ═══

    @Test
    fun testTextBlockParseMarkdown() {
        val md = """
            # Title

            ```he
            שלום עולם
            זו שורה שנייה
            ```

            Some English text

            ```en
            Hello World
            ```
        """.trimIndent()

        val blocks = TextBlock.parseMarkdown(md)
        assertEquals(2, blocks.size)

        val heBlock = blocks[0]
        assertEquals("he", heBlock.lang)
        assertTrue(heBlock.isRtl)
        assertTrue(heBlock.content.contains("שלום"))
        assertEquals(KerningHint.TIGHT, heBlock.kerning)

        val enBlock = blocks[1]
        assertEquals("en", enBlock.lang)
        assertFalse(enBlock.isRtl)
    }

    @Test
    fun testKerningHint() {
        assertEquals(KerningHint.TIGHT, KerningHint.forLang("he"))
        assertEquals(KerningHint.TIGHT, KerningHint.forLang("ar"))
        assertEquals(KerningHint.NONE, KerningHint.forLang("ja"))
        assertEquals(KerningHint.NONE, KerningHint.forLang("code"))
        assertEquals(KerningHint.NORMAL, KerningHint.forLang("en"))
    }

    // ═══ SubtitleParser Tests ═══

    @Test
    fun testSubtitleCueTimestamp() {
        val cue = SubtitleCue(1, 3661500, 3665000, "Hello")
        assertEquals("01:01:01,500", cue.startTimestamp)
        assertEquals("01:01:05,000", cue.endTimestamp)
        assertEquals(3500, cue.duration)
    }

    @Test
    fun testSubtitleCueParseSrtTime() {
        assertEquals(3661500, SubtitleCue.parseSrtTime("01:01:01,500"))
        assertEquals(0, SubtitleCue.parseSrtTime("00:00:00,000"))
        assertEquals(3723456, SubtitleCue.parseSrtTime("01:02:03,456"))
    }

    @Test
    fun testSubtitleParserParseSrt() {
        val srt = """
            1
            00:00:01,000 --> 00:00:04,000
            Hello World

            2
            00:00:05,000 --> 00:00:08,000
            שלום עולם
            Second line
        """.trimIndent()

        val cues = SubtitleParser.parseSrt(srt)
        assertEquals(2, cues.size)

        assertEquals(1, cues[0].index)
        assertEquals(1000, cues[0].startTime)
        assertEquals(4000, cues[0].endTime)
        assertEquals("Hello World", cues[0].text)

        assertEquals(2, cues[1].index)
        assertEquals("he", cues[1].scriptAttrs.lang)
        assertTrue(cues[1].text.contains("שלום"))
        assertTrue(cues[1].text.contains("Second line"))
    }

    @Test
    fun testSubtitleParserToSrt() {
        val cues = listOf(
            SubtitleCue(1, 1000, 4000, "Line one"),
            SubtitleCue(2, 5000, 8000, "Line two")
        )

        val srt = SubtitleParser.toSrt(cues)
        assertTrue(srt.contains("00:00:01,000 --> 00:00:04,000"))
        assertTrue(srt.contains("Line one"))
        assertTrue(srt.contains("00:00:05,000 --> 00:00:08,000"))
    }

    // ═══ FileType Tests ═══

    @Test
    fun testFileTypeFromFilename() {
        assertEquals(FileType.MARKDOWN, FileType.fromFilename("readme.md"))
        assertEquals(FileType.HTML, FileType.fromFilename("index.html"))
        assertEquals(FileType.SRT, FileType.fromFilename("movie.srt"))
        assertEquals(FileType.JSON, FileType.fromFilename("data.json"))
        assertEquals(FileType.YAML, FileType.fromFilename("config.yaml"))
        assertEquals(FileType.KOTLIN, FileType.fromFilename("Main.kt"))
        assertEquals(FileType.JAVASCRIPT, FileType.fromFilename("app.js"))
        assertEquals(FileType.INK, FileType.fromFilename("story.ink"))
        assertEquals(FileType.MXKL, FileType.fromFilename("layout.mxklfile"))
        assertEquals(FileType.UNKNOWN, FileType.fromFilename("file.xyz"))
    }

    @Test
    fun testFileTypeFromPath() {
        assertEquals(FileType.KOTLIN, FileType.fromPath("/src/main/kotlin/App.kt"))
        assertEquals(FileType.TSV, FileType.fromPath("data/export.tsv"))
    }

    @Test
    fun testFileTypeCategories() {
        assertTrue(FileType.CODE_TYPES.contains(FileType.KOTLIN))
        assertTrue(FileType.CODE_TYPES.contains(FileType.JAVASCRIPT))
        assertFalse(FileType.CODE_TYPES.contains(FileType.MARKDOWN))

        assertTrue(FileType.DATA_TYPES.contains(FileType.JSON))
        assertTrue(FileType.DATA_TYPES.contains(FileType.CSV))

        assertTrue(FileType.SUBTITLE_TYPES.contains(FileType.SRT))
        assertTrue(FileType.SUBTITLE_TYPES.contains(FileType.VTT))
    }

    @Test
    fun testFileTypeLangCode() {
        assertEquals("kt", FileType.langCode(FileType.KOTLIN))
        assertEquals("js", FileType.langCode(FileType.JAVASCRIPT))
        assertEquals("py", FileType.langCode(FileType.PYTHON))
        assertNull(FileType.langCode(FileType.MARKDOWN))
    }

    // ═══ ScriptAttrs.forLang Tests ═══

    @Test
    fun testScriptAttrsForLang() {
        val hebrew = ScriptAttrs.forLang("he")
        assertNotNull(hebrew)
        assertEquals("he", hebrew.lang)
        assertEquals(TextDir.RTL, hebrew.dir)

        val arabic = ScriptAttrs.forLang("arabic")
        assertNotNull(arabic)
        assertEquals("ar", arabic.lang)
        assertEquals(TextDir.RTL, arabic.dir)

        val english = ScriptAttrs.forLang("en")
        assertNotNull(english)
        assertEquals(TextDir.LTR, english.dir)

        assertNull(ScriptAttrs.forLang("unknown"))
    }
}
