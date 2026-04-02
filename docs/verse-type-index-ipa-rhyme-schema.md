# Implementation Plan: Verse Type Index with IPA Rhyme Schema

## Architecture: Common Logic + Compose UI Binding

```
┌─────────────────────────────────────────────────────────┐
│                    COMMON LOGIC                          │
│         (unikey-kmp/src/commonMain/kotlin)              │
│                                                          │
│  VerseIndexState    RhymeSchemeState    CursorState     │
│  - vType: Int       - scheme: List      - pos: Int      │
│  - lineOrder: Float - ipas: List        - wordIdx: Int  │
│  - lineIdx: String  - letters: List     - sylBounds: [] │
└──────────────────────┬──────────────────────────────────┘
                       │
        ┌──────────────┼──────────────┐
        ▼              ▼              ▼
┌───────────────┐ ┌───────────────┐ ┌───────────────┐
│  Android UI   │ │    Web UI     │ │   Desktop UI  │
│   (Compose)   │ │ (HTML/JS/KMP) │ │  (Compose)    │
│               │ │               │ │               │
│ VerseScreen() │ │ poetry.html   │ │ VerseScreen() │
│ LineRow()     │ │ render()      │ │ LineRow()     │
│ CursorView()  │ │ colorLine..() │ │ CursorView()  │
└───────────────┘ └───────────────┘ └───────────────┘
```

---

## Low-Level Design (Code Discovery)

### UniKey JS API (from UniKeyJs.kt)

```javascript
// Auto-detect script by UTF ranges, returns syllable array
toIpa(word) → [{consonant, vowel, original, hue}, ...]

// Script detection (first-match on UTF ranges)
detectScript(text) → "HEBREW" | "LATIN" | "ARABIC" | "GREEK" | ...

// Word hue (auto-detect)
wordHueAuto(word) → 0-360

// Rhyme scheme
rhymeScheme(ipas) → [{ch: "A", ipa: "IN", hue: 120}, ...]
```

**UTF Ranges for Script Detection:**
| Script | Range |
|--------|-------|
| HEBREW | 0x05D0-0x05EA (letters), 0x05B0-0x05C7 (nikud) |
| ARABIC | 0x0600-0x06FF |
| LATIN | 0x0041-0x005A (A-Z), 0x0061-0x007A (a-z) |

### Existing Cursor Pattern (poetry.html lines 814-868)

```javascript
function colorLineWithCursor(text, lang, pos, vi, li) {
  var segs = text.split(/(\s+)/);  // Split preserving whitespace
  var offset = 0;
  segs.forEach(function(seg) {
    var hasCursor = pos >= segStart && pos < segEnd;
    if (hasCursor) {
      var ci = pos - segStart;  // Char index within word
      var before = seg.slice(0, ci);
      var after = seg.slice(ci);
      // Render: [before_span] [cursor_span] [after_span]
    }
  });
}
```

### State Variables (line 250)
```javascript
var editing = null;      // "lang-verseId-lineIdx" or null
var editVal = "";        // Current text being edited
var editWordIdx = -1;    // Word index for cursor restoration
```

### _activeJump Cursor Router (line 263)
```javascript
var _activeJump = null;  // {lang, jump(charOff)} for optimized cursor moves
```

---

## Context

The user wants to enhance the poetry.html POC with a structured verse/line indexing system that includes:
- Verse type markers (V1, V2, V3...)
- Line indices with (Vn).(Ln) format  
- IPA-based rhyme schema display
- Float ordering for line insertion
- Enhanced hidden input cursor for pipe-separated syllable editing
- UniKey keyboard integration for real-time IPA feedback

The existing `poetry.html` is a bilingual poetry translation editor (Hebrew<->English) with:
- IPA conversion via UniKey KMP library (`heIpa()`, `enIpa()`, `ipaHue()`)
- Hidden input cursor pattern for inline editing
- Syllable parsing and rhyme color coding

---

## Implementation Summary

### 1. Enhanced Data Structure

**File:** `/home/user/hackerskeyboard/poetry.html` (lines 238-249)

Enhance the `verses` array structure:

```javascript
// Current: he/en arrays contain plain strings
// New: Convert to line objects with metadata

var verses = [{
  id: 1,
  label: "א׳",
  tag: "Verse",
  vType: 1,           // NEW: Verse Type Index (V1)
  bpmLo: 100,
  bpmHi: 140,
  he: [{              // ENHANCED: Line objects
    text: "line text",
    order: 1.0        // Float ordering (1.0, 1.5, 2.0...)
  }],
  en: [{
    text: "line text", 
    order: 1.0
  }]
}];
```

