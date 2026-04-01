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
