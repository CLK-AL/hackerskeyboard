// ═══════════════════════════════════════════════════════════════════════════════
// UNIKEY V4 - COMPLETE PHONETICS ENGINE
// ═══════════════════════════════════════════════════════════════════════════════
// Merged module with:
// - Bidirectional IPA mapping (Hebrew ↔ IPA ↔ English)
// - Vowel approximations with quality indicators
// - Silent/soft letters for both languages
// - Unique sounds with geresh marking
// - Dagesh, mappiq, and guttural distinctions
// ═══════════════════════════════════════════════════════════════════════════════

class UniKeyPhonetics {
  
  // ═══════════════════════════════════════════════════════════════════════════
  // STATIC DATA
  // ═══════════════════════════════════════════════════════════════════════════
  
  // === VOWELS ===
  static VOWELS = {
    // Exact matches (התאמה מדויקת)
    exact: {
      'a': { he: 'ַ', name: 'פַּתַח', en: ['a'], ex: { he: 'אַב', en: 'father' } },
      'i': { he: 'ִ', name: 'חִירִיק', male: 'ִי', en: ['ee', 'ea'], ex: { he: 'מִי', en: 'see' } },
      'u': { he: 'ֻ', name: 'קֻבּוּץ', male: 'וּ', en: ['oo', 'u'], ex: { he: 'הוּא', en: 'moon' } },
      'e': { he: 'ֵ', name: 'צֵירֵי', male: 'ֵי', en: ['ay', 'ai'], ex: { he: 'בֵּית', en: 'say' } },
      'o': { he: 'ֹ', name: 'חוֹלָם', male: 'וֹ', en: ['o', 'oa'], ex: { he: 'לֹא', en: 'go' } },
      'ɛ': { he: 'ֶ', name: 'סֶגוֹל', male: 'ֶא', en: ['e'], ex: { he: 'אֶת', en: 'bed' } },
      'ə': { he: 'ְ', name: 'שְׁוָא נָע', en: ['a', 'e'], ex: { he: 'בְּ', en: 'about' } },
    },
    
    // Approximations (קירוב - לא מדויק!)
    approx: {
      'æ': { he: 'ֶ', alt: 'ַ', name: 'סֶגוֹל (קירוב)', en: ['a'], ex: 'cat', warning: '⚠️' },
      'ʌ': { he: 'ַ', name: 'פַּתַח (קירוב)', en: ['u', 'o'], ex: 'cup', warning: '⚠️' },
      'ɒ': { he: 'ָ', alt: 'ֹ', name: 'קָמָץ קָטָן (קירוב)', en: ['o'], ex: 'hot', warning: '⚠️' },
      'ɪ': { he: 'ִ', name: 'חִירִיק (קירוב)', en: ['i'], ex: 'sit', warning: '⚠️' },
      'ʊ': { he: 'ֻ', name: 'קֻבּוּץ (קירוב)', en: ['oo', 'u'], ex: 'book', warning: '⚠️' },
      'ɜː': { he: 'ֶ', alt: 'ְ', name: 'סֶגוֹל (קירוב גס)', en: ['er', 'ir', 'ur'], ex: 'bird', warning: '⚠️⚠️' },
    },
    
    // Hataf (חטפים - לגרוניות)
    hataf: {
      'ֲ': { ipa: 'a', name: 'חֲטַף פַּתַח', for: 'gutturals' },
      'ֱ': { ipa: 'ɛ', name: 'חֲטַף סֶגוֹל', for: 'gutturals' },
      'ֳ': { ipa: 'o', name: 'חֲטַף קָמָץ', for: 'gutturals' },
    },
    
    // Diphthongs (תנועות כפולות)
    diphthongs: {
      'aɪ': { he: 'ַי', he_ipa: 'aj', en: ['i', 'y', 'igh'], ex: { he: 'בַּיִת', en: 'my' } },
      'eɪ': { he: 'ֵי', he_ipa: 'ej', en: ['a', 'ay', 'ai'], ex: { he: 'בֵּית', en: 'day' } },
      'ɔɪ': { he: 'וֹי', he_ipa: 'oj', en: ['oi', 'oy'], ex: { he: 'גּוֹי', en: 'boy' } },
      'aʊ': { he: 'ָאוּ', he_ipa: 'aw', en: ['ou', 'ow'], ex: { he: 'תָּו', en: 'now' } },
      'oʊ': { he: 'וֹ', en: ['o', 'ow', 'oa'], ex: { he: 'לֹא', en: 'go' }, approx: true },
    },
  };
  
