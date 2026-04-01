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
  var collectionSizeOrDefault = kotlin_kotlin.$_$.o;
  var mapCapacity = kotlin_kotlin.$_$.y;
  var coerceAtLeast = kotlin_kotlin.$_$.d2;
  var LinkedHashMap_init_$Create$ = kotlin_kotlin.$_$.d;
  var Char = kotlin_kotlin.$_$.p2;
  var Unit_instance = kotlin_kotlin.$_$.m;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.c;
  var protoOf = kotlin_kotlin.$_$.a2;
  var initMetadataForCompanion = kotlin_kotlin.$_$.v1;
  var enumEntries = kotlin_kotlin.$_$.f1;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.h;
  var Enum = kotlin_kotlin.$_$.q2;
  var VOID = kotlin_kotlin.$_$.a;
  var initMetadataForClass = kotlin_kotlin.$_$.u1;
  var getStringHashCode = kotlin_kotlin.$_$.s1;
  var listOf = kotlin_kotlin.$_$.x;
  var listOf_0 = kotlin_kotlin.$_$.w;
  var toString = kotlin_kotlin.$_$.l;
  var Char__hashCode_impl_otmys = kotlin_kotlin.$_$.i;
  var Regex_init_$Create$ = kotlin_kotlin.$_$.f;
  var to = kotlin_kotlin.$_$.x2;
  var mapOf = kotlin_kotlin.$_$.a1;
  var charSequenceLength = kotlin_kotlin.$_$.o1;
  var takeLast = kotlin_kotlin.$_$.m2;
  var StringBuilder_init_$Create$ = kotlin_kotlin.$_$.g;
  var charCodeAt = kotlin_kotlin.$_$.m1;
  var Char__toInt_impl_vasixd = kotlin_kotlin.$_$.k;
  var equals = kotlin_kotlin.$_$.p1;
  var THROW_CCE = kotlin_kotlin.$_$.s2;
  var isCharSequence = kotlin_kotlin.$_$.x1;
  var trim = kotlin_kotlin.$_$.o2;
  var toString_0 = kotlin_kotlin.$_$.b2;
  var lastOrNull = kotlin_kotlin.$_$.u;
  var Long = kotlin_kotlin.$_$.r2;
  var fromInt = kotlin_kotlin.$_$.i1;
  var multiply = kotlin_kotlin.$_$.k1;
  var bitwiseXor = kotlin_kotlin.$_$.h1;
  var bitwiseAnd = kotlin_kotlin.$_$.g1;
  var coerceAtMost = kotlin_kotlin.$_$.e2;
  var dropLast = kotlin_kotlin.$_$.i2;
  var modulo = kotlin_kotlin.$_$.j1;
  var toNumber = kotlin_kotlin.$_$.l1;
  var numberToInt = kotlin_kotlin.$_$.z1;
  var ArrayList_init_$Create$_0 = kotlin_kotlin.$_$.b;
  var charSequenceGet = kotlin_kotlin.$_$.n1;
  var initMetadataForObject = kotlin_kotlin.$_$.w1;
  var lazy = kotlin_kotlin.$_$.u2;
  var KProperty1 = kotlin_kotlin.$_$.f2;
  var getPropertyCallableRef = kotlin_kotlin.$_$.r1;
  var getBooleanHashCode = kotlin_kotlin.$_$.q1;
  var hashCode = kotlin_kotlin.$_$.t1;
  var emptyList = kotlin_kotlin.$_$.q;
  var emptyMap = kotlin_kotlin.$_$.r;
  var mapOf_0 = kotlin_kotlin.$_$.z;
  var LinkedHashMap_init_$Create$_0 = kotlin_kotlin.$_$.e;
  var Char__plus_impl_qi7pgj = kotlin_kotlin.$_$.j;
  var contains = kotlin_kotlin.$_$.g2;
  var sorted = kotlin_kotlin.$_$.b1;
  var toList = kotlin_kotlin.$_$.d1;
  var first = kotlin_kotlin.$_$.s;
  var noWhenBranchMatchedException = kotlin_kotlin.$_$.v2;
  var toString_1 = kotlin_kotlin.$_$.w2;
  var take = kotlin_kotlin.$_$.n2;
  var firstOrNull = kotlin_kotlin.$_$.j2;
  var Triple = kotlin_kotlin.$_$.t2;
  var abs = kotlin_kotlin.$_$.c2;
  var KtMap = kotlin_kotlin.$_$.n;
  var isInterface = kotlin_kotlin.$_$.y1;
  var contains_0 = kotlin_kotlin.$_$.h2;
  var substring = kotlin_kotlin.$_$.l2;
  var takeLast_0 = kotlin_kotlin.$_$.c1;
  var joinToString = kotlin_kotlin.$_$.t;
  var last = kotlin_kotlin.$_$.v;
  var isLetter = kotlin_kotlin.$_$.k2;
  var copyToArray = kotlin_kotlin.$_$.p;
  var toList_0 = kotlin_kotlin.$_$.e1;
  //endregion
  //region block: pre-declaration
  initMetadataForCompanion(Companion);
  initMetadataForClass(Bgdkpt, 'Bgdkpt', VOID, Enum);
  initMetadataForClass(BgdkptSound, 'BgdkptSound');
  initMetadataForCompanion(Companion_0);
  initMetadataForClass(HebrewLetter, 'HebrewLetter', VOID, Enum);
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
  initMetadataForClass(LayoutKey, 'LayoutKey');
  initMetadataForClass(Modifier, 'Modifier', VOID, Enum);
  initMetadataForObject(KeyboardLayouts, 'KeyboardLayouts');
  initMetadataForCompanion(Companion_4);
  initMetadataForClass(Nikud, 'Nikud', VOID, Enum);
  initMetadataForClass(UniKey, 'UniKey');
  initMetadataForClass(KeyMode, 'KeyMode', VOID, Enum);
  initMetadataForClass(Modifiers, 'Modifiers', Modifiers);
  initMetadataForClass(ConsonantFeatures, 'ConsonantFeatures');
  initMetadataForClass(VowelResult, 'VowelResult');
  initMetadataForClass(Script, 'Script', VOID, Enum);
  initMetadataForCompanion(Companion_5);
  initMetadataForClass(UniKeySyllable, 'UniKeySyllable');
  initMetadataForObject(UniKeys, 'UniKeys');
  initMetadataForObject(UniKeyMode, 'UniKeyMode');
  initMetadataForObject(UniKeyModifiers, 'UniKeyModifiers');
  //endregion
  var Bgdkpt_BET_instance;
  var Bgdkpt_GIMEL_instance;
  var Bgdkpt_DALET_instance;
  var Bgdkpt_KAF_instance;
  var Bgdkpt_PE_instance;
  var Bgdkpt_TAV_instance;
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
      var tmp$ret$0 = new Char(element.qa_1);
      destination.e3(tmp$ret$0, element);
    }
    tmp.ua_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_0 = tmp0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      if (element_0.wa()) {
        destination_0.g(element_0);
      }
    }
    tmp_0.va_1 = destination_0;
  }
  protoOf(Companion).xa = function (c) {
    return this.ua_1.o1(new Char(c));
  };
  protoOf(Companion).ya = function (c) {
    return this.ua_1.m1(new Char(c));
  };
  var Companion_instance;
  function Companion_getInstance() {
    Bgdkpt_initEntries();
    if (Companion_instance == null)
      new Companion();
    return Companion_instance;
  }
  function values() {
    return [Bgdkpt_BET_getInstance(), Bgdkpt_GIMEL_getInstance(), Bgdkpt_DALET_getInstance(), Bgdkpt_KAF_getInstance(), Bgdkpt_PE_getInstance(), Bgdkpt_TAV_getInstance()];
  }
  function get_entries() {
    if ($ENTRIES == null)
      $ENTRIES = enumEntries(values());
    return $ENTRIES;
  }
  var Bgdkpt_entriesInitialized;
  function Bgdkpt_initEntries() {
    if (Bgdkpt_entriesInitialized)
      return Unit_instance;
    Bgdkpt_entriesInitialized = true;
    Bgdkpt_BET_instance = new Bgdkpt('BET', 0, _Char___init__impl__6a9atx(1489), new BgdkptSound('\u05D1\u05BC', 'b', 'b'), new BgdkptSound('\u05D1', 'v', 'v'));
    Bgdkpt_GIMEL_instance = new Bgdkpt('GIMEL', 1, _Char___init__impl__6a9atx(1490), new BgdkptSound('\u05D2\u05BC', 'g', 'g'), new BgdkptSound('\u05D2', 'g', 'g'), new BgdkptSound('\u05D2', '\u0263', 'gh'));
    Bgdkpt_DALET_instance = new Bgdkpt('DALET', 2, _Char___init__impl__6a9atx(1491), new BgdkptSound('\u05D3\u05BC', 'd', 'd'), new BgdkptSound('\u05D3', 'd', 'd'), new BgdkptSound('\u05D3', '\xF0', 'th'));
    Bgdkpt_KAF_instance = new Bgdkpt('KAF', 3, _Char___init__impl__6a9atx(1499), new BgdkptSound('\u05DB\u05BC', 'k', 'k'), new BgdkptSound('\u05DB', 'x', "kh'"));
    Bgdkpt_PE_instance = new Bgdkpt('PE', 4, _Char___init__impl__6a9atx(1508), new BgdkptSound('\u05E4\u05BC', 'p', 'p'), new BgdkptSound('\u05E4', 'f', 'f'));
    Bgdkpt_TAV_instance = new Bgdkpt('TAV', 5, _Char___init__impl__6a9atx(1514), new BgdkptSound('\u05EA\u05BC', 't', 't'), new BgdkptSound('\u05EA', 't', 't'), new BgdkptSound('\u05EA', '\u03B8', 'th'));
    Companion_getInstance();
  }
  var $ENTRIES;
  function Bgdkpt(name, ordinal, letter, withDagesh, withoutDagesh, classical) {
    classical = classical === VOID ? null : classical;
    Enum.call(this, name, ordinal);
    this.qa_1 = letter;
    this.ra_1 = withDagesh;
    this.sa_1 = withoutDagesh;
    this.ta_1 = classical;
  }
  protoOf(Bgdkpt).za = function (hasDagesh, useClassical) {
    return hasDagesh ? this.ra_1 : useClassical && !(this.ta_1 == null) ? this.ta_1 : this.sa_1;
  };
  protoOf(Bgdkpt).wa = function () {
    return !(this.ra_1.bb_1 === this.sa_1.bb_1);
  };
  function BgdkptSound(letter, ipa, en) {
    this.ab_1 = letter;
    this.bb_1 = ipa;
    this.cb_1 = en;
  }
  protoOf(BgdkptSound).toString = function () {
    return 'BgdkptSound(letter=' + this.ab_1 + ', ipa=' + this.bb_1 + ', en=' + this.cb_1 + ')';
  };
  protoOf(BgdkptSound).hashCode = function () {
    var result = getStringHashCode(this.ab_1);
    result = imul(result, 31) + getStringHashCode(this.bb_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.cb_1) | 0;
    return result;
  };
  protoOf(BgdkptSound).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof BgdkptSound))
      return false;
    if (!(this.ab_1 === other.ab_1))
      return false;
    if (!(this.bb_1 === other.bb_1))
      return false;
    if (!(this.cb_1 === other.cb_1))
      return false;
    return true;
  };
  function Bgdkpt_BET_getInstance() {
    Bgdkpt_initEntries();
    return Bgdkpt_BET_instance;
  }
  function Bgdkpt_GIMEL_getInstance() {
    Bgdkpt_initEntries();
    return Bgdkpt_GIMEL_instance;
  }
  function Bgdkpt_DALET_getInstance() {
    Bgdkpt_initEntries();
    return Bgdkpt_DALET_instance;
  }
  function Bgdkpt_KAF_getInstance() {
    Bgdkpt_initEntries();
    return Bgdkpt_KAF_instance;
  }
  function Bgdkpt_PE_getInstance() {
    Bgdkpt_initEntries();
    return Bgdkpt_PE_instance;
  }
  function Bgdkpt_TAV_getInstance() {
    Bgdkpt_initEntries();
    return Bgdkpt_TAV_instance;
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
      var tmp$ret$0 = new Char(element.fb_1);
      destination.e3(tmp$ret$0, element);
    }
    tmp.pb_1 = destination;
  }
  protoOf(Companion_0).xa = function (c) {
    return this.pb_1.o1(new Char(c));
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
    HebrewLetter_ALEF_instance = new HebrewLetter('ALEF', 0, _Char___init__impl__6a9atx(1488), '\u0294', VOID, '\u05D0\u05B8\u05DC\u05B6\u05E3', LetterType_GUTTURAL_getInstance(), "'", VOID, VOID, true);
    HebrewLetter_BET_instance = new HebrewLetter('BET', 1, _Char___init__impl__6a9atx(1489), 'v', 'b', '\u05D1\u05B5\u05BC\u05D9\u05EA', LetterType_BGDKPT_getInstance(), 'v', 'b');
    HebrewLetter_GIMEL_instance = new HebrewLetter('GIMEL', 2, _Char___init__impl__6a9atx(1490), 'g', VOID, '\u05D2\u05B4\u05BC\u05D9\u05DE\u05B6\u05DC', LetterType_BGDKPT_getInstance(), 'g', VOID, VOID, VOID, '\u0263');
    HebrewLetter_DALET_instance = new HebrewLetter('DALET', 3, _Char___init__impl__6a9atx(1491), 'd', VOID, '\u05D3\u05B8\u05BC\u05DC\u05B6\u05EA', LetterType_BGDKPT_getInstance(), 'd', VOID, VOID, VOID, '\xF0');
    HebrewLetter_HE_instance = new HebrewLetter('HE', 4, _Char___init__impl__6a9atx(1492), 'h', VOID, '\u05D4\u05B5\u05D0', LetterType_GUTTURAL_getInstance(), 'h', VOID, VOID, true);
    HebrewLetter_VAV_instance = new HebrewLetter('VAV', 5, _Char___init__impl__6a9atx(1493), 'v', VOID, '\u05D5\u05B8\u05D5', LetterType_WEAK_getInstance(), 'v');
    HebrewLetter_ZAYIN_instance = new HebrewLetter('ZAYIN', 6, _Char___init__impl__6a9atx(1494), 'z', VOID, '\u05D6\u05B7\u05D9\u05B4\u05DF', VOID, 'z');
    HebrewLetter_CHET_instance = new HebrewLetter('CHET', 7, _Char___init__impl__6a9atx(1495), '\u0127', VOID, '\u05D7\u05B5\u05D9\u05EA', LetterType_GUTTURAL_getInstance(), "\u1E25'");
    HebrewLetter_TET_instance = new HebrewLetter('TET', 8, _Char___init__impl__6a9atx(1496), 't', VOID, '\u05D8\u05B5\u05D9\u05EA', LetterType_EMPHATIC_getInstance(), 't');
    HebrewLetter_YOD_instance = new HebrewLetter('YOD', 9, _Char___init__impl__6a9atx(1497), 'j', VOID, '\u05D9\u05D5\u05B9\u05D3', LetterType_WEAK_getInstance(), 'y');
    HebrewLetter_KAF_instance = new HebrewLetter('KAF', 10, _Char___init__impl__6a9atx(1499), 'x', 'k', '\u05DB\u05B7\u05BC\u05E3', LetterType_BGDKPT_getInstance(), "kh'", 'k', _Char___init__impl__6a9atx(1498));
    HebrewLetter_LAMED_instance = new HebrewLetter('LAMED', 11, _Char___init__impl__6a9atx(1500), 'l', VOID, '\u05DC\u05B8\u05DE\u05B6\u05D3', VOID, 'l');
    HebrewLetter_MEM_instance = new HebrewLetter('MEM', 12, _Char___init__impl__6a9atx(1502), 'm', VOID, '\u05DE\u05B5\u05DD', VOID, 'm', VOID, _Char___init__impl__6a9atx(1501));
    HebrewLetter_NUN_instance = new HebrewLetter('NUN', 13, _Char___init__impl__6a9atx(1504), 'n', VOID, '\u05E0\u05D5\u05BC\u05DF', VOID, 'n', VOID, _Char___init__impl__6a9atx(1503));
    HebrewLetter_SAMECH_instance = new HebrewLetter('SAMECH', 14, _Char___init__impl__6a9atx(1505), 's', VOID, '\u05E1\u05B8\u05DE\u05B6\u05DA', VOID, 's');
    HebrewLetter_AYIN_instance = new HebrewLetter('AYIN', 15, _Char___init__impl__6a9atx(1506), '\u0295', VOID, '\u05E2\u05B7\u05D9\u05B4\u05DF', LetterType_GUTTURAL_getInstance(), "'");
    HebrewLetter_PE_instance = new HebrewLetter('PE', 16, _Char___init__impl__6a9atx(1508), 'f', 'p', '\u05E4\u05B5\u05BC\u05D0', LetterType_BGDKPT_getInstance(), 'f', 'p', _Char___init__impl__6a9atx(1507));
    HebrewLetter_TSADI_instance = new HebrewLetter('TSADI', 17, _Char___init__impl__6a9atx(1510), 'ts', VOID, '\u05E6\u05B8\u05D3\u05B4\u05D9', LetterType_EMPHATIC_getInstance(), "ts'", VOID, _Char___init__impl__6a9atx(1509));
    HebrewLetter_QOF_instance = new HebrewLetter('QOF', 18, _Char___init__impl__6a9atx(1511), 'k', VOID, '\u05E7\u05D5\u05B9\u05E3', LetterType_EMPHATIC_getInstance(), "k'");
    HebrewLetter_RESH_instance = new HebrewLetter('RESH', 19, _Char___init__impl__6a9atx(1512), '\u0281', VOID, '\u05E8\u05B5\u05D9\u05E9\u05C1', LetterType_GUTTURAL_getInstance(), "r'");
    HebrewLetter_SHIN_instance = new HebrewLetter('SHIN', 20, _Char___init__impl__6a9atx(1513), '\u0283', VOID, '\u05E9\u05C1\u05B4\u05D9\u05DF/\u05E9\u05C2\u05B4\u05D9\u05DF', LetterType_SHIN_getInstance(), 'sh');
    HebrewLetter_TAV_instance = new HebrewLetter('TAV', 21, _Char___init__impl__6a9atx(1514), 't', VOID, '\u05EA\u05B8\u05BC\u05D5', LetterType_BGDKPT_getInstance(), 't', VOID, VOID, VOID, '\u03B8');
    HebrewLetter_KAF_SOFIT_instance = new HebrewLetter('KAF_SOFIT', 22, _Char___init__impl__6a9atx(1498), 'x', VOID, '\u05DB\u05B7\u05BC\u05E3 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, "kh'");
    HebrewLetter_MEM_SOFIT_instance = new HebrewLetter('MEM_SOFIT', 23, _Char___init__impl__6a9atx(1501), 'm', VOID, '\u05DE\u05B5\u05DD \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, 'm');
    HebrewLetter_NUN_SOFIT_instance = new HebrewLetter('NUN_SOFIT', 24, _Char___init__impl__6a9atx(1503), 'n', VOID, '\u05E0\u05D5\u05BC\u05DF \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, 'n');
    HebrewLetter_PE_SOFIT_instance = new HebrewLetter('PE_SOFIT', 25, _Char___init__impl__6a9atx(1507), 'f', VOID, '\u05E4\u05B5\u05BC\u05D0 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, 'f');
    HebrewLetter_TSADI_SOFIT_instance = new HebrewLetter('TSADI_SOFIT', 26, _Char___init__impl__6a9atx(1509), 'ts', VOID, '\u05E6\u05B8\u05D3\u05B4\u05D9 \u05E1\u05D5\u05B9\u05E4\u05B4\u05D9\u05EA', VOID, "ts'");
    Companion_getInstance_0();
  }
  var $ENTRIES_0;
  function HebrewLetter(name, ordinal, letter, ipa, ipaDagesh, displayName, type, en, enDagesh, finalForm, silentEnd, classical) {
    ipaDagesh = ipaDagesh === VOID ? null : ipaDagesh;
    type = type === VOID ? LetterType_REGULAR_getInstance() : type;
    enDagesh = enDagesh === VOID ? null : enDagesh;
    finalForm = finalForm === VOID ? null : finalForm;
    silentEnd = silentEnd === VOID ? false : silentEnd;
    classical = classical === VOID ? null : classical;
    Enum.call(this, name, ordinal);
    this.fb_1 = letter;
    this.gb_1 = ipa;
    this.hb_1 = ipaDagesh;
    this.ib_1 = displayName;
    this.jb_1 = type;
    this.kb_1 = en;
    this.lb_1 = enDagesh;
    this.mb_1 = finalForm;
    this.nb_1 = silentEnd;
    this.ob_1 = classical;
  }
  protoOf(HebrewLetter).qb = function () {
    return listOf([HebrewLetter_KAF_SOFIT_getInstance(), HebrewLetter_MEM_SOFIT_getInstance(), HebrewLetter_NUN_SOFIT_getInstance(), HebrewLetter_PE_SOFIT_getInstance(), HebrewLetter_TSADI_SOFIT_getInstance()]).h1(this);
  };
  protoOf(HebrewLetter).rb = function () {
    return this.jb_1.equals(LetterType_GUTTURAL_getInstance());
  };
  protoOf(HebrewLetter).sb = function () {
    return this.jb_1.equals(LetterType_BGDKPT_getInstance());
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
      var tmp$ret$0 = element.vb_1;
      destination.e3(tmp$ret$0, element);
    }
    tmp.cc_1 = destination;
  }
  protoOf(Companion_1).dc = function (ipa) {
    return this.cc_1.o1(ipa);
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
    this.vb_1 = ipa;
    this.wb_1 = displayName;
    this.xb_1 = heNikud;
    this.yb_1 = heName;
    this.zb_1 = enSpellings;
    this.ac_1 = quality;
    this.bc_1 = warning;
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
      var tmp$ret$0 = element.gc_1;
      destination.e3(tmp$ret$0, element);
    }
    tmp.nc_1 = destination;
  }
  protoOf(Companion_2).dc = function (ipa) {
    return this.nc_1.o1(ipa);
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
    this.gc_1 = ipa;
    this.hc_1 = displayName;
    this.ic_1 = he;
    this.jc_1 = en;
    this.kc_1 = geresh;
    this.lc_1 = heUnique;
    this.mc_1 = warning;
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
  function RhymeSchemeItem(letter, ipa, hue) {
    this.oc_1 = letter;
    this.pc_1 = ipa;
    this.qc_1 = hue;
  }
  protoOf(RhymeSchemeItem).toString = function () {
    return 'RhymeSchemeItem(letter=' + toString(this.oc_1) + ', ipa=' + this.pc_1 + ', hue=' + this.qc_1 + ')';
  };
  protoOf(RhymeSchemeItem).hashCode = function () {
    var result = Char__hashCode_impl_otmys(this.oc_1);
    result = imul(result, 31) + getStringHashCode(this.pc_1) | 0;
    result = imul(result, 31) + this.qc_1 | 0;
    return result;
  };
  protoOf(RhymeSchemeItem).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof RhymeSchemeItem))
      return false;
    if (!(this.oc_1 === other.oc_1))
      return false;
    if (!(this.pc_1 === other.pc_1))
      return false;
    if (!(this.qc_1 === other.qc_1))
      return false;
    return true;
  };
  function IpaColor() {
    IpaColor_instance = this;
    this.rc_1 = listOf([to(Regex_init_$Create$('ing$'), 'IN'), to(Regex_init_$Create$('tion$'), 'shEn'), to(Regex_init_$Create$('ting$'), 'tIN'), to(Regex_init_$Create$('ring$'), 'rIN'), to(Regex_init_$Create$('ling$'), 'lIN'), to(Regex_init_$Create$('ning$'), 'nIN'), to(Regex_init_$Create$('ent$'), 'Ent'), to(Regex_init_$Create$('ant$'), 'ant'), to(Regex_init_$Create$('ine$'), 'ajn'), to(Regex_init_$Create$('ate$'), 'ejt'), to(Regex_init_$Create$('ight$'), 'ajt'), to(Regex_init_$Create$('ite$'), 'ajt'), to(Regex_init_$Create$('ake$'), 'ejk'), to(Regex_init_$Create$('ade$'), 'ejd'), to(Regex_init_$Create$('age$'), 'Ij'), to(Regex_init_$Create$('ound$'), 'awnd'), to(Regex_init_$Create$('own$'), 'awn'), to(Regex_init_$Create$('out$'), 'awt'), to(Regex_init_$Create$('ash$'), 'ash'), to(Regex_init_$Create$('ush$'), 'ush'), to(Regex_init_$Create$('ay$'), 'ej'), to(Regex_init_$Create$('ey$'), 'ej'), to(Regex_init_$Create$('oy$'), 'oj'), to(Regex_init_$Create$('er$'), 'Er'), to(Regex_init_$Create$('or$'), 'Er'), to(Regex_init_$Create$('ar$'), 'ar'), to(Regex_init_$Create$('le$'), 'El'), to(Regex_init_$Create$('al$'), 'El'), to(Regex_init_$Create$('ed$'), 'd'), to(Regex_init_$Create$('es$'), 'z'), to(Regex_init_$Create$('ly$'), 'li'), to(Regex_init_$Create$('ck$'), 'k'), to(Regex_init_$Create$('ss$'), 's'), to(Regex_init_$Create$('ll$'), 'l')]);
    this.sc_1 = mapOf([to(new Char(_Char___init__impl__6a9atx(1488)), ''), to(new Char(_Char___init__impl__6a9atx(1489)), 'v'), to(new Char(_Char___init__impl__6a9atx(1490)), 'g'), to(new Char(_Char___init__impl__6a9atx(1491)), 'd'), to(new Char(_Char___init__impl__6a9atx(1492)), 'h'), to(new Char(_Char___init__impl__6a9atx(1493)), 'v'), to(new Char(_Char___init__impl__6a9atx(1494)), 'z'), to(new Char(_Char___init__impl__6a9atx(1495)), 'x'), to(new Char(_Char___init__impl__6a9atx(1496)), 't'), to(new Char(_Char___init__impl__6a9atx(1497)), 'j'), to(new Char(_Char___init__impl__6a9atx(1498)), 'x'), to(new Char(_Char___init__impl__6a9atx(1499)), 'x'), to(new Char(_Char___init__impl__6a9atx(1500)), 'l'), to(new Char(_Char___init__impl__6a9atx(1501)), 'm'), to(new Char(_Char___init__impl__6a9atx(1502)), 'm'), to(new Char(_Char___init__impl__6a9atx(1503)), 'n'), to(new Char(_Char___init__impl__6a9atx(1504)), 'n'), to(new Char(_Char___init__impl__6a9atx(1505)), 's'), to(new Char(_Char___init__impl__6a9atx(1506)), ''), to(new Char(_Char___init__impl__6a9atx(1507)), 'f'), to(new Char(_Char___init__impl__6a9atx(1508)), 'f'), to(new Char(_Char___init__impl__6a9atx(1509)), 'ts'), to(new Char(_Char___init__impl__6a9atx(1510)), 'ts'), to(new Char(_Char___init__impl__6a9atx(1511)), 'k'), to(new Char(_Char___init__impl__6a9atx(1512)), 'r'), to(new Char(_Char___init__impl__6a9atx(1513)), 'sh'), to(new Char(_Char___init__impl__6a9atx(1514)), 't')]);
    this.tc_1 = mapOf([to(new Char(_Char___init__impl__6a9atx(1489)), 'b'), to(new Char(_Char___init__impl__6a9atx(1499)), 'k'), to(new Char(_Char___init__impl__6a9atx(1508)), 'p')]);
    this.uc_1 = mapOf([to(1456, '@'), to(1457, 'e'), to(1458, 'a'), to(1459, 'o'), to(1460, 'i'), to(1461, 'e'), to(1462, 'E'), to(1463, 'a'), to(1464, 'a'), to(1465, 'o'), to(1466, 'o'), to(1467, 'u')]);
  }
  protoOf(IpaColor).vc = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0 = word.toLowerCase();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[^a-z]').k7(tmp0, '');
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(w) === 0)
      return '';
    var _iterator__ex2g4s = this.rc_1.i();
    while (_iterator__ex2g4s.j()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.k();
      var pattern = _destruct__k2r9zo.d9();
      var ipa = _destruct__k2r9zo.e9();
      if (pattern.g7(w))
        return ipa;
    }
    return takeLast(w, 3);
  };
  protoOf(IpaColor).wc = function (word) {
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[,.\\-;:!?\\s]+$').k7(word, '');
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
        var tmp0_elvis_lhs = this.sc_1.o1(new Char(c));
        result.d6(tmp0_elvis_lhs == null ? '' : tmp0_elvis_lhs);
      } else if (cp === 1468) {
        var tmp1_safe_receiver = lastConsonant;
        var tmp = tmp1_safe_receiver;
        if ((tmp == null ? null : new Char(tmp)) == null)
          null;
        else {
          var tmp_0 = tmp1_safe_receiver;
          // Inline function 'kotlin.let' call
          var lc = (tmp_0 == null ? null : new Char(tmp_0)).v_1;
          var tmp0_safe_receiver = IpaColor_getInstance().tc_1.o1(new Char(lc));
          var tmp_1;
          if (tmp0_safe_receiver == null) {
            tmp_1 = null;
          } else {
            // Inline function 'kotlin.let' call
            if (lastConsonantPos >= 0) {
              var before = result.u6(0, lastConsonantPos);
              var tmp0_safe_receiver_0 = IpaColor_getInstance().sc_1.o1(new Char(lc));
              var tmp1_elvis_lhs = tmp0_safe_receiver_0 == null ? null : tmp0_safe_receiver_0.length;
              var consonantLen = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
              var after = (lastConsonantPos + consonantLen | 0) <= result.a() ? result.t6(lastConsonantPos + consonantLen | 0) : '';
              result.w6();
              result.d6(before);
              result.d6(tmp0_safe_receiver);
              result.d6(after);
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
            var before_0 = result.u6(0, lastConsonantPos);
            var after_0 = (lastConsonantPos + 2 | 0) <= result.a() ? result.t6(lastConsonantPos + 2 | 0) : '';
            result.w6();
            result.d6(before_0);
            result.d6('s');
            result.d6(after_0);
          }
        } else {
          var tmp2_safe_receiver = this.uc_1.o1(cp);
          if (tmp2_safe_receiver == null)
            null;
          else {
            // Inline function 'kotlin.let' call
            result.d6(tmp2_safe_receiver);
          }
        }
    }
    return result.toString();
  };
  protoOf(IpaColor).xc = function (line, isHebrew) {
    // Inline function 'kotlin.text.replace' call
    // Inline function 'kotlin.text.trim' call
    var this_0 = Regex_init_$Create$('[,.\\-;:!?\\s]+$').k7(line, '');
    var cleaned = toString_0(trim(isCharSequence(this_0) ? this_0 : THROW_CCE()));
    // Inline function 'kotlin.text.split' call
    var words = Regex_init_$Create$('\\s+').s7(cleaned, 0);
    var tmp0_elvis_lhs = lastOrNull(words);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return '';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lastWord = tmp;
    var ipa = isHebrew ? this.wc(lastWord) : this.vc(lastWord);
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
  protoOf(IpaColor).yc = function (ipa) {
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
  protoOf(IpaColor).zc = function (hue, saturation, lightness) {
    return 'hsl(' + hue + ', ' + saturation + '%, ' + lightness + '%)';
  };
  protoOf(IpaColor).ad = function (ipa) {
    var hue = this.yc(ipa);
    return this.zc(hue, 80, 72);
  };
  protoOf(IpaColor).bd = function (ipa) {
    var hue = this.yc(ipa);
    return this.zc(hue, 45, 55);
  };
  protoOf(IpaColor).cd = function (ipas) {
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
        var hue = IpaColor_getInstance().yc(item);
        // Inline function 'kotlin.collections.find' call
        var tmp$ret$3;
        $l$block: {
          // Inline function 'kotlin.collections.firstOrNull' call
          var _iterator__ex2g4s_0 = groups.i();
          while (_iterator__ex2g4s_0.j()) {
            var element = _iterator__ex2g4s_0.k();
            if (element.b9_1 === key) {
              tmp$ret$3 = element;
              break $l$block;
            }
          }
          tmp$ret$3 = null;
        }
        var existing = tmp$ret$3;
        var tmp_0;
        if (!(existing == null)) {
          tmp_0 = new RhymeSchemeItem(existing.c9_1.v_1, key, hue);
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
    var placeHue = imul($this.jd_1.fd_1, 36);
    var mannerOffset = imul($this.kd_1.td_1, 2);
    var voiceOffset = $this.ld_1 ? 0 : 18;
    return ((placeHue + mannerOffset | 0) + voiceOffset | 0) % 360 | 0;
  }
  function IpaMatrix$Consonant$hue$delegate$lambda(this$0) {
    return function () {
      return computeHue(this$0);
    };
  }
  function IpaMatrix$Consonant$_get_hue_$ref_uftwb6() {
    return function (p0) {
      return p0.vd();
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
    var backnessHue = imul($this.de_1.yd_1, 120);
    var heightOffset = imul($this.ce_1.le_1, 8);
    var roundOffset = $this.ee_1 ? 10 : 0;
    return ((backnessHue + heightOffset | 0) + roundOffset | 0) % 360 | 0;
  }
  function IpaMatrix$Vowel$hue$delegate$lambda(this$0) {
    return function () {
      return computeHue_0(this$0);
    };
  }
  function IpaMatrix$Vowel$_get_hue_$ref_u61xhc() {
    return function (p0) {
      return p0.vd();
    };
  }
  function Place(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.fd_1 = code;
    this.gd_1 = displayName;
  }
  function Manner(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.td_1 = code;
    this.ud_1 = displayName;
  }
  function Consonant(ipa, name, place, manner, voiced, aspirated, breathy, emphatic, languages) {
    aspirated = aspirated === VOID ? false : aspirated;
    breathy = breathy === VOID ? false : breathy;
    emphatic = emphatic === VOID ? false : emphatic;
    this.hd_1 = ipa;
    this.id_1 = name;
    this.jd_1 = place;
    this.kd_1 = manner;
    this.ld_1 = voiced;
    this.md_1 = aspirated;
    this.nd_1 = breathy;
    this.od_1 = emphatic;
    this.pd_1 = languages;
    var tmp = this;
    tmp.qd_1 = lazy(IpaMatrix$Consonant$hue$delegate$lambda(this));
  }
  protoOf(Consonant).vd = function () {
    var tmp0 = this.qd_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('hue', 1, tmp, IpaMatrix$Consonant$_get_hue_$ref_uftwb6(), null);
    return tmp0.l1();
  };
  protoOf(Consonant).toString = function () {
    return 'Consonant(ipa=' + this.hd_1 + ', name=' + this.id_1 + ', place=' + this.jd_1.toString() + ', manner=' + this.kd_1.toString() + ', voiced=' + this.ld_1 + ', aspirated=' + this.md_1 + ', breathy=' + this.nd_1 + ', emphatic=' + this.od_1 + ', languages=' + toString_0(this.pd_1) + ')';
  };
  protoOf(Consonant).hashCode = function () {
    var result = getStringHashCode(this.hd_1);
    result = imul(result, 31) + getStringHashCode(this.id_1) | 0;
    result = imul(result, 31) + this.jd_1.hashCode() | 0;
    result = imul(result, 31) + this.kd_1.hashCode() | 0;
    result = imul(result, 31) + getBooleanHashCode(this.ld_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.md_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.nd_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.od_1) | 0;
    result = imul(result, 31) + hashCode(this.pd_1) | 0;
    return result;
  };
  protoOf(Consonant).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Consonant))
      return false;
    if (!(this.hd_1 === other.hd_1))
      return false;
    if (!(this.id_1 === other.id_1))
      return false;
    if (!this.jd_1.equals(other.jd_1))
      return false;
    if (!this.kd_1.equals(other.kd_1))
      return false;
    if (!(this.ld_1 === other.ld_1))
      return false;
    if (!(this.md_1 === other.md_1))
      return false;
    if (!(this.nd_1 === other.nd_1))
      return false;
    if (!(this.od_1 === other.od_1))
      return false;
    if (!equals(this.pd_1, other.pd_1))
      return false;
    return true;
  };
  function Height(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.le_1 = code;
    this.me_1 = displayName;
  }
  function Backness(name, ordinal, code, displayName) {
    Enum.call(this, name, ordinal);
    this.yd_1 = code;
    this.zd_1 = displayName;
  }
  function Vowel(ipa, name, height, backness, rounded, long, nasal, languages) {
    long = long === VOID ? false : long;
    nasal = nasal === VOID ? false : nasal;
    this.ae_1 = ipa;
    this.be_1 = name;
    this.ce_1 = height;
    this.de_1 = backness;
    this.ee_1 = rounded;
    this.fe_1 = long;
    this.ge_1 = nasal;
    this.he_1 = languages;
    var tmp = this;
    tmp.ie_1 = lazy(IpaMatrix$Vowel$hue$delegate$lambda(this));
  }
  protoOf(Vowel).vd = function () {
    var tmp0 = this.ie_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('hue', 1, tmp, IpaMatrix$Vowel$_get_hue_$ref_u61xhc(), null);
    return tmp0.l1();
  };
  protoOf(Vowel).toString = function () {
    return 'Vowel(ipa=' + this.ae_1 + ', name=' + this.be_1 + ', height=' + this.ce_1.toString() + ', backness=' + this.de_1.toString() + ', rounded=' + this.ee_1 + ', long=' + this.fe_1 + ', nasal=' + this.ge_1 + ', languages=' + toString_0(this.he_1) + ')';
  };
  protoOf(Vowel).hashCode = function () {
    var result = getStringHashCode(this.ae_1);
    result = imul(result, 31) + getStringHashCode(this.be_1) | 0;
    result = imul(result, 31) + this.ce_1.hashCode() | 0;
    result = imul(result, 31) + this.de_1.hashCode() | 0;
    result = imul(result, 31) + getBooleanHashCode(this.ee_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.fe_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.ge_1) | 0;
    result = imul(result, 31) + hashCode(this.he_1) | 0;
    return result;
  };
  protoOf(Vowel).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Vowel))
      return false;
    if (!(this.ae_1 === other.ae_1))
      return false;
    if (!(this.be_1 === other.be_1))
      return false;
    if (!this.ce_1.equals(other.ce_1))
      return false;
    if (!this.de_1.equals(other.de_1))
      return false;
    if (!(this.ee_1 === other.ee_1))
      return false;
    if (!(this.fe_1 === other.fe_1))
      return false;
    if (!(this.ge_1 === other.ge_1))
      return false;
    if (!equals(this.he_1, other.he_1))
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
    var tmp_0 = new Consonant('p', 'voiceless bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_1 = new Consonant('b', 'voiced bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_2 = new Consonant('p\u02B0', 'aspirated bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf(['hi', 'ko', 'zh']));
    var tmp_3 = new Consonant('b\u02B1', 'breathy bilabial plosive', Place_BILABIAL_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0('hi'));
    var tmp_4 = new Consonant('t', 'voiceless alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_5 = new Consonant('d', 'voiced alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_6 = new Consonant('t\u02B0', 'aspirated alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf(['hi', 'ko', 'zh']));
    var tmp_7 = new Consonant('d\u02B1', 'breathy alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0('hi'));
    var tmp_8 = new Consonant('\u0288', 'voiceless retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf_0('hi'));
    var tmp_9 = new Consonant('\u0256', 'voiced retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf_0('hi'));
    var tmp_10 = new Consonant('\u0288\u02B0', 'aspirated retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf_0('hi'));
    var tmp_11 = new Consonant('\u0256\u02B1', 'breathy retroflex plosive', Place_RETROFLEX_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0('hi'));
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
    var tmp_18 = new Consonant('k', 'voiceless velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_19 = new Consonant('\u0261', 'voiced velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr']));
    var tmp_20 = new Consonant('k\u02B0', 'aspirated velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), false, true, VOID, VOID, listOf(['hi', 'ko', 'zh']));
    var tmp_21 = new Consonant('\u0261\u02B1', 'breathy velar plosive', Place_VELAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, true, VOID, listOf_0('hi'));
    var tmp_22 = new Consonant('q', 'voiceless uvular plosive', Place_UVULAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf_0('ar'));
    var tmp_23 = Place_UVULAR_getInstance();
    var tmp_24 = Manner_PLOSIVE_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$2 = emptyList();
    var tmp_25 = new Consonant('\u0262', 'voiced uvular plosive', tmp_23, tmp_24, true, VOID, VOID, VOID, tmp$ret$2);
    var tmp_26 = new Consonant('\u0294', 'glottal stop', Place_GLOTTAL_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'he', 'de']));
    var tmp_27 = new Consonant('m', 'bilabial nasal', Place_BILABIAL_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_28 = new Consonant('n', 'alveolar nasal', Place_ALVEOLAR_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_29 = new Consonant('\u0273', 'retroflex nasal', Place_RETROFLEX_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf_0('hi'));
    var tmp_30 = new Consonant('\u0272', 'palatal nasal', Place_PALATAL_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf(['es', 'it', 'ja', 'hi']));
    var tmp_31 = new Consonant('\u014B', 'velar nasal', Place_VELAR_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf(['en', 'de', 'nl', 'ja', 'ko', 'zh', 'hi', 'sw']));
    var tmp_32 = new Consonant('\u0274', 'uvular nasal', Place_UVULAR_getInstance(), Manner_NASAL_getInstance(), true, VOID, VOID, VOID, listOf_0('ja'));
    var tmp_33 = new Consonant('r', 'alveolar trill', Place_ALVEOLAR_getInstance(), Manner_TRILL_getInstance(), true, VOID, VOID, VOID, listOf(['es', 'it', 'ru', 'pl', 'fi', 'ar']));
    var tmp_34 = new Consonant('\u0280', 'uvular trill', Place_UVULAR_getInstance(), Manner_TRILL_getInstance(), true, VOID, VOID, VOID, listOf(['de', 'fr']));
    var tmp_35 = new Consonant('\u027E', 'alveolar tap', Place_ALVEOLAR_getInstance(), Manner_TAP_getInstance(), true, VOID, VOID, VOID, listOf(['es', 'it', 'ja', 'ko', 'pt']));
    var tmp_36 = new Consonant('\u027D', 'retroflex flap', Place_RETROFLEX_getInstance(), Manner_TAP_getInstance(), true, VOID, VOID, VOID, listOf_0('hi'));
    var tmp_37 = new Consonant('f', 'voiceless labiodental fricative', Place_LABIODENTAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_38 = new Consonant('v', 'voiced labiodental fricative', Place_LABIODENTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['da', 'de', 'el', 'en', 'fi', 'fr', 'he', 'it', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'tr']));
    var tmp_39 = new Consonant('\u03B8', 'voiceless dental fricative', Place_DENTAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'el', 'en']));
    var tmp_40 = new Consonant('\xF0', 'voiced dental fricative', Place_DENTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'el', 'en']));
    var tmp_41 = new Consonant('s', 'voiceless alveolar fricative', Place_ALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_42 = new Consonant('z', 'voiced alveolar fricative', Place_ALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['da', 'de', 'el', 'en', 'fi', 'fr', 'he', 'it', 'ja', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'tr']));
    var tmp_43 = new Consonant('\u0283', 'voiceless postalveolar fricative', Place_POSTALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'de', 'en', 'fr', 'he', 'hi', 'it', 'pl', 'pt', 'ru', 'tr']));
    var tmp_44 = new Consonant('\u0292', 'voiced postalveolar fricative', Place_POSTALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['en', 'fr', 'pl', 'pt', 'ru', 'tr']));
    var tmp_45 = new Consonant('\u0282', 'voiceless retroflex fricative', Place_RETROFLEX_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['hi', 'pl', 'zh']));
    var tmp_46 = new Consonant('\u0290', 'voiced retroflex fricative', Place_RETROFLEX_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['pl', 'zh']));
    var tmp_47 = new Consonant('\u0255', 'voiceless alveolo-palatal fricative', Place_PALATAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ja', 'ko', 'pl', 'zh']));
    var tmp_48 = new Consonant('\u0291', 'voiced alveolo-palatal fricative', Place_PALATAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['ja', 'pl']));
    var tmp_49 = new Consonant('\xE7', 'voiceless palatal fricative', Place_PALATAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['de', 'ja']));
    var tmp_50 = new Consonant('x', 'voiceless velar fricative', Place_VELAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'de', 'el', 'he', 'nl', 'pl', 'ru', 'zh']));
    var tmp_51 = new Consonant('\u0263', 'voiced velar fricative', Place_VELAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'el', 'nl']));
    var tmp_52 = new Consonant('\u03C7', 'voiceless uvular fricative', Place_UVULAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['de', 'nl', 'he']));
    var tmp_53 = new Consonant('\u0281', 'voiced uvular fricative', Place_UVULAR_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['de', 'fr', 'he', 'nl', 'pt']));
    var tmp_54 = new Consonant('\u0127', 'voiceless pharyngeal fricative', Place_PHARYNGEAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf_0('ar'));
    var tmp_55 = new Consonant('\u0295', 'voiced pharyngeal fricative', Place_PHARYNGEAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'he']));
    var tmp_56 = new Consonant('h', 'voiceless glottal fricative', Place_GLOTTAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'en', 'fi', 'he', 'hi', 'ja', 'ko', 'nl', 'no', 'sv', 'sw', 'tr']));
    var tmp_57 = new Consonant('\u0266', 'voiced glottal fricative', Place_GLOTTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf(['hi', 'nl']));
    var tmp_58 = new Consonant('\u028B', 'labiodental approximant', Place_LABIODENTAL_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf(['hi', 'nl']));
    var tmp_59 = new Consonant('\u0279', 'alveolar approximant', Place_ALVEOLAR_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0('en'));
    var tmp_60 = new Consonant('\u027B', 'retroflex approximant', Place_RETROFLEX_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0('zh'));
    var tmp_61 = new Consonant('j', 'palatal approximant', Place_PALATAL_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_62 = new Consonant('w', 'labio-velar approximant', Place_VELAR_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'en', 'es', 'fr', 'it', 'ja', 'ko', 'ms', 'nl', 'pt', 'sw', 'zh']));
    var tmp_63 = new Consonant('\u0270', 'velar approximant', Place_VELAR_getInstance(), Manner_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0('ko'));
    var tmp_64 = new Consonant('l', 'alveolar lateral approximant', Place_ALVEOLAR_getInstance(), Manner_LATERAL_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_65 = new Consonant('\u026D', 'retroflex lateral approximant', Place_RETROFLEX_getInstance(), Manner_LATERAL_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf_0('hi'));
    var tmp_66 = new Consonant('\u028E', 'palatal lateral approximant', Place_PALATAL_getInstance(), Manner_LATERAL_APPROXIMANT_getInstance(), true, VOID, VOID, VOID, listOf(['es', 'it', 'pt']));
    var tmp_67 = Place_VELAR_getInstance();
    var tmp_68 = Manner_LATERAL_APPROXIMANT_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$3 = emptyList();
    tmp.ne_1 = listOf([tmp_0, tmp_1, tmp_2, tmp_3, tmp_4, tmp_5, tmp_6, tmp_7, tmp_8, tmp_9, tmp_10, tmp_11, tmp_14, tmp_17, tmp_18, tmp_19, tmp_20, tmp_21, tmp_22, tmp_25, tmp_26, tmp_27, tmp_28, tmp_29, tmp_30, tmp_31, tmp_32, tmp_33, tmp_34, tmp_35, tmp_36, tmp_37, tmp_38, tmp_39, tmp_40, tmp_41, tmp_42, tmp_43, tmp_44, tmp_45, tmp_46, tmp_47, tmp_48, tmp_49, tmp_50, tmp_51, tmp_52, tmp_53, tmp_54, tmp_55, tmp_56, tmp_57, tmp_58, tmp_59, tmp_60, tmp_61, tmp_62, tmp_63, tmp_64, tmp_65, tmp_66, new Consonant('\u029F', 'velar lateral approximant', tmp_67, tmp_68, true, VOID, VOID, VOID, tmp$ret$3), new Consonant('ts', 'voiceless alveolar affricate', Place_ALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), false, VOID, VOID, VOID, listOf(['de', 'he', 'it', 'ja', 'ko', 'pl', 'ru', 'zh'])), new Consonant('dz', 'voiced alveolar affricate', Place_ALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), true, VOID, VOID, VOID, listOf(['it', 'ja', 'pl'])), new Consonant('t\u0283', 'voiceless postalveolar affricate', Place_POSTALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), false, VOID, VOID, VOID, listOf(['en', 'es', 'it', 'ru', 'tr'])), new Consonant('d\u0292', 'voiced postalveolar affricate', Place_POSTALVEOLAR_getInstance(), Manner_AFFRICATE_getInstance(), true, VOID, VOID, VOID, listOf(['en', 'hi', 'it', 'ja', 'ko', 'tr'])), new Consonant('t\u0255', 'voiceless alveolo-palatal affricate', Place_PALATAL_getInstance(), Manner_AFFRICATE_getInstance(), false, VOID, VOID, VOID, listOf(['ja', 'ko', 'pl', 'zh'])), new Consonant('d\u0291', 'voiced alveolo-palatal affricate', Place_PALATAL_getInstance(), Manner_AFFRICATE_getInstance(), true, VOID, VOID, VOID, listOf(['ja', 'ko', 'pl'])), new Consonant('t\u0255\u02B0', 'aspirated alveolo-palatal affricate', Place_PALATAL_getInstance(), Manner_AFFRICATE_getInstance(), false, true, VOID, VOID, listOf(['ko', 'zh'])), new Consonant('s\u02E4', 'emphatic voiceless alveolar fricative', Place_ALVEOLAR_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, true, listOf_0('ar')), new Consonant('d\u02E4', 'emphatic voiced alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), true, VOID, VOID, true, listOf_0('ar')), new Consonant('t\u02E4', 'emphatic voiceless alveolar plosive', Place_ALVEOLAR_getInstance(), Manner_PLOSIVE_getInstance(), false, VOID, VOID, true, listOf_0('ar')), new Consonant('\xF0\u02E4', 'emphatic voiced dental fricative', Place_DENTAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, true, listOf_0('ar')), new Consonant('\u0278', 'voiceless bilabial fricative', Place_BILABIAL_getInstance(), Manner_FRICATIVE_getInstance(), false, VOID, VOID, VOID, listOf_0('ja')), new Consonant('\u03B2', 'voiced bilabial fricative', Place_BILABIAL_getInstance(), Manner_FRICATIVE_getInstance(), true, VOID, VOID, VOID, listOf_0('es'))]);
    var tmp_69 = this;
    var tmp_70 = new Vowel('i', 'close front unrounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_71 = new Vowel('i\u02D0', 'long close front unrounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf(['de', 'fi', 'hi', 'ja']));
    var tmp_72 = new Vowel('y', 'close front rounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf(['da', 'de', 'fi', 'fr', 'nl', 'no', 'sv', 'tr', 'zh']));
    var tmp_73 = new Vowel('y\u02D0', 'long close front rounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), true, true, VOID, listOf(['de', 'fi']));
    var tmp_74 = new Vowel('\u0268', 'close central unrounded', Height_CLOSE_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf(['ko', 'pl', 'ru']));
    var tmp_75 = new Vowel('\u0289', 'close central rounded', Height_CLOSE_getInstance(), Backness_CENTRAL_getInstance(), true, VOID, VOID, listOf(['no', 'sv']));
    var tmp_76 = new Vowel('\u026F', 'close back unrounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf(['ja', 'ko', 'tr']));
    var tmp_77 = new Vowel('u', 'close back rounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_78 = new Vowel('u\u02D0', 'long close back rounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), true, true, VOID, listOf(['de', 'fi', 'hi', 'ja']));
    var tmp_79 = new Vowel('\u026A', 'near-close front unrounded', Height_NEAR_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf(['de', 'en', 'hi', 'nl']));
    var tmp_80 = new Vowel('\u028F', 'near-close front rounded', Height_NEAR_CLOSE_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf(['da', 'de', 'nl', 'no', 'sv']));
    var tmp_81 = new Vowel('\u028A', 'near-close back rounded', Height_NEAR_CLOSE_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf(['de', 'en', 'hi', 'nl']));
    var tmp_82 = new Vowel('e', 'close-mid front unrounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf(['da', 'de', 'el', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']));
    var tmp_83 = new Vowel('e\u02D0', 'long close-mid front unrounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf(['de', 'fi', 'hi', 'ja']));
    var tmp_84 = new Vowel('\xF8', 'close-mid front rounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf(['da', 'de', 'fi', 'fr', 'nl', 'no', 'sv', 'tr']));
    var tmp_85 = new Vowel('\xF8\u02D0', 'long close-mid front rounded', Height_CLOSE_MID_getInstance(), Backness_FRONT_getInstance(), true, true, VOID, listOf(['de', 'fi']));
    var tmp_86 = Height_CLOSE_MID_getInstance();
    var tmp_87 = Backness_CENTRAL_getInstance();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$4 = emptyList();
    tmp_69.oe_1 = listOf([tmp_70, tmp_71, tmp_72, tmp_73, tmp_74, tmp_75, tmp_76, tmp_77, tmp_78, tmp_79, tmp_80, tmp_81, tmp_82, tmp_83, tmp_84, tmp_85, new Vowel('\u0258', 'close-mid central unrounded', tmp_86, tmp_87, false, VOID, VOID, tmp$ret$4), new Vowel('\u0275', 'close-mid central rounded', Height_CLOSE_MID_getInstance(), Backness_CENTRAL_getInstance(), true, VOID, VOID, listOf(['no', 'sv'])), new Vowel('\u0264', 'close-mid back unrounded', Height_CLOSE_MID_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf(['ko', 'zh'])), new Vowel('o', 'close-mid back rounded', Height_CLOSE_MID_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf(['da', 'de', 'el', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh'])), new Vowel('o\u02D0', 'long close-mid back rounded', Height_CLOSE_MID_getInstance(), Backness_BACK_getInstance(), true, true, VOID, listOf(['de', 'fi', 'hi', 'ja'])), new Vowel('\u0259', 'schwa', Height_MID_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf(['da', 'de', 'en', 'fr', 'hi', 'nl', 'pt', 'ru', 'zh'])), new Vowel('\u025B', 'open-mid front unrounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf(['da', 'de', 'el', 'en', 'fi', 'fr', 'he', 'hi', 'it', 'ko', 'nl', 'no', 'pl', 'pt', 'sv', 'tr'])), new Vowel('\u025B\u02D0', 'long open-mid front unrounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf(['de', 'fi', 'hi'])), new Vowel('\u0153', 'open-mid front rounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), true, VOID, VOID, listOf(['da', 'de', 'fr', 'nl'])), new Vowel('\u0153\u02D0', 'long open-mid front rounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), true, true, VOID, listOf_0('de')), new Vowel('\u025C', 'open-mid central unrounded', Height_OPEN_MID_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf_0('en')), new Vowel('\u028C', 'open-mid back unrounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf(['en', 'ko'])), new Vowel('\u0254', 'open-mid back rounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf(['da', 'de', 'el', 'en', 'fi', 'fr', 'it', 'ko', 'nl', 'no', 'pl', 'pt', 'sv', 'tr'])), new Vowel('\u0254\u02D0', 'long open-mid back rounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), true, true, VOID, listOf(['de', 'en', 'fi', 'hi'])), new Vowel('\xE6', 'near-open front unrounded', Height_NEAR_OPEN_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf(['da', 'en', 'fi', 'no', 'sv'])), new Vowel('\u0250', 'near-open central', Height_NEAR_OPEN_getInstance(), Backness_CENTRAL_getInstance(), false, VOID, VOID, listOf(['de', 'pt'])), new Vowel('a', 'open front unrounded', Height_OPEN_getInstance(), Backness_FRONT_getInstance(), false, VOID, VOID, listOf(['ar', 'da', 'de', 'el', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh'])), new Vowel('a\u02D0', 'long open front unrounded', Height_OPEN_getInstance(), Backness_FRONT_getInstance(), false, true, VOID, listOf(['de', 'fi', 'hi', 'ja'])), new Vowel('\u0251', 'open back unrounded', Height_OPEN_getInstance(), Backness_BACK_getInstance(), false, VOID, VOID, listOf(['en', 'nl', 'no'])), new Vowel('\u0251\u02D0', 'long open back unrounded', Height_OPEN_getInstance(), Backness_BACK_getInstance(), false, true, VOID, listOf(['en', 'nl'])), new Vowel('\u0252', 'open back rounded', Height_OPEN_getInstance(), Backness_BACK_getInstance(), true, VOID, VOID, listOf_0('en')), new Vowel('\u025B\u0303', 'nasal open-mid front unrounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), false, VOID, true, listOf(['fr', 'pt'])), new Vowel('\u0153\u0303', 'nasal open-mid front rounded', Height_OPEN_MID_getInstance(), Backness_FRONT_getInstance(), true, VOID, true, listOf_0('fr')), new Vowel('\u0254\u0303', 'nasal open-mid back rounded', Height_OPEN_MID_getInstance(), Backness_BACK_getInstance(), true, VOID, true, listOf(['fr', 'pt'])), new Vowel('\xE3', 'nasal open front unrounded', Height_OPEN_getInstance(), Backness_FRONT_getInstance(), false, VOID, true, listOf_0('pt')), new Vowel('\u0129', 'nasal close front unrounded', Height_CLOSE_getInstance(), Backness_FRONT_getInstance(), false, VOID, true, listOf_0('pt')), new Vowel('\u0169', 'nasal close back rounded', Height_CLOSE_getInstance(), Backness_BACK_getInstance(), true, VOID, true, listOf_0('pt'))]);
    var tmp_88 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_0 = this.ne_1;
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      var tmp$ret$5 = element.hd_1;
      destination.e3(tmp$ret$5, element);
    }
    tmp_88.pe_1 = destination;
    var tmp_89 = this;
    // Inline function 'kotlin.collections.associateBy' call
    var this_1 = this.oe_1;
    var capacity_0 = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16);
    // Inline function 'kotlin.collections.associateByTo' call
    var destination_0 = LinkedHashMap_init_$Create$(capacity_0);
    var _iterator__ex2g4s_0 = this_1.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp$ret$8 = element_0.ae_1;
      destination_0.e3(tmp$ret$8, element_0);
    }
    tmp_89.qe_1 = destination_0;
    this.re_1 = listOf(['ar', 'da', 'de', 'el', 'en', 'es', 'fi', 'fr', 'he', 'hi', 'it', 'ja', 'ko', 'ms', 'nl', 'no', 'pl', 'pt', 'ru', 'sv', 'sw', 'tr', 'zh']);
  }
  protoOf(IpaMatrix).se = function (ipa) {
    return this.pe_1.o1(ipa);
  };
  protoOf(IpaMatrix).te = function (ipa) {
    return this.qe_1.o1(ipa);
  };
  protoOf(IpaMatrix).ue = function (langCode) {
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = this.ne_1;
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (element.pd_1.h1(langCode)) {
        destination.g(element);
      }
    }
    return destination;
  };
  protoOf(IpaMatrix).ve = function (langCode) {
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = this.oe_1;
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (element.he_1.h1(langCode)) {
        destination.g(element);
      }
    }
    return destination;
  };
  protoOf(IpaMatrix).we = function (ipa) {
    var tmp0_safe_receiver = this.pe_1.o1(ipa);
    if (tmp0_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      return tmp0_safe_receiver.vd();
    }
    var tmp1_safe_receiver = this.qe_1.o1(ipa);
    if (tmp1_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      return tmp1_safe_receiver.vd();
    }
    return getStringHashCode(ipa) % 360 | 0;
  };
  var IpaMatrix_instance;
  function IpaMatrix_getInstance() {
    if (IpaMatrix_instance == null)
      new IpaMatrix();
    return IpaMatrix_instance;
  }
  function KeyboardLayout(code, name, nativeName, script, keys) {
    this.xe_1 = code;
    this.ye_1 = name;
    this.ze_1 = nativeName;
    this.af_1 = script;
    this.bf_1 = keys;
  }
  protoOf(KeyboardLayout).toString = function () {
    return 'KeyboardLayout(code=' + this.xe_1 + ', name=' + this.ye_1 + ', nativeName=' + this.ze_1 + ', script=' + this.af_1.toString() + ', keys=' + toString_0(this.bf_1) + ')';
  };
  protoOf(KeyboardLayout).hashCode = function () {
    var result = getStringHashCode(this.xe_1);
    result = imul(result, 31) + getStringHashCode(this.ye_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.ze_1) | 0;
    result = imul(result, 31) + this.af_1.hashCode() | 0;
    result = imul(result, 31) + hashCode(this.bf_1) | 0;
    return result;
  };
  protoOf(KeyboardLayout).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof KeyboardLayout))
      return false;
    if (!(this.xe_1 === other.xe_1))
      return false;
    if (!(this.ye_1 === other.ye_1))
      return false;
    if (!(this.ze_1 === other.ze_1))
      return false;
    if (!this.af_1.equals(other.af_1))
      return false;
    if (!equals(this.bf_1, other.bf_1))
      return false;
    return true;
  };
  function Companion_3() {
  }
  var Companion_instance_3;
  function Companion_getInstance_3() {
    return Companion_instance_3;
  }
  function LayoutKey(char, ipa, name, modifiers) {
    modifiers = modifiers === VOID ? emptyMap() : modifiers;
    this.cf_1 = char;
    this.df_1 = ipa;
    this.ef_1 = name;
    this.ff_1 = modifiers;
  }
  protoOf(LayoutKey).gf = function () {
    return this.ff_1.o1(Modifier_SHIFT_getInstance());
  };
  protoOf(LayoutKey).toString = function () {
    return 'LayoutKey(char=' + this.cf_1 + ', ipa=' + this.df_1 + ', name=' + this.ef_1 + ', modifiers=' + toString_0(this.ff_1) + ')';
  };
  protoOf(LayoutKey).hashCode = function () {
    var result = getStringHashCode(this.cf_1);
    result = imul(result, 31) + getStringHashCode(this.df_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.ef_1) | 0;
    result = imul(result, 31) + hashCode(this.ff_1) | 0;
    return result;
  };
  protoOf(LayoutKey).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof LayoutKey))
      return false;
    if (!(this.cf_1 === other.cf_1))
      return false;
    if (!(this.df_1 === other.df_1))
      return false;
    if (!(this.ef_1 === other.ef_1))
      return false;
    if (!equals(this.ff_1, other.ff_1))
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
      tmp = mapOf_0(to(tmp_0, new LayoutKey(shiftChar, tmp_1, shiftName == null ? 'shift-' + name : shiftName)));
    } else {
      tmp = emptyMap();
    }
    var modifiers = tmp;
    return new LayoutKey(char, ipa, name, modifiers);
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
        var lower = toString(c);
        // Inline function 'kotlin.text.uppercase' call
        // Inline function 'kotlin.js.asDynamic' call
        var upper = lower.toUpperCase();
        var ipa = getLatinIpa($this, c);
        // Inline function 'kotlin.collections.set' call
        var value = key($this, lower, ipa, lower, upper, ipa, upper);
        base.e3(lower, value);
      }
       while (inductionVariable <= _Char___init__impl__6a9atx(122));
    // Inline function 'kotlin.collections.set' call
    var value_0 = key($this, '1', '', 'one', '!', '', 'exclamation');
    base.e3('1', value_0);
    // Inline function 'kotlin.collections.set' call
    var value_1 = key($this, '2', '', 'two', '@', '', 'at');
    base.e3('2', value_1);
    // Inline function 'kotlin.collections.set' call
    var value_2 = key($this, '3', '', 'three', '#', '', 'hash');
    base.e3('3', value_2);
    // Inline function 'kotlin.collections.set' call
    var value_3 = key($this, '4', '', 'four', currency, '', currencyName);
    base.e3('4', value_3);
    // Inline function 'kotlin.collections.set' call
    var value_4 = key($this, '5', '', 'five', '%', '', 'percent');
    base.e3('5', value_4);
    // Inline function 'kotlin.collections.set' call
    var value_5 = key($this, '6', '', 'six', '^', '', 'caret');
    base.e3('6', value_5);
    // Inline function 'kotlin.collections.set' call
    var value_6 = key($this, '7', '', 'seven', '&', '', 'ampersand');
    base.e3('7', value_6);
    // Inline function 'kotlin.collections.set' call
    var value_7 = key($this, '8', '', 'eight', '*', '', 'asterisk');
    base.e3('8', value_7);
    // Inline function 'kotlin.collections.set' call
    var value_8 = key($this, '9', '', 'nine', '(', '', 'left-paren');
    base.e3('9', value_8);
    // Inline function 'kotlin.collections.set' call
    var value_9 = key($this, '0', '', 'zero', ')', '', 'right-paren');
    base.e3('0', value_9);
    base.f3(overrides);
    return base;
  }
  function getLatinIpa($this, c) {
    return c === _Char___init__impl__6a9atx(97) ? 'a' : c === _Char___init__impl__6a9atx(98) ? 'b' : c === _Char___init__impl__6a9atx(99) ? 'k' : c === _Char___init__impl__6a9atx(100) ? 'd' : c === _Char___init__impl__6a9atx(101) ? 'e' : c === _Char___init__impl__6a9atx(102) ? 'f' : c === _Char___init__impl__6a9atx(103) ? '\u0261' : c === _Char___init__impl__6a9atx(104) ? 'h' : c === _Char___init__impl__6a9atx(105) ? 'i' : c === _Char___init__impl__6a9atx(106) ? 'd\u0292' : c === _Char___init__impl__6a9atx(107) ? 'k' : c === _Char___init__impl__6a9atx(108) ? 'l' : c === _Char___init__impl__6a9atx(109) ? 'm' : c === _Char___init__impl__6a9atx(110) ? 'n' : c === _Char___init__impl__6a9atx(111) ? 'o' : c === _Char___init__impl__6a9atx(112) ? 'p' : c === _Char___init__impl__6a9atx(113) ? 'k' : c === _Char___init__impl__6a9atx(114) ? 'r' : c === _Char___init__impl__6a9atx(115) ? 's' : c === _Char___init__impl__6a9atx(116) ? 't' : c === _Char___init__impl__6a9atx(117) ? 'u' : c === _Char___init__impl__6a9atx(118) ? 'v' : c === _Char___init__impl__6a9atx(119) ? 'w' : c === _Char___init__impl__6a9atx(120) ? 'ks' : c === _Char___init__impl__6a9atx(121) ? 'j' : c === _Char___init__impl__6a9atx(122) ? 'z' : '';
  }
  function KeyboardLayouts() {
    KeyboardLayouts_instance = this;
    this.hf_1 = listOf(['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p']);
    this.if_1 = listOf(['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l']);
    this.jf_1 = listOf(['z', 'x', 'c', 'v', 'b', 'n', 'm']);
    this.kf_1 = new KeyboardLayout('ar', 'Arabic', '\u0627\u0644\u0639\u0631\u0628\u064A\u0629', Script_ARABIC_getInstance(), mapOf([to('q', key(this, '\u0636', 'd\u02E4', 'dad', '\u064E', 'a', 'fatha')), to('w', key(this, '\u0635', 's\u02E4', 'sad', '\u064B', 'an', 'tanwin-fath')), to('e', key(this, '\u062B', '\u03B8', 'tha', '\u064F', 'u', 'damma')), to('r', key(this, '\u0642', 'q', 'qaf', '\u064C', 'un', 'tanwin-damm')), to('t', key(this, '\u0641', 'f', 'fa', '\u0650', 'i', 'kasra')), to('y', key(this, '\u063A', '\u0263', 'ghain', '\u064D', 'in', 'tanwin-kasr')), to('u', key(this, '\u0639', '\u0295', 'ain', '\u0651', '', 'shadda')), to('i', key(this, '\u0647', 'h', 'ha', '\u0652', '', 'sukun')), to('o', key(this, '\u062E', 'x', 'kha', '\u0640', '', 'tatweel')), to('p', key(this, '\u062D', '\u0127', 'ha', '\u061B', '', 'semicolon')), to('a', key(this, '\u0634', '\u0283', 'shin', '\\', '', 'backslash')), to('s', key_0(this, '\u0633', 's', 'sin')), to('d', key(this, '\u064A', 'j', 'ya', ']', '', 'bracket-right')), to('f', key(this, '\u0628', 'b', 'ba', '[', '', 'bracket-left')), to('g', key(this, '\u0644', 'l', 'lam', '\u0644\u0623', 'la\u0294', 'lam-hamza')), to('h', key(this, '\u0627', '', 'alif', '\u0623', '\u0294', 'alif-hamza')), to('j', key(this, '\u062A', 't', 'ta', '\u0640', '', 'tatweel')), to('k', key(this, '\u0646', 'n', 'nun', '\u060C', '', 'comma')), to('l', key(this, '\u0645', 'm', 'mim', '/', '', 'slash')), to('z', key(this, '\u0626', '\u0294', 'hamza-ya', '~', '', 'tilde')), to('x', key(this, '\u0621', '\u0294', 'hamza', '\u0652', '', 'sukun')), to('c', key(this, '\u0624', '\u0294', 'hamza-waw', '}', '', 'brace-right')), to('v', key(this, '\u0631', 'r', 'ra', '{', '', 'brace-left')), to('b', key(this, '\u0644\u0627', 'la', 'lam-alif', '\u0644\u0622', 'la\u02D0', 'lam-alif-madda')), to('n', key(this, '\u0649', 'a\u02D0', 'alif-maqsura', '\u0622', '\u0294a\u02D0', 'alif-madda')), to('m', key(this, '\u0629', 'a', 'ta-marbuta', "'", '', 'quote')), to(',', key(this, '\u0648', 'w', 'waw', ',', '', 'comma')), to('.', key(this, '\u0632', 'z', 'zay', '.', '', 'period')), to('/', key(this, '\u0638', '\xF0\u02E4', 'za', '\u061F', '', 'question'))]));
    this.lf_1 = new KeyboardLayout('da', 'Danish', 'Dansk', Script_LATIN_getInstance(), buildLatinLayout(this, 'kr', 'krone', mapOf([to(';', key(this, '\xE6', '\u025B', 'ae', '\xC6', '\u025B', 'AE')), to("'", key(this, '\xF8', '\xF8', 'oe', '\xD8', '\xF8', 'OE')), to('[', key(this, '\xE5', '\u0254', 'aa', '\xC5', '\u0254', 'AA'))])));
    this.mf_1 = new KeyboardLayout('de', 'German', 'Deutsch', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20AC', 'euro', mapOf([to(';', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut')), to("'", key(this, '\xE4', '\u025B', 'a-umlaut', '\xC4', '\u025B', 'A-umlaut')), to('[', key(this, '\xFC', 'y', 'u-umlaut', '\xDC', 'y', 'U-umlaut')), to(']', key(this, '\xDF', 's', 'eszett', '\u1E9E', 's', 'Eszett'))])));
    this.nf_1 = new KeyboardLayout('el', 'Greek', '\u0395\u03BB\u03BB\u03B7\u03BD\u03B9\u03BA\u03AC', Script_GREEK_getInstance(), mapOf([to('q', key(this, ';', '', 'semicolon', ':', '', 'colon')), to('w', key(this, '\u03C2', 's', 'final-sigma', '\u0385', '', 'dialytika-tonos')), to('e', key(this, '\u03B5', 'e', 'epsilon', '\u0395', 'e', 'Epsilon')), to('r', key(this, '\u03C1', 'r', 'rho', '\u03A1', 'r', 'Rho')), to('t', key(this, '\u03C4', 't', 'tau', '\u03A4', 't', 'Tau')), to('y', key(this, '\u03C5', 'i', 'upsilon', '\u03A5', 'i', 'Upsilon')), to('u', key(this, '\u03B8', '\u03B8', 'theta', '\u0398', '\u03B8', 'Theta')), to('i', key(this, '\u03B9', 'i', 'iota', '\u0399', 'i', 'Iota')), to('o', key(this, '\u03BF', 'o', 'omicron', '\u039F', 'o', 'Omicron')), to('p', key(this, '\u03C0', 'p', 'pi', '\u03A0', 'p', 'Pi')), to('a', key(this, '\u03B1', 'a', 'alpha', '\u0391', 'a', 'Alpha')), to('s', key(this, '\u03C3', 's', 'sigma', '\u03A3', 's', 'Sigma')), to('d', key(this, '\u03B4', '\xF0', 'delta', '\u0394', '\xF0', 'Delta')), to('f', key(this, '\u03C6', 'f', 'phi', '\u03A6', 'f', 'Phi')), to('g', key(this, '\u03B3', '\u0263', 'gamma', '\u0393', '\u0263', 'Gamma')), to('h', key(this, '\u03B7', 'i', 'eta', '\u0397', 'i', 'Eta')), to('j', key(this, '\u03BE', 'ks', 'xi', '\u039E', 'ks', 'Xi')), to('k', key(this, '\u03BA', 'k', 'kappa', '\u039A', 'k', 'Kappa')), to('l', key(this, '\u03BB', 'l', 'lambda', '\u039B', 'l', 'Lambda')), to('z', key(this, '\u03B6', 'z', 'zeta', '\u0396', 'z', 'Zeta')), to('x', key(this, '\u03C7', 'x', 'chi', '\u03A7', 'x', 'Chi')), to('c', key(this, '\u03C8', 'ps', 'psi', '\u03A8', 'ps', 'Psi')), to('v', key(this, '\u03C9', 'o', 'omega', '\u03A9', 'o', 'Omega')), to('b', key(this, '\u03B2', 'v', 'beta', '\u0392', 'v', 'Beta')), to('n', key(this, '\u03BD', 'n', 'nu', '\u039D', 'n', 'Nu')), to('m', key(this, '\u03BC', 'm', 'mu', '\u039C', 'm', 'Mu'))]));
    this.of_1 = new KeyboardLayout('en', 'English (US)', 'English', Script_LATIN_getInstance(), buildLatinLayout(this, '$', 'dollar', emptyMap()));
    this.pf_1 = new KeyboardLayout('en-gb', 'English (UK)', 'English', Script_LATIN_getInstance(), buildLatinLayout(this, '\xA3', 'pound', mapOf_0(to('3', key(this, '3', '', 'three', '\xA3', '', 'pound')))));
    this.qf_1 = new KeyboardLayout('es', 'Spanish', 'Espa\xF1ol', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20AC', 'euro', mapOf([to(';', key(this, '\xF1', '\u0272', 'ene', '\xD1', '\u0272', 'Ene')), to("'", key(this, '\xB4', '', 'acute', '\xA8', '', 'diaeresis')), to('[', key(this, '\xBF', '', 'inverted-question', '\xA1', '', 'inverted-exclamation'))])));
    this.rf_1 = new KeyboardLayout('fi', 'Finnish', 'Suomi', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20AC', 'euro', mapOf([to(';', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut')), to("'", key(this, '\xE4', '\xE6', 'a-umlaut', '\xC4', '\xE6', 'A-umlaut'))])));
    this.sf_1 = new KeyboardLayout('fr', 'French', 'Fran\xE7ais', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20AC', 'euro', mapOf([to(';', key(this, '\xE9', 'e', 'e-acute', '\xC9', 'e', 'E-acute')), to("'", key(this, '\xE8', '\u025B', 'e-grave', '\xC8', '\u025B', 'E-grave')), to('[', key(this, '\xE7', 's', 'c-cedilla', '\xC7', 's', 'C-cedilla')), to(']', key(this, '\xE0', 'a', 'a-grave', '\xC0', 'a', 'A-grave'))])));
    this.tf_1 = new KeyboardLayout('he', 'Hebrew', '\u05E2\u05D1\u05E8\u05D9\u05EA', Script_HEBREW_getInstance(), mapOf([to('t', key_0(this, '\u05D0', '\u0294', 'alef')), to('c', key_0(this, '\u05D1', 'v', 'bet')), to('d', key_0(this, '\u05D2', 'g', 'gimel')), to('s', key_0(this, '\u05D3', 'd', 'dalet')), to('v', key_0(this, '\u05D4', 'h', 'he')), to('u', key_0(this, '\u05D5', 'v', 'vav')), to('z', key_0(this, '\u05D6', 'z', 'zayin')), to('j', key_0(this, '\u05D7', 'x', 'chet')), to('y', key_0(this, '\u05D8', 't', 'tet')), to('h', key_0(this, '\u05D9', 'j', 'yod')), to('l', key_0(this, '\u05DA', 'x', 'kaf-sofit')), to('f', key_0(this, '\u05DB', 'x', 'kaf')), to('k', key_0(this, '\u05DC', 'l', 'lamed')), to('o', key_0(this, '\u05DD', 'm', 'mem-sofit')), to('n', key_0(this, '\u05DE', 'm', 'mem')), to('i', key_0(this, '\u05DF', 'n', 'nun-sofit')), to('b', key_0(this, '\u05E0', 'n', 'nun')), to('x', key_0(this, '\u05E1', 's', 'samech')), to('g', key_0(this, '\u05E2', '\u0295', 'ayin')), to(';', key_0(this, '\u05E3', 'f', 'pe-sofit')), to('p', key_0(this, '\u05E4', 'f', 'pe')), to('.', key_0(this, '\u05E5', 'ts', 'tsadi-sofit')), to('m', key_0(this, '\u05E6', 'ts', 'tsadi')), to('e', key_0(this, '\u05E7', 'k', 'qof')), to('r', key_0(this, '\u05E8', '\u0281', 'resh')), to('a', key_0(this, '\u05E9', '\u0283', 'shin')), to(',', key_0(this, '\u05EA', 't', 'tav')), to('4', key(this, '\u20AA', '', 'shekel', '$', '', 'dollar'))]));
    this.uf_1 = new KeyboardLayout('hi', 'Hindi', '\u0939\u093F\u0928\u094D\u0926\u0940', Script_DEVANAGARI_getInstance(), mapOf([to('q', key(this, '\u0914', '\u0254\u02D0', 'au', '\u094C', '\u0254\u02D0', 'au-matra')), to('w', key(this, '\u0910', '\u025B\u02D0', 'ai', '\u0948', '\u025B\u02D0', 'ai-matra')), to('e', key(this, '\u0906', 'a\u02D0', 'aa', '\u093E', 'a\u02D0', 'aa-matra')), to('r', key(this, '\u0908', 'i\u02D0', 'ii', '\u0940', 'i\u02D0', 'ii-matra')), to('t', key(this, '\u090A', 'u\u02D0', 'uu', '\u0942', 'u\u02D0', 'uu-matra')), to('y', key_0(this, '\u092D', 'b\u02B1', 'bha')), to('u', key_0(this, '\u0919', '\u014B', 'nga')), to('i', key_0(this, '\u0918', '\u0261\u02B1', 'gha')), to('o', key_0(this, '\u0927', 'd\u02B1', 'dha')), to('p', key_0(this, '\u091D', 'd\u0292\u02B1', 'jha')), to('a', key(this, '\u0913', 'o\u02D0', 'o', '\u094B', 'o\u02D0', 'o-matra')), to('s', key(this, '\u090F', 'e\u02D0', 'e', '\u0947', 'e\u02D0', 'e-matra')), to('d', key_0(this, '\u0905', '\u0259', 'a')), to('f', key(this, '\u0907', '\u026A', 'i', '\u093F', '\u026A', 'i-matra')), to('g', key(this, '\u0909', '\u028A', 'u', '\u0941', '\u028A', 'u-matra')), to('h', key_0(this, '\u092A', 'p', 'pa')), to('j', key(this, '\u0930', 'r', 'ra', '\u094D', '', 'virama')), to('k', key_0(this, '\u0915', 'k', 'ka')), to('l', key_0(this, '\u0924', 't', 'ta')), to('z', key_0(this, '\u0949', '\u0254', 'candra-o')), to('x', key_0(this, '\u0901', '\u0303', 'chandrabindu')), to('c', key_0(this, '\u092E', 'm', 'ma')), to('v', key_0(this, '\u0928', 'n', 'na')), to('b', key_0(this, '\u092C', 'b', 'ba')), to('n', key_0(this, '\u0932', 'l', 'la')), to('m', key_0(this, '\u0938', 's', 'sa')), to(',', key(this, '\u0935', '\u028B', 'va', '\u0943', '\u027B', 'ri-matra')), to('.', key_0(this, '\u092F', 'j', 'ya')), to('/', key_0(this, '\u091C', 'd\u0292', 'ja')), to('4', key(this, '\u20B9', '', 'rupee', '$', '', 'dollar'))]));
    this.vf_1 = new KeyboardLayout('it', 'Italian', 'Italiano', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20AC', 'euro', mapOf([to(';', key(this, '\xF2', '\u0254', 'o-grave', '\xD2', '\u0254', 'O-grave')), to("'", key(this, '\xE0', 'a', 'a-grave', '\xC0', 'a', 'A-grave')), to('[', key(this, '\xE8', '\u025B', 'e-grave', '\xC8', '\u025B', 'E-grave')), to(']', key(this, '\xF9', 'u', 'u-grave', '\xD9', 'u', 'U-grave')), to('\\', key(this, '\xEC', 'i', 'i-grave', '\xCC', 'i', 'I-grave'))])));
    this.wf_1 = new KeyboardLayout('ja', 'Japanese', '\u65E5\u672C\u8A9E', Script_HIRAGANA_getInstance(), mapOf([to('a', key(this, '\u3042', 'a', 'a', '\u30A2', 'a', 'a-katakana')), to('i', key(this, '\u3044', 'i', 'i', '\u30A4', 'i', 'i-katakana')), to('u', key(this, '\u3046', '\u026F', 'u', '\u30A6', '\u026F', 'u-katakana')), to('e', key(this, '\u3048', 'e', 'e', '\u30A8', 'e', 'e-katakana')), to('o', key(this, '\u304A', 'o', 'o', '\u30AA', 'o', 'o-katakana')), to('k', key(this, '\u304B', 'k', 'ka', '\u30AB', 'k', 'ka-katakana')), to('s', key(this, '\u3055', 's', 'sa', '\u30B5', 's', 'sa-katakana')), to('t', key(this, '\u305F', 't', 'ta', '\u30BF', 't', 'ta-katakana')), to('n', key(this, '\u306A', 'n', 'na', '\u30CA', 'n', 'na-katakana')), to('h', key(this, '\u306F', 'h', 'ha', '\u30CF', 'h', 'ha-katakana')), to('m', key(this, '\u307E', 'm', 'ma', '\u30DE', 'm', 'ma-katakana')), to('y', key(this, '\u3084', 'j', 'ya', '\u30E4', 'j', 'ya-katakana')), to('r', key(this, '\u3089', '\u027E', 'ra', '\u30E9', '\u027E', 'ra-katakana')), to('w', key(this, '\u308F', 'w', 'wa', '\u30EF', 'w', 'wa-katakana')), to('g', key(this, '\u304C', '\u0261', 'ga', '\u30AC', '\u0261', 'ga-katakana')), to('z', key(this, '\u3056', 'z', 'za', '\u30B6', 'z', 'za-katakana')), to('d', key(this, '\u3060', 'd', 'da', '\u30C0', 'd', 'da-katakana')), to('b', key(this, '\u3070', 'b', 'ba', '\u30D0', 'b', 'ba-katakana')), to('p', key(this, '\u3071', 'p', 'pa', '\u30D1', 'p', 'pa-katakana')), to('f', key(this, '\u3075', '\u0278', 'fu', '\u30D5', '\u0278', 'fu-katakana')), to('j', key(this, '\u3058', 'd\u0292', 'ji', '\u30B8', 'd\u0292', 'ji-katakana')), to('c', key(this, '\u3061', 't\u0255', 'chi', '\u30C1', 't\u0255', 'chi-katakana')), to('x', key(this, '\u3063', '', 'small-tsu', '\u30C3', '', 'small-tsu-katakana')), to('v', key(this, '\u3093', 'n', 'n', '\u30F3', 'n', 'n-katakana')), to('l', key_0(this, '\u30FC', '\u02D0', 'long-vowel')), to('q', key_0(this, '\u3002', '', 'period')), to('/', key_0(this, '\u30FB', '', 'middle-dot')), to('4', key(this, '\xA5', '', 'yen', '$', '', 'dollar'))]));
    this.xf_1 = new KeyboardLayout('ko', 'Korean', '\uD55C\uAD6D\uC5B4', Script_HANGUL_getInstance(), mapOf([to('q', key(this, '\u3142', 'p', 'bieup', '\u3143', 'p\u0348', 'ssang-bieup')), to('w', key(this, '\u3148', 't\u0255', 'jieut', '\u3149', 't\u0348\u0255', 'ssang-jieut')), to('e', key(this, '\u3137', 't', 'digeut', '\u3138', 't\u0348', 'ssang-digeut')), to('r', key(this, '\u3131', 'k', 'giyeok', '\u3132', 'k\u0348', 'ssang-giyeok')), to('t', key(this, '\u3145', 's', 'siot', '\u3146', 's\u0348', 'ssang-siot')), to('a', key_0(this, '\u3141', 'm', 'mieum')), to('s', key_0(this, '\u3134', 'n', 'nieun')), to('d', key_0(this, '\u3147', '\u014B', 'ieung')), to('f', key_0(this, '\u3139', '\u027E', 'rieul')), to('g', key_0(this, '\u314E', 'h', 'hieut')), to('z', key_0(this, '\u314B', 'k\u02B0', 'kieuk')), to('x', key_0(this, '\u314C', 't\u02B0', 'tieut')), to('c', key_0(this, '\u314A', 't\u0255\u02B0', 'chieut')), to('v', key_0(this, '\u314D', 'p\u02B0', 'pieup')), to('y', key_0(this, '\u315B', 'jo', 'yo')), to('u', key_0(this, '\u3155', 'j\u028C', 'yeo')), to('i', key_0(this, '\u3151', 'ja', 'ya')), to('o', key(this, '\u3150', '\u025B', 'ae', '\u3152', 'j\u025B', 'yae')), to('p', key(this, '\u3154', 'e', 'e', '\u3156', 'je', 'ye')), to('h', key_0(this, '\u3157', 'o', 'o')), to('j', key_0(this, '\u3153', '\u028C', 'eo')), to('k', key_0(this, '\u314F', 'a', 'a')), to('l', key_0(this, '\u3163', 'i', 'i')), to('b', key_0(this, '\u3160', 'ju', 'yu')), to('n', key_0(this, '\u315C', 'u', 'u')), to('m', key_0(this, '\u3161', '\u0268', 'eu')), to('4', key(this, '\u20A9', '', 'won', '$', '', 'dollar'))]));
    this.yf_1 = new KeyboardLayout('ms', 'Malay', 'Bahasa Melayu', Script_LATIN_getInstance(), buildLatinLayout(this, 'RM', 'ringgit', emptyMap()));
    this.zf_1 = new KeyboardLayout('nl', 'Dutch', 'Nederlands', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20AC', 'euro', mapOf([to("'", key(this, '\xB4', '', 'acute', '\xA8', '', 'diaeresis')), to('[', key(this, 'ij', '\u025Bi', 'ij', 'IJ', '\u025Bi', 'IJ'))])));
    this.ag_1 = new KeyboardLayout('no', 'Norwegian', 'Norsk', Script_LATIN_getInstance(), buildLatinLayout(this, 'kr', 'krone', mapOf([to(';', key(this, '\xF8', '\xF8', 'oe', '\xD8', '\xF8', 'OE')), to("'", key(this, '\xE6', '\xE6', 'ae', '\xC6', '\xE6', 'AE')), to('[', key(this, '\xE5', '\u0254', 'aa', '\xC5', '\u0254', 'AA'))])));
    this.bg_1 = new KeyboardLayout('pl', 'Polish', 'Polski', Script_LATIN_getInstance(), buildLatinLayout(this, 'z\u0142', 'zloty', mapOf([to('a', key(this, 'a', 'a', 'a', '\u0105', '\u0254\u0303', 'a-ogonek')), to('c', key(this, 'c', 'ts', 'c', '\u0107', 't\u0255', 'c-acute')), to('e', key(this, 'e', 'e', 'e', '\u0119', '\u025B\u0303', 'e-ogonek')), to('l', key(this, 'l', 'l', 'l', '\u0142', 'w', 'l-stroke')), to('n', key(this, 'n', 'n', 'n', '\u0144', '\u0272', 'n-acute')), to('o', key(this, 'o', 'o', 'o', '\xF3', 'u', 'o-acute')), to('s', key(this, 's', 's', 's', '\u015B', '\u0255', 's-acute')), to('z', key(this, 'z', 'z', 'z', '\u017C', '\u0290', 'z-dot')), to('x', key(this, 'x', 'ks', 'x', '\u017A', '\u0291', 'z-acute'))])));
    this.cg_1 = new KeyboardLayout('pt', 'Portuguese', 'Portugu\xEAs', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20AC', 'euro', mapOf([to(';', key(this, '\xE7', 's', 'c-cedilla', '\xC7', 's', 'C-cedilla')), to("'", key(this, '~', '', 'tilde', '^', '', 'circumflex')), to('[', key(this, '\xB4', '', 'acute', '`', '', 'grave')), to(']', key(this, '\xE3', '\u0250\u0303', 'a-tilde', '\xC3', '\u0250\u0303', 'A-tilde'))])));
    this.dg_1 = new KeyboardLayout('pt-br', 'Portuguese (Brazil)', 'Portugu\xEAs (Brasil)', Script_LATIN_getInstance(), buildLatinLayout(this, 'R$', 'real', mapOf([to(';', key(this, '\xE7', 's', 'c-cedilla', '\xC7', 's', 'C-cedilla')), to("'", key(this, '~', '', 'tilde', '^', '', 'circumflex')), to('[', key(this, '\xB4', '', 'acute', '`', '', 'grave')), to(']', key(this, '\xE3', '\u0250\u0303', 'a-tilde', '\xC3', '\u0250\u0303', 'A-tilde'))])));
    this.eg_1 = new KeyboardLayout('ru', 'Russian', '\u0420\u0443\u0441\u0441\u043A\u0438\u0439', Script_CYRILLIC_getInstance(), mapOf([to('q', key(this, '\u0439', 'j', 'short-i', '\u0419', 'j', 'Short-I')), to('w', key(this, '\u0446', 'ts', 'tse', '\u0426', 'ts', 'Tse')), to('e', key(this, '\u0443', 'u', 'u', '\u0423', 'u', 'U')), to('r', key(this, '\u043A', 'k', 'ka', '\u041A', 'k', 'Ka')), to('t', key(this, '\u0435', 'je', 'ye', '\u0415', 'je', 'Ye')), to('y', key(this, '\u043D', 'n', 'en', '\u041D', 'n', 'En')), to('u', key(this, '\u0433', '\u0261', 'ge', '\u0413', '\u0261', 'Ge')), to('i', key(this, '\u0448', '\u0283', 'sha', '\u0428', '\u0283', 'Sha')), to('o', key(this, '\u0449', '\u0283t\u0283', 'shcha', '\u0429', '\u0283t\u0283', 'Shcha')), to('p', key(this, '\u0437', 'z', 'ze', '\u0417', 'z', 'Ze')), to('[', key(this, '\u0445', 'x', 'kha', '\u0425', 'x', 'Kha')), to(']', key(this, '\u044A', '', 'hard-sign', '\u042A', '', 'Hard-sign')), to('a', key(this, '\u0444', 'f', 'ef', '\u0424', 'f', 'Ef')), to('s', key(this, '\u044B', '\u0268', 'yeru', '\u042B', '\u0268', 'Yeru')), to('d', key(this, '\u0432', 'v', 've', '\u0412', 'v', 'Ve')), to('f', key(this, '\u0430', 'a', 'a', '\u0410', 'a', 'A')), to('g', key(this, '\u043F', 'p', 'pe', '\u041F', 'p', 'Pe')), to('h', key(this, '\u0440', 'r', 'er', '\u0420', 'r', 'Er')), to('j', key(this, '\u043E', 'o', 'o', '\u041E', 'o', 'O')), to('k', key(this, '\u043B', 'l', 'el', '\u041B', 'l', 'El')), to('l', key(this, '\u0434', 'd', 'de', '\u0414', 'd', 'De')), to(';', key(this, '\u0436', '\u0292', 'zhe', '\u0416', '\u0292', 'Zhe')), to("'", key(this, '\u044D', 'e', 'e', '\u042D', 'e', 'E')), to('z', key(this, '\u044F', 'ja', 'ya', '\u042F', 'ja', 'Ya')), to('x', key(this, '\u0447', 't\u0283', 'che', '\u0427', 't\u0283', 'Che')), to('c', key(this, '\u0441', 's', 'es', '\u0421', 's', 'Es')), to('v', key(this, '\u043C', 'm', 'em', '\u041C', 'm', 'Em')), to('b', key(this, '\u0438', 'i', 'i', '\u0418', 'i', 'I')), to('n', key(this, '\u0442', 't', 'te', '\u0422', 't', 'Te')), to('m', key(this, '\u044C', '', 'soft-sign', '\u042C', '', 'Soft-sign')), to(',', key(this, '\u0431', 'b', 'be', '\u0411', 'b', 'Be')), to('.', key(this, '\u044E', 'ju', 'yu', '\u042E', 'ju', 'Yu')), to('4', key(this, '\u20BD', '', 'ruble', '$', '', 'dollar'))]));
    this.fg_1 = new KeyboardLayout('sv', 'Swedish', 'Svenska', Script_LATIN_getInstance(), buildLatinLayout(this, 'kr', 'krona', mapOf([to(';', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut')), to("'", key(this, '\xE4', '\u025B', 'a-umlaut', '\xC4', '\u025B', 'A-umlaut')), to('[', key(this, '\xE5', '\u0254', 'a-ring', '\xC5', '\u0254', 'A-ring'))])));
    this.gg_1 = new KeyboardLayout('sw', 'Swahili', 'Kiswahili', Script_LATIN_getInstance(), buildLatinLayout(this, 'TSh', 'shilling', emptyMap()));
    this.hg_1 = new KeyboardLayout('tr', 'Turkish', 'T\xFCrk\xE7e', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20BA', 'lira', mapOf([to('i', key(this, '\u0131', '\u026F', 'dotless-i', 'I', '\u026F', 'Dotless-I')), to(';', key(this, '\u015F', '\u0283', 's-cedilla', '\u015E', '\u0283', 'S-cedilla')), to("'", key(this, 'i', 'i', 'dotted-i', '\u0130', 'i', 'Dotted-I')), to('[', key(this, '\u011F', '\u0263', 'g-breve', '\u011E', '\u0263', 'G-breve')), to(']', key(this, '\xFC', 'y', 'u-umlaut', '\xDC', 'y', 'U-umlaut')), to('\\', key(this, '\xE7', 't\u0283', 'c-cedilla', '\xC7', 't\u0283', 'C-cedilla')), to('/', key(this, '\xF6', '\xF8', 'o-umlaut', '\xD6', '\xF8', 'O-umlaut'))])));
    this.ig_1 = new KeyboardLayout('zh', 'Chinese', '\u4E2D\u6587', Script_CJK_getInstance(), mapOf([to('b', key_0(this, 'b', 'p', 'bo')), to('p', key_0(this, 'p', 'p\u02B0', 'po')), to('m', key_0(this, 'm', 'm', 'mo')), to('f', key_0(this, 'f', 'f', 'fo')), to('d', key_0(this, 'd', 't', 'de')), to('t', key_0(this, 't', 't\u02B0', 'te')), to('n', key_0(this, 'n', 'n', 'ne')), to('l', key_0(this, 'l', 'l', 'le')), to('g', key_0(this, 'g', 'k', 'ge')), to('k', key_0(this, 'k', 'k\u02B0', 'ke')), to('h', key_0(this, 'h', 'x', 'he')), to('j', key_0(this, 'j', 't\u0255', 'ji')), to('q', key_0(this, 'q', 't\u0255\u02B0', 'qi')), to('x', key_0(this, 'x', '\u0255', 'xi')), to('z', key_0(this, 'z', 'ts', 'zi')), to('c', key_0(this, 'c', 'ts\u02B0', 'ci')), to('s', key_0(this, 's', 's', 'si')), to('r', key_0(this, 'r', '\u027B', 'ri')), to('y', key_0(this, 'y', 'j', 'yi')), to('w', key_0(this, 'w', 'w', 'wu')), to('a', key_0(this, 'a', 'a', 'a')), to('o', key_0(this, 'o', 'o', 'o')), to('e', key_0(this, 'e', '\u0259', 'e')), to('i', key_0(this, 'i', 'i', 'i')), to('u', key_0(this, 'u', 'u', 'u')), to('v', key_0(this, '\xFC', 'y', 'u-umlaut')), to('4', key(this, '\xA5', '', 'yuan', '$', '', 'dollar'))]));
    this.jg_1 = new KeyboardLayout('en-au', 'English (Australia)', 'English', Script_LATIN_getInstance(), buildLatinLayout(this, '$', 'dollar', emptyMap()));
    this.kg_1 = new KeyboardLayout('en-ca', 'English (Canada)', 'English', Script_LATIN_getInstance(), buildLatinLayout(this, '$', 'dollar', emptyMap()));
    this.lg_1 = new KeyboardLayout('en-in', 'English (India)', 'English', Script_LATIN_getInstance(), buildLatinLayout(this, '\u20B9', 'rupee', emptyMap()));
    this.mg_1 = new KeyboardLayout('es-419', 'Spanish (Latin America)', 'Espa\xF1ol (Latinoam\xE9rica)', Script_LATIN_getInstance(), buildLatinLayout(this, '$', 'dollar', mapOf([to(';', key(this, '\xF1', '\u0272', 'ene', '\xD1', '\u0272', 'Ene')), to("'", key(this, '\xB4', '', 'acute', '\xA8', '', 'diaeresis')), to('[', key(this, '\xBF', '', 'inverted-question', '\xA1', '', 'inverted-exclamation'))])));
    this.ng_1 = mapOf([to('ar', this.kf_1), to('da', this.lf_1), to('de', this.mf_1), to('el', this.nf_1), to('en', this.of_1), to('es', this.qf_1), to('fi', this.rf_1), to('fr', this.sf_1), to('he', this.tf_1), to('hi', this.uf_1), to('it', this.vf_1), to('ja', this.wf_1), to('ko', this.xf_1), to('ms', this.yf_1), to('nl', this.zf_1), to('no', this.ag_1), to('pl', this.bg_1), to('pt', this.cg_1), to('ru', this.eg_1), to('sv', this.fg_1), to('sw', this.gg_1), to('tr', this.hg_1), to('zh', this.ig_1), to('en-us', this.of_1), to('en-gb', this.pf_1), to('en-au', this.jg_1), to('en-ca', this.kg_1), to('en-in', this.lg_1), to('pt-br', this.dg_1), to('es-419', this.mg_1)]);
    var tmp = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = this.ng_1.p1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (!contains(element, '-') || element === 'en-gb' || element === 'pt-br' || element === 'es-419') {
        destination.g(element);
      }
    }
    tmp.og_1 = sorted(destination);
    this.pg_1 = sorted(toList(this.ng_1.p1()));
  }
  protoOf(KeyboardLayouts).v7 = function (code) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = code.toLowerCase();
    return this.ng_1.o1(tmp$ret$1);
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
  function isGuttural($this, letter) {
    return listOf([new Char(_Char___init__impl__6a9atx(1488)), new Char(_Char___init__impl__6a9atx(1492)), new Char(_Char___init__impl__6a9atx(1495)), new Char(_Char___init__impl__6a9atx(1506)), new Char(_Char___init__impl__6a9atx(1512))]).h1(new Char(letter));
  }
  var Nikud_KAMATZ_instance;
  var Nikud_PATACH_instance;
  var Nikud_TZERE_instance;
  var Nikud_SEGOL_instance;
  var Nikud_CHIRIK_instance;
  var Nikud_CHOLAM_instance;
  var Nikud_KUBUTZ_instance;
  var Nikud_CHIRIK_MALE_instance;
  var Nikud_CHOLAM_MALE_instance;
  var Nikud_SHURUK_instance;
  var Nikud_SHVA_instance;
  var Nikud_HATAF_PATACH_instance;
  var Nikud_HATAF_SEGOL_instance;
  var Nikud_HATAF_KAMATZ_instance;
  var Nikud_DAGESH_instance;
  var Nikud_SHIN_DOT_instance;
  var Nikud_SIN_DOT_instance;
  var Nikud_RAFE_instance;
  var Nikud_MAPPIQ_instance;
  function Companion_4() {
  }
  protoOf(Companion_4).qg = function (ipa) {
    switch (ipa) {
      case 'a':
        return listOf([Nikud_PATACH_getInstance(), Nikud_KAMATZ_getInstance(), Nikud_HATAF_PATACH_getInstance()]);
      case 'e':
        return listOf([Nikud_TZERE_getInstance(), Nikud_SEGOL_getInstance(), Nikud_HATAF_SEGOL_getInstance()]);
      case '\u025B':
        return listOf([Nikud_SEGOL_getInstance(), Nikud_HATAF_SEGOL_getInstance()]);
      case 'i':
        return listOf([Nikud_CHIRIK_getInstance(), Nikud_CHIRIK_MALE_getInstance()]);
      case 'o':
        return listOf([Nikud_CHOLAM_getInstance(), Nikud_CHOLAM_MALE_getInstance(), Nikud_KAMATZ_getInstance(), Nikud_HATAF_KAMATZ_getInstance()]);
      case 'u':
        return listOf([Nikud_KUBUTZ_getInstance(), Nikud_SHURUK_getInstance()]);
      case '\u0259':
        return listOf_0(Nikud_SHVA_getInstance());
      case '\u026A':
        return listOf_0(Nikud_CHIRIK_getInstance());
      case '\u028A':
        return listOf_0(Nikud_KUBUTZ_getInstance());
      case '\xE6':
        return listOf([Nikud_SEGOL_getInstance(), Nikud_PATACH_getInstance()]);
      case '\u028C':
        return listOf_0(Nikud_PATACH_getInstance());
      case '\u0254':
        return listOf_0(Nikud_KAMATZ_getInstance());
      case '\u0251':
        return listOf_0(Nikud_KAMATZ_getInstance());
      default:
        return emptyList();
    }
  };
  protoOf(Companion_4).rg = function (letter, ipa, useMale) {
    var nikudList = this.qg(ipa);
    if (nikudList.n())
      return toString(letter);
    if (isGuttural(this, letter) && ipa === '\u0259') {
      return toString(letter) + Nikud_HATAF_PATACH_getInstance().ug_1;
    }
    var nikud = first(nikudList);
    if (useMale) {
      switch (ipa) {
        case 'i':
          return toString(letter) + Nikud_CHIRIK_MALE_getInstance().ug_1;
        case 'o':
          return toString(letter) + Nikud_CHOLAM_MALE_getInstance().ug_1;
        case 'u':
          return toString(letter) + Nikud_SHURUK_getInstance().ug_1;
        default:
          return toString(letter) + nikud.ug_1;
      }
    }
    return toString(letter) + nikud.ug_1;
  };
  var Companion_instance_4;
  function Companion_getInstance_4() {
    return Companion_instance_4;
  }
  var Nikud_entriesInitialized;
  function Nikud_initEntries() {
    if (Nikud_entriesInitialized)
      return Unit_instance;
    Nikud_entriesInitialized = true;
    Nikud_KAMATZ_instance = new Nikud('KAMATZ', 0, '\u05B8', '\u05E7\u05B8\u05DE\u05B8\u05E5', 'a', 'o');
    Nikud_PATACH_instance = new Nikud('PATACH', 1, '\u05B7', '\u05E4\u05B7\u05BC\u05EA\u05B7\u05D7', 'a');
    Nikud_TZERE_instance = new Nikud('TZERE', 2, '\u05B5', '\u05E6\u05B5\u05D9\u05E8\u05B5\u05D9', 'e');
    Nikud_SEGOL_instance = new Nikud('SEGOL', 3, '\u05B6', '\u05E1\u05B6\u05D2\u05D5\u05B9\u05DC', '\u025B');
    Nikud_CHIRIK_instance = new Nikud('CHIRIK', 4, '\u05B4', '\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7', 'i');
    Nikud_CHOLAM_instance = new Nikud('CHOLAM', 5, '\u05B9', '\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD', 'o');
    Nikud_KUBUTZ_instance = new Nikud('KUBUTZ', 6, '\u05BB', '\u05E7\u05BB\u05D1\u05BC\u05D5\u05BC\u05E5', 'u');
    Nikud_CHIRIK_MALE_instance = new Nikud('CHIRIK_MALE', 7, '\u05B4\u05D9', '\u05D7\u05B4\u05D9\u05E8\u05B4\u05D9\u05E7 \u05DE\u05B8\u05DC\u05B5\u05D0', 'i');
    Nikud_CHOLAM_MALE_instance = new Nikud('CHOLAM_MALE', 8, '\u05D5\u05B9', '\u05D7\u05D5\u05B9\u05DC\u05B8\u05DD \u05DE\u05B8\u05DC\u05B5\u05D0', 'o');
    Nikud_SHURUK_instance = new Nikud('SHURUK', 9, '\u05D5\u05BC', '\u05E9\u05C1\u05D5\u05BC\u05E8\u05D5\u05BC\u05E7', 'u');
    Nikud_SHVA_instance = new Nikud('SHVA', 10, '\u05B0', '\u05E9\u05B0\u05C1\u05D5\u05B8\u05D0', '\u0259');
    Nikud_HATAF_PATACH_instance = new Nikud('HATAF_PATACH', 11, '\u05B2', '\u05D7\u05B2\u05D8\u05B7\u05E3 \u05E4\u05B7\u05BC\u05EA\u05B7\u05D7', 'a');
    Nikud_HATAF_SEGOL_instance = new Nikud('HATAF_SEGOL', 12, '\u05B1', '\u05D7\u05B2\u05D8\u05B7\u05E3 \u05E1\u05B6\u05D2\u05D5\u05B9\u05DC', '\u025B');
    Nikud_HATAF_KAMATZ_instance = new Nikud('HATAF_KAMATZ', 13, '\u05B3', '\u05D7\u05B2\u05D8\u05B7\u05E3 \u05E7\u05B8\u05DE\u05B8\u05E5', 'o');
    Nikud_DAGESH_instance = new Nikud('DAGESH', 14, '\u05BC', '\u05D3\u05B8\u05BC\u05D2\u05B5\u05E9\u05C1', '', VOID, 'double/hard');
    Nikud_SHIN_DOT_instance = new Nikud('SHIN_DOT', 15, '\u05C1', '\u05E0\u05B0\u05E7\u05BB\u05D3\u05B7\u05BC\u05EA \u05E9\u05C1\u05B4\u05D9\u05DF', '\u0283');
    Nikud_SIN_DOT_instance = new Nikud('SIN_DOT', 16, '\u05C2', '\u05E0\u05B0\u05E7\u05BB\u05D3\u05B7\u05BC\u05EA \u05E9\u05C2\u05B4\u05D9\u05DF', 's');
    Nikud_RAFE_instance = new Nikud('RAFE', 17, '\u05BF', '\u05E8\u05B8\u05E4\u05B6\u05D4', '', VOID, 'soft');
    Nikud_MAPPIQ_instance = new Nikud('MAPPIQ', 18, '\u05BC', '\u05DE\u05B7\u05E4\u05B4\u05BC\u05D9\u05E7', 'h', VOID, 'pronounced');
  }
  function Nikud(name, ordinal, mark, displayName, ipa, ipaAlt, modifier) {
    ipaAlt = ipaAlt === VOID ? null : ipaAlt;
    modifier = modifier === VOID ? null : modifier;
    Enum.call(this, name, ordinal);
    this.ug_1 = mark;
    this.vg_1 = displayName;
    this.wg_1 = ipa;
    this.xg_1 = ipaAlt;
    this.yg_1 = modifier;
  }
  function Nikud_KAMATZ_getInstance() {
    Nikud_initEntries();
    return Nikud_KAMATZ_instance;
  }
  function Nikud_PATACH_getInstance() {
    Nikud_initEntries();
    return Nikud_PATACH_instance;
  }
  function Nikud_TZERE_getInstance() {
    Nikud_initEntries();
    return Nikud_TZERE_instance;
  }
  function Nikud_SEGOL_getInstance() {
    Nikud_initEntries();
    return Nikud_SEGOL_instance;
  }
  function Nikud_CHIRIK_getInstance() {
    Nikud_initEntries();
    return Nikud_CHIRIK_instance;
  }
  function Nikud_CHOLAM_getInstance() {
    Nikud_initEntries();
    return Nikud_CHOLAM_instance;
  }
  function Nikud_KUBUTZ_getInstance() {
    Nikud_initEntries();
    return Nikud_KUBUTZ_instance;
  }
  function Nikud_CHIRIK_MALE_getInstance() {
    Nikud_initEntries();
    return Nikud_CHIRIK_MALE_instance;
  }
  function Nikud_CHOLAM_MALE_getInstance() {
    Nikud_initEntries();
    return Nikud_CHOLAM_MALE_instance;
  }
  function Nikud_SHURUK_getInstance() {
    Nikud_initEntries();
    return Nikud_SHURUK_instance;
  }
  function Nikud_SHVA_getInstance() {
    Nikud_initEntries();
    return Nikud_SHVA_instance;
  }
  function Nikud_HATAF_PATACH_getInstance() {
    Nikud_initEntries();
    return Nikud_HATAF_PATACH_instance;
  }
  function Nikud_HATAF_SEGOL_getInstance() {
    Nikud_initEntries();
    return Nikud_HATAF_SEGOL_instance;
  }
  function Nikud_HATAF_KAMATZ_getInstance() {
    Nikud_initEntries();
    return Nikud_HATAF_KAMATZ_instance;
  }
  function UniKey(id, en, EN, he, ipa, shift, heShift, syl, enSyl, mg, mgSyl, dagesh, guttural, isFinal) {
    ipa = ipa === VOID ? '' : ipa;
    shift = shift === VOID ? null : shift;
    heShift = heShift === VOID ? null : heShift;
    syl = syl === VOID ? null : syl;
    enSyl = enSyl === VOID ? null : enSyl;
    mg = mg === VOID ? null : mg;
    mgSyl = mgSyl === VOID ? null : mgSyl;
    dagesh = dagesh === VOID ? null : dagesh;
    guttural = guttural === VOID ? false : guttural;
    isFinal = isFinal === VOID ? false : isFinal;
    this.zg_1 = id;
    this.ah_1 = en;
    this.bh_1 = EN;
    this.ch_1 = he;
    this.dh_1 = ipa;
    this.eh_1 = shift;
    this.fh_1 = heShift;
    this.gh_1 = syl;
    this.hh_1 = enSyl;
    this.ih_1 = mg;
    this.jh_1 = mgSyl;
    this.kh_1 = dagesh;
    this.lh_1 = guttural;
    this.mh_1 = isFinal;
  }
  protoOf(UniKey).nh = function (mode, mods) {
    if (mods.qh_1)
      return this.dh_1;
    if (mods.ph_1 && !(this.ih_1 == null)) {
      var mf = mods.oh_1;
      var tmp;
      switch (mode.t1_1) {
        case 2:
        case 1:
          tmp = mf ? this.ih_1.yh_1 : this.ih_1.xh_1;
          break;
        case 0:
          tmp = mf ? this.ih_1.uh_1 : this.ih_1.th_1;
          break;
        default:
          noWhenBranchMatchedException();
          break;
      }
      return tmp;
    }
    if (mods.oh_1 && !mods.ph_1) {
      var tmp_0;
      switch (mode.t1_1) {
        case 0:
          var tmp_1;
          // Inline function 'kotlin.collections.isNullOrEmpty' call

          var this_0 = this.gh_1;
          if (!(this_0 == null || this_0.n())) {
            tmp_1 = this.ch_1 + '\xB7';
          } else {
            tmp_1 = this.ch_1;
          }

          tmp_0 = tmp_1;
          break;
        case 1:
          var tmp_2;
          // Inline function 'kotlin.collections.isNullOrEmpty' call

          var this_1 = this.hh_1;
          if (!(this_1 == null || this_1.n())) {
            tmp_2 = this.ah_1 + '\xB7';
          } else {
            // Inline function 'kotlin.text.ifEmpty' call
            var this_2 = this.bh_1;
            var tmp_3;
            // Inline function 'kotlin.text.isEmpty' call
            if (charSequenceLength(this_2) === 0) {
              // Inline function 'kotlin.text.uppercase' call
              // Inline function 'kotlin.js.asDynamic' call
              tmp_3 = this.ah_1.toUpperCase();
            } else {
              tmp_3 = this_2;
            }
            tmp_2 = tmp_3;
          }

          tmp_0 = tmp_2;
          break;
        case 2:
          var tmp2_elvis_lhs = this.eh_1;
          tmp_0 = tmp2_elvis_lhs == null ? this.bh_1 : tmp2_elvis_lhs;
          break;
        default:
          noWhenBranchMatchedException();
          break;
      }
      return tmp_0;
    }
    var tmp_4;
    switch (mode.t1_1) {
      case 1:
        tmp_4 = this.ah_1;
        break;
      case 2:
        // Inline function 'kotlin.text.ifEmpty' call

        var this_3 = this.bh_1;
        var tmp_5;
        // Inline function 'kotlin.text.isEmpty' call

        if (charSequenceLength(this_3) === 0) {
          // Inline function 'kotlin.text.uppercase' call
          // Inline function 'kotlin.js.asDynamic' call
          tmp_5 = this.ah_1.toUpperCase();
        } else {
          tmp_5 = this_3;
        }

        tmp_4 = tmp_5;
        break;
      case 0:
        tmp_4 = this.ch_1;
        break;
      default:
        noWhenBranchMatchedException();
        break;
    }
    return tmp_4;
  };
  protoOf(UniKey).toString = function () {
    return 'UniKey(id=' + this.zg_1 + ', en=' + this.ah_1 + ', EN=' + this.bh_1 + ', he=' + this.ch_1 + ', ipa=' + this.dh_1 + ', shift=' + this.eh_1 + ', heShift=' + this.fh_1 + ', syl=' + toString_1(this.gh_1) + ', enSyl=' + toString_1(this.hh_1) + ', mg=' + toString_1(this.ih_1) + ', mgSyl=' + toString_1(this.jh_1) + ', dagesh=' + this.kh_1 + ', guttural=' + this.lh_1 + ', isFinal=' + this.mh_1 + ')';
  };
  protoOf(UniKey).hashCode = function () {
    var result = getStringHashCode(this.zg_1);
    result = imul(result, 31) + getStringHashCode(this.ah_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.bh_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.ch_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.dh_1) | 0;
    result = imul(result, 31) + (this.eh_1 == null ? 0 : getStringHashCode(this.eh_1)) | 0;
    result = imul(result, 31) + (this.fh_1 == null ? 0 : getStringHashCode(this.fh_1)) | 0;
    result = imul(result, 31) + (this.gh_1 == null ? 0 : hashCode(this.gh_1)) | 0;
    result = imul(result, 31) + (this.hh_1 == null ? 0 : hashCode(this.hh_1)) | 0;
    result = imul(result, 31) + (this.ih_1 == null ? 0 : this.ih_1.hashCode()) | 0;
    result = imul(result, 31) + (this.jh_1 == null ? 0 : hashCode(this.jh_1)) | 0;
    result = imul(result, 31) + (this.kh_1 == null ? 0 : getStringHashCode(this.kh_1)) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.lh_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.mh_1) | 0;
    return result;
  };
  protoOf(UniKey).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof UniKey))
      return false;
    if (!(this.zg_1 === other.zg_1))
      return false;
    if (!(this.ah_1 === other.ah_1))
      return false;
    if (!(this.bh_1 === other.bh_1))
      return false;
    if (!(this.ch_1 === other.ch_1))
      return false;
    if (!(this.dh_1 === other.dh_1))
      return false;
    if (!(this.eh_1 == other.eh_1))
      return false;
    if (!(this.fh_1 == other.fh_1))
      return false;
    if (!equals(this.gh_1, other.gh_1))
      return false;
    if (!equals(this.hh_1, other.hh_1))
      return false;
    if (!equals(this.ih_1, other.ih_1))
      return false;
    if (!equals(this.jh_1, other.jh_1))
      return false;
    if (!(this.kh_1 == other.kh_1))
      return false;
    if (!(this.lh_1 === other.lh_1))
      return false;
    if (!(this.mh_1 === other.mh_1))
      return false;
    return true;
  };
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
    this.oh_1 = shift;
    this.ph_1 = alt;
    this.qh_1 = ctrl;
  }
  protoOf(Modifiers).toString = function () {
    return 'Modifiers(shift=' + this.oh_1 + ', alt=' + this.ph_1 + ', ctrl=' + this.qh_1 + ')';
  };
  protoOf(Modifiers).hashCode = function () {
    var result = getBooleanHashCode(this.oh_1);
    result = imul(result, 31) + getBooleanHashCode(this.ph_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.qh_1) | 0;
    return result;
  };
  protoOf(Modifiers).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Modifiers))
      return false;
    if (!(this.oh_1 === other.oh_1))
      return false;
    if (!(this.ph_1 === other.ph_1))
      return false;
    if (!(this.qh_1 === other.qh_1))
      return false;
    return true;
  };
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
  function ConsonantFeatures(place, manner, voiced) {
    this.zh_1 = place;
    this.ai_1 = manner;
    this.bi_1 = voiced;
  }
  protoOf(ConsonantFeatures).toString = function () {
    return 'ConsonantFeatures(place=' + this.zh_1 + ', manner=' + this.ai_1 + ', voiced=' + this.bi_1 + ')';
  };
  protoOf(ConsonantFeatures).hashCode = function () {
    var result = this.zh_1;
    result = imul(result, 31) + this.ai_1 | 0;
    result = imul(result, 31) + getBooleanHashCode(this.bi_1) | 0;
    return result;
  };
  protoOf(ConsonantFeatures).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof ConsonantFeatures))
      return false;
    if (!(this.zh_1 === other.zh_1))
      return false;
    if (!(this.ai_1 === other.ai_1))
      return false;
    if (!(this.bi_1 === other.bi_1))
      return false;
    return true;
  };
  function getHebrewConsonantIpa($this, letter, word, pos) {
    var tmp;
    if ((pos + 1 | 0) < word.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(word, pos + 1 | 0);
      tmp = Char__toInt_impl_vasixd(this_0) === 1468;
    } else {
      tmp = false;
    }
    var hasDagesh = tmp;
    var tmp_0;
    if (letter === _Char___init__impl__6a9atx(1488)) {
      tmp_0 = '';
    } else if (letter === _Char___init__impl__6a9atx(1489)) {
      tmp_0 = hasDagesh ? 'b' : 'v';
    } else if (letter === _Char___init__impl__6a9atx(1490)) {
      tmp_0 = 'g';
    } else if (letter === _Char___init__impl__6a9atx(1491)) {
      tmp_0 = 'd';
    } else if (letter === _Char___init__impl__6a9atx(1492)) {
      tmp_0 = 'h';
    } else if (letter === _Char___init__impl__6a9atx(1493)) {
      tmp_0 = 'v';
    } else if (letter === _Char___init__impl__6a9atx(1494)) {
      tmp_0 = 'z';
    } else if (letter === _Char___init__impl__6a9atx(1495)) {
      tmp_0 = 'x';
    } else if (letter === _Char___init__impl__6a9atx(1496)) {
      tmp_0 = 't';
    } else if (letter === _Char___init__impl__6a9atx(1497)) {
      tmp_0 = 'j';
    } else if (letter === _Char___init__impl__6a9atx(1498) || letter === _Char___init__impl__6a9atx(1499)) {
      tmp_0 = hasDagesh ? 'k' : 'x';
    } else if (letter === _Char___init__impl__6a9atx(1500)) {
      tmp_0 = 'l';
    } else if (letter === _Char___init__impl__6a9atx(1501) || letter === _Char___init__impl__6a9atx(1502)) {
      tmp_0 = 'm';
    } else if (letter === _Char___init__impl__6a9atx(1503) || letter === _Char___init__impl__6a9atx(1504)) {
      tmp_0 = 'n';
    } else if (letter === _Char___init__impl__6a9atx(1505)) {
      tmp_0 = 's';
    } else if (letter === _Char___init__impl__6a9atx(1506)) {
      tmp_0 = '';
    } else if (letter === _Char___init__impl__6a9atx(1507) || letter === _Char___init__impl__6a9atx(1508)) {
      tmp_0 = hasDagesh ? 'p' : 'f';
    } else if (letter === _Char___init__impl__6a9atx(1509) || letter === _Char___init__impl__6a9atx(1510)) {
      tmp_0 = 'ts';
    } else if (letter === _Char___init__impl__6a9atx(1511)) {
      tmp_0 = 'k';
    } else if (letter === _Char___init__impl__6a9atx(1512)) {
      tmp_0 = 'r';
    } else if (letter === _Char___init__impl__6a9atx(1513)) {
      var tmp_1;
      var tmp_2;
      if ((pos + 1 | 0) < word.length) {
        // Inline function 'kotlin.code' call
        var this_1 = charCodeAt(word, pos + 1 | 0);
        tmp_2 = Char__toInt_impl_vasixd(this_1) === 1474;
      } else {
        tmp_2 = false;
      }
      if (tmp_2) {
        tmp_1 = true;
      } else {
        var tmp_3;
        if ((pos + 2 | 0) < word.length) {
          // Inline function 'kotlin.code' call
          var this_2 = charCodeAt(word, pos + 2 | 0);
          tmp_3 = Char__toInt_impl_vasixd(this_2) === 1474;
        } else {
          tmp_3 = false;
        }
        tmp_1 = tmp_3;
      }
      var hasSinDot = tmp_1;
      tmp_0 = hasSinDot ? 's' : 'sh';
    } else if (letter === _Char___init__impl__6a9atx(1514)) {
      tmp_0 = 't';
    } else {
      tmp_0 = '';
    }
    return tmp_0;
  }
  function VowelResult(ipa, length, original) {
    this.ci_1 = ipa;
    this.di_1 = length;
    this.ei_1 = original;
  }
  protoOf(VowelResult).d9 = function () {
    return this.ci_1;
  };
  protoOf(VowelResult).e9 = function () {
    return this.di_1;
  };
  protoOf(VowelResult).na = function () {
    return this.ei_1;
  };
  protoOf(VowelResult).toString = function () {
    return 'VowelResult(ipa=' + this.ci_1 + ', length=' + this.di_1 + ', original=' + this.ei_1 + ')';
  };
  protoOf(VowelResult).hashCode = function () {
    var result = getStringHashCode(this.ci_1);
    result = imul(result, 31) + this.di_1 | 0;
    result = imul(result, 31) + getStringHashCode(this.ei_1) | 0;
    return result;
  };
  protoOf(VowelResult).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof VowelResult))
      return false;
    if (!(this.ci_1 === other.ci_1))
      return false;
    if (!(this.di_1 === other.di_1))
      return false;
    if (!(this.ei_1 === other.ei_1))
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
      switch (cp) {
        case 1456:
          tmp = '@';
          break;
        case 1457:
          tmp = 'e';
          break;
        case 1458:
          tmp = 'a';
          break;
        case 1459:
          tmp = 'o';
          break;
        case 1460:
          tmp = 'i';
          break;
        case 1461:
          tmp = 'e';
          break;
        case 1462:
          tmp = 'E';
          break;
        case 1463:
          tmp = 'a';
          break;
        case 1464:
          tmp = 'a';
          break;
        case 1465:
          tmp = 'o';
          break;
        case 1466:
          tmp = 'o';
          break;
        case 1467:
          tmp = 'u';
          break;
        case 1468:
          tmp = null;
          break;
        case 1473:
          tmp = null;
          break;
        case 1474:
          tmp = null;
          break;
        default:
          break $l$loop;
      }
      var vowelIpa = tmp;
      original.e6(charCodeAt(word, i));
      if (!(vowelIpa == null)) {
        result.d6(vowelIpa);
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
          tmp_0 = toString(tmp1_safe_receiver);
        }

        var tmp2_elvis_lhs = tmp_0;
        tmp = tmp2_elvis_lhs == null ? 'a' : tmp2_elvis_lhs;
        break;
    }
    return tmp;
  }
  function arabicConsonantIpa($this, c) {
    return c === _Char___init__impl__6a9atx(1575) ? '' : c === _Char___init__impl__6a9atx(1576) ? 'b' : c === _Char___init__impl__6a9atx(1578) ? 't' : c === _Char___init__impl__6a9atx(1579) ? '\u03B8' : c === _Char___init__impl__6a9atx(1580) ? 'd\u0292' : c === _Char___init__impl__6a9atx(1581) ? '\u0127' : c === _Char___init__impl__6a9atx(1582) ? 'x' : c === _Char___init__impl__6a9atx(1583) ? 'd' : c === _Char___init__impl__6a9atx(1584) ? '\xF0' : c === _Char___init__impl__6a9atx(1585) ? 'r' : c === _Char___init__impl__6a9atx(1586) ? 'z' : c === _Char___init__impl__6a9atx(1587) ? 's' : c === _Char___init__impl__6a9atx(1588) ? '\u0283' : c === _Char___init__impl__6a9atx(1589) ? 's\u02E4' : c === _Char___init__impl__6a9atx(1590) ? 'd\u02E4' : c === _Char___init__impl__6a9atx(1591) ? 't\u02E4' : c === _Char___init__impl__6a9atx(1592) ? '\xF0\u02E4' : c === _Char___init__impl__6a9atx(1593) ? '\u0295' : c === _Char___init__impl__6a9atx(1594) ? '\u0263' : c === _Char___init__impl__6a9atx(1601) ? 'f' : c === _Char___init__impl__6a9atx(1602) ? 'q' : c === _Char___init__impl__6a9atx(1603) ? 'k' : c === _Char___init__impl__6a9atx(1604) ? 'l' : c === _Char___init__impl__6a9atx(1605) ? 'm' : c === _Char___init__impl__6a9atx(1606) ? 'n' : c === _Char___init__impl__6a9atx(1607) ? 'h' : c === _Char___init__impl__6a9atx(1608) ? 'w' : c === _Char___init__impl__6a9atx(1610) ? 'j' : c === _Char___init__impl__6a9atx(1569) ? '\u0294' : '';
  }
  function collectArabicVowel($this, word, start) {
    if (start >= word.length)
      return to('a', 0);
    // Inline function 'kotlin.code' call
    var this_0 = charCodeAt(word, start);
    var cp = Char__toInt_impl_vasixd(this_0);
    switch (cp) {
      case 1614:
        return to('a', 1);
      case 1615:
        return to('u', 1);
      case 1616:
        return to('i', 1);
      case 1617:
        return to('a', 1);
      case 1618:
        return to('', 1);
      default:
        return to('a', 0);
    }
  }
  function parseGreekChar($this, word, pos) {
    var c = charCodeAt(word, pos);
    return c === _Char___init__impl__6a9atx(945) || c === _Char___init__impl__6a9atx(940) ? new Triple('', 'a', 1) : c === _Char___init__impl__6a9atx(949) || c === _Char___init__impl__6a9atx(941) ? new Triple('', 'e', 1) : c === _Char___init__impl__6a9atx(951) || c === _Char___init__impl__6a9atx(942) ? new Triple('', 'i', 1) : c === _Char___init__impl__6a9atx(953) || c === _Char___init__impl__6a9atx(943) || (c === _Char___init__impl__6a9atx(970) || c === _Char___init__impl__6a9atx(912)) ? new Triple('', 'i', 1) : c === _Char___init__impl__6a9atx(959) || c === _Char___init__impl__6a9atx(972) ? new Triple('', 'o', 1) : c === _Char___init__impl__6a9atx(965) || c === _Char___init__impl__6a9atx(973) || (c === _Char___init__impl__6a9atx(971) || c === _Char___init__impl__6a9atx(944)) ? new Triple('', 'i', 1) : c === _Char___init__impl__6a9atx(969) || c === _Char___init__impl__6a9atx(974) ? new Triple('', 'o', 1) : c === _Char___init__impl__6a9atx(946) ? new Triple('v', '', 1) : c === _Char___init__impl__6a9atx(947) ? new Triple('\u0263', '', 1) : c === _Char___init__impl__6a9atx(948) ? new Triple('\xF0', '', 1) : c === _Char___init__impl__6a9atx(950) ? new Triple('z', '', 1) : c === _Char___init__impl__6a9atx(952) ? new Triple('\u03B8', '', 1) : c === _Char___init__impl__6a9atx(954) ? new Triple('k', '', 1) : c === _Char___init__impl__6a9atx(955) ? new Triple('l', '', 1) : c === _Char___init__impl__6a9atx(956) ? new Triple('m', '', 1) : c === _Char___init__impl__6a9atx(957) ? new Triple('n', '', 1) : c === _Char___init__impl__6a9atx(958) ? new Triple('ks', '', 1) : c === _Char___init__impl__6a9atx(960) ? new Triple('p', '', 1) : c === _Char___init__impl__6a9atx(961) ? new Triple('r', '', 1) : c === _Char___init__impl__6a9atx(963) || c === _Char___init__impl__6a9atx(962) ? new Triple('s', '', 1) : c === _Char___init__impl__6a9atx(964) ? new Triple('t', '', 1) : c === _Char___init__impl__6a9atx(966) ? new Triple('f', '', 1) : c === _Char___init__impl__6a9atx(967) ? new Triple('x', '', 1) : c === _Char___init__impl__6a9atx(968) ? new Triple('ps', '', 1) : new Triple('', '', 1);
  }
  function devanagariConsonantIpa($this, c) {
    return c === _Char___init__impl__6a9atx(2325) ? 'k' : c === _Char___init__impl__6a9atx(2326) ? 'k\u02B0' : c === _Char___init__impl__6a9atx(2327) ? '\u0261' : c === _Char___init__impl__6a9atx(2328) ? '\u0261\u02B1' : c === _Char___init__impl__6a9atx(2329) ? '\u014B' : c === _Char___init__impl__6a9atx(2330) ? 't\u0283' : c === _Char___init__impl__6a9atx(2331) ? 't\u0283\u02B0' : c === _Char___init__impl__6a9atx(2332) ? 'd\u0292' : c === _Char___init__impl__6a9atx(2333) ? 'd\u0292\u02B1' : c === _Char___init__impl__6a9atx(2334) ? '\u0272' : c === _Char___init__impl__6a9atx(2335) ? '\u0288' : c === _Char___init__impl__6a9atx(2336) ? '\u0288\u02B0' : c === _Char___init__impl__6a9atx(2337) ? '\u0256' : c === _Char___init__impl__6a9atx(2338) ? '\u0256\u02B1' : c === _Char___init__impl__6a9atx(2339) ? '\u0273' : c === _Char___init__impl__6a9atx(2340) ? 't' : c === _Char___init__impl__6a9atx(2341) ? 't\u02B0' : c === _Char___init__impl__6a9atx(2342) ? 'd' : c === _Char___init__impl__6a9atx(2343) ? 'd\u02B1' : c === _Char___init__impl__6a9atx(2344) ? 'n' : c === _Char___init__impl__6a9atx(2346) ? 'p' : c === _Char___init__impl__6a9atx(2347) ? 'p\u02B0' : c === _Char___init__impl__6a9atx(2348) ? 'b' : c === _Char___init__impl__6a9atx(2349) ? 'b\u02B1' : c === _Char___init__impl__6a9atx(2350) ? 'm' : c === _Char___init__impl__6a9atx(2351) ? 'j' : c === _Char___init__impl__6a9atx(2352) ? 'r' : c === _Char___init__impl__6a9atx(2354) ? 'l' : c === _Char___init__impl__6a9atx(2357) ? '\u028B' : c === _Char___init__impl__6a9atx(2358) ? '\u0283' : c === _Char___init__impl__6a9atx(2359) ? '\u0282' : c === _Char___init__impl__6a9atx(2360) ? 's' : c === _Char___init__impl__6a9atx(2361) ? '\u0266' : '';
  }
  function devanagariVowelIpa($this, c) {
    return c === _Char___init__impl__6a9atx(2309) ? '\u0259' : c === _Char___init__impl__6a9atx(2310) || c === _Char___init__impl__6a9atx(2366) ? 'a\u02D0' : c === _Char___init__impl__6a9atx(2311) || c === _Char___init__impl__6a9atx(2367) ? '\u026A' : c === _Char___init__impl__6a9atx(2312) || c === _Char___init__impl__6a9atx(2368) ? 'i\u02D0' : c === _Char___init__impl__6a9atx(2313) || c === _Char___init__impl__6a9atx(2369) ? '\u028A' : c === _Char___init__impl__6a9atx(2314) || c === _Char___init__impl__6a9atx(2370) ? 'u\u02D0' : c === _Char___init__impl__6a9atx(2319) || c === _Char___init__impl__6a9atx(2375) ? 'e\u02D0' : c === _Char___init__impl__6a9atx(2320) || c === _Char___init__impl__6a9atx(2376) ? '\u025B\u02D0' : c === _Char___init__impl__6a9atx(2323) || c === _Char___init__impl__6a9atx(2379) ? 'o\u02D0' : c === _Char___init__impl__6a9atx(2324) || c === _Char___init__impl__6a9atx(2380) ? '\u0254\u02D0' : '\u0259';
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
    return c === _Char___init__impl__6a9atx(1072) ? to('', 'a') : c === _Char___init__impl__6a9atx(1073) ? to('b', '') : c === _Char___init__impl__6a9atx(1074) ? to('v', '') : c === _Char___init__impl__6a9atx(1075) ? to('\u0261', '') : c === _Char___init__impl__6a9atx(1076) ? to('d', '') : c === _Char___init__impl__6a9atx(1077) ? to('j', 'e') : c === _Char___init__impl__6a9atx(1105) ? to('j', 'o') : c === _Char___init__impl__6a9atx(1078) ? to('\u0292', '') : c === _Char___init__impl__6a9atx(1079) ? to('z', '') : c === _Char___init__impl__6a9atx(1080) ? to('', 'i') : c === _Char___init__impl__6a9atx(1081) ? to('j', '') : c === _Char___init__impl__6a9atx(1082) ? to('k', '') : c === _Char___init__impl__6a9atx(1083) ? to('l', '') : c === _Char___init__impl__6a9atx(1084) ? to('m', '') : c === _Char___init__impl__6a9atx(1085) ? to('n', '') : c === _Char___init__impl__6a9atx(1086) ? to('', 'o') : c === _Char___init__impl__6a9atx(1087) ? to('p', '') : c === _Char___init__impl__6a9atx(1088) ? to('r', '') : c === _Char___init__impl__6a9atx(1089) ? to('s', '') : c === _Char___init__impl__6a9atx(1090) ? to('t', '') : c === _Char___init__impl__6a9atx(1091) ? to('', 'u') : c === _Char___init__impl__6a9atx(1092) ? to('f', '') : c === _Char___init__impl__6a9atx(1093) ? to('x', '') : c === _Char___init__impl__6a9atx(1094) ? to('ts', '') : c === _Char___init__impl__6a9atx(1095) ? to('t\u0283', '') : c === _Char___init__impl__6a9atx(1096) ? to('\u0283', '') : c === _Char___init__impl__6a9atx(1097) ? to('\u0283t\u0283', '') : c === _Char___init__impl__6a9atx(1098) ? to('', '') : c === _Char___init__impl__6a9atx(1099) ? to('', '\u0268') : c === _Char___init__impl__6a9atx(1100) ? to('', '') : c === _Char___init__impl__6a9atx(1101) ? to('', 'e') : c === _Char___init__impl__6a9atx(1102) ? to('j', 'u') : c === _Char___init__impl__6a9atx(1103) ? to('j', 'a') : to('', '');
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
    switch (i) {
      case 0:
        return '\u0261';
      case 1:
        return 'k';
      case 2:
        return 'n';
      case 3:
        return 'd';
      case 4:
        return 't';
      case 5:
        return 'r';
      case 6:
        return 'm';
      case 7:
        return 'b';
      case 8:
        return 'p';
      case 9:
        return 's';
      case 10:
        return 's';
      case 11:
        return '';
      case 12:
        return 'd\u0292';
      case 13:
        return 't\u0283';
      case 14:
        return 't\u0283\u02B0';
      case 15:
        return 'k\u02B0';
      case 16:
        return 't\u02B0';
      case 17:
        return 'p\u02B0';
      case 18:
        return 'h';
      default:
        return '';
    }
  }
  function hangulMedialIpa($this, m) {
    switch (m) {
      case 0:
        return 'a';
      case 1:
        return '\u025B';
      case 2:
        return 'ja';
      case 3:
        return 'j\u025B';
      case 4:
        return '\u028C';
      case 5:
        return 'e';
      case 6:
        return 'j\u028C';
      case 7:
        return 'je';
      case 8:
        return 'o';
      case 9:
        return 'wa';
      case 10:
        return 'w\u025B';
      case 11:
        return 'we';
      case 12:
        return 'jo';
      case 13:
        return 'u';
      case 14:
        return 'w\u028C';
      case 15:
        return 'we';
      case 16:
        return 'wi';
      case 17:
        return 'ju';
      case 18:
        return '\u0268';
      case 19:
        return '\u0270i';
      case 20:
        return 'i';
      default:
        return '';
    }
  }
  function hangulFinalIpa($this, f) {
    switch (f) {
      case 1:
        return 'k';
      case 2:
        return 'k';
      case 3:
        return 'k';
      case 4:
        return 'n';
      case 5:
        return 'n';
      case 6:
        return 'n';
      case 7:
        return 't';
      case 8:
        return 'l';
      case 9:
        return 'k';
      case 10:
        return 'm';
      case 11:
        return 'p';
      case 12:
        return 'l';
      case 13:
        return 'l';
      case 14:
        return 'l';
      case 15:
        return 'p';
      case 16:
        return 'm';
      case 17:
        return 'p';
      case 18:
        return 'p';
      case 19:
        return 't';
      case 20:
        return 't';
      case 21:
        return '\u014B';
      case 22:
        return 't';
      case 23:
        return 't';
      case 24:
        return 'k';
      case 25:
        return 't';
      case 26:
        return 'p';
      case 27:
        return 't';
      default:
        return '';
    }
  }
  function hiraganaIpa($this, c) {
    return c === _Char___init__impl__6a9atx(12354) ? to('', 'a') : c === _Char___init__impl__6a9atx(12356) ? to('', 'i') : c === _Char___init__impl__6a9atx(12358) ? to('', '\u026F') : c === _Char___init__impl__6a9atx(12360) ? to('', 'e') : c === _Char___init__impl__6a9atx(12362) ? to('', 'o') : c === _Char___init__impl__6a9atx(12363) ? to('k', 'a') : c === _Char___init__impl__6a9atx(12365) ? to('k', 'i') : c === _Char___init__impl__6a9atx(12367) ? to('k', '\u026F') : c === _Char___init__impl__6a9atx(12369) ? to('k', 'e') : c === _Char___init__impl__6a9atx(12371) ? to('k', 'o') : c === _Char___init__impl__6a9atx(12373) ? to('s', 'a') : c === _Char___init__impl__6a9atx(12375) ? to('\u0255', 'i') : c === _Char___init__impl__6a9atx(12377) ? to('s', '\u026F') : c === _Char___init__impl__6a9atx(12379) ? to('s', 'e') : c === _Char___init__impl__6a9atx(12381) ? to('s', 'o') : c === _Char___init__impl__6a9atx(12383) ? to('t', 'a') : c === _Char___init__impl__6a9atx(12385) ? to('t\u0255', 'i') : c === _Char___init__impl__6a9atx(12388) ? to('ts', '\u026F') : c === _Char___init__impl__6a9atx(12390) ? to('t', 'e') : c === _Char___init__impl__6a9atx(12392) ? to('t', 'o') : c === _Char___init__impl__6a9atx(12394) ? to('n', 'a') : c === _Char___init__impl__6a9atx(12395) ? to('\u0272', 'i') : c === _Char___init__impl__6a9atx(12396) ? to('n', '\u026F') : c === _Char___init__impl__6a9atx(12397) ? to('n', 'e') : c === _Char___init__impl__6a9atx(12398) ? to('n', 'o') : c === _Char___init__impl__6a9atx(12399) ? to('h', 'a') : c === _Char___init__impl__6a9atx(12402) ? to('\xE7', 'i') : c === _Char___init__impl__6a9atx(12405) ? to('\u0278', '\u026F') : c === _Char___init__impl__6a9atx(12408) ? to('h', 'e') : c === _Char___init__impl__6a9atx(12411) ? to('h', 'o') : c === _Char___init__impl__6a9atx(12414) ? to('m', 'a') : c === _Char___init__impl__6a9atx(12415) ? to('m', 'i') : c === _Char___init__impl__6a9atx(12416) ? to('m', '\u026F') : c === _Char___init__impl__6a9atx(12417) ? to('m', 'e') : c === _Char___init__impl__6a9atx(12418) ? to('m', 'o') : c === _Char___init__impl__6a9atx(12420) ? to('j', 'a') : c === _Char___init__impl__6a9atx(12422) ? to('j', '\u026F') : c === _Char___init__impl__6a9atx(12424) ? to('j', 'o') : c === _Char___init__impl__6a9atx(12425) ? to('\u027E', 'a') : c === _Char___init__impl__6a9atx(12426) ? to('\u027E', 'i') : c === _Char___init__impl__6a9atx(12427) ? to('\u027E', '\u026F') : c === _Char___init__impl__6a9atx(12428) ? to('\u027E', 'e') : c === _Char___init__impl__6a9atx(12429) ? to('\u027E', 'o') : c === _Char___init__impl__6a9atx(12431) ? to('w', 'a') : c === _Char___init__impl__6a9atx(12434) ? to('', 'o') : c === _Char___init__impl__6a9atx(12435) ? to('n', '') : to('', '');
  }
  function katakanaIpa($this, c) {
    var hiragana = c === _Char___init__impl__6a9atx(12450) ? _Char___init__impl__6a9atx(12354) : c === _Char___init__impl__6a9atx(12452) ? _Char___init__impl__6a9atx(12356) : c === _Char___init__impl__6a9atx(12454) ? _Char___init__impl__6a9atx(12358) : c === _Char___init__impl__6a9atx(12456) ? _Char___init__impl__6a9atx(12360) : c === _Char___init__impl__6a9atx(12458) ? _Char___init__impl__6a9atx(12362) : c === _Char___init__impl__6a9atx(12459) ? _Char___init__impl__6a9atx(12363) : c === _Char___init__impl__6a9atx(12461) ? _Char___init__impl__6a9atx(12365) : c === _Char___init__impl__6a9atx(12463) ? _Char___init__impl__6a9atx(12367) : c === _Char___init__impl__6a9atx(12465) ? _Char___init__impl__6a9atx(12369) : c === _Char___init__impl__6a9atx(12467) ? _Char___init__impl__6a9atx(12371) : c === _Char___init__impl__6a9atx(12469) ? _Char___init__impl__6a9atx(12373) : c === _Char___init__impl__6a9atx(12471) ? _Char___init__impl__6a9atx(12375) : c === _Char___init__impl__6a9atx(12473) ? _Char___init__impl__6a9atx(12377) : c === _Char___init__impl__6a9atx(12475) ? _Char___init__impl__6a9atx(12379) : c === _Char___init__impl__6a9atx(12477) ? _Char___init__impl__6a9atx(12381) : c === _Char___init__impl__6a9atx(12479) ? _Char___init__impl__6a9atx(12383) : c === _Char___init__impl__6a9atx(12481) ? _Char___init__impl__6a9atx(12385) : c === _Char___init__impl__6a9atx(12484) ? _Char___init__impl__6a9atx(12388) : c === _Char___init__impl__6a9atx(12486) ? _Char___init__impl__6a9atx(12390) : c === _Char___init__impl__6a9atx(12488) ? _Char___init__impl__6a9atx(12392) : c === _Char___init__impl__6a9atx(12490) ? _Char___init__impl__6a9atx(12394) : c === _Char___init__impl__6a9atx(12491) ? _Char___init__impl__6a9atx(12395) : c === _Char___init__impl__6a9atx(12492) ? _Char___init__impl__6a9atx(12396) : c === _Char___init__impl__6a9atx(12493) ? _Char___init__impl__6a9atx(12397) : c === _Char___init__impl__6a9atx(12494) ? _Char___init__impl__6a9atx(12398) : c === _Char___init__impl__6a9atx(12495) ? _Char___init__impl__6a9atx(12399) : c === _Char___init__impl__6a9atx(12498) ? _Char___init__impl__6a9atx(12402) : c === _Char___init__impl__6a9atx(12501) ? _Char___init__impl__6a9atx(12405) : c === _Char___init__impl__6a9atx(12504) ? _Char___init__impl__6a9atx(12408) : c === _Char___init__impl__6a9atx(12507) ? _Char___init__impl__6a9atx(12411) : c === _Char___init__impl__6a9atx(12510) ? _Char___init__impl__6a9atx(12414) : c === _Char___init__impl__6a9atx(12511) ? _Char___init__impl__6a9atx(12415) : c === _Char___init__impl__6a9atx(12512) ? _Char___init__impl__6a9atx(12416) : c === _Char___init__impl__6a9atx(12513) ? _Char___init__impl__6a9atx(12417) : c === _Char___init__impl__6a9atx(12514) ? _Char___init__impl__6a9atx(12418) : c === _Char___init__impl__6a9atx(12516) ? _Char___init__impl__6a9atx(12420) : c === _Char___init__impl__6a9atx(12518) ? _Char___init__impl__6a9atx(12422) : c === _Char___init__impl__6a9atx(12520) ? _Char___init__impl__6a9atx(12424) : c === _Char___init__impl__6a9atx(12521) ? _Char___init__impl__6a9atx(12425) : c === _Char___init__impl__6a9atx(12522) ? _Char___init__impl__6a9atx(12426) : c === _Char___init__impl__6a9atx(12523) ? _Char___init__impl__6a9atx(12427) : c === _Char___init__impl__6a9atx(12524) ? _Char___init__impl__6a9atx(12428) : c === _Char___init__impl__6a9atx(12525) ? _Char___init__impl__6a9atx(12429) : c === _Char___init__impl__6a9atx(12527) ? _Char___init__impl__6a9atx(12431) : c === _Char___init__impl__6a9atx(12530) ? _Char___init__impl__6a9atx(12434) : c === _Char___init__impl__6a9atx(12531) ? _Char___init__impl__6a9atx(12435) : c;
    return hiraganaIpa($this, hiragana);
  }
  function Script(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function consonantDistance($this, c1, c2) {
    var f1 = $this.gi_1.o1(c1);
    var f2 = $this.gi_1.o1(c2);
    if (f1 == null || f2 == null)
      return 10;
    var dist = 0;
    if (!(f1.zh_1 === f2.zh_1))
      dist = dist + imul(abs(f1.zh_1 - f2.zh_1 | 0), 2) | 0;
    if (!(f1.ai_1 === f2.ai_1))
      dist = dist + abs(f1.ai_1 - f2.ai_1 | 0) | 0;
    if (!(f1.bi_1 === f2.bi_1))
      dist = dist + 1 | 0;
    return dist;
  }
  function vowelDistance($this, v1, v2) {
    var tmp0_elvis_lhs = $this.fi_1.o1(v1);
    var h1 = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var tmp1_elvis_lhs = $this.fi_1.o1(v2);
    var h2 = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
    var tmp0 = abs(h1 - h2 | 0);
    // Inline function 'kotlin.math.min' call
    var b = 360 - abs(h1 - h2 | 0) | 0;
    return Math.min(tmp0, b) / 10 | 0;
  }
  function UniKeySyllable$Companion$rhymeKey$lambda(it) {
    return it.ji_1 + it.ki_1;
  }
  function UniKeySyllable$Companion$rhymeKey$lambda_0(it) {
    return it.ji_1 + it.ki_1;
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
  function computeHue_1($this) {
    var tmp0_elvis_lhs = Companion_getInstance_5().fi_1.o1($this.ki_1);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      var tmp0 = Companion_getInstance_5().fi_1;
      var tmp1_safe_receiver = firstOrNull($this.ki_1);
      var tmp_0;
      var tmp_1 = tmp1_safe_receiver;
      if ((tmp_1 == null ? null : new Char(tmp_1)) == null) {
        tmp_0 = null;
      } else {
        tmp_0 = toString(tmp1_safe_receiver);
      }
      // Inline function 'kotlin.collections.get' call
      var key = tmp_0;
      tmp = (isInterface(tmp0, KtMap) ? tmp0 : THROW_CCE()).o1(key);
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
    var this_0 = $this.ji_1;
    if (charSequenceLength(this_0) === 0)
      return 0;
    var tmp0_elvis_lhs = Companion_getInstance_5().gi_1.o1($this.ji_1);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return getStringHashCode($this.ji_1) % 30 | 0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var features = tmp;
    var placeOffset = imul(features.zh_1, 5);
    var mannerOffset = imul(features.ai_1, 2);
    var voiceOffset = features.bi_1 ? 0 : 1;
    return ((placeOffset + mannerOffset | 0) + voiceOffset | 0) % 50 | 0;
  }
  function Companion_5() {
    Companion_instance_5 = this;
    this.fi_1 = mapOf([to('a', 0), to('A', 5), to('\u0251', 10), to('\xE6', 15), to('a\u02D0', 5), to('\u025B\u02D0', 35), to('\u025B', 40), to('E', 45), to('e', 50), to('e\u02D0', 55), to('i', 120), to('\u026A', 125), to('I', 120), to('i\u02D0', 115), to('y', 130), to('o', 180), to('\u0254', 185), to('O', 180), to('o\u02D0', 175), to('\u0254\u02D0', 190), to('\xF8', 160), to('u', 240), to('\u028A', 245), to('U', 240), to('u\u02D0', 235), to('\u026F', 250), to('\xFC', 255), to('\u0259', 300), to('@', 300), to('\u028C', 310), to('\u0268', 290), to('ja', 10), to('j\u025B', 45), to('j\u028C', 315), to('je', 55), to('jo', 185), to('ju', 245), to('wa', 5), to('w\u025B', 40), to('w\u028C', 315), to('we', 50), to('wi', 125), to('\u0270i', 120), to('ai', 120), to('ei', 50), to('ao', 180), to('ou', 240), to('an', 0), to('en', 50), to('ang', 5), to('eng', 55), to('ong', 185)]);
    this.gi_1 = mapOf([to('p', new ConsonantFeatures(0, 0, false)), to('b', new ConsonantFeatures(0, 0, true)), to('p\u02B0', new ConsonantFeatures(0, 0, false)), to('b\u02B1', new ConsonantFeatures(0, 0, true)), to('m', new ConsonantFeatures(0, 2, true)), to('f', new ConsonantFeatures(0, 1, false)), to('v', new ConsonantFeatures(0, 1, true)), to('\u0278', new ConsonantFeatures(0, 1, false)), to('\u028B', new ConsonantFeatures(0, 3, true)), to('w', new ConsonantFeatures(0, 3, true)), to('\u03B8', new ConsonantFeatures(1, 1, false)), to('\xF0', new ConsonantFeatures(1, 1, true)), to('t', new ConsonantFeatures(2, 0, false)), to('d', new ConsonantFeatures(2, 0, true)), to('t\u02B0', new ConsonantFeatures(2, 0, false)), to('d\u02B1', new ConsonantFeatures(2, 0, true)), to('n', new ConsonantFeatures(2, 2, true)), to('s', new ConsonantFeatures(2, 1, false)), to('z', new ConsonantFeatures(2, 1, true)), to('l', new ConsonantFeatures(2, 3, true)), to('r', new ConsonantFeatures(2, 4, true)), to('\u027E', new ConsonantFeatures(2, 4, true)), to('ts', new ConsonantFeatures(2, 5, false)), to('\u0288', new ConsonantFeatures(2, 0, false)), to('\u0288\u02B0', new ConsonantFeatures(2, 0, false)), to('\u0256', new ConsonantFeatures(2, 0, true)), to('\u0256\u02B1', new ConsonantFeatures(2, 0, true)), to('\u0273', new ConsonantFeatures(2, 2, true)), to('\u0282', new ConsonantFeatures(2, 1, false)), to('\u0283', new ConsonantFeatures(3, 1, false)), to('sh', new ConsonantFeatures(3, 1, false)), to('\u0292', new ConsonantFeatures(3, 1, true)), to('\u0255', new ConsonantFeatures(3, 1, false)), to('j', new ConsonantFeatures(3, 3, true)), to('y', new ConsonantFeatures(3, 3, true)), to('\u0272', new ConsonantFeatures(3, 2, true)), to('\xE7', new ConsonantFeatures(3, 1, false)), to('t\u0283', new ConsonantFeatures(3, 5, false)), to('t\u0283\u02B0', new ConsonantFeatures(3, 5, false)), to('ch', new ConsonantFeatures(3, 5, false)), to('d\u0292', new ConsonantFeatures(3, 5, true)), to('d\u0292\u02B1', new ConsonantFeatures(3, 5, true)), to('t\u0255', new ConsonantFeatures(3, 5, false)), to('k', new ConsonantFeatures(4, 0, false)), to('k\u02B0', new ConsonantFeatures(4, 0, false)), to('g', new ConsonantFeatures(4, 0, true)), to('\u0261', new ConsonantFeatures(4, 0, true)), to('\u0261\u02B1', new ConsonantFeatures(4, 0, true)), to('x', new ConsonantFeatures(4, 1, false)), to('\u0263', new ConsonantFeatures(4, 1, true)), to('\u014B', new ConsonantFeatures(4, 2, true)), to('q', new ConsonantFeatures(4, 0, false)), to('\u0127', new ConsonantFeatures(5, 1, false)), to('\u0295', new ConsonantFeatures(5, 1, true)), to('h', new ConsonantFeatures(5, 1, false)), to('\u0266', new ConsonantFeatures(5, 1, true)), to('\u0294', new ConsonantFeatures(5, 0, false)), to('', new ConsonantFeatures(5, 0, false)), to('s\u02E4', new ConsonantFeatures(2, 1, false)), to('d\u02E4', new ConsonantFeatures(2, 0, true)), to('t\u02E4', new ConsonantFeatures(2, 0, false)), to('\xF0\u02E4', new ConsonantFeatures(1, 1, true)), to('ks', new ConsonantFeatures(4, 5, false)), to('ps', new ConsonantFeatures(0, 5, false)), to('\u0283t\u0283', new ConsonantFeatures(3, 5, false)), to('zh', new ConsonantFeatures(3, 5, false))]);
    this.hi_1 = listOf(['', 'b', 'p', 'm', 'f', 'd', 't', 'n', 'l', 'g', 'k', 'h', 'j', 'q', 'x', 'zh', 'ch', 'sh', 'r', 'z', 'c', 's']);
    this.ii_1 = listOf(['a', 'o', 'e', 'i', 'u', '\xFC', 'ai', 'ei', 'ao', 'ou', 'an', 'en', 'ang', 'eng', 'ong']);
  }
  protoOf(Companion_5).ni = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[,.\\-;:!?\\s]+$').k7(word, '');
    var i = 0;
    while (i < w.length) {
      var c = charCodeAt(w, i);
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (1488 <= cp ? cp <= 1514 : false) {
        var consonantIpa = getHebrewConsonantIpa(this, c, w, i);
        var _destruct__k2r9zo = collectVowels(this, w, i + 1 | 0);
        var vowelIpa = _destruct__k2r9zo.d9();
        var vowelLen = _destruct__k2r9zo.e9();
        var originalVowels = _destruct__k2r9zo.na();
        syllables.g(new UniKeySyllable(consonantIpa, vowelIpa, toString(c) + originalVowels));
        i = i + (1 + vowelLen | 0) | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_5).oi = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0 = word.toLowerCase();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[^a-z]').k7(tmp0, '');
    var i = 0;
    while (i < w.length) {
      var consonantStart = i;
      while (i < w.length && !contains_0('aeiou', charCodeAt(w, i))) {
        i = i + 1 | 0;
      }
      var consonants = substring(w, consonantStart, i);
      var vowelStart = i;
      while (i < w.length && contains_0('aeiou', charCodeAt(w, i))) {
        i = i + 1 | 0;
      }
      var vowels = substring(w, vowelStart, i);
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
  protoOf(Companion_5).pi = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[\\s,.!?]+').k7(word, '');
    var i = 0;
    while (i < w.length) {
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt(w, i);
      var cp = Char__toInt_impl_vasixd(this_0);
      if (1536 <= cp ? cp <= 1791 : false) {
        var consonant = arabicConsonantIpa(this, charCodeAt(w, i));
        var _destruct__k2r9zo = collectArabicVowel(this, w, i + 1 | 0);
        var vowel = _destruct__k2r9zo.d9();
        var len = _destruct__k2r9zo.e9();
        var tmp = i;
        var tmp0 = (i + 1 | 0) + len | 0;
        // Inline function 'kotlin.comparisons.minOf' call
        var b = w.length;
        var tmp$ret$3 = Math.min(tmp0, b);
        syllables.g(new UniKeySyllable(consonant, vowel, substring(w, tmp, tmp$ret$3)));
        i = i + (1 + len | 0) | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_5).qi = function (word) {
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
        var cons = _destruct__k2r9zo.d9();
        var vowel = _destruct__k2r9zo.e9();
        var len = _destruct__k2r9zo.na();
        syllables.g(new UniKeySyllable(cons, vowel, substring(w, i, i + len | 0)));
        i = i + len | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_5).ri = function (word) {
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
        var vowel = _destruct__k2r9zo.d9();
        var len = _destruct__k2r9zo.e9();
        var tmp = i;
        var tmp0 = (i + 1 | 0) + len | 0;
        // Inline function 'kotlin.comparisons.minOf' call
        var b = word.length;
        var tmp$ret$2 = Math.min(tmp0, b);
        syllables.g(new UniKeySyllable(cons, vowel, substring(word, tmp, tmp$ret$2)));
        i = i + (1 + len | 0) | 0;
      } else if (2309 <= cp ? cp <= 2324 : false) {
        var vowel_0 = devanagariVowelIpa(this, charCodeAt(word, i));
        syllables.g(new UniKeySyllable('', vowel_0, toString(charCodeAt(word, i))));
        i = i + 1 | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_5).si = function (word) {
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
        var cons = _destruct__k2r9zo.d9();
        var vowel = _destruct__k2r9zo.e9();
        syllables.g(new UniKeySyllable(cons, vowel, toString(charCodeAt(w, i))));
        i = i + 1 | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_5).ti = function (word) {
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
        var cons = _destruct__k2r9zo.d9();
        var vowel = _destruct__k2r9zo.e9();
        var final = _destruct__k2r9zo.na();
        syllables.g(new UniKeySyllable(cons, vowel, toString(c)));
        // Inline function 'kotlin.text.isNotEmpty' call
        if (charSequenceLength(final) > 0) {
          syllables.g(new UniKeySyllable(final, '', ''));
        }
      }
    }
    return syllables;
  };
  protoOf(Companion_5).ui = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    var i = 0;
    while (i < word.length) {
      var c = charCodeAt(word, i);
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (12352 <= cp ? cp <= 12447 : false) {
        var _destruct__k2r9zo = hiraganaIpa(this, c);
        var cons = _destruct__k2r9zo.d9();
        var vowel = _destruct__k2r9zo.e9();
        syllables.g(new UniKeySyllable(cons, vowel, toString(c)));
      } else if (12448 <= cp ? cp <= 12543 : false) {
        var _destruct__k2r9zo_0 = katakanaIpa(this, c);
        var cons_0 = _destruct__k2r9zo_0.d9();
        var vowel_0 = _destruct__k2r9zo_0.e9();
        syllables.g(new UniKeySyllable(cons_0, vowel_0, toString(c)));
      }
      i = i + 1 | 0;
    }
    return syllables;
  };
  protoOf(Companion_5).vi = function (word) {
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
        var tmp0 = this.hi_1;
        // Inline function 'kotlin.collections.getOrElse' call
        var index = hash % this.hi_1.l() | 0;
        var tmp;
        if (0 <= index ? index < tmp0.l() : false) {
          tmp = tmp0.m(index);
        } else {
          tmp = '';
        }
        var consonant = tmp;
        var tmp0_0 = this.ii_1;
        // Inline function 'kotlin.collections.getOrElse' call
        var index_0 = hash % this.ii_1.l() | 0;
        var tmp_0;
        if (0 <= index_0 ? index_0 < tmp0_0.l() : false) {
          tmp_0 = tmp0_0.m(index_0);
        } else {
          tmp_0 = 'a';
        }
        var vowel = tmp_0;
        syllables.g(new UniKeySyllable(consonant, vowel, toString(c)));
      }
    }
    return syllables;
  };
  protoOf(Companion_5).wi = function (text) {
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
  protoOf(Companion_5).xi = function (word) {
    var tmp;
    switch (this.wi(word).t1_1) {
      case 0:
        tmp = this.ni(word);
        break;
      case 1:
        tmp = this.oi(word);
        break;
      case 2:
        tmp = this.pi(word);
        break;
      case 3:
        tmp = this.qi(word);
        break;
      case 4:
        tmp = this.ri(word);
        break;
      case 5:
        tmp = this.si(word);
        break;
      case 6:
        tmp = this.ti(word);
        break;
      case 7:
        tmp = this.ui(word);
        break;
      case 8:
        tmp = this.vi(word);
        break;
      case 9:
        tmp = this.oi(word);
        break;
      default:
        noWhenBranchMatchedException();
        break;
    }
    return tmp;
  };
  protoOf(Companion_5).yi = function (word) {
    var syllables = this.xi(word);
    var tmp0_safe_receiver = lastOrNull(syllables);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.vd();
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  };
  protoOf(Companion_5).zi = function (word, isHebrew) {
    var syllables = isHebrew ? this.ni(word) : this.oi(word);
    var tmp0_safe_receiver = lastOrNull(syllables);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.vd();
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  };
  protoOf(Companion_5).zc = function (hue, saturation, lightness) {
    return 'hsl(' + hue + ', ' + saturation + '%, ' + lightness + '%)';
  };
  protoOf(Companion_5).aj = function (word) {
    return this.zc(this.yi(word), 80, 72);
  };
  protoOf(Companion_5).bj = function (word, isHebrew) {
    return this.zc(this.zi(word, isHebrew), 80, 72);
  };
  protoOf(Companion_5).cj = function (word, syllableCount) {
    var syllables = this.xi(word);
    var tmp = takeLast_0(syllables, syllableCount);
    return joinToString(tmp, '-', VOID, VOID, VOID, VOID, UniKeySyllable$Companion$rhymeKey$lambda);
  };
  protoOf(Companion_5).dj = function (word, isHebrew, syllableCount) {
    var syllables = isHebrew ? this.ni(word) : this.oi(word);
    var tmp = takeLast_0(syllables, syllableCount);
    return joinToString(tmp, '-', VOID, VOID, VOID, VOID, UniKeySyllable$Companion$rhymeKey$lambda_0);
  };
  protoOf(Companion_5).ej = function (word, isHebrew, syllableCount, $super) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return $super === VOID ? this.dj(word, isHebrew, syllableCount) : $super.dj.call(this, word, isHebrew, syllableCount);
  };
  protoOf(Companion_5).fj = function (word1, isHebrew1, word2, isHebrew2) {
    var key1 = this.ej(word1, isHebrew1);
    var key2 = this.ej(word2, isHebrew2);
    return key1 === key2;
  };
  protoOf(Companion_5).gj = function (word1, isHebrew1, word2, isHebrew2) {
    var syl1 = isHebrew1 ? this.ni(word1) : this.oi(word1);
    var syl2 = isHebrew2 ? this.ni(word2) : this.oi(word2);
    if (syl1.n() || syl2.n())
      return 100;
    var last1 = last(syl1);
    var last2 = last(syl2);
    if (last1.ji_1 === last2.ji_1 && last1.ki_1 === last2.ki_1)
      return 0;
    if (last1.ki_1 === last2.ki_1)
      return 10 + consonantDistance(this, last1.ji_1, last2.ji_1) | 0;
    return 50 + vowelDistance(this, last1.ki_1, last2.ki_1) | 0;
  };
  var Companion_instance_5;
  function Companion_getInstance_5() {
    if (Companion_instance_5 == null)
      new Companion_5();
    return Companion_instance_5;
  }
  function UniKeySyllable$hue$delegate$lambda(this$0) {
    return function () {
      return computeHue_1(this$0);
    };
  }
  function UniKeySyllable$_get_hue_$ref_61carx() {
    return function (p0) {
      return p0.vd();
    };
  }
  function UniKeySyllable(consonant, vowel, original) {
    Companion_getInstance_5();
    this.ji_1 = consonant;
    this.ki_1 = vowel;
    this.li_1 = original;
    var tmp = this;
    tmp.mi_1 = lazy(UniKeySyllable$hue$delegate$lambda(this));
  }
  protoOf(UniKeySyllable).vd = function () {
    var tmp0 = this.mi_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('hue', 1, tmp, UniKeySyllable$_get_hue_$ref_61carx(), null);
    return tmp0.l1();
  };
  protoOf(UniKeySyllable).toString = function () {
    return 'UniKeySyllable(consonant=' + this.ji_1 + ', vowel=' + this.ki_1 + ', original=' + this.li_1 + ')';
  };
  protoOf(UniKeySyllable).hashCode = function () {
    var result = getStringHashCode(this.ji_1);
    result = imul(result, 31) + getStringHashCode(this.ki_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.li_1) | 0;
    return result;
  };
  protoOf(UniKeySyllable).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof UniKeySyllable))
      return false;
    if (!(this.ji_1 === other.ji_1))
      return false;
    if (!(this.ki_1 === other.ki_1))
      return false;
    if (!(this.li_1 === other.li_1))
      return false;
    return true;
  };
  function UniKeys() {
    UniKeys_instance = this;
    this.hj_1 = mapOf([to('t', new UniKey('t', 't', 'T', '\u05D0', '\u0294', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('c', new UniKey('c', 'c', 'C', '\u05D1', 'v', VOID, VOID, VOID, VOID, VOID, VOID, 'b')), to('d', new UniKey('d', 'd', 'D', '\u05D2', 'g')), to('s', new UniKey('s', 's', 'S', '\u05D3', 'd')), to('v', new UniKey('v', 'v', 'V', '\u05D4', 'h', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('u', new UniKey('u', 'u', 'U', '\u05D5', 'v')), to('z', new UniKey('z', 'z', 'Z', '\u05D6', 'z')), to('j', new UniKey('j', 'j', 'J', '\u05D7', '\u0127', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('y', new UniKey('y', 'y', 'Y', '\u05D8', 't')), to('h', new UniKey('h', 'h', 'H', '\u05D9', 'j')), to('f', new UniKey('f', 'f', 'F', '\u05DB', 'x', VOID, VOID, VOID, VOID, VOID, VOID, 'k')), to('l', new UniKey('l', 'l', 'L', '\u05DA', 'x', VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('k', new UniKey('k', 'k', 'K', '\u05DC', 'l')), to('n', new UniKey('n', 'n', 'N', '\u05DE', 'm')), to('o', new UniKey('o', 'o', 'O', '\u05DD', 'm', VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('b', new UniKey('b', 'b', 'B', '\u05E0', 'n')), to('i', new UniKey('i', 'i', 'I', '\u05DF', 'n', VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('x', new UniKey('x', 'x', 'X', '\u05E1', 's')), to('g', new UniKey('g', 'g', 'G', '\u05E2', '\u0295', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('p', new UniKey('p', 'p', 'P', '\u05E4', 'f', VOID, VOID, VOID, VOID, VOID, VOID, 'p')), to(';', new UniKey(';', ';', ':', '\u05E3', 'f', ':', ':', VOID, VOID, VOID, VOID, VOID, VOID, true)), to('m', new UniKey('m', 'm', 'M', '\u05E6', 'ts')), to('.', new UniKey('.', '.', '>', '\u05E5', 'ts', '>', '>', VOID, VOID, VOID, VOID, VOID, VOID, true)), to('e', new UniKey('e', 'e', 'E', '\u05E7', 'k')), to('r', new UniKey('r', 'r', 'R', '\u05E8', '\u0281')), to('a', new UniKey('a', 'a', 'A', '\u05E9', '\u0283')), to(',', new UniKey(',', ',', '<', '\u05EA', 't', '<', '<')), to('q', new UniKey('q', 'q', 'Q', '/', 'kw')), to('w', new UniKey('w', 'w', 'W', "'", 'w')), to('/', new UniKey('/', '/', '?', '.', VOID, '?', '?')), to("'", new UniKey("'", "'", '"', ',', VOID, '"', '"')), to('`', new UniKey('`', '`', '~', '`', VOID, '~', '~')), to('1', new UniKey('1', '1', '!', '1', VOID, '!', '!')), to('2', new UniKey('2', '2', '@', '2', VOID, '@', '@')), to('3', new UniKey('3', '3', '#', '3', VOID, '#', '#')), to('4', new UniKey('4', '4', '$', '4', VOID, '$', '\u20AA')), to('5', new UniKey('5', '5', '%', '5', VOID, '%', '%')), to('6', new UniKey('6', '6', '^', '6', VOID, '^', '^')), to('7', new UniKey('7', '7', '&', '7', VOID, '&', '&')), to('8', new UniKey('8', '8', '*', '8', VOID, '*', '*')), to('9', new UniKey('9', '9', '(', '9', VOID, '(', ')')), to('0', new UniKey('0', '0', ')', '0', VOID, ')', '(')), to('-', new UniKey('-', '-', '_', '-', VOID, '_', '_')), to('=', new UniKey('=', '=', '+', '=', VOID, '+', '+')), to('[', new UniKey('[', '[', '{', '[', VOID, '{', '{')), to(']', new UniKey(']', ']', '}', ']', VOID, '}', '}')), to('\\', new UniKey('\\', '\\', '|', '\\', VOID, '|', '|'))]);
    var tmp = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = this.hj_1.q1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      if (element.zg_1.length === 1 && isLetter(charCodeAt(element.zg_1, 0))) {
        destination.g(element);
      }
    }
    tmp.ij_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_0 = this.hj_1.q1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_0 = tmp0_0.i();
    while (_iterator__ex2g4s_0.j()) {
      var element_0 = _iterator__ex2g4s_0.k();
      var tmp_1;
      if (element_0.ch_1.length === 1) {
        var containsArg = charCodeAt(element_0.ch_1, 0);
        tmp_1 = _Char___init__impl__6a9atx(1488) <= containsArg ? containsArg <= _Char___init__impl__6a9atx(1514) : false;
      } else {
        tmp_1 = false;
      }
      if (tmp_1) {
        destination_0.g(element_0);
      }
    }
    tmp_0.jj_1 = destination_0;
    var tmp_2 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_1 = this.hj_1.q1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_1 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_1 = tmp0_1.i();
    while (_iterator__ex2g4s_1.j()) {
      var element_1 = _iterator__ex2g4s_1.k();
      if (!(element_1.kh_1 == null)) {
        destination_1.g(element_1);
      }
    }
    tmp_2.kj_1 = destination_1;
    var tmp_3 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_2 = this.hj_1.q1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_2 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_2 = tmp0_2.i();
    while (_iterator__ex2g4s_2.j()) {
      var element_2 = _iterator__ex2g4s_2.k();
      if (element_2.lh_1) {
        destination_2.g(element_2);
      }
    }
    tmp_3.lj_1 = destination_2;
    var tmp_4 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_3 = this.hj_1.q1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_3 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_3 = tmp0_3.i();
    while (_iterator__ex2g4s_3.j()) {
      var element_3 = _iterator__ex2g4s_3.k();
      if (element_3.mh_1) {
        destination_3.g(element_3);
      }
    }
    tmp_4.mj_1 = destination_3;
  }
  protoOf(UniKeys).v7 = function (id) {
    return this.hj_1.o1(id);
  };
  protoOf(UniKeys).nj = function (he) {
    // Inline function 'kotlin.collections.find' call
    var tmp0 = this.hj_1.q1();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.i();
      while (_iterator__ex2g4s.j()) {
        var element = _iterator__ex2g4s.k();
        if (element.ch_1 === he) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    return tmp$ret$1;
  };
  protoOf(UniKeys).oj = function (en) {
    // Inline function 'kotlin.collections.find' call
    var tmp0 = this.hj_1.q1();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.i();
      while (_iterator__ex2g4s.j()) {
        var element = _iterator__ex2g4s.k();
        if (element.ah_1 === en) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    return tmp$ret$1;
  };
  protoOf(UniKeys).pj = function (key, mode, mods) {
    var tmp0_elvis_lhs = this.v7(key);
    var tmp1_elvis_lhs = tmp0_elvis_lhs == null ? this.nj(key) : tmp0_elvis_lhs;
    var tmp2_elvis_lhs = tmp1_elvis_lhs == null ? this.oj(key) : tmp1_elvis_lhs;
    var tmp;
    if (tmp2_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp2_elvis_lhs;
    }
    var uk = tmp;
    return uk.nh(mode, mods);
  };
  var UniKeys_instance;
  function UniKeys_getInstance() {
    if (UniKeys_instance == null)
      new UniKeys();
    return UniKeys_instance;
  }
  function UniKeyMode() {
    UniKeyMode_instance = this;
    this.qj_1 = listOf([KeyMode_he_getInstance(), KeyMode_en_getInstance(), KeyMode_EN_getInstance()]);
    this.rj_1 = 0;
  }
  protoOf(UniKeyMode).sj = function () {
    return this.qj_1.m(this.rj_1);
  };
  protoOf(UniKeyMode).tj = function (mode) {
    var tmp = this;
    // Inline function 'kotlin.takeIf' call
    var this_0 = this.qj_1.i1(mode);
    var tmp_0;
    if (this_0 >= 0) {
      tmp_0 = this_0;
    } else {
      tmp_0 = null;
    }
    var tmp0_elvis_lhs = tmp_0;
    tmp.rj_1 = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
  };
  protoOf(UniKeyMode).uj = function () {
    this.rj_1 = (this.rj_1 + 1 | 0) % this.qj_1.l() | 0;
    return this.sj();
  };
  var UniKeyMode_instance;
  function UniKeyMode_getInstance() {
    if (UniKeyMode_instance == null)
      new UniKeyMode();
    return UniKeyMode_instance;
  }
  function UniKeyModifiers() {
    this.vj_1 = false;
    this.wj_1 = false;
    this.xj_1 = false;
  }
  protoOf(UniKeyModifiers).yj = function () {
    this.vj_1 = false;
    this.wj_1 = false;
    this.xj_1 = false;
  };
  var UniKeyModifiers_instance;
  function UniKeyModifiers_getInstance() {
    return UniKeyModifiers_instance;
  }
  function getUniKey(id) {
    return UniKeys_getInstance().v7(id);
  }
  function getUniKeyByHe(he) {
    return UniKeys_getInstance().nj(he);
  }
  function getUniKeyByEn(en) {
    return UniKeys_getInstance().oj(en);
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
    return UniKeys_getInstance().pj(key, keyMode, new Modifiers(shift, alt, ctrl));
  }
  function getUkMode() {
    return UniKeyMode_getInstance().sj().s1_1;
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
    UniKeyMode_getInstance().tj(keyMode);
  }
  function cycleUkMode() {
    return UniKeyMode_getInstance().uj().s1_1;
  }
  function setModifier(mod, value) {
    switch (mod) {
      case 'shift':
        UniKeyModifiers_instance.vj_1 = value;
        break;
      case 'alt':
        UniKeyModifiers_instance.wj_1 = value;
        break;
      case 'ctrl':
        UniKeyModifiers_instance.xj_1 = value;
        break;
    }
  }
  function getModifiers() {
    var obj = {};
    obj['shift'] = UniKeyModifiers_instance.vj_1;
    obj['alt'] = UniKeyModifiers_instance.wj_1;
    obj['ctrl'] = UniKeyModifiers_instance.xj_1;
    return obj;
  }
  function resetModifiers() {
    return UniKeyModifiers_instance.yj();
  }
  function getNikudForIpa(ipa) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_instance_4.qg(ipa);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = item.ug_1;
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function applyNikud(letter, ipa, useMale) {
    useMale = useMale === VOID ? false : useMale;
    return letter.length === 1 ? Companion_instance_4.rg(charCodeAt(letter, 0), ipa, useMale) : letter;
  }
  function getHebrewLetterInfo(letter) {
    if (!(letter.length === 1))
      return null;
    var tmp0_elvis_lhs = Companion_getInstance_0().xa(charCodeAt(letter, 0));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var heLetter = tmp;
    var obj = {};
    obj['letter'] = toString(heLetter.fb_1);
    obj['ipa'] = heLetter.gb_1;
    obj['ipaDagesh'] = heLetter.hb_1;
    obj['name'] = heLetter.ib_1;
    obj['type'] = heLetter.jb_1.s1_1;
    obj['en'] = heLetter.kb_1;
    obj['isGuttural'] = heLetter.rb();
    obj['isBgdkpt'] = heLetter.sb();
    obj['isFinal'] = heLetter.qb();
    return obj;
  }
  function isBgdkpt(letter) {
    return letter.length === 1 && Companion_getInstance().ya(charCodeAt(letter, 0));
  }
  function getBgdkptSound(letter, hasDagesh, useClassical) {
    useClassical = useClassical === VOID ? false : useClassical;
    if (!(letter.length === 1))
      return null;
    var tmp0_elvis_lhs = Companion_getInstance().xa(charCodeAt(letter, 0));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bgdkpt = tmp;
    var sound = bgdkpt.za(hasDagesh, useClassical);
    var obj = {};
    obj['letter'] = sound.ab_1;
    obj['ipa'] = sound.bb_1;
    obj['en'] = sound.cb_1;
    return obj;
  }
  function getAllKeys() {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = UniKeys_getInstance().hj_1.p1();
    return copyToArray(this_0);
  }
  function getLetterKeys() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = UniKeys_getInstance().ij_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = item.zg_1;
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getHebrewKeys() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = UniKeys_getInstance().jj_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var tmp$ret$0 = item.zg_1;
      destination.g(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getIpaVowelInfo(ipa) {
    var tmp0_elvis_lhs = Companion_getInstance_1().dc(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var vowel = tmp;
    var obj = {};
    obj['ipa'] = vowel.vb_1;
    obj['name'] = vowel.wb_1;
    obj['heNikud'] = vowel.xb_1;
    obj['heName'] = vowel.yb_1;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = vowel.zb_1;
    obj['enSpellings'] = copyToArray(this_0);
    obj['quality'] = vowel.ac_1.s1_1;
    obj['warning'] = vowel.bc_1;
    return obj;
  }
  function getIpaConsonantInfo(ipa) {
    var tmp0_elvis_lhs = Companion_getInstance_2().dc(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var consonant = tmp;
    var obj = {};
    obj['ipa'] = consonant.gc_1;
    obj['name'] = consonant.hc_1;
    obj['he'] = consonant.ic_1;
    obj['en'] = consonant.jc_1;
    obj['geresh'] = consonant.kc_1;
    obj['heUnique'] = consonant.lc_1;
    obj['warning'] = consonant.mc_1;
    return obj;
  }
  function heIpa(word) {
    return IpaColor_getInstance().wc(word);
  }
  function enIpa(word) {
    return IpaColor_getInstance().vc(word);
  }
  function lineEndIpa(line, isHebrew) {
    return IpaColor_getInstance().xc(line, isHebrew);
  }
  function ipaHue(ipa) {
    return IpaColor_getInstance().yc(ipa);
  }
  function hsl(hue, saturation, lightness) {
    return IpaColor_getInstance().zc(hue, saturation, lightness);
  }
  function ipaEndColor(ipa) {
    return IpaColor_getInstance().ad(ipa);
  }
  function ipaMidColor(ipa) {
    return IpaColor_getInstance().bd(ipa);
  }
  function rhymeScheme(ipas) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaColor_getInstance().cd(toList_0(ipas));
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ch'] = toString(item.oc_1);
      obj['ipa'] = item.pc_1;
      obj['hue'] = item.qc_1;
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function detectScript(text) {
    return Companion_getInstance_5().wi(text).s1_1;
  }
  function toIpa(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().xi(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseHebrewSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().ni(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseEnglishSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().oi(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseArabicSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().pi(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseGreekSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().qi(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseHindiSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().ri(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseRussianSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().si(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseKoreanSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().ti(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseJapaneseSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().ui(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseChineseSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_5().vi(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['consonant'] = item.ji_1;
      obj['vowel'] = item.ki_1;
      obj['original'] = item.li_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function wordHueAuto(word) {
    return Companion_getInstance_5().yi(word);
  }
  function wordHue(word, isHebrew) {
    return Companion_getInstance_5().zi(word, isHebrew);
  }
  function wordEndColorAuto(word) {
    return Companion_getInstance_5().aj(word);
  }
  function wordEndColor(word, isHebrew) {
    return Companion_getInstance_5().bj(word, isHebrew);
  }
  function syllableHsl(hue, saturation, lightness) {
    saturation = saturation === VOID ? 70 : saturation;
    lightness = lightness === VOID ? 65 : lightness;
    return Companion_getInstance_5().zc(hue, saturation, lightness);
  }
  function rhymeKeyAuto(word, syllableCount) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return Companion_getInstance_5().cj(word, syllableCount);
  }
  function rhymeKey(word, isHebrew, syllableCount) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return Companion_getInstance_5().dj(word, isHebrew, syllableCount);
  }
  function rhymes(word1, isHebrew1, word2, isHebrew2) {
    return Companion_getInstance_5().fj(word1, isHebrew1, word2, isHebrew2);
  }
  function rhymeDistance(word1, isHebrew1, word2, isHebrew2) {
    return Companion_getInstance_5().gj(word1, isHebrew1, word2, isHebrew2);
  }
  function getSupportedLanguages() {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = KeyboardLayouts_getInstance().og_1;
    return copyToArray(this_0);
  }
  function getLayout(langCode) {
    var tmp0_elvis_lhs = KeyboardLayouts_getInstance().v7(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var layout = tmp;
    var obj = {};
    obj['code'] = layout.xe_1;
    obj['name'] = layout.ye_1;
    obj['nativeName'] = layout.ze_1;
    obj['script'] = layout.af_1.s1_1;
    obj['keys'] = layoutKeysToDynamic(layout.bf_1);
    return obj;
  }
  function layoutKeysToDynamic(keys) {
    var obj = {};
    // Inline function 'kotlin.collections.forEach' call
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = keys.r1().i();
    while (_iterator__ex2g4s.j()) {
      var element = _iterator__ex2g4s.k();
      // Inline function 'kotlin.collections.component1' call
      var keyId = element.k1();
      // Inline function 'kotlin.collections.component2' call
      var key = element.l1();
      var keyObj = {};
      keyObj['char'] = key.cf_1;
      keyObj['shift'] = key.gf();
      keyObj['ipa'] = key.df_1;
      keyObj['name'] = key.ef_1;
      obj[keyId] = keyObj;
    }
    return obj;
  }
  function getLayoutKey(langCode, keyId) {
    var tmp0_elvis_lhs = KeyboardLayouts_getInstance().v7(langCode);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var layout = tmp;
    var tmp1_elvis_lhs = layout.bf_1.o1(keyId);
    var tmp_0;
    if (tmp1_elvis_lhs == null) {
      return null;
    } else {
      tmp_0 = tmp1_elvis_lhs;
    }
    var key = tmp_0;
    var obj = {};
    obj['char'] = key.cf_1;
    obj['shift'] = key.gf();
    obj['ipa'] = key.df_1;
    obj['name'] = key.ef_1;
    return obj;
  }
  function getIpaConsonant(ipa) {
    var tmp0_elvis_lhs = IpaMatrix_getInstance().se(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var cons = tmp;
    var obj = {};
    obj['ipa'] = cons.hd_1;
    obj['name'] = cons.id_1;
    obj['place'] = cons.jd_1.s1_1;
    obj['manner'] = cons.kd_1.s1_1;
    obj['voiced'] = cons.ld_1;
    obj['aspirated'] = cons.md_1;
    obj['breathy'] = cons.nd_1;
    obj['emphatic'] = cons.od_1;
    obj['hue'] = cons.vd();
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = cons.pd_1;
    obj['languages'] = copyToArray(this_0);
    return obj;
  }
  function getIpaVowelFull(ipa) {
    var tmp0_elvis_lhs = IpaMatrix_getInstance().te(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var vowel = tmp;
    var obj = {};
    obj['ipa'] = vowel.ae_1;
    obj['name'] = vowel.be_1;
    obj['height'] = vowel.ce_1.s1_1;
    obj['backness'] = vowel.de_1.s1_1;
    obj['rounded'] = vowel.ee_1;
    obj['long'] = vowel.fe_1;
    obj['nasal'] = vowel.ge_1;
    obj['hue'] = vowel.vd();
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = vowel.he_1;
    obj['languages'] = copyToArray(this_0);
    return obj;
  }
  function getConsonantsForLanguage(langCode) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().ue(langCode);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.hd_1;
      obj['name'] = item.id_1;
      obj['place'] = item.jd_1.s1_1;
      obj['manner'] = item.kd_1.s1_1;
      obj['voiced'] = item.ld_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getVowelsForLanguage(langCode) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().ve(langCode);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.ae_1;
      obj['name'] = item.be_1;
      obj['height'] = item.ce_1.s1_1;
      obj['backness'] = item.de_1.s1_1;
      obj['rounded'] = item.ee_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getPhonemeHue(ipa) {
    return IpaMatrix_getInstance().we(ipa);
  }
  function getAllConsonants() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().ne_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.hd_1;
      obj['name'] = item.id_1;
      obj['place'] = item.jd_1.s1_1;
      obj['manner'] = item.kd_1.s1_1;
      obj['voiced'] = item.ld_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getAllVowels() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaMatrix_getInstance().oe_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.i();
    while (_iterator__ex2g4s.j()) {
      var item = _iterator__ex2g4s.k();
      var obj = {};
      obj['ipa'] = item.ae_1;
      obj['name'] = item.be_1;
      obj['height'] = item.ce_1.s1_1;
      obj['backness'] = item.de_1.s1_1;
      obj['rounded'] = item.ee_1;
      obj['hue'] = item.vd();
      destination.g(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  //region block: init
  Companion_instance_3 = new Companion_3();
  Companion_instance_4 = new Companion_4();
  UniKeyModifiers_instance = new UniKeyModifiers();
  //endregion
  //region block: exports
  function $jsExportAll$(_) {
    var $org = _.org || (_.org = {});
    var $org$pocketworkstation = $org.pocketworkstation || ($org.pocketworkstation = {});
    var $org$pocketworkstation$unikey = $org$pocketworkstation.unikey || ($org$pocketworkstation.unikey = {});
    $org$pocketworkstation$unikey.getUniKey = getUniKey;
    $org$pocketworkstation$unikey.getUniKeyByHe = getUniKeyByHe;
    $org$pocketworkstation$unikey.getUniKeyByEn = getUniKeyByEn;
    $org$pocketworkstation$unikey.getDisplay = getDisplay;
    $org$pocketworkstation$unikey.getUkMode = getUkMode;
    $org$pocketworkstation$unikey.setUkMode = setUkMode;
    $org$pocketworkstation$unikey.cycleUkMode = cycleUkMode;
    $org$pocketworkstation$unikey.setModifier = setModifier;
    $org$pocketworkstation$unikey.getModifiers = getModifiers;
    $org$pocketworkstation$unikey.resetModifiers = resetModifiers;
    $org$pocketworkstation$unikey.getNikudForIpa = getNikudForIpa;
    $org$pocketworkstation$unikey.applyNikud = applyNikud;
    $org$pocketworkstation$unikey.getHebrewLetterInfo = getHebrewLetterInfo;
    $org$pocketworkstation$unikey.isBgdkpt = isBgdkpt;
    $org$pocketworkstation$unikey.getBgdkptSound = getBgdkptSound;
    $org$pocketworkstation$unikey.getAllKeys = getAllKeys;
    $org$pocketworkstation$unikey.getLetterKeys = getLetterKeys;
    $org$pocketworkstation$unikey.getHebrewKeys = getHebrewKeys;
    $org$pocketworkstation$unikey.getIpaVowelInfo = getIpaVowelInfo;
    $org$pocketworkstation$unikey.getIpaConsonantInfo = getIpaConsonantInfo;
    $org$pocketworkstation$unikey.heIpa = heIpa;
    $org$pocketworkstation$unikey.enIpa = enIpa;
    $org$pocketworkstation$unikey.lineEndIpa = lineEndIpa;
    $org$pocketworkstation$unikey.ipaHue = ipaHue;
    $org$pocketworkstation$unikey.hsl = hsl;
    $org$pocketworkstation$unikey.ipaEndColor = ipaEndColor;
    $org$pocketworkstation$unikey.ipaMidColor = ipaMidColor;
    $org$pocketworkstation$unikey.rhymeScheme = rhymeScheme;
    $org$pocketworkstation$unikey.detectScript = detectScript;
    $org$pocketworkstation$unikey.toIpa = toIpa;
    $org$pocketworkstation$unikey.parseHebrewSyllables = parseHebrewSyllables;
    $org$pocketworkstation$unikey.parseEnglishSyllables = parseEnglishSyllables;
    $org$pocketworkstation$unikey.parseArabicSyllables = parseArabicSyllables;
    $org$pocketworkstation$unikey.parseGreekSyllables = parseGreekSyllables;
    $org$pocketworkstation$unikey.parseHindiSyllables = parseHindiSyllables;
    $org$pocketworkstation$unikey.parseRussianSyllables = parseRussianSyllables;
    $org$pocketworkstation$unikey.parseKoreanSyllables = parseKoreanSyllables;
    $org$pocketworkstation$unikey.parseJapaneseSyllables = parseJapaneseSyllables;
    $org$pocketworkstation$unikey.parseChineseSyllables = parseChineseSyllables;
    $org$pocketworkstation$unikey.wordHueAuto = wordHueAuto;
    $org$pocketworkstation$unikey.wordHue = wordHue;
    $org$pocketworkstation$unikey.wordEndColorAuto = wordEndColorAuto;
    $org$pocketworkstation$unikey.wordEndColor = wordEndColor;
    $org$pocketworkstation$unikey.syllableHsl = syllableHsl;
    $org$pocketworkstation$unikey.rhymeKeyAuto = rhymeKeyAuto;
    $org$pocketworkstation$unikey.rhymeKey = rhymeKey;
    $org$pocketworkstation$unikey.rhymes = rhymes;
    $org$pocketworkstation$unikey.rhymeDistance = rhymeDistance;
    $org$pocketworkstation$unikey.getSupportedLanguages = getSupportedLanguages;
    $org$pocketworkstation$unikey.getLayout = getLayout;
    $org$pocketworkstation$unikey.getLayoutKey = getLayoutKey;
    $org$pocketworkstation$unikey.getIpaConsonant = getIpaConsonant;
    $org$pocketworkstation$unikey.getIpaVowelFull = getIpaVowelFull;
    $org$pocketworkstation$unikey.getConsonantsForLanguage = getConsonantsForLanguage;
    $org$pocketworkstation$unikey.getVowelsForLanguage = getVowelsForLanguage;
    $org$pocketworkstation$unikey.getPhonemeHue = getPhonemeHue;
    $org$pocketworkstation$unikey.getAllConsonants = getAllConsonants;
    $org$pocketworkstation$unikey.getAllVowels = getAllVowels;
  }
  $jsExportAll$(_);
  //endregion
  return _;
}));

//# sourceMappingURL=hackerskeyboard-unikey-kmp.js.map
