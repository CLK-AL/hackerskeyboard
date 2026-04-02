(function (factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'));
  else {
    if (typeof globalThis['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'hackerskeyboard:unikey-kmp'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'hackerskeyboard:unikey-kmp'.");
    }
    globalThis['hackerskeyboard:unikey-kmp'] = factory(typeof globalThis['hackerskeyboard:unikey-kmp'] === 'undefined' ? {} : globalThis['hackerskeyboard:unikey-kmp'], globalThis['kotlin-kotlin-stdlib']);
  }
}(function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var imul = Math.imul;
  var collectionSizeOrDefault = kotlin_kotlin.$_$.r;
  var mapCapacity = kotlin_kotlin.$_$.f1;
  var coerceAtLeast = kotlin_kotlin.$_$.w2;
  var LinkedHashMap_init_$Create$ = kotlin_kotlin.$_$.d;
  var Unit_instance = kotlin_kotlin.$_$.o;
  var protoOf = kotlin_kotlin.$_$.t2;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.c;
  var initMetadataForCompanion = kotlin_kotlin.$_$.m2;
  var enumEntries = kotlin_kotlin.$_$.t1;
  var Enum = kotlin_kotlin.$_$.r3;
  var VOID = kotlin_kotlin.$_$.a;
  var initMetadataForClass = kotlin_kotlin.$_$.l2;
  var initMetadataForObject = kotlin_kotlin.$_$.o2;
  var initMetadataForInterface = kotlin_kotlin.$_$.n2;
  var toString = kotlin_kotlin.$_$.y3;
  var getStringHashCode = kotlin_kotlin.$_$.j2;
  var hashCode = kotlin_kotlin.$_$.k2;
  var equals = kotlin_kotlin.$_$.f2;
  var Char = kotlin_kotlin.$_$.p3;
  var LinkedHashMap_init_$Create$_0 = kotlin_kotlin.$_$.e;
  var charSequenceLength = kotlin_kotlin.$_$.d2;
  var THROW_CCE = kotlin_kotlin.$_$.t3;
  var isInterface = kotlin_kotlin.$_$.q2;
  var to = kotlin_kotlin.$_$.z3;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.i;
  var toString_0 = kotlin_kotlin.$_$.n;
  var listOf = kotlin_kotlin.$_$.e1;
  var listOf_0 = kotlin_kotlin.$_$.d1;
  var replace = kotlin_kotlin.$_$.g3;
  var Char__hashCode_impl_otmys = kotlin_kotlin.$_$.j;
  var ensureNotNull = kotlin_kotlin.$_$.v3;
  var Regex_init_$Create$ = kotlin_kotlin.$_$.g;
  var takeLast = kotlin_kotlin.$_$.m3;
  var StringBuilder_init_$Create$ = kotlin_kotlin.$_$.h;
  var charCodeAt = kotlin_kotlin.$_$.b2;
  var Char__toInt_impl_vasixd = kotlin_kotlin.$_$.m;
  var isCharSequence = kotlin_kotlin.$_$.p2;
  var trim = kotlin_kotlin.$_$.o3;
  var toString_1 = kotlin_kotlin.$_$.u2;
  var lastOrNull = kotlin_kotlin.$_$.b1;
  var Long = kotlin_kotlin.$_$.s3;
  var fromInt = kotlin_kotlin.$_$.w1;
  var multiply = kotlin_kotlin.$_$.y1;
  var bitwiseXor = kotlin_kotlin.$_$.v1;
  var bitwiseAnd = kotlin_kotlin.$_$.u1;
  var coerceAtMost = kotlin_kotlin.$_$.x2;
  var dropLast = kotlin_kotlin.$_$.b3;
  var modulo = kotlin_kotlin.$_$.x1;
  var toNumber = kotlin_kotlin.$_$.z1;
  var numberToInt = kotlin_kotlin.$_$.r2;
  var ArrayList_init_$Create$_0 = kotlin_kotlin.$_$.b;
  var charSequenceGet = kotlin_kotlin.$_$.c2;
  var lazy = kotlin_kotlin.$_$.w3;
  var KProperty1 = kotlin_kotlin.$_$.y2;
  var getPropertyCallableRef = kotlin_kotlin.$_$.i2;
  var getBooleanHashCode = kotlin_kotlin.$_$.g2;
  var emptyList = kotlin_kotlin.$_$.t;
  var KtMap = kotlin_kotlin.$_$.p;
  var objectCreate = kotlin_kotlin.$_$.s2;
  var emptyMap = kotlin_kotlin.$_$.u;
  var mapOf = kotlin_kotlin.$_$.g1;
  var Char__plus_impl_qi7pgj = kotlin_kotlin.$_$.l;
  var mapOf_0 = kotlin_kotlin.$_$.h1;
  var plus = kotlin_kotlin.$_$.j1;
  var sorted = kotlin_kotlin.$_$.n1;
  var toList = kotlin_kotlin.$_$.p1;
  var setOf = kotlin_kotlin.$_$.l1;
  var first = kotlin_kotlin.$_$.y;
  var replace_0 = kotlin_kotlin.$_$.h3;
  var take = kotlin_kotlin.$_$.n3;
  var firstOrNull = kotlin_kotlin.$_$.e3;
  var LinkedHashSet_init_$Create$ = kotlin_kotlin.$_$.f;
  var Triple = kotlin_kotlin.$_$.u3;
  var substring = kotlin_kotlin.$_$.k3;
  var FunctionAdapter = kotlin_kotlin.$_$.a2;
  var Comparator = kotlin_kotlin.$_$.q3;
  var compareValues = kotlin_kotlin.$_$.s1;
  var sortedWith = kotlin_kotlin.$_$.m1;
  var endsWith = kotlin_kotlin.$_$.d3;
  var joinToString = kotlin_kotlin.$_$.z;
  var addAll = kotlin_kotlin.$_$.q;
  var emptySet = kotlin_kotlin.$_$.v;
  var firstOrNull_0 = kotlin_kotlin.$_$.x;
  var noWhenBranchMatchedException = kotlin_kotlin.$_$.x3;
  var drop = kotlin_kotlin.$_$.c3;
  var startsWith = kotlin_kotlin.$_$.j3;
  var abs = kotlin_kotlin.$_$.v2;
  var contains = kotlin_kotlin.$_$.a3;
  var substring_0 = kotlin_kotlin.$_$.l3;
  var takeLast_0 = kotlin_kotlin.$_$.o1;
  var last = kotlin_kotlin.$_$.c1;
  var toMutableList = kotlin_kotlin.$_$.r1;
  var get_lastIndex = kotlin_kotlin.$_$.a1;
  var isLetter = kotlin_kotlin.$_$.f3;
  var getNumberHashCode = kotlin_kotlin.$_$.h2;
  var Char__inc_impl_6e1wmz = kotlin_kotlin.$_$.k;
  var mutableListOf = kotlin_kotlin.$_$.i1;
  var firstOrNull_1 = kotlin_kotlin.$_$.w;
  var split = kotlin_kotlin.$_$.i3;
  var removeAll = kotlin_kotlin.$_$.k1;
  var contains_0 = kotlin_kotlin.$_$.z2;
  var copyToArray = kotlin_kotlin.$_$.s;
  var toList_0 = kotlin_kotlin.$_$.q1;
  var defineProp = kotlin_kotlin.$_$.e2;
  //endregion
  //region block: pre-declaration
  initMetadataForCompanion(Companion);
  initMetadataForClass(Lang, 'Lang', VOID, Enum);
  initMetadataForObject(LangPatterns, 'LangPatterns');
  initMetadataForClass(Script, 'Script', VOID, Enum);
  function get_shiftKey() {
    return null;
  }
  initMetadataForInterface(ILayoutKey, 'ILayoutKey');
  initMetadataForClass(SimpleKey, 'SimpleKey', VOID, VOID, [ILayoutKey]);
  initMetadataForCompanion(Companion_0);
  initMetadataForClass(HebrewLetter, 'HebrewLetter', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(LetterType, 'LetterType', VOID, Enum);
  initMetadataForClass(IpaQuality, 'IpaQuality', VOID, Enum);
  initMetadataForCompanion(Companion_1);
  initMetadataForClass(IpaVowel, 'IpaVowel', VOID, Enum);
  initMetadataForCompanion(Companion_2);
  initMetadataForClass(IpaConsonant, 'IpaConsonant', VOID, Enum);
  initMetadataForClass(RhymeSchemeItem, 'RhymeSchemeItem');
  initMetadataForObject(IpaColor, 'IpaColor');
  initMetadataForClass(Place, 'Place', VOID, Enum);
  initMetadataForClass(Manner, 'Manner', VOID, Enum);
  initMetadataForClass(Consonant, 'Consonant');
  initMetadataForClass(Height, 'Height', VOID, Enum);
  initMetadataForClass(Backness, 'Backness', VOID, Enum);
  initMetadataForClass(Vowel, 'Vowel');
  initMetadataForObject(IpaMatrix, 'IpaMatrix');
  initMetadataForClass(KeyboardLayout, 'KeyboardLayout');
  initMetadataForCompanion(Companion_3);
  initMetadataForClass(LayoutKey, 'LayoutKey', VOID, VOID, [ILayoutKey]);
  initMetadataForClass(Modifier, 'Modifier', VOID, Enum);
  initMetadataForObject(KeyboardLayouts, 'KeyboardLayouts');
  initMetadataForCompanion(Companion_4);
  initMetadataForClass(NikudVowel, 'NikudVowel', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_5);
  initMetadataForClass(HebrewBgdkpt, 'HebrewBgdkpt', VOID, Enum);
  initMetadataForCompanion(Companion_6);
  initMetadataForClass(ArabicLetter, 'ArabicLetter', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_7);
  initMetadataForClass(Haraka, 'Haraka', VOID, Enum);
  initMetadataForCompanion(Companion_8);
  initMetadataForClass(ArabicKeySymbol, 'ArabicKeySymbol', VOID, Enum);
  initMetadataForCompanion(Companion_9);
  initMetadataForClass(GreekKey, 'GreekKey', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_10);
  initMetadataForClass(CyrillicKey, 'CyrillicKey', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_11);
  initMetadataForClass(HangulInitial, 'HangulInitial', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_12);
  initMetadataForClass(HangulMedial, 'HangulMedial', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_13);
  initMetadataForClass(HangulFinal, 'HangulFinal', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_14);
  initMetadataForClass(HiraganaKey, 'HiraganaKey', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_15);
  initMetadataForClass(DevanagariKey, 'DevanagariKey', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_16);
  initMetadataForClass(LatinKey, 'LatinKey', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_17);
  initMetadataForClass(EnglishPattern, 'EnglishPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(PatternPosition, 'PatternPosition', VOID, Enum);
  initMetadataForClass(sam$kotlin_Comparator$0_0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_18);
  initMetadataForClass(GermanPattern, 'GermanPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_1, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_19);
  initMetadataForClass(FrenchPattern, 'FrenchPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_2, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_20);
  initMetadataForClass(SpanishPattern, 'SpanishPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_3, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_21);
  initMetadataForClass(ItalianPattern, 'ItalianPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_4, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_22);
  initMetadataForClass(PortuguesePattern, 'PortuguesePattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_5, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_23);
  initMetadataForClass(DutchPattern, 'DutchPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_6, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_24);
  initMetadataForClass(PolishPattern, 'PolishPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_7, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_25);
  initMetadataForClass(TurkishPattern, 'TurkishPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_8, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_26);
  initMetadataForClass(SwedishPattern, 'SwedishPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_9, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_27);
  initMetadataForClass(NorwegianPattern, 'NorwegianPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_10, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_28);
  initMetadataForClass(DanishPattern, 'DanishPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_11, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_29);
  initMetadataForClass(FinnishPattern, 'FinnishPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_12, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_30);
  initMetadataForClass(GreekPattern, 'GreekPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_13, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_31);
  initMetadataForClass(RussianPattern, 'RussianPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_14, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_32);
  initMetadataForClass(PinyinPattern, 'PinyinPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_15, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_33);
  initMetadataForClass(SwahiliPattern, 'SwahiliPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForClass(sam$kotlin_Comparator$0_16, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForCompanion(Companion_34);
  initMetadataForClass(MalayPattern, 'MalayPattern', VOID, Enum, [ILayoutKey, Enum]);
  initMetadataForCompanion(Companion_35);
  initMetadataForClass(KeyLanguage, 'KeyLanguage');
  initMetadataForClass(TextDirection, 'TextDirection', VOID, Enum);
  initMetadataForCompanion(Companion_36);
  initMetadataForClass(LanguagePromptHints, 'LanguagePromptHints');
  initMetadataForCompanion(Companion_37);
  initMetadataForClass(TranslationPair, 'TranslationPair');
  initMetadataForCompanion(Companion_38);
  initMetadataForClass(UniKey, 'UniKey', VOID, VOID, [ILayoutKey]);
  initMetadataForClass(KeyProperty, 'KeyProperty', VOID, Enum);
  initMetadataForClass(KeyMode, 'KeyMode', VOID, Enum);
  initMetadataForClass(Modifiers, 'Modifiers', Modifiers);
  initMetadataForClass(ConsonantFeatures, 'ConsonantFeatures');
  initMetadataForClass(VowelResult, 'VowelResult');
  initMetadataForCompanion(Companion_39);
  initMetadataForClass(UniKeySyllable, 'UniKeySyllable');
  initMetadataForObject(UniKeys, 'UniKeys');
  initMetadataForObject(UniKeyMode, 'UniKeyMode');
  initMetadataForObject(UniKeyModifiers, 'UniKeyModifiers');
  initMetadataForCompanion(Companion_40);
  initMetadataForClass(VerseIndexState, 'VerseIndexState');
  initMetadataForClass(RhymeLineState, 'RhymeLineState');
  initMetadataForCompanion(Companion_41);
  initMetadataForClass(RhymeSchemeState, 'RhymeSchemeState');
  initMetadataForCompanion(Companion_42);
  initMetadataForClass(CursorState, 'CursorState');
  initMetadataForClass(LanguageSelectorManager, 'LanguageSelectorManager', LanguageSelectorManager);
  initMetadataForClass(TranslationPairManager, 'TranslationPairManager', TranslationPairManager);
  initMetadataForObject(LanguageUI, 'LanguageUI');
  initMetadataForCompanion(Companion_43);
  initMetadataForClass(LanguageSelectorState, 'LanguageSelectorState', LanguageSelectorState);
  initMetadataForCompanion(Companion_44);
  initMetadataForClass(LanguageItem, 'LanguageItem');
  initMetadataForCompanion(Companion_45);
  initMetadataForClass(TranslationPairSelectorState, 'TranslationPairSelectorState', TranslationPairSelectorState);
  initMetadataForCompanion(Companion_46);
  initMetadataForClass(TranslationPairItem, 'TranslationPairItem');
  initMetadataForClass(LanguageSelectorAction, 'LanguageSelectorAction');
  initMetadataForClass(SelectLanguage, 'SelectLanguage', VOID, LanguageSelectorAction);
  initMetadataForClass(UpdateFilter, 'UpdateFilter', VOID, LanguageSelectorAction);
  initMetadataForClass(ToggleGroupByScript, 'ToggleGroupByScript', VOID, LanguageSelectorAction);
  initMetadataForObject(ClearSelection, 'ClearSelection', VOID, LanguageSelectorAction);
  initMetadataForClass(TranslationPairAction, 'TranslationPairAction');
  initMetadataForClass(SelectSource, 'SelectSource', VOID, TranslationPairAction);
  initMetadataForClass(SelectTarget, 'SelectTarget', VOID, TranslationPairAction);
  initMetadataForClass(SelectPair, 'SelectPair', VOID, TranslationPairAction);
  initMetadataForObject(SwapLanguages, 'SwapLanguages', VOID, TranslationPairAction);
  initMetadataForObject(ClearSelection_0, 'ClearSelection', VOID, TranslationPairAction);
  initMetadataForObject(langUI, 'UniKeyUI');
  //endregion
  var Lang_HE_instance;
  var Lang_EN_instance;
  var Lang_AR_instance;
  var Lang_EL_instance;
  var Lang_RU_instance;
  var Lang_HI_instance;
  var Lang_JA_instance;
  var Lang_KO_instance;
  var Lang_ZH_instance;
  var Lang_DA_instance;
  var Lang_DE_instance;
  var Lang_ES_instance;
  var Lang_FI_instance;
  var Lang_FR_instance;
  var Lang_IT_instance;
  var Lang_MS_instance;
  var Lang_NL_instance;
  var Lang_NO_instance;
  var Lang_PL_instance;
  var Lang_PT_instance;
  var Lang_SV_instance;
  var Lang_SW_instance;
  var Lang_TR_instance;
  var Lang_EN_GB_instance;
  var Lang_EN_AU_instance;
  var Lang_EN_CA_instance;
  var Lang_EN_IN_instance;
  var Lang_PT_BR_instance;
  var Lang_ES_419_instance;
  function Companion() {
    Companion_instance = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.nb_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.qb_1 = destination;
  }
  protoOf(Companion).rb = function (code) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = code.toLowerCase();
    return this.qb_1.q1(tmp$ret$1);
  };
  protoOf(Companion).sb = function () {
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.tb()) {
        destination.g(element);
      }
    }
    return destination;
  };
  var Companion_instance;
  function Companion_getInstance() {
    Lang_initEntries();
    if (Companion_instance == null)
      new Companion();
    return Companion_instance;
  }
  function values() {
    return [Lang_HE_getInstance(), Lang_EN_getInstance(), Lang_AR_getInstance(), Lang_EL_getInstance(), Lang_RU_getInstance(), Lang_HI_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_ZH_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_IT_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_EN_GB_getInstance(), Lang_EN_AU_getInstance(), Lang_EN_CA_getInstance(), Lang_EN_IN_getInstance(), Lang_PT_BR_getInstance(), Lang_ES_419_getInstance()];
  }
  function get_entries() {
    if ($ENTRIES == null)
      $ENTRIES = enumEntries(values());
    return $ENTRIES;
  }
  var Lang_entriesInitialized;
  function Lang_initEntries() {
    if (Lang_entriesInitialized)
      return Unit_instance;
    Lang_entriesInitialized = true;
    Lang_HE_instance = new Lang('HE', 0, 'he', Script_HEBREW_getInstance());
    Lang_EN_instance = new Lang('EN', 1, 'en', Script_LATIN_getInstance());
    Lang_AR_instance = new Lang('AR', 2, 'ar', Script_ARABIC_getInstance());
    Lang_EL_instance = new Lang('EL', 3, 'el', Script_GREEK_getInstance());
    Lang_RU_instance = new Lang('RU', 4, 'ru', Script_CYRILLIC_getInstance());
    Lang_HI_instance = new Lang('HI', 5, 'hi', Script_DEVANAGARI_getInstance());
    Lang_JA_instance = new Lang('JA', 6, 'ja', Script_HIRAGANA_getInstance());
    Lang_KO_instance = new Lang('KO', 7, 'ko', Script_HANGUL_getInstance());
    Lang_ZH_instance = new Lang('ZH', 8, 'zh', Script_CJK_getInstance());
    Lang_DA_instance = new Lang('DA', 9, 'da', Script_LATIN_getInstance());
    Lang_DE_instance = new Lang('DE', 10, 'de', Script_LATIN_getInstance());
    Lang_ES_instance = new Lang('ES', 11, 'es', Script_LATIN_getInstance());
    Lang_FI_instance = new Lang('FI', 12, 'fi', Script_LATIN_getInstance());
    Lang_FR_instance = new Lang('FR', 13, 'fr', Script_LATIN_getInstance());
    Lang_IT_instance = new Lang('IT', 14, 'it', Script_LATIN_getInstance());
    Lang_MS_instance = new Lang('MS', 15, 'ms', Script_LATIN_getInstance());
    Lang_NL_instance = new Lang('NL', 16, 'nl', Script_LATIN_getInstance());
    Lang_NO_instance = new Lang('NO', 17, 'no', Script_LATIN_getInstance());
    Lang_PL_instance = new Lang('PL', 18, 'pl', Script_LATIN_getInstance());
    Lang_PT_instance = new Lang('PT', 19, 'pt', Script_LATIN_getInstance());
    Lang_SV_instance = new Lang('SV', 20, 'sv', Script_LATIN_getInstance());
    Lang_SW_instance = new Lang('SW', 21, 'sw', Script_LATIN_getInstance());
    Lang_TR_instance = new Lang('TR', 22, 'tr', Script_LATIN_getInstance());
    Lang_EN_GB_instance = new Lang('EN_GB', 23, 'en-gb', Script_LATIN_getInstance(), Lang_EN_getInstance());
    Lang_EN_AU_instance = new Lang('EN_AU', 24, 'en-au', Script_LATIN_getInstance(), Lang_EN_getInstance());
    Lang_EN_CA_instance = new Lang('EN_CA', 25, 'en-ca', Script_LATIN_getInstance(), Lang_EN_getInstance());
    Lang_EN_IN_instance = new Lang('EN_IN', 26, 'en-in', Script_LATIN_getInstance(), Lang_EN_getInstance());
    Lang_PT_BR_instance = new Lang('PT_BR', 27, 'pt-br', Script_LATIN_getInstance(), Lang_PT_getInstance());
    Lang_ES_419_instance = new Lang('ES_419', 28, 'es-419', Script_LATIN_getInstance(), Lang_ES_getInstance());
    Companion_getInstance();
  }
  var $ENTRIES;
  function Lang(name, ordinal, code, script, baseLang) {
    baseLang = baseLang === VOID ? null : baseLang;
    Enum.call(this, name, ordinal);
    this.nb_1 = code;
    this.ob_1 = script;
    this.pb_1 = baseLang;
  }
  protoOf(Lang).tb = function () {
    return !(this.pb_1 == null);
  };
  protoOf(Lang).ub = function (word) {
    return LangPatterns_instance.vb(this, word);
  };
  function LangPatterns() {
  }
  protoOf(LangPatterns).vb = function (lang, word) {
    var tmp0_elvis_lhs = lang.pb_1;
    var baseLang = tmp0_elvis_lhs == null ? lang : tmp0_elvis_lhs;
    var tmp;
    switch (baseLang.v1_1) {
      case 1:
        var tmp2_safe_receiver = Companion_getInstance_17().yb(word);
        tmp = tmp2_safe_receiver == null ? null : tmp2_safe_receiver.cc_1;
        break;
      case 10:
        var tmp3_safe_receiver = Companion_getInstance_18().yb(word);
        tmp = tmp3_safe_receiver == null ? null : tmp3_safe_receiver.lc_1;
        break;
      case 13:
        var tmp4_safe_receiver = Companion_getInstance_19().yb(word);
        tmp = tmp4_safe_receiver == null ? null : tmp4_safe_receiver.uc_1;
        break;
      case 11:
        var tmp5_safe_receiver = Companion_getInstance_20().yb(word);
        tmp = tmp5_safe_receiver == null ? null : tmp5_safe_receiver.dd_1;
        break;
      case 14:
        var tmp6_safe_receiver = Companion_getInstance_21().yb(word);
        tmp = tmp6_safe_receiver == null ? null : tmp6_safe_receiver.md_1;
        break;
      case 19:
        var tmp7_safe_receiver = Companion_getInstance_22().yb(word);
        tmp = tmp7_safe_receiver == null ? null : tmp7_safe_receiver.vd_1;
        break;
      case 16:
        var tmp8_safe_receiver = Companion_getInstance_23().yb(word);
        tmp = tmp8_safe_receiver == null ? null : tmp8_safe_receiver.ee_1;
        break;
      case 18:
        var tmp9_safe_receiver = Companion_getInstance_24().yb(word);
        tmp = tmp9_safe_receiver == null ? null : tmp9_safe_receiver.ne_1;
        break;
      case 22:
        var tmp10_safe_receiver = Companion_getInstance_25().yb(word);
        tmp = tmp10_safe_receiver == null ? null : tmp10_safe_receiver.we_1;
        break;
      case 9:
        var tmp11_safe_receiver = Companion_getInstance_28().yb(word);
        tmp = tmp11_safe_receiver == null ? null : tmp11_safe_receiver.ff_1;
        break;
      case 12:
        var tmp12_safe_receiver = Companion_getInstance_29().yb(word);
        tmp = tmp12_safe_receiver == null ? null : tmp12_safe_receiver.of_1;
        break;
      case 17:
        var tmp13_safe_receiver = Companion_getInstance_27().yb(word);
        tmp = tmp13_safe_receiver == null ? null : tmp13_safe_receiver.xf_1;
        break;
      case 20:
        var tmp14_safe_receiver = Companion_getInstance_26().yb(word);
        tmp = tmp14_safe_receiver == null ? null : tmp14_safe_receiver.gg_1;
        break;
      case 15:
        var tmp15_safe_receiver = Companion_getInstance_34().yb(word);
        tmp = tmp15_safe_receiver == null ? null : tmp15_safe_receiver.pg_1;
        break;
      case 21:
        var tmp16_safe_receiver = Companion_getInstance_33().yb(word);
        tmp = tmp16_safe_receiver == null ? null : tmp16_safe_receiver.yg_1;
        break;
      case 3:
        var tmp17_safe_receiver = Companion_getInstance_30().yb(word);
        tmp = tmp17_safe_receiver == null ? null : tmp17_safe_receiver.hh_1;
        break;
      case 4:
        var tmp18_safe_receiver = Companion_getInstance_31().yb(word);
        tmp = tmp18_safe_receiver == null ? null : tmp18_safe_receiver.qh_1;
        break;
      case 8:
        var tmp19_safe_receiver = Companion_getInstance_32().yb(word);
        tmp = tmp19_safe_receiver == null ? null : tmp19_safe_receiver.zh_1;
        break;
      case 0:
        tmp = null;
        break;
      case 2:
        tmp = null;
        break;
      case 5:
        tmp = null;
        break;
      case 6:
        tmp = null;
        break;
      case 7:
        tmp = null;
        break;
      default:
        tmp = null;
        break;
    }
    return tmp;
  };
  var LangPatterns_instance;
  function LangPatterns_getInstance() {
    return LangPatterns_instance;
  }
  var Script_HEBREW_instance;
  var Script_LATIN_instance;
  var Script_ARABIC_instance;
  var Script_GREEK_instance;
  var Script_DEVANAGARI_instance;
  var Script_CYRILLIC_instance;
  var Script_HANGUL_instance;
  var Script_HIRAGANA_instance;
  var Script_CJK_instance;
  var Script_UNKNOWN_instance;
  var Script_entriesInitialized;
  function Script_initEntries() {
    if (Script_entriesInitialized)
      return Unit_instance;
    Script_entriesInitialized = true;
    Script_HEBREW_instance = new Script('HEBREW', 0);
    Script_LATIN_instance = new Script('LATIN', 1);
    Script_ARABIC_instance = new Script('ARABIC', 2);
    Script_GREEK_instance = new Script('GREEK', 3);
    Script_DEVANAGARI_instance = new Script('DEVANAGARI', 4);
    Script_CYRILLIC_instance = new Script('CYRILLIC', 5);
    Script_HANGUL_instance = new Script('HANGUL', 6);
    Script_HIRAGANA_instance = new Script('HIRAGANA', 7);
    Script_CJK_instance = new Script('CJK', 8);
    Script_UNKNOWN_instance = new Script('UNKNOWN', 9);
  }
  function Script(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function ILayoutKey() {
  }
  function SimpleKey(char, ipa, displayName, shiftKey) {
    shiftKey = shiftKey === VOID ? null : shiftKey;
    this.hi_1 = char;
    this.ii_1 = ipa;
    this.ji_1 = displayName;
    this.ki_1 = shiftKey;
  }
  protoOf(SimpleKey).di = function () {
    return this.hi_1;
  };
  protoOf(SimpleKey).ei = function () {
    return this.ii_1;
  };
  protoOf(SimpleKey).fi = function () {
    return this.ji_1;
  };
  protoOf(SimpleKey).gi = function () {
    return this.ki_1;
  };
  protoOf(SimpleKey).toString = function () {
    return 'SimpleKey(char=' + this.hi_1 + ', ipa=' + this.ii_1 + ', displayName=' + this.ji_1 + ', shiftKey=' + toString(this.ki_1) + ')';
  };
  protoOf(SimpleKey).hashCode = function () {
    var result = getStringHashCode(this.hi_1);
    result = imul(result, 31) + getStringHashCode(this.ii_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.ji_1) | 0;
    result = imul(result, 31) + (this.ki_1 == null ? 0 : hashCode(this.ki_1)) | 0;
    return result;
  };
  protoOf(SimpleKey).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof SimpleKey))
      return false;
    if (!(this.hi_1 === other.hi_1))
      return false;
    if (!(this.ii_1 === other.ii_1))
      return false;
    if (!(this.ji_1 === other.ji_1))
      return false;
    if (!equals(this.ki_1, other.ki_1))
      return false;
    return true;
  };
  function Lang_HE_getInstance() {
    Lang_initEntries();
    return Lang_HE_instance;
  }
  function Lang_EN_getInstance() {
    Lang_initEntries();
    return Lang_EN_instance;
  }
  function Lang_AR_getInstance() {
    Lang_initEntries();
    return Lang_AR_instance;
  }
  function Lang_EL_getInstance() {
    Lang_initEntries();
    return Lang_EL_instance;
  }
  function Lang_RU_getInstance() {
    Lang_initEntries();
    return Lang_RU_instance;
  }
  function Lang_HI_getInstance() {
    Lang_initEntries();
    return Lang_HI_instance;
  }
  function Lang_JA_getInstance() {
    Lang_initEntries();
    return Lang_JA_instance;
  }
  function Lang_KO_getInstance() {
    Lang_initEntries();
    return Lang_KO_instance;
  }
  function Lang_ZH_getInstance() {
    Lang_initEntries();
    return Lang_ZH_instance;
  }
  function Lang_DA_getInstance() {
    Lang_initEntries();
    return Lang_DA_instance;
  }
  function Lang_DE_getInstance() {
    Lang_initEntries();
    return Lang_DE_instance;
  }
  function Lang_ES_getInstance() {
    Lang_initEntries();
    return Lang_ES_instance;
  }
  function Lang_FI_getInstance() {
    Lang_initEntries();
    return Lang_FI_instance;
  }
  function Lang_FR_getInstance() {
    Lang_initEntries();
    return Lang_FR_instance;
  }
  function Lang_IT_getInstance() {
    Lang_initEntries();
    return Lang_IT_instance;
  }
  function Lang_MS_getInstance() {
    Lang_initEntries();
    return Lang_MS_instance;
  }
  function Lang_NL_getInstance() {
    Lang_initEntries();
    return Lang_NL_instance;
  }
  function Lang_NO_getInstance() {
    Lang_initEntries();
    return Lang_NO_instance;
  }
  function Lang_PL_getInstance() {
    Lang_initEntries();
    return Lang_PL_instance;
  }
  function Lang_PT_getInstance() {
    Lang_initEntries();
    return Lang_PT_instance;
  }
  function Lang_SV_getInstance() {
    Lang_initEntries();
    return Lang_SV_instance;
  }
  function Lang_SW_getInstance() {
    Lang_initEntries();
    return Lang_SW_instance;
  }
  function Lang_TR_getInstance() {
    Lang_initEntries();
    return Lang_TR_instance;
  }
  function Lang_EN_GB_getInstance() {
    Lang_initEntries();
    return Lang_EN_GB_instance;
  }
  function Lang_EN_AU_getInstance() {
    Lang_initEntries();
    return Lang_EN_AU_instance;
  }
  function Lang_EN_CA_getInstance() {
    Lang_initEntries();
    return Lang_EN_CA_instance;
  }
  function Lang_EN_IN_getInstance() {
    Lang_initEntries();
    return Lang_EN_IN_instance;
  }
  function Lang_PT_BR_getInstance() {
    Lang_initEntries();
    return Lang_PT_BR_instance;
  }
  function Lang_ES_419_getInstance() {
    Lang_initEntries();
    return Lang_ES_419_instance;
  }
  function Script_HEBREW_getInstance() {
    Script_initEntries();
    return Script_HEBREW_instance;
  }
  function Script_LATIN_getInstance() {
    Script_initEntries();
    return Script_LATIN_instance;
  }
  function Script_ARABIC_getInstance() {
    Script_initEntries();
    return Script_ARABIC_instance;
  }
  function Script_GREEK_getInstance() {
    Script_initEntries();
    return Script_GREEK_instance;
  }
  function Script_DEVANAGARI_getInstance() {
    Script_initEntries();
    return Script_DEVANAGARI_instance;
  }
  function Script_CYRILLIC_getInstance() {
    Script_initEntries();
    return Script_CYRILLIC_instance;
  }
  function Script_HANGUL_getInstance() {
    Script_initEntries();
    return Script_HANGUL_instance;
  }
  function Script_HIRAGANA_getInstance() {
    Script_initEntries();
    return Script_HIRAGANA_instance;
  }
  function Script_CJK_getInstance() {
    Script_initEntries();
    return Script_CJK_instance;
  }
  function Script_UNKNOWN_getInstance() {
    Script_initEntries();
    return Script_UNKNOWN_instance;
  }
  var HebrewLetter_ALEF_instance;
  var HebrewLetter_BET_instance;
  var HebrewLetter_GIMEL_instance;
  var HebrewLetter_DALET_instance;
  var HebrewLetter_HE_instance;
  var HebrewLetter_VAV_instance;
  var HebrewLetter_ZAYIN_instance;
  var HebrewLetter_CHET_instance;
  var HebrewLetter_TET_instance;
  var HebrewLetter_YOD_instance;
  var HebrewLetter_KAF_instance;
  var HebrewLetter_LAMED_instance;
  var HebrewLetter_MEM_instance;
  var HebrewLetter_NUN_instance;
  var HebrewLetter_SAMECH_instance;
  var HebrewLetter_AYIN_instance;
  var HebrewLetter_PE_instance;
  var HebrewLetter_TSADI_instance;
  var HebrewLetter_QOF_instance;
  var HebrewLetter_RESH_instance;
  var HebrewLetter_SHIN_instance;
  var HebrewLetter_TAV_instance;
  var HebrewLetter_KAF_SOFIT_instance;
  var HebrewLetter_MEM_SOFIT_instance;
  var HebrewLetter_NUN_SOFIT_instance;
  var HebrewLetter_PE_SOFIT_instance;
  var HebrewLetter_TSADI_SOFIT_instance;
  function Companion_0() {
    Companion_instance_0 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_0();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = new Char(element.ni_1);
      destination.l3(tmp$ret$0, element);
    }
    tmp.yi_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = get_entries_0();
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$3 = element_0.xi_1;
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_0.zi_1 = destination_0;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_0();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_1 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_1 = tmp0.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var key = element_1.oi_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination_1.q1(key);
      var tmp_2;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination_1.l3(key, answer);
        tmp_2 = answer;
      } else {
        tmp_2 = value;
      }
      var list = tmp_2;
      list.g(element_1);
    }
    tmp_1.aj_1 = destination_1;
    var tmp_3 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_0 = get_entries_0();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_2 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_2 = tmp0_0.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      // Inline function 'kotlin.text.isNotEmpty' call
      var this_2 = element_2.xi_1;
      if (charSequenceLength(this_2) > 0) {
        destination_2.g(element_2);
      }
    }
    // Inline function 'kotlin.collections.associate' call
    var capacity_1 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(destination_2, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination_3 = LinkedHashMap_init_$Create$(capacity_1);
    var _iterator__ex2g4s_3 = destination_2.i();
    while (_iterator__ex2g4s_3.j()) {
      var element_3 = _iterator__ex2g4s_3.k();
      // Inline function 'kotlin.collections.plusAssign' call
      var pair = to(element_3.xi_1, isInterface(element_3, ILayoutKey) ? element_3 : THROW_CCE());
      destination_3.l3(pair.p9_1, pair.q9_1);
    }
    tmp_3.bj_1 = destination_3;
  }
  protoOf(Companion_0).cj = function (c) {
    return this.yi_1.q1(new Char(c));
  };
  var Companion_instance_0;
  function Companion_getInstance_0() {
    HebrewLetter_initEntries();
    if (Companion_instance_0 == null)
      new Companion_0();
    return Companion_instance_0;
  }
  function values_0() {
    return [HebrewLetter_ALEF_getInstance(), HebrewLetter_BET_getInstance(), HebrewLetter_GIMEL_getInstance(), HebrewLetter_DALET_getInstance(), HebrewLetter_HE_getInstance(), HebrewLetter_VAV_getInstance(), HebrewLetter_ZAYIN_getInstance(), HebrewLetter_CHET_getInstance(), HebrewLetter_TET_getInstance(), HebrewLetter_YOD_getInstance(), HebrewLetter_KAF_getInstance(), HebrewLetter_LAMED_getInstance(), HebrewLetter_MEM_getInstance(), HebrewLetter_NUN_getInstance(), HebrewLetter_SAMECH_getInstance(), HebrewLetter_AYIN_getInstance(), HebrewLetter_PE_getInstance(), HebrewLetter_TSADI_getInstance(), HebrewLetter_QOF_getInstance(), HebrewLetter_RESH_getInstance(), HebrewLetter_SHIN_getInstance(), HebrewLetter_TAV_getInstance(), HebrewLetter_KAF_SOFIT_getInstance(), HebrewLetter_MEM_SOFIT_getInstance(), HebrewLetter_NUN_SOFIT_getInstance(), HebrewLetter_PE_SOFIT_getInstance(), HebrewLetter_TSADI_SOFIT_getInstance()];
  }
  function get_entries_0() {
    if ($ENTRIES_0 == null)
      $ENTRIES_0 = enumEntries(values_0());
    return $ENTRIES_0;
  }
  var HebrewLetter_entriesInitialized;
  function HebrewLetter_initEntries() {
    if (HebrewLetter_entriesInitialized)
      return Unit_instance;
    HebrewLetter_entriesInitialized = true;
    HebrewLetter_ALEF_instance = new HebrewLetter('ALEF', 0, _Char___init__impl__6a9atx(1488), '\u0294', VOID, '\u05D0\u05B8\u05DC\u05B6\u05E3', LetterType_GUTTURAL_getInstance(), "'", VOID, VOID, true, VOID, 't');
    HebrewLetter_BET_instance = new HebrewLetter('BET', 1, _Char___init__impl__6a9atx(1489), 'v', 'b', '\u05D1\u05B5\u05BC\u05D9\u05EA', LetterType_BGDKPT_getInstance(), 'v', 'b', VOID, VOID, VOID, 'c');
    HebrewLetter_GIMEL_instance = new HebrewLetter('GIMEL', 2, _Char___init__impl__6a9atx(1490), 'g', VOID, '\u05D2\u05B4\u05BC\u05D9\u05DE\u05B6\u05DC', LetterType_BGDKPT_getInstance(), 'g', VOID, VOID, VOID, '\u0263', 'd');
    HebrewLetter_DALET_instance = new HebrewLetter('DALET', 3, _Char___init__impl__6a9atx(1491), 'd', VOID, '\u05D3\u05B8\u05BC\u05DC\u05B6\u05EA', LetterType_BGDKPT_getInstance(), 'd', VOID, VOID, VOID, '\xF0', 's');
    HebrewLetter_HE_instance = new HebrewLetter('HE', 4, _Char___init__impl__6a9atx(1492), 'h', VOID, '\u05D4\u05B5\u05D0', LetterType_GUTTURAL_getInstance(), 'h', VOID, VOID, true, VOID, 'v');
    HebrewLetter_VAV_instance = new HebrewLetter('VAV', 5, _Char___init__impl__6a9atx(1493), 'v', VOID, '\u05D5\u05B8\u05D5', LetterType_WEAK_getInstance(), 'v', VOID, VOID, VOID, VOID, 'u');
    HebrewLetter_ZAYIN_instance = new HebrewLetter('ZAYIN', 6, _Char___init__impl__6a9atx(1494), 'z', VOID, '\u05D6\u05B7\u05D9\u05B4\u05DF', VOID, 'z', VOID, VOID, VOID, VOID, 'z');
    HebrewLetter_CHET_instance = new HebrewLetter('CHET', 7, _Char___init__impl__6a9atx(1495), '\u0127', VOID, '\u05D7\u05B5\u05D9\u05EA', LetterType_GUTTURAL_getInstance(), "\u1E25'", VOID, VOID, VOID, VOID, 'j');
    HebrewLetter_TET_instance = new HebrewLetter('TET', 8, _Char___init__impl__6a9atx(1496), 't', VOID, '\u05D8\u05B5\u05D9\u05EA', LetterType_EMPHATIC_getInstance(), 't', VOID, VOID, VOID, VOID, 'y');
    HebrewLetter_YOD_instance = new HebrewLetter('YOD', 9, _Char___init__impl__6a9atx(1497), 'j', VOID, '\u05D9\u05D5\u05B9\u05D3', LetterType_WEAK_getInstance(), 'y', VOID, VOID, VOID, VOID, 'h');
    HebrewLetter_KAF_instance = new HebrewLetter('KAF', 10, _Char___init__impl__6a9atx(1499), 'x', 'k', '\u05DB\u05B7\u05BC\u05E3', LetterType_BGDKPT_getInstance(), "kh'", 'k', _Char___init__impl__6a9atx(1498), VOID, VOID, 'f');
    HebrewLetter_LAMED_instance = new HebrewLetter('LAMED', 11, _Char___init__impl__6a9atx(1500), 'l', VOID, '\u05DC\u05B8\u05DE\u05B6\u05D3', VOID, 'l', VOID, VOID, VOID, VOID, 'k');
    HebrewLetter_MEM_instance = new HebrewLetter('MEM', 12, _Char___init__impl__6a9atx(1502), 'm', VOID, '\u05DE\u05B5\u05DD', VOID, 'm', VOID, _Char___init__impl__6a9atx(1501), VOID, VOID, 'n');
    HebrewLetter_NUN_instance = new HebrewLetter('NUN', 13, _Char___init__impl__6a9atx(1504), 'n', VOID, '\u05E0\u05D5\u05BC\u05DF', VOID, 'n', VOID, _Char___init__impl__6a9atx(1503), VOID, VOID, 'b');
    HebrewLetter_SAMECH_instance = new HebrewLetter('SAMECH', 14, _Char___init__impl__6a9atx(1505), 's', VOID, '\u05E1\u05B8\u05DE\u05B6\u05DA', VOID, 's', VOID, VOID, VOID, VOID, 'x');
    HebrewLetter_AYIN_instance = new HebrewLetter('AYIN', 15, _Char___init__impl__6a9atx(1506), '\u0295', VOID, '\u05E2\u05B7\u05D9\u05B4\u05DF', LetterType_GUTTURAL_getInstance(), "'", VOID, VOID, VOID, VOID, 'g');
    HebrewLetter_PE_instance = new HebrewLetter('PE', 16, _Char___init__impl__6a9atx(1508), 'f', 'p', '\u05E4\u05B5\u05BC\u05D0', LetterType_BGDKPT_getInstance(), 'f', 'p', _Char___init__impl__6a9atx(1507), VOID, VOID, 'p');
    HebrewLetter_TSADI_instance = new HebrewLetter('TSADI', 17, _Char___init__impl__6a9atx(1510), 'ts', VOID, '\u05E6\u05B8\u05D3\u05B4\u05D9', LetterType_EMPHATIC_getInstance(), "ts'", VOID, _Char___init__impl__6a9atx(1509), VOID, VOID, 'm');
    HebrewLetter_QOF_instance = new HebrewLetter('QOF', 18, _Char___init__impl__6a9atx(1511), 'k', VOID, '\u05E7\u05D5\u05B9\u05E3', LetterType_EMPHATIC_getInstance(), "k'", VOID, VOID, VOID, VOID, 'e');
    HebrewLetter_RESH_instance = new HebrewLetter('RESH', 19, _Char___init__impl__6a9atx(1512), '\u0281', VOID, '\u05E8\u05B5\u05D9\u05E9\u05C1', LetterType_GUTTURAL_getInstance(), "r'", VOID, VOID, VOID, VOID, 'r');
    HebrewLetter_SHIN_instance = new HebrewLetter('SHIN', 20, _Char___init__impl__6a9atx(1513), '\u0283', VOID, '\u05E9\u05C1\u05B4\u05D9\u05DF/\u05E9\u05C2\u05B4\u05D9\u05DF', LetterType_SHIN_getInstance(), 'sh', VOID, VOID, VOID, VOID, 'a');
    HebrewLetter_TAV_instance = new HebrewLetter('TAV', 21, _Char___init__impl__6a9atx(1514), 't', VOID, '\u05EA\u05B8\u05BC\u05D5', LetterType_BGDKPT_getInstance(), 't', VOID, VOID, VOID, '\u03B8', ',');
    HebrewLetter_KAF_SOFIT_instance = new HebrewLetter('KAF_SOFIT', 22, _Char___init__impl__6a9atx(1498), 'x', VOID, '\u05DB\u05B7\u05BC\u05E3 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, "kh'", VOID, VOID, VOID, VOID, 'l');
    HebrewLetter_MEM_SOFIT_instance = new HebrewLetter('MEM_SOFIT', 23, _Char___init__impl__6a9atx(1501), 'm', VOID, '\u05DE\u05B5\u05DD \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, 'm', VOID, VOID, VOID, VOID, 'o');
    HebrewLetter_NUN_SOFIT_instance = new HebrewLetter('NUN_SOFIT', 24, _Char___init__impl__6a9atx(1503), 'n', VOID, '\u05E0\u05D5\u05BC\u05DF \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, 'n', VOID, VOID, VOID, VOID, 'i');
    HebrewLetter_PE_SOFIT_instance = new HebrewLetter('PE_SOFIT', 25, _Char___init__impl__6a9atx(1507), 'f', VOID, '\u05E4\u05B5\u05BC\u05D0 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, 'f', VOID, VOID, VOID, VOID, ';');
    HebrewLetter_TSADI_SOFIT_instance = new HebrewLetter('TSADI_SOFIT', 26, _Char___init__impl__6a9atx(1509), 'ts', VOID, '\u05E6\u05B8\u05D3\u05B4\u05D9 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, "ts'", VOID, VOID, VOID, VOID, '.');
    Companion_getInstance_0();
  }
  var $ENTRIES_0;
  function HebrewLetter(name, ordinal, letter, ipa, ipaDagesh, displayName, type, en, enDagesh, finalForm, silentEnd, classical, qwerty) {
    ipaDagesh = ipaDagesh === VOID ? null : ipaDagesh;
    type = type === VOID ? LetterType_REGULAR_getInstance() : type;
    enDagesh = enDagesh === VOID ? null : enDagesh;
    finalForm = finalForm === VOID ? null : finalForm;
    silentEnd = silentEnd === VOID ? false : silentEnd;
    classical = classical === VOID ? null : classical;
    qwerty = qwerty === VOID ? '' : qwerty;
    Enum.call(this, name, ordinal);
    this.ni_1 = letter;
    this.oi_1 = ipa;
    this.pi_1 = ipaDagesh;
    this.qi_1 = displayName;
    this.ri_1 = type;
    this.si_1 = en;
    this.ti_1 = enDagesh;
    this.ui_1 = finalForm;
    this.vi_1 = silentEnd;
    this.wi_1 = classical;
    this.xi_1 = qwerty;
  }
  protoOf(HebrewLetter).ei = function () {
    return this.oi_1;
  };
  protoOf(HebrewLetter).fi = function () {
    return this.qi_1;
  };
  protoOf(HebrewLetter).di = function () {
    return toString_0(this.ni_1);
  };
  protoOf(HebrewLetter).gi = function () {
    var tmp;
    if (!(this.pi_1 == null) && !(this.pi_1 === this.oi_1)) {
      tmp = new SimpleKey(toString_0(this.ni_1) + '\u05BC', this.pi_1, this.qi_1 + '-dagesh');
    } else {
      tmp = null;
    }
    return tmp;
  };
  protoOf(HebrewLetter).dj = function () {
    return listOf([HebrewLetter_KAF_SOFIT_getInstance(), HebrewLetter_MEM_SOFIT_getInstance(), HebrewLetter_NUN_SOFIT_getInstance(), HebrewLetter_PE_SOFIT_getInstance(), HebrewLetter_TSADI_SOFIT_getInstance()]).j1(this);
  };
  protoOf(HebrewLetter).ej = function () {
    return this.ri_1.equals(LetterType_GUTTURAL_getInstance());
  };
  protoOf(HebrewLetter).fj = function () {
    return this.ri_1.equals(LetterType_BGDKPT_getInstance());
  };
  protoOf(HebrewLetter).gj = function () {
    switch (this.oi_1) {
      case '\u0294':
      case '\u0295':
        return '';
      case '\u0283':
        return 'sh';
      case '\u0127':
        return 'x';
      case '\u0281':
        return 'r';
      default:
        return this.oi_1;
    }
  };
  protoOf(HebrewLetter).hj = function () {
    return this.pi_1;
  };
  var LetterType_REGULAR_instance;
  var LetterType_GUTTURAL_instance;
  var LetterType_BGDKPT_instance;
  var LetterType_WEAK_instance;
  var LetterType_EMPHATIC_instance;
  var LetterType_SHIN_instance;
  var LetterType_entriesInitialized;
  function LetterType_initEntries() {
    if (LetterType_entriesInitialized)
      return Unit_instance;
    LetterType_entriesInitialized = true;
    LetterType_REGULAR_instance = new LetterType('REGULAR', 0);
    LetterType_GUTTURAL_instance = new LetterType('GUTTURAL', 1);
    LetterType_BGDKPT_instance = new LetterType('BGDKPT', 2);
    LetterType_WEAK_instance = new LetterType('WEAK', 3);
    LetterType_EMPHATIC_instance = new LetterType('EMPHATIC', 4);
    LetterType_SHIN_instance = new LetterType('SHIN', 5);
  }
  function LetterType(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function HebrewLetter_ALEF_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_ALEF_instance;
  }
  function HebrewLetter_BET_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_BET_instance;
  }
  function HebrewLetter_GIMEL_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_GIMEL_instance;
  }
  function HebrewLetter_DALET_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_DALET_instance;
  }
  function HebrewLetter_HE_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_HE_instance;
  }
  function HebrewLetter_VAV_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_VAV_instance;
  }
  function HebrewLetter_ZAYIN_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_ZAYIN_instance;
  }
  function HebrewLetter_CHET_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_CHET_instance;
  }
  function HebrewLetter_TET_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_TET_instance;
  }
  function HebrewLetter_YOD_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_YOD_instance;
  }
  function HebrewLetter_KAF_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_KAF_instance;
  }
  function HebrewLetter_LAMED_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_LAMED_instance;
  }
  function HebrewLetter_MEM_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_MEM_instance;
  }
  function HebrewLetter_NUN_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_NUN_instance;
  }
  function HebrewLetter_SAMECH_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_SAMECH_instance;
  }
  function HebrewLetter_AYIN_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_AYIN_instance;
  }
  function HebrewLetter_PE_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_PE_instance;
  }
  function HebrewLetter_TSADI_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_TSADI_instance;
  }
  function HebrewLetter_QOF_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_QOF_instance;
  }
  function HebrewLetter_RESH_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_RESH_instance;
  }
  function HebrewLetter_SHIN_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_SHIN_instance;
  }
  function HebrewLetter_TAV_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_TAV_instance;
  }
  function HebrewLetter_KAF_SOFIT_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_KAF_SOFIT_instance;
  }
  function HebrewLetter_MEM_SOFIT_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_MEM_SOFIT_instance;
  }
  function HebrewLetter_NUN_SOFIT_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_NUN_SOFIT_instance;
  }
  function HebrewLetter_PE_SOFIT_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_PE_SOFIT_instance;
  }
  function HebrewLetter_TSADI_SOFIT_getInstance() {
    HebrewLetter_initEntries();
    return HebrewLetter_TSADI_SOFIT_instance;
  }
  function LetterType_REGULAR_getInstance() {
    LetterType_initEntries();
    return LetterType_REGULAR_instance;
  }
  function LetterType_GUTTURAL_getInstance() {
    LetterType_initEntries();
    return LetterType_GUTTURAL_instance;
  }
  function LetterType_BGDKPT_getInstance() {
    LetterType_initEntries();
    return LetterType_BGDKPT_instance;
  }
  function LetterType_WEAK_getInstance() {
    LetterType_initEntries();
    return LetterType_WEAK_instance;
  }
  function LetterType_EMPHATIC_getInstance() {
    LetterType_initEntries();
    return LetterType_EMPHATIC_instance;
  }
  function LetterType_SHIN_getInstance() {
    LetterType_initEntries();
    return LetterType_SHIN_instance;
  }
  var IpaQuality_EXACT_instance;
  var IpaQuality_NEAR_EXACT_instance;
  var IpaQuality_APPROX_instance;
  var IpaQuality_entriesInitialized;
  function IpaQuality_initEntries() {
    if (IpaQuality_entriesInitialized)
      return Unit_instance;
    IpaQuality_entriesInitialized = true;
    IpaQuality_EXACT_instance = new IpaQuality('EXACT', 0);
    IpaQuality_NEAR_EXACT_instance = new IpaQuality('NEAR_EXACT', 1);
    IpaQuality_APPROX_instance = new IpaQuality('APPROX', 2);
  }
  function IpaQuality(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  var IpaVowel_I_instance;
  var IpaVowel_E_instance;
  var IpaVowel_EPSILON_instance;
  var IpaVowel_SMALL_I_instance;
  var IpaVowel_ASH_instance;
  var IpaVowel_SCHWA_instance;
  var IpaVowel_U_instance;
  var IpaVowel_O_instance;
  var IpaVowel_A_instance;
  var IpaVowel_AI_instance;
  var IpaVowel_AU_instance;
  var IpaVowel_OI_instance;
  var IpaVowel_EI_instance;
  function Companion_1() {
    Companion_instance_1 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_1();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.kj_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.rj_1 = destination;
  }
  protoOf(Companion_1).sj = function (ipa) {
    return this.rj_1.q1(ipa);
  };
  var Companion_instance_1;
  function Companion_getInstance_1() {
    IpaVowel_initEntries();
    if (Companion_instance_1 == null)
      new Companion_1();
    return Companion_instance_1;
  }
  function values_1() {
    return [IpaVowel_I_getInstance(), IpaVowel_E_getInstance(), IpaVowel_EPSILON_getInstance(), IpaVowel_SMALL_I_getInstance(), IpaVowel_ASH_getInstance(), IpaVowel_SCHWA_getInstance(), IpaVowel_U_getInstance(), IpaVowel_O_getInstance(), IpaVowel_A_getInstance(), IpaVowel_AI_getInstance(), IpaVowel_AU_getInstance(), IpaVowel_OI_getInstance(), IpaVowel_EI_getInstance()];
  }
  function get_entries_1() {
    if ($ENTRIES_1 == null)
      $ENTRIES_1 = enumEntries(values_1());
    return $ENTRIES_1;
  }
  var IpaVowel_entriesInitialized;
  function IpaVowel_initEntries() {
    if (IpaVowel_entriesInitialized)
      return Unit_instance;
    IpaVowel_entriesInitialized = true;
    IpaVowel_I_instance = new IpaVowel('I', 0, 'i', 'close front', '\u05B4\u05D9', '\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7', listOf(['ee', 'ea', 'ie']), IpaQuality_EXACT_getInstance());
    IpaVowel_E_instance = new IpaVowel('E', 1, 'e', 'close-mid front', '\u05B5', '\u05E6\u05B5\u05D9\u05E8\u05B5\u05D9', listOf(['ay', 'ei', 'a_e']), IpaQuality_EXACT_getInstance());
    IpaVowel_EPSILON_instance = new IpaVowel('EPSILON', 2, '\u025B', 'open-mid front', '\u05B6', '\u05E1\u05B6\u05D2\u05D5\u05B9\u05DC', listOf_0('e'), IpaQuality_EXACT_getInstance());
    IpaVowel_SMALL_I_instance = new IpaVowel('SMALL_I', 3, '\u026A', 'near-close front', '\u05B4', '\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7 \u05E7\u05E6\u05E8', listOf_0('i'), IpaQuality_APPROX_getInstance());
    IpaVowel_ASH_instance = new IpaVowel('ASH', 4, '\xE6', 'near-open front', '\u05B6', '\u2014', listOf_0('a'), IpaQuality_APPROX_getInstance());
    IpaVowel_SCHWA_instance = new IpaVowel('SCHWA', 5, '\u0259', 'schwa', '\u05B0', '\u05E9\u05B0\u05C1\u05D5\u05B8\u05D0 \u05E0\u05B8\u05E2', listOf(['a', 'u']), IpaQuality_EXACT_getInstance());
    IpaVowel_U_instance = new IpaVowel('U', 6, 'u', 'close back', '\u05BB', '\u05E9\u05C1\u05D5\u05BC\u05E8\u05D5\u05BC\u05E7/\u05E7\u05BB\u05D1\u05BC\u05D5\u05BC\u05E5', listOf(['oo', 'u']), IpaQuality_EXACT_getInstance());
    IpaVowel_O_instance = new IpaVowel('O', 7, 'o', 'close-mid back', '\u05B9', '\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD', listOf(['o', 'oa']), IpaQuality_EXACT_getInstance());
    IpaVowel_A_instance = new IpaVowel('A', 8, 'a', 'open', '\u05B7', '\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7', listOf_0('a'), IpaQuality_EXACT_getInstance());
    IpaVowel_AI_instance = new IpaVowel('AI', 9, 'a\u026A', 'diphthong', '\u05B7\u05D9', '\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7+\u05D9\u05D5\u05B9\u05D3', listOf(['i', 'igh', 'y']), IpaQuality_NEAR_EXACT_getInstance());
    IpaVowel_AU_instance = new IpaVowel('AU', 10, 'a\u028A', 'diphthong', '\u05B8\u05D0\u05D5\u05BC', '\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7+\u05D5\u05B8\u05D5', listOf(['ou', 'ow']), IpaQuality_NEAR_EXACT_getInstance());
    IpaVowel_OI_instance = new IpaVowel('OI', 11, '\u0254\u026A', 'diphthong', '\u05D5\u05B9\u05D9', '\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD+\u05D9\u05D5\u05B9\u05D3', listOf(['oi', 'oy']), IpaQuality_NEAR_EXACT_getInstance());
    IpaVowel_EI_instance = new IpaVowel('EI', 12, 'e\u026A', 'diphthong', '\u05B5\u05D9', '\u05E6\u05B5\u05D9\u05E8\u05B5\u05D9+\u05D9\u05D5\u05B9\u05D3', listOf(['ay', 'ai', 'ei']), IpaQuality_NEAR_EXACT_getInstance());
    Companion_getInstance_1();
  }
  var $ENTRIES_1;
  function IpaVowel(name, ordinal, ipa, displayName, heNikud, heName, enSpellings, quality, warning) {
    warning = warning === VOID ? null : warning;
    Enum.call(this, name, ordinal);
    this.kj_1 = ipa;
    this.lj_1 = displayName;
    this.mj_1 = heNikud;
    this.nj_1 = heName;
    this.oj_1 = enSpellings;
    this.pj_1 = quality;
    this.qj_1 = warning;
  }
  var IpaConsonant_P_instance;
  var IpaConsonant_B_instance;
  var IpaConsonant_T_instance;
  var IpaConsonant_D_instance;
  var IpaConsonant_K_instance;
  var IpaConsonant_G_instance;
  var IpaConsonant_GLOTTAL_STOP_instance;
  var IpaConsonant_F_instance;
  var IpaConsonant_V_instance;
  var IpaConsonant_S_instance;
  var IpaConsonant_Z_instance;
  var IpaConsonant_SH_instance;
  var IpaConsonant_ZH_instance;
  var IpaConsonant_H_instance;
  var IpaConsonant_TH_VOICELESS_instance;
  var IpaConsonant_TH_VOICED_instance;
  var IpaConsonant_CH_instance;
  var IpaConsonant_J_instance;
  var IpaConsonant_X_instance;
  var IpaConsonant_TS_instance;
  var IpaConsonant_UVULAR_R_instance;
  var IpaConsonant_M_instance;
  var IpaConsonant_N_instance;
  var IpaConsonant_NG_instance;
  var IpaConsonant_L_instance;
  var IpaConsonant_R_instance;
  var IpaConsonant_Y_instance;
  var IpaConsonant_W_instance;
  function Companion_2() {
    Companion_instance_2 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_2();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.vj_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.ck_1 = destination;
  }
  protoOf(Companion_2).sj = function (ipa) {
    return this.ck_1.q1(ipa);
  };
  var Companion_instance_2;
  function Companion_getInstance_2() {
    IpaConsonant_initEntries();
    if (Companion_instance_2 == null)
      new Companion_2();
    return Companion_instance_2;
  }
  function values_2() {
    return [IpaConsonant_P_getInstance(), IpaConsonant_B_getInstance(), IpaConsonant_T_getInstance(), IpaConsonant_D_getInstance(), IpaConsonant_K_getInstance(), IpaConsonant_G_getInstance(), IpaConsonant_GLOTTAL_STOP_getInstance(), IpaConsonant_F_getInstance(), IpaConsonant_V_getInstance(), IpaConsonant_S_getInstance(), IpaConsonant_Z_getInstance(), IpaConsonant_SH_getInstance(), IpaConsonant_ZH_getInstance(), IpaConsonant_H_getInstance(), IpaConsonant_TH_VOICELESS_getInstance(), IpaConsonant_TH_VOICED_getInstance(), IpaConsonant_CH_getInstance(), IpaConsonant_J_getInstance(), IpaConsonant_X_getInstance(), IpaConsonant_TS_getInstance(), IpaConsonant_UVULAR_R_getInstance(), IpaConsonant_M_getInstance(), IpaConsonant_N_getInstance(), IpaConsonant_NG_getInstance(), IpaConsonant_L_getInstance(), IpaConsonant_R_getInstance(), IpaConsonant_Y_getInstance(), IpaConsonant_W_getInstance()];
  }
  function get_entries_2() {
    if ($ENTRIES_2 == null)
      $ENTRIES_2 = enumEntries(values_2());
    return $ENTRIES_2;
  }
  var IpaConsonant_entriesInitialized;
  function IpaConsonant_initEntries() {
    if (IpaConsonant_entriesInitialized)
      return Unit_instance;
    IpaConsonant_entriesInitialized = true;
    IpaConsonant_P_instance = new IpaConsonant('P', 0, 'p', 'voiceless bilabial', '\u05E4\u05BC', 'p');
    IpaConsonant_B_instance = new IpaConsonant('B', 1, 'b', 'voiced bilabial', '\u05D1\u05BC', 'b');
    IpaConsonant_T_instance = new IpaConsonant('T', 2, 't', 'voiceless alveolar', '\u05EA/\u05D8', 't');
    IpaConsonant_D_instance = new IpaConsonant('D', 3, 'd', 'voiced alveolar', '\u05D3', 'd');
    IpaConsonant_K_instance = new IpaConsonant('K', 4, 'k', 'voiceless velar', '\u05DB\u05BC/\u05E7', 'k');
    IpaConsonant_G_instance = new IpaConsonant('G', 5, 'g', 'voiced velar', '\u05D2', 'g');
    IpaConsonant_GLOTTAL_STOP_instance = new IpaConsonant('GLOTTAL_STOP', 6, '\u0294', 'glottal stop', '\u05D0/\u05E2', '\u2014');
    IpaConsonant_F_instance = new IpaConsonant('F', 7, 'f', 'voiceless labiodental', '\u05E4', 'f');
    IpaConsonant_V_instance = new IpaConsonant('V', 8, 'v', 'voiced labiodental', '\u05D1/\u05D5', 'v');
    IpaConsonant_S_instance = new IpaConsonant('S', 9, 's', 'voiceless alveolar', '\u05E1/\u05E9\u05C2', 's');
    IpaConsonant_Z_instance = new IpaConsonant('Z', 10, 'z', 'voiced alveolar', '\u05D6', 'z');
    IpaConsonant_SH_instance = new IpaConsonant('SH', 11, '\u0283', 'voiceless postalveolar', '\u05E9\u05C1', 'sh');
    IpaConsonant_ZH_instance = new IpaConsonant('ZH', 12, '\u0292', 'voiced postalveolar', '\u05D6\u05F3', 'si/su', true);
    IpaConsonant_H_instance = new IpaConsonant('H', 13, 'h', 'voiceless glottal', '\u05D4', 'h');
    IpaConsonant_TH_VOICELESS_instance = new IpaConsonant('TH_VOICELESS', 14, '\u03B8', 'voiceless dental', '\u05EA\u05F3', 'th', true, VOID, 'NO Hebrew equivalent!');
    IpaConsonant_TH_VOICED_instance = new IpaConsonant('TH_VOICED', 15, '\xF0', 'voiced dental', '\u05D3\u05F3', 'th', true, VOID, 'NO Hebrew equivalent!');
    IpaConsonant_CH_instance = new IpaConsonant('CH', 16, 't\u0283', 'voiceless postalveolar affricate', '\u05E6\u05F3', 'ch', true);
    IpaConsonant_J_instance = new IpaConsonant('J', 17, 'd\u0292', 'voiced postalveolar affricate', '\u05D2\u05F3', 'j', true);
    IpaConsonant_X_instance = new IpaConsonant('X', 18, 'x', 'voiceless velar fricative', '\u05DB/\u05D7', "kh'", VOID, true);
    IpaConsonant_TS_instance = new IpaConsonant('TS', 19, 'ts', 'voiceless alveolar affricate', '\u05E6', "ts'", VOID, true);
    IpaConsonant_UVULAR_R_instance = new IpaConsonant('UVULAR_R', 20, '\u0281', 'uvular fricative', '\u05E8', "r'", VOID, true);
    IpaConsonant_M_instance = new IpaConsonant('M', 21, 'm', 'bilabial nasal', '\u05DE', 'm');
    IpaConsonant_N_instance = new IpaConsonant('N', 22, 'n', 'alveolar nasal', '\u05E0', 'n');
    IpaConsonant_NG_instance = new IpaConsonant('NG', 23, '\u014B', 'velar nasal', '\u05E0\u05D2', 'ng');
    IpaConsonant_L_instance = new IpaConsonant('L', 24, 'l', 'alveolar lateral', '\u05DC', 'l');
    IpaConsonant_R_instance = new IpaConsonant('R', 25, 'r', 'alveolar approximant', '\u05E8\u05F3', 'r');
    IpaConsonant_Y_instance = new IpaConsonant('Y', 26, 'j', 'palatal approximant', '\u05D9', 'y');
    IpaConsonant_W_instance = new IpaConsonant('W', 27, 'w', 'labio-velar', '\u05D5\u05F3', 'w', true, VOID, 'W \u2260 V!');
    Companion_getInstance_2();
  }
  var $ENTRIES_2;
  function IpaConsonant(name, ordinal, ipa, displayName, he, en, geresh, heUnique, warning) {
    geresh = geresh === VOID ? false : geresh;
    heUnique = heUnique === VOID ? false : heUnique;
    warning = warning === VOID ? null : warning;
    Enum.call(this, name, ordinal);
    this.vj_1 = ipa;
    this.wj_1 = displayName;
    this.xj_1 = he;
    this.yj_1 = en;
    this.zj_1 = geresh;
    this.ak_1 = heUnique;
    this.bk_1 = warning;
  }
  function IpaQuality_EXACT_getInstance() {
    IpaQuality_initEntries();
    return IpaQuality_EXACT_instance;
  }
  function IpaQuality_NEAR_EXACT_getInstance() {
    IpaQuality_initEntries();
    return IpaQuality_NEAR_EXACT_instance;
  }
  function IpaQuality_APPROX_getInstance() {
    IpaQuality_initEntries();
    return IpaQuality_APPROX_instance;
  }
  function IpaVowel_I_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_I_instance;
  }
  function IpaVowel_E_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_E_instance;
  }
  function IpaVowel_EPSILON_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_EPSILON_instance;
  }
  function IpaVowel_SMALL_I_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_SMALL_I_instance;
  }
  function IpaVowel_ASH_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_ASH_instance;
  }
  function IpaVowel_SCHWA_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_SCHWA_instance;
  }
  function IpaVowel_U_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_U_instance;
  }
  function IpaVowel_O_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_O_instance;
  }
  function IpaVowel_A_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_A_instance;
  }
  function IpaVowel_AI_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_AI_instance;
  }
  function IpaVowel_AU_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_AU_instance;
  }
  function IpaVowel_OI_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_OI_instance;
  }
  function IpaVowel_EI_getInstance() {
    IpaVowel_initEntries();
    return IpaVowel_EI_instance;
  }
  function IpaConsonant_P_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_P_instance;
  }
  function IpaConsonant_B_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_B_instance;
  }
  function IpaConsonant_T_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_T_instance;
  }
  function IpaConsonant_D_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_D_instance;
  }
  function IpaConsonant_K_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_K_instance;
  }
  function IpaConsonant_G_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_G_instance;
  }
  function IpaConsonant_GLOTTAL_STOP_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_GLOTTAL_STOP_instance;
  }
  function IpaConsonant_F_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_F_instance;
  }
  function IpaConsonant_V_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_V_instance;
  }
  function IpaConsonant_S_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_S_instance;
  }
  function IpaConsonant_Z_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_Z_instance;
  }
  function IpaConsonant_SH_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_SH_instance;
  }
  function IpaConsonant_ZH_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_ZH_instance;
  }
  function IpaConsonant_H_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_H_instance;
  }
  function IpaConsonant_TH_VOICELESS_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_TH_VOICELESS_instance;
  }
  function IpaConsonant_TH_VOICED_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_TH_VOICED_instance;
  }
  function IpaConsonant_CH_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_CH_instance;
  }
  function IpaConsonant_J_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_J_instance;
  }
  function IpaConsonant_X_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_X_instance;
  }
  function IpaConsonant_TS_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_TS_instance;
  }
  function IpaConsonant_UVULAR_R_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_UVULAR_R_instance;
  }
  function IpaConsonant_M_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_M_instance;
  }
  function IpaConsonant_N_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_N_instance;
  }
  function IpaConsonant_NG_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_NG_instance;
  }
  function IpaConsonant_L_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_L_instance;
  }
  function IpaConsonant_R_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_R_instance;
  }
  function IpaConsonant_Y_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_Y_instance;
  }
  function IpaConsonant_W_getInstance() {
    IpaConsonant_initEntries();
    return IpaConsonant_W_instance;
  }
  function simplifyIpa($this, ipa) {
    return replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(ipa, '\u026A', 'I'), '\u014B', 'N'), '\u0283', 'sh'), '\u0292', 'zh'), '\u0259', 'E'), '\u0254\u02D0', 'aw'), '\u0254', 'o'), '\u025C\u02D0', 'Er'), 'a\u028A', 'aw'), 'a\u026A', 'aj'), 'e\u026A', 'ej'), 'o\u028A', 'ow'), '\u0254\u026A', 'oj'), 'u\u02D0', 'oo'), 'i\u02D0', 'ee'), '\u025B', 'e'), '\xE6', 'a'), '\u028C', 'u'), '\u028A', 'U'), '\u03B8', 'th'), '\xF0', 'dh'), '\u02D0', ''), 'r', 'r');
  }
  function RhymeSchemeItem(letter, ipa, hue) {
    this.dk_1 = letter;
    this.ek_1 = ipa;
    this.fk_1 = hue;
  }
  protoOf(RhymeSchemeItem).toString = function () {
    return 'RhymeSchemeItem(letter=' + toString_0(this.dk_1) + ', ipa=' + this.ek_1 + ', hue=' + this.fk_1 + ')';
  };
  protoOf(RhymeSchemeItem).hashCode = function () {
    var result = Char__hashCode_impl_otmys(this.dk_1);
    result = imul(result, 31) + getStringHashCode(this.ek_1) | 0;
    result = imul(result, 31) + this.fk_1 | 0;
    return result;
  };
  protoOf(RhymeSchemeItem).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof RhymeSchemeItem))
      return false;
    if (!(this.dk_1 === other.dk_1))
      return false;
    if (!(this.ek_1 === other.ek_1))
      return false;
    if (!(this.fk_1 === other.fk_1))
      return false;
    return true;
  };
  function IpaColor() {
    IpaColor_instance = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_0();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp_0 = new Char(element.ni_1);
      var tmp$ret$1 = element.gj();
      destination.l3(tmp_0, tmp$ret$1);
    }
    tmp.gk_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_0();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_0 = tmp0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      if (!(element_0.hj() == null)) {
        destination_0.g(element_0);
      }
    }
    // Inline function 'kotlin.collections.associateBy' call
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(destination_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_1 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_1 = destination_0.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var tmp_2 = new Char(element_1.ni_1);
      var tmp$ret$8 = ensureNotNull(element_1.hj());
      destination_1.l3(tmp_2, tmp$ret$8);
    }
    tmp_1.hk_1 = destination_1;
    var tmp_3 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = get_entries_3();
    var capacity_1 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_2 = LinkedHashMap_init_$Create$(capacity_1);
    var _iterator__ex2g4s_2 = this_1.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      var tmp_4 = element_2.nk_1;
      var tmp$ret$12 = element_2.gj();
      destination_2.l3(tmp_4, tmp$ret$12);
    }
    tmp_3.ik_1 = destination_2;
  }
  protoOf(IpaColor).qk = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0 = word.toLowerCase();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[^a-z]').y7(tmp0, '');
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(w) === 0)
      return '';
    var pattern = Companion_getInstance_17().yb(w);
    if (!(pattern == null)) {
      return simplifyIpa(this, pattern.cc_1);
    }
    return takeLast(w, 3);
  };
  protoOf(IpaColor).rk = function (word) {
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[,.\\-;:!?\\s]+$').y7(word, '');
    var result = StringBuilder_init_$Create$();
    var lastConsonant = null;
    var lastConsonantPos = -1;
    var inductionVariable = 0;
    var last = w.length;
    while (inductionVariable < last) {
      var c = charCodeAt(w, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (1488 <= cp ? cp <= 1514 : false) {
        lastConsonant = c;
        lastConsonantPos = result.a();
        var tmp0_elvis_lhs = this.gk_1.q1(new Char(c));
        result.l6(tmp0_elvis_lhs == null ? '' : tmp0_elvis_lhs);
      } else if (cp === 1468) {
        var tmp1_safe_receiver = lastConsonant;
        var tmp = tmp1_safe_receiver;
        if ((tmp == null ? null : new Char(tmp)) == null)
          null;
        else {
          var tmp_0 = tmp1_safe_receiver;
          // Inline function 'kotlin.let' call
          var lc = (tmp_0 == null ? null : new Char(tmp_0)).x_1;
          var tmp0_safe_receiver = IpaColor_getInstance().hk_1.q1(new Char(lc));
          var tmp_1;
          if (tmp0_safe_receiver == null) {
            tmp_1 = null;
          } else {
            // Inline function 'kotlin.let' call
            if (lastConsonantPos >= 0) {
              var before = result.h7(0, lastConsonantPos);
              var tmp0_safe_receiver_0 = IpaColor_getInstance().gk_1.q1(new Char(lc));
              var tmp1_elvis_lhs = tmp0_safe_receiver_0 == null ? null : tmp0_safe_receiver_0.length;
              var consonantLen = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
              var after = (lastConsonantPos + consonantLen | 0) <= result.a() ? result.g7(lastConsonantPos + consonantLen | 0) : '';
              result.j7();
              result.l6(before);
              result.l6(tmp0_safe_receiver);
              result.l6(after);
            }
            tmp_1 = Unit_instance;
          }
        }
      } else if (cp !== 1473)
        if (cp === 1474) {
          var tmp_2;
          if (lastConsonantPos >= 0) {
            var tmp_3 = lastConsonant;
            tmp_2 = equals(tmp_3 == null ? null : new Char(tmp_3), new Char(_Char___init__impl__6a9atx(1513)));
          } else {
            tmp_2 = false;
          }
          if (tmp_2) {
            var before_0 = result.h7(0, lastConsonantPos);
            var after_0 = (lastConsonantPos + 2 | 0) <= result.a() ? result.g7(lastConsonantPos + 2 | 0) : '';
            result.j7();
            result.l6(before_0);
            result.l6('s');
            result.l6(after_0);
          }
        } else {
          var tmp2_safe_receiver = this.ik_1.q1(cp);
          if (tmp2_safe_receiver == null)
            null;
          else {
            // Inline function 'kotlin.let' call
            result.l6(tmp2_safe_receiver);
          }
        }
    }
    return result.toString();
  };
  protoOf(IpaColor).sk = function (line, isHebrew) {
    // Inline function 'kotlin.text.replace' call
    // Inline function 'kotlin.text.trim' call
    var this_0 = Regex_init_$Create$('[,.\\-;:!?\\s]+$').y7(line, '');
    var cleaned = toString_1(trim(isCharSequence(this_0) ? this_0 : THROW_CCE()));
    // Inline function 'kotlin.text.split' call
    var words = Regex_init_$Create$('\\s+').g8(cleaned, 0);
    var tmp0_elvis_lhs = lastOrNull(words);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return '';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lastWord = tmp;
    var ipa = isHebrew ? this.rk(lastWord) : this.qk(lastWord);
    var tmp_0;
    if (isHebrew) {
      // Inline function 'kotlin.text.ifEmpty' call
      var this_1 = takeLast(ipa, 4);
      var tmp_1;
      // Inline function 'kotlin.text.isEmpty' call
      if (charSequenceLength(this_1) === 0) {
        tmp_1 = ipa;
      } else {
        tmp_1 = this_1;
      }
      tmp_0 = tmp_1;
    } else {
      tmp_0 = ipa;
    }
    return tmp_0;
  };
  protoOf(IpaColor).tk = function (ipa) {
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(ipa) === 0)
      return 0;
    var core = takeLast(ipa, 3);
    var hc = new Long(5381, 0);
    var inductionVariable = 0;
    var last = core.length;
    while (inductionVariable < last) {
      var c = charCodeAt(core, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.Long.times' call
      var this_0 = hc;
      var tmp = multiply(this_0, fromInt(33));
      // Inline function 'kotlin.code' call
      var tmp$ret$2 = Char__toInt_impl_vasixd(c);
      hc = bitwiseAnd(bitwiseXor(tmp, fromInt(tmp$ret$2)), new Long(-1, 0));
    }
    var prefix = dropLast(ipa, coerceAtMost(3, ipa.length));
    var hp = new Long(5381, 0);
    var inductionVariable_0 = 0;
    var last_0 = prefix.length;
    while (inductionVariable_0 < last_0) {
      var c_0 = charCodeAt(prefix, inductionVariable_0);
      inductionVariable_0 = inductionVariable_0 + 1 | 0;
      // Inline function 'kotlin.Long.times' call
      var this_1 = hp;
      var tmp_0 = multiply(this_1, fromInt(33));
      // Inline function 'kotlin.code' call
      var tmp$ret$4 = Char__toInt_impl_vasixd(c_0);
      hp = bitwiseAnd(bitwiseXor(tmp_0, fromInt(tmp$ret$4)), new Long(-1, 0));
    }
    // Inline function 'kotlin.Long.rem' call
    var this_2 = hc;
    // Inline function 'kotlin.Long.times' call
    var this_3 = modulo(this_2, fromInt(360));
    var tmp_1 = toNumber(this_3) * 0.85;
    // Inline function 'kotlin.Long.rem' call
    var this_4 = hp;
    // Inline function 'kotlin.Long.times' call
    var this_5 = modulo(this_4, fromInt(360));
    var tmp$ret$8 = toNumber(this_5) * 0.15;
    return numberToInt(tmp_1 + tmp$ret$8) % 360 | 0;
  };
  protoOf(IpaColor).uk = function (hue, saturation, lightness) {
    return 'hsl(' + hue + ', ' + saturation + '%, ' + lightness + '%)';
  };
  protoOf(IpaColor).vk = function (ipa) {
    var hue = this.tk(ipa);
    return this.uk(hue, 80, 72);
  };
  protoOf(IpaColor).wk = function (ipa) {
    var hue = this.tk(ipa);
    return this.uk(hue, 45, 55);
  };
  protoOf(IpaColor).xk = function (ipas) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var groups = ArrayList_init_$Create$();
    var letters = 'ABCDEFGHIJKLM';
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(ipas, 10));
    var _iterator__ex2g4s = ipas.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp;
      // Inline function 'kotlin.text.isEmpty' call
      if (charSequenceLength(item) === 0) {
        tmp = new RhymeSchemeItem(_Char___init__impl__6a9atx(120), '?', 0);
      } else {
        var key = takeLast(item, 3);
        var hue = IpaColor_getInstance().tk(item);
        // Inline function 'kotlin.collections.find' call
        var tmp$ret$3;
        $l$block: {
          // Inline function 'kotlin.collections.firstOrNull' call
          var _iterator__ex2g4s_0 = groups.i();
          while (_iterator__ex2g4s_0.j()) {
            var element = _iterator__ex2g4s_0.k();
            if (element.p9_1 === key) {
              tmp$ret$3 = element;
              break $l$block;
            }
          }
          tmp$ret$3 = null;
        }
        var existing = tmp$ret$3;
        var tmp_0;
        if (!(existing == null)) {
          tmp_0 = new RhymeSchemeItem(existing.q9_1.x_1, key, hue);
        } else {
          // Inline function 'kotlin.text.getOrElse' call
          var index = groups.l();
          var tmp_1;
          if (0 <= index ? index <= (charSequenceLength(letters) - 1 | 0) : false) {
            tmp_1 = charSequenceGet(letters, index);
          } else {
            tmp_1 = _Char___init__impl__6a9atx(63);
          }
          var letter = tmp_1;
          groups.g(to(key, new Char(letter)));
          tmp_0 = new RhymeSchemeItem(letter, key, hue);
        }
        tmp = tmp_0;
      }
      var tmp$ret$7 = tmp;
      destination.g(tmp$ret$7);
    }
    return destination;
  };
  var IpaColor_instance;
  function IpaColor_getInstance() {
    if (IpaColor_instance == null)
      new IpaColor();
    return IpaColor_instance;
  }
  var Place_BILABIAL_instance;
  var Place_LABIODENTAL_instance;
  var Place_DENTAL_instance;
  var Place_ALVEOLAR_instance;
  var Place_POSTALVEOLAR_instance;
  var Place_RETROFLEX_instance;
  var Place_PALATAL_instance;
  var Place_VELAR_instance;
  var Place_UVULAR_instance;
  var Place_PHARYNGEAL_instance;
  var Place_GLOTTAL_instance;
  var Place_entriesInitialized;
  function Place_initEntries() {
    if (Place_entriesInitialized)
      return Unit_instance;
    Place_entriesInitialized = true;
    Place_BILABIAL_instance = new Place('BILABIAL', 0, 0, 'Bilabial');
    Place_LABIODENTAL_instance = new Place('LABIODENTAL', 1, 1, 'Labiodental');
    Place_DENTAL_instance = new Place('DENTAL', 2, 2, 'Dental');
    Place_ALVEOLAR_instance = new Place('ALVEOLAR', 3, 3, 'Alveolar');
    Place_POSTALVEOLAR_instance = new Place('POSTALVEOLAR', 4, 4, 'Postalveolar');
    Place_RETROFLEX_instance = new Place('RETROFLEX', 5, 5, 'Retroflex');
    Place_PALATAL_instance = new Place('PALATAL', 6, 6, 'Palatal');
    Place_VELAR_instance = new Place('VELAR', 7, 7, 'Velar');
    Place_UVULAR_instance = new Place('UVULAR', 8, 8, 'Uvular');
    Place_PHARYNGEAL_instance = new Place('PHARYNGEAL', 9, 9, 'Pharyngeal');
    Place_GLOTTAL_instance = new Place('GLOTTAL', 10, 10, 'Glottal');
  }
  var Manner_PLOSIVE_instance;
  var Manner_NASAL_instance;
  var Manner_TRILL_instance;
  var Manner_TAP_instance;
  var Manner_FRICATIVE_instance;
  var Manner_LATERAL_FRICATIVE_instance;
  var Manner_APPROXIMANT_instance;
  var Manner_LATERAL_APPROXIMANT_instance;
  var Manner_AFFRICATE_instance;
  var Manner_entriesInitialized;
  function Manner_initEntries() {
    if (Manner_entriesInitialized)
      return Unit_instance;
    Manner_entriesInitialized = true;
    Manner_PLOSIVE_instance = new Manner('PLOSIVE', 0, 0, 'Plosive');
    Manner_NASAL_instance = new Manner('NASAL', 1, 1, 'Nasal');
    Manner_TRILL_instance = new Manner('TRILL', 2, 2, 'Trill');
    Manner_TAP_instance = new Manner('TAP', 3, 3, 'Tap/Flap');
    Manner_FRICATIVE_instance = new Manner('FRICATIVE', 4, 4, 'Fricative');
    Manner_LATERAL_FRICATIVE_instance = new Manner('LATERAL_FRICATIVE', 5, 5, 'Lateral Fricative');
    Manner_APPROXIMANT_instance = new Manner('APPROXIMANT', 6, 6, 'Approximant');
    Manner_LATERAL_APPROXIMANT_instance = new Manner('LATERAL_APPROXIMANT', 7, 7, 'Lateral Approximant');
    Manner_AFFRICATE_instance = new Manner('AFFRICATE', 8, 8, 'Affricate');
  }
  function computeHue($this) {
    var placeHue = imul($this.el_1.al_1, 36);
    var mannerOffset = imul($this.fl_1.ol_1, 2);
    var voiceOffset = $this.gl_1 ? 0 : 18;
    return ((placeHue + mannerOffset | 0) + voiceOffset | 0) % 360 | 0;
  }
  function IpaMatrix$Consonant$hue$delegate$lambda(this$0) {
    return function () {
      return computeHue(this$0);
    };
  }
  function IpaMatrix$Consonant$_get_hue_$ref_uftwb6() {
    return function (p0) {
      return p0.ql();
    };
  }
  var Height_CLOSE_instance;
  var Height_NEAR_CLOSE_instance;
  var Height_CLOSE_MID_instance;
  var Height_MID_instance;
  var Height_OPEN_MID_instance;
  var Height_NEAR_OPEN_instance;
  var Height_OPEN_instance;
  var Height_entriesInitialized;
  function Height_initEntries() {
    if (Height_entriesInitialized)
      return Unit_instance;
    Height_entriesInitialized = true;
    Height_CLOSE_instance = new Height('CLOSE', 0, 0, 'Close');
    Height_NEAR_CLOSE_instance = new Height('NEAR_CLOSE', 1, 1, 'Near-close');
    Height_CLOSE_MID_instance = new Height('CLOSE_MID', 2, 2, 'Close-mid');
    Height_MID_instance = new Height('MID', 3, 3, 'Mid');
    Height_OPEN_MID_instance = new Height('OPEN_MID', 4, 4, 'Open-mid');
    Height_NEAR_OPEN_instance = new Height('NEAR_OPEN', 5, 5, 'Near-open');
    Height_OPEN_instance = new Height('OPEN', 6, 6, 'Open');
  }
  var Backness_FRONT_instance;
  var Backness_CENTRAL_instance;
  var Backness_BACK_instance;
  var Backness_entriesInitialized;
  function Backness_initEntries() {
    if (Backness_entriesInitialized)
      return Unit_instance;
    Backness_entriesInitialized = true;
    Backness_FRONT_instance = new Backness('FRONT', 0, 0, 'Front');
    Backness_CENTRAL_instance = new Backness('CENTRAL', 1, 1, 'Central');
    Backness_BACK_instance = new Backness('BACK', 2, 2, 'Back');
  }
  function computeHue_0($this) {
    var backnessHue = imul($this.yl_1.tl_1, 120);
    var heightOffset = imul($this.xl_1.gm_1, 8);
    var roundOffset = $this.zl_1 ? 10 : 0;
    return ((backnessHue + heightOffset | 0) + roundOffset | 0) % 360 | 0;
  }
  function IpaMatrix$Vowel$hue$delegate$lambda(this$0) {
    return function () {
      return computeHue_0(this$0);
    };
  }
  function IpaMatrix$Vowel$_get_hue_$ref_u61xhc() {
    return function (p0) {
      return p0.ql();
    };
  }
  function Place(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.al_1 = code;
    this.bl_1 = displayName;
  }
  function Manner(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.ol_1 = code;
    this.pl_1 = displayName;
  }
  function Consonant(ipa, name, place, manner, voiced, aspirated, breathy, emphatic, languages) {
    aspirated = aspirated === VOID ? false : aspirated;
    breathy = breathy === VOID ? false : breathy;
    emphatic = emphatic === VOID ? false : emphatic;
    this.cl_1 = ipa;
    this.dl_1 = name;
    this.el_1 = place;
    this.fl_1 = manner;
    this.gl_1 = voiced;
    this.hl_1 = aspirated;
    this.il_1 = breathy;
    this.jl_1 = emphatic;
    this.kl_1 = languages;
    var tmp = this;
    tmp.ll_1 = lazy(IpaMatrix$Consonant$hue$delegate$lambda(this));
  }
  protoOf(Consonant).ql = function () {
    var tmp0 = this.ll_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('hue', 1, tmp, IpaMatrix$Consonant$_get_hue_$ref_uftwb6(), null);
    return tmp0.n1();
  };
  protoOf(Consonant).toString = function () {
    return 'Consonant(ipa=' + this.cl_1 + ', name=' + this.dl_1 + ', place=' + this.el_1.toString() + ', manner=' + this.fl_1.toString() + ', voiced=' + this.gl_1 + ', aspirated=' + this.hl_1 + ', breathy=' + this.il_1 + ', emphatic=' + this.jl_1 + ', languages=' + toString_1(this.kl_1) + ')';
  };
  protoOf(Consonant).hashCode = function () {
    var result = getStringHashCode(this.cl_1);
    result = imul(result, 31) + getStringHashCode(this.dl_1) | 0;
    result = imul(result, 31) + this.el_1.hashCode() | 0;
    result = imul(result, 31) + this.fl_1.hashCode() | 0;
    result = imul(result, 31) + getBooleanHashCode(this.gl_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.hl_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.il_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.jl_1) | 0;
    result = imul(result, 31) + hashCode(this.kl_1) | 0;
    return result;
  };
  protoOf(Consonant).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Consonant))
      return false;
    if (!(this.cl_1 === other.cl_1))
      return false;
    if (!(this.dl_1 === other.dl_1))
      return false;
    if (!this.el_1.equals(other.el_1))
      return false;
    if (!this.fl_1.equals(other.fl_1))
      return false;
    if (!(this.gl_1 === other.gl_1))
      return false;
    if (!(this.hl_1 === other.hl_1))
      return false;
    if (!(this.il_1 === other.il_1))
      return false;
    if (!(this.jl_1 === other.jl_1))
      return false;
    if (!equals(this.kl_1, other.kl_1))
      return false;
    return true;
  };
  function Height(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.gm_1 = code;
    this.hm_1 = displayName;
  }
  function Backness(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.tl_1 = code;
    this.ul_1 = displayName;
  }
  function Vowel(ipa, name, height, backness, rounded, long, nasal, languages) {
    long = long === VOID ? false : long;
    nasal = nasal === VOID ? false : nasal;
    this.vl_1 = ipa;
    this.wl_1 = name;
    this.xl_1 = height;
    this.yl_1 = backness;
    this.zl_1 = rounded;
    this.am_1 = long;
    this.bm_1 = nasal;
    this.cm_1 = languages;
    var tmp = this;
    tmp.dm_1 = lazy(IpaMatrix$Vowel$hue$delegate$lambda(this));
  }
  protoOf(Vowel).ql = function () {
    var tmp0 = this.dm_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('hue', 1, tmp, IpaMatrix$Vowel$_get_hue_$ref_u61xhc(), null);
    return tmp0.n1();
  };
  protoOf(Vowel).toString = function () {
    return 'Vowel(ipa=' + this.vl_1 + ', name=' + this.wl_1 + ', height=' + this.xl_1.toString() + ', backness=' + this.yl_1.toString() + ', rounded=' + this.zl_1 + ', long=' + this.am_1 + ', nasal=' + this.bm_1 + ', languages=' + toString_1(this.cm_1) + ')';
  };
  protoOf(Vowel).hashCode = function () {
    var result = getStringHashCode(this.vl_1);
    result = imul(result, 31) + getStringHashCode(this.wl_1) | 0;
    result = imul(result, 31) + this.xl_1.hashCode() | 0;
    result = imul(result, 31) + this.yl_1.hashCode() | 0;
    result = imul(result, 31) + getBooleanHashCode(this.zl_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.am_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.bm_1) | 0;
    result = imul(result, 31) + hashCode(this.cm_1) | 0;
    return result;
  };
  protoOf(Vowel).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Vowel))
      return false;
    if (!(this.vl_1 === other.vl_1))
      return false;
    if (!(this.wl_1 === other.wl_1))
      return false;
    if (!this.xl_1.equals(other.xl_1))
      return false;
    if (!this.yl_1.equals(other.yl_1))
      return false;
    if (!(this.zl_1 === other.zl_1))
      return false;
    if (!(this.am_1 === other.am_1))
      return false;
    if (!(this.bm_1 === other.bm_1))
      return false;
    if (!equals(this.cm_1, other.cm_1))
      return false;
    return true;
  };
  function Place_BILABIAL_getInstance() {
    Place_initEntries();
    return Place_BILABIAL_instance;
  }
  function Place_LABIODENTAL_getInstance() {
    Place_initEntries();
    return Place_LABIODENTAL_instance;
  }
  function Place_DENTAL_getInstance() {
    Place_initEntries();
    return Place_DENTAL_instance;
  }
  function Place_ALVEOLAR_getInstance() {
    Place_initEntries();
    return Place_ALVEOLAR_instance;
  }
  function Place_POSTALVEOLAR_getInstance() {
    Place_initEntries();
    return Place_POSTALVEOLAR_instance;
  }
  function Place_RETROFLEX_getInstance() {
    Place_initEntries();
    return Place_RETROFLEX_instance;
  }
  function Place_PALATAL_getInstance() {
    Place_initEntries();
    return Place_PALATAL_instance;
  }
  function Place_VELAR_getInstance() {
    Place_initEntries();
    return Place_VELAR_instance;
  }
  function Place_UVULAR_getInstance() {
    Place_initEntries();
    return Place_UVULAR_instance;
  }
  function Place_PHARYNGEAL_getInstance() {
    Place_initEntries();
    return Place_PHARYNGEAL_instance;
  }
  function Place_GLOTTAL_getInstance() {
    Place_initEntries();
    return Place_GLOTTAL_instance;
  }
  function Manner_PLOSIVE_getInstance() {
    Manner_initEntries();
    return Manner_PLOSIVE_instance;
  }
  function Manner_NASAL_getInstance() {
    Manner_initEntries();
    return Manner_NASAL_instance;
  }
  function Manner_TRILL_getInstance() {
    Manner_initEntries();
    return Manner_TRILL_instance;
  }
  function Manner_TAP_getInstance() {
    Manner_initEntries();
    return Manner_TAP_instance;
  }
  function Manner_FRICATIVE_getInstance() {
    Manner_initEntries();
    return Manner_FRICATIVE_instance;
  }
  function Manner_APPROXIMANT_getInstance() {
    Manner_initEntries();
    return Manner_APPROXIMANT_instance;
  }
  function Manner_LATERAL_APPROXIMANT_getInstance() {
    Manner_initEntries();
    return Manner_LATERAL_APPROXIMANT_instance;
  }
  function Manner_AFFRICATE_getInstance() {
    Manner_initEntries();
    return Manner_AFFRICATE_instance;
  }
  function Height_CLOSE_getInstance() {
    Height_initEntries();
    return Height_CLOSE_instance;
  }
  function Height_NEAR_CLOSE_getInstance() {
    Height_initEntries();
    return Height_NEAR_CLOSE_instance;
  }
  function Height_CLOSE_MID_getInstance() {
    Height_initEntries();
    return Height_CLOSE_MID_instance;
  }
  function Height_MID_getInstance() {
    Height_initEntries();
    return Height_MID_instance;
  }
  function Height_OPEN_MID_getInstance() {
    Height_initEntries();
    return Height_OPEN_MID_instance;
  }
  function Height_NEAR_OPEN_getInstance() {
    Height_initEntries();
    return Height_NEAR_OPEN_instance;
  }
  function Height_OPEN_getInstance() {
    Height_initEntries();
    return Height_OPEN_instance;
  }
  function Backness_FRONT_getInstance() {
    Backness_initEntries();
    return Backness_FRONT_instance;
  }
  function Backness_CENTRAL_getInstance() {
    Backness_initEntries();
    return Backness_CENTRAL_instance;
  }
  function Backness_BACK_getInstance() {
    Backness_initEntries();
    return Backness_BACK_instance;
  }
  function IpaMatrix() {
    IpaMatrix_instance = this;
    var tmp = this;
    var tmp_0 = new Consonant('p', 'voiceless bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_1 = new Consonant('b', 'voiced bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_2 = new Consonant('p\u02B0', 'aspirated bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf([Lang_HI_getInstance(), Lang_KO_getInstance(), Lang_ZH_getInstance()]));
    var tmp_3 = new Consonant('b\u02B1', 'breathy bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_4 = new Consonant('t', 'voiceless alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_5 = new Consonant('d', 'voiced alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_6 = new Consonant('t\u02B0', 'aspirated alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf([Lang_HI_getInstance(), Lang_KO_getInstance(), Lang_ZH_getInstance()]));
    var tmp_7 = new Consonant('d\u02B1', 'breathy alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_8 = new Consonant('\u0288', 'voiceless retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_9 = new Consonant('\u0256', 'voiced retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_10 = new Consonant('\u0288\u02B0', 'aspirated retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_11 = new Consonant('\u0256\u02B1', 'breathy retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_12 = Place_PALATAL_getInstance();
    var tmp_13 = Manner_PLOSIVE_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$0 = emptyList();
    var tmp_14 = new Consonant('c', 'voiceless palatal plosive', tmp_12, tmp_13, false, VOID, VOID, VOID, tmp$ret$0);
    var tmp_15 = Place_PALATAL_getInstance();
    var tmp_16 = Manner_PLOSIVE_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$1 = emptyList();
    var tmp_17 = new Consonant('\u025F', 'voiced palatal plosive', tmp_15, tmp_16, true, VOID, VOID, VOID, tmp$ret$1);
    var tmp_18 = new Consonant('k', 'voiceless velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_19 = new Consonant('\u0261', 'voiced velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance()]));
    var tmp_20 = new Consonant('k\u02B0', 'aspirated velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf([Lang_HI_getInstance(), Lang_KO_getInstance(), Lang_ZH_getInstance()]));
    var tmp_21 = new Consonant('\u0261\u02B1', 'breathy velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_22 = new Consonant('q', 'voiceless uvular plosive', Place_UVULAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf_0(Lang_AR_getInstance()));
    var tmp_23 = Place_UVULAR_getInstance();
    var tmp_24 = Manner_PLOSIVE_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$2 = emptyList();
    var tmp_25 = new Consonant('\u0262', 'voiced uvular plosive', tmp_23, tmp_24, true, VOID, VOID, VOID, tmp$ret$2);
    var tmp_26 = new Consonant('\u0294', 'glottal stop', Place_GLOTTAL_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_HE_getInstance(), Lang_DE_getInstance()]));
    var tmp_27 = new Consonant('m', 'bilabial nasal', Place_BILABIAL_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_28 = new Consonant('n', 'alveolar nasal', Place_ALVEOLAR_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_29 = new Consonant('\u0273', 'retroflex nasal', Place_RETROFLEX_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_30 = new Consonant('\u0272', 'palatal nasal', Place_PALATAL_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf([Lang_ES_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_HI_getInstance()]));
    var tmp_31 = new Consonant('\u014B', 'velar nasal', Place_VELAR_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf([Lang_EN_getInstance(), Lang_DE_getInstance(), Lang_NL_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_ZH_getInstance(), Lang_HI_getInstance(), Lang_SW_getInstance()]));
    var tmp_32 = new Consonant('\u0274', 'uvular nasal', Place_UVULAR_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_JA_getInstance()));
    var tmp_33 = new Consonant('r', 'alveolar trill', Place_ALVEOLAR_getInstance(), Manner_TRILL_getInstance(), true, VOID, VOID, VOID, listOf([Lang_ES_getInstance(), Lang_IT_getInstance(), Lang_RU_getInstance(), Lang_PL_getInstance(), Lang_FI_getInstance(), Lang_AR_getInstance()]));
    var tmp_34 = new Consonant('\u0280', 'uvular trill', Place_UVULAR_getInstance(), Manner_TRILL_getInstance(), true, VOID, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_FR_getInstance()]));
    var tmp_35 = new Consonant('\u027E', 'alveolar tap', Place_ALVEOLAR_getInstance(), Manner_TAP_getInstance(), true, VOID, VOID, VOID, listOf([Lang_ES_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_PT_getInstance()]));
    var tmp_36 = new Consonant('\u027D', 'retroflex flap', Place_RETROFLEX_getInstance(), Manner_TAP_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_37 = new Consonant('f', 'voiceless labiodental fricative', Place_LABIODENTAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_38 = new Consonant('v', 'voiced labiodental fricative', Place_LABIODENTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_IT_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_TR_getInstance()]));
    var tmp_39 = new Consonant('\u03B8', 'voiceless dental fricative', Place_DENTAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance()]));
    var tmp_40 = new Consonant('\xF0', 'voiced dental fricative', Place_DENTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance()]));
    var tmp_41 = new Consonant('s', 'voiceless alveolar fricative', Place_ALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_42 = new Consonant('z', 'voiced alveolar fricative', Place_ALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_TR_getInstance()]));
    var tmp_43 = new Consonant('\u0283', 'voiceless postalveolar fricative', Place_POSTALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DE_getInstance(), Lang_EN_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_TR_getInstance()]));
    var tmp_44 = new Consonant('\u0292', 'voiced postalveolar fricative', Place_POSTALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_EN_getInstance(), Lang_FR_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_TR_getInstance()]));
    var tmp_45 = new Consonant('\u0282', 'voiceless retroflex fricative', Place_RETROFLEX_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_HI_getInstance(), Lang_PL_getInstance(), Lang_ZH_getInstance()]));
    var tmp_46 = new Consonant('\u0290', 'voiced retroflex fricative', Place_RETROFLEX_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_PL_getInstance(), Lang_ZH_getInstance()]));
    var tmp_47 = new Consonant('\u0255', 'voiceless alveolo-palatal fricative', Place_PALATAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_PL_getInstance(), Lang_ZH_getInstance()]));
    var tmp_48 = new Consonant('\u0291', 'voiced alveolo-palatal fricative', Place_PALATAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_JA_getInstance(), Lang_PL_getInstance()]));
    var tmp_49 = new Consonant('\xE7', 'voiceless palatal fricative', Place_PALATAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_JA_getInstance()]));
    var tmp_50 = new Consonant('x', 'voiceless velar fricative', Place_VELAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_HE_getInstance(), Lang_NL_getInstance(), Lang_PL_getInstance(), Lang_RU_getInstance(), Lang_ZH_getInstance()]));
    var tmp_51 = new Consonant('\u0263', 'voiced velar fricative', Place_VELAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_EL_getInstance(), Lang_NL_getInstance()]));
    var tmp_52 = new Consonant('\u03C7', 'voiceless uvular fricative', Place_UVULAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_NL_getInstance(), Lang_HE_getInstance()]));
    var tmp_53 = new Consonant('\u0281', 'voiced uvular fricative', Place_UVULAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_NL_getInstance(), Lang_PT_getInstance()]));
    var tmp_54 = new Consonant('\u0127', 'voiceless pharyngeal fricative', Place_PHARYNGEAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf_0(Lang_AR_getInstance()));
    var tmp_55 = new Consonant('\u0295', 'voiced pharyngeal fricative', Place_PHARYNGEAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_HE_getInstance()]));
    var tmp_56 = new Consonant('h', 'voiceless glottal fricative', Place_GLOTTAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EN_getInstance(), Lang_FI_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance()]));
    var tmp_57 = new Consonant('\u0266', 'voiced glottal fricative', Place_GLOTTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_HI_getInstance(), Lang_NL_getInstance()]));
    var tmp_58 = new Consonant('\u028B', 'labiodental approximant', Place_LABIODENTAL_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf([Lang_HI_getInstance(), Lang_NL_getInstance()]));
    var tmp_59 = new Consonant('\u0279', 'alveolar approximant', Place_ALVEOLAR_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_EN_getInstance()));
    var tmp_60 = new Consonant('\u027B', 'retroflex approximant', Place_RETROFLEX_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_ZH_getInstance()));
    var tmp_61 = new Consonant('j', 'palatal approximant', Place_PALATAL_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_62 = new Consonant('w', 'labio-velar approximant', Place_VELAR_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FR_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_PT_getInstance(), Lang_SW_getInstance(), Lang_ZH_getInstance()]));
    var tmp_63 = new Consonant('\u0270', 'velar approximant', Place_VELAR_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_KO_getInstance()));
    var tmp_64 = new Consonant('l', 'alveolar lateral approximant', Place_ALVEOLAR_getInstance(), Manner_LATERAL_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_65 = new Consonant('\u026D', 'retroflex lateral approximant', Place_RETROFLEX_getInstance(), Manner_LATERAL_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_HI_getInstance()));
    var tmp_66 = new Consonant('\u028E', 'palatal lateral approximant', Place_PALATAL_getInstance(), Manner_LATERAL_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf([Lang_ES_getInstance(), Lang_IT_getInstance(), Lang_PT_getInstance()]));
    var tmp_67 = Place_VELAR_getInstance();
    var tmp_68 = Manner_LATERAL_APPROXIMANT_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$3 = emptyList();
    tmp.im_1 = listOf([tmp_0, tmp_1, tmp_2, tmp_3, tmp_4, tmp_5, tmp_6, tmp_7, tmp_8, tmp_9, tmp_10, tmp_11, tmp_14, tmp_17, tmp_18, tmp_19, tmp_20, tmp_21, tmp_22, tmp_25, tmp_26, tmp_27, tmp_28, tmp_29, tmp_30, tmp_31, tmp_32, tmp_33, tmp_34, tmp_35, tmp_36, tmp_37, tmp_38, tmp_39, tmp_40, tmp_41, tmp_42, tmp_43, tmp_44, tmp_45, tmp_46, tmp_47, tmp_48, tmp_49, tmp_50, tmp_51, tmp_52, tmp_53, tmp_54, tmp_55, tmp_56, tmp_57, tmp_58, tmp_59, tmp_60, tmp_61, tmp_62, tmp_63, tmp_64, tmp_65, tmp_66, new Consonant('\u029F', 'velar lateral approximant', tmp_67, tmp_68, true, VOID, VOID, VOID, tmp$ret$3), new Consonant('ts', 'voiceless alveolar affricate', Place_ALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_HE_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_PL_getInstance(), Lang_RU_getInstance(), Lang_ZH_getInstance()])), new Consonant('dz', 'voiced alveolar affricate', Place_ALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_PL_getInstance()])), new Consonant('t\u0283', 'voiceless postalveolar affricate', Place_POSTALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_IT_getInstance(), Lang_RU_getInstance(), Lang_TR_getInstance()])), new Consonant('d\u0292', 'voiced postalveolar affricate', Place_POSTALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_EN_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_TR_getInstance()])), new Consonant('t\u0255', 'voiceless alveolo-palatal affricate', Place_PALATAL_getInstance(), Manner_AFFRICATE_getInstance(), false, VOID, VOID, VOID, listOf([Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_PL_getInstance(), Lang_ZH_getInstance()])), new Consonant('d\u0291', 'voiced alveolo-palatal affricate', Place_PALATAL_getInstance(), Manner_AFFRICATE_getInstance(), true, VOID, VOID, VOID, listOf([Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_PL_getInstance()])), new Consonant('t\u0255\u02B0', 'aspirated alveolo-palatal affricate', Place_PALATAL_getInstance(), Manner_AFFRICATE_getInstance(), false, true, VOID, VOID, listOf([Lang_KO_getInstance(), Lang_ZH_getInstance()])), new Consonant('s\u02E4', 'emphatic voiceless alveolar fricative', Place_ALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, true, listOf_0(Lang_AR_getInstance())), new Consonant('d\u02E4', 'emphatic voiced alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, true, listOf_0(Lang_AR_getInstance())), new Consonant('t\u02E4', 'emphatic voiceless alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, true, listOf_0(Lang_AR_getInstance())), new Consonant('\xF0\u02E4', 'emphatic voiced dental fricative', Place_DENTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, true, listOf_0(Lang_AR_getInstance())), new Consonant('\u0278', 'voiceless bilabial fricative', Place_BILABIAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf_0(Lang_JA_getInstance())), new Consonant('\u03B2', 'voiced bilabial fricative', Place_BILABIAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf_0(Lang_ES_getInstance()))]);
    var tmp_69 = this;
    var tmp_70 = new Vowel('i', 'close front unrounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_71 = new Vowel('i\u02D0', 'long close front unrounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_HI_getInstance(), Lang_JA_getInstance()]));
    var tmp_72 = new Vowel('y', 'close front rounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_SV_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_73 = new Vowel('y\u02D0', 'long close front rounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), true, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance()]));
    var tmp_74 = new Vowel('\u0268', 'close central unrounded', Height_CLOSE_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf([Lang_KO_getInstance(), Lang_PL_getInstance(), Lang_RU_getInstance()]));
    var tmp_75 = new Vowel('\u0289', 'close central rounded', Height_CLOSE_getInstance(), Backness_CENTRAL_getInstance(), true, VOID, VOID, listOf([Lang_NO_getInstance(), Lang_SV_getInstance()]));
    var tmp_76 = new Vowel('\u026F', 'close back unrounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf([Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_TR_getInstance()]));
    var tmp_77 = new Vowel('u', 'close back rounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_78 = new Vowel('u\u02D0', 'long close back rounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), true, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_HI_getInstance(), Lang_JA_getInstance()]));
    var tmp_79 = new Vowel('\u026A', 'near-close front unrounded', Height_NEAR_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_EN_getInstance(), Lang_HI_getInstance(), Lang_NL_getInstance()]));
    var tmp_80 = new Vowel('\u028F', 'near-close front rounded', Height_NEAR_CLOSE_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_SV_getInstance()]));
    var tmp_81 = new Vowel('\u028A', 'near-close back rounded', Height_NEAR_CLOSE_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_EN_getInstance(), Lang_HI_getInstance(), Lang_NL_getInstance()]));
    var tmp_82 = new Vowel('e', 'close-mid front unrounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]));
    var tmp_83 = new Vowel('e\u02D0', 'long close-mid front unrounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_HI_getInstance(), Lang_JA_getInstance()]));
    var tmp_84 = new Vowel('\xF8', 'close-mid front rounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_SV_getInstance(), Lang_TR_getInstance()]));
    var tmp_85 = new Vowel('\xF8\u02D0', 'long close-mid front rounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), true, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance()]));
    var tmp_86 = Height_CLOSE_MID_getInstance();
    var tmp_87 = Backness_CENTRAL_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$4 = emptyList();
    tmp_69.jm_1 = listOf([tmp_70, tmp_71, tmp_72, tmp_73, tmp_74, tmp_75, tmp_76, tmp_77, tmp_78, tmp_79, tmp_80, tmp_81, tmp_82, tmp_83, tmp_84, tmp_85, new Vowel('\u0258', 'close-mid central unrounded', tmp_86, tmp_87, false, VOID, VOID, tmp$ret$4), new Vowel('\u0275', 'close-mid central rounded', Height_CLOSE_MID_getInstance(), Backness_CENTRAL_getInstance(), true, VOID, VOID, listOf([Lang_NO_getInstance(), Lang_SV_getInstance()])), new Vowel('\u0264', 'close-mid back unrounded', Height_CLOSE_MID_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf([Lang_KO_getInstance(), Lang_ZH_getInstance()])), new Vowel('o', 'close-mid back rounded', Height_CLOSE_MID_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()])), new Vowel('o\u02D0', 'long close-mid back rounded', Height_CLOSE_MID_getInstance(), Backness_BACK_getInstance(), true, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_HI_getInstance(), Lang_JA_getInstance()])), new Vowel('\u0259', 'schwa', Height_MID_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EN_getInstance(), Lang_FR_getInstance(), Lang_HI_getInstance(), Lang_NL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_ZH_getInstance()])), new Vowel('\u025B', 'open-mid front unrounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_KO_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_SV_getInstance(), Lang_TR_getInstance()])), new Vowel('\u025B\u02D0', 'long open-mid front unrounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_HI_getInstance()])), new Vowel('\u0153', 'open-mid front rounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_FR_getInstance(), Lang_NL_getInstance()])), new Vowel('\u0153\u02D0', 'long open-mid front rounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), true, true, VOID, listOf_0(Lang_DE_getInstance())), new Vowel('\u025C', 'open-mid central unrounded', Height_OPEN_MID_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf_0(Lang_EN_getInstance())), new Vowel('\u028C', 'open-mid back unrounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf([Lang_EN_getInstance(), Lang_KO_getInstance()])), new Vowel('\u0254', 'open-mid back rounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_IT_getInstance(), Lang_KO_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_SV_getInstance(), Lang_TR_getInstance()])), new Vowel('\u0254\u02D0', 'long open-mid back rounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), true, true, VOID, listOf([Lang_DE_getInstance(), Lang_EN_getInstance(), Lang_FI_getInstance(), Lang_HI_getInstance()])), new Vowel('\xE6', 'near-open front unrounded', Height_NEAR_OPEN_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf([Lang_DA_getInstance(), Lang_EN_getInstance(), Lang_FI_getInstance(), Lang_NO_getInstance(), Lang_SV_getInstance()])), new Vowel('\u0250', 'near-open central', Height_NEAR_OPEN_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf([Lang_DE_getInstance(), Lang_PT_getInstance()])), new Vowel('a', 'open front unrounded', Height_OPEN_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()])), new Vowel('a\u02D0', 'long open front unrounded', Height_OPEN_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf([Lang_DE_getInstance(), Lang_FI_getInstance(), Lang_HI_getInstance(), Lang_JA_getInstance()])), new Vowel('\u0251', 'open back unrounded', Height_OPEN_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf([Lang_EN_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance()])), new Vowel('\u0251\u02D0', 'long open back unrounded', Height_OPEN_getInstance(), Backness_BACK_getInstance(), false, true, VOID, listOf([Lang_EN_getInstance(), Lang_NL_getInstance()])), new Vowel('\u0252', 'open back rounded', Height_OPEN_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf_0(Lang_EN_getInstance())), new Vowel('\u025B\u0303', 'nasal open-mid front unrounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), false, VOID, true, listOf([Lang_FR_getInstance(), Lang_PT_getInstance()])), new Vowel('\u0153\u0303', 'nasal open-mid front rounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), true, VOID, true, listOf_0(Lang_FR_getInstance())), new Vowel('\u0254\u0303', 'nasal open-mid back rounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), true, VOID, true, listOf([Lang_FR_getInstance(), Lang_PT_getInstance()])), new Vowel('\xE3', 'nasal open front unrounded', Height_OPEN_getInstance(), Backness_FRONT_getInstance(), false, VOID, true, listOf_0(Lang_PT_getInstance())), new Vowel('\u0129', 'nasal close front unrounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, VOID, true, listOf_0(Lang_PT_getInstance())), new Vowel('\u0169', 'nasal close back rounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), true, VOID, true, listOf_0(Lang_PT_getInstance()))]);
    var tmp_88 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = this.im_1;
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$5 = element.cl_1;
      destination.l3(tmp$ret$5, element);
    }
    tmp_88.km_1 = destination;
    var tmp_89 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = this.jm_1;
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$8 = element_0.vl_1;
      destination_0.l3(tmp$ret$8, element_0);
    }
    tmp_89.lm_1 = destination_0;
    this.mm_1 = listOf([Lang_AR_getInstance(), Lang_DA_getInstance(), Lang_DE_getInstance(), Lang_EL_getInstance(), Lang_EN_getInstance(), Lang_ES_getInstance(), Lang_FI_getInstance(), Lang_FR_getInstance(), Lang_HE_getInstance(), Lang_HI_getInstance(), Lang_IT_getInstance(), Lang_JA_getInstance(), Lang_KO_getInstance(), Lang_MS_getInstance(), Lang_NL_getInstance(), Lang_NO_getInstance(), Lang_PL_getInstance(), Lang_PT_getInstance(), Lang_RU_getInstance(), Lang_SV_getInstance(), Lang_SW_getInstance(), Lang_TR_getInstance(), Lang_ZH_getInstance()]);
  }
  protoOf(IpaMatrix).nm = function (ipa) {
    return this.km_1.q1(ipa);
  };
  protoOf(IpaMatrix).om = function (ipa) {
    return this.lm_1.q1(ipa);
  };
  protoOf(IpaMatrix).pm = function (lang) {
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = this.im_1;
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (element.kl_1.j1(lang)) {
        destination.g(element);
      }
    }
    return destination;
  };
  protoOf(IpaMatrix).qm = function (lang) {
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = this.jm_1;
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (element.cm_1.j1(lang)) {
        destination.g(element);
      }
    }
    return destination;
  };
  protoOf(IpaMatrix).rm = function (ipa) {
    var tmp0_safe_receiver = this.km_1.q1(ipa);
    if (tmp0_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      return tmp0_safe_receiver.ql();
    }
    var tmp1_safe_receiver = this.lm_1.q1(ipa);
    if (tmp1_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      return tmp1_safe_receiver.ql();
    }
    return getStringHashCode(ipa) % 360 | 0;
  };
  var IpaMatrix_instance;
  function IpaMatrix_getInstance() {
    if (IpaMatrix_instance == null)
      new IpaMatrix();
    return IpaMatrix_instance;
  }
  function KeyboardLayout(lang, name, nativeName, script, keys) {
    script = script === VOID ? lang.ob_1 : script;
    this.sm_1 = lang;
    this.tm_1 = name;
    this.um_1 = nativeName;
    this.vm_1 = script;
    this.wm_1 = keys;
  }
  protoOf(KeyboardLayout).xm = function () {
    return this.sm_1.nb_1;
  };
  protoOf(KeyboardLayout).toString = function () {
    return 'KeyboardLayout(lang=' + this.sm_1.toString() + ', name=' + this.tm_1 + ', nativeName=' + this.um_1 + ', script=' + this.vm_1.toString() + ', keys=' + toString_1(this.wm_1) + ')';
  };
  protoOf(KeyboardLayout).hashCode = function () {
    var result = this.sm_1.hashCode();
    result = imul(result, 31) + getStringHashCode(this.tm_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.um_1) | 0;
    result = imul(result, 31) + this.vm_1.hashCode() | 0;
    result = imul(result, 31) + hashCode(this.wm_1) | 0;
    return result;
  };
  protoOf(KeyboardLayout).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof KeyboardLayout))
      return false;
    if (!this.sm_1.equals(other.sm_1))
      return false;
    if (!(this.tm_1 === other.tm_1))
      return false;
    if (!(this.um_1 === other.um_1))
      return false;
    if (!this.vm_1.equals(other.vm_1))
      return false;
    if (!equals(this.wm_1, other.wm_1))
      return false;
    return true;
  };
  function LayoutKey_init_$Init$(char, ipa, name, modifiers, $this) {
    LayoutKey.call($this, char, ipa, name, isInterface(modifiers, KtMap) ? modifiers : THROW_CCE());
    return $this;
  }
  function LayoutKey_init_$Create$(char, ipa, name, modifiers) {
    return LayoutKey_init_$Init$(char, ipa, name, modifiers, objectCreate(protoOf(LayoutKey)));
  }
  function Companion_3() {
  }
  var Companion_instance_3;
  function Companion_getInstance_3() {
    return Companion_instance_3;
  }
  function LayoutKey(char, ipa, displayName, modifiers) {
    modifiers = modifiers === VOID ? emptyMap() : modifiers;
    this.ym_1 = char;
    this.zm_1 = ipa;
    this.an_1 = displayName;
    this.bn_1 = modifiers;
  }
  protoOf(LayoutKey).di = function () {
    return this.ym_1;
  };
  protoOf(LayoutKey).ei = function () {
    return this.zm_1;
  };
  protoOf(LayoutKey).fi = function () {
    return this.an_1;
  };
  protoOf(LayoutKey).gi = function () {
    return this.bn_1.q1(Modifier_SHIFT_getInstance());
  };
  protoOf(LayoutKey).toString = function () {
    return 'LayoutKey(char=' + this.ym_1 + ', ipa=' + this.zm_1 + ', displayName=' + this.an_1 + ', modifiers=' + toString_1(this.bn_1) + ')';
  };
  protoOf(LayoutKey).hashCode = function () {
    var result = getStringHashCode(this.ym_1);
    result = imul(result, 31) + getStringHashCode(this.zm_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.an_1) | 0;
    result = imul(result, 31) + hashCode(this.bn_1) | 0;
    return result;
  };
  protoOf(LayoutKey).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof LayoutKey))
      return false;
    if (!(this.ym_1 === other.ym_1))
      return false;
    if (!(this.zm_1 === other.zm_1))
      return false;
    if (!(this.an_1 === other.an_1))
      return false;
    if (!equals(this.bn_1, other.bn_1))
      return false;
    return true;
  };
  var Modifier_SHIFT_instance;
  var Modifier_CTRL_instance;
  var Modifier_ALT_instance;
  var Modifier_CTRL_SHIFT_instance;
  var Modifier_ALT_SHIFT_instance;
  var Modifier_CTRL_ALT_instance;
  var Modifier_entriesInitialized;
  function Modifier_initEntries() {
    if (Modifier_entriesInitialized)
      return Unit_instance;
    Modifier_entriesInitialized = true;
    Modifier_SHIFT_instance = new Modifier('SHIFT', 0);
    Modifier_CTRL_instance = new Modifier('CTRL', 1);
    Modifier_ALT_instance = new Modifier('ALT', 2);
    Modifier_CTRL_SHIFT_instance = new Modifier('CTRL_SHIFT', 3);
    Modifier_ALT_SHIFT_instance = new Modifier('ALT_SHIFT', 4);
    Modifier_CTRL_ALT_instance = new Modifier('CTRL_ALT', 5);
  }
  function Modifier(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function key($this, char, ipa, name, shiftChar, shiftIpa, shiftName) {
    var tmp;
    if (!(shiftChar == null)) {
      var tmp_0 = Modifier_SHIFT_getInstance();
      var tmp_1 = shiftIpa == null ? ipa : shiftIpa;
      tmp = mapOf(to(tmp_0, new LayoutKey(shiftChar, tmp_1, shiftName == null ? 'shift-' + name : shiftName)));
    } else {
      tmp = emptyMap();
    }
    var modifiers = tmp;
    return LayoutKey_init_$Create$(char, ipa, name, modifiers);
  }
  function key_0($this, char, ipa, name) {
    return new LayoutKey(char, ipa, name);
  }
  function buildLatinLayout($this, currency, currencyName, overrides) {
    // Inline function 'kotlin.collections.mutableMapOf' call
    var base = LinkedHashMap_init_$Create$_0();
    var inductionVariable = _Char___init__impl__6a9atx(97);
    if (inductionVariable <= _Char___init__impl__6a9atx(122))
      do {
        var c = inductionVariable;
        inductionVariable = Char__plus_impl_qi7pgj(inductionVariable, 1);
        var lower = toString_0(c);
        // Inline function 'kotlin.text.uppercase' call
        // Inline function 'kotlin.js.asDynamic' call
        var upper = lower.toUpperCase();
        var tmp0_safe_receiver = Companion_getInstance_16().fn(lower);
        var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.kn_1;
        var ipa = tmp1_elvis_lhs == null ? '' : tmp1_elvis_lhs;
        // Inline function 'kotlin.collections.set' call
        var value = key($this, lower, ipa, lower, upper, ipa, upper);
        base.l3(lower, value);
      }
       while (inductionVariable <= _Char___init__impl__6a9atx(122));
    // Inline function 'kotlin.collections.set' call
    var value_0 = key($this, '1', '', 'one', '!', '', 'exclamation');
    base.l3('1', value_0);
    // Inline function 'kotlin.collections.set' call
    var value_1 = key($this, '2', '', 'two', '@', '', 'at');
    base.l3('2', value_1);
    // Inline function 'kotlin.collections.set' call
    var value_2 = key($this, '3', '', 'three', '#', '', 'hash');
    base.l3('3', value_2);
    // Inline function 'kotlin.collections.set' call
    var value_3 = key($this, '4', '', 'four', currency, '', currencyName);
    base.l3('4', value_3);
    // Inline function 'kotlin.collections.set' call
    var value_4 = key($this, '5', '', 'five', '%', '', 'percent');
    base.l3('5', value_4);
    // Inline function 'kotlin.collections.set' call
    var value_5 = key($this, '6', '', 'six', '^', '', 'caret');
    base.l3('6', value_5);
    // Inline function 'kotlin.collections.set' call
    var value_6 = key($this, '7', '', 'seven', '&', '', 'ampersand');
    base.l3('7', value_6);
    // Inline function 'kotlin.collections.set' call
    var value_7 = key($this, '8', '', 'eight', '*', '', 'asterisk');
    base.l3('8', value_7);
    // Inline function 'kotlin.collections.set' call
    var value_8 = key($this, '9', '', 'nine', '(', '', 'left-paren');
    base.l3('9', value_8);
    // Inline function 'kotlin.collections.set' call
    var value_9 = key($this, '0', '', 'zero', ')', '', 'right-paren');
    base.l3('0', value_9);
    base.m3(overrides);
    return base;
  }
  function KeyboardLayouts() {
    KeyboardLayouts_instance = this;
    this.mn_1 = listOf(['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p']);
    this.nn_1 = listOf(['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l']);
    this.on_1 = listOf(['z', 'x', 'c', 'v', 'b', 'n', 'm']);
    this.pn_1 = new KeyboardLayout(Lang_AR_getInstance(), 'Arabic', '\u0627\u0644\u0639\u0631\u0628\u064A\u0629', VOID, Companion_getInstance_6().r1());
    this.qn_1 = new KeyboardLayout(Lang_DA_getInstance(), 'Danish', 'Dansk', VOID, buildLatinLayout(this, 'kr', 'krone', mapOf_0([to(';', key(this, '\xE6', '\u025B', 'ae', '\xC6', '\u025B', 'AE')), to("'", key(this, '\xF8', '\xF8', 'oe', '\xD8', '\xF8', 'OE')), to('[', key(this, '\xE5', '\u0254', 'aa', '\xC5', '\u0254', 'AA'))])));
    this.rn_1 = new KeyboardLayout(Lang_DE_getInstance(), 'German', 'Deutsch', VOID, buildLatinLayout(this, '\u20AC', 'euro', mapOf_0([to(';', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut')), to("'", key(this, '\xE4', '\u025B', 'a-umlaut', '\xC4', '\u025B', 'A-umlaut')), to('[', key(this, '\xFC', 'y', 'u-umlaut', '\xDC', 'y', 'U-umlaut')), to(']', key(this, '\xDF', 's', 'eszett', '\u1E9E', 's', 'Eszett'))])));
    this.sn_1 = new KeyboardLayout(Lang_EL_getInstance(), 'Greek', '\u0395\u03BB\u03BB\u03B7\u03BD\u03B9\u03BA\u03AC', VOID, Companion_getInstance_9().hp_1);
    this.tn_1 = new KeyboardLayout(Lang_EN_getInstance(), 'English (US)', 'English', VOID, buildLatinLayout(this, '$', 'dollar', emptyMap()));
    this.un_1 = new KeyboardLayout(Lang_EN_GB_getInstance(), 'English (UK)', 'English', VOID, buildLatinLayout(this, '\xA3', 'pound', mapOf(to('3', key(this, '3', '', 'three', '\xA3', '', 'pound')))));
    this.vn_1 = new KeyboardLayout(Lang_ES_getInstance(), 'Spanish', 'Espa\xF1ol', VOID, buildLatinLayout(this, '\u20AC', 'euro', mapOf_0([to(';', key(this, '\xF1', '\u0272', 'ene', '\xD1', '\u0272', 'Ene')), to("'", key(this, '\xB4', '', 'acute', '\xA8', '', 'diaeresis')), to('[', key(this, '\xBF', '', 'inverted-question', '\xA1', '', 'inverted-exclamation'))])));
    this.wn_1 = new KeyboardLayout(Lang_FI_getInstance(), 'Finnish', 'Suomi', VOID, buildLatinLayout(this, '\u20AC', 'euro', mapOf_0([to(';', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut')), to("'", key(this, '\xE4', '\xE6', 'a-umlaut', '\xC4', '\xE6', 'A-umlaut'))])));
    this.xn_1 = new KeyboardLayout(Lang_FR_getInstance(), 'French', 'Fran\xE7ais', VOID, buildLatinLayout(this, '\u20AC', 'euro', mapOf_0([to(';', key(this, '\xE9', 'e', 'e-acute', '\xC9', 'e', 'E-acute')), to("'", key(this, '\xE8', '\u025B', 'e-grave', '\xC8', '\u025B', 'E-grave')), to('[', key(this, '\xE7', 's', 'c-cedilla', '\xC7', 's', 'C-cedilla')), to(']', key(this, '\xE0', 'a', 'a-grave', '\xC0', 'a', 'A-grave'))])));
    this.yn_1 = new KeyboardLayout(Lang_HE_getInstance(), 'Hebrew', '\u05E2\u05D1\u05E8\u05D9\u05EA', VOID, plus(Companion_getInstance_0().bj_1, mapOf(to('4', key(this, '\u20AA', '', 'shekel', '$', '', 'dollar')))));
    this.zn_1 = new KeyboardLayout(Lang_HI_getInstance(), 'Hindi', '\u0939\u093F\u0928\u094D\u0926\u0940', VOID, mapOf_0([to('q', key(this, '\u0914', '\u0254\u02D0', 'au', '\u094C', '\u0254\u02D0', 'au-matra')), to('w', key(this, '\u0910', '\u025B\u02D0', 'ai', '\u0948', '\u025B\u02D0', 'ai-matra')), to('e', key(this, '\u0906', 'a\u02D0', 'aa', '\u093E', 'a\u02D0', 'aa-matra')), to('r', key(this, '\u0908', 'i\u02D0', 'ii', '\u0940', 'i\u02D0', 'ii-matra')), to('t', key(this, '\u090A', 'u\u02D0', 'uu', '\u0942', 'u\u02D0', 'uu-matra')), to('y', key_0(this, '\u092D', 'b\u02B1', 'bha')), to('u', key_0(this, '\u0919', '\u014B', 'nga')), to('i', key_0(this, '\u0918', '\u0261\u02B1', 'gha')), to('o', key_0(this, '\u0927', 'd\u02B1', 'dha')), to('p', key_0(this, '\u091D', 'd\u0292\u02B1', 'jha')), to('a', key(this, '\u0913', 'o\u02D0', 'o', '\u094B', 'o\u02D0', 'o-matra')), to('s', key(this, '\u090F', 'e\u02D0', 'e', '\u0947', 'e\u02D0', 'e-matra')), to('d', key_0(this, '\u0905', '\u0259', 'a')), to('f', key(this, '\u0907', '\u026A', 'i', '\u093F', '\u026A', 'i-matra')), to('g', key(this, '\u0909', '\u028A', 'u', '\u0941', '\u028A', 'u-matra')), to('h', key_0(this, '\u092A', 'p', 'pa')), to('j', key(this, '\u0930', 'r', 'ra', '\u094D', '', 'virama')), to('k', key_0(this, '\u0915', 'k', 'ka')), to('l', key_0(this, '\u0924', 't', 'ta')), to('z', key_0(this, '\u0949', '\u0254', 'candra-o')), to('x', key_0(this, '\u0901', '\u0303', 'chandrabindu')), to('c', key_0(this, '\u092E', 'm', 'ma')), to('v', key_0(this, '\u0928', 'n', 'na')), to('b', key_0(this, '\u092C', 'b', 'ba')), to('n', key_0(this, '\u0932', 'l', 'la')), to('m', key_0(this, '\u0938', 's', 'sa')), to(',', key(this, '\u0935', '\u028B', 'va', '\u0943', '\u027B', 'ri-matra')), to('.', key_0(this, '\u092F', 'j', 'ya')), to('/', key_0(this, '\u091C', 'd\u0292', 'ja')), to('4', key(this, '\u20B9', '', 'rupee', '$', '', 'dollar'))]));
    this.ao_1 = new KeyboardLayout(Lang_IT_getInstance(), 'Italian', 'Italiano', VOID, buildLatinLayout(this, '\u20AC', 'euro', mapOf_0([to(';', key(this, '\xF2', '\u0254', 'o-grave', '\xD2', '\u0254', 'O-grave')), to("'", key(this, '\xE0', 'a', 'a-grave', '\xC0', 'a', 'A-grave')), to('[', key(this, '\xE8', '\u025B', 'e-grave', '\xC8', '\u025B', 'E-grave')), to(']', key(this, '\xF9', 'u', 'u-grave', '\xD9', 'u', 'U-grave')), to('\\', key(this, '\xEC', 'i', 'i-grave', '\xCC', 'i', 'I-grave'))])));
    this.bo_1 = new KeyboardLayout(Lang_JA_getInstance(), 'Japanese', '\u65E5\u672C\u8A9E', VOID, plus(Companion_getInstance_14().mp_1, mapOf_0([to('l', key_0(this, '\u30FC', '\u02D0', 'long-vowel')), to('q', key_0(this, '\u3002', '', 'period')), to('/', key_0(this, '\u30FB', '', 'middle-dot')), to('4', key(this, '\xA5', '', 'yen', '$', '', 'dollar'))])));
    this.co_1 = new KeyboardLayout(Lang_KO_getInstance(), 'Korean', '\uD55C\uAD6D\uC5B4', VOID, mapOf_0([to('q', key(this, '\u3142', 'p', 'bieup', '\u3143', 'p\u0348', 'ssang-bieup')), to('w', key(this, '\u3148', 't\u0255', 'jieut', '\u3149', 't\u0348\u0255', 'ssang-jieut')), to('e', key(this, '\u3137', 't', 'digeut', '\u3138', 't\u0348', 'ssang-digeut')), to('r', key(this, '\u3131', 'k', 'giyeok', '\u3132', 'k\u0348', 'ssang-giyeok')), to('t', key(this, '\u3145', 's', 'siot', '\u3146', 's\u0348', 'ssang-siot')), to('a', key_0(this, '\u3141', 'm', 'mieum')), to('s', key_0(this, '\u3134', 'n', 'nieun')), to('d', key_0(this, '\u3147', '\u014B', 'ieung')), to('f', key_0(this, '\u3139', '\u027E', 'rieul')), to('g', key_0(this, '\u314E', 'h', 'hieut')), to('z', key_0(this, '\u314B', 'k\u02B0', 'kieuk')), to('x', key_0(this, '\u314C', 't\u02B0', 'tieut')), to('c', key_0(this, '\u314A', 't\u0255\u02B0', 'chieut')), to('v', key_0(this, '\u314D', 'p\u02B0', 'pieup')), to('y', key_0(this, '\u315B', 'jo', 'yo')), to('u', key_0(this, '\u3155', 'j\u028C', 'yeo')), to('i', key_0(this, '\u3151', 'ja', 'ya')), to('o', key(this, '\u3150', '\u025B', 'ae', '\u3152', 'j\u025B', 'yae')), to('p', key(this, '\u3154', 'e', 'e', '\u3156', 'je', 'ye')), to('h', key_0(this, '\u3157', 'o', 'o')), to('j', key_0(this, '\u3153', '\u028C', 'eo')), to('k', key_0(this, '\u314F', 'a', 'a')), to('l', key_0(this, '\u3163', 'i', 'i')), to('b', key_0(this, '\u3160', 'ju', 'yu')), to('n', key_0(this, '\u315C', 'u', 'u')), to('m', key_0(this, '\u3161', '\u0268', 'eu')), to('4', key(this, '\u20A9', '', 'won', '$', '', 'dollar'))]));
    this.do_1 = new KeyboardLayout(Lang_MS_getInstance(), 'Malay', 'Bahasa Melayu', VOID, buildLatinLayout(this, 'RM', 'ringgit', emptyMap()));
    this.eo_1 = new KeyboardLayout(Lang_NL_getInstance(), 'Dutch', 'Nederlands', VOID, buildLatinLayout(this, '\u20AC', 'euro', mapOf_0([to("'", key(this, '\xB4', '', 'acute', '\xA8', '', 'diaeresis')), to('[', key(this, 'ij', '\u025Bi', 'ij', 'IJ', '\u025Bi', 'IJ'))])));
    this.fo_1 = new KeyboardLayout(Lang_NO_getInstance(), 'Norwegian', 'Norsk', VOID, buildLatinLayout(this, 'kr', 'krone', mapOf_0([to(';', key(this, '\xF8', '\xF8', 'oe', '\xD8', '\xF8', 'OE')), to("'", key(this, '\xE6', '\xE6', 'ae', '\xC6', '\xE6', 'AE')), to('[', key(this, '\xE5', '\u0254', 'aa', '\xC5', '\u0254', 'AA'))])));
    this.go_1 = new KeyboardLayout(Lang_PL_getInstance(), 'Polish', 'Polski', VOID, buildLatinLayout(this, 'z\u0142', 'zloty', mapOf_0([to('a', key(this, 'a', 'a', 'a', '\u0105', '\u0254\u0303', 'a-ogonek')), to('c', key(this, 'c', 'ts', 'c', '\u0107', 't\u0255', 'c-acute')), to('e', key(this, 'e', 'e', 'e', '\u0119', '\u025B\u0303', 'e-ogonek')), to('l', key(this, 'l', 'l', 'l', '\u0142', 'w', 'l-stroke')), to('n', key(this, 'n', 'n', 'n', '\u0144', '\u0272', 'n-acute')), to('o', key(this, 'o', 'o', 'o', '\xF3', 'u', 'o-acute')), to('s', key(this, 's', 's', 's', '\u015B', '\u0255', 's-acute')), to('z', key(this, 'z', 'z', 'z', '\u017C', '\u0290', 'z-dot')), to('x', key(this, 'x', 'ks', 'x', '\u017A', '\u0291', 'z-acute'))])));
    this.ho_1 = new KeyboardLayout(Lang_PT_getInstance(), 'Portuguese', 'Portugu\xEAs', VOID, buildLatinLayout(this, '\u20AC', 'euro', mapOf_0([to(';', key(this, '\xE7', 's', 'c-cedilla', '\xC7', 's', 'C-cedilla')), to("'", key(this, '~', '', 'tilde', '^', '', 'circumflex')), to('[', key(this, '\xB4', '', 'acute', '`', '', 'grave')), to(']', key(this, '\xE3', '\u0250\u0303', 'a-tilde', '\xC3', '\u0250\u0303', 'A-tilde'))])));
    this.io_1 = new KeyboardLayout(Lang_PT_BR_getInstance(), 'Portuguese (Brazil)', 'Portugu\xEAs (Brasil)', VOID, buildLatinLayout(this, 'R$', 'real', mapOf_0([to(';', key(this, '\xE7', 's', 'c-cedilla', '\xC7', 's', 'C-cedilla')), to("'", key(this, '~', '', 'tilde', '^', '', 'circumflex')), to('[', key(this, '\xB4', '', 'acute', '`', '', 'grave')), to(']', key(this, '\xE3', '\u0250\u0303', 'a-tilde', '\xC3', '\u0250\u0303', 'A-tilde'))])));
    this.jo_1 = new KeyboardLayout(Lang_RU_getInstance(), 'Russian', '\u0420\u0443\u0441\u0441\u043A\u0438\u0439', VOID, plus(Companion_getInstance_10().qp_1, mapOf(to('4', key(this, '\u20BD', '', 'ruble', '$', '', 'dollar')))));
    this.ko_1 = new KeyboardLayout(Lang_SV_getInstance(), 'Swedish', 'Svenska', VOID, buildLatinLayout(this, 'kr', 'krona', mapOf_0([to(';', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut')), to("'", key(this, '\xE4', '\u025B', 'a-umlaut', '\xC4', '\u025B', 'A-umlaut')), to('[', key(this, '\xE5', '\u0254', 'a-ring', '\xC5', '\u0254', 'A-ring'))])));
    this.lo_1 = new KeyboardLayout(Lang_SW_getInstance(), 'Swahili', 'Kiswahili', VOID, buildLatinLayout(this, 'TSh', 'shilling', emptyMap()));
    this.mo_1 = new KeyboardLayout(Lang_TR_getInstance(), 'Turkish', 'T\xFCrk\xE7e', VOID, buildLatinLayout(this, '\u20BA', 'lira', mapOf_0([to('i', key(this, '\u0131', '\u026F', 'dotless-i', 'I', '\u026F', 'Dotless-I')), to(';', key(this, '\u015F', '\u0283', 's-cedilla', '\u015E', '\u0283', 'S-cedilla')), to("'", key(this, 'i', 'i', 'dotted-i', '\u0130', 'i', 'Dotted-I')), to('[', key(this, '\u011F', '\u0263', 'g-breve', '\u011E', '\u0263', 'G-breve')), to(']', key(this, '\xFC', 'y', 'u-umlaut', '\xDC', 'y', 'U-umlaut')), to('\\', key(this, '\xE7', 't\u0283', 'c-cedilla', '\xC7', 't\u0283', 'C-cedilla')), to('/', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut'))])));
    this.no_1 = new KeyboardLayout(Lang_ZH_getInstance(), 'Chinese', '\u4E2D\u6587', VOID, mapOf_0([to('b', key_0(this, 'b', 'p', 'bo')), to('p', key_0(this, 'p', 'p\u02B0', 'po')), to('m', key_0(this, 'm', 'm', 'mo')), to('f', key_0(this, 'f', 'f', 'fo')), to('d', key_0(this, 'd', 't', 'de')), to('t', key_0(this, 't', 't\u02B0', 'te')), to('n', key_0(this, 'n', 'n', 'ne')), to('l', key_0(this, 'l', 'l', 'le')), to('g', key_0(this, 'g', 'k', 'ge')), to('k', key_0(this, 'k', 'k\u02B0', 'ke')), to('h', key_0(this, 'h', 'x', 'he')), to('j', key_0(this, 'j', 't\u0255', 'ji')), to('q', key_0(this, 'q', 't\u0255\u02B0', 'qi')), to('x', key_0(this, 'x', '\u0255', 'xi')), to('z', key_0(this, 'z', 'ts', 'zi')), to('c', key_0(this, 'c', 'ts\u02B0', 'ci')), to('s', key_0(this, 's', 's', 'si')), to('r', key_0(this, 'r', '\u027B', 'ri')), to('y', key_0(this, 'y', 'j', 'yi')), to('w', key_0(this, 'w', 'w', 'wu')), to('a', key_0(this, 'a', 'a', 'a')), to('o', key_0(this, 'o', 'o', 'o')), to('e', key_0(this, 'e', '\u0259', 'e')), to('i', key_0(this, 'i', 'i', 'i')), to('u', key_0(this, 'u', 'u', 'u')), to('v', key_0(this, '\xFC', 'y', 'u-umlaut')), to('4', key(this, '\xA5', '', 'yuan', '$', '', 'dollar'))]));
    this.oo_1 = new KeyboardLayout(Lang_EN_AU_getInstance(), 'English (Australia)', 'English', VOID, buildLatinLayout(this, '$', 'dollar', emptyMap()));
    this.po_1 = new KeyboardLayout(Lang_EN_CA_getInstance(), 'English (Canada)', 'English', VOID, buildLatinLayout(this, '$', 'dollar', emptyMap()));
    this.qo_1 = new KeyboardLayout(Lang_EN_IN_getInstance(), 'English (India)', 'English', VOID, buildLatinLayout(this, '\u20B9', 'rupee', emptyMap()));
    this.ro_1 = new KeyboardLayout(Lang_ES_419_getInstance(), 'Spanish (Latin America)', 'Espa\xF1ol (Latinoam\xE9rica)', VOID, buildLatinLayout(this, '$', 'dollar', mapOf_0([to(';', key(this, '\xF1', '\u0272', 'ene', '\xD1', '\u0272', 'Ene')), to("'", key(this, '\xB4', '', 'acute', '\xA8', '', 'diaeresis')), to('[', key(this, '\xBF', '', 'inverted-question', '\xA1', '', 'inverted-exclamation'))])));
    this.so_1 = listOf([this.pn_1, this.qn_1, this.rn_1, this.sn_1, this.tn_1, this.vn_1, this.wn_1, this.xn_1, this.yn_1, this.zn_1, this.ao_1, this.bo_1, this.co_1, this.do_1, this.eo_1, this.fo_1, this.go_1, this.ho_1, this.jo_1, this.ko_1, this.lo_1, this.mo_1, this.no_1, this.un_1, this.oo_1, this.po_1, this.qo_1, this.io_1, this.ro_1]);
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = this.so_1;
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.sm_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.to_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = this.so_1;
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$3 = element_0.xm();
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_0.uo_1 = plus(destination_0, mapOf(to('en-us', this.tn_1)));
    this.vo_1 = Companion_getInstance().sb();
    var tmp_1 = this;
    // Inline function 'kotlin.collections.map' call
    var this_2 = this.vo_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_2, 10));
    var _iterator__ex2g4s_1 = this_2.i();
    while (_iterator__ex2g4s_1.j()) {
      var item = _iterator__ex2g4s_1.k();
      var tmp$ret$6 = item.nb_1;
      destination_1.g(tmp$ret$6);
    }
    tmp_1.wo_1 = sorted(destination_1);
    this.xo_1 = sorted(toList(this.uo_1.r1()));
  }
  protoOf(KeyboardLayouts).j8 = function (code) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = code.toLowerCase();
    return this.uo_1.q1(tmp$ret$1);
  };
  var KeyboardLayouts_instance;
  function KeyboardLayouts_getInstance() {
    if (KeyboardLayouts_instance == null)
      new KeyboardLayouts();
    return KeyboardLayouts_instance;
  }
  function Modifier_SHIFT_getInstance() {
    Modifier_initEntries();
    return Modifier_SHIFT_instance;
  }
  function get_arabicShiftMap() {
    _init_properties_MorphoEnum_kt__kaqzu0();
    return arabicShiftMap;
  }
  var arabicShiftMap;
  var NikudVowel_KAMATZ_instance;
  var NikudVowel_PATACH_instance;
  var NikudVowel_HATAF_PATACH_instance;
  var NikudVowel_TZERE_instance;
  var NikudVowel_SEGOL_instance;
  var NikudVowel_HATAF_SEGOL_instance;
  var NikudVowel_CHIRIK_instance;
  var NikudVowel_CHIRIK_MALE_instance;
  var NikudVowel_CHOLAM_instance;
  var NikudVowel_CHOLAM_MALE_instance;
  var NikudVowel_HATAF_KAMATZ_instance;
  var NikudVowel_KAMATZ_KATAN_instance;
  var NikudVowel_KUBUTZ_instance;
  var NikudVowel_SHURUK_instance;
  var NikudVowel_SHVA_instance;
  var NikudVowel_SHVA_NA_instance;
  function Companion_4() {
    Companion_instance_4 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.buildMap' call
    // Inline function 'kotlin.collections.buildMapInternal' call
    // Inline function 'kotlin.apply' call
    var this_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = get_entries_3().i();
    while (_iterator__ex2g4s.j()) {
      var v = _iterator__ex2g4s.k();
      if (!this_0.r1().j1(v.nk_1)) {
        this_0.l3(v.nk_1, v);
      }
    }
    tmp.rp_1 = this_0.w6();
  }
  protoOf(Companion_4).sp = function (cp) {
    return this.rp_1.q1(cp);
  };
  protoOf(Companion_4).tp = function (ipa) {
    switch (ipa) {
      case 'a':
        return listOf([NikudVowel_PATACH_getInstance(), NikudVowel_KAMATZ_getInstance(), NikudVowel_HATAF_PATACH_getInstance()]);
      case 'e':
        return listOf([NikudVowel_TZERE_getInstance(), NikudVowel_SEGOL_getInstance(), NikudVowel_HATAF_SEGOL_getInstance()]);
      case '\u025B':
        return listOf([NikudVowel_SEGOL_getInstance(), NikudVowel_HATAF_SEGOL_getInstance()]);
      case 'i':
        return listOf([NikudVowel_CHIRIK_getInstance(), NikudVowel_CHIRIK_MALE_getInstance()]);
      case 'o':
        return listOf([NikudVowel_CHOLAM_getInstance(), NikudVowel_CHOLAM_MALE_getInstance(), NikudVowel_KAMATZ_getInstance(), NikudVowel_HATAF_KAMATZ_getInstance()]);
      case 'u':
        return listOf([NikudVowel_KUBUTZ_getInstance(), NikudVowel_SHURUK_getInstance()]);
      case '\u0259':
        return listOf_0(NikudVowel_SHVA_getInstance());
      case '\u026A':
        return listOf_0(NikudVowel_CHIRIK_getInstance());
      case '\u028A':
        return listOf_0(NikudVowel_KUBUTZ_getInstance());
      case '\xE6':
        return listOf([NikudVowel_SEGOL_getInstance(), NikudVowel_PATACH_getInstance()]);
      case '\u028C':
        return listOf_0(NikudVowel_PATACH_getInstance());
      case '\u0254':
        return listOf_0(NikudVowel_KAMATZ_getInstance());
      case '\u0251':
        return listOf_0(NikudVowel_KAMATZ_getInstance());
      default:
        return emptyList();
    }
  };
  protoOf(Companion_4).up = function (letter, ipa, useMale) {
    var nikudList = this.tp(ipa);
    if (nikudList.n())
      return toString_0(letter);
    var gutturals = setOf([new Char(_Char___init__impl__6a9atx(1488)), new Char(_Char___init__impl__6a9atx(1492)), new Char(_Char___init__impl__6a9atx(1495)), new Char(_Char___init__impl__6a9atx(1506)), new Char(_Char___init__impl__6a9atx(1512))]);
    if (gutturals.j1(new Char(letter)) && ipa === '\u0259') {
      return toString_0(letter) + NikudVowel_HATAF_PATACH_getInstance().lk_1;
    }
    var nikud = first(nikudList);
    if (useMale) {
      switch (ipa) {
        case 'i':
          return toString_0(letter) + NikudVowel_CHIRIK_MALE_getInstance().lk_1;
        case 'o':
          return toString_0(letter) + NikudVowel_CHOLAM_MALE_getInstance().lk_1;
        case 'u':
          return toString_0(letter) + NikudVowel_SHURUK_getInstance().lk_1;
        default:
          return toString_0(letter) + nikud.lk_1;
      }
    }
    return toString_0(letter) + nikud.lk_1;
  };
  var Companion_instance_4;
  function Companion_getInstance_4() {
    NikudVowel_initEntries();
    if (Companion_instance_4 == null)
      new Companion_4();
    return Companion_instance_4;
  }
  function values_3() {
    return [NikudVowel_KAMATZ_getInstance(), NikudVowel_PATACH_getInstance(), NikudVowel_HATAF_PATACH_getInstance(), NikudVowel_TZERE_getInstance(), NikudVowel_SEGOL_getInstance(), NikudVowel_HATAF_SEGOL_getInstance(), NikudVowel_CHIRIK_getInstance(), NikudVowel_CHIRIK_MALE_getInstance(), NikudVowel_CHOLAM_getInstance(), NikudVowel_CHOLAM_MALE_getInstance(), NikudVowel_HATAF_KAMATZ_getInstance(), NikudVowel_KAMATZ_KATAN_getInstance(), NikudVowel_KUBUTZ_getInstance(), NikudVowel_SHURUK_getInstance(), NikudVowel_SHVA_getInstance(), NikudVowel_SHVA_NA_getInstance()];
  }
  function get_entries_3() {
    if ($ENTRIES_3 == null)
      $ENTRIES_3 = enumEntries(values_3());
    return $ENTRIES_3;
  }
  var NikudVowel_entriesInitialized;
  function NikudVowel_initEntries() {
    if (NikudVowel_entriesInitialized)
      return Unit_instance;
    NikudVowel_entriesInitialized = true;
    NikudVowel_KAMATZ_instance = new NikudVowel('KAMATZ', 0, '\u05B8', 'a', 1464, 0, false);
    NikudVowel_PATACH_instance = new NikudVowel('PATACH', 1, '\u05B7', 'a', 1463, 15, true);
    NikudVowel_HATAF_PATACH_instance = new NikudVowel('HATAF_PATACH', 2, '\u05B2', 'a', 1458, 30, true);
    NikudVowel_TZERE_instance = new NikudVowel('TZERE', 3, '\u05B5', 'e', 1461, 60, false);
    NikudVowel_SEGOL_instance = new NikudVowel('SEGOL', 4, '\u05B6', '\u025B', 1462, 75, true);
    NikudVowel_HATAF_SEGOL_instance = new NikudVowel('HATAF_SEGOL', 5, '\u05B1', '\u025B', 1457, 90, true);
    NikudVowel_CHIRIK_instance = new NikudVowel('CHIRIK', 6, '\u05B4', 'i', 1460, 120, true);
    NikudVowel_CHIRIK_MALE_instance = new NikudVowel('CHIRIK_MALE', 7, '\u05B4\u05D9', 'i\u02D0', 1460, 135, false);
    NikudVowel_CHOLAM_instance = new NikudVowel('CHOLAM', 8, '\u05B9', 'o', 1465, 200, false);
    NikudVowel_CHOLAM_MALE_instance = new NikudVowel('CHOLAM_MALE', 9, '\u05D5\u05B9', 'o\u02D0', 1465, 210, false);
    NikudVowel_HATAF_KAMATZ_instance = new NikudVowel('HATAF_KAMATZ', 10, '\u05B3', 'o', 1459, 220, true);
    NikudVowel_KAMATZ_KATAN_instance = new NikudVowel('KAMATZ_KATAN', 11, '\u05B8', 'o', 1464, 230, true);
    NikudVowel_KUBUTZ_instance = new NikudVowel('KUBUTZ', 12, '\u05BB', 'u', 1467, 280, true);
    NikudVowel_SHURUK_instance = new NikudVowel('SHURUK', 13, '\u05D5\u05BC', 'u\u02D0', 1468, 290, false);
    NikudVowel_SHVA_instance = new NikudVowel('SHVA', 14, '\u05B0', '\u0259', 1456, 0, true);
    NikudVowel_SHVA_NA_instance = new NikudVowel('SHVA_NA', 15, '\u05B0', 'e', 1456, 60, true);
    Companion_getInstance_4();
  }
  var $ENTRIES_3;
  function NikudVowel(name, ordinal, mark, ipa, unicode, hue, isShort) {
    Enum.call(this, name, ordinal);
    this.lk_1 = mark;
    this.mk_1 = ipa;
    this.nk_1 = unicode;
    this.ok_1 = hue;
    this.pk_1 = isShort;
  }
  protoOf(NikudVowel).ei = function () {
    return this.mk_1;
  };
  protoOf(NikudVowel).di = function () {
    return this.lk_1;
  };
  protoOf(NikudVowel).fi = function () {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.u1_1.toLowerCase();
    return replace_0(tmp$ret$1, _Char___init__impl__6a9atx(95), _Char___init__impl__6a9atx(45));
  };
  protoOf(NikudVowel).gj = function () {
    return this.mk_1 === '\u0259' ? '@' : this.mk_1 === '\u025B' && this.equals(NikudVowel_SEGOL_getInstance()) ? 'E' : this.mk_1 === '\u025B' ? 'e' : take(this.mk_1, 1);
  };
  var HebrewBgdkpt_BET_instance;
  var HebrewBgdkpt_GIMEL_instance;
  var HebrewBgdkpt_DALET_instance;
  var HebrewBgdkpt_KAF_instance;
  var HebrewBgdkpt_PE_instance;
  var HebrewBgdkpt_TAV_instance;
  function Companion_5() {
    Companion_instance_5 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_4();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = new Char(element.xp_1);
      destination.l3(tmp$ret$0, element);
    }
    tmp.eq_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_4();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_0 = tmp0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      if (element_0.gq()) {
        destination_0.g(element_0);
      }
    }
    tmp_0.fq_1 = destination_0;
  }
  protoOf(Companion_5).cj = function (c) {
    return this.eq_1.q1(new Char(c));
  };
  protoOf(Companion_5).hq = function (c) {
    return this.eq_1.o1(new Char(c));
  };
  var Companion_instance_5;
  function Companion_getInstance_5() {
    HebrewBgdkpt_initEntries();
    if (Companion_instance_5 == null)
      new Companion_5();
    return Companion_instance_5;
  }
  function values_4() {
    return [HebrewBgdkpt_BET_getInstance(), HebrewBgdkpt_GIMEL_getInstance(), HebrewBgdkpt_DALET_getInstance(), HebrewBgdkpt_KAF_getInstance(), HebrewBgdkpt_PE_getInstance(), HebrewBgdkpt_TAV_getInstance()];
  }
  function get_entries_4() {
    if ($ENTRIES_4 == null)
      $ENTRIES_4 = enumEntries(values_4());
    return $ENTRIES_4;
  }
  var HebrewBgdkpt_entriesInitialized;
  function HebrewBgdkpt_initEntries() {
    if (HebrewBgdkpt_entriesInitialized)
      return Unit_instance;
    HebrewBgdkpt_entriesInitialized = true;
    HebrewBgdkpt_BET_instance = new HebrewBgdkpt('BET', 0, _Char___init__impl__6a9atx(1489), '\u05D1\u05BC', 'b', '\u05D1', 'v', null, null);
    HebrewBgdkpt_GIMEL_instance = new HebrewBgdkpt('GIMEL', 1, _Char___init__impl__6a9atx(1490), '\u05D2\u05BC', 'g', '\u05D2', 'g', '\u05D2', '\u0263');
    HebrewBgdkpt_DALET_instance = new HebrewBgdkpt('DALET', 2, _Char___init__impl__6a9atx(1491), '\u05D3\u05BC', 'd', '\u05D3', 'd', '\u05D3', '\xF0');
    HebrewBgdkpt_KAF_instance = new HebrewBgdkpt('KAF', 3, _Char___init__impl__6a9atx(1499), '\u05DB\u05BC', 'k', '\u05DB', 'x', null, null);
    HebrewBgdkpt_PE_instance = new HebrewBgdkpt('PE', 4, _Char___init__impl__6a9atx(1508), '\u05E4\u05BC', 'p', '\u05E4', 'f', null, null);
    HebrewBgdkpt_TAV_instance = new HebrewBgdkpt('TAV', 5, _Char___init__impl__6a9atx(1514), '\u05EA\u05BC', 't', '\u05EA', 't', '\u05EA', '\u03B8');
    Companion_getInstance_5();
  }
  var $ENTRIES_4;
  function HebrewBgdkpt(name, ordinal, letter, withDagesh, ipaDagesh, withoutDagesh, ipaWithout, classical, classicalIpa) {
    classical = classical === VOID ? null : classical;
    classicalIpa = classicalIpa === VOID ? null : classicalIpa;
    Enum.call(this, name, ordinal);
    this.xp_1 = letter;
    this.yp_1 = withDagesh;
    this.zp_1 = ipaDagesh;
    this.aq_1 = withoutDagesh;
    this.bq_1 = ipaWithout;
    this.cq_1 = classical;
    this.dq_1 = classicalIpa;
  }
  protoOf(HebrewBgdkpt).gq = function () {
    return !(this.zp_1 === this.bq_1);
  };
  protoOf(HebrewBgdkpt).iq = function (hasDagesh, useClassical) {
    return hasDagesh ? this.zp_1 : useClassical && !(this.dq_1 == null) ? this.dq_1 : this.bq_1;
  };
  protoOf(HebrewBgdkpt).jq = function (hasDagesh) {
    return hasDagesh ? this.yp_1 : this.aq_1;
  };
  function ArabicLetter$Companion$keys$delegate$lambda() {
    return buildArabicKeys();
  }
  function ArabicLetter$Companion$_get_keys_$ref_2x7c5s() {
    return function (p0) {
      return p0.r1();
    };
  }
  var ArabicLetter_ALIF_instance;
  var ArabicLetter_BA_instance;
  var ArabicLetter_TA_instance;
  var ArabicLetter_THA_instance;
  var ArabicLetter_JEEM_instance;
  var ArabicLetter_HA_instance;
  var ArabicLetter_KHA_instance;
  var ArabicLetter_DAL_instance;
  var ArabicLetter_DHAL_instance;
  var ArabicLetter_RA_instance;
  var ArabicLetter_ZAY_instance;
  var ArabicLetter_SEEN_instance;
  var ArabicLetter_SHEEN_instance;
  var ArabicLetter_SAD_instance;
  var ArabicLetter_DAD_instance;
  var ArabicLetter_TAA_instance;
  var ArabicLetter_ZAA_instance;
  var ArabicLetter_AIN_instance;
  var ArabicLetter_GHAIN_instance;
  var ArabicLetter_FA_instance;
  var ArabicLetter_QAF_instance;
  var ArabicLetter_KAF_instance;
  var ArabicLetter_LAM_instance;
  var ArabicLetter_MEEM_instance;
  var ArabicLetter_NOON_instance;
  var ArabicLetter_HA_END_instance;
  var ArabicLetter_WAW_instance;
  var ArabicLetter_YA_instance;
  var ArabicLetter_HAMZA_instance;
  var ArabicLetter_TA_MARBUTA_instance;
  function Companion_6() {
    Companion_instance_6 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_5();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.mq_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.yo_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = get_entries_5();
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp_1 = firstOrNull(element_0.mq_1);
      var tmp$ret$3 = tmp_1 == null ? null : new Char(tmp_1);
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_0.zo_1 = destination_0;
    var tmp_2 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_2 = get_entries_5();
    var capacity_1 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_2, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_1 = LinkedHashMap_init_$Create$(capacity_1);
    var _iterator__ex2g4s_1 = this_2.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var tmp$ret$6 = new Char(element_1.rq_1);
      destination_1.l3(tmp$ret$6, element_1);
    }
    tmp_2.ap_1 = destination_1;
    var tmp_3 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_5();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_2 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_2 = tmp0.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      var key = element_2.qq_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination_2.q1(key);
      var tmp_4;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination_2.l3(key, answer);
        tmp_4 = answer;
      } else {
        tmp_4 = value;
      }
      var list = tmp_4;
      list.g(element_2);
    }
    tmp_3.bp_1 = destination_2;
    var tmp_5 = this;
    tmp_5.cp_1 = lazy(ArabicLetter$Companion$keys$delegate$lambda);
  }
  protoOf(Companion_6).r1 = function () {
    var tmp0 = this.cp_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('keys', 1, tmp, ArabicLetter$Companion$_get_keys_$ref_2x7c5s(), null);
    return tmp0.n1();
  };
  protoOf(Companion_6).cj = function (c) {
    return this.zo_1.q1(new Char(c));
  };
  var Companion_instance_6;
  function Companion_getInstance_6() {
    ArabicLetter_initEntries();
    if (Companion_instance_6 == null)
      new Companion_6();
    return Companion_instance_6;
  }
  function values_5() {
    return [ArabicLetter_ALIF_getInstance(), ArabicLetter_BA_getInstance(), ArabicLetter_TA_getInstance(), ArabicLetter_THA_getInstance(), ArabicLetter_JEEM_getInstance(), ArabicLetter_HA_getInstance(), ArabicLetter_KHA_getInstance(), ArabicLetter_DAL_getInstance(), ArabicLetter_DHAL_getInstance(), ArabicLetter_RA_getInstance(), ArabicLetter_ZAY_getInstance(), ArabicLetter_SEEN_getInstance(), ArabicLetter_SHEEN_getInstance(), ArabicLetter_SAD_getInstance(), ArabicLetter_DAD_getInstance(), ArabicLetter_TAA_getInstance(), ArabicLetter_ZAA_getInstance(), ArabicLetter_AIN_getInstance(), ArabicLetter_GHAIN_getInstance(), ArabicLetter_FA_getInstance(), ArabicLetter_QAF_getInstance(), ArabicLetter_KAF_getInstance(), ArabicLetter_LAM_getInstance(), ArabicLetter_MEEM_getInstance(), ArabicLetter_NOON_getInstance(), ArabicLetter_HA_END_getInstance(), ArabicLetter_WAW_getInstance(), ArabicLetter_YA_getInstance(), ArabicLetter_HAMZA_getInstance(), ArabicLetter_TA_MARBUTA_getInstance()];
  }
  function get_entries_5() {
    if ($ENTRIES_5 == null)
      $ENTRIES_5 = enumEntries(values_5());
    return $ENTRIES_5;
  }
  var ArabicLetter_entriesInitialized;
  function ArabicLetter_initEntries() {
    if (ArabicLetter_entriesInitialized)
      return Unit_instance;
    ArabicLetter_entriesInitialized = true;
    ArabicLetter_ALIF_instance = new ArabicLetter('ALIF', 0, '\u0627', '\u0627', '\u0640\u0627', '\u0640\u0627', '', _Char___init__impl__6a9atx(104));
    ArabicLetter_BA_instance = new ArabicLetter('BA', 1, '\u0628', '\u0628\u0640', '\u0640\u0628\u0640', '\u0640\u0628', 'b', _Char___init__impl__6a9atx(102));
    ArabicLetter_TA_instance = new ArabicLetter('TA', 2, '\u062A', '\u062A\u0640', '\u0640\u062A\u0640', '\u0640\u062A', 't', _Char___init__impl__6a9atx(106));
    ArabicLetter_THA_instance = new ArabicLetter('THA', 3, '\u062B', '\u062B\u0640', '\u0640\u062B\u0640', '\u0640\u062B', '\u03B8', _Char___init__impl__6a9atx(101));
    ArabicLetter_JEEM_instance = new ArabicLetter('JEEM', 4, '\u062C', '\u062C\u0640', '\u0640\u062C\u0640', '\u0640\u062C', 'd\u0292', _Char___init__impl__6a9atx(47));
    ArabicLetter_HA_instance = new ArabicLetter('HA', 5, '\u062D', '\u062D\u0640', '\u0640\u062D\u0640', '\u0640\u062D', '\u0127', _Char___init__impl__6a9atx(112));
    ArabicLetter_KHA_instance = new ArabicLetter('KHA', 6, '\u062E', '\u062E\u0640', '\u0640\u062E\u0640', '\u0640\u062E', 'x', _Char___init__impl__6a9atx(111));
    ArabicLetter_DAL_instance = new ArabicLetter('DAL', 7, '\u062F', '\u062F', '\u0640\u062F', '\u0640\u062F', 'd', _Char___init__impl__6a9atx(115));
    ArabicLetter_DHAL_instance = new ArabicLetter('DHAL', 8, '\u0630', '\u0630', '\u0640\u0630', '\u0640\u0630', '\xF0', _Char___init__impl__6a9atx(47));
    ArabicLetter_RA_instance = new ArabicLetter('RA', 9, '\u0631', '\u0631', '\u0640\u0631', '\u0640\u0631', 'r', _Char___init__impl__6a9atx(118));
    ArabicLetter_ZAY_instance = new ArabicLetter('ZAY', 10, '\u0632', '\u0632', '\u0640\u0632', '\u0640\u0632', 'z', _Char___init__impl__6a9atx(46));
    ArabicLetter_SEEN_instance = new ArabicLetter('SEEN', 11, '\u0633', '\u0633\u0640', '\u0640\u0633\u0640', '\u0640\u0633', 's', _Char___init__impl__6a9atx(115));
    ArabicLetter_SHEEN_instance = new ArabicLetter('SHEEN', 12, '\u0634', '\u0634\u0640', '\u0640\u0634\u0640', '\u0640\u0634', '\u0283', _Char___init__impl__6a9atx(97));
    ArabicLetter_SAD_instance = new ArabicLetter('SAD', 13, '\u0635', '\u0635\u0640', '\u0640\u0635\u0640', '\u0640\u0635', 's\u02E4', _Char___init__impl__6a9atx(119));
    ArabicLetter_DAD_instance = new ArabicLetter('DAD', 14, '\u0636', '\u0636\u0640', '\u0640\u0636\u0640', '\u0640\u0636', 'd\u02E4', _Char___init__impl__6a9atx(113));
    ArabicLetter_TAA_instance = new ArabicLetter('TAA', 15, '\u0637', '\u0637\u0640', '\u0640\u0637\u0640', '\u0640\u0637', 't\u02E4', _Char___init__impl__6a9atx(47));
    ArabicLetter_ZAA_instance = new ArabicLetter('ZAA', 16, '\u0638', '\u0638\u0640', '\u0640\u0638\u0640', '\u0640\u0638', '\xF0\u02E4', _Char___init__impl__6a9atx(47));
    ArabicLetter_AIN_instance = new ArabicLetter('AIN', 17, '\u0639', '\u0639\u0640', '\u0640\u0639\u0640', '\u0640\u0639', '\u0295', _Char___init__impl__6a9atx(117));
    ArabicLetter_GHAIN_instance = new ArabicLetter('GHAIN', 18, '\u063A', '\u063A\u0640', '\u0640\u063A\u0640', '\u0640\u063A', '\u0263', _Char___init__impl__6a9atx(121));
    ArabicLetter_FA_instance = new ArabicLetter('FA', 19, '\u0641', '\u0641\u0640', '\u0640\u0641\u0640', '\u0640\u0641', 'f', _Char___init__impl__6a9atx(116));
    ArabicLetter_QAF_instance = new ArabicLetter('QAF', 20, '\u0642', '\u0642\u0640', '\u0640\u0642\u0640', '\u0640\u0642', 'q', _Char___init__impl__6a9atx(114));
    ArabicLetter_KAF_instance = new ArabicLetter('KAF', 21, '\u0643', '\u0643\u0640', '\u0640\u0643\u0640', '\u0640\u0643', 'k', _Char___init__impl__6a9atx(107));
    ArabicLetter_LAM_instance = new ArabicLetter('LAM', 22, '\u0644', '\u0644\u0640', '\u0640\u0644\u0640', '\u0640\u0644', 'l', _Char___init__impl__6a9atx(103));
    ArabicLetter_MEEM_instance = new ArabicLetter('MEEM', 23, '\u0645', '\u0645\u0640', '\u0640\u0645\u0640', '\u0640\u0645', 'm', _Char___init__impl__6a9atx(108));
    ArabicLetter_NOON_instance = new ArabicLetter('NOON', 24, '\u0646', '\u0646\u0640', '\u0640\u0646\u0640', '\u0640\u0646', 'n', _Char___init__impl__6a9atx(110));
    ArabicLetter_HA_END_instance = new ArabicLetter('HA_END', 25, '\u0647', '\u0647\u0640', '\u0640\u0647\u0640', '\u0640\u0647', 'h', _Char___init__impl__6a9atx(105));
    ArabicLetter_WAW_instance = new ArabicLetter('WAW', 26, '\u0648', '\u0648', '\u0640\u0648', '\u0640\u0648', 'w', _Char___init__impl__6a9atx(44));
    ArabicLetter_YA_instance = new ArabicLetter('YA', 27, '\u064A', '\u064A\u0640', '\u0640\u064A\u0640', '\u0640\u064A', 'j', _Char___init__impl__6a9atx(100));
    ArabicLetter_HAMZA_instance = new ArabicLetter('HAMZA', 28, '\u0621', '\u0621', '\u0621', '\u0621', '\u0294', _Char___init__impl__6a9atx(120));
    ArabicLetter_TA_MARBUTA_instance = new ArabicLetter('TA_MARBUTA', 29, '\u0629', '\u0629', '\u0640\u0629', '\u0640\u0629', 'a', _Char___init__impl__6a9atx(93));
    Companion_getInstance_6();
  }
  var $ENTRIES_5;
  function ArabicLetter(name, ordinal, isolated, initial, medial, finalForm, ipa, qwerty) {
    Enum.call(this, name, ordinal);
    this.mq_1 = isolated;
    this.nq_1 = initial;
    this.oq_1 = medial;
    this.pq_1 = finalForm;
    this.qq_1 = ipa;
    this.rq_1 = qwerty;
  }
  protoOf(ArabicLetter).ei = function () {
    return this.qq_1;
  };
  protoOf(ArabicLetter).di = function () {
    return this.mq_1;
  };
  protoOf(ArabicLetter).fi = function () {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.u1_1.toLowerCase();
  };
  var Haraka_FATHA_instance;
  var Haraka_KASRA_instance;
  var Haraka_DAMMA_instance;
  var Haraka_SUKUN_instance;
  var Haraka_SHADDA_instance;
  function Companion_7() {
    Companion_instance_7 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_6();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = new Char(element.uq_1);
      destination.l3(tmp$ret$0, element);
    }
    tmp.br_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = get_entries_6();
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$3 = new Char(element_0.xq_1);
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_0.cr_1 = destination_0;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_6();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_1 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_1 = tmp0.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var tmp_2 = element_1.ar_1;
      if (!((tmp_2 == null ? null : new Char(tmp_2)) == null)) {
        destination_1.g(element_1);
      }
    }
    // Inline function 'kotlin.collections.associateBy' call
    var capacity_1 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(destination_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_2 = LinkedHashMap_init_$Create$(capacity_1);
    var _iterator__ex2g4s_2 = destination_1.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      var tmp_3 = element_2.ar_1;
      var tmp$ret$9 = tmp_3 == null ? null : new Char(tmp_3);
      destination_2.l3(tmp$ret$9, element_2);
    }
    tmp_1.dr_1 = destination_2;
  }
  protoOf(Companion_7).cj = function (c) {
    return this.br_1.q1(new Char(c));
  };
  var Companion_instance_7;
  function Companion_getInstance_7() {
    Haraka_initEntries();
    if (Companion_instance_7 == null)
      new Companion_7();
    return Companion_instance_7;
  }
  function values_6() {
    return [Haraka_FATHA_getInstance(), Haraka_KASRA_getInstance(), Haraka_DAMMA_getInstance(), Haraka_SUKUN_getInstance(), Haraka_SHADDA_getInstance()];
  }
  function get_entries_6() {
    if ($ENTRIES_6 == null)
      $ENTRIES_6 = enumEntries(values_6());
    return $ENTRIES_6;
  }
  var Haraka_entriesInitialized;
  function Haraka_initEntries() {
    if (Haraka_entriesInitialized)
      return Unit_instance;
    Haraka_entriesInitialized = true;
    Haraka_FATHA_instance = new Haraka('FATHA', 0, _Char___init__impl__6a9atx(1614), 'a', 'fatha', _Char___init__impl__6a9atx(113), _Char___init__impl__6a9atx(1611), 'an', _Char___init__impl__6a9atx(119));
    Haraka_KASRA_instance = new Haraka('KASRA', 1, _Char___init__impl__6a9atx(1616), 'i', 'kasra', _Char___init__impl__6a9atx(116), _Char___init__impl__6a9atx(1613), 'in', _Char___init__impl__6a9atx(121));
    Haraka_DAMMA_instance = new Haraka('DAMMA', 2, _Char___init__impl__6a9atx(1615), 'u', 'damma', _Char___init__impl__6a9atx(101), _Char___init__impl__6a9atx(1612), 'un', _Char___init__impl__6a9atx(114));
    Haraka_SUKUN_instance = new Haraka('SUKUN', 3, _Char___init__impl__6a9atx(1618), '', 'sukun', _Char___init__impl__6a9atx(105), null, null, null);
    Haraka_SHADDA_instance = new Haraka('SHADDA', 4, _Char___init__impl__6a9atx(1617), '', 'shadda', _Char___init__impl__6a9atx(117), null, null, null);
    Companion_getInstance_7();
  }
  var $ENTRIES_6;
  function Haraka(name, ordinal, char, ipa, displayName, qwerty, tanwin, tanwinIpa, tanwinQwerty) {
    Enum.call(this, name, ordinal);
    this.uq_1 = char;
    this.vq_1 = ipa;
    this.wq_1 = displayName;
    this.xq_1 = qwerty;
    this.yq_1 = tanwin;
    this.zq_1 = tanwinIpa;
    this.ar_1 = tanwinQwerty;
  }
  var ArabicKeySymbol_TATWEEL_instance;
  var ArabicKeySymbol_SEMICOLON_instance;
  var ArabicKeySymbol_BACKSLASH_instance;
  var ArabicKeySymbol_BRACKET_RIGHT_instance;
  var ArabicKeySymbol_BRACKET_LEFT_instance;
  var ArabicKeySymbol_LAM_HAMZA_instance;
  var ArabicKeySymbol_ALIF_HAMZA_instance;
  var ArabicKeySymbol_TATWEEL2_instance;
  var ArabicKeySymbol_COMMA_instance;
  var ArabicKeySymbol_SLASH_instance;
  var ArabicKeySymbol_COMMA_EN_instance;
  var ArabicKeySymbol_PERIOD_instance;
  function Companion_8() {
    Companion_instance_8 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_7();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = new Char(element.jr_1);
      destination.l3(tmp$ret$0, element);
    }
    tmp.kr_1 = destination;
  }
  var Companion_instance_8;
  function Companion_getInstance_8() {
    ArabicKeySymbol_initEntries();
    if (Companion_instance_8 == null)
      new Companion_8();
    return Companion_instance_8;
  }
  function values_7() {
    return [ArabicKeySymbol_TATWEEL_getInstance(), ArabicKeySymbol_SEMICOLON_getInstance(), ArabicKeySymbol_BACKSLASH_getInstance(), ArabicKeySymbol_BRACKET_RIGHT_getInstance(), ArabicKeySymbol_BRACKET_LEFT_getInstance(), ArabicKeySymbol_LAM_HAMZA_getInstance(), ArabicKeySymbol_ALIF_HAMZA_getInstance(), ArabicKeySymbol_TATWEEL2_getInstance(), ArabicKeySymbol_COMMA_getInstance(), ArabicKeySymbol_SLASH_getInstance(), ArabicKeySymbol_COMMA_EN_getInstance(), ArabicKeySymbol_PERIOD_getInstance()];
  }
  function get_entries_7() {
    if ($ENTRIES_7 == null)
      $ENTRIES_7 = enumEntries(values_7());
    return $ENTRIES_7;
  }
  var ArabicKeySymbol_entriesInitialized;
  function ArabicKeySymbol_initEntries() {
    if (ArabicKeySymbol_entriesInitialized)
      return Unit_instance;
    ArabicKeySymbol_entriesInitialized = true;
    ArabicKeySymbol_TATWEEL_instance = new ArabicKeySymbol('TATWEEL', 0, '\u0640', '', 'tatweel', _Char___init__impl__6a9atx(111));
    ArabicKeySymbol_SEMICOLON_instance = new ArabicKeySymbol('SEMICOLON', 1, '\u061B', '', 'semicolon', _Char___init__impl__6a9atx(112));
    ArabicKeySymbol_BACKSLASH_instance = new ArabicKeySymbol('BACKSLASH', 2, '\\', '', 'backslash', _Char___init__impl__6a9atx(97));
    ArabicKeySymbol_BRACKET_RIGHT_instance = new ArabicKeySymbol('BRACKET_RIGHT', 3, ']', '', 'bracket-right', _Char___init__impl__6a9atx(100));
    ArabicKeySymbol_BRACKET_LEFT_instance = new ArabicKeySymbol('BRACKET_LEFT', 4, '[', '', 'bracket-left', _Char___init__impl__6a9atx(102));
    ArabicKeySymbol_LAM_HAMZA_instance = new ArabicKeySymbol('LAM_HAMZA', 5, '\u0644\u0623', 'la\u0294', 'lam-hamza', _Char___init__impl__6a9atx(103));
    ArabicKeySymbol_ALIF_HAMZA_instance = new ArabicKeySymbol('ALIF_HAMZA', 6, '\u0623', '\u0294', 'alif-hamza', _Char___init__impl__6a9atx(104));
    ArabicKeySymbol_TATWEEL2_instance = new ArabicKeySymbol('TATWEEL2', 7, '\u0640', '', 'tatweel', _Char___init__impl__6a9atx(106));
    ArabicKeySymbol_COMMA_instance = new ArabicKeySymbol('COMMA', 8, '\u060C', '', 'comma', _Char___init__impl__6a9atx(107));
    ArabicKeySymbol_SLASH_instance = new ArabicKeySymbol('SLASH', 9, '/', '', 'slash', _Char___init__impl__6a9atx(108));
    ArabicKeySymbol_COMMA_EN_instance = new ArabicKeySymbol('COMMA_EN', 10, ',', '', 'comma', _Char___init__impl__6a9atx(44));
    ArabicKeySymbol_PERIOD_instance = new ArabicKeySymbol('PERIOD', 11, '.', '', 'period', _Char___init__impl__6a9atx(46));
    Companion_getInstance_8();
  }
  var $ENTRIES_7;
  function ArabicKeySymbol(name, ordinal, char, ipa, displayName, qwerty) {
    Enum.call(this, name, ordinal);
    this.gr_1 = char;
    this.hr_1 = ipa;
    this.ir_1 = displayName;
    this.jr_1 = qwerty;
  }
  function toUniKey(_this__u8e3s4) {
    _init_properties_MorphoEnum_kt__kaqzu0();
    var tmp;
    if (!(_this__u8e3s4.pi_1 == null) && !(_this__u8e3s4.pi_1 === _this__u8e3s4.oi_1)) {
      tmp = new SimpleKey(toString_0(_this__u8e3s4.ni_1), _this__u8e3s4.oi_1, _this__u8e3s4.qi_1, new SimpleKey(toString_0(_this__u8e3s4.ni_1) + '\u05BC', _this__u8e3s4.pi_1, _this__u8e3s4.qi_1 + '-dagesh'));
    } else {
      tmp = new SimpleKey(toString_0(_this__u8e3s4.ni_1), _this__u8e3s4.oi_1, _this__u8e3s4.qi_1);
    }
    var heKey = tmp;
    // Inline function 'kotlin.text.uppercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp_0 = _this__u8e3s4.xi_1.toUpperCase();
    // Inline function 'kotlin.text.uppercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$3 = _this__u8e3s4.xi_1.toUpperCase();
    var enKey = new SimpleKey(_this__u8e3s4.xi_1, _this__u8e3s4.oi_1, _this__u8e3s4.xi_1, new SimpleKey(tmp_0, _this__u8e3s4.oi_1, tmp$ret$3));
    // Inline function 'kotlin.collections.buildSet' call
    // Inline function 'kotlin.collections.buildSetInternal' call
    // Inline function 'kotlin.apply' call
    var this_0 = LinkedHashSet_init_$Create$();
    if (_this__u8e3s4.ej()) {
      this_0.g(KeyProperty_GUTTURAL_getInstance());
    }
    if (_this__u8e3s4.dj()) {
      this_0.g(KeyProperty_FINAL_FORM_getInstance());
    }
    if (_this__u8e3s4.fj()) {
      this_0.g(KeyProperty_BGDKPT_getInstance());
    }
    var props = this_0.w6();
    return new UniKey(_this__u8e3s4.xi_1, _this__u8e3s4.oi_1, mapOf_0([to(Lang_HE_getInstance(), heKey), to(Lang_EN_getInstance(), enKey)]), VOID, VOID, VOID, VOID, props);
  }
  function toLayoutKey(_this__u8e3s4, shiftChar, shiftIpa, shiftName) {
    shiftChar = shiftChar === VOID ? null : shiftChar;
    shiftIpa = shiftIpa === VOID ? null : shiftIpa;
    shiftName = shiftName === VOID ? null : shiftName;
    _init_properties_MorphoEnum_kt__kaqzu0();
    var tmp;
    if (!(shiftChar == null)) {
      var tmp_0 = Modifier_SHIFT_getInstance();
      var tmp_1 = shiftIpa == null ? '' : shiftIpa;
      tmp = mapOf(to(tmp_0, new LayoutKey(shiftChar, tmp_1, shiftName == null ? shiftChar : shiftName)));
    } else {
      tmp = emptyMap();
    }
    var modifiers = tmp;
    return LayoutKey_init_$Create$(_this__u8e3s4.mq_1, _this__u8e3s4.qq_1, _this__u8e3s4.fi(), modifiers);
  }
  function buildArabicKeys() {
    _init_properties_MorphoEnum_kt__kaqzu0();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_5();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!(element.rq_1 === _Char___init__impl__6a9atx(47))) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.associate' call
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(destination, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s_0 = destination.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var shift = get_arabicShiftMap().q1(new Char(element_0.rq_1));
      var tmp = toString_0(element_0.rq_1);
      var tmp_0 = shift == null ? null : shift.hb_1;
      var tmp_1 = shift == null ? null : shift.ib_1;
      // Inline function 'kotlin.collections.plusAssign' call
      var pair = to(tmp, toLayoutKey(element_0, tmp_0, tmp_1, shift == null ? null : shift.jb_1));
      destination_0.l3(pair.p9_1, pair.q9_1);
    }
    return plus(destination_0, mapOf_0([to('b', LayoutKey_init_$Create$('\u0644\u0627', 'la', 'lam-alif', mapOf(to(Modifier_SHIFT_getInstance(), new LayoutKey('\u0644\u0622', 'la\u02D0', 'lam-alif-madda'))))), to('n', LayoutKey_init_$Create$('\u0649', 'a\u02D0', 'alif-maqsura', mapOf(to(Modifier_SHIFT_getInstance(), new LayoutKey('\u0622', '\u0294a\u02D0', 'alif-madda'))))), to('m', LayoutKey_init_$Create$('\u0629', 'a', 'ta-marbuta', mapOf(to(Modifier_SHIFT_getInstance(), new LayoutKey("'", '', 'quote'))))), to('z', LayoutKey_init_$Create$('\u0626', '\u0294', 'hamza-ya', mapOf(to(Modifier_SHIFT_getInstance(), new LayoutKey('~', '', 'tilde'))))), to('c', LayoutKey_init_$Create$('\u0624', '\u0294', 'hamza-waw', mapOf(to(Modifier_SHIFT_getInstance(), new LayoutKey('}', '', 'brace-right'))))), to('/', LayoutKey_init_$Create$('\u0638', '\xF0\u02E4', 'za', mapOf(to(Modifier_SHIFT_getInstance(), new LayoutKey('\u061F', '', 'question')))))]));
  }
  function NikudVowel_KAMATZ_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_KAMATZ_instance;
  }
  function NikudVowel_PATACH_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_PATACH_instance;
  }
  function NikudVowel_HATAF_PATACH_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_HATAF_PATACH_instance;
  }
  function NikudVowel_TZERE_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_TZERE_instance;
  }
  function NikudVowel_SEGOL_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_SEGOL_instance;
  }
  function NikudVowel_HATAF_SEGOL_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_HATAF_SEGOL_instance;
  }
  function NikudVowel_CHIRIK_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_CHIRIK_instance;
  }
  function NikudVowel_CHIRIK_MALE_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_CHIRIK_MALE_instance;
  }
  function NikudVowel_CHOLAM_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_CHOLAM_instance;
  }
  function NikudVowel_CHOLAM_MALE_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_CHOLAM_MALE_instance;
  }
  function NikudVowel_HATAF_KAMATZ_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_HATAF_KAMATZ_instance;
  }
  function NikudVowel_KAMATZ_KATAN_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_KAMATZ_KATAN_instance;
  }
  function NikudVowel_KUBUTZ_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_KUBUTZ_instance;
  }
  function NikudVowel_SHURUK_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_SHURUK_instance;
  }
  function NikudVowel_SHVA_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_SHVA_instance;
  }
  function NikudVowel_SHVA_NA_getInstance() {
    NikudVowel_initEntries();
    return NikudVowel_SHVA_NA_instance;
  }
  function HebrewBgdkpt_BET_getInstance() {
    HebrewBgdkpt_initEntries();
    return HebrewBgdkpt_BET_instance;
  }
  function HebrewBgdkpt_GIMEL_getInstance() {
    HebrewBgdkpt_initEntries();
    return HebrewBgdkpt_GIMEL_instance;
  }
  function HebrewBgdkpt_DALET_getInstance() {
    HebrewBgdkpt_initEntries();
    return HebrewBgdkpt_DALET_instance;
  }
  function HebrewBgdkpt_KAF_getInstance() {
    HebrewBgdkpt_initEntries();
    return HebrewBgdkpt_KAF_instance;
  }
  function HebrewBgdkpt_PE_getInstance() {
    HebrewBgdkpt_initEntries();
    return HebrewBgdkpt_PE_instance;
  }
  function HebrewBgdkpt_TAV_getInstance() {
    HebrewBgdkpt_initEntries();
    return HebrewBgdkpt_TAV_instance;
  }
  function ArabicLetter_ALIF_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_ALIF_instance;
  }
  function ArabicLetter_BA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_BA_instance;
  }
  function ArabicLetter_TA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_TA_instance;
  }
  function ArabicLetter_THA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_THA_instance;
  }
  function ArabicLetter_JEEM_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_JEEM_instance;
  }
  function ArabicLetter_HA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_HA_instance;
  }
  function ArabicLetter_KHA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_KHA_instance;
  }
  function ArabicLetter_DAL_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_DAL_instance;
  }
  function ArabicLetter_DHAL_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_DHAL_instance;
  }
  function ArabicLetter_RA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_RA_instance;
  }
  function ArabicLetter_ZAY_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_ZAY_instance;
  }
  function ArabicLetter_SEEN_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_SEEN_instance;
  }
  function ArabicLetter_SHEEN_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_SHEEN_instance;
  }
  function ArabicLetter_SAD_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_SAD_instance;
  }
  function ArabicLetter_DAD_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_DAD_instance;
  }
  function ArabicLetter_TAA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_TAA_instance;
  }
  function ArabicLetter_ZAA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_ZAA_instance;
  }
  function ArabicLetter_AIN_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_AIN_instance;
  }
  function ArabicLetter_GHAIN_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_GHAIN_instance;
  }
  function ArabicLetter_FA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_FA_instance;
  }
  function ArabicLetter_QAF_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_QAF_instance;
  }
  function ArabicLetter_KAF_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_KAF_instance;
  }
  function ArabicLetter_LAM_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_LAM_instance;
  }
  function ArabicLetter_MEEM_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_MEEM_instance;
  }
  function ArabicLetter_NOON_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_NOON_instance;
  }
  function ArabicLetter_HA_END_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_HA_END_instance;
  }
  function ArabicLetter_WAW_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_WAW_instance;
  }
  function ArabicLetter_YA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_YA_instance;
  }
  function ArabicLetter_HAMZA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_HAMZA_instance;
  }
  function ArabicLetter_TA_MARBUTA_getInstance() {
    ArabicLetter_initEntries();
    return ArabicLetter_TA_MARBUTA_instance;
  }
  function Haraka_FATHA_getInstance() {
    Haraka_initEntries();
    return Haraka_FATHA_instance;
  }
  function Haraka_KASRA_getInstance() {
    Haraka_initEntries();
    return Haraka_KASRA_instance;
  }
  function Haraka_DAMMA_getInstance() {
    Haraka_initEntries();
    return Haraka_DAMMA_instance;
  }
  function Haraka_SUKUN_getInstance() {
    Haraka_initEntries();
    return Haraka_SUKUN_instance;
  }
  function Haraka_SHADDA_getInstance() {
    Haraka_initEntries();
    return Haraka_SHADDA_instance;
  }
  function ArabicKeySymbol_TATWEEL_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_TATWEEL_instance;
  }
  function ArabicKeySymbol_SEMICOLON_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_SEMICOLON_instance;
  }
  function ArabicKeySymbol_BACKSLASH_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_BACKSLASH_instance;
  }
  function ArabicKeySymbol_BRACKET_RIGHT_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_BRACKET_RIGHT_instance;
  }
  function ArabicKeySymbol_BRACKET_LEFT_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_BRACKET_LEFT_instance;
  }
  function ArabicKeySymbol_LAM_HAMZA_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_LAM_HAMZA_instance;
  }
  function ArabicKeySymbol_ALIF_HAMZA_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_ALIF_HAMZA_instance;
  }
  function ArabicKeySymbol_TATWEEL2_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_TATWEEL2_instance;
  }
  function ArabicKeySymbol_COMMA_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_COMMA_instance;
  }
  function ArabicKeySymbol_SLASH_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_SLASH_instance;
  }
  function ArabicKeySymbol_COMMA_EN_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_COMMA_EN_instance;
  }
  function ArabicKeySymbol_PERIOD_getInstance() {
    ArabicKeySymbol_initEntries();
    return ArabicKeySymbol_PERIOD_instance;
  }
  var properties_initialized_MorphoEnum_kt_1skcty;
  function _init_properties_MorphoEnum_kt__kaqzu0() {
    if (!properties_initialized_MorphoEnum_kt_1skcty) {
      properties_initialized_MorphoEnum_kt_1skcty = true;
      // Inline function 'kotlin.collections.buildMap' call
      // Inline function 'kotlin.collections.buildMapInternal' call
      // Inline function 'kotlin.apply' call
      var this_0 = LinkedHashMap_init_$Create$_0();
      var _iterator__ex2g4s = get_entries_6().i();
      while (_iterator__ex2g4s.j()) {
        var h = _iterator__ex2g4s.k();
        this_0.l3(new Char(h.xq_1), new Triple(toString_0(h.uq_1), h.vq_1, h.wq_1));
        var tmp = h.ar_1;
        if (!((tmp == null ? null : new Char(tmp)) == null)) {
          var tmp_0 = h.yq_1;
          this_0.l3(new Char(h.ar_1), new Triple(toString_0(ensureNotNull(tmp_0 == null ? null : new Char(tmp_0)).x_1), ensureNotNull(h.zq_1), 'tanwin-' + take(h.wq_1, 4)));
        }
      }
      var _iterator__ex2g4s_0 = get_entries_7().i();
      while (_iterator__ex2g4s_0.j()) {
        var s = _iterator__ex2g4s_0.k();
        this_0.l3(new Char(s.jr_1), new Triple(s.gr_1, s.hr_1, s.ir_1));
      }
      this_0.l3(new Char(_Char___init__impl__6a9atx(122)), new Triple('~', '', 'tilde'));
      this_0.l3(new Char(_Char___init__impl__6a9atx(120)), new Triple('\u0652', '', 'sukun'));
      this_0.l3(new Char(_Char___init__impl__6a9atx(99)), new Triple('}', '', 'brace-right'));
      this_0.l3(new Char(_Char___init__impl__6a9atx(118)), new Triple('{', '', 'brace-left'));
      arabicShiftMap = this_0.w6();
    }
  }
  var GreekKey_ALPHA_instance;
  var GreekKey_BETA_instance;
  var GreekKey_GAMMA_instance;
  var GreekKey_DELTA_instance;
  var GreekKey_EPSILON_instance;
  var GreekKey_ZETA_instance;
  var GreekKey_ETA_instance;
  var GreekKey_THETA_instance;
  var GreekKey_IOTA_instance;
  var GreekKey_KAPPA_instance;
  var GreekKey_LAMBDA_instance;
  var GreekKey_MU_instance;
  var GreekKey_NU_instance;
  var GreekKey_XI_instance;
  var GreekKey_OMICRON_instance;
  var GreekKey_PI_instance;
  var GreekKey_RHO_instance;
  var GreekKey_SIGMA_instance;
  var GreekKey_SIGMA_FINAL_instance;
  var GreekKey_TAU_instance;
  var GreekKey_UPSILON_instance;
  var GreekKey_PHI_instance;
  var GreekKey_CHI_instance;
  var GreekKey_PSI_instance;
  var GreekKey_OMEGA_instance;
  function Companion_9() {
    Companion_instance_9 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_8();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.rr_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.dp_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = get_entries_8();
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp_1 = firstOrNull(element_0.nr_1);
      var tmp$ret$3 = tmp_1 == null ? null : new Char(tmp_1);
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_0.ep_1 = destination_0;
    var tmp_2 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_8();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_1 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_1 = tmp0.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var key = element_1.pr_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination_1.q1(key);
      var tmp_3;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination_1.l3(key, answer);
        tmp_3 = answer;
      } else {
        tmp_3 = value;
      }
      var list = tmp_3;
      list.g(element_1);
    }
    tmp_2.fp_1 = destination_1;
    this.gp_1 = mapOf_0([to(new Char(_Char___init__impl__6a9atx(940)), new Char(_Char___init__impl__6a9atx(945))), to(new Char(_Char___init__impl__6a9atx(941)), new Char(_Char___init__impl__6a9atx(949))), to(new Char(_Char___init__impl__6a9atx(942)), new Char(_Char___init__impl__6a9atx(951))), to(new Char(_Char___init__impl__6a9atx(943)), new Char(_Char___init__impl__6a9atx(953))), to(new Char(_Char___init__impl__6a9atx(970)), new Char(_Char___init__impl__6a9atx(953))), to(new Char(_Char___init__impl__6a9atx(912)), new Char(_Char___init__impl__6a9atx(953))), to(new Char(_Char___init__impl__6a9atx(972)), new Char(_Char___init__impl__6a9atx(959))), to(new Char(_Char___init__impl__6a9atx(973)), new Char(_Char___init__impl__6a9atx(965))), to(new Char(_Char___init__impl__6a9atx(971)), new Char(_Char___init__impl__6a9atx(965))), to(new Char(_Char___init__impl__6a9atx(944)), new Char(_Char___init__impl__6a9atx(965))), to(new Char(_Char___init__impl__6a9atx(974)), new Char(_Char___init__impl__6a9atx(969)))]);
    var tmp_4 = this;
    // Inline function 'kotlin.collections.associate' call
    var this_2 = get_entries_8();
    var capacity_1 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_2, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination_2 = LinkedHashMap_init_$Create$(capacity_1);
    var _iterator__ex2g4s_2 = this_2.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      // Inline function 'kotlin.collections.plusAssign' call
      var pair = to(element_2.rr_1, isInterface(element_2, ILayoutKey) ? element_2 : THROW_CCE());
      destination_2.l3(pair.p9_1, pair.q9_1);
    }
    tmp_4.hp_1 = plus(destination_2, mapOf(to('q', new SimpleKey(';', '', 'semicolon', new SimpleKey(':', '', 'colon')))));
  }
  protoOf(Companion_9).cj = function (c) {
    var tmp0_elvis_lhs = this.ep_1.q1(new Char(c));
    return tmp0_elvis_lhs == null ? this.ep_1.q1(this.gp_1.q1(new Char(c))) : tmp0_elvis_lhs;
  };
  var Companion_instance_9;
  function Companion_getInstance_9() {
    GreekKey_initEntries();
    if (Companion_instance_9 == null)
      new Companion_9();
    return Companion_instance_9;
  }
  function values_8() {
    return [GreekKey_ALPHA_getInstance(), GreekKey_BETA_getInstance(), GreekKey_GAMMA_getInstance(), GreekKey_DELTA_getInstance(), GreekKey_EPSILON_getInstance(), GreekKey_ZETA_getInstance(), GreekKey_ETA_getInstance(), GreekKey_THETA_getInstance(), GreekKey_IOTA_getInstance(), GreekKey_KAPPA_getInstance(), GreekKey_LAMBDA_getInstance(), GreekKey_MU_getInstance(), GreekKey_NU_getInstance(), GreekKey_XI_getInstance(), GreekKey_OMICRON_getInstance(), GreekKey_PI_getInstance(), GreekKey_RHO_getInstance(), GreekKey_SIGMA_getInstance(), GreekKey_SIGMA_FINAL_getInstance(), GreekKey_TAU_getInstance(), GreekKey_UPSILON_getInstance(), GreekKey_PHI_getInstance(), GreekKey_CHI_getInstance(), GreekKey_PSI_getInstance(), GreekKey_OMEGA_getInstance()];
  }
  function get_entries_8() {
    if ($ENTRIES_8 == null)
      $ENTRIES_8 = enumEntries(values_8());
    return $ENTRIES_8;
  }
  var GreekKey_entriesInitialized;
  function GreekKey_initEntries() {
    if (GreekKey_entriesInitialized)
      return Unit_instance;
    GreekKey_entriesInitialized = true;
    GreekKey_ALPHA_instance = new GreekKey('ALPHA', 0, '\u03B1', '\u0391', 'a', 'alpha', 'a');
    GreekKey_BETA_instance = new GreekKey('BETA', 1, '\u03B2', '\u0392', 'v', 'beta', 'b');
    GreekKey_GAMMA_instance = new GreekKey('GAMMA', 2, '\u03B3', '\u0393', '\u0263', 'gamma', 'g');
    GreekKey_DELTA_instance = new GreekKey('DELTA', 3, '\u03B4', '\u0394', '\xF0', 'delta', 'd');
    GreekKey_EPSILON_instance = new GreekKey('EPSILON', 4, '\u03B5', '\u0395', 'e', 'epsilon', 'e');
    GreekKey_ZETA_instance = new GreekKey('ZETA', 5, '\u03B6', '\u0396', 'z', 'zeta', 'z');
    GreekKey_ETA_instance = new GreekKey('ETA', 6, '\u03B7', '\u0397', 'i', 'eta', 'h');
    GreekKey_THETA_instance = new GreekKey('THETA', 7, '\u03B8', '\u0398', '\u03B8', 'theta', 'u');
    GreekKey_IOTA_instance = new GreekKey('IOTA', 8, '\u03B9', '\u0399', 'i', 'iota', 'i');
    GreekKey_KAPPA_instance = new GreekKey('KAPPA', 9, '\u03BA', '\u039A', 'k', 'kappa', 'k');
    GreekKey_LAMBDA_instance = new GreekKey('LAMBDA', 10, '\u03BB', '\u039B', 'l', 'lambda', 'l');
    GreekKey_MU_instance = new GreekKey('MU', 11, '\u03BC', '\u039C', 'm', 'mu', 'm');
    GreekKey_NU_instance = new GreekKey('NU', 12, '\u03BD', '\u039D', 'n', 'nu', 'n');
    GreekKey_XI_instance = new GreekKey('XI', 13, '\u03BE', '\u039E', 'ks', 'xi', 'j');
    GreekKey_OMICRON_instance = new GreekKey('OMICRON', 14, '\u03BF', '\u039F', 'o', 'omicron', 'o');
    GreekKey_PI_instance = new GreekKey('PI', 15, '\u03C0', '\u03A0', 'p', 'pi', 'p');
    GreekKey_RHO_instance = new GreekKey('RHO', 16, '\u03C1', '\u03A1', 'r', 'rho', 'r');
    GreekKey_SIGMA_instance = new GreekKey('SIGMA', 17, '\u03C3', '\u03A3', 's', 'sigma', 's');
    GreekKey_SIGMA_FINAL_instance = new GreekKey('SIGMA_FINAL', 18, '\u03C2', '\u03A3', 's', 'sigma-final', 'w');
    GreekKey_TAU_instance = new GreekKey('TAU', 19, '\u03C4', '\u03A4', 't', 'tau', 't');
    GreekKey_UPSILON_instance = new GreekKey('UPSILON', 20, '\u03C5', '\u03A5', 'i', 'upsilon', 'y');
    GreekKey_PHI_instance = new GreekKey('PHI', 21, '\u03C6', '\u03A6', 'f', 'phi', 'f');
    GreekKey_CHI_instance = new GreekKey('CHI', 22, '\u03C7', '\u03A7', 'x', 'chi', 'x');
    GreekKey_PSI_instance = new GreekKey('PSI', 23, '\u03C8', '\u03A8', 'ps', 'psi', 'c');
    GreekKey_OMEGA_instance = new GreekKey('OMEGA', 24, '\u03C9', '\u03A9', 'o', 'omega', 'v');
    Companion_getInstance_9();
  }
  var $ENTRIES_8;
  function GreekKey(name, ordinal, char, upper, ipa, displayName, qwerty) {
    Enum.call(this, name, ordinal);
    this.nr_1 = char;
    this.or_1 = upper;
    this.pr_1 = ipa;
    this.qr_1 = displayName;
    this.rr_1 = qwerty;
  }
  protoOf(GreekKey).di = function () {
    return this.nr_1;
  };
  protoOf(GreekKey).ei = function () {
    return this.pr_1;
  };
  protoOf(GreekKey).fi = function () {
    return this.qr_1;
  };
  protoOf(GreekKey).sr = function () {
    return listOf(['a', 'e', 'i', 'o']).j1(this.pr_1);
  };
  protoOf(GreekKey).gi = function () {
    // Inline function 'kotlin.text.replaceFirstChar' call
    var this_0 = this.qr_1;
    var tmp;
    // Inline function 'kotlin.text.isNotEmpty' call
    if (charSequenceLength(this_0) > 0) {
      // Inline function 'kotlin.text.uppercase' call
      var this_1 = charCodeAt(this_0, 0);
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.unsafeCast' call
      var tmp$ret$4 = toString_0(this_1).toUpperCase();
      tmp = toString_1(tmp$ret$4) + substring(this_0, 1);
    } else {
      tmp = this_0;
    }
    var tmp$ret$5 = tmp;
    return new SimpleKey(this.or_1, this.pr_1, tmp$ret$5);
  };
  var CyrillicKey_A_instance;
  var CyrillicKey_BE_instance;
  var CyrillicKey_VE_instance;
  var CyrillicKey_GE_instance;
  var CyrillicKey_DE_instance;
  var CyrillicKey_YE_instance;
  var CyrillicKey_YO_instance;
  var CyrillicKey_ZHE_instance;
  var CyrillicKey_ZE_instance;
  var CyrillicKey_I_instance;
  var CyrillicKey_SHORT_I_instance;
  var CyrillicKey_KA_instance;
  var CyrillicKey_EL_instance;
  var CyrillicKey_EM_instance;
  var CyrillicKey_EN_instance;
  var CyrillicKey_O_instance;
  var CyrillicKey_PE_instance;
  var CyrillicKey_ER_instance;
  var CyrillicKey_ES_instance;
  var CyrillicKey_TE_instance;
  var CyrillicKey_U_instance;
  var CyrillicKey_EF_instance;
  var CyrillicKey_HA_instance;
  var CyrillicKey_TSE_instance;
  var CyrillicKey_CHE_instance;
  var CyrillicKey_SHA_instance;
  var CyrillicKey_SHCHA_instance;
  var CyrillicKey_HARD_SIGN_instance;
  var CyrillicKey_YERU_instance;
  var CyrillicKey_SOFT_SIGN_instance;
  var CyrillicKey_E_instance;
  var CyrillicKey_YU_instance;
  var CyrillicKey_YA_instance;
  function Companion_10() {
    Companion_instance_10 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_9();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.zr_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.np_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = get_entries_9();
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp_1 = firstOrNull(element_0.vr_1);
      var tmp$ret$3 = tmp_1 == null ? null : new Char(tmp_1);
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_0.op_1 = destination_0;
    var tmp_2 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_9();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_1 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_1 = tmp0.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var key = element_1.xr_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination_1.q1(key);
      var tmp_3;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination_1.l3(key, answer);
        tmp_3 = answer;
      } else {
        tmp_3 = value;
      }
      var list = tmp_3;
      list.g(element_1);
    }
    tmp_2.pp_1 = destination_1;
    var tmp_4 = this;
    // Inline function 'kotlin.collections.associate' call
    var this_2 = get_entries_9();
    var capacity_1 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_2, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination_2 = LinkedHashMap_init_$Create$(capacity_1);
    var _iterator__ex2g4s_2 = this_2.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      // Inline function 'kotlin.collections.plusAssign' call
      var pair = to(element_2.zr_1, isInterface(element_2, ILayoutKey) ? element_2 : THROW_CCE());
      destination_2.l3(pair.p9_1, pair.q9_1);
    }
    tmp_4.qp_1 = destination_2;
  }
  protoOf(Companion_10).cj = function (c) {
    return this.op_1.q1(new Char(c));
  };
  var Companion_instance_10;
  function Companion_getInstance_10() {
    CyrillicKey_initEntries();
    if (Companion_instance_10 == null)
      new Companion_10();
    return Companion_instance_10;
  }
  function values_9() {
    return [CyrillicKey_A_getInstance(), CyrillicKey_BE_getInstance(), CyrillicKey_VE_getInstance(), CyrillicKey_GE_getInstance(), CyrillicKey_DE_getInstance(), CyrillicKey_YE_getInstance(), CyrillicKey_YO_getInstance(), CyrillicKey_ZHE_getInstance(), CyrillicKey_ZE_getInstance(), CyrillicKey_I_getInstance(), CyrillicKey_SHORT_I_getInstance(), CyrillicKey_KA_getInstance(), CyrillicKey_EL_getInstance(), CyrillicKey_EM_getInstance(), CyrillicKey_EN_getInstance(), CyrillicKey_O_getInstance(), CyrillicKey_PE_getInstance(), CyrillicKey_ER_getInstance(), CyrillicKey_ES_getInstance(), CyrillicKey_TE_getInstance(), CyrillicKey_U_getInstance(), CyrillicKey_EF_getInstance(), CyrillicKey_HA_getInstance(), CyrillicKey_TSE_getInstance(), CyrillicKey_CHE_getInstance(), CyrillicKey_SHA_getInstance(), CyrillicKey_SHCHA_getInstance(), CyrillicKey_HARD_SIGN_getInstance(), CyrillicKey_YERU_getInstance(), CyrillicKey_SOFT_SIGN_getInstance(), CyrillicKey_E_getInstance(), CyrillicKey_YU_getInstance(), CyrillicKey_YA_getInstance()];
  }
  function get_entries_9() {
    if ($ENTRIES_9 == null)
      $ENTRIES_9 = enumEntries(values_9());
    return $ENTRIES_9;
  }
  var CyrillicKey_entriesInitialized;
  function CyrillicKey_initEntries() {
    if (CyrillicKey_entriesInitialized)
      return Unit_instance;
    CyrillicKey_entriesInitialized = true;
    CyrillicKey_A_instance = new CyrillicKey('A', 0, '\u0430', '\u0410', 'a', 'a', 'f');
    CyrillicKey_BE_instance = new CyrillicKey('BE', 1, '\u0431', '\u0411', 'b', 'be', ',');
    CyrillicKey_VE_instance = new CyrillicKey('VE', 2, '\u0432', '\u0412', 'v', 've', 'd');
    CyrillicKey_GE_instance = new CyrillicKey('GE', 3, '\u0433', '\u0413', '\u0261', 'ge', 'u');
    CyrillicKey_DE_instance = new CyrillicKey('DE', 4, '\u0434', '\u0414', 'd', 'de', 'l');
    CyrillicKey_YE_instance = new CyrillicKey('YE', 5, '\u0435', '\u0415', 'je', 'ye', 't');
    CyrillicKey_YO_instance = new CyrillicKey('YO', 6, '\u0451', '\u0401', 'jo', 'yo', '`');
    CyrillicKey_ZHE_instance = new CyrillicKey('ZHE', 7, '\u0436', '\u0416', '\u0292', 'zhe', ';');
    CyrillicKey_ZE_instance = new CyrillicKey('ZE', 8, '\u0437', '\u0417', 'z', 'ze', 'p');
    CyrillicKey_I_instance = new CyrillicKey('I', 9, '\u0438', '\u0418', 'i', 'i', 'b');
    CyrillicKey_SHORT_I_instance = new CyrillicKey('SHORT_I', 10, '\u0439', '\u0419', 'j', 'short-i', 'q');
    CyrillicKey_KA_instance = new CyrillicKey('KA', 11, '\u043A', '\u041A', 'k', 'ka', 'r');
    CyrillicKey_EL_instance = new CyrillicKey('EL', 12, '\u043B', '\u041B', 'l', 'el', 'k');
    CyrillicKey_EM_instance = new CyrillicKey('EM', 13, '\u043C', '\u041C', 'm', 'em', 'v');
    CyrillicKey_EN_instance = new CyrillicKey('EN', 14, '\u043D', '\u041D', 'n', 'en', 'y');
    CyrillicKey_O_instance = new CyrillicKey('O', 15, '\u043E', '\u041E', 'o', 'o', 'j');
    CyrillicKey_PE_instance = new CyrillicKey('PE', 16, '\u043F', '\u041F', 'p', 'pe', 'g');
    CyrillicKey_ER_instance = new CyrillicKey('ER', 17, '\u0440', '\u0420', 'r', 'er', 'h');
    CyrillicKey_ES_instance = new CyrillicKey('ES', 18, '\u0441', '\u0421', 's', 'es', 'c');
    CyrillicKey_TE_instance = new CyrillicKey('TE', 19, '\u0442', '\u0422', 't', 'te', 'n');
    CyrillicKey_U_instance = new CyrillicKey('U', 20, '\u0443', '\u0423', 'u', 'u', 'e');
    CyrillicKey_EF_instance = new CyrillicKey('EF', 21, '\u0444', '\u0424', 'f', 'ef', 'a');
    CyrillicKey_HA_instance = new CyrillicKey('HA', 22, '\u0445', '\u0425', 'x', 'ha', '[');
    CyrillicKey_TSE_instance = new CyrillicKey('TSE', 23, '\u0446', '\u0426', 'ts', 'tse', 'w');
    CyrillicKey_CHE_instance = new CyrillicKey('CHE', 24, '\u0447', '\u0427', 't\u0283', 'che', 'x');
    CyrillicKey_SHA_instance = new CyrillicKey('SHA', 25, '\u0448', '\u0428', '\u0283', 'sha', 'i');
    CyrillicKey_SHCHA_instance = new CyrillicKey('SHCHA', 26, '\u0449', '\u0429', '\u0283t\u0283', 'shcha', 'o');
    CyrillicKey_HARD_SIGN_instance = new CyrillicKey('HARD_SIGN', 27, '\u044A', '\u042A', '', 'hard-sign', ']');
    CyrillicKey_YERU_instance = new CyrillicKey('YERU', 28, '\u044B', '\u042B', '\u0268', 'yeru', 's');
    CyrillicKey_SOFT_SIGN_instance = new CyrillicKey('SOFT_SIGN', 29, '\u044C', '\u042C', '', 'soft-sign', 'm');
    CyrillicKey_E_instance = new CyrillicKey('E', 30, '\u044D', '\u042D', 'e', 'e', "'");
    CyrillicKey_YU_instance = new CyrillicKey('YU', 31, '\u044E', '\u042E', 'ju', 'yu', '.');
    CyrillicKey_YA_instance = new CyrillicKey('YA', 32, '\u044F', '\u042F', 'ja', 'ya', 'z');
    Companion_getInstance_10();
  }
  var $ENTRIES_9;
  function CyrillicKey(name, ordinal, char, upper, ipa, displayName, qwerty) {
    Enum.call(this, name, ordinal);
    this.vr_1 = char;
    this.wr_1 = upper;
    this.xr_1 = ipa;
    this.yr_1 = displayName;
    this.zr_1 = qwerty;
  }
  protoOf(CyrillicKey).di = function () {
    return this.vr_1;
  };
  protoOf(CyrillicKey).ei = function () {
    return this.xr_1;
  };
  protoOf(CyrillicKey).fi = function () {
    return this.yr_1;
  };
  protoOf(CyrillicKey).gi = function () {
    // Inline function 'kotlin.text.replaceFirstChar' call
    var this_0 = this.yr_1;
    var tmp;
    // Inline function 'kotlin.text.isNotEmpty' call
    if (charSequenceLength(this_0) > 0) {
      // Inline function 'kotlin.text.uppercase' call
      var this_1 = charCodeAt(this_0, 0);
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.unsafeCast' call
      var tmp$ret$4 = toString_0(this_1).toUpperCase();
      tmp = toString_1(tmp$ret$4) + substring(this_0, 1);
    } else {
      tmp = this_0;
    }
    var tmp$ret$5 = tmp;
    return new SimpleKey(this.wr_1, this.xr_1, tmp$ret$5);
  };
  var HangulInitial_GIYEOK_instance;
  var HangulInitial_SSANG_GIYEOK_instance;
  var HangulInitial_NIEUN_instance;
  var HangulInitial_DIGEUT_instance;
  var HangulInitial_SSANG_DIGEUT_instance;
  var HangulInitial_RIEUL_instance;
  var HangulInitial_MIEUM_instance;
  var HangulInitial_BIEUP_instance;
  var HangulInitial_SSANG_BIEUP_instance;
  var HangulInitial_SIOT_instance;
  var HangulInitial_SSANG_SIOT_instance;
  var HangulInitial_IEUNG_instance;
  var HangulInitial_JIEUT_instance;
  var HangulInitial_SSANG_JIEUT_instance;
  var HangulInitial_CHIEUT_instance;
  var HangulInitial_KIEUK_instance;
  var HangulInitial_TIEUT_instance;
  var HangulInitial_PIEUP_instance;
  var HangulInitial_HIEUT_instance;
  function Companion_11() {
    Companion_instance_11 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_10();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.fs_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.hs_1 = destination;
  }
  protoOf(Companion_11).is = function (code) {
    return this.hs_1.q1(code);
  };
  var Companion_instance_11;
  function Companion_getInstance_11() {
    HangulInitial_initEntries();
    if (Companion_instance_11 == null)
      new Companion_11();
    return Companion_instance_11;
  }
  function values_10() {
    return [HangulInitial_GIYEOK_getInstance(), HangulInitial_SSANG_GIYEOK_getInstance(), HangulInitial_NIEUN_getInstance(), HangulInitial_DIGEUT_getInstance(), HangulInitial_SSANG_DIGEUT_getInstance(), HangulInitial_RIEUL_getInstance(), HangulInitial_MIEUM_getInstance(), HangulInitial_BIEUP_getInstance(), HangulInitial_SSANG_BIEUP_getInstance(), HangulInitial_SIOT_getInstance(), HangulInitial_SSANG_SIOT_getInstance(), HangulInitial_IEUNG_getInstance(), HangulInitial_JIEUT_getInstance(), HangulInitial_SSANG_JIEUT_getInstance(), HangulInitial_CHIEUT_getInstance(), HangulInitial_KIEUK_getInstance(), HangulInitial_TIEUT_getInstance(), HangulInitial_PIEUP_getInstance(), HangulInitial_HIEUT_getInstance()];
  }
  function get_entries_10() {
    if ($ENTRIES_10 == null)
      $ENTRIES_10 = enumEntries(values_10());
    return $ENTRIES_10;
  }
  var HangulInitial_entriesInitialized;
  function HangulInitial_initEntries() {
    if (HangulInitial_entriesInitialized)
      return Unit_instance;
    HangulInitial_entriesInitialized = true;
    HangulInitial_GIYEOK_instance = new HangulInitial('GIYEOK', 0, '\u3131', 'k', 'giyeok', 0, '\u0261');
    HangulInitial_SSANG_GIYEOK_instance = new HangulInitial('SSANG_GIYEOK', 1, '\u3132', 'k', 'ssang-giyeok', 1);
    HangulInitial_NIEUN_instance = new HangulInitial('NIEUN', 2, '\u3134', 'n', 'nieun', 2);
    HangulInitial_DIGEUT_instance = new HangulInitial('DIGEUT', 3, '\u3137', 't', 'digeut', 3, 'd');
    HangulInitial_SSANG_DIGEUT_instance = new HangulInitial('SSANG_DIGEUT', 4, '\u3138', 't', 'ssang-digeut', 4);
    HangulInitial_RIEUL_instance = new HangulInitial('RIEUL', 5, '\u3139', 'l', 'rieul', 5, 'r');
    HangulInitial_MIEUM_instance = new HangulInitial('MIEUM', 6, '\u3141', 'm', 'mieum', 6);
    HangulInitial_BIEUP_instance = new HangulInitial('BIEUP', 7, '\u3142', 'p', 'bieup', 7, 'b');
    HangulInitial_SSANG_BIEUP_instance = new HangulInitial('SSANG_BIEUP', 8, '\u3143', 'p', 'ssang-bieup', 8);
    HangulInitial_SIOT_instance = new HangulInitial('SIOT', 9, '\u3145', 's', 'siot', 9);
    HangulInitial_SSANG_SIOT_instance = new HangulInitial('SSANG_SIOT', 10, '\u3146', 's', 'ssang-siot', 10);
    HangulInitial_IEUNG_instance = new HangulInitial('IEUNG', 11, '\u3147', '\u014B', 'ieung', 11, '');
    HangulInitial_JIEUT_instance = new HangulInitial('JIEUT', 12, '\u3148', 't', 'jieut', 12, 'd\u0292');
    HangulInitial_SSANG_JIEUT_instance = new HangulInitial('SSANG_JIEUT', 13, '\u3149', 't', 'ssang-jieut', 13, 't\u0283');
    HangulInitial_CHIEUT_instance = new HangulInitial('CHIEUT', 14, '\u314A', 't', 'chieut', 14, 't\u0283\u02B0');
    HangulInitial_KIEUK_instance = new HangulInitial('KIEUK', 15, '\u314B', 'k', 'kieuk', 15, 'k\u02B0');
    HangulInitial_TIEUT_instance = new HangulInitial('TIEUT', 16, '\u314C', 't', 'tieut', 16, 't\u02B0');
    HangulInitial_PIEUP_instance = new HangulInitial('PIEUP', 17, '\u314D', 'p', 'pieup', 17, 'p\u02B0');
    HangulInitial_HIEUT_instance = new HangulInitial('HIEUT', 18, '\u314E', 'h', 'hieut', 18);
    Companion_getInstance_11();
  }
  var $ENTRIES_10;
  function HangulInitial(name, ordinal, char, ipa, displayName, code, initialIpa) {
    initialIpa = initialIpa === VOID ? ipa : initialIpa;
    Enum.call(this, name, ordinal);
    this.cs_1 = char;
    this.ds_1 = ipa;
    this.es_1 = displayName;
    this.fs_1 = code;
    this.gs_1 = initialIpa;
  }
  protoOf(HangulInitial).di = function () {
    return this.cs_1;
  };
  protoOf(HangulInitial).ei = function () {
    return this.ds_1;
  };
  protoOf(HangulInitial).fi = function () {
    return this.es_1;
  };
  var HangulMedial_A_instance;
  var HangulMedial_AE_instance;
  var HangulMedial_YA_instance;
  var HangulMedial_YAE_instance;
  var HangulMedial_EO_instance;
  var HangulMedial_E_instance;
  var HangulMedial_YEO_instance;
  var HangulMedial_YE_instance;
  var HangulMedial_O_instance;
  var HangulMedial_WA_instance;
  var HangulMedial_WAE_instance;
  var HangulMedial_OE_instance;
  var HangulMedial_YO_instance;
  var HangulMedial_U_instance;
  var HangulMedial_WEO_instance;
  var HangulMedial_WE_instance;
  var HangulMedial_WI_instance;
  var HangulMedial_YU_instance;
  var HangulMedial_EU_instance;
  var HangulMedial_UI_instance;
  var HangulMedial_I_instance;
  function Companion_12() {
    Companion_instance_12 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_11();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.os_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.ps_1 = destination;
  }
  protoOf(Companion_12).is = function (code) {
    return this.ps_1.q1(code);
  };
  var Companion_instance_12;
  function Companion_getInstance_12() {
    HangulMedial_initEntries();
    if (Companion_instance_12 == null)
      new Companion_12();
    return Companion_instance_12;
  }
  function values_11() {
    return [HangulMedial_A_getInstance(), HangulMedial_AE_getInstance(), HangulMedial_YA_getInstance(), HangulMedial_YAE_getInstance(), HangulMedial_EO_getInstance(), HangulMedial_E_getInstance(), HangulMedial_YEO_getInstance(), HangulMedial_YE_getInstance(), HangulMedial_O_getInstance(), HangulMedial_WA_getInstance(), HangulMedial_WAE_getInstance(), HangulMedial_OE_getInstance(), HangulMedial_YO_getInstance(), HangulMedial_U_getInstance(), HangulMedial_WEO_getInstance(), HangulMedial_WE_getInstance(), HangulMedial_WI_getInstance(), HangulMedial_YU_getInstance(), HangulMedial_EU_getInstance(), HangulMedial_UI_getInstance(), HangulMedial_I_getInstance()];
  }
  function get_entries_11() {
    if ($ENTRIES_11 == null)
      $ENTRIES_11 = enumEntries(values_11());
    return $ENTRIES_11;
  }
  var HangulMedial_entriesInitialized;
  function HangulMedial_initEntries() {
    if (HangulMedial_entriesInitialized)
      return Unit_instance;
    HangulMedial_entriesInitialized = true;
    HangulMedial_A_instance = new HangulMedial('A', 0, '\u314F', 'a', 'a', 0);
    HangulMedial_AE_instance = new HangulMedial('AE', 1, '\u3150', '\u025B', 'ae', 1);
    HangulMedial_YA_instance = new HangulMedial('YA', 2, '\u3151', 'ja', 'ya', 2);
    HangulMedial_YAE_instance = new HangulMedial('YAE', 3, '\u3152', 'j\u025B', 'yae', 3);
    HangulMedial_EO_instance = new HangulMedial('EO', 4, '\u3153', '\u028C', 'eo', 4);
    HangulMedial_E_instance = new HangulMedial('E', 5, '\u3154', 'e', 'e', 5);
    HangulMedial_YEO_instance = new HangulMedial('YEO', 6, '\u3155', 'j\u028C', 'yeo', 6);
    HangulMedial_YE_instance = new HangulMedial('YE', 7, '\u3156', 'je', 'ye', 7);
    HangulMedial_O_instance = new HangulMedial('O', 8, '\u3157', 'o', 'o', 8);
    HangulMedial_WA_instance = new HangulMedial('WA', 9, '\u3158', 'wa', 'wa', 9);
    HangulMedial_WAE_instance = new HangulMedial('WAE', 10, '\u3159', 'w\u025B', 'wae', 10);
    HangulMedial_OE_instance = new HangulMedial('OE', 11, '\u315A', 'we', 'oe', 11);
    HangulMedial_YO_instance = new HangulMedial('YO', 12, '\u315B', 'jo', 'yo', 12);
    HangulMedial_U_instance = new HangulMedial('U', 13, '\u315C', 'u', 'u', 13);
    HangulMedial_WEO_instance = new HangulMedial('WEO', 14, '\u315D', 'w\u028C', 'weo', 14);
    HangulMedial_WE_instance = new HangulMedial('WE', 15, '\u315E', 'we', 'we', 15);
    HangulMedial_WI_instance = new HangulMedial('WI', 16, '\u315F', 'wi', 'wi', 16);
    HangulMedial_YU_instance = new HangulMedial('YU', 17, '\u3160', 'ju', 'yu', 17);
    HangulMedial_EU_instance = new HangulMedial('EU', 18, '\u3161', '\u0268', 'eu', 18);
    HangulMedial_UI_instance = new HangulMedial('UI', 19, '\u3162', '\u0270i', 'ui', 19);
    HangulMedial_I_instance = new HangulMedial('I', 20, '\u3163', 'i', 'i', 20);
    Companion_getInstance_12();
  }
  var $ENTRIES_11;
  function HangulMedial(name, ordinal, char, ipa, displayName, code) {
    Enum.call(this, name, ordinal);
    this.ls_1 = char;
    this.ms_1 = ipa;
    this.ns_1 = displayName;
    this.os_1 = code;
  }
  protoOf(HangulMedial).di = function () {
    return this.ls_1;
  };
  protoOf(HangulMedial).ei = function () {
    return this.ms_1;
  };
  protoOf(HangulMedial).fi = function () {
    return this.ns_1;
  };
  var HangulFinal_NONE_instance;
  var HangulFinal_GIYEOK_instance;
  var HangulFinal_SSANG_GIYEOK_instance;
  var HangulFinal_GIYEOK_SIOT_instance;
  var HangulFinal_NIEUN_instance;
  var HangulFinal_NIEUN_JIEUT_instance;
  var HangulFinal_NIEUN_HIEUT_instance;
  var HangulFinal_DIGEUT_instance;
  var HangulFinal_RIEUL_instance;
  var HangulFinal_RIEUL_GIYEOK_instance;
  var HangulFinal_RIEUL_MIEUM_instance;
  var HangulFinal_RIEUL_BIEUP_instance;
  var HangulFinal_RIEUL_SIOT_instance;
  var HangulFinal_RIEUL_TIEUT_instance;
  var HangulFinal_RIEUL_PIEUP_instance;
  var HangulFinal_RIEUL_HIEUT_instance;
  var HangulFinal_MIEUM_instance;
  var HangulFinal_BIEUP_instance;
  var HangulFinal_BIEUP_SIOT_instance;
  var HangulFinal_SIOT_instance;
  var HangulFinal_SSANG_SIOT_instance;
  var HangulFinal_IEUNG_instance;
  var HangulFinal_JIEUT_instance;
  var HangulFinal_CHIEUT_instance;
  var HangulFinal_KIEUK_instance;
  var HangulFinal_TIEUT_instance;
  var HangulFinal_PIEUP_instance;
  var HangulFinal_HIEUT_instance;
  function Companion_13() {
    Companion_instance_13 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_12();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.vs_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.ws_1 = destination;
  }
  protoOf(Companion_13).is = function (code) {
    return this.ws_1.q1(code);
  };
  var Companion_instance_13;
  function Companion_getInstance_13() {
    HangulFinal_initEntries();
    if (Companion_instance_13 == null)
      new Companion_13();
    return Companion_instance_13;
  }
  function values_12() {
    return [HangulFinal_NONE_getInstance(), HangulFinal_GIYEOK_getInstance(), HangulFinal_SSANG_GIYEOK_getInstance(), HangulFinal_GIYEOK_SIOT_getInstance(), HangulFinal_NIEUN_getInstance(), HangulFinal_NIEUN_JIEUT_getInstance(), HangulFinal_NIEUN_HIEUT_getInstance(), HangulFinal_DIGEUT_getInstance(), HangulFinal_RIEUL_getInstance(), HangulFinal_RIEUL_GIYEOK_getInstance(), HangulFinal_RIEUL_MIEUM_getInstance(), HangulFinal_RIEUL_BIEUP_getInstance(), HangulFinal_RIEUL_SIOT_getInstance(), HangulFinal_RIEUL_TIEUT_getInstance(), HangulFinal_RIEUL_PIEUP_getInstance(), HangulFinal_RIEUL_HIEUT_getInstance(), HangulFinal_MIEUM_getInstance(), HangulFinal_BIEUP_getInstance(), HangulFinal_BIEUP_SIOT_getInstance(), HangulFinal_SIOT_getInstance(), HangulFinal_SSANG_SIOT_getInstance(), HangulFinal_IEUNG_getInstance(), HangulFinal_JIEUT_getInstance(), HangulFinal_CHIEUT_getInstance(), HangulFinal_KIEUK_getInstance(), HangulFinal_TIEUT_getInstance(), HangulFinal_PIEUP_getInstance(), HangulFinal_HIEUT_getInstance()];
  }
  function get_entries_12() {
    if ($ENTRIES_12 == null)
      $ENTRIES_12 = enumEntries(values_12());
    return $ENTRIES_12;
  }
  var HangulFinal_entriesInitialized;
  function HangulFinal_initEntries() {
    if (HangulFinal_entriesInitialized)
      return Unit_instance;
    HangulFinal_entriesInitialized = true;
    HangulFinal_NONE_instance = new HangulFinal('NONE', 0, '', '', 'none', 0);
    HangulFinal_GIYEOK_instance = new HangulFinal('GIYEOK', 1, '\u3131', 'k', 'giyeok', 1);
    HangulFinal_SSANG_GIYEOK_instance = new HangulFinal('SSANG_GIYEOK', 2, '\u3132', 'k', 'ssang-giyeok', 2);
    HangulFinal_GIYEOK_SIOT_instance = new HangulFinal('GIYEOK_SIOT', 3, '\u3133', 'k', 'giyeok-siot', 3);
    HangulFinal_NIEUN_instance = new HangulFinal('NIEUN', 4, '\u3134', 'n', 'nieun', 4);
    HangulFinal_NIEUN_JIEUT_instance = new HangulFinal('NIEUN_JIEUT', 5, '\u3135', 'n', 'nieun-jieut', 5);
    HangulFinal_NIEUN_HIEUT_instance = new HangulFinal('NIEUN_HIEUT', 6, '\u3136', 'n', 'nieun-hieut', 6);
    HangulFinal_DIGEUT_instance = new HangulFinal('DIGEUT', 7, '\u3137', 't', 'digeut', 7);
    HangulFinal_RIEUL_instance = new HangulFinal('RIEUL', 8, '\u3139', 'l', 'rieul', 8);
    HangulFinal_RIEUL_GIYEOK_instance = new HangulFinal('RIEUL_GIYEOK', 9, '\u313A', 'k', 'rieul-giyeok', 9);
    HangulFinal_RIEUL_MIEUM_instance = new HangulFinal('RIEUL_MIEUM', 10, '\u313B', 'm', 'rieul-mieum', 10);
    HangulFinal_RIEUL_BIEUP_instance = new HangulFinal('RIEUL_BIEUP', 11, '\u313C', 'p', 'rieul-bieup', 11);
    HangulFinal_RIEUL_SIOT_instance = new HangulFinal('RIEUL_SIOT', 12, '\u313D', 'l', 'rieul-siot', 12);
    HangulFinal_RIEUL_TIEUT_instance = new HangulFinal('RIEUL_TIEUT', 13, '\u313E', 'l', 'rieul-tieut', 13);
    HangulFinal_RIEUL_PIEUP_instance = new HangulFinal('RIEUL_PIEUP', 14, '\u313F', 'l', 'rieul-pieup', 14);
    HangulFinal_RIEUL_HIEUT_instance = new HangulFinal('RIEUL_HIEUT', 15, '\u3140', 'p', 'rieul-hieut', 15);
    HangulFinal_MIEUM_instance = new HangulFinal('MIEUM', 16, '\u3141', 'm', 'mieum', 16);
    HangulFinal_BIEUP_instance = new HangulFinal('BIEUP', 17, '\u3142', 'p', 'bieup', 17);
    HangulFinal_BIEUP_SIOT_instance = new HangulFinal('BIEUP_SIOT', 18, '\u3144', 'p', 'bieup-siot', 18);
    HangulFinal_SIOT_instance = new HangulFinal('SIOT', 19, '\u3145', 't', 'siot', 19);
    HangulFinal_SSANG_SIOT_instance = new HangulFinal('SSANG_SIOT', 20, '\u3146', 't', 'ssang-siot', 20);
    HangulFinal_IEUNG_instance = new HangulFinal('IEUNG', 21, '\u3147', '\u014B', 'ieung', 21);
    HangulFinal_JIEUT_instance = new HangulFinal('JIEUT', 22, '\u3148', 't', 'jieut', 22);
    HangulFinal_CHIEUT_instance = new HangulFinal('CHIEUT', 23, '\u314A', 't', 'chieut', 23);
    HangulFinal_KIEUK_instance = new HangulFinal('KIEUK', 24, '\u314B', 'k', 'kieuk', 24);
    HangulFinal_TIEUT_instance = new HangulFinal('TIEUT', 25, '\u314C', 't', 'tieut', 25);
    HangulFinal_PIEUP_instance = new HangulFinal('PIEUP', 26, '\u314D', 'p', 'pieup', 26);
    HangulFinal_HIEUT_instance = new HangulFinal('HIEUT', 27, '\u314E', 't', 'hieut', 27);
    Companion_getInstance_13();
  }
  var $ENTRIES_12;
  function HangulFinal(name, ordinal, char, ipa, displayName, code) {
    Enum.call(this, name, ordinal);
    this.ss_1 = char;
    this.ts_1 = ipa;
    this.us_1 = displayName;
    this.vs_1 = code;
  }
  protoOf(HangulFinal).di = function () {
    return this.ss_1;
  };
  protoOf(HangulFinal).ei = function () {
    return this.ts_1;
  };
  protoOf(HangulFinal).fi = function () {
    return this.us_1;
  };
  var HiraganaKey_A_instance;
  var HiraganaKey_I_instance;
  var HiraganaKey_U_instance;
  var HiraganaKey_E_instance;
  var HiraganaKey_O_instance;
  var HiraganaKey_KA_instance;
  var HiraganaKey_KI_instance;
  var HiraganaKey_KU_instance;
  var HiraganaKey_KE_instance;
  var HiraganaKey_KO_instance;
  var HiraganaKey_SA_instance;
  var HiraganaKey_SHI_instance;
  var HiraganaKey_SU_instance;
  var HiraganaKey_SE_instance;
  var HiraganaKey_SO_instance;
  var HiraganaKey_TA_instance;
  var HiraganaKey_CHI_instance;
  var HiraganaKey_TSU_instance;
  var HiraganaKey_TE_instance;
  var HiraganaKey_TO_instance;
  var HiraganaKey_NA_instance;
  var HiraganaKey_NI_instance;
  var HiraganaKey_NU_instance;
  var HiraganaKey_NE_instance;
  var HiraganaKey_NO_instance;
  var HiraganaKey_HA_instance;
  var HiraganaKey_HI_instance;
  var HiraganaKey_FU_instance;
  var HiraganaKey_HE_instance;
  var HiraganaKey_HO_instance;
  var HiraganaKey_MA_instance;
  var HiraganaKey_MI_instance;
  var HiraganaKey_MU_instance;
  var HiraganaKey_ME_instance;
  var HiraganaKey_MO_instance;
  var HiraganaKey_YA_instance;
  var HiraganaKey_YU_instance;
  var HiraganaKey_YO_instance;
  var HiraganaKey_RA_instance;
  var HiraganaKey_RI_instance;
  var HiraganaKey_RU_instance;
  var HiraganaKey_RE_instance;
  var HiraganaKey_RO_instance;
  var HiraganaKey_WA_instance;
  var HiraganaKey_WO_instance;
  var HiraganaKey_N_instance;
  function Companion_14() {
    Companion_instance_14 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_13();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.dt_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.ip_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = get_entries_13();
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp_1 = firstOrNull(element_0.zs_1);
      var tmp$ret$3 = tmp_1 == null ? null : new Char(tmp_1);
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_0.jp_1 = destination_0;
    var tmp_2 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_2 = get_entries_13();
    var capacity_1 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_2, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_1 = LinkedHashMap_init_$Create$(capacity_1);
    var _iterator__ex2g4s_1 = this_2.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var tmp_3 = firstOrNull(element_1.at_1);
      var tmp$ret$6 = tmp_3 == null ? null : new Char(tmp_3);
      destination_1.l3(tmp$ret$6, element_1);
    }
    tmp_2.kp_1 = destination_1;
    var tmp_4 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_13();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_2 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_2 = tmp0.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      var key = element_2.bt_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination_2.q1(key);
      var tmp_5;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination_2.l3(key, answer);
        tmp_5 = answer;
      } else {
        tmp_5 = value;
      }
      var list = tmp_5;
      list.g(element_2);
    }
    tmp_4.lp_1 = destination_2;
    var tmp_6 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_0 = get_entries_13();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_3 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_3 = tmp0_0.i();
    while (_iterator__ex2g4s_3.j()) {
      var element_3 = _iterator__ex2g4s_3.k();
      if (element_3.dt_1.length === 1 || listOf(['ka', 'sa', 'ta', 'na', 'ha', 'ma', 'ya', 'ra', 'wa']).j1(element_3.dt_1)) {
        destination_3.g(element_3);
      }
    }
    // Inline function 'kotlin.collections.associate' call
    var capacity_2 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(destination_3, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination_4 = LinkedHashMap_init_$Create$(capacity_2);
    var _iterator__ex2g4s_4 = destination_3.i();
    while (_iterator__ex2g4s_4.j()) {
      var element_4 = _iterator__ex2g4s_4.k();
      var key_0;
      switch (element_4.dt_1) {
        case 'a':
          key_0 = 'a';
          break;
        case 'i':
          key_0 = 'i';
          break;
        case 'u':
          key_0 = 'u';
          break;
        case 'e':
          key_0 = 'e';
          break;
        case 'o':
          key_0 = 'o';
          break;
        case 'ka':
          key_0 = 'k';
          break;
        case 'sa':
          key_0 = 's';
          break;
        case 'ta':
          key_0 = 't';
          break;
        case 'na':
          key_0 = 'n';
          break;
        case 'ha':
          key_0 = 'h';
          break;
        case 'ma':
          key_0 = 'm';
          break;
        case 'ya':
          key_0 = 'y';
          break;
        case 'ra':
          key_0 = 'r';
          break;
        case 'wa':
          key_0 = 'w';
          break;
        default:
          key_0 = element_4.dt_1;
          break;
      }
      // Inline function 'kotlin.collections.plusAssign' call
      var pair = to(key_0, isInterface(element_4, ILayoutKey) ? element_4 : THROW_CCE());
      destination_4.l3(pair.p9_1, pair.q9_1);
    }
    tmp_6.mp_1 = destination_4;
  }
  protoOf(Companion_14).cj = function (c) {
    var tmp0_elvis_lhs = this.jp_1.q1(new Char(c));
    return tmp0_elvis_lhs == null ? this.kp_1.q1(new Char(c)) : tmp0_elvis_lhs;
  };
  var Companion_instance_14;
  function Companion_getInstance_14() {
    HiraganaKey_initEntries();
    if (Companion_instance_14 == null)
      new Companion_14();
    return Companion_instance_14;
  }
  function values_13() {
    return [HiraganaKey_A_getInstance(), HiraganaKey_I_getInstance(), HiraganaKey_U_getInstance(), HiraganaKey_E_getInstance(), HiraganaKey_O_getInstance(), HiraganaKey_KA_getInstance(), HiraganaKey_KI_getInstance(), HiraganaKey_KU_getInstance(), HiraganaKey_KE_getInstance(), HiraganaKey_KO_getInstance(), HiraganaKey_SA_getInstance(), HiraganaKey_SHI_getInstance(), HiraganaKey_SU_getInstance(), HiraganaKey_SE_getInstance(), HiraganaKey_SO_getInstance(), HiraganaKey_TA_getInstance(), HiraganaKey_CHI_getInstance(), HiraganaKey_TSU_getInstance(), HiraganaKey_TE_getInstance(), HiraganaKey_TO_getInstance(), HiraganaKey_NA_getInstance(), HiraganaKey_NI_getInstance(), HiraganaKey_NU_getInstance(), HiraganaKey_NE_getInstance(), HiraganaKey_NO_getInstance(), HiraganaKey_HA_getInstance(), HiraganaKey_HI_getInstance(), HiraganaKey_FU_getInstance(), HiraganaKey_HE_getInstance(), HiraganaKey_HO_getInstance(), HiraganaKey_MA_getInstance(), HiraganaKey_MI_getInstance(), HiraganaKey_MU_getInstance(), HiraganaKey_ME_getInstance(), HiraganaKey_MO_getInstance(), HiraganaKey_YA_getInstance(), HiraganaKey_YU_getInstance(), HiraganaKey_YO_getInstance(), HiraganaKey_RA_getInstance(), HiraganaKey_RI_getInstance(), HiraganaKey_RU_getInstance(), HiraganaKey_RE_getInstance(), HiraganaKey_RO_getInstance(), HiraganaKey_WA_getInstance(), HiraganaKey_WO_getInstance(), HiraganaKey_N_getInstance()];
  }
  function get_entries_13() {
    if ($ENTRIES_13 == null)
      $ENTRIES_13 = enumEntries(values_13());
    return $ENTRIES_13;
  }
  var HiraganaKey_entriesInitialized;
  function HiraganaKey_initEntries() {
    if (HiraganaKey_entriesInitialized)
      return Unit_instance;
    HiraganaKey_entriesInitialized = true;
    HiraganaKey_A_instance = new HiraganaKey('A', 0, '\u3042', '\u30A2', 'a', 'a', 'a');
    HiraganaKey_I_instance = new HiraganaKey('I', 1, '\u3044', '\u30A4', 'i', 'i', 'i');
    HiraganaKey_U_instance = new HiraganaKey('U', 2, '\u3046', '\u30A6', '\u026F', 'u', 'u');
    HiraganaKey_E_instance = new HiraganaKey('E', 3, '\u3048', '\u30A8', 'e', 'e', 'e');
    HiraganaKey_O_instance = new HiraganaKey('O', 4, '\u304A', '\u30AA', 'o', 'o', 'o');
    HiraganaKey_KA_instance = new HiraganaKey('KA', 5, '\u304B', '\u30AB', 'ka', 'ka', 'k');
    HiraganaKey_KI_instance = new HiraganaKey('KI', 6, '\u304D', '\u30AD', 'ki', 'ki', 'ki');
    HiraganaKey_KU_instance = new HiraganaKey('KU', 7, '\u304F', '\u30AF', 'k\u026F', 'ku', 'ku');
    HiraganaKey_KE_instance = new HiraganaKey('KE', 8, '\u3051', '\u30B1', 'ke', 'ke', 'ke');
    HiraganaKey_KO_instance = new HiraganaKey('KO', 9, '\u3053', '\u30B3', 'ko', 'ko', 'ko');
    HiraganaKey_SA_instance = new HiraganaKey('SA', 10, '\u3055', '\u30B5', 'sa', 'sa', 's');
    HiraganaKey_SHI_instance = new HiraganaKey('SHI', 11, '\u3057', '\u30B7', '\u0255i', 'shi', 'si');
    HiraganaKey_SU_instance = new HiraganaKey('SU', 12, '\u3059', '\u30B9', 's\u026F', 'su', 'su');
    HiraganaKey_SE_instance = new HiraganaKey('SE', 13, '\u305B', '\u30BB', 'se', 'se', 'se');
    HiraganaKey_SO_instance = new HiraganaKey('SO', 14, '\u305D', '\u30BD', 'so', 'so', 'so');
    HiraganaKey_TA_instance = new HiraganaKey('TA', 15, '\u305F', '\u30BF', 'ta', 'ta', 't');
    HiraganaKey_CHI_instance = new HiraganaKey('CHI', 16, '\u3061', '\u30C1', 't\u0255i', 'chi', 'ti');
    HiraganaKey_TSU_instance = new HiraganaKey('TSU', 17, '\u3064', '\u30C4', 'ts\u026F', 'tsu', 'tu');
    HiraganaKey_TE_instance = new HiraganaKey('TE', 18, '\u3066', '\u30C6', 'te', 'te', 'te');
    HiraganaKey_TO_instance = new HiraganaKey('TO', 19, '\u3068', '\u30C8', 'to', 'to', 'to');
    HiraganaKey_NA_instance = new HiraganaKey('NA', 20, '\u306A', '\u30CA', 'na', 'na', 'n');
    HiraganaKey_NI_instance = new HiraganaKey('NI', 21, '\u306B', '\u30CB', '\u0272i', 'ni', 'ni');
    HiraganaKey_NU_instance = new HiraganaKey('NU', 22, '\u306C', '\u30CC', 'n\u026F', 'nu', 'nu');
    HiraganaKey_NE_instance = new HiraganaKey('NE', 23, '\u306D', '\u30CD', 'ne', 'ne', 'ne');
    HiraganaKey_NO_instance = new HiraganaKey('NO', 24, '\u306E', '\u30CE', 'no', 'no', 'no');
    HiraganaKey_HA_instance = new HiraganaKey('HA', 25, '\u306F', '\u30CF', 'ha', 'ha', 'h');
    HiraganaKey_HI_instance = new HiraganaKey('HI', 26, '\u3072', '\u30D2', '\xE7i', 'hi', 'hi');
    HiraganaKey_FU_instance = new HiraganaKey('FU', 27, '\u3075', '\u30D5', '\u0278\u026F', 'fu', 'hu');
    HiraganaKey_HE_instance = new HiraganaKey('HE', 28, '\u3078', '\u30D8', 'he', 'he', 'he');
    HiraganaKey_HO_instance = new HiraganaKey('HO', 29, '\u307B', '\u30DB', 'ho', 'ho', 'ho');
    HiraganaKey_MA_instance = new HiraganaKey('MA', 30, '\u307E', '\u30DE', 'ma', 'ma', 'm');
    HiraganaKey_MI_instance = new HiraganaKey('MI', 31, '\u307F', '\u30DF', 'mi', 'mi', 'mi');
    HiraganaKey_MU_instance = new HiraganaKey('MU', 32, '\u3080', '\u30E0', 'm\u026F', 'mu', 'mu');
    HiraganaKey_ME_instance = new HiraganaKey('ME', 33, '\u3081', '\u30E1', 'me', 'me', 'me');
    HiraganaKey_MO_instance = new HiraganaKey('MO', 34, '\u3082', '\u30E2', 'mo', 'mo', 'mo');
    HiraganaKey_YA_instance = new HiraganaKey('YA', 35, '\u3084', '\u30E4', 'ja', 'ya', 'y');
    HiraganaKey_YU_instance = new HiraganaKey('YU', 36, '\u3086', '\u30E6', 'j\u026F', 'yu', 'yu');
    HiraganaKey_YO_instance = new HiraganaKey('YO', 37, '\u3088', '\u30E8', 'jo', 'yo', 'yo');
    HiraganaKey_RA_instance = new HiraganaKey('RA', 38, '\u3089', '\u30E9', '\u027Ea', 'ra', 'r');
    HiraganaKey_RI_instance = new HiraganaKey('RI', 39, '\u308A', '\u30EA', '\u027Ei', 'ri', 'ri');
    HiraganaKey_RU_instance = new HiraganaKey('RU', 40, '\u308B', '\u30EB', '\u027E\u026F', 'ru', 'ru');
    HiraganaKey_RE_instance = new HiraganaKey('RE', 41, '\u308C', '\u30EC', '\u027Ee', 're', 're');
    HiraganaKey_RO_instance = new HiraganaKey('RO', 42, '\u308D', '\u30ED', '\u027Eo', 'ro', 'ro');
    HiraganaKey_WA_instance = new HiraganaKey('WA', 43, '\u308F', '\u30EF', 'wa', 'wa', 'w');
    HiraganaKey_WO_instance = new HiraganaKey('WO', 44, '\u3092', '\u30F2', 'o', 'wo', 'wo');
    HiraganaKey_N_instance = new HiraganaKey('N', 45, '\u3093', '\u30F3', 'n', 'n', 'nn');
    Companion_getInstance_14();
  }
  var $ENTRIES_13;
  function HiraganaKey(name, ordinal, char, katakana, ipa, displayName, romaji) {
    Enum.call(this, name, ordinal);
    this.zs_1 = char;
    this.at_1 = katakana;
    this.bt_1 = ipa;
    this.ct_1 = displayName;
    this.dt_1 = romaji;
  }
  protoOf(HiraganaKey).di = function () {
    return this.zs_1;
  };
  protoOf(HiraganaKey).ei = function () {
    return this.bt_1;
  };
  protoOf(HiraganaKey).fi = function () {
    return this.ct_1;
  };
  protoOf(HiraganaKey).gi = function () {
    return new SimpleKey(this.at_1, this.bt_1, this.ct_1 + '-katakana');
  };
  var DevanagariKey_A_instance;
  var DevanagariKey_AA_instance;
  var DevanagariKey_I_instance;
  var DevanagariKey_II_instance;
  var DevanagariKey_U_instance;
  var DevanagariKey_UU_instance;
  var DevanagariKey_E_instance;
  var DevanagariKey_AI_instance;
  var DevanagariKey_O_instance;
  var DevanagariKey_AU_instance;
  var DevanagariKey_KA_instance;
  var DevanagariKey_KHA_instance;
  var DevanagariKey_GA_instance;
  var DevanagariKey_GHA_instance;
  var DevanagariKey_NGA_instance;
  var DevanagariKey_CHA_instance;
  var DevanagariKey_CHHA_instance;
  var DevanagariKey_JA_instance;
  var DevanagariKey_JHA_instance;
  var DevanagariKey_NYA_instance;
  var DevanagariKey_TA_instance;
  var DevanagariKey_THA_instance;
  var DevanagariKey_DA_instance;
  var DevanagariKey_DHA_instance;
  var DevanagariKey_NA_R_instance;
  var DevanagariKey_TA_D_instance;
  var DevanagariKey_THA_D_instance;
  var DevanagariKey_DA_D_instance;
  var DevanagariKey_DHA_D_instance;
  var DevanagariKey_NA_instance;
  var DevanagariKey_PA_instance;
  var DevanagariKey_PHA_instance;
  var DevanagariKey_BA_instance;
  var DevanagariKey_BHA_instance;
  var DevanagariKey_MA_instance;
  var DevanagariKey_YA_instance;
  var DevanagariKey_RA_instance;
  var DevanagariKey_LA_instance;
  var DevanagariKey_VA_instance;
  var DevanagariKey_SHA_instance;
  var DevanagariKey_SHA_R_instance;
  var DevanagariKey_SA_instance;
  var DevanagariKey_HA_instance;
  function Companion_15() {
    Companion_instance_15 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_14();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.jt_1) {
        destination.g(element);
      }
    }
    tmp.lt_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_0 = get_entries_14();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      if (element_0.jt_1) {
        destination_0.g(element_0);
      }
    }
    tmp_0.mt_1 = destination_0;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_14();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_1 = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s_1 = this_0.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      var tmp_2 = firstOrNull(element_1.gt_1);
      var tmp$ret$6 = tmp_2 == null ? null : new Char(tmp_2);
      destination_1.l3(tmp$ret$6, element_1);
    }
    tmp_1.nt_1 = destination_1;
    var tmp_3 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_1 = get_entries_14();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_2 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_2 = tmp0_1.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      var tmp_4 = element_2.kt_1;
      if (!((tmp_4 == null ? null : new Char(tmp_4)) == null)) {
        destination_2.g(element_2);
      }
    }
    // Inline function 'kotlin.collections.associateBy' call
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(destination_2, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_3 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_3 = destination_2.i();
    while (_iterator__ex2g4s_3.j()) {
      var element_3 = _iterator__ex2g4s_3.k();
      var tmp_5 = element_3.kt_1;
      var tmp$ret$12 = tmp_5 == null ? null : new Char(tmp_5);
      destination_3.l3(tmp$ret$12, element_3);
    }
    tmp_3.ot_1 = destination_3;
  }
  protoOf(Companion_15).cj = function (c) {
    return this.nt_1.q1(new Char(c));
  };
  protoOf(Companion_15).pt = function (c) {
    var tmp0_safe_receiver = this.nt_1.q1(new Char(c));
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ht_1;
    var tmp;
    if (tmp1_elvis_lhs == null) {
      var tmp2_safe_receiver = this.ot_1.q1(new Char(c));
      tmp = tmp2_safe_receiver == null ? null : tmp2_safe_receiver.ht_1;
    } else {
      tmp = tmp1_elvis_lhs;
    }
    var tmp3_elvis_lhs = tmp;
    return tmp3_elvis_lhs == null ? '\u0259' : tmp3_elvis_lhs;
  };
  var Companion_instance_15;
  function Companion_getInstance_15() {
    DevanagariKey_initEntries();
    if (Companion_instance_15 == null)
      new Companion_15();
    return Companion_instance_15;
  }
  function values_14() {
    return [DevanagariKey_A_getInstance(), DevanagariKey_AA_getInstance(), DevanagariKey_I_getInstance(), DevanagariKey_II_getInstance(), DevanagariKey_U_getInstance(), DevanagariKey_UU_getInstance(), DevanagariKey_E_getInstance(), DevanagariKey_AI_getInstance(), DevanagariKey_O_getInstance(), DevanagariKey_AU_getInstance(), DevanagariKey_KA_getInstance(), DevanagariKey_KHA_getInstance(), DevanagariKey_GA_getInstance(), DevanagariKey_GHA_getInstance(), DevanagariKey_NGA_getInstance(), DevanagariKey_CHA_getInstance(), DevanagariKey_CHHA_getInstance(), DevanagariKey_JA_getInstance(), DevanagariKey_JHA_getInstance(), DevanagariKey_NYA_getInstance(), DevanagariKey_TA_getInstance(), DevanagariKey_THA_getInstance(), DevanagariKey_DA_getInstance(), DevanagariKey_DHA_getInstance(), DevanagariKey_NA_R_getInstance(), DevanagariKey_TA_D_getInstance(), DevanagariKey_THA_D_getInstance(), DevanagariKey_DA_D_getInstance(), DevanagariKey_DHA_D_getInstance(), DevanagariKey_NA_getInstance(), DevanagariKey_PA_getInstance(), DevanagariKey_PHA_getInstance(), DevanagariKey_BA_getInstance(), DevanagariKey_BHA_getInstance(), DevanagariKey_MA_getInstance(), DevanagariKey_YA_getInstance(), DevanagariKey_RA_getInstance(), DevanagariKey_LA_getInstance(), DevanagariKey_VA_getInstance(), DevanagariKey_SHA_getInstance(), DevanagariKey_SHA_R_getInstance(), DevanagariKey_SA_getInstance(), DevanagariKey_HA_getInstance()];
  }
  function get_entries_14() {
    if ($ENTRIES_14 == null)
      $ENTRIES_14 = enumEntries(values_14());
    return $ENTRIES_14;
  }
  var DevanagariKey_entriesInitialized;
  function DevanagariKey_initEntries() {
    if (DevanagariKey_entriesInitialized)
      return Unit_instance;
    DevanagariKey_entriesInitialized = true;
    DevanagariKey_A_instance = new DevanagariKey('A', 0, '\u0905', '\u0259', 'a', true);
    DevanagariKey_AA_instance = new DevanagariKey('AA', 1, '\u0906', 'a\u02D0', 'aa', true, _Char___init__impl__6a9atx(2366));
    DevanagariKey_I_instance = new DevanagariKey('I', 2, '\u0907', '\u026A', 'i', true, _Char___init__impl__6a9atx(2367));
    DevanagariKey_II_instance = new DevanagariKey('II', 3, '\u0908', 'i\u02D0', 'ii', true, _Char___init__impl__6a9atx(2368));
    DevanagariKey_U_instance = new DevanagariKey('U', 4, '\u0909', '\u028A', 'u', true, _Char___init__impl__6a9atx(2369));
    DevanagariKey_UU_instance = new DevanagariKey('UU', 5, '\u090A', 'u\u02D0', 'uu', true, _Char___init__impl__6a9atx(2370));
    DevanagariKey_E_instance = new DevanagariKey('E', 6, '\u090F', 'e\u02D0', 'e', true, _Char___init__impl__6a9atx(2375));
    DevanagariKey_AI_instance = new DevanagariKey('AI', 7, '\u0910', '\u025B\u02D0', 'ai', true, _Char___init__impl__6a9atx(2376));
    DevanagariKey_O_instance = new DevanagariKey('O', 8, '\u0913', 'o\u02D0', 'o', true, _Char___init__impl__6a9atx(2379));
    DevanagariKey_AU_instance = new DevanagariKey('AU', 9, '\u0914', '\u0254\u02D0', 'au', true, _Char___init__impl__6a9atx(2380));
    DevanagariKey_KA_instance = new DevanagariKey('KA', 10, '\u0915', 'k', 'ka');
    DevanagariKey_KHA_instance = new DevanagariKey('KHA', 11, '\u0916', 'k\u02B0', 'kha');
    DevanagariKey_GA_instance = new DevanagariKey('GA', 12, '\u0917', '\u0261', 'ga');
    DevanagariKey_GHA_instance = new DevanagariKey('GHA', 13, '\u0918', '\u0261\u02B1', 'gha');
    DevanagariKey_NGA_instance = new DevanagariKey('NGA', 14, '\u0919', '\u014B', 'nga');
    DevanagariKey_CHA_instance = new DevanagariKey('CHA', 15, '\u091A', 't\u0283', 'cha');
    DevanagariKey_CHHA_instance = new DevanagariKey('CHHA', 16, '\u091B', 't\u0283\u02B0', 'chha');
    DevanagariKey_JA_instance = new DevanagariKey('JA', 17, '\u091C', 'd\u0292', 'ja');
    DevanagariKey_JHA_instance = new DevanagariKey('JHA', 18, '\u091D', 'd\u0292\u02B1', 'jha');
    DevanagariKey_NYA_instance = new DevanagariKey('NYA', 19, '\u091E', '\u0272', 'nya');
    DevanagariKey_TA_instance = new DevanagariKey('TA', 20, '\u091F', '\u0288', 'ta');
    DevanagariKey_THA_instance = new DevanagariKey('THA', 21, '\u0920', '\u0288\u02B0', 'tha');
    DevanagariKey_DA_instance = new DevanagariKey('DA', 22, '\u0921', '\u0256', 'da');
    DevanagariKey_DHA_instance = new DevanagariKey('DHA', 23, '\u0922', '\u0256\u02B1', 'dha');
    DevanagariKey_NA_R_instance = new DevanagariKey('NA_R', 24, '\u0923', '\u0273', 'na-retroflex');
    DevanagariKey_TA_D_instance = new DevanagariKey('TA_D', 25, '\u0924', 't', 'ta-dental');
    DevanagariKey_THA_D_instance = new DevanagariKey('THA_D', 26, '\u0925', 't\u02B0', 'tha-dental');
    DevanagariKey_DA_D_instance = new DevanagariKey('DA_D', 27, '\u0926', 'd', 'da-dental');
    DevanagariKey_DHA_D_instance = new DevanagariKey('DHA_D', 28, '\u0927', 'd\u02B1', 'dha-dental');
    DevanagariKey_NA_instance = new DevanagariKey('NA', 29, '\u0928', 'n', 'na');
    DevanagariKey_PA_instance = new DevanagariKey('PA', 30, '\u092A', 'p', 'pa');
    DevanagariKey_PHA_instance = new DevanagariKey('PHA', 31, '\u092B', 'p\u02B0', 'pha');
    DevanagariKey_BA_instance = new DevanagariKey('BA', 32, '\u092C', 'b', 'ba');
    DevanagariKey_BHA_instance = new DevanagariKey('BHA', 33, '\u092D', 'b\u02B1', 'bha');
    DevanagariKey_MA_instance = new DevanagariKey('MA', 34, '\u092E', 'm', 'ma');
    DevanagariKey_YA_instance = new DevanagariKey('YA', 35, '\u092F', 'j', 'ya');
    DevanagariKey_RA_instance = new DevanagariKey('RA', 36, '\u0930', 'r', 'ra');
    DevanagariKey_LA_instance = new DevanagariKey('LA', 37, '\u0932', 'l', 'la');
    DevanagariKey_VA_instance = new DevanagariKey('VA', 38, '\u0935', '\u028B', 'va');
    DevanagariKey_SHA_instance = new DevanagariKey('SHA', 39, '\u0936', '\u0283', 'sha');
    DevanagariKey_SHA_R_instance = new DevanagariKey('SHA_R', 40, '\u0937', '\u0282', 'sha-retroflex');
    DevanagariKey_SA_instance = new DevanagariKey('SA', 41, '\u0938', 's', 'sa');
    DevanagariKey_HA_instance = new DevanagariKey('HA', 42, '\u0939', '\u0266', 'ha');
    Companion_getInstance_15();
  }
  var $ENTRIES_14;
  function DevanagariKey(name, ordinal, char, ipa, displayName, isVowel, matra) {
    isVowel = isVowel === VOID ? false : isVowel;
    matra = matra === VOID ? null : matra;
    Enum.call(this, name, ordinal);
    this.gt_1 = char;
    this.ht_1 = ipa;
    this.it_1 = displayName;
    this.jt_1 = isVowel;
    this.kt_1 = matra;
  }
  protoOf(DevanagariKey).di = function () {
    return this.gt_1;
  };
  protoOf(DevanagariKey).ei = function () {
    return this.ht_1;
  };
  protoOf(DevanagariKey).fi = function () {
    return this.it_1;
  };
  var LatinKey_A_instance;
  var LatinKey_B_instance;
  var LatinKey_C_instance;
  var LatinKey_D_instance;
  var LatinKey_E_instance;
  var LatinKey_F_instance;
  var LatinKey_G_instance;
  var LatinKey_H_instance;
  var LatinKey_I_instance;
  var LatinKey_J_instance;
  var LatinKey_K_instance;
  var LatinKey_L_instance;
  var LatinKey_M_instance;
  var LatinKey_N_instance;
  var LatinKey_O_instance;
  var LatinKey_P_instance;
  var LatinKey_Q_instance;
  var LatinKey_R_instance;
  var LatinKey_S_instance;
  var LatinKey_T_instance;
  var LatinKey_U_instance;
  var LatinKey_V_instance;
  var LatinKey_W_instance;
  var LatinKey_X_instance;
  var LatinKey_Y_instance;
  var LatinKey_Z_instance;
  function Companion_16() {
    Companion_instance_16 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = get_entries_15();
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.in_1;
      destination.l3(tmp$ret$0, element);
    }
    tmp.cn_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_15();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key = element_0.kn_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination_0.q1(key);
      var tmp_1;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination_0.l3(key, answer);
        tmp_1 = answer;
      } else {
        tmp_1 = value;
      }
      var list = tmp_1;
      list.g(element_0);
    }
    tmp_0.dn_1 = destination_0;
    var tmp_2 = this;
    // Inline function 'kotlin.collections.associate' call
    var this_1 = get_entries_15();
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination_1 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_1 = this_1.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      // Inline function 'kotlin.collections.plusAssign' call
      var pair = to(element_1.in_1, isInterface(element_1, ILayoutKey) ? element_1 : THROW_CCE());
      destination_1.l3(pair.p9_1, pair.q9_1);
    }
    tmp_2.en_1 = destination_1;
  }
  protoOf(Companion_16).fn = function (c) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = c.toLowerCase();
    return this.cn_1.q1(tmp$ret$1);
  };
  var Companion_instance_16;
  function Companion_getInstance_16() {
    LatinKey_initEntries();
    if (Companion_instance_16 == null)
      new Companion_16();
    return Companion_instance_16;
  }
  function values_15() {
    return [LatinKey_A_getInstance(), LatinKey_B_getInstance(), LatinKey_C_getInstance(), LatinKey_D_getInstance(), LatinKey_E_getInstance(), LatinKey_F_getInstance(), LatinKey_G_getInstance(), LatinKey_H_getInstance(), LatinKey_I_getInstance(), LatinKey_J_getInstance(), LatinKey_K_getInstance(), LatinKey_L_getInstance(), LatinKey_M_getInstance(), LatinKey_N_getInstance(), LatinKey_O_getInstance(), LatinKey_P_getInstance(), LatinKey_Q_getInstance(), LatinKey_R_getInstance(), LatinKey_S_getInstance(), LatinKey_T_getInstance(), LatinKey_U_getInstance(), LatinKey_V_getInstance(), LatinKey_W_getInstance(), LatinKey_X_getInstance(), LatinKey_Y_getInstance(), LatinKey_Z_getInstance()];
  }
  function get_entries_15() {
    if ($ENTRIES_15 == null)
      $ENTRIES_15 = enumEntries(values_15());
    return $ENTRIES_15;
  }
  var LatinKey_entriesInitialized;
  function LatinKey_initEntries() {
    if (LatinKey_entriesInitialized)
      return Unit_instance;
    LatinKey_entriesInitialized = true;
    LatinKey_A_instance = new LatinKey('A', 0, 'a', 'A', 'a', 'a');
    LatinKey_B_instance = new LatinKey('B', 1, 'b', 'B', 'b', 'b');
    LatinKey_C_instance = new LatinKey('C', 2, 'c', 'C', 'k', 'c');
    LatinKey_D_instance = new LatinKey('D', 3, 'd', 'D', 'd', 'd');
    LatinKey_E_instance = new LatinKey('E', 4, 'e', 'E', 'e', 'e');
    LatinKey_F_instance = new LatinKey('F', 5, 'f', 'F', 'f', 'f');
    LatinKey_G_instance = new LatinKey('G', 6, 'g', 'G', '\u0261', 'g');
    LatinKey_H_instance = new LatinKey('H', 7, 'h', 'H', 'h', 'h');
    LatinKey_I_instance = new LatinKey('I', 8, 'i', 'I', 'i', 'i');
    LatinKey_J_instance = new LatinKey('J', 9, 'j', 'J', 'd\u0292', 'j');
    LatinKey_K_instance = new LatinKey('K', 10, 'k', 'K', 'k', 'k');
    LatinKey_L_instance = new LatinKey('L', 11, 'l', 'L', 'l', 'l');
    LatinKey_M_instance = new LatinKey('M', 12, 'm', 'M', 'm', 'm');
    LatinKey_N_instance = new LatinKey('N', 13, 'n', 'N', 'n', 'n');
    LatinKey_O_instance = new LatinKey('O', 14, 'o', 'O', 'o', 'o');
    LatinKey_P_instance = new LatinKey('P', 15, 'p', 'P', 'p', 'p');
    LatinKey_Q_instance = new LatinKey('Q', 16, 'q', 'Q', 'k', 'q');
    LatinKey_R_instance = new LatinKey('R', 17, 'r', 'R', 'r', 'r');
    LatinKey_S_instance = new LatinKey('S', 18, 's', 'S', 's', 's');
    LatinKey_T_instance = new LatinKey('T', 19, 't', 'T', 't', 't');
    LatinKey_U_instance = new LatinKey('U', 20, 'u', 'U', 'u', 'u');
    LatinKey_V_instance = new LatinKey('V', 21, 'v', 'V', 'v', 'v');
    LatinKey_W_instance = new LatinKey('W', 22, 'w', 'W', 'w', 'w');
    LatinKey_X_instance = new LatinKey('X', 23, 'x', 'X', 'ks', 'x');
    LatinKey_Y_instance = new LatinKey('Y', 24, 'y', 'Y', 'j', 'y');
    LatinKey_Z_instance = new LatinKey('Z', 25, 'z', 'Z', 'z', 'z');
    Companion_getInstance_16();
  }
  var $ENTRIES_15;
  function LatinKey(name, ordinal, char, upper, ipa, displayName) {
    Enum.call(this, name, ordinal);
    this.in_1 = char;
    this.jn_1 = upper;
    this.kn_1 = ipa;
    this.ln_1 = displayName;
  }
  protoOf(LatinKey).di = function () {
    return this.in_1;
  };
  protoOf(LatinKey).ei = function () {
    return this.kn_1;
  };
  protoOf(LatinKey).fi = function () {
    return this.ln_1;
  };
  protoOf(LatinKey).gi = function () {
    // Inline function 'kotlin.text.uppercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.ln_1.toUpperCase();
    return new SimpleKey(this.jn_1, this.kn_1, tmp$ret$1);
  };
  function sam$kotlin_Comparator$0(function_0) {
    this.qt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0).x8 = function (a, b) {
    return this.qt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0).e2 = function () {
    return this.qt_1;
  };
  protoOf(sam$kotlin_Comparator$0).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0).hashCode = function () {
    return hashCode(this.e2());
  };
  function EnglishPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.bc_1.length;
    var tmp$ret$1 = a.bc_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var EnglishPattern_OUGHT_instance;
  var EnglishPattern_OUGH_O_instance;
  var EnglishPattern_OUGH_UFF_instance;
  var EnglishPattern_OUGH_OFF_instance;
  var EnglishPattern_OUGH_OO_instance;
  var EnglishPattern_OUGH_OW_instance;
  var EnglishPattern_AUGHT_instance;
  var EnglishPattern_AUGH_instance;
  var EnglishPattern_LAUGH_instance;
  var EnglishPattern_EIGHT_instance;
  var EnglishPattern_EIGH_instance;
  var EnglishPattern_HEIGHT_instance;
  var EnglishPattern_IGHT_instance;
  var EnglishPattern_IGH_instance;
  var EnglishPattern_TION_instance;
  var EnglishPattern_SION_ZH_instance;
  var EnglishPattern_SION_SH_instance;
  var EnglishPattern_ING_instance;
  var EnglishPattern_TING_instance;
  var EnglishPattern_RING_VERB_instance;
  var EnglishPattern_OUND_instance;
  var EnglishPattern_OUNT_instance;
  var EnglishPattern_OUT_instance;
  var EnglishPattern_OUR_instance;
  var EnglishPattern_OUR_ER_instance;
  var EnglishPattern_OUSE_instance;
  var EnglishPattern_OUSE_Z_instance;
  var EnglishPattern_OULD_instance;
  var EnglishPattern_OUP_instance;
  var EnglishPattern_OWN_OWN_instance;
  var EnglishPattern_OWN_OWN2_instance;
  var EnglishPattern_OW_LONG_instance;
  var EnglishPattern_OW_OW_instance;
  var EnglishPattern_EAR_EER_instance;
  var EnglishPattern_EAR_AIR_instance;
  var EnglishPattern_EAR_ER_instance;
  var EnglishPattern_EAT_instance;
  var EnglishPattern_EAD_EED_instance;
  var EnglishPattern_EAD_ED_instance;
  var EnglishPattern_EAK_instance;
  var EnglishPattern_EAL_instance;
  var EnglishPattern_EAM_instance;
  var EnglishPattern_EAN_instance;
  var EnglishPattern_EAP_instance;
  var EnglishPattern_EAS_instance;
  var EnglishPattern_EAST_instance;
  var EnglishPattern_EATH_instance;
  var EnglishPattern_EATH_SHORT_instance;
  var EnglishPattern_OOK_instance;
  var EnglishPattern_OOD_GOOD_instance;
  var EnglishPattern_OOD_FOOD_instance;
  var EnglishPattern_OOL_instance;
  var EnglishPattern_OOM_instance;
  var EnglishPattern_OON_instance;
  var EnglishPattern_OOP_instance;
  var EnglishPattern_OOR_instance;
  var EnglishPattern_OOT_FOOT_instance;
  var EnglishPattern_OOT_BOOT_instance;
  var EnglishPattern_OOTH_instance;
  var EnglishPattern_AY_instance;
  var EnglishPattern_EY_instance;
  var EnglishPattern_EY_I_instance;
  var EnglishPattern_OY_instance;
  var EnglishPattern_OI_instance;
  var EnglishPattern_OISE_instance;
  var EnglishPattern_OICE_instance;
  var EnglishPattern_AWE_instance;
  var EnglishPattern_AW_instance;
  var EnglishPattern_AWN_instance;
  var EnglishPattern_AWL_instance;
  var EnglishPattern_AIR_instance;
  var EnglishPattern_ARE_instance;
  var EnglishPattern_ER_instance;
  var EnglishPattern_IR_instance;
  var EnglishPattern_UR_instance;
  var EnglishPattern_ALL_instance;
  var EnglishPattern_ALK_instance;
  var EnglishPattern_ALT_instance;
  var EnglishPattern_KN_instance;
  var EnglishPattern_WR_instance;
  var EnglishPattern_GN_instance;
  var EnglishPattern_MB_instance;
  var EnglishPattern_GH_SILENT_instance;
  var EnglishPattern_ABLE_instance;
  var EnglishPattern_IBLE_instance;
  var EnglishPattern_NESS_instance;
  var EnglishPattern_MENT_instance;
  var EnglishPattern_ENCE_instance;
  var EnglishPattern_ANCE_instance;
  var EnglishPattern_ENT_instance;
  var EnglishPattern_ANT_instance;
  var EnglishPattern_LY_instance;
  var EnglishPattern_FUL_instance;
  var EnglishPattern_LESS_instance;
  var EnglishPattern_ED_T_instance;
  var EnglishPattern_ED_D_instance;
  var EnglishPattern_ED_ID_instance;
  function Companion_17() {
    Companion_instance_17 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_16();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.bc_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.wb_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_16();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.cc_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.xb_1 = destination_0;
  }
  protoOf(Companion_17).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_16();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.fc_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = EnglishPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.bc_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_17;
  function Companion_getInstance_17() {
    EnglishPattern_initEntries();
    if (Companion_instance_17 == null)
      new Companion_17();
    return Companion_instance_17;
  }
  function values_16() {
    return [EnglishPattern_OUGHT_getInstance(), EnglishPattern_OUGH_O_getInstance(), EnglishPattern_OUGH_UFF_getInstance(), EnglishPattern_OUGH_OFF_getInstance(), EnglishPattern_OUGH_OO_getInstance(), EnglishPattern_OUGH_OW_getInstance(), EnglishPattern_AUGHT_getInstance(), EnglishPattern_AUGH_getInstance(), EnglishPattern_LAUGH_getInstance(), EnglishPattern_EIGHT_getInstance(), EnglishPattern_EIGH_getInstance(), EnglishPattern_HEIGHT_getInstance(), EnglishPattern_IGHT_getInstance(), EnglishPattern_IGH_getInstance(), EnglishPattern_TION_getInstance(), EnglishPattern_SION_ZH_getInstance(), EnglishPattern_SION_SH_getInstance(), EnglishPattern_ING_getInstance(), EnglishPattern_TING_getInstance(), EnglishPattern_RING_VERB_getInstance(), EnglishPattern_OUND_getInstance(), EnglishPattern_OUNT_getInstance(), EnglishPattern_OUT_getInstance(), EnglishPattern_OUR_getInstance(), EnglishPattern_OUR_ER_getInstance(), EnglishPattern_OUSE_getInstance(), EnglishPattern_OUSE_Z_getInstance(), EnglishPattern_OULD_getInstance(), EnglishPattern_OUP_getInstance(), EnglishPattern_OWN_OWN_getInstance(), EnglishPattern_OWN_OWN2_getInstance(), EnglishPattern_OW_LONG_getInstance(), EnglishPattern_OW_OW_getInstance(), EnglishPattern_EAR_EER_getInstance(), EnglishPattern_EAR_AIR_getInstance(), EnglishPattern_EAR_ER_getInstance(), EnglishPattern_EAT_getInstance(), EnglishPattern_EAD_EED_getInstance(), EnglishPattern_EAD_ED_getInstance(), EnglishPattern_EAK_getInstance(), EnglishPattern_EAL_getInstance(), EnglishPattern_EAM_getInstance(), EnglishPattern_EAN_getInstance(), EnglishPattern_EAP_getInstance(), EnglishPattern_EAS_getInstance(), EnglishPattern_EAST_getInstance(), EnglishPattern_EATH_getInstance(), EnglishPattern_EATH_SHORT_getInstance(), EnglishPattern_OOK_getInstance(), EnglishPattern_OOD_GOOD_getInstance(), EnglishPattern_OOD_FOOD_getInstance(), EnglishPattern_OOL_getInstance(), EnglishPattern_OOM_getInstance(), EnglishPattern_OON_getInstance(), EnglishPattern_OOP_getInstance(), EnglishPattern_OOR_getInstance(), EnglishPattern_OOT_FOOT_getInstance(), EnglishPattern_OOT_BOOT_getInstance(), EnglishPattern_OOTH_getInstance(), EnglishPattern_AY_getInstance(), EnglishPattern_EY_getInstance(), EnglishPattern_EY_I_getInstance(), EnglishPattern_OY_getInstance(), EnglishPattern_OI_getInstance(), EnglishPattern_OISE_getInstance(), EnglishPattern_OICE_getInstance(), EnglishPattern_AWE_getInstance(), EnglishPattern_AW_getInstance(), EnglishPattern_AWN_getInstance(), EnglishPattern_AWL_getInstance(), EnglishPattern_AIR_getInstance(), EnglishPattern_ARE_getInstance(), EnglishPattern_ER_getInstance(), EnglishPattern_IR_getInstance(), EnglishPattern_UR_getInstance(), EnglishPattern_ALL_getInstance(), EnglishPattern_ALK_getInstance(), EnglishPattern_ALT_getInstance(), EnglishPattern_KN_getInstance(), EnglishPattern_WR_getInstance(), EnglishPattern_GN_getInstance(), EnglishPattern_MB_getInstance(), EnglishPattern_GH_SILENT_getInstance(), EnglishPattern_ABLE_getInstance(), EnglishPattern_IBLE_getInstance(), EnglishPattern_NESS_getInstance(), EnglishPattern_MENT_getInstance(), EnglishPattern_ENCE_getInstance(), EnglishPattern_ANCE_getInstance(), EnglishPattern_ENT_getInstance(), EnglishPattern_ANT_getInstance(), EnglishPattern_LY_getInstance(), EnglishPattern_FUL_getInstance(), EnglishPattern_LESS_getInstance(), EnglishPattern_ED_T_getInstance(), EnglishPattern_ED_D_getInstance(), EnglishPattern_ED_ID_getInstance()];
  }
  function get_entries_16() {
    if ($ENTRIES_16 == null)
      $ENTRIES_16 = enumEntries(values_16());
    return $ENTRIES_16;
  }
  var EnglishPattern_entriesInitialized;
  function EnglishPattern_initEntries() {
    if (EnglishPattern_entriesInitialized)
      return Unit_instance;
    EnglishPattern_entriesInitialized = true;
    EnglishPattern_OUGHT_instance = new EnglishPattern('OUGHT', 0, 'ought', '\u0254\u02D0t', 'ought', listOf(['bought', 'thought', 'fought', 'brought']), PatternPosition_END_getInstance());
    EnglishPattern_OUGH_O_instance = new EnglishPattern('OUGH_O', 1, 'ough', 'o\u028A', 'ough-long-o', listOf(['though', 'dough', 'although']), PatternPosition_END_getInstance());
    EnglishPattern_OUGH_UFF_instance = new EnglishPattern('OUGH_UFF', 2, 'ough', '\u028Cf', 'ough-uff', listOf(['rough', 'tough', 'enough']), PatternPosition_END_getInstance());
    EnglishPattern_OUGH_OFF_instance = new EnglishPattern('OUGH_OFF', 3, 'ough', '\u0252f', 'ough-off', listOf(['cough', 'trough']));
    EnglishPattern_OUGH_OO_instance = new EnglishPattern('OUGH_OO', 4, 'ough', 'u\u02D0', 'ough-oo', listOf(['through', 'throughout']));
    EnglishPattern_OUGH_OW_instance = new EnglishPattern('OUGH_OW', 5, 'ough', 'a\u028A', 'ough-ow', listOf(['bough', 'plough', 'drought']));
    EnglishPattern_AUGHT_instance = new EnglishPattern('AUGHT', 6, 'aught', '\u0254\u02D0t', 'aught', listOf(['caught', 'taught', 'daughter', 'naughty']), PatternPosition_END_getInstance());
    EnglishPattern_AUGH_instance = new EnglishPattern('AUGH', 7, 'augh', '\u0254\u02D0', 'augh', listOf_0('laugh'), PatternPosition_END_getInstance());
    EnglishPattern_LAUGH_instance = new EnglishPattern('LAUGH', 8, 'augh', '\xE6f', 'laugh', listOf(['laugh', 'laughter']));
    EnglishPattern_EIGHT_instance = new EnglishPattern('EIGHT', 9, 'eight', 'e\u026At', 'eight', listOf(['eight', 'weight', 'freight']));
    EnglishPattern_EIGH_instance = new EnglishPattern('EIGH', 10, 'eigh', 'e\u026A', 'eigh', listOf(['weigh', 'sleigh', 'neigh', 'neighbor']));
    EnglishPattern_HEIGHT_instance = new EnglishPattern('HEIGHT', 11, 'eight', 'a\u026At', 'height', listOf_0('height'));
    EnglishPattern_IGHT_instance = new EnglishPattern('IGHT', 12, 'ight', 'a\u026At', 'ight', listOf(['light', 'night', 'right', 'might', 'fight', 'sight']));
    EnglishPattern_IGH_instance = new EnglishPattern('IGH', 13, 'igh', 'a\u026A', 'igh', listOf(['high', 'sigh', 'thigh']));
    EnglishPattern_TION_instance = new EnglishPattern('TION', 14, 'tion', '\u0283\u0259n', 'tion', listOf(['nation', 'station', 'motion', 'action']));
    EnglishPattern_SION_ZH_instance = new EnglishPattern('SION_ZH', 15, 'sion', '\u0292\u0259n', 'sion-zh', listOf(['vision', 'decision', 'television']));
    EnglishPattern_SION_SH_instance = new EnglishPattern('SION_SH', 16, 'sion', '\u0283\u0259n', 'sion-sh', listOf(['mission', 'passion', 'session']));
    EnglishPattern_ING_instance = new EnglishPattern('ING', 17, 'ing', '\u026A\u014B', 'ing', listOf(['sing', 'ring', 'king', 'thing']));
    EnglishPattern_TING_instance = new EnglishPattern('TING', 18, 'ting', 't\u026A\u014B', 'ting', listOf(['sitting', 'hitting', 'cutting']));
    EnglishPattern_RING_VERB_instance = new EnglishPattern('RING_VERB', 19, 'ring', 'r\u026A\u014B', 'ring', listOf(['bring', 'spring', 'string']));
    EnglishPattern_OUND_instance = new EnglishPattern('OUND', 20, 'ound', 'a\u028And', 'ound', listOf(['sound', 'round', 'ground', 'found']));
    EnglishPattern_OUNT_instance = new EnglishPattern('OUNT', 21, 'ount', 'a\u028Ant', 'ount', listOf(['count', 'mount', 'amount']));
    EnglishPattern_OUT_instance = new EnglishPattern('OUT', 22, 'out', 'a\u028At', 'out', listOf(['out', 'about', 'shout', 'scout']));
    EnglishPattern_OUR_instance = new EnglishPattern('OUR', 23, 'our', 'a\u028A\u0259r', 'our', listOf(['our', 'hour', 'sour']));
    EnglishPattern_OUR_ER_instance = new EnglishPattern('OUR_ER', 24, 'our', '\u0259r', 'our-er', listOf(['colour', 'favour', 'honour']));
    EnglishPattern_OUSE_instance = new EnglishPattern('OUSE', 25, 'ouse', 'a\u028As', 'ouse', listOf(['house', 'mouse', 'blouse']));
    EnglishPattern_OUSE_Z_instance = new EnglishPattern('OUSE_Z', 26, 'ouse', 'a\u028Az', 'ouse-z', listOf(['house', 'rouse']));
    EnglishPattern_OULD_instance = new EnglishPattern('OULD', 27, 'ould', '\u028Ad', 'ould', listOf(['could', 'would', 'should']));
    EnglishPattern_OUP_instance = new EnglishPattern('OUP', 28, 'oup', 'u\u02D0p', 'oup', listOf(['soup', 'group', 'coup']));
    EnglishPattern_OWN_OWN_instance = new EnglishPattern('OWN_OWN', 29, 'own', 'o\u028An', 'own-long', listOf(['own', 'grown', 'shown', 'known']));
    EnglishPattern_OWN_OWN2_instance = new EnglishPattern('OWN_OWN2', 30, 'own', 'a\u028An', 'own-down', listOf(['town', 'down', 'brown', 'crown']));
    EnglishPattern_OW_LONG_instance = new EnglishPattern('OW_LONG', 31, 'ow', 'o\u028A', 'ow-long', listOf(['low', 'show', 'know', 'grow', 'slow']));
    EnglishPattern_OW_OW_instance = new EnglishPattern('OW_OW', 32, 'ow', 'a\u028A', 'ow-ow', listOf(['now', 'how', 'cow', 'bow', 'wow']));
    EnglishPattern_EAR_EER_instance = new EnglishPattern('EAR_EER', 33, 'ear', '\u026A\u0259r', 'ear-eer', listOf(['ear', 'hear', 'near', 'fear', 'dear']));
    EnglishPattern_EAR_AIR_instance = new EnglishPattern('EAR_AIR', 34, 'ear', 'e\u0259r', 'ear-air', listOf(['bear', 'wear', 'pear', 'swear']));
    EnglishPattern_EAR_ER_instance = new EnglishPattern('EAR_ER', 35, 'ear', '\u025C\u02D0r', 'ear-er', listOf(['earth', 'learn', 'early', 'search']));
    EnglishPattern_EAT_instance = new EnglishPattern('EAT', 36, 'eat', 'i\u02D0t', 'eat', listOf(['eat', 'beat', 'heat', 'meat', 'seat']));
    EnglishPattern_EAD_EED_instance = new EnglishPattern('EAD_EED', 37, 'ead', 'i\u02D0d', 'ead-eed', listOf(['read', 'lead', 'bead']));
    EnglishPattern_EAD_ED_instance = new EnglishPattern('EAD_ED', 38, 'ead', '\u025Bd', 'ead-ed', listOf(['head', 'dead', 'bread', 'spread']));
    EnglishPattern_EAK_instance = new EnglishPattern('EAK', 39, 'eak', 'i\u02D0k', 'eak', listOf(['speak', 'weak', 'peak', 'sneak']));
    EnglishPattern_EAL_instance = new EnglishPattern('EAL', 40, 'eal', 'i\u02D0l', 'eal', listOf(['deal', 'meal', 'real', 'steal']));
    EnglishPattern_EAM_instance = new EnglishPattern('EAM', 41, 'eam', 'i\u02D0m', 'eam', listOf(['team', 'dream', 'stream', 'cream']));
    EnglishPattern_EAN_instance = new EnglishPattern('EAN', 42, 'ean', 'i\u02D0n', 'ean', listOf(['mean', 'clean', 'bean', 'lean']));
    EnglishPattern_EAP_instance = new EnglishPattern('EAP', 43, 'eap', 'i\u02D0p', 'eap', listOf(['heap', 'leap', 'cheap']));
    EnglishPattern_EAS_instance = new EnglishPattern('EAS', 44, 'eas', 'i\u02D0z', 'eas', listOf(['peas', 'please', 'ease']));
    EnglishPattern_EAST_instance = new EnglishPattern('EAST', 45, 'east', 'i\u02D0st', 'east', listOf(['east', 'beast', 'feast', 'least']));
    EnglishPattern_EATH_instance = new EnglishPattern('EATH', 46, 'eath', 'i\u02D0\u03B8', 'eath', listOf(['beneath', 'wreath']));
    EnglishPattern_EATH_SHORT_instance = new EnglishPattern('EATH_SHORT', 47, 'eath', '\u025B\u03B8', 'eath-short', listOf(['death', 'breath']));
    EnglishPattern_OOK_instance = new EnglishPattern('OOK', 48, 'ook', '\u028Ak', 'ook', listOf(['book', 'look', 'cook', 'hook', 'took']));
    EnglishPattern_OOD_GOOD_instance = new EnglishPattern('OOD_GOOD', 49, 'ood', '\u028Ad', 'ood-good', listOf(['good', 'wood', 'stood', 'hood']));
    EnglishPattern_OOD_FOOD_instance = new EnglishPattern('OOD_FOOD', 50, 'ood', 'u\u02D0d', 'ood-food', listOf(['food', 'mood', 'brood']));
    EnglishPattern_OOL_instance = new EnglishPattern('OOL', 51, 'ool', 'u\u02D0l', 'ool', listOf(['pool', 'cool', 'school', 'tool', 'fool']));
    EnglishPattern_OOM_instance = new EnglishPattern('OOM', 52, 'oom', 'u\u02D0m', 'oom', listOf(['room', 'boom', 'doom', 'zoom', 'bloom']));
    EnglishPattern_OON_instance = new EnglishPattern('OON', 53, 'oon', 'u\u02D0n', 'oon', listOf(['moon', 'soon', 'noon', 'spoon']));
    EnglishPattern_OOP_instance = new EnglishPattern('OOP', 54, 'oop', 'u\u02D0p', 'oop', listOf(['loop', 'hoop', 'troop']));
    EnglishPattern_OOR_instance = new EnglishPattern('OOR', 55, 'oor', '\u0254\u02D0r', 'oor', listOf(['door', 'floor', 'poor']));
    EnglishPattern_OOT_FOOT_instance = new EnglishPattern('OOT_FOOT', 56, 'oot', '\u028At', 'oot-foot', listOf(['foot', 'soot']));
    EnglishPattern_OOT_BOOT_instance = new EnglishPattern('OOT_BOOT', 57, 'oot', 'u\u02D0t', 'oot-boot', listOf(['boot', 'root', 'shoot', 'hoot']));
    EnglishPattern_OOTH_instance = new EnglishPattern('OOTH', 58, 'ooth', 'u\u02D0\u03B8', 'ooth', listOf(['tooth', 'ooth', 'booth']));
    EnglishPattern_AY_instance = new EnglishPattern('AY', 59, 'ay', 'e\u026A', 'ay', listOf(['day', 'say', 'way', 'play', 'stay']));
    EnglishPattern_EY_instance = new EnglishPattern('EY', 60, 'ey', 'e\u026A', 'ey', listOf(['they', 'grey', 'hey', 'prey']));
    EnglishPattern_EY_I_instance = new EnglishPattern('EY_I', 61, 'ey', 'i', 'ey-i', listOf(['key', 'money', 'honey', 'monkey']));
    EnglishPattern_OY_instance = new EnglishPattern('OY', 62, 'oy', '\u0254\u026A', 'oy', listOf(['boy', 'toy', 'joy', 'enjoy']));
    EnglishPattern_OI_instance = new EnglishPattern('OI', 63, 'oi', '\u0254\u026A', 'oi', listOf(['oil', 'coin', 'join', 'point']));
    EnglishPattern_OISE_instance = new EnglishPattern('OISE', 64, 'oise', '\u0254\u026Az', 'oise', listOf(['noise', 'poise']));
    EnglishPattern_OICE_instance = new EnglishPattern('OICE', 65, 'oice', '\u0254\u026As', 'oice', listOf(['voice', 'choice', 'rejoice']));
    EnglishPattern_AWE_instance = new EnglishPattern('AWE', 66, 'awe', '\u0254\u02D0', 'awe', listOf(['awe', 'awesome']));
    EnglishPattern_AW_instance = new EnglishPattern('AW', 67, 'aw', '\u0254\u02D0', 'aw', listOf(['saw', 'law', 'raw', 'draw', 'jaw']));
    EnglishPattern_AWN_instance = new EnglishPattern('AWN', 68, 'awn', '\u0254\u02D0n', 'awn', listOf(['dawn', 'lawn', 'yawn', 'spawn']));
    EnglishPattern_AWL_instance = new EnglishPattern('AWL', 69, 'awl', '\u0254\u02D0l', 'awl', listOf(['crawl', 'shawl', 'brawl']));
    EnglishPattern_AIR_instance = new EnglishPattern('AIR', 70, 'air', 'e\u0259r', 'air', listOf(['air', 'hair', 'fair', 'pair', 'chair']));
    EnglishPattern_ARE_instance = new EnglishPattern('ARE', 71, 'are', 'e\u0259r', 'are', listOf(['care', 'share', 'rare', 'bare', 'dare']));
    EnglishPattern_ER_instance = new EnglishPattern('ER', 72, 'er', '\u0259r', 'er', listOf(['her', 'father', 'water', 'better']));
    EnglishPattern_IR_instance = new EnglishPattern('IR', 73, 'ir', '\u025C\u02D0r', 'ir', listOf(['bird', 'girl', 'first', 'shirt', 'sir']));
    EnglishPattern_UR_instance = new EnglishPattern('UR', 74, 'ur', '\u025C\u02D0r', 'ur', listOf(['turn', 'burn', 'hurt', 'nurse', 'fur']));
    EnglishPattern_ALL_instance = new EnglishPattern('ALL', 75, 'all', '\u0254\u02D0l', 'all', listOf(['all', 'ball', 'call', 'fall', 'tall', 'wall']));
    EnglishPattern_ALK_instance = new EnglishPattern('ALK', 76, 'alk', '\u0254\u02D0k', 'alk', listOf(['walk', 'talk', 'chalk']));
    EnglishPattern_ALT_instance = new EnglishPattern('ALT', 77, 'alt', '\u0254\u02D0lt', 'alt', listOf(['salt', 'halt', 'malt']));
    EnglishPattern_KN_instance = new EnglishPattern('KN', 78, 'kn', 'n', 'kn', listOf(['know', 'knee', 'knife', 'knock', 'knight']));
    EnglishPattern_WR_instance = new EnglishPattern('WR', 79, 'wr', 'r', 'wr', listOf(['write', 'wrong', 'wrap', 'wrist']));
    EnglishPattern_GN_instance = new EnglishPattern('GN', 80, 'gn', 'n', 'gn', listOf(['gnaw', 'gnat', 'sign', 'design']));
    EnglishPattern_MB_instance = new EnglishPattern('MB', 81, 'mb', 'm', 'mb', listOf(['lamb', 'climb', 'thumb', 'bomb', 'comb']), PatternPosition_END_getInstance());
    EnglishPattern_GH_SILENT_instance = new EnglishPattern('GH_SILENT', 82, 'gh', '', 'gh-silent', listOf(['high', 'sigh', 'weigh', 'though']));
    EnglishPattern_ABLE_instance = new EnglishPattern('ABLE', 83, 'able', '\u0259b\u0259l', 'able', listOf(['able', 'table', 'capable']), PatternPosition_END_getInstance());
    EnglishPattern_IBLE_instance = new EnglishPattern('IBLE', 84, 'ible', '\u026Ab\u0259l', 'ible', listOf(['possible', 'visible', 'terrible']), PatternPosition_END_getInstance());
    EnglishPattern_NESS_instance = new EnglishPattern('NESS', 85, 'ness', 'n\u0259s', 'ness', listOf(['happiness', 'darkness', 'kindness']), PatternPosition_END_getInstance());
    EnglishPattern_MENT_instance = new EnglishPattern('MENT', 86, 'ment', 'm\u0259nt', 'ment', listOf(['moment', 'element', 'government']), PatternPosition_END_getInstance());
    EnglishPattern_ENCE_instance = new EnglishPattern('ENCE', 87, 'ence', '\u0259ns', 'ence', listOf(['silence', 'difference', 'presence']), PatternPosition_END_getInstance());
    EnglishPattern_ANCE_instance = new EnglishPattern('ANCE', 88, 'ance', '\u0259ns', 'ance', listOf(['dance', 'chance', 'balance']), PatternPosition_END_getInstance());
    EnglishPattern_ENT_instance = new EnglishPattern('ENT', 89, 'ent', '\u0259nt', 'ent', listOf(['sent', 'tent', 'moment']), PatternPosition_END_getInstance());
    EnglishPattern_ANT_instance = new EnglishPattern('ANT', 90, 'ant', '\u0259nt', 'ant', listOf(['ant', 'plant', 'giant']), PatternPosition_END_getInstance());
    EnglishPattern_LY_instance = new EnglishPattern('LY', 91, 'ly', 'li', 'ly', listOf(['really', 'quickly', 'slowly']), PatternPosition_END_getInstance());
    EnglishPattern_FUL_instance = new EnglishPattern('FUL', 92, 'ful', 'f\u0259l', 'ful', listOf(['beautiful', 'wonderful', 'careful']), PatternPosition_END_getInstance());
    EnglishPattern_LESS_instance = new EnglishPattern('LESS', 93, 'less', 'l\u0259s', 'less', listOf(['helpless', 'careless', 'endless']), PatternPosition_END_getInstance());
    EnglishPattern_ED_T_instance = new EnglishPattern('ED_T', 94, 'ed', 't', 'ed-t', listOf(['walked', 'jumped', 'helped']), PatternPosition_END_getInstance());
    EnglishPattern_ED_D_instance = new EnglishPattern('ED_D', 95, 'ed', 'd', 'ed-d', listOf(['called', 'played', 'loved']), PatternPosition_END_getInstance());
    EnglishPattern_ED_ID_instance = new EnglishPattern('ED_ID', 96, 'ed', '\u026Ad', 'ed-id', listOf(['wanted', 'needed', 'added']), PatternPosition_END_getInstance());
    Companion_getInstance_17();
  }
  var $ENTRIES_16;
  function EnglishPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.bc_1 = pattern;
    this.cc_1 = ipa;
    this.dc_1 = displayName;
    this.ec_1 = examples;
    this.fc_1 = position;
  }
  protoOf(EnglishPattern).ei = function () {
    return this.cc_1;
  };
  protoOf(EnglishPattern).fi = function () {
    return this.dc_1;
  };
  protoOf(EnglishPattern).di = function () {
    return this.bc_1;
  };
  var PatternPosition_START_instance;
  var PatternPosition_END_instance;
  var PatternPosition_ANY_instance;
  var PatternPosition_entriesInitialized;
  function PatternPosition_initEntries() {
    if (PatternPosition_entriesInitialized)
      return Unit_instance;
    PatternPosition_entriesInitialized = true;
    PatternPosition_START_instance = new PatternPosition('START', 0);
    PatternPosition_END_instance = new PatternPosition('END', 1);
    PatternPosition_ANY_instance = new PatternPosition('ANY', 2);
  }
  function PatternPosition(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function GreekKey_ALPHA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_ALPHA_instance;
  }
  function GreekKey_BETA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_BETA_instance;
  }
  function GreekKey_GAMMA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_GAMMA_instance;
  }
  function GreekKey_DELTA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_DELTA_instance;
  }
  function GreekKey_EPSILON_getInstance() {
    GreekKey_initEntries();
    return GreekKey_EPSILON_instance;
  }
  function GreekKey_ZETA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_ZETA_instance;
  }
  function GreekKey_ETA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_ETA_instance;
  }
  function GreekKey_THETA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_THETA_instance;
  }
  function GreekKey_IOTA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_IOTA_instance;
  }
  function GreekKey_KAPPA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_KAPPA_instance;
  }
  function GreekKey_LAMBDA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_LAMBDA_instance;
  }
  function GreekKey_MU_getInstance() {
    GreekKey_initEntries();
    return GreekKey_MU_instance;
  }
  function GreekKey_NU_getInstance() {
    GreekKey_initEntries();
    return GreekKey_NU_instance;
  }
  function GreekKey_XI_getInstance() {
    GreekKey_initEntries();
    return GreekKey_XI_instance;
  }
  function GreekKey_OMICRON_getInstance() {
    GreekKey_initEntries();
    return GreekKey_OMICRON_instance;
  }
  function GreekKey_PI_getInstance() {
    GreekKey_initEntries();
    return GreekKey_PI_instance;
  }
  function GreekKey_RHO_getInstance() {
    GreekKey_initEntries();
    return GreekKey_RHO_instance;
  }
  function GreekKey_SIGMA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_SIGMA_instance;
  }
  function GreekKey_SIGMA_FINAL_getInstance() {
    GreekKey_initEntries();
    return GreekKey_SIGMA_FINAL_instance;
  }
  function GreekKey_TAU_getInstance() {
    GreekKey_initEntries();
    return GreekKey_TAU_instance;
  }
  function GreekKey_UPSILON_getInstance() {
    GreekKey_initEntries();
    return GreekKey_UPSILON_instance;
  }
  function GreekKey_PHI_getInstance() {
    GreekKey_initEntries();
    return GreekKey_PHI_instance;
  }
  function GreekKey_CHI_getInstance() {
    GreekKey_initEntries();
    return GreekKey_CHI_instance;
  }
  function GreekKey_PSI_getInstance() {
    GreekKey_initEntries();
    return GreekKey_PSI_instance;
  }
  function GreekKey_OMEGA_getInstance() {
    GreekKey_initEntries();
    return GreekKey_OMEGA_instance;
  }
  function CyrillicKey_A_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_A_instance;
  }
  function CyrillicKey_BE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_BE_instance;
  }
  function CyrillicKey_VE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_VE_instance;
  }
  function CyrillicKey_GE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_GE_instance;
  }
  function CyrillicKey_DE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_DE_instance;
  }
  function CyrillicKey_YE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_YE_instance;
  }
  function CyrillicKey_YO_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_YO_instance;
  }
  function CyrillicKey_ZHE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_ZHE_instance;
  }
  function CyrillicKey_ZE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_ZE_instance;
  }
  function CyrillicKey_I_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_I_instance;
  }
  function CyrillicKey_SHORT_I_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_SHORT_I_instance;
  }
  function CyrillicKey_KA_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_KA_instance;
  }
  function CyrillicKey_EL_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_EL_instance;
  }
  function CyrillicKey_EM_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_EM_instance;
  }
  function CyrillicKey_EN_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_EN_instance;
  }
  function CyrillicKey_O_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_O_instance;
  }
  function CyrillicKey_PE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_PE_instance;
  }
  function CyrillicKey_ER_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_ER_instance;
  }
  function CyrillicKey_ES_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_ES_instance;
  }
  function CyrillicKey_TE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_TE_instance;
  }
  function CyrillicKey_U_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_U_instance;
  }
  function CyrillicKey_EF_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_EF_instance;
  }
  function CyrillicKey_HA_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_HA_instance;
  }
  function CyrillicKey_TSE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_TSE_instance;
  }
  function CyrillicKey_CHE_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_CHE_instance;
  }
  function CyrillicKey_SHA_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_SHA_instance;
  }
  function CyrillicKey_SHCHA_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_SHCHA_instance;
  }
  function CyrillicKey_HARD_SIGN_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_HARD_SIGN_instance;
  }
  function CyrillicKey_YERU_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_YERU_instance;
  }
  function CyrillicKey_SOFT_SIGN_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_SOFT_SIGN_instance;
  }
  function CyrillicKey_E_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_E_instance;
  }
  function CyrillicKey_YU_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_YU_instance;
  }
  function CyrillicKey_YA_getInstance() {
    CyrillicKey_initEntries();
    return CyrillicKey_YA_instance;
  }
  function HangulInitial_GIYEOK_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_GIYEOK_instance;
  }
  function HangulInitial_SSANG_GIYEOK_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_SSANG_GIYEOK_instance;
  }
  function HangulInitial_NIEUN_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_NIEUN_instance;
  }
  function HangulInitial_DIGEUT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_DIGEUT_instance;
  }
  function HangulInitial_SSANG_DIGEUT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_SSANG_DIGEUT_instance;
  }
  function HangulInitial_RIEUL_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_RIEUL_instance;
  }
  function HangulInitial_MIEUM_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_MIEUM_instance;
  }
  function HangulInitial_BIEUP_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_BIEUP_instance;
  }
  function HangulInitial_SSANG_BIEUP_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_SSANG_BIEUP_instance;
  }
  function HangulInitial_SIOT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_SIOT_instance;
  }
  function HangulInitial_SSANG_SIOT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_SSANG_SIOT_instance;
  }
  function HangulInitial_IEUNG_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_IEUNG_instance;
  }
  function HangulInitial_JIEUT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_JIEUT_instance;
  }
  function HangulInitial_SSANG_JIEUT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_SSANG_JIEUT_instance;
  }
  function HangulInitial_CHIEUT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_CHIEUT_instance;
  }
  function HangulInitial_KIEUK_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_KIEUK_instance;
  }
  function HangulInitial_TIEUT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_TIEUT_instance;
  }
  function HangulInitial_PIEUP_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_PIEUP_instance;
  }
  function HangulInitial_HIEUT_getInstance() {
    HangulInitial_initEntries();
    return HangulInitial_HIEUT_instance;
  }
  function HangulMedial_A_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_A_instance;
  }
  function HangulMedial_AE_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_AE_instance;
  }
  function HangulMedial_YA_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_YA_instance;
  }
  function HangulMedial_YAE_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_YAE_instance;
  }
  function HangulMedial_EO_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_EO_instance;
  }
  function HangulMedial_E_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_E_instance;
  }
  function HangulMedial_YEO_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_YEO_instance;
  }
  function HangulMedial_YE_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_YE_instance;
  }
  function HangulMedial_O_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_O_instance;
  }
  function HangulMedial_WA_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_WA_instance;
  }
  function HangulMedial_WAE_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_WAE_instance;
  }
  function HangulMedial_OE_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_OE_instance;
  }
  function HangulMedial_YO_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_YO_instance;
  }
  function HangulMedial_U_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_U_instance;
  }
  function HangulMedial_WEO_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_WEO_instance;
  }
  function HangulMedial_WE_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_WE_instance;
  }
  function HangulMedial_WI_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_WI_instance;
  }
  function HangulMedial_YU_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_YU_instance;
  }
  function HangulMedial_EU_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_EU_instance;
  }
  function HangulMedial_UI_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_UI_instance;
  }
  function HangulMedial_I_getInstance() {
    HangulMedial_initEntries();
    return HangulMedial_I_instance;
  }
  function HangulFinal_NONE_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_NONE_instance;
  }
  function HangulFinal_GIYEOK_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_GIYEOK_instance;
  }
  function HangulFinal_SSANG_GIYEOK_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_SSANG_GIYEOK_instance;
  }
  function HangulFinal_GIYEOK_SIOT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_GIYEOK_SIOT_instance;
  }
  function HangulFinal_NIEUN_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_NIEUN_instance;
  }
  function HangulFinal_NIEUN_JIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_NIEUN_JIEUT_instance;
  }
  function HangulFinal_NIEUN_HIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_NIEUN_HIEUT_instance;
  }
  function HangulFinal_DIGEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_DIGEUT_instance;
  }
  function HangulFinal_RIEUL_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_instance;
  }
  function HangulFinal_RIEUL_GIYEOK_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_GIYEOK_instance;
  }
  function HangulFinal_RIEUL_MIEUM_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_MIEUM_instance;
  }
  function HangulFinal_RIEUL_BIEUP_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_BIEUP_instance;
  }
  function HangulFinal_RIEUL_SIOT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_SIOT_instance;
  }
  function HangulFinal_RIEUL_TIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_TIEUT_instance;
  }
  function HangulFinal_RIEUL_PIEUP_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_PIEUP_instance;
  }
  function HangulFinal_RIEUL_HIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_RIEUL_HIEUT_instance;
  }
  function HangulFinal_MIEUM_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_MIEUM_instance;
  }
  function HangulFinal_BIEUP_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_BIEUP_instance;
  }
  function HangulFinal_BIEUP_SIOT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_BIEUP_SIOT_instance;
  }
  function HangulFinal_SIOT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_SIOT_instance;
  }
  function HangulFinal_SSANG_SIOT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_SSANG_SIOT_instance;
  }
  function HangulFinal_IEUNG_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_IEUNG_instance;
  }
  function HangulFinal_JIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_JIEUT_instance;
  }
  function HangulFinal_CHIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_CHIEUT_instance;
  }
  function HangulFinal_KIEUK_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_KIEUK_instance;
  }
  function HangulFinal_TIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_TIEUT_instance;
  }
  function HangulFinal_PIEUP_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_PIEUP_instance;
  }
  function HangulFinal_HIEUT_getInstance() {
    HangulFinal_initEntries();
    return HangulFinal_HIEUT_instance;
  }
  function HiraganaKey_A_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_A_instance;
  }
  function HiraganaKey_I_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_I_instance;
  }
  function HiraganaKey_U_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_U_instance;
  }
  function HiraganaKey_E_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_E_instance;
  }
  function HiraganaKey_O_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_O_instance;
  }
  function HiraganaKey_KA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_KA_instance;
  }
  function HiraganaKey_KI_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_KI_instance;
  }
  function HiraganaKey_KU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_KU_instance;
  }
  function HiraganaKey_KE_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_KE_instance;
  }
  function HiraganaKey_KO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_KO_instance;
  }
  function HiraganaKey_SA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_SA_instance;
  }
  function HiraganaKey_SHI_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_SHI_instance;
  }
  function HiraganaKey_SU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_SU_instance;
  }
  function HiraganaKey_SE_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_SE_instance;
  }
  function HiraganaKey_SO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_SO_instance;
  }
  function HiraganaKey_TA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_TA_instance;
  }
  function HiraganaKey_CHI_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_CHI_instance;
  }
  function HiraganaKey_TSU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_TSU_instance;
  }
  function HiraganaKey_TE_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_TE_instance;
  }
  function HiraganaKey_TO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_TO_instance;
  }
  function HiraganaKey_NA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_NA_instance;
  }
  function HiraganaKey_NI_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_NI_instance;
  }
  function HiraganaKey_NU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_NU_instance;
  }
  function HiraganaKey_NE_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_NE_instance;
  }
  function HiraganaKey_NO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_NO_instance;
  }
  function HiraganaKey_HA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_HA_instance;
  }
  function HiraganaKey_HI_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_HI_instance;
  }
  function HiraganaKey_FU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_FU_instance;
  }
  function HiraganaKey_HE_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_HE_instance;
  }
  function HiraganaKey_HO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_HO_instance;
  }
  function HiraganaKey_MA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_MA_instance;
  }
  function HiraganaKey_MI_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_MI_instance;
  }
  function HiraganaKey_MU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_MU_instance;
  }
  function HiraganaKey_ME_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_ME_instance;
  }
  function HiraganaKey_MO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_MO_instance;
  }
  function HiraganaKey_YA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_YA_instance;
  }
  function HiraganaKey_YU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_YU_instance;
  }
  function HiraganaKey_YO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_YO_instance;
  }
  function HiraganaKey_RA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_RA_instance;
  }
  function HiraganaKey_RI_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_RI_instance;
  }
  function HiraganaKey_RU_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_RU_instance;
  }
  function HiraganaKey_RE_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_RE_instance;
  }
  function HiraganaKey_RO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_RO_instance;
  }
  function HiraganaKey_WA_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_WA_instance;
  }
  function HiraganaKey_WO_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_WO_instance;
  }
  function HiraganaKey_N_getInstance() {
    HiraganaKey_initEntries();
    return HiraganaKey_N_instance;
  }
  function DevanagariKey_A_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_A_instance;
  }
  function DevanagariKey_AA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_AA_instance;
  }
  function DevanagariKey_I_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_I_instance;
  }
  function DevanagariKey_II_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_II_instance;
  }
  function DevanagariKey_U_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_U_instance;
  }
  function DevanagariKey_UU_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_UU_instance;
  }
  function DevanagariKey_E_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_E_instance;
  }
  function DevanagariKey_AI_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_AI_instance;
  }
  function DevanagariKey_O_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_O_instance;
  }
  function DevanagariKey_AU_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_AU_instance;
  }
  function DevanagariKey_KA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_KA_instance;
  }
  function DevanagariKey_KHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_KHA_instance;
  }
  function DevanagariKey_GA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_GA_instance;
  }
  function DevanagariKey_GHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_GHA_instance;
  }
  function DevanagariKey_NGA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_NGA_instance;
  }
  function DevanagariKey_CHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_CHA_instance;
  }
  function DevanagariKey_CHHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_CHHA_instance;
  }
  function DevanagariKey_JA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_JA_instance;
  }
  function DevanagariKey_JHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_JHA_instance;
  }
  function DevanagariKey_NYA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_NYA_instance;
  }
  function DevanagariKey_TA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_TA_instance;
  }
  function DevanagariKey_THA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_THA_instance;
  }
  function DevanagariKey_DA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_DA_instance;
  }
  function DevanagariKey_DHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_DHA_instance;
  }
  function DevanagariKey_NA_R_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_NA_R_instance;
  }
  function DevanagariKey_TA_D_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_TA_D_instance;
  }
  function DevanagariKey_THA_D_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_THA_D_instance;
  }
  function DevanagariKey_DA_D_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_DA_D_instance;
  }
  function DevanagariKey_DHA_D_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_DHA_D_instance;
  }
  function DevanagariKey_NA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_NA_instance;
  }
  function DevanagariKey_PA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_PA_instance;
  }
  function DevanagariKey_PHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_PHA_instance;
  }
  function DevanagariKey_BA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_BA_instance;
  }
  function DevanagariKey_BHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_BHA_instance;
  }
  function DevanagariKey_MA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_MA_instance;
  }
  function DevanagariKey_YA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_YA_instance;
  }
  function DevanagariKey_RA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_RA_instance;
  }
  function DevanagariKey_LA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_LA_instance;
  }
  function DevanagariKey_VA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_VA_instance;
  }
  function DevanagariKey_SHA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_SHA_instance;
  }
  function DevanagariKey_SHA_R_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_SHA_R_instance;
  }
  function DevanagariKey_SA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_SA_instance;
  }
  function DevanagariKey_HA_getInstance() {
    DevanagariKey_initEntries();
    return DevanagariKey_HA_instance;
  }
  function LatinKey_A_getInstance() {
    LatinKey_initEntries();
    return LatinKey_A_instance;
  }
  function LatinKey_B_getInstance() {
    LatinKey_initEntries();
    return LatinKey_B_instance;
  }
  function LatinKey_C_getInstance() {
    LatinKey_initEntries();
    return LatinKey_C_instance;
  }
  function LatinKey_D_getInstance() {
    LatinKey_initEntries();
    return LatinKey_D_instance;
  }
  function LatinKey_E_getInstance() {
    LatinKey_initEntries();
    return LatinKey_E_instance;
  }
  function LatinKey_F_getInstance() {
    LatinKey_initEntries();
    return LatinKey_F_instance;
  }
  function LatinKey_G_getInstance() {
    LatinKey_initEntries();
    return LatinKey_G_instance;
  }
  function LatinKey_H_getInstance() {
    LatinKey_initEntries();
    return LatinKey_H_instance;
  }
  function LatinKey_I_getInstance() {
    LatinKey_initEntries();
    return LatinKey_I_instance;
  }
  function LatinKey_J_getInstance() {
    LatinKey_initEntries();
    return LatinKey_J_instance;
  }
  function LatinKey_K_getInstance() {
    LatinKey_initEntries();
    return LatinKey_K_instance;
  }
  function LatinKey_L_getInstance() {
    LatinKey_initEntries();
    return LatinKey_L_instance;
  }
  function LatinKey_M_getInstance() {
    LatinKey_initEntries();
    return LatinKey_M_instance;
  }
  function LatinKey_N_getInstance() {
    LatinKey_initEntries();
    return LatinKey_N_instance;
  }
  function LatinKey_O_getInstance() {
    LatinKey_initEntries();
    return LatinKey_O_instance;
  }
  function LatinKey_P_getInstance() {
    LatinKey_initEntries();
    return LatinKey_P_instance;
  }
  function LatinKey_Q_getInstance() {
    LatinKey_initEntries();
    return LatinKey_Q_instance;
  }
  function LatinKey_R_getInstance() {
    LatinKey_initEntries();
    return LatinKey_R_instance;
  }
  function LatinKey_S_getInstance() {
    LatinKey_initEntries();
    return LatinKey_S_instance;
  }
  function LatinKey_T_getInstance() {
    LatinKey_initEntries();
    return LatinKey_T_instance;
  }
  function LatinKey_U_getInstance() {
    LatinKey_initEntries();
    return LatinKey_U_instance;
  }
  function LatinKey_V_getInstance() {
    LatinKey_initEntries();
    return LatinKey_V_instance;
  }
  function LatinKey_W_getInstance() {
    LatinKey_initEntries();
    return LatinKey_W_instance;
  }
  function LatinKey_X_getInstance() {
    LatinKey_initEntries();
    return LatinKey_X_instance;
  }
  function LatinKey_Y_getInstance() {
    LatinKey_initEntries();
    return LatinKey_Y_instance;
  }
  function LatinKey_Z_getInstance() {
    LatinKey_initEntries();
    return LatinKey_Z_instance;
  }
  function EnglishPattern_OUGHT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUGHT_instance;
  }
  function EnglishPattern_OUGH_O_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUGH_O_instance;
  }
  function EnglishPattern_OUGH_UFF_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUGH_UFF_instance;
  }
  function EnglishPattern_OUGH_OFF_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUGH_OFF_instance;
  }
  function EnglishPattern_OUGH_OO_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUGH_OO_instance;
  }
  function EnglishPattern_OUGH_OW_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUGH_OW_instance;
  }
  function EnglishPattern_AUGHT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AUGHT_instance;
  }
  function EnglishPattern_AUGH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AUGH_instance;
  }
  function EnglishPattern_LAUGH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_LAUGH_instance;
  }
  function EnglishPattern_EIGHT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EIGHT_instance;
  }
  function EnglishPattern_EIGH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EIGH_instance;
  }
  function EnglishPattern_HEIGHT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_HEIGHT_instance;
  }
  function EnglishPattern_IGHT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_IGHT_instance;
  }
  function EnglishPattern_IGH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_IGH_instance;
  }
  function EnglishPattern_TION_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_TION_instance;
  }
  function EnglishPattern_SION_ZH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_SION_ZH_instance;
  }
  function EnglishPattern_SION_SH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_SION_SH_instance;
  }
  function EnglishPattern_ING_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ING_instance;
  }
  function EnglishPattern_TING_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_TING_instance;
  }
  function EnglishPattern_RING_VERB_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_RING_VERB_instance;
  }
  function EnglishPattern_OUND_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUND_instance;
  }
  function EnglishPattern_OUNT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUNT_instance;
  }
  function EnglishPattern_OUT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUT_instance;
  }
  function EnglishPattern_OUR_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUR_instance;
  }
  function EnglishPattern_OUR_ER_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUR_ER_instance;
  }
  function EnglishPattern_OUSE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUSE_instance;
  }
  function EnglishPattern_OUSE_Z_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUSE_Z_instance;
  }
  function EnglishPattern_OULD_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OULD_instance;
  }
  function EnglishPattern_OUP_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OUP_instance;
  }
  function EnglishPattern_OWN_OWN_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OWN_OWN_instance;
  }
  function EnglishPattern_OWN_OWN2_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OWN_OWN2_instance;
  }
  function EnglishPattern_OW_LONG_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OW_LONG_instance;
  }
  function EnglishPattern_OW_OW_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OW_OW_instance;
  }
  function EnglishPattern_EAR_EER_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAR_EER_instance;
  }
  function EnglishPattern_EAR_AIR_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAR_AIR_instance;
  }
  function EnglishPattern_EAR_ER_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAR_ER_instance;
  }
  function EnglishPattern_EAT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAT_instance;
  }
  function EnglishPattern_EAD_EED_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAD_EED_instance;
  }
  function EnglishPattern_EAD_ED_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAD_ED_instance;
  }
  function EnglishPattern_EAK_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAK_instance;
  }
  function EnglishPattern_EAL_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAL_instance;
  }
  function EnglishPattern_EAM_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAM_instance;
  }
  function EnglishPattern_EAN_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAN_instance;
  }
  function EnglishPattern_EAP_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAP_instance;
  }
  function EnglishPattern_EAS_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAS_instance;
  }
  function EnglishPattern_EAST_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EAST_instance;
  }
  function EnglishPattern_EATH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EATH_instance;
  }
  function EnglishPattern_EATH_SHORT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EATH_SHORT_instance;
  }
  function EnglishPattern_OOK_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOK_instance;
  }
  function EnglishPattern_OOD_GOOD_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOD_GOOD_instance;
  }
  function EnglishPattern_OOD_FOOD_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOD_FOOD_instance;
  }
  function EnglishPattern_OOL_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOL_instance;
  }
  function EnglishPattern_OOM_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOM_instance;
  }
  function EnglishPattern_OON_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OON_instance;
  }
  function EnglishPattern_OOP_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOP_instance;
  }
  function EnglishPattern_OOR_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOR_instance;
  }
  function EnglishPattern_OOT_FOOT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOT_FOOT_instance;
  }
  function EnglishPattern_OOT_BOOT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOT_BOOT_instance;
  }
  function EnglishPattern_OOTH_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OOTH_instance;
  }
  function EnglishPattern_AY_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AY_instance;
  }
  function EnglishPattern_EY_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EY_instance;
  }
  function EnglishPattern_EY_I_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_EY_I_instance;
  }
  function EnglishPattern_OY_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OY_instance;
  }
  function EnglishPattern_OI_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OI_instance;
  }
  function EnglishPattern_OISE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OISE_instance;
  }
  function EnglishPattern_OICE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_OICE_instance;
  }
  function EnglishPattern_AWE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AWE_instance;
  }
  function EnglishPattern_AW_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AW_instance;
  }
  function EnglishPattern_AWN_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AWN_instance;
  }
  function EnglishPattern_AWL_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AWL_instance;
  }
  function EnglishPattern_AIR_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_AIR_instance;
  }
  function EnglishPattern_ARE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ARE_instance;
  }
  function EnglishPattern_ER_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ER_instance;
  }
  function EnglishPattern_IR_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_IR_instance;
  }
  function EnglishPattern_UR_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_UR_instance;
  }
  function EnglishPattern_ALL_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ALL_instance;
  }
  function EnglishPattern_ALK_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ALK_instance;
  }
  function EnglishPattern_ALT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ALT_instance;
  }
  function EnglishPattern_KN_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_KN_instance;
  }
  function EnglishPattern_WR_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_WR_instance;
  }
  function EnglishPattern_GN_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_GN_instance;
  }
  function EnglishPattern_MB_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_MB_instance;
  }
  function EnglishPattern_GH_SILENT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_GH_SILENT_instance;
  }
  function EnglishPattern_ABLE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ABLE_instance;
  }
  function EnglishPattern_IBLE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_IBLE_instance;
  }
  function EnglishPattern_NESS_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_NESS_instance;
  }
  function EnglishPattern_MENT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_MENT_instance;
  }
  function EnglishPattern_ENCE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ENCE_instance;
  }
  function EnglishPattern_ANCE_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ANCE_instance;
  }
  function EnglishPattern_ENT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ENT_instance;
  }
  function EnglishPattern_ANT_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ANT_instance;
  }
  function EnglishPattern_LY_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_LY_instance;
  }
  function EnglishPattern_FUL_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_FUL_instance;
  }
  function EnglishPattern_LESS_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_LESS_instance;
  }
  function EnglishPattern_ED_T_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ED_T_instance;
  }
  function EnglishPattern_ED_D_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ED_D_instance;
  }
  function EnglishPattern_ED_ID_getInstance() {
    EnglishPattern_initEntries();
    return EnglishPattern_ED_ID_instance;
  }
  function PatternPosition_START_getInstance() {
    PatternPosition_initEntries();
    return PatternPosition_START_instance;
  }
  function PatternPosition_END_getInstance() {
    PatternPosition_initEntries();
    return PatternPosition_END_instance;
  }
  function PatternPosition_ANY_getInstance() {
    PatternPosition_initEntries();
    return PatternPosition_ANY_instance;
  }
  function sam$kotlin_Comparator$0_0(function_0) {
    this.rt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_0).x8 = function (a, b) {
    return this.rt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_0).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_0).e2 = function () {
    return this.rt_1;
  };
  protoOf(sam$kotlin_Comparator$0_0).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_0).hashCode = function () {
    return hashCode(this.e2());
  };
  function GermanPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.kc_1.length;
    var tmp$ret$1 = a.kc_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var GermanPattern_SCH_instance;
  var GermanPattern_TSCH_instance;
  var GermanPattern_CH_ACH_instance;
  var GermanPattern_CH_ICH_instance;
  var GermanPattern_CHS_instance;
  var GermanPattern_CK_instance;
  var GermanPattern_PF_instance;
  var GermanPattern_SP_INITIAL_instance;
  var GermanPattern_ST_INITIAL_instance;
  var GermanPattern_Z_instance;
  var GermanPattern_V_instance;
  var GermanPattern_W_instance;
  var GermanPattern_S_VOICED_instance;
  var GermanPattern_SS_instance;
  var GermanPattern_AE_instance;
  var GermanPattern_OE_instance;
  var GermanPattern_UE_instance;
  var GermanPattern_IE_instance;
  var GermanPattern_EI_instance;
  var GermanPattern_EU_instance;
  var GermanPattern_AEU_instance;
  var GermanPattern_AU_instance;
  var GermanPattern_AH_instance;
  var GermanPattern_EH_instance;
  var GermanPattern_IH_instance;
  var GermanPattern_OH_instance;
  var GermanPattern_UH_instance;
  var GermanPattern_TION_instance;
  var GermanPattern_IG_instance;
  var GermanPattern_LICH_instance;
  var GermanPattern_KEIT_instance;
  var GermanPattern_HEIT_instance;
  var GermanPattern_SCHAFT_instance;
  var GermanPattern_UNG_instance;
  function Companion_18() {
    Companion_instance_18 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_17();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.kc_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.gc_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_17();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.lc_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.hc_1 = destination_0;
  }
  protoOf(Companion_18).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_17();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.oc_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = GermanPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_0(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.kc_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_18;
  function Companion_getInstance_18() {
    GermanPattern_initEntries();
    if (Companion_instance_18 == null)
      new Companion_18();
    return Companion_instance_18;
  }
  function values_17() {
    return [GermanPattern_SCH_getInstance(), GermanPattern_TSCH_getInstance(), GermanPattern_CH_ACH_getInstance(), GermanPattern_CH_ICH_getInstance(), GermanPattern_CHS_getInstance(), GermanPattern_CK_getInstance(), GermanPattern_PF_getInstance(), GermanPattern_SP_INITIAL_getInstance(), GermanPattern_ST_INITIAL_getInstance(), GermanPattern_Z_getInstance(), GermanPattern_V_getInstance(), GermanPattern_W_getInstance(), GermanPattern_S_VOICED_getInstance(), GermanPattern_SS_getInstance(), GermanPattern_AE_getInstance(), GermanPattern_OE_getInstance(), GermanPattern_UE_getInstance(), GermanPattern_IE_getInstance(), GermanPattern_EI_getInstance(), GermanPattern_EU_getInstance(), GermanPattern_AEU_getInstance(), GermanPattern_AU_getInstance(), GermanPattern_AH_getInstance(), GermanPattern_EH_getInstance(), GermanPattern_IH_getInstance(), GermanPattern_OH_getInstance(), GermanPattern_UH_getInstance(), GermanPattern_TION_getInstance(), GermanPattern_IG_getInstance(), GermanPattern_LICH_getInstance(), GermanPattern_KEIT_getInstance(), GermanPattern_HEIT_getInstance(), GermanPattern_SCHAFT_getInstance(), GermanPattern_UNG_getInstance()];
  }
  function get_entries_17() {
    if ($ENTRIES_17 == null)
      $ENTRIES_17 = enumEntries(values_17());
    return $ENTRIES_17;
  }
  var GermanPattern_entriesInitialized;
  function GermanPattern_initEntries() {
    if (GermanPattern_entriesInitialized)
      return Unit_instance;
    GermanPattern_entriesInitialized = true;
    GermanPattern_SCH_instance = new GermanPattern('SCH', 0, 'sch', '\u0283', 'sch', listOf(['Schule', 'sch\xF6n', 'Fisch']));
    GermanPattern_TSCH_instance = new GermanPattern('TSCH', 1, 'tsch', 't\u0283', 'tsch', listOf(['Deutsch', 'Tschechien', 'Kutsche']));
    GermanPattern_CH_ACH_instance = new GermanPattern('CH_ACH', 2, 'ch', 'x', 'ch-ach', listOf(['Bach', 'Dach', 'machen']));
    GermanPattern_CH_ICH_instance = new GermanPattern('CH_ICH', 3, 'ch', '\xE7', 'ch-ich', listOf(['ich', 'nicht', 'M\xE4dchen']));
    GermanPattern_CHS_instance = new GermanPattern('CHS', 4, 'chs', 'ks', 'chs', listOf(['sechs', 'wachsen', 'Lachs']));
    GermanPattern_CK_instance = new GermanPattern('CK', 5, 'ck', 'k', 'ck', listOf(['Br\xFCcke', 'St\xFCck', 'Gl\xFCck']));
    GermanPattern_PF_instance = new GermanPattern('PF', 6, 'pf', 'pf', 'pf', listOf(['Pferd', 'Apfel', 'Kopf']));
    GermanPattern_SP_INITIAL_instance = new GermanPattern('SP_INITIAL', 7, 'sp', '\u0283p', 'sp-initial', listOf(['Sprache', 'spielen', 'Sport']), PatternPosition_START_getInstance());
    GermanPattern_ST_INITIAL_instance = new GermanPattern('ST_INITIAL', 8, 'st', '\u0283t', 'st-initial', listOf(['Stra\xDFe', 'stehen', 'Stuhl']), PatternPosition_START_getInstance());
    GermanPattern_Z_instance = new GermanPattern('Z', 9, 'z', 'ts', 'z', listOf(['Zeit', 'zehn', 'Herz']));
    GermanPattern_V_instance = new GermanPattern('V', 10, 'v', 'f', 'v-f', listOf(['Vater', 'viel', 'von']));
    GermanPattern_W_instance = new GermanPattern('W', 11, 'w', 'v', 'w', listOf(['Wasser', 'Welt', 'wir']));
    GermanPattern_S_VOICED_instance = new GermanPattern('S_VOICED', 12, 's', 'z', 's-voiced', listOf(['Sonne', 'sehr', 'lesen']), PatternPosition_START_getInstance());
    GermanPattern_SS_instance = new GermanPattern('SS', 13, '\xDF', 's', 'eszett', listOf(['Stra\xDFe', 'gro\xDF', 'hei\xDF']));
    GermanPattern_AE_instance = new GermanPattern('AE', 14, '\xE4', '\u025B', 'a-umlaut', listOf(['M\xE4dchen', 'K\xE4se', 'B\xE4r']));
    GermanPattern_OE_instance = new GermanPattern('OE', 15, '\xF6', '\u0153', 'o-umlaut', listOf(['sch\xF6n', 'K\xF6ln', 'b\xF6se']));
    GermanPattern_UE_instance = new GermanPattern('UE', 16, '\xFC', 'y', 'u-umlaut', listOf(['gr\xFCn', 'f\xFCr', 'T\xFCr']));
    GermanPattern_IE_instance = new GermanPattern('IE', 17, 'ie', 'i\u02D0', 'ie-long', listOf(['Liebe', 'Bier', 'hier']));
    GermanPattern_EI_instance = new GermanPattern('EI', 18, 'ei', 'a\u026A', 'ei', listOf(['mein', 'Stein', 'zwei']));
    GermanPattern_EU_instance = new GermanPattern('EU', 19, 'eu', '\u0254\u028F', 'eu', listOf(['Freund', 'heute', 'neu']));
    GermanPattern_AEU_instance = new GermanPattern('AEU', 20, '\xE4u', '\u0254\u028F', 'aeu', listOf(['H\xE4user', 'B\xE4ume', 'tr\xE4umen']));
    GermanPattern_AU_instance = new GermanPattern('AU', 21, 'au', 'a\u028A', 'au', listOf(['Haus', 'Baum', 'Frau']));
    GermanPattern_AH_instance = new GermanPattern('AH', 22, 'ah', 'a\u02D0', 'ah-long', listOf(['fahren', 'Jahr', 'Bahn']));
    GermanPattern_EH_instance = new GermanPattern('EH', 23, 'eh', 'e\u02D0', 'eh-long', listOf(['sehr', 'mehr', 'nehmen']));
    GermanPattern_IH_instance = new GermanPattern('IH', 24, 'ih', 'i\u02D0', 'ih-long', listOf(['ihm', 'ihr', 'Vieh']));
    GermanPattern_OH_instance = new GermanPattern('OH', 25, 'oh', 'o\u02D0', 'oh-long', listOf(['Sohn', 'Ohr', 'wohnen']));
    GermanPattern_UH_instance = new GermanPattern('UH', 26, 'uh', 'u\u02D0', 'uh-long', listOf(['Uhr', 'Kuh', 'Schuh']));
    GermanPattern_TION_instance = new GermanPattern('TION', 27, 'tion', 'tsio\u02D0n', 'tion', listOf(['Nation', 'Station', 'Information']), PatternPosition_END_getInstance());
    GermanPattern_IG_instance = new GermanPattern('IG', 28, 'ig', '\u026A\xE7', 'ig', listOf(['fertig', 'wenig', 'ruhig']), PatternPosition_END_getInstance());
    GermanPattern_LICH_instance = new GermanPattern('LICH', 29, 'lich', 'l\u026A\xE7', 'lich', listOf(['herzlich', 'wirklich', 'm\xF6glich']), PatternPosition_END_getInstance());
    GermanPattern_KEIT_instance = new GermanPattern('KEIT', 30, 'keit', 'ka\u026At', 'keit', listOf(['M\xF6glichkeit', 'Freiheit']), PatternPosition_END_getInstance());
    GermanPattern_HEIT_instance = new GermanPattern('HEIT', 31, 'heit', 'ha\u026At', 'heit', listOf(['Gesundheit', 'Sicherheit']), PatternPosition_END_getInstance());
    GermanPattern_SCHAFT_instance = new GermanPattern('SCHAFT', 32, 'schaft', '\u0283aft', 'schaft', listOf(['Freundschaft', 'Gesellschaft']), PatternPosition_END_getInstance());
    GermanPattern_UNG_instance = new GermanPattern('UNG', 33, 'ung', '\u028A\u014B', 'ung', listOf(['Zeitung', 'Wohnung', '\xDCbung']), PatternPosition_END_getInstance());
    Companion_getInstance_18();
  }
  var $ENTRIES_17;
  function GermanPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.kc_1 = pattern;
    this.lc_1 = ipa;
    this.mc_1 = displayName;
    this.nc_1 = examples;
    this.oc_1 = position;
  }
  protoOf(GermanPattern).ei = function () {
    return this.lc_1;
  };
  protoOf(GermanPattern).fi = function () {
    return this.mc_1;
  };
  protoOf(GermanPattern).di = function () {
    return this.kc_1;
  };
  function sam$kotlin_Comparator$0_1(function_0) {
    this.st_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_1).x8 = function (a, b) {
    return this.st_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_1).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_1).e2 = function () {
    return this.st_1;
  };
  protoOf(sam$kotlin_Comparator$0_1).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_1).hashCode = function () {
    return hashCode(this.e2());
  };
  function FrenchPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.tc_1.length;
    var tmp$ret$1 = a.tc_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var FrenchPattern_EAU_instance;
  var FrenchPattern_AU_instance;
  var FrenchPattern_OI_instance;
  var FrenchPattern_OU_instance;
  var FrenchPattern_EU_CLOSED_instance;
  var FrenchPattern_EU_OPEN_instance;
  var FrenchPattern_OEU_instance;
  var FrenchPattern_AN_instance;
  var FrenchPattern_EN_instance;
  var FrenchPattern_AIN_instance;
  var FrenchPattern_EIN_instance;
  var FrenchPattern_IN_instance;
  var FrenchPattern_ON_instance;
  var FrenchPattern_UN_instance;
  var FrenchPattern_CH_instance;
  var FrenchPattern_GN_instance;
  var FrenchPattern_QU_instance;
  var FrenchPattern_PH_instance;
  var FrenchPattern_C_SOFT_instance;
  var FrenchPattern_G_SOFT_instance;
  var FrenchPattern_ILL_instance;
  var FrenchPattern_AIL_instance;
  var FrenchPattern_EIL_instance;
  var FrenchPattern_H_SILENT_instance;
  var FrenchPattern_ENT_SILENT_instance;
  var FrenchPattern_TION_instance;
  var FrenchPattern_SION_instance;
  var FrenchPattern_EUR_instance;
  var FrenchPattern_MENT_instance;
  var FrenchPattern_IQUE_instance;
  var FrenchPattern_OIRE_instance;
  function Companion_19() {
    Companion_instance_19 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_18();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.tc_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.pc_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_18();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.uc_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.qc_1 = destination_0;
  }
  protoOf(Companion_19).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_18();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.xc_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = FrenchPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_1(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.tc_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_19;
  function Companion_getInstance_19() {
    FrenchPattern_initEntries();
    if (Companion_instance_19 == null)
      new Companion_19();
    return Companion_instance_19;
  }
  function values_18() {
    return [FrenchPattern_EAU_getInstance(), FrenchPattern_AU_getInstance(), FrenchPattern_OI_getInstance(), FrenchPattern_OU_getInstance(), FrenchPattern_EU_CLOSED_getInstance(), FrenchPattern_EU_OPEN_getInstance(), FrenchPattern_OEU_getInstance(), FrenchPattern_AN_getInstance(), FrenchPattern_EN_getInstance(), FrenchPattern_AIN_getInstance(), FrenchPattern_EIN_getInstance(), FrenchPattern_IN_getInstance(), FrenchPattern_ON_getInstance(), FrenchPattern_UN_getInstance(), FrenchPattern_CH_getInstance(), FrenchPattern_GN_getInstance(), FrenchPattern_QU_getInstance(), FrenchPattern_PH_getInstance(), FrenchPattern_C_SOFT_getInstance(), FrenchPattern_G_SOFT_getInstance(), FrenchPattern_ILL_getInstance(), FrenchPattern_AIL_getInstance(), FrenchPattern_EIL_getInstance(), FrenchPattern_H_SILENT_getInstance(), FrenchPattern_ENT_SILENT_getInstance(), FrenchPattern_TION_getInstance(), FrenchPattern_SION_getInstance(), FrenchPattern_EUR_getInstance(), FrenchPattern_MENT_getInstance(), FrenchPattern_IQUE_getInstance(), FrenchPattern_OIRE_getInstance()];
  }
  function get_entries_18() {
    if ($ENTRIES_18 == null)
      $ENTRIES_18 = enumEntries(values_18());
    return $ENTRIES_18;
  }
  var FrenchPattern_entriesInitialized;
  function FrenchPattern_initEntries() {
    if (FrenchPattern_entriesInitialized)
      return Unit_instance;
    FrenchPattern_entriesInitialized = true;
    FrenchPattern_EAU_instance = new FrenchPattern('EAU', 0, 'eau', 'o', 'eau', listOf(['beau', 'eau', 'g\xE2teau']));
    FrenchPattern_AU_instance = new FrenchPattern('AU', 1, 'au', 'o', 'au', listOf(['aussi', 'autre', 'faute']));
    FrenchPattern_OI_instance = new FrenchPattern('OI', 2, 'oi', 'wa', 'oi', listOf(['moi', 'roi', 'trois']));
    FrenchPattern_OU_instance = new FrenchPattern('OU', 3, 'ou', 'u', 'ou', listOf(['pour', 'jour', 'nous']));
    FrenchPattern_EU_CLOSED_instance = new FrenchPattern('EU_CLOSED', 4, 'eu', '\xF8', 'eu-closed', listOf(['deux', 'jeu', 'peu']));
    FrenchPattern_EU_OPEN_instance = new FrenchPattern('EU_OPEN', 5, 'eu', '\u0153', 'eu-open', listOf(['peur', 'heure', 'seul']));
    FrenchPattern_OEU_instance = new FrenchPattern('OEU', 6, '\u0153u', '\u0153', 'oeu', listOf(['c\u0153ur', 's\u0153ur', '\u0153uf']));
    FrenchPattern_AN_instance = new FrenchPattern('AN', 7, 'an', '\u0251\u0303', 'an-nasal', listOf(['dans', 'enfant', 'grand']));
    FrenchPattern_EN_instance = new FrenchPattern('EN', 8, 'en', '\u0251\u0303', 'en-nasal', listOf(['vent', 'enfant', 'comment']));
    FrenchPattern_AIN_instance = new FrenchPattern('AIN', 9, 'ain', '\u025B\u0303', 'ain-nasal', listOf(['pain', 'main', 'train']), PatternPosition_END_getInstance());
    FrenchPattern_EIN_instance = new FrenchPattern('EIN', 10, 'ein', '\u025B\u0303', 'ein-nasal', listOf(['plein', 'sein']), PatternPosition_END_getInstance());
    FrenchPattern_IN_instance = new FrenchPattern('IN', 11, 'in', '\u025B\u0303', 'in-nasal', listOf(['vin', 'fin', 'matin']));
    FrenchPattern_ON_instance = new FrenchPattern('ON', 12, 'on', '\u0254\u0303', 'on-nasal', listOf(['bon', 'son', 'maison']));
    FrenchPattern_UN_instance = new FrenchPattern('UN', 13, 'un', '\u0153\u0303', 'un-nasal', listOf(['un', 'brun', 'parfum']));
    FrenchPattern_CH_instance = new FrenchPattern('CH', 14, 'ch', '\u0283', 'ch', listOf(['chat', 'chose', 'chercher']));
    FrenchPattern_GN_instance = new FrenchPattern('GN', 15, 'gn', '\u0272', 'gn', listOf(['montagne', 'campagne', 'signe']));
    FrenchPattern_QU_instance = new FrenchPattern('QU', 16, 'qu', 'k', 'qu', listOf(['que', 'qui', 'quoi']));
    FrenchPattern_PH_instance = new FrenchPattern('PH', 17, 'ph', 'f', 'ph', listOf(['photo', 'philosophie', 'pharmacie']));
    FrenchPattern_C_SOFT_instance = new FrenchPattern('C_SOFT', 18, 'c', 's', 'c-soft', listOf(['ce', 'cela', 'merci']));
    FrenchPattern_G_SOFT_instance = new FrenchPattern('G_SOFT', 19, 'g', '\u0292', 'g-soft', listOf(['\xE2ge', 'rouge', 'manger']));
    FrenchPattern_ILL_instance = new FrenchPattern('ILL', 20, 'ill', 'ij', 'ill', listOf(['fille', 'famille', 'brillant']));
    FrenchPattern_AIL_instance = new FrenchPattern('AIL', 21, 'ail', 'aj', 'ail', listOf(['travail', 'soleil']), PatternPosition_END_getInstance());
    FrenchPattern_EIL_instance = new FrenchPattern('EIL', 22, 'eil', '\u025Bj', 'eil', listOf(['soleil', 'r\xE9veil']), PatternPosition_END_getInstance());
    FrenchPattern_H_SILENT_instance = new FrenchPattern('H_SILENT', 23, 'h', '', 'h-silent', listOf(['homme', 'heure', 'histoire']), PatternPosition_START_getInstance());
    FrenchPattern_ENT_SILENT_instance = new FrenchPattern('ENT_SILENT', 24, 'ent', '', 'ent-silent', listOf(['parlent', 'mangent', 'aiment']), PatternPosition_END_getInstance());
    FrenchPattern_TION_instance = new FrenchPattern('TION', 25, 'tion', 'sj\u0254\u0303', 'tion', listOf(['nation', 'action', 'information']), PatternPosition_END_getInstance());
    FrenchPattern_SION_instance = new FrenchPattern('SION', 26, 'sion', 'zj\u0254\u0303', 'sion', listOf(['t\xE9l\xE9vision', 'd\xE9cision']), PatternPosition_END_getInstance());
    FrenchPattern_EUR_instance = new FrenchPattern('EUR', 27, 'eur', '\u0153\u0281', 'eur', listOf(['douleur', 'docteur', 'fleur']), PatternPosition_END_getInstance());
    FrenchPattern_MENT_instance = new FrenchPattern('MENT', 28, 'ment', 'm\u0251\u0303', 'ment', listOf(['vraiment', 'moment', 'comment']), PatternPosition_END_getInstance());
    FrenchPattern_IQUE_instance = new FrenchPattern('IQUE', 29, 'ique', 'ik', 'ique', listOf(['musique', 'politique', 'unique']), PatternPosition_END_getInstance());
    FrenchPattern_OIRE_instance = new FrenchPattern('OIRE', 30, 'oire', 'wa\u0281', 'oire', listOf(['histoire', 'm\xE9moire', 'victoire']), PatternPosition_END_getInstance());
    Companion_getInstance_19();
  }
  var $ENTRIES_18;
  function FrenchPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.tc_1 = pattern;
    this.uc_1 = ipa;
    this.vc_1 = displayName;
    this.wc_1 = examples;
    this.xc_1 = position;
  }
  protoOf(FrenchPattern).ei = function () {
    return this.uc_1;
  };
  protoOf(FrenchPattern).fi = function () {
    return this.vc_1;
  };
  protoOf(FrenchPattern).di = function () {
    return this.tc_1;
  };
  function sam$kotlin_Comparator$0_2(function_0) {
    this.tt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_2).x8 = function (a, b) {
    return this.tt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_2).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_2).e2 = function () {
    return this.tt_1;
  };
  protoOf(sam$kotlin_Comparator$0_2).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_2).hashCode = function () {
    return hashCode(this.e2());
  };
  function SpanishPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.cd_1.length;
    var tmp$ret$1 = a.cd_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var SpanishPattern_LL_instance;
  var SpanishPattern_LL_YEISMO_instance;
  var SpanishPattern_RR_instance;
  var SpanishPattern_CH_instance;
  var SpanishPattern_NY_instance;
  var SpanishPattern_C_THETA_instance;
  var SpanishPattern_Z_THETA_instance;
  var SpanishPattern_C_SESEO_instance;
  var SpanishPattern_Z_SESEO_instance;
  var SpanishPattern_J_instance;
  var SpanishPattern_G_SOFT_instance;
  var SpanishPattern_GUE_instance;
  var SpanishPattern_GUI_instance;
  var SpanishPattern_GUE_UMLAUT_instance;
  var SpanishPattern_GUI_UMLAUT_instance;
  var SpanishPattern_QUE_instance;
  var SpanishPattern_QUI_instance;
  var SpanishPattern_H_SILENT_instance;
  var SpanishPattern_Y_VOWEL_instance;
  var SpanishPattern_CION_instance;
  var SpanishPattern_DAD_instance;
  var SpanishPattern_MENTE_instance;
  var SpanishPattern_ERO_instance;
  var SpanishPattern_ERA_instance;
  function Companion_20() {
    Companion_instance_20 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_19();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.cd_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.yc_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_19();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.dd_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.zc_1 = destination_0;
  }
  protoOf(Companion_20).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_19();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.gd_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = SpanishPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_2(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.cd_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_20;
  function Companion_getInstance_20() {
    SpanishPattern_initEntries();
    if (Companion_instance_20 == null)
      new Companion_20();
    return Companion_instance_20;
  }
  function values_19() {
    return [SpanishPattern_LL_getInstance(), SpanishPattern_LL_YEISMO_getInstance(), SpanishPattern_RR_getInstance(), SpanishPattern_CH_getInstance(), SpanishPattern_NY_getInstance(), SpanishPattern_C_THETA_getInstance(), SpanishPattern_Z_THETA_getInstance(), SpanishPattern_C_SESEO_getInstance(), SpanishPattern_Z_SESEO_getInstance(), SpanishPattern_J_getInstance(), SpanishPattern_G_SOFT_getInstance(), SpanishPattern_GUE_getInstance(), SpanishPattern_GUI_getInstance(), SpanishPattern_GUE_UMLAUT_getInstance(), SpanishPattern_GUI_UMLAUT_getInstance(), SpanishPattern_QUE_getInstance(), SpanishPattern_QUI_getInstance(), SpanishPattern_H_SILENT_getInstance(), SpanishPattern_Y_VOWEL_getInstance(), SpanishPattern_CION_getInstance(), SpanishPattern_DAD_getInstance(), SpanishPattern_MENTE_getInstance(), SpanishPattern_ERO_getInstance(), SpanishPattern_ERA_getInstance()];
  }
  function get_entries_19() {
    if ($ENTRIES_19 == null)
      $ENTRIES_19 = enumEntries(values_19());
    return $ENTRIES_19;
  }
  var SpanishPattern_entriesInitialized;
  function SpanishPattern_initEntries() {
    if (SpanishPattern_entriesInitialized)
      return Unit_instance;
    SpanishPattern_entriesInitialized = true;
    SpanishPattern_LL_instance = new SpanishPattern('LL', 0, 'll', '\u028E', 'll', listOf(['llamar', 'calle', 'ella']));
    SpanishPattern_LL_YEISMO_instance = new SpanishPattern('LL_YEISMO', 1, 'll', '\u029D', 'll-yeismo', listOf(['llamar', 'calle', 'ella']));
    SpanishPattern_RR_instance = new SpanishPattern('RR', 2, 'rr', 'r', 'rr-trill', listOf(['perro', 'carro', 'tierra']));
    SpanishPattern_CH_instance = new SpanishPattern('CH', 3, 'ch', 't\u0283', 'ch', listOf(['chico', 'noche', 'mucho']));
    SpanishPattern_NY_instance = new SpanishPattern('NY', 4, '\xF1', '\u0272', 'enye', listOf(['ni\xF1o', 'a\xF1o', 'Espa\xF1a']));
    SpanishPattern_C_THETA_instance = new SpanishPattern('C_THETA', 5, 'c', '\u03B8', 'c-theta', listOf(['cena', 'cinco', 'gracias']));
    SpanishPattern_Z_THETA_instance = new SpanishPattern('Z_THETA', 6, 'z', '\u03B8', 'z-theta', listOf(['zapato', 'azul', 'luz']));
    SpanishPattern_C_SESEO_instance = new SpanishPattern('C_SESEO', 7, 'c', 's', 'c-seseo', listOf(['cena', 'cinco', 'gracias']));
    SpanishPattern_Z_SESEO_instance = new SpanishPattern('Z_SESEO', 8, 'z', 's', 'z-seseo', listOf(['zapato', 'azul', 'luz']));
    SpanishPattern_J_instance = new SpanishPattern('J', 9, 'j', 'x', 'jota', listOf(['jugar', 'trabajo', 'rojo']));
    SpanishPattern_G_SOFT_instance = new SpanishPattern('G_SOFT', 10, 'g', 'x', 'g-soft', listOf(['gente', 'elegir', 'p\xE1gina']));
    SpanishPattern_GUE_instance = new SpanishPattern('GUE', 11, 'gue', 'ge', 'gue', listOf(['guerra', 'juguete', 'llegue']));
    SpanishPattern_GUI_instance = new SpanishPattern('GUI', 12, 'gui', 'gi', 'gui', listOf(['guitarra', '\xE1guila', 'guisante']));
    SpanishPattern_GUE_UMLAUT_instance = new SpanishPattern('GUE_UMLAUT', 13, 'g\xFCe', 'gwe', 'gue-umlaut', listOf(['verg\xFCenza', 'biling\xFCe']));
    SpanishPattern_GUI_UMLAUT_instance = new SpanishPattern('GUI_UMLAUT', 14, 'g\xFCi', 'gwi', 'gui-umlaut', listOf(['ping\xFCino', 'ling\xFC\xEDstica']));
    SpanishPattern_QUE_instance = new SpanishPattern('QUE', 15, 'que', 'ke', 'que', listOf(['que', 'porque', 'parque']));
    SpanishPattern_QUI_instance = new SpanishPattern('QUI', 16, 'qui', 'ki', 'qui', listOf(['quiero', 'aqu\xED', 'peque\xF1o']));
    SpanishPattern_H_SILENT_instance = new SpanishPattern('H_SILENT', 17, 'h', '', 'h-silent', listOf(['hola', 'hacer', 'ahora']));
    SpanishPattern_Y_VOWEL_instance = new SpanishPattern('Y_VOWEL', 18, 'y', 'i', 'y-vowel', listOf(['y', 'muy', 'hay']));
    SpanishPattern_CION_instance = new SpanishPattern('CION', 19, 'ci\xF3n', 'sjon', 'cion', listOf(['naci\xF3n', 'acci\xF3n', 'informaci\xF3n']), PatternPosition_END_getInstance());
    SpanishPattern_DAD_instance = new SpanishPattern('DAD', 20, 'dad', '\xF0a\xF0', 'dad', listOf(['ciudad', 'verdad', 'universidad']), PatternPosition_END_getInstance());
    SpanishPattern_MENTE_instance = new SpanishPattern('MENTE', 21, 'mente', 'mente', 'mente', listOf(['realmente', 'normalmente']), PatternPosition_END_getInstance());
    SpanishPattern_ERO_instance = new SpanishPattern('ERO', 22, 'ero', 'e\u027Eo', 'ero', listOf(['dinero', 'primero', 'extranjero']), PatternPosition_END_getInstance());
    SpanishPattern_ERA_instance = new SpanishPattern('ERA', 23, 'era', 'e\u027Ea', 'era', listOf(['manera', 'primera', 'carrera']), PatternPosition_END_getInstance());
    Companion_getInstance_20();
  }
  var $ENTRIES_19;
  function SpanishPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.cd_1 = pattern;
    this.dd_1 = ipa;
    this.ed_1 = displayName;
    this.fd_1 = examples;
    this.gd_1 = position;
  }
  protoOf(SpanishPattern).ei = function () {
    return this.dd_1;
  };
  protoOf(SpanishPattern).fi = function () {
    return this.ed_1;
  };
  protoOf(SpanishPattern).di = function () {
    return this.cd_1;
  };
  function sam$kotlin_Comparator$0_3(function_0) {
    this.ut_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_3).x8 = function (a, b) {
    return this.ut_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_3).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_3).e2 = function () {
    return this.ut_1;
  };
  protoOf(sam$kotlin_Comparator$0_3).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_3).hashCode = function () {
    return hashCode(this.e2());
  };
  function ItalianPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.ld_1.length;
    var tmp$ret$1 = a.ld_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var ItalianPattern_SCE_instance;
  var ItalianPattern_SCI_instance;
  var ItalianPattern_SCIA_instance;
  var ItalianPattern_SCIO_instance;
  var ItalianPattern_SCIU_instance;
  var ItalianPattern_SCH_instance;
  var ItalianPattern_GLI_instance;
  var ItalianPattern_GN_instance;
  var ItalianPattern_CE_instance;
  var ItalianPattern_CI_instance;
  var ItalianPattern_CIA_instance;
  var ItalianPattern_CIO_instance;
  var ItalianPattern_CIU_instance;
  var ItalianPattern_CH_instance;
  var ItalianPattern_GE_instance;
  var ItalianPattern_GI_instance;
  var ItalianPattern_GIA_instance;
  var ItalianPattern_GIO_instance;
  var ItalianPattern_GIU_instance;
  var ItalianPattern_GH_instance;
  var ItalianPattern_QU_instance;
  var ItalianPattern_Z_TS_instance;
  var ItalianPattern_Z_DZ_instance;
  var ItalianPattern_LL_instance;
  var ItalianPattern_NN_instance;
  var ItalianPattern_TT_instance;
  var ItalianPattern_PP_instance;
  var ItalianPattern_ZIONE_instance;
  var ItalianPattern_MENTE_instance;
  var ItalianPattern_EZZA_instance;
  var ItalianPattern_ERIA_instance;
  function Companion_21() {
    Companion_instance_21 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_20();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.ld_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.hd_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_20();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.md_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.id_1 = destination_0;
  }
  protoOf(Companion_21).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_20();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.pd_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = ItalianPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_3(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.ld_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_21;
  function Companion_getInstance_21() {
    ItalianPattern_initEntries();
    if (Companion_instance_21 == null)
      new Companion_21();
    return Companion_instance_21;
  }
  function values_20() {
    return [ItalianPattern_SCE_getInstance(), ItalianPattern_SCI_getInstance(), ItalianPattern_SCIA_getInstance(), ItalianPattern_SCIO_getInstance(), ItalianPattern_SCIU_getInstance(), ItalianPattern_SCH_getInstance(), ItalianPattern_GLI_getInstance(), ItalianPattern_GN_getInstance(), ItalianPattern_CE_getInstance(), ItalianPattern_CI_getInstance(), ItalianPattern_CIA_getInstance(), ItalianPattern_CIO_getInstance(), ItalianPattern_CIU_getInstance(), ItalianPattern_CH_getInstance(), ItalianPattern_GE_getInstance(), ItalianPattern_GI_getInstance(), ItalianPattern_GIA_getInstance(), ItalianPattern_GIO_getInstance(), ItalianPattern_GIU_getInstance(), ItalianPattern_GH_getInstance(), ItalianPattern_QU_getInstance(), ItalianPattern_Z_TS_getInstance(), ItalianPattern_Z_DZ_getInstance(), ItalianPattern_LL_getInstance(), ItalianPattern_NN_getInstance(), ItalianPattern_TT_getInstance(), ItalianPattern_PP_getInstance(), ItalianPattern_ZIONE_getInstance(), ItalianPattern_MENTE_getInstance(), ItalianPattern_EZZA_getInstance(), ItalianPattern_ERIA_getInstance()];
  }
  function get_entries_20() {
    if ($ENTRIES_20 == null)
      $ENTRIES_20 = enumEntries(values_20());
    return $ENTRIES_20;
  }
  var ItalianPattern_entriesInitialized;
  function ItalianPattern_initEntries() {
    if (ItalianPattern_entriesInitialized)
      return Unit_instance;
    ItalianPattern_entriesInitialized = true;
    ItalianPattern_SCE_instance = new ItalianPattern('SCE', 0, 'sce', '\u0283e', 'sce', listOf(['scena', 'scelta', 'pesce']));
    ItalianPattern_SCI_instance = new ItalianPattern('SCI', 1, 'sci', '\u0283i', 'sci', listOf(['scienza', 'uscire', 'piscina']));
    ItalianPattern_SCIA_instance = new ItalianPattern('SCIA', 2, 'scia', '\u0283a', 'scia', listOf(['lasciare', 'sciare', 'fascia']));
    ItalianPattern_SCIO_instance = new ItalianPattern('SCIO', 3, 'scio', '\u0283o', 'scio', listOf(['uscio', 'liscio']));
    ItalianPattern_SCIU_instance = new ItalianPattern('SCIU', 4, 'sciu', '\u0283u', 'sciu', listOf(['sciupare', 'asciutto']));
    ItalianPattern_SCH_instance = new ItalianPattern('SCH', 5, 'sch', 'sk', 'sch', listOf(['schema', 'scherzo', 'fischio']));
    ItalianPattern_GLI_instance = new ItalianPattern('GLI', 6, 'gli', '\u028Ei', 'gli', listOf(['famiglia', 'figlio', 'meglio']));
    ItalianPattern_GN_instance = new ItalianPattern('GN', 7, 'gn', '\u0272', 'gn', listOf(['bagno', 'sogno', 'montagna']));
    ItalianPattern_CE_instance = new ItalianPattern('CE', 8, 'ce', 't\u0283e', 'ce', listOf(['cena', 'voce', 'pace']));
    ItalianPattern_CI_instance = new ItalianPattern('CI', 9, 'ci', 't\u0283i', 'ci', listOf(['citt\xE0', 'cinema', 'amici']));
    ItalianPattern_CIA_instance = new ItalianPattern('CIA', 10, 'cia', 't\u0283a', 'cia', listOf(['ciao', 'pancia', 'camicia']));
    ItalianPattern_CIO_instance = new ItalianPattern('CIO', 11, 'cio', 't\u0283o', 'cio', listOf(['bacio', 'calcio', 'ufficio']));
    ItalianPattern_CIU_instance = new ItalianPattern('CIU', 12, 'ciu', 't\u0283u', 'ciu', listOf(['ciuffo', 'fanciullo']));
    ItalianPattern_CH_instance = new ItalianPattern('CH', 13, 'ch', 'k', 'ch', listOf(['che', 'chi', 'chiaro']));
    ItalianPattern_GE_instance = new ItalianPattern('GE', 14, 'ge', 'd\u0292e', 'ge', listOf(['gente', 'gelato', 'leggere']));
    ItalianPattern_GI_instance = new ItalianPattern('GI', 15, 'gi', 'd\u0292i', 'gi', listOf(['giorno', 'giro', 'oggi']));
    ItalianPattern_GIA_instance = new ItalianPattern('GIA', 16, 'gia', 'd\u0292a', 'gia', listOf(['gi\xE0', 'spiaggia', 'viaggio']));
    ItalianPattern_GIO_instance = new ItalianPattern('GIO', 17, 'gio', 'd\u0292o', 'gio', listOf(['giovane', 'maggio', 'orologio']));
    ItalianPattern_GIU_instance = new ItalianPattern('GIU', 18, 'giu', 'd\u0292u', 'giu', listOf(['giugno', 'giusto', 'gi\xF9']));
    ItalianPattern_GH_instance = new ItalianPattern('GH', 19, 'gh', 'g', 'gh', listOf(['ghetto', 'spaghetti', 'funghi']));
    ItalianPattern_QU_instance = new ItalianPattern('QU', 20, 'qu', 'kw', 'qu', listOf(['questo', 'quale', 'quattro']));
    ItalianPattern_Z_TS_instance = new ItalianPattern('Z_TS', 21, 'z', 'ts', 'z-ts', listOf(['pizza', 'stanza', 'grazie']));
    ItalianPattern_Z_DZ_instance = new ItalianPattern('Z_DZ', 22, 'z', 'dz', 'z-dz', listOf(['zero', 'zona', 'zanzara']));
    ItalianPattern_LL_instance = new ItalianPattern('LL', 23, 'll', 'l\u02D0', 'll-gem', listOf(['bello', 'quello', 'cappello']));
    ItalianPattern_NN_instance = new ItalianPattern('NN', 24, 'nn', 'n\u02D0', 'nn-gem', listOf(['anno', 'donna', 'penna']));
    ItalianPattern_TT_instance = new ItalianPattern('TT', 25, 'tt', 't\u02D0', 'tt-gem', listOf(['tutto', 'notte', 'detto']));
    ItalianPattern_PP_instance = new ItalianPattern('PP', 26, 'pp', 'p\u02D0', 'pp-gem', listOf(['troppo', 'gruppo', 'cappuccino']));
    ItalianPattern_ZIONE_instance = new ItalianPattern('ZIONE', 27, 'zione', 'tsjone', 'zione', listOf(['azione', 'nazione', 'stazione']), PatternPosition_END_getInstance());
    ItalianPattern_MENTE_instance = new ItalianPattern('MENTE', 28, 'mente', 'mente', 'mente', listOf(['veramente', 'solamente']), PatternPosition_END_getInstance());
    ItalianPattern_EZZA_instance = new ItalianPattern('EZZA', 29, 'ezza', 'et\u02D0sa', 'ezza', listOf(['bellezza', 'certezza']), PatternPosition_END_getInstance());
    ItalianPattern_ERIA_instance = new ItalianPattern('ERIA', 30, 'eria', 'e\u02C8ria', 'eria', listOf(['pizzeria', 'pasticceria']), PatternPosition_END_getInstance());
    Companion_getInstance_21();
  }
  var $ENTRIES_20;
  function ItalianPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.ld_1 = pattern;
    this.md_1 = ipa;
    this.nd_1 = displayName;
    this.od_1 = examples;
    this.pd_1 = position;
  }
  protoOf(ItalianPattern).ei = function () {
    return this.md_1;
  };
  protoOf(ItalianPattern).fi = function () {
    return this.nd_1;
  };
  protoOf(ItalianPattern).di = function () {
    return this.ld_1;
  };
  function sam$kotlin_Comparator$0_4(function_0) {
    this.vt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_4).x8 = function (a, b) {
    return this.vt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_4).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_4).e2 = function () {
    return this.vt_1;
  };
  protoOf(sam$kotlin_Comparator$0_4).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_4).hashCode = function () {
    return hashCode(this.e2());
  };
  function PortuguesePattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.ud_1.length;
    var tmp$ret$1 = a.ud_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var PortuguesePattern_LH_instance;
  var PortuguesePattern_NH_instance;
  var PortuguesePattern_CH_instance;
  var PortuguesePattern_X_SH_instance;
  var PortuguesePattern_X_KS_instance;
  var PortuguesePattern_X_Z_instance;
  var PortuguesePattern_X_S_instance;
  var PortuguesePattern_A_TIL_instance;
  var PortuguesePattern_O_TIL_instance;
  var PortuguesePattern_AO_instance;
  var PortuguesePattern_OES_instance;
  var PortuguesePattern_AES_instance;
  var PortuguesePattern_RR_instance;
  var PortuguesePattern_R_INITIAL_instance;
  var PortuguesePattern_SS_instance;
  var PortuguesePattern_C_CEDILLA_instance;
  var PortuguesePattern_QUE_instance;
  var PortuguesePattern_QUI_instance;
  var PortuguesePattern_GUE_instance;
  var PortuguesePattern_GUI_instance;
  var PortuguesePattern_CAO_instance;
  var PortuguesePattern_DADE_instance;
  var PortuguesePattern_MENTE_instance;
  var PortuguesePattern_AGEM_instance;
  var PortuguesePattern_EIRO_instance;
  var PortuguesePattern_EIRA_instance;
  function Companion_22() {
    Companion_instance_22 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_21();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.ud_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.qd_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_21();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.vd_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.rd_1 = destination_0;
  }
  protoOf(Companion_22).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_21();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.yd_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = PortuguesePattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_4(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.ud_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_22;
  function Companion_getInstance_22() {
    PortuguesePattern_initEntries();
    if (Companion_instance_22 == null)
      new Companion_22();
    return Companion_instance_22;
  }
  function values_21() {
    return [PortuguesePattern_LH_getInstance(), PortuguesePattern_NH_getInstance(), PortuguesePattern_CH_getInstance(), PortuguesePattern_X_SH_getInstance(), PortuguesePattern_X_KS_getInstance(), PortuguesePattern_X_Z_getInstance(), PortuguesePattern_X_S_getInstance(), PortuguesePattern_A_TIL_getInstance(), PortuguesePattern_O_TIL_getInstance(), PortuguesePattern_AO_getInstance(), PortuguesePattern_OES_getInstance(), PortuguesePattern_AES_getInstance(), PortuguesePattern_RR_getInstance(), PortuguesePattern_R_INITIAL_getInstance(), PortuguesePattern_SS_getInstance(), PortuguesePattern_C_CEDILLA_getInstance(), PortuguesePattern_QUE_getInstance(), PortuguesePattern_QUI_getInstance(), PortuguesePattern_GUE_getInstance(), PortuguesePattern_GUI_getInstance(), PortuguesePattern_CAO_getInstance(), PortuguesePattern_DADE_getInstance(), PortuguesePattern_MENTE_getInstance(), PortuguesePattern_AGEM_getInstance(), PortuguesePattern_EIRO_getInstance(), PortuguesePattern_EIRA_getInstance()];
  }
  function get_entries_21() {
    if ($ENTRIES_21 == null)
      $ENTRIES_21 = enumEntries(values_21());
    return $ENTRIES_21;
  }
  var PortuguesePattern_entriesInitialized;
  function PortuguesePattern_initEntries() {
    if (PortuguesePattern_entriesInitialized)
      return Unit_instance;
    PortuguesePattern_entriesInitialized = true;
    PortuguesePattern_LH_instance = new PortuguesePattern('LH', 0, 'lh', '\u028E', 'lh', listOf(['filho', 'olho', 'trabalho']));
    PortuguesePattern_NH_instance = new PortuguesePattern('NH', 1, 'nh', '\u0272', 'nh', listOf(['minha', 'senhor', 'ninho']));
    PortuguesePattern_CH_instance = new PortuguesePattern('CH', 2, 'ch', '\u0283', 'ch', listOf(['chave', 'chocolate', 'fechar']));
    PortuguesePattern_X_SH_instance = new PortuguesePattern('X_SH', 3, 'x', '\u0283', 'x-sh', listOf(['xadrez', 'caixa', 'baixo']));
    PortuguesePattern_X_KS_instance = new PortuguesePattern('X_KS', 4, 'x', 'ks', 'x-ks', listOf(['t\xE1xi', 'fixo', 'm\xE1ximo']));
    PortuguesePattern_X_Z_instance = new PortuguesePattern('X_Z', 5, 'x', 'z', 'x-z', listOf(['exame', 'exemplo', 'exerc\xEDcio']));
    PortuguesePattern_X_S_instance = new PortuguesePattern('X_S', 6, 'x', 's', 'x-s', listOf(['pr\xF3ximo', 'aux\xEDlio']));
    PortuguesePattern_A_TIL_instance = new PortuguesePattern('A_TIL', 7, '\xE3', '\u0250\u0303', 'a-til', listOf(['irm\xE3', 'manh\xE3', 'ma\xE7\xE3']));
    PortuguesePattern_O_TIL_instance = new PortuguesePattern('O_TIL', 8, '\xF5', '\xF5', 'o-til', listOf(['lim\xF5es', 'cora\xE7\xF5es']));
    PortuguesePattern_AO_instance = new PortuguesePattern('AO', 9, '\xE3o', '\u0250\u0303w\u0303', 'ao-nasal', listOf(['n\xE3o', 'p\xE3o', 'irm\xE3o']), PatternPosition_END_getInstance());
    PortuguesePattern_OES_instance = new PortuguesePattern('OES', 10, '\xF5es', '\xF5j\u0283', 'oes-nasal', listOf(['lim\xF5es', 'na\xE7\xF5es']), PatternPosition_END_getInstance());
    PortuguesePattern_AES_instance = new PortuguesePattern('AES', 11, '\xE3es', '\u0250\u0303j\u0283', 'aes-nasal', listOf(['p\xE3es', 'm\xE3es', 'c\xE3es']), PatternPosition_END_getInstance());
    PortuguesePattern_RR_instance = new PortuguesePattern('RR', 12, 'rr', '\u0281', 'rr', listOf(['carro', 'terra', 'arroz']));
    PortuguesePattern_R_INITIAL_instance = new PortuguesePattern('R_INITIAL', 13, 'r', '\u0281', 'r-initial', listOf(['rato', 'rio', 'rua']), PatternPosition_START_getInstance());
    PortuguesePattern_SS_instance = new PortuguesePattern('SS', 14, 'ss', 's', 'ss', listOf(['p\xE1ssaro', 'pessoa', 'assunto']));
    PortuguesePattern_C_CEDILLA_instance = new PortuguesePattern('C_CEDILLA', 15, '\xE7', 's', 'c-cedilla', listOf(['cabe\xE7a', 'a\xE7\xFAcar', 'cora\xE7\xE3o']));
    PortuguesePattern_QUE_instance = new PortuguesePattern('QUE', 16, 'que', 'ke', 'que', listOf(['que', 'porque', 'parque']));
    PortuguesePattern_QUI_instance = new PortuguesePattern('QUI', 17, 'qui', 'ki', 'qui', listOf(['aqui', 'quilo', 'm\xE1quina']));
    PortuguesePattern_GUE_instance = new PortuguesePattern('GUE', 18, 'gue', 'ge', 'gue', listOf(['guerra', 'segue']));
    PortuguesePattern_GUI_instance = new PortuguesePattern('GUI', 19, 'gui', 'gi', 'gui', listOf(['guitarra', 'seguir']));
    PortuguesePattern_CAO_instance = new PortuguesePattern('CAO', 20, '\xE7\xE3o', 's\u0250\u0303w\u0303', 'cao', listOf(['a\xE7\xE3o', 'informa\xE7\xE3o', 'na\xE7\xE3o']), PatternPosition_END_getInstance());
    PortuguesePattern_DADE_instance = new PortuguesePattern('DADE', 21, 'dade', 'da\xF0\u0268', 'dade', listOf(['cidade', 'verdade', 'universidade']), PatternPosition_END_getInstance());
    PortuguesePattern_MENTE_instance = new PortuguesePattern('MENTE', 22, 'mente', 'm\u1EBDt\u0268', 'mente', listOf(['realmente', 'certamente']), PatternPosition_END_getInstance());
    PortuguesePattern_AGEM_instance = new PortuguesePattern('AGEM', 23, 'agem', 'a\u0292\u1EBDj', 'agem', listOf(['viagem', 'mensagem', 'coragem']), PatternPosition_END_getInstance());
    PortuguesePattern_EIRO_instance = new PortuguesePattern('EIRO', 24, 'eiro', 'ej\u027Eu', 'eiro', listOf(['brasileiro', 'primeiro', 'dinheiro']), PatternPosition_END_getInstance());
    PortuguesePattern_EIRA_instance = new PortuguesePattern('EIRA', 25, 'eira', 'ej\u027Ea', 'eira', listOf(['maneira', 'primeira', 'bandeira']), PatternPosition_END_getInstance());
    Companion_getInstance_22();
  }
  var $ENTRIES_21;
  function PortuguesePattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.ud_1 = pattern;
    this.vd_1 = ipa;
    this.wd_1 = displayName;
    this.xd_1 = examples;
    this.yd_1 = position;
  }
  protoOf(PortuguesePattern).ei = function () {
    return this.vd_1;
  };
  protoOf(PortuguesePattern).fi = function () {
    return this.wd_1;
  };
  protoOf(PortuguesePattern).di = function () {
    return this.ud_1;
  };
  function sam$kotlin_Comparator$0_5(function_0) {
    this.wt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_5).x8 = function (a, b) {
    return this.wt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_5).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_5).e2 = function () {
    return this.wt_1;
  };
  protoOf(sam$kotlin_Comparator$0_5).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_5).hashCode = function () {
    return hashCode(this.e2());
  };
  function DutchPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.de_1.length;
    var tmp$ret$1 = a.de_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var DutchPattern_G_instance;
  var DutchPattern_CH_instance;
  var DutchPattern_SCH_instance;
  var DutchPattern_IJ_instance;
  var DutchPattern_EI_instance;
  var DutchPattern_UI_instance;
  var DutchPattern_EU_instance;
  var DutchPattern_OE_instance;
  var DutchPattern_OU_instance;
  var DutchPattern_AU_instance;
  var DutchPattern_AA_instance;
  var DutchPattern_EE_instance;
  var DutchPattern_OO_instance;
  var DutchPattern_UU_instance;
  var DutchPattern_IE_instance;
  var DutchPattern_W_instance;
  var DutchPattern_V_instance;
  var DutchPattern_LIJK_instance;
  var DutchPattern_HEID_instance;
  var DutchPattern_ISCH_instance;
  var DutchPattern_TIE_instance;
  function Companion_23() {
    Companion_instance_23 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_22();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.de_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.zd_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_22();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.ee_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.ae_1 = destination_0;
  }
  protoOf(Companion_23).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_22();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.he_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = DutchPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_5(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.de_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_23;
  function Companion_getInstance_23() {
    DutchPattern_initEntries();
    if (Companion_instance_23 == null)
      new Companion_23();
    return Companion_instance_23;
  }
  function values_22() {
    return [DutchPattern_G_getInstance(), DutchPattern_CH_getInstance(), DutchPattern_SCH_getInstance(), DutchPattern_IJ_getInstance(), DutchPattern_EI_getInstance(), DutchPattern_UI_getInstance(), DutchPattern_EU_getInstance(), DutchPattern_OE_getInstance(), DutchPattern_OU_getInstance(), DutchPattern_AU_getInstance(), DutchPattern_AA_getInstance(), DutchPattern_EE_getInstance(), DutchPattern_OO_getInstance(), DutchPattern_UU_getInstance(), DutchPattern_IE_getInstance(), DutchPattern_W_getInstance(), DutchPattern_V_getInstance(), DutchPattern_LIJK_getInstance(), DutchPattern_HEID_getInstance(), DutchPattern_ISCH_getInstance(), DutchPattern_TIE_getInstance()];
  }
  function get_entries_22() {
    if ($ENTRIES_22 == null)
      $ENTRIES_22 = enumEntries(values_22());
    return $ENTRIES_22;
  }
  var DutchPattern_entriesInitialized;
  function DutchPattern_initEntries() {
    if (DutchPattern_entriesInitialized)
      return Unit_instance;
    DutchPattern_entriesInitialized = true;
    DutchPattern_G_instance = new DutchPattern('G', 0, 'g', 'x', 'g-guttural', listOf(['goed', 'gaan', 'zeggen']));
    DutchPattern_CH_instance = new DutchPattern('CH', 1, 'ch', 'x', 'ch-guttural', listOf(['acht', 'nacht', 'lachen']));
    DutchPattern_SCH_instance = new DutchPattern('SCH', 2, 'sch', 'sx', 'sch', listOf(['school', 'schrijven', 'schip']));
    DutchPattern_IJ_instance = new DutchPattern('IJ', 3, 'ij', '\u025Bi', 'ij', listOf(['ijs', 'wijn', 'mijn']));
    DutchPattern_EI_instance = new DutchPattern('EI', 4, 'ei', '\u025Bi', 'ei', listOf(['klein', 'trein', 'geen']));
    DutchPattern_UI_instance = new DutchPattern('UI', 5, 'ui', '\u0153y', 'ui', listOf(['huis', 'buiten', 'fruit']));
    DutchPattern_EU_instance = new DutchPattern('EU', 6, 'eu', '\xF8', 'eu', listOf(['neus', 'deur', 'keuken']));
    DutchPattern_OE_instance = new DutchPattern('OE', 7, 'oe', 'u', 'oe', listOf(['boek', 'goed', 'moeite']));
    DutchPattern_OU_instance = new DutchPattern('OU', 8, 'ou', '\u0251u', 'ou', listOf(['oud', 'koud', 'houden']));
    DutchPattern_AU_instance = new DutchPattern('AU', 9, 'au', '\u0251u', 'au', listOf(['auto', 'blauw']));
    DutchPattern_AA_instance = new DutchPattern('AA', 10, 'aa', 'a\u02D0', 'aa-long', listOf(['naam', 'straat', 'gaan']));
    DutchPattern_EE_instance = new DutchPattern('EE', 11, 'ee', 'e\u02D0', 'ee-long', listOf(['been', 'twee', 'veel']));
    DutchPattern_OO_instance = new DutchPattern('OO', 12, 'oo', 'o\u02D0', 'oo-long', listOf(['groot', 'rood', 'ook']));
    DutchPattern_UU_instance = new DutchPattern('UU', 13, 'uu', 'y\u02D0', 'uu-long', listOf(['uur', 'muur', 'duur']));
    DutchPattern_IE_instance = new DutchPattern('IE', 14, 'ie', 'i', 'ie', listOf(['niet', 'drie', 'brief']));
    DutchPattern_W_instance = new DutchPattern('W', 15, 'w', '\u028B', 'w', listOf(['water', 'waar', 'weten']));
    DutchPattern_V_instance = new DutchPattern('V', 16, 'v', 'v', 'v', listOf(['van', 'veel', 'voor']));
    DutchPattern_LIJK_instance = new DutchPattern('LIJK', 17, 'lijk', 'l\u0259k', 'lijk', listOf(['eigenlijk', 'mogelijk', 'moeilijk']), PatternPosition_END_getInstance());
    DutchPattern_HEID_instance = new DutchPattern('HEID', 18, 'heid', 'h\u025Bit', 'heid', listOf(['mogelijkheid', 'vrijheid']), PatternPosition_END_getInstance());
    DutchPattern_ISCH_instance = new DutchPattern('ISCH', 19, 'isch', 'is', 'isch', listOf(['historisch', 'praktisch']), PatternPosition_END_getInstance());
    DutchPattern_TIE_instance = new DutchPattern('TIE', 20, 'tie', 'tsi', 'tie', listOf(['informatie', 'situatie']), PatternPosition_END_getInstance());
    Companion_getInstance_23();
  }
  var $ENTRIES_22;
  function DutchPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.de_1 = pattern;
    this.ee_1 = ipa;
    this.fe_1 = displayName;
    this.ge_1 = examples;
    this.he_1 = position;
  }
  protoOf(DutchPattern).ei = function () {
    return this.ee_1;
  };
  protoOf(DutchPattern).fi = function () {
    return this.fe_1;
  };
  protoOf(DutchPattern).di = function () {
    return this.de_1;
  };
  function sam$kotlin_Comparator$0_6(function_0) {
    this.xt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_6).x8 = function (a, b) {
    return this.xt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_6).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_6).e2 = function () {
    return this.xt_1;
  };
  protoOf(sam$kotlin_Comparator$0_6).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_6).hashCode = function () {
    return hashCode(this.e2());
  };
  function PolishPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.me_1.length;
    var tmp$ret$1 = a.me_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var PolishPattern_SZ_instance;
  var PolishPattern_RZ_instance;
  var PolishPattern_CZ_instance;
  var PolishPattern_DZ_instance;
  var PolishPattern_DZ_SOFT_instance;
  var PolishPattern_DZ_RETRO_instance;
  var PolishPattern_S_SOFT_instance;
  var PolishPattern_Z_SOFT_instance;
  var PolishPattern_C_SOFT_instance;
  var PolishPattern_N_SOFT_instance;
  var PolishPattern_L_STROKE_instance;
  var PolishPattern_A_OGONEK_instance;
  var PolishPattern_E_OGONEK_instance;
  var PolishPattern_O_ACUTE_instance;
  var PolishPattern_CH_instance;
  var PolishPattern_CI_instance;
  var PolishPattern_SI_instance;
  var PolishPattern_ZI_instance;
  var PolishPattern_NI_instance;
  var PolishPattern_OWOSC_instance;
  var PolishPattern_ACJA_instance;
  var PolishPattern_SKI_instance;
  var PolishPattern_OWSKI_instance;
  function Companion_24() {
    Companion_instance_24 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_23();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.me_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.ie_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_23();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.ne_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.je_1 = destination_0;
  }
  protoOf(Companion_24).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_23();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.qe_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = PolishPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_6(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.me_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_24;
  function Companion_getInstance_24() {
    PolishPattern_initEntries();
    if (Companion_instance_24 == null)
      new Companion_24();
    return Companion_instance_24;
  }
  function values_23() {
    return [PolishPattern_SZ_getInstance(), PolishPattern_RZ_getInstance(), PolishPattern_CZ_getInstance(), PolishPattern_DZ_getInstance(), PolishPattern_DZ_SOFT_getInstance(), PolishPattern_DZ_RETRO_getInstance(), PolishPattern_S_SOFT_getInstance(), PolishPattern_Z_SOFT_getInstance(), PolishPattern_C_SOFT_getInstance(), PolishPattern_N_SOFT_getInstance(), PolishPattern_L_STROKE_getInstance(), PolishPattern_A_OGONEK_getInstance(), PolishPattern_E_OGONEK_getInstance(), PolishPattern_O_ACUTE_getInstance(), PolishPattern_CH_getInstance(), PolishPattern_CI_getInstance(), PolishPattern_SI_getInstance(), PolishPattern_ZI_getInstance(), PolishPattern_NI_getInstance(), PolishPattern_OWOSC_getInstance(), PolishPattern_ACJA_getInstance(), PolishPattern_SKI_getInstance(), PolishPattern_OWSKI_getInstance()];
  }
  function get_entries_23() {
    if ($ENTRIES_23 == null)
      $ENTRIES_23 = enumEntries(values_23());
    return $ENTRIES_23;
  }
  var PolishPattern_entriesInitialized;
  function PolishPattern_initEntries() {
    if (PolishPattern_entriesInitialized)
      return Unit_instance;
    PolishPattern_entriesInitialized = true;
    PolishPattern_SZ_instance = new PolishPattern('SZ', 0, 'sz', '\u0282', 'sz', listOf(['szko\u0142a', 'pisz', 'masz']));
    PolishPattern_RZ_instance = new PolishPattern('RZ', 1, 'rz', '\u0290', 'rz', listOf(['rzeka', 'morze', 'przez']));
    PolishPattern_CZ_instance = new PolishPattern('CZ', 2, 'cz', 't\u0282', 'cz', listOf(['czas', 'czarny', 'cz\u0142owiek']));
    PolishPattern_DZ_instance = new PolishPattern('DZ', 3, 'dz', 'dz', 'dz', listOf(['dzie\u0144', 'bardzo']));
    PolishPattern_DZ_SOFT_instance = new PolishPattern('DZ_SOFT', 4, 'd\u017A', 'd\u0291', 'dz-soft', listOf(['nied\u017Awied\u017A', 'd\u017Awi\u0119k']));
    PolishPattern_DZ_RETRO_instance = new PolishPattern('DZ_RETRO', 5, 'd\u017C', 'd\u0290', 'dz-retro', listOf(['d\u017Cem', 'd\u017Cungla']));
    PolishPattern_S_SOFT_instance = new PolishPattern('S_SOFT', 6, '\u015B', '\u0255', 's-soft', listOf(['\u015Bwiat', 'dzi\u015B', 'prosi\u0107']));
    PolishPattern_Z_SOFT_instance = new PolishPattern('Z_SOFT', 7, '\u017A', '\u0291', 'z-soft', listOf(['\u017Ar\xF3d\u0142o', 'p\xF3\u017Ano']));
    PolishPattern_C_SOFT_instance = new PolishPattern('C_SOFT', 8, '\u0107', 't\u0255', 'c-soft', listOf(['\u0107ma', 'by\u0107', 'robi\u0107']));
    PolishPattern_N_SOFT_instance = new PolishPattern('N_SOFT', 9, '\u0144', '\u0272', 'n-soft', listOf(['ko\u0144', 'dzie\u0144']));
    PolishPattern_L_STROKE_instance = new PolishPattern('L_STROKE', 10, '\u0142', 'w', 'l-stroke', listOf(['ma\u0142y', 'szko\u0142a', '\u0142\xF3d\u017A']));
    PolishPattern_A_OGONEK_instance = new PolishPattern('A_OGONEK', 11, '\u0105', '\u0254\u0303', 'a-ogonek', listOf(['s\u0105', 'm\u0105\u017C', 'r\u0105k']));
    PolishPattern_E_OGONEK_instance = new PolishPattern('E_OGONEK', 12, '\u0119', '\u025B\u0303', 'e-ogonek', listOf(['si\u0119', 'r\u0119ka', 'b\u0119d\u0119']));
    PolishPattern_O_ACUTE_instance = new PolishPattern('O_ACUTE', 13, '\xF3', 'u', 'o-acute', listOf(['m\xF3j', 'kt\xF3ry', 'wr\xF3g']));
    PolishPattern_CH_instance = new PolishPattern('CH', 14, 'ch', 'x', 'ch', listOf(['chcie\u0107', 'ch\u0142opiec', 'chodzi\u0107']));
    PolishPattern_CI_instance = new PolishPattern('CI', 15, 'ci', 't\u0255i', 'ci', listOf(['cia\u0142o', 'cichy', 'ciekawy']));
    PolishPattern_SI_instance = new PolishPattern('SI', 16, 'si', '\u0255i', 'si', listOf(['siedem', 'siostra', 'ksi\u0119\u017Cyc']));
    PolishPattern_ZI_instance = new PolishPattern('ZI', 17, 'zi', '\u0291i', 'zi', listOf(['zima', 'zielony']));
    PolishPattern_NI_instance = new PolishPattern('NI', 18, 'ni', '\u0272i', 'ni', listOf(['nie', 'nic', 'nigdy']));
    PolishPattern_OWOSC_instance = new PolishPattern('OWOSC', 19, 'owo\u015B\u0107', 'ov\u0254\u0255t\u0255', 'owosc', listOf(['mo\u017Cliwo\u015B\u0107', 'pewno\u015B\u0107']), PatternPosition_END_getInstance());
    PolishPattern_ACJA_instance = new PolishPattern('ACJA', 20, 'acja', 'atsja', 'acja', listOf(['informacja', 'sytuacja']), PatternPosition_END_getInstance());
    PolishPattern_SKI_instance = new PolishPattern('SKI', 21, 'ski', 'ski', 'ski', listOf(['polski', 'miejski']), PatternPosition_END_getInstance());
    PolishPattern_OWSKI_instance = new PolishPattern('OWSKI', 22, 'owski', '\u0254fski', 'owski', listOf(['warszawski', 'krakowski']), PatternPosition_END_getInstance());
    Companion_getInstance_24();
  }
  var $ENTRIES_23;
  function PolishPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.me_1 = pattern;
    this.ne_1 = ipa;
    this.oe_1 = displayName;
    this.pe_1 = examples;
    this.qe_1 = position;
  }
  protoOf(PolishPattern).ei = function () {
    return this.ne_1;
  };
  protoOf(PolishPattern).fi = function () {
    return this.oe_1;
  };
  protoOf(PolishPattern).di = function () {
    return this.me_1;
  };
  function sam$kotlin_Comparator$0_7(function_0) {
    this.yt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_7).x8 = function (a, b) {
    return this.yt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_7).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_7).e2 = function () {
    return this.yt_1;
  };
  protoOf(sam$kotlin_Comparator$0_7).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_7).hashCode = function () {
    return hashCode(this.e2());
  };
  function TurkishPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.ve_1.length;
    var tmp$ret$1 = a.ve_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var TurkishPattern_S_CEDILLA_instance;
  var TurkishPattern_C_CEDILLA_instance;
  var TurkishPattern_C_SOFT_instance;
  var TurkishPattern_G_BREVE_instance;
  var TurkishPattern_I_DOTLESS_instance;
  var TurkishPattern_I_DOTTED_instance;
  var TurkishPattern_O_UMLAUT_instance;
  var TurkishPattern_U_UMLAUT_instance;
  var TurkishPattern_SH_instance;
  var TurkishPattern_LIK_instance;
  var TurkishPattern_LUK_instance;
  var TurkishPattern_LIK_I_instance;
  var TurkishPattern_CI_instance;
  var TurkishPattern_CU_instance;
  var TurkishPattern_CI_I_instance;
  var TurkishPattern_LAR_instance;
  var TurkishPattern_LER_instance;
  var TurkishPattern_DA_instance;
  var TurkishPattern_DE_instance;
  var TurkishPattern_TA_instance;
  var TurkishPattern_TE_instance;
  function Companion_25() {
    Companion_instance_25 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_24();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.ve_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.re_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_24();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.we_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.se_1 = destination_0;
  }
  protoOf(Companion_25).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_24();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.ze_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = TurkishPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_7(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.ve_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_25;
  function Companion_getInstance_25() {
    TurkishPattern_initEntries();
    if (Companion_instance_25 == null)
      new Companion_25();
    return Companion_instance_25;
  }
  function values_24() {
    return [TurkishPattern_S_CEDILLA_getInstance(), TurkishPattern_C_CEDILLA_getInstance(), TurkishPattern_C_SOFT_getInstance(), TurkishPattern_G_BREVE_getInstance(), TurkishPattern_I_DOTLESS_getInstance(), TurkishPattern_I_DOTTED_getInstance(), TurkishPattern_O_UMLAUT_getInstance(), TurkishPattern_U_UMLAUT_getInstance(), TurkishPattern_SH_getInstance(), TurkishPattern_LIK_getInstance(), TurkishPattern_LUK_getInstance(), TurkishPattern_LIK_I_getInstance(), TurkishPattern_CI_getInstance(), TurkishPattern_CU_getInstance(), TurkishPattern_CI_I_getInstance(), TurkishPattern_LAR_getInstance(), TurkishPattern_LER_getInstance(), TurkishPattern_DA_getInstance(), TurkishPattern_DE_getInstance(), TurkishPattern_TA_getInstance(), TurkishPattern_TE_getInstance()];
  }
  function get_entries_24() {
    if ($ENTRIES_24 == null)
      $ENTRIES_24 = enumEntries(values_24());
    return $ENTRIES_24;
  }
  var TurkishPattern_entriesInitialized;
  function TurkishPattern_initEntries() {
    if (TurkishPattern_entriesInitialized)
      return Unit_instance;
    TurkishPattern_entriesInitialized = true;
    TurkishPattern_S_CEDILLA_instance = new TurkishPattern('S_CEDILLA', 0, '\u015F', '\u0283', 's-cedilla', listOf(['\u015Fimdi', 'g\xFCne\u015F', '\u015Fehir']));
    TurkishPattern_C_CEDILLA_instance = new TurkishPattern('C_CEDILLA', 1, '\xE7', 't\u0283', 'c-cedilla', listOf(['\xE7ok', '\xE7ocuk', 'ge\xE7mek']));
    TurkishPattern_C_SOFT_instance = new TurkishPattern('C_SOFT', 2, 'c', 'd\u0292', 'c-soft', listOf(['cam', 'cevap', 'gece']));
    TurkishPattern_G_BREVE_instance = new TurkishPattern('G_BREVE', 3, '\u011F', '', 'g-breve', listOf(['da\u011F', 'so\u011Fuk', 'o\u011Flu']));
    TurkishPattern_I_DOTLESS_instance = new TurkishPattern('I_DOTLESS', 4, '\u0131', '\u026F', 'i-dotless', listOf(['s\u0131cak', 'k\u0131\u015F', 'alt\u0131']));
    TurkishPattern_I_DOTTED_instance = new TurkishPattern('I_DOTTED', 5, 'i', 'i', 'i-dotted', listOf(['bir', 'gitmek', 'ilk']));
    TurkishPattern_O_UMLAUT_instance = new TurkishPattern('O_UMLAUT', 6, '\xF6', '\xF8', 'o-umlaut', listOf(['g\xF6z', 'k\xF6y', 's\xF6ylemek']));
    TurkishPattern_U_UMLAUT_instance = new TurkishPattern('U_UMLAUT', 7, '\xFC', 'y', 'u-umlaut', listOf(['g\xFCn', '\xFC\xE7', 'g\xFCzel']));
    TurkishPattern_SH_instance = new TurkishPattern('SH', 8, 'sh', '\u0283', 'sh', listOf(['\u015Fu', '\u015Feker']));
    TurkishPattern_LIK_instance = new TurkishPattern('LIK', 9, 'lik', 'lik', 'lik', listOf(['g\xFCzellik', 'mutluluk']), PatternPosition_END_getInstance());
    TurkishPattern_LUK_instance = new TurkishPattern('LUK', 10, 'luk', 'luk', 'luk', listOf(['\xE7okluk', 'so\u011Fukluk']), PatternPosition_END_getInstance());
    TurkishPattern_LIK_I_instance = new TurkishPattern('LIK_I', 11, 'l\u0131k', 'l\u026Fk', 'lik-i', listOf(['s\u0131cakl\u0131k', 'a\xE7\u0131kl\u0131k']), PatternPosition_END_getInstance());
    TurkishPattern_CI_instance = new TurkishPattern('CI', 12, 'ci', 'd\u0292i', 'ci', listOf(['\xE7ayc\u0131', 'gazeteci']), PatternPosition_END_getInstance());
    TurkishPattern_CU_instance = new TurkishPattern('CU', 13, 'cu', 'd\u0292u', 'cu', listOf(['yolcu', 'kahveci']), PatternPosition_END_getInstance());
    TurkishPattern_CI_I_instance = new TurkishPattern('CI_I', 14, 'c\u0131', 'd\u0292\u026F', 'ci-i', listOf(['bal\u0131k\xE7\u0131', 'kap\u0131c\u0131']), PatternPosition_END_getInstance());
    TurkishPattern_LAR_instance = new TurkishPattern('LAR', 15, 'lar', 'la\u027E', 'lar', listOf(['evler', 'adamlar']), PatternPosition_END_getInstance());
    TurkishPattern_LER_instance = new TurkishPattern('LER', 16, 'ler', 'le\u027E', 'ler', listOf(['eller', 'g\xF6zler']), PatternPosition_END_getInstance());
    TurkishPattern_DA_instance = new TurkishPattern('DA', 17, 'da', 'da', 'da', listOf(['evde', 'okulda']), PatternPosition_END_getInstance());
    TurkishPattern_DE_instance = new TurkishPattern('DE', 18, 'de', 'de', 'de', listOf(['elde', 'evde']), PatternPosition_END_getInstance());
    TurkishPattern_TA_instance = new TurkishPattern('TA', 19, 'ta', 'ta', 'ta', listOf_0('sokakta'), PatternPosition_END_getInstance());
    TurkishPattern_TE_instance = new TurkishPattern('TE', 20, 'te', 'te', 'te', listOf_0('\xFCstte'), PatternPosition_END_getInstance());
    Companion_getInstance_25();
  }
  var $ENTRIES_24;
  function TurkishPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.ve_1 = pattern;
    this.we_1 = ipa;
    this.xe_1 = displayName;
    this.ye_1 = examples;
    this.ze_1 = position;
  }
  protoOf(TurkishPattern).ei = function () {
    return this.we_1;
  };
  protoOf(TurkishPattern).fi = function () {
    return this.xe_1;
  };
  protoOf(TurkishPattern).di = function () {
    return this.ve_1;
  };
  function GermanPattern_SCH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_SCH_instance;
  }
  function GermanPattern_TSCH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_TSCH_instance;
  }
  function GermanPattern_CH_ACH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_CH_ACH_instance;
  }
  function GermanPattern_CH_ICH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_CH_ICH_instance;
  }
  function GermanPattern_CHS_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_CHS_instance;
  }
  function GermanPattern_CK_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_CK_instance;
  }
  function GermanPattern_PF_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_PF_instance;
  }
  function GermanPattern_SP_INITIAL_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_SP_INITIAL_instance;
  }
  function GermanPattern_ST_INITIAL_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_ST_INITIAL_instance;
  }
  function GermanPattern_Z_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_Z_instance;
  }
  function GermanPattern_V_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_V_instance;
  }
  function GermanPattern_W_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_W_instance;
  }
  function GermanPattern_S_VOICED_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_S_VOICED_instance;
  }
  function GermanPattern_SS_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_SS_instance;
  }
  function GermanPattern_AE_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_AE_instance;
  }
  function GermanPattern_OE_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_OE_instance;
  }
  function GermanPattern_UE_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_UE_instance;
  }
  function GermanPattern_IE_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_IE_instance;
  }
  function GermanPattern_EI_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_EI_instance;
  }
  function GermanPattern_EU_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_EU_instance;
  }
  function GermanPattern_AEU_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_AEU_instance;
  }
  function GermanPattern_AU_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_AU_instance;
  }
  function GermanPattern_AH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_AH_instance;
  }
  function GermanPattern_EH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_EH_instance;
  }
  function GermanPattern_IH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_IH_instance;
  }
  function GermanPattern_OH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_OH_instance;
  }
  function GermanPattern_UH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_UH_instance;
  }
  function GermanPattern_TION_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_TION_instance;
  }
  function GermanPattern_IG_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_IG_instance;
  }
  function GermanPattern_LICH_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_LICH_instance;
  }
  function GermanPattern_KEIT_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_KEIT_instance;
  }
  function GermanPattern_HEIT_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_HEIT_instance;
  }
  function GermanPattern_SCHAFT_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_SCHAFT_instance;
  }
  function GermanPattern_UNG_getInstance() {
    GermanPattern_initEntries();
    return GermanPattern_UNG_instance;
  }
  function FrenchPattern_EAU_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_EAU_instance;
  }
  function FrenchPattern_AU_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_AU_instance;
  }
  function FrenchPattern_OI_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_OI_instance;
  }
  function FrenchPattern_OU_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_OU_instance;
  }
  function FrenchPattern_EU_CLOSED_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_EU_CLOSED_instance;
  }
  function FrenchPattern_EU_OPEN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_EU_OPEN_instance;
  }
  function FrenchPattern_OEU_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_OEU_instance;
  }
  function FrenchPattern_AN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_AN_instance;
  }
  function FrenchPattern_EN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_EN_instance;
  }
  function FrenchPattern_AIN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_AIN_instance;
  }
  function FrenchPattern_EIN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_EIN_instance;
  }
  function FrenchPattern_IN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_IN_instance;
  }
  function FrenchPattern_ON_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_ON_instance;
  }
  function FrenchPattern_UN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_UN_instance;
  }
  function FrenchPattern_CH_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_CH_instance;
  }
  function FrenchPattern_GN_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_GN_instance;
  }
  function FrenchPattern_QU_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_QU_instance;
  }
  function FrenchPattern_PH_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_PH_instance;
  }
  function FrenchPattern_C_SOFT_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_C_SOFT_instance;
  }
  function FrenchPattern_G_SOFT_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_G_SOFT_instance;
  }
  function FrenchPattern_ILL_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_ILL_instance;
  }
  function FrenchPattern_AIL_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_AIL_instance;
  }
  function FrenchPattern_EIL_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_EIL_instance;
  }
  function FrenchPattern_H_SILENT_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_H_SILENT_instance;
  }
  function FrenchPattern_ENT_SILENT_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_ENT_SILENT_instance;
  }
  function FrenchPattern_TION_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_TION_instance;
  }
  function FrenchPattern_SION_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_SION_instance;
  }
  function FrenchPattern_EUR_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_EUR_instance;
  }
  function FrenchPattern_MENT_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_MENT_instance;
  }
  function FrenchPattern_IQUE_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_IQUE_instance;
  }
  function FrenchPattern_OIRE_getInstance() {
    FrenchPattern_initEntries();
    return FrenchPattern_OIRE_instance;
  }
  function SpanishPattern_LL_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_LL_instance;
  }
  function SpanishPattern_LL_YEISMO_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_LL_YEISMO_instance;
  }
  function SpanishPattern_RR_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_RR_instance;
  }
  function SpanishPattern_CH_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_CH_instance;
  }
  function SpanishPattern_NY_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_NY_instance;
  }
  function SpanishPattern_C_THETA_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_C_THETA_instance;
  }
  function SpanishPattern_Z_THETA_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_Z_THETA_instance;
  }
  function SpanishPattern_C_SESEO_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_C_SESEO_instance;
  }
  function SpanishPattern_Z_SESEO_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_Z_SESEO_instance;
  }
  function SpanishPattern_J_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_J_instance;
  }
  function SpanishPattern_G_SOFT_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_G_SOFT_instance;
  }
  function SpanishPattern_GUE_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_GUE_instance;
  }
  function SpanishPattern_GUI_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_GUI_instance;
  }
  function SpanishPattern_GUE_UMLAUT_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_GUE_UMLAUT_instance;
  }
  function SpanishPattern_GUI_UMLAUT_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_GUI_UMLAUT_instance;
  }
  function SpanishPattern_QUE_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_QUE_instance;
  }
  function SpanishPattern_QUI_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_QUI_instance;
  }
  function SpanishPattern_H_SILENT_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_H_SILENT_instance;
  }
  function SpanishPattern_Y_VOWEL_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_Y_VOWEL_instance;
  }
  function SpanishPattern_CION_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_CION_instance;
  }
  function SpanishPattern_DAD_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_DAD_instance;
  }
  function SpanishPattern_MENTE_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_MENTE_instance;
  }
  function SpanishPattern_ERO_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_ERO_instance;
  }
  function SpanishPattern_ERA_getInstance() {
    SpanishPattern_initEntries();
    return SpanishPattern_ERA_instance;
  }
  function ItalianPattern_SCE_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_SCE_instance;
  }
  function ItalianPattern_SCI_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_SCI_instance;
  }
  function ItalianPattern_SCIA_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_SCIA_instance;
  }
  function ItalianPattern_SCIO_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_SCIO_instance;
  }
  function ItalianPattern_SCIU_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_SCIU_instance;
  }
  function ItalianPattern_SCH_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_SCH_instance;
  }
  function ItalianPattern_GLI_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GLI_instance;
  }
  function ItalianPattern_GN_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GN_instance;
  }
  function ItalianPattern_CE_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_CE_instance;
  }
  function ItalianPattern_CI_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_CI_instance;
  }
  function ItalianPattern_CIA_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_CIA_instance;
  }
  function ItalianPattern_CIO_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_CIO_instance;
  }
  function ItalianPattern_CIU_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_CIU_instance;
  }
  function ItalianPattern_CH_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_CH_instance;
  }
  function ItalianPattern_GE_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GE_instance;
  }
  function ItalianPattern_GI_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GI_instance;
  }
  function ItalianPattern_GIA_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GIA_instance;
  }
  function ItalianPattern_GIO_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GIO_instance;
  }
  function ItalianPattern_GIU_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GIU_instance;
  }
  function ItalianPattern_GH_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_GH_instance;
  }
  function ItalianPattern_QU_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_QU_instance;
  }
  function ItalianPattern_Z_TS_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_Z_TS_instance;
  }
  function ItalianPattern_Z_DZ_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_Z_DZ_instance;
  }
  function ItalianPattern_LL_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_LL_instance;
  }
  function ItalianPattern_NN_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_NN_instance;
  }
  function ItalianPattern_TT_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_TT_instance;
  }
  function ItalianPattern_PP_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_PP_instance;
  }
  function ItalianPattern_ZIONE_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_ZIONE_instance;
  }
  function ItalianPattern_MENTE_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_MENTE_instance;
  }
  function ItalianPattern_EZZA_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_EZZA_instance;
  }
  function ItalianPattern_ERIA_getInstance() {
    ItalianPattern_initEntries();
    return ItalianPattern_ERIA_instance;
  }
  function PortuguesePattern_LH_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_LH_instance;
  }
  function PortuguesePattern_NH_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_NH_instance;
  }
  function PortuguesePattern_CH_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_CH_instance;
  }
  function PortuguesePattern_X_SH_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_X_SH_instance;
  }
  function PortuguesePattern_X_KS_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_X_KS_instance;
  }
  function PortuguesePattern_X_Z_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_X_Z_instance;
  }
  function PortuguesePattern_X_S_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_X_S_instance;
  }
  function PortuguesePattern_A_TIL_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_A_TIL_instance;
  }
  function PortuguesePattern_O_TIL_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_O_TIL_instance;
  }
  function PortuguesePattern_AO_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_AO_instance;
  }
  function PortuguesePattern_OES_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_OES_instance;
  }
  function PortuguesePattern_AES_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_AES_instance;
  }
  function PortuguesePattern_RR_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_RR_instance;
  }
  function PortuguesePattern_R_INITIAL_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_R_INITIAL_instance;
  }
  function PortuguesePattern_SS_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_SS_instance;
  }
  function PortuguesePattern_C_CEDILLA_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_C_CEDILLA_instance;
  }
  function PortuguesePattern_QUE_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_QUE_instance;
  }
  function PortuguesePattern_QUI_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_QUI_instance;
  }
  function PortuguesePattern_GUE_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_GUE_instance;
  }
  function PortuguesePattern_GUI_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_GUI_instance;
  }
  function PortuguesePattern_CAO_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_CAO_instance;
  }
  function PortuguesePattern_DADE_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_DADE_instance;
  }
  function PortuguesePattern_MENTE_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_MENTE_instance;
  }
  function PortuguesePattern_AGEM_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_AGEM_instance;
  }
  function PortuguesePattern_EIRO_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_EIRO_instance;
  }
  function PortuguesePattern_EIRA_getInstance() {
    PortuguesePattern_initEntries();
    return PortuguesePattern_EIRA_instance;
  }
  function DutchPattern_G_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_G_instance;
  }
  function DutchPattern_CH_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_CH_instance;
  }
  function DutchPattern_SCH_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_SCH_instance;
  }
  function DutchPattern_IJ_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_IJ_instance;
  }
  function DutchPattern_EI_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_EI_instance;
  }
  function DutchPattern_UI_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_UI_instance;
  }
  function DutchPattern_EU_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_EU_instance;
  }
  function DutchPattern_OE_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_OE_instance;
  }
  function DutchPattern_OU_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_OU_instance;
  }
  function DutchPattern_AU_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_AU_instance;
  }
  function DutchPattern_AA_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_AA_instance;
  }
  function DutchPattern_EE_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_EE_instance;
  }
  function DutchPattern_OO_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_OO_instance;
  }
  function DutchPattern_UU_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_UU_instance;
  }
  function DutchPattern_IE_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_IE_instance;
  }
  function DutchPattern_W_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_W_instance;
  }
  function DutchPattern_V_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_V_instance;
  }
  function DutchPattern_LIJK_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_LIJK_instance;
  }
  function DutchPattern_HEID_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_HEID_instance;
  }
  function DutchPattern_ISCH_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_ISCH_instance;
  }
  function DutchPattern_TIE_getInstance() {
    DutchPattern_initEntries();
    return DutchPattern_TIE_instance;
  }
  function PolishPattern_SZ_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_SZ_instance;
  }
  function PolishPattern_RZ_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_RZ_instance;
  }
  function PolishPattern_CZ_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_CZ_instance;
  }
  function PolishPattern_DZ_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_DZ_instance;
  }
  function PolishPattern_DZ_SOFT_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_DZ_SOFT_instance;
  }
  function PolishPattern_DZ_RETRO_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_DZ_RETRO_instance;
  }
  function PolishPattern_S_SOFT_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_S_SOFT_instance;
  }
  function PolishPattern_Z_SOFT_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_Z_SOFT_instance;
  }
  function PolishPattern_C_SOFT_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_C_SOFT_instance;
  }
  function PolishPattern_N_SOFT_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_N_SOFT_instance;
  }
  function PolishPattern_L_STROKE_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_L_STROKE_instance;
  }
  function PolishPattern_A_OGONEK_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_A_OGONEK_instance;
  }
  function PolishPattern_E_OGONEK_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_E_OGONEK_instance;
  }
  function PolishPattern_O_ACUTE_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_O_ACUTE_instance;
  }
  function PolishPattern_CH_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_CH_instance;
  }
  function PolishPattern_CI_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_CI_instance;
  }
  function PolishPattern_SI_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_SI_instance;
  }
  function PolishPattern_ZI_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_ZI_instance;
  }
  function PolishPattern_NI_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_NI_instance;
  }
  function PolishPattern_OWOSC_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_OWOSC_instance;
  }
  function PolishPattern_ACJA_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_ACJA_instance;
  }
  function PolishPattern_SKI_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_SKI_instance;
  }
  function PolishPattern_OWSKI_getInstance() {
    PolishPattern_initEntries();
    return PolishPattern_OWSKI_instance;
  }
  function TurkishPattern_S_CEDILLA_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_S_CEDILLA_instance;
  }
  function TurkishPattern_C_CEDILLA_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_C_CEDILLA_instance;
  }
  function TurkishPattern_C_SOFT_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_C_SOFT_instance;
  }
  function TurkishPattern_G_BREVE_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_G_BREVE_instance;
  }
  function TurkishPattern_I_DOTLESS_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_I_DOTLESS_instance;
  }
  function TurkishPattern_I_DOTTED_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_I_DOTTED_instance;
  }
  function TurkishPattern_O_UMLAUT_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_O_UMLAUT_instance;
  }
  function TurkishPattern_U_UMLAUT_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_U_UMLAUT_instance;
  }
  function TurkishPattern_SH_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_SH_instance;
  }
  function TurkishPattern_LIK_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_LIK_instance;
  }
  function TurkishPattern_LUK_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_LUK_instance;
  }
  function TurkishPattern_LIK_I_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_LIK_I_instance;
  }
  function TurkishPattern_CI_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_CI_instance;
  }
  function TurkishPattern_CU_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_CU_instance;
  }
  function TurkishPattern_CI_I_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_CI_I_instance;
  }
  function TurkishPattern_LAR_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_LAR_instance;
  }
  function TurkishPattern_LER_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_LER_instance;
  }
  function TurkishPattern_DA_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_DA_instance;
  }
  function TurkishPattern_DE_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_DE_instance;
  }
  function TurkishPattern_TA_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_TA_instance;
  }
  function TurkishPattern_TE_getInstance() {
    TurkishPattern_initEntries();
    return TurkishPattern_TE_instance;
  }
  function sam$kotlin_Comparator$0_8(function_0) {
    this.zt_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_8).x8 = function (a, b) {
    return this.zt_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_8).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_8).e2 = function () {
    return this.zt_1;
  };
  protoOf(sam$kotlin_Comparator$0_8).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_8).hashCode = function () {
    return hashCode(this.e2());
  };
  function SwedishPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.fg_1.length;
    var tmp$ret$1 = a.fg_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var SwedishPattern_SJ_instance;
  var SwedishPattern_SKJ_instance;
  var SwedishPattern_STJ_instance;
  var SwedishPattern_SK_SOFT_instance;
  var SwedishPattern_TJ_instance;
  var SwedishPattern_KJ_instance;
  var SwedishPattern_K_SOFT_instance;
  var SwedishPattern_RS_instance;
  var SwedishPattern_RD_instance;
  var SwedishPattern_RN_instance;
  var SwedishPattern_RT_instance;
  var SwedishPattern_RL_instance;
  var SwedishPattern_A_RING_instance;
  var SwedishPattern_A_UMLAUT_instance;
  var SwedishPattern_O_UMLAUT_instance;
  var SwedishPattern_Y_instance;
  var SwedishPattern_U_instance;
  var SwedishPattern_NG_instance;
  var SwedishPattern_NK_instance;
  var SwedishPattern_D_SILENT_instance;
  var SwedishPattern_G_SILENT_instance;
  var SwedishPattern_TION_instance;
  var SwedishPattern_IGHET_instance;
  var SwedishPattern_LIGEN_instance;
  function Companion_26() {
    Companion_instance_26 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_25();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.fg_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.bg_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_25();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.gg_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.cg_1 = destination_0;
  }
  protoOf(Companion_26).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_25();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.jg_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = SwedishPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_8(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.fg_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_26;
  function Companion_getInstance_26() {
    SwedishPattern_initEntries();
    if (Companion_instance_26 == null)
      new Companion_26();
    return Companion_instance_26;
  }
  function values_25() {
    return [SwedishPattern_SJ_getInstance(), SwedishPattern_SKJ_getInstance(), SwedishPattern_STJ_getInstance(), SwedishPattern_SK_SOFT_getInstance(), SwedishPattern_TJ_getInstance(), SwedishPattern_KJ_getInstance(), SwedishPattern_K_SOFT_getInstance(), SwedishPattern_RS_getInstance(), SwedishPattern_RD_getInstance(), SwedishPattern_RN_getInstance(), SwedishPattern_RT_getInstance(), SwedishPattern_RL_getInstance(), SwedishPattern_A_RING_getInstance(), SwedishPattern_A_UMLAUT_getInstance(), SwedishPattern_O_UMLAUT_getInstance(), SwedishPattern_Y_getInstance(), SwedishPattern_U_getInstance(), SwedishPattern_NG_getInstance(), SwedishPattern_NK_getInstance(), SwedishPattern_D_SILENT_getInstance(), SwedishPattern_G_SILENT_getInstance(), SwedishPattern_TION_getInstance(), SwedishPattern_IGHET_getInstance(), SwedishPattern_LIGEN_getInstance()];
  }
  function get_entries_25() {
    if ($ENTRIES_25 == null)
      $ENTRIES_25 = enumEntries(values_25());
    return $ENTRIES_25;
  }
  var SwedishPattern_entriesInitialized;
  function SwedishPattern_initEntries() {
    if (SwedishPattern_entriesInitialized)
      return Unit_instance;
    SwedishPattern_entriesInitialized = true;
    SwedishPattern_SJ_instance = new SwedishPattern('SJ', 0, 'sj', '\u0267', 'sj', listOf(['sj\xF6', 'sjunga', 'sj\xE4lv']));
    SwedishPattern_SKJ_instance = new SwedishPattern('SKJ', 1, 'skj', '\u0267', 'skj', listOf(['skjorta', 'skjuta']));
    SwedishPattern_STJ_instance = new SwedishPattern('STJ', 2, 'stj', '\u0267', 'stj', listOf(['stj\xE4rna', 'stj\xE4la']));
    SwedishPattern_SK_SOFT_instance = new SwedishPattern('SK_SOFT', 3, 'sk', '\u0267', 'sk-soft', listOf(['sked', 'skina', 'sk\xF6n']));
    SwedishPattern_TJ_instance = new SwedishPattern('TJ', 4, 'tj', '\u0255', 'tj', listOf(['tjugo', 'tjock', 'tj\xE4na']));
    SwedishPattern_KJ_instance = new SwedishPattern('KJ', 5, 'kj', '\u0255', 'kj', listOf(['kjol', 'kjortel']));
    SwedishPattern_K_SOFT_instance = new SwedishPattern('K_SOFT', 6, 'k', '\u0255', 'k-soft', listOf(['k\xF6pa', 'k\xE4r', 'k\xF6']));
    SwedishPattern_RS_instance = new SwedishPattern('RS', 7, 'rs', '\u0282', 'rs-retro', listOf(['torsdag', 'kors', 'fors']));
    SwedishPattern_RD_instance = new SwedishPattern('RD', 8, 'rd', '\u0256', 'rd-retro', listOf(['bord', 'ord', 'g\xE5rd']));
    SwedishPattern_RN_instance = new SwedishPattern('RN', 9, 'rn', '\u0273', 'rn-retro', listOf(['barn', 'horn', 'torn']));
    SwedishPattern_RT_instance = new SwedishPattern('RT', 10, 'rt', '\u0288', 'rt-retro', listOf(['kort', 'svart', 'sort']));
    SwedishPattern_RL_instance = new SwedishPattern('RL', 11, 'rl', '\u026D', 'rl-retro', listOf(['Karl', 'p\xE4rla']));
    SwedishPattern_A_RING_instance = new SwedishPattern('A_RING', 12, '\xE5', 'o\u02D0', 'a-ring', listOf(['\xE5r', 'g\xE5', 'br\xE5ka']));
    SwedishPattern_A_UMLAUT_instance = new SwedishPattern('A_UMLAUT', 13, '\xE4', '\u025B\u02D0', 'a-umlaut', listOf(['\xE4ta', 'v\xE4g', 'l\xE4sa']));
    SwedishPattern_O_UMLAUT_instance = new SwedishPattern('O_UMLAUT', 14, '\xF6', '\xF8\u02D0', 'o-umlaut', listOf(['\xF6ra', 'r\xF6d', 'g\xF6ra']));
    SwedishPattern_Y_instance = new SwedishPattern('Y', 15, 'y', 'y\u02D0', 'y', listOf(['by', 'ny', 'syster']));
    SwedishPattern_U_instance = new SwedishPattern('U', 16, 'u', '\u0289\u02D0', 'u', listOf(['du', 'hus', 'bus']));
    SwedishPattern_NG_instance = new SwedishPattern('NG', 17, 'ng', '\u014B', 'ng', listOf(['kung', 'l\xE5ng', 's\xE5ng']));
    SwedishPattern_NK_instance = new SwedishPattern('NK', 18, 'nk', '\u014Bk', 'nk', listOf(['t\xE4nka', 'dricka']));
    SwedishPattern_D_SILENT_instance = new SwedishPattern('D_SILENT', 19, 'd', '', 'd-silent', listOf(['hj\xE4l', 'fj\xE4rd', 'g\xE5rd']), PatternPosition_END_getInstance());
    SwedishPattern_G_SILENT_instance = new SwedishPattern('G_SILENT', 20, 'g', '', 'g-silent', listOf(['dag', 'v\xE4g', 'mig']), PatternPosition_END_getInstance());
    SwedishPattern_TION_instance = new SwedishPattern('TION', 21, 'tion', '\u0283u\u02D0n', 'tion', listOf(['nation', 'station', 'information']), PatternPosition_END_getInstance());
    SwedishPattern_IGHET_instance = new SwedishPattern('IGHET', 22, 'ighet', 'i\u02D0h\u025Bt', 'ighet', listOf(['m\xF6jlighet', 'sv\xE5righet']), PatternPosition_END_getInstance());
    SwedishPattern_LIGEN_instance = new SwedishPattern('LIGEN', 23, 'ligen', 'li\u02D0\u025Bn', 'ligen', listOf(['verkligen', 'egentligen']), PatternPosition_END_getInstance());
    Companion_getInstance_26();
  }
  var $ENTRIES_25;
  function SwedishPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.fg_1 = pattern;
    this.gg_1 = ipa;
    this.hg_1 = displayName;
    this.ig_1 = examples;
    this.jg_1 = position;
  }
  protoOf(SwedishPattern).ei = function () {
    return this.gg_1;
  };
  protoOf(SwedishPattern).fi = function () {
    return this.hg_1;
  };
  protoOf(SwedishPattern).di = function () {
    return this.fg_1;
  };
  function sam$kotlin_Comparator$0_9(function_0) {
    this.au_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_9).x8 = function (a, b) {
    return this.au_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_9).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_9).e2 = function () {
    return this.au_1;
  };
  protoOf(sam$kotlin_Comparator$0_9).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_9).hashCode = function () {
    return hashCode(this.e2());
  };
  function NorwegianPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.wf_1.length;
    var tmp$ret$1 = a.wf_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var NorwegianPattern_SJ_instance;
  var NorwegianPattern_SKJ_instance;
  var NorwegianPattern_KJ_instance;
  var NorwegianPattern_HJ_instance;
  var NorwegianPattern_RS_instance;
  var NorwegianPattern_RD_instance;
  var NorwegianPattern_RN_instance;
  var NorwegianPattern_RT_instance;
  var NorwegianPattern_RL_instance;
  var NorwegianPattern_A_RING_instance;
  var NorwegianPattern_AE_instance;
  var NorwegianPattern_O_SLASH_instance;
  var NorwegianPattern_Y_instance;
  var NorwegianPattern_EI_instance;
  var NorwegianPattern_AI_instance;
  var NorwegianPattern_NG_instance;
  var NorwegianPattern_SJON_instance;
  var NorwegianPattern_HET_instance;
  var NorwegianPattern_ELSE_instance;
  function Companion_27() {
    Companion_instance_27 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_26();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.wf_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.sf_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_26();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.xf_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.tf_1 = destination_0;
  }
  protoOf(Companion_27).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_26();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.ag_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = NorwegianPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_9(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.wf_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_27;
  function Companion_getInstance_27() {
    NorwegianPattern_initEntries();
    if (Companion_instance_27 == null)
      new Companion_27();
    return Companion_instance_27;
  }
  function values_26() {
    return [NorwegianPattern_SJ_getInstance(), NorwegianPattern_SKJ_getInstance(), NorwegianPattern_KJ_getInstance(), NorwegianPattern_HJ_getInstance(), NorwegianPattern_RS_getInstance(), NorwegianPattern_RD_getInstance(), NorwegianPattern_RN_getInstance(), NorwegianPattern_RT_getInstance(), NorwegianPattern_RL_getInstance(), NorwegianPattern_A_RING_getInstance(), NorwegianPattern_AE_getInstance(), NorwegianPattern_O_SLASH_getInstance(), NorwegianPattern_Y_getInstance(), NorwegianPattern_EI_getInstance(), NorwegianPattern_AI_getInstance(), NorwegianPattern_NG_getInstance(), NorwegianPattern_SJON_getInstance(), NorwegianPattern_HET_getInstance(), NorwegianPattern_ELSE_getInstance()];
  }
  function get_entries_26() {
    if ($ENTRIES_26 == null)
      $ENTRIES_26 = enumEntries(values_26());
    return $ENTRIES_26;
  }
  var NorwegianPattern_entriesInitialized;
  function NorwegianPattern_initEntries() {
    if (NorwegianPattern_entriesInitialized)
      return Unit_instance;
    NorwegianPattern_entriesInitialized = true;
    NorwegianPattern_SJ_instance = new NorwegianPattern('SJ', 0, 'sj', '\u0283', 'sj', listOf(['sj\xF8', 'sjel', 'sjelden']));
    NorwegianPattern_SKJ_instance = new NorwegianPattern('SKJ', 1, 'skj', '\u0283', 'skj', listOf(['skjorte', 'skj\xF8nn', 'skj\xE6re']));
    NorwegianPattern_KJ_instance = new NorwegianPattern('KJ', 2, 'kj', '\xE7', 'kj', listOf(['kj\xF8pe', 'kjenne', 'kj\xE6re']));
    NorwegianPattern_HJ_instance = new NorwegianPattern('HJ', 3, 'hj', 'j', 'hj', listOf(['hjerte', 'hjelpe', 'hjemme']));
    NorwegianPattern_RS_instance = new NorwegianPattern('RS', 4, 'rs', '\u0282', 'rs-retro', listOf(['torsk', 'norsk', 'f\xF8rst']));
    NorwegianPattern_RD_instance = new NorwegianPattern('RD', 5, 'rd', '\u0256', 'rd-retro', listOf(['bord', 'ord', 'jord']));
    NorwegianPattern_RN_instance = new NorwegianPattern('RN', 6, 'rn', '\u0273', 'rn-retro', listOf(['barn', 'horn']));
    NorwegianPattern_RT_instance = new NorwegianPattern('RT', 7, 'rt', '\u0288', 'rt-retro', listOf(['kort', 'svart']));
    NorwegianPattern_RL_instance = new NorwegianPattern('RL', 8, 'rl', '\u026D', 'rl-retro', listOf(['perle', 'j\xE6rlig']));
    NorwegianPattern_A_RING_instance = new NorwegianPattern('A_RING', 9, '\xE5', 'o\u02D0', 'a-ring', listOf(['\xE5r', 'g\xE5', 'b\xE5t']));
    NorwegianPattern_AE_instance = new NorwegianPattern('AE', 10, '\xE6', '\xE6\u02D0', 'ae', listOf(['v\xE6re', 'l\xE6rer', 'd\xE6ven']));
    NorwegianPattern_O_SLASH_instance = new NorwegianPattern('O_SLASH', 11, '\xF8', '\xF8\u02D0', 'o-slash', listOf(['\xF8ye', 'f\xF8r', 's\xF8ster']));
    NorwegianPattern_Y_instance = new NorwegianPattern('Y', 12, 'y', 'y\u02D0', 'y', listOf(['by', 'ny', 'syk']));
    NorwegianPattern_EI_instance = new NorwegianPattern('EI', 13, 'ei', '\xE6i', 'ei', listOf(['stein', 'rein', 'vei']));
    NorwegianPattern_AI_instance = new NorwegianPattern('AI', 14, 'ai', 'ai', 'ai', listOf(['hai', 'mai']));
    NorwegianPattern_NG_instance = new NorwegianPattern('NG', 15, 'ng', '\u014B', 'ng', listOf(['lang', 'ring', 'sang']));
    NorwegianPattern_SJON_instance = new NorwegianPattern('SJON', 16, 'sjon', '\u0283u\u02D0n', 'sjon', listOf(['nasjon', 'stasjon']), PatternPosition_END_getInstance());
    NorwegianPattern_HET_instance = new NorwegianPattern('HET', 17, 'het', 'he\u02D0t', 'het', listOf(['mulighet', 'sikkerhet']), PatternPosition_END_getInstance());
    NorwegianPattern_ELSE_instance = new NorwegianPattern('ELSE', 18, 'else', '\u025Bls\u0259', 'else', listOf(['forst\xE5else', 'f\xF8lelse']), PatternPosition_END_getInstance());
    Companion_getInstance_27();
  }
  var $ENTRIES_26;
  function NorwegianPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.wf_1 = pattern;
    this.xf_1 = ipa;
    this.yf_1 = displayName;
    this.zf_1 = examples;
    this.ag_1 = position;
  }
  protoOf(NorwegianPattern).ei = function () {
    return this.xf_1;
  };
  protoOf(NorwegianPattern).fi = function () {
    return this.yf_1;
  };
  protoOf(NorwegianPattern).di = function () {
    return this.wf_1;
  };
  function sam$kotlin_Comparator$0_10(function_0) {
    this.bu_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_10).x8 = function (a, b) {
    return this.bu_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_10).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_10).e2 = function () {
    return this.bu_1;
  };
  protoOf(sam$kotlin_Comparator$0_10).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_10).hashCode = function () {
    return hashCode(this.e2());
  };
  function DanishPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.ef_1.length;
    var tmp$ret$1 = a.ef_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var DanishPattern_D_SOFT_instance;
  var DanishPattern_G_SILENT_instance;
  var DanishPattern_D_SILENT_instance;
  var DanishPattern_A_RING_instance;
  var DanishPattern_AE_instance;
  var DanishPattern_O_SLASH_instance;
  var DanishPattern_Y_instance;
  var DanishPattern_EJ_instance;
  var DanishPattern_AJ_instance;
  var DanishPattern_OJ_instance;
  var DanishPattern_NG_instance;
  var DanishPattern_HV_instance;
  var DanishPattern_HJ_instance;
  var DanishPattern_TION_instance;
  var DanishPattern_ELSE_instance;
  var DanishPattern_HED_instance;
  var DanishPattern_IGHED_instance;
  function Companion_28() {
    Companion_instance_28 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_27();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.ef_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.af_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_27();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.ff_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.bf_1 = destination_0;
  }
  protoOf(Companion_28).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_27();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.if_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = DanishPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_10(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.ef_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_28;
  function Companion_getInstance_28() {
    DanishPattern_initEntries();
    if (Companion_instance_28 == null)
      new Companion_28();
    return Companion_instance_28;
  }
  function values_27() {
    return [DanishPattern_D_SOFT_getInstance(), DanishPattern_G_SILENT_getInstance(), DanishPattern_D_SILENT_getInstance(), DanishPattern_A_RING_getInstance(), DanishPattern_AE_getInstance(), DanishPattern_O_SLASH_getInstance(), DanishPattern_Y_getInstance(), DanishPattern_EJ_getInstance(), DanishPattern_AJ_getInstance(), DanishPattern_OJ_getInstance(), DanishPattern_NG_getInstance(), DanishPattern_HV_getInstance(), DanishPattern_HJ_getInstance(), DanishPattern_TION_getInstance(), DanishPattern_ELSE_getInstance(), DanishPattern_HED_getInstance(), DanishPattern_IGHED_getInstance()];
  }
  function get_entries_27() {
    if ($ENTRIES_27 == null)
      $ENTRIES_27 = enumEntries(values_27());
    return $ENTRIES_27;
  }
  var DanishPattern_entriesInitialized;
  function DanishPattern_initEntries() {
    if (DanishPattern_entriesInitialized)
      return Unit_instance;
    DanishPattern_entriesInitialized = true;
    DanishPattern_D_SOFT_instance = new DanishPattern('D_SOFT', 0, 'd', '\xF0', 'd-soft', listOf(['mad', 'side', 'gade']));
    DanishPattern_G_SILENT_instance = new DanishPattern('G_SILENT', 1, 'g', '', 'g-silent', listOf(['dag', 'sige', 'lige']), PatternPosition_END_getInstance());
    DanishPattern_D_SILENT_instance = new DanishPattern('D_SILENT', 2, 'd', '', 'd-silent', listOf(['mand', 'land', 'vand']), PatternPosition_END_getInstance());
    DanishPattern_A_RING_instance = new DanishPattern('A_RING', 3, '\xE5', '\u0254\u02D0', 'a-ring', listOf(['\xE5r', 'g\xE5', 'b\xE5de']));
    DanishPattern_AE_instance = new DanishPattern('AE', 4, '\xE6', '\u025B\u02D0', 'ae', listOf(['v\xE6re', 'l\xE6se', 'm\xE6nd']));
    DanishPattern_O_SLASH_instance = new DanishPattern('O_SLASH', 5, '\xF8', '\xF8\u02D0', 'o-slash', listOf(['f\xF8r', 'gr\xF8n', 'h\xF8re']));
    DanishPattern_Y_instance = new DanishPattern('Y', 6, 'y', 'y\u02D0', 'y', listOf(['ny', 'by', 'system']));
    DanishPattern_EJ_instance = new DanishPattern('EJ', 7, 'ej', 'ai', 'ej', listOf(['hej', 'vej', 'nej']));
    DanishPattern_AJ_instance = new DanishPattern('AJ', 8, 'aj', 'ai', 'aj', listOf_0('maj'));
    DanishPattern_OJ_instance = new DanishPattern('OJ', 9, 'oj', '\u0254i', 'oj', listOf(['oj', 'dr\xF8j']));
    DanishPattern_NG_instance = new DanishPattern('NG', 10, 'ng', '\u014B', 'ng', listOf(['lang', 'sang', 'ting']));
    DanishPattern_HV_instance = new DanishPattern('HV', 11, 'hv', 'v', 'hv', listOf(['hvad', 'hvor', 'hvem']), PatternPosition_START_getInstance());
    DanishPattern_HJ_instance = new DanishPattern('HJ', 12, 'hj', 'j', 'hj', listOf(['hj\xE6lp', 'hjerte', 'hjem']), PatternPosition_START_getInstance());
    DanishPattern_TION_instance = new DanishPattern('TION', 13, 'tion', '\u0283o\u02D0n', 'tion', listOf(['nation', 'station']), PatternPosition_END_getInstance());
    DanishPattern_ELSE_instance = new DanishPattern('ELSE', 14, 'else', '\u0259ls\u0259', 'else', listOf(['forst\xE5else', 'f\xF8lelse']), PatternPosition_END_getInstance());
    DanishPattern_HED_instance = new DanishPattern('HED', 15, 'hed', 'he\xF0', 'hed', listOf(['mulighed', 'sandhed']), PatternPosition_END_getInstance());
    DanishPattern_IGHED_instance = new DanishPattern('IGHED', 16, 'ighed', 'i\u02D0\u0259\xF0', 'ighed', listOf(['vigtighed', 'rigtighed']), PatternPosition_END_getInstance());
    Companion_getInstance_28();
  }
  var $ENTRIES_27;
  function DanishPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.ef_1 = pattern;
    this.ff_1 = ipa;
    this.gf_1 = displayName;
    this.hf_1 = examples;
    this.if_1 = position;
  }
  protoOf(DanishPattern).ei = function () {
    return this.ff_1;
  };
  protoOf(DanishPattern).fi = function () {
    return this.gf_1;
  };
  protoOf(DanishPattern).di = function () {
    return this.ef_1;
  };
  function sam$kotlin_Comparator$0_11(function_0) {
    this.cu_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_11).x8 = function (a, b) {
    return this.cu_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_11).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_11).e2 = function () {
    return this.cu_1;
  };
  protoOf(sam$kotlin_Comparator$0_11).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_11).hashCode = function () {
    return hashCode(this.e2());
  };
  function FinnishPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.nf_1.length;
    var tmp$ret$1 = a.nf_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var FinnishPattern_KK_instance;
  var FinnishPattern_PP_instance;
  var FinnishPattern_TT_instance;
  var FinnishPattern_SS_instance;
  var FinnishPattern_LL_instance;
  var FinnishPattern_MM_instance;
  var FinnishPattern_NN_instance;
  var FinnishPattern_RR_instance;
  var FinnishPattern_AA_instance;
  var FinnishPattern_EE_instance;
  var FinnishPattern_II_instance;
  var FinnishPattern_OO_instance;
  var FinnishPattern_UU_instance;
  var FinnishPattern_YY_instance;
  var FinnishPattern_AA_UMLAUT_instance;
  var FinnishPattern_OO_UMLAUT_instance;
  var FinnishPattern_A_UMLAUT_instance;
  var FinnishPattern_O_UMLAUT_instance;
  var FinnishPattern_Y_instance;
  var FinnishPattern_NK_instance;
  var FinnishPattern_NG_instance;
  var FinnishPattern_INEN_instance;
  var FinnishPattern_INEN_GEN_instance;
  var FinnishPattern_SSA_instance;
  var FinnishPattern_STA_instance;
  var FinnishPattern_LLE_instance;
  var FinnishPattern_LTA_instance;
  function Companion_29() {
    Companion_instance_29 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_28();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.nf_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.jf_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_28();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.of_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.kf_1 = destination_0;
  }
  protoOf(Companion_29).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_28();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.rf_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = FinnishPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_11(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.nf_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_29;
  function Companion_getInstance_29() {
    FinnishPattern_initEntries();
    if (Companion_instance_29 == null)
      new Companion_29();
    return Companion_instance_29;
  }
  function values_28() {
    return [FinnishPattern_KK_getInstance(), FinnishPattern_PP_getInstance(), FinnishPattern_TT_getInstance(), FinnishPattern_SS_getInstance(), FinnishPattern_LL_getInstance(), FinnishPattern_MM_getInstance(), FinnishPattern_NN_getInstance(), FinnishPattern_RR_getInstance(), FinnishPattern_AA_getInstance(), FinnishPattern_EE_getInstance(), FinnishPattern_II_getInstance(), FinnishPattern_OO_getInstance(), FinnishPattern_UU_getInstance(), FinnishPattern_YY_getInstance(), FinnishPattern_AA_UMLAUT_getInstance(), FinnishPattern_OO_UMLAUT_getInstance(), FinnishPattern_A_UMLAUT_getInstance(), FinnishPattern_O_UMLAUT_getInstance(), FinnishPattern_Y_getInstance(), FinnishPattern_NK_getInstance(), FinnishPattern_NG_getInstance(), FinnishPattern_INEN_getInstance(), FinnishPattern_INEN_GEN_getInstance(), FinnishPattern_SSA_getInstance(), FinnishPattern_STA_getInstance(), FinnishPattern_LLE_getInstance(), FinnishPattern_LTA_getInstance()];
  }
  function get_entries_28() {
    if ($ENTRIES_28 == null)
      $ENTRIES_28 = enumEntries(values_28());
    return $ENTRIES_28;
  }
  var FinnishPattern_entriesInitialized;
  function FinnishPattern_initEntries() {
    if (FinnishPattern_entriesInitialized)
      return Unit_instance;
    FinnishPattern_entriesInitialized = true;
    FinnishPattern_KK_instance = new FinnishPattern('KK', 0, 'kk', 'k\u02D0', 'kk-gem', listOf(['kukka', 'takki', 'pakko']));
    FinnishPattern_PP_instance = new FinnishPattern('PP', 1, 'pp', 'p\u02D0', 'pp-gem', listOf(['kuppi', 'oppia', 'kauppa']));
    FinnishPattern_TT_instance = new FinnishPattern('TT', 2, 'tt', 't\u02D0', 'tt-gem', listOf(['katto', 'letti', 'matto']));
    FinnishPattern_SS_instance = new FinnishPattern('SS', 3, 'ss', 's\u02D0', 'ss-gem', listOf(['kissa', 'missa', 'vessa']));
    FinnishPattern_LL_instance = new FinnishPattern('LL', 4, 'll', 'l\u02D0', 'll-gem', listOf(['kello', 'pallo', 'tulla']));
    FinnishPattern_MM_instance = new FinnishPattern('MM', 5, 'mm', 'm\u02D0', 'mm-gem', listOf(['k\xE4mmen', 'ammatti']));
    FinnishPattern_NN_instance = new FinnishPattern('NN', 6, 'nn', 'n\u02D0', 'nn-gem', listOf(['kunnon', 'menn\xE4', 'onnea']));
    FinnishPattern_RR_instance = new FinnishPattern('RR', 7, 'rr', 'r\u02D0', 'rr-gem', listOf_0('kurraus'));
    FinnishPattern_AA_instance = new FinnishPattern('AA', 8, 'aa', 'a\u02D0', 'aa-long', listOf(['saada', 'kaada', 'maailma']));
    FinnishPattern_EE_instance = new FinnishPattern('EE', 9, 'ee', 'e\u02D0', 'ee-long', listOf(['teen', 'meen', 'peeli']));
    FinnishPattern_II_instance = new FinnishPattern('II', 10, 'ii', 'i\u02D0', 'ii-long', listOf(['siisti', 'viisi', 'kiitos']));
    FinnishPattern_OO_instance = new FinnishPattern('OO', 11, 'oo', 'o\u02D0', 'oo-long', listOf(['koota', 'toomio']));
    FinnishPattern_UU_instance = new FinnishPattern('UU', 12, 'uu', 'u\u02D0', 'uu-long', listOf(['suuri', 'kuuma', 'muutos']));
    FinnishPattern_YY_instance = new FinnishPattern('YY', 13, 'yy', 'y\u02D0', 'yy-long', listOf(['syy', 'hyy']));
    FinnishPattern_AA_UMLAUT_instance = new FinnishPattern('AA_UMLAUT', 14, '\xE4\xE4', '\xE6\u02D0', 'aa-umlaut', listOf(['m\xE4\xE4r\xE4', 'p\xE4\xE4', 's\xE4\xE4']));
    FinnishPattern_OO_UMLAUT_instance = new FinnishPattern('OO_UMLAUT', 15, '\xF6\xF6', '\xF8\u02D0', 'oo-umlaut', listOf(['y\xF6', 'ty\xF6']));
    FinnishPattern_A_UMLAUT_instance = new FinnishPattern('A_UMLAUT', 16, '\xE4', '\xE6', 'a-umlaut', listOf(['\xE4iti', 'k\xE4si', 'p\xE4l\xE4']));
    FinnishPattern_O_UMLAUT_instance = new FinnishPattern('O_UMLAUT', 17, '\xF6', '\xF8', 'o-umlaut', listOf(['ty\xF6', 'p\xF6yt\xE4', 'k\xF6yh\xE4']));
    FinnishPattern_Y_instance = new FinnishPattern('Y', 18, 'y', 'y', 'y', listOf(['syy', 'ty\xF6', 'kyl\xE4']));
    FinnishPattern_NK_instance = new FinnishPattern('NK', 19, 'nk', '\u014Bk', 'nk', listOf(['kenk\xE4', 'pankki', 'vankka']));
    FinnishPattern_NG_instance = new FinnishPattern('NG', 20, 'ng', '\u014B\u02D0', 'ng', listOf(['lanki', 'rengas']));
    FinnishPattern_INEN_instance = new FinnishPattern('INEN', 21, 'inen', 'in\u025Bn', 'inen', listOf(['suomalainen', 'ihminen']), PatternPosition_END_getInstance());
    FinnishPattern_INEN_GEN_instance = new FinnishPattern('INEN_GEN', 22, 'isen', 'is\u025Bn', 'isen-gen', listOf_0('suomalaisen'), PatternPosition_END_getInstance());
    FinnishPattern_SSA_instance = new FinnishPattern('SSA', 23, 'ssa', 's\u02D0a', 'ssa-iness', listOf(['talossa', 'kotissa']), PatternPosition_END_getInstance());
    FinnishPattern_STA_instance = new FinnishPattern('STA', 24, 'sta', 'sta', 'sta-elat', listOf(['talosta', 'kotista']), PatternPosition_END_getInstance());
    FinnishPattern_LLE_instance = new FinnishPattern('LLE', 25, 'lle', 'l\u02D0\u025B', 'lle-allat', listOf(['talolle', 'kotille']), PatternPosition_END_getInstance());
    FinnishPattern_LTA_instance = new FinnishPattern('LTA', 26, 'lta', 'lta', 'lta-ablat', listOf(['talolta', 'kotilta']), PatternPosition_END_getInstance());
    Companion_getInstance_29();
  }
  var $ENTRIES_28;
  function FinnishPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.nf_1 = pattern;
    this.of_1 = ipa;
    this.pf_1 = displayName;
    this.qf_1 = examples;
    this.rf_1 = position;
  }
  protoOf(FinnishPattern).ei = function () {
    return this.of_1;
  };
  protoOf(FinnishPattern).fi = function () {
    return this.pf_1;
  };
  protoOf(FinnishPattern).di = function () {
    return this.nf_1;
  };
  function sam$kotlin_Comparator$0_12(function_0) {
    this.du_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_12).x8 = function (a, b) {
    return this.du_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_12).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_12).e2 = function () {
    return this.du_1;
  };
  protoOf(sam$kotlin_Comparator$0_12).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_12).hashCode = function () {
    return hashCode(this.e2());
  };
  function GreekPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.gh_1.length;
    var tmp$ret$1 = a.gh_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var GreekPattern_AI_instance;
  var GreekPattern_EI_instance;
  var GreekPattern_OI_instance;
  var GreekPattern_UI_instance;
  var GreekPattern_AU_V_instance;
  var GreekPattern_AU_F_instance;
  var GreekPattern_EU_V_instance;
  var GreekPattern_EU_F_instance;
  var GreekPattern_OU_instance;
  var GreekPattern_MP_instance;
  var GreekPattern_MP_MB_instance;
  var GreekPattern_NT_instance;
  var GreekPattern_NT_ND_instance;
  var GreekPattern_GK_instance;
  var GreekPattern_GK_NG_instance;
  var GreekPattern_GG_instance;
  var GreekPattern_GCH_instance;
  var GreekPattern_GX_instance;
  var GreekPattern_THETA_instance;
  var GreekPattern_CHI_instance;
  var GreekPattern_PSI_instance;
  var GreekPattern_XI_instance;
  var GreekPattern_OS_instance;
  var GreekPattern_IS_instance;
  var GreekPattern_A_instance;
  var GreekPattern_I_instance;
  function Companion_30() {
    Companion_instance_30 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_29();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.gh_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.ch_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_29();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.hh_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.dh_1 = destination_0;
  }
  protoOf(Companion_30).yb = function (word) {
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_29();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.kh_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = GreekPattern$Companion$matchEnding$lambda;
    var tmp$ret$3 = new sam$kotlin_Comparator$0_12(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$3);
    var tmp$ret$6;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(word, element_0.gh_1)) {
          tmp$ret$6 = element_0;
          break $l$block;
        }
      }
      tmp$ret$6 = null;
    }
    return tmp$ret$6;
  };
  var Companion_instance_30;
  function Companion_getInstance_30() {
    GreekPattern_initEntries();
    if (Companion_instance_30 == null)
      new Companion_30();
    return Companion_instance_30;
  }
  function values_29() {
    return [GreekPattern_AI_getInstance(), GreekPattern_EI_getInstance(), GreekPattern_OI_getInstance(), GreekPattern_UI_getInstance(), GreekPattern_AU_V_getInstance(), GreekPattern_AU_F_getInstance(), GreekPattern_EU_V_getInstance(), GreekPattern_EU_F_getInstance(), GreekPattern_OU_getInstance(), GreekPattern_MP_getInstance(), GreekPattern_MP_MB_getInstance(), GreekPattern_NT_getInstance(), GreekPattern_NT_ND_getInstance(), GreekPattern_GK_getInstance(), GreekPattern_GK_NG_getInstance(), GreekPattern_GG_getInstance(), GreekPattern_GCH_getInstance(), GreekPattern_GX_getInstance(), GreekPattern_THETA_getInstance(), GreekPattern_CHI_getInstance(), GreekPattern_PSI_getInstance(), GreekPattern_XI_getInstance(), GreekPattern_OS_getInstance(), GreekPattern_IS_getInstance(), GreekPattern_A_getInstance(), GreekPattern_I_getInstance()];
  }
  function get_entries_29() {
    if ($ENTRIES_29 == null)
      $ENTRIES_29 = enumEntries(values_29());
    return $ENTRIES_29;
  }
  var GreekPattern_entriesInitialized;
  function GreekPattern_initEntries() {
    if (GreekPattern_entriesInitialized)
      return Unit_instance;
    GreekPattern_entriesInitialized = true;
    GreekPattern_AI_instance = new GreekPattern('AI', 0, '\u03B1\u03B9', 'e', 'ai', listOf(['\u03BA\u03B1\u03B9', '\u03BC\u03B1\u03B9', '\u03C0\u03B1\u03B9\u03B4\u03AF']));
    GreekPattern_EI_instance = new GreekPattern('EI', 1, '\u03B5\u03B9', 'i', 'ei', listOf(['\u03B5\u03AF\u03BD\u03B1\u03B9', '\u03B8\u03B5\u03AF\u03BF', '\u03BA\u03B5\u03AF\u03BC\u03B5\u03BD\u03BF']));
    GreekPattern_OI_instance = new GreekPattern('OI', 2, '\u03BF\u03B9', 'i', 'oi', listOf(['\u03BF\u03B9', '\u03C0\u03BF\u03B9\u03BF\u03C2', '\u03BC\u03BF\u03AF\u03C1\u03B1']));
    GreekPattern_UI_instance = new GreekPattern('UI', 3, '\u03C5\u03B9', 'i', 'ui', listOf_0('\u03C5\u03B9\u03CC\u03C2'));
    GreekPattern_AU_V_instance = new GreekPattern('AU_V', 4, '\u03B1\u03C5', 'av', 'au-v', listOf(['\u03B1\u03C5\u03C4\u03CC\u03C2', '\u03B1\u03CD\u03C1\u03B9\u03BF']));
    GreekPattern_AU_F_instance = new GreekPattern('AU_F', 5, '\u03B1\u03C5', 'af', 'au-f', listOf(['\u03B1\u03C5\u03C4\u03AF', '\u03C0\u03B1\u03CD\u03C3\u03B7']));
    GreekPattern_EU_V_instance = new GreekPattern('EU_V', 6, '\u03B5\u03C5', 'ev', 'eu-v', listOf(['\u0395\u03C5\u03C1\u03CE\u03C0\u03B7', '\u03B5\u03C5\u03C7\u03B1\u03C1\u03B9\u03C3\u03C4\u03CE']));
    GreekPattern_EU_F_instance = new GreekPattern('EU_F', 7, '\u03B5\u03C5', 'ef', 'eu-f', listOf_0('\u03B5\u03C5\u03C4\u03C5\u03C7\u03AF\u03B1'));
    GreekPattern_OU_instance = new GreekPattern('OU', 8, '\u03BF\u03C5', 'u', 'ou', listOf(['\u03C0\u03BF\u03C5', '\u03BF\u03CD\u03C4\u03B5', '\u03BC\u03BF\u03C5']));
    GreekPattern_MP_instance = new GreekPattern('MP', 9, '\u03BC\u03C0', 'b', 'mp-b', listOf(['\u03BC\u03C0\u03B1\u03BC\u03C0\u03AC\u03C2', '\u03BC\u03C0\u03BF\u03C1\u03CE']));
    GreekPattern_MP_MB_instance = new GreekPattern('MP_MB', 10, '\u03BC\u03C0', 'mb', 'mp-mb', listOf(['\u03BA\u03CC\u03BC\u03C0\u03BF\u03C2', '\u03BB\u03AC\u03BC\u03C0\u03C9']));
    GreekPattern_NT_instance = new GreekPattern('NT', 11, '\u03BD\u03C4', 'd', 'nt-d', listOf(['\u03BD\u03C4\u03BF\u03BC\u03AC\u03C4\u03B1', '\u03BD\u03C4\u03CD\u03BD\u03C9']));
    GreekPattern_NT_ND_instance = new GreekPattern('NT_ND', 12, '\u03BD\u03C4', 'nd', 'nt-nd', listOf(['\u03C0\u03AD\u03BD\u03C4\u03B5', '\u03AC\u03BD\u03C4\u03C1\u03B1\u03C2']));
    GreekPattern_GK_instance = new GreekPattern('GK', 13, '\u03B3\u03BA', 'g', 'gk-g', listOf(['\u03B3\u03BA\u03B1\u03C1\u03AC\u03B6', '\u03B3\u03BA\u03BF\u03BB']));
    GreekPattern_GK_NG_instance = new GreekPattern('GK_NG', 14, '\u03B3\u03BA', '\u014Bg', 'gk-ng', listOf(['\u03B1\u03B3\u03BA\u03AC\u03B8\u03B9', '\u03AC\u03B3\u03BA\u03C5\u03C1\u03B1']));
    GreekPattern_GG_instance = new GreekPattern('GG', 15, '\u03B3\u03B3', '\u014Bg', 'gg', listOf(['\u03AC\u03B3\u03B3\u03B5\u03BB\u03BF\u03C2', '\u03B1\u03B3\u03B3\u03BB\u03B9\u03BA\u03AC']));
    GreekPattern_GCH_instance = new GreekPattern('GCH', 16, '\u03B3\u03C7', '\u014Bx', 'gch', listOf(['\u03AD\u03BB\u03B5\u03B3\u03C7\u03BF\u03C2', '\u03AC\u03B3\u03C7\u03BF\u03C2']));
    GreekPattern_GX_instance = new GreekPattern('GX', 17, '\u03B3\u03BE', '\u014Bks', 'gx', listOf_0('\u03C3\u03C6\u03AF\u03B3\u03BE'));
    GreekPattern_THETA_instance = new GreekPattern('THETA', 18, '\u03B8', '\u03B8', 'theta', listOf(['\u03B8\u03AD\u03BB\u03C9', '\u03B8\u03AC\u03BB\u03B1\u03C3\u03C3\u03B1', '\u03B1\u03B8\u03BB\u03B7\u03C4\u03AE\u03C2']));
    GreekPattern_CHI_instance = new GreekPattern('CHI', 19, '\u03C7', 'x', 'chi', listOf(['\u03C7\u03AD\u03C1\u03B9', '\u03C7\u03C1\u03CC\u03BD\u03BF\u03C2', '\u03C0\u03BF\u03BB\u03CD\u03C7\u03C1\u03C9\u03BC\u03BF\u03C2']));
    GreekPattern_PSI_instance = new GreekPattern('PSI', 20, '\u03C8', 'ps', 'psi', listOf(['\u03C8\u03C9\u03BC\u03AF', '\u03C8\u03C5\u03C7\u03AE', '\u03BB\u03AD\u03C8\u03B7\u03C2']));
    GreekPattern_XI_instance = new GreekPattern('XI', 21, '\u03BE', 'ks', 'xi', listOf(['\u03BE\u03AD\u03C1\u03C9', '\u03B1\u03BE\u03AF\u03B1', '\u03C4\u03AC\u03BE\u03B7']));
    GreekPattern_OS_instance = new GreekPattern('OS', 22, '\u03BF\u03C2', 'os', 'os', listOf(['\u03BA\u03CC\u03C3\u03BC\u03BF\u03C2', '\u03AC\u03BD\u03B8\u03C1\u03C9\u03C0\u03BF\u03C2']), PatternPosition_END_getInstance());
    GreekPattern_IS_instance = new GreekPattern('IS', 23, '\u03B9\u03C2', 'is', 'is', listOf(['\u03C0\u03CC\u03BB\u03B9\u03C2', '\u03B4\u03CD\u03BD\u03B1\u03BC\u03B9\u03C2']), PatternPosition_END_getInstance());
    GreekPattern_A_instance = new GreekPattern('A', 24, '\u03B1', 'a', 'a', listOf(['\u03B8\u03AC\u03BB\u03B1\u03C3\u03C3\u03B1', '\u03B3\u03BB\u03CE\u03C3\u03C3\u03B1']), PatternPosition_END_getInstance());
    GreekPattern_I_instance = new GreekPattern('I', 25, '\u03B9', 'i', 'i', listOf(['\u03C0\u03B1\u03B9\u03B4\u03AF', '\u03C8\u03C9\u03BC\u03AF']), PatternPosition_END_getInstance());
    Companion_getInstance_30();
  }
  var $ENTRIES_29;
  function GreekPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.gh_1 = pattern;
    this.hh_1 = ipa;
    this.ih_1 = displayName;
    this.jh_1 = examples;
    this.kh_1 = position;
  }
  protoOf(GreekPattern).ei = function () {
    return this.hh_1;
  };
  protoOf(GreekPattern).fi = function () {
    return this.ih_1;
  };
  protoOf(GreekPattern).di = function () {
    return this.gh_1;
  };
  function sam$kotlin_Comparator$0_13(function_0) {
    this.eu_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_13).x8 = function (a, b) {
    return this.eu_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_13).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_13).e2 = function () {
    return this.eu_1;
  };
  protoOf(sam$kotlin_Comparator$0_13).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_13).hashCode = function () {
    return hashCode(this.e2());
  };
  function RussianPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.ph_1.length;
    var tmp$ret$1 = a.ph_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var RussianPattern_SOFT_T_instance;
  var RussianPattern_SOFT_D_instance;
  var RussianPattern_SOFT_N_instance;
  var RussianPattern_SOFT_L_instance;
  var RussianPattern_SOFT_S_instance;
  var RussianPattern_YA_INIT_instance;
  var RussianPattern_YE_INIT_instance;
  var RussianPattern_YO_INIT_instance;
  var RussianPattern_YU_INIT_instance;
  var RussianPattern_YA_SOFT_instance;
  var RussianPattern_YE_SOFT_instance;
  var RussianPattern_YO_SOFT_instance;
  var RussianPattern_YU_SOFT_instance;
  var RussianPattern_HARD_YA_instance;
  var RussianPattern_HARD_YE_instance;
  var RussianPattern_B_FINAL_instance;
  var RussianPattern_V_FINAL_instance;
  var RussianPattern_G_FINAL_instance;
  var RussianPattern_D_FINAL_instance;
  var RussianPattern_Z_FINAL_instance;
  var RussianPattern_ZH_FINAL_instance;
  var RussianPattern_SHCH_instance;
  var RussianPattern_TS_instance;
  var RussianPattern_CH_instance;
  var RussianPattern_OGO_instance;
  var RussianPattern_EGO_instance;
  var RussianPattern_TION_instance;
  var RussianPattern_NOST_instance;
  function Companion_31() {
    Companion_instance_31 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_30();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.ph_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.lh_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_30();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.qh_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.mh_1 = destination_0;
  }
  protoOf(Companion_31).yb = function (word) {
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_30();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.th_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = RussianPattern$Companion$matchEnding$lambda;
    var tmp$ret$3 = new sam$kotlin_Comparator$0_13(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$3);
    var tmp$ret$6;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(word, element_0.ph_1)) {
          tmp$ret$6 = element_0;
          break $l$block;
        }
      }
      tmp$ret$6 = null;
    }
    return tmp$ret$6;
  };
  var Companion_instance_31;
  function Companion_getInstance_31() {
    RussianPattern_initEntries();
    if (Companion_instance_31 == null)
      new Companion_31();
    return Companion_instance_31;
  }
  function values_30() {
    return [RussianPattern_SOFT_T_getInstance(), RussianPattern_SOFT_D_getInstance(), RussianPattern_SOFT_N_getInstance(), RussianPattern_SOFT_L_getInstance(), RussianPattern_SOFT_S_getInstance(), RussianPattern_YA_INIT_getInstance(), RussianPattern_YE_INIT_getInstance(), RussianPattern_YO_INIT_getInstance(), RussianPattern_YU_INIT_getInstance(), RussianPattern_YA_SOFT_getInstance(), RussianPattern_YE_SOFT_getInstance(), RussianPattern_YO_SOFT_getInstance(), RussianPattern_YU_SOFT_getInstance(), RussianPattern_HARD_YA_getInstance(), RussianPattern_HARD_YE_getInstance(), RussianPattern_B_FINAL_getInstance(), RussianPattern_V_FINAL_getInstance(), RussianPattern_G_FINAL_getInstance(), RussianPattern_D_FINAL_getInstance(), RussianPattern_Z_FINAL_getInstance(), RussianPattern_ZH_FINAL_getInstance(), RussianPattern_SHCH_getInstance(), RussianPattern_TS_getInstance(), RussianPattern_CH_getInstance(), RussianPattern_OGO_getInstance(), RussianPattern_EGO_getInstance(), RussianPattern_TION_getInstance(), RussianPattern_NOST_getInstance()];
  }
  function get_entries_30() {
    if ($ENTRIES_30 == null)
      $ENTRIES_30 = enumEntries(values_30());
    return $ENTRIES_30;
  }
  var RussianPattern_entriesInitialized;
  function RussianPattern_initEntries() {
    if (RussianPattern_entriesInitialized)
      return Unit_instance;
    RussianPattern_entriesInitialized = true;
    RussianPattern_SOFT_T_instance = new RussianPattern('SOFT_T', 0, '\u0442\u044C', 't\u02B2', 'soft-t', listOf(['\u043C\u0430\u0442\u044C', '\u043F\u0443\u0442\u044C', '\u0435\u0441\u0442\u044C']), PatternPosition_END_getInstance());
    RussianPattern_SOFT_D_instance = new RussianPattern('SOFT_D', 1, '\u0434\u044C', 'd\u02B2', 'soft-d', listOf(['\u0432\u0435\u0434\u044C', '\u043C\u0435\u0434\u044C']));
    RussianPattern_SOFT_N_instance = new RussianPattern('SOFT_N', 2, '\u043D\u044C', '\u0272', 'soft-n', listOf(['\u043A\u043E\u043D\u044C', '\u0434\u0435\u043D\u044C', '\u0441\u0435\u043C\u044C']));
    RussianPattern_SOFT_L_instance = new RussianPattern('SOFT_L', 3, '\u043B\u044C', 'l\u02B2', 'soft-l', listOf(['\u0441\u043E\u043B\u044C', '\u043C\u043E\u043B\u044C', '\u0431\u043E\u043B\u044C']));
    RussianPattern_SOFT_S_instance = new RussianPattern('SOFT_S', 4, '\u0441\u044C', 's\u02B2', 'soft-s', listOf(['\u0432\u0435\u0441\u044C', '\u0432\u044B\u0441\u044C']));
    RussianPattern_YA_INIT_instance = new RussianPattern('YA_INIT', 5, '\u044F', 'ja', 'ya-init', listOf(['\u044F\u0431\u043B\u043E\u043A\u043E', '\u044F\u0437\u044B\u043A', '\u044F\u0441\u043D\u043E']), PatternPosition_START_getInstance());
    RussianPattern_YE_INIT_instance = new RussianPattern('YE_INIT', 6, '\u0435', 'je', 'ye-init', listOf(['\u0435\u0434\u0430', '\u0435\u0441\u043B\u0438', '\u0435\u0449\u0451']), PatternPosition_START_getInstance());
    RussianPattern_YO_INIT_instance = new RussianPattern('YO_INIT', 7, '\u0451', 'jo', 'yo-init', listOf(['\u0451\u043B\u043A\u0430', '\u0451\u0436']), PatternPosition_START_getInstance());
    RussianPattern_YU_INIT_instance = new RussianPattern('YU_INIT', 8, '\u044E', 'ju', 'yu-init', listOf(['\u044E\u0433', '\u044E\u043C\u043E\u0440', '\u044E\u043D\u044B\u0439']), PatternPosition_START_getInstance());
    RussianPattern_YA_SOFT_instance = new RussianPattern('YA_SOFT', 9, '\u044C\u044F', '\u02B2ja', 'ya-soft', listOf(['\u0441\u0435\u043C\u044C\u044F', '\u0441\u0442\u0430\u0442\u044C\u044F']));
    RussianPattern_YE_SOFT_instance = new RussianPattern('YE_SOFT', 10, '\u044C\u0435', '\u02B2je', 'ye-soft', listOf(['\u0441\u0447\u0430\u0441\u0442\u044C\u0435', '\u0437\u0434\u043E\u0440\u043E\u0432\u044C\u0435']));
    RussianPattern_YO_SOFT_instance = new RussianPattern('YO_SOFT', 11, '\u044C\u0451', '\u02B2jo', 'yo-soft', listOf(['\u043F\u0438\u0442\u044C\u0451', '\u0431\u0440\u0438\u0442\u044C\u0451']));
    RussianPattern_YU_SOFT_instance = new RussianPattern('YU_SOFT', 12, '\u044C\u044E', '\u02B2ju', 'yu-soft', listOf(['\u0441\u0435\u043C\u044C\u044E', '\u0441\u0442\u0430\u0442\u044C\u044E']));
    RussianPattern_HARD_YA_instance = new RussianPattern('HARD_YA', 13, '\u044A\u044F', 'ja', 'hard-ya', listOf(['\u043E\u0431\u044A\u044F\u0441\u043D\u0438\u0442\u044C', '\u0432\u044A\u044F\u0432\u044C']));
    RussianPattern_HARD_YE_instance = new RussianPattern('HARD_YE', 14, '\u044A\u0435', 'je', 'hard-ye', listOf(['\u0441\u044A\u0435\u0441\u0442\u044C', '\u043E\u0431\u044A\u0435\u043A\u0442']));
    RussianPattern_B_FINAL_instance = new RussianPattern('B_FINAL', 15, '\u0431', 'p', 'b-devoice', listOf(['\u0445\u043B\u0435\u0431', '\u0434\u0443\u0431', '\u0437\u0443\u0431']), PatternPosition_END_getInstance());
    RussianPattern_V_FINAL_instance = new RussianPattern('V_FINAL', 16, '\u0432', 'f', 'v-devoice', listOf(['\u043A\u0440\u043E\u0432\u044C', '\u043B\u044E\u0431\u043E\u0432\u044C']), PatternPosition_END_getInstance());
    RussianPattern_G_FINAL_instance = new RussianPattern('G_FINAL', 17, '\u0433', 'k', 'g-devoice', listOf(['\u0434\u0440\u0443\u0433', '\u0441\u043D\u0435\u0433', '\u0432\u0440\u0430\u0433']), PatternPosition_END_getInstance());
    RussianPattern_D_FINAL_instance = new RussianPattern('D_FINAL', 18, '\u0434', 't', 'd-devoice', listOf(['\u0433\u043E\u0434', '\u0441\u0430\u0434', '\u0433\u043E\u0440\u043E\u0434']), PatternPosition_END_getInstance());
    RussianPattern_Z_FINAL_instance = new RussianPattern('Z_FINAL', 19, '\u0437', 's', 'z-devoice', listOf(['\u0440\u0430\u0437', '\u0433\u043B\u0430\u0437', '\u043C\u043E\u0440\u043E\u0437']), PatternPosition_END_getInstance());
    RussianPattern_ZH_FINAL_instance = new RussianPattern('ZH_FINAL', 20, '\u0436', '\u0282', 'zh-devoice', listOf(['\u043D\u043E\u0436', '\u043C\u0443\u0436', '\u043F\u043B\u044F\u0436']), PatternPosition_END_getInstance());
    RussianPattern_SHCH_instance = new RussianPattern('SHCH', 21, '\u0449', '\u0255\u02D0', 'shch', listOf(['\u0449\u0438', '\u0438\u0449\u0443', '\u0435\u0449\u0451']));
    RussianPattern_TS_instance = new RussianPattern('TS', 22, '\u0446', 'ts', 'ts', listOf(['\u0446\u0435\u043D\u0430', '\u043F\u0442\u0438\u0446\u0430', '\u043A\u043E\u043D\u0435\u0446']));
    RussianPattern_CH_instance = new RussianPattern('CH', 23, '\u0447', 't\u0255', 'ch', listOf(['\u0447\u0430\u0439', '\u0447\u0435\u043B\u043E\u0432\u0435\u043A', '\u043D\u043E\u0447\u044C']));
    RussianPattern_OGO_instance = new RussianPattern('OGO', 24, '\u043E\u0433\u043E', 'ovo', 'ogo', listOf(['\u043D\u043E\u0432\u043E\u0433\u043E', '\u0431\u043E\u043B\u044C\u0448\u043E\u0433\u043E']), PatternPosition_END_getInstance());
    RussianPattern_EGO_instance = new RussianPattern('EGO', 25, '\u0435\u0433\u043E', 'jevo', 'ego', listOf(['\u0441\u0438\u043D\u0435\u0433\u043E', '\u043C\u043E\u0435\u0433\u043E']), PatternPosition_END_getInstance());
    RussianPattern_TION_instance = new RussianPattern('TION', 26, '\u0446\u0438\u044F', 'ts\u0268ja', 'tion', listOf(['\u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F', '\u0441\u0438\u0442\u0443\u0430\u0446\u0438\u044F']), PatternPosition_END_getInstance());
    RussianPattern_NOST_instance = new RussianPattern('NOST', 27, '\u043D\u043E\u0441\u0442\u044C', 'nost\u02B2', 'nost', listOf(['\u0432\u043E\u0437\u043C\u043E\u0436\u043D\u043E\u0441\u0442\u044C', '\u0441\u043F\u043E\u0441\u043E\u0431\u043D\u043E\u0441\u0442\u044C']), PatternPosition_END_getInstance());
    Companion_getInstance_31();
  }
  var $ENTRIES_30;
  function RussianPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.ph_1 = pattern;
    this.qh_1 = ipa;
    this.rh_1 = displayName;
    this.sh_1 = examples;
    this.th_1 = position;
  }
  protoOf(RussianPattern).ei = function () {
    return this.qh_1;
  };
  protoOf(RussianPattern).fi = function () {
    return this.rh_1;
  };
  protoOf(RussianPattern).di = function () {
    return this.ph_1;
  };
  function sam$kotlin_Comparator$0_14(function_0) {
    this.fu_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_14).x8 = function (a, b) {
    return this.fu_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_14).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_14).e2 = function () {
    return this.fu_1;
  };
  protoOf(sam$kotlin_Comparator$0_14).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_14).hashCode = function () {
    return hashCode(this.e2());
  };
  function PinyinPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.yh_1.length;
    var tmp$ret$1 = a.yh_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var PinyinPattern_ZH_instance;
  var PinyinPattern_CH_instance;
  var PinyinPattern_SH_instance;
  var PinyinPattern_R_instance;
  var PinyinPattern_J_instance;
  var PinyinPattern_Q_instance;
  var PinyinPattern_X_instance;
  var PinyinPattern_Z_instance;
  var PinyinPattern_C_instance;
  var PinyinPattern_S_instance;
  var PinyinPattern_ZHI_instance;
  var PinyinPattern_CHI_instance;
  var PinyinPattern_SHI_instance;
  var PinyinPattern_RI_instance;
  var PinyinPattern_ZI_instance;
  var PinyinPattern_CI_instance;
  var PinyinPattern_SI_instance;
  var PinyinPattern_IA_instance;
  var PinyinPattern_IE_instance;
  var PinyinPattern_IU_instance;
  var PinyinPattern_UO_instance;
  var PinyinPattern_UE_instance;
  var PinyinPattern_UA_instance;
  var PinyinPattern_UI_instance;
  var PinyinPattern_UN_instance;
  var PinyinPattern_UAN_instance;
  var PinyinPattern_AN_instance;
  var PinyinPattern_EN_instance;
  var PinyinPattern_IN_instance;
  var PinyinPattern_ANG_instance;
  var PinyinPattern_ENG_instance;
  var PinyinPattern_ING_instance;
  var PinyinPattern_ONG_instance;
  var PinyinPattern_TONE1_instance;
  var PinyinPattern_TONE2_instance;
  var PinyinPattern_TONE3_instance;
  var PinyinPattern_TONE4_instance;
  function Companion_32() {
    Companion_instance_32 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_31();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.yh_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.uh_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_31();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.zh_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.vh_1 = destination_0;
  }
  protoOf(Companion_32).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_31();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.ci_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = PinyinPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_14(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.yh_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_32;
  function Companion_getInstance_32() {
    PinyinPattern_initEntries();
    if (Companion_instance_32 == null)
      new Companion_32();
    return Companion_instance_32;
  }
  function values_31() {
    return [PinyinPattern_ZH_getInstance(), PinyinPattern_CH_getInstance(), PinyinPattern_SH_getInstance(), PinyinPattern_R_getInstance(), PinyinPattern_J_getInstance(), PinyinPattern_Q_getInstance(), PinyinPattern_X_getInstance(), PinyinPattern_Z_getInstance(), PinyinPattern_C_getInstance(), PinyinPattern_S_getInstance(), PinyinPattern_ZHI_getInstance(), PinyinPattern_CHI_getInstance(), PinyinPattern_SHI_getInstance(), PinyinPattern_RI_getInstance(), PinyinPattern_ZI_getInstance(), PinyinPattern_CI_getInstance(), PinyinPattern_SI_getInstance(), PinyinPattern_IA_getInstance(), PinyinPattern_IE_getInstance(), PinyinPattern_IU_getInstance(), PinyinPattern_UO_getInstance(), PinyinPattern_UE_getInstance(), PinyinPattern_UA_getInstance(), PinyinPattern_UI_getInstance(), PinyinPattern_UN_getInstance(), PinyinPattern_UAN_getInstance(), PinyinPattern_AN_getInstance(), PinyinPattern_EN_getInstance(), PinyinPattern_IN_getInstance(), PinyinPattern_ANG_getInstance(), PinyinPattern_ENG_getInstance(), PinyinPattern_ING_getInstance(), PinyinPattern_ONG_getInstance(), PinyinPattern_TONE1_getInstance(), PinyinPattern_TONE2_getInstance(), PinyinPattern_TONE3_getInstance(), PinyinPattern_TONE4_getInstance()];
  }
  function get_entries_31() {
    if ($ENTRIES_31 == null)
      $ENTRIES_31 = enumEntries(values_31());
    return $ENTRIES_31;
  }
  var PinyinPattern_entriesInitialized;
  function PinyinPattern_initEntries() {
    if (PinyinPattern_entriesInitialized)
      return Unit_instance;
    PinyinPattern_entriesInitialized = true;
    PinyinPattern_ZH_instance = new PinyinPattern('ZH', 0, 'zh', '\u0288\u0282', 'zh-retro', listOf(['zh\u014Dng', 'zh\u012B', 'zh\xF9']));
    PinyinPattern_CH_instance = new PinyinPattern('CH', 1, 'ch', '\u0288\u0282\u02B0', 'ch-retro', listOf(['ch\u012B', 'ch\xE1', 'ch\u016B']));
    PinyinPattern_SH_instance = new PinyinPattern('SH', 2, 'sh', '\u0282', 'sh-retro', listOf(['sh\xEC', 'sh\u016B', 'sh\u0101n']));
    PinyinPattern_R_instance = new PinyinPattern('R', 3, 'r', '\u0290', 'r-retro', listOf(['r\xE9n', 'r\xEC', 'r\xF2u']));
    PinyinPattern_J_instance = new PinyinPattern('J', 4, 'j', 't\u0255', 'j-palatal', listOf(['ji\u0101', 'j\u012Bn', 'ji\xF9']));
    PinyinPattern_Q_instance = new PinyinPattern('Q', 5, 'q', 't\u0255\u02B0', 'q-palatal', listOf(['q\u012B', 'q\xF9', 'qi\xE1n']));
    PinyinPattern_X_instance = new PinyinPattern('X', 6, 'x', '\u0255', 'x-palatal', listOf(['x\u012B', 'xi\xE8', 'xu\u011B']));
    PinyinPattern_Z_instance = new PinyinPattern('Z', 7, 'z', 'ts', 'z-alveolar', listOf(['z\xE0i', 'z\u01D2u', 'zu\xF2']));
    PinyinPattern_C_instance = new PinyinPattern('C', 8, 'c', 'ts\u02B0', 'c-alveolar', listOf(['c\xE1i', 'c\xF3ng', 'cu\xF2']));
    PinyinPattern_S_instance = new PinyinPattern('S', 9, 's', 's', 's-alveolar', listOf(['s\u0101n', 's\xEC', 'shu\u014D']));
    PinyinPattern_ZHI_instance = new PinyinPattern('ZHI', 10, 'zhi', '\u0288\u0282\u0268', 'zhi', listOf(['zh\u012B', 'zh\u01D0', 'zh\xEC']));
    PinyinPattern_CHI_instance = new PinyinPattern('CHI', 11, 'chi', '\u0288\u0282\u02B0\u0268', 'chi', listOf(['ch\u012B', 'ch\u01D0', 'ch\xEC']));
    PinyinPattern_SHI_instance = new PinyinPattern('SHI', 12, 'shi', '\u0282\u0268', 'shi', listOf(['sh\u012B', 'sh\xED', 'sh\xEC']));
    PinyinPattern_RI_instance = new PinyinPattern('RI', 13, 'ri', '\u0290\u0268', 'ri', listOf_0('r\xEC'));
    PinyinPattern_ZI_instance = new PinyinPattern('ZI', 14, 'zi', 'ts\u0268', 'zi', listOf(['z\xEC', 'z\u01D0']));
    PinyinPattern_CI_instance = new PinyinPattern('CI', 15, 'ci', 'ts\u02B0\u0268', 'ci', listOf(['c\xEC', 'c\xED']));
    PinyinPattern_SI_instance = new PinyinPattern('SI', 16, 'si', 's\u0268', 'si', listOf(['s\xEC', 's\u012B']));
    PinyinPattern_IA_instance = new PinyinPattern('IA', 17, 'ia', 'ja', 'ia', listOf(['ji\u0101', 'xi\xE0']));
    PinyinPattern_IE_instance = new PinyinPattern('IE', 18, 'ie', 'je', 'ie', listOf(['ji\u011B', 'xi\u011B']));
    PinyinPattern_IU_instance = new PinyinPattern('IU', 19, 'iu', 'jo\u028A', 'iu', listOf(['ji\xF9', 'li\xF9']));
    PinyinPattern_UO_instance = new PinyinPattern('UO', 20, 'uo', 'wo', 'uo', listOf(['du\u014D', 'gu\xF2']));
    PinyinPattern_UE_instance = new PinyinPattern('UE', 21, '\xFCe', '\u0265e', 'ue', listOf(['xu\u011B', 'ju\xE9']));
    PinyinPattern_UA_instance = new PinyinPattern('UA', 22, 'ua', 'wa', 'ua', listOf(['hu\u0101', 'gu\xE0']));
    PinyinPattern_UI_instance = new PinyinPattern('UI', 23, 'ui', 'we\u026A', 'ui', listOf(['hu\xED', 'du\xEC']));
    PinyinPattern_UN_instance = new PinyinPattern('UN', 24, 'un', 'w\u0259n', 'un', listOf(['l\xFAn', 'd\xF9n']));
    PinyinPattern_UAN_instance = new PinyinPattern('UAN', 25, 'uan', 'wan', 'uan', listOf(['hu\xE1n', 'gu\u0101n']));
    PinyinPattern_AN_instance = new PinyinPattern('AN', 26, 'an', 'an', 'an', listOf(['\u0101n', 'f\xE0n']));
    PinyinPattern_EN_instance = new PinyinPattern('EN', 27, 'en', '\u0259n', 'en', listOf(['r\xE9n', 'sh\xE9n']));
    PinyinPattern_IN_instance = new PinyinPattern('IN', 28, 'in', 'in', 'in', listOf(['x\u012Bn', 'j\u012Bn']));
    PinyinPattern_ANG_instance = new PinyinPattern('ANG', 29, 'ang', 'a\u014B', 'ang', listOf(['f\xE1ng', 'm\xE1ng']));
    PinyinPattern_ENG_instance = new PinyinPattern('ENG', 30, 'eng', '\u0259\u014B', 'eng', listOf(['f\u0113ng', 'sh\u0113ng']));
    PinyinPattern_ING_instance = new PinyinPattern('ING', 31, 'ing', 'i\u014B', 'ing', listOf(['t\u012Bng', 'm\xEDng']));
    PinyinPattern_ONG_instance = new PinyinPattern('ONG', 32, 'ong', '\u028A\u014B', 'ong', listOf(['zh\u014Dng', 'h\xF3ng']));
    PinyinPattern_TONE1_instance = new PinyinPattern('TONE1', 33, '\u0101', '\u02E5', 'tone1-high', listOf(['m\u0101', 'sh\u012B']));
    PinyinPattern_TONE2_instance = new PinyinPattern('TONE2', 34, '\xE1', '\u02E7\u02E5', 'tone2-rising', listOf(['m\xE1', 'sh\xED']));
    PinyinPattern_TONE3_instance = new PinyinPattern('TONE3', 35, '\u01CE', '\u02E8\u02E9\u02E6', 'tone3-dip', listOf(['m\u01CE', 'sh\u01D0']));
    PinyinPattern_TONE4_instance = new PinyinPattern('TONE4', 36, '\xE0', '\u02E5\u02E9', 'tone4-falling', listOf(['m\xE0', 'sh\xEC']));
    Companion_getInstance_32();
  }
  var $ENTRIES_31;
  function PinyinPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.yh_1 = pattern;
    this.zh_1 = ipa;
    this.ai_1 = displayName;
    this.bi_1 = examples;
    this.ci_1 = position;
  }
  protoOf(PinyinPattern).ei = function () {
    return this.zh_1;
  };
  protoOf(PinyinPattern).fi = function () {
    return this.ai_1;
  };
  protoOf(PinyinPattern).di = function () {
    return this.yh_1;
  };
  function sam$kotlin_Comparator$0_15(function_0) {
    this.gu_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_15).x8 = function (a, b) {
    return this.gu_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_15).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_15).e2 = function () {
    return this.gu_1;
  };
  protoOf(sam$kotlin_Comparator$0_15).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_15).hashCode = function () {
    return hashCode(this.e2());
  };
  function SwahiliPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.xg_1.length;
    var tmp$ret$1 = a.xg_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var SwahiliPattern_MB_instance;
  var SwahiliPattern_ND_instance;
  var SwahiliPattern_NG_instance;
  var SwahiliPattern_NJ_instance;
  var SwahiliPattern_NZ_instance;
  var SwahiliPattern_NG_APOSTROPHE_instance;
  var SwahiliPattern_CH_instance;
  var SwahiliPattern_SH_instance;
  var SwahiliPattern_DH_instance;
  var SwahiliPattern_TH_instance;
  var SwahiliPattern_GH_instance;
  var SwahiliPattern_NY_instance;
  var SwahiliPattern_M_PREFIX_instance;
  var SwahiliPattern_WA_PREFIX_instance;
  var SwahiliPattern_KI_PREFIX_instance;
  var SwahiliPattern_VI_PREFIX_instance;
  var SwahiliPattern_A_FINAL_instance;
  var SwahiliPattern_I_FINAL_instance;
  var SwahiliPattern_U_FINAL_instance;
  function Companion_33() {
    Companion_instance_33 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_32();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.xg_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.tg_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_32();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.yg_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.ug_1 = destination_0;
  }
  protoOf(Companion_33).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_32();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.bh_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = SwahiliPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_15(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.xg_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_33;
  function Companion_getInstance_33() {
    SwahiliPattern_initEntries();
    if (Companion_instance_33 == null)
      new Companion_33();
    return Companion_instance_33;
  }
  function values_32() {
    return [SwahiliPattern_MB_getInstance(), SwahiliPattern_ND_getInstance(), SwahiliPattern_NG_getInstance(), SwahiliPattern_NJ_getInstance(), SwahiliPattern_NZ_getInstance(), SwahiliPattern_NG_APOSTROPHE_getInstance(), SwahiliPattern_CH_getInstance(), SwahiliPattern_SH_getInstance(), SwahiliPattern_DH_getInstance(), SwahiliPattern_TH_getInstance(), SwahiliPattern_GH_getInstance(), SwahiliPattern_NY_getInstance(), SwahiliPattern_M_PREFIX_getInstance(), SwahiliPattern_WA_PREFIX_getInstance(), SwahiliPattern_KI_PREFIX_getInstance(), SwahiliPattern_VI_PREFIX_getInstance(), SwahiliPattern_A_FINAL_getInstance(), SwahiliPattern_I_FINAL_getInstance(), SwahiliPattern_U_FINAL_getInstance()];
  }
  function get_entries_32() {
    if ($ENTRIES_32 == null)
      $ENTRIES_32 = enumEntries(values_32());
    return $ENTRIES_32;
  }
  var SwahiliPattern_entriesInitialized;
  function SwahiliPattern_initEntries() {
    if (SwahiliPattern_entriesInitialized)
      return Unit_instance;
    SwahiliPattern_entriesInitialized = true;
    SwahiliPattern_MB_instance = new SwahiliPattern('MB', 0, 'mb', 'mb', 'mb', listOf(['mbwa', 'jambo', 'simba']));
    SwahiliPattern_ND_instance = new SwahiliPattern('ND', 1, 'nd', 'nd', 'nd', listOf(['ndio', 'panda', 'mwandishi']));
    SwahiliPattern_NG_instance = new SwahiliPattern('NG', 2, 'ng', '\u014Bg', 'ng', listOf(['ngoma', 'mwanga', 'fungua']));
    SwahiliPattern_NJ_instance = new SwahiliPattern('NJ', 3, 'nj', 'nd\u0292', 'nj', listOf(['njia', 'mwanajeshi']));
    SwahiliPattern_NZ_instance = new SwahiliPattern('NZ', 4, 'nz', 'nz', 'nz', listOf(['nzuri', 'mwanzilishi']));
    SwahiliPattern_NG_APOSTROPHE_instance = new SwahiliPattern('NG_APOSTROPHE', 5, "ng'", '\u014B', 'ng-velar', listOf(["ng'ombe", "kung'oa"]));
    SwahiliPattern_CH_instance = new SwahiliPattern('CH', 6, 'ch', 't\u0283', 'ch', listOf(['chakula', 'mchana', 'kucheza']));
    SwahiliPattern_SH_instance = new SwahiliPattern('SH', 7, 'sh', '\u0283', 'sh', listOf(['shule', 'kushukuru', 'shamba']));
    SwahiliPattern_DH_instance = new SwahiliPattern('DH', 8, 'dh', '\xF0', 'dh', listOf(['dhambi', 'dharau']));
    SwahiliPattern_TH_instance = new SwahiliPattern('TH', 9, 'th', '\u03B8', 'th', listOf(['thelathini', 'thamani']));
    SwahiliPattern_GH_instance = new SwahiliPattern('GH', 10, 'gh', '\u0263', 'gh', listOf(['ghali', 'lugha']));
    SwahiliPattern_NY_instance = new SwahiliPattern('NY', 11, 'ny', '\u0272', 'ny', listOf(['nyumba', 'mnyama', 'nyota']));
    SwahiliPattern_M_PREFIX_instance = new SwahiliPattern('M_PREFIX', 12, 'm-', 'm', 'm-class', listOf(['mtu', 'mwalimu']), PatternPosition_START_getInstance());
    SwahiliPattern_WA_PREFIX_instance = new SwahiliPattern('WA_PREFIX', 13, 'wa-', 'wa', 'wa-class', listOf(['watu', 'walimu']), PatternPosition_START_getInstance());
    SwahiliPattern_KI_PREFIX_instance = new SwahiliPattern('KI_PREFIX', 14, 'ki-', 'ki', 'ki-class', listOf(['kitu', 'kitabu']), PatternPosition_START_getInstance());
    SwahiliPattern_VI_PREFIX_instance = new SwahiliPattern('VI_PREFIX', 15, 'vi-', 'vi', 'vi-class', listOf(['vitu', 'vitabu']), PatternPosition_START_getInstance());
    SwahiliPattern_A_FINAL_instance = new SwahiliPattern('A_FINAL', 16, 'a', 'a', 'a-final', listOf(['soma', 'kula', 'lala']), PatternPosition_END_getInstance());
    SwahiliPattern_I_FINAL_instance = new SwahiliPattern('I_FINAL', 17, 'i', 'i', 'i-final', listOf(['safi', 'mali', 'hali']), PatternPosition_END_getInstance());
    SwahiliPattern_U_FINAL_instance = new SwahiliPattern('U_FINAL', 18, 'u', 'u', 'u-final', listOf(['mtu', 'siku', 'nguvu']), PatternPosition_END_getInstance());
    Companion_getInstance_33();
  }
  var $ENTRIES_32;
  function SwahiliPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.xg_1 = pattern;
    this.yg_1 = ipa;
    this.zg_1 = displayName;
    this.ah_1 = examples;
    this.bh_1 = position;
  }
  protoOf(SwahiliPattern).ei = function () {
    return this.yg_1;
  };
  protoOf(SwahiliPattern).fi = function () {
    return this.zg_1;
  };
  protoOf(SwahiliPattern).di = function () {
    return this.xg_1;
  };
  function sam$kotlin_Comparator$0_16(function_0) {
    this.hu_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_16).x8 = function (a, b) {
    return this.hu_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_16).compare = function (a, b) {
    return this.x8(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_16).e2 = function () {
    return this.hu_1;
  };
  protoOf(sam$kotlin_Comparator$0_16).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.e2(), other.e2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_16).hashCode = function () {
    return hashCode(this.e2());
  };
  function MalayPattern$Companion$matchEnding$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.og_1.length;
    var tmp$ret$1 = a.og_1.length;
    return compareValues(tmp, tmp$ret$1);
  }
  var MalayPattern_NG_instance;
  var MalayPattern_NY_instance;
  var MalayPattern_SY_instance;
  var MalayPattern_C_instance;
  var MalayPattern_J_instance;
  var MalayPattern_KH_instance;
  var MalayPattern_GH_instance;
  var MalayPattern_E_PEPET_instance;
  var MalayPattern_E_TALING_instance;
  var MalayPattern_MEM_instance;
  var MalayPattern_MEN_instance;
  var MalayPattern_MENG_instance;
  var MalayPattern_MENY_instance;
  var MalayPattern_BER_instance;
  var MalayPattern_TER_instance;
  var MalayPattern_KAN_instance;
  var MalayPattern_I_instance;
  var MalayPattern_AN_instance;
  function Companion_34() {
    Companion_instance_34 = this;
    var tmp = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = get_entries_33();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.og_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp_0;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp_0 = answer;
      } else {
        tmp_0 = value;
      }
      var list = tmp_0;
      list.g(element);
    }
    tmp.kg_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0_0 = get_entries_33();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination_0 = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var key_0 = element_0.pg_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value_0 = destination_0.q1(key_0);
      var tmp_2;
      if (value_0 == null) {
        var answer_0 = ArrayList_init_$Create$();
        destination_0.l3(key_0, answer_0);
        tmp_2 = answer_0;
      } else {
        tmp_2 = value_0;
      }
      var list_0 = tmp_2;
      list_0.g(element_0);
    }
    tmp_1.lg_1 = destination_0;
  }
  protoOf(Companion_34).yb = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_33();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!element.sg_1.equals(PatternPosition_START_getInstance())) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.sortedByDescending' call
    // Inline function 'kotlin.comparisons.compareByDescending' call
    var tmp = MalayPattern$Companion$matchEnding$lambda;
    var tmp$ret$5 = new sam$kotlin_Comparator$0_16(tmp);
    var tmp0_0 = sortedWith(destination, tmp$ret$5);
    var tmp$ret$8;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (endsWith(w, element_0.og_1)) {
          tmp$ret$8 = element_0;
          break $l$block;
        }
      }
      tmp$ret$8 = null;
    }
    return tmp$ret$8;
  };
  var Companion_instance_34;
  function Companion_getInstance_34() {
    MalayPattern_initEntries();
    if (Companion_instance_34 == null)
      new Companion_34();
    return Companion_instance_34;
  }
  function values_33() {
    return [MalayPattern_NG_getInstance(), MalayPattern_NY_getInstance(), MalayPattern_SY_getInstance(), MalayPattern_C_getInstance(), MalayPattern_J_getInstance(), MalayPattern_KH_getInstance(), MalayPattern_GH_getInstance(), MalayPattern_E_PEPET_getInstance(), MalayPattern_E_TALING_getInstance(), MalayPattern_MEM_getInstance(), MalayPattern_MEN_getInstance(), MalayPattern_MENG_getInstance(), MalayPattern_MENY_getInstance(), MalayPattern_BER_getInstance(), MalayPattern_TER_getInstance(), MalayPattern_KAN_getInstance(), MalayPattern_I_getInstance(), MalayPattern_AN_getInstance()];
  }
  function get_entries_33() {
    if ($ENTRIES_33 == null)
      $ENTRIES_33 = enumEntries(values_33());
    return $ENTRIES_33;
  }
  var MalayPattern_entriesInitialized;
  function MalayPattern_initEntries() {
    if (MalayPattern_entriesInitialized)
      return Unit_instance;
    MalayPattern_entriesInitialized = true;
    MalayPattern_NG_instance = new MalayPattern('NG', 0, 'ng', '\u014B', 'ng', listOf(['dengan', 'orang', 'bunga']));
    MalayPattern_NY_instance = new MalayPattern('NY', 1, 'ny', '\u0272', 'ny', listOf(['nyata', 'banyak', 'menyanyi']));
    MalayPattern_SY_instance = new MalayPattern('SY', 2, 'sy', '\u0283', 'sy', listOf(['syarat', 'masyarakat', 'syukur']));
    MalayPattern_C_instance = new MalayPattern('C', 3, 'c', 't\u0283', 'c', listOf(['cari', 'baca', 'cuci']));
    MalayPattern_J_instance = new MalayPattern('J', 4, 'j', 'd\u0292', 'j', listOf(['jalan', 'belajar', 'meja']));
    MalayPattern_KH_instance = new MalayPattern('KH', 5, 'kh', 'x', 'kh', listOf(['khusus', 'akhir', 'khabar']));
    MalayPattern_GH_instance = new MalayPattern('GH', 6, 'gh', '\u0263', 'gh', listOf_0('maghrib'));
    MalayPattern_E_PEPET_instance = new MalayPattern('E_PEPET', 7, 'e', '\u0259', 'e-pepet', listOf(['empat', 'dengan', 'emas']));
    MalayPattern_E_TALING_instance = new MalayPattern('E_TALING', 8, '\xE9', 'e', 'e-taling', listOf(['b\xE9b\xE9k', '\xE9lok']));
    MalayPattern_MEM_instance = new MalayPattern('MEM', 9, 'mem', 'm\u0259m', 'mem', listOf(['membuat', 'membeli']), PatternPosition_START_getInstance());
    MalayPattern_MEN_instance = new MalayPattern('MEN', 10, 'men', 'm\u0259n', 'men', listOf(['menulis', 'menari']), PatternPosition_START_getInstance());
    MalayPattern_MENG_instance = new MalayPattern('MENG', 11, 'meng', 'm\u0259\u014B', 'meng', listOf(['mengambil', 'mengajar']), PatternPosition_START_getInstance());
    MalayPattern_MENY_instance = new MalayPattern('MENY', 12, 'meny', 'm\u0259\u0272', 'meny', listOf(['menyapu', 'menyanyi']), PatternPosition_START_getInstance());
    MalayPattern_BER_instance = new MalayPattern('BER', 13, 'ber-', 'b\u0259r', 'ber', listOf(['berjalan', 'berbicara']), PatternPosition_START_getInstance());
    MalayPattern_TER_instance = new MalayPattern('TER', 14, 'ter-', 't\u0259r', 'ter', listOf(['terbang', 'terbuka']), PatternPosition_START_getInstance());
    MalayPattern_KAN_instance = new MalayPattern('KAN', 15, '-kan', 'kan', 'kan', listOf(['berikan', 'buatkan']), PatternPosition_END_getInstance());
    MalayPattern_I_instance = new MalayPattern('I', 16, '-i', 'i', 'i-suffix', listOf(['mengerti', 'mendekati']), PatternPosition_END_getInstance());
    MalayPattern_AN_instance = new MalayPattern('AN', 17, '-an', 'an', 'an', listOf(['makanan', 'minuman']), PatternPosition_END_getInstance());
    Companion_getInstance_34();
  }
  var $ENTRIES_33;
  function MalayPattern(name, ordinal, pattern, ipa, displayName, examples, position) {
    position = position === VOID ? PatternPosition_ANY_getInstance() : position;
    Enum.call(this, name, ordinal);
    this.og_1 = pattern;
    this.pg_1 = ipa;
    this.qg_1 = displayName;
    this.rg_1 = examples;
    this.sg_1 = position;
  }
  protoOf(MalayPattern).ei = function () {
    return this.pg_1;
  };
  protoOf(MalayPattern).fi = function () {
    return this.qg_1;
  };
  protoOf(MalayPattern).di = function () {
    return this.og_1;
  };
  function SwedishPattern_SJ_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_SJ_instance;
  }
  function SwedishPattern_SKJ_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_SKJ_instance;
  }
  function SwedishPattern_STJ_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_STJ_instance;
  }
  function SwedishPattern_SK_SOFT_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_SK_SOFT_instance;
  }
  function SwedishPattern_TJ_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_TJ_instance;
  }
  function SwedishPattern_KJ_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_KJ_instance;
  }
  function SwedishPattern_K_SOFT_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_K_SOFT_instance;
  }
  function SwedishPattern_RS_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_RS_instance;
  }
  function SwedishPattern_RD_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_RD_instance;
  }
  function SwedishPattern_RN_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_RN_instance;
  }
  function SwedishPattern_RT_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_RT_instance;
  }
  function SwedishPattern_RL_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_RL_instance;
  }
  function SwedishPattern_A_RING_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_A_RING_instance;
  }
  function SwedishPattern_A_UMLAUT_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_A_UMLAUT_instance;
  }
  function SwedishPattern_O_UMLAUT_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_O_UMLAUT_instance;
  }
  function SwedishPattern_Y_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_Y_instance;
  }
  function SwedishPattern_U_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_U_instance;
  }
  function SwedishPattern_NG_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_NG_instance;
  }
  function SwedishPattern_NK_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_NK_instance;
  }
  function SwedishPattern_D_SILENT_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_D_SILENT_instance;
  }
  function SwedishPattern_G_SILENT_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_G_SILENT_instance;
  }
  function SwedishPattern_TION_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_TION_instance;
  }
  function SwedishPattern_IGHET_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_IGHET_instance;
  }
  function SwedishPattern_LIGEN_getInstance() {
    SwedishPattern_initEntries();
    return SwedishPattern_LIGEN_instance;
  }
  function NorwegianPattern_SJ_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_SJ_instance;
  }
  function NorwegianPattern_SKJ_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_SKJ_instance;
  }
  function NorwegianPattern_KJ_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_KJ_instance;
  }
  function NorwegianPattern_HJ_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_HJ_instance;
  }
  function NorwegianPattern_RS_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_RS_instance;
  }
  function NorwegianPattern_RD_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_RD_instance;
  }
  function NorwegianPattern_RN_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_RN_instance;
  }
  function NorwegianPattern_RT_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_RT_instance;
  }
  function NorwegianPattern_RL_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_RL_instance;
  }
  function NorwegianPattern_A_RING_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_A_RING_instance;
  }
  function NorwegianPattern_AE_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_AE_instance;
  }
  function NorwegianPattern_O_SLASH_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_O_SLASH_instance;
  }
  function NorwegianPattern_Y_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_Y_instance;
  }
  function NorwegianPattern_EI_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_EI_instance;
  }
  function NorwegianPattern_AI_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_AI_instance;
  }
  function NorwegianPattern_NG_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_NG_instance;
  }
  function NorwegianPattern_SJON_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_SJON_instance;
  }
  function NorwegianPattern_HET_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_HET_instance;
  }
  function NorwegianPattern_ELSE_getInstance() {
    NorwegianPattern_initEntries();
    return NorwegianPattern_ELSE_instance;
  }
  function DanishPattern_D_SOFT_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_D_SOFT_instance;
  }
  function DanishPattern_G_SILENT_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_G_SILENT_instance;
  }
  function DanishPattern_D_SILENT_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_D_SILENT_instance;
  }
  function DanishPattern_A_RING_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_A_RING_instance;
  }
  function DanishPattern_AE_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_AE_instance;
  }
  function DanishPattern_O_SLASH_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_O_SLASH_instance;
  }
  function DanishPattern_Y_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_Y_instance;
  }
  function DanishPattern_EJ_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_EJ_instance;
  }
  function DanishPattern_AJ_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_AJ_instance;
  }
  function DanishPattern_OJ_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_OJ_instance;
  }
  function DanishPattern_NG_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_NG_instance;
  }
  function DanishPattern_HV_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_HV_instance;
  }
  function DanishPattern_HJ_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_HJ_instance;
  }
  function DanishPattern_TION_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_TION_instance;
  }
  function DanishPattern_ELSE_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_ELSE_instance;
  }
  function DanishPattern_HED_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_HED_instance;
  }
  function DanishPattern_IGHED_getInstance() {
    DanishPattern_initEntries();
    return DanishPattern_IGHED_instance;
  }
  function FinnishPattern_KK_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_KK_instance;
  }
  function FinnishPattern_PP_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_PP_instance;
  }
  function FinnishPattern_TT_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_TT_instance;
  }
  function FinnishPattern_SS_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_SS_instance;
  }
  function FinnishPattern_LL_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_LL_instance;
  }
  function FinnishPattern_MM_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_MM_instance;
  }
  function FinnishPattern_NN_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_NN_instance;
  }
  function FinnishPattern_RR_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_RR_instance;
  }
  function FinnishPattern_AA_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_AA_instance;
  }
  function FinnishPattern_EE_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_EE_instance;
  }
  function FinnishPattern_II_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_II_instance;
  }
  function FinnishPattern_OO_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_OO_instance;
  }
  function FinnishPattern_UU_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_UU_instance;
  }
  function FinnishPattern_YY_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_YY_instance;
  }
  function FinnishPattern_AA_UMLAUT_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_AA_UMLAUT_instance;
  }
  function FinnishPattern_OO_UMLAUT_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_OO_UMLAUT_instance;
  }
  function FinnishPattern_A_UMLAUT_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_A_UMLAUT_instance;
  }
  function FinnishPattern_O_UMLAUT_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_O_UMLAUT_instance;
  }
  function FinnishPattern_Y_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_Y_instance;
  }
  function FinnishPattern_NK_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_NK_instance;
  }
  function FinnishPattern_NG_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_NG_instance;
  }
  function FinnishPattern_INEN_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_INEN_instance;
  }
  function FinnishPattern_INEN_GEN_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_INEN_GEN_instance;
  }
  function FinnishPattern_SSA_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_SSA_instance;
  }
  function FinnishPattern_STA_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_STA_instance;
  }
  function FinnishPattern_LLE_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_LLE_instance;
  }
  function FinnishPattern_LTA_getInstance() {
    FinnishPattern_initEntries();
    return FinnishPattern_LTA_instance;
  }
  function GreekPattern_AI_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_AI_instance;
  }
  function GreekPattern_EI_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_EI_instance;
  }
  function GreekPattern_OI_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_OI_instance;
  }
  function GreekPattern_UI_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_UI_instance;
  }
  function GreekPattern_AU_V_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_AU_V_instance;
  }
  function GreekPattern_AU_F_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_AU_F_instance;
  }
  function GreekPattern_EU_V_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_EU_V_instance;
  }
  function GreekPattern_EU_F_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_EU_F_instance;
  }
  function GreekPattern_OU_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_OU_instance;
  }
  function GreekPattern_MP_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_MP_instance;
  }
  function GreekPattern_MP_MB_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_MP_MB_instance;
  }
  function GreekPattern_NT_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_NT_instance;
  }
  function GreekPattern_NT_ND_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_NT_ND_instance;
  }
  function GreekPattern_GK_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_GK_instance;
  }
  function GreekPattern_GK_NG_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_GK_NG_instance;
  }
  function GreekPattern_GG_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_GG_instance;
  }
  function GreekPattern_GCH_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_GCH_instance;
  }
  function GreekPattern_GX_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_GX_instance;
  }
  function GreekPattern_THETA_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_THETA_instance;
  }
  function GreekPattern_CHI_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_CHI_instance;
  }
  function GreekPattern_PSI_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_PSI_instance;
  }
  function GreekPattern_XI_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_XI_instance;
  }
  function GreekPattern_OS_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_OS_instance;
  }
  function GreekPattern_IS_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_IS_instance;
  }
  function GreekPattern_A_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_A_instance;
  }
  function GreekPattern_I_getInstance() {
    GreekPattern_initEntries();
    return GreekPattern_I_instance;
  }
  function RussianPattern_SOFT_T_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_SOFT_T_instance;
  }
  function RussianPattern_SOFT_D_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_SOFT_D_instance;
  }
  function RussianPattern_SOFT_N_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_SOFT_N_instance;
  }
  function RussianPattern_SOFT_L_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_SOFT_L_instance;
  }
  function RussianPattern_SOFT_S_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_SOFT_S_instance;
  }
  function RussianPattern_YA_INIT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YA_INIT_instance;
  }
  function RussianPattern_YE_INIT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YE_INIT_instance;
  }
  function RussianPattern_YO_INIT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YO_INIT_instance;
  }
  function RussianPattern_YU_INIT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YU_INIT_instance;
  }
  function RussianPattern_YA_SOFT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YA_SOFT_instance;
  }
  function RussianPattern_YE_SOFT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YE_SOFT_instance;
  }
  function RussianPattern_YO_SOFT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YO_SOFT_instance;
  }
  function RussianPattern_YU_SOFT_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_YU_SOFT_instance;
  }
  function RussianPattern_HARD_YA_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_HARD_YA_instance;
  }
  function RussianPattern_HARD_YE_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_HARD_YE_instance;
  }
  function RussianPattern_B_FINAL_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_B_FINAL_instance;
  }
  function RussianPattern_V_FINAL_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_V_FINAL_instance;
  }
  function RussianPattern_G_FINAL_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_G_FINAL_instance;
  }
  function RussianPattern_D_FINAL_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_D_FINAL_instance;
  }
  function RussianPattern_Z_FINAL_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_Z_FINAL_instance;
  }
  function RussianPattern_ZH_FINAL_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_ZH_FINAL_instance;
  }
  function RussianPattern_SHCH_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_SHCH_instance;
  }
  function RussianPattern_TS_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_TS_instance;
  }
  function RussianPattern_CH_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_CH_instance;
  }
  function RussianPattern_OGO_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_OGO_instance;
  }
  function RussianPattern_EGO_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_EGO_instance;
  }
  function RussianPattern_TION_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_TION_instance;
  }
  function RussianPattern_NOST_getInstance() {
    RussianPattern_initEntries();
    return RussianPattern_NOST_instance;
  }
  function PinyinPattern_ZH_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_ZH_instance;
  }
  function PinyinPattern_CH_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_CH_instance;
  }
  function PinyinPattern_SH_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_SH_instance;
  }
  function PinyinPattern_R_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_R_instance;
  }
  function PinyinPattern_J_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_J_instance;
  }
  function PinyinPattern_Q_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_Q_instance;
  }
  function PinyinPattern_X_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_X_instance;
  }
  function PinyinPattern_Z_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_Z_instance;
  }
  function PinyinPattern_C_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_C_instance;
  }
  function PinyinPattern_S_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_S_instance;
  }
  function PinyinPattern_ZHI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_ZHI_instance;
  }
  function PinyinPattern_CHI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_CHI_instance;
  }
  function PinyinPattern_SHI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_SHI_instance;
  }
  function PinyinPattern_RI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_RI_instance;
  }
  function PinyinPattern_ZI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_ZI_instance;
  }
  function PinyinPattern_CI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_CI_instance;
  }
  function PinyinPattern_SI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_SI_instance;
  }
  function PinyinPattern_IA_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_IA_instance;
  }
  function PinyinPattern_IE_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_IE_instance;
  }
  function PinyinPattern_IU_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_IU_instance;
  }
  function PinyinPattern_UO_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_UO_instance;
  }
  function PinyinPattern_UE_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_UE_instance;
  }
  function PinyinPattern_UA_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_UA_instance;
  }
  function PinyinPattern_UI_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_UI_instance;
  }
  function PinyinPattern_UN_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_UN_instance;
  }
  function PinyinPattern_UAN_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_UAN_instance;
  }
  function PinyinPattern_AN_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_AN_instance;
  }
  function PinyinPattern_EN_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_EN_instance;
  }
  function PinyinPattern_IN_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_IN_instance;
  }
  function PinyinPattern_ANG_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_ANG_instance;
  }
  function PinyinPattern_ENG_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_ENG_instance;
  }
  function PinyinPattern_ING_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_ING_instance;
  }
  function PinyinPattern_ONG_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_ONG_instance;
  }
  function PinyinPattern_TONE1_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_TONE1_instance;
  }
  function PinyinPattern_TONE2_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_TONE2_instance;
  }
  function PinyinPattern_TONE3_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_TONE3_instance;
  }
  function PinyinPattern_TONE4_getInstance() {
    PinyinPattern_initEntries();
    return PinyinPattern_TONE4_instance;
  }
  function SwahiliPattern_MB_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_MB_instance;
  }
  function SwahiliPattern_ND_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_ND_instance;
  }
  function SwahiliPattern_NG_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_NG_instance;
  }
  function SwahiliPattern_NJ_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_NJ_instance;
  }
  function SwahiliPattern_NZ_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_NZ_instance;
  }
  function SwahiliPattern_NG_APOSTROPHE_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_NG_APOSTROPHE_instance;
  }
  function SwahiliPattern_CH_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_CH_instance;
  }
  function SwahiliPattern_SH_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_SH_instance;
  }
  function SwahiliPattern_DH_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_DH_instance;
  }
  function SwahiliPattern_TH_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_TH_instance;
  }
  function SwahiliPattern_GH_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_GH_instance;
  }
  function SwahiliPattern_NY_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_NY_instance;
  }
  function SwahiliPattern_M_PREFIX_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_M_PREFIX_instance;
  }
  function SwahiliPattern_WA_PREFIX_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_WA_PREFIX_instance;
  }
  function SwahiliPattern_KI_PREFIX_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_KI_PREFIX_instance;
  }
  function SwahiliPattern_VI_PREFIX_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_VI_PREFIX_instance;
  }
  function SwahiliPattern_A_FINAL_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_A_FINAL_instance;
  }
  function SwahiliPattern_I_FINAL_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_I_FINAL_instance;
  }
  function SwahiliPattern_U_FINAL_getInstance() {
    SwahiliPattern_initEntries();
    return SwahiliPattern_U_FINAL_instance;
  }
  function MalayPattern_NG_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_NG_instance;
  }
  function MalayPattern_NY_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_NY_instance;
  }
  function MalayPattern_SY_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_SY_instance;
  }
  function MalayPattern_C_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_C_instance;
  }
  function MalayPattern_J_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_J_instance;
  }
  function MalayPattern_KH_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_KH_instance;
  }
  function MalayPattern_GH_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_GH_instance;
  }
  function MalayPattern_E_PEPET_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_E_PEPET_instance;
  }
  function MalayPattern_E_TALING_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_E_TALING_instance;
  }
  function MalayPattern_MEM_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_MEM_instance;
  }
  function MalayPattern_MEN_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_MEN_instance;
  }
  function MalayPattern_MENG_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_MENG_instance;
  }
  function MalayPattern_MENY_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_MENY_instance;
  }
  function MalayPattern_BER_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_BER_instance;
  }
  function MalayPattern_TER_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_TER_instance;
  }
  function MalayPattern_KAN_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_KAN_instance;
  }
  function MalayPattern_I_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_I_instance;
  }
  function MalayPattern_AN_getInstance() {
    MalayPattern_initEntries();
    return MalayPattern_AN_instance;
  }
  function syllablesToIpa($this, syllables) {
    return joinToString(syllables, '', VOID, VOID, VOID, VOID, KeyLanguage$Companion$syllablesToIpa$lambda);
  }
  function ipaForLang($this, word, lang) {
    var tmp0_elvis_lhs = lang.ub(word);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.text.lowercase' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$1 = word.toLowerCase();
      tmp = takeLast(tmp$ret$1, 3);
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
  function KeyLanguage$Companion$HEBREW$lambda(it) {
    return Companion_getInstance_39().mu(it);
  }
  function KeyLanguage$Companion$HEBREW$lambda_0(it) {
    return IpaColor_getInstance().rk(it);
  }
  function KeyLanguage$Companion$ARABIC$lambda(it) {
    return Companion_getInstance_39().nu(it);
  }
  function KeyLanguage$Companion$ARABIC$lambda_0(it) {
    return syllablesToIpa(Companion_getInstance_35(), Companion_getInstance_39().nu(it));
  }
  function KeyLanguage$Companion$RUSSIAN$lambda(it) {
    return Companion_getInstance_39().ou(it);
  }
  function KeyLanguage$Companion$RUSSIAN$lambda_0(it) {
    return syllablesToIpa(Companion_getInstance_35(), Companion_getInstance_39().ou(it));
  }
  function KeyLanguage$Companion$GREEK$lambda(it) {
    return Companion_getInstance_39().pu(it);
  }
  function KeyLanguage$Companion$GREEK$lambda_0(it) {
    return syllablesToIpa(Companion_getInstance_35(), Companion_getInstance_39().pu(it));
  }
  function KeyLanguage$Companion$HINDI$lambda(it) {
    return Companion_getInstance_39().qu(it);
  }
  function KeyLanguage$Companion$HINDI$lambda_0(it) {
    return syllablesToIpa(Companion_getInstance_35(), Companion_getInstance_39().qu(it));
  }
  function KeyLanguage$Companion$JAPANESE$lambda(it) {
    return Companion_getInstance_39().ru(it);
  }
  function KeyLanguage$Companion$JAPANESE$lambda_0(it) {
    return syllablesToIpa(Companion_getInstance_35(), Companion_getInstance_39().ru(it));
  }
  function KeyLanguage$Companion$KOREAN$lambda(it) {
    return Companion_getInstance_39().su(it);
  }
  function KeyLanguage$Companion$KOREAN$lambda_0(it) {
    return syllablesToIpa(Companion_getInstance_35(), Companion_getInstance_39().su(it));
  }
  function KeyLanguage$Companion$CHINESE$lambda(it) {
    return Companion_getInstance_39().tu(it);
  }
  function KeyLanguage$Companion$CHINESE$lambda_0(it) {
    return syllablesToIpa(Companion_getInstance_35(), Companion_getInstance_39().tu(it));
  }
  function KeyLanguage$Companion$ENGLISH$lambda(it) {
    return Companion_getInstance_39().uu(it);
  }
  function KeyLanguage$Companion$ENGLISH$lambda_0(it) {
    return IpaColor_getInstance().qk(it);
  }
  function KeyLanguage$Companion$GERMAN$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_DE_getInstance());
  }
  function KeyLanguage$Companion$GERMAN$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_DE_getInstance());
  }
  function KeyLanguage$Companion$FRENCH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_FR_getInstance());
  }
  function KeyLanguage$Companion$FRENCH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_FR_getInstance());
  }
  function KeyLanguage$Companion$SPANISH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_ES_getInstance());
  }
  function KeyLanguage$Companion$SPANISH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_ES_getInstance());
  }
  function KeyLanguage$Companion$ITALIAN$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_IT_getInstance());
  }
  function KeyLanguage$Companion$ITALIAN$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_IT_getInstance());
  }
  function KeyLanguage$Companion$PORTUGUESE$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_PT_getInstance());
  }
  function KeyLanguage$Companion$PORTUGUESE$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_PT_getInstance());
  }
  function KeyLanguage$Companion$DUTCH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_NL_getInstance());
  }
  function KeyLanguage$Companion$DUTCH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_NL_getInstance());
  }
  function KeyLanguage$Companion$POLISH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_PL_getInstance());
  }
  function KeyLanguage$Companion$POLISH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_PL_getInstance());
  }
  function KeyLanguage$Companion$TURKISH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_TR_getInstance());
  }
  function KeyLanguage$Companion$TURKISH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_TR_getInstance());
  }
  function KeyLanguage$Companion$DANISH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_DA_getInstance());
  }
  function KeyLanguage$Companion$DANISH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_DA_getInstance());
  }
  function KeyLanguage$Companion$FINNISH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_FI_getInstance());
  }
  function KeyLanguage$Companion$FINNISH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_FI_getInstance());
  }
  function KeyLanguage$Companion$NORWEGIAN$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_NO_getInstance());
  }
  function KeyLanguage$Companion$NORWEGIAN$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_NO_getInstance());
  }
  function KeyLanguage$Companion$SWEDISH$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_SV_getInstance());
  }
  function KeyLanguage$Companion$SWEDISH$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_SV_getInstance());
  }
  function KeyLanguage$Companion$MALAY$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_MS_getInstance());
  }
  function KeyLanguage$Companion$MALAY$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_MS_getInstance());
  }
  function KeyLanguage$Companion$SWAHILI$lambda(it) {
    return Companion_getInstance_39().vu(it, Lang_SW_getInstance());
  }
  function KeyLanguage$Companion$SWAHILI$lambda_0(it) {
    return ipaForLang(Companion_getInstance_35(), it, Lang_SW_getInstance());
  }
  function KeyLanguage$Companion$syllablesToIpa$lambda(it) {
    return it.wu_1 + it.xu_1;
  }
  function Companion_35() {
    Companion_instance_35 = this;
    var tmp = this;
    var tmp_0 = Lang_HE_getInstance();
    var tmp_1 = TextDirection_RTL_getInstance();
    var tmp_2 = KeyLanguage$Companion$HEBREW$lambda;
    tmp.av_1 = new KeyLanguage(tmp_0, '\u05E2\u05D1\u05E8\u05D9\u05EA', 'Hebrew', tmp_1, tmp_2, KeyLanguage$Companion$HEBREW$lambda_0, Companion_getInstance_36().aw_1);
    var tmp_3 = this;
    var tmp_4 = Lang_AR_getInstance();
    var tmp_5 = TextDirection_RTL_getInstance();
    var tmp_6 = KeyLanguage$Companion$ARABIC$lambda;
    tmp_3.bv_1 = new KeyLanguage(tmp_4, '\u0627\u0644\u0639\u0631\u0628\u064A\u0629', 'Arabic', tmp_5, tmp_6, KeyLanguage$Companion$ARABIC$lambda_0, Companion_getInstance_36().bw_1);
    var tmp_7 = this;
    var tmp_8 = Lang_RU_getInstance();
    var tmp_9 = TextDirection_LTR_getInstance();
    var tmp_10 = KeyLanguage$Companion$RUSSIAN$lambda;
    tmp_7.cv_1 = new KeyLanguage(tmp_8, '\u0420\u0443\u0441\u0441\u043A\u0438\u0439', 'Russian', tmp_9, tmp_10, KeyLanguage$Companion$RUSSIAN$lambda_0, Companion_getInstance_36().cw_1);
    var tmp_11 = this;
    var tmp_12 = Lang_EL_getInstance();
    var tmp_13 = TextDirection_LTR_getInstance();
    var tmp_14 = KeyLanguage$Companion$GREEK$lambda;
    tmp_11.dv_1 = new KeyLanguage(tmp_12, '\u0395\u03BB\u03BB\u03B7\u03BD\u03B9\u03BA\u03AC', 'Greek', tmp_13, tmp_14, KeyLanguage$Companion$GREEK$lambda_0, Companion_getInstance_36().dw_1);
    var tmp_15 = this;
    var tmp_16 = Lang_HI_getInstance();
    var tmp_17 = TextDirection_LTR_getInstance();
    var tmp_18 = KeyLanguage$Companion$HINDI$lambda;
    tmp_15.ev_1 = new KeyLanguage(tmp_16, '\u0939\u093F\u0928\u094D\u0926\u0940', 'Hindi', tmp_17, tmp_18, KeyLanguage$Companion$HINDI$lambda_0, Companion_getInstance_36().ew_1);
    var tmp_19 = this;
    var tmp_20 = Lang_JA_getInstance();
    var tmp_21 = TextDirection_LTR_getInstance();
    var tmp_22 = KeyLanguage$Companion$JAPANESE$lambda;
    tmp_19.fv_1 = new KeyLanguage(tmp_20, '\u65E5\u672C\u8A9E', 'Japanese', tmp_21, tmp_22, KeyLanguage$Companion$JAPANESE$lambda_0, Companion_getInstance_36().fw_1);
    var tmp_23 = this;
    var tmp_24 = Lang_KO_getInstance();
    var tmp_25 = TextDirection_LTR_getInstance();
    var tmp_26 = KeyLanguage$Companion$KOREAN$lambda;
    tmp_23.gv_1 = new KeyLanguage(tmp_24, '\uD55C\uAD6D\uC5B4', 'Korean', tmp_25, tmp_26, KeyLanguage$Companion$KOREAN$lambda_0, Companion_getInstance_36().gw_1);
    var tmp_27 = this;
    var tmp_28 = Lang_ZH_getInstance();
    var tmp_29 = TextDirection_LTR_getInstance();
    var tmp_30 = KeyLanguage$Companion$CHINESE$lambda;
    tmp_27.hv_1 = new KeyLanguage(tmp_28, '\u4E2D\u6587', 'Chinese', tmp_29, tmp_30, KeyLanguage$Companion$CHINESE$lambda_0, Companion_getInstance_36().hw_1);
    var tmp_31 = this;
    var tmp_32 = Lang_EN_getInstance();
    var tmp_33 = TextDirection_LTR_getInstance();
    var tmp_34 = KeyLanguage$Companion$ENGLISH$lambda;
    tmp_31.iv_1 = new KeyLanguage(tmp_32, 'English', 'English', tmp_33, tmp_34, KeyLanguage$Companion$ENGLISH$lambda_0, Companion_getInstance_36().iw_1);
    var tmp_35 = this;
    var tmp_36 = Lang_DE_getInstance();
    var tmp_37 = TextDirection_LTR_getInstance();
    var tmp_38 = KeyLanguage$Companion$GERMAN$lambda;
    tmp_35.jv_1 = new KeyLanguage(tmp_36, 'Deutsch', 'German', tmp_37, tmp_38, KeyLanguage$Companion$GERMAN$lambda_0, Companion_getInstance_36().jw_1);
    var tmp_39 = this;
    var tmp_40 = Lang_FR_getInstance();
    var tmp_41 = TextDirection_LTR_getInstance();
    var tmp_42 = KeyLanguage$Companion$FRENCH$lambda;
    tmp_39.kv_1 = new KeyLanguage(tmp_40, 'Fran\xE7ais', 'French', tmp_41, tmp_42, KeyLanguage$Companion$FRENCH$lambda_0, Companion_getInstance_36().kw_1);
    var tmp_43 = this;
    var tmp_44 = Lang_ES_getInstance();
    var tmp_45 = TextDirection_LTR_getInstance();
    var tmp_46 = KeyLanguage$Companion$SPANISH$lambda;
    tmp_43.lv_1 = new KeyLanguage(tmp_44, 'Espa\xF1ol', 'Spanish', tmp_45, tmp_46, KeyLanguage$Companion$SPANISH$lambda_0, Companion_getInstance_36().lw_1);
    var tmp_47 = this;
    var tmp_48 = Lang_IT_getInstance();
    var tmp_49 = TextDirection_LTR_getInstance();
    var tmp_50 = KeyLanguage$Companion$ITALIAN$lambda;
    tmp_47.mv_1 = new KeyLanguage(tmp_48, 'Italiano', 'Italian', tmp_49, tmp_50, KeyLanguage$Companion$ITALIAN$lambda_0, Companion_getInstance_36().mw_1);
    var tmp_51 = this;
    var tmp_52 = Lang_PT_getInstance();
    var tmp_53 = TextDirection_LTR_getInstance();
    var tmp_54 = KeyLanguage$Companion$PORTUGUESE$lambda;
    tmp_51.nv_1 = new KeyLanguage(tmp_52, 'Portugu\xEAs', 'Portuguese', tmp_53, tmp_54, KeyLanguage$Companion$PORTUGUESE$lambda_0, Companion_getInstance_36().nw_1);
    var tmp_55 = this;
    var tmp_56 = Lang_NL_getInstance();
    var tmp_57 = TextDirection_LTR_getInstance();
    var tmp_58 = KeyLanguage$Companion$DUTCH$lambda;
    tmp_55.ov_1 = new KeyLanguage(tmp_56, 'Nederlands', 'Dutch', tmp_57, tmp_58, KeyLanguage$Companion$DUTCH$lambda_0, Companion_getInstance_36().ow_1);
    var tmp_59 = this;
    var tmp_60 = Lang_PL_getInstance();
    var tmp_61 = TextDirection_LTR_getInstance();
    var tmp_62 = KeyLanguage$Companion$POLISH$lambda;
    tmp_59.pv_1 = new KeyLanguage(tmp_60, 'Polski', 'Polish', tmp_61, tmp_62, KeyLanguage$Companion$POLISH$lambda_0, Companion_getInstance_36().pw_1);
    var tmp_63 = this;
    var tmp_64 = Lang_TR_getInstance();
    var tmp_65 = TextDirection_LTR_getInstance();
    var tmp_66 = KeyLanguage$Companion$TURKISH$lambda;
    tmp_63.qv_1 = new KeyLanguage(tmp_64, 'T\xFCrk\xE7e', 'Turkish', tmp_65, tmp_66, KeyLanguage$Companion$TURKISH$lambda_0, Companion_getInstance_36().qw_1);
    var tmp_67 = this;
    var tmp_68 = Lang_DA_getInstance();
    var tmp_69 = TextDirection_LTR_getInstance();
    var tmp_70 = KeyLanguage$Companion$DANISH$lambda;
    tmp_67.rv_1 = new KeyLanguage(tmp_68, 'Dansk', 'Danish', tmp_69, tmp_70, KeyLanguage$Companion$DANISH$lambda_0, Companion_getInstance_36().rw_1);
    var tmp_71 = this;
    var tmp_72 = Lang_FI_getInstance();
    var tmp_73 = TextDirection_LTR_getInstance();
    var tmp_74 = KeyLanguage$Companion$FINNISH$lambda;
    tmp_71.sv_1 = new KeyLanguage(tmp_72, 'Suomi', 'Finnish', tmp_73, tmp_74, KeyLanguage$Companion$FINNISH$lambda_0, Companion_getInstance_36().sw_1);
    var tmp_75 = this;
    var tmp_76 = Lang_NO_getInstance();
    var tmp_77 = TextDirection_LTR_getInstance();
    var tmp_78 = KeyLanguage$Companion$NORWEGIAN$lambda;
    tmp_75.tv_1 = new KeyLanguage(tmp_76, 'Norsk', 'Norwegian', tmp_77, tmp_78, KeyLanguage$Companion$NORWEGIAN$lambda_0, Companion_getInstance_36().tw_1);
    var tmp_79 = this;
    var tmp_80 = Lang_SV_getInstance();
    var tmp_81 = TextDirection_LTR_getInstance();
    var tmp_82 = KeyLanguage$Companion$SWEDISH$lambda;
    tmp_79.uv_1 = new KeyLanguage(tmp_80, 'Svenska', 'Swedish', tmp_81, tmp_82, KeyLanguage$Companion$SWEDISH$lambda_0, Companion_getInstance_36().uw_1);
    var tmp_83 = this;
    var tmp_84 = Lang_MS_getInstance();
    var tmp_85 = TextDirection_LTR_getInstance();
    var tmp_86 = KeyLanguage$Companion$MALAY$lambda;
    tmp_83.vv_1 = new KeyLanguage(tmp_84, 'Bahasa Melayu', 'Malay', tmp_85, tmp_86, KeyLanguage$Companion$MALAY$lambda_0, Companion_getInstance_36().vw_1);
    var tmp_87 = this;
    var tmp_88 = Lang_SW_getInstance();
    var tmp_89 = TextDirection_LTR_getInstance();
    var tmp_90 = KeyLanguage$Companion$SWAHILI$lambda;
    tmp_87.wv_1 = new KeyLanguage(tmp_88, 'Kiswahili', 'Swahili', tmp_89, tmp_90, KeyLanguage$Companion$SWAHILI$lambda_0, Companion_getInstance_36().ww_1);
    this.xv_1 = listOf([this.av_1, this.bv_1, this.cv_1, this.dv_1, this.ev_1, this.fv_1, this.gv_1, this.hv_1, this.iv_1, this.jv_1, this.kv_1, this.lv_1, this.mv_1, this.nv_1, this.ov_1, this.pv_1, this.qv_1, this.rv_1, this.sv_1, this.tv_1, this.uv_1, this.vv_1, this.wv_1]);
    var tmp_91 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = this.xv_1;
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$0 = element.xm();
      destination.l3(tmp$ret$0, element);
    }
    tmp_91.yv_1 = destination;
    var tmp_92 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = this.xv_1;
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$3 = element_0.xw_1;
      destination_0.l3(tmp$ret$3, element_0);
    }
    tmp_92.zv_1 = destination_0;
  }
  protoOf(Companion_35).ex = function () {
    return this.xv_1.l();
  };
  protoOf(Companion_35).rb = function (code) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = code.toLowerCase();
    return this.yv_1.q1(tmp$ret$1);
  };
  protoOf(Companion_35).fx = function () {
    // Inline function 'kotlin.collections.flatMap' call
    var tmp0 = this.xv_1;
    // Inline function 'kotlin.collections.flatMapTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      // Inline function 'kotlin.collections.filter' call
      var tmp0_0 = Companion_getInstance_35().xv_1;
      // Inline function 'kotlin.collections.filterTo' call
      var destination_0 = ArrayList_init_$Create$();
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (!element_0.equals(element)) {
          destination_0.g(element_0);
        }
      }
      // Inline function 'kotlin.collections.map' call
      // Inline function 'kotlin.collections.mapTo' call
      var destination_1 = ArrayList_init_$Create$_0(collectionSizeOrDefault(destination_0, 10));
      var _iterator__ex2g4s_1 = destination_0.i();
      while (_iterator__ex2g4s_1.j()) {
        var item = _iterator__ex2g4s_1.k();
        var tmp$ret$3 = new TranslationPair(element, item);
        destination_1.g(tmp$ret$3);
      }
      var list = destination_1;
      addAll(destination, list);
    }
    return destination;
  };
  var Companion_instance_35;
  function Companion_getInstance_35() {
    if (Companion_instance_35 == null)
      new Companion_35();
    return Companion_instance_35;
  }
  function KeyLanguage(lang, nativeName, englishName, direction, syllableParser, ipaConverter, aiPromptHints) {
    Companion_getInstance_35();
    direction = direction === VOID ? TextDirection_LTR_getInstance() : direction;
    this.xw_1 = lang;
    this.yw_1 = nativeName;
    this.zw_1 = englishName;
    this.ax_1 = direction;
    this.bx_1 = syllableParser;
    this.cx_1 = ipaConverter;
    this.dx_1 = aiPromptHints;
  }
  protoOf(KeyLanguage).xm = function () {
    return this.xw_1.nb_1;
  };
  protoOf(KeyLanguage).gx = function () {
    return this.xw_1.ob_1;
  };
  protoOf(KeyLanguage).hx = function (word) {
    return this.bx_1(word);
  };
  protoOf(KeyLanguage).toString = function () {
    return 'KeyLanguage(lang=' + this.xw_1.toString() + ', nativeName=' + this.yw_1 + ', englishName=' + this.zw_1 + ', direction=' + this.ax_1.toString() + ', syllableParser=' + toString_1(this.bx_1) + ', ipaConverter=' + toString_1(this.cx_1) + ', aiPromptHints=' + this.dx_1.toString() + ')';
  };
  protoOf(KeyLanguage).hashCode = function () {
    var result = this.xw_1.hashCode();
    result = imul(result, 31) + getStringHashCode(this.yw_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.zw_1) | 0;
    result = imul(result, 31) + this.ax_1.hashCode() | 0;
    result = imul(result, 31) + hashCode(this.bx_1) | 0;
    result = imul(result, 31) + hashCode(this.cx_1) | 0;
    result = imul(result, 31) + this.dx_1.hashCode() | 0;
    return result;
  };
  protoOf(KeyLanguage).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof KeyLanguage))
      return false;
    if (!this.xw_1.equals(other.xw_1))
      return false;
    if (!(this.yw_1 === other.yw_1))
      return false;
    if (!(this.zw_1 === other.zw_1))
      return false;
    if (!this.ax_1.equals(other.ax_1))
      return false;
    if (!equals(this.bx_1, other.bx_1))
      return false;
    if (!equals(this.cx_1, other.cx_1))
      return false;
    if (!this.dx_1.equals(other.dx_1))
      return false;
    return true;
  };
  var TextDirection_LTR_instance;
  var TextDirection_RTL_instance;
  var TextDirection_entriesInitialized;
  function TextDirection_initEntries() {
    if (TextDirection_entriesInitialized)
      return Unit_instance;
    TextDirection_entriesInitialized = true;
    TextDirection_LTR_instance = new TextDirection('LTR', 0);
    TextDirection_RTL_instance = new TextDirection('RTL', 1);
  }
  function TextDirection(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function Companion_36() {
    Companion_instance_36 = this;
    this.aw_1 = new LanguagePromptHints('Hebrew linguist', 'Consider Biblical vs Modern Hebrew register, Sephardic vs Ashkenazi pronunciation', 'Hebrew rhymes on final syllable stress; nikud affects vowel quality', 'Root-based morphology (shoresh); verb binyanim affect meaning', 'Grammatical gender affects all nouns, adjectives, verbs', 'Nikud (vowel points) optional but affects pronunciation; dagesh changes consonants');
    this.bw_1 = new LanguagePromptHints('Arabic linguist', 'Consider Classical vs Modern Standard vs dialectal register', 'Arabic poetry has rich rhyme traditions (qafiya); root echoes matter', 'Triliteral root system; verb forms (awzan) carry semantic weight', 'Grammatical gender; dual number exists', 'Harakat (short vowels) usually omitted; affects pronunciation');
    this.cw_1 = new LanguagePromptHints('Russian linguist', 'Consider formal vs informal register; Church Slavonic influences in poetry', 'Russian favors rich rhymes; stress patterns crucial (\u0443\u0434\u0430\u0440\u0435\u043D\u0438\u0435)', 'Case system; aspect pairs; word order flexible', 'Three genders; affects adjective/verb agreement');
    this.dw_1 = new LanguagePromptHints('Greek linguist', 'Consider Ancient vs Modern Greek; Katharevousa vs Demotic', 'Stress-based rhythm; final syllable rhymes common', 'Cases (4 in Modern); verb aspect; article usage', 'Three genders; neuter plurals take singular verbs');
    this.ew_1 = new LanguagePromptHints('Hindi linguist', 'Sanskrit-derived vs Perso-Arabic vocabulary; formal vs casual', 'Hindi poetry has strong rhyme traditions; nasalization matters', 'SOV order; postpositions; ergativity in past tense', 'Two genders; affects verbs in past tense', "Devanagari; conjunct consonants; inherent 'a' vowel");
    this.fw_1 = new LanguagePromptHints('Japanese linguist', 'Consider keigo (politeness levels); literary vs colloquial', 'Japanese poetry favors mora count (5-7-5); less focus on end rhyme', 'SOV order; particles mark grammatical function; topic vs subject', VOID, 'Kanji + hiragana + katakana; kanji choice affects tone');
    this.gw_1 = new LanguagePromptHints('Korean linguist', 'Speech levels (\uC874\uB313\uB9D0 vs \uBC18\uB9D0); honorifics', 'Korean has syllable blocks; final consonants affect rhyme', 'SOV order; particles; verb endings carry much meaning', VOID, 'Hangul is alphabetic but arranged in syllable blocks');
    this.hw_1 = new LanguagePromptHints('Chinese linguist', 'Classical vs Modern Chinese; literary allusions important', 'Tones affect meaning; classical poetry has strict tonal patterns', 'No inflection; aspect markers; classifier system', VOID, 'Characters carry meaning; simplified vs traditional');
    this.iw_1 = new LanguagePromptHints('English linguist', 'Consider British vs American register; formal vs colloquial', 'English has rich rhyme traditions; stress-timed rhythm; slant rhymes accepted', 'SVO order; minimal inflection; phrasal verbs important');
    this.jw_1 = new LanguagePromptHints('German linguist', 'Consider formal (Sie) vs informal (du); regional variation', 'German allows compound words for creative rhyming', 'Case system (4 cases); verb-final in subordinate clauses', 'Three genders (der/die/das); affects all articles/adjectives');
    this.kw_1 = new LanguagePromptHints('French linguist', 'Distinguish formal literary vs conversational register', 'Classical French poetry: rime riche, alternating masculine/feminine endings', 'Liaison affects pronunciation; subjunctive mood matters', 'Two genders; affects articles, adjectives, past participles');
    this.lw_1 = new LanguagePromptHints('Spanish linguist', 'Consider Castilian vs Latin American; voseo regions', 'Asonancia (assonance) common; stress affects rhyme', 'Subjunctive heavily used; ser vs estar', 'Two genders; some nouns change meaning by gender');
    this.mw_1 = new LanguagePromptHints('Italian linguist', 'Rich poetic tradition (Dante, Petrarch); formal vs informal', 'Italian has abundant rhymes due to vowel-final words', 'Double consonants affect meaning; subjunctive important', 'Two genders; irregular plurals common');
    this.nw_1 = new LanguagePromptHints('Portuguese linguist', 'Consider European vs Brazilian Portuguese; formal vs colloquial', 'Portuguese has nasal vowels; rich lyrical tradition (Cam\xF5es, Pessoa)', 'Personal infinitive unique to Portuguese; subjunctive important', 'Two genders; augmentative/diminutive suffixes common');
    this.ow_1 = new LanguagePromptHints('Dutch linguist', 'Consider Netherlandic vs Flemish; formal vs informal (u/jij)', 'Dutch has guttural sounds (g, ch); compound words for rhyming', 'V2 word order; diminutives (-je) very common', 'Common/neuter gender (de/het)');
    this.pw_1 = new LanguagePromptHints('Polish linguist', 'Rich literary tradition; formal vs familiar', 'Polish has fixed penultimate stress; consonant clusters affect rhyme', 'Complex case system (7 cases); aspect pairs; flexible word order', 'Three genders in singular; virile/non-virile in plural');
    this.qw_1 = new LanguagePromptHints('Turkish linguist', 'Ottoman literary heritage; formal vs casual register', 'Vowel harmony affects suffixes; agglutinative morphology enables rhyming', 'SOV order; agglutinative; vowel harmony; no grammatical gender', VOID, 'Latin script since 1928; some Turkish-specific letters (\u011F, \u015F, \u0131)');
    this.rw_1 = new LanguagePromptHints('Danish linguist', 'Nordic literary tradition; formal vs informal', 'St\xF8d (glottal stop) affects rhyme; vowel-rich endings', 'V2 word order; definite article suffixed; two genders', 'Common/neuter gender (en/et)');
    this.sw_1 = new LanguagePromptHints('Finnish linguist', 'Kalevala meter tradition; unique among European languages', 'Vowel harmony; long/short vowels matter; alliteration traditional', '15 cases; agglutinative; no grammatical gender; no articles', VOID, 'Uses \xE4, \xF6; double letters indicate length');
    this.tw_1 = new LanguagePromptHints('Norwegian linguist', 'Bokm\xE5l vs Nynorsk; rich folk poetry tradition', 'Pitch accent affects meaning; similar to Swedish/Danish rhyme', 'V2 word order; definite article suffixed; three genders (or two)', 'Masculine/feminine/neuter (or common/neuter)');
    this.uw_1 = new LanguagePromptHints('Swedish linguist', 'Nobel Prize language; formal vs casual', 'Pitch accent (acute/grave); vowel length matters', 'V2 word order; definite article suffixed; two genders', 'Common/neuter gender (en/ett)');
    this.vw_1 = new LanguagePromptHints('Malay/Indonesian linguist', 'Pantun (quatrain) poetic tradition; formal vs informal register', 'Pantun has strict ABAB rhyme; often witty/allusive', 'No inflection; affixes modify meaning; no grammatical gender');
    this.ww_1 = new LanguagePromptHints('Swahili linguist', 'Bantu language with Arabic influences; East African literary tradition', 'Shairi poetry has strict meter; vowel-final words easy to rhyme', 'Noun class system (18 classes); agglutinative; SVO order');
  }
  var Companion_instance_36;
  function Companion_getInstance_36() {
    if (Companion_instance_36 == null)
      new Companion_36();
    return Companion_instance_36;
  }
  function LanguagePromptHints(linguistRole, culturalNotes, rhymeNotes, grammarNotes, genderNotes, scriptNotes) {
    Companion_getInstance_36();
    genderNotes = genderNotes === VOID ? null : genderNotes;
    scriptNotes = scriptNotes === VOID ? null : scriptNotes;
    this.ix_1 = linguistRole;
    this.jx_1 = culturalNotes;
    this.kx_1 = rhymeNotes;
    this.lx_1 = grammarNotes;
    this.mx_1 = genderNotes;
    this.nx_1 = scriptNotes;
  }
  protoOf(LanguagePromptHints).toString = function () {
    return 'LanguagePromptHints(linguistRole=' + this.ix_1 + ', culturalNotes=' + this.jx_1 + ', rhymeNotes=' + this.kx_1 + ', grammarNotes=' + this.lx_1 + ', genderNotes=' + this.mx_1 + ', scriptNotes=' + this.nx_1 + ')';
  };
  protoOf(LanguagePromptHints).hashCode = function () {
    var result = getStringHashCode(this.ix_1);
    result = imul(result, 31) + getStringHashCode(this.jx_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.kx_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.lx_1) | 0;
    result = imul(result, 31) + (this.mx_1 == null ? 0 : getStringHashCode(this.mx_1)) | 0;
    result = imul(result, 31) + (this.nx_1 == null ? 0 : getStringHashCode(this.nx_1)) | 0;
    return result;
  };
  protoOf(LanguagePromptHints).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof LanguagePromptHints))
      return false;
    if (!(this.ix_1 === other.ix_1))
      return false;
    if (!(this.jx_1 === other.jx_1))
      return false;
    if (!(this.kx_1 === other.kx_1))
      return false;
    if (!(this.lx_1 === other.lx_1))
      return false;
    if (!(this.mx_1 == other.mx_1))
      return false;
    if (!(this.nx_1 == other.nx_1))
      return false;
    return true;
  };
  function Companion_37() {
    Companion_instance_37 = this;
    this.ox_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().av_1);
    this.px_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().bv_1);
    this.qx_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().cv_1);
    this.rx_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().jv_1);
    this.sx_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().kv_1);
    this.tx_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().lv_1);
    this.ux_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().mv_1);
    this.vx_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().fv_1);
    this.wx_1 = new TranslationPair(Companion_getInstance_35().iv_1, Companion_getInstance_35().hv_1);
    this.xx_1 = new TranslationPair(Companion_getInstance_35().av_1, Companion_getInstance_35().iv_1);
    this.yx_1 = new TranslationPair(Companion_getInstance_35().kv_1, Companion_getInstance_35().iv_1);
    this.zx_1 = new TranslationPair(Companion_getInstance_35().jv_1, Companion_getInstance_35().iv_1);
    this.ay_1 = new TranslationPair(Companion_getInstance_35().lv_1, Companion_getInstance_35().iv_1);
    this.by_1 = new TranslationPair(Companion_getInstance_35().cv_1, Companion_getInstance_35().iv_1);
    this.cy_1 = new TranslationPair(Companion_getInstance_35().fv_1, Companion_getInstance_35().iv_1);
    this.dy_1 = new TranslationPair(Companion_getInstance_35().hv_1, Companion_getInstance_35().iv_1);
    this.ey_1 = new TranslationPair(Companion_getInstance_35().bv_1, Companion_getInstance_35().iv_1);
  }
  protoOf(Companion_37).fy = function (srcCode, tgtCode) {
    var tmp0_elvis_lhs = Companion_getInstance_35().rb(srcCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var src = tmp;
    var tmp1_elvis_lhs = Companion_getInstance_35().rb(tgtCode);
    var tmp_0;
    if (tmp1_elvis_lhs == null) {
      return null;
    } else {
      tmp_0 = tmp1_elvis_lhs;
    }
    var tgt = tmp_0;
    if (src.equals(tgt))
      return null;
    return new TranslationPair(src, tgt);
  };
  var Companion_instance_37;
  function Companion_getInstance_37() {
    if (Companion_instance_37 == null)
      new Companion_37();
    return Companion_instance_37;
  }
  function TranslationPair(source, target) {
    Companion_getInstance_37();
    this.gy_1 = source;
    this.hy_1 = target;
  }
  protoOf(TranslationPair).iy = function () {
    return this.gy_1.xm();
  };
  protoOf(TranslationPair).jy = function () {
    return this.hy_1.xm();
  };
  protoOf(TranslationPair).ky = function () {
    return this.gy_1.xm() + '\u2192' + this.hy_1.xm();
  };
  protoOf(TranslationPair).ly = function (context) {
    var srcHints = this.gy_1.dx_1;
    var tgtHints = this.hy_1.dx_1;
    // Inline function 'kotlin.text.buildString' call
    // Inline function 'kotlin.apply' call
    var this_0 = StringBuilder_init_$Create$();
    // Inline function 'kotlin.text.appendLine' call
    var value = 'You are a bilingual ' + this.gy_1.zw_1 + '-' + this.hy_1.zw_1 + ' linguist and poet.';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    this_0.m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.ifEmpty' call
    var tmp;
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(context) === 0) {
      tmp = 'Poetry translation from ' + this.gy_1.yw_1 + ' to ' + this.hy_1.yw_1;
    } else {
      tmp = context;
    }
    // Inline function 'kotlin.text.appendLine' call
    var value_0 = 'Context: ' + tmp;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_0).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    this_0.m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_1 = 'SOURCE LANGUAGE (' + this.gy_1.yw_1 + '):';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_1).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_2 = '  Cultural: ' + srcHints.jx_1;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_2).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_3 = '  Rhyme: ' + srcHints.kx_1;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_3).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_4 = '  Grammar: ' + srcHints.lx_1;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_4).m6(_Char___init__impl__6a9atx(10));
    var tmp0_safe_receiver = srcHints.mx_1;
    if (tmp0_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.text.appendLine' call
      var value_5 = '  Gender: ' + tmp0_safe_receiver;
      // Inline function 'kotlin.text.appendLine' call
      this_0.l6(value_5).m6(_Char___init__impl__6a9atx(10));
    }
    var tmp1_safe_receiver = srcHints.nx_1;
    if (tmp1_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.text.appendLine' call
      var value_6 = '  Script: ' + tmp1_safe_receiver;
      // Inline function 'kotlin.text.appendLine' call
      this_0.l6(value_6).m6(_Char___init__impl__6a9atx(10));
    }
    // Inline function 'kotlin.text.appendLine' call
    this_0.m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_7 = 'TARGET LANGUAGE (' + this.hy_1.yw_1 + '):';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_7).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_8 = '  Cultural: ' + tgtHints.jx_1;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_8).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_9 = '  Rhyme: ' + tgtHints.kx_1;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_9).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_10 = '  Grammar: ' + tgtHints.lx_1;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_10).m6(_Char___init__impl__6a9atx(10));
    var tmp2_safe_receiver = tgtHints.mx_1;
    if (tmp2_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.text.appendLine' call
      var value_11 = '  Gender: ' + tmp2_safe_receiver;
      // Inline function 'kotlin.text.appendLine' call
      this_0.l6(value_11).m6(_Char___init__impl__6a9atx(10));
    }
    var tmp3_safe_receiver = tgtHints.nx_1;
    if (tmp3_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.text.appendLine' call
      var value_12 = '  Script: ' + tmp3_safe_receiver;
      // Inline function 'kotlin.text.appendLine' call
      this_0.l6(value_12).m6(_Char___init__impl__6a9atx(10));
    }
    // Inline function 'kotlin.text.appendLine' call
    this_0.m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_13 = 'Analyze the ' + this.gy_1.yw_1 + ' source text and provide:';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_13).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_14 = 'A) WORDS: 3-5 key content words with IPA, ' + this.hy_1.zw_1 + ' synonyms, and cultural notes';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_14).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_15 = 'B) PAIRS: viable end-rhyme pairs from the synonym pool';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_15).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_16 = 'C) TARGETS: top ' + this.hy_1.zw_1 + ' words matching the ' + this.gy_1.yw_1 + ' IPA endings';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_16).m6(_Char___init__impl__6a9atx(10));
    return this_0.toString();
  };
  protoOf(TranslationPair).my = function (context) {
    var tgtHints = this.hy_1.dx_1;
    // Inline function 'kotlin.text.buildString' call
    // Inline function 'kotlin.apply' call
    var this_0 = StringBuilder_init_$Create$();
    // Inline function 'kotlin.text.appendLine' call
    var value = 'You are a ' + this.hy_1.zw_1 + ' poet refining a translation from ' + this.gy_1.yw_1 + '.';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    this_0.m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(context).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    this_0.m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_0 = 'THE 6 PATHS:';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_0).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_1 = '1 IPA ECHO \u2014 pick synonyms whose IPA end most closely echoes the ' + this.gy_1.yw_1 + ' source IPA';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_1).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_2 = '2 LITERAL ANCHOR \u2014 pick the most denotatively accurate synonym';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_2).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_3 = '3 CULTURAL CHARGE \u2014 pick the synonym with the strongest ' + this.gy_1.yw_1 + ' cultural/register charge';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_3).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_4 = '4 EMOTIONAL REGISTER \u2014 pick synonyms matching emotional intensity';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_4).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.uppercase' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.text.appendLine' call
    var value_5 = '5 ' + this.hy_1.zw_1.toUpperCase() + ' IDIOM \u2014 most natural ' + this.hy_1.zw_1 + ', zero ' + this.gy_1.zw_1 + 'isms';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_5).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_6 = '6 COMPRESSION \u2014 shortest/hardest synonym; fewest syllables';
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_6).m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    this_0.m6(_Char___init__impl__6a9atx(10));
    // Inline function 'kotlin.text.appendLine' call
    var value_7 = 'Rhyme rules for ' + this.hy_1.yw_1 + ': ' + tgtHints.kx_1;
    // Inline function 'kotlin.text.appendLine' call
    this_0.l6(value_7).m6(_Char___init__impl__6a9atx(10));
    return this_0.toString();
  };
  protoOf(TranslationPair).toString = function () {
    return 'TranslationPair(source=' + this.gy_1.toString() + ', target=' + this.hy_1.toString() + ')';
  };
  protoOf(TranslationPair).hashCode = function () {
    var result = this.gy_1.hashCode();
    result = imul(result, 31) + this.hy_1.hashCode() | 0;
    return result;
  };
  protoOf(TranslationPair).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof TranslationPair))
      return false;
    if (!this.gy_1.equals(other.gy_1))
      return false;
    if (!this.hy_1.equals(other.hy_1))
      return false;
    return true;
  };
  function TextDirection_LTR_getInstance() {
    TextDirection_initEntries();
    return TextDirection_LTR_instance;
  }
  function TextDirection_RTL_getInstance() {
    TextDirection_initEntries();
    return TextDirection_RTL_instance;
  }
  function Companion_38() {
  }
  var Companion_instance_38;
  function Companion_getInstance_38() {
    return Companion_instance_38;
  }
  function UniKey(id, ipa, forms, syl, enSyl, mg, mgSyl, properties) {
    forms = forms === VOID ? emptyMap() : forms;
    syl = syl === VOID ? null : syl;
    enSyl = enSyl === VOID ? null : enSyl;
    mg = mg === VOID ? null : mg;
    mgSyl = mgSyl === VOID ? null : mgSyl;
    properties = properties === VOID ? emptySet() : properties;
    this.ny_1 = id;
    this.oy_1 = ipa;
    this.py_1 = forms;
    this.qy_1 = syl;
    this.ry_1 = enSyl;
    this.sy_1 = mg;
    this.ty_1 = mgSyl;
    this.uy_1 = properties;
  }
  protoOf(UniKey).ei = function () {
    return this.oy_1;
  };
  protoOf(UniKey).di = function () {
    var tmp0_safe_receiver = firstOrNull_0(this.py_1.s1());
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.di();
    return tmp1_elvis_lhs == null ? this.oy_1 : tmp1_elvis_lhs;
  };
  protoOf(UniKey).fi = function () {
    return this.ny_1;
  };
  protoOf(UniKey).gi = function () {
    var tmp0_safe_receiver = firstOrNull_0(this.py_1.s1());
    return tmp0_safe_receiver == null ? null : tmp0_safe_receiver.gi();
  };
  protoOf(UniKey).vy = function (prop) {
    return this.uy_1.j1(prop);
  };
  protoOf(UniKey).wy = function () {
    var tmp0_safe_receiver = this.py_1.q1(Lang_HE_getInstance());
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.di();
    return tmp1_elvis_lhs == null ? '' : tmp1_elvis_lhs;
  };
  protoOf(UniKey).xy = function () {
    var tmp0_safe_receiver = this.py_1.q1(Lang_EN_getInstance());
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.di();
    return tmp1_elvis_lhs == null ? this.ny_1 : tmp1_elvis_lhs;
  };
  protoOf(UniKey).yy = function () {
    var tmp0_safe_receiver = this.py_1.q1(Lang_HE_getInstance());
    var tmp = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.gi();
    var tmp1_safe_receiver = tmp instanceof SimpleKey ? tmp : null;
    var tmp2_safe_receiver = tmp1_safe_receiver == null ? null : tmp1_safe_receiver.ii_1;
    var tmp_0;
    if (tmp2_safe_receiver == null) {
      tmp_0 = null;
    } else {
      // Inline function 'kotlin.takeIf' call
      var tmp_1;
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(tmp2_safe_receiver) > 0) {
        tmp_1 = tmp2_safe_receiver;
      } else {
        tmp_1 = null;
      }
      tmp_0 = tmp_1;
    }
    return tmp_0;
  };
  protoOf(UniKey).zy = function () {
    return this.vy(KeyProperty_GUTTURAL_getInstance());
  };
  protoOf(UniKey).az = function () {
    return this.vy(KeyProperty_FINAL_FORM_getInstance());
  };
  protoOf(UniKey).bz = function (mode, mods) {
    if (mods.ez_1)
      return this.oy_1;
    if (mods.dz_1 && !(this.sy_1 == null)) {
      var mf = mods.cz_1;
      var tmp;
      switch (mode.v1_1) {
        case 2:
        case 1:
          tmp = mf ? this.sy_1.mz_1 : this.sy_1.lz_1;
          break;
        case 0:
          tmp = mf ? this.sy_1.iz_1 : this.sy_1.hz_1;
          break;
        default:
          noWhenBranchMatchedException();
          break;
      }
      return tmp;
    }
    var tmp_0;
    switch (mode.v1_1) {
      case 0:
        tmp_0 = this.py_1.q1(Lang_HE_getInstance());
        break;
      case 1:
      case 2:
        tmp_0 = this.py_1.q1(Lang_EN_getInstance());
        break;
      default:
        noWhenBranchMatchedException();
        break;
    }
    var langKey = tmp_0;
    if (mods.cz_1 && !mods.dz_1) {
      var tmp3_safe_receiver = langKey == null ? null : langKey.gi();
      var tmp4_elvis_lhs = tmp3_safe_receiver == null ? null : tmp3_safe_receiver.di();
      var tmp_1;
      if (tmp4_elvis_lhs == null) {
        var tmp6_safe_receiver = langKey == null ? null : langKey.di();
        var tmp_2;
        if (tmp6_safe_receiver == null) {
          tmp_2 = null;
        } else {
          // Inline function 'kotlin.text.uppercase' call
          // Inline function 'kotlin.js.asDynamic' call
          tmp_2 = tmp6_safe_receiver.toUpperCase();
        }
        tmp_1 = tmp_2;
      } else {
        tmp_1 = tmp4_elvis_lhs;
      }
      var tmp7_elvis_lhs = tmp_1;
      return tmp7_elvis_lhs == null ? this.oy_1 : tmp7_elvis_lhs;
    }
    var tmp_3;
    if (mode.v1_1 === 2) {
      var tmp10_safe_receiver = langKey == null ? null : langKey.gi();
      var tmp11_elvis_lhs = tmp10_safe_receiver == null ? null : tmp10_safe_receiver.di();
      var tmp_4;
      if (tmp11_elvis_lhs == null) {
        var tmp13_safe_receiver = langKey == null ? null : langKey.di();
        var tmp_5;
        if (tmp13_safe_receiver == null) {
          tmp_5 = null;
        } else {
          // Inline function 'kotlin.text.uppercase' call
          // Inline function 'kotlin.js.asDynamic' call
          tmp_5 = tmp13_safe_receiver.toUpperCase();
        }
        tmp_4 = tmp_5;
      } else {
        tmp_4 = tmp11_elvis_lhs;
      }
      var tmp14_elvis_lhs = tmp_4;
      tmp_3 = tmp14_elvis_lhs == null ? this.oy_1 : tmp14_elvis_lhs;
    } else {
      var tmp16_elvis_lhs = langKey == null ? null : langKey.di();
      tmp_3 = tmp16_elvis_lhs == null ? this.oy_1 : tmp16_elvis_lhs;
    }
    return tmp_3;
  };
  protoOf(UniKey).toString = function () {
    return 'UniKey(id=' + this.ny_1 + ', ipa=' + this.oy_1 + ', forms=' + toString_1(this.py_1) + ', syl=' + toString(this.qy_1) + ', enSyl=' + toString(this.ry_1) + ', mg=' + toString(this.sy_1) + ', mgSyl=' + toString(this.ty_1) + ', properties=' + toString_1(this.uy_1) + ')';
  };
  protoOf(UniKey).hashCode = function () {
    var result = getStringHashCode(this.ny_1);
    result = imul(result, 31) + getStringHashCode(this.oy_1) | 0;
    result = imul(result, 31) + hashCode(this.py_1) | 0;
    result = imul(result, 31) + (this.qy_1 == null ? 0 : hashCode(this.qy_1)) | 0;
    result = imul(result, 31) + (this.ry_1 == null ? 0 : hashCode(this.ry_1)) | 0;
    result = imul(result, 31) + (this.sy_1 == null ? 0 : this.sy_1.hashCode()) | 0;
    result = imul(result, 31) + (this.ty_1 == null ? 0 : hashCode(this.ty_1)) | 0;
    result = imul(result, 31) + hashCode(this.uy_1) | 0;
    return result;
  };
  protoOf(UniKey).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof UniKey))
      return false;
    if (!(this.ny_1 === other.ny_1))
      return false;
    if (!(this.oy_1 === other.oy_1))
      return false;
    if (!equals(this.py_1, other.py_1))
      return false;
    if (!equals(this.qy_1, other.qy_1))
      return false;
    if (!equals(this.ry_1, other.ry_1))
      return false;
    if (!equals(this.sy_1, other.sy_1))
      return false;
    if (!equals(this.ty_1, other.ty_1))
      return false;
    if (!equals(this.uy_1, other.uy_1))
      return false;
    return true;
  };
  var KeyProperty_GUTTURAL_instance;
  var KeyProperty_FINAL_FORM_instance;
  var KeyProperty_BGDKPT_instance;
  var KeyProperty_WEAK_instance;
  var KeyProperty_EMPHATIC_instance;
  var KeyProperty_entriesInitialized;
  function KeyProperty_initEntries() {
    if (KeyProperty_entriesInitialized)
      return Unit_instance;
    KeyProperty_entriesInitialized = true;
    KeyProperty_GUTTURAL_instance = new KeyProperty('GUTTURAL', 0);
    KeyProperty_FINAL_FORM_instance = new KeyProperty('FINAL_FORM', 1);
    KeyProperty_BGDKPT_instance = new KeyProperty('BGDKPT', 2);
    KeyProperty_WEAK_instance = new KeyProperty('WEAK', 3);
    KeyProperty_EMPHATIC_instance = new KeyProperty('EMPHATIC', 4);
  }
  function KeyProperty(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  var KeyMode_he_instance;
  var KeyMode_en_instance;
  var KeyMode_EN_instance;
  var KeyMode_entriesInitialized;
  function KeyMode_initEntries() {
    if (KeyMode_entriesInitialized)
      return Unit_instance;
    KeyMode_entriesInitialized = true;
    KeyMode_he_instance = new KeyMode('he', 0);
    KeyMode_en_instance = new KeyMode('en', 1);
    KeyMode_EN_instance = new KeyMode('EN', 2);
  }
  function KeyMode(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function Modifiers(shift, alt, ctrl) {
    shift = shift === VOID ? false : shift;
    alt = alt === VOID ? false : alt;
    ctrl = ctrl === VOID ? false : ctrl;
    this.cz_1 = shift;
    this.dz_1 = alt;
    this.ez_1 = ctrl;
  }
  protoOf(Modifiers).toString = function () {
    return 'Modifiers(shift=' + this.cz_1 + ', alt=' + this.dz_1 + ', ctrl=' + this.ez_1 + ')';
  };
  protoOf(Modifiers).hashCode = function () {
    var result = getBooleanHashCode(this.cz_1);
    result = imul(result, 31) + getBooleanHashCode(this.dz_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.ez_1) | 0;
    return result;
  };
  protoOf(Modifiers).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Modifiers))
      return false;
    if (!(this.cz_1 === other.cz_1))
      return false;
    if (!(this.dz_1 === other.dz_1))
      return false;
    if (!(this.ez_1 === other.ez_1))
      return false;
    return true;
  };
  function KeyProperty_GUTTURAL_getInstance() {
    KeyProperty_initEntries();
    return KeyProperty_GUTTURAL_instance;
  }
  function KeyProperty_FINAL_FORM_getInstance() {
    KeyProperty_initEntries();
    return KeyProperty_FINAL_FORM_instance;
  }
  function KeyProperty_BGDKPT_getInstance() {
    KeyProperty_initEntries();
    return KeyProperty_BGDKPT_instance;
  }
  function KeyMode_he_getInstance() {
    KeyMode_initEntries();
    return KeyMode_he_instance;
  }
  function KeyMode_en_getInstance() {
    KeyMode_initEntries();
    return KeyMode_en_instance;
  }
  function KeyMode_EN_getInstance() {
    KeyMode_initEntries();
    return KeyMode_EN_instance;
  }
  function ConsonantFeatures(place, manner, voiced) {
    this.nz_1 = place;
    this.oz_1 = manner;
    this.pz_1 = voiced;
  }
  protoOf(ConsonantFeatures).toString = function () {
    return 'ConsonantFeatures(place=' + this.nz_1 + ', manner=' + this.oz_1 + ', voiced=' + this.pz_1 + ')';
  };
  protoOf(ConsonantFeatures).hashCode = function () {
    var result = this.nz_1;
    result = imul(result, 31) + this.oz_1 | 0;
    result = imul(result, 31) + getBooleanHashCode(this.pz_1) | 0;
    return result;
  };
  protoOf(ConsonantFeatures).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof ConsonantFeatures))
      return false;
    if (!(this.nz_1 === other.nz_1))
      return false;
    if (!(this.oz_1 === other.oz_1))
      return false;
    if (!(this.pz_1 === other.pz_1))
      return false;
    return true;
  };
  function getHebrewConsonantIpa($this, letter, word, pos) {
    var tmp0_elvis_lhs = Companion_getInstance_0().cj(letter);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return '';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var hebrewLetter = tmp;
    var tmp_0;
    var tmp_1;
    if ((pos + 1 | 0) < word.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(word, pos + 1 | 0);
      tmp_1 = Char__toInt_impl_vasixd(this_0) === 1468;
    } else {
      tmp_1 = false;
    }
    if (tmp_1) {
      tmp_0 = true;
    } else {
      var tmp_2;
      if ((pos + 2 | 0) < word.length) {
        // Inline function 'kotlin.code' call
        var this_1 = charCodeAt(word, pos + 2 | 0);
        tmp_2 = Char__toInt_impl_vasixd(this_1) === 1468;
      } else {
        tmp_2 = false;
      }
      tmp_0 = tmp_2;
    }
    var hasDagesh = tmp_0;
    if (hebrewLetter.equals(HebrewLetter_SHIN_getInstance())) {
      var tmp_3;
      var tmp_4;
      if ((pos + 1 | 0) < word.length) {
        // Inline function 'kotlin.code' call
        var this_2 = charCodeAt(word, pos + 1 | 0);
        tmp_4 = Char__toInt_impl_vasixd(this_2) === 1474;
      } else {
        tmp_4 = false;
      }
      if (tmp_4) {
        tmp_3 = true;
      } else {
        var tmp_5;
        if ((pos + 2 | 0) < word.length) {
          // Inline function 'kotlin.code' call
          var this_3 = charCodeAt(word, pos + 2 | 0);
          tmp_5 = Char__toInt_impl_vasixd(this_3) === 1474;
        } else {
          tmp_5 = false;
        }
        tmp_3 = tmp_5;
      }
      var hasSinDot = tmp_3;
      return hasSinDot ? 's' : 'sh';
    }
    var tmp_6;
    if (hasDagesh && !(hebrewLetter.hj() == null)) {
      tmp_6 = ensureNotNull(hebrewLetter.hj());
    } else {
      tmp_6 = hebrewLetter.gj();
    }
    return tmp_6;
  }
  function VowelResult(ipa, length, original) {
    this.qz_1 = ipa;
    this.rz_1 = length;
    this.sz_1 = original;
  }
  protoOf(VowelResult).r9 = function () {
    return this.qz_1;
  };
  protoOf(VowelResult).s9 = function () {
    return this.rz_1;
  };
  protoOf(VowelResult).kb = function () {
    return this.sz_1;
  };
  protoOf(VowelResult).toString = function () {
    return 'VowelResult(ipa=' + this.qz_1 + ', length=' + this.rz_1 + ', original=' + this.sz_1 + ')';
  };
  protoOf(VowelResult).hashCode = function () {
    var result = getStringHashCode(this.qz_1);
    result = imul(result, 31) + this.rz_1 | 0;
    result = imul(result, 31) + getStringHashCode(this.sz_1) | 0;
    return result;
  };
  protoOf(VowelResult).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof VowelResult))
      return false;
    if (!(this.qz_1 === other.qz_1))
      return false;
    if (!(this.rz_1 === other.rz_1))
      return false;
    if (!(this.sz_1 === other.sz_1))
      return false;
    return true;
  };
  function collectVowels($this, word, start) {
    var result = StringBuilder_init_$Create$();
    var original = StringBuilder_init_$Create$();
    var i = start;
    $l$loop: while (i < word.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(word, i);
      var cp = Char__toInt_impl_vasixd(this_0);
      var tmp;
      if (1456 <= cp ? cp <= 1467 : false) {
        var tmp0_safe_receiver = Companion_getInstance_4().sp(cp);
        tmp = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.gj();
      } else if (cp === 1468) {
        tmp = null;
      } else if (cp === 1473) {
        tmp = null;
      } else if (cp === 1474) {
        tmp = null;
      } else {
        break $l$loop;
      }
      var vowelIpa = tmp;
      original.m6(charCodeAt(word, i));
      if (!(vowelIpa == null)) {
        result.l6(vowelIpa);
      }
      i = i + 1 | 0;
    }
    // Inline function 'kotlin.text.ifEmpty' call
    var this_1 = result.toString();
    var tmp_0;
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(this_1) === 0) {
      tmp_0 = 'a';
    } else {
      tmp_0 = this_1;
    }
    var tmp$ret$3 = tmp_0;
    return new VowelResult(tmp$ret$3, i - start | 0, original.toString());
  }
  function mapEnglishConsonant($this, consonants) {
    switch (consonants) {
      case 'sh':
        return 'sh';
      case 'ch':
        return 'ch';
      case 'th':
        return 't';
      case 'ph':
        return 'f';
      case 'wh':
        return 'w';
      case 'ck':
        return 'k';
      case 'ng':
        return '\u014B';
      default:
        return take(consonants, 1);
    }
  }
  function mapEnglishVowel($this, vowels) {
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(vowels) === 0)
      return '';
    var tmp;
    switch (vowels) {
      case 'a':
        tmp = 'a';
        break;
      case 'e':
        tmp = 'e';
        break;
      case 'i':
        tmp = 'i';
        break;
      case 'o':
        tmp = 'o';
        break;
      case 'u':
        tmp = 'u';
        break;
      case 'ai':
      case 'ay':
        tmp = 'e';
        break;
      case 'ea':
      case 'ee':
        tmp = 'i';
        break;
      case 'oa':
      case 'ow':
        tmp = 'o';
        break;
      case 'oo':
        tmp = 'u';
        break;
      case 'ou':
        tmp = 'a';
        break;
      case 'oi':
      case 'oy':
        tmp = 'o';
        break;
      default:
        var tmp1_safe_receiver = firstOrNull(vowels);
        var tmp_0;
        var tmp_1 = tmp1_safe_receiver;
        if ((tmp_1 == null ? null : new Char(tmp_1)) == null) {
          tmp_0 = null;
        } else {
          tmp_0 = toString_0(tmp1_safe_receiver);
        }

        var tmp2_elvis_lhs = tmp_0;
        tmp = tmp2_elvis_lhs == null ? '' : tmp2_elvis_lhs;
        break;
    }
    return tmp;
  }
  function arabicConsonantIpa($this, c) {
    var tmp0_safe_receiver = Companion_getInstance_6().cj(c);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.qq_1;
    return tmp1_elvis_lhs == null ? '' : tmp1_elvis_lhs;
  }
  function collectArabicVowel($this, word, start) {
    if (start >= word.length)
      return to('a', 0);
    var haraka = Companion_getInstance_7().cj(charCodeAt(word, start));
    var tmp;
    if (!(haraka == null)) {
      // Inline function 'kotlin.text.ifEmpty' call
      var this_0 = haraka.vq_1;
      var tmp_0;
      // Inline function 'kotlin.text.isEmpty' call
      if (charSequenceLength(this_0) === 0) {
        tmp_0 = 'a';
      } else {
        tmp_0 = this_0;
      }
      var tmp$ret$2 = tmp_0;
      tmp = to(tmp$ret$2, 1);
    } else {
      tmp = to('a', 0);
    }
    return tmp;
  }
  function parseGreekChar($this, word, pos) {
    var tmp0_elvis_lhs = Companion_getInstance_9().cj(charCodeAt(word, pos));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return new Triple('', '', 1);
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var key = tmp;
    var tmp_0;
    if (key.sr()) {
      tmp_0 = new Triple('', key.pr_1, 1);
    } else {
      tmp_0 = new Triple(key.pr_1, '', 1);
    }
    return tmp_0;
  }
  function devanagariConsonantIpa($this, c) {
    var tmp0_safe_receiver = Companion_getInstance_15().cj(c);
    var tmp;
    if (tmp0_safe_receiver == null) {
      tmp = null;
    } else {
      // Inline function 'kotlin.takeIf' call
      var tmp_0;
      if (!tmp0_safe_receiver.jt_1) {
        tmp_0 = tmp0_safe_receiver;
      } else {
        tmp_0 = null;
      }
      tmp = tmp_0;
    }
    var tmp1_safe_receiver = tmp;
    var tmp2_elvis_lhs = tmp1_safe_receiver == null ? null : tmp1_safe_receiver.ht_1;
    return tmp2_elvis_lhs == null ? '' : tmp2_elvis_lhs;
  }
  function devanagariVowelIpa($this, c) {
    return Companion_getInstance_15().pt(c);
  }
  function collectDevanagariVowel($this, word, start) {
    if (start >= word.length)
      return to('\u0259', 0);
    var c = charCodeAt(word, start);
    // Inline function 'kotlin.code' call
    var cp = Char__toInt_impl_vasixd(c);
    if (2366 <= cp ? cp <= 2380 : false) {
      return to(devanagariVowelIpa($this, c), 1);
    }
    if (cp === 2381)
      return to('', 1);
    return to('\u0259', 0);
  }
  function cyrillicCharIpa($this, c) {
    var tmp0_elvis_lhs = Companion_getInstance_10().cj(c);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return to('', '');
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var key = tmp;
    var ipa = key.xr_1;
    var tmp_0;
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(ipa) === 0) {
      tmp_0 = to('', '');
    } else {
      if (listOf(['a', 'e', 'i', 'o', 'u', '\u0268']).j1(ipa)) {
        tmp_0 = to('', ipa);
      } else {
        if (startsWith(ipa, 'j') && ipa.length > 1) {
          tmp_0 = to('j', drop(ipa, 1));
        } else {
          tmp_0 = to(ipa, '');
        }
      }
    }
    return tmp_0;
  }
  function decomposeHangul($this, syllable) {
    var base = syllable - 44032 | 0;
    var initial = base / 588 | 0;
    var medial = (base % 588 | 0) / 28 | 0;
    var finalC = base % 28 | 0;
    var initIpa = hangulInitialIpa($this, initial);
    var medIpa = hangulMedialIpa($this, medial);
    var finIpa = finalC > 0 ? hangulFinalIpa($this, finalC) : '';
    return new Triple(initIpa, medIpa, finIpa);
  }
  function hangulInitialIpa($this, i) {
    var tmp0_safe_receiver = Companion_getInstance_11().is(i);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.gs_1;
    return tmp1_elvis_lhs == null ? '' : tmp1_elvis_lhs;
  }
  function hangulMedialIpa($this, m) {
    var tmp0_safe_receiver = Companion_getInstance_12().is(m);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ms_1;
    return tmp1_elvis_lhs == null ? '' : tmp1_elvis_lhs;
  }
  function hangulFinalIpa($this, f) {
    var tmp0_safe_receiver = Companion_getInstance_13().is(f);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ts_1;
    return tmp1_elvis_lhs == null ? '' : tmp1_elvis_lhs;
  }
  function hiraganaIpa($this, c) {
    var tmp0_elvis_lhs = Companion_getInstance_14().cj(c);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return to('', '');
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var key = tmp;
    var ipa = key.bt_1;
    var vowels = listOf(['a', 'i', '\u026F', 'e', 'o']);
    var tmp_0;
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(ipa) === 0) {
      tmp_0 = to('', '');
    } else {
      if (vowels.j1(ipa)) {
        tmp_0 = to('', ipa);
      } else {
        if (ipa === 'n') {
          tmp_0 = to('n', '');
        } else {
          // Inline function 'kotlin.collections.find' call
          var tmp$ret$2;
          $l$block: {
            // Inline function 'kotlin.collections.firstOrNull' call
            var _iterator__ex2g4s = vowels.i();
            while (_iterator__ex2g4s.j()) {
              var element = _iterator__ex2g4s.k();
              if (endsWith(ipa, element)) {
                tmp$ret$2 = element;
                break $l$block;
              }
            }
            tmp$ret$2 = null;
          }
          var tmp1_elvis_lhs = tmp$ret$2;
          var vowel = tmp1_elvis_lhs == null ? '' : tmp1_elvis_lhs;
          var tmp_1;
          // Inline function 'kotlin.text.isNotEmpty' call
          if (charSequenceLength(vowel) > 0) {
            tmp_1 = dropLast(ipa, vowel.length);
          } else {
            tmp_1 = ipa;
          }
          var consonant = tmp_1;
          tmp_0 = to(consonant, vowel);
        }
      }
    }
    return tmp_0;
  }
  function katakanaIpa($this, c) {
    return hiraganaIpa($this, c);
  }
  function consonantDistance($this, c1, c2) {
    var f1 = $this.ju_1.q1(c1);
    var f2 = $this.ju_1.q1(c2);
    if (f1 == null || f2 == null)
      return 10;
    var dist = 0;
    if (!(f1.nz_1 === f2.nz_1))
      dist = dist + imul(abs(f1.nz_1 - f2.nz_1 | 0), 2) | 0;
    if (!(f1.oz_1 === f2.oz_1))
      dist = dist + abs(f1.oz_1 - f2.oz_1 | 0) | 0;
    if (!(f1.pz_1 === f2.pz_1))
      dist = dist + 1 | 0;
    return dist;
  }
  function vowelDistance($this, v1, v2) {
    var tmp0_elvis_lhs = $this.iu_1.q1(v1);
    var h1 = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var tmp1_elvis_lhs = $this.iu_1.q1(v2);
    var h2 = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
    var tmp0 = abs(h1 - h2 | 0);
    // Inline function 'kotlin.math.min' call
    var b = 360 - abs(h1 - h2 | 0) | 0;
    return Math.min(tmp0, b) / 10 | 0;
  }
  function UniKeySyllable$Companion$rhymeKey$lambda(it) {
    return it.wu_1 + it.xu_1;
  }
  function UniKeySyllable$Companion$rhymeKey$lambda_0(it) {
    return it.wu_1 + it.xu_1;
  }
  function UniKeySyllable$Companion$rhymeKeyForLang$lambda(it) {
    return it.wu_1 + it.xu_1;
  }
  function computeHue_1($this) {
    var tmp0_elvis_lhs = Companion_getInstance_39().iu_1.q1($this.xu_1);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      var tmp0 = Companion_getInstance_39().iu_1;
      var tmp1_safe_receiver = firstOrNull($this.xu_1);
      var tmp_0;
      var tmp_1 = tmp1_safe_receiver;
      if ((tmp_1 == null ? null : new Char(tmp_1)) == null) {
        tmp_0 = null;
      } else {
        tmp_0 = toString_0(tmp1_safe_receiver);
      }
      // Inline function 'kotlin.collections.get' call
      var key = tmp_0;
      tmp = (isInterface(tmp0, KtMap) ? tmp0 : THROW_CCE()).q1(key);
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var tmp2_elvis_lhs = tmp;
    var vowelHue = tmp2_elvis_lhs == null ? 0 : tmp2_elvis_lhs;
    var consonantOffset = computeConsonantOffset($this);
    return (vowelHue + consonantOffset | 0) % 360 | 0;
  }
  function computeConsonantOffset($this) {
    // Inline function 'kotlin.text.isEmpty' call
    var this_0 = $this.wu_1;
    if (charSequenceLength(this_0) === 0)
      return 0;
    var tmp0_elvis_lhs = Companion_getInstance_39().ju_1.q1($this.wu_1);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return getStringHashCode($this.wu_1) % 30 | 0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var features = tmp;
    var placeOffset = imul(features.nz_1, 5);
    var mannerOffset = imul(features.oz_1, 2);
    var voiceOffset = features.pz_1 ? 0 : 1;
    return ((placeOffset + mannerOffset | 0) + voiceOffset | 0) % 50 | 0;
  }
  function Companion_39() {
    Companion_instance_39 = this;
    this.iu_1 = mapOf_0([to('a', 0), to('A', 5), to('\u0251', 10), to('\xE6', 15), to('a\u02D0', 5), to('\u025B\u02D0', 35), to('\u025B', 40), to('E', 45), to('e', 50), to('e\u02D0', 55), to('i', 120), to('\u026A', 125), to('I', 120), to('i\u02D0', 115), to('y', 130), to('o', 180), to('\u0254', 185), to('O', 180), to('o\u02D0', 175), to('\u0254\u02D0', 190), to('\xF8', 160), to('u', 240), to('\u028A', 245), to('U', 240), to('u\u02D0', 235), to('\u026F', 250), to('\xFC', 255), to('\u0259', 300), to('@', 300), to('\u028C', 310), to('\u0268', 290), to('ja', 10), to('j\u025B', 45), to('j\u028C', 315), to('je', 55), to('jo', 185), to('ju', 245), to('wa', 5), to('w\u025B', 40), to('w\u028C', 315), to('we', 50), to('wi', 125), to('\u0270i', 120), to('ai', 120), to('ei', 50), to('ao', 180), to('ou', 240), to('an', 0), to('en', 50), to('ang', 5), to('eng', 55), to('ong', 185)]);
    this.ju_1 = mapOf_0([to('p', new ConsonantFeatures(0, 0, false)), to('b', new ConsonantFeatures(0, 0, true)), to('p\u02B0', new ConsonantFeatures(0, 0, false)), to('b\u02B1', new ConsonantFeatures(0, 0, true)), to('m', new ConsonantFeatures(0, 2, true)), to('f', new ConsonantFeatures(0, 1, false)), to('v', new ConsonantFeatures(0, 1, true)), to('\u0278', new ConsonantFeatures(0, 1, false)), to('\u028B', new ConsonantFeatures(0, 3, true)), to('w', new ConsonantFeatures(0, 3, true)), to('\u03B8', new ConsonantFeatures(1, 1, false)), to('\xF0', new ConsonantFeatures(1, 1, true)), to('t', new ConsonantFeatures(2, 0, false)), to('d', new ConsonantFeatures(2, 0, true)), to('t\u02B0', new ConsonantFeatures(2, 0, false)), to('d\u02B1', new ConsonantFeatures(2, 0, true)), to('n', new ConsonantFeatures(2, 2, true)), to('s', new ConsonantFeatures(2, 1, false)), to('z', new ConsonantFeatures(2, 1, true)), to('l', new ConsonantFeatures(2, 3, true)), to('r', new ConsonantFeatures(2, 4, true)), to('\u027E', new ConsonantFeatures(2, 4, true)), to('ts', new ConsonantFeatures(2, 5, false)), to('\u0288', new ConsonantFeatures(2, 0, false)), to('\u0288\u02B0', new ConsonantFeatures(2, 0, false)), to('\u0256', new ConsonantFeatures(2, 0, true)), to('\u0256\u02B1', new ConsonantFeatures(2, 0, true)), to('\u0273', new ConsonantFeatures(2, 2, true)), to('\u0282', new ConsonantFeatures(2, 1, false)), to('\u0283', new ConsonantFeatures(3, 1, false)), to('sh', new ConsonantFeatures(3, 1, false)), to('\u0292', new ConsonantFeatures(3, 1, true)), to('\u0255', new ConsonantFeatures(3, 1, false)), to('j', new ConsonantFeatures(3, 3, true)), to('y', new ConsonantFeatures(3, 3, true)), to('\u0272', new ConsonantFeatures(3, 2, true)), to('\xE7', new ConsonantFeatures(3, 1, false)), to('t\u0283', new ConsonantFeatures(3, 5, false)), to('t\u0283\u02B0', new ConsonantFeatures(3, 5, false)), to('ch', new ConsonantFeatures(3, 5, false)), to('d\u0292', new ConsonantFeatures(3, 5, true)), to('d\u0292\u02B1', new ConsonantFeatures(3, 5, true)), to('t\u0255', new ConsonantFeatures(3, 5, false)), to('k', new ConsonantFeatures(4, 0, false)), to('k\u02B0', new ConsonantFeatures(4, 0, false)), to('g', new ConsonantFeatures(4, 0, true)), to('\u0261', new ConsonantFeatures(4, 0, true)), to('\u0261\u02B1', new ConsonantFeatures(4, 0, true)), to('x', new ConsonantFeatures(4, 1, false)), to('\u0263', new ConsonantFeatures(4, 1, true)), to('\u014B', new ConsonantFeatures(4, 2, true)), to('q', new ConsonantFeatures(4, 0, false)), to('\u0127', new ConsonantFeatures(5, 1, false)), to('\u0295', new ConsonantFeatures(5, 1, true)), to('h', new ConsonantFeatures(5, 1, false)), to('\u0266', new ConsonantFeatures(5, 1, true)), to('\u0294', new ConsonantFeatures(5, 0, false)), to('', new ConsonantFeatures(5, 0, false)), to('s\u02E4', new ConsonantFeatures(2, 1, false)), to('d\u02E4', new ConsonantFeatures(2, 0, true)), to('t\u02E4', new ConsonantFeatures(2, 0, false)), to('\xF0\u02E4', new ConsonantFeatures(1, 1, true)), to('ks', new ConsonantFeatures(4, 5, false)), to('ps', new ConsonantFeatures(0, 5, false)), to('\u0283t\u0283', new ConsonantFeatures(3, 5, false)), to('zh', new ConsonantFeatures(3, 5, false))]);
    this.ku_1 = listOf(['', 'b', 'p', 'm', 'f', 'd', 't', 'n', 'l', 'g', 'k', 'h', 'j', 'q', 'x', 'zh', 'ch', 'sh', 'r', 'z', 'c', 's']);
    this.lu_1 = listOf(['a', 'o', 'e', 'i', 'u', '\xFC', 'ai', 'ei', 'ao', 'ou', 'an', 'en', 'ang', 'eng', 'ong']);
  }
  protoOf(Companion_39).mu = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[,.\\-;:!?\\s]+$').y7(word, '');
    var i = 0;
    while (i < w.length) {
      var c = charCodeAt(w, i);
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (1488 <= cp ? cp <= 1514 : false) {
        var consonantIpa = getHebrewConsonantIpa(this, c, w, i);
        var _destruct__k2r9zo = collectVowels(this, w, i + 1 | 0);
        var vowelIpa = _destruct__k2r9zo.r9();
        var vowelLen = _destruct__k2r9zo.s9();
        var originalVowels = _destruct__k2r9zo.kb();
        syllables.g(new UniKeySyllable(consonantIpa, vowelIpa, toString_0(c) + originalVowels));
        i = i + (1 + vowelLen | 0) | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_39).uu = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0 = word.toLowerCase();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[^a-z]').y7(tmp0, '');
    var i = 0;
    while (i < w.length) {
      var consonantStart = i;
      while (i < w.length && !contains('aeiou', charCodeAt(w, i))) {
        i = i + 1 | 0;
      }
      var consonants = substring_0(w, consonantStart, i);
      var vowelStart = i;
      while (i < w.length && contains('aeiou', charCodeAt(w, i))) {
        i = i + 1 | 0;
      }
      var vowels = substring_0(w, vowelStart, i);
      var tmp;
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(consonants) > 0) {
        tmp = true;
      } else {
        // Inline function 'kotlin.text.isNotEmpty' call
        tmp = charSequenceLength(vowels) > 0;
      }
      if (tmp) {
        syllables.g(new UniKeySyllable(mapEnglishConsonant(this, consonants), mapEnglishVowel(this, vowels), consonants + vowels));
      }
    }
    return syllables;
  };
  protoOf(Companion_39).nu = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[\\s,.!?]+').y7(word, '');
    var i = 0;
    while (i < w.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(w, i);
      var cp = Char__toInt_impl_vasixd(this_0);
      if (1536 <= cp ? cp <= 1791 : false) {
        var consonant = arabicConsonantIpa(this, charCodeAt(w, i));
        var _destruct__k2r9zo = collectArabicVowel(this, w, i + 1 | 0);
        var vowel = _destruct__k2r9zo.r9();
        var len = _destruct__k2r9zo.s9();
        var tmp = i;
        var tmp0 = (i + 1 | 0) + len | 0;
        // Inline function 'kotlin.comparisons.minOf' call
        var b = w.length;
        var tmp$ret$3 = Math.min(tmp0, b);
        syllables.g(new UniKeySyllable(consonant, vowel, substring_0(w, tmp, tmp$ret$3)));
        i = i + (1 + len | 0) | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_39).pu = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    var i = 0;
    while (i < w.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(w, i);
      var cp = Char__toInt_impl_vasixd(this_0);
      if ((945 <= cp ? cp <= 969 : false) || (902 <= cp ? cp <= 974 : false)) {
        var _destruct__k2r9zo = parseGreekChar(this, w, i);
        var cons = _destruct__k2r9zo.r9();
        var vowel = _destruct__k2r9zo.s9();
        var len = _destruct__k2r9zo.kb();
        syllables.g(new UniKeySyllable(cons, vowel, substring_0(w, i, i + len | 0)));
        i = i + len | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_39).qu = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    var i = 0;
    while (i < word.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(word, i);
      var cp = Char__toInt_impl_vasixd(this_0);
      if (2325 <= cp ? cp <= 2361 : false) {
        var cons = devanagariConsonantIpa(this, charCodeAt(word, i));
        var _destruct__k2r9zo = collectDevanagariVowel(this, word, i + 1 | 0);
        var vowel = _destruct__k2r9zo.r9();
        var len = _destruct__k2r9zo.s9();
        var tmp = i;
        var tmp0 = (i + 1 | 0) + len | 0;
        // Inline function 'kotlin.comparisons.minOf' call
        var b = word.length;
        var tmp$ret$2 = Math.min(tmp0, b);
        syllables.g(new UniKeySyllable(cons, vowel, substring_0(word, tmp, tmp$ret$2)));
        i = i + (1 + len | 0) | 0;
      } else if (2309 <= cp ? cp <= 2324 : false) {
        var vowel_0 = devanagariVowelIpa(this, charCodeAt(word, i));
        syllables.g(new UniKeySyllable('', vowel_0, toString_0(charCodeAt(word, i))));
        i = i + 1 | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_39).ou = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var w = word.toLowerCase();
    var i = 0;
    while (i < w.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(w, i);
      var cp = Char__toInt_impl_vasixd(this_0);
      if ((1072 <= cp ? cp <= 1103 : false) || (1105 <= cp ? cp <= 1105 : false)) {
        var _destruct__k2r9zo = cyrillicCharIpa(this, charCodeAt(w, i));
        var cons = _destruct__k2r9zo.r9();
        var vowel = _destruct__k2r9zo.s9();
        syllables.g(new UniKeySyllable(cons, vowel, toString_0(charCodeAt(w, i))));
        i = i + 1 | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_39).su = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    var inductionVariable = 0;
    var last = word.length;
    while (inductionVariable < last) {
      var c = charCodeAt(word, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (44032 <= cp ? cp <= 55215 : false) {
        var _destruct__k2r9zo = decomposeHangul(this, cp);
        var cons = _destruct__k2r9zo.r9();
        var vowel = _destruct__k2r9zo.s9();
        var final = _destruct__k2r9zo.kb();
        syllables.g(new UniKeySyllable(cons, vowel, toString_0(c)));
        // Inline function 'kotlin.text.isNotEmpty' call
        if (charSequenceLength(final) > 0) {
          syllables.g(new UniKeySyllable(final, '', ''));
        }
      }
    }
    return syllables;
  };
  protoOf(Companion_39).ru = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    var i = 0;
    while (i < word.length) {
      var c = charCodeAt(word, i);
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (12352 <= cp ? cp <= 12447 : false) {
        var _destruct__k2r9zo = hiraganaIpa(this, c);
        var cons = _destruct__k2r9zo.r9();
        var vowel = _destruct__k2r9zo.s9();
        syllables.g(new UniKeySyllable(cons, vowel, toString_0(c)));
      } else if (12448 <= cp ? cp <= 12543 : false) {
        var _destruct__k2r9zo_0 = katakanaIpa(this, c);
        var cons_0 = _destruct__k2r9zo_0.r9();
        var vowel_0 = _destruct__k2r9zo_0.s9();
        syllables.g(new UniKeySyllable(cons_0, vowel_0, toString_0(c)));
      }
      i = i + 1 | 0;
    }
    return syllables;
  };
  protoOf(Companion_39).tu = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    var inductionVariable = 0;
    var last = word.length;
    while (inductionVariable < last) {
      var c = charCodeAt(word, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (19968 <= cp ? cp <= 40959 : false) {
        var hash = Char__hashCode_impl_otmys(c) % 25 | 0;
        var tmp0 = this.ku_1;
        // Inline function 'kotlin.collections.getOrElse' call
        var index = hash % this.ku_1.l() | 0;
        var tmp;
        if (0 <= index ? index < tmp0.l() : false) {
          tmp = tmp0.m(index);
        } else {
          tmp = '';
        }
        var consonant = tmp;
        var tmp0_0 = this.lu_1;
        // Inline function 'kotlin.collections.getOrElse' call
        var index_0 = hash % this.lu_1.l() | 0;
        var tmp_0;
        if (0 <= index_0 ? index_0 < tmp0_0.l() : false) {
          tmp_0 = tmp0_0.m(index_0);
        } else {
          tmp_0 = 'a';
        }
        var vowel = tmp_0;
        syllables.g(new UniKeySyllable(consonant, vowel, toString_0(c)));
      }
    }
    return syllables;
  };
  protoOf(Companion_39).tz = function (text) {
    var inductionVariable = 0;
    var last = text.length;
    while (inductionVariable < last) {
      var c = charCodeAt(text, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if ((1488 <= cp ? cp <= 1514 : false) || (1456 <= cp ? cp <= 1479 : false)) {
        return Script_HEBREW_getInstance();
      }
      if ((1536 <= cp ? cp <= 1791 : false) || (1872 <= cp ? cp <= 1919 : false)) {
        return Script_ARABIC_getInstance();
      }
      if (880 <= cp ? cp <= 1023 : false) {
        return Script_GREEK_getInstance();
      }
      if (2304 <= cp ? cp <= 2431 : false) {
        return Script_DEVANAGARI_getInstance();
      }
      if (1024 <= cp ? cp <= 1279 : false) {
        return Script_CYRILLIC_getInstance();
      }
      if ((44032 <= cp ? cp <= 55215 : false) || (4352 <= cp ? cp <= 4607 : false)) {
        return Script_HANGUL_getInstance();
      }
      if ((12352 <= cp ? cp <= 12447 : false) || (12448 <= cp ? cp <= 12543 : false)) {
        return Script_HIRAGANA_getInstance();
      }
      if (19968 <= cp ? cp <= 40959 : false) {
        return Script_CJK_getInstance();
      }
      if ((65 <= cp ? cp <= 90 : false) || (97 <= cp ? cp <= 122 : false) || (192 <= cp ? cp <= 591 : false)) {
        return Script_LATIN_getInstance();
      }
    }
    return Script_UNKNOWN_getInstance();
  };
  protoOf(Companion_39).uz = function (word) {
    var tmp;
    switch (this.tz(word).v1_1) {
      case 0:
        tmp = this.mu(word);
        break;
      case 1:
        tmp = this.uu(word);
        break;
      case 2:
        tmp = this.nu(word);
        break;
      case 3:
        tmp = this.pu(word);
        break;
      case 4:
        tmp = this.qu(word);
        break;
      case 5:
        tmp = this.ou(word);
        break;
      case 6:
        tmp = this.su(word);
        break;
      case 7:
        tmp = this.ru(word);
        break;
      case 8:
        tmp = this.tu(word);
        break;
      case 9:
        tmp = this.uu(word);
        break;
      default:
        noWhenBranchMatchedException();
        break;
    }
    return tmp;
  };
  protoOf(Companion_39).vz = function (word) {
    var syllables = this.uz(word);
    var tmp0_safe_receiver = lastOrNull(syllables);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ql();
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  };
  protoOf(Companion_39).wz = function (word, isHebrew) {
    var syllables = isHebrew ? this.mu(word) : this.uu(word);
    var tmp0_safe_receiver = lastOrNull(syllables);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ql();
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  };
  protoOf(Companion_39).uk = function (hue, saturation, lightness) {
    return 'hsl(' + hue + ', ' + saturation + '%, ' + lightness + '%)';
  };
  protoOf(Companion_39).xz = function (word) {
    return this.uk(this.vz(word), 80, 72);
  };
  protoOf(Companion_39).yz = function (word, isHebrew) {
    return this.uk(this.wz(word, isHebrew), 80, 72);
  };
  protoOf(Companion_39).zz = function (word, syllableCount) {
    var syllables = this.uz(word);
    var tmp = takeLast_0(syllables, syllableCount);
    return joinToString(tmp, '-', VOID, VOID, VOID, VOID, UniKeySyllable$Companion$rhymeKey$lambda);
  };
  protoOf(Companion_39).a10 = function (word, isHebrew, syllableCount) {
    var syllables = isHebrew ? this.mu(word) : this.uu(word);
    var tmp = takeLast_0(syllables, syllableCount);
    return joinToString(tmp, '-', VOID, VOID, VOID, VOID, UniKeySyllable$Companion$rhymeKey$lambda_0);
  };
  protoOf(Companion_39).b10 = function (word, isHebrew, syllableCount, $super) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return $super === VOID ? this.a10(word, isHebrew, syllableCount) : $super.a10.call(this, word, isHebrew, syllableCount);
  };
  protoOf(Companion_39).c10 = function (word1, isHebrew1, word2, isHebrew2) {
    var key1 = this.b10(word1, isHebrew1);
    var key2 = this.b10(word2, isHebrew2);
    return key1 === key2;
  };
  protoOf(Companion_39).d10 = function (word1, isHebrew1, word2, isHebrew2) {
    var syl1 = isHebrew1 ? this.mu(word1) : this.uu(word1);
    var syl2 = isHebrew2 ? this.mu(word2) : this.uu(word2);
    if (syl1.n() || syl2.n())
      return 100;
    var last1 = last(syl1);
    var last2 = last(syl2);
    if (last1.wu_1 === last2.wu_1 && last1.xu_1 === last2.xu_1)
      return 0;
    var tmp;
    if (last1.xu_1 === last2.xu_1) {
      // Inline function 'kotlin.text.isNotEmpty' call
      var this_0 = last1.xu_1;
      tmp = charSequenceLength(this_0) > 0;
    } else {
      tmp = false;
    }
    if (tmp)
      return 10 + consonantDistance(this, last1.wu_1, last2.wu_1) | 0;
    return 50 + vowelDistance(this, last1.xu_1, last2.xu_1) | 0;
  };
  protoOf(Companion_39).vu = function (word, lang) {
    var endingIpa = lang.ub(word);
    var syllables = toMutableList(this.uu(word));
    var tmp;
    if (!(endingIpa == null)) {
      // Inline function 'kotlin.collections.isNotEmpty' call
      tmp = !syllables.n();
    } else {
      tmp = false;
    }
    if (tmp) {
      var last_0 = last(syllables);
      syllables.l2(get_lastIndex(syllables), new UniKeySyllable(last_0.wu_1, endingIpa, last_0.yu_1));
    }
    return syllables;
  };
  protoOf(Companion_39).e10 = function (word, lang) {
    var tmp;
    switch (lang.ob_1.v1_1) {
      case 0:
        tmp = this.mu(word);
        break;
      case 2:
        tmp = this.nu(word);
        break;
      case 3:
        tmp = this.pu(word);
        break;
      case 4:
        tmp = this.qu(word);
        break;
      case 5:
        tmp = this.ou(word);
        break;
      case 6:
        tmp = this.su(word);
        break;
      case 7:
        tmp = this.ru(word);
        break;
      case 8:
        tmp = this.tu(word);
        break;
      case 1:
        tmp = this.vu(word, lang);
        break;
      case 9:
        tmp = this.uu(word);
        break;
      default:
        noWhenBranchMatchedException();
        break;
    }
    return tmp;
  };
  protoOf(Companion_39).f10 = function (word, lang, syllableCount) {
    var syllables = this.e10(word, lang);
    var tmp = takeLast_0(syllables, syllableCount);
    return joinToString(tmp, '-', VOID, VOID, VOID, VOID, UniKeySyllable$Companion$rhymeKeyForLang$lambda);
  };
  protoOf(Companion_39).g10 = function (word, lang) {
    var syllables = this.e10(word, lang);
    var tmp0_safe_receiver = lastOrNull(syllables);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ql();
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  };
  protoOf(Companion_39).h10 = function (word, lang) {
    return this.uk(this.g10(word, lang), 80, 72);
  };
  var Companion_instance_39;
  function Companion_getInstance_39() {
    if (Companion_instance_39 == null)
      new Companion_39();
    return Companion_instance_39;
  }
  function UniKeySyllable$hue$delegate$lambda(this$0) {
    return function () {
      return computeHue_1(this$0);
    };
  }
  function UniKeySyllable$_get_hue_$ref_61carx() {
    return function (p0) {
      return p0.ql();
    };
  }
  function UniKeySyllable(consonant, vowel, original) {
    Companion_getInstance_39();
    this.wu_1 = consonant;
    this.xu_1 = vowel;
    this.yu_1 = original;
    var tmp = this;
    tmp.zu_1 = lazy(UniKeySyllable$hue$delegate$lambda(this));
  }
  protoOf(UniKeySyllable).ql = function () {
    var tmp0 = this.zu_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('hue', 1, tmp, UniKeySyllable$_get_hue_$ref_61carx(), null);
    return tmp0.n1();
  };
  protoOf(UniKeySyllable).toString = function () {
    return 'UniKeySyllable(consonant=' + this.wu_1 + ', vowel=' + this.xu_1 + ', original=' + this.yu_1 + ')';
  };
  protoOf(UniKeySyllable).hashCode = function () {
    var result = getStringHashCode(this.wu_1);
    result = imul(result, 31) + getStringHashCode(this.xu_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.yu_1) | 0;
    return result;
  };
  protoOf(UniKeySyllable).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof UniKeySyllable))
      return false;
    if (!(this.wu_1 === other.wu_1))
      return false;
    if (!(this.xu_1 === other.xu_1))
      return false;
    if (!(this.yu_1 === other.yu_1))
      return false;
    return true;
  };
  function key_1($this, id, ipa, he, en, heShift, enShift) {
    var heKey = !(heShift == null) ? new SimpleKey(he, ipa, id, new SimpleKey(heShift, '', id + '-shift')) : new SimpleKey(he, ipa, id);
    var tmp;
    if (enShift == null) {
      tmp = null;
    } else {
      // Inline function 'kotlin.let' call
      tmp = new SimpleKey(enShift, '', id + '-shift');
    }
    var enKey = new SimpleKey(en, ipa, id, tmp);
    return new UniKey(id, ipa, mapOf_0([to(Lang_HE_getInstance(), heKey), to(Lang_EN_getInstance(), enKey)]));
  }
  function UniKeys() {
    UniKeys_instance = this;
    var tmp = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries_0();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      // Inline function 'kotlin.text.isNotEmpty' call
      var this_0 = element.xi_1;
      if (charSequenceLength(this_0) > 0) {
        destination.g(element);
      }
    }
    // Inline function 'kotlin.collections.associate' call
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(destination, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s_0 = destination.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      // Inline function 'kotlin.collections.plusAssign' call
      var pair = to(element_0.xi_1, toUniKey(element_0));
      destination_0.l3(pair.p9_1, pair.q9_1);
    }
    tmp.i10_1 = destination_0;
    this.j10_1 = mapOf_0([to(';', key_1(this, ';', 'f', '\u05E3', ';', ':', ':')), to('.', key_1(this, '.', 'ts', '\u05E5', '.', '>', '>')), to(',', key_1(this, ',', 't', '\u05EA', ',', '<', '<')), to('/', key_1(this, '/', '', '.', '/', '?', '?')), to("'", key_1(this, "'", '', ',', "'", '"', '"')), to('`', key_1(this, '`', '', '`', '`', '~', '~'))]);
    this.k10_1 = mapOf_0([to('q', key_1(this, 'q', 'kw', '/', 'q', null, 'Q')), to('w', key_1(this, 'w', 'w', "'", 'w', null, 'W'))]);
    this.l10_1 = mapOf_0([to('1', key_1(this, '1', '', '1', '1', '!', '!')), to('2', key_1(this, '2', '', '2', '2', '@', '@')), to('3', key_1(this, '3', '', '3', '3', '#', '#')), to('4', key_1(this, '4', '', '4', '4', '\u20AA', '$')), to('5', key_1(this, '5', '', '5', '5', '%', '%')), to('6', key_1(this, '6', '', '6', '6', '^', '^')), to('7', key_1(this, '7', '', '7', '7', '&', '&')), to('8', key_1(this, '8', '', '8', '8', '*', '*')), to('9', key_1(this, '9', '', '9', '9', ')', '(')), to('0', key_1(this, '0', '', '0', '0', '(', ')'))]);
    this.m10_1 = mapOf_0([to('-', key_1(this, '-', '', '-', '-', '_', '_')), to('=', key_1(this, '=', '', '=', '=', '+', '+')), to('[', key_1(this, '[', '', '[', '[', '{', '{')), to(']', key_1(this, ']', '', ']', ']', '}', '}')), to('\\', key_1(this, '\\', '', '\\', '\\', '|', '|'))]);
    this.n10_1 = plus(plus(plus(plus(this.i10_1, this.j10_1), this.k10_1), this.l10_1), this.m10_1);
    var tmp_0 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_0 = this.n10_1.s1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_1 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_1 = tmp0_0.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      if (element_1.ny_1.length === 1 && isLetter(charCodeAt(element_1.ny_1, 0))) {
        destination_1.g(element_1);
      }
    }
    tmp_0.o10_1 = destination_1;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_1 = this.n10_1.s1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_2 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_2 = tmp0_1.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      var tmp_2;
      if (element_2.wy().length === 1) {
        var containsArg = charCodeAt(element_2.wy(), 0);
        tmp_2 = _Char___init__impl__6a9atx(1488) <= containsArg ? containsArg <= _Char___init__impl__6a9atx(1514) : false;
      } else {
        tmp_2 = false;
      }
      if (tmp_2) {
        destination_2.g(element_2);
      }
    }
    tmp_1.p10_1 = destination_2;
    var tmp_3 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_2 = this.n10_1.s1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_3 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_3 = tmp0_2.i();
    while (_iterator__ex2g4s_3.j()) {
      var element_3 = _iterator__ex2g4s_3.k();
      if (!(element_3.yy() == null)) {
        destination_3.g(element_3);
      }
    }
    tmp_3.q10_1 = destination_3;
    var tmp_4 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_3 = this.n10_1.s1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_4 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_4 = tmp0_3.i();
    while (_iterator__ex2g4s_4.j()) {
      var element_4 = _iterator__ex2g4s_4.k();
      if (element_4.zy()) {
        destination_4.g(element_4);
      }
    }
    tmp_4.r10_1 = destination_4;
    var tmp_5 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_4 = this.n10_1.s1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_5 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_5 = tmp0_4.i();
    while (_iterator__ex2g4s_5.j()) {
      var element_5 = _iterator__ex2g4s_5.k();
      if (element_5.az()) {
        destination_5.g(element_5);
      }
    }
    tmp_5.s10_1 = destination_5;
  }
  protoOf(UniKeys).j8 = function (id) {
    return this.n10_1.q1(id);
  };
  protoOf(UniKeys).t10 = function (he) {
    // Inline function 'kotlin.collections.find' call
    var tmp0 = this.n10_1.s1();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.i();
      while (_iterator__ex2g4s.j()) {
        var element = _iterator__ex2g4s.k();
        if (element.wy() === he) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    return tmp$ret$1;
  };
  protoOf(UniKeys).u10 = function (en) {
    // Inline function 'kotlin.collections.find' call
    var tmp0 = this.n10_1.s1();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.i();
      while (_iterator__ex2g4s.j()) {
        var element = _iterator__ex2g4s.k();
        if (element.xy() === en) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    return tmp$ret$1;
  };
  protoOf(UniKeys).v10 = function (key, mode, mods) {
    var tmp0_elvis_lhs = this.j8(key);
    var tmp1_elvis_lhs = tmp0_elvis_lhs == null ? this.t10(key) : tmp0_elvis_lhs;
    var tmp2_elvis_lhs = tmp1_elvis_lhs == null ? this.u10(key) : tmp1_elvis_lhs;
    var tmp;
    if (tmp2_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp2_elvis_lhs;
    }
    var uk = tmp;
    return uk.bz(mode, mods);
  };
  var UniKeys_instance;
  function UniKeys_getInstance() {
    if (UniKeys_instance == null)
      new UniKeys();
    return UniKeys_instance;
  }
  function UniKeyMode() {
    UniKeyMode_instance = this;
    this.w10_1 = listOf([KeyMode_he_getInstance(), KeyMode_en_getInstance(), KeyMode_EN_getInstance()]);
    this.x10_1 = 0;
  }
  protoOf(UniKeyMode).y10 = function () {
    return this.w10_1.m(this.x10_1);
  };
  protoOf(UniKeyMode).z10 = function (mode) {
    var tmp = this;
    // Inline function 'kotlin.takeIf' call
    var this_0 = this.w10_1.k1(mode);
    var tmp_0;
    if (this_0 >= 0) {
      tmp_0 = this_0;
    } else {
      tmp_0 = null;
    }
    var tmp0_elvis_lhs = tmp_0;
    tmp.x10_1 = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
  };
  protoOf(UniKeyMode).a11 = function () {
    this.x10_1 = (this.x10_1 + 1 | 0) % this.w10_1.l() | 0;
    return this.y10();
  };
  var UniKeyMode_instance;
  function UniKeyMode_getInstance() {
    if (UniKeyMode_instance == null)
      new UniKeyMode();
    return UniKeyMode_instance;
  }
  function UniKeyModifiers() {
    this.b11_1 = false;
    this.c11_1 = false;
    this.d11_1 = false;
  }
  protoOf(UniKeyModifiers).e11 = function () {
    this.b11_1 = false;
    this.c11_1 = false;
    this.d11_1 = false;
  };
  var UniKeyModifiers_instance;
  function UniKeyModifiers_getInstance() {
    return UniKeyModifiers_instance;
  }
  function Companion_40() {
  }
  protoOf(Companion_40).f11 = function (before, after) {
    return (before + after) / 2.0;
  };
  var Companion_instance_40;
  function Companion_getInstance_40() {
    return Companion_instance_40;
  }
  function VerseIndexState(vType, lineIdx, order) {
    order = order === VOID ? 0.0 : order;
    this.g11_1 = vType;
    this.h11_1 = lineIdx;
    this.i11_1 = order;
  }
  protoOf(VerseIndexState).j11 = function () {
    return 'V' + this.g11_1 + '.L' + (this.h11_1 + 1 | 0);
  };
  protoOf(VerseIndexState).toString = function () {
    return 'VerseIndexState(vType=' + this.g11_1 + ', lineIdx=' + this.h11_1 + ', order=' + this.i11_1 + ')';
  };
  protoOf(VerseIndexState).hashCode = function () {
    var result = this.g11_1;
    result = imul(result, 31) + this.h11_1 | 0;
    result = imul(result, 31) + getNumberHashCode(this.i11_1) | 0;
    return result;
  };
  protoOf(VerseIndexState).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof VerseIndexState))
      return false;
    if (!(this.g11_1 === other.g11_1))
      return false;
    if (!(this.h11_1 === other.h11_1))
      return false;
    if (!equals(this.i11_1, other.i11_1))
      return false;
    return true;
  };
  function RhymeLineState(letter, ipa, hue) {
    this.k11_1 = letter;
    this.l11_1 = ipa;
    this.m11_1 = hue;
  }
  protoOf(RhymeLineState).toString = function () {
    return 'RhymeLineState(letter=' + toString_0(this.k11_1) + ', ipa=' + this.l11_1 + ', hue=' + this.m11_1 + ')';
  };
  protoOf(RhymeLineState).hashCode = function () {
    var result = Char__hashCode_impl_otmys(this.k11_1);
    result = imul(result, 31) + getStringHashCode(this.l11_1) | 0;
    result = imul(result, 31) + this.m11_1 | 0;
    return result;
  };
  protoOf(RhymeLineState).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof RhymeLineState))
      return false;
    if (!(this.k11_1 === other.k11_1))
      return false;
    if (!(this.l11_1 === other.l11_1))
      return false;
    if (!(this.m11_1 === other.m11_1))
      return false;
    return true;
  };
  function Companion_41() {
  }
  protoOf(Companion_41).n11 = function (ipas) {
    // Inline function 'kotlin.collections.mutableMapOf' call
    var ipaToLetter = LinkedHashMap_init_$Create$_0();
    var nextLetter = _Char___init__impl__6a9atx(65);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(ipas, 10));
    var _iterator__ex2g4s = ipas.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      // Inline function 'kotlin.collections.getOrPut' call
      var value = ipaToLetter.q1(item);
      var tmp;
      if (value == null) {
        var _unary__edvuaz = nextLetter;
        nextLetter = Char__inc_impl_6e1wmz(_unary__edvuaz);
        var answer = new Char(_unary__edvuaz);
        ipaToLetter.l3(item, answer);
        tmp = answer;
      } else {
        tmp = value;
      }
      var letter = tmp.x_1;
      var hue = IpaColor_getInstance().tk(item);
      var tmp$ret$3 = new RhymeLineState(letter, item, hue);
      destination.g(tmp$ret$3);
    }
    var lines = destination;
    return new RhymeSchemeState(lines);
  };
  protoOf(Companion_41).o11 = function (lines, isHebrew) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(lines, 10));
    var _iterator__ex2g4s = lines.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = IpaColor_getInstance().sk(item, isHebrew);
      destination.g(tmp$ret$0);
    }
    var ipas = destination;
    return this.n11(ipas);
  };
  var Companion_instance_41;
  function Companion_getInstance_41() {
    return Companion_instance_41;
  }
  function RhymeSchemeState$_get_pattern_$lambda_2tfisd(it) {
    return toString_0(it.k11_1);
  }
  function RhymeSchemeState(lines) {
    this.p11_1 = lines;
  }
  protoOf(RhymeSchemeState).q11 = function () {
    return joinToString(this.p11_1, '', VOID, VOID, VOID, VOID, RhymeSchemeState$_get_pattern_$lambda_2tfisd);
  };
  protoOf(RhymeSchemeState).toString = function () {
    return 'RhymeSchemeState(lines=' + toString_1(this.p11_1) + ')';
  };
  protoOf(RhymeSchemeState).hashCode = function () {
    return hashCode(this.p11_1);
  };
  protoOf(RhymeSchemeState).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof RhymeSchemeState))
      return false;
    if (!equals(this.p11_1, other.p11_1))
      return false;
    return true;
  };
  function Companion_42() {
  }
  protoOf(Companion_42).r11 = function (word) {
    var syls = Companion_getInstance_39().uz(word);
    var bounds = mutableListOf([0]);
    var offset = 0;
    // Inline function 'kotlin.collections.forEach' call
    var _iterator__ex2g4s = syls.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      offset = offset + element.yu_1.length | 0;
      bounds.g(offset);
    }
    return bounds;
  };
  protoOf(Companion_42).s11 = function (word, pos, wordIdx) {
    return new CursorState(pos, wordIdx, this.r11(word));
  };
  var Companion_instance_42;
  function Companion_getInstance_42() {
    return Companion_instance_42;
  }
  function CursorState(pos, wordIdx, sylBounds) {
    this.t11_1 = pos;
    this.u11_1 = wordIdx;
    this.v11_1 = sylBounds;
  }
  protoOf(CursorState).w11 = function () {
    var tmp0 = this.v11_1;
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.i();
      while (_iterator__ex2g4s.j()) {
        var element = _iterator__ex2g4s.k();
        if (element > this.t11_1) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    var tmp0_elvis_lhs = tmp$ret$1;
    var tmp1_elvis_lhs = tmp0_elvis_lhs == null ? lastOrNull(this.v11_1) : tmp0_elvis_lhs;
    return tmp1_elvis_lhs == null ? this.t11_1 : tmp1_elvis_lhs;
  };
  protoOf(CursorState).x11 = function () {
    var tmp0 = this.v11_1;
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.lastOrNull' call
      var iterator = tmp0.o(tmp0.l());
      while (iterator.a3()) {
        var element = iterator.b3();
        if (element < this.t11_1) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    var tmp0_elvis_lhs = tmp$ret$1;
    var tmp1_elvis_lhs = tmp0_elvis_lhs == null ? firstOrNull_1(this.v11_1) : tmp0_elvis_lhs;
    return tmp1_elvis_lhs == null ? this.t11_1 : tmp1_elvis_lhs;
  };
  protoOf(CursorState).y11 = function () {
    return this.v11_1.j1(this.t11_1);
  };
  protoOf(CursorState).toString = function () {
    return 'CursorState(pos=' + this.t11_1 + ', wordIdx=' + this.u11_1 + ', sylBounds=' + toString_1(this.v11_1) + ')';
  };
  protoOf(CursorState).hashCode = function () {
    var result = this.t11_1;
    result = imul(result, 31) + this.u11_1 | 0;
    result = imul(result, 31) + hashCode(this.v11_1) | 0;
    return result;
  };
  protoOf(CursorState).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof CursorState))
      return false;
    if (!(this.t11_1 === other.t11_1))
      return false;
    if (!(this.u11_1 === other.u11_1))
      return false;
    if (!equals(this.v11_1, other.v11_1))
      return false;
    return true;
  };
  function reduce($this, state, action) {
    var tmp;
    if (action instanceof SelectLanguage) {
      // Inline function 'kotlin.collections.find' call
      var tmp0 = state.z11_1;
      var tmp$ret$1;
      $l$block: {
        // Inline function 'kotlin.collections.firstOrNull' call
        var _iterator__ex2g4s = tmp0.i();
        while (_iterator__ex2g4s.j()) {
          var element = _iterator__ex2g4s.k();
          if (element.g12_1 === action.m12_1) {
            tmp$ret$1 = element;
            break $l$block;
          }
        }
        tmp$ret$1 = null;
      }
      var selected = tmp$ret$1;
      tmp = state.d12(VOID, selected);
    } else {
      if (action instanceof UpdateFilter) {
        tmp = state.d12(VOID, VOID, action.f12_1);
      } else {
        if (action instanceof ToggleGroupByScript) {
          tmp = state.d12(VOID, VOID, VOID, action.e12_1);
        } else {
          if (action instanceof ClearSelection) {
            tmp = state.d12(VOID, null);
          } else {
            noWhenBranchMatchedException();
          }
        }
      }
    }
    return tmp;
  }
  function LanguageSelectorManager() {
    this.n12_1 = Companion_instance_43.o12();
  }
  protoOf(LanguageSelectorManager).p12 = function () {
    return this.n12_1;
  };
  protoOf(LanguageSelectorManager).q12 = function (action) {
    this.n12_1 = reduce(this, this.n12_1, action);
    return this.n12_1;
  };
  function reduce_0($this, state, action) {
    var tmp;
    if (action instanceof SelectSource) {
      // Inline function 'kotlin.collections.find' call
      var tmp0 = state.v12_1;
      var tmp$ret$1;
      $l$block: {
        // Inline function 'kotlin.collections.firstOrNull' call
        var _iterator__ex2g4s = tmp0.i();
        while (_iterator__ex2g4s.j()) {
          var element = _iterator__ex2g4s.k();
          if (element.g12_1 === action.c13_1) {
            tmp$ret$1 = element;
            break $l$block;
          }
        }
        tmp$ret$1 = null;
      }
      var source = tmp$ret$1;
      // Inline function 'kotlin.collections.filter' call
      var tmp0_0 = state.v12_1;
      // Inline function 'kotlin.collections.filterTo' call
      var destination = ArrayList_init_$Create$();
      var _iterator__ex2g4s_0 = tmp0_0.i();
      while (_iterator__ex2g4s_0.j()) {
        var element_0 = _iterator__ex2g4s_0.k();
        if (!(element_0.g12_1 === action.c13_1)) {
          destination.g(element_0);
        }
      }
      var targets = destination;
      var tmp_0;
      var tmp1_safe_receiver = state.u12_1;
      if ((tmp1_safe_receiver == null ? null : tmp1_safe_receiver.g12_1) === action.c13_1) {
        tmp_0 = null;
      } else {
        tmp_0 = state.u12_1;
      }
      var target = tmp_0;
      tmp = state.x12(source, target, targets);
    } else {
      if (action instanceof SelectTarget) {
        // Inline function 'kotlin.collections.map' call
        var this_0 = Companion_getInstance_35().xv_1;
        // Inline function 'kotlin.collections.mapTo' call
        var destination_0 = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
        var _iterator__ex2g4s_1 = this_0.i();
        while (_iterator__ex2g4s_1.j()) {
          var item = _iterator__ex2g4s_1.k();
          var tmp$ret$6 = Companion_instance_44.y12(item);
          destination_0.g(tmp$ret$6);
        }
        // Inline function 'kotlin.collections.find' call
        var tmp$ret$10;
        $l$block_0: {
          // Inline function 'kotlin.collections.firstOrNull' call
          var _iterator__ex2g4s_2 = destination_0.i();
          while (_iterator__ex2g4s_2.j()) {
            var element_1 = _iterator__ex2g4s_2.k();
            if (element_1.g12_1 === action.a13_1) {
              tmp$ret$10 = element_1;
              break $l$block_0;
            }
          }
          tmp$ret$10 = null;
        }
        var target_0 = tmp$ret$10;
        var newState = state.x12(VOID, target_0);
        if (newState.b13()) {
          addToRecent($this, newState);
        }
        tmp = newState;
      } else {
        if (action instanceof SelectPair) {
          var parts = split(action.z12_1, ['\u2192']);
          var tmp_1;
          if (parts.l() === 2) {
            var srcCode = parts.m(0);
            var tgtCode = parts.m(1);
            // Inline function 'kotlin.collections.map' call
            var this_1 = Companion_getInstance_35().xv_1;
            // Inline function 'kotlin.collections.mapTo' call
            var destination_1 = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_1, 10));
            var _iterator__ex2g4s_3 = this_1.i();
            while (_iterator__ex2g4s_3.j()) {
              var item_0 = _iterator__ex2g4s_3.k();
              var tmp$ret$12 = Companion_instance_44.y12(item_0);
              destination_1.g(tmp$ret$12);
            }
            var allLangs = destination_1;
            // Inline function 'kotlin.collections.find' call
            var tmp$ret$16;
            $l$block_1: {
              // Inline function 'kotlin.collections.firstOrNull' call
              var _iterator__ex2g4s_4 = allLangs.i();
              while (_iterator__ex2g4s_4.j()) {
                var element_2 = _iterator__ex2g4s_4.k();
                if (element_2.g12_1 === srcCode) {
                  tmp$ret$16 = element_2;
                  break $l$block_1;
                }
              }
              tmp$ret$16 = null;
            }
            var source_0 = tmp$ret$16;
            // Inline function 'kotlin.collections.find' call
            var tmp$ret$19;
            $l$block_2: {
              // Inline function 'kotlin.collections.firstOrNull' call
              var _iterator__ex2g4s_5 = allLangs.i();
              while (_iterator__ex2g4s_5.j()) {
                var element_3 = _iterator__ex2g4s_5.k();
                if (element_3.g12_1 === tgtCode) {
                  tmp$ret$19 = element_3;
                  break $l$block_2;
                }
              }
              tmp$ret$19 = null;
            }
            var target_1 = tmp$ret$19;
            // Inline function 'kotlin.collections.filter' call
            // Inline function 'kotlin.collections.filterTo' call
            var destination_2 = ArrayList_init_$Create$();
            var _iterator__ex2g4s_6 = allLangs.i();
            while (_iterator__ex2g4s_6.j()) {
              var element_4 = _iterator__ex2g4s_6.k();
              if (!(element_4.g12_1 === srcCode)) {
                destination_2.g(element_4);
              }
            }
            tmp_1 = state.x12(source_0, target_1, destination_2);
          } else {
            tmp_1 = state;
          }
          tmp = tmp_1;
        } else {
          if (action instanceof SwapLanguages) {
            var tmp_2;
            if (!(state.t12_1 == null) && !(state.u12_1 == null)) {
              // Inline function 'kotlin.collections.map' call
              var this_2 = Companion_getInstance_35().xv_1;
              // Inline function 'kotlin.collections.mapTo' call
              var destination_3 = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_2, 10));
              var _iterator__ex2g4s_7 = this_2.i();
              while (_iterator__ex2g4s_7.j()) {
                var item_1 = _iterator__ex2g4s_7.k();
                var tmp$ret$24 = Companion_instance_44.y12(item_1);
                destination_3.g(tmp$ret$24);
              }
              var allLangs_0 = destination_3;
              // Inline function 'kotlin.collections.filter' call
              // Inline function 'kotlin.collections.filterTo' call
              var destination_4 = ArrayList_init_$Create$();
              var _iterator__ex2g4s_8 = allLangs_0.i();
              while (_iterator__ex2g4s_8.j()) {
                var element_5 = _iterator__ex2g4s_8.k();
                if (!(element_5.g12_1 === ensureNotNull(state.u12_1).g12_1)) {
                  destination_4.g(element_5);
                }
              }
              tmp_2 = state.x12(state.u12_1, state.t12_1, destination_4);
            } else {
              tmp_2 = state;
            }
            tmp = tmp_2;
          } else {
            if (action instanceof ClearSelection_0) {
              tmp = Companion_instance_45.o12().x12(VOID, VOID, VOID, toList($this.s12_1));
            } else {
              noWhenBranchMatchedException();
            }
          }
        }
      }
    }
    return tmp;
  }
  function addToRecent($this, state) {
    var tmp0_elvis_lhs = Companion_getInstance_37().fy(ensureNotNull(state.t12_1).g12_1, ensureNotNull(state.u12_1).g12_1);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return Unit_instance;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var pair = tmp;
    var item = Companion_instance_46.d13(pair);
    removeAll($this.s12_1, TranslationPairManager$addToRecent$lambda(item));
    $this.s12_1.c3(0, item);
    while ($this.s12_1.l() > 10) {
      $this.s12_1.t2(get_lastIndex($this.s12_1));
    }
    $this.r12_1 = $this.r12_1.x12(VOID, VOID, VOID, toList($this.s12_1));
  }
  function TranslationPairManager$addToRecent$lambda($item) {
    return function (it) {
      return it.e13_1 === $item.e13_1;
    };
  }
  function TranslationPairManager() {
    this.r12_1 = Companion_instance_45.o12();
    var tmp = this;
    // Inline function 'kotlin.collections.mutableListOf' call
    tmp.s12_1 = ArrayList_init_$Create$();
  }
  protoOf(TranslationPairManager).p12 = function () {
    return this.r12_1;
  };
  protoOf(TranslationPairManager).l13 = function (action) {
    this.r12_1 = reduce_0(this, this.r12_1, action);
    return this.r12_1;
  };
  protoOf(TranslationPairManager).m13 = function () {
    var tmp;
    if (this.r12_1.b13()) {
      tmp = Companion_getInstance_37().fy(ensureNotNull(this.r12_1.t12_1).g12_1, ensureNotNull(this.r12_1.u12_1).g12_1);
    } else {
      tmp = null;
    }
    return tmp;
  };
  protoOf(TranslationPairManager).n13 = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = listOf([Companion_getInstance_37().ox_1, Companion_getInstance_37().xx_1, Companion_getInstance_37().sx_1, Companion_getInstance_37().rx_1, Companion_getInstance_37().tx_1, Companion_getInstance_37().vx_1, Companion_getInstance_37().wx_1, Companion_getInstance_37().yx_1, Companion_getInstance_37().zx_1]);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = Companion_instance_46.d13(item);
      destination.g(tmp$ret$0);
    }
    return destination;
  };
  function LanguageUI() {
    LanguageUI_instance = this;
    this.o13_1 = new LanguageSelectorManager();
    this.p13_1 = new TranslationPairManager();
  }
  protoOf(LanguageUI).q13 = function () {
    return this.o13_1.p12().z11_1;
  };
  protoOf(LanguageUI).r13 = function () {
    return this.p13_1.n13();
  };
  var LanguageUI_instance;
  function LanguageUI_getInstance() {
    if (LanguageUI_instance == null)
      new LanguageUI();
    return LanguageUI_instance;
  }
  function Companion_43() {
  }
  protoOf(Companion_43).o12 = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_35().xv_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = Companion_instance_44.y12(item);
      destination.g(tmp$ret$0);
    }
    return new LanguageSelectorState(destination);
  };
  var Companion_instance_43;
  function Companion_getInstance_43() {
    return Companion_instance_43;
  }
  function LanguageSelectorState(languages, selectedLanguage, filter, groupByScript) {
    languages = languages === VOID ? emptyList() : languages;
    selectedLanguage = selectedLanguage === VOID ? null : selectedLanguage;
    filter = filter === VOID ? '' : filter;
    groupByScript = groupByScript === VOID ? true : groupByScript;
    this.z11_1 = languages;
    this.a12_1 = selectedLanguage;
    this.b12_1 = filter;
    this.c12_1 = groupByScript;
  }
  protoOf(LanguageSelectorState).s13 = function () {
    var tmp;
    // Inline function 'kotlin.text.isEmpty' call
    var this_0 = this.b12_1;
    if (charSequenceLength(this_0) === 0) {
      tmp = this.z11_1;
    } else {
      // Inline function 'kotlin.collections.filter' call
      var tmp0 = this.z11_1;
      // Inline function 'kotlin.collections.filterTo' call
      var destination = ArrayList_init_$Create$();
      var _iterator__ex2g4s = tmp0.i();
      while (_iterator__ex2g4s.j()) {
        var element = _iterator__ex2g4s.k();
        if (contains_0(element.i12_1, this.b12_1, true) || contains_0(element.h12_1, this.b12_1, true) || contains_0(element.g12_1, this.b12_1, true)) {
          destination.g(element);
        }
      }
      tmp = destination;
    }
    return tmp;
  };
  protoOf(LanguageSelectorState).t13 = function () {
    // Inline function 'kotlin.collections.groupBy' call
    var tmp0 = this.s13();
    // Inline function 'kotlin.collections.groupByTo' call
    var destination = LinkedHashMap_init_$Create$_0();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var key = element.j12_1;
      // Inline function 'kotlin.collections.getOrPut' call
      var value = destination.q1(key);
      var tmp;
      if (value == null) {
        var answer = ArrayList_init_$Create$();
        destination.l3(key, answer);
        tmp = answer;
      } else {
        tmp = value;
      }
      var list = tmp;
      list.g(element);
    }
    return destination;
  };
  protoOf(LanguageSelectorState).u13 = function (languages, selectedLanguage, filter, groupByScript) {
    return new LanguageSelectorState(languages, selectedLanguage, filter, groupByScript);
  };
  protoOf(LanguageSelectorState).d12 = function (languages, selectedLanguage, filter, groupByScript, $super) {
    languages = languages === VOID ? this.z11_1 : languages;
    selectedLanguage = selectedLanguage === VOID ? this.a12_1 : selectedLanguage;
    filter = filter === VOID ? this.b12_1 : filter;
    groupByScript = groupByScript === VOID ? this.c12_1 : groupByScript;
    return $super === VOID ? this.u13(languages, selectedLanguage, filter, groupByScript) : $super.u13.call(this, languages, selectedLanguage, filter, groupByScript);
  };
  protoOf(LanguageSelectorState).toString = function () {
    return 'LanguageSelectorState(languages=' + toString_1(this.z11_1) + ', selectedLanguage=' + toString(this.a12_1) + ', filter=' + this.b12_1 + ', groupByScript=' + this.c12_1 + ')';
  };
  protoOf(LanguageSelectorState).hashCode = function () {
    var result = hashCode(this.z11_1);
    result = imul(result, 31) + (this.a12_1 == null ? 0 : this.a12_1.hashCode()) | 0;
    result = imul(result, 31) + getStringHashCode(this.b12_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.c12_1) | 0;
    return result;
  };
  protoOf(LanguageSelectorState).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof LanguageSelectorState))
      return false;
    if (!equals(this.z11_1, other.z11_1))
      return false;
    if (!equals(this.a12_1, other.a12_1))
      return false;
    if (!(this.b12_1 === other.b12_1))
      return false;
    if (!(this.c12_1 === other.c12_1))
      return false;
    return true;
  };
  function flagForLang($this, lang) {
    switch (lang.v1_1) {
      case 0:
        return '\uD83C\uDDEE\uD83C\uDDF1';
      case 1:
        return '\uD83C\uDDEC\uD83C\uDDE7';
      case 2:
        return '\uD83C\uDDF8\uD83C\uDDE6';
      case 4:
        return '\uD83C\uDDF7\uD83C\uDDFA';
      case 3:
        return '\uD83C\uDDEC\uD83C\uDDF7';
      case 5:
        return '\uD83C\uDDEE\uD83C\uDDF3';
      case 6:
        return '\uD83C\uDDEF\uD83C\uDDF5';
      case 7:
        return '\uD83C\uDDF0\uD83C\uDDF7';
      case 8:
        return '\uD83C\uDDE8\uD83C\uDDF3';
      case 10:
        return '\uD83C\uDDE9\uD83C\uDDEA';
      case 13:
        return '\uD83C\uDDEB\uD83C\uDDF7';
      case 11:
        return '\uD83C\uDDEA\uD83C\uDDF8';
      case 14:
        return '\uD83C\uDDEE\uD83C\uDDF9';
      case 19:
        return '\uD83C\uDDF5\uD83C\uDDF9';
      case 16:
        return '\uD83C\uDDF3\uD83C\uDDF1';
      case 18:
        return '\uD83C\uDDF5\uD83C\uDDF1';
      case 22:
        return '\uD83C\uDDF9\uD83C\uDDF7';
      case 9:
        return '\uD83C\uDDE9\uD83C\uDDF0';
      case 12:
        return '\uD83C\uDDEB\uD83C\uDDEE';
      case 17:
        return '\uD83C\uDDF3\uD83C\uDDF4';
      case 20:
        return '\uD83C\uDDF8\uD83C\uDDEA';
      case 15:
        return '\uD83C\uDDF2\uD83C\uDDFE';
      case 21:
        return '\uD83C\uDDF0\uD83C\uDDEA';
      default:
        return '\uD83C\uDF10';
    }
  }
  function Companion_44() {
  }
  protoOf(Companion_44).y12 = function (lang) {
    return new LanguageItem(lang.xm(), lang.yw_1, lang.zw_1, lang.gx(), lang.ax_1, flagForLang(this, lang.xw_1));
  };
  var Companion_instance_44;
  function Companion_getInstance_44() {
    return Companion_instance_44;
  }
  function LanguageItem(code, nativeName, englishName, script, direction, flag) {
    this.g12_1 = code;
    this.h12_1 = nativeName;
    this.i12_1 = englishName;
    this.j12_1 = script;
    this.k12_1 = direction;
    this.l12_1 = flag;
  }
  protoOf(LanguageItem).v13 = function () {
    return this.k12_1.equals(TextDirection_RTL_getInstance());
  };
  protoOf(LanguageItem).w13 = function () {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.text.replaceFirstChar' call
    var this_0 = this.j12_1.u1_1.toLowerCase();
    var tmp;
    // Inline function 'kotlin.text.isNotEmpty' call
    if (charSequenceLength(this_0) > 0) {
      // Inline function 'kotlin.text.uppercase' call
      var this_1 = charCodeAt(this_0, 0);
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.unsafeCast' call
      var tmp$ret$6 = toString_0(this_1).toUpperCase();
      tmp = toString_1(tmp$ret$6) + substring(this_0, 1);
    } else {
      tmp = this_0;
    }
    return tmp;
  };
  protoOf(LanguageItem).toString = function () {
    return 'LanguageItem(code=' + this.g12_1 + ', nativeName=' + this.h12_1 + ', englishName=' + this.i12_1 + ', script=' + this.j12_1.toString() + ', direction=' + this.k12_1.toString() + ', flag=' + this.l12_1 + ')';
  };
  protoOf(LanguageItem).hashCode = function () {
    var result = getStringHashCode(this.g12_1);
    result = imul(result, 31) + getStringHashCode(this.h12_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.i12_1) | 0;
    result = imul(result, 31) + this.j12_1.hashCode() | 0;
    result = imul(result, 31) + this.k12_1.hashCode() | 0;
    result = imul(result, 31) + getStringHashCode(this.l12_1) | 0;
    return result;
  };
  protoOf(LanguageItem).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof LanguageItem))
      return false;
    if (!(this.g12_1 === other.g12_1))
      return false;
    if (!(this.h12_1 === other.h12_1))
      return false;
    if (!(this.i12_1 === other.i12_1))
      return false;
    if (!this.j12_1.equals(other.j12_1))
      return false;
    if (!this.k12_1.equals(other.k12_1))
      return false;
    if (!(this.l12_1 === other.l12_1))
      return false;
    return true;
  };
  function Companion_45() {
  }
  protoOf(Companion_45).o12 = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_35().xv_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = Companion_instance_44.y12(item);
      destination.g(tmp$ret$0);
    }
    return new TranslationPairSelectorState(VOID, VOID, destination);
  };
  var Companion_instance_45;
  function Companion_getInstance_45() {
    return Companion_instance_45;
  }
  function TranslationPairSelectorState(sourceLanguage, targetLanguage, availableTargets, recentPairs) {
    sourceLanguage = sourceLanguage === VOID ? null : sourceLanguage;
    targetLanguage = targetLanguage === VOID ? null : targetLanguage;
    availableTargets = availableTargets === VOID ? emptyList() : availableTargets;
    recentPairs = recentPairs === VOID ? emptyList() : recentPairs;
    this.t12_1 = sourceLanguage;
    this.u12_1 = targetLanguage;
    this.v12_1 = availableTargets;
    this.w12_1 = recentPairs;
  }
  protoOf(TranslationPairSelectorState).b13 = function () {
    return !(this.t12_1 == null) && !(this.u12_1 == null) && !equals(this.t12_1, this.u12_1);
  };
  protoOf(TranslationPairSelectorState).ky = function () {
    return this.b13() ? ensureNotNull(this.t12_1).g12_1 + '\u2192' + ensureNotNull(this.u12_1).g12_1 : null;
  };
  protoOf(TranslationPairSelectorState).x13 = function (sourceLanguage, targetLanguage, availableTargets, recentPairs) {
    return new TranslationPairSelectorState(sourceLanguage, targetLanguage, availableTargets, recentPairs);
  };
  protoOf(TranslationPairSelectorState).x12 = function (sourceLanguage, targetLanguage, availableTargets, recentPairs, $super) {
    sourceLanguage = sourceLanguage === VOID ? this.t12_1 : sourceLanguage;
    targetLanguage = targetLanguage === VOID ? this.u12_1 : targetLanguage;
    availableTargets = availableTargets === VOID ? this.v12_1 : availableTargets;
    recentPairs = recentPairs === VOID ? this.w12_1 : recentPairs;
    return $super === VOID ? this.x13(sourceLanguage, targetLanguage, availableTargets, recentPairs) : $super.x13.call(this, sourceLanguage, targetLanguage, availableTargets, recentPairs);
  };
  protoOf(TranslationPairSelectorState).toString = function () {
    return 'TranslationPairSelectorState(sourceLanguage=' + toString(this.t12_1) + ', targetLanguage=' + toString(this.u12_1) + ', availableTargets=' + toString_1(this.v12_1) + ', recentPairs=' + toString_1(this.w12_1) + ')';
  };
  protoOf(TranslationPairSelectorState).hashCode = function () {
    var result = this.t12_1 == null ? 0 : this.t12_1.hashCode();
    result = imul(result, 31) + (this.u12_1 == null ? 0 : this.u12_1.hashCode()) | 0;
    result = imul(result, 31) + hashCode(this.v12_1) | 0;
    result = imul(result, 31) + hashCode(this.w12_1) | 0;
    return result;
  };
  protoOf(TranslationPairSelectorState).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof TranslationPairSelectorState))
      return false;
    if (!equals(this.t12_1, other.t12_1))
      return false;
    if (!equals(this.u12_1, other.u12_1))
      return false;
    if (!equals(this.v12_1, other.v12_1))
      return false;
    if (!equals(this.w12_1, other.w12_1))
      return false;
    return true;
  };
  function Companion_46() {
  }
  protoOf(Companion_46).d13 = function (pair) {
    return new TranslationPairItem(pair.ky(), pair.iy(), pair.jy(), pair.gy_1.yw_1, pair.hy_1.yw_1, Companion_instance_44.y12(pair.gy_1).l12_1, Companion_instance_44.y12(pair.hy_1).l12_1);
  };
  var Companion_instance_46;
  function Companion_getInstance_46() {
    return Companion_instance_46;
  }
  function TranslationPairItem(pairId, sourceCode, targetCode, sourceNative, targetNative, sourceFlag, targetFlag) {
    this.e13_1 = pairId;
    this.f13_1 = sourceCode;
    this.g13_1 = targetCode;
    this.h13_1 = sourceNative;
    this.i13_1 = targetNative;
    this.j13_1 = sourceFlag;
    this.k13_1 = targetFlag;
  }
  protoOf(TranslationPairItem).y13 = function () {
    return this.j13_1 + ' \u2192 ' + this.k13_1;
  };
  protoOf(TranslationPairItem).z13 = function () {
    return this.h13_1 + ' \u2192 ' + this.i13_1;
  };
  protoOf(TranslationPairItem).toString = function () {
    return 'TranslationPairItem(pairId=' + this.e13_1 + ', sourceCode=' + this.f13_1 + ', targetCode=' + this.g13_1 + ', sourceNative=' + this.h13_1 + ', targetNative=' + this.i13_1 + ', sourceFlag=' + this.j13_1 + ', targetFlag=' + this.k13_1 + ')';
  };
  protoOf(TranslationPairItem).hashCode = function () {
    var result = getStringHashCode(this.e13_1);
    result = imul(result, 31) + getStringHashCode(this.f13_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.g13_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.h13_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.i13_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.j13_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.k13_1) | 0;
    return result;
  };
  protoOf(TranslationPairItem).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof TranslationPairItem))
      return false;
    if (!(this.e13_1 === other.e13_1))
      return false;
    if (!(this.f13_1 === other.f13_1))
      return false;
    if (!(this.g13_1 === other.g13_1))
      return false;
    if (!(this.h13_1 === other.h13_1))
      return false;
    if (!(this.i13_1 === other.i13_1))
      return false;
    if (!(this.j13_1 === other.j13_1))
      return false;
    if (!(this.k13_1 === other.k13_1))
      return false;
    return true;
  };
  function SelectLanguage(code) {
    LanguageSelectorAction.call(this);
    this.m12_1 = code;
  }
  protoOf(SelectLanguage).toString = function () {
    return 'SelectLanguage(code=' + this.m12_1 + ')';
  };
  protoOf(SelectLanguage).hashCode = function () {
    return getStringHashCode(this.m12_1);
  };
  protoOf(SelectLanguage).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof SelectLanguage))
      return false;
    if (!(this.m12_1 === other.m12_1))
      return false;
    return true;
  };
  function UpdateFilter(filter) {
    LanguageSelectorAction.call(this);
    this.f12_1 = filter;
  }
  protoOf(UpdateFilter).toString = function () {
    return 'UpdateFilter(filter=' + this.f12_1 + ')';
  };
  protoOf(UpdateFilter).hashCode = function () {
    return getStringHashCode(this.f12_1);
  };
  protoOf(UpdateFilter).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof UpdateFilter))
      return false;
    if (!(this.f12_1 === other.f12_1))
      return false;
    return true;
  };
  function ToggleGroupByScript(enabled) {
    LanguageSelectorAction.call(this);
    this.e12_1 = enabled;
  }
  protoOf(ToggleGroupByScript).toString = function () {
    return 'ToggleGroupByScript(enabled=' + this.e12_1 + ')';
  };
  protoOf(ToggleGroupByScript).hashCode = function () {
    return getBooleanHashCode(this.e12_1);
  };
  protoOf(ToggleGroupByScript).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof ToggleGroupByScript))
      return false;
    if (!(this.e12_1 === other.e12_1))
      return false;
    return true;
  };
  function ClearSelection() {
    ClearSelection_instance = this;
    LanguageSelectorAction.call(this);
  }
  var ClearSelection_instance;
  function ClearSelection_getInstance() {
    if (ClearSelection_instance == null)
      new ClearSelection();
    return ClearSelection_instance;
  }
  function LanguageSelectorAction() {
  }
  function SelectSource(code) {
    TranslationPairAction.call(this);
    this.c13_1 = code;
  }
  protoOf(SelectSource).toString = function () {
    return 'SelectSource(code=' + this.c13_1 + ')';
  };
  protoOf(SelectSource).hashCode = function () {
    return getStringHashCode(this.c13_1);
  };
  protoOf(SelectSource).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof SelectSource))
      return false;
    if (!(this.c13_1 === other.c13_1))
      return false;
    return true;
  };
  function SelectTarget(code) {
    TranslationPairAction.call(this);
    this.a13_1 = code;
  }
  protoOf(SelectTarget).toString = function () {
    return 'SelectTarget(code=' + this.a13_1 + ')';
  };
  protoOf(SelectTarget).hashCode = function () {
    return getStringHashCode(this.a13_1);
  };
  protoOf(SelectTarget).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof SelectTarget))
      return false;
    if (!(this.a13_1 === other.a13_1))
      return false;
    return true;
  };
  function SelectPair(pairId) {
    TranslationPairAction.call(this);
    this.z12_1 = pairId;
  }
  protoOf(SelectPair).toString = function () {
    return 'SelectPair(pairId=' + this.z12_1 + ')';
  };
  protoOf(SelectPair).hashCode = function () {
    return getStringHashCode(this.z12_1);
  };
  protoOf(SelectPair).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof SelectPair))
      return false;
    if (!(this.z12_1 === other.z12_1))
      return false;
    return true;
  };
  function SwapLanguages() {
    SwapLanguages_instance = this;
    TranslationPairAction.call(this);
  }
  var SwapLanguages_instance;
  function SwapLanguages_getInstance() {
    if (SwapLanguages_instance == null)
      new SwapLanguages();
    return SwapLanguages_instance;
  }
  function ClearSelection_0() {
    ClearSelection_instance_0 = this;
    TranslationPairAction.call(this);
  }
  var ClearSelection_instance_0;
  function ClearSelection_getInstance_0() {
    if (ClearSelection_instance_0 == null)
      new ClearSelection_0();
    return ClearSelection_instance_0;
  }
  function TranslationPairAction() {
  }
  function getUniKey(id) {
    return UniKeys_getInstance().j8(id);
  }
  function getUniKeyByHe(he) {
    return UniKeys_getInstance().t10(he);
  }
  function getUniKeyByEn(en) {
    return UniKeys_getInstance().u10(en);
  }
  function getDisplay(key, mode, shift, alt, ctrl) {
    shift = shift === VOID ? false : shift;
    alt = alt === VOID ? false : alt;
    ctrl = ctrl === VOID ? false : ctrl;
    var keyMode;
    switch (mode) {
      case 'he':
        keyMode = KeyMode_he_getInstance();
        break;
      case 'en':
        keyMode = KeyMode_en_getInstance();
        break;
      case 'EN':
        keyMode = KeyMode_EN_getInstance();
        break;
      default:
        keyMode = KeyMode_en_getInstance();
        break;
    }
    return UniKeys_getInstance().v10(key, keyMode, new Modifiers(shift, alt, ctrl));
  }
  function getUkMode() {
    return UniKeyMode_getInstance().y10().u1_1;
  }
  function setUkMode(mode) {
    var tmp;
    switch (mode) {
      case 'he':
        tmp = KeyMode_he_getInstance();
        break;
      case 'en':
        tmp = KeyMode_en_getInstance();
        break;
      case 'EN':
        tmp = KeyMode_EN_getInstance();
        break;
      default:
        return Unit_instance;
    }
    var keyMode = tmp;
    UniKeyMode_getInstance().z10(keyMode);
  }
  function cycleUkMode() {
    return UniKeyMode_getInstance().a11().u1_1;
  }
  function setModifier(mod, value) {
    switch (mod) {
      case 'shift':
        UniKeyModifiers_instance.b11_1 = value;
        break;
      case 'alt':
        UniKeyModifiers_instance.c11_1 = value;
        break;
      case 'ctrl':
        UniKeyModifiers_instance.d11_1 = value;
        break;
    }
  }
  function getModifiers() {
    var obj = {};
    obj['shift'] = UniKeyModifiers_instance.b11_1;
    obj['alt'] = UniKeyModifiers_instance.c11_1;
    obj['ctrl'] = UniKeyModifiers_instance.d11_1;
    return obj;
  }
  function resetModifiers() {
    return UniKeyModifiers_instance.e11();
  }
  function getNikudForIpa(ipa) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_4().tp(ipa);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = item.lk_1;
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function applyNikud(letter, ipa, useMale) {
    useMale = useMale === VOID ? false : useMale;
    return letter.length === 1 ? Companion_getInstance_4().up(charCodeAt(letter, 0), ipa, useMale) : letter;
  }
  function getHebrewLetterInfo(letter) {
    if (!(letter.length === 1))
      return null;
    var tmp0_elvis_lhs = Companion_getInstance_0().cj(charCodeAt(letter, 0));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var heLetter = tmp;
    var obj = {};
    obj['letter'] = toString_0(heLetter.ni_1);
    obj['ipa'] = heLetter.oi_1;
    obj['ipaDagesh'] = heLetter.pi_1;
    obj['name'] = heLetter.qi_1;
    obj['type'] = heLetter.ri_1.u1_1;
    obj['en'] = heLetter.si_1;
    obj['isGuttural'] = heLetter.ej();
    obj['isBgdkpt'] = heLetter.fj();
    obj['isFinal'] = heLetter.dj();
    return obj;
  }
  function isBgdkpt(letter) {
    return letter.length === 1 && Companion_getInstance_5().hq(charCodeAt(letter, 0));
  }
  function getBgdkptSound(letter, hasDagesh, useClassical) {
    useClassical = useClassical === VOID ? false : useClassical;
    if (!(letter.length === 1))
      return null;
    var tmp0_elvis_lhs = Companion_getInstance_5().cj(charCodeAt(letter, 0));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bgdkpt = tmp;
    var obj = {};
    obj['letter'] = bgdkpt.jq(hasDagesh);
    obj['ipa'] = bgdkpt.iq(hasDagesh, useClassical);
    obj['en'] = bgdkpt.iq(hasDagesh, useClassical);
    return obj;
  }
  function getAllKeys() {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = UniKeys_getInstance().n10_1.r1();
    return copyToArray(this_0);
  }
  function getLetterKeys() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = UniKeys_getInstance().o10_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = item.ny_1;
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getHebrewKeys() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = UniKeys_getInstance().p10_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = item.ny_1;
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getIpaVowelInfo(ipa) {
    var tmp0_elvis_lhs = Companion_getInstance_1().sj(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var vowel = tmp;
    var obj = {};
    obj['ipa'] = vowel.kj_1;
    obj['name'] = vowel.lj_1;
    obj['heNikud'] = vowel.mj_1;
    obj['heName'] = vowel.nj_1;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = vowel.oj_1;
    obj['enSpellings'] = copyToArray(this_0);
    obj['quality'] = vowel.pj_1.u1_1;
    obj['warning'] = vowel.qj_1;
    return obj;
  }
  function getIpaConsonantInfo(ipa) {
    var tmp0_elvis_lhs = Companion_getInstance_2().sj(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var consonant = tmp;
    var obj = {};
    obj['ipa'] = consonant.vj_1;
    obj['name'] = consonant.wj_1;
    obj['he'] = consonant.xj_1;
    obj['en'] = consonant.yj_1;
    obj['geresh'] = consonant.zj_1;
    obj['heUnique'] = consonant.ak_1;
    obj['warning'] = consonant.bk_1;
    return obj;
  }
  function heIpa(word) {
    return IpaColor_getInstance().rk(word);
  }
  function enIpa(word) {
    return IpaColor_getInstance().qk(word);
  }
  function lineEndIpa(line, isHebrew) {
    return IpaColor_getInstance().sk(line, isHebrew);
  }
  function ipaHue(ipa) {
    return IpaColor_getInstance().tk(ipa);
  }
  function hsl(hue, saturation, lightness) {
    return IpaColor_getInstance().uk(hue, saturation, lightness);
  }
  function ipaEndColor(ipa) {
    return IpaColor_getInstance().vk(ipa);
  }
  function ipaMidColor(ipa) {
    return IpaColor_getInstance().wk(ipa);
  }
  function rhymeScheme(ipas) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaColor_getInstance().xk(toList_0(ipas));
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ch'] = toString_0(item.dk_1);
      obj['ipa'] = item.ek_1;
      obj['hue'] = item.fk_1;
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function detectScript(text) {
    return Companion_getInstance_39().tz(text).u1_1;
  }
  function toIpa(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().uz(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseHebrewSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().mu(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseEnglishSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().uu(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseArabicSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().nu(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseGreekSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().pu(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseHindiSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().qu(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseRussianSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().ou(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseKoreanSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().su(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseJapaneseSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().ru(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseChineseSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().tu(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function wordHueAuto(word) {
    return Companion_getInstance_39().vz(word);
  }
  function wordHue(word, isHebrew) {
    return Companion_getInstance_39().wz(word, isHebrew);
  }
  function wordEndColorAuto(word) {
    return Companion_getInstance_39().xz(word);
  }
  function wordEndColor(word, isHebrew) {
    return Companion_getInstance_39().yz(word, isHebrew);
  }
  function syllableHsl(hue, saturation, lightness) {
    saturation = saturation === VOID ? 70 : saturation;
    lightness = lightness === VOID ? 65 : lightness;
    return Companion_getInstance_39().uk(hue, saturation, lightness);
  }
  function rhymeKeyAuto(word, syllableCount) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return Companion_getInstance_39().zz(word, syllableCount);
  }
  function rhymeKey(word, isHebrew, syllableCount) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return Companion_getInstance_39().a10(word, isHebrew, syllableCount);
  }
  function rhymes(word1, isHebrew1, word2, isHebrew2) {
    return Companion_getInstance_39().c10(word1, isHebrew1, word2, isHebrew2);
  }
  function rhymeDistance(word1, isHebrew1, word2, isHebrew2) {
    return Companion_getInstance_39().d10(word1, isHebrew1, word2, isHebrew2);
  }
  function createVerseIndex(vType, lineIdx, order) {
    order = order === VOID ? 0.0 : order;
    var state = new VerseIndexState(vType, lineIdx, order);
    var obj = {};
    obj['vType'] = state.g11_1;
    obj['lineIdx'] = state.h11_1;
    obj['order'] = state.i11_1;
    obj['formatted'] = state.j11();
    return obj;
  }
  function insertLineOrder(before, after) {
    return Companion_instance_40.f11(before, after);
  }
  function computeRhymeSchemeState(lines, isHebrew) {
    isHebrew = isHebrew === VOID ? false : isHebrew;
    var state = Companion_instance_41.o11(toList_0(lines), isHebrew);
    var obj = {};
    obj['pattern'] = state.q11();
    // Inline function 'kotlin.collections.map' call
    var this_0 = state.p11_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var lineObj = {};
      lineObj['letter'] = toString_0(item.k11_1);
      lineObj['ipa'] = item.l11_1;
      lineObj['hue'] = item.m11_1;
      destination.g(lineObj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    obj['lines'] = copyToArray(destination);
    return obj;
  }
  function syllableBoundaries(word) {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = Companion_instance_42.r11(word);
    return copyToArray(this_0);
  }
  function createCursorState(word, pos, wordIdx) {
    wordIdx = wordIdx === VOID ? 0 : wordIdx;
    var state = Companion_instance_42.s11(word, pos, wordIdx);
    var obj = {};
    obj['pos'] = state.t11_1;
    obj['wordIdx'] = state.u11_1;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = state.v11_1;
    obj['sylBounds'] = copyToArray(this_0);
    obj['nextSylBound'] = state.w11();
    obj['prevSylBound'] = state.x11();
    obj['isAtBoundary'] = state.y11();
    return obj;
  }
  function getSupportedLanguages() {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = KeyboardLayouts_getInstance().wo_1;
    return copyToArray(this_0);
  }
  function getLayout(langCode) {
    var tmp0_elvis_lhs = KeyboardLayouts_getInstance().j8(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var layout = tmp;
    var obj = {};
    obj['code'] = layout.xm();
    obj['name'] = layout.tm_1;
    obj['nativeName'] = layout.um_1;
    obj['script'] = layout.vm_1.u1_1;
    obj['keys'] = layoutKeysToDynamic(layout.wm_1);
    return obj;
  }
  function layoutKeysToDynamic(keys) {
    var obj = {};
    // Inline function 'kotlin.collections.forEach' call
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = keys.t1().i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      // Inline function 'kotlin.collections.component1' call
      var keyId = element.m1();
      // Inline function 'kotlin.collections.component2' call
      var key = element.n1();
      var keyObj = {};
      keyObj['char'] = key.di();
      var tmp0_safe_receiver = key.gi();
      keyObj['shift'] = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.di();
      keyObj['ipa'] = key.ei();
      keyObj['name'] = key.fi();
      obj[keyId] = keyObj;
    }
    return obj;
  }
  function getLayoutKey(langCode, keyId) {
    var tmp0_elvis_lhs = KeyboardLayouts_getInstance().j8(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var layout = tmp;
    var tmp1_elvis_lhs = layout.wm_1.q1(keyId);
    var tmp_0;
    if (tmp1_elvis_lhs == null) {
      return null;
    } else {
      tmp_0 = tmp1_elvis_lhs;
    }
    var key = tmp_0;
    var obj = {};
    obj['char'] = key.di();
    var tmp2_safe_receiver = key.gi();
    obj['shift'] = tmp2_safe_receiver == null ? null : tmp2_safe_receiver.di();
    obj['ipa'] = key.ei();
    obj['name'] = key.fi();
    return obj;
  }
  function getIpaConsonant(ipa) {
    var tmp0_elvis_lhs = IpaMatrix_getInstance().nm(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var cons = tmp;
    var obj = {};
    obj['ipa'] = cons.cl_1;
    obj['name'] = cons.dl_1;
    obj['place'] = cons.el_1.u1_1;
    obj['manner'] = cons.fl_1.u1_1;
    obj['voiced'] = cons.gl_1;
    obj['aspirated'] = cons.hl_1;
    obj['breathy'] = cons.il_1;
    obj['emphatic'] = cons.jl_1;
    obj['hue'] = cons.ql();
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = cons.kl_1;
    obj['languages'] = copyToArray(this_0);
    return obj;
  }
  function getIpaVowelFull(ipa) {
    var tmp0_elvis_lhs = IpaMatrix_getInstance().om(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var vowel = tmp;
    var obj = {};
    obj['ipa'] = vowel.vl_1;
    obj['name'] = vowel.wl_1;
    obj['height'] = vowel.xl_1.u1_1;
    obj['backness'] = vowel.yl_1.u1_1;
    obj['rounded'] = vowel.zl_1;
    obj['long'] = vowel.am_1;
    obj['nasal'] = vowel.bm_1;
    obj['hue'] = vowel.ql();
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = vowel.cm_1;
    obj['languages'] = copyToArray(this_0);
    return obj;
  }
  function getConsonantsForLanguage(langCode) {
    var tmp0_elvis_lhs = Companion_getInstance().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.emptyArray' call
      return [];
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lang = tmp;
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().pm(lang);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.cl_1;
      obj['name'] = item.dl_1;
      obj['place'] = item.el_1.u1_1;
      obj['manner'] = item.fl_1.u1_1;
      obj['voiced'] = item.gl_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getVowelsForLanguage(langCode) {
    var tmp0_elvis_lhs = Companion_getInstance().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.emptyArray' call
      return [];
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lang = tmp;
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().qm(lang);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.vl_1;
      obj['name'] = item.wl_1;
      obj['height'] = item.xl_1.u1_1;
      obj['backness'] = item.yl_1.u1_1;
      obj['rounded'] = item.zl_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getPhonemeHue(ipa) {
    return IpaMatrix_getInstance().rm(ipa);
  }
  function getAllConsonants() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().im_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.cl_1;
      obj['name'] = item.dl_1;
      obj['place'] = item.el_1.u1_1;
      obj['manner'] = item.fl_1.u1_1;
      obj['voiced'] = item.gl_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getAllVowels() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().jm_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.vl_1;
      obj['name'] = item.wl_1;
      obj['height'] = item.xl_1.u1_1;
      obj['backness'] = item.yl_1.u1_1;
      obj['rounded'] = item.zl_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getKeyLanguages() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_35().xv_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['code'] = item.xm();
      obj['nativeName'] = item.yw_1;
      obj['englishName'] = item.zw_1;
      obj['direction'] = item.ax_1.u1_1;
      obj['script'] = item.gx().u1_1;
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getLanguageCount() {
    return Companion_getInstance_35().ex();
  }
  function getKeyLanguage(code) {
    var tmp0_elvis_lhs = Companion_getInstance_35().rb(code);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lang = tmp;
    var obj = {};
    obj['code'] = lang.xm();
    obj['nativeName'] = lang.yw_1;
    obj['englishName'] = lang.zw_1;
    obj['direction'] = lang.ax_1.u1_1;
    obj['script'] = lang.gx().u1_1;
    return obj;
  }
  function toIpaForLang(word, langCode) {
    var tmp0_elvis_lhs = Companion_getInstance().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.emptyArray' call
      return [];
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lang = tmp;
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_39().e10(word, lang);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function wordHueForLang(word, langCode) {
    var tmp0_elvis_lhs = Companion_getInstance().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return 0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lang = tmp;
    return Companion_getInstance_39().g10(word, lang);
  }
  function wordEndColorForLang(word, langCode) {
    var tmp0_elvis_lhs = Companion_getInstance().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return 'hsl(0, 0%, 50%)';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lang = tmp;
    return Companion_getInstance_39().h10(word, lang);
  }
  function rhymeKeyForLang(word, langCode, syllableCount) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    var tmp0_elvis_lhs = Companion_getInstance().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return '';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lang = tmp;
    return Companion_getInstance_39().f10(word, lang, syllableCount);
  }
  function parseSyllablesForLang(word, langCode) {
    var tmp0_elvis_lhs = Companion_getInstance_35().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.emptyArray' call
      return [];
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var keyLang = tmp;
    // Inline function 'kotlin.collections.map' call
    var this_0 = keyLang.hx(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.wu_1;
      obj['vowel'] = item.xu_1;
      obj['original'] = item.yu_1;
      obj['hue'] = item.ql();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getLanguagePromptHints(langCode) {
    var tmp0_elvis_lhs = Companion_getInstance_35().rb(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var keyLang = tmp;
    var hints = keyLang.dx_1;
    var obj = {};
    obj['linguistRole'] = hints.ix_1;
    obj['culturalNotes'] = hints.jx_1;
    obj['rhymeNotes'] = hints.kx_1;
    obj['grammarNotes'] = hints.lx_1;
    obj['genderNotes'] = hints.mx_1;
    obj['scriptNotes'] = hints.nx_1;
    return obj;
  }
  function generateAnalysisPrompt(srcLangCode, tgtLangCode, context) {
    context = context === VOID ? '' : context;
    var tmp0_elvis_lhs = Companion_getInstance_37().fy(srcLangCode, tgtLangCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return '';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var pair = tmp;
    return pair.ly(context);
  }
  function generatePathPrompt(srcLangCode, tgtLangCode, context) {
    context = context === VOID ? '' : context;
    var tmp0_elvis_lhs = Companion_getInstance_37().fy(srcLangCode, tgtLangCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return '';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var pair = tmp;
    return pair.my(context);
  }
  function getTranslationPair(srcLangCode, tgtLangCode) {
    var tmp0_elvis_lhs = Companion_getInstance_37().fy(srcLangCode, tgtLangCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var pair = tmp;
    var obj = {};
    obj['pairId'] = pair.ky();
    obj['srcCode'] = pair.iy();
    obj['tgtCode'] = pair.jy();
    obj['srcNativeName'] = pair.gy_1.yw_1;
    obj['tgtNativeName'] = pair.hy_1.yw_1;
    obj['srcEnglishName'] = pair.gy_1.zw_1;
    obj['tgtEnglishName'] = pair.hy_1.zw_1;
    obj['srcDirection'] = pair.gy_1.ax_1.u1_1;
    obj['tgtDirection'] = pair.hy_1.ax_1.u1_1;
    return obj;
  }
  function getAllTranslationPairIds() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_35().fx();
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = item.ky();
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function langUI() {
  }
  protoOf(langUI).getLanguageSelectorState = function () {
    return getLanguageSelectorState();
  };
  protoOf(langUI).selectLanguage = function (code) {
    return selectLanguage(code);
  };
  protoOf(langUI).filterLanguages = function (filter) {
    return filterLanguages(filter);
  };
  protoOf(langUI).toggleGroupByScript = function (enabled) {
    return toggleGroupByScript(enabled);
  };
  protoOf(langUI).clearLanguageSelection = function () {
    return clearLanguageSelection();
  };
  protoOf(langUI).getAllLanguageItems = function () {
    return getAllLanguageItems();
  };
  protoOf(langUI).getLanguagesByScript = function () {
    return getLanguagesByScript();
  };
  protoOf(langUI).getPairSelectorState = function () {
    return getPairSelectorState();
  };
  protoOf(langUI).selectSourceLanguage = function (code) {
    return selectSourceLanguage(code);
  };
  protoOf(langUI).selectTargetLanguage = function (code) {
    return selectTargetLanguage(code);
  };
  protoOf(langUI).selectTranslationPair = function (pairId) {
    return selectTranslationPair(pairId);
  };
  protoOf(langUI).swapLanguages = function () {
    return swapLanguages();
  };
  protoOf(langUI).clearPairSelection = function () {
    return clearPairSelection();
  };
  protoOf(langUI).getPopularPairs = function () {
    return getPopularPairs();
  };
  protoOf(langUI).getRecentPairs = function () {
    return getRecentPairs();
  };
  protoOf(langUI).getCurrentTranslationPair = function () {
    return getCurrentTranslationPair();
  };
  var UniKeyUI_instance;
  function UniKeyUI_getInstance() {
    return UniKeyUI_instance;
  }
  function getLanguageSelectorState() {
    var state = LanguageUI_getInstance().o13_1.p12();
    return stateToJs(state);
  }
  function selectLanguage(code) {
    var state = LanguageUI_getInstance().o13_1.q12(new SelectLanguage(code));
    return stateToJs(state);
  }
  function filterLanguages(filter) {
    var state = LanguageUI_getInstance().o13_1.q12(new UpdateFilter(filter));
    return stateToJs(state);
  }
  function toggleGroupByScript(enabled) {
    var state = LanguageUI_getInstance().o13_1.q12(new ToggleGroupByScript(enabled));
    return stateToJs(state);
  }
  function clearLanguageSelection() {
    var state = LanguageUI_getInstance().o13_1.q12(ClearSelection_getInstance());
    return stateToJs(state);
  }
  function getAllLanguageItems() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = LanguageUI_getInstance().q13();
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = langToJs(item);
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getLanguagesByScript() {
    var state = LanguageUI_getInstance().o13_1.p12();
    var obj = {};
    // Inline function 'kotlin.collections.forEach' call
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = state.t13().t1().i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      // Inline function 'kotlin.collections.component1' call
      var script = element.m1();
      // Inline function 'kotlin.collections.component2' call
      var langs = element.n1();
      // Inline function 'kotlin.collections.map' call
      // Inline function 'kotlin.collections.mapTo' call
      var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(langs, 10));
      var _iterator__ex2g4s_0 = langs.i();
      while (_iterator__ex2g4s_0.j()) {
        var item = _iterator__ex2g4s_0.k();
        var tmp$ret$3 = langToJs(item);
        destination.g(tmp$ret$3);
      }
      // Inline function 'kotlin.collections.toTypedArray' call
      var tmp$ret$6 = copyToArray(destination);
      obj[script.u1_1] = tmp$ret$6;
    }
    return obj;
  }
  function getPairSelectorState() {
    var state = LanguageUI_getInstance().p13_1.p12();
    return pairStateToJs(state);
  }
  function selectSourceLanguage(code) {
    var state = LanguageUI_getInstance().p13_1.l13(new SelectSource(code));
    return pairStateToJs(state);
  }
  function selectTargetLanguage(code) {
    var state = LanguageUI_getInstance().p13_1.l13(new SelectTarget(code));
    return pairStateToJs(state);
  }
  function selectTranslationPair(pairId) {
    var state = LanguageUI_getInstance().p13_1.l13(new SelectPair(pairId));
    return pairStateToJs(state);
  }
  function swapLanguages() {
    var state = LanguageUI_getInstance().p13_1.l13(SwapLanguages_getInstance());
    return pairStateToJs(state);
  }
  function clearPairSelection() {
    var state = LanguageUI_getInstance().p13_1.l13(ClearSelection_getInstance_0());
    return pairStateToJs(state);
  }
  function getPopularPairs() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = LanguageUI_getInstance().r13();
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = pairItemToJs(item);
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getRecentPairs() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = LanguageUI_getInstance().p13_1.p12().w12_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = pairItemToJs(item);
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getCurrentTranslationPair() {
    var tmp0_elvis_lhs = LanguageUI_getInstance().p13_1.m13();
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var pair = tmp;
    var obj = {};
    obj['pairId'] = pair.ky();
    obj['srcCode'] = pair.iy();
    obj['tgtCode'] = pair.jy();
    obj['srcNativeName'] = pair.gy_1.yw_1;
    obj['tgtNativeName'] = pair.hy_1.yw_1;
    obj['srcEnglishName'] = pair.gy_1.zw_1;
    obj['tgtEnglishName'] = pair.hy_1.zw_1;
    obj['srcDirection'] = pair.gy_1.ax_1.u1_1;
    obj['tgtDirection'] = pair.hy_1.ax_1.u1_1;
    return obj;
  }
  function stateToJs(state) {
    var obj = {};
    // Inline function 'kotlin.collections.map' call
    var this_0 = state.z11_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = langToJs(item);
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    obj['languages'] = copyToArray(destination);
    // Inline function 'kotlin.collections.map' call
    var this_1 = state.s13();
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_1, 10));
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var item_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$4 = langToJs(item_0);
      destination_0.g(tmp$ret$4);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    obj['filteredLanguages'] = copyToArray(destination_0);
    var tmp0_safe_receiver = state.a12_1;
    var tmp;
    if (tmp0_safe_receiver == null) {
      tmp = null;
    } else {
      // Inline function 'kotlin.let' call
      tmp = langToJs(tmp0_safe_receiver);
    }
    obj['selectedLanguage'] = tmp;
    obj['filter'] = state.b12_1;
    obj['groupByScript'] = state.c12_1;
    obj['languageCount'] = state.z11_1.l();
    obj['filteredCount'] = state.s13().l();
    return obj;
  }
  function langToJs(lang) {
    var obj = {};
    obj['code'] = lang.g12_1;
    obj['nativeName'] = lang.h12_1;
    obj['englishName'] = lang.i12_1;
    obj['script'] = lang.j12_1.u1_1;
    obj['scriptName'] = lang.w13();
    obj['direction'] = lang.k12_1.u1_1;
    obj['isRtl'] = lang.v13();
    obj['flag'] = lang.l12_1;
    return obj;
  }
  function pairStateToJs(state) {
    var obj = {};
    var tmp0_safe_receiver = state.t12_1;
    var tmp;
    if (tmp0_safe_receiver == null) {
      tmp = null;
    } else {
      // Inline function 'kotlin.let' call
      tmp = langToJs(tmp0_safe_receiver);
    }
    obj['sourceLanguage'] = tmp;
    var tmp1_safe_receiver = state.u12_1;
    var tmp_0;
    if (tmp1_safe_receiver == null) {
      tmp_0 = null;
    } else {
      // Inline function 'kotlin.let' call
      tmp_0 = langToJs(tmp1_safe_receiver);
    }
    obj['targetLanguage'] = tmp_0;
    // Inline function 'kotlin.collections.map' call
    var this_0 = state.v12_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$4 = langToJs(item);
      destination.g(tmp$ret$4);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    obj['availableTargets'] = copyToArray(destination);
    // Inline function 'kotlin.collections.map' call
    var this_1 = state.w12_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_1, 10));
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var item_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$8 = pairItemToJs(item_0);
      destination_0.g(tmp$ret$8);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    obj['recentPairs'] = copyToArray(destination_0);
    obj['isValidPair'] = state.b13();
    obj['pairId'] = state.ky();
    return obj;
  }
  function pairItemToJs(item) {
    var obj = {};
    obj['pairId'] = item.e13_1;
    obj['sourceCode'] = item.f13_1;
    obj['targetCode'] = item.g13_1;
    obj['sourceNative'] = item.h13_1;
    obj['targetNative'] = item.i13_1;
    obj['sourceFlag'] = item.j13_1;
    obj['targetFlag'] = item.k13_1;
    obj['label'] = item.y13();
    obj['fullLabel'] = item.z13();
    return obj;
  }
  //region block: post-declaration
  protoOf(NikudVowel).gi = get_shiftKey;
  protoOf(ArabicLetter).gi = get_shiftKey;
  protoOf(HangulInitial).gi = get_shiftKey;
  protoOf(HangulMedial).gi = get_shiftKey;
  protoOf(HangulFinal).gi = get_shiftKey;
  protoOf(DevanagariKey).gi = get_shiftKey;
  protoOf(EnglishPattern).gi = get_shiftKey;
  protoOf(GermanPattern).gi = get_shiftKey;
  protoOf(FrenchPattern).gi = get_shiftKey;
  protoOf(SpanishPattern).gi = get_shiftKey;
  protoOf(ItalianPattern).gi = get_shiftKey;
  protoOf(PortuguesePattern).gi = get_shiftKey;
  protoOf(DutchPattern).gi = get_shiftKey;
  protoOf(PolishPattern).gi = get_shiftKey;
  protoOf(TurkishPattern).gi = get_shiftKey;
  protoOf(SwedishPattern).gi = get_shiftKey;
  protoOf(NorwegianPattern).gi = get_shiftKey;
  protoOf(DanishPattern).gi = get_shiftKey;
  protoOf(FinnishPattern).gi = get_shiftKey;
  protoOf(GreekPattern).gi = get_shiftKey;
  protoOf(RussianPattern).gi = get_shiftKey;
  protoOf(PinyinPattern).gi = get_shiftKey;
  protoOf(SwahiliPattern).gi = get_shiftKey;
  protoOf(MalayPattern).gi = get_shiftKey;
  //endregion
  //region block: init
  LangPatterns_instance = new LangPatterns();
  Companion_instance_3 = new Companion_3();
  Companion_instance_38 = new Companion_38();
  UniKeyModifiers_instance = new UniKeyModifiers();
  Companion_instance_40 = new Companion_40();
  Companion_instance_41 = new Companion_41();
  Companion_instance_42 = new Companion_42();
  Companion_instance_43 = new Companion_43();
  Companion_instance_44 = new Companion_44();
  Companion_instance_45 = new Companion_45();
  Companion_instance_46 = new Companion_46();
  UniKeyUI_instance = new langUI();
  //endregion
  //region block: exports
  function $jsExportAll$(_) {
    var $al = _.al || (_.al = {});
    var $al$clk = $al.clk || ($al.clk = {});
    var $al$clk$key = $al$clk.key || ($al$clk.key = {});
    $al$clk$key.getUniKey = getUniKey;
    $al$clk$key.getUniKeyByHe = getUniKeyByHe;
    $al$clk$key.getUniKeyByEn = getUniKeyByEn;
    $al$clk$key.getDisplay = getDisplay;
    $al$clk$key.getUkMode = getUkMode;
    $al$clk$key.setUkMode = setUkMode;
    $al$clk$key.cycleUkMode = cycleUkMode;
    $al$clk$key.setModifier = setModifier;
    $al$clk$key.getModifiers = getModifiers;
    $al$clk$key.resetModifiers = resetModifiers;
    $al$clk$key.getNikudForIpa = getNikudForIpa;
    $al$clk$key.applyNikud = applyNikud;
    $al$clk$key.getHebrewLetterInfo = getHebrewLetterInfo;
    $al$clk$key.isBgdkpt = isBgdkpt;
    $al$clk$key.getBgdkptSound = getBgdkptSound;
    $al$clk$key.getAllKeys = getAllKeys;
    $al$clk$key.getLetterKeys = getLetterKeys;
    $al$clk$key.getHebrewKeys = getHebrewKeys;
    $al$clk$key.getIpaVowelInfo = getIpaVowelInfo;
    $al$clk$key.getIpaConsonantInfo = getIpaConsonantInfo;
    $al$clk$key.heIpa = heIpa;
    $al$clk$key.enIpa = enIpa;
    $al$clk$key.lineEndIpa = lineEndIpa;
    $al$clk$key.ipaHue = ipaHue;
    $al$clk$key.hsl = hsl;
    $al$clk$key.ipaEndColor = ipaEndColor;
    $al$clk$key.ipaMidColor = ipaMidColor;
    $al$clk$key.rhymeScheme = rhymeScheme;
    $al$clk$key.detectScript = detectScript;
    $al$clk$key.toIpa = toIpa;
    $al$clk$key.parseHebrewSyllables = parseHebrewSyllables;
    $al$clk$key.parseEnglishSyllables = parseEnglishSyllables;
    $al$clk$key.parseArabicSyllables = parseArabicSyllables;
    $al$clk$key.parseGreekSyllables = parseGreekSyllables;
    $al$clk$key.parseHindiSyllables = parseHindiSyllables;
    $al$clk$key.parseRussianSyllables = parseRussianSyllables;
    $al$clk$key.parseKoreanSyllables = parseKoreanSyllables;
    $al$clk$key.parseJapaneseSyllables = parseJapaneseSyllables;
    $al$clk$key.parseChineseSyllables = parseChineseSyllables;
    $al$clk$key.wordHueAuto = wordHueAuto;
    $al$clk$key.wordHue = wordHue;
    $al$clk$key.wordEndColorAuto = wordEndColorAuto;
    $al$clk$key.wordEndColor = wordEndColor;
    $al$clk$key.syllableHsl = syllableHsl;
    $al$clk$key.rhymeKeyAuto = rhymeKeyAuto;
    $al$clk$key.rhymeKey = rhymeKey;
    $al$clk$key.rhymes = rhymes;
    $al$clk$key.rhymeDistance = rhymeDistance;
    $al$clk$key.createVerseIndex = createVerseIndex;
    $al$clk$key.insertLineOrder = insertLineOrder;
    $al$clk$key.computeRhymeSchemeState = computeRhymeSchemeState;
    $al$clk$key.syllableBoundaries = syllableBoundaries;
    $al$clk$key.createCursorState = createCursorState;
    $al$clk$key.getSupportedLanguages = getSupportedLanguages;
    $al$clk$key.getLayout = getLayout;
    $al$clk$key.getLayoutKey = getLayoutKey;
    $al$clk$key.getIpaConsonant = getIpaConsonant;
    $al$clk$key.getIpaVowelFull = getIpaVowelFull;
    $al$clk$key.getConsonantsForLanguage = getConsonantsForLanguage;
    $al$clk$key.getVowelsForLanguage = getVowelsForLanguage;
    $al$clk$key.getPhonemeHue = getPhonemeHue;
    $al$clk$key.getAllConsonants = getAllConsonants;
    $al$clk$key.getAllVowels = getAllVowels;
    $al$clk$key.getKeyLanguages = getKeyLanguages;
    $al$clk$key.getLanguageCount = getLanguageCount;
    $al$clk$key.getKeyLanguage = getKeyLanguage;
    $al$clk$key.toIpaForLang = toIpaForLang;
    $al$clk$key.wordHueForLang = wordHueForLang;
    $al$clk$key.wordEndColorForLang = wordEndColorForLang;
    $al$clk$key.rhymeKeyForLang = rhymeKeyForLang;
    $al$clk$key.parseSyllablesForLang = parseSyllablesForLang;
    $al$clk$key.getLanguagePromptHints = getLanguagePromptHints;
    $al$clk$key.generateAnalysisPrompt = generateAnalysisPrompt;
    $al$clk$key.generatePathPrompt = generatePathPrompt;
    $al$clk$key.getTranslationPair = getTranslationPair;
    $al$clk$key.getAllTranslationPairIds = getAllTranslationPairIds;
    defineProp($al$clk$key, 'langUI', UniKeyUI_getInstance, VOID, true);
    var $al = _.al || (_.al = {});
    var $al$clk = $al.clk || ($al.clk = {});
    var $al$clk$key = $al$clk.key || ($al$clk.key = {});
    var $al$clk$key$ui = $al$clk$key.ui || ($al$clk$key.ui = {});
    $al$clk$key$ui.getLanguageSelectorState = getLanguageSelectorState;
    $al$clk$key$ui.selectLanguage = selectLanguage;
    $al$clk$key$ui.filterLanguages = filterLanguages;
    $al$clk$key$ui.toggleGroupByScript = toggleGroupByScript;
    $al$clk$key$ui.clearLanguageSelection = clearLanguageSelection;
    $al$clk$key$ui.getAllLanguageItems = getAllLanguageItems;
    $al$clk$key$ui.getLanguagesByScript = getLanguagesByScript;
    $al$clk$key$ui.getPairSelectorState = getPairSelectorState;
    $al$clk$key$ui.selectSourceLanguage = selectSourceLanguage;
    $al$clk$key$ui.selectTargetLanguage = selectTargetLanguage;
    $al$clk$key$ui.selectTranslationPair = selectTranslationPair;
    $al$clk$key$ui.swapLanguages = swapLanguages;
    $al$clk$key$ui.clearPairSelection = clearPairSelection;
    $al$clk$key$ui.getPopularPairs = getPopularPairs;
    $al$clk$key$ui.getRecentPairs = getRecentPairs;
    $al$clk$key$ui.getCurrentTranslationPair = getCurrentTranslationPair;
  }
  $jsExportAll$(_);
  //endregion
  return _;
}));

//# sourceMappingURL=hackerskeyboard-unikey-kmp.js.map
