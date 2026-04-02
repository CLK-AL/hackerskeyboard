# Language Orthographic Patterns

Each language has characteristic spelling variants and patterns that affect IPA conversion.
Hebrew's nikud system (male/haser/unpointed) provides the model for understanding these variants.

## Pattern Variant Model

| Variant | Hebrew Example | Description |
|---------|---------------|-------------|
| **Full** (Male) | שָׁלוֹם (shalom) | Canonical form with all diacritics/vowels |
| **Defective** (Haser) | שָׁלֹם | Reduced form without mater lectionis |
| **Unpointed** | שלום | Consonants only, no diacritics |

## 23 Languages - Orthographic Patterns

### Non-Latin Scripts (9 languages)

#### 1. Hebrew (HE) - עברית
**Interface**: `IHebrewPattern`
**Variants**: male (full nikud), haser (defective), unpointed (ktiv haser)
**Pattern enum**: `HebrewPattern`
**Key patterns**:
- Vowels: CHIRIK_MALE (ִי), CHOLAM_MALE (וֹ), SHURUK (וּ)
- Suffixes: MASC_PLURAL (ִים/-ים), FEM_PLURAL (וֹת/-ות)
- Prefixes: definite article הַ, conjunctions וְ/בְּ/לְ/מִ
**Truth center**: `HebrewLetter.entries.associateBy { it.ipa }`

#### 2. Arabic (AR) - العربية
**Interface**: `IArabicPattern`
**Variants**: with harakat (vowel marks), without harakat
**Pattern enum**: (pending)
**Key patterns**:
- Short vowels: fatha (a), kasra (i), damma (u)
- Long vowels: alif maddah (ā), ya' (ī), waw (ū)
- Tanwin: -an, -in, -un
- Sun/Moon letter assimilation (ال)
**Truth center**: `ArabicLetter.entries.associateBy { it.ipa }`

#### 3. Russian (RU) - Русский
**Interface**: `ISpellingPattern`
**Variants**: stressed/unstressed vowels (аканье/оканье)
**Pattern enum**: `RussianPattern` (28 patterns)
**Key patterns**:
- Vowel reduction: unstressed о→а, е→и
- Palatalization: soft sign ь
- Assimilation: voiced/voiceless consonants
**Truth center**: `CyrillicKey.entries.associateBy { it.ipa }`

#### 4. Greek (EL) - Ελληνικά
**Interface**: `ISpellingPattern`
**Variants**: polytonic (ancient), monotonic (modern)
**Pattern enum**: `GreekPattern` (26 patterns)
**Key patterns**:
- Diphthongs: αι→e, ει→i, οι→i, ου→u
- Initial vowels: often have rough breathing
- Final -ς in nominative singular
**Truth center**: `GreekKey.entries.associateBy { it.ipa }`

#### 5. Hindi (HI) - हिन्दी
**Interface**: `ISpellingPattern`
**Variants**: with matra (vowel signs), inherent schwa
**Pattern enum**: (pending)
**Key patterns**:
- Inherent 'a' vowel (schwa deletion rules)
- Matra vowels: ा (ā), ि (i), ी (ī), ु (u), ू (ū), etc.
- Consonant conjuncts (ligatures)
- Nukta (़) for borrowed sounds
**Truth center**: `DevanagariKey.entries.associateBy { it.ipa }`

#### 6. Japanese (JA) - 日本語
**Interface**: `ISpellingPattern`
**Variants**: hiragana, katakana, romaji
**Pattern enum**: (pending)
**Key patterns**:
- Mora-timed rhythm (not stress)
- Long vowels: ー or repeated vowel
- Gemination: っ (small tsu)
- Pitch accent (not encoded in writing)
**Truth center**: `HiraganaKey.entries.associateBy { it.ipa }`

#### 7. Korean (KO) - 한국어
**Interface**: `ISpellingPattern`
**Variants**: syllable block composition
**Pattern enum**: (pending)
**Key patterns**:
- Syllable blocks: initial + medial (+ final)
- Consonant assimilation at syllable boundaries
- Tensification rules
- Aspiration patterns
**Truth center**: `HangulInitial/Vowel/Final.entries.associateBy { it.ipa }`

#### 8. Chinese (ZH) - 中文
**Interface**: `ISpellingPattern`
**Variants**: pinyin with/without tones, simplified/traditional
**Pattern enum**: `PinyinPattern` (33 patterns)
**Key patterns**:
- Initials (声母): 23 consonants
- Finals (韵母): 37 vowel combinations
- Tones: 4 main + neutral (affects meaning, not rhyme)
- Rhyme matching by final only
**Truth center**: `PinyinFinal.entries.associateBy { it.ipa }`

### Latin Scripts (14 languages)

#### 9. English (EN)
**Pattern enum**: `EnglishPattern` (extensive - in ScriptKeys.kt)
**Key patterns**:
- OUGH: 6 different pronunciations
- Silent letters: k in know, w in write
- Vowel digraphs: ea, oo, ou, ow
- Suffixes: -tion, -sion, -ing, -ed
**Variant model**: spelling/pronunciation disconnect

#### 10. German (DE) - Deutsch
**Pattern enum**: `GermanPattern` (34 patterns)
**Key patterns**:
- Umlauts: ä, ö, ü (vs ae, oe, ue)
- Eszett: ß vs ss
- Final devoicing: Tag→[tak]
- ch: [ç] after front vowels, [x] after back
**Variant model**: Standard (Hochdeutsch) vs Swiss (no ß)