  // === CONSONANTS ===
  static CONSONANTS = {
    // בג"ד כפ"ת - affected by dagesh
    bgdkpt: {
      'ב': { withDagesh: { letter: 'בּ', ipa: 'b', en: 'b' }, without: { letter: 'ב', ipa: 'v', en: 'v' } },
      'ג': { withDagesh: { letter: 'גּ', ipa: 'g', en: 'g' }, without: { letter: 'ג', ipa: 'g', en: 'g' }, note: 'modern same' },
      'ד': { withDagesh: { letter: 'דּ', ipa: 'd', en: 'd' }, without: { letter: 'ד', ipa: 'd', en: 'd' }, classical: 'ð' },
      'כ': { withDagesh: { letter: 'כּ', ipa: 'k', en: 'k' }, without: { letter: 'כ', ipa: 'x', en: "kh'" } },
      'פ': { withDagesh: { letter: 'פּ', ipa: 'p', en: 'p' }, without: { letter: 'פ', ipa: 'f', en: 'f' } },
      'ת': { withDagesh: { letter: 'תּ', ipa: 't', en: 't' }, without: { letter: 'ת', ipa: 't', en: 't' }, classical: 'θ' },
    },
    
    // Gutturals (גרוניות)
    gutturals: {
      'א': { ipa: 'ʔ', en: "'", name: 'אָלֶף', silent_end: true },
      'ה': { ipa: 'h', en: 'h', name: 'הֵא', silent_end: true, mappiq: 'הּ' },
      'ח': { ipa: 'ħ', ipa_modern: 'x', en: "ḥ'", alt: "h'", name: 'חֵית', type: 'pharyngeal' },
      'ע': { ipa: 'ʕ', ipa_modern: 'ʔ', en: "'", name: 'עַיִן', type: 'pharyngeal' },
    },
    
    // Unique Hebrew sounds
    unique_he: {
      'צ': { ipa: 'ts', en: "ts'", name: 'צָדִי' },
      'ק': { ipa: 'k', en: "k'", name: 'קוֹף', note: 'emphatic' },
      'ר': { ipa: 'ʁ', en: "r'", name: 'רֵישׁ', note: 'guttural R' },
      'שׁ': { ipa: 'ʃ', en: 'sh', name: 'שִׁין' },
      'שׂ': { ipa: 's', en: 's', name: 'שִׂין' },
    },
    
    // Unique English sounds (need geresh in Hebrew)
    unique_en: {
      'θ': { en: 'th', he: 'ת׳', ex: ['think', 'three', 'bath'], warning: '⚠️ NO Hebrew equivalent!' },
      'ð': { en: 'th', he: 'ד׳', ex: ['the', 'this', 'that'], warning: '⚠️ NO Hebrew equivalent!' },
      'tʃ': { en: 'ch', he: 'צ׳', ex: ['chair', 'cheese', 'watch'] },
      'dʒ': { en: 'j', he: 'ג׳', ex: ['jam', 'job', 'judge'] },
      'w': { en: 'w', he: 'ו׳', alt: 'וו', ex: ['water', 'want', 'with'], note: 'W ≠ V!' },
      'ŋ': { en: 'ng', he: 'נג', ex: ['sing', 'ring', 'long'] },
      'ɹ': { en: 'r', he: 'ר׳', ex: ['red', 'run', 'car'], note: 'alveolar R' },
    },
    
    // Regular consonants
    regular: {
      'ז': { ipa: 'z', en: 'z' },
      'ט': { ipa: 't', en: 't', note: 'emphatic' },
      'י': { ipa: 'j', en: 'y' },
      'ל': { ipa: 'l', en: 'l' },
      'מ': { ipa: 'm', en: 'm' },
      'נ': { ipa: 'n', en: 'n' },
      'ס': { ipa: 's', en: 's' },
    },
  };
  
