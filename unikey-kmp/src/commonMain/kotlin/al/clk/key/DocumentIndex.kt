package al.clk.key

import kotlin.math.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import al.clk.key.proto.*

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

        /**
         * Get ScriptAttrs for a language code.
         * Returns null for unknown languages (use ScriptDetector instead).
         */
        fun forLang(lang: String): ScriptAttrs? = when (lang.lowercase()) {
            "he", "heb", "hebrew" -> HEBREW
            "ar", "ara", "arabic" -> ARABIC
            "fa", "fas", "persian", "farsi" -> ScriptAttrs("fa", TextDir.RTL)
            "ur", "urd", "urdu" -> ScriptAttrs("ur", TextDir.RTL)
            "en", "eng", "english" -> ENGLISH
            "de", "deu", "german" -> ScriptAttrs("de", TextDir.LTR)
            "fr", "fra", "french" -> ScriptAttrs("fr", TextDir.LTR)
            "es", "spa", "spanish" -> ScriptAttrs("es", TextDir.LTR)
            "ja", "jpn", "japanese" -> ScriptAttrs("ja", TextDir.LTR)
            "zh", "zho", "chinese" -> ScriptAttrs("zh", TextDir.LTR)
            "ko", "kor", "korean" -> ScriptAttrs("ko", TextDir.LTR)
            "ru", "rus", "russian" -> ScriptAttrs("ru", TextDir.LTR)
            else -> null
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
data class IndexRange(
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
        fun parse(segment: String): IndexRange? {
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
            return IndexRange(tag, instance, cssClasses, scriptAttrs, dataAttrs, defPairs)
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
    val segments: List<IndexRange>
) {
    /** Depth of the index (number of path segments) */
    val depth: Int get() = segments.size

    /** Formatted path: HTML1/BODY1/P1.intro */
    val formatted: String get() = segments.joinToString("/") { it.formatted }

    /** Get the leaf (last) segment */
    val leaf: IndexRange? get() = segments.lastOrNull()

    /** Get parent index (all segments except last) */
    fun parent(): DocumentIndex? {
        if (segments.size <= 1) return null
        return DocumentIndex(segments.dropLast(1))
    }

    /** Append a child segment */
    fun child(segment: IndexRange): DocumentIndex {
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
            val segments = segmentStrings.mapNotNull { IndexRange.parse(it) }

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
        val stack = mutableListOf<IndexRange>()

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

                        val segment = IndexRange(tag, count, cssClasses, scriptAttrs, dataAttrs)
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

                    val segment = IndexRange(tag, count)
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
// TABLE DATA - Parse tables to TSV/JSON with cell indexing and formulas
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Cell reference like Excel (A1, B2, AA100).
 * Column is letter-based (A=0, B=1, Z=25, AA=26).
 * Row is 1-based.
 */
data class CellRef(
    val col: Int,   // 0-based column index
    val row: Int    // 0-based row index
) {
    /** Column letter (A, B, ..., Z, AA, AB, ...) */
    val colLetter: String get() {
        var n = col
        val sb = StringBuilder()
        do {
            sb.insert(0, ('A' + (n % 26)))
            n = n / 26 - 1
        } while (n >= 0)
        return sb.toString()
    }

    /** Formatted reference: A1, B2, AA100 */
    val formatted: String get() = "$colLetter${row + 1}"

    /** Range reference for a cell range */
    fun rangeTo(other: CellRef): String = "$formatted:${other.formatted}"

    companion object {
        /** Parse cell reference string like "A1", "B2", "AA100" */
        fun parse(ref: String): CellRef? {
            val match = Regex("^([A-Z]+)(\\d+)$").matchEntire(ref.uppercase()) ?: return null
            val colStr = match.groupValues[1]
            val rowStr = match.groupValues[2]

            var col = 0
            for (c in colStr) {
                col = col * 26 + (c - 'A' + 1)
            }
            col -= 1  // 0-based

            val row = rowStr.toIntOrNull()?.minus(1) ?: return null
            return CellRef(col, row)
        }

        /** Create from 0-based indices */
        fun of(col: Int, row: Int) = CellRef(col, row)
    }
}

/**
 * Cell value types for table data.
 */
sealed class CellValue {
    abstract val raw: String

    /** Empty cell */
    data object Empty : CellValue() {
        override val raw: String = ""
    }

    /** Text value */
    data class Text(override val raw: String) : CellValue()

    /** Numeric value */
    data class Number(val value: Double) : CellValue() {
        override val raw: String = if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }

    /** Formula (starts with =) */
    data class Formula(val expression: String, var cachedResult: CellValue? = null) : CellValue() {
        override val raw: String = "=$expression"
    }

    companion object {
        /** Parse cell content into appropriate type */
        fun parse(content: String): CellValue {
            val trimmed = content.trim()
            return when {
                trimmed.isEmpty() -> Empty
                trimmed.startsWith("=") -> Formula(trimmed.drop(1))
                else -> {
                    val num = trimmed.toDoubleOrNull()
                    if (num != null) Number(num) else Text(trimmed)
                }
            }
        }
    }
}

/**
 * Table row with indexed cells.
 */
data class TableRow(
    val rowIndex: Int,
    val cells: List<CellValue>,
    val isHeader: Boolean = false
) {
    /** Get cell by column index */
    operator fun get(col: Int): CellValue = cells.getOrElse(col) { CellValue.Empty }

    /** Get cell reference for column */
    fun cellRef(col: Int) = CellRef(col, rowIndex)

    /** Number of cells */
    val size: Int get() = cells.size

    /** Convert to TSV line */
    fun toTsv(): String = cells.joinToString("\t") { it.raw }

    /** Convert to JSON array */
    fun toJsonArray(): String = "[${cells.joinToString(",") { "\"${escapeJson(it.raw)}\"" }}]"

    private fun escapeJson(s: String): String = s
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
}

/**
 * Table data with headers, rows, and cell indexing.
 * Supports TSV/JSON export and formula evaluation.
 */
data class TableData(
    val headers: List<String>,
    val rows: List<TableRow>,
    val name: String = ""  // Sheet name from first header or caption
) {
    /** Number of columns */
    val colCount: Int get() = headers.size.coerceAtLeast(rows.maxOfOrNull { it.size } ?: 0)

    /** Number of data rows (excluding header) */
    val rowCount: Int get() = rows.size

    /** Get cell by reference */
    operator fun get(ref: CellRef): CellValue {
        if (ref.row < 0 || ref.row >= rows.size) return CellValue.Empty
        return rows[ref.row][ref.col]
    }

    /** Get cell by string reference */
    operator fun get(ref: String): CellValue {
        val cellRef = CellRef.parse(ref) ?: return CellValue.Empty
        return get(cellRef)
    }

    /** Get header by column index */
    fun header(col: Int): String = headers.getOrElse(col) { CellRef.of(col, 0).colLetter }

    /** Get column index by header name */
    fun colIndex(headerName: String): Int = headers.indexOf(headerName)

    /** Convert to TSV string */
    fun toTsv(): String = buildString {
        if (headers.isNotEmpty()) {
            appendLine(headers.joinToString("\t"))
        }
        rows.forEach { appendLine(it.toTsv()) }
    }

    /** Convert to JSON array of arrays */
    fun toJsonArray(): String = buildString {
        append("[")
        if (headers.isNotEmpty()) {
            append("[${headers.joinToString(",") { "\"${escapeJson(it)}\"" }}]")
            if (rows.isNotEmpty()) append(",")
        }
        append(rows.joinToString(",") { it.toJsonArray() })
        append("]")
    }

    /** Convert to JSON array of objects (using headers as keys) */
    fun toJsonObjects(): String = buildString {
        append("[")
        append(rows.joinToString(",") { row ->
            val pairs = headers.mapIndexed { i, h ->
                "\"${escapeJson(h)}\":\"${escapeJson(row[i].raw)}\""
            }
            "{${pairs.joinToString(",")}}"
        })
        append("]")
    }

    private fun escapeJson(s: String): String = s
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")

    companion object {
        /** HTML table pattern */
        private val TABLE_PATTERN by lazy { Regex("""<table[^>]*>([\s\S]*?)</table>""", RegexOption.IGNORE_CASE) }
        private val TR_PATTERN by lazy { Regex("""<tr[^>]*>([\s\S]*?)</tr>""", RegexOption.IGNORE_CASE) }
        private val TH_PATTERN by lazy { Regex("""<th[^>]*>([\s\S]*?)</th>""", RegexOption.IGNORE_CASE) }
        private val TD_PATTERN by lazy { Regex("""<td[^>]*>([\s\S]*?)</td>""", RegexOption.IGNORE_CASE) }
        private val CAPTION_PATTERN by lazy { Regex("""<caption[^>]*>([\s\S]*?)</caption>""", RegexOption.IGNORE_CASE) }

        /** Markdown table row pattern */
        private val MD_ROW_PATTERN by lazy { Regex("""^\|(.+)\|$""") }
        private val MD_SEPARATOR by lazy { Regex("""^\|[\s:-]+\|$""") }

        /** Parse HTML table */
        fun parseHtml(html: String): TableData? {
            val tableMatch = TABLE_PATTERN.find(html) ?: return null
            val tableContent = tableMatch.groupValues[1]

            // Extract caption for sheet name
            val caption = CAPTION_PATTERN.find(tableContent)?.groupValues?.get(1)?.trim() ?: ""

            val trMatches = TR_PATTERN.findAll(tableContent).toList()
            if (trMatches.isEmpty()) return null

            val headers = mutableListOf<String>()
            val rows = mutableListOf<TableRow>()

            trMatches.forEachIndexed { rowIdx, tr ->
                val rowContent = tr.groupValues[1]
                val thCells = TH_PATTERN.findAll(rowContent).map { stripHtml(it.groupValues[1]) }.toList()
                val tdCells = TD_PATTERN.findAll(rowContent).map { stripHtml(it.groupValues[1]) }.toList()

                if (thCells.isNotEmpty() && headers.isEmpty()) {
                    // This is the header row
                    headers.addAll(thCells)
                    rows.add(TableRow(0, thCells.map { CellValue.parse(it) }, isHeader = true))
                } else {
                    val cells = if (tdCells.isNotEmpty()) tdCells else thCells
                    rows.add(TableRow(rows.size, cells.map { CellValue.parse(it) }))
                }
            }

            val name = caption.ifEmpty { headers.firstOrNull() ?: "Sheet1" }
            return TableData(headers, rows.drop(1), name)  // Drop header row from data rows
        }

        /** Parse Markdown table */
        fun parseMarkdown(md: String): TableData? {
            val lines = LineSeparator.splitLines(md).filter { it.isNotBlank() }
            if (lines.size < 2) return null

            val headerLine = lines.firstOrNull { MD_ROW_PATTERN.matches(it.trim()) } ?: return null
            val headerIdx = lines.indexOf(headerLine)

            // Parse headers
            val headers = headerLine.trim().trim('|').split("|").map { it.trim() }
            if (headers.isEmpty()) return null

            // Skip separator line
            val dataLines = lines.drop(headerIdx + 1).filterNot { MD_SEPARATOR.matches(it.trim()) }

            val rows = dataLines.mapIndexed { idx, line ->
                val cells = line.trim().trim('|').split("|").map { CellValue.parse(it.trim()) }
                TableRow(idx, cells)
            }

            return TableData(headers, rows, headers.firstOrNull() ?: "Sheet1")
        }

        /** Parse TSV string */
        fun parseTsv(tsv: String, hasHeader: Boolean = true): TableData {
            val lines = LineSeparator.splitLines(tsv).filter { it.isNotBlank() }
            if (lines.isEmpty()) return TableData(emptyList(), emptyList())

            val headers = if (hasHeader) lines.first().split("\t") else emptyList()
            val dataLines = if (hasHeader) lines.drop(1) else lines

            val rows = dataLines.mapIndexed { idx, line ->
                val cells = line.split("\t").map { CellValue.parse(it) }
                TableRow(idx, cells)
            }

            return TableData(headers, rows, headers.firstOrNull() ?: "Sheet1")
        }

        private fun stripHtml(html: String): String {
            return html.replace(Regex("<[^>]+>"), "").trim()
        }
    }
}

/**
 * Formula evaluator for table cells (like Apache POI).
 * Supports basic functions: SUM, AVG, COUNT, MIN, MAX, IF.
 */
class FormulaEvaluator(private val table: TableData) {

    /** Evaluate all formulas in the table */
    fun evaluateAll(): TableData {
        val newRows = table.rows.map { row ->
            val newCells = row.cells.map { cell ->
                if (cell is CellValue.Formula) {
                    val result = evaluate(cell.expression)
                    cell.cachedResult = result
                    result
                } else cell
            }
            TableRow(row.rowIndex, newCells, row.isHeader)
        }
        return table.copy(rows = newRows)
    }

    /** Evaluate a single formula expression */
    fun evaluate(expression: String): CellValue {
        val expr = expression.trim().uppercase()
        return try {
            // Extract function name if present
            val funcMatch = Regex("""^([A-Z]+)\(""").find(expr)
            if (funcMatch != null) {
                val funcName = funcMatch.groupValues[1]
                return evalFunction(funcName, expr)
            }

            // Check for cell reference
            CellRef.parse(expr)?.let { return table[expr] }

            // Check for arithmetic expression
            if (hasArithmeticOp(expr)) return evalArithmetic(expr)

            // Return as text
            CellValue.Text(expression)
        } catch (e: Exception) {
            CellValue.Text("#ERROR: ${e.message}")
        }
    }

    private fun hasArithmeticOp(expr: String): Boolean {
        var depth = 0
        for (c in expr) {
            when (c) {
                '(' -> depth++
                ')' -> depth--
                '+', '-', '*', '/', '^', '%' -> if (depth == 0) return true
            }
        }
        return false
    }

