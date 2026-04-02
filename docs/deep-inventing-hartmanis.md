# Plan: Consolidate Hardcoded Language Strings to Lang Enum

## Context

The codebase has a `Lang` enum in Currency.kt with all 24 language codes, but several files still use hardcoded string literals like `"he"`, `"en"`, `"ar"` instead of `Lang.HE`, `Lang.EN`, `Lang.AR`. This creates duplicate logic and maintenance issues.

**Goal**: Replace hardcoded strings with Lang enum values, making enums the single source of truth with `entries.associateBy { it.ipa }` pattern for lookups.

## Files to Update

| File | Impact | Changes |
|------|--------|---------|
| **IpaMatrix.kt** | HIGH | 50+ phoneme language lists, 3 function signatures |
| **KeyboardLayout.kt** | HIGH | 30+ layout definitions, accessor functions |
| **MorphoKey.kt** | MODERATE | 2 layouts, accessor function |
| **Currency.kt** | LOW | Region lists (may keep strings for locale codes) |

## Implementation

### 1. IpaMatrix.kt - Change language lists to use Lang enum

**Current:**
```kotlin
data class Consonant(
    val ipa: String,
    val languages: List<String>,  // ["ar", "de", "en", ...]
    ...
)

fun getConsonantsForLanguage(langCode: String): List<Consonant>
```

**Target:**
```kotlin
data class Consonant(
    val ipa: String,
    val languages: List<Lang>,  // [Lang.AR, Lang.DE, Lang.EN, ...]
    ...
)

fun getConsonantsForLanguage(lang: Lang): List<Consonant>
```

### 2. KeyboardLayout.kt - Add Lang property

**Current:**
```kotlin
data class KeyboardLayout(
    val code: String,  // "he"
    ...
)

val HE = KeyboardLayout(code = "he", ...)
```

**Target:**
```kotlin
data class KeyboardLayout(
    val lang: Lang,
    val code: String get() = lang.code,  // Derived from Lang
    ...
)

val HE = KeyboardLayout(lang = Lang.HE, ...)
```

### 3. MorphoKey.kt - Add Lang property

**Current:**
```kotlin
data class MorphoLayout(
    val code: String,  // "he"
    ...
)
```

**Target:**
```kotlin
data class MorphoLayout(
    val lang: Lang,
    val code: String get() = lang.code,
    ...
)
```

### 4. Update lookup functions

**Current:**
```kotlin
fun get(code: String): KeyboardLayout?
operator fun get(code: String): MorphoLayout?
```

**Target:**
```kotlin
fun get(lang: Lang): KeyboardLayout?
operator fun get(lang: Lang): MorphoLayout?

// Keep deprecated overload for compatibility
@Deprecated("Use get(Lang) instead")
fun get(code: String): KeyboardLayout? = Lang.fromCode(code)?.let { get(it) }
```

## Critical Files

- `/home/user/hackerskeyboard/unikey-kmp/src/commonMain/kotlin/al/clk/key/IpaMatrix.kt`
- `/home/user/hackerskeyboard/unikey-kmp/src/commonMain/kotlin/al/clk/key/KeyboardLayout.kt`
- `/home/user/hackerskeyboard/unikey-kmp/src/commonMain/kotlin/al/clk/key/MorphoKey.kt`
- `/home/user/hackerskeyboard/unikey-kmp/src/commonMain/kotlin/al/clk/key/Currency.kt` (Lang enum source)

## Implementation Order

1. Update `KeyboardLayout` to use `lang: Lang` property
2. Update `MorphoLayout` to use `lang: Lang` property  
3. Update `IpaMatrix` Consonant/Vowel to use `languages: List<Lang>`
4. Update all function signatures to take `Lang` instead of `String`
5. Update all call sites
6. Run tests and verify compilation

## Verification

1. `./gradlew :unikey-kmp:compileKotlinJs` - Compile check
2. Verify all lookups work with Lang enum
3. Search for remaining hardcoded strings: `grep -r '"he"\|"en"\|"ar"'`
