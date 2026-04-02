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
 * Pattern parts for regex matching - open tag, attributes, content, close tag.
 * Supports both HTML tags with attributes and Markdown patterns.
 */
data class TagPattern(
    val prefix: String,      // Opening: "<h1" or "# "
    val attrPattern: String, // Attributes: "[^>]*" or ""
    val suffix: String,      // Close opening: ">" or ""
    val closeTag: String = "" // Closing tag: "</h1>" or "" for self-closing/MD
) {
    /** Compile to regex with named groups for opening tag */
    fun toRegex(tagName: String): Regex {
        val pattern = "(?<${tagName}_prefix>$prefix)(?<${tagName}_attr>$attrPattern)(?<${tagName}_suffix>$suffix)"
        return Regex(pattern, RegexOption.IGNORE_CASE)
    }

    /** Compile to regex for full tag with content and closing */
    fun toFullRegex(tagName: String): Regex {
        val close = if (closeTag.isNotEmpty()) closeTag else ""
        val contentPattern = if (close.isNotEmpty()) ".*?" else ".*"
        val pattern = "(?<${tagName}_prefix>$prefix)(?<${tagName}_attr>$attrPattern)(?<${tagName}_suffix>$suffix)" +
            "(?<${tagName}_content>$contentPattern)" +
            if (close.isNotEmpty()) "(?<${tagName}_close>$close)" else ""
        return Regex(pattern, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
    }

    /** Regex for closing tag only */
    fun closeRegex(tagName: String): Regex? {
        if (closeTag.isEmpty()) return null
        return Regex(closeTag, RegexOption.IGNORE_CASE)
    }

    companion object {
        /** Standard HTML attribute pattern */
        const val HTML_ATTRS = """(?:\s+[\w-]+(?:\s*=\s*(?:"[^"]*"|'[^']*'|[^\s>]*))?)*"""

        /** Create HTML tag pattern with standard attributes */
        fun html(tag: String, selfClosing: Boolean = false): TagPattern {
            return if (selfClosing) {
                TagPattern("<$tag", HTML_ATTRS, """\s*/?>""", "")
            } else {
                TagPattern("<$tag", HTML_ATTRS, ">", "</$tag>")
            }
        }

        /** Create Markdown pattern (no closing tag) */
        fun md(prefix: String, content: String, suffix: String): TagPattern {
            return TagPattern(prefix, content, suffix, "")
        }
    }
}

/**
 * Attribute pattern for parsing HTML attributes.
 * Format: name="value" or name='value' or name=value or name (boolean)
 * All regex patterns are lazily compiled for performance.
 */
object AttrPattern {
    /** Pattern for a single attribute */
    val SINGLE by lazy { Regex("""([\w-]+)(?:\s*=\s*(?:"([^"]*)"|'([^']*)'|([^\s>]*)))?""") }

    /** Pattern for lang attribute */
    val LANG by lazy { Regex("""lang\s*=\s*["']?([a-z]{2,3})["']?""", RegexOption.IGNORE_CASE) }

    /** Pattern for dir attribute */
    val DIR by lazy { Regex("""dir\s*=\s*["']?(ltr|rtl|auto)["']?""", RegexOption.IGNORE_CASE) }

    /** Pattern for class attribute */
    val CLASS by lazy { Regex("""class\s*=\s*["']([^"']+)["']""", RegexOption.IGNORE_CASE) }

    /** Pattern for id attribute */
    val ID by lazy { Regex("""id\s*=\s*["']([^"']+)["']""", RegexOption.IGNORE_CASE) }

    /** Pattern for data-* attributes */
    val DATA by lazy { Regex("""(data-[\w-]+)\s*=\s*["']([^"']+)["']""", RegexOption.IGNORE_CASE) }

    /**
     * Parse all attributes from an attribute string.
     */
    fun parseAll(attrString: String): Map<String, String> {
        val attrs = mutableMapOf<String, String>()
        SINGLE.findAll(attrString).forEach { match ->
            val name = match.groupValues[1]
            val value = match.groupValues[2].ifEmpty {
                match.groupValues[3].ifEmpty {
                    match.groupValues[4].ifEmpty { "" }
                }
            }
            attrs[name] = value
        }
        return attrs
    }

    /**
     * Extract script attributes (lang, dir) from attribute string.
     */
    fun parseScriptAttrs(attrString: String): ScriptAttrs {
        val lang = LANG.find(attrString)?.groupValues?.get(1)
        val dir = DIR.find(attrString)?.groupValues?.get(1)?.let { TextDir.fromValue(it) }
        return ScriptAttrs(lang, dir)
    }

    /**
     * Extract CSS classes from attribute string.
     */
    fun parseClasses(attrString: String): List<String> {
        val match = CLASS.find(attrString) ?: return emptyList()
        return match.groupValues[1].split(Regex("\\s+")).filter { it.isNotBlank() }
    }

    /**
     * Extract data-* attributes from attribute string.
     */
    fun parseDataAttrs(attrString: String): Map<String, String> {
        return DATA.findAll(attrString).associate { it.groupValues[1] to it.groupValues[2] }
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
        TagPattern.html("html"),
        isContainer = true
    ),
    HEAD(
        TagPattern.html("head"),
        isContainer = true
    ),
    BODY(
        TagPattern.html("body"),
        isContainer = true
    ),

    // ═══ Headings ═══
    H1(
        TagPattern.html("h1"),
        TagPattern.md("^#\\s+", ".*", "$")
    ),
    H2(
        TagPattern.html("h2"),
        TagPattern.md("^##\\s+", ".*", "$")
    ),
    H3(
        TagPattern.html("h3"),
        TagPattern.md("^###\\s+", ".*", "$")
    ),
    H4(
        TagPattern.html("h4"),
        TagPattern.md("^####\\s+", ".*", "$")
    ),
    H5(
        TagPattern.html("h5"),
        TagPattern.md("^#####\\s+", ".*", "$")
    ),
    H6(
        TagPattern.html("h6"),
        TagPattern.md("^######\\s+", ".*", "$")
    ),

    // ═══ Block Elements ═══
    P(
        TagPattern.html("p"),
        TagPattern.md("^(?![-*#>])", ".+", "$")
    ),
    DIV(
        TagPattern.html("div"),
        isContainer = true
    ),
    SECTION(
        TagPattern.html("section"),
        isContainer = true
    ),
    ARTICLE(
        TagPattern.html("article"),
        isContainer = true
    ),
    ASIDE(
        TagPattern.html("aside"),
        isContainer = true
    ),
    HEADER(
        TagPattern.html("header"),
        isContainer = true
    ),
    FOOTER(
        TagPattern.html("footer"),
        isContainer = true
    ),
    NAV(
        TagPattern.html("nav"),
        isContainer = true
    ),
    MAIN(
        TagPattern.html("main"),
        isContainer = true
    ),

    // ═══ Lists ═══
    OL(
        TagPattern.html("ol"),
        isContainer = true
    ),
    UL(
        TagPattern.html("ul"),
        isContainer = true
    ),
    LI(
        TagPattern.html("li"),
        TagPattern.md("^[-*]\\s+|^\\d+\\.\\s+", ".*", "$")
    ),
    DL(
        TagPattern.html("dl"),
        isContainer = true
    ),
    DT(
        TagPattern.html("dt")
    ),
    DD(
        TagPattern.html("dd")
    ),

    // ═══ Code/Preformatted ═══
    PRE(
        TagPattern.html("pre"),
        TagPattern.md("^```", ".*", "```$")
    ),
    CODE(
        TagPattern.html("code"),
        TagPattern.md("`", "[^`]+", "`"),
        isBlock = false
    ),
    KBD(
        TagPattern.html("kbd"),
        isBlock = false
    ),
    SAMP(
        TagPattern.html("samp"),
        isBlock = false
    ),
    VAR(
        TagPattern.html("var"),
        isBlock = false
    ),

    // ═══ Inline Text Formatting ═══
    SPAN(
        TagPattern.html("span"),
        isBlock = false
    ),
    B(
        TagPattern.html("b"),
        TagPattern.md("\\*\\*", ".+?", "\\*\\*"),
        isBlock = false
    ),
    I(
        TagPattern.html("i"),
        TagPattern.md("\\*", "[^*]+", "\\*"),
        isBlock = false
    ),
    U(
        TagPattern.html("u"),
        isBlock = false
    ),
    S(
        TagPattern.html("s"),
        TagPattern.md("~~", ".+?", "~~"),
        isBlock = false
    ),
    STRIKE(
        TagPattern.html("strike"),
        TagPattern.md("~~", ".+?", "~~"),
        isBlock = false
    ),
    DEL(
        TagPattern.html("del"),
        TagPattern.md("~~", ".+?", "~~"),
        isBlock = false
    ),
    INS(
        TagPattern.html("ins"),
        isBlock = false
    ),
    MARK(
        TagPattern.html("mark"),
        TagPattern.md("==", ".+?", "=="),
        isBlock = false
    ),
    SMALL(
        TagPattern.html("small"),
        isBlock = false
    ),
    BIG(
        TagPattern.html("big"),
        isBlock = false
    ),
    SUB(
        TagPattern.html("sub"),
        TagPattern.md("~", "[^~]+", "~"),
        isBlock = false
    ),
    SUP(
        TagPattern.html("sup"),
        TagPattern.md("\\^", "[^^]+", "\\^"),
        isBlock = false
    ),

    // ═══ Semantic Inline ═══
    STRONG(
        TagPattern.html("strong"),
        TagPattern.md("\\*\\*|__", ".+?", "\\*\\*|__"),
        isBlock = false
    ),
    EM(
        TagPattern.html("em"),
        TagPattern.md("\\*|_", ".+?", "\\*|_"),
        isBlock = false
    ),
    CITE(
        TagPattern.html("cite"),
        isBlock = false
    ),
    DFN(
        TagPattern.html("dfn"),
        isBlock = false
    ),
    ABBR(
        TagPattern.html("abbr"),
        isBlock = false
    ),
    TIME(
        TagPattern.html("time"),
        isBlock = false
    ),
    DATA(
        TagPattern.html("data"),
        isBlock = false
    ),
    Q(
        TagPattern.html("q"),
        isBlock = false
    ),

    // ═══ Links ═══
    A(
        TagPattern.html("a"),
        TagPattern.md("\\[", "[^\\]]+", "\\]\\([^)]+\\)"),
        isBlock = false
    ),

    // ═══ Bidirectional Text (important for Hebrew/Arabic) ═══
    BDO(
        TagPattern.html("bdo"),
        isBlock = false
    ),
    BDI(
        TagPattern.html("bdi"),
        isBlock = false
    ),

    // ═══ Ruby Annotations (for phonetic guides) ═══
    RUBY(
        TagPattern.html("ruby"),
        isBlock = false
    ),
    RT(
        TagPattern.html("rt"),
        isBlock = false
    ),
    RP(
        TagPattern.html("rp"),
        isBlock = false
    ),

    // ═══ Line Breaks ═══
    BR(
        TagPattern.html("br", selfClosing = true),
        isBlock = false
    ),
    WBR(
        TagPattern.html("wbr", selfClosing = true),
        isBlock = false
    ),
    HR(
        TagPattern.html("hr", selfClosing = true),
        TagPattern.md("^---+$|^\\*\\*\\*+$|^___+$", "", "")
    ),

    // ═══ Quotes ═══
    BLOCKQUOTE(
        TagPattern.html("blockquote"),
        TagPattern.md("^>\\s*", ".*", "$"),
        isContainer = true
    ),

    // ═══ Figures ═══
    FIGURE(
        TagPattern.html("figure"),
        isContainer = true
    ),
    FIGCAPTION(
        TagPattern.html("figcaption")
    ),
    IMG(
        TagPattern.html("img", selfClosing = true),
        TagPattern.md("!\\[", "[^\\]]*", "\\]\\([^)]+\\)"),
        isBlock = false
    ),

    // ═══ Tables ═══
    TABLE(
        TagPattern.html("table"),
        isContainer = true
    ),
    THEAD(
        TagPattern.html("thead"),
        isContainer = true
    ),
    TBODY(
        TagPattern.html("tbody"),
        isContainer = true
    ),
    TFOOT(
        TagPattern.html("tfoot"),
        isContainer = true
    ),
    TR(
        TagPattern.html("tr"),
        isContainer = true
    ),
    TH(
        TagPattern.html("th")
    ),
    TD(
        TagPattern.html("td")
    ),
    CAPTION(
        TagPattern.html("caption")
    ),
    COLGROUP(
        TagPattern.html("colgroup"),
        isContainer = true
    ),
    COL(
        TagPattern.html("col", selfClosing = true),
        isBlock = false
    ),

    // ═══ Forms (for completeness) ═══
    FORM(
        TagPattern.html("form"),
        isContainer = true
    ),
    INPUT(
        TagPattern.html("input", selfClosing = true),
        isBlock = false
    ),
    TEXTAREA(
        TagPattern.html("textarea")
    ),
    BUTTON(
        TagPattern.html("button"),
        isBlock = false
    ),
    SELECT(
        TagPattern.html("select"),
        isContainer = true
    ),
    OPTION(
        TagPattern.html("option")
    ),
    LABEL(
        TagPattern.html("label"),
        isBlock = false
    ),
    FIELDSET(
        TagPattern.html("fieldset"),
        isContainer = true
    ),
    LEGEND(
        TagPattern.html("legend")
    ),

    // ═══ Details/Summary ═══
    DETAILS(
        TagPattern.html("details"),
        isContainer = true
    ),
    SUMMARY(
        TagPattern.html("summary")
    ),

    // ═══ Media ═══
    AUDIO(
        TagPattern.html("audio"),
        isContainer = true
    ),
    VIDEO(
        TagPattern.html("video"),
        isContainer = true
    ),
    SOURCE(
        TagPattern.html("source", selfClosing = true),
        isBlock = false
    ),
    TRACK(
        TagPattern.html("track", selfClosing = true),
        isBlock = false
    ),

    // ═══ Embedded Content ═══
    IFRAME(
        TagPattern.html("iframe"),
        isBlock = false
    ),
    EMBED(
        TagPattern.html("embed", selfClosing = true),
        isBlock = false
    ),
    OBJECT(
        TagPattern.html("object"),
        isContainer = true
    ),

    // ═══ Script/Style (metadata) ═══
    SCRIPT(
        TagPattern.html("script"),
        isBlock = false
    ),
    STYLE(
        TagPattern.html("style"),
        isBlock = false
    ),
    LINK(
        TagPattern.html("link", selfClosing = true),
        isBlock = false
    ),
    META(
        TagPattern.html("meta", selfClosing = true),
        isBlock = false
    ),
    TITLE(
        TagPattern.html("title")
    ),
    BASE(
        TagPattern.html("base", selfClosing = true),
        isBlock = false
    );

    /** Check if this tag has a Markdown equivalent */
    val hasMdPattern: Boolean get() = mdPattern != null

    /** Get the HTML regex pattern with named groups (lazy cached) */
    fun htmlRegex(): Regex = htmlRegexCache.getOrPut(this) { htmlPattern.toRegex(name) }

    /** Get the Markdown regex pattern with named groups (lazy cached) */
    fun mdRegex(): Regex? = mdPattern?.let { mdRegexCache.getOrPut(this) { it.toRegex(name) } }

    /** Get the full HTML regex including content and closing tag (lazy cached) */
    fun htmlFullRegex(): Regex = htmlFullRegexCache.getOrPut(this) { htmlPattern.toFullRegex(name) }

    /** Get the closing tag regex (lazy cached) */
    fun closeRegex(): Regex? = htmlPattern.closeTag.takeIf { it.isNotEmpty() }?.let {
        closeRegexCache.getOrPut(this) { htmlPattern.closeRegex(name)!! }
    }

    companion object {
        private val byName by lazy { entries.associateBy { it.name.uppercase() } }
        fun fromName(name: String): DocTag? = byName[name.uppercase()]

        // Lazy regex caches - avoid recompiling regex on every call
        private val htmlRegexCache = mutableMapOf<DocTag, Regex>()
        private val mdRegexCache = mutableMapOf<DocTag, Regex>()
        private val htmlFullRegexCache = mutableMapOf<DocTag, Regex>()
        private val closeRegexCache = mutableMapOf<DocTag, Regex>()
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

        // Lazy compiled regex patterns
        private val LANG_REGEX by lazy { Regex("@lang=([a-z]{2,3})") }
        private val DIR_REGEX by lazy { Regex("@dir=(ltr|rtl|auto)") }

        /**
         * Parse from attribute string like "@lang=he@dir=rtl"
         */
        fun parse(s: String): ScriptAttrs {
            val lang = LANG_REGEX.find(s)?.groupValues?.get(1)
            val dir = DIR_REGEX.find(s)?.groupValues?.get(1)?.let { TextDir.fromValue(it) }
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
        // Lazy compiled regex patterns
        private val SPLIT_REGEX by lazy { Regex("(?=[.@=])") }
        private val TAG_REGEX by lazy { Regex("^([A-Z]+)(\\d+)$", RegexOption.IGNORE_CASE) }

        /**
         * Parse a segment string like "P1.poem.intro@lang=he@dir=rtl=data-verse=1"
         * Returns null if format is invalid.
         */
        fun parse(segment: String): IndexSegment? {
            if (segment.isBlank()) return null

            // Split by delimiters while preserving order (. for class, @ for script, = for data)
            val parts = segment.split(SPLIT_REGEX)
            if (parts.isEmpty()) return null

            // First part is TAG + instance (e.g., "P1", "LI23")
            val tagPart = parts[0]
            val tagMatch = TAG_REGEX.find(tagPart) ?: return null

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
