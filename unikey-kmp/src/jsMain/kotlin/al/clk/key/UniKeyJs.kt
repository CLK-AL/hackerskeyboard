@file:JsExport
package al.clk.key

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * JavaScript API for UniKey
 * Exposes common logic to JS with JS-friendly names
 */

@JsName("getUniKey")
fun getUniKey(id: String): UniKey? = UniKeys.get(id)

@JsName("getUniKeyByHe")
fun getUniKeyByHe(he: String): UniKey? = UniKeys.getByHe(he)

@JsName("getUniKeyByEn")
fun getUniKeyByEn(en: String): UniKey? = UniKeys.getByEn(en)

@JsName("getDisplay")
fun getDisplay(key: String, mode: String, shift: Boolean = false, alt: Boolean = false, ctrl: Boolean = false): String? {
    val keyMode = when (mode) {
        "he" -> KeyMode.he
        "en" -> KeyMode.en
        "EN" -> KeyMode.EN
        else -> KeyMode.en
    }
    return UniKeys.getDisplay(key, keyMode, Modifiers(shift, alt, ctrl))
}

@JsName("getUkMode")
fun getUkMode(): String = UniKeyMode.current.name

@JsName("setUkMode")
fun setUkMode(mode: String) {
    val keyMode = when (mode) {
        "he" -> KeyMode.he
        "en" -> KeyMode.en
        "EN" -> KeyMode.EN
        else -> return
    }
    UniKeyMode.set(keyMode)
}

@JsName("cycleUkMode")
fun cycleUkMode(): String = UniKeyMode.cycle().name

@JsName("setModifier")
fun setModifier(mod: String, value: Boolean) {
    when (mod) {
        "shift" -> UniKeyModifiers.shift = value
        "alt" -> UniKeyModifiers.alt = value
        "ctrl" -> UniKeyModifiers.ctrl = value
    }
}

@JsName("getModifiers")
fun getModifiers(): dynamic {
    val obj = js("{}")
    obj["shift"] = UniKeyModifiers.shift
    obj["alt"] = UniKeyModifiers.alt
    obj["ctrl"] = UniKeyModifiers.ctrl
    return obj
}

@JsName("resetModifiers")
fun resetModifiers() = UniKeyModifiers.reset()

// Nikud helpers
@JsName("getNikudForIpa")
fun getNikudForIpa(ipa: String): Array<String> = NikudVowel.forIpa(ipa).map { it.mark }.toTypedArray()

@JsName("applyNikud")
fun applyNikud(letter: String, ipa: String, useMale: Boolean = false): String {
    return if (letter.length == 1) NikudVowel.apply(letter[0], ipa, useMale) else letter
}

// Hebrew letter helpers
@JsName("getHebrewLetterInfo")
fun getHebrewLetterInfo(letter: String): dynamic? {
    if (letter.length != 1) return null
    val heLetter = HebrewLetter.fromChar(letter[0]) ?: return null
    val obj = js("{}")
    obj["letter"] = heLetter.letter.toString()
    obj["ipa"] = heLetter.ipa
    obj["ipaDagesh"] = heLetter.ipaDagesh
    obj["name"] = heLetter.displayName
    obj["type"] = heLetter.type.name
    obj["en"] = heLetter.en
    obj["isGuttural"] = heLetter.isGuttural
    obj["isBgdkpt"] = heLetter.isBgdkpt
    obj["isFinal"] = heLetter.isFinalForm
    return obj
}

// BGDKPT helpers
@JsName("isBgdkpt")
fun isBgdkpt(letter: String): Boolean = letter.length == 1 && HebrewBgdkpt.isBgdkpt(letter[0])

@JsName("getBgdkptSound")
fun getBgdkptSound(letter: String, hasDagesh: Boolean, useClassical: Boolean = false): dynamic? {
    if (letter.length != 1) return null
    val bgdkpt = HebrewBgdkpt.fromChar(letter[0]) ?: return null
    val obj = js("{}")
    obj["letter"] = bgdkpt.getForm(hasDagesh)
    obj["ipa"] = bgdkpt.getIpa(hasDagesh, useClassical)
    obj["en"] = bgdkpt.getIpa(hasDagesh, useClassical)  // Use IPA as EN approximation
    return obj
}

// All keys export
@JsName("getAllKeys")
fun getAllKeys(): Array<String> = UniKeys.keys.keys.toTypedArray()

@JsName("getLetterKeys")
fun getLetterKeys(): Array<String> = UniKeys.letterKeys.map { it.id }.toTypedArray()

@JsName("getHebrewKeys")
fun getHebrewKeys(): Array<String> = UniKeys.hebrewKeys.map { it.id }.toTypedArray()

