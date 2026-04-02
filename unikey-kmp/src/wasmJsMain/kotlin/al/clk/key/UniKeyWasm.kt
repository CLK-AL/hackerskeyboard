package al.clk.key

import al.clk.key.ui.*
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * WebAssembly/JS API for UniKey
 *
 * WASM interop uses primitive types and JSON strings.
 * Complex objects are returned as JSON for parsing in JS.
 */

// ═══════════════════════════════════════════════════════════════════════════════
// Core Keyboard Functions
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_getDisplay")
fun wasm_getDisplay(key: String, mode: String, shift: Boolean = false, alt: Boolean = false, ctrl: Boolean = false): String {
    val keyMode = when (mode) {
        "he" -> KeyMode.he
        "en" -> KeyMode.en
        "EN" -> KeyMode.EN
        else -> KeyMode.en
    }
    return UniKeys.getDisplay(key, keyMode, Modifiers(shift, alt, ctrl)) ?: ""
}

@JsExport
@JsName("wasm_getUkMode")
fun wasm_getUkMode(): String = UniKeyMode.current.name

@JsExport
@JsName("wasm_setUkMode")
fun wasm_setUkMode(mode: String): String {
    val keyMode = when (mode) {
        "he" -> KeyMode.he
        "en" -> KeyMode.en
        "EN" -> KeyMode.EN
        else -> return UniKeyMode.current.name
    }
    UniKeyMode.set(keyMode)
    return keyMode.name
}

@JsExport
@JsName("wasm_cycleUkMode")
fun wasm_cycleUkMode(): String = UniKeyMode.cycle().name

// ═══════════════════════════════════════════════════════════════════════════════
// IPA Color System
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_heIpa")
fun wasm_heIpa(word: String): String = IpaColor.heIpa(word)

@JsExport
@JsName("wasm_enIpa")
fun wasm_enIpa(word: String): String = IpaColor.enIpa(word)

@JsExport
@JsName("wasm_ipaHue")
fun wasm_ipaHue(ipa: String): Int = IpaColor.ipaHue(ipa)

@JsExport
@JsName("wasm_hsl")
fun wasm_hsl(hue: Int, saturation: Int, lightness: Int): String =
    IpaColor.hsl(hue, saturation, lightness)

@JsExport
@JsName("wasm_ipaEndColor")
fun wasm_ipaEndColor(ipa: String): String = IpaColor.ipaEndColor(ipa)

@JsExport
@JsName("wasm_lineEndIpa")
fun wasm_lineEndIpa(line: String, isHebrew: Boolean): String =
    IpaColor.lineEndIpa(line, isHebrew)

// ═══════════════════════════════════════════════════════════════════════════════
// Syllable System
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_detectScript")
fun wasm_detectScript(text: String): String = UniKeySyllable.detectScript(text).name

@JsExport
@JsName("wasm_wordHue")
fun wasm_wordHue(word: String, isHebrew: Boolean): Int =
    UniKeySyllable.wordHue(word, isHebrew)

@JsExport
@JsName("wasm_wordEndColor")
fun wasm_wordEndColor(word: String, isHebrew: Boolean): String =
    UniKeySyllable.wordEndColor(word, isHebrew)

@JsExport
@JsName("wasm_rhymeKey")
fun wasm_rhymeKey(word: String, isHebrew: Boolean, syllableCount: Int = 1): String =
    UniKeySyllable.rhymeKey(word, isHebrew, syllableCount)

