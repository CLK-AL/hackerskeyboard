# Bilingual Poetry Editor Architecture

## Overview

This is a bilingual poetry translation editor that uses IPA (International Phonetic Alphabet) as a universal phonetic hub to enable cross-language rhyme matching. 

**Supports 23 languages × 22 target languages = 506 translation pairs:**

| Script | Languages |
|--------|-----------|
| **Non-Latin** | Hebrew (עברית), Arabic (العربية), Russian (Русский), Greek (Ελληνικά), Hindi (हिन्दी), Japanese (日本語), Korean (한국어), Chinese (中文) |
| **Latin** | English, German, French, Spanish, Italian, Portuguese, Dutch, Polish, Turkish, Danish, Finnish, Norwegian, Swedish, Malay, Swahili |

The current POC demonstrates English↔Hebrew, but any pair works via the unified IPA layer.

## System Components

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           POETRY.HTML (Frontend)                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                   │
│  │ Verse Editor │  │ Rhyme Matrix │  │ Path Picker  │                   │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘                   │
│         │                 │                 │                            │
│         └─────────────────┼─────────────────┘                            │
│                           ▼                                              │
│         ┌─────────────────────────────────────┐                          │
│         │         UniKey JS API               │                          │
│         │  heIpa(), enIpa(), rhymeScheme()   │                          │
│         │  wordHue(), rhymeKey(), toIpa()    │                          │
│         └─────────────────┬───────────────────┘                          │
└───────────────────────────┼──────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                    UNIKEY-KMP (Kotlin Multiplatform)                     │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                   │
│  │   IpaColor   │  │ UniKeySyl    │  │  IpaMatrix   │                   │
│  │  heIpa()     │  │ parseHebrew  │  │ consonants   │                   │
│  │  enIpa()     │  │ parseEnglish │  │ vowels       │                   │
│  │  ipaHue()    │  │ detectScript │  │ phonemeHue   │                   │
│  └──────────────┘  └──────────────┘  └──────────────┘                   │
│                           │                                              │
│  ┌────────────────────────┼────────────────────────────────────────┐    │
│  │              Language-Specific Modules                           │    │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │    │
│  │  │ HebrewLetter │  │EnglishPattern│  │ GermanPattern│          │    │
│  │  │ NikudVowel   │  │SpanishPattern│  │ FrenchPattern│          │    │
│  │  │ HebrewPattern│  │ItalianPattern│  │  ...12 more  │          │    │
│  │  └──────────────┘  └──────────────┘  └──────────────┘          │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                           │                                              │
│  ┌────────────────────────┼────────────────────────────────────────┐    │
│  │              Script Parsers (23 Languages)                       │    │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐   │    │
│  │  │ Hebrew  │ │ Arabic  │ │ Greek   │ │ Cyrillic│ │ Hangul  │   │    │
│  │  │ Hiragana│ │Devanagari│ │  CJK   │ │  Latin  │ │  Thai   │   │    │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘   │    │
│  └─────────────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                       CLAUDE API (Translation Engine)                    │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │  Stage 1: analyzeAll()                                           │    │
│  │  - Input: Hebrew source stanzas                                  │    │
│  │  - Output: words[], pairs[], targets[] per stanza                │    │
│  │  - Contains: IPA, synonyms, rhyme pairs, cultural notes          │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │  Stage 2: buildPaths(vid)                                        │    │
│  │  - Input: Hebrew + Current English + Synonym Space               │    │
│  │  - Output: 6 alternative couplets per stanza                     │    │
│  │  - Paths: IPA Echo, Literal, Cultural, Emotional, Idiom, Compress│    │
│  └─────────────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────────┘
```

## Current en→he Flow

### 1. Source Text Input (Hebrew)
```javascript
var POEM = [
  { id: 1, label: "א׳", 
    he: ["בַּזֶּלֶת הַשְּׁחַרְחוֹרֶת,", "תּוֹהָה, לֹא מְדַבֶּרֶת,", ...],
    en: ["Darkened basalt spring,", "musing not uttering,", ...]
  },
  ...
];
```

### 2. IPA Conversion (UniKey KMP)
```kotlin
// Hebrew word → IPA
IpaColor.heIpa("בַּזֶּלֶת") → "bazelet"

// English word → IPA  
IpaColor.enIpa("basalt") → "lt"  // ending for rhyme matching
```

**Hebrew IPA Process:**
1. Parse Hebrew letters via `HebrewLetter` enum (consonant IPA)
2. Apply dagesh modifications via `DAGESH_IPA` map
3. Handle shin/sin dot markers
4. Map nikud (vowels) via `NikudVowel` enum

**English IPA Process:**
1. Match word ending against `EnglishPattern` enum
2. Simplify IPA to ASCII-friendly form for hashing
3. Fall back to last 3 characters if no pattern matches

### 3. Rhyme Color System
```kotlin
// IPA → Hue (0-360) for visual rhyme matching
IpaColor.ipaHue("bazelet") → 245  // blue-ish

// Similar IPA endings get similar hues
IpaColor.ipaHue("soeret")   → 247  // similar to bazelet
IpaColor.ipaHue("staging")  → 248  // matches raging
```

### 4. AI Analysis (Stage 1)
```javascript
// Claude API call with Hebrew linguist prompt
const S1SYS = "You are a Hebrew linguist and English poet...";

