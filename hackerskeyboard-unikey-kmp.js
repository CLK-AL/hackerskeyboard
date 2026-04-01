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
  var collectionSizeOrDefault = kotlin_kotlin.$_$.m;
  var mapCapacity = kotlin_kotlin.$_$.v;
  var coerceAtLeast = kotlin_kotlin.$_$.x1;
  var LinkedHashMap_init_$Create$ = kotlin_kotlin.$_$.d;
  var Char = kotlin_kotlin.$_$.i2;
  var Unit_instance = kotlin_kotlin.$_$.k;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.c;
  var protoOf = kotlin_kotlin.$_$.u1;
  var initMetadataForCompanion = kotlin_kotlin.$_$.p1;
  var enumEntries = kotlin_kotlin.$_$.z;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.g;
  var Enum = kotlin_kotlin.$_$.j2;
  var VOID = kotlin_kotlin.$_$.a;
  var initMetadataForClass = kotlin_kotlin.$_$.o1;
  var getStringHashCode = kotlin_kotlin.$_$.m1;
  var listOf = kotlin_kotlin.$_$.u;
  var listOf_0 = kotlin_kotlin.$_$.t;
  var toString = kotlin_kotlin.$_$.j;
  var Char__hashCode_impl_otmys = kotlin_kotlin.$_$.h;
  var Regex_init_$Create$ = kotlin_kotlin.$_$.e;
  var to = kotlin_kotlin.$_$.p2;
  var mapOf = kotlin_kotlin.$_$.w;
  var charSequenceLength = kotlin_kotlin.$_$.i1;
  var takeLast = kotlin_kotlin.$_$.f2;
  var StringBuilder_init_$Create$ = kotlin_kotlin.$_$.f;
  var charCodeAt = kotlin_kotlin.$_$.g1;
  var Char__toInt_impl_vasixd = kotlin_kotlin.$_$.i;
  var equals = kotlin_kotlin.$_$.j1;
  var THROW_CCE = kotlin_kotlin.$_$.l2;
  var isCharSequence = kotlin_kotlin.$_$.r1;
  var trim = kotlin_kotlin.$_$.h2;
  var toString_0 = kotlin_kotlin.$_$.v1;
  var lastOrNull = kotlin_kotlin.$_$.r;
  var Long = kotlin_kotlin.$_$.k2;
  var fromInt = kotlin_kotlin.$_$.c1;
  var multiply = kotlin_kotlin.$_$.e1;
  var bitwiseXor = kotlin_kotlin.$_$.b1;
  var bitwiseAnd = kotlin_kotlin.$_$.a1;
  var coerceAtMost = kotlin_kotlin.$_$.y1;
  var dropLast = kotlin_kotlin.$_$.b2;
  var modulo = kotlin_kotlin.$_$.d1;
  var toNumber = kotlin_kotlin.$_$.f1;
  var numberToInt = kotlin_kotlin.$_$.t1;
  var ArrayList_init_$Create$_0 = kotlin_kotlin.$_$.b;
  var charSequenceGet = kotlin_kotlin.$_$.h1;
  var initMetadataForObject = kotlin_kotlin.$_$.q1;
  var emptyList = kotlin_kotlin.$_$.o;
  var first = kotlin_kotlin.$_$.p;
  var noWhenBranchMatchedException = kotlin_kotlin.$_$.n2;
  var toString_1 = kotlin_kotlin.$_$.o2;
  var hashCode = kotlin_kotlin.$_$.n1;
  var getBooleanHashCode = kotlin_kotlin.$_$.k1;
  var take = kotlin_kotlin.$_$.g2;
  var firstOrNull = kotlin_kotlin.$_$.c2;
  var abs = kotlin_kotlin.$_$.w1;
  var KtMap = kotlin_kotlin.$_$.l;
  var isInterface = kotlin_kotlin.$_$.s1;
  var contains = kotlin_kotlin.$_$.a2;
  var substring = kotlin_kotlin.$_$.e2;
  var takeLast_0 = kotlin_kotlin.$_$.x;
  var joinToString = kotlin_kotlin.$_$.q;
  var last = kotlin_kotlin.$_$.s;
  var lazy = kotlin_kotlin.$_$.m2;
  var KProperty1 = kotlin_kotlin.$_$.z1;
  var getPropertyCallableRef = kotlin_kotlin.$_$.l1;
  var isLetter = kotlin_kotlin.$_$.d2;
  var copyToArray = kotlin_kotlin.$_$.n;
  var toList = kotlin_kotlin.$_$.y;
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
  initMetadataForCompanion(Companion_3);
  initMetadataForClass(Nikud, 'Nikud', VOID, Enum);
  initMetadataForClass(UniKey, 'UniKey');
  initMetadataForClass(KeyMode, 'KeyMode', VOID, Enum);
  initMetadataForClass(Modifiers, 'Modifiers', Modifiers);
  initMetadataForClass(ConsonantFeatures, 'ConsonantFeatures');
  initMetadataForClass(VowelResult, 'VowelResult');
  initMetadataForCompanion(Companion_4);
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
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var element = _iterator__ex2g4s.j();
      var tmp$ret$0 = new Char(element.ca_1);
      destination.c3(tmp$ret$0, element);
    }
    tmp.ga_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = get_entries();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_0 = tmp0.h();
    while (_iterator__ex2g4s_0.i()) {
      var element_0 = _iterator__ex2g4s_0.j();
      if (element_0.ia()) {
        destination_0.f(element_0);
      }
    }
    tmp_0.ha_1 = destination_0;
  }
  protoOf(Companion).ja = function (c) {
    return this.ga_1.n1(new Char(c));
  };
  protoOf(Companion).ka = function (c) {
    return this.ga_1.l1(new Char(c));
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
    this.ca_1 = letter;
    this.da_1 = withDagesh;
    this.ea_1 = withoutDagesh;
    this.fa_1 = classical;
  }
  protoOf(Bgdkpt).la = function (hasDagesh, useClassical) {
    return hasDagesh ? this.da_1 : useClassical && !(this.fa_1 == null) ? this.fa_1 : this.ea_1;
  };
  protoOf(Bgdkpt).ia = function () {
    return !(this.da_1.na_1 === this.ea_1.na_1);
  };
  function BgdkptSound(letter, ipa, en) {
    this.ma_1 = letter;
    this.na_1 = ipa;
    this.oa_1 = en;
  }
  protoOf(BgdkptSound).toString = function () {
    return 'BgdkptSound(letter=' + this.ma_1 + ', ipa=' + this.na_1 + ', en=' + this.oa_1 + ')';
  };
  protoOf(BgdkptSound).hashCode = function () {
    var result = getStringHashCode(this.ma_1);
    result = imul(result, 31) + getStringHashCode(this.na_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.oa_1) | 0;
    return result;
  };
  protoOf(BgdkptSound).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof BgdkptSound))
      return false;
    if (!(this.ma_1 === other.ma_1))
      return false;
    if (!(this.na_1 === other.na_1))
      return false;
    if (!(this.oa_1 === other.oa_1))
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
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var element = _iterator__ex2g4s.j();
      var tmp$ret$0 = new Char(element.ra_1);
      destination.c3(tmp$ret$0, element);
    }
    tmp.bb_1 = destination;
  }
  protoOf(Companion_0).ja = function (c) {
    return this.bb_1.n1(new Char(c));
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
    this.ra_1 = letter;
    this.sa_1 = ipa;
    this.ta_1 = ipaDagesh;
    this.ua_1 = displayName;
    this.va_1 = type;
    this.wa_1 = en;
    this.xa_1 = enDagesh;
    this.ya_1 = finalForm;
    this.za_1 = silentEnd;
    this.ab_1 = classical;
  }
  protoOf(HebrewLetter).cb = function () {
    return listOf([HebrewLetter_KAF_SOFIT_getInstance(), HebrewLetter_MEM_SOFIT_getInstance(), HebrewLetter_NUN_SOFIT_getInstance(), HebrewLetter_PE_SOFIT_getInstance(), HebrewLetter_TSADI_SOFIT_getInstance()]).g1(this);
  };
  protoOf(HebrewLetter).db = function () {
    return this.va_1.equals(LetterType_GUTTURAL_getInstance());
  };
  protoOf(HebrewLetter).eb = function () {
    return this.va_1.equals(LetterType_BGDKPT_getInstance());
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
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var element = _iterator__ex2g4s.j();
      var tmp$ret$0 = element.hb_1;
      destination.c3(tmp$ret$0, element);
    }
    tmp.ob_1 = destination;
  }
  protoOf(Companion_1).pb = function (ipa) {
    return this.ob_1.n1(ipa);
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
    this.hb_1 = ipa;
    this.ib_1 = displayName;
    this.jb_1 = heNikud;
    this.kb_1 = heName;
    this.lb_1 = enSpellings;
    this.mb_1 = quality;
    this.nb_1 = warning;
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
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var element = _iterator__ex2g4s.j();
      var tmp$ret$0 = element.sb_1;
      destination.c3(tmp$ret$0, element);
    }
    tmp.zb_1 = destination;
  }
  protoOf(Companion_2).pb = function (ipa) {
    return this.zb_1.n1(ipa);
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
    this.sb_1 = ipa;
    this.tb_1 = displayName;
    this.ub_1 = he;
    this.vb_1 = en;
    this.wb_1 = geresh;
    this.xb_1 = heUnique;
    this.yb_1 = warning;
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
    this.ac_1 = letter;
    this.bc_1 = ipa;
    this.cc_1 = hue;
  }
  protoOf(RhymeSchemeItem).toString = function () {
    return 'RhymeSchemeItem(letter=' + toString(this.ac_1) + ', ipa=' + this.bc_1 + ', hue=' + this.cc_1 + ')';
  };
  protoOf(RhymeSchemeItem).hashCode = function () {
    var result = Char__hashCode_impl_otmys(this.ac_1);
    result = imul(result, 31) + getStringHashCode(this.bc_1) | 0;
    result = imul(result, 31) + this.cc_1 | 0;
    return result;
  };
  protoOf(RhymeSchemeItem).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof RhymeSchemeItem))
      return false;
    if (!(this.ac_1 === other.ac_1))
      return false;
    if (!(this.bc_1 === other.bc_1))
      return false;
    if (!(this.cc_1 === other.cc_1))
      return false;
    return true;
  };
  function IpaColor() {
    IpaColor_instance = this;
    this.dc_1 = listOf([to(Regex_init_$Create$('ing$'), 'IN'), to(Regex_init_$Create$('tion$'), 'shEn'), to(Regex_init_$Create$('ting$'), 'tIN'), to(Regex_init_$Create$('ring$'), 'rIN'), to(Regex_init_$Create$('ling$'), 'lIN'), to(Regex_init_$Create$('ning$'), 'nIN'), to(Regex_init_$Create$('ent$'), 'Ent'), to(Regex_init_$Create$('ant$'), 'ant'), to(Regex_init_$Create$('ine$'), 'ajn'), to(Regex_init_$Create$('ate$'), 'ejt'), to(Regex_init_$Create$('ight$'), 'ajt'), to(Regex_init_$Create$('ite$'), 'ajt'), to(Regex_init_$Create$('ake$'), 'ejk'), to(Regex_init_$Create$('ade$'), 'ejd'), to(Regex_init_$Create$('age$'), 'Ij'), to(Regex_init_$Create$('ound$'), 'awnd'), to(Regex_init_$Create$('own$'), 'awn'), to(Regex_init_$Create$('out$'), 'awt'), to(Regex_init_$Create$('ash$'), 'ash'), to(Regex_init_$Create$('ush$'), 'ush'), to(Regex_init_$Create$('ay$'), 'ej'), to(Regex_init_$Create$('ey$'), 'ej'), to(Regex_init_$Create$('oy$'), 'oj'), to(Regex_init_$Create$('er$'), 'Er'), to(Regex_init_$Create$('or$'), 'Er'), to(Regex_init_$Create$('ar$'), 'ar'), to(Regex_init_$Create$('le$'), 'El'), to(Regex_init_$Create$('al$'), 'El'), to(Regex_init_$Create$('ed$'), 'd'), to(Regex_init_$Create$('es$'), 'z'), to(Regex_init_$Create$('ly$'), 'li'), to(Regex_init_$Create$('ck$'), 'k'), to(Regex_init_$Create$('ss$'), 's'), to(Regex_init_$Create$('ll$'), 'l')]);
    this.ec_1 = mapOf([to(new Char(_Char___init__impl__6a9atx(1488)), ''), to(new Char(_Char___init__impl__6a9atx(1489)), 'v'), to(new Char(_Char___init__impl__6a9atx(1490)), 'g'), to(new Char(_Char___init__impl__6a9atx(1491)), 'd'), to(new Char(_Char___init__impl__6a9atx(1492)), 'h'), to(new Char(_Char___init__impl__6a9atx(1493)), 'v'), to(new Char(_Char___init__impl__6a9atx(1494)), 'z'), to(new Char(_Char___init__impl__6a9atx(1495)), 'x'), to(new Char(_Char___init__impl__6a9atx(1496)), 't'), to(new Char(_Char___init__impl__6a9atx(1497)), 'j'), to(new Char(_Char___init__impl__6a9atx(1498)), 'x'), to(new Char(_Char___init__impl__6a9atx(1499)), 'x'), to(new Char(_Char___init__impl__6a9atx(1500)), 'l'), to(new Char(_Char___init__impl__6a9atx(1501)), 'm'), to(new Char(_Char___init__impl__6a9atx(1502)), 'm'), to(new Char(_Char___init__impl__6a9atx(1503)), 'n'), to(new Char(_Char___init__impl__6a9atx(1504)), 'n'), to(new Char(_Char___init__impl__6a9atx(1505)), 's'), to(new Char(_Char___init__impl__6a9atx(1506)), ''), to(new Char(_Char___init__impl__6a9atx(1507)), 'f'), to(new Char(_Char___init__impl__6a9atx(1508)), 'f'), to(new Char(_Char___init__impl__6a9atx(1509)), 'ts'), to(new Char(_Char___init__impl__6a9atx(1510)), 'ts'), to(new Char(_Char___init__impl__6a9atx(1511)), 'k'), to(new Char(_Char___init__impl__6a9atx(1512)), 'r'), to(new Char(_Char___init__impl__6a9atx(1513)), 'sh'), to(new Char(_Char___init__impl__6a9atx(1514)), 't')]);
    this.fc_1 = mapOf([to(new Char(_Char___init__impl__6a9atx(1489)), 'b'), to(new Char(_Char___init__impl__6a9atx(1499)), 'k'), to(new Char(_Char___init__impl__6a9atx(1508)), 'p')]);
    this.gc_1 = mapOf([to(1456, '@'), to(1457, 'e'), to(1458, 'a'), to(1459, 'o'), to(1460, 'i'), to(1461, 'e'), to(1462, 'E'), to(1463, 'a'), to(1464, 'a'), to(1465, 'o'), to(1466, 'o'), to(1467, 'u')]);
  }
  protoOf(IpaColor).hc = function (word) {
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0 = word.toLowerCase();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[^a-z]').b7(tmp0, '');
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(w) === 0)
      return '';
    var _iterator__ex2g4s = this.dc_1.h();
    while (_iterator__ex2g4s.i()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.j();
      var pattern = _destruct__k2r9zo.r8();
      var ipa = _destruct__k2r9zo.s8();
      if (pattern.x6(w))
        return ipa;
    }
    return takeLast(w, 3);
  };
  protoOf(IpaColor).ic = function (word) {
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[,.\\-;:!?\\s]+$').b7(word, '');
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
        var tmp0_elvis_lhs = this.ec_1.n1(new Char(c));
        result.a6(tmp0_elvis_lhs == null ? '' : tmp0_elvis_lhs);
      } else if (cp === 1468) {
        var tmp1_safe_receiver = lastConsonant;
        var tmp = tmp1_safe_receiver;
        if ((tmp == null ? null : new Char(tmp)) == null)
          null;
        else {
          var tmp_0 = tmp1_safe_receiver;
          // Inline function 'kotlin.let' call
          var lc = (tmp_0 == null ? null : new Char(tmp_0)).t_1;
          var tmp0_safe_receiver = IpaColor_getInstance().fc_1.n1(new Char(lc));
          var tmp_1;
          if (tmp0_safe_receiver == null) {
            tmp_1 = null;
          } else {
            // Inline function 'kotlin.let' call
            if (lastConsonantPos >= 0) {
              var before = result.l6(0, lastConsonantPos);
              var tmp0_safe_receiver_0 = IpaColor_getInstance().ec_1.n1(new Char(lc));
              var tmp1_elvis_lhs = tmp0_safe_receiver_0 == null ? null : tmp0_safe_receiver_0.length;
              var consonantLen = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
              var after = (lastConsonantPos + consonantLen | 0) <= result.a() ? result.k6(lastConsonantPos + consonantLen | 0) : '';
              result.n6();
              result.a6(before);
              result.a6(tmp0_safe_receiver);
              result.a6(after);
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
            var before_0 = result.l6(0, lastConsonantPos);
            var after_0 = (lastConsonantPos + 2 | 0) <= result.a() ? result.k6(lastConsonantPos + 2 | 0) : '';
            result.n6();
            result.a6(before_0);
            result.a6('s');
            result.a6(after_0);
          }
        } else {
          var tmp2_safe_receiver = this.gc_1.n1(cp);
          if (tmp2_safe_receiver == null)
            null;
          else {
            // Inline function 'kotlin.let' call
            result.a6(tmp2_safe_receiver);
          }
        }
    }
    return result.toString();
  };
  protoOf(IpaColor).jc = function (line, isHebrew) {
    // Inline function 'kotlin.text.replace' call
    // Inline function 'kotlin.text.trim' call
    var this_0 = Regex_init_$Create$('[,.\\-;:!?\\s]+$').b7(line, '');
    var cleaned = toString_0(trim(isCharSequence(this_0) ? this_0 : THROW_CCE()));
    // Inline function 'kotlin.text.split' call
    var words = Regex_init_$Create$('\\s+').j7(cleaned, 0);
    var tmp0_elvis_lhs = lastOrNull(words);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return '';
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var lastWord = tmp;
    var ipa = isHebrew ? this.ic(lastWord) : this.hc(lastWord);
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
  protoOf(IpaColor).kc = function (ipa) {
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
  protoOf(IpaColor).lc = function (hue, saturation, lightness) {
    return 'hsl(' + hue + ', ' + saturation + '%, ' + lightness + '%)';
  };
  protoOf(IpaColor).mc = function (ipa) {
    var hue = this.kc(ipa);
    return this.lc(hue, 80, 72);
  };
  protoOf(IpaColor).nc = function (ipa) {
    var hue = this.kc(ipa);
    return this.lc(hue, 45, 55);
  };
  protoOf(IpaColor).oc = function (ipas) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var groups = ArrayList_init_$Create$();
    var letters = 'ABCDEFGHIJKLM';
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(ipas, 10));
    var _iterator__ex2g4s = ipas.h();
    while (_iterator__ex2g4s.i()) {
      var item = _iterator__ex2g4s.j();
      var tmp;
      // Inline function 'kotlin.text.isEmpty' call
      if (charSequenceLength(item) === 0) {
        tmp = new RhymeSchemeItem(_Char___init__impl__6a9atx(120), '?', 0);
      } else {
        var key = takeLast(item, 3);
        var hue = IpaColor_getInstance().kc(item);
        // Inline function 'kotlin.collections.find' call
        var tmp$ret$3;
        $l$block: {
          // Inline function 'kotlin.collections.firstOrNull' call
          var _iterator__ex2g4s_0 = groups.h();
          while (_iterator__ex2g4s_0.i()) {
            var element = _iterator__ex2g4s_0.j();
            if (element.p8_1 === key) {
              tmp$ret$3 = element;
              break $l$block;
            }
          }
          tmp$ret$3 = null;
        }
        var existing = tmp$ret$3;
        var tmp_0;
        if (!(existing == null)) {
          tmp_0 = new RhymeSchemeItem(existing.q8_1.t_1, key, hue);
        } else {
          // Inline function 'kotlin.text.getOrElse' call
          var index = groups.k();
          var tmp_1;
          if (0 <= index ? index <= (charSequenceLength(letters) - 1 | 0) : false) {
            tmp_1 = charSequenceGet(letters, index);
          } else {
            tmp_1 = _Char___init__impl__6a9atx(63);
          }
          var letter = tmp_1;
          groups.f(to(key, new Char(letter)));
          tmp_0 = new RhymeSchemeItem(letter, key, hue);
        }
        tmp = tmp_0;
      }
      var tmp$ret$7 = tmp;
      destination.f(tmp$ret$7);
    }
    return destination;
  };
  var IpaColor_instance;
  function IpaColor_getInstance() {
    if (IpaColor_instance == null)
      new IpaColor();
    return IpaColor_instance;
  }
  function isGuttural($this, letter) {
    return listOf([new Char(_Char___init__impl__6a9atx(1488)), new Char(_Char___init__impl__6a9atx(1492)), new Char(_Char___init__impl__6a9atx(1495)), new Char(_Char___init__impl__6a9atx(1506)), new Char(_Char___init__impl__6a9atx(1512))]).g1(new Char(letter));
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
  function Companion_3() {
  }
  protoOf(Companion_3).pc = function (ipa) {
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
  protoOf(Companion_3).qc = function (letter, ipa, useMale) {
    var nikudList = this.pc(ipa);
    if (nikudList.m())
      return toString(letter);
    if (isGuttural(this, letter) && ipa === '\u0259') {
      return toString(letter) + Nikud_HATAF_PATACH_getInstance().tc_1;
    }
    var nikud = first(nikudList);
    if (useMale) {
      switch (ipa) {
        case 'i':
          return toString(letter) + Nikud_CHIRIK_MALE_getInstance().tc_1;
        case 'o':
          return toString(letter) + Nikud_CHOLAM_MALE_getInstance().tc_1;
        case 'u':
          return toString(letter) + Nikud_SHURUK_getInstance().tc_1;
        default:
          return toString(letter) + nikud.tc_1;
      }
    }
    return toString(letter) + nikud.tc_1;
  };
  var Companion_instance_3;
  function Companion_getInstance_3() {
    return Companion_instance_3;
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
    this.tc_1 = mark;
    this.uc_1 = displayName;
    this.vc_1 = ipa;
    this.wc_1 = ipaAlt;
    this.xc_1 = modifier;
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
    this.yc_1 = id;
    this.zc_1 = en;
    this.ad_1 = EN;
    this.bd_1 = he;
    this.cd_1 = ipa;
    this.dd_1 = shift;
    this.ed_1 = heShift;
    this.fd_1 = syl;
    this.gd_1 = enSyl;
    this.hd_1 = mg;
    this.id_1 = mgSyl;
    this.jd_1 = dagesh;
    this.kd_1 = guttural;
    this.ld_1 = isFinal;
  }
  protoOf(UniKey).md = function (mode, mods) {
    if (mods.pd_1)
      return this.cd_1;
    if (mods.od_1 && !(this.hd_1 == null)) {
      var mf = mods.nd_1;
      var tmp;
      switch (mode.s1_1) {
        case 2:
        case 1:
          tmp = mf ? this.hd_1.xd_1 : this.hd_1.wd_1;
          break;
        case 0:
          tmp = mf ? this.hd_1.td_1 : this.hd_1.sd_1;
          break;
        default:
          noWhenBranchMatchedException();
          break;
      }
      return tmp;
    }
    if (mods.nd_1 && !mods.od_1) {
      var tmp_0;
      switch (mode.s1_1) {
        case 0:
          var tmp_1;
          // Inline function 'kotlin.collections.isNullOrEmpty' call

          var this_0 = this.fd_1;
          if (!(this_0 == null || this_0.m())) {
            tmp_1 = this.bd_1 + '\xB7';
          } else {
            tmp_1 = this.bd_1;
          }

          tmp_0 = tmp_1;
          break;
        case 1:
          var tmp_2;
          // Inline function 'kotlin.collections.isNullOrEmpty' call

          var this_1 = this.gd_1;
          if (!(this_1 == null || this_1.m())) {
            tmp_2 = this.zc_1 + '\xB7';
          } else {
            // Inline function 'kotlin.text.ifEmpty' call
            var this_2 = this.ad_1;
            var tmp_3;
            // Inline function 'kotlin.text.isEmpty' call
            if (charSequenceLength(this_2) === 0) {
              // Inline function 'kotlin.text.uppercase' call
              // Inline function 'kotlin.js.asDynamic' call
              tmp_3 = this.zc_1.toUpperCase();
            } else {
              tmp_3 = this_2;
            }
            tmp_2 = tmp_3;
          }

          tmp_0 = tmp_2;
          break;
        case 2:
          var tmp2_elvis_lhs = this.dd_1;
          tmp_0 = tmp2_elvis_lhs == null ? this.ad_1 : tmp2_elvis_lhs;
          break;
        default:
          noWhenBranchMatchedException();
          break;
      }
      return tmp_0;
    }
    var tmp_4;
    switch (mode.s1_1) {
      case 1:
        tmp_4 = this.zc_1;
        break;
      case 2:
        // Inline function 'kotlin.text.ifEmpty' call

        var this_3 = this.ad_1;
        var tmp_5;
        // Inline function 'kotlin.text.isEmpty' call

        if (charSequenceLength(this_3) === 0) {
          // Inline function 'kotlin.text.uppercase' call
          // Inline function 'kotlin.js.asDynamic' call
          tmp_5 = this.zc_1.toUpperCase();
        } else {
          tmp_5 = this_3;
        }

        tmp_4 = tmp_5;
        break;
      case 0:
        tmp_4 = this.bd_1;
        break;
      default:
        noWhenBranchMatchedException();
        break;
    }
    return tmp_4;
  };
  protoOf(UniKey).toString = function () {
    return 'UniKey(id=' + this.yc_1 + ', en=' + this.zc_1 + ', EN=' + this.ad_1 + ', he=' + this.bd_1 + ', ipa=' + this.cd_1 + ', shift=' + this.dd_1 + ', heShift=' + this.ed_1 + ', syl=' + toString_1(this.fd_1) + ', enSyl=' + toString_1(this.gd_1) + ', mg=' + toString_1(this.hd_1) + ', mgSyl=' + toString_1(this.id_1) + ', dagesh=' + this.jd_1 + ', guttural=' + this.kd_1 + ', isFinal=' + this.ld_1 + ')';
  };
  protoOf(UniKey).hashCode = function () {
    var result = getStringHashCode(this.yc_1);
    result = imul(result, 31) + getStringHashCode(this.zc_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.ad_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.bd_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.cd_1) | 0;
    result = imul(result, 31) + (this.dd_1 == null ? 0 : getStringHashCode(this.dd_1)) | 0;
    result = imul(result, 31) + (this.ed_1 == null ? 0 : getStringHashCode(this.ed_1)) | 0;
    result = imul(result, 31) + (this.fd_1 == null ? 0 : hashCode(this.fd_1)) | 0;
    result = imul(result, 31) + (this.gd_1 == null ? 0 : hashCode(this.gd_1)) | 0;
    result = imul(result, 31) + (this.hd_1 == null ? 0 : this.hd_1.hashCode()) | 0;
    result = imul(result, 31) + (this.id_1 == null ? 0 : hashCode(this.id_1)) | 0;
    result = imul(result, 31) + (this.jd_1 == null ? 0 : getStringHashCode(this.jd_1)) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.kd_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.ld_1) | 0;
    return result;
  };
  protoOf(UniKey).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof UniKey))
      return false;
    if (!(this.yc_1 === other.yc_1))
      return false;
    if (!(this.zc_1 === other.zc_1))
      return false;
    if (!(this.ad_1 === other.ad_1))
      return false;
    if (!(this.bd_1 === other.bd_1))
      return false;
    if (!(this.cd_1 === other.cd_1))
      return false;
    if (!(this.dd_1 == other.dd_1))
      return false;
    if (!(this.ed_1 == other.ed_1))
      return false;
    if (!equals(this.fd_1, other.fd_1))
      return false;
    if (!equals(this.gd_1, other.gd_1))
      return false;
    if (!equals(this.hd_1, other.hd_1))
      return false;
    if (!equals(this.id_1, other.id_1))
      return false;
    if (!(this.jd_1 == other.jd_1))
      return false;
    if (!(this.kd_1 === other.kd_1))
      return false;
    if (!(this.ld_1 === other.ld_1))
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
    this.nd_1 = shift;
    this.od_1 = alt;
    this.pd_1 = ctrl;
  }
  protoOf(Modifiers).toString = function () {
    return 'Modifiers(shift=' + this.nd_1 + ', alt=' + this.od_1 + ', ctrl=' + this.pd_1 + ')';
  };
  protoOf(Modifiers).hashCode = function () {
    var result = getBooleanHashCode(this.nd_1);
    result = imul(result, 31) + getBooleanHashCode(this.od_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.pd_1) | 0;
    return result;
  };
  protoOf(Modifiers).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Modifiers))
      return false;
    if (!(this.nd_1 === other.nd_1))
      return false;
    if (!(this.od_1 === other.od_1))
      return false;
    if (!(this.pd_1 === other.pd_1))
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
  function ConsonantFeatures(place, manner, voiced) {
    this.yd_1 = place;
    this.zd_1 = manner;
    this.ae_1 = voiced;
  }
  protoOf(ConsonantFeatures).toString = function () {
    return 'ConsonantFeatures(place=' + this.yd_1 + ', manner=' + this.zd_1 + ', voiced=' + this.ae_1 + ')';
  };
  protoOf(ConsonantFeatures).hashCode = function () {
    var result = this.yd_1;
    result = imul(result, 31) + this.zd_1 | 0;
    result = imul(result, 31) + getBooleanHashCode(this.ae_1) | 0;
    return result;
  };
  protoOf(ConsonantFeatures).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof ConsonantFeatures))
      return false;
    if (!(this.yd_1 === other.yd_1))
      return false;
    if (!(this.zd_1 === other.zd_1))
      return false;
    if (!(this.ae_1 === other.ae_1))
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
    this.be_1 = ipa;
    this.ce_1 = length;
    this.de_1 = original;
  }
  protoOf(VowelResult).r8 = function () {
    return this.be_1;
  };
  protoOf(VowelResult).s8 = function () {
    return this.ce_1;
  };
  protoOf(VowelResult).ee = function () {
    return this.de_1;
  };
  protoOf(VowelResult).toString = function () {
    return 'VowelResult(ipa=' + this.be_1 + ', length=' + this.ce_1 + ', original=' + this.de_1 + ')';
  };
  protoOf(VowelResult).hashCode = function () {
    var result = getStringHashCode(this.be_1);
    result = imul(result, 31) + this.ce_1 | 0;
    result = imul(result, 31) + getStringHashCode(this.de_1) | 0;
    return result;
  };
  protoOf(VowelResult).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof VowelResult))
      return false;
    if (!(this.be_1 === other.be_1))
      return false;
    if (!(this.ce_1 === other.ce_1))
      return false;
    if (!(this.de_1 === other.de_1))
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
      original.b6(charCodeAt(word, i));
      if (!(vowelIpa == null)) {
        result.a6(vowelIpa);
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
  function consonantDistance($this, c1, c2) {
    var f1 = $this.ge_1.n1(c1);
    var f2 = $this.ge_1.n1(c2);
    if (f1 == null || f2 == null)
      return 10;
    var dist = 0;
    if (!(f1.yd_1 === f2.yd_1))
      dist = dist + imul(abs(f1.yd_1 - f2.yd_1 | 0), 2) | 0;
    if (!(f1.zd_1 === f2.zd_1))
      dist = dist + abs(f1.zd_1 - f2.zd_1 | 0) | 0;
    if (!(f1.ae_1 === f2.ae_1))
      dist = dist + 1 | 0;
    return dist;
  }
  function vowelDistance($this, v1, v2) {
    var tmp0_elvis_lhs = $this.fe_1.n1(v1);
    var h1 = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var tmp1_elvis_lhs = $this.fe_1.n1(v2);
    var h2 = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
    var tmp0 = abs(h1 - h2 | 0);
    // Inline function 'kotlin.math.min' call
    var b = 360 - abs(h1 - h2 | 0) | 0;
    return Math.min(tmp0, b) / 10 | 0;
  }
  function UniKeySyllable$Companion$rhymeKey$lambda(it) {
    return it.he_1 + it.ie_1;
  }
  function computeHue($this) {
    var tmp0_elvis_lhs = Companion_getInstance_4().fe_1.n1($this.ie_1);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      var tmp0 = Companion_getInstance_4().fe_1;
      var tmp1_safe_receiver = firstOrNull($this.ie_1);
      var tmp_0;
      var tmp_1 = tmp1_safe_receiver;
      if ((tmp_1 == null ? null : new Char(tmp_1)) == null) {
        tmp_0 = null;
      } else {
        tmp_0 = toString(tmp1_safe_receiver);
      }
      // Inline function 'kotlin.collections.get' call
      var key = tmp_0;
      tmp = (isInterface(tmp0, KtMap) ? tmp0 : THROW_CCE()).n1(key);
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
    var this_0 = $this.he_1;
    if (charSequenceLength(this_0) === 0)
      return 0;
    var tmp0_elvis_lhs = Companion_getInstance_4().ge_1.n1($this.he_1);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return getStringHashCode($this.he_1) % 30 | 0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var features = tmp;
    var placeOffset = imul(features.yd_1, 5);
    var mannerOffset = imul(features.zd_1, 2);
    var voiceOffset = features.ae_1 ? 0 : 1;
    return ((placeOffset + mannerOffset | 0) + voiceOffset | 0) % 50 | 0;
  }
  function Companion_4() {
    Companion_instance_4 = this;
    this.fe_1 = mapOf([to('a', 0), to('A', 5), to('\u0251', 10), to('\xE6', 15), to('\u025B', 40), to('E', 45), to('e', 50), to('i', 120), to('\u026A', 125), to('I', 120), to('o', 180), to('\u0254', 185), to('O', 180), to('u', 240), to('\u028A', 245), to('U', 240), to('\u0259', 300), to('@', 300), to('\u028C', 310)]);
    this.ge_1 = mapOf([to('p', new ConsonantFeatures(0, 0, false)), to('b', new ConsonantFeatures(0, 0, true)), to('m', new ConsonantFeatures(0, 2, true)), to('f', new ConsonantFeatures(0, 1, false)), to('v', new ConsonantFeatures(0, 1, true)), to('t', new ConsonantFeatures(2, 0, false)), to('d', new ConsonantFeatures(2, 0, true)), to('n', new ConsonantFeatures(2, 2, true)), to('s', new ConsonantFeatures(2, 1, false)), to('z', new ConsonantFeatures(2, 1, true)), to('l', new ConsonantFeatures(2, 3, true)), to('r', new ConsonantFeatures(2, 4, true)), to('ts', new ConsonantFeatures(2, 5, false)), to('\u0283', new ConsonantFeatures(3, 1, false)), to('sh', new ConsonantFeatures(3, 1, false)), to('\u0292', new ConsonantFeatures(3, 1, true)), to('j', new ConsonantFeatures(3, 3, true)), to('y', new ConsonantFeatures(3, 3, true)), to('t\u0283', new ConsonantFeatures(3, 5, false)), to('ch', new ConsonantFeatures(3, 5, false)), to('d\u0292', new ConsonantFeatures(3, 5, true)), to('k', new ConsonantFeatures(4, 0, false)), to('g', new ConsonantFeatures(4, 0, true)), to('x', new ConsonantFeatures(4, 1, false)), to('\u014B', new ConsonantFeatures(4, 2, true)), to('h', new ConsonantFeatures(5, 1, false)), to('\u0294', new ConsonantFeatures(5, 0, false)), to('', new ConsonantFeatures(5, 0, false))]);
  }
  protoOf(Companion_4).le = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[,.\\-;:!?\\s]+$').b7(word, '');
    var i = 0;
    while (i < w.length) {
      var c = charCodeAt(w, i);
      // Inline function 'kotlin.code' call
      var cp = Char__toInt_impl_vasixd(c);
      if (1488 <= cp ? cp <= 1514 : false) {
        var consonantIpa = getHebrewConsonantIpa(this, c, w, i);
        var _destruct__k2r9zo = collectVowels(this, w, i + 1 | 0);
        var vowelIpa = _destruct__k2r9zo.r8();
        var vowelLen = _destruct__k2r9zo.s8();
        var originalVowels = _destruct__k2r9zo.ee();
        syllables.f(new UniKeySyllable(consonantIpa, vowelIpa, toString(c) + originalVowels));
        i = i + (1 + vowelLen | 0) | 0;
      } else {
        i = i + 1 | 0;
      }
    }
    return syllables;
  };
  protoOf(Companion_4).me = function (word) {
    // Inline function 'kotlin.collections.mutableListOf' call
    var syllables = ArrayList_init_$Create$();
    // Inline function 'kotlin.text.lowercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0 = word.toLowerCase();
    // Inline function 'kotlin.text.replace' call
    var w = Regex_init_$Create$('[^a-z]').b7(tmp0, '');
    var i = 0;
    while (i < w.length) {
      var consonantStart = i;
      while (i < w.length && !contains('aeiou', charCodeAt(w, i))) {
        i = i + 1 | 0;
      }
      var consonants = substring(w, consonantStart, i);
      var vowelStart = i;
      while (i < w.length && contains('aeiou', charCodeAt(w, i))) {
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
        syllables.f(new UniKeySyllable(mapEnglishConsonant(this, consonants), mapEnglishVowel(this, vowels), consonants + vowels));
      }
    }
    return syllables;
  };
  protoOf(Companion_4).ne = function (word, isHebrew) {
    var syllables = isHebrew ? this.le(word) : this.me(word);
    var tmp0_safe_receiver = lastOrNull(syllables);
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.oe();
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  };
  protoOf(Companion_4).lc = function (hue, saturation, lightness) {
    return 'hsl(' + hue + ', ' + saturation + '%, ' + lightness + '%)';
  };
  protoOf(Companion_4).pe = function (word, isHebrew) {
    return this.lc(this.ne(word, isHebrew), 80, 72);
  };
  protoOf(Companion_4).qe = function (word, isHebrew, syllableCount) {
    var syllables = isHebrew ? this.le(word) : this.me(word);
    var tmp = takeLast_0(syllables, syllableCount);
    return joinToString(tmp, '-', VOID, VOID, VOID, VOID, UniKeySyllable$Companion$rhymeKey$lambda);
  };
  protoOf(Companion_4).re = function (word, isHebrew, syllableCount, $super) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return $super === VOID ? this.qe(word, isHebrew, syllableCount) : $super.qe.call(this, word, isHebrew, syllableCount);
  };
  protoOf(Companion_4).se = function (word1, isHebrew1, word2, isHebrew2) {
    var key1 = this.re(word1, isHebrew1);
    var key2 = this.re(word2, isHebrew2);
    return key1 === key2;
  };
  protoOf(Companion_4).te = function (word1, isHebrew1, word2, isHebrew2) {
    var syl1 = isHebrew1 ? this.le(word1) : this.me(word1);
    var syl2 = isHebrew2 ? this.le(word2) : this.me(word2);
    if (syl1.m() || syl2.m())
      return 100;
    var last1 = last(syl1);
    var last2 = last(syl2);
    if (last1.he_1 === last2.he_1 && last1.ie_1 === last2.ie_1)
      return 0;
    if (last1.ie_1 === last2.ie_1)
      return 10 + consonantDistance(this, last1.he_1, last2.he_1) | 0;
    return 50 + vowelDistance(this, last1.ie_1, last2.ie_1) | 0;
  };
  var Companion_instance_4;
  function Companion_getInstance_4() {
    if (Companion_instance_4 == null)
      new Companion_4();
    return Companion_instance_4;
  }
  function UniKeySyllable$hue$delegate$lambda(this$0) {
    return function () {
      return computeHue(this$0);
    };
  }
  function UniKeySyllable$_get_hue_$ref_61carx() {
    return function (p0) {
      return p0.oe();
    };
  }
  function UniKeySyllable(consonant, vowel, original) {
    Companion_getInstance_4();
    this.he_1 = consonant;
    this.ie_1 = vowel;
    this.je_1 = original;
    var tmp = this;
    tmp.ke_1 = lazy(UniKeySyllable$hue$delegate$lambda(this));
  }
  protoOf(UniKeySyllable).oe = function () {
    var tmp0 = this.ke_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('hue', 1, tmp, UniKeySyllable$_get_hue_$ref_61carx(), null);
    return tmp0.k1();
  };
  protoOf(UniKeySyllable).toString = function () {
    return 'UniKeySyllable(consonant=' + this.he_1 + ', vowel=' + this.ie_1 + ', original=' + this.je_1 + ')';
  };
  protoOf(UniKeySyllable).hashCode = function () {
    var result = getStringHashCode(this.he_1);
    result = imul(result, 31) + getStringHashCode(this.ie_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.je_1) | 0;
    return result;
  };
  protoOf(UniKeySyllable).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof UniKeySyllable))
      return false;
    if (!(this.he_1 === other.he_1))
      return false;
    if (!(this.ie_1 === other.ie_1))
      return false;
    if (!(this.je_1 === other.je_1))
      return false;
    return true;
  };
  function UniKeys() {
    UniKeys_instance = this;
    this.ue_1 = mapOf([to('t', new UniKey('t', 't', 'T', '\u05D0', '\u0294', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('c', new UniKey('c', 'c', 'C', '\u05D1', 'v', VOID, VOID, VOID, VOID, VOID, VOID, 'b')), to('d', new UniKey('d', 'd', 'D', '\u05D2', 'g')), to('s', new UniKey('s', 's', 'S', '\u05D3', 'd')), to('v', new UniKey('v', 'v', 'V', '\u05D4', 'h', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('u', new UniKey('u', 'u', 'U', '\u05D5', 'v')), to('z', new UniKey('z', 'z', 'Z', '\u05D6', 'z')), to('j', new UniKey('j', 'j', 'J', '\u05D7', '\u0127', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('y', new UniKey('y', 'y', 'Y', '\u05D8', 't')), to('h', new UniKey('h', 'h', 'H', '\u05D9', 'j')), to('f', new UniKey('f', 'f', 'F', '\u05DB', 'x', VOID, VOID, VOID, VOID, VOID, VOID, 'k')), to('l', new UniKey('l', 'l', 'L', '\u05DA', 'x', VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('k', new UniKey('k', 'k', 'K', '\u05DC', 'l')), to('n', new UniKey('n', 'n', 'N', '\u05DE', 'm')), to('o', new UniKey('o', 'o', 'O', '\u05DD', 'm', VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('b', new UniKey('b', 'b', 'B', '\u05E0', 'n')), to('i', new UniKey('i', 'i', 'I', '\u05DF', 'n', VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('x', new UniKey('x', 'x', 'X', '\u05E1', 's')), to('g', new UniKey('g', 'g', 'G', '\u05E2', '\u0295', VOID, VOID, VOID, VOID, VOID, VOID, VOID, true)), to('p', new UniKey('p', 'p', 'P', '\u05E4', 'f', VOID, VOID, VOID, VOID, VOID, VOID, 'p')), to(';', new UniKey(';', ';', ':', '\u05E3', 'f', ':', ':', VOID, VOID, VOID, VOID, VOID, VOID, true)), to('m', new UniKey('m', 'm', 'M', '\u05E6', 'ts')), to('.', new UniKey('.', '.', '>', '\u05E5', 'ts', '>', '>', VOID, VOID, VOID, VOID, VOID, VOID, true)), to('e', new UniKey('e', 'e', 'E', '\u05E7', 'k')), to('r', new UniKey('r', 'r', 'R', '\u05E8', '\u0281')), to('a', new UniKey('a', 'a', 'A', '\u05E9', '\u0283')), to(',', new UniKey(',', ',', '<', '\u05EA', 't', '<', '<')), to('q', new UniKey('q', 'q', 'Q', '/', 'kw')), to('w', new UniKey('w', 'w', 'W', "'", 'w')), to('/', new UniKey('/', '/', '?', '.', VOID, '?', '?')), to("'", new UniKey("'", "'", '"', ',', VOID, '"', '"')), to('`', new UniKey('`', '`', '~', '`', VOID, '~', '~')), to('1', new UniKey('1', '1', '!', '1', VOID, '!', '!')), to('2', new UniKey('2', '2', '@', '2', VOID, '@', '@')), to('3', new UniKey('3', '3', '#', '3', VOID, '#', '#')), to('4', new UniKey('4', '4', '$', '4', VOID, '$', '\u20AA')), to('5', new UniKey('5', '5', '%', '5', VOID, '%', '%')), to('6', new UniKey('6', '6', '^', '6', VOID, '^', '^')), to('7', new UniKey('7', '7', '&', '7', VOID, '&', '&')), to('8', new UniKey('8', '8', '*', '8', VOID, '*', '*')), to('9', new UniKey('9', '9', '(', '9', VOID, '(', ')')), to('0', new UniKey('0', '0', ')', '0', VOID, ')', '(')), to('-', new UniKey('-', '-', '_', '-', VOID, '_', '_')), to('=', new UniKey('=', '=', '+', '=', VOID, '+', '+')), to('[', new UniKey('[', '[', '{', '[', VOID, '{', '{')), to(']', new UniKey(']', ']', '}', ']', VOID, '}', '}')), to('\\', new UniKey('\\', '\\', '|', '\\', VOID, '|', '|'))]);
    var tmp = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0 = this.ue_1.p1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$();
    var _iterator__ex2g4s = tmp0.h();
    while (_iterator__ex2g4s.i()) {
      var element = _iterator__ex2g4s.j();
      if (element.yc_1.length === 1 && isLetter(charCodeAt(element.yc_1, 0))) {
        destination.f(element);
      }
    }
    tmp.ve_1 = destination;
    var tmp_0 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_0 = this.ue_1.p1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_0 = tmp0_0.h();
    while (_iterator__ex2g4s_0.i()) {
      var element_0 = _iterator__ex2g4s_0.j();
      var tmp_1;
      if (element_0.bd_1.length === 1) {
        var containsArg = charCodeAt(element_0.bd_1, 0);
        tmp_1 = _Char___init__impl__6a9atx(1488) <= containsArg ? containsArg <= _Char___init__impl__6a9atx(1514) : false;
      } else {
        tmp_1 = false;
      }
      if (tmp_1) {
        destination_0.f(element_0);
      }
    }
    tmp_0.we_1 = destination_0;
    var tmp_2 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_1 = this.ue_1.p1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_1 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_1 = tmp0_1.h();
    while (_iterator__ex2g4s_1.i()) {
      var element_1 = _iterator__ex2g4s_1.j();
      if (!(element_1.jd_1 == null)) {
        destination_1.f(element_1);
      }
    }
    tmp_2.xe_1 = destination_1;
    var tmp_3 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_2 = this.ue_1.p1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_2 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_2 = tmp0_2.h();
    while (_iterator__ex2g4s_2.i()) {
      var element_2 = _iterator__ex2g4s_2.j();
      if (element_2.kd_1) {
        destination_2.f(element_2);
      }
    }
    tmp_3.ye_1 = destination_2;
    var tmp_4 = this;
    // Inline function 'kotlin.collections.filter' call
    var tmp0_3 = this.ue_1.p1();
    // Inline function 'kotlin.collections.filterTo' call
    var destination_3 = ArrayList_init_$Create$();
    var _iterator__ex2g4s_3 = tmp0_3.h();
    while (_iterator__ex2g4s_3.i()) {
      var element_3 = _iterator__ex2g4s_3.j();
      if (element_3.ld_1) {
        destination_3.f(element_3);
      }
    }
    tmp_4.ze_1 = destination_3;
  }
  protoOf(UniKeys).m7 = function (id) {
    return this.ue_1.n1(id);
  };
  protoOf(UniKeys).af = function (he) {
    // Inline function 'kotlin.collections.find' call
    var tmp0 = this.ue_1.p1();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.h();
      while (_iterator__ex2g4s.i()) {
        var element = _iterator__ex2g4s.j();
        if (element.bd_1 === he) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    return tmp$ret$1;
  };
  protoOf(UniKeys).bf = function (en) {
    // Inline function 'kotlin.collections.find' call
    var tmp0 = this.ue_1.p1();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.h();
      while (_iterator__ex2g4s.i()) {
        var element = _iterator__ex2g4s.j();
        if (element.zc_1 === en) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    return tmp$ret$1;
  };
  protoOf(UniKeys).cf = function (key, mode, mods) {
    var tmp0_elvis_lhs = this.m7(key);
    var tmp1_elvis_lhs = tmp0_elvis_lhs == null ? this.af(key) : tmp0_elvis_lhs;
    var tmp2_elvis_lhs = tmp1_elvis_lhs == null ? this.bf(key) : tmp1_elvis_lhs;
    var tmp;
    if (tmp2_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp2_elvis_lhs;
    }
    var uk = tmp;
    return uk.md(mode, mods);
  };
  var UniKeys_instance;
  function UniKeys_getInstance() {
    if (UniKeys_instance == null)
      new UniKeys();
    return UniKeys_instance;
  }
  function UniKeyMode() {
    UniKeyMode_instance = this;
    this.df_1 = listOf([KeyMode_he_getInstance(), KeyMode_en_getInstance(), KeyMode_EN_getInstance()]);
    this.ef_1 = 0;
  }
  protoOf(UniKeyMode).ff = function () {
    return this.df_1.l(this.ef_1);
  };
  protoOf(UniKeyMode).gf = function (mode) {
    var tmp = this;
    // Inline function 'kotlin.takeIf' call
    var this_0 = this.df_1.h1(mode);
    var tmp_0;
    if (this_0 >= 0) {
      tmp_0 = this_0;
    } else {
      tmp_0 = null;
    }
    var tmp0_elvis_lhs = tmp_0;
    tmp.ef_1 = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
  };
  protoOf(UniKeyMode).hf = function () {
    this.ef_1 = (this.ef_1 + 1 | 0) % this.df_1.k() | 0;
    return this.ff();
  };
  var UniKeyMode_instance;
  function UniKeyMode_getInstance() {
    if (UniKeyMode_instance == null)
      new UniKeyMode();
    return UniKeyMode_instance;
  }
  function UniKeyModifiers() {
    this.if_1 = false;
    this.jf_1 = false;
    this.kf_1 = false;
  }
  protoOf(UniKeyModifiers).lf = function () {
    this.if_1 = false;
    this.jf_1 = false;
    this.kf_1 = false;
  };
  var UniKeyModifiers_instance;
  function UniKeyModifiers_getInstance() {
    return UniKeyModifiers_instance;
  }
  function getUniKey(id) {
    return UniKeys_getInstance().m7(id);
  }
  function getUniKeyByHe(he) {
    return UniKeys_getInstance().af(he);
  }
  function getUniKeyByEn(en) {
    return UniKeys_getInstance().bf(en);
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
    return UniKeys_getInstance().cf(key, keyMode, new Modifiers(shift, alt, ctrl));
  }
  function getUkMode() {
    return UniKeyMode_getInstance().ff().r1_1;
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
    UniKeyMode_getInstance().gf(keyMode);
  }
  function cycleUkMode() {
    return UniKeyMode_getInstance().hf().r1_1;
  }
  function setModifier(mod, value) {
    switch (mod) {
      case 'shift':
        UniKeyModifiers_instance.if_1 = value;
        break;
      case 'alt':
        UniKeyModifiers_instance.jf_1 = value;
        break;
      case 'ctrl':
        UniKeyModifiers_instance.kf_1 = value;
        break;
    }
  }
  function getModifiers() {
    var obj = {};
    obj['shift'] = UniKeyModifiers_instance.if_1;
    obj['alt'] = UniKeyModifiers_instance.jf_1;
    obj['ctrl'] = UniKeyModifiers_instance.kf_1;
    return obj;
  }
  function resetModifiers() {
    return UniKeyModifiers_instance.lf();
  }
  function getNikudForIpa(ipa) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_instance_3.pc(ipa);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var item = _iterator__ex2g4s.j();
      var tmp$ret$0 = item.tc_1;
      destination.f(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function applyNikud(letter, ipa, useMale) {
    useMale = useMale === VOID ? false : useMale;
    return letter.length === 1 ? Companion_instance_3.qc(charCodeAt(letter, 0), ipa, useMale) : letter;
  }
  function getHebrewLetterInfo(letter) {
    if (!(letter.length === 1))
      return null;
    var tmp0_elvis_lhs = Companion_getInstance_0().ja(charCodeAt(letter, 0));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var heLetter = tmp;
    var obj = {};
    obj['letter'] = toString(heLetter.ra_1);
    obj['ipa'] = heLetter.sa_1;
    obj['ipaDagesh'] = heLetter.ta_1;
    obj['name'] = heLetter.ua_1;
    obj['type'] = heLetter.va_1.r1_1;
    obj['en'] = heLetter.wa_1;
    obj['isGuttural'] = heLetter.db();
    obj['isBgdkpt'] = heLetter.eb();
    obj['isFinal'] = heLetter.cb();
    return obj;
  }
  function isBgdkpt(letter) {
    return letter.length === 1 && Companion_getInstance().ka(charCodeAt(letter, 0));
  }
  function getBgdkptSound(letter, hasDagesh, useClassical) {
    useClassical = useClassical === VOID ? false : useClassical;
    if (!(letter.length === 1))
      return null;
    var tmp0_elvis_lhs = Companion_getInstance().ja(charCodeAt(letter, 0));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bgdkpt = tmp;
    var sound = bgdkpt.la(hasDagesh, useClassical);
    var obj = {};
    obj['letter'] = sound.ma_1;
    obj['ipa'] = sound.na_1;
    obj['en'] = sound.oa_1;
    return obj;
  }
  function getAllKeys() {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = UniKeys_getInstance().ue_1.o1();
    return copyToArray(this_0);
  }
  function getLetterKeys() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = UniKeys_getInstance().ve_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var item = _iterator__ex2g4s.j();
      var tmp$ret$0 = item.yc_1;
      destination.f(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getHebrewKeys() {
    // Inline function 'kotlin.collections.map' call
    var this_0 = UniKeys_getInstance().we_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var item = _iterator__ex2g4s.j();
      var tmp$ret$0 = item.yc_1;
      destination.f(tmp$ret$0);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function getIpaVowelInfo(ipa) {
    var tmp0_elvis_lhs = Companion_getInstance_1().pb(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var vowel = tmp;
    var obj = {};
    obj['ipa'] = vowel.hb_1;
    obj['name'] = vowel.ib_1;
    obj['heNikud'] = vowel.jb_1;
    obj['heName'] = vowel.kb_1;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = vowel.lb_1;
    obj['enSpellings'] = copyToArray(this_0);
    obj['quality'] = vowel.mb_1.r1_1;
    obj['warning'] = vowel.nb_1;
    return obj;
  }
  function getIpaConsonantInfo(ipa) {
    var tmp0_elvis_lhs = Companion_getInstance_2().pb(ipa);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var consonant = tmp;
    var obj = {};
    obj['ipa'] = consonant.sb_1;
    obj['name'] = consonant.tb_1;
    obj['he'] = consonant.ub_1;
    obj['en'] = consonant.vb_1;
    obj['geresh'] = consonant.wb_1;
    obj['heUnique'] = consonant.xb_1;
    obj['warning'] = consonant.yb_1;
    return obj;
  }
  function heIpa(word) {
    return IpaColor_getInstance().ic(word);
  }
  function enIpa(word) {
    return IpaColor_getInstance().hc(word);
  }
  function lineEndIpa(line, isHebrew) {
    return IpaColor_getInstance().jc(line, isHebrew);
  }
  function ipaHue(ipa) {
    return IpaColor_getInstance().kc(ipa);
  }
  function hsl(hue, saturation, lightness) {
    return IpaColor_getInstance().lc(hue, saturation, lightness);
  }
  function ipaEndColor(ipa) {
    return IpaColor_getInstance().mc(ipa);
  }
  function ipaMidColor(ipa) {
    return IpaColor_getInstance().nc(ipa);
  }
  function rhymeScheme(ipas) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = IpaColor_getInstance().oc(toList(ipas));
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var item = _iterator__ex2g4s.j();
      var obj = {};
      obj['ch'] = toString(item.ac_1);
      obj['ipa'] = item.bc_1;
      obj['hue'] = item.cc_1;
      destination.f(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseHebrewSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_4().le(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var item = _iterator__ex2g4s.j();
      var obj = {};
      obj['consonant'] = item.he_1;
      obj['vowel'] = item.ie_1;
      obj['original'] = item.je_1;
      obj['hue'] = item.oe();
      destination.f(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function parseEnglishSyllables(word) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = Companion_getInstance_4().me(word);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.h();
    while (_iterator__ex2g4s.i()) {
      var item = _iterator__ex2g4s.j();
      var obj = {};
      obj['consonant'] = item.he_1;
      obj['vowel'] = item.ie_1;
      obj['original'] = item.je_1;
      obj['hue'] = item.oe();
      destination.f(obj);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    return copyToArray(destination);
  }
  function wordHue(word, isHebrew) {
    return Companion_getInstance_4().ne(word, isHebrew);
  }
  function wordEndColor(word, isHebrew) {
    return Companion_getInstance_4().pe(word, isHebrew);
  }
  function syllableHsl(hue, saturation, lightness) {
    saturation = saturation === VOID ? 70 : saturation;
    lightness = lightness === VOID ? 65 : lightness;
    return Companion_getInstance_4().lc(hue, saturation, lightness);
  }
  function rhymeKey(word, isHebrew, syllableCount) {
    syllableCount = syllableCount === VOID ? 1 : syllableCount;
    return Companion_getInstance_4().qe(word, isHebrew, syllableCount);
  }
  function rhymes(word1, isHebrew1, word2, isHebrew2) {
    return Companion_getInstance_4().se(word1, isHebrew1, word2, isHebrew2);
  }
  function rhymeDistance(word1, isHebrew1, word2, isHebrew2) {
    return Companion_getInstance_4().te(word1, isHebrew1, word2, isHebrew2);
  }
  //region block: init
  Companion_instance_3 = new Companion_3();
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
    $org$pocketworkstation$unikey.parseHebrewSyllables = parseHebrewSyllables;
    $org$pocketworkstation$unikey.parseEnglishSyllables = parseEnglishSyllables;
    $org$pocketworkstation$unikey.wordHue = wordHue;
    $org$pocketworkstation$unikey.wordEndColor = wordEndColor;
    $org$pocketworkstation$unikey.syllableHsl = syllableHsl;
    $org$pocketworkstation$unikey.rhymeKey = rhymeKey;
    $org$pocketworkstation$unikey.rhymes = rhymes;
    $org$pocketworkstation$unikey.rhymeDistance = rhymeDistance;
  }
  $jsExportAll$(_);
  //endregion
  return _;
}));

//# sourceMappingURL=hackerskeyboard-unikey-kmp.js.map
