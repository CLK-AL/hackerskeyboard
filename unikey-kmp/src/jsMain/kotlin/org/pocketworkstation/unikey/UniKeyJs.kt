@file:JsExport
package org.pocketworkstation.unikey

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
fun getNikudForIpa(ipa: String): Array<String> = Nikud.forIpa(ipa).map { it.mark }.toTypedArray()

@JsName("applyNikud")
fun applyNikud(letter: String, ipa: String, useMale: Boolean = false): String {
    return if (letter.length == 1) Nikud.apply(letter[0], ipa, useMale) else letter
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
fun isBgdkpt(letter: String): Boolean = letter.length == 1 && Bgdkpt.isBgdkpt(letter[0])

@JsName("getBgdkptSound")
fun getBgdkptSound(letter: String, hasDagesh: Boolean, useClassical: Boolean = false): dynamic? {
    if (letter.length != 1) return null
    val bgdkpt = Bgdkpt.fromChar(letter[0]) ?: return null
    val sound = bgdkpt.getSound(hasDagesh, useClassical)
    val obj = js("{}")
    obj["letter"] = sound.letter
    obj["ipa"] = sound.ipa
    obj["en"] = sound.en
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

@JsName("wordHue")
fun wordHue(word: String, isHebrew: Boolean): Int = UniKeySyllable.wordHue(word, isHebrew)

@JsName("wordEndColor")
fun wordEndColor(word: String, isHebrew: Boolean): String = UniKeySyllable.wordEndColor(word, isHebrew)

@JsName("syllableHsl")
fun syllableHsl(hue: Int, saturation: Int = 70, lightness: Int = 65): String =
    UniKeySyllable.hsl(hue, saturation, lightness)

@JsName("rhymeKey")
fun rhymeKey(word: String, isHebrew: Boolean, syllableCount: Int = 1): String =
    UniKeySyllable.rhymeKey(word, isHebrew, syllableCount)

@JsName("rhymes")
fun rhymes(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Boolean =
    UniKeySyllable.rhymes(word1, isHebrew1, word2, isHebrew2)

@JsName("rhymeDistance")
fun rhymeDistance(word1: String, isHebrew1: Boolean, word2: String, isHebrew2: Boolean): Int =
    UniKeySyllable.rhymeDistance(word1, isHebrew1, word2, isHebrew2)