  // === SILENT LETTERS ===
  static SILENT = {
    // Hebrew mater lectionis (אותיות אֵם קְרִיאָה)
    hebrew: {
      'א': { silent_when: 'end of word', ex: ['הוּא', 'קָרָא', 'מָצָא'] },
      'ה': { silent_when: 'end suffix', ex: ['יָפָה', 'גְּדוֹלָה'], mappiq_voiced: 'הּ' },
      'ו': { silent_when: 'vowel marker (וֹ/וּ)', ex: ['אוֹר', 'שׁוּק'] },
      'י': { silent_when: 'vowel marker (ִי/ֵי)', ex: ['שִׁיר', 'בֵּית'] },
      'shva_nach': { mark: 'ְ', ex: ['מֶלֶךְ', 'כָּתְבָה'] },
    },
    
    // English silent patterns
    english: {
      'kn': { silent: 'k', ex: ['knight', 'knife', 'know'], he: ['נַייט', 'נַייף', 'נוֹ'] },
      'gn': { silent: 'g', ex: ['gnome', 'sign'], he: ['נוֹם', 'סַיין'] },
      'wr': { silent: 'w', ex: ['write', 'wrong'], he: ['רַייט', 'רוֹנג'] },
      'mb': { silent: 'b', ex: ['lamb', 'climb'], he: ['לֶם', 'קְלַיים'] },
      'bt': { silent: 'b', ex: ['debt', 'doubt'], he: ['דֶט', 'דַאוּט'] },
      'igh': { silent: 'gh', sound: 'aɪ', ex: ['night', 'light', 'high'], he: ['נַייט', 'לַייט', 'הַיי'] },
      'ough': { silent: 'gh', ex: ['though', 'through', 'thought'], he: ['ד׳וֹ', 'ת׳רוּ', 'ת׳וֹט'] },
      'alk': { silent: 'l', ex: ['walk', 'talk'], he: ['ווֹק', 'טוֹק'] },
      'alm': { silent: 'l', ex: ['calm', 'palm', 'psalm'], he: ['קָאם', 'פָּאם', 'סָאם'] },
      'ps': { silent: 'p', ex: ['psychology', 'psalm'], he: ['סַייקוֹלוֹג׳י', 'סָאם'] },
      'sten': { silent: 't', ex: ['listen', 'fasten'], he: ['לִיסֶן', 'פֶסֶן'] },
      'stle': { silent: 't', ex: ['castle', 'whistle'], he: ['קֶסֶל', 'ווִיסֶל'] },
    },
  };
  
  // === MAPPIQ ===
  static MAPPIQ = {
    letter: 'הּ',
    ipa: 'h',
    rule: 'Pronounced final ה (possessives, construct)',
    examples: {
      with: [
        { he: 'שֶׁלָּהּ', en: "shela'", meaning: 'hers' },
        { he: 'גָּבֹהַּ', en: "gavoah'", meaning: 'high' },
        { he: 'בֵּיתָהּ', en: "beta'", meaning: 'her house' },
      ],
      without: [
        { he: 'שֶׁלָּה', en: 'shela', meaning: 'there (direction)' },
        { he: 'יָפָה', en: 'yafa', meaning: 'beautiful' },
      ],
    },
  };
  
  // === TRANSCRIPTION RULES ===
  static TRANSCRIPTION = {
    // Hebrew → English
    he_to_en: {
      // בג"ד כפ"ת with dagesh
      'בּ': 'b', 'ב': 'v',
      'כּ': 'k', 'כ': "kh'", 'ךּ': 'k', 'ך': "kh'",
      'פּ': 'p', 'פ': 'f', 'ףּ': 'p', 'ף': 'f',
      // Gutturals
      'ח': "ḥ'", 'ע': "'", 'א': "'",
      'הּ': "'",  // mappiq
      // Unique
      'צ': "ts'", 'ק': "k'", 'ר': "r'",
      'שׁ': 'sh', 'שׂ': 's',
      // Regular
      'ז': 'z', 'ט': 't', 'י': 'y', 'ל': 'l',
      'מ': 'm', 'נ': 'n', 'ס': 's', 'ת': 't', 'ד': 'd', 'ג': 'g',
    },
    
    // English → Hebrew (with geresh for unique sounds)
    en_to_he: {
      'th_voiced': 'ד׳',   // the, this
      'th_voiceless': 'ת׳', // think, three
      'ch': 'צ׳',           // chair, cheese
      'j': 'ג׳',            // jam, job
      'w': 'וו',            // water, want
      'ng': 'נג',           // sing, ring
    },
  };
  
  // ═══════════════════════════════════════════════════════════════════════════
  // API METHODS
  // ═══════════════════════════════════════════════════════════════════════════
  
  /**
   * Get Hebrew nikud for IPA vowel
   */
  static getNikudForIPA(ipa, options = {}) {
    // Check exact matches first
    if (this.VOWELS.exact[ipa]) {
      const v = this.VOWELS.exact[ipa];
      return {
        mark: options.male && v.male ? v.male : v.he,
        name: v.name,
        quality: 'exact',
      };
    }
    
    // Check approximations
    if (this.VOWELS.approx[ipa]) {
      const v = this.VOWELS.approx[ipa];
      return {
        mark: v.he,
        alt: v.alt,
        name: v.name,
        quality: 'approximate',
        warning: v.warning,
      };
    }
    
    // Check diphthongs
    if (this.VOWELS.diphthongs[ipa]) {
      const v = this.VOWELS.diphthongs[ipa];
      return {
        mark: v.he,
        name: `דיפתונג ${ipa}`,
        quality: v.approx ? 'approximate' : 'near-exact',
      };
    }
    
    return null;
  }
  
