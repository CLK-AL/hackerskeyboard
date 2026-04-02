# Plan: Extend Verse Indexing to Include Word and Syllable Levels

## Context

The current `VerseIndexState` in the UniKey KMP library supports only Verse (V) and Line (L) levels, producing formatted strings like "V1.L2". The user wants to extend this hierarchical indexing system to include Word (W) and Syllable (S) levels, enabling full notation like "V1.L2.W3.S4".

This extension will enable precise text positioning for:
- Syllable-level cursor navigation
- Fine-grained text selection and editing
- Word/syllable-aware operations in the poetry editor

## Implementation Approach

Create a new `HierarchicalIndex` data class that supports optional levels, maintaining backward compatibility with existing `VerseIndexState`.

### Files to Modify

1. **`/home/user/hackerskeyboard/unikey-kmp/src/commonMain/kotlin/al/clk/key/VerseIndex.kt`**
   - Add `HierarchicalIndex` data class with optional word/syllable indices
   - Add `WordState` data class (mirrors `LineState` pattern)
   - Add `SyllableState` data class
   - Extend `CursorState` with `sylIdx` property and `toIndex()` method

2. **`/home/user/hackerskeyboard/unikey-kmp/src/jsMain/kotlin/al/clk/key/UniKeyJs.kt`**
   - Add `createHierarchicalIndex()` export
   - Add `parseHierarchicalIndex()` export
   - Add `createWordState()` export
   - Add `cursorToIndex()` export

3. **`/home/user/hackerskeyboard/poetry.html`**
   - Add JavaScript wrapper functions for new APIs

## Detailed Changes

### 1. HierarchicalIndex (New Class)

```kotlin
data class HierarchicalIndex(
    val vType: Int,                    // V1, V2... (always required)
    val lineIdx: Int? = null,          // 0-based internal
    val wordIdx: Int? = null,          // 0-based internal
    val sylIdx: Int? = null,           // 0-based internal
    val lineOrder: Float = 0f,
    val wordOrder: Float = 0f,
    val sylOrder: Float = 0f
) {
    val formatted: String  // "V1", "V1.L2", "V1.L2.W3", "V1.L2.W3.S4"
    val depth: Int         // 1-4 based on how many levels specified
    
    fun toVerseIndexState(): VerseIndexState  // Backward compat
    
    companion object {
        fun parse(formatted: String): HierarchicalIndex?
        fun fromVerseIndexState(state: VerseIndexState): HierarchicalIndex
        fun insertOrder(before: Float, after: Float): Float
    }
}
```

### 2. WordState (New Class)

```kotlin
data class WordState(
    val text: String,
    val order: Float,
    val sylBounds: List<Int>,
    val hue: Int
) {
    fun syllables(): List<SyllableState>
    
    companion object {
        fun fromText(text: String, order: Float = 1f): WordState
    }
}
```

### 3. SyllableState (New Class)

```kotlin
data class SyllableState(
    val ipa: UniKeySyllable,
    val order: Float,
    val startOffset: Int,
    val endOffset: Int
) {
    val text: String
    val hue: Int
}
```

### 4. CursorState Extension

Add to existing `CursorState`:
- `sylIdx: Int` - computed property for current syllable index
- `toIndex(vType, lineIdx): HierarchicalIndex` - create full index from cursor

### 5. JS API Functions

| Function | Purpose |
|----------|---------|
| `createHierarchicalIndex(vType, lineIdx?, wordIdx?, sylIdx?, ...)` | Create new index |
| `parseHierarchicalIndex(formatted)` | Parse "V1.L2.W3.S4" string |
| `createWordState(text, order?)` | Create word with syllable info |
| `cursorToIndex(word, pos, wordIdx, vType, lineIdx)` | Cursor position to index |

## Backward Compatibility

- Keep `VerseIndexState` unchanged
- Keep existing `createVerseIndex()` JS API working
- New classes and APIs are additive

## Verification

1. Build the KMP library: `./gradlew :unikey-kmp:build`
2. Run existing tests: `./gradlew :unikey-kmp:jsTest`
3. Test new APIs:
   - `createHierarchicalIndex(1, 0, 2, 1).formatted` should return "V1.L1.W3.S2"
   - `parseHierarchicalIndex("V1.L2.W3.S4")` should return object with correct indices
   - `createWordState("hello").syllables` should return syllable array
4. Load `poetry.html` and verify existing functionality still works
