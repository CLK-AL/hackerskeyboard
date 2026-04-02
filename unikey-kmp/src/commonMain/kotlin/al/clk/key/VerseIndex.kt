package al.clk.key

/**
 * Verse Index State - common logic for verse/line indexing.
 * Used by Compose UI bindings (Android, Desktop) and Web (JS).
 */
data class VerseIndexState(
    val vType: Int,           // Verse type (V1, V2, V3...)
    val lineIdx: Int,         // Line index within verse (0-based)
    val order: Float = 0f     // Float ordering for line insertion (1.0, 1.5, 2.0...)
) {
    /** Formatted index string: "V1.L1", "V2.L3", etc. */
    val formatted: String get() = "V${vType}.L${lineIdx + 1}"

    companion object {
        /** Compute new order for inserting between two lines */
        fun insertOrder(before: Float, after: Float): Float = (before + after) / 2f
    }
}

/**
 * Rhyme Scheme State - computed from IPA endings.
 * Letters (A, B, C...) assigned to matching IPA patterns.
 */
data class RhymeSchemeState(
    val lines: List<RhymeLineState>
) {
    /** Get rhyme pattern string: "AABB", "ABAB", etc. */
    val pattern: String get() = lines.joinToString("") { it.letter.toString() }

    data class RhymeLineState(
        val letter: Char,     // Rhyme letter: A, B, C...
        val ipa: String,      // IPA ending pattern
        val hue: Int          // Color hue 0-360 from IPA
    )

    companion object {
        /**
         * Compute rhyme scheme from line IPA endings.
         * Same IPA -> same letter, different IPA -> next letter.
         */
        fun compute(ipas: List<String>): RhymeSchemeState {
            val ipaToLetter = mutableMapOf<String, Char>()
            var nextLetter = 'A'

            val lines = ipas.map { ipa ->
                val letter = ipaToLetter.getOrPut(ipa) { nextLetter++ }
                val hue = IpaColor.ipaHue(ipa)
                RhymeLineState(letter, ipa, hue)
            }

            return RhymeSchemeState(lines)
        }

        /**
         * Compute rhyme scheme from text lines (auto-detects script).
         */
        fun fromLines(lines: List<String>, isHebrew: Boolean = false): RhymeSchemeState {
            val ipas = lines.map { line ->
                IpaColor.lineEndIpa(line, isHebrew)
            }
            return compute(ipas)
        }
    }
}

/**
 * Cursor State - for syllable-aware text editing.
 * Tracks position within word and syllable boundaries.
 */
data class CursorState(
    val pos: Int,             // Character position in text
    val wordIdx: Int,         // Word index in line
    val sylBounds: List<Int>  // Syllable boundaries [0, 3, 6] for "rag|ing"
) {
    /** Next syllable boundary after current position */
    fun nextSylBound(): Int = sylBounds.firstOrNull { it > pos } ?: sylBounds.lastOrNull() ?: pos

    /** Previous syllable boundary before current position */
    fun prevSylBound(): Int = sylBounds.lastOrNull { it < pos } ?: sylBounds.firstOrNull() ?: pos

    /** Check if cursor is at a syllable boundary */
    val isAtBoundary: Boolean get() = pos in sylBounds

    /** Current syllable index (0-based) based on cursor position */
    val sylIdx: Int get() {
        if (sylBounds.size <= 1) return 0
        val idx = sylBounds.indexOfFirst { it > pos }
        return if (idx <= 0) (sylBounds.size - 2).coerceAtLeast(0) else idx - 1
    }

    /**
     * Create full hierarchical index from cursor position.
     */
    fun toIndex(vType: Int, lineIdx: Int, lineOrder: Float = 0f, wordOrder: Float = 0f): HierarchicalIndex {
        return HierarchicalIndex(
            vType = vType,
            lineIdx = lineIdx,
            wordIdx = wordIdx,
            sylIdx = sylIdx,
            lineOrder = lineOrder,
            wordOrder = wordOrder
        )
    }

    companion object {
        /**
         * Compute syllable boundaries for a word using IPA parsing.
         * Returns list of character offsets where syllables start/end.
         */
        fun syllableBoundaries(word: String): List<Int> {
            val syls = UniKeySyllable.toIpa(word)
            val bounds = mutableListOf(0)
            var offset = 0
            syls.forEach { syl ->
                offset += syl.original.length
                bounds.add(offset)
            }
            return bounds
        }

        /**
         * Create cursor state for a position within a word.
         */
        fun forWord(word: String, pos: Int, wordIdx: Int = 0): CursorState {
            return CursorState(
                pos = pos,
                wordIdx = wordIdx,
                sylBounds = syllableBoundaries(word)
            )
        }
    }
}

/**
 * Line State - represents a single line in a verse.
 * Used for float ordering and metadata tracking.
 */
data class LineState(
    val text: String,
    val order: Float,         // Float for insertion ordering
    val rhyme: RhymeSchemeState.RhymeLineState? = null
)