  /**
   * Get IPA for Hebrew letter/nikud
   */
  static getIPAForHebrew(char) {
    // Check vowels
    for (const [ipa, v] of Object.entries(this.VOWELS.exact)) {
      if (v.he === char || v.male === char) return ipa;
    }
    
    // Check hataf
    if (this.VOWELS.hataf[char]) return this.VOWELS.hataf[char].ipa;
    
    // Check consonants
    for (const group of Object.values(this.CONSONANTS)) {
      for (const [letter, info] of Object.entries(group)) {
        if (letter === char) return info.ipa;
        if (info.withDagesh?.letter === char) return info.withDagesh.ipa;
        if (info.without?.letter === char) return info.without.ipa;
      }
    }
    
    return null;
  }
  
  /**
   * Transcribe Hebrew to English
   */
  static transcribeHeToEn(text) {
    let result = '';
    const chars = [...text];
    
    for (let i = 0; i < chars.length; i++) {
      const char = chars[i];
      const next = chars[i + 1];
      
      // Check for dagesh
      if (next === 'ּ') {
        const base = char;
        if (this.CONSONANTS.bgdkpt[base]) {
          result += this.CONSONANTS.bgdkpt[base].withDagesh.en;
          i++; // skip dagesh
          continue;
        }
      }
      
      // Check transcription rules
      if (this.TRANSCRIPTION.he_to_en[char]) {
        result += this.TRANSCRIPTION.he_to_en[char];
        continue;
      }
      
      // Check bgdkpt without dagesh
      if (this.CONSONANTS.bgdkpt[char]) {
        result += this.CONSONANTS.bgdkpt[char].without.en;
        continue;
      }
      
      // Vowels
      const vowelIpa = this.getIPAForHebrew(char);
      if (vowelIpa) {
        result += vowelIpa;
        continue;
      }
      
      result += char;
    }
    
    return result;
  }
  
  /**
   * Check if a sound needs geresh marking
   */
  static needsGeresh(ipa, direction) {
    if (direction === 'he_to_en') {
      return ['ts', 'x', 'ħ', 'ʕ', 'ʁ', 'q'].includes(ipa);
    }
    if (direction === 'en_to_he') {
      return ['θ', 'ð', 'tʃ', 'dʒ', 'w', 'ɹ'].includes(ipa);
    }
    return false;
  }
  
  /**
   * Get Hebrew transcription for English word pattern
   */
  static getHebrewForEnglish(pattern) {
    // Check silent letter patterns
    for (const [pat, info] of Object.entries(this.SILENT.english)) {
      if (pattern.includes(pat) && info.he) {
        return { pattern: pat, he: info.he, silent: info.silent };
      }
    }
    
    // Check unique sounds
    if (this.CONSONANTS.unique_en[pattern]) {
      return this.CONSONANTS.unique_en[pattern];
    }
    
    return null;
  }
  
  /**
   * Check if letter is guttural (for hataf vowels)
   */
  static isGuttural(letter) {
    return ['א', 'ה', 'ח', 'ע'].includes(letter);
  }
  
  /**
   * Get hataf vowel for guttural letter
   */
  static getHatafFor(letter, baseVowel) {
    if (!this.isGuttural(letter)) return null;
    
    const hatafMap = {
      'a': 'ֲ',
      'ɛ': 'ֱ',
      'o': 'ֳ',
    };
    
    return hatafMap[baseVowel] || 'ֲ';
  }
  
  /**
   * Apply nikud to letter
   */
  static applyNikud(letter, ipa, options = {}) {
    const nikud = this.getNikudForIPA(ipa, options);
    if (!nikud) return letter;
    
    // Use hataf for gutturals with shva-like vowels
    if (this.isGuttural(letter) && ipa === 'ə') {
      return letter + 'ֲ';  // default hataf patach
    }
    
    return letter + nikud.mark;
  }
  
