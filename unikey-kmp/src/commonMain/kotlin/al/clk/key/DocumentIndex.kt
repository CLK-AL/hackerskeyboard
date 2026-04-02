package al.clk.key

/**
 * Document Index System - hierarchical indexing for HTML/Markdown documents.
 *
 * Index format: TAG1/TAG2/TAG3 with attributes
 * - Tags: HTML1, BODY1, H1, P1, OL1, LI1, PRE1, SPAN1
 * - CSS classes: TAG1.class1.class2
 * - Data attributes: TAG1=data-index=value
 * - Delimiter: / for path segments
 *
 * Parsing uses enum patterns with prefix/middle/suffix regex groups.
 */

// ═══════════════════════════════════════════════════════════════════════════════
// TAG ENUM - Document elements with parsing patterns
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Pattern parts for regex matching - prefix, middle, suffix.
 */
data class TagPattern(
    val prefix: String,   // Opening pattern (e.g., "<h1", "# ")
    val middle: String,   // Content pattern (e.g., "[^>]*", ".*")
    val suffix: String    // Closing pattern (e.g., ">", "\n")
) {
    /** Compile to regex with named groups */
    fun toRegex(tagName: String): Regex {
        val pattern = "(?<${tagName}_prefix>$prefix)(?<${tagName}_middle>$middle)(?<${tagName}_suffix>$suffix)"
        return Regex(pattern, RegexOption.IGNORE_CASE)
    }
}

/**
 * Document tags - HTML and Markdown elements with parsing patterns.
 */