// Returns per stanza:
{
  "sid": 1,
  "words": [
    {
      "he": "בַּזֶּלֶת",
      "ipa": "bazelet", 
      "syns": [
        {"en": "basalt", "end": "lt", "s": true},  // s=sonic match
        {"en": "rock", "end": "ok", "s": false},
        ...
      ]
    }
  ],
  "pairs": [
    {"a": "raging", "b": "staging", "ipa": "IN", "hi": true}  // hi=Hebrew echo
  ],
  "targets": ["bazelet", "soeret", ...]  // Hebrew IPA targets
}
```

### 5. Path Generation (Stage 2)
```javascript
// 6 translation strategies:
const PATHS = {
  1: "IPA ECHO",       // Max sound fidelity to Hebrew
  2: "LITERAL ANCHOR", // Denotatively accurate
  3: "CULTURAL CHARGE",// Hebrew register/connotation
  4: "EMOTIONAL",      // Match poem's emotional arc
  5: "ENGLISH IDIOM",  // Natural English, zero Hebraisms
  6: "COMPRESSION"     // Shortest/hardest consonants
};
```

### 6. Visual Feedback
```javascript
// Word coloring by IPA ending
function wordEndColor(word) {
  const hue = UK.wordHueAuto(word);
  return `hsl(${hue}, 70%, 65%)`;
}

// Rhyme scheme detection
UK.rhymeScheme(["IN", "IN", "nd", "nt"]) 
  → [{ch:'A', ipa:'IN'}, {ch:'A', ipa:'IN'}, {ch:'B', ipa:'nd'}, {ch:'C', ipa:'nt'}]
```

## Key Data Structures

### Lang Enum (Currency.kt)
```kotlin
enum class Lang(val code: String, val script: Script) {
    HE("he", Script.HEBREW),
    EN("en", Script.LATIN),
    AR("ar", Script.ARABIC),
    // ... 23 languages total
}
```

### Script Enum
```kotlin
enum class Script {
    HEBREW, LATIN, ARABIC, GREEK, DEVANAGARI, 
    CYRILLIC, HANGUL, HIRAGANA, CJK, UNKNOWN
}
```

### ILayoutKey Interface
```kotlin
interface ILayoutKey {
    val char: String       // Display character
    val ipa: String        // IPA representation
    val displayName: String
    val shiftKey: ILayoutKey?
}
```

### UniKeySyllable
```kotlin
data class UniKeySyllable(
    val consonant: String,  // IPA consonant
    val vowel: String,      // IPA vowel
    val original: String,   // Original text
    val hue: Int           // Computed from phonetic features
)
```

## Adding a New Target Language

### Step 1: Add Lang Entry
```kotlin
// Currency.kt
enum class Lang {
    // ...
    NEW_LANG("xx", Script.SCRIPT_TYPE),
}
```

### Step 2: Create Letter/Pattern Enum (if unique script)
```kotlin
// NewLangLetter.kt
enum class NewLangLetter(
    val letter: Char,
    override val ipa: String,
    override val displayName: String
) : ILayoutKey {
    // Define all letters with IPA mappings
}
```

### Step 3: Add Spelling Patterns (Latin script)
```kotlin
// SpellingPatterns.kt
enum class NewLangPattern(
    override val pattern: String,
    override val ipa: String,
    ...
) : ISpellingPattern {
    // Pattern → IPA mappings
}
```

### Step 4: Add Syllable Parser
```kotlin
// UniKeySyllable.kt
fun parseNewLang(word: String): List<UniKeySyllable> {
    // Script-specific syllable parsing
}
```

### Step 5: Expose via JS API
```kotlin
// UniKeyJs.kt
@JsName("parseNewLangSyllables")
fun parseNewLangSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseNewLang(word).map { ... }.toTypedArray()
}
```

### Step 6: Update Frontend
```javascript
// poetry.html
function newLangIpa(word) { return UK.parseNewLangSyllables(word); }
```

## Multi-Language Refactoring Goals

### Current State (Hebrew-Specific)
- `heIpa()` hardcoded for Hebrew
- `enIpa()` hardcoded for English
- `lineEndIpa(line, isHebrew)` boolean flag
- AI prompts reference Hebrew-specific concepts

### Target State (Language-Agnostic)
- `toIpa(word, lang)` - unified IPA conversion
- `lineEndIpa(line, lang)` - Lang enum parameter
- `LanguageConfig` - per-language AI prompt templates
- `TranslationPair(src: Lang, tgt: Lang)` - language pair config

## Files to Modify for New Language Support

| File | Change |
|------|--------|
| `Currency.kt` | Add `Lang.XX` entry |
| `ScriptKeys.kt` | Add letter enum if new script |
| `SpellingPatterns.kt` | Add pattern enum if Latin script |
| `UniKeySyllable.kt` | Add `parseXX()` function |
| `IpaColor.kt` | Add `xxIpa()` function |
| `UniKeyJs.kt` | Expose JS bindings |
| `poetry.html` | Add language selector, update API calls |

## Build & Run

```bash
# Build KMP library to JS
cd unikey-kmp
./gradlew jsBrowserProductionWebpack

# Copy outputs to root
cp build/dist/js/productionExecutable/*.js ../

# Open poetry.html in browser
```