@JsExport
@JsName("wasm_rhymes")
fun wasm_rhymes(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Boolean =
    UniKeySyllable.rhymes(word1, isHebrew1, word2, isHebrew2)

@JsExport
@JsName("wasm_rhymeDistance")
fun wasm_rhymeDistance(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Int =
    UniKeySyllable.rhymeDistance(word1, isHebrew1, word2, isHebrew2)

// ═══════════════════════════════════════════════════════════════════════════════
// Syllable Parsing (returns JSON)
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_toIpa")
fun wasm_toIpa(word: String): String {
    val syllables = UniKeySyllable.toIpa(word)
    return syllablesToJson(syllables)
}

@JsExport
@JsName("wasm_parseHebrewSyllables")
fun wasm_parseHebrewSyllables(word: String): String {
    val syllables = UniKeySyllable.parseHebrew(word)
    return syllablesToJson(syllables)
}

@JsExport
@JsName("wasm_parseEnglishSyllables")
fun wasm_parseEnglishSyllables(word: String): String {
    val syllables = UniKeySyllable.parseEnglish(word)
    return syllablesToJson(syllables)
}

@JsExport
@JsName("wasm_toIpaForLang")
fun wasm_toIpaForLang(word: String, langCode: String): String {
    val lang = Lang.fromCode(langCode) ?: return "[]"
    val syllables = UniKeySyllable.toIpaForLang(word, lang)
    return syllablesToJson(syllables)
}

private fun syllablesToJson(syllables: List<UniKeySyllable>): String {
    return "[" + syllables.joinToString(",") { syl ->
        """{"consonant":"${escapeJson(syl.consonant)}","vowel":"${escapeJson(syl.vowel)}","original":"${escapeJson(syl.original)}","hue":${syl.hue}}"""
    } + "]"
}

// ═══════════════════════════════════════════════════════════════════════════════
// Verse Index System
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_createVerseIndex")
fun wasm_createVerseIndex(vType: Int, lineIdx: Int, order: Float = 0f): String {
    val state = VerseIndexState(vType, lineIdx, order)
    return """{"vType":${state.vType},"lineIdx":${state.lineIdx},"order":${state.order},"formatted":"${state.formatted}"}"""
}

@JsExport
@JsName("wasm_insertLineOrder")
fun wasm_insertLineOrder(before: Float, after: Float): Float =
    VerseIndexState.insertOrder(before, after)

// ═══════════════════════════════════════════════════════════════════════════════
// Hierarchical Index (V.L.W.S) - returns JSON
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_createHierarchicalIndex")
fun wasm_createHierarchicalIndex(vType: Int, lineIdx: Int = -1, wordIdx: Int = -1, sylIdx: Int = -1): String {
    val line = if (lineIdx >= 0) lineIdx else null
    val word = if (wordIdx >= 0) wordIdx else null
    val syl = if (sylIdx >= 0) sylIdx else null
    val index = HierarchicalIndex(vType, line, word, syl)
    return """{"vType":${index.vType},"lineIdx":${index.lineIdx},"wordIdx":${index.wordIdx},"sylIdx":${index.sylIdx},"depth":${index.depth},"formatted":"${index.formatted}"}"""
}

@JsExport
@JsName("wasm_parseHierarchicalIndex")
fun wasm_parseHierarchicalIndex(formatted: String): String {
    val index = HierarchicalIndex.parse(formatted)
        ?: return """{"error":"Invalid format"}"""
    return """{"vType":${index.vType},"lineIdx":${index.lineIdx},"wordIdx":${index.wordIdx},"sylIdx":${index.sylIdx},"depth":${index.depth},"formatted":"${index.formatted}"}"""
}

@JsExport
@JsName("wasm_syllableBoundaries")
fun wasm_syllableBoundaries(word: String): String {
    val bounds = CursorState.syllableBoundaries(word)
    return "[" + bounds.joinToString(",") + "]"
}

@JsExport
@JsName("wasm_createCursorState")
fun wasm_createCursorState(word: String, pos: Int, wordIdx: Int = 0): String {
    val state = CursorState.forWord(word, pos, wordIdx)
    return """{"pos":${state.pos},"wordIdx":${state.wordIdx},"sylBounds":[${state.sylBounds.joinToString(",")}],"nextSylBound":${state.nextSylBound()},"prevSylBound":${state.prevSylBound()},"isAtBoundary":${state.isAtBoundary},"sylIdx":${state.sylIdx}}"""
}

// ═══════════════════════════════════════════════════════════════════════════════
// Language System (23 languages)
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_getLanguageCount")
fun wasm_getLanguageCount(): Int = KeyLanguage.COUNT

@JsExport
@JsName("wasm_getLanguageCodes")
fun wasm_getLanguageCodes(): String {
    return "[" + KeyLanguage.ALL.joinToString(",") { "\"${it.code}\"" } + "]"
}

@JsExport
@JsName("wasm_getKeyLanguages")
fun wasm_getKeyLanguages(): String {
    return "[" + KeyLanguage.ALL.joinToString(",") { lang ->
        """{"code":"${lang.code}","nativeName":"${escapeJson(lang.nativeName)}","englishName":"${lang.englishName}","direction":"${lang.direction.name}","script":"${lang.script.name}"}"""
    } + "]"
}

@JsExport
@JsName("wasm_getKeyLanguage")
fun wasm_getKeyLanguage(code: String): String {
    val lang = KeyLanguage.fromCode(code) ?: return """{"error":"Not found"}"""
    return """{"code":"${lang.code}","nativeName":"${escapeJson(lang.nativeName)}","englishName":"${lang.englishName}","direction":"${lang.direction.name}","script":"${lang.script.name}"}"""
}

@JsExport
@JsName("wasm_wordHueForLang")
fun wasm_wordHueForLang(word: String, langCode: String): Int {
    val lang = Lang.fromCode(langCode) ?: return 0
    return UniKeySyllable.wordHueForLang(word, lang)
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Pair System
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_isValidPair")
fun wasm_isValidPair(srcCode: String, tgtCode: String): Boolean {
    return TranslationPair.fromCodes(srcCode, tgtCode) != null
}

@JsExport
@JsName("wasm_getTranslationPair")
fun wasm_getTranslationPair(srcCode: String, tgtCode: String): String {
    val pair = TranslationPair.fromCodes(srcCode, tgtCode)
        ?: return """{"error":"Invalid pair"}"""
    return """{"pairId":"${pair.pairId}","srcCode":"${pair.srcCode}","tgtCode":"${pair.tgtCode}","srcNativeName":"${escapeJson(pair.source.nativeName)}","tgtNativeName":"${escapeJson(pair.target.nativeName)}"}"""
}

@JsExport
@JsName("wasm_generateAnalysisPrompt")
fun wasm_generateAnalysisPrompt(srcCode: String, tgtCode: String, context: String = ""): String {
    val pair = TranslationPair.fromCodes(srcCode, tgtCode) ?: return ""
    return pair.generateAnalysisPrompt(context)
}

@JsExport
@JsName("wasm_generatePathPrompt")
fun wasm_generatePathPrompt(srcCode: String, tgtCode: String, context: String = ""): String {
    val pair = TranslationPair.fromCodes(srcCode, tgtCode) ?: return ""
    return pair.generatePathPrompt(context)
}

// ═══════════════════════════════════════════════════════════════════════════════
// Translation Stages (6 Paths)
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_getStageCount")
fun wasm_getStageCount(): Int = 6

@JsExport
@JsName("wasm_getAllStages")
fun wasm_getAllStages(): String {
    return "[" + TranslationStage.ALL.joinToString(",") { stage ->
        """{"index":${stage.index},"label":"${stage.label}","shortLabel":"${stage.shortLabel}","description":"${escapeJson(stage.description)}","icon":"${stage.icon}","hue":${stage.hue},"color":"${stage.color}","bgColor":"${stage.bgColor}"}"""
    } + "]"
}

@JsExport
@JsName("wasm_getStageByIndex")
fun wasm_getStageByIndex(index: Int): String {
    val stage = TranslationStage.fromIndex(index) ?: return """{"error":"Invalid index"}"""
    return """{"index":${stage.index},"label":"${stage.label}","shortLabel":"${stage.shortLabel}","description":"${escapeJson(stage.description)}","icon":"${stage.icon}","hue":${stage.hue},"color":"${stage.color}"}"""
}

@JsExport
@JsName("wasm_getStagePrompt")
fun wasm_getStagePrompt(index: Int, srcCode: String, tgtCode: String): String {
    val stage = TranslationStage.fromIndex(index) ?: return ""
    val pair = TranslationPair.fromCodes(srcCode, tgtCode) ?: return ""
    val srcName = pair.source.englishName
    val tgtName = pair.target.englishName

    return when (stage) {
        TranslationStage.IPA_ECHO ->
            "Pick synonyms whose IPA ending most closely echoes the $srcName source IPA"
        TranslationStage.LITERAL_ANCHOR ->
            "Pick the most denotatively accurate $tgtName synonym"
        TranslationStage.CULTURAL_CHARGE ->
            "Pick the synonym with the strongest $srcName cultural/register charge"
        TranslationStage.EMOTIONAL_REGISTER ->
            "Pick synonyms matching the emotional intensity of the original"
        TranslationStage.TARGET_IDIOM ->
            "Most natural $tgtName, zero ${srcName}isms"
        TranslationStage.COMPRESSION ->
            "Shortest/hardest synonym; fewest syllables"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Word State
// ═══════════════════════════════════════════════════════════════════════════════

@JsExport
@JsName("wasm_createWordState")
fun wasm_createWordState(text: String, order: Float = 1f): String {
    val word = WordState.fromText(text, order)
    val syllablesJson = word.syllables().joinToString(",") { syl ->
        """{"text":"${escapeJson(syl.text)}","order":${syl.order},"startOffset":${syl.startOffset},"endOffset":${syl.endOffset},"hue":${syl.hue}}"""
    }
    return """{"text":"${escapeJson(word.text)}","order":${word.order},"sylBounds":[${word.sylBounds.joinToString(",")}],"hue":${word.hue},"syllables":[$syllablesJson]}"""
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper Functions
// ═══════════════════════════════════════════════════════════════════════════════

private fun escapeJson(s: String): String {
    return s.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
}