enum class DocTag(
    val htmlPattern: TagPattern,
    val mdPattern: TagPattern? = null,  // null if no MD equivalent
    val isBlock: Boolean = true,
    val isContainer: Boolean = false
) {
    // ═══ Document Structure ═══
    HTML(
        TagPattern("<html", "[^>]*", ">"),
        isContainer = true
    ),
    HEAD(
        TagPattern("<head", "[^>]*", ">"),
        isContainer = true
    ),
    BODY(
        TagPattern("<body", "[^>]*", ">"),
        isContainer = true
    ),

    // ═══ Headings ═══
    H1(
        TagPattern("<h1", "[^>]*", ">"),
        TagPattern("^#\\s+", ".*", "$")
    ),
    H2(
        TagPattern("<h2", "[^>]*", ">"),
        TagPattern("^##\\s+", ".*", "$")
    ),
    H3(
        TagPattern("<h3", "[^>]*", ">"),
        TagPattern("^###\\s+", ".*", "$")
    ),
    H4(
        TagPattern("<h4", "[^>]*", ">"),
        TagPattern("^####\\s+", ".*", "$")
    ),
    H5(
        TagPattern("<h5", "[^>]*", ">"),
        TagPattern("^#####\\s+", ".*", "$")
    ),
    H6(
        TagPattern("<h6", "[^>]*", ">"),
        TagPattern("^######\\s+", ".*", "$")
    ),

    // ═══ Block Elements ═══
    P(
        TagPattern("<p", "[^>]*", ">"),
        TagPattern("^(?![-*#>])", ".+", "$")
    ),
    DIV(
        TagPattern("<div", "[^>]*", ">"),
        isContainer = true
    ),
    SECTION(
        TagPattern("<section", "[^>]*", ">"),
        isContainer = true
    ),
    ARTICLE(
        TagPattern("<article", "[^>]*", ">"),
        isContainer = true
    ),
    ASIDE(
        TagPattern("<aside", "[^>]*", ">"),
        isContainer = true
    ),
    HEADER(
        TagPattern("<header", "[^>]*", ">"),
        isContainer = true
    ),
    FOOTER(
        TagPattern("<footer", "[^>]*", ">"),
        isContainer = true
    ),
    NAV(
        TagPattern("<nav", "[^>]*", ">"),
        isContainer = true
    ),
    MAIN(
        TagPattern("<main", "[^>]*", ">"),
        isContainer = true
    ),

    // ═══ Lists ═══
    OL(
        TagPattern("<ol", "[^>]*", ">"),
        isContainer = true
    ),
    UL(
        TagPattern("<ul", "[^>]*", ">"),
        isContainer = true
    ),
    LI(
        TagPattern("<li", "[^>]*", ">"),
        TagPattern("^[-*]\\s+|^\\d+\\.\\s+", ".*", "$")
    ),
    DL(
        TagPattern("<dl", "[^>]*", ">"),
        isContainer = true
    ),
    DT(
        TagPattern("<dt", "[^>]*", ">")
    ),
    DD(
        TagPattern("<dd", "[^>]*", ">")
    ),

    // ═══ Code/Preformatted ═══
    PRE(
        TagPattern("<pre", "[^>]*", ">"),
        TagPattern("^```", ".*", "```$")
    ),
    CODE(
        TagPattern("<code", "[^>]*", ">"),
        TagPattern("`", "[^`]+", "`"),
        isBlock = false
    ),
    KBD(
        TagPattern("<kbd", "[^>]*", ">"),
        isBlock = false
    ),
    SAMP(
        TagPattern("<samp", "[^>]*", ">"),
        isBlock = false
    ),
    VAR(
        TagPattern("<var", "[^>]*", ">"),
        isBlock = false
    ),

    // ═══ Inline Text Formatting ═══
    SPAN(
        TagPattern("<span", "[^>]*", ">"),
        isBlock = false
    ),
    B(
        TagPattern("<b", "[^>]*", ">"),
        TagPattern("\\*\\*", ".+?", "\\*\\*"),
        isBlock = false
    ),
    I(
        TagPattern("<i", "[^>]*", ">"),
        TagPattern("\\*", "[^*]+", "\\*"),
        isBlock = false
    ),
    U(
        TagPattern("<u", "[^>]*", ">"),
        isBlock = false
    ),
    S(
        TagPattern("<s", "[^>]*", ">"),
        TagPattern("~~", ".+?", "~~"),
        isBlock = false
    ),
    STRIKE(
        TagPattern("<strike", "[^>]*", ">"),
        TagPattern("~~", ".+?", "~~"),
        isBlock = false
    ),
    DEL(
        TagPattern("<del", "[^>]*", ">"),
        TagPattern("~~", ".+?", "~~"),
        isBlock = false
    ),
    INS(
        TagPattern("<ins", "[^>]*", ">"),
        isBlock = false
    ),
    MARK(
        TagPattern("<mark", "[^>]*", ">"),
        TagPattern("==", ".+?", "=="),
        isBlock = false
    ),
    SMALL(
        TagPattern("<small", "[^>]*", ">"),
        isBlock = false
    ),
    BIG(
        TagPattern("<big", "[^>]*", ">"),
        isBlock = false
    ),
    SUB(
        TagPattern("<sub", "[^>]*", ">"),
        TagPattern("~", "[^~]+", "~"),
        isBlock = false
    ),
    SUP(
        TagPattern("<sup", "[^>]*", ">"),
        TagPattern("\\^", "[^^]+", "\\^"),
        isBlock = false
    ),

    // ═══ Semantic Inline ═══
    STRONG(
        TagPattern("<strong", "[^>]*", ">"),
        TagPattern("\\*\\*|__", ".+?", "\\*\\*|__"),
        isBlock = false
    ),
    EM(
        TagPattern("<em", "[^>]*", ">"),
        TagPattern("\\*|_", ".+?", "\\*|_"),
        isBlock = false
    ),
    CITE(
        TagPattern("<cite", "[^>]*", ">"),
        isBlock = false
    ),
    DFN(
        TagPattern("<dfn", "[^>]*", ">"),
        isBlock = false
    ),
    ABBR(
        TagPattern("<abbr", "[^>]*", ">"),
        isBlock = false
    ),
    TIME(
        TagPattern("<time", "[^>]*", ">"),
        isBlock = false
    ),
    DATA(
        TagPattern("<data", "[^>]*", ">"),
        isBlock = false
    ),
    Q(
        TagPattern("<q", "[^>]*", ">"),
        isBlock = false
    ),

    // ═══ Links ═══
    A(
        TagPattern("<a", "[^>]*", ">"),
        TagPattern("\\[", "[^\\]]+", "\\]\\([^)]+\\)"),
        isBlock = false
    ),

    // ═══ Bidirectional Text (important for Hebrew/Arabic) ═══
    BDO(
        TagPattern("<bdo", "[^>]*", ">"),
        isBlock = false
    ),
    BDI(
        TagPattern("<bdi", "[^>]*", ">"),
        isBlock = false
    ),

    // ═══ Ruby Annotations (for phonetic guides) ═══
    RUBY(
        TagPattern("<ruby", "[^>]*", ">"),
        isBlock = false
    ),
    RT(
        TagPattern("<rt", "[^>]*", ">"),
        isBlock = false
    ),
    RP(
        TagPattern("<rp", "[^>]*", ">"),
        isBlock = false
    ),

    // ═══ Line Breaks ═══
    BR(
        TagPattern("<br", "[^>]*", "/?>"),
        isBlock = false
    ),
    WBR(
        TagPattern("<wbr", "[^>]*", "/?>"),
        isBlock = false
    ),
    HR(
        TagPattern("<hr", "[^>]*", "/?>"),
        TagPattern("^---+$|^\\*\\*\\*+$|^___+$", "", "")
    ),

    // ═══ Quotes ═══
    BLOCKQUOTE(
        TagPattern("<blockquote", "[^>]*", ">"),
        TagPattern("^>\\s*", ".*", "$"),
        isContainer = true
    ),

    // ═══ Figures ═══
    FIGURE(
        TagPattern("<figure", "[^>]*", ">"),
        isContainer = true
    ),
    FIGCAPTION(
        TagPattern("<figcaption", "[^>]*", ">")
    ),
    IMG(
        TagPattern("<img", "[^>]*", "/?>"),
        TagPattern("!\\[", "[^\\]]*", "\\]\\([^)]+\\)"),
        isBlock = false
    ),

    // ═══ Tables ═══
    TABLE(
        TagPattern("<table", "[^>]*", ">"),
        isContainer = true
    ),
    THEAD(
        TagPattern("<thead", "[^>]*", ">"),
        isContainer = true
    ),
    TBODY(
        TagPattern("<tbody", "[^>]*", ">"),
        isContainer = true
    ),
    TFOOT(
        TagPattern("<tfoot", "[^>]*", ">"),
        isContainer = true
    ),
    TR(
        TagPattern("<tr", "[^>]*", ">"),
        isContainer = true
    ),
    TH(
        TagPattern("<th", "[^>]*", ">")
    ),
    TD(
        TagPattern("<td", "[^>]*", ">")
    ),
    CAPTION(
        TagPattern("<caption", "[^>]*", ">")
    ),
    COLGROUP(
        TagPattern("<colgroup", "[^>]*", ">"),
        isContainer = true
    ),
    COL(
        TagPattern("<col", "[^>]*", "/?>"),
        isBlock = false
    ),

    // ═══ Forms (for completeness) ═══
    FORM(
        TagPattern("<form", "[^>]*", ">"),
        isContainer = true
    ),
    INPUT(
        TagPattern("<input", "[^>]*", "/?>"),
        isBlock = false
    ),
    TEXTAREA(
        TagPattern("<textarea", "[^>]*", ">")
    ),
    BUTTON(
        TagPattern("<button", "[^>]*", ">"),
        isBlock = false
    ),
    SELECT(
        TagPattern("<select", "[^>]*", ">"),
        isContainer = true
    ),
    OPTION(
        TagPattern("<option", "[^>]*", ">")
    ),
    LABEL(
        TagPattern("<label", "[^>]*", ">"),
        isBlock = false
    ),
    FIELDSET(
        TagPattern("<fieldset", "[^>]*", ">"),
        isContainer = true
    ),
    LEGEND(
        TagPattern("<legend", "[^>]*", ">")
    ),

    // ═══ Details/Summary ═══
    DETAILS(
        TagPattern("<details", "[^>]*", ">"),
        isContainer = true
    ),
    SUMMARY(
        TagPattern("<summary", "[^>]*", ">")
    ),

    // ═══ Media ═══
    AUDIO(
        TagPattern("<audio", "[^>]*", ">"),
        isContainer = true
    ),
    VIDEO(
        TagPattern("<video", "[^>]*", ">"),
        isContainer = true
    ),
    SOURCE(
        TagPattern("<source", "[^>]*", "/?>"),
        isBlock = false
    ),
    TRACK(
        TagPattern("<track", "[^>]*", "/?>"),
        isBlock = false
    ),

    // ═══ Embedded Content ═══
    IFRAME(
        TagPattern("<iframe", "[^>]*", ">"),
        isBlock = false
    ),
    EMBED(
        TagPattern("<embed", "[^>]*", "/?>"),
        isBlock = false
    ),
    OBJECT(
        TagPattern("<object", "[^>]*", ">"),
        isContainer = true
    ),

    // ═══ Script/Style (metadata) ═══
    SCRIPT(
        TagPattern("<script", "[^>]*", ">"),
        isBlock = false
    ),
    STYLE(
        TagPattern("<style", "[^>]*", ">"),
        isBlock = false
    ),
    LINK(
        TagPattern("<link", "[^>]*", "/?>"),
        isBlock = false
    ),
    META(
        TagPattern("<meta", "[^>]*", "/?>"),
        isBlock = false
    ),
    TITLE(
        TagPattern("<title", "[^>]*", ">")
    ),
    BASE(
        TagPattern("<base", "[^>]*", "/?>"),
        isBlock = false
    );

    /** Check if this tag has a Markdown equivalent */
    val hasMdPattern: Boolean get() = mdPattern != null

    /** Get the HTML regex pattern with named groups */
    fun htmlRegex(): Regex = htmlPattern.toRegex(name)

    /** Get the Markdown regex pattern with named groups (if available) */
    fun mdRegex(): Regex? = mdPattern?.toRegex(name)

    companion object {
        private val byName = entries.associateBy { it.name.uppercase() }
        fun fromName(name: String): DocTag? = byName[name.uppercase()]
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SCRIPT ATTRIBUTES - lang, dir for internationalization
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Text direction for bidirectional text support.
 */
enum class TextDir(val value: String) {
    LTR("ltr"),   // Left-to-right (English, etc.)
    RTL("rtl"),   // Right-to-left (Hebrew, Arabic)
    AUTO("auto"); // Auto-detect

    companion object {
        fun fromValue(v: String): TextDir? = entries.find { it.value == v.lowercase() }
    }
}

/**
 * Script/language attributes for a document element.
 */
data class ScriptAttrs(
    val lang: String? = null,    // Language code: "he", "en", "ar"
    val dir: TextDir? = null     // Text direction
) {
    /** Format as attribute string: @lang=he@dir=rtl */
    val formatted: String get() = buildString {
        lang?.let { append("@lang=$it") }
        dir?.let { append("@dir=${it.value}") }
    }

    /** Check if any script attributes are set */
    val isEmpty: Boolean get() = lang == null && dir == null

    companion object {
        val EMPTY = ScriptAttrs()
        val HEBREW = ScriptAttrs("he", TextDir.RTL)
        val ARABIC = ScriptAttrs("ar", TextDir.RTL)
        val ENGLISH = ScriptAttrs("en", TextDir.LTR)

        /**
         * Parse from attribute string like "@lang=he@dir=rtl"
         */
        fun parse(s: String): ScriptAttrs {
            var lang: String? = null
            var dir: TextDir? = null

            val langMatch = Regex("@lang=([a-z]{2,3})").find(s)
            langMatch?.let { lang = it.groupValues[1] }

            val dirMatch = Regex("@dir=(ltr|rtl|auto)").find(s)
            dirMatch?.let { dir = TextDir.fromValue(it.groupValues[1]) }

            return ScriptAttrs(lang, dir)
        }

        /**
         * Parse from HTML attributes map.
         */
        fun fromAttributes(attrs: Map<String, String>): ScriptAttrs {
            val lang = attrs["lang"]
            val dir = attrs["dir"]?.let { TextDir.fromValue(it) }
            return ScriptAttrs(lang, dir)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// INDEX SEGMENT - Single element in the document path
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * A single segment in a document index path.
 * Format: TAG1.class1.class2@lang=he@dir=rtl=attr1=val1
 */
data class IndexSegment(
    val tag: DocTag,
    val instance: Int,                              // 1-based instance number
    val cssClasses: List<String> = emptyList(),     // CSS classes
    val scriptAttrs: ScriptAttrs = ScriptAttrs.EMPTY, // lang, dir attributes
    val dataAttrs: Map<String, String> = emptyMap() // data-* attributes
) {
    /** Formatted segment: P1.poem.intro@lang=he@dir=rtl=data-verse=1 */
    val formatted: String get() = buildString {
        append(tag.name)
        append(instance)
        cssClasses.forEach { append(".$it") }
        if (!scriptAttrs.isEmpty) append(scriptAttrs.formatted)
        dataAttrs.forEach { (k, v) -> append("=$k=$v") }
    }

    /** Get language code if set */
    val lang: String? get() = scriptAttrs.lang

    /** Get text direction if set */
    val dir: TextDir? get() = scriptAttrs.dir

    /** Check if this is RTL text */
    val isRtl: Boolean get() = scriptAttrs.dir == TextDir.RTL

    companion object {
        /**
         * Parse a segment string like "P1.poem.intro@lang=he@dir=rtl=data-verse=1"
         * Returns null if format is invalid.
         */
        fun parse(segment: String): IndexSegment? {
            if (segment.isBlank()) return null

            // Split by delimiters while preserving order (. for class, @ for script, = for data)
            val parts = segment.split(Regex("(?=[.@=])"))
            if (parts.isEmpty()) return null

            // First part is TAG + instance (e.g., "P1", "LI23")
            val tagPart = parts[0]
            val tagMatch = Regex("^([A-Z]+)(\\d+)$", RegexOption.IGNORE_CASE).find(tagPart)
                ?: return null

            val tagName = tagMatch.groupValues[1].uppercase()
            val instance = tagMatch.groupValues[2].toIntOrNull() ?: return null
            val tag = DocTag.fromName(tagName) ?: return null

            // Parse remaining parts
            val cssClasses = mutableListOf<String>()
            val dataAttrs = mutableMapOf<String, String>()
            var lang: String? = null
            var dir: TextDir? = null

            var i = 1
            while (i < parts.size) {
                val part = parts[i]
                when {
                    part.startsWith(".") -> {
                        cssClasses.add(part.drop(1))
                    }
                    part.startsWith("@lang=") -> {
                        lang = part.drop(6)
                    }
                    part.startsWith("@dir=") -> {
                        dir = TextDir.fromValue(part.drop(5))
                    }
                    part.startsWith("=") -> {
                        // Attribute: =name=value (consumes two parts)
                        val attrName = part.drop(1)
                        if (i + 1 < parts.size && parts[i + 1].startsWith("=")) {
                            dataAttrs[attrName] = parts[i + 1].drop(1)
                            i++
                        }
                    }
                }
                i++
            }

            val scriptAttrs = ScriptAttrs(lang, dir)
            return IndexSegment(tag, instance, cssClasses, scriptAttrs, dataAttrs)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// DOCUMENT INDEX - Full hierarchical path
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Full document index path.
 * Format: HTML1/BODY1/DIV1.container/P1.intro=data-index=1
 */
data class DocumentIndex(
    val segments: List<IndexSegment>
) {
    /** Depth of the index (number of path segments) */
    val depth: Int get() = segments.size

    /** Formatted path: HTML1/BODY1/P1.intro */
    val formatted: String get() = segments.joinToString("/") { it.formatted }

    /** Get the leaf (last) segment */
    val leaf: IndexSegment? get() = segments.lastOrNull()

    /** Get parent index (all segments except last) */
    fun parent(): DocumentIndex? {
        if (segments.size <= 1) return null
        return DocumentIndex(segments.dropLast(1))
    }

    /** Append a child segment */
    fun child(segment: IndexSegment): DocumentIndex {
        return DocumentIndex(segments + segment)
    }

    /** Check if this index is a descendant of another */
    fun isDescendantOf(ancestor: DocumentIndex): Boolean {
        if (ancestor.segments.size >= segments.size) return false
        return segments.take(ancestor.segments.size) == ancestor.segments
    }

    companion object {
        /**
         * Parse a path string like "HTML1/BODY1/P1.intro"
         * Returns null if format is invalid.
         */
        fun parse(path: String): DocumentIndex? {
            if (path.isBlank()) return null

            val segmentStrings = path.split("/")
            val segments = segmentStrings.mapNotNull { IndexSegment.parse(it) }

            if (segments.size != segmentStrings.size) return null

            return DocumentIndex(segments)
        }

        /** Create an empty root index */
        fun root(): DocumentIndex = DocumentIndex(emptyList())
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// DOCUMENT PARSER - Parse HTML/MD to create indices
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Parsed element with position and index information.
 */
data class ParsedElement(
    val index: DocumentIndex,
    val tag: DocTag,
    val startOffset: Int,
    val endOffset: Int,
    val attributes: Map<String, String>,
    val content: String
)

/**
 * Parser for HTML/Markdown documents.
 * Uses regex patterns from DocTag enum.
 */
class DocumentParser {
    private val tagCounts = mutableMapOf<DocTag, Int>()

    /**
     * Parse HTML content and return indexed elements.
     */
    fun parseHtml(html: String): List<ParsedElement> {
        tagCounts.clear()
        val elements = mutableListOf<ParsedElement>()
        val stack = mutableListOf<IndexSegment>()

        // Regex to match opening tags
        val openTagRegex = Regex("<(\\w+)([^>]*)>", RegexOption.IGNORE_CASE)
        val closeTagRegex = Regex("</(\\w+)>", RegexOption.IGNORE_CASE)

        var pos = 0
        while (pos < html.length) {
            // Find next tag
            val openMatch = openTagRegex.find(html, pos)
            val closeMatch = closeTagRegex.find(html, pos)

            // Determine which comes first
            val nextOpen = openMatch?.range?.first ?: Int.MAX_VALUE
            val nextClose = closeMatch?.range?.first ?: Int.MAX_VALUE

            when {
                nextOpen < nextClose && openMatch != null -> {
                    val tagName = openMatch.groupValues[1].uppercase()
                    val attrString = openMatch.groupValues[2]
                    val tag = DocTag.fromName(tagName)

                    if (tag != null) {
                        val count = tagCounts.getOrPut(tag) { 0 } + 1
                        tagCounts[tag] = count

                        val attrs = parseAttributes(attrString)
                        val cssClasses = attrs["class"]?.split(" ")?.filter { it.isNotBlank() } ?: emptyList()
                        val scriptAttrs = ScriptAttrs.fromAttributes(attrs)
                        val dataAttrs = attrs.filterKeys { it.startsWith("data-") }

                        val segment = IndexSegment(tag, count, cssClasses, scriptAttrs, dataAttrs)
                        stack.add(segment)

                        val index = DocumentIndex(stack.toList())

                        // Find content end
                        val contentStart = openMatch.range.last + 1
                        val closePattern = Regex("</${tag.name}>", RegexOption.IGNORE_CASE)
                        val contentEnd = closePattern.find(html, contentStart)?.range?.first ?: html.length
                        val content = html.substring(contentStart, contentEnd)

                        elements.add(ParsedElement(
                            index = index,
                            tag = tag,
                            startOffset = openMatch.range.first,
                            endOffset = contentEnd,
                            attributes = attrs,
                            content = content
                        ))
                    }
                    pos = openMatch.range.last + 1
                }
                nextClose < nextOpen && closeMatch != null -> {
                    val tagName = closeMatch.groupValues[1].uppercase()
                    val tag = DocTag.fromName(tagName)
                    if (tag != null && stack.isNotEmpty() && stack.last().tag == tag) {
                        stack.removeLast()
                    }
                    pos = closeMatch.range.last + 1
                }
                else -> break
            }
        }

        return elements
    }

    /**
     * Parse Markdown content and return indexed elements.
     */
    fun parseMarkdown(md: String): List<ParsedElement> {
        tagCounts.clear()
        val elements = mutableListOf<ParsedElement>()
        val lines = md.lines()
        var offset = 0

        for (line in lines) {
            for (tag in DocTag.entries.filter { it.hasMdPattern }) {
                val regex = tag.mdRegex() ?: continue
                val match = regex.find(line)
                if (match != null) {
                    val count = tagCounts.getOrPut(tag) { 0 } + 1
                    tagCounts[tag] = count

                    val segment = IndexSegment(tag, count)
                    val index = DocumentIndex(listOf(segment))

                    elements.add(ParsedElement(
                        index = index,
                        tag = tag,
                        startOffset = offset + match.range.first,
                        endOffset = offset + match.range.last + 1,
                        attributes = emptyMap(),
                        content = match.value
                    ))
                    break
                }
            }
            offset += line.length + 1  // +1 for newline
        }

        return elements
    }

    /**
     * Parse HTML attribute string into key-value map.
     */
    private fun parseAttributes(attrString: String): Map<String, String> {
        val attrs = mutableMapOf<String, String>()
        val attrRegex = Regex("(\\w[\\w-]*)\\s*=\\s*[\"']([^\"']*)[\"']")

        attrRegex.findAll(attrString).forEach { match ->
            attrs[match.groupValues[1]] = match.groupValues[2]
        }

        return attrs
    }

    companion object {
        /** Singleton instance */
        val instance = DocumentParser()
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// PLAIN TEXT PARSER - Parse plain text by line separators into HTML-like indices
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Line type detection for plain text parsing.
 * Infers HTML-like structure from line patterns.
 */
enum class LineType(
    val tag: DocTag,
    val pattern: Regex
) {
    // Headers detected by patterns (ALL CAPS, === underline, etc.)
    HEADER1(DocTag.H1, Regex("^[A-Z][A-Z0-9 ]{0,50}$|^.+\\n={3,}$")),
    HEADER2(DocTag.H2, Regex("^[A-Z][A-Za-z0-9 ]{0,50}:$|^.+\\n-{3,}$")),
    HEADER3(DocTag.H3, Regex("^\\d+\\.\\s+[A-Z].*$")),  // "1. Title"
    HEADER4(DocTag.H4, Regex("^\\d+\\.\\d+\\.\\s+.*$")),  // "1.1. Subtitle"
    HEADER5(DocTag.H5, Regex("^[a-z]\\)\\s+.*$")),  // "a) item"
    HEADER6(DocTag.H6, Regex("^\\([a-z]\\)\\s+.*$")),  // "(a) item"

    // List items
    OL_ITEM(DocTag.LI, Regex("^\\s*\\d+[\\.\\)]\\s+.*$")),  // "1. text" or "1) text"
    UL_ITEM(DocTag.LI, Regex("^\\s*[-*+]\\s+.*$")),  // "- text", "* text"

    // Table header (pipe-delimited or tab-separated with caps)
    TABLE_HEADER(DocTag.TH, Regex("^[A-Z][A-Za-z0-9 ]+\\|.*$|^[A-Z][A-Z ]+\\t.*$")),
    TABLE_ROW(DocTag.TD, Regex("^.*\\|.*$|^.*\\t.*\\t.*$")),

    // Code/preformatted (starts with whitespace or has code-like patterns)
    PRE(DocTag.PRE, Regex("^\\s{4,}.*$|^\\t+.*$|^[a-z_][a-z_0-9]*\\s*[=({].*$", RegexOption.IGNORE_CASE)),

    // Inline span (short fragments, emphasized)
    SPAN(DocTag.SPAN, Regex("^\\*.*\\*$|^_.*_$")),

    // Default paragraph (any non-empty line)
    PARAGRAPH(DocTag.P, Regex("^.+$"));

    companion object {
        /**
         * Detect line type from text content.
         * Checks patterns in priority order.
         */
        fun detect(line: String): LineType {
            if (line.isBlank()) return PARAGRAPH

            // Check in priority order (headers first, then lists, etc.)
            val priorityOrder = listOf(
                HEADER1, HEADER2, HEADER3, HEADER4, HEADER5, HEADER6,
                OL_ITEM, UL_ITEM,
                TABLE_HEADER, TABLE_ROW,
                PRE, SPAN,
                PARAGRAPH
            )

            for (type in priorityOrder) {
                if (type.pattern.matches(line)) return type
            }

            return PARAGRAPH
        }
    }
}

/**
 * Parser for plain text documents.
 * Splits by line separators and infers HTML-like structure.
 * Builds complete nested paths: HTML1/BODY1/DIV1/OL1/LI1
 */
class PlainTextParser {
    private val tagCounts = mutableMapOf<DocTag, Int>()

    // Container stack for proper nesting
    private data class ContainerState(
        val segment: IndexSegment,
        val level: Int  // Heading level or nesting depth
    )

    /**
     * Parse plain text into indexed elements.
     * Lines are split and categorized into HTML-like elements.
     * Full paths include all container tags.
     */
    fun parse(text: String): List<ParsedElement> {
        tagCounts.clear()
        val elements = mutableListOf<ParsedElement>()

        // Root structure
        val htmlSegment = pushTag(DocTag.HTML)
        val bodySegment = pushTag(DocTag.BODY)

        // Container stack: tracks DIVs for sections, OL/UL for lists, TABLE/TR for tables
        val containerStack = mutableListOf<ContainerState>()

        // Current section DIV (for heading hierarchy)
        var currentSectionLevel = 0
        var sectionDivSegment: IndexSegment? = null

        // List state
        var currentListSegment: IndexSegment? = null
        var currentListType: LineType? = null

        // Table state
        var inTable = false
        var tableSegment: IndexSegment? = null
        var currentRowSegment: IndexSegment? = null

        // Split by line separators: \r\n, \r, \n
        val lines = text.split(Regex("\\r\\n|\\r|\\n"))
        var offset = 0

        for (line in lines) {
            if (line.isBlank()) {
                // Close lists and tables on blank line
                currentListSegment = null
                currentListType = null
                if (inTable) {
                    inTable = false
                    tableSegment = null
                    currentRowSegment = null
                }
                offset += line.length + 1
                continue
            }

            val lineType = LineType.detect(line)
            val tag = lineType.tag

            // Build the full path
            val pathSegments = mutableListOf(htmlSegment, bodySegment)

            // Handle section containers (DIV for heading hierarchy)
            when (lineType) {
                LineType.HEADER1, LineType.HEADER2, LineType.HEADER3,
                LineType.HEADER4, LineType.HEADER5, LineType.HEADER6 -> {
                    val headingLevel = when (lineType) {
                        LineType.HEADER1 -> 1
                        LineType.HEADER2 -> 2
                        LineType.HEADER3 -> 3
                        LineType.HEADER4 -> 4
                        LineType.HEADER5 -> 5
                        LineType.HEADER6 -> 6
                        else -> 0
                    }

                    // Close deeper sections, open new section DIV
                    if (headingLevel <= currentSectionLevel || sectionDivSegment == null) {
                        sectionDivSegment = pushTag(DocTag.DIV)
                    }
                    currentSectionLevel = headingLevel

                    // Close any open lists/tables
                    currentListSegment = null
                    currentListType = null
                    inTable = false
                    tableSegment = null
                }
                else -> {}
            }

            // Add section DIV if we're in a section
            sectionDivSegment?.let { pathSegments.add(it) }

            // Handle list containers
            when (lineType) {
                LineType.OL_ITEM -> {
                    if (currentListType != LineType.OL_ITEM) {
                        currentListSegment = pushTag(DocTag.OL)
                        currentListType = LineType.OL_ITEM
                    }
                    currentListSegment?.let { pathSegments.add(it) }
                }
                LineType.UL_ITEM -> {
                    if (currentListType != LineType.UL_ITEM) {
                        currentListSegment = pushTag(DocTag.UL)
                        currentListType = LineType.UL_ITEM
                    }
                    currentListSegment?.let { pathSegments.add(it) }
                }
                else -> {
                    // Not a list item - close list
                    if (lineType != LineType.PARAGRAPH || currentListSegment != null) {
                        currentListSegment = null
                        currentListType = null
                    }
                }
            }

            // Handle table containers
            when (lineType) {
                LineType.TABLE_HEADER, LineType.TABLE_ROW -> {
                    if (!inTable) {
                        tableSegment = pushTag(DocTag.TABLE)
                        inTable = true
                    }
                    tableSegment?.let { pathSegments.add(it) }

                    // Each row is a TR
                    currentRowSegment = pushTag(DocTag.TR)
                    currentRowSegment?.let { pathSegments.add(it) }
                }
                else -> {}
            }

            // Handle PRE container (wraps code blocks)
            if (lineType == LineType.PRE) {
                // PRE is self-contained, no extra container needed
            }

            // Add the leaf element
            val leafSegment = pushTag(tag)
            pathSegments.add(leafSegment)

            val index = DocumentIndex(pathSegments)

            elements.add(ParsedElement(
                index = index,
                tag = tag,
                startOffset = offset,
                endOffset = offset + line.length,
                attributes = emptyMap(),
                content = line
            ))

            offset += line.length + 1  // +1 for line separator
        }

        return elements
    }

    /**
     * Push a new tag and return its segment with incremented count.
     */
    private fun pushTag(tag: DocTag): IndexSegment {
        val count = tagCounts.getOrPut(tag) { 0 } + 1
        tagCounts[tag] = count
        return IndexSegment(tag, count)
    }

    /**
     * Parse and return indices only (for indexing).
     */
    fun parseIndices(text: String): List<DocumentIndex> {
        return parse(text).map { it.index }
    }

    companion object {
        val instance = PlainTextParser()
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// INDEX BUILDER - Build indices from parsing context
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Builder for creating document indices incrementally.
 */
class DocumentIndexBuilder {
    private val tagCounts = mutableMapOf<DocTag, Int>()
    private val stack = mutableListOf<IndexSegment>()

    /** Push a new tag onto the stack */
    fun push(tag: DocTag, cssClasses: List<String> = emptyList(), dataAttrs: Map<String, String> = emptyMap()): DocumentIndex {
        val count = tagCounts.getOrPut(tag) { 0 } + 1
        tagCounts[tag] = count

        val segment = IndexSegment(tag, count, cssClasses, dataAttrs)
        stack.add(segment)

        return DocumentIndex(stack.toList())
    }

    /** Pop the last tag from the stack */
    fun pop(): IndexSegment? {
        return if (stack.isNotEmpty()) stack.removeLast() else null
    }

    /** Get current index */
    fun current(): DocumentIndex = DocumentIndex(stack.toList())

    /** Get current depth */
    fun depth(): Int = stack.size

    /** Reset the builder */
    fun reset() {
        tagCounts.clear()
        stack.clear()
    }
}
