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
        // Use [\s\S] instead of . for multiplatform (DOT_MATCHES_ALL not in common)
        val contentPattern = if (close.isNotEmpty()) "[\\s\\S]*?" else "[\\s\\S]*"
        val pattern = "(?<${tagName}_prefix>$prefix)(?<${tagName}_attr>$attrPattern)(?<${tagName}_suffix>$suffix)" +
            "(?<${tagName}_content>$contentPattern)" +
            if (close.isNotEmpty()) "(?<${tagName}_close>$close)" else ""
        return Regex(pattern, RegexOption.IGNORE_CASE)
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
        TagPattern.html("dt"),
        TagPattern.md("^(?:\\*\\*)?[^:]+(?:\\*\\*)?(?=:\\s)", "", "")  // term before : (with optional **)
    ),
    DD(
        TagPattern.html("dd"),
        TagPattern.md("(?<=:\\s)(?:\\*\\*)?.*(?:\\*\\*)?$", "", "")  // description after : (with optional **)
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
// DEFINITION PAIR - DT/DD paired like key=value attributes
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * A definition term/description pair (DT=DD).
 * Represents a single entry in a definition list, similar to key=value attributes.
 */
data class DefinitionPair(
    val term: String,                              // DT content
    val description: String,                       // DD content
    val termScript: ScriptAttrs = ScriptAttrs.EMPTY,   // Script attrs for term
    val descScript: ScriptAttrs = ScriptAttrs.EMPTY    // Script attrs for description
) {
    /** Formatted pair: [term=description] */
    val formatted: String get() = "[$term=$description]"

    /** Formatted with script: [term@lang=he=description@lang=en] */
    val formattedFull: String get() = buildString {
        append("[")
        append(term)
        if (!termScript.isEmpty) append(termScript.formatted)
        append("=")
        append(description)
        if (!descScript.isEmpty) append(descScript.formatted)
        append("]")
    }

    companion object {
        private val PAIR_REGEX by lazy { Regex("\\[([^=]+)=([^\\]]+)\\]") }

        /**
         * Parse from formatted string like "[term=description]"
         */
        fun parse(s: String): DefinitionPair? {
            val match = PAIR_REGEX.find(s) ?: return null
            val term = match.groupValues[1]
            val desc = match.groupValues[2]

            // Extract script attrs if present
            val termScript = ScriptAttrs.parse(term)
            val descScript = ScriptAttrs.parse(desc)

            // Remove script attrs from term/desc for clean values
            val cleanTerm = term.replace(Regex("@lang=[a-z]+|@dir=(ltr|rtl|auto)"), "")
            val cleanDesc = desc.replace(Regex("@lang=[a-z]+|@dir=(ltr|rtl|auto)"), "")

            return DefinitionPair(cleanTerm, cleanDesc, termScript, descScript)
        }

        // Regex to strip markdown formatting (**, *, __, _)
        private val MD_BOLD_REGEX by lazy { Regex("^\\*\\*|\\*\\*$|^__|__$") }
        private val MD_ITALIC_REGEX by lazy { Regex("^\\*|\\*$|^_|_$") }

        /**
         * Strip markdown formatting from text.
         */
        private fun stripMarkdown(s: String): String {
            return s.trim()
                .replace(MD_BOLD_REGEX, "")  // Remove ** or __
                .replace(MD_ITALIC_REGEX, "") // Remove * or _
                .trim()
        }

        /**
         * Create from term: description line.
         * Strips markdown bold/italic from both term and description.
         */
        fun fromLine(line: String): DefinitionPair? {
            val colonIdx = line.indexOf(": ")
            if (colonIdx <= 0) return null

            // Extract and strip markdown formatting
            val rawTerm = line.substring(0, colonIdx)
            val rawDesc = line.substring(colonIdx + 2)

            val term = stripMarkdown(rawTerm)
            val desc = stripMarkdown(rawDesc)

            return DefinitionPair(
                term = term,
                description = desc,
                termScript = ScriptDetector.detect(term),
                descScript = ScriptDetector.detect(desc)
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// INDEX SEGMENT - Single element in the document path
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * A single segment in a document index path.
 * Format: TAG1.class1.class2@lang=he@dir=rtl=attr1=val1[term=desc]
 */
data class IndexSegment(
    val tag: DocTag,
    val instance: Int,                              // 1-based instance number
    val cssClasses: List<String> = emptyList(),     // CSS classes
    val scriptAttrs: ScriptAttrs = ScriptAttrs.EMPTY, // lang, dir attributes
    val dataAttrs: Map<String, String> = emptyMap(), // data-* attributes
    val defPairs: List<DefinitionPair> = emptyList() // DT=DD pairs (for DL tags)
) {
    /** Formatted segment: P1.poem.intro@lang=he@dir=rtl=data-verse=1[term=desc] */
    val formatted: String get() = buildString {
        append(tag.name)
        append(instance)
        cssClasses.forEach { append(".$it") }
        if (!scriptAttrs.isEmpty) append(scriptAttrs.formatted)
        dataAttrs.forEach { (k, v) -> append("=$k=$v") }
        defPairs.forEach { append(it.formatted) }
    }

    /** Get language code if set */
    val lang: String? get() = scriptAttrs.lang

    /** Get text direction if set */
    val dir: TextDir? get() = scriptAttrs.dir

    /** Check if this is RTL text */
    val isRtl: Boolean get() = scriptAttrs.dir == TextDir.RTL

    /** Get definition pair by term */
    fun getDefinition(term: String): String? = defPairs.find { it.term == term }?.description

    /** Get all definitions as a map */
    val definitions: Map<String, String> get() = defPairs.associate { it.term to it.description }

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

            // Parse definition pairs [term=desc]
            val defPairs = mutableListOf<DefinitionPair>()
            val pairRegex = Regex("\\[([^=\\]]+)=([^\\]]+)\\]")
            pairRegex.findAll(segment).forEach { match ->
                val pair = DefinitionPair.parse(match.value)
                if (pair != null) defPairs.add(pair)
            }

            val scriptAttrs = ScriptAttrs(lang, dir)
            return IndexSegment(tag, instance, cssClasses, scriptAttrs, dataAttrs, defPairs)
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

// ═══════════════════════════════════════════════════════════════════════════════
// SCRIPT DETECTION - Detect language/direction from text content
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Detect script type and direction from text content.
 * Used to auto-set lang/dir attributes during parsing.
 */
object ScriptDetector {
    // Unicode ranges for RTL scripts
    private val HEBREW_RANGE = '\u0590'..'\u05FF'
    private val ARABIC_RANGE = '\u0600'..'\u06FF'
    private val ARABIC_SUPP_RANGE = '\u0750'..'\u077F'
    private val ARABIC_EXT_A_RANGE = '\u08A0'..'\u08FF'

    // Hebrew vowels (nikud) range
    private val NIKUD_RANGE = '\u05B0'..'\u05C7'

    /**
     * Detect script attributes from text content.
     */
    fun detect(text: String): ScriptAttrs {
        if (text.isBlank()) return ScriptAttrs.EMPTY

        var hebrewCount = 0
        var arabicCount = 0
        var latinCount = 0

        for (c in text) {
            when (c) {
                in HEBREW_RANGE -> hebrewCount++
                in ARABIC_RANGE, in ARABIC_SUPP_RANGE, in ARABIC_EXT_A_RANGE -> arabicCount++
                in 'A'..'Z', in 'a'..'z' -> latinCount++
            }
        }

        return when {
            hebrewCount > arabicCount && hebrewCount > latinCount -> ScriptAttrs.HEBREW
            arabicCount > hebrewCount && arabicCount > latinCount -> ScriptAttrs.ARABIC
            latinCount > 0 -> ScriptAttrs.ENGLISH
            else -> ScriptAttrs.EMPTY
        }
    }

    /**
     * Check if text contains Hebrew characters.
     */
    fun isHebrew(text: String): Boolean = text.any { it in HEBREW_RANGE }

    /**
     * Check if text contains Arabic characters.
     */
    fun isArabic(text: String): Boolean = text.any {
        it in ARABIC_RANGE || it in ARABIC_SUPP_RANGE || it in ARABIC_EXT_A_RANGE
    }

    /**
     * Check if text is RTL (Hebrew or Arabic).
     */
    fun isRtl(text: String): Boolean = isHebrew(text) || isArabic(text)

    /**
     * Check if text contains nikud (Hebrew vowel marks).
     */
    fun hasNikud(text: String): Boolean = text.any { it in NIKUD_RANGE }

    /**
     * Get dominant text direction.
     */
    fun getDirection(text: String): TextDir {
        val script = detect(text)
        return script.dir ?: TextDir.LTR
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// DECORATION - Enum for content decorations/parent tags
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Content decorations - syntax markers that wrap or prefix content.
 * These are parent tags, not part of the actual content.
 */
enum class Decoration(
    patternStr: String,
    val isPrefix: Boolean = true,    // Strip from start only
    val isWrapper: Boolean = false   // Strip all occurrences
) {
    // ═══ Block Prefixes ═══
    MD_H1("^#\\s+"),
    MD_H2("^##\\s+"),
    MD_H3("^###\\s+"),
    MD_H4("^####\\s+"),
    MD_H5("^#####\\s+"),
    MD_H6("^######\\s+"),
    UNDERLINE_EQ("^={3,}$"),
    UNDERLINE_DASH("^-{3,}$"),
    BLOCKQUOTE("^>\\s*"),
    LIST_BULLET("^\\s*[-*+]\\s+"),
    LIST_NUMBER("^\\s*\\d+[.):]\\s+"),
    DEF_COLON("^:\\s+"),
    INDENT("^\\s{2,}"),

    // ═══ Inline Wrappers ═══
    BOLD("\\*\\*|__", isPrefix = false, isWrapper = true),
    ITALIC("(?<![\\w*_])\\*(?![\\w*_])|(?<![\\w*_])_(?![\\w*_])", isPrefix = false, isWrapper = true),
    STRIKE("~~", isPrefix = false, isWrapper = true),
    CODE_INLINE("`", isPrefix = false, isWrapper = true),
    CODE_FENCE("^```.*$");

    /** Lazy compiled pattern */
    val pattern: Regex by lazy { Regex(patternStr) }

    /** Strip this decoration from text */
    fun strip(text: String): String {
        return if (isWrapper) {
            pattern.replace(text, "")
        } else {
            pattern.replaceFirst(text, "")
        }
    }

    /** Check if this decoration matches the text */
    fun matches(text: String): Boolean {
        return if (isPrefix) pattern.containsMatchIn(text) else pattern.containsMatchIn(text)
    }

    /** Extract the decoration marker from text */
    fun extract(text: String): String? {
        return pattern.find(text)?.value?.trim()
    }

    companion object {
        /** Get all header decorations */
        val HEADERS = listOf(MD_H1, MD_H2, MD_H3, MD_H4, MD_H5, MD_H6)

        /** Get all inline wrapper decorations */
        val WRAPPERS = entries.filter { it.isWrapper }

        /** Get all prefix decorations */
        val PREFIXES = entries.filter { it.isPrefix && !it.isWrapper }

        /** Detect decoration type from line */
        fun detect(text: String): Decoration? {
            val trimmed = text.trim()
            return entries.find { it.pattern.containsMatchIn(trimmed) }
        }

        /** Get header level (1-6) from decoration */
        fun headerLevel(dec: Decoration?): Int = when (dec) {
            MD_H1, UNDERLINE_EQ -> 1
            MD_H2, UNDERLINE_DASH -> 2
            MD_H3 -> 3
            MD_H4 -> 4
            MD_H5 -> 5
            MD_H6 -> 6
            else -> 0
        }
    }
}

/**
 * Content stripper utilities using Decoration enum.
 */
object ContentStripper {
    /**
     * Strip all decorations from text, returning clean content.
     */
    fun strip(text: String): String {
        var result = text.trim()
        // Strip prefixes first
        for (dec in Decoration.PREFIXES) {
            result = dec.strip(result)
        }
        // Then wrappers
        for (dec in Decoration.WRAPPERS) {
            result = dec.strip(result)
        }
        return result.trim()
    }

    /**
     * Strip header decorations only (# prefixes and bold/italic).
     */
    fun stripHeader(text: String): String {
        var result = text.trim()
        for (dec in Decoration.HEADERS) {
            result = dec.strip(result)
        }
        result = Decoration.BOLD.strip(result)
        result = Decoration.ITALIC.strip(result)
        return result.trim()
    }

    /**
     * Strip list item decorations only (- * + or numbers).
     */
    fun stripListItem(text: String): String {
        var result = text.trim()
        result = Decoration.LIST_BULLET.strip(result)
        result = Decoration.LIST_NUMBER.strip(result)
        result = Decoration.BOLD.strip(result)
        result = Decoration.ITALIC.strip(result)
        return result.trim()
    }

    /**
     * Strip blockquote decoration only (> prefix).
     */
    fun stripBlockquote(text: String): String {
        var result = text.trim()
        result = Decoration.BLOCKQUOTE.strip(result)
        return result.trim()
    }

    /**
     * Check if line is an underline marker (=== or ---).
     */
    fun isUnderline(text: String): Boolean {
        val trimmed = text.trim()
        return Decoration.UNDERLINE_EQ.pattern.matches(trimmed) ||
               Decoration.UNDERLINE_DASH.pattern.matches(trimmed)
    }

    /**
     * Extract decoration type from line.
     */
    fun getDecoration(text: String): String? {
        val trimmed = text.trim()
        val dec = Decoration.detect(trimmed) ?: return null
        return when (dec) {
            Decoration.UNDERLINE_EQ -> "==="
            Decoration.UNDERLINE_DASH -> "---"
            Decoration.BLOCKQUOTE -> ">"
            else -> dec.extract(trimmed)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// RFC/SPEC PATTERNS - RFC 2119 keywords and spec syntax
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * RFC 2119 keyword detection and spec document patterns.
 */
object RfcPattern {
    // RFC 2119 keywords (MUST, SHOULD, MAY, etc.)
    private val RFC2119_KEYWORDS = setOf(
        "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT",
        "SHOULD", "SHOULD NOT", "RECOMMENDED", "NOT RECOMMENDED",
        "MAY", "OPTIONAL"
    )

    /** Pattern for RFC 2119 keywords */
    val RFC2119 by lazy {
        Regex("\\b(${RFC2119_KEYWORDS.joinToString("|") { Regex.escape(it) }})\\b")
    }

    /** Pattern for RFC reference (RFC 1234) */
    val RFC_REF by lazy { Regex("\\bRFC\\s*\\d{1,5}\\b", RegexOption.IGNORE_CASE) }

    /** Pattern for section reference (Section 1.2.3) */
    val SECTION_REF by lazy { Regex("\\b[Ss]ection\\s+\\d+(\\.\\d+)*\\b") }

    /** Pattern for ABNF rule definition (rule = ...) */
    val ABNF_RULE by lazy { Regex("^\\s*[a-zA-Z][a-zA-Z0-9-]*\\s*=.*$") }

    /** Pattern for BNF-style definition (<rule> ::= ...) */
    val BNF_RULE by lazy { Regex("^\\s*<[^>]+>\\s*::=.*$") }

    /**
     * Check if text contains RFC 2119 keywords.
     */
    fun hasRfc2119Keywords(text: String): Boolean = RFC2119.containsMatchIn(text)

    /**
     * Extract RFC references from text.
     */
    fun findRfcRefs(text: String): List<String> = RFC_REF.findAll(text).map { it.value }.toList()
}

// ═══════════════════════════════════════════════════════════════════════════════
// LINE TYPE - Detect element type from line content
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Line type detection for plain text parsing.
 * Infers HTML-like structure from line patterns.
 * Patterns are lazily compiled for performance.
 */
enum class LineType(
    val tag: DocTag,
    patternString: String,
    private val options: Set<RegexOption> = emptySet()
) {
    // ═══ Markdown Headers ═══
    MD_H1(DocTag.H1, "^#\\s+.*$"),
    MD_H2(DocTag.H2, "^##\\s+.*$"),
    MD_H3(DocTag.H3, "^###\\s+.*$"),
    MD_H4(DocTag.H4, "^####\\s+.*$"),
    MD_H5(DocTag.H5, "^#####\\s+.*$"),
    MD_H6(DocTag.H6, "^######\\s+.*$"),

    // ═══ Plain Text Headers ═══
    HEADER1(DocTag.H1, "^[A-Z][A-Z0-9 ]{0,50}$"),  // ALL CAPS
    HEADER2(DocTag.H2, "^[A-Z][A-Za-z0-9 ]{0,50}:$"),  // Title:
    HEADER3(DocTag.H3, "^\\d+\\.\\s+[A-Z].*$"),  // "1. Title"
    HEADER4(DocTag.H4, "^\\d+\\.\\d+\\.\\s+.*$"),  // "1.1. Subtitle"
    HEADER5(DocTag.H5, "^[a-z]\\)\\s+.*$"),  // "a) item"
    HEADER6(DocTag.H6, "^\\([a-z]\\)\\s+.*$"),  // "(a) item"
    UNDERLINE_H1(DocTag.H1, "^={3,}$"),  // === under title
    UNDERLINE_H2(DocTag.H2, "^-{3,}$"),  // --- under title

    // ═══ List Items ═══
    OL_ITEM(DocTag.LI, "^\\s*\\d+[\\.\\)]\\s+.*$"),  // "1. text" or "1) text"
    UL_ITEM(DocTag.LI, "^\\s*[-*+]\\s+.*$"),  // "- text", "* text"

    // ═══ Definition List ═══
    // Inline format: "term: description" or "**term**: description"
    DT_DD_INLINE(DocTag.DL, "^(?:\\*\\*)?[^:*]+(?:\\*\\*)?:\\s+.+$"),  // term: desc on same line
    DT_ITEM(DocTag.DT, "^(?:\\*\\*)?[^:]+(?:\\*\\*)?(?=::\\s*$)"),  // Term before ::
    DD_ITEM(DocTag.DD, "^:\\s+.*$|^\\s{2,}[^\\s].*$"),  // : description or indented

    // ═══ Tables ═══
    TABLE_HEADER(DocTag.TH, "^\\|?[A-Z][A-Za-z0-9 ]*\\|.*$"),  // |Header|...
    TABLE_SEP(DocTag.TR, "^\\|?[-:|]+\\|[-:|\\s]*$"),  // |---|---|
    TABLE_ROW(DocTag.TD, "^\\|.*\\|.*$"),  // |cell|cell|
    TAB_TABLE(DocTag.TD, "^.*\\t.*\\t.*$"),  // tab-separated

    // ═══ Code/Preformatted ═══
    FENCE_START(DocTag.PRE, "^```.*$"),  // ```lang
    FENCE_END(DocTag.PRE, "^```$"),
    INDENT_CODE(DocTag.PRE, "^\\s{4,}.*$|^\\t+.*$"),  // 4+ spaces or tabs

    // ═══ Blockquote ═══
    BLOCKQUOTE(DocTag.BLOCKQUOTE, "^>\\s*.*$"),

    // ═══ Horizontal Rule ═══
    HR(DocTag.HR, "^[-*_]{3,}$"),

    // ═══ RFC/Spec Patterns ═══
    ABNF_DEF(DocTag.PRE, "^\\s*[a-zA-Z][a-zA-Z0-9-]*\\s*=.*$"),  // ABNF rule
    BNF_DEF(DocTag.PRE, "^\\s*<[^>]+>\\s*::=.*$"),  // BNF rule

    // ═══ Inline ═══
    SPAN(DocTag.SPAN, "^\\*.*\\*$|^_.*_$"),  // *text* or _text_

    // ═══ Default ═══
    PARAGRAPH(DocTag.P, "^.+$");

    /** Lazy compiled pattern */
    val pattern: Regex by lazy { Regex(patternString, options) }

    companion object {
        /**
         * Detect line type from text content.
         * Checks patterns in priority order.
         */
        fun detect(line: String): LineType {
            if (line.isBlank()) return PARAGRAPH

            // Priority order - more specific patterns first
            val priorityOrder = listOf(
                // Markdown headers (most specific)
                MD_H6, MD_H5, MD_H4, MD_H3, MD_H2, MD_H1,
                // Underline headers
                UNDERLINE_H1, UNDERLINE_H2,
                // HR must come before UNDERLINE
                HR,
                // Plain text headers
                HEADER1, HEADER2, HEADER3, HEADER4, HEADER5, HEADER6,
                // Definition list - inline first (term: desc), then separate
                DT_DD_INLINE, DD_ITEM, DT_ITEM,
                // Lists
                OL_ITEM, UL_ITEM,
                // Tables
                TABLE_SEP, TABLE_HEADER, TABLE_ROW, TAB_TABLE,
                // Code
                FENCE_START, FENCE_END, INDENT_CODE, ABNF_DEF, BNF_DEF,
                // Blockquote
                BLOCKQUOTE,
                // Inline
                SPAN,
                // Default
                PARAGRAPH
            )

            for (type in priorityOrder) {
                if (type.pattern.matches(line)) return type
            }

            return PARAGRAPH
        }

        /**
         * Detect with script awareness.
         * Returns pair of (LineType, ScriptAttrs).
         */
        fun detectWithScript(line: String): Pair<LineType, ScriptAttrs> {
            val type = detect(line)
            val script = ScriptDetector.detect(line)
            return type to script
        }
    }
}

/**
 * Line separator utilities - normalizes BR, CR, LF, CRLF to Linux LF.
 */
object LineSeparator {
    /** Unix/Linux line feed */
    const val LF = "\n"
    /** Windows carriage return + line feed */
    const val CRLF = "\r\n"
    /** Old Mac carriage return */
    const val CR = "\r"

    /** HTML/XML line break patterns */
    private val BR_PATTERN by lazy { Regex("""<br\s*/?>""", RegexOption.IGNORE_CASE) }

    /**
     * Normalize all line endings to Linux LF (\n).
     * Handles: <br>, <br/>, <br />, \r\n, \r
     */
    fun normalize(text: String): String {
        var result = BR_PATTERN.replace(text, LF)  // <br> -> \n
        result = result.replace(CRLF, LF)          // \r\n -> \n
        result = result.replace(CR, LF)            // \r -> \n
        return result
    }

    /**
     * Split text into lines, normalizing line endings first.
     */
    fun splitLines(text: String): List<String> {
        return normalize(text).split(LF)
    }

    /**
     * Check if text contains mixed line endings.
     */
    fun hasMixedEndings(text: String): Boolean {
        val hasCrlf = text.contains(CRLF)
        val hasCr = text.replace(CRLF, "").contains(CR)
        val hasLf = text.replace(CRLF, "").contains(LF)
        return listOf(hasCrlf, hasCr, hasLf).count { it } > 1
    }

    /**
     * Detect dominant line ending style in text.
     */
    fun detectStyle(text: String): String {
        val crlfCount = CRLF.toRegex().findAll(text).count()
        val stripped = text.replace(CRLF, "")
        val crCount = CR.toRegex().findAll(stripped).count()
        val lfCount = LF.toRegex().findAll(stripped).count()

        return when {
            crlfCount >= crCount && crlfCount >= lfCount -> CRLF
            crCount >= lfCount -> CR
            else -> LF
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

        // Split by line separators: <br>, \r\n, \r, \n -> all normalized to LF
        val lines = LineSeparator.splitLines(text)
        var offset = 0

        // Definition list state
        var dlSegment: IndexSegment? = null
        var inDefList = false

        // Code fence state
        var inCodeFence = false
        var codeBlockSegment: IndexSegment? = null

        for (line in lines) {
            if (line.isBlank()) {
                // Close lists and tables on blank line (but not code fences)
                if (!inCodeFence) {
                    currentListSegment = null
                    currentListType = null
                    dlSegment = null
                    inDefList = false
                    if (inTable) {
                        inTable = false
                        tableSegment = null
                        currentRowSegment = null
                    }
                }
                offset += line.length + 1
                continue
            }

            // Detect line type and script
            val (lineType, scriptAttrs) = LineType.detectWithScript(line)
            val tag = lineType.tag

            // Handle code fence state
            if (lineType == LineType.FENCE_START && !inCodeFence) {
                inCodeFence = true
                codeBlockSegment = pushTagWithScript(DocTag.PRE, scriptAttrs)
            } else if (lineType == LineType.FENCE_END && inCodeFence) {
                inCodeFence = false
                codeBlockSegment = null
                offset += line.length + 1
                continue
            }

            // Build the full path
            val pathSegments = mutableListOf(htmlSegment, bodySegment)

            // If in code fence, just add to PRE block
            if (inCodeFence && codeBlockSegment != null) {
                pathSegments.add(codeBlockSegment)
                val leafSegment = pushTagWithScript(DocTag.CODE, scriptAttrs)
                pathSegments.add(leafSegment)

                val index = DocumentIndex(pathSegments)
                elements.add(ParsedElement(index, DocTag.CODE, offset, offset + line.length, emptyMap(), line))
                offset += line.length + 1
                continue
            }

            // Handle section containers (DIV for heading hierarchy)
            val headingLevel = when (lineType) {
                LineType.MD_H1, LineType.HEADER1, LineType.UNDERLINE_H1 -> 1
                LineType.MD_H2, LineType.HEADER2, LineType.UNDERLINE_H2 -> 2
                LineType.MD_H3, LineType.HEADER3 -> 3
                LineType.MD_H4, LineType.HEADER4 -> 4
                LineType.MD_H5, LineType.HEADER5 -> 5
                LineType.MD_H6, LineType.HEADER6 -> 6
                else -> 0
            }

            if (headingLevel > 0) {
                // Close deeper sections, open new section DIV
                if (headingLevel <= currentSectionLevel || sectionDivSegment == null) {
                    sectionDivSegment = pushTagWithScript(DocTag.DIV, scriptAttrs)
                }
                currentSectionLevel = headingLevel

                // Close any open lists/tables
                currentListSegment = null
                currentListType = null
                dlSegment = null
                inDefList = false
                inTable = false
                tableSegment = null
            }

            // Add section DIV if we're in a section
            sectionDivSegment?.let { pathSegments.add(it) }

            // Handle definition list containers - DT/DD are paired like attributes
            when (lineType) {
                LineType.DT_DD_INLINE -> {
                    // Inline format: "term: description" - create paired DL element
                    if (!inDefList) {
                        dlSegment = pushTagWithScript(DocTag.DL, scriptAttrs)
                        inDefList = true
                    }
                    currentListSegment = null
                    currentListType = null

                    // Parse the definition pair
                    val pair = DefinitionPair.fromLine(line)
                    if (pair != null) {
                        // Add DL element with the pair
                        val dlPath = mutableListOf(htmlSegment, bodySegment)
                        sectionDivSegment?.let { dlPath.add(it) }

                        // Create DL segment with this pair
                        val pairCount = tagCounts.getOrPut(DocTag.DL) { 0 }  // Use same DL instance
                        val dlWithPair = IndexSegment(
                            DocTag.DL,
                            pairCount,
                            emptyList(),
                            scriptAttrs,
                            emptyMap(),
                            listOf(pair)
                        )
                        dlPath.add(dlWithPair)

                        elements.add(ParsedElement(
                            DocumentIndex(dlPath),
                            DocTag.DL,
                            offset,
                            offset + line.length,
                            buildMap {
                                pair.termScript.lang?.let { put("dt-lang", it) }
                                pair.descScript.lang?.let { put("dd-lang", it) }
                            },
                            line
                        ))

                        offset += line.length + 1
                        continue  // Skip normal element creation
                    }
                }
                LineType.DT_ITEM -> {
                    if (!inDefList) {
                        dlSegment = pushTagWithScript(DocTag.DL, scriptAttrs)
                        inDefList = true
                    }
                    dlSegment?.let { pathSegments.add(it) }
                    // Close other list types
                    currentListSegment = null
                    currentListType = null
                }
                LineType.DD_ITEM -> {
                    if (!inDefList) {
                        dlSegment = pushTagWithScript(DocTag.DL, scriptAttrs)
                        inDefList = true
                    }
                    dlSegment?.let { pathSegments.add(it) }
                    currentListSegment = null
                    currentListType = null
                }
                else -> {}
            }

            // Handle list containers
            when (lineType) {
                LineType.OL_ITEM -> {
                    if (currentListType != LineType.OL_ITEM) {
                        currentListSegment = pushTagWithScript(DocTag.OL, scriptAttrs)
                        currentListType = LineType.OL_ITEM
                    }
                    currentListSegment?.let { pathSegments.add(it) }
                    dlSegment = null
                    inDefList = false
                }
                LineType.UL_ITEM -> {
                    if (currentListType != LineType.UL_ITEM) {
                        currentListSegment = pushTagWithScript(DocTag.UL, scriptAttrs)
                        currentListType = LineType.UL_ITEM
                    }
                    currentListSegment?.let { pathSegments.add(it) }
                    dlSegment = null
                    inDefList = false
                }
                else -> {
                    // Not a list item - close list (unless it's a definition list item)
                    if (lineType !in listOf(LineType.DT_ITEM, LineType.DD_ITEM, LineType.DT_DD_INLINE, LineType.PARAGRAPH)) {
                        currentListSegment = null
                        currentListType = null
                    }
                }
            }

            // Handle table containers
            when (lineType) {
                LineType.TABLE_HEADER, LineType.TABLE_ROW, LineType.TABLE_SEP, LineType.TAB_TABLE -> {
                    if (!inTable) {
                        tableSegment = pushTagWithScript(DocTag.TABLE, scriptAttrs)
                        inTable = true
                    }
                    tableSegment?.let { pathSegments.add(it) }

                    // Skip separator rows
                    if (lineType == LineType.TABLE_SEP) {
                        offset += line.length + 1
                        continue
                    }

                    // Each row is a TR
                    currentRowSegment = pushTagWithScript(DocTag.TR, scriptAttrs)
                    currentRowSegment?.let { pathSegments.add(it) }
                }
                else -> {}
            }

            // Handle blockquote
            if (lineType == LineType.BLOCKQUOTE) {
                val bqSegment = pushTagWithScript(DocTag.BLOCKQUOTE, scriptAttrs)
                pathSegments.add(bqSegment)
            }

            // Add the leaf element with script attributes
            val leafSegment = pushTagWithScript(tag, scriptAttrs)
            pathSegments.add(leafSegment)

            val index = DocumentIndex(pathSegments)

            // Strip decorations from content based on tag type
            val strippedContent = when (tag) {
                DocTag.H1, DocTag.H2, DocTag.H3, DocTag.H4, DocTag.H5, DocTag.H6 ->
                    ContentStripper.stripHeader(line)
                DocTag.LI -> ContentStripper.stripListItem(line)
                DocTag.BLOCKQUOTE -> ContentStripper.stripBlockquote(line)
                DocTag.DT, DocTag.DD -> ContentStripper.strip(line)
                else -> line
            }

            // Store decoration as attribute
            val decoration = ContentStripper.getDecoration(line)

            elements.add(ParsedElement(
                index = index,
                tag = tag,
                startOffset = offset,
                endOffset = offset + line.length,
                attributes = buildMap {
                    scriptAttrs.lang?.let { put("lang", it) }
                    scriptAttrs.dir?.let { put("dir", it.value) }
                    decoration?.let { put("decoration", it) }
                },
                content = strippedContent
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
     * Push a new tag with script attributes and return its segment.
     */
    private fun pushTagWithScript(tag: DocTag, scriptAttrs: ScriptAttrs = ScriptAttrs.EMPTY): IndexSegment {
        val count = tagCounts.getOrPut(tag) { 0 } + 1
        tagCounts[tag] = count
        return IndexSegment(tag, count, emptyList(), scriptAttrs)
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
    fun push(tag: DocTag, cssClasses: List<String> = emptyList(), scriptAttrs: ScriptAttrs = ScriptAttrs.EMPTY, dataAttrs: Map<String, String> = emptyMap()): DocumentIndex {
        val count = tagCounts.getOrPut(tag) { 0 } + 1
        tagCounts[tag] = count

        val segment = IndexSegment(tag, count, cssClasses, scriptAttrs, dataAttrs)
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