Add migration function to maintain backward compatibility with existing POEM data.

### 2. Verse Type Index Display (Vn)

**Add in line rendering (lines 1098-1200)**

Display `V1`, `V2`, `V3`... badges in verse headers:
- Add `.vtype-badge` CSS class
- Show in `.vhdr` section alongside Hebrew label
- Allow clicking to cycle verse types (Intro, Verse, Chorus, Bridge...)

### 3. Line Index with (Vn).(Ln) Format

**Enhance line rendering**

Add line index column showing `V1.L1`, `V1.L2`, etc.:
- New `.line-idx` CSS class (8px mono font)
- Position: leftmost column in `.ln .rw`
- Color: dim gray (#555)
- Format: `(Vn).(Ln)` clickable for navigation

**Low-Level Design:**

```javascript
// Render line index
function renderLineIdx(vi, li, verseType) {
  var idx = h("span", {className: "line-idx"}, 
    ["V" + verseType + ".L" + (li + 1)]);
  idx.style.color = "#666";
  idx.style.fontSize = "8px";
  idx.style.fontFamily = "'JetBrains Mono', monospace";
  idx.style.minWidth = "36px";
  return idx;
}
```

### 4. IPA Rhyme Schema Index

**Leverage existing `rhymeScheme()` function (line 115)**

Display AABB/ABAB pattern letters alongside line indices:
- Compute rhyme scheme per verse using `rhymeScheme(ipas)`
- Show colored letter (A, B, C...) next to line index
- Color matches IPA hue from `ipaHue()`
- Format: `V1.L1 [A]` where [A] is colored

**Low-Level Design:**

```javascript
// Compute rhyme scheme for verse
function computeVerseRhymeScheme(v) {
  var ipas = v.en.map(function(line) {
    return lineEndIpa(line, false);  // false = English
  });
  return rhymeScheme(ipas);  // [{ch:"A", ipa:"IN", hue:120}, ...]
}

// Render rhyme letter chip
function renderRhymeLetter(scheme, li) {
  var item = scheme[li];
  if (!item) return null;
  var chip = h("span", {className: "rhyme-letter"}, [item.ch]);
  chip.style.color = hl(item.hue, 75, 70);
  chip.style.background = hl(item.hue, 30, 12);
  chip.style.fontSize = "9px";
  chip.style.padding = "1px 4px";
  chip.style.borderRadius = "2px";
  return chip;
}
```

### 5. Float Ordering for Line Insertion

**Add ordering system**

- Each line has `order` float property (1.0, 2.0, 3.0...)
- Insert between lines: new order = (prev + next) / 2
- Add `+` button between lines for insertion
- Maintain stable sort on render

**Low-Level Design:**

```javascript
// Line object structure
// v.lines[li] = {order: 1.0, text: "...", ...}

// Insert line between li and li+1
function insertLineBetween(vi, li) {
  pushUndo();
  var v = verses[vi];
  var orderBefore = v.lines[li]?.order || (li + 1);
  var orderAfter = v.lines[li + 1]?.order || (li + 2);
  var newOrder = (orderBefore + orderAfter) / 2;
  
  // Insert empty line
  v.en.splice(li + 1, 0, "");
  v.he.splice(li + 1, 0, "");
  v.lines.splice(li + 1, 0, {order: newOrder});
  render();
}

// Render insert button between lines
function renderInsertBtn(vi, li) {
  return h("div", {
    className: "insert-btn",
    onClick: function() { insertLineBetween(vi, li); }
  }, ["+"]);
}
```

### 6. Enhanced Hidden Input Cursor

**Enhance existing pattern (lines 38-40, 803-868)**

Current `.edit-input` is 1px invisible overlay. Enhance:
- Better cursor positioning within pipe-separated syllables
- Cursor jumps to syllable boundaries on Tab
- Show cursor position relative to syllable segments

### 7. Pipe Cursor for Word Editing

**Pipe `|` as inline cursor within words**

The pipe character represents cursor position during word-level editing:
- `rag|ing` shows cursor between "rag" and "ing"
- Syllables auto-parsed by `toIpa(word)` - detects script by UTF ranges
- Each syllable segment colored by its IPA hue
- Cursor navigates between syllable boundaries

**Low-Level Design:**

```javascript
// Render word with cursor and syllable coloring
function renderWordWithCursor(word, cursorPos) {
  var syls = toIpa(word);  // [{consonant, vowel, original, hue}, ...]
  var frag = document.createDocumentFragment();
  var offset = 0;
  
  syls.forEach(function(syl) {
    var sylStart = offset;
    var sylEnd = offset + syl.original.length;
    var hasCursor = cursorPos >= sylStart && cursorPos < sylEnd;
    
    if (hasCursor) {
      var ci = cursorPos - sylStart;
      var before = syl.original.slice(0, ci);
      var after = syl.original.slice(ci);
      // [before_span color:hue] [cursor_span |] [after_span color:hue]
      appendColoredSpan(frag, before, syl.hue);
      appendCursor(frag);  // <span class="cursor">|</span>
      appendColoredSpan(frag, after, syl.hue);
    } else {
      appendColoredSpan(frag, syl.original, syl.hue);
    }
    offset = sylEnd;
  });
  return frag;
}

// Navigation: Tab jumps to next syllable boundary
function syllableBoundaries(word) {
  var syls = toIpa(word);
  var bounds = [0];
  var offset = 0;
  syls.forEach(function(syl) {
    offset += syl.original.length;
    bounds.push(offset);
  });
  return bounds;  // [0, 3, 6] for "rag|ing" = ["rag", "ing"]
}
```

### 8. UniKey Keyboard Integration

**Connect to existing API (lines 87-127)**

Unified functions with auto script detection by UTF ranges:
- `parseSyllables(word)` - syllable parsing, auto-detects script
- `toIpa(word)` - IPA conversion, auto-detects language
- `wordHue(word)` - color hue from IPA
- Real-time IPA chip preview near cursor during editing

---

## Common Logic (unikey-kmp/commonMain)

### New Data Classes

```kotlin
// VerseIndex state - shared logic
data class VerseIndexState(
    val vType: Int,           // V1, V2, V3...
    val lineIdx: Int,         // L1, L2, L3...
    val order: Float          // Float ordering (1.0, 1.5, 2.0)
) {
    val formatted: String get() = "V${vType}.L${lineIdx + 1}"
}

// Rhyme scheme state
data class RhymeSchemeState(
    val lines: List<RhymeLineState>
) {
    data class RhymeLineState(
        val letter: Char,     // A, B, C...
        val ipa: String,      // IPA ending
        val hue: Int          // 0-360
    )
}

// Cursor state for syllable-aware editing
data class CursorState(
    val pos: Int,             // Character position
    val wordIdx: Int,         // Word index in line
    val sylBounds: List<Int>  // Syllable boundaries [0, 3, 6]
) {
    fun nextSylBound(): Int = sylBounds.firstOrNull { it > pos } ?: pos
    fun prevSylBound(): Int = sylBounds.lastOrNull { it < pos } ?: pos
}
```

### Common Functions

```kotlin
// In UniKeySyllable.kt or new VerseIndex.kt

fun computeRhymeScheme(lines: List<String>, isHebrew: Boolean): RhymeSchemeState {
    val ipas = lines.map { lineEndIpa(it, isHebrew) }
    val scheme = rhymeScheme(ipas)
    return RhymeSchemeState(scheme.map { 
        RhymeSchemeState.RhymeLineState(it.ch, it.ipa, it.hue) 
    })
}

fun syllableBoundaries(word: String): List<Int> {
    val syls = toIpa(word)
    val bounds = mutableListOf(0)
    var offset = 0
    syls.forEach { syl ->
        offset += syl.original.length
        bounds.add(offset)
    }
    return bounds
}

fun insertLineOrder(before: Float, after: Float): Float = (before + after) / 2
```

---

## Per-Target UI Binding

### Android (Jetpack Compose)

```kotlin
// In app/src/main/kotlin/.../compose/

@Composable
fun VerseScreen(state: VerseState) {
    Column {
        state.lines.forEachIndexed { li, line ->
            LineRow(
                index = VerseIndexState(state.vType, li, line.order),
                rhyme = state.rhymeScheme.lines[li],
                text = line.text,
                cursor = state.cursorState.takeIf { it.lineIdx == li }
            )
        }
    }
}

@Composable
fun LineRow(index: VerseIndexState, rhyme: RhymeLineState, text: String, cursor: CursorState?) {
    Row {
        // V1.L1 index
        Text(index.formatted, style = indexStyle)
        // Rhyme letter chip
        RhymeChip(rhyme.letter, rhyme.hue)
        // Text with cursor
        if (cursor != null) {
            SyllableText(text, cursor)
        } else {
            ColoredText(text)
        }
    }
}
```

### Web (poetry.html - JS binding)

```javascript
// Bind to common logic via UniKey KMP JS exports

function renderLineWithState(vi, li, line, rhymeState, cursorState) {
    var idx = renderLineIdx(vi, li, verses[vi].vType);
    var rhyme = renderRhymeLetter(rhymeState, li);
    var text = cursorState 
        ? renderWordWithCursor(line.text, cursorState.pos)
        : colorLine(line.text, "en", vi, li);
    // Compose row: [idx] [rhyme] [text]
}
```

---

## Files to Modify

### Common Logic (unikey-kmp)
- **`unikey-kmp/src/commonMain/kotlin/al/clk/key/VerseIndex.kt`** - NEW: VerseIndexState, RhymeSchemeState, CursorState
- **`unikey-kmp/src/commonMain/kotlin/al/clk/key/UniKeySyllable.kt`** - Add `syllableBoundaries()`, `insertLineOrder()`
- **`unikey-kmp/src/jsMain/kotlin/al/clk/key/UniKeyJs.kt`** - Export new functions to JS

### Android Compose UI
- **`app/src/main/kotlin/.../compose/VerseScreen.kt`** - NEW: Compose UI binding
- **`app/src/main/kotlin/.../compose/LineRow.kt`** - NEW: Line with index, rhyme, cursor

### Web UI
1. **`/home/user/hackerskeyboard/poetry.html`** - Main implementation
   - CSS: Add `.vtype-badge`, `.line-idx`, `.rhyme-letter`, `.syl-segment` classes
   - JavaScript:
     - `migrateVersesFormat()` - Convert existing data
     - `renderLineIdx(vi, li)` - Format (Vn).(Ln)
     - `computeRhymeScheme(verse)` - Get rhyme letters for verse
     - `insertLineBetween(vi, li)` - Float ordering insert
     - `renderWordWithCursor(word, cursorPos)` - Render word with `|` cursor
     - `colorSyllableSegments(word)` - Use `parseSyllables()` + `toIpa()` for coloring
     - Enhance `colorLineWithCursor()` - Word-level cursor with syllable coloring
     - Enhance `editNav()` - Tab to syllable boundaries

---

## Implementation Steps

1. Add new CSS classes for verse/line indices and rhyme letters
2. Create migration function to convert POEM strings to line objects
3. Update render function to show (Vn).(Ln) indices
4. Compute and display rhyme scheme letters with colors
5. Implement float ordering with insert line buttons
6. Add word-level cursor display with pipe `|`
7. Auto-color syllable segments by IPA hue
8. Add real-time IPA preview during editing

---

## Implementation Sequence

### Phase 1: Common Logic (unikey-kmp/commonMain)
- Create `VerseIndexState` data class (vType, lineIdx, order, formatted)
- Create `RhymeSchemeState` data class (lines with letter, ipa, hue)
- Create `CursorState` data class (pos, wordIdx, sylBounds, nextSylBound/prevSylBound)
- Add `syllableBoundaries(word)` function
- Add `insertLineOrder(before, after)` function
- Export to JS via UniKeyJs.kt

### Phase 2: Web UI Binding (poetry.html)
- Bind to common `VerseIndexState` for V1.L1 display
- Bind to `RhymeSchemeState` for colored letter chips
- Bind to `CursorState` for syllable-aware cursor
- Update `render()` to use common state objects

### Phase 3: Android Compose UI (app/)
- Create `VerseScreen()` composable
- Create `LineRow()` composable with index, rhyme chip, text
- Create `SyllableText()` composable for cursor rendering
- Create `RhymeChip()` composable with hue coloring

### Phase 4: Float Ordering (common + UI)
- Common: `insertLineOrder()` logic
- Web: `+` buttons between lines
- Android: Insert FAB or long-press menu

### Phase 5: Pipe Cursor (common + UI)
- Common: `CursorState.sylBounds` from `toIpa()`
- Common: `nextSylBound()` / `prevSylBound()` navigation
- Web: Enhanced `colorLineWithCursor()` with syllable coloring
- Android: `SyllableText()` composable with cursor

### Phase 6: Real-time IPA Preview
- Common: IPA computation during editing
- Web: IPA chip near cursor
- Android: IPA chip composable

---

## Verification

### Common Logic
1. **Unit tests:** `VerseIndexState.formatted` returns "V1.L1" format
2. **Unit tests:** `syllableBoundaries("raging")` returns correct bounds
3. **Unit tests:** `CursorState.nextSylBound()` navigation works

### Web UI (poetry.html)
4. **Visual:** V1.L1, V1.L2... indices appear in line rows
5. **Rhyme:** AABB/ABAB letters colored by IPA hue
6. **Insert:** `+` between lines creates fractional order
7. **Cursor:** Pipe `|` shows within word during editing
8. **Syllables:** Auto-parsed syllables colored by hue

### Android Compose
9. **Visual:** VerseScreen renders with LineRow components
10. **Rhyme:** RhymeChip shows colored letters
11. **Cursor:** SyllableText renders cursor within word