// IPA helpers
@JsName("getIpaVowelInfo")
fun getIpaVowelInfo(ipa: String): dynamic? {
    val vowel = IpaVowel.fromIpa(ipa) ?: return null
    val obj = js("{}")
    obj["ipa"] = vowel.ipa
    obj["name"] = vowel.displayName
    obj["heNikud"] = vowel.heNikud
    obj["heName"] = vowel.heName
    obj["enSpellings"] = vowel.enSpellings.toTypedArray()
    obj["quality"] = vowel.quality.name
    obj["warning"] = vowel.warning
    return obj
}

@JsName("getIpaConsonantInfo")
fun getIpaConsonantInfo(ipa: String): dynamic? {
    val consonant = IpaConsonant.fromIpa(ipa) ?: return null
    val obj = js("{}")
    obj["ipa"] = consonant.ipa
    obj["name"] = consonant.displayName
    obj["he"] = consonant.he
    obj["en"] = consonant.en
    obj["geresh"] = consonant.geresh
    obj["heUnique"] = consonant.heUnique
    obj["warning"] = consonant.warning
    return obj
}

// ═══ IPA Color System ═══

@JsName("heIpa")
fun heIpa(word: String): String = IpaColor.heIpa(word)

@JsName("enIpa")
fun enIpa(word: String): String = IpaColor.enIpa(word)

@JsName("lineEndIpa")
fun lineEndIpa(line: String, isHebrew: Boolean): String = IpaColor.lineEndIpa(line, isHebrew)

@JsName("ipaHue")
fun ipaHue(ipa: String): Int = IpaColor.ipaHue(ipa)

@JsName("hsl")
fun hsl(hue: Int, saturation: Int, lightness: Int): String = IpaColor.hsl(hue, saturation, lightness)

@JsName("ipaEndColor")
fun ipaEndColor(ipa: String): String = IpaColor.ipaEndColor(ipa)

@JsName("ipaMidColor")
fun ipaMidColor(ipa: String): String = IpaColor.ipaMidColor(ipa)

@JsName("rhymeScheme")
fun rhymeScheme(ipas: Array<String>): Array<dynamic> {
    return IpaColor.rhymeScheme(ipas.toList()).map { item ->
        val obj = js("{}")
        obj["ch"] = item.letter.toString()
        obj["ipa"] = item.ipa
        obj["hue"] = item.hue
        obj
    }.toTypedArray()
}

// ═══ UniKey Syllable System ═══

@JsName("detectScript")
fun detectScript(text: String): String = UniKeySyllable.detectScript(text).name