  /**
   * Get complete info for a sound
   */
  static getSoundInfo(char) {
    return {
      vowel: this.getNikudForIPA(char),
      consonant: this.getIPAForHebrew(char),
      isGuttural: this.isGuttural(char),
      needsGeresh: this.needsGeresh(char, 'en_to_he'),
      silent: this.SILENT.hebrew[char] || null,
    };
  }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TESTS
// ═══════════════════════════════════════════════════════════════════════════════

console.log('═══════════════════════════════════════════════════════════════════════════════════');
console.log('UNIKEY V4 - PHONETICS ENGINE');
console.log('═══════════════════════════════════════════════════════════════════════════════════\n');

console.log('VOWEL TESTS:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
const vowelTests = ['a', 'i', 'u', 'æ', 'ʌ', 'aɪ', 'θ'];
vowelTests.forEach(ipa => {
  const result = UniKeyPhonetics.getNikudForIPA(ipa);
  if (result) {
    console.log(`/${ipa}/ → ${result.mark} (${result.name}) [${result.quality}] ${result.warning || ''}`);
  } else {
    console.log(`/${ipa}/ → (not a vowel)`);
  }
});

console.log('\nCONSONANT TESTS:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('בּ (with dagesh):', UniKeyPhonetics.CONSONANTS.bgdkpt['ב'].withDagesh);
console.log('ב (without dagesh):', UniKeyPhonetics.CONSONANTS.bgdkpt['ב'].without);
console.log('כּ (with dagesh):', UniKeyPhonetics.CONSONANTS.bgdkpt['כ'].withDagesh);
console.log('כ (without dagesh):', UniKeyPhonetics.CONSONANTS.bgdkpt['כ'].without);

console.log('\nUNIQUE SOUNDS:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('Hebrew צ →', UniKeyPhonetics.CONSONANTS.unique_he['צ']);
console.log('Hebrew ח →', UniKeyPhonetics.CONSONANTS.gutturals['ח']);
console.log('English th /θ/ →', UniKeyPhonetics.CONSONANTS.unique_en['θ']);
console.log('English th /ð/ →', UniKeyPhonetics.CONSONANTS.unique_en['ð']);
console.log('English ch →', UniKeyPhonetics.CONSONANTS.unique_en['tʃ']);

console.log('\nSILENT LETTER PATTERNS:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('kn-:', UniKeyPhonetics.SILENT.english['kn']);
console.log('igh:', UniKeyPhonetics.SILENT.english['igh']);
console.log('ough:', UniKeyPhonetics.SILENT.english['ough']);

console.log('\nMAPPIQ EXAMPLES:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
UniKeyPhonetics.MAPPIQ.examples.with.forEach(ex => {
  console.log(`${ex.he} → ${ex.en} (${ex.meaning})`);
});

console.log('\nAPPLY NIKUD:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('ב + /a/ →', UniKeyPhonetics.applyNikud('ב', 'a'));
console.log('ב + /i/ →', UniKeyPhonetics.applyNikud('ב', 'i'));
console.log('ב + /u/ →', UniKeyPhonetics.applyNikud('ב', 'u'));
console.log('ח + /ə/ →', UniKeyPhonetics.applyNikud('ח', 'ə'));  // Should use hataf

console.log('\n═══════════════════════════════════════════════════════════════════════════════════');
console.log('TRANSCRIPTION TABLE:');
console.log('═══════════════════════════════════════════════════════════════════════════════════\n');

const transcriptionExamples = [
  { dir: 'HE→EN', from: 'כֵּן', to: 'ken', note: 'כּ = k' },
  { dir: 'HE→EN', from: 'מֶלֶךְ', to: "melekh'", note: 'ך = kh\'' },
  { dir: 'HE→EN', from: 'חֵן', to: "ḥ'en", note: 'ח = ḥ\' (guttural)' },
  { dir: 'HE→EN', from: 'צִפּוֹר', to: "ts'ipor", note: 'צ = ts\'' },
  { dir: 'HE→EN', from: 'שֶׁלָּהּ', to: "shela'", note: 'הּ = \' (mappiq)' },
  { dir: 'EN→HE', from: 'child', to: 'צ׳ַיילד', note: 'ch = צ׳' },
  { dir: 'EN→HE', from: 'the', to: 'ד׳ָה', note: 'th = ד׳' },
  { dir: 'EN→HE', from: 'think', to: 'ת׳ִינק', note: 'th = ת׳' },
  { dir: 'EN→HE', from: 'knight', to: 'נַייט', note: 'kn- → n (k silent)' },
  { dir: 'EN→HE', from: 'night', to: 'נַייט', note: '-igh → /aɪ/ (gh silent)' },
];

console.log('Dir      From         To           Note');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
transcriptionExamples.forEach(ex => {
  console.log(`${ex.dir.padEnd(8)} ${ex.from.padEnd(12)} ${ex.to.padEnd(12)} ${ex.note}`);
});

// Export
if (typeof module !== 'undefined' && module.exports) {
  module.exports = UniKeyPhonetics;
}
