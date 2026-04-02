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