@JsName("toIpa")
fun toIpa(word: String): Array<dynamic> {
    return UniKeySyllable.toIpa(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseHebrewSyllables")
fun parseHebrewSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseHebrew(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseEnglishSyllables")
fun parseEnglishSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseEnglish(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

// ═══ Multilingual Parsers (Chatterbox 23 languages) ═══

@JsName("parseArabicSyllables")
fun parseArabicSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseArabic(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseGreekSyllables")
fun parseGreekSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseGreek(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseHindiSyllables")
fun parseHindiSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseDevanagari(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseRussianSyllables")
fun parseRussianSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseCyrillic(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseKoreanSyllables")
fun parseKoreanSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseHangul(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseJapaneseSyllables")
fun parseJapaneseSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseJapanese(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("parseChineseSyllables")
fun parseChineseSyllables(word: String): Array<dynamic> {
    return UniKeySyllable.parseCjk(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("wordHueAuto")
fun wordHueAuto(word: String): Int = UniKeySyllable.wordHue(word)

@JsName("wordHue")
fun wordHue(word: String, isHebrew: Boolean): Int = UniKeySyllable.wordHue(word, isHebrew)

@JsName("wordEndColorAuto")
fun wordEndColorAuto(word: String): String = UniKeySyllable.wordEndColor(word)

@JsName("wordEndColor")
fun wordEndColor(word: String, isHebrew: Boolean): String = UniKeySyllable.wordEndColor(word, isHebrew)

@JsName("syllableHsl")
fun syllableHsl(hue: Int, saturation: Int = 70, lightness: Int = 65): String =
    UniKeySyllable.hsl(hue, saturation, lightness)

@JsName("rhymeKeyAuto")
fun rhymeKeyAuto(word: String, syllableCount: Int = 1): String =
    UniKeySyllable.rhymeKey(word, syllableCount)

@JsName("rhymeKey")
fun rhymeKey(word: String, isHebrew: Boolean, syllableCount: Int = 1): String =
    UniKeySyllable.rhymeKey(word, isHebrew, syllableCount)

@JsName("rhymes")
fun rhymes(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Boolean =
    UniKeySyllable.rhymes(word1, isHebrew1, word2, isHebrew2)

@JsName("rhymeDistance")
fun rhymeDistance(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Int =
    UniKeySyllable.rhymeDistance(word1, isHebrew1, word2, isHebrew2)

// ═══ Keyboard Layouts (23 languages) ═══

@JsName("getSupportedLanguages")
fun getSupportedLanguages(): Array<String> = KeyboardLayouts.supportedLanguages.toTypedArray()

@JsName("getLayout")
fun getLayout(langCode: String): dynamic? {
    val layout = KeyboardLayouts.get(langCode) ?: return null
    val obj = js("{}")
    obj["code"] = layout.code
    obj["name"] = layout.name
    obj["nativeName"] = layout.nativeName
    obj["script"] = layout.script.name
    obj["keys"] = layoutKeysToDynamic(layout.keys)
    return obj
}

private fun layoutKeysToDynamic(keys: Map<String, ILayoutKey>): dynamic {
    val obj = js("{}")
    keys.forEach { (keyId, key) ->
        val keyObj = js("{}")
        keyObj["char"] = key.char
        keyObj["shift"] = key.shiftKey?.char
        keyObj["ipa"] = key.ipa
        keyObj["name"] = key.displayName
        obj[keyId] = keyObj
    }
    return obj
}

@JsName("getLayoutKey")
fun getLayoutKey(langCode: String, keyId: String): dynamic? {
    val layout = KeyboardLayouts.get(langCode) ?: return null
    val key = layout.keys[keyId] ?: return null
    val obj = js("{}")
    obj["char"] = key.char
    obj["shift"] = key.shiftKey?.char
    obj["ipa"] = key.ipa
    obj["name"] = key.displayName
    return obj
}

// ═══ IPA Matrix ═══

@JsName("getIpaConsonant")
fun getIpaConsonant(ipa: String): dynamic? {
    val cons = IpaMatrix.getConsonant(ipa) ?: return null
    val obj = js("{}")
    obj["ipa"] = cons.ipa
    obj["name"] = cons.name
    obj["place"] = cons.place.name
    obj["manner"] = cons.manner.name
    obj["voiced"] = cons.voiced
    obj["aspirated"] = cons.aspirated
    obj["breathy"] = cons.breathy
    obj["emphatic"] = cons.emphatic
    obj["hue"] = cons.hue
    obj["languages"] = cons.languages.toTypedArray()
    return obj
}

@JsName("getIpaVowelFull")
fun getIpaVowelFull(ipa: String): dynamic? {
    val vowel = IpaMatrix.getVowel(ipa) ?: return null
    val obj = js("{}")
    obj["ipa"] = vowel.ipa
    obj["name"] = vowel.name
    obj["height"] = vowel.height.name
    obj["backness"] = vowel.backness.name
    obj["rounded"] = vowel.rounded
    obj["long"] = vowel.long
    obj["nasal"] = vowel.nasal
    obj["hue"] = vowel.hue
    obj["languages"] = vowel.languages.toTypedArray()
    return obj
}

@JsName("getConsonantsForLanguage")
fun getConsonantsForLanguage(langCode: String): Array<dynamic> {
    val lang = Lang.fromCode(langCode) ?: return emptyArray()
    return IpaMatrix.getConsonantsForLanguage(lang).map { cons ->
        val obj = js("{}")
        obj["ipa"] = cons.ipa
        obj["name"] = cons.name
        obj["place"] = cons.place.name
        obj["manner"] = cons.manner.name
        obj["voiced"] = cons.voiced
        obj["hue"] = cons.hue
        obj
    }.toTypedArray()
}

@JsName("getVowelsForLanguage")
fun getVowelsForLanguage(langCode: String): Array<dynamic> {
    val lang = Lang.fromCode(langCode) ?: return emptyArray()
    return IpaMatrix.getVowelsForLanguage(lang).map { vowel ->
        val obj = js("{}")
        obj["ipa"] = vowel.ipa
        obj["name"] = vowel.name
        obj["height"] = vowel.height.name
        obj["backness"] = vowel.backness.name
        obj["rounded"] = vowel.rounded
        obj["hue"] = vowel.hue
        obj
    }.toTypedArray()
}

@JsName("getPhonemeHue")
fun getPhonemeHue(ipa: String): Int = IpaMatrix.getPhonemeHue(ipa)

@JsName("getAllConsonants")
fun getAllConsonants(): Array<dynamic> {
    return IpaMatrix.consonants.map { cons ->
        val obj = js("{}")
        obj["ipa"] = cons.ipa
        obj["name"] = cons.name
        obj["place"] = cons.place.name
        obj["manner"] = cons.manner.name
        obj["voiced"] = cons.voiced
        obj["hue"] = cons.hue
        obj
    }.toTypedArray()
}

@JsName("getAllVowels")
fun getAllVowels(): Array<dynamic> {
    return IpaMatrix.vowels.map { vowel ->
        val obj = js("{}")
        obj["ipa"] = vowel.ipa
        obj["name"] = vowel.name
        obj["height"] = vowel.height.name
        obj["backness"] = vowel.backness.name
        obj["rounded"] = vowel.rounded
        obj["hue"] = vowel.hue
        obj
    }.toTypedArray()
}

// ═══ KeyLanguage System (23 Language Support) ═══

@JsName("getKeyLanguages")
fun getKeyLanguages(): Array<dynamic> {
    return KeyLanguage.ALL.map { lang ->
        val obj = js("{}")
        obj["code"] = lang.code
        obj["nativeName"] = lang.nativeName
        obj["englishName"] = lang.englishName
        obj["direction"] = lang.direction.name
        obj["script"] = lang.script.name
        obj
    }.toTypedArray()
}

@JsName("getLanguageCount")
fun getLanguageCount(): Int = KeyLanguage.COUNT

@JsName("getKeyLanguage")
fun getKeyLanguage(code: String): dynamic? {
    val lang = KeyLanguage.fromCode(code) ?: return null
    val obj = js("{}")
    obj["code"] = lang.code
    obj["nativeName"] = lang.nativeName
    obj["englishName"] = lang.englishName
    obj["direction"] = lang.direction.name
    obj["script"] = lang.script.name
    return obj
}

@JsName("toIpaForLang")
fun toIpaForLang(word: String, langCode: String): Array<dynamic> {
    val lang = Lang.fromCode(langCode) ?: return emptyArray()
    return UniKeySyllable.toIpaForLang(word, lang).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("wordHueForLang")
fun wordHueForLang(word: String, langCode: String): Int {
    val lang = Lang.fromCode(langCode) ?: return 0
    return UniKeySyllable.wordHueForLang(word, lang)
}

@JsName("wordEndColorForLang")
fun wordEndColorForLang(word: String, langCode: String): String {
    val lang = Lang.fromCode(langCode) ?: return "hsl(0, 0%, 50%)"
    return UniKeySyllable.wordEndColorForLang(word, lang)
}

@JsName("rhymeKeyForLang")
fun rhymeKeyForLang(word: String, langCode: String, syllableCount: Int = 1): String {
    val lang = Lang.fromCode(langCode) ?: return ""
    return UniKeySyllable.rhymeKeyForLang(word, lang, syllableCount)
}

@JsName("parseSyllablesForLang")
fun parseSyllablesForLang(word: String, langCode: String): Array<dynamic> {
    val keyLang = KeyLanguage.fromCode(langCode) ?: return emptyArray()
    return keyLang.parseSyllables(word).map { syl ->
        val obj = js("{}")
        obj["consonant"] = syl.consonant
        obj["vowel"] = syl.vowel
        obj["original"] = syl.original
        obj["hue"] = syl.hue
        obj
    }.toTypedArray()
}

@JsName("getLanguagePromptHints")
fun getLanguagePromptHints(langCode: String): dynamic? {
    val keyLang = KeyLanguage.fromCode(langCode) ?: return null
    val hints = keyLang.aiPromptHints
    val obj = js("{}")
    obj["linguistRole"] = hints.linguistRole
    obj["culturalNotes"] = hints.culturalNotes
    obj["rhymeNotes"] = hints.rhymeNotes
    obj["grammarNotes"] = hints.grammarNotes
    obj["genderNotes"] = hints.genderNotes
    obj["scriptNotes"] = hints.scriptNotes
    return obj
}

@JsName("generateAnalysisPrompt")
fun generateAnalysisPrompt(srcLangCode: String, tgtLangCode: String, context: String = ""): String {
    val pair = TranslationPair.fromCodes(srcLangCode, tgtLangCode) ?: return ""
    return pair.generateAnalysisPrompt(context)
}

@JsName("generatePathPrompt")
fun generatePathPrompt(srcLangCode: String, tgtLangCode: String, context: String = ""): String {
    val pair = TranslationPair.fromCodes(srcLangCode, tgtLangCode) ?: return ""
    return pair.generatePathPrompt(context)
}

@JsName("getTranslationPair")
fun getTranslationPair(srcLangCode: String, tgtLangCode: String): dynamic? {
    val pair = TranslationPair.fromCodes(srcLangCode, tgtLangCode) ?: return null
    val obj = js("{}")
    obj["pairId"] = pair.pairId
    obj["srcCode"] = pair.srcCode
    obj["tgtCode"] = pair.tgtCode
    obj["srcNativeName"] = pair.source.nativeName
    obj["tgtNativeName"] = pair.target.nativeName
    obj["srcEnglishName"] = pair.source.englishName
    obj["tgtEnglishName"] = pair.target.englishName
    obj["srcDirection"] = pair.source.direction.name
    obj["tgtDirection"] = pair.target.direction.name
    return obj
}

@JsName("getAllTranslationPairIds")
fun getAllTranslationPairIds(): Array<String> {
    return KeyLanguage.allPairs().map { it.pairId }.toTypedArray()
}