    private fun evalFunction(name: String, expr: String): CellValue {
        val args = extractFunctionArgs(expr)
        val values by lazy { extractRangeValues(expr) }

        return when (name) {
            // ═══ Aggregate Functions ═══
            "SUM" -> CellValue.Number(values.sum())
            "AVERAGE", "AVG" -> CellValue.Number(if (values.isEmpty()) 0.0 else values.sum() / values.size)
            "COUNT" -> CellValue.Number(values.size.toDouble())
            "COUNTA" -> CellValue.Number(extractAllValues(expr).count { it.isNotEmpty() }.toDouble())
            "MIN" -> CellValue.Number(values.minOrNull() ?: 0.0)
            "MAX" -> CellValue.Number(values.maxOrNull() ?: 0.0)
            "PRODUCT" -> CellValue.Number(values.fold(1.0) { acc, v -> acc * v })
            "MEDIAN" -> {
                val sorted = values.sorted()
                val mid = sorted.size / 2
                CellValue.Number(if (sorted.size % 2 == 0) (sorted[mid - 1] + sorted[mid]) / 2 else sorted[mid])
            }
            "STDEV", "STDEV.S" -> CellValue.Number(stdev(values, sample = true))
            "STDEV.P" -> CellValue.Number(stdev(values, sample = false))
            "VAR", "VAR.S" -> CellValue.Number(variance(values, sample = true))
            "VAR.P" -> CellValue.Number(variance(values, sample = false))

            // ═══ Math Functions ═══
            "ABS" -> CellValue.Number(kotlin.math.abs(resolveValue(args[0])))
            "SQRT" -> CellValue.Number(kotlin.math.sqrt(resolveValue(args[0])))
            "POWER", "POW" -> CellValue.Number(resolveValue(args[0]).pow(resolveValue(args[1])))
            "MOD" -> {
                val n = resolveValue(args[0])
                val d = resolveValue(args[1])
                CellValue.Number(n - d * kotlin.math.floor(n / d))
            }
            "ROUND" -> {
                val num = resolveValue(args[0])
                val digits = args.getOrNull(1)?.let { resolveValue(it).toInt() } ?: 0
                val factor = 10.0.pow(digits.toDouble())
                CellValue.Number(kotlin.math.round(num * factor) / factor)
            }
            "ROUNDUP" -> {
                val num = resolveValue(args[0])
                val digits = args.getOrNull(1)?.let { resolveValue(it).toInt() } ?: 0
                val factor = 10.0.pow(digits.toDouble())
                CellValue.Number(kotlin.math.ceil(num * factor) / factor)
            }
            "ROUNDDOWN" -> {
                val num = resolveValue(args[0])
                val digits = args.getOrNull(1)?.let { resolveValue(it).toInt() } ?: 0
                val factor = 10.0.pow(digits.toDouble())
                CellValue.Number(kotlin.math.floor(num * factor) / factor)
            }
            "FLOOR" -> CellValue.Number(kotlin.math.floor(resolveValue(args[0])))
            "CEILING", "CEIL" -> CellValue.Number(kotlin.math.ceil(resolveValue(args[0])))
            "INT", "TRUNC" -> CellValue.Number(kotlin.math.truncate(resolveValue(args[0])))
            "SIGN" -> CellValue.Number(kotlin.math.sign(resolveValue(args[0])))
            "EXP" -> CellValue.Number(kotlin.math.exp(resolveValue(args[0])))
            "LN" -> CellValue.Number(kotlin.math.ln(resolveValue(args[0])))
            "LOG", "LOG10" -> CellValue.Number(kotlin.math.log10(resolveValue(args[0])))
            "LOG2" -> CellValue.Number(kotlin.math.log2(resolveValue(args[0])))
            "PI" -> CellValue.Number(kotlin.math.PI)
            "RAND" -> CellValue.Number(kotlin.random.Random.nextDouble())
            "RANDBETWEEN" -> {
                val low = resolveValue(args[0]).toInt()
                val high = resolveValue(args[1]).toInt()
                CellValue.Number(kotlin.random.Random.nextInt(low, high + 1).toDouble())
            }

            // ═══ Trigonometric Functions ═══
            "SIN" -> CellValue.Number(kotlin.math.sin(resolveValue(args[0])))
            "COS" -> CellValue.Number(kotlin.math.cos(resolveValue(args[0])))
            "TAN" -> CellValue.Number(kotlin.math.tan(resolveValue(args[0])))
            "ASIN" -> CellValue.Number(kotlin.math.asin(resolveValue(args[0])))
            "ACOS" -> CellValue.Number(kotlin.math.acos(resolveValue(args[0])))
            "ATAN" -> CellValue.Number(kotlin.math.atan(resolveValue(args[0])))
            "ATAN2" -> CellValue.Number(kotlin.math.atan2(resolveValue(args[0]), resolveValue(args[1])))
            "SINH" -> CellValue.Number(kotlin.math.sinh(resolveValue(args[0])))
            "COSH" -> CellValue.Number(kotlin.math.cosh(resolveValue(args[0])))
            "TANH" -> CellValue.Number(kotlin.math.tanh(resolveValue(args[0])))
            "RADIANS" -> CellValue.Number(resolveValue(args[0]) * kotlin.math.PI / 180.0)
            "DEGREES" -> CellValue.Number(resolveValue(args[0]) * 180.0 / kotlin.math.PI)

            // ═══ Logical Functions ═══
            "IF" -> {
                if (args.size < 3) return CellValue.Text("#ERROR: IF needs 3 args")
                if (evaluateCondition(args[0])) evaluate(args[1]) else evaluate(args[2])
            }
            "AND" -> CellValue.Number(if (args.all { evaluateCondition(it) }) 1.0 else 0.0)
            "OR" -> CellValue.Number(if (args.any { evaluateCondition(it) }) 1.0 else 0.0)
            "NOT" -> CellValue.Number(if (evaluateCondition(args[0])) 0.0 else 1.0)
            "XOR" -> CellValue.Number(if (args.count { evaluateCondition(it) } % 2 == 1) 1.0 else 0.0)
            "TRUE" -> CellValue.Number(1.0)
            "FALSE" -> CellValue.Number(0.0)
            "IFERROR" -> {
                val result = evaluate(args[0])
                if (result.raw.startsWith("#")) evaluate(args.getOrElse(1) { "0" }) else result
            }
            "IFNA" -> {
                val result = evaluate(args[0])
                if (result.raw == "#N/A") evaluate(args.getOrElse(1) { "0" }) else result
            }

            // ═══ Text Functions ═══
            "LEN" -> CellValue.Number(resolveText(args[0]).length.toDouble())
            "LEFT" -> {
                val text = resolveText(args[0])
                val n = args.getOrNull(1)?.let { resolveValue(it).toInt() } ?: 1
                CellValue.Text(text.take(n))
            }
            "RIGHT" -> {
                val text = resolveText(args[0])
                val n = args.getOrNull(1)?.let { resolveValue(it).toInt() } ?: 1
                CellValue.Text(text.takeLast(n))
            }
            "MID" -> {
                val text = resolveText(args[0])
                val start = resolveValue(args[1]).toInt() - 1  // 1-based
                val len = resolveValue(args[2]).toInt()
                CellValue.Text(text.substring(start.coerceAtLeast(0), (start + len).coerceAtMost(text.length)))
            }
            "CONCAT", "CONCATENATE" -> CellValue.Text(args.joinToString("") { resolveText(it) })
            "TRIM" -> CellValue.Text(resolveText(args[0]).trim())
            "UPPER" -> CellValue.Text(resolveText(args[0]).uppercase())
            "LOWER" -> CellValue.Text(resolveText(args[0]).lowercase())
            "PROPER" -> CellValue.Text(resolveText(args[0]).split(" ").joinToString(" ") {
                it.lowercase().replaceFirstChar { c -> c.uppercase() }
            })
            "REPLACE" -> {
                val text = resolveText(args[0])
                val start = resolveValue(args[1]).toInt() - 1
                val len = resolveValue(args[2]).toInt()
                val newText = resolveText(args[3])
                CellValue.Text(text.replaceRange(start, start + len, newText))
            }
            "SUBSTITUTE" -> {
                val text = resolveText(args[0])
                val old = resolveText(args[1])
                val new = resolveText(args[2])
                CellValue.Text(text.replace(old, new))
            }
            "REPT" -> {
                val text = resolveText(args[0])
                val n = resolveValue(args[1]).toInt()
                CellValue.Text(text.repeat(n))
            }
            "FIND", "SEARCH" -> {
                val find = resolveText(args[0])
                val text = resolveText(args[1])
                val start = args.getOrNull(2)?.let { resolveValue(it).toInt() - 1 } ?: 0
                val idx = if (name == "SEARCH") text.lowercase().indexOf(find.lowercase(), start)
                          else text.indexOf(find, start)
                CellValue.Number((if (idx >= 0) idx + 1 else 0).toDouble())
            }
            "VALUE" -> CellValue.Number(resolveText(args[0]).toDoubleOrNull() ?: 0.0)
            "TEXT" -> CellValue.Text(resolveValue(args[0]).toString())

            // ═══ Lookup Functions (simplified) ═══
            "INDEX" -> {
                val range = args[0]
                val rowNum = resolveValue(args[1]).toInt()
                val colNum = args.getOrNull(2)?.let { resolveValue(it).toInt() } ?: 1
                val ref = CellRef.parse(range.split(":")[0]) ?: return CellValue.Text("#REF!")
                val targetRef = CellRef(ref.col + colNum - 1, ref.row + rowNum - 1)
                table[targetRef]
            }
            "MATCH" -> {
                val lookup = resolveValue(args[0])
                val rangeValues = extractRangeValues("($args[1])")
                val idx = rangeValues.indexOf(lookup)
                CellValue.Number((if (idx >= 0) idx + 1 else 0).toDouble())
            }

            else -> CellValue.Text("#NAME? Unknown function: $name")
        }
    }

    private fun stdev(values: List<Double>, sample: Boolean): Double {
        if (values.size < 2) return 0.0
        val mean = values.sum() / values.size
        val sumSq = values.sumOf { (it - mean) * (it - mean) }
        val n = if (sample) values.size - 1 else values.size
        return kotlin.math.sqrt(sumSq / n)
    }

    private fun variance(values: List<Double>, sample: Boolean): Double {
        if (values.size < 2) return 0.0
        val mean = values.sum() / values.size
        val sumSq = values.sumOf { (it - mean) * (it - mean) }
        val n = if (sample) values.size - 1 else values.size
        return sumSq / n
    }

    private fun evalArithmetic(expr: String): CellValue {
        // Tokenize and evaluate with proper precedence
        val tokens = tokenize(expr)
        val result = parseExpression(tokens, 0).first
        return CellValue.Number(result)
    }

    private fun tokenize(expr: String): List<String> {
        val tokens = mutableListOf<String>()
        var current = StringBuilder()
        var i = 0

        while (i < expr.length) {
            val c = expr[i]
            when {
                c in "+-*/%^()" -> {
                    if (current.isNotEmpty()) {
                        tokens.add(current.toString())
                        current = StringBuilder()
                    }
                    tokens.add(c.toString())
                }
                c.isWhitespace() -> {
                    if (current.isNotEmpty()) {
                        tokens.add(current.toString())
                        current = StringBuilder()
                    }
                }
                else -> current.append(c)
            }
            i++
        }
        if (current.isNotEmpty()) tokens.add(current.toString())
        return tokens
    }

    private fun parseExpression(tokens: List<String>, start: Int): Pair<Double, Int> {
        var (left, pos) = parseTerm(tokens, start)

        while (pos < tokens.size && tokens[pos] in listOf("+", "-")) {
            val op = tokens[pos]
            val (right, newPos) = parseTerm(tokens, pos + 1)
            left = if (op == "+") left + right else left - right
            pos = newPos
        }
        return left to pos
    }

    private fun parseTerm(tokens: List<String>, start: Int): Pair<Double, Int> {
        var (left, pos) = parsePower(tokens, start)

        while (pos < tokens.size && tokens[pos] in listOf("*", "/", "%")) {
            val op = tokens[pos]
            val (right, newPos) = parsePower(tokens, pos + 1)
            left = when (op) {
                "*" -> left * right
                "/" -> if (right != 0.0) left / right else Double.NaN
                "%" -> left % right
                else -> left
            }
            pos = newPos
        }
        return left to pos
    }

    private fun parsePower(tokens: List<String>, start: Int): Pair<Double, Int> {
        var (left, pos) = parseFactor(tokens, start)

        while (pos < tokens.size && tokens[pos] == "^") {
            val (right, newPos) = parseFactor(tokens, pos + 1)
            left = left.pow(right)
            pos = newPos
        }
        return left to pos
    }

    private fun parseFactor(tokens: List<String>, start: Int): Pair<Double, Int> {
        if (start >= tokens.size) return 0.0 to start

        val token = tokens[start]

        // Handle parentheses
        if (token == "(") {
            val (result, pos) = parseExpression(tokens, start + 1)
            val endPos = if (pos < tokens.size && tokens[pos] == ")") pos + 1 else pos
            return result to endPos
        }

        // Handle unary minus
        if (token == "-") {
            val (result, pos) = parseFactor(tokens, start + 1)
            return -result to pos
        }

        // Handle unary plus
        if (token == "+") {
            return parseFactor(tokens, start + 1)
        }

        // Number or cell reference
        return resolveValue(token) to start + 1
    }