#### 11. French (FR) - Français
**Pattern enum**: `FrenchPattern` (31 patterns)
**Key patterns**:
- Nasal vowels: -an, -en, -in, -on, -un
- Silent final consonants (mostly)
- Liaison rules
- Accents: é, è, ê, ë (affect pronunciation)
**Variant model**: with/without liaisons marked

#### 12. Spanish (ES) - Español
**Pattern enum**: `SpanishPattern` (24 patterns)
**Key patterns**:
- ñ: palatalized n
- ll/y: yeísmo (merged in most dialects)
- c/z: θ (Castilian) vs s (Latin American)
- Stress marks: á, é, í, ó, ú
**Variant model**: Castilian vs Latin American

#### 13. Italian (IT) - Italiano
**Pattern enum**: `ItalianPattern` (31 patterns)
**Key patterns**:
- Open/closed e, o (è vs é, ò vs ó)
- Double consonants (phonemic)
- gn→[ɲ], gli→[ʎ], sc(i/e)→[ʃ]
- z: /ts/ or /dz/
**Variant model**: stress placement not always marked

#### 14. Portuguese (PT) - Português
**Pattern enum**: `PortuguesePattern` (26 patterns)
**Key patterns**:
- Nasal vowels: ã, õ, -m, -n
- nh→[ɲ], lh→[ʎ]
- ç: always [s]
- Accent marks for stress and vowel quality
**Variant model**: European vs Brazilian

#### 15. Dutch (NL) - Nederlands
**Pattern enum**: `DutchPattern` (21 patterns)
**Key patterns**:
- ij/ei (both [ɛi])
- g/ch: voiced [ɣ] vs voiceless [x]
- aa/ee/oo/uu: long vowels
- sch→[sx]
**Variant model**: Netherlandic vs Flemish

#### 16. Polish (PL) - Polski
**Pattern enum**: `PolishPattern` (23 patterns)
**Key patterns**:
- Nasal vowels: ą, ę
- sz/cz/rz/ż: postalveolar consonants
- ó=u, rz=ż (historical spelling)
- Palatalization: ś, ć, ź, ń
**Variant model**: consistent spelling rules

#### 17. Turkish (TR) - Türkçe
**Pattern enum**: `TurkishPattern` (21 patterns)
**Key patterns**:
- Vowel harmony (front/back, rounded/unrounded)
- Dotted/dotless i: i/ı, İ/I
- ğ: lengthens preceding vowel
- ş, ç: consistent pronunciation
**Variant model**: highly regular spelling

#### 18. Danish (DA) - Dansk
**Pattern enum**: `DanishPattern` (17 patterns)
**Key patterns**:
- Stød (glottal stop)
- å, æ, ø (ä→æ, ö→ø, aa→å)
- Soft d: [ð] between vowels
- Silent letters in clusters
**Variant model**: pre/post-1948 spelling reform

#### 19. Finnish (FI) - Suomi
**Pattern enum**: `FinnishPattern` (27 patterns)
**Key patterns**:
- Vowel harmony: a,o,u vs ä,ö,y
- Long vowels/consonants: aa, kk (phonemic)
- Consistent letter-sound correspondence
- No consonant clusters initially
**Variant model**: highly phonemic spelling

#### 20. Norwegian (NO) - Norsk
**Pattern enum**: `NorwegianPattern` (19 patterns)
**Key patterns**:
- Pitch accent (not written)
- æ, ø, å
- kj/sj: merged in some dialects
- Retroflex consonants: rt, rn, rl
**Variant model**: Bokmål vs Nynorsk

#### 21. Swedish (SV) - Svenska
**Pattern enum**: `SwedishPattern` (24 patterns)
**Key patterns**:
- Pitch accent (not written)
- å, ä, ö
- sj-sound: multiple spellings (sj, sk, stj, sch)
- Retroflex consonants: rt, rn, rs, rl
**Variant model**: Standard Swedish

#### 22. Malay (MS) - Bahasa Melayu
**Pattern enum**: `MalayPattern` (18 patterns)
**Key patterns**:
- Highly phonemic Latin spelling
- ng: velar nasal (word-initially too)
- ny: palatal nasal
- Minimal vowel reduction
- Doubled consonants rare
**Variant model**: Malaysian vs Indonesian spelling

#### 23. Swahili (SW) - Kiswahili
**Pattern enum**: `SwahiliPattern` (19 patterns)
**Key patterns**:
- Highly phonemic Latin spelling
- ng': velar nasal [ŋ] vs ng [ŋg]
- ny: palatal nasal [ɲ]
- Noun class prefixes affect agreement
- Consistent vowel quality (a, e, i, o, u)
**Variant model**: Standard Swahili

## Truth Center Architecture

```
XXXKey.entries.associateBy { it.ipa }  ← Primary truth source
          ↓
    LangPatterns.matchEnding(lang, word)  ← Centralized routing
          ↓
    XXXPattern.matchEnding(word)?.ipa  ← Pattern-specific logic
          ↓
    Lang.matchEndingIpa(word)  ← Public API
```

## Adding New Language Patterns

1. Create pattern enum implementing `ISpellingPattern`
2. Add to `LangPatterns.matchEnding()` switch
3. Add `hasSpellingPatterns()` entry
4. Document variant model in this file

For scripts with diacritic variants (like Hebrew nikud):
1. Create interface extending `ISpellingPattern` with variant properties
2. Implement `male`/`haser`/`unpointed` equivalents for your script
3. Add lookup methods: `fromMale()`, `fromHaser()`, `fromUnpointed()`