/**
 * Verse State - complete state for a verse/stanza.
 * Combines verse index, lines, and rhyme scheme.
 */
data class VerseState(
    val id: Int,
    val vType: Int,           // Verse type index (V1, V2...)
    val label: String,        // Display label (א׳, ב׳...)
    val tag: String,          // Suno tag (Verse, Chorus...)
    val enLines: List<LineState>,
    val heLines: List<LineState>,
    val rhymeScheme: RhymeSchemeState? = null
) {
    /** Get verse index state for a specific line */
    fun indexFor(lineIdx: Int): VerseIndexState {
        val order = enLines.getOrNull(lineIdx)?.order ?: (lineIdx + 1).toFloat()
        return VerseIndexState(vType, lineIdx, order)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Hierarchical Index System (V1.L2.W3.S4)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Hierarchical Index - full V1.L2.W3.S4 notation for precise text positioning.
 * Supports optional depth levels while maintaining backward compatibility.
 */
data class HierarchicalIndex(
    val vType: Int,                    // V1, V2... (always required)
    val lineIdx: Int? = null,          // 0-based internal
    val wordIdx: Int? = null,          // 0-based internal
    val sylIdx: Int? = null,           // 0-based internal
    val lineOrder: Float = 0f,
    val wordOrder: Float = 0f,
    val sylOrder: Float = 0f
) {
    /** Depth of index: 1=V, 2=V.L, 3=V.L.W, 4=V.L.W.S */
    val depth: Int get() = when {
        sylIdx != null -> 4
        wordIdx != null -> 3
        lineIdx != null -> 2
        else -> 1
    }

    /** Formatted index string: "V1", "V1.L2", "V1.L2.W3", "V1.L2.W3.S4" */
    val formatted: String get() = buildString {
        append("V$vType")
        lineIdx?.let { append(".L${it + 1}") }
        wordIdx?.let { append(".W${it + 1}") }
        sylIdx?.let { append(".S${it + 1}") }
    }

    /** Convert to legacy VerseIndexState (backward compatibility) */
    fun toVerseIndexState(): VerseIndexState = VerseIndexState(
        vType = vType,
        lineIdx = lineIdx ?: 0,
        order = lineOrder
    )

    companion object {
        /** Compute new order for inserting between two elements */
        fun insertOrder(before: Float, after: Float): Float = (before + after) / 2f

        /** Create from legacy VerseIndexState */
        fun fromVerseIndexState(state: VerseIndexState): HierarchicalIndex = HierarchicalIndex(
            vType = state.vType,
            lineIdx = state.lineIdx,
            lineOrder = state.order
        )

        /**
         * Parse formatted string like "V1.L2.W3.S4" into HierarchicalIndex.
         * Returns null if format is invalid.
         */
        fun parse(formatted: String): HierarchicalIndex? {
            val parts = formatted.uppercase().split(".")
            if (parts.isEmpty()) return null

            var vType: Int? = null
            var lineIdx: Int? = null
            var wordIdx: Int? = null
            var sylIdx: Int? = null

            for (part in parts) {
                when {
                    part.startsWith("V") -> vType = part.drop(1).toIntOrNull()
                    part.startsWith("L") -> lineIdx = part.drop(1).toIntOrNull()?.minus(1)
                    part.startsWith("W") -> wordIdx = part.drop(1).toIntOrNull()?.minus(1)
                    part.startsWith("S") -> sylIdx = part.drop(1).toIntOrNull()?.minus(1)
                }
            }

            vType ?: return null
            return HierarchicalIndex(vType, lineIdx, wordIdx, sylIdx)
        }
    }
}

/**
 * Word State - represents a word with syllable information.
 */
data class WordState(
    val text: String,
    val order: Float,
    val sylBounds: List<Int>,
    val hue: Int
) {
    /** Get syllables as SyllableState list */
    fun syllables(): List<SyllableState> {
        val syls = UniKeySyllable.toIpa(text)
        var offset = 0
        return syls.mapIndexed { idx, syl ->
            val start = offset
            offset += syl.original.length
            SyllableState(
                ipa = syl,
                order = (idx + 1).toFloat(),
                startOffset = start,
                endOffset = offset
            )
        }
    }

    companion object {
        /** Create WordState from text with auto-computed syllable info */
        fun fromText(text: String, order: Float = 1f): WordState {
            val syls = UniKeySyllable.toIpa(text)
            val hue = syls.lastOrNull()?.hue ?: 0
            val bounds = mutableListOf(0)
            var offset = 0
            syls.forEach { syl ->
                offset += syl.original.length
                bounds.add(offset)
            }
            return WordState(text, order, bounds, hue)
        }
    }
}

/**
 * Syllable State - represents a single syllable within a word.
 */
data class SyllableState(
    val ipa: UniKeySyllable,
    val order: Float,
    val startOffset: Int,
    val endOffset: Int
) {
    /** Original text of the syllable */
    val text: String get() = ipa.original

    /** Color hue (0-360) based on phonetic features */
    val hue: Int get() = ipa.hue
}