    private fun resolveText(ref: String): String {
        val trimmed = ref.trim()
        // Check if it's a quoted string
        if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
            return trimmed.drop(1).dropLast(1)
        }
        // Check if it's a cell reference
        val cellRef = CellRef.parse(trimmed)
        if (cellRef != null) {
            return table[cellRef].raw
        }
        return trimmed
    }

    private fun extractAllValues(expr: String): List<String> {
        val inner = expr.substringAfter("(").substringBefore(")")
        val results = mutableListOf<String>()

        for (part in inner.split(",")) {
            val trimmed = part.trim()
            if (trimmed.contains(":")) {
                results.addAll(expandRangeAsStrings(trimmed))
            } else {
                results.add(resolveText(trimmed))
            }
        }
        return results
    }

    private fun expandRangeAsStrings(range: String): List<String> {
        val parts = range.split(":")
        if (parts.size != 2) return emptyList()

        val start = CellRef.parse(parts[0].trim()) ?: return emptyList()
        val end = CellRef.parse(parts[1].trim()) ?: return emptyList()

        val results = mutableListOf<String>()
        for (row in start.row..end.row) {
            for (col in start.col..end.col) {
                results.add(table[CellRef(col, row)].raw)
            }
        }
        return results
    }

    private fun extractRangeValues(expr: String): List<Double> {
        val inner = expr.substringAfter("(").substringBefore(")")
        val values = mutableListOf<Double>()

        for (part in inner.split(",")) {
            val trimmed = part.trim()
            if (trimmed.contains(":")) {
                // Range like A1:A10
                values.addAll(expandRange(trimmed))
            } else {
                // Single cell or value
                values.add(resolveValue(trimmed))
            }
        }
        return values
    }

    private fun expandRange(range: String): List<Double> {
        val parts = range.split(":")
        if (parts.size != 2) return emptyList()

        val start = CellRef.parse(parts[0].trim()) ?: return emptyList()
        val end = CellRef.parse(parts[1].trim()) ?: return emptyList()

        val values = mutableListOf<Double>()
        for (row in start.row..end.row) {
            for (col in start.col..end.col) {
                val cell = table[CellRef(col, row)]
                if (cell is CellValue.Number) values.add(cell.value)
            }
        }
        return values
    }

    private fun resolveValue(ref: String): Double {
        val cellRef = CellRef.parse(ref)
        if (cellRef != null) {
            return when (val cell = table[cellRef]) {
                is CellValue.Number -> cell.value
                is CellValue.Text -> cell.raw.toDoubleOrNull() ?: 0.0
                else -> 0.0
            }
        }
        return ref.toDoubleOrNull() ?: 0.0
    }

    private fun extractFunctionArgs(expr: String): List<String> {
        val inner = expr.substringAfter("(").substringBeforeLast(")")
        // Simple split by comma (doesn't handle nested functions)
        return inner.split(",").map { it.trim() }
    }

    private fun evaluateCondition(condition: String): Boolean {
        // Simple conditions: A1>10, B2=5, C3<>0
        val operators = listOf(">=", "<=", "<>", "!=", "=", ">", "<")
        for (op in operators) {
            if (condition.contains(op)) {
                val parts = condition.split(op, limit = 2)
                if (parts.size == 2) {
                    val left = resolveValue(parts[0].trim())
                    val right = resolveValue(parts[1].trim())
                    return when (op) {
                        ">=" -> left >= right
                        "<=" -> left <= right
                        "<>", "!=" -> left != right
                        "=" -> left == right
                        ">" -> left > right
                        "<" -> left < right
                        else -> false
                    }
                }
            }
        }
        return resolveValue(condition) != 0.0
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// JSON PATH - Query language for JSON (RFC 9535)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * JSONPath query result.
 */
data class JsonPathResult(
    val path: String,
    val value: Any?,
    val index: Int = -1
)

/**
 * JSONPath implementation (subset of RFC 9535).
 * Supports: $, ., [], *, .., [n], [n:m], [?()], @
 */
object JsonPath {
    /**
     * Query JSON-like structure using JSONPath.
     * @param root The root object (Map or List)
     * @param path JSONPath expression (e.g., "$.store.book[0].title")
     */
    fun query(root: Any?, path: String): List<JsonPathResult> {
        if (root == null) return emptyList()
        val results = mutableListOf<JsonPathResult>()
        val segments = parse(path)
        traverse(root, segments, "$", results)
        return results
    }

    /** Parse JSONPath into segments */
    private fun parse(path: String): List<PathSegment> {
        val segments = mutableListOf<PathSegment>()
        var i = 0
        val p = path.trim()

        if (p.startsWith("$")) i = 1
        if (i < p.length && p[i] == '.') i++

        while (i < p.length) {
            when {
                p[i] == '.' && i + 1 < p.length && p[i + 1] == '.' -> {
                    segments.add(PathSegment.Recursive)
                    i += 2
                }
                p[i] == '.' -> {
                    i++
                }
                p[i] == '[' -> {
                    val end = p.indexOf(']', i)
                    if (end == -1) break
                    val inner = p.substring(i + 1, end).trim()
                    segments.add(parseBracket(inner))
                    i = end + 1
                }
                p[i] == '*' -> {
                    segments.add(PathSegment.Wildcard)
                    i++
                }
                else -> {
                    val start = i
                    while (i < p.length && p[i] !in ".[") i++
                    val name = p.substring(start, i)
                    if (name.isNotEmpty()) segments.add(PathSegment.Property(name))
                }
            }
        }
        return segments
    }

    private fun parseBracket(inner: String): PathSegment = when {
        inner == "*" -> PathSegment.Wildcard
        inner.startsWith("?") -> PathSegment.Filter(inner.drop(1).trim('(', ')'))
        inner.contains(":") -> {
            val parts = inner.split(":")
            val start = parts.getOrNull(0)?.toIntOrNull() ?: 0
            val end = parts.getOrNull(1)?.toIntOrNull()
            PathSegment.Slice(start, end)
        }
        inner.startsWith("'") || inner.startsWith("\"") ->
            PathSegment.Property(inner.trim('\'', '"'))
        else -> inner.toIntOrNull()?.let { PathSegment.Index(it) }
            ?: PathSegment.Property(inner)
    }

    private fun traverse(
        node: Any?,
        segments: List<PathSegment>,
        currentPath: String,
        results: MutableList<JsonPathResult>
    ) {
        if (segments.isEmpty()) {
            results.add(JsonPathResult(currentPath, node))
            return
        }

        val seg = segments.first()
        val rest = segments.drop(1)

        when (seg) {
            is PathSegment.Property -> {
                when (node) {
                    is Map<*, *> -> node[seg.name]?.let {
                        traverse(it, rest, "$currentPath.${seg.name}", results)
                    }
                }
            }
            is PathSegment.Index -> {
                when (node) {
                    is List<*> -> node.getOrNull(seg.index)?.let {
                        traverse(it, rest, "$currentPath[${seg.index}]", results)
                    }
                }
            }
            is PathSegment.Wildcard -> {
                when (node) {
                    is Map<*, *> -> node.forEach { (k, v) ->
                        traverse(v, rest, "$currentPath.$k", results)
                    }
                    is List<*> -> node.forEachIndexed { i, v ->
                        traverse(v, rest, "$currentPath[$i]", results)
                    }
                }
            }
            is PathSegment.Recursive -> {
                // Match at current level
                traverse(node, rest, currentPath, results)
                // Recurse into children
                when (node) {
                    is Map<*, *> -> node.forEach { (k, v) ->
                        traverse(v, segments, "$currentPath.$k", results)
                    }
                    is List<*> -> node.forEachIndexed { i, v ->
                        traverse(v, segments, "$currentPath[$i]", results)
                    }
                }
            }
            is PathSegment.Slice -> {
                if (node is List<*>) {
                    val end = seg.end ?: node.size
                    val sublist = node.subList(seg.start.coerceAtLeast(0), end.coerceAtMost(node.size))
                    sublist.forEachIndexed { i, v ->
                        traverse(v, rest, "$currentPath[${seg.start + i}]", results)
                    }
                }
            }
            is PathSegment.Filter -> {
                // Simplified filter: @.property op value
                if (node is List<*>) {
                    node.forEachIndexed { i, item ->
                        if (matchFilter(item, seg.expr)) {
                            traverse(item, rest, "$currentPath[$i]", results)
                        }
                    }
                }
            }
        }
    }

    private fun matchFilter(item: Any?, expr: String): Boolean {
        if (item !is Map<*, *>) return false
        val pattern = Regex("""@\.(\w+)\s*(==|!=|>|<|>=|<=)\s*(.+)""")
        val match = pattern.matchEntire(expr.trim()) ?: return true
        val (prop, op, valueStr) = match.destructured
        val actual = item[prop] ?: return false
        val expectedStr = valueStr.trim('\'', '"')

        // Convert to comparable numbers using hashCode for non-numeric comparison
        fun toNum(v: Any?): Double? = when (v) {
            is Int -> v.toDouble()
            is Long -> v.toDouble()
            is Float -> v.toDouble()
            is Double -> v
            is String -> v.toDoubleOrNull()
            else -> null
        }

        val actualNum = toNum(actual)
        val expectedNum = toNum(expectedStr)

        return when (op) {
            "==" -> actual.toString() == expectedStr || actual.hashCode() == expectedStr.hashCode()
            "!=" -> actual.toString() != expectedStr && actual.hashCode() != expectedStr.hashCode()
            ">" -> if (actualNum != null && expectedNum != null) actualNum > expectedNum else false
            "<" -> if (actualNum != null && expectedNum != null) actualNum < expectedNum else false
            ">=" -> if (actualNum != null && expectedNum != null) actualNum >= expectedNum else false
            "<=" -> if (actualNum != null && expectedNum != null) actualNum <= expectedNum else false
            else -> false
        }
    }

    sealed class PathSegment {
        data class Property(val name: String) : PathSegment()
        data class Index(val index: Int) : PathSegment()
        data object Wildcard : PathSegment()
        data object Recursive : PathSegment()
        data class Slice(val start: Int, val end: Int?) : PathSegment()
        data class Filter(val expr: String) : PathSegment()
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// XML PATH - XPath subset for XML querying
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * XPath query result.
 */
data class XPathResult(
    val path: String,
    val nodeName: String,
    val value: String?,
    val attributes: Map<String, String> = emptyMap()
)

/**
 * Simplified XPath for document queries.
 * Supports: /, //, *, @attr, [n], [predicate]
 */
object XPath {
    /**
     * Query parsed elements using XPath-like syntax.
     */
    fun query(elements: List<ParsedElement>, xpath: String): List<XPathResult> {
        val results = mutableListOf<XPathResult>()
        val segments = parseXPath(xpath)

        elements.forEach { elem ->
            if (matchesPath(elem, segments)) {
                results.add(XPathResult(
                    path = elem.index.formatted,
                    nodeName = elem.tag.name,
                    value = elem.content,
                    attributes = elem.attributes
                ))
            }
        }
        return results
    }

    private fun parseXPath(xpath: String): List<XPathSegment> {
        val segments = mutableListOf<XPathSegment>()
        val parts = xpath.split("/").filter { it.isNotEmpty() }

        var i = 0
        while (i < parts.size) {
            val part = parts[i]
            when {
                part.isEmpty() && i == 0 -> {
                    // // at start means descendant
                    if (parts.getOrNull(i + 1)?.isEmpty() == true) {
                        segments.add(XPathSegment.Descendant)
                        i++
                    }
                }
                part == "*" -> segments.add(XPathSegment.Wildcard)
                part.startsWith("@") -> segments.add(XPathSegment.Attribute(part.drop(1)))
                part.contains("[") -> {
                    val name = part.substringBefore("[")
                    val pred = part.substringAfter("[").substringBefore("]")
                    segments.add(XPathSegment.Element(name, pred))
                }
                else -> segments.add(XPathSegment.Element(part, null))
            }
            i++
        }
        return segments
    }

    private fun matchesPath(elem: ParsedElement, segments: List<XPathSegment>): Boolean {
        if (segments.isEmpty()) return true

        val pathParts = elem.index.formatted.split("/")
        var segIdx = 0
        var pathIdx = 0

        while (segIdx < segments.size && pathIdx < pathParts.size) {
            val seg = segments[segIdx]
            val pathPart = pathParts[pathIdx]

            when (seg) {
                is XPathSegment.Element -> {
                    val tagName = pathPart.replace(Regex("\\d+.*"), "")
                    if (seg.name == "*" || seg.name.equals(tagName, ignoreCase = true)) {
                        if (seg.predicate != null && !matchPredicate(elem, seg.predicate)) {
                            return false
                        }
                        segIdx++
                    }
                    pathIdx++
                }
                is XPathSegment.Wildcard -> {
                    segIdx++
                    pathIdx++
                }
                is XPathSegment.Descendant -> {
                    // Skip to next segment match
                    segIdx++
                    if (segIdx >= segments.size) return true
                }
                is XPathSegment.Attribute -> {
                    return elem.attributes.containsKey(seg.name)
                }
            }
        }
        return segIdx >= segments.size
    }

    private fun matchPredicate(elem: ParsedElement, predicate: String): Boolean {
        return when {
            predicate.toIntOrNull() != null -> true // Index predicate, simplified
            predicate.startsWith("@") -> {
                val attrMatch = Regex("""@(\w+)='([^']*)'""").matchEntire(predicate)
                if (attrMatch != null) {
                    val (name, value) = attrMatch.destructured
                    elem.attributes[name] == value
                } else {
                    elem.attributes.containsKey(predicate.drop(1))
                }
            }
            else -> true
        }
    }

    sealed class XPathSegment {
        data class Element(val name: String, val predicate: String?) : XPathSegment()
        data object Wildcard : XPathSegment()
        data object Descendant : XPathSegment()
        data class Attribute(val name: String) : XPathSegment()
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// CSS SELECTORS - Query elements using CSS selector syntax
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * CSS Selector query engine for parsed elements.
 */
object CssSelector {
    /**
     * Query elements using CSS selector syntax.
     * Supports: tag, .class, #id, [attr], [attr=value], >, +, ~, :first-child, :last-child
     */
    fun query(elements: List<ParsedElement>, selector: String): List<ParsedElement> {
        val parsed = parseSelector(selector)
        return elements.filter { matchesSelector(it, parsed, elements) }
    }

    private fun parseSelector(selector: String): List<SelectorPart> {
        val parts = mutableListOf<SelectorPart>()
        var current = selector.trim()

        while (current.isNotEmpty()) {
            when {
                current.startsWith(">") -> {
                    parts.add(SelectorPart.Combinator.Child)
                    current = current.drop(1).trim()
                }
                current.startsWith("+") -> {
                    parts.add(SelectorPart.Combinator.Adjacent)
                    current = current.drop(1).trim()
                }
                current.startsWith("~") -> {
                    parts.add(SelectorPart.Combinator.Sibling)
                    current = current.drop(1).trim()
                }
                current.startsWith("#") -> {
                    val id = current.drop(1).takeWhile { it.isLetterOrDigit() || it == '-' || it == '_' }
                    parts.add(SelectorPart.Id(id))
                    current = current.drop(1 + id.length).trim()
                }
                current.startsWith(".") -> {
                    val cls = current.drop(1).takeWhile { it.isLetterOrDigit() || it == '-' || it == '_' }
                    parts.add(SelectorPart.Class(cls))
                    current = current.drop(1 + cls.length).trim()
                }
                current.startsWith("[") -> {
                    val end = current.indexOf(']')
                    if (end > 0) {
                        val attrExpr = current.substring(1, end)
                        parts.add(parseAttrSelector(attrExpr))
                        current = current.drop(end + 1).trim()
                    } else break
                }
                current.startsWith(":") -> {
                    val pseudo = current.drop(1).takeWhile { it.isLetterOrDigit() || it == '-' }
                    parts.add(SelectorPart.Pseudo(pseudo))
                    current = current.drop(1 + pseudo.length).trim()
                }
                current.startsWith("*") -> {
                    parts.add(SelectorPart.Universal)
                    current = current.drop(1).trim()
                }
                current.first().isLetter() -> {
                    val tag = current.takeWhile { it.isLetterOrDigit() || it == '-' }
                    parts.add(SelectorPart.Tag(tag))
                    current = current.drop(tag.length).trim()
                }
                else -> {
                    current = current.drop(1).trim()
                }
            }
        }
        return parts
    }

    private fun parseAttrSelector(expr: String): SelectorPart.Attribute {
        val ops = listOf("~=", "|=", "^=", "$=", "*=", "=")
        for (op in ops) {
            if (expr.contains(op)) {
                val parts = expr.split(op, limit = 2)
                return SelectorPart.Attribute(parts[0].trim(), op, parts[1].trim().trim('\'', '"'))
            }
        }
        return SelectorPart.Attribute(expr.trim(), null, null)
    }

    private fun matchesSelector(
        elem: ParsedElement,
        parts: List<SelectorPart>,
        allElements: List<ParsedElement>
    ): Boolean {
        for (part in parts) {
            val matches = when (part) {
                is SelectorPart.Tag -> elem.tag.name.equals(part.name, ignoreCase = true)
                is SelectorPart.Class -> elem.index.leaf?.cssClasses?.any {
                    it.equals(part.name, ignoreCase = true)
                } ?: false
                is SelectorPart.Id -> elem.attributes["id"] == part.id
                is SelectorPart.Attribute -> matchAttribute(elem, part)
                is SelectorPart.Universal -> true
                is SelectorPart.Pseudo -> matchPseudo(elem, part.name, allElements)
                is SelectorPart.Combinator -> true // Handled separately
            }
            if (!matches) return false
        }
        return true
    }

    private fun matchAttribute(elem: ParsedElement, attr: SelectorPart.Attribute): Boolean {
        val value = elem.attributes[attr.name] ?: return attr.op == null
        if (attr.op == null) return true
        val expected = attr.value ?: return false

        return when (attr.op) {
            "=" -> value == expected
            "~=" -> value.split(" ").contains(expected)
            "|=" -> value == expected || value.startsWith("$expected-")
            "^=" -> value.startsWith(expected)
            "$=" -> value.endsWith(expected)
            "*=" -> value.contains(expected)
            else -> false
        }
    }

    private fun matchPseudo(elem: ParsedElement, pseudo: String, allElements: List<ParsedElement>): Boolean {
        val elemParent = elem.index.parent()?.formatted
        val siblings = allElements.filter {
            it.index.parent()?.formatted == elemParent
        }
        return when (pseudo) {
            "first-child" -> siblings.firstOrNull() == elem
            "last-child" -> siblings.lastOrNull() == elem
            "only-child" -> siblings.size == 1
            "empty" -> elem.content.isBlank()
            else -> true
        }
    }

    sealed class SelectorPart {
        data class Tag(val name: String) : SelectorPart()
        data class Class(val name: String) : SelectorPart()
        data class Id(val id: String) : SelectorPart()
        data class Attribute(val name: String, val op: String?, val value: String?) : SelectorPart()
        data object Universal : SelectorPart()
        data class Pseudo(val name: String) : SelectorPart()
        sealed class Combinator : SelectorPart() {
            data object Child : Combinator()
            data object Adjacent : Combinator()
            data object Sibling : Combinator()
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// JSON PATCH - RFC 6902 implementation
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * JSON Patch operation (RFC 6902).
 */
sealed class JsonPatchOp {
    abstract val path: String

    data class Add(override val path: String, val value: Any?) : JsonPatchOp()
    data class Remove(override val path: String) : JsonPatchOp()
    data class Replace(override val path: String, val value: Any?) : JsonPatchOp()
    data class Move(override val path: String, val from: String) : JsonPatchOp()
    data class Copy(override val path: String, val from: String) : JsonPatchOp()
    data class Test(override val path: String, val value: Any?) : JsonPatchOp()

    /** Convert to JSON-like map */
    fun toMap(): Map<String, Any?> = when (this) {
        is Add -> mapOf("op" to "add", "path" to path, "value" to value)
        is Remove -> mapOf("op" to "remove", "path" to path)
        is Replace -> mapOf("op" to "replace", "path" to path, "value" to value)
        is Move -> mapOf("op" to "move", "from" to from, "path" to path)
        is Copy -> mapOf("op" to "copy", "from" to from, "path" to path)
        is Test -> mapOf("op" to "test", "path" to path, "value" to value)
    }

    companion object {
        /** Parse from map */
        fun fromMap(map: Map<String, Any?>): JsonPatchOp? {
            val op = map["op"] as? String ?: return null
            val path = map["path"] as? String ?: return null
            return when (op) {
                "add" -> Add(path, map["value"])
                "remove" -> Remove(path)
                "replace" -> Replace(path, map["value"])
                "move" -> Move(path, map["from"] as? String ?: return null)
                "copy" -> Copy(path, map["from"] as? String ?: return null)
                "test" -> Test(path, map["value"])
                else -> null
            }
        }
    }
}

/**
 * JSON Patch implementation (RFC 6902).
 */
object JsonPatch {
    /**
     * Apply patch operations to a document.
     * @return Modified document or null if test fails
     */
    fun apply(document: MutableMap<String, Any?>, ops: List<JsonPatchOp>): MutableMap<String, Any?>? {
        for (op in ops) {
            val success = when (op) {
                is JsonPatchOp.Add -> applyAdd(document, op.path, op.value)
                is JsonPatchOp.Remove -> applyRemove(document, op.path)
                is JsonPatchOp.Replace -> applyReplace(document, op.path, op.value)
                is JsonPatchOp.Move -> applyMove(document, op.from, op.path)
                is JsonPatchOp.Copy -> applyCopy(document, op.from, op.path)
                is JsonPatchOp.Test -> applyTest(document, op.path, op.value)
            }
            if (!success) return null
        }
        return document
    }

    /**
     * Generate patch from two documents (diff).
     */
    fun diff(source: Map<String, Any?>, target: Map<String, Any?>, path: String = ""): List<JsonPatchOp> {
        val ops = mutableListOf<JsonPatchOp>()

        // Check for removals and changes
        for ((key, sourceVal) in source) {
            val currentPath = "$path/$key"
            if (key !in target) {
                ops.add(JsonPatchOp.Remove(currentPath))
            } else {
                val targetVal = target[key]
                if (sourceVal != targetVal) {
                    when {
                        sourceVal is Map<*, *> && targetVal is Map<*, *> -> {
                            @Suppress("UNCHECKED_CAST")
                            ops.addAll(diff(sourceVal as Map<String, Any?>, targetVal as Map<String, Any?>, currentPath))
                        }
                        else -> ops.add(JsonPatchOp.Replace(currentPath, targetVal))
                    }
                }
            }
        }

        // Check for additions
        for ((key, targetVal) in target) {
            if (key !in source) {
                ops.add(JsonPatchOp.Add("$path/$key", targetVal))
            }
        }

        return ops
    }

    private fun applyAdd(doc: MutableMap<String, Any?>, path: String, value: Any?): Boolean {
        val (parent, key) = resolvePath(doc, path) ?: return false
        when (parent) {
            is MutableMap<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                (parent as MutableMap<String, Any?>)[key] = value
            }
            is MutableList<*> -> {
                val idx = key.toIntOrNull() ?: return false
                @Suppress("UNCHECKED_CAST")
                (parent as MutableList<Any?>).add(idx, value)
            }
            else -> return false
        }
        return true
    }

    private fun applyRemove(doc: MutableMap<String, Any?>, path: String): Boolean {
        val (parent, key) = resolvePath(doc, path) ?: return false
        when (parent) {
            is MutableMap<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                (parent as MutableMap<String, Any?>).remove(key)
            }
            is MutableList<*> -> {
                val idx = key.toIntOrNull() ?: return false
                @Suppress("UNCHECKED_CAST")
                (parent as MutableList<Any?>).removeAt(idx)
            }
            else -> return false
        }
        return true
    }

    private fun applyReplace(doc: MutableMap<String, Any?>, path: String, value: Any?): Boolean {
        val (parent, key) = resolvePath(doc, path) ?: return false
        when (parent) {
            is MutableMap<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                (parent as MutableMap<String, Any?>)[key] = value
            }
            is MutableList<*> -> {
                val idx = key.toIntOrNull() ?: return false
                @Suppress("UNCHECKED_CAST")
                (parent as MutableList<Any?>)[idx] = value
            }
            else -> return false
        }
        return true
    }

    private fun applyMove(doc: MutableMap<String, Any?>, from: String, path: String): Boolean {
        val value = getValue(doc, from) ?: return false
        if (!applyRemove(doc, from)) return false
        return applyAdd(doc, path, value)
    }

    private fun applyCopy(doc: MutableMap<String, Any?>, from: String, path: String): Boolean {
        val value = getValue(doc, from) ?: return false
        return applyAdd(doc, path, value)
    }

    private fun applyTest(doc: MutableMap<String, Any?>, path: String, value: Any?): Boolean {
        val actual = getValue(doc, path)
        return actual == value
    }

    private fun resolvePath(doc: MutableMap<String, Any?>, path: String): Pair<Any, String>? {
        val parts = path.trimStart('/').split("/")
        if (parts.isEmpty()) return null

        var current: Any = doc
        for (i in 0 until parts.size - 1) {
            current = when (current) {
                is Map<*, *> -> current[parts[i]] ?: return null
                is List<*> -> current.getOrNull(parts[i].toIntOrNull() ?: return null) ?: return null
                else -> return null
            }
        }
        return current to parts.last()
    }

    private fun getValue(doc: Map<String, Any?>, path: String): Any? {
        val parts = path.trimStart('/').split("/")
        var current: Any? = doc
        for (part in parts) {
            current = when (current) {
                is Map<*, *> -> current[part]
                is List<*> -> current.getOrNull(part.toIntOrNull() ?: return null)
                else -> return null
            }
        }
        return current
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// JSON SCHEMA - Validation (JSON Schema Draft 2020-12 subset)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * JSON Schema validation result.
 */
data class ValidationResult(
    val valid: Boolean,
    val errors: List<ValidationError> = emptyList()
) {
    data class ValidationError(
        val path: String,
        val message: String,
        val keyword: String
    )
}

/**
 * JSON Schema validator (subset of Draft 2020-12).
 */
object JsonSchema {
    /**
     * Validate data against schema.
     */
    fun validate(data: Any?, schema: Map<String, Any?>, path: String = "$"): ValidationResult {
        val errors = mutableListOf<ValidationResult.ValidationError>()
        validateNode(data, schema, path, errors)
        return ValidationResult(errors.isEmpty(), errors)
    }

    private fun validateNode(
        data: Any?,
        schema: Map<String, Any?>,
        path: String,
        errors: MutableList<ValidationResult.ValidationError>
    ) {
        // Type validation
        schema["type"]?.let { type ->
            if (!validateType(data, type.toString())) {
                errors.add(ValidationResult.ValidationError(path, "Expected type $type", "type"))
            }
        }

        // Enum validation
        @Suppress("UNCHECKED_CAST")
        (schema["enum"] as? List<Any?>)?.let { enum ->
            if (data !in enum) {
                errors.add(ValidationResult.ValidationError(path, "Value not in enum $enum", "enum"))
            }
        }

        // Const validation
        schema["const"]?.let { const ->
            if (data != const) {
                errors.add(ValidationResult.ValidationError(path, "Value must be $const", "const"))
            }
        }

        // String validations
        if (data is String) {
            val minLen: Int? = toNumber(schema["minLength"])?.toInt()
            val maxLen: Int? = toNumber(schema["maxLength"])?.toInt()

            if (minLen != null && data.length < minLen) {
                errors.add(ValidationResult.ValidationError(path, "String too short (min $minLen)", "minLength"))
            }
            if (maxLen != null && data.length > maxLen) {
                errors.add(ValidationResult.ValidationError(path, "String too long (max $maxLen)", "maxLength"))
            }
            (schema["pattern"] as? String)?.let { pattern ->
                if (!Regex(pattern).containsMatchIn(data)) {
                    errors.add(ValidationResult.ValidationError(path, "Does not match pattern", "pattern"))
                }
            }
        }

        // Number validations
        val dataNum = toNumber(data)
        if (dataNum != null) {
            val minVal: Double? = toNumber(schema["minimum"])
            val maxVal: Double? = toNumber(schema["maximum"])
            val multVal: Double? = toNumber(schema["multipleOf"])

            if (minVal != null && dataNum < minVal) {
                errors.add(ValidationResult.ValidationError(path, "Value below minimum $minVal", "minimum"))
            }
            if (maxVal != null && dataNum > maxVal) {
                errors.add(ValidationResult.ValidationError(path, "Value above maximum $maxVal", "maximum"))
            }
            if (multVal != null && multVal != 0.0 && dataNum % multVal != 0.0) {
                errors.add(ValidationResult.ValidationError(path, "Not multiple of $multVal", "multipleOf"))
            }
        }

        // Array validations
        if (data is List<*>) {
            val minItems: Int? = toNumber(schema["minItems"])?.toInt()
            val maxItems: Int? = toNumber(schema["maxItems"])?.toInt()

            if (minItems != null && data.size < minItems) {
                errors.add(ValidationResult.ValidationError(path, "Too few items (min $minItems)", "minItems"))
            }
            if (maxItems != null && data.size > maxItems) {
                errors.add(ValidationResult.ValidationError(path, "Too many items (max $maxItems)", "maxItems"))
            }
            // uniqueItems validation (array is set / enum is set)
            if (schema["uniqueItems"] == true) {
                if (data.size != data.toSet().size) {
                    errors.add(ValidationResult.ValidationError(path, "Array items must be unique", "uniqueItems"))
                }
            }
            @Suppress("UNCHECKED_CAST")
            (schema["items"] as? Map<String, Any?>)?.let { itemSchema ->
                data.forEachIndexed { i, item ->
                    validateNode(item, itemSchema, "$path[$i]", errors)
                }
            }
        }

        // Object validations
        if (data is Map<*, *>) {
            @Suppress("UNCHECKED_CAST")
            val props = schema["properties"] as? Map<String, Map<String, Any?>>
            @Suppress("UNCHECKED_CAST")
            val required = schema["required"] as? List<String> ?: emptyList()

            // Check required properties
            for (req in required) {
                if (req !in data) {
                    errors.add(ValidationResult.ValidationError("$path.$req", "Required property missing", "required"))
                }
            }

            // Validate properties
            props?.forEach { (propName, propSchema) ->
                if (propName in data) {
                    validateNode(data[propName], propSchema, "$path.$propName", errors)
                }
            }
        }
    }

    private fun validateType(data: Any?, type: String): Boolean = when (type) {
        "string" -> data is String
        "number" -> data is Number
        "integer" -> data is Int || data is Long
        "boolean" -> data is Boolean
        "array" -> data is List<*>
        "object" -> data is Map<*, *>
        "null" -> data == null
        else -> true
    }

    /** Convert Any? to Double for numeric comparisons */
    private fun toNumber(value: Any?): Double? {
        return when (value) {
            is Int -> value.toDouble()
            is Long -> value.toDouble()
            is Float -> value.toDouble()
            is Double -> value
            is String -> value.toDoubleOrNull()
            else -> null
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// VERSION / CRDT - Document versioning and Yjs-compatible operations
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Document version with vector clock.
 */
data class DocVersion(
    val clientId: String,
    val clock: Long,
    val timestamp: Long = currentTimeMillis()
) {
    /** Version string: clientId:clock */
    val formatted: String get() = "$clientId:$clock"

    companion object {
        fun parse(s: String): DocVersion? {
            val parts = s.split(":")
            if (parts.size != 2) return null
            return DocVersion(parts[0], parts[1].toLongOrNull() ?: return null)
        }

        // Simple monotonic counter for common (real impl would use expect/actual)
        private var timeCounter = 0L
        private fun currentTimeMillis(): Long = ++timeCounter
    }
}

/**
 * Vector clock for distributed versioning.
 */
data class VectorClock(
    val clocks: MutableMap<String, Long> = mutableMapOf()
) {
    /** Increment clock for client */
    fun tick(clientId: String): Long {
        val next = (clocks[clientId] ?: 0) + 1
        clocks[clientId] = next
        return next
    }

    /** Merge with another vector clock (take max) */
    fun merge(other: VectorClock) {
        for ((id, clock) in other.clocks) {
            clocks[id] = maxOf(clocks[id] ?: 0, clock)
        }
    }

    /** Check if this clock is concurrent with another */
    fun isConcurrent(other: VectorClock): Boolean {
        val thisGt = clocks.any { (id, c) -> c > (other.clocks[id] ?: 0) }
        val otherGt = other.clocks.any { (id, c) -> c > (clocks[id] ?: 0) }
        return thisGt && otherGt
    }

    /** Check if this clock happens before another */
    fun happensBefore(other: VectorClock): Boolean {
        return clocks.all { (id, c) -> c <= (other.clocks[id] ?: 0) } &&
               clocks.any { (id, c) -> c < (other.clocks[id] ?: 0) }
    }
}

/**
 * Yjs-compatible CRDT operation types.
 */
sealed class YOp {
    abstract val id: DocVersion
    abstract val origin: DocVersion?

    /** Insert text/content */
    data class Insert(
        override val id: DocVersion,
        override val origin: DocVersion?,
        val content: String,
        val attrs: Map<String, Any?> = emptyMap()
    ) : YOp()

    /** Delete content */
    data class Delete(
        override val id: DocVersion,
        override val origin: DocVersion?,
        val length: Int
    ) : YOp()

    /** Format/attribute change */
    data class Format(
        override val id: DocVersion,
        override val origin: DocVersion?,
        val length: Int,
        val attrs: Map<String, Any?>
    ) : YOp()
}

/**
 * Yjs-compatible YText for collaborative text editing.
 */
class YText(private val clientId: String) {
    private val ops = mutableListOf<YOp>()
    private val clock = VectorClock()
    private var content = StringBuilder()

    /** Current text content */
    val text: String get() = content.toString()

    /** All operations */
    val operations: List<YOp> get() = ops.toList()

    /** Insert text at position */
    fun insert(index: Int, str: String, attrs: Map<String, Any?> = emptyMap()): YOp.Insert {
        val version = DocVersion(clientId, clock.tick(clientId))
        val origin = if (index > 0 && ops.isNotEmpty()) ops.last().id else null

        val op = YOp.Insert(version, origin, str, attrs)
        applyLocal(op)
        return op
    }

    /** Delete text at position */
    fun delete(index: Int, length: Int): YOp.Delete {
        val version = DocVersion(clientId, clock.tick(clientId))
        val origin = if (ops.isNotEmpty()) ops.last().id else null

        val op = YOp.Delete(version, origin, length)
        applyLocal(op)
        return op
    }

    /** Apply remote operation */
    fun applyRemote(op: YOp) {
        clock.merge(VectorClock(mutableMapOf(op.id.clientId to op.id.clock)))
        // Simplified: just append (real impl needs CRDT merge)
        when (op) {
            is YOp.Insert -> content.append(op.content)
            is YOp.Delete -> {
                val end = minOf(content.length, op.length)
                if (end > 0) content.deleteRange(0, end)
            }
            is YOp.Format -> { /* Apply formatting */ }
        }
        ops.add(op)
    }

    private fun applyLocal(op: YOp) {
        when (op) {
            is YOp.Insert -> {
                val idx = minOf(content.length, findInsertIndex(op.origin))
                content.insert(idx, op.content)
            }
            is YOp.Delete -> {
                val idx = findInsertIndex(op.origin)
                val end = minOf(content.length, idx + op.length)
                if (idx < end) content.deleteRange(idx, end)
            }
            is YOp.Format -> { /* Apply formatting */ }
        }
        ops.add(op)
    }

    private fun findInsertIndex(origin: DocVersion?): Int {
        if (origin == null) return 0
        var idx = 0
        for (op in ops) {
            if (op.id == origin) break
            if (op is YOp.Insert) idx += op.content.length
        }
        return idx
    }

    /** Export state for sync */
    fun exportState(): Map<String, Any> = mapOf(
        "clientId" to clientId,
        "clock" to clock.clocks.toMap(),
        "ops" to ops.map { op ->
            when (op) {
                is YOp.Insert -> mapOf(
                    "type" to "insert",
                    "id" to op.id.formatted,
                    "origin" to op.origin?.formatted,
                    "content" to op.content,
                    "attrs" to op.attrs
                )
                is YOp.Delete -> mapOf(
                    "type" to "delete",
                    "id" to op.id.formatted,
                    "origin" to op.origin?.formatted,
                    "length" to op.length
                )
                is YOp.Format -> mapOf(
                    "type" to "format",
                    "id" to op.id.formatted,
                    "origin" to op.origin?.formatted,
                    "length" to op.length,
                    "attrs" to op.attrs
                )
            }
        }
    )
}

/**
 * YDoc - Yjs-compatible document container.
 */
class YDoc(val clientId: String = generateClientId()) {
    private val texts = mutableMapOf<String, YText>()
    private val arrays = mutableMapOf<String, MutableList<Any?>>()
    private val maps = mutableMapOf<String, MutableMap<String, Any?>>()

    /** Get or create YText */
    fun getText(name: String = "default"): YText {
        return texts.getOrPut(name) { YText(clientId) }
    }

    /** Get or create array */
    fun getArray(name: String): MutableList<Any?> {
        return arrays.getOrPut(name) { mutableListOf() }
    }

    /** Get or create map */
    fun getMap(name: String): MutableMap<String, Any?> {
        return maps.getOrPut(name) { mutableMapOf() }
    }

    /** Export full document state */
    fun exportState(): Map<String, Any> = mapOf(
        "clientId" to clientId,
        "texts" to texts.mapValues { it.value.exportState() },
        "arrays" to arrays.toMap(),
        "maps" to maps.mapValues { it.value.toMap() }
    )

    companion object {
        private var counter = 0
        private fun generateClientId(): String = "client_${++counter}_${kotlin.random.Random.nextInt(1000000)}"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// DATETIME / CLDR CALENDAR - Locale-aware date/time with calendar systems
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Calendar type enum (CLDR calendar identifiers).
 */
enum class CalendarType(val cldrId: String) {
    GREGORIAN("gregory"),
    HEBREW("hebrew"),
    ISLAMIC("islamic"),
    ISLAMIC_CIVIL("islamic-civil"),
    PERSIAN("persian"),
    CHINESE("chinese"),
    JAPANESE("japanese"),
    BUDDHIST("buddhist"),
    COPTIC("coptic"),
    ETHIOPIC("ethiopic"),
    INDIAN("indian");

    companion object {
        fun fromCldr(id: String): CalendarType? = entries.find { it.cldrId == id.lowercase() }
        fun forLang(lang: String): CalendarType = when (lang.lowercase()) {
            "he", "heb" -> HEBREW
            "ar", "ara" -> ISLAMIC
            "fa", "fas" -> PERSIAN
            "ja", "jpn" -> JAPANESE
            "zh", "zho" -> CHINESE
            "th", "tha" -> BUDDHIST
            else -> GREGORIAN
        }
    }
}

/**
 * Hebrew month names (leap year aware).
 */
enum class HebrewMonth(val hebrewName: String, val transliteration: String, val index: Int) {
    TISHREI("תשרי", "Tishrei", 1),
    CHESHVAN("חשון", "Cheshvan", 2),
    KISLEV("כסלו", "Kislev", 3),
    TEVET("טבת", "Tevet", 4),
    SHEVAT("שבט", "Shevat", 5),
    ADAR("אדר", "Adar", 6),
    ADAR_I("אדר א׳", "Adar I", 6),    // Leap year
    ADAR_II("אדר ב׳", "Adar II", 7),  // Leap year
    NISAN("ניסן", "Nisan", 7),
    IYAR("אייר", "Iyar", 8),
    SIVAN("סיון", "Sivan", 9),
    TAMMUZ("תמוז", "Tammuz", 10),
    AV("אב", "Av", 11),
    ELUL("אלול", "Elul", 12);

    companion object {
        /** Get month for index (1-based), leap year aware */
        fun forIndex(index: Int, isLeapYear: Boolean): HebrewMonth? {
            return if (isLeapYear) {
                when (index) {
                    1 -> TISHREI
                    2 -> CHESHVAN
                    3 -> KISLEV
                    4 -> TEVET
                    5 -> SHEVAT
                    6 -> ADAR_I
                    7 -> ADAR_II
                    8 -> NISAN
                    9 -> IYAR
                    10 -> SIVAN
                    11 -> TAMMUZ
                    12 -> AV
                    13 -> ELUL
                    else -> null
                }
            } else {
                entries.find { it.index == index && it != ADAR_I && it != ADAR_II }
                    ?: if (index == 6) ADAR else null
            }
        }
    }
}

/**
 * Islamic month names.
 */
enum class IslamicMonth(val arabicName: String, val transliteration: String, val index: Int) {
    MUHARRAM("محرم", "Muharram", 1),
    SAFAR("صفر", "Safar", 2),
    RABI_AL_AWWAL("ربيع الأول", "Rabi' al-Awwal", 3),
    RABI_AL_THANI("ربيع الآخر", "Rabi' al-Thani", 4),
    JUMADA_AL_ULA("جمادى الأولى", "Jumada al-Ula", 5),
    JUMADA_AL_THANI("جمادى الآخرة", "Jumada al-Thani", 6),
    RAJAB("رجب", "Rajab", 7),
    SHABAN("شعبان", "Sha'ban", 8),
    RAMADAN("رمضان", "Ramadan", 9),
    SHAWWAL("شوال", "Shawwal", 10),
    DHU_AL_QADAH("ذو القعدة", "Dhu al-Qa'dah", 11),
    DHU_AL_HIJJAH("ذو الحجة", "Dhu al-Hijjah", 12);

    companion object {
        fun forIndex(index: Int): IslamicMonth? = entries.find { it.index == index }
    }
}

/**
 * Day of week with locale-aware names.
 */
enum class DayOfWeek(val index: Int) {
    SUNDAY(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    /** Get localized name */
    fun getName(lang: String): String = when (lang.lowercase()) {
        "he" -> when (this) {
            SUNDAY -> "יום ראשון"
            MONDAY -> "יום שני"
            TUESDAY -> "יום שלישי"
            WEDNESDAY -> "יום רביעי"
            THURSDAY -> "יום חמישי"
            FRIDAY -> "יום שישי"
            SATURDAY -> "שבת"
        }
        "ar" -> when (this) {
            SUNDAY -> "الأحد"
            MONDAY -> "الإثنين"
            TUESDAY -> "الثلاثاء"
            WEDNESDAY -> "الأربعاء"
            THURSDAY -> "الخميس"
            FRIDAY -> "الجمعة"
            SATURDAY -> "السبت"
        }
        else -> name.lowercase().replaceFirstChar { it.uppercase() }
    }

    /** Get short name (1-2 chars) */
    fun getShortName(lang: String): String = when (lang.lowercase()) {
        "he" -> when (this) {
            SUNDAY -> "א׳"
            MONDAY -> "ב׳"
            TUESDAY -> "ג׳"
            WEDNESDAY -> "ד׳"
            THURSDAY -> "ה׳"
            FRIDAY -> "ו׳"
            SATURDAY -> "ש׳"
        }
        else -> name.take(2)
    }

    companion object {
        fun fromIndex(index: Int): DayOfWeek = entries.find { it.index == index % 7 } ?: SUNDAY
    }
}

/**
 * Locale-aware date representation.
 */
data class LocaleDate(
    val year: Int,
    val month: Int,      // 1-based
    val day: Int,
    val calendar: CalendarType = CalendarType.GREGORIAN,
    val lang: String = "en"
) {
    /** Get month name in locale */
    val monthName: String get() = when (calendar) {
        CalendarType.HEBREW -> HebrewMonth.forIndex(month, isHebrewLeapYear(year))?.let {
            if (lang == "he") it.hebrewName else it.transliteration
        } ?: month.toString()
        CalendarType.ISLAMIC, CalendarType.ISLAMIC_CIVIL -> IslamicMonth.forIndex(month)?.let {
            if (lang == "ar") it.arabicName else it.transliteration
        } ?: month.toString()
        else -> getGregorianMonthName(month, lang)
    }

    /** Format date according to locale */
    fun format(pattern: String = "default"): String = when (pattern) {
        "default" -> when (lang) {
            "he" -> "$day ב$monthName $year"
            "ar" -> "$day $monthName $year"
            else -> "$monthName $day, $year"
        }
        "short" -> "$day/$month/$year"
        "iso" -> "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
        else -> "$day $monthName $year"
    }

    /** Convert between calendar systems (simplified) */
    fun toCalendar(target: CalendarType): LocaleDate {
        if (target == calendar) return this
        // Simplified conversion via Julian Day Number
        val jdn = toJulianDay()
        return fromJulianDay(jdn, target, lang)
    }

    /** Convert to Julian Day Number */
    fun toJulianDay(): Long {
        return when (calendar) {
            CalendarType.GREGORIAN -> gregorianToJdn(year, month, day)
            CalendarType.HEBREW -> hebrewToJdn(year, month, day)
            CalendarType.ISLAMIC, CalendarType.ISLAMIC_CIVIL -> islamicToJdn(year, month, day)
            else -> gregorianToJdn(year, month, day) // Fallback
        }
    }

    companion object {
        /** Create from Julian Day Number */
        fun fromJulianDay(jdn: Long, calendar: CalendarType, lang: String = "en"): LocaleDate {
            val (y, m, d) = when (calendar) {
                CalendarType.GREGORIAN -> jdnToGregorian(jdn)
                CalendarType.HEBREW -> jdnToHebrew(jdn)
                CalendarType.ISLAMIC, CalendarType.ISLAMIC_CIVIL -> jdnToIslamic(jdn)
                else -> jdnToGregorian(jdn)
            }
            return LocaleDate(y, m, d, calendar, lang)
        }

        /** Today's date in specified calendar */
        fun today(calendar: CalendarType = CalendarType.GREGORIAN, lang: String = "en"): LocaleDate {
            // Simplified: would use kotlinx-datetime in real impl
            val epoch = 2440588L  // Jan 1, 1970 JDN
            val days = (System.currentTimeMillis() / 86400000L)
            return fromJulianDay(epoch + days, calendar, lang)
        }

        // Gregorian <-> JDN conversions
        private fun gregorianToJdn(y: Int, m: Int, d: Int): Long {
            val a = (14 - m) / 12
            val yy = y + 4800 - a
            val mm = m + 12 * a - 3
            return d + (153 * mm + 2) / 5 + 365L * yy + yy / 4 - yy / 100 + yy / 400 - 32045
        }

        private fun jdnToGregorian(jdn: Long): Triple<Int, Int, Int> {
            val a = jdn + 32044
            val b = (4 * a + 3) / 146097
            val c = a - 146097 * b / 4
            val d = (4 * c + 3) / 1461
            val e = c - 1461 * d / 4
            val m = (5 * e + 2) / 153
            val day = (e - (153 * m + 2) / 5 + 1).toInt()
            val month = (m + 3 - 12 * (m / 10)).toInt()
            val year = (100 * b + d - 4800 + m / 10).toInt()
            return Triple(year, month, day)
        }

        // Hebrew calendar (simplified)
        private fun hebrewToJdn(y: Int, m: Int, d: Int): Long {
            // Simplified algorithm
            val months = if (isHebrewLeapYear(y)) 13 else 12
            val days = hebrewYearDays(y)
            var jdn = hebrewEpoch(y)
            for (i in 1 until m) {
                jdn += hebrewMonthDays(y, i)
            }
            return jdn + d - 1
        }

        private fun jdnToHebrew(jdn: Long): Triple<Int, Int, Int> {
            // Simplified reverse calculation
            var year = ((jdn - 347997) * 98496 / 35975351).toInt()
            while (hebrewEpoch(year + 1) <= jdn) year++
            var month = 1
            var remaining = (jdn - hebrewEpoch(year)).toInt()
            while (remaining >= hebrewMonthDays(year, month)) {
                remaining -= hebrewMonthDays(year, month)
                month++
            }
            return Triple(year, month, remaining + 1)
        }

        private fun hebrewEpoch(year: Int): Long {
            // Simplified: days from creation to Tishrei 1 of year
            val months = (235 * year - 234) / 19
            val parts = 12084L + 13753 * months
            val day = 29 * months + parts / 25920
            return day + 347997 // Hebrew epoch in JDN
        }

        private fun hebrewYearDays(year: Int): Int {
            return (hebrewEpoch(year + 1) - hebrewEpoch(year)).toInt()
        }

        private fun hebrewMonthDays(year: Int, month: Int): Int {
            return when (month) {
                2 -> if (hebrewYearDays(year) % 10 == 5) 30 else 29  // Cheshvan
                3 -> if (hebrewYearDays(year) % 10 == 3) 29 else 30  // Kislev
                6 -> if (isHebrewLeapYear(year)) 30 else 29          // Adar
                else -> if (month % 2 == 1) 30 else 29
            }
        }

        private fun isHebrewLeapYear(year: Int): Boolean {
            return (7 * year + 1) % 19 < 7
        }

        // Islamic calendar (simplified)
        private fun islamicToJdn(y: Int, m: Int, d: Int): Long {
            return d + 29 * (m - 1) + m / 2 + 354L * (y - 1) + (3 + 11 * y) / 30 + 1948439
        }

        private fun jdnToIslamic(jdn: Long): Triple<Int, Int, Int> {
            val l = jdn - 1948440 + 10632
            val n = (l - 1) / 10631
            val ll = l - 10631 * n + 354
            val j = (10985 - ll) / 5316 * ((50 * ll) / 17719) + (ll / 5670) * ((43 * ll) / 15238)
            val lll = ll - ((30 - j) / 15) * ((17719 * j) / 50) - (j / 16) * ((15238 * j) / 43) + 29
            val month = ((24 * lll) / 709).toInt()
            val day = (lll - ((709 * month) / 24)).toInt()
            val year = (30 * n + j - 30).toInt()
            return Triple(year, month, day)
        }

        private fun getGregorianMonthName(month: Int, lang: String): String = when (lang) {
            "he" -> listOf("ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני",
                          "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר")
                .getOrElse(month - 1) { "" }
            "ar" -> listOf("يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو",
                          "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر")
                .getOrElse(month - 1) { "" }
            else -> listOf("January", "February", "March", "April", "May", "June",
                          "July", "August", "September", "October", "November", "December")
                .getOrElse(month - 1) { "" }
        }

        // Stub for KMP
        private object System {
            fun currentTimeMillis(): Long = kotlin.random.Random.nextLong(0, 100000000000L)
        }
    }
}

/**
 * Sheet in a workbook.
 */
data class Sheet(
    val name: String,
    val table: TableData
) {
    /** Get cell by reference */
    operator fun get(ref: String): CellValue = table[ref]

    /** Evaluate formulas */
    fun evaluate(): Sheet = copy(table = FormulaEvaluator(table).evaluateAll())
}

/**
 * Workbook containing multiple sheets (like Apache POI Workbook).
 */
data class Workbook(
    val sheets: List<Sheet> = emptyList()
) {
    /** Get sheet by index */
    operator fun get(index: Int): Sheet? = sheets.getOrNull(index)

    /** Get sheet by name */
    operator fun get(name: String): Sheet? = sheets.find { it.name.equals(name, ignoreCase = true) }

    /** Number of sheets */
    val sheetCount: Int get() = sheets.size

    /** Sheet names */
    val sheetNames: List<String> get() = sheets.map { it.name }

    /** Add a sheet */
    fun addSheet(sheet: Sheet): Workbook = copy(sheets = sheets + sheet)

    /** Add table as new sheet */
    fun addTable(table: TableData): Workbook = addSheet(Sheet(table.name, table))

    /** Evaluate all formulas in all sheets */
    fun evaluateAll(): Workbook = copy(sheets = sheets.map { it.evaluate() })

    companion object {
        /** Create workbook from HTML containing multiple tables */
        fun fromHtml(html: String): Workbook {
            val tablePattern = Regex("""<table[^>]*>[\s\S]*?</table>""", RegexOption.IGNORE_CASE)
            val tables = tablePattern.findAll(html).mapNotNull { TableData.parseHtml(it.value) }
            return Workbook(tables.mapIndexed { idx, table ->
                Sheet(table.name.ifEmpty { "Sheet${idx + 1}" }, table)
            }.toList())
        }

        /** Create workbook from multiple TSV strings */
        fun fromTsvList(tsvList: List<Pair<String, String>>): Workbook {
            return Workbook(tsvList.map { (name, tsv) ->
                Sheet(name, TableData.parseTsv(tsv))
            })
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TEXT BLOCK - Language-tagged markdown blocks with script detection
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Text block with language tag for MD fenced blocks.
 * Supports: ```he, ```en, ```ar, etc.
 * Detects kerning and bidirectional text requirements.
 */
data class TextBlock(
    val content: String,
    val lang: String,
    val scriptAttrs: ScriptAttrs,
    val startLine: Int,
    val endLine: Int
) {
    /** Is this a right-to-left block */
    val isRtl: Boolean get() = scriptAttrs.dir == TextDir.RTL

    /** Get kerning hint for the block */
    val kerning: KerningHint get() = KerningHint.forLang(lang)

    companion object {
        /** Fenced code block pattern with optional language */
        private val FENCE_START by lazy { Regex("""^```(\w*)(.*)$""") }
        private val FENCE_END by lazy { Regex("""^```\s*$""") }

        /** Parse fenced blocks from markdown */
        fun parseMarkdown(md: String): List<TextBlock> {
            val lines = LineSeparator.splitLines(md)
            val blocks = mutableListOf<TextBlock>()

            var inBlock = false
            var blockLang = ""
            var blockContent = StringBuilder()
            var startLine = 0

            lines.forEachIndexed { idx, line ->
                if (!inBlock) {
                    val match = FENCE_START.matchEntire(line.trim())
                    if (match != null) {
                        inBlock = true
                        blockLang = match.groupValues[1].lowercase().ifEmpty { "text" }
                        blockContent = StringBuilder()
                        startLine = idx
                    }
                } else {
                    if (FENCE_END.matches(line.trim())) {
                        val content = blockContent.toString()
                        val script = ScriptAttrs.forLang(blockLang)
                            ?: ScriptDetector.detect(content)
                        blocks.add(TextBlock(content, blockLang, script, startLine, idx))
                        inBlock = false
                    } else {
                        if (blockContent.isNotEmpty()) blockContent.append("\n")
                        blockContent.append(line)
                    }
                }
            }

            return blocks
        }
    }
}

/**
 * Kerning hint based on script/language.
 */
enum class KerningHint {
    NORMAL,      // Standard kerning
    TIGHT,       // Tight kerning (compact scripts)
    LOOSE,       // Loose kerning (decorative)
    NONE;        // No kerning (monospace/code)

    companion object {
        fun forLang(lang: String): KerningHint = when (lang.lowercase()) {
            "he", "ar", "fa", "ur" -> TIGHT  // RTL scripts often need tighter kerning
            "ja", "zh", "ko" -> NONE         // CJK scripts typically don't kern
            "code", "pre", "mono" -> NONE    // Code blocks
            else -> NORMAL
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SUBTITLE FORMATS - SRT and related subtitle parsing
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Subtitle cue (single subtitle entry).
 */
data class SubtitleCue(
    val index: Int,
    val startTime: Long,      // Milliseconds
    val endTime: Long,        // Milliseconds
    val text: String,
    val scriptAttrs: ScriptAttrs = ScriptAttrs.EMPTY
) {
    /** Duration in milliseconds */
    val duration: Long get() = endTime - startTime

    /** Format start time as SRT timestamp (HH:MM:SS,mmm) */
    val startTimestamp: String get() = formatSrtTime(startTime)

    /** Format end time as SRT timestamp */
    val endTimestamp: String get() = formatSrtTime(endTime)

    /** Format as SRT block */
    fun toSrt(): String = buildString {
        appendLine(index)
        appendLine("$startTimestamp --> $endTimestamp")
        appendLine(text)
    }

    companion object {
        private fun formatSrtTime(ms: Long): String {
            val hours = ms / 3600000
            val minutes = (ms % 3600000) / 60000
            val seconds = (ms % 60000) / 1000
            val millis = ms % 1000
            return "${pad2(hours)}:${pad2(minutes)}:${pad2(seconds)},${pad3(millis)}"
        }

        private fun pad2(n: Long): String = n.toString().padStart(2, '0')
        private fun pad3(n: Long): String = n.toString().padStart(3, '0')

        /** Parse SRT timestamp to milliseconds */
        fun parseSrtTime(timestamp: String): Long {
            val pattern = Regex("""(\d{2}):(\d{2}):(\d{2})[,.](\d{3})""")
            val match = pattern.matchEntire(timestamp.trim()) ?: return 0
            val (h, m, s, ms) = match.destructured
            return h.toLong() * 3600000 + m.toLong() * 60000 + s.toLong() * 1000 + ms.toLong()
        }
    }
}

/**
 * Subtitle file parser - supports SRT format.
 */
object SubtitleParser {
    private val SRT_INDEX by lazy { Regex("""^\d+$""") }
    private val SRT_TIMESTAMP by lazy { Regex("""^(\d{2}:\d{2}:\d{2}[,.]\d{3})\s*-->\s*(\d{2}:\d{2}:\d{2}[,.]\d{3})(.*)$""") }

    /**
     * Parse SRT subtitle file content.
     */
    fun parseSrt(content: String): List<SubtitleCue> {
        val lines = LineSeparator.splitLines(content)
        val cues = mutableListOf<SubtitleCue>()

        var i = 0
        while (i < lines.size) {
            // Skip empty lines
            while (i < lines.size && lines[i].isBlank()) i++
            if (i >= lines.size) break

            // Parse index
            val indexLine = lines[i].trim()
            if (!SRT_INDEX.matches(indexLine)) {
                i++
                continue
            }
            val index = indexLine.toInt()
            i++

            // Parse timestamp
            if (i >= lines.size) break
            val timestampMatch = SRT_TIMESTAMP.matchEntire(lines[i].trim())
            if (timestampMatch == null) {
                i++
                continue
            }
            val startTime = SubtitleCue.parseSrtTime(timestampMatch.groupValues[1])
            val endTime = SubtitleCue.parseSrtTime(timestampMatch.groupValues[2])
            i++

            // Parse text (may be multiple lines)
            val textLines = mutableListOf<String>()
            while (i < lines.size && lines[i].isNotBlank()) {
                textLines.add(lines[i])
                i++
            }
            val text = textLines.joinToString("\n")
            val script = ScriptDetector.detect(text)

            cues.add(SubtitleCue(index, startTime, endTime, text, script))
        }

        return cues
    }

    /**
     * Convert cues to SRT format.
     */
    fun toSrt(cues: List<SubtitleCue>): String {
        return cues.joinToString("\n") { it.toSrt() }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// FILE EXTENSION DETECTION
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * File type detection by extension.
 */
enum class FileType(
    val extensions: Set<String>,
    val mimeType: String,
    val isText: Boolean = true,
    val isCode: Boolean = false
) {
    // ═══ Document Formats ═══
    MARKDOWN(setOf("md", "markdown", "mdown"), "text/markdown"),
    HTML(setOf("html", "htm", "xhtml"), "text/html"),
    TEXT(setOf("txt", "text"), "text/plain"),

    // ═══ Subtitle Formats ═══
    SRT(setOf("srt"), "application/x-subrip"),
    VTT(setOf("vtt"), "text/vtt"),
    ASS(setOf("ass", "ssa"), "text/x-ssa"),
    SUB(setOf("sub"), "text/x-sub"),

    // ═══ Data Formats ═══
    JSON(setOf("json"), "application/json"),
    YAML(setOf("yaml", "yml"), "text/yaml"),
    XML(setOf("xml"), "application/xml"),
    TSV(setOf("tsv"), "text/tab-separated-values"),
    CSV(setOf("csv"), "text/csv"),

    // ═══ Code Formats ═══
    KOTLIN(setOf("kt", "kts"), "text/x-kotlin", isCode = true),
    JAVASCRIPT(setOf("js", "mjs", "cjs"), "text/javascript", isCode = true),
    TYPESCRIPT(setOf("ts", "tsx"), "text/typescript", isCode = true),
    JAVA(setOf("java"), "text/x-java", isCode = true),
    PYTHON(setOf("py", "pyw"), "text/x-python", isCode = true),
    RUST(setOf("rs"), "text/x-rust", isCode = true),
    GO(setOf("go"), "text/x-go", isCode = true),
    SWIFT(setOf("swift"), "text/x-swift", isCode = true),
    C(setOf("c", "h"), "text/x-c", isCode = true),
    CPP(setOf("cpp", "cc", "cxx", "hpp", "hh"), "text/x-c++", isCode = true),
    CSHARP(setOf("cs"), "text/x-csharp", isCode = true),

    // ═══ Script/Narrative Formats ═══
    INK(setOf("ink"), "text/x-ink"),           // Inkle's Ink scripting
    FOUNTAIN(setOf("fountain"), "text/x-fountain"),  // Screenplay format

    // ═══ MKV/Media Formats ═══
    MKV(setOf("mkv"), "video/x-matroska", isText = false),
    MKA(setOf("mka"), "audio/x-matroska", isText = false),
    MKS(setOf("mks"), "video/x-matroska", isText = false),  // Subtitles only MKV
    MXL(setOf("mxl", "musicxml"), "application/vnd.recordare.musicxml", isText = false),  // MusicXML

    // ═══ Keyboard/Layout Files ═══
    KL(setOf("kl"), "application/x-keylayout"),
    MXKL(setOf("mxkl", "mxklfile"), "application/x-mxkeylayout"),
    XKB(setOf("xkb"), "text/x-xkb"),

    UNKNOWN(emptySet(), "application/octet-stream", false);

    companion object {
        /** Detect file type from filename or extension */
        fun fromFilename(filename: String): FileType {
            val ext = filename.substringAfterLast('.', "").lowercase()
            return entries.find { ext in it.extensions } ?: UNKNOWN
        }

        /** Detect file type from path */
        fun fromPath(path: String): FileType {
            val filename = path.substringAfterLast('/')
            return fromFilename(filename)
        }

        /** Get all subtitle formats */
        val SUBTITLE_TYPES = listOf(SRT, VTT, ASS, SUB)

        /** Get all text-based formats */
        val TEXT_TYPES = entries.filter { it.isText }

        /** Get all code formats */
        val CODE_TYPES = entries.filter { it.isCode }

        /** Get all data formats */
        val DATA_TYPES = listOf(JSON, YAML, XML, TSV, CSV)

        /** Get language code for code file type */
        fun langCode(type: FileType): String? = when (type) {
            KOTLIN -> "kt"
            JAVASCRIPT -> "js"
            TYPESCRIPT -> "ts"
            JAVA -> "java"
            PYTHON -> "py"
            RUST -> "rs"
            GO -> "go"
            SWIFT -> "swift"
            C, CPP -> "c"
            CSHARP -> "cs"
            INK -> "ink"
            else -> null
        }
    }
}

/**
 * Document parser that auto-detects format from file extension.
 */
object AutoParser {
    /**
     * Parse content based on file type.
     * Returns list of parsed elements or null if unsupported.
     */
    fun parse(content: String, fileType: FileType): List<ParsedElement>? {
        return when (fileType) {
            FileType.MARKDOWN -> DocumentParser().parseMarkdown(content)
            FileType.HTML -> DocumentParser().parseHtml(content)
            FileType.TEXT -> PlainTextParser().parse(content)
            FileType.SRT -> parseSrtAsElements(content)
            FileType.TSV, FileType.CSV -> parseTableAsElements(content, fileType)
            else -> null
        }
    }

    /**
     * Parse file by filename (auto-detect type).
     */
    fun parseByFilename(content: String, filename: String): List<ParsedElement>? {
        return parse(content, FileType.fromFilename(filename))
    }

    private fun parseSrtAsElements(content: String): List<ParsedElement> {
        val cues = SubtitleParser.parseSrt(content)
        val builder = DocumentIndexBuilder()

        builder.push(DocTag.HTML)
        builder.push(DocTag.BODY)

        return cues.map { cue ->
            val segment = builder.push(DocTag.P, scriptAttrs = cue.scriptAttrs)
            ParsedElement(
                index = segment,
                tag = DocTag.P,
                startOffset = 0,
                endOffset = cue.text.length,
                attributes = mapOf(
                    "data-index" to cue.index.toString(),
                    "data-start" to cue.startTime.toString(),
                    "data-end" to cue.endTime.toString(),
                    "lang" to (cue.scriptAttrs.lang ?: ""),
                    "dir" to (cue.scriptAttrs.dir?.value ?: "")
                ),
                content = cue.text
            ).also { builder.pop() }
        }
    }

    private fun parseTableAsElements(content: String, fileType: FileType): List<ParsedElement> {
        val table = when (fileType) {
            FileType.TSV -> TableData.parseTsv(content)
            FileType.CSV -> TableData.parseTsv(content.replace(",", "\t"))  // Simple CSV handling
            else -> return emptyList()
        }

        val builder = DocumentIndexBuilder()
        builder.push(DocTag.HTML)
        builder.push(DocTag.BODY)
        builder.push(DocTag.TABLE)

        val elements = mutableListOf<ParsedElement>()

        // Header row
        if (table.headers.isNotEmpty()) {
            val trIndex = builder.push(DocTag.TR)
            table.headers.forEach { header ->
                val thIndex = builder.push(DocTag.TH)
                elements.add(ParsedElement(thIndex, DocTag.TH, 0, header.length, emptyMap(), header))
                builder.pop()
            }
            builder.pop()
        }

        // Data rows
        table.rows.forEach { row ->
            val trIndex = builder.push(DocTag.TR)
            row.cells.forEachIndexed { idx, cell ->
                val tdIndex = builder.push(DocTag.TD)
                elements.add(ParsedElement(
                    tdIndex, DocTag.TD, 0, cell.raw.length,
                    mapOf("data-col" to CellRef(idx, row.rowIndex).colLetter),
                    cell.raw
                ))
                builder.pop()
            }
            builder.pop()
        }

        return elements
    }
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
        val segment: IndexRange,
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
        var sectionDivSegment: IndexRange? = null

        // List state
        var currentListSegment: IndexRange? = null
        var currentListType: LineType? = null

        // Table state
        var inTable = false
        var tableSegment: IndexRange? = null
        var currentRowSegment: IndexRange? = null

        // Split by line separators: <br>, \r\n, \r, \n -> all normalized to LF
        val lines = LineSeparator.splitLines(text)
        var offset = 0

        // Definition list state
        var dlSegment: IndexRange? = null
        var inDefList = false

        // Code fence state
        var inCodeFence = false
        var codeBlockSegment: IndexRange? = null

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
                        val dlWithPair = IndexRange(
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
    private fun pushTag(tag: DocTag): IndexRange {
        val count = tagCounts.getOrPut(tag) { 0 } + 1
        tagCounts[tag] = count
        return IndexRange(tag, count)
    }

    /**
     * Push a new tag with script attributes and return its segment.
     */
    private fun pushTagWithScript(tag: DocTag, scriptAttrs: ScriptAttrs = ScriptAttrs.EMPTY): IndexRange {
        val count = tagCounts.getOrPut(tag) { 0 } + 1
        tagCounts[tag] = count
        return IndexRange(tag, count, emptyList(), scriptAttrs)
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
    private val stack = mutableListOf<IndexRange>()

    /** Push a new tag onto the stack */
    fun push(tag: DocTag, cssClasses: List<String> = emptyList(), scriptAttrs: ScriptAttrs = ScriptAttrs.EMPTY, dataAttrs: Map<String, String> = emptyMap()): DocumentIndex {
        val count = tagCounts.getOrPut(tag) { 0 } + 1
        tagCounts[tag] = count

        val segment = IndexRange(tag, count, cssClasses, scriptAttrs, dataAttrs)
        stack.add(segment)

        return DocumentIndex(stack.toList())
    }

    /** Pop the last tag from the stack */
    fun pop(): IndexRange? {
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

// ═══════════════════════════════════════════════════════════════════════════════
// PROTOCOL ABSTRACTION - Unified wire protocol for MCP/WebDAV/Ykt
// ═══════════════════════════════════════════════════════════════════════════════

// WireFormat, Range, RangeCodec imported from al.clk.key.proto

/**
 * XML codec for XML wire format (local extension).
 */
object XmlCodec {
    inline fun <reified T> encode(value: T, rootTag: String = "root"): String {
        val jsonStr = ProtoWire.json.encodeToString(value)
        return jsonToXml(jsonStr, rootTag)
    }

    fun jsonToXml(json: String, rootTag: String = "root"): String {
        val element = ProtoWire.json.parseToJsonElement(json)
        return buildString {
            append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            appendElement(rootTag, element)
        }
    }

    private fun StringBuilder.appendElement(tag: String, element: JsonElement) {
        when (element) {
            is JsonPrimitive -> {
                append("<$tag>")
                append(escapeXml(element.content))
                append("</$tag>")
            }
            is JsonArray -> {
                element.forEach { item ->
                    appendElement(tag, item)
                    append("\n")
                }
            }
            is JsonObject -> {
                append("<$tag>")
                element.forEach { (key, value) ->
                    append("\n  ")
                    appendElement(key, value)
                }
                append("\n</$tag>")
            }
            is JsonNull -> append("<$tag/>")
        }
    }

    private fun escapeXml(s: String) = s
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
}

// Proto types imported from al.clk.key.proto:
// - SessionId, ClientId, RequestId, ResourceUri, JsonPointer (value classes)
// - ProtoRpc, ProtoResult, ProtoVersion, ProtoVectorClock (protocol types)
// - WireFormat, Range, RangeCodec, ProtoWire (codecs)

// ═══════════════════════════════════════════════════════════════════════════════
// MCP - Model Context Protocol (tools/resources for LLM agents)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * MCP Tool definition for LLM agent invocation.
 */
@Serializable
data class McpToolDef(
    val name: String,
    val description: String,
    val inputSchema: JsonObject
)

/**
 * MCP Tool with handler (non-serializable runtime component).
 */
data class McpTool(
    val def: McpToolDef,
    val handler: suspend (JsonObject) -> McpToolResult
) {
    val name get() = def.name
    val description get() = def.description
    val inputSchema get() = def.inputSchema
}

/**
 * MCP Tool execution result.
 */
@Serializable
sealed class McpToolResult {
    @Serializable @SerialName("success")
    data class Success(val content: List<McpContent>) : McpToolResult()
    @Serializable @SerialName("error")
    data class Error(val message: String, val code: Int = -1) : McpToolResult()
}

/**
 * MCP Content types.
 */
@Serializable
sealed class McpContent {
    @Serializable @SerialName("text")
    data class Text(val text: String) : McpContent()
    @Serializable @SerialName("image")
    data class Image(val data: String, val mimeType: String) : McpContent()
    @Serializable @SerialName("resource")
    data class Resource(val uri: String, val mimeType: String, val text: String? = null) : McpContent()
}

/**
 * MCP Resource definition.
 */
@Serializable
data class McpResource(
    val uri: String,
    val name: String,
    val description: String? = null,
    val mimeType: String = "application/json"
)

/**
 * MCP JSON-RPC Error.
 */
@Serializable
data class McpError(
    val code: Int,
    val message: String,
    val data: JsonElement? = null
)

/**
 * MCP Message types for protocol communication.
 */
@Serializable
sealed class McpMessage {
    abstract val jsonrpc: String

    @Serializable @SerialName("request")
    data class Request(
        val id: String,
        override val jsonrpc: String = "2.0",
        val method: String,
        val params: JsonObject? = null
    ) : McpMessage()

    @Serializable @SerialName("response")
    data class Response(
        val id: String,
        override val jsonrpc: String = "2.0",
        val result: JsonElement? = null,
        val error: McpError? = null
    ) : McpMessage()

    @Serializable @SerialName("notification")
    data class Notification(
        override val jsonrpc: String = "2.0",
        val method: String,
        val params: JsonObject? = null
    ) : McpMessage()
}

/**
 * MCP Server capabilities.
 */
@Serializable
data class McpCapabilities(
    val tools: Boolean = true,
    val resources: Boolean = true,
    val prompts: Boolean = false,
    val logging: Boolean = true
)

/**
 * MCP Server info.
 */
@Serializable
data class McpServerInfo(
    val name: String,
    val version: String
)

/**
 * MCP Initialize result.
 */
@Serializable
data class McpInitializeResult(
    val protocolVersion: String = "2024-11-05",
    val serverInfo: McpServerInfo,
    val capabilities: McpCapabilities
)

/**
 * MCP Tools list result.
 */
@Serializable
data class McpToolsListResult(val tools: List<McpToolDef>)

/**
 * MCP Resources list result.
 */
@Serializable
data class McpResourcesListResult(val resources: List<McpResource>)

/**
 * MCP Resource content.
 */
@Serializable
data class McpResourceContent(
    val uri: String,
    val text: String,
    val mimeType: String? = null
)

/**
 * MCP Resource read result.
 */
@Serializable
data class McpResourceReadResult(val contents: List<McpResourceContent>)

/**
 * MCP Server registry for tools and resources.
 */
class McpServer(
    val name: String,
    val version: String = "1.0.0",
    val capabilities: McpCapabilities = McpCapabilities(),
    val wireFormat: WireFormat = WireFormat.JSON
) {
    private val tools = mutableMapOf<String, McpTool>()
    private val resources = mutableMapOf<String, McpResource>()
    private val resourceHandlers = mutableMapOf<String, suspend () -> String>()

    fun registerTool(tool: McpTool) {
        tools[tool.name] = tool
    }

    fun registerResource(resource: McpResource, handler: suspend () -> String) {
        resources[resource.uri] = resource
        resourceHandlers[resource.uri] = handler
    }

    fun listTools(): List<McpToolDef> = tools.values.map { it.def }
    fun listResources(): List<McpResource> = resources.values.toList()

    suspend fun invokeTool(name: String, args: JsonObject): McpToolResult {
        val tool = tools[name] ?: return McpToolResult.Error("Tool not found: $name", -32601)
        return try {
            tool.handler(args)
        } catch (e: Exception) {
            McpToolResult.Error(e.message ?: "Tool execution failed", -32603)
        }
    }

    suspend fun readResource(uri: String): String? {
        return resourceHandlers[uri]?.invoke()
    }

    /** Handle incoming wire message */
    suspend fun handleWire(data: ByteArray): ByteArray {
        val msg = ProtoWire.decode<McpMessage>(data, wireFormat)
        val response = handleMessage(msg)
        return response?.let { ProtoWire.encode(it, wireFormat) } ?: byteArrayOf()
    }

    /** Handle incoming MCP message */
    suspend fun handleMessage(msg: McpMessage): McpMessage.Response? {
        return when (msg) {
            is McpMessage.Request -> handleRequest(msg)
            is McpMessage.Response -> null
            is McpMessage.Notification -> { handleNotification(msg); null }
        }
    }

    private suspend fun handleRequest(req: McpMessage.Request): McpMessage.Response {
        return when (req.method) {
            "initialize" -> {
                val result = McpInitializeResult(
                    serverInfo = McpServerInfo(name, version),
                    capabilities = capabilities
                )
                McpMessage.Response(id = req.id, result = ProtoWire.json.encodeToJsonElement(result))
            }
            "tools/list" -> {
                val result = McpToolsListResult(listTools())
                McpMessage.Response(id = req.id, result = ProtoWire.json.encodeToJsonElement(result))
            }
            "tools/call" -> {
                val toolName = req.params?.get("name")?.jsonPrimitive?.content
                    ?: return McpMessage.Response(req.id, error = McpError(-32602, "Missing tool name"))
                val args = req.params["arguments"]?.jsonObject ?: JsonObject(emptyMap())
                when (val result = invokeTool(toolName, args)) {
                    is McpToolResult.Success -> McpMessage.Response(
                        id = req.id,
                        result = ProtoWire.json.encodeToJsonElement(result)
                    )
                    is McpToolResult.Error -> McpMessage.Response(
                        id = req.id,
                        error = McpError(result.code, result.message)
                    )
                }
            }
            "resources/list" -> {
                val result = McpResourcesListResult(listResources())
                McpMessage.Response(id = req.id, result = ProtoWire.json.encodeToJsonElement(result))
            }
            "resources/read" -> {
                val uri = req.params?.get("uri")?.jsonPrimitive?.content
                    ?: return McpMessage.Response(req.id, error = McpError(-32602, "Missing resource URI"))
                val content = readResource(uri)
                    ?: return McpMessage.Response(req.id, error = McpError(-32601, "Resource not found"))
                val result = McpResourceReadResult(listOf(
                    McpResourceContent(uri, content, resources[uri]?.mimeType)
                ))
                McpMessage.Response(id = req.id, result = ProtoWire.json.encodeToJsonElement(result))
            }
            else -> McpMessage.Response(req.id, error = McpError(-32601, "Method not found: ${req.method}"))
        }
    }

    private fun handleNotification(notif: McpMessage.Notification) {
        // Handle notifications (logging, progress, etc.)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// WEBDAV - RFC 4918 WebDAV protocol for document storage
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * WebDAV HTTP methods extending Ktor HttpMethod.
 * RFC 4918 defines PROPFIND, PROPPATCH, MKCOL, COPY, MOVE, LOCK, UNLOCK.
 */
@Serializable
enum class WebDavMethod(val value: String) {
    @SerialName("OPTIONS") OPTIONS("OPTIONS"),
    @SerialName("GET") GET("GET"),
    @SerialName("HEAD") HEAD("HEAD"),
    @SerialName("PUT") PUT("PUT"),
    @SerialName("DELETE") DELETE("DELETE"),
    @SerialName("MKCOL") MKCOL("MKCOL"),
    @SerialName("COPY") COPY("COPY"),
    @SerialName("MOVE") MOVE("MOVE"),
    @SerialName("PROPFIND") PROPFIND("PROPFIND"),
    @SerialName("PROPPATCH") PROPPATCH("PROPPATCH"),
    @SerialName("LOCK") LOCK("LOCK"),
    @SerialName("UNLOCK") UNLOCK("UNLOCK");

    companion object {
        /** Parse HTTP method string to WebDavMethod */
        fun parse(method: String): WebDavMethod? =
            entries.find { it.value.equals(method, ignoreCase = true) }

        /** Standard HTTP methods (setOf for O(1) contains) */
        val standardMethods = setOf(OPTIONS, GET, HEAD, PUT, DELETE)

        /** WebDAV extension methods (RFC 4918) */
        val davMethods = setOf(MKCOL, COPY, MOVE, PROPFIND, PROPPATCH, LOCK, UNLOCK)

        /** Methods that typically have a request body */
        val methodsWithBody = setOf(PUT, PROPFIND, PROPPATCH, LOCK)
    }
}

// Usage with Ktor: io.ktor.http.HttpMethod(webDavMethod.value)
// Example: HttpMethod(WebDavMethod.PROPFIND.value) creates Ktor's HttpMethod("PROPFIND")

// ═══════════════════════════════════════════════════════════════════════════════
// OKIO INTEGRATION - Buffer/Source/Sink for efficient streaming
// Usage: okio.Buffer, okio.Source, okio.Sink for Ktor/OkHttp streaming
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Okio-compatible streaming body for WebDAV operations.
 * Integrates with okio.Buffer for efficient byte streaming.
 */
interface OkioBody {
    /** Content length (-1 if unknown/streaming) */
    val contentLength: Long get() = -1L

    /** Content type for the body */
    val contentType: String

    /** Write body content to an Okio BufferedSink */
    fun writeTo(sink: Any) // okio.BufferedSink
}

/**
 * ByteArray body with Okio integration.
 */
data class ByteArrayBody(
    val bytes: ByteArray,
    override val contentType: String = "application/octet-stream"
) : OkioBody {
    override val contentLength: Long get() = bytes.size.toLong()
    override fun writeTo(sink: Any) {
        // sink.write(bytes) when using actual okio.BufferedSink
    }
    override fun equals(other: Any?) = other is ByteArrayBody && bytes.contentEquals(other.bytes)
    override fun hashCode() = bytes.contentHashCode()
}

/**
 * String body with Okio integration.
 */
data class StringBody(
    val content: String,
    override val contentType: String = "text/plain; charset=utf-8"
) : OkioBody {
    override val contentLength: Long get() = content.encodeToByteArray().size.toLong()
    override fun writeTo(sink: Any) {
        // sink.writeUtf8(content) when using actual okio.BufferedSink
    }
}

/**
 * Streaming body with Okio Source.
 * Used for large content that shouldn't be loaded into memory.
 */
data class StreamingBody(
    val source: Any, // okio.Source
    override val contentLength: Long = -1L,
    override val contentType: String = "application/octet-stream"
) : OkioBody {
    override fun writeTo(sink: Any) {
        // sink.writeAll(source) when using actual okio.BufferedSink and okio.Source
    }
}

/**
 * Range-aware body for HTTP 206 Partial Content responses.
 * Integrates with Okio for efficient range streaming.
 */
data class RangeBody(
    val source: Any, // okio.Source
    val range: Range,
    override val contentType: String = "application/octet-stream"
) : OkioBody {
    override val contentLength: Long get() = range.length
    override fun writeTo(sink: Any) {
        // Skip to range.start, write range.length bytes
        // source.skip(range.start); sink.write(source, range.length)
    }
}

/**
 * WebDAV property.
 */
@Serializable
data class DavProperty(
    val namespace: String,
    val name: String,
    val value: String?
)

/**
 * WebDAV resource properties.
 */
@Serializable
data class DavResource(
    val href: String,
    val displayName: String? = null,
    val contentType: String? = null,
    val contentLength: Long? = null,
    val lastModified: String? = null,
    val etag: String? = null,
    val isCollection: Boolean = false,
    val properties: List<DavProperty> = emptyList()
)

/**
 * WebDAV lock info.
 */
@Serializable
data class DavLock(
    val token: String,
    val owner: String,
    val scope: DavLockScope = DavLockScope.EXCLUSIVE,
    val type: DavLockType = DavLockType.WRITE,
    val depth: DavDepth = DavDepth.INFINITY,
    val timeout: Long = 3600
)

@Serializable
enum class DavLockScope {
    @SerialName("exclusive") EXCLUSIVE,
    @SerialName("shared") SHARED
}

@Serializable
enum class DavLockType {
    @SerialName("write") WRITE
}

@Serializable
enum class DavDepth {
    @SerialName("0") ZERO,
    @SerialName("1") ONE,
    @SerialName("infinity") INFINITY
}

/**
 * WebDAV PROPFIND response.
 */
@Serializable
data class DavMultistatus(
    val responses: List<DavResponse>
)

@Serializable
data class DavResponse(
    val href: String,
    val propstat: List<DavPropstat>
)

@Serializable
data class DavPropstat(
    val properties: List<DavProperty>,
    val status: Int = 200
)

/**
 * WebDAV request builder with wire format content negotiation.
 * Supports all WireFormat types via Accept header.
 * Integrates with Okio for efficient streaming via OkioBody.
 */
class DavRequest(
    val method: WebDavMethod,
    val path: String,
    val depth: DavDepth = DavDepth.ONE,
    val headers: Map<String, String> = emptyMap(),
    val body: String? = null,
    val okioBody: OkioBody? = null,
    val format: WireFormat = WireFormat.JSON
) {
    /** Content-Type for request body based on wire format or Okio body */
    val contentType: String get() = okioBody?.contentType ?: format.toContentType()

    /** Accept header for response content negotiation (all methods) */
    val accept: String get() = format.toContentType()

    /** HTTP method value for Ktor routing - use with io.ktor.http.HttpMethod(methodValue) */
    val methodValue: String get() = method.value

    /** All headers including Ktor content negotiation (Accept for all methods) */
    val allHeaders: Map<String, String> get() {
        val base = mutableMapOf<String, String>()
        // Accept header for all methods (Ktor content negotiation)
        base["Accept"] = accept
        // Depth header for WebDAV methods
        if (method in WebDavMethod.davMethods) {
            base["Depth"] = when (depth) {
                DavDepth.ZERO -> "0"
                DavDepth.ONE -> "1"
                DavDepth.INFINITY -> "infinity"
            }
        }
        // Content-Type for methods with body
        if (method in WebDavMethod.methodsWithBody) {
            base["Content-Type"] = contentType
        }
        return base + headers
    }

    /** Generate request body in specified wire format */
    fun toBody(): String? = when (method) {
        WebDavMethod.PROPFIND -> encodePropfindBody()
        WebDavMethod.PROPPATCH -> body?.let { encodeProppatchBody(it) }
        WebDavMethod.LOCK -> encodeLockBody()
        else -> body
    }

    private fun encodePropfindBody(): String = when (format) {
        WireFormat.XML -> """
            <?xml version="1.0" encoding="utf-8"?>
            <D:propfind xmlns:D="DAV:">
                <D:allprop/>
            </D:propfind>
        """.trimIndent()
        WireFormat.JSON -> """{"propfind":{"allprop":true}}"""
        WireFormat.MESSAGE_PACK -> """{"propfind":{"allprop":true}}"""
        WireFormat.CBOR -> """{"propfind":{"allprop":true}}"""
        WireFormat.YDOC -> """{"propfind":{"allprop":true,"format":"ydoc"}}"""
        WireFormat.SEGMENTS -> """{"propfind":{"allprop":true,"format":"segments"}}"""
    }

    private fun encodeProppatchBody(props: String): String = when (format) {
        WireFormat.XML -> """
            <?xml version="1.0" encoding="utf-8"?>
            <D:propertyupdate xmlns:D="DAV:">
                <D:set>
                    <D:prop>$props</D:prop>
                </D:set>
            </D:propertyupdate>
        """.trimIndent()
        else -> props // JSON/other formats pass through
    }

    private fun encodeLockBody(): String = when (format) {
        WireFormat.XML -> """
            <?xml version="1.0" encoding="utf-8"?>
            <D:lockinfo xmlns:D="DAV:">
                <D:lockscope><D:exclusive/></D:lockscope>
                <D:locktype><D:write/></D:locktype>
            </D:lockinfo>
        """.trimIndent()
        WireFormat.JSON -> """{"lockinfo":{"scope":"exclusive","type":"write"}}"""
        WireFormat.MESSAGE_PACK -> """{"lockinfo":{"scope":"exclusive","type":"write"}}"""
        WireFormat.CBOR -> """{"lockinfo":{"scope":"exclusive","type":"write"}}"""
        WireFormat.YDOC -> """{"lockinfo":{"scope":"exclusive","type":"write"}}"""
        WireFormat.SEGMENTS -> """{"lockinfo":{"scope":"exclusive","type":"write"}}"""
    }

    /** Legacy XML body (deprecated, use toBody()) */
    @Deprecated("Use toBody() with format parameter", ReplaceWith("toBody()"))
    fun toXml(): String? = DavRequest(method, path, depth, headers, body, format = WireFormat.XML).toBody()

    companion object {
        /** PROPFIND with content negotiation */
        fun propfind(path: String, depth: DavDepth = DavDepth.ONE, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.PROPFIND, path, depth, format = format)

        /** PROPPATCH with content negotiation */
        fun proppatch(path: String, properties: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.PROPPATCH, path, body = properties, format = format)

        /** GET with Accept header for content negotiation */
        fun get(path: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.GET, path, format = format)

        /** HEAD with Accept header */
        fun head(path: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.HEAD, path, format = format)

        /** OPTIONS with Accept header */
        fun options(path: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.OPTIONS, path, format = format)

        /** PUT with content negotiation */
        fun put(path: String, content: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.PUT, path, body = content, format = format)

        /** PUT with Okio streaming body */
        fun put(path: String, body: OkioBody, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.PUT, path, okioBody = body, format = format)

        /** PUT with byte array */
        fun put(path: String, bytes: ByteArray, contentType: String = "application/octet-stream") =
            DavRequest(WebDavMethod.PUT, path, okioBody = ByteArrayBody(bytes, contentType))

        /** DELETE with Accept header */
        fun delete(path: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.DELETE, path, format = format)

        /** MKCOL (create collection) */
        fun mkcol(path: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.MKCOL, path, format = format)

        /** LOCK with content negotiation */
        fun lock(path: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.LOCK, path, format = format)

        /** UNLOCK */
        fun unlock(path: String, token: String, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.UNLOCK, path, headers = mapOf("Lock-Token" to "<$token>"), format = format)

        /** COPY with Accept header */
        fun copy(source: String, destination: String, overwrite: Boolean = false, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.COPY, source, headers = mapOf(
                "Destination" to destination,
                "Overwrite" to if (overwrite) "T" else "F"
            ), format = format)

        /** MOVE with Accept header */
        fun move(source: String, destination: String, overwrite: Boolean = false, format: WireFormat = WireFormat.JSON) =
            DavRequest(WebDavMethod.MOVE, source, headers = mapOf(
                "Destination" to destination,
                "Overwrite" to if (overwrite) "T" else "F"
            ), format = format)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// YKT - Real-time collaboration protocol (SSE + WSS + JsonPatch)
// Uses unified ProtoWire codec
// ═══════════════════════════════════════════════════════════════════════════════

/** Extension for YktEvent encoding */
fun ProtoWire.encodeEvent(event: YktEvent): String = encodeToString(event)
fun ProtoWire.decodeEvent(data: String): YktEvent = decodeFromString(data)

/**
 * Ykt collaboration session.
 */
@Serializable
data class YktSession(
    val id: SessionId,
    val documentUri: ResourceUri,
    val clientId: ClientId,
    val createdAt: Long = 0
) {
    constructor(id: String, documentUri: String, clientId: String, createdAt: Long = 0) :
        this(SessionId(id), ResourceUri(documentUri), ClientId(clientId), createdAt)
}

/**
 * Selection range for awareness - optimized as value class pair.
 */
@Serializable
data class YktSelection(val start: Int, val end: Int) {
    val length get() = end - start
    val isEmpty get() = start == end
    fun contains(pos: Int) = pos in start until end
    fun toIntRange() = start until end
}

/**
 * Serializable JsonPatchOp - unified with JsonPointer.
 */
@Serializable
sealed class YktPatchOp {
    abstract val path: JsonPointer

    @Serializable @SerialName("add")
    data class Add(override val path: JsonPointer, val value: JsonElement) : YktPatchOp() {
        constructor(pathStr: String, value: JsonElement) : this(JsonPointer(pathStr), value)
    }
    @Serializable @SerialName("remove")
    data class Remove(override val path: JsonPointer) : YktPatchOp() {
        constructor(pathStr: String) : this(JsonPointer(pathStr))
    }
    @Serializable @SerialName("replace")
    data class Replace(override val path: JsonPointer, val value: JsonElement) : YktPatchOp() {
        constructor(pathStr: String, value: JsonElement) : this(JsonPointer(pathStr), value)
    }
    @Serializable @SerialName("move")
    data class Move(override val path: JsonPointer, val from: JsonPointer) : YktPatchOp() {
        constructor(pathStr: String, fromStr: String) : this(JsonPointer(pathStr), JsonPointer(fromStr))
    }
    @Serializable @SerialName("copy")
    data class Copy(override val path: JsonPointer, val from: JsonPointer) : YktPatchOp() {
        constructor(pathStr: String, fromStr: String) : this(JsonPointer(pathStr), JsonPointer(fromStr))
    }
    @Serializable @SerialName("test")
    data class Test(override val path: JsonPointer, val value: JsonElement) : YktPatchOp() {
        constructor(pathStr: String, value: JsonElement) : this(JsonPointer(pathStr), value)
    }
}


/**
 * Ykt event types for SSE/WSS transport.
 * Implements ProtoEvent for unified protocol handling.
 */
@Serializable
sealed class YktEvent : ProtoEvent {
    abstract val sessionId: String
    abstract val clientId: String
    abstract override val timestamp: Long

    override val eventType: String get() = when (this) {
        is Patch -> "patch"
        is Awareness -> "awareness"
        is Join -> "join"
        is Leave -> "leave"
        is Sync -> "sync"
        is Ack -> "ack"
    }

    /** Document patch event */
    @Serializable @SerialName("patch")
    data class Patch(
        override val sessionId: String,
        override val clientId: String,
        override val timestamp: Long,
        val ops: List<YktPatchOp>,
        val version: ProtoVersion
    ) : YktEvent()

    /** Cursor/selection awareness */
    @Serializable @SerialName("awareness")
    data class Awareness(
        override val sessionId: String,
        override val clientId: String,
        override val timestamp: Long,
        val cursor: Int? = null,
        val selection: YktSelection? = null,
        val user: Map<String, String> = emptyMap()
    ) : YktEvent()

    /** Client joined */
    @Serializable @SerialName("join")
    data class Join(
        override val sessionId: String,
        override val clientId: String,
        override val timestamp: Long,
        val user: Map<String, String> = emptyMap()
    ) : YktEvent()

    /** Client left */
    @Serializable @SerialName("leave")
    data class Leave(
        override val sessionId: String,
        override val clientId: String,
        override val timestamp: Long
    ) : YktEvent()

    /** Full document sync */
    @Serializable @SerialName("sync")
    data class Sync(
        override val sessionId: String,
        override val clientId: String,
        override val timestamp: Long,
        val document: JsonObject,
        val version: ProtoVectorClock
    ) : YktEvent()

    /** Ack/confirmation */
    @Serializable @SerialName("ack")
    data class Ack(
        override val sessionId: String,
        override val clientId: String,
        override val timestamp: Long,
        val ackedVersion: ProtoVersion
    ) : YktEvent()

    /** Convert to SSE format */
    fun toSse() = "event: $eventType\ndata: ${ProtoWire.encodeEvent(this)}\n\n"
}

/**
 * Ykt transport abstraction for SSE/WSS.
 */
interface YktTransport {
    val isConnected: Boolean
    suspend fun connect(sessionId: String, clientId: String)
    suspend fun disconnect()
    suspend fun send(event: YktEvent)
    suspend fun receive(): YktEvent?
}

/**
 * Ykt audit log for SSE streaming.
 */
class YktAuditLog {
    private val events = mutableListOf<YktEvent>()
    private val listeners = mutableListOf<(YktEvent) -> Unit>()

    fun append(event: YktEvent) {
        events.add(event)
        listeners.forEach { it(event) }
    }

    fun subscribe(listener: (YktEvent) -> Unit): () -> Unit {
        listeners.add(listener)
        return { listeners.remove(listener) }
    }

    fun replay(fromTimestamp: Long = 0): Sequence<YktEvent> =
        events.asSequence().filter { it.timestamp >= fromTimestamp }

    fun toSseStream(fromTimestamp: Long = 0): String =
        replay(fromTimestamp).joinToString("") { it.toSse() }
}

/**
 * Ykt collaboration client - optimized with value class IDs.
 */
class YktClient(
    private val transport: YktTransport,
    val clientId: ClientId = ClientId.generate(),
    val wireFormat: WireFormat = WireFormat.JSON
) {
    constructor(transport: YktTransport, clientIdStr: String, wireFormat: WireFormat = WireFormat.JSON) :
        this(transport, ClientId(clientIdStr), wireFormat)

    private var session: YktSession? = null
    private var localClock = ProtoVectorClock()
    private var document = JsonObject(emptyMap())
    private val pendingOps = mutableListOf<YktEvent.Patch>()
    private val eventHandlers = mutableListOf<(YktEvent) -> Unit>()

    suspend fun join(documentUri: String): YktSession {
        val sessionId = SessionId.generate()
        transport.connect(sessionId.value, clientId.value)
        val sess = YktSession(sessionId, ResourceUri(documentUri), clientId, currentTimeMillis())
        session = sess

        transport.send(YktEvent.Join(
            sessionId = sessionId.value,
            clientId = clientId.value,
            timestamp = currentTimeMillis()
        ))

        return sess
    }

    suspend fun leave() {
        session?.let { sess ->
            transport.send(YktEvent.Leave(
                sessionId = sess.id.value,
                clientId = clientId.value,
                timestamp = currentTimeMillis()
            ))
        }
        transport.disconnect()
        session = null
    }

    suspend fun applyPatch(ops: List<YktPatchOp>) {
        val sess = session ?: return
        localClock = localClock.tick(clientId)
        val version = ProtoVersion(clientId, localClock[clientId])

        val event = YktEvent.Patch(
            sessionId = sess.id.value,
            clientId = clientId.value,
            timestamp = currentTimeMillis(),
            ops = ops,
            version = version
        )
        pendingOps.add(event)
        transport.send(event)
    }

    suspend fun updateAwareness(cursor: Int? = null, selection: YktSelection? = null, user: Map<String, String> = emptyMap()) {
        val sess = session ?: return
        transport.send(YktEvent.Awareness(
            sessionId = sess.id.value,
            clientId = clientId.value,
            timestamp = currentTimeMillis(),
            cursor = cursor,
            selection = selection,
            user = user
        ))
    }

    suspend fun processIncoming() {
        val event = transport.receive() ?: return
        when (event) {
            is YktEvent.Patch -> {
                if (event.clientId != clientId.value) {
                    localClock = localClock.merge(ProtoVectorClock(mapOf(event.version.clientId.value to event.version.clock)))
                }
            }
            is YktEvent.Sync -> {
                document = event.document
                localClock = localClock.merge(event.version)
            }
            is YktEvent.Ack -> {
                pendingOps.removeAll { it.version == event.ackedVersion }
            }
            else -> { }
        }
        eventHandlers.forEach { it(event) }
    }

    fun onEvent(handler: (YktEvent) -> Unit): () -> Unit {
        eventHandlers.add(handler)
        return { eventHandlers.remove(handler) }
    }

    fun getDocument(): JsonObject = document
}

/**
 * Ykt collaboration server - optimized with value class IDs.
 */
class YktServer {
    private val sessions = mutableMapOf<SessionId, YktServerSession>()
    val auditLog = YktAuditLog()

    fun createSession(documentUri: String): YktServerSession {
        val sessionId = SessionId.generate()
        val session = YktServerSession(sessionId, ResourceUri(documentUri), auditLog)
        sessions[sessionId] = session
        return session
    }

    fun getSession(sessionId: String): YktServerSession? = sessions[SessionId(sessionId)]
    fun getSession(sessionId: SessionId): YktServerSession? = sessions[sessionId]

    fun closeSession(sessionId: String) = sessions.remove(SessionId(sessionId))
    fun closeSession(sessionId: SessionId) = sessions.remove(sessionId)
}

class YktServerSession(
    val id: SessionId,
    val documentUri: ResourceUri,
    private val auditLog: YktAuditLog
) {
    constructor(id: String, documentUri: String, auditLog: YktAuditLog) :
        this(SessionId(id), ResourceUri(documentUri), auditLog)

    private val clients = mutableMapOf<ClientId, YktTransport>()
    private var document = JsonObject(emptyMap())
    private var clock = ProtoVectorClock()

    suspend fun addClient(clientId: String, transport: YktTransport) = addClient(ClientId(clientId), transport)

    suspend fun addClient(clientId: ClientId, transport: YktTransport) {
        clients[clientId] = transport

        // Send current state
        transport.send(YktEvent.Sync(
            sessionId = id.value,
            clientId = "server",
            timestamp = currentTimeMillis(),
            document = document,
            version = clock
        ))

        // Broadcast join
        broadcast(YktEvent.Join(
            sessionId = id.value,
            clientId = clientId.value,
            timestamp = currentTimeMillis()
        ), exclude = clientId)
    }

    fun removeClient(clientId: String) = clients.remove(ClientId(clientId))
    fun removeClient(clientId: ClientId) = clients.remove(clientId)

    suspend fun handleEvent(event: YktEvent) {
        auditLog.append(event)

        when (event) {
            is YktEvent.Patch -> {
                clock = clock.merge(ProtoVectorClock(mapOf(event.version.clientId.value to event.version.clock)))

                // Ack to sender
                clients[ClientId(event.clientId)]?.send(YktEvent.Ack(
                    sessionId = id.value,
                    clientId = "server",
                    timestamp = currentTimeMillis(),
                    ackedVersion = event.version
                ))

                // Broadcast to others
                broadcast(event, exclude = ClientId(event.clientId))
            }
            is YktEvent.Awareness -> broadcast(event, exclude = ClientId(event.clientId))
            is YktEvent.Leave -> {
                removeClient(event.clientId)
                broadcast(event)
            }
            else -> { }
        }
    }

    private suspend fun broadcast(event: YktEvent, exclude: ClientId? = null) {
        clients.filterKeys { it != exclude }.values.forEach { it.send(event) }
    }

    fun getDocument(): JsonObject = document
}

// ═══════════════════════════════════════════════════════════════════════════════
// KTOR CIO TRANSPORT - HTTP client interfaces for Ktor CIO engine
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * HTTP request abstraction for Ktor CIO.
 */
@Serializable
data class KtorRequest(
    val method: String,
    val url: String,
    val headers: Map<String, String> = emptyMap(),
    val body: String? = null,
    val timeout: Long = 30_000
)

/**
 * HTTP response abstraction.
 */
@Serializable
data class KtorResponse(
    val status: Int,
    val headers: Map<String, List<String>>,
    val body: String
)

/**
 * WebSocket message types.
 */
@Serializable
sealed class WsMessage {
    @Serializable @SerialName("text")
    data class Text(val content: String) : WsMessage()
    @Serializable @SerialName("binary")
    data class Binary(@Contextual val data: ByteArray) : WsMessage() {
        override fun equals(other: Any?) = other is Binary && data.contentEquals(other.data)
        override fun hashCode() = data.contentHashCode()
    }
    @Serializable @SerialName("close")
    data object Close : WsMessage()
    @Serializable @SerialName("ping")
    data object Ping : WsMessage()
    @Serializable @SerialName("pong")
    data object Pong : WsMessage()
}

/**
 * SSE event from server.
 */
@Serializable
data class SseEvent(
    val event: String? = null,
    val data: String,
    val id: String? = null,
    val retry: Long? = null
)

/**
 * HTTP client interface for Ktor CIO implementation.
 * Implement with actual Ktor HttpClient in JVM/Native targets.
 */
interface KtorCioClient {
    /** Execute HTTP request */
    suspend fun request(req: KtorRequest): KtorResponse

    /** Open WebSocket connection */
    suspend fun websocket(
        url: String,
        headers: Map<String, String> = emptyMap(),
        onMessage: suspend (WsMessage) -> Unit,
        onClose: suspend () -> Unit = {}
    ): WsSession

    /** Open SSE stream */
    suspend fun sse(
        url: String,
        headers: Map<String, String> = emptyMap(),
        onEvent: suspend (SseEvent) -> Unit,
        onClose: suspend () -> Unit = {}
    ): SseSession
}

/**
 * WebSocket session handle.
 */
interface WsSession {
    val isActive: Boolean
    suspend fun send(message: WsMessage)
    suspend fun close()
}

/**
 * SSE session handle.
 */
interface SseSession {
    val isActive: Boolean
    suspend fun close()
}

/**
 * Ykt transport implementation using Ktor CIO WebSocket.
 */
class YktWssTransport(
    private val client: KtorCioClient,
    private val baseUrl: String
) : YktTransport {
    private var session: WsSession? = null
    private val messageQueue = mutableListOf<YktEvent>()
    override val isConnected: Boolean get() = session?.isActive == true

    override suspend fun connect(sessionId: String, clientId: String) {
        session = client.websocket(
            url = "$baseUrl/mushi/$sessionId",
            headers = mapOf("X-Client-Id" to clientId),
            onMessage = { msg ->
                if (msg is WsMessage.Text) {
                    try {
                        val event = ProtoWire.decodeEvent(msg.content)
                        messageQueue.add(event)
                    } catch (_: Exception) { }
                }
            }
        )
    }

    override suspend fun disconnect() {
        session?.close()
        session = null
    }

    override suspend fun send(event: YktEvent) {
        session?.send(WsMessage.Text(ProtoWire.encodeEvent(event)))
    }

    override suspend fun receive(): YktEvent? {
        return if (messageQueue.isNotEmpty()) messageQueue.removeAt(0) else null
    }
}

/**
 * Ykt SSE transport for read-only audit stream.
 */
class YktSseTransport(
    private val client: KtorCioClient,
    private val baseUrl: String
) {
    private var session: SseSession? = null
    private val eventHandlers = mutableListOf<(YktEvent) -> Unit>()

    suspend fun connect(sessionId: String, fromTimestamp: Long = 0) {
        session = client.sse(
            url = "$baseUrl/mushi/$sessionId/audit?from=$fromTimestamp",
            onEvent = { sse ->
                try {
                    val event = ProtoWire.decodeEvent(sse.data)
                    eventHandlers.forEach { it(event) }
                } catch (_: Exception) { }
            }
        )
    }

    suspend fun disconnect() {
        session?.close()
        session = null
    }

    fun onEvent(handler: (YktEvent) -> Unit): () -> Unit {
        eventHandlers.add(handler)
        return { eventHandlers.remove(handler) }
    }
}

/**
 * WebDAV client using Ktor CIO.
 */
/**
 * WebDAV client using Ktor CIO with wire format negotiation.
 * Supports all WireFormat types via Accept header.
 */
class WebDavClient(
    private val client: KtorCioClient,
    private val baseUrl: String,
    private val defaultFormat: WireFormat = WireFormat.JSON
) {
    suspend fun propfind(
        path: String,
        depth: DavDepth = DavDepth.ONE,
        format: WireFormat = defaultFormat
    ): DavMultistatus {
        val req = DavRequest.propfind(path, depth, format)
        val resp = client.request(KtorRequest(
            method = "PROPFIND",
            url = "$baseUrl$path",
            headers = req.allHeaders,
            body = req.toBody()
        ))
        return parseMultistatus(resp.body, format)
    }

    suspend fun proppatch(
        path: String,
        properties: String,
        format: WireFormat = defaultFormat
    ): Boolean {
        val req = DavRequest.proppatch(path, properties, format)
        val resp = client.request(KtorRequest(
            method = "PROPPATCH",
            url = "$baseUrl$path",
            headers = req.allHeaders,
            body = req.toBody()
        ))
        return resp.status in 200..299
    }

    suspend fun get(path: String, format: WireFormat = defaultFormat): String {
        val req = DavRequest.get(path, format)
        val resp = client.request(KtorRequest(
            method = "GET",
            url = "$baseUrl$path",
            headers = req.allHeaders
        ))
        return resp.body
    }

    suspend fun put(path: String, content: String, contentType: String = "application/octet-stream"): Boolean {
        val resp = client.request(KtorRequest(
            method = "PUT",
            url = "$baseUrl$path",
            headers = mapOf("Content-Type" to contentType),
            body = content
        ))
        return resp.status in 200..299
    }

    suspend fun delete(path: String): Boolean {
        val resp = client.request(KtorRequest(method = "DELETE", url = "$baseUrl$path"))
        return resp.status in 200..299
    }

    suspend fun mkcol(path: String): Boolean {
        val resp = client.request(KtorRequest(method = "MKCOL", url = "$baseUrl$path"))
        return resp.status == 201
    }

    suspend fun lock(path: String, format: WireFormat = defaultFormat): DavLock? {
        val req = DavRequest.lock(path, format)
        val resp = client.request(KtorRequest(
            method = "LOCK",
            url = "$baseUrl$path",
            headers = req.allHeaders,
            body = req.toBody()
        ))
        return if (resp.status == 200) parseLock(resp.body, resp.headers, format) else null
    }

    suspend fun unlock(path: String, token: String): Boolean {
        val resp = client.request(KtorRequest(
            method = "UNLOCK",
            url = "$baseUrl$path",
            headers = mapOf("Lock-Token" to "<$token>")
        ))
        return resp.status == 204
    }

    private fun parseMultistatus(body: String, format: WireFormat): DavMultistatus = when (format) {
        WireFormat.JSON, WireFormat.MESSAGE_PACK, WireFormat.CBOR ->
            try { ProtoWire.decodeFromString<DavMultistatus>(body) } catch (_: Exception) { DavMultistatus(emptyList()) }
        WireFormat.XML -> parseMultistatusXml(body)
        WireFormat.YDOC -> DavMultistatus(emptyList()) // YDoc state not applicable
        WireFormat.SEGMENTS -> DavMultistatus(emptyList()) // Segments not applicable
    }

    private fun parseMultistatusXml(xml: String): DavMultistatus {
        // Simplified XML parsing - extract href elements
        val hrefRegex = Regex("""<D:href>([^<]+)</D:href>""")
        val responses = hrefRegex.findAll(xml).map { match ->
            DavResponse(href = match.groupValues[1], propstat = emptyList())
        }.toList()
        return DavMultistatus(responses)
    }

    private fun parseLock(body: String, headers: Map<String, List<String>>, format: WireFormat): DavLock? {
        val token = headers["Lock-Token"]?.firstOrNull()?.removeSurrounding("<", ">") ?: return null
        return when (format) {
            WireFormat.JSON, WireFormat.MESSAGE_PACK, WireFormat.CBOR ->
                try { ProtoWire.decodeFromString<DavLock>(body) } catch (_: Exception) { DavLock(token = token, owner = "") }
            else -> DavLock(token = token, owner = "")
        }
    }
}

// Utility functions
private fun currentTimeMillis(): Long = kotlin.time.TimeSource.Monotonic.markNow().elapsedNow().inWholeMilliseconds
private fun randomId(len: Int): String = (1..len).map { "abcdefghijklmnopqrstuvwxyz0123456789".random() }.joinToString("")
