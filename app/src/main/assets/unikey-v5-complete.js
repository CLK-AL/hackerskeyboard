// ═══════════════════════════════════════════════════════════════════════════════
// UNIKEY V5 - COMPLETE MERGED FROM unikeyboard.html + V5 ADDITIONS
// ═══════════════════════════════════════════════════════════════════════════════
//
// BASE: unikeyboard.html (13,960 lines)
// ADDITIONS: Quality indicators, Geresh marking, Silent letters, Mappiq
//
// ═══════════════════════════════════════════════════════════════════════════════

// ═══════════════════════════════════════════════════════════════════════════════
// UNIKEY CLASS (from HTML)
// ═══════════════════════════════════════════════════════════════════════════════

class UniKey {
  constructor(data) { Object.assign(this, data); }
  
  label(mode, mods = {}) {
    const { shift, alt, ctrl } = mods;
    if (ctrl) return this.ipa || '';
    if (alt && this.mg) {
      const mf = shift;
      if (mode === 'en' || mode === 'EN') return mf ? this.mg.fm_en : this.mg.mf_en;
      return mf ? this.mg.fm : this.mg.mf;
    }
    if (shift && !alt) {
      if (mode === 'he') {
        if (this.syl && this.syl.length > 0) return this.he + '·';
        return this.he;
      }
      if ((mode === 'en' || mode === 'EN') && this.enSyl && this.enSyl.length > 0) return this.en + '·';
      if (mode === 'en') return this.EN || this.en?.toUpperCase() || this.shift;
      if (mode === 'EN') return this.shift || this.EN;
    }
    if (mode === 'en') return this.en;
    if (mode === 'EN') return this.EN || this.en?.toUpperCase();
    if (mode === 'he') return this.he;
    return this.en;
  }
  
  getSyllables(mode) {
    if (mode === 'he') return this.syl || [];
    if (mode === 'en' || mode === 'EN') return this.enSyl || [];
    return [];
  }
  
  getMg(mode, femaleFirst = false) {
    if (!this.mg) return null;
    const isEn = mode === 'en' || mode === 'EN';
    return {
      display: femaleFirst ? (isEn ? this.mg.fm_en : this.mg.fm) : (isEn ? this.mg.mf_en : this.mg.mf),
      male: isEn ? this.mg.m_en : this.mg.m,
      female: isEn ? this.mg.f_en : this.mg.f,
    };
  }
}

// ═══════════════════════════════════════════════════════════════════════════════
// NIKUD (from HTML)
// ═══════════════════════════════════════════════════════════════════════════════

const NIKUD = {
  // תנועות גדולות
  kamatz:   { n: 'ָ', name: 'קָמָץ', ipa: 'a', ipa_alt: 'o' },
  patach:   { n: 'ַ', name: 'פַּתַח', ipa: 'a' },
  tzere:    { n: 'ֵ', name: 'צֵירֵי', ipa: 'e' },
  segol:    { n: 'ֶ', name: 'סֶגוֹל', ipa: 'ɛ' },
  chirik:   { n: 'ִ', name: 'חִירִיק', ipa: 'i' },
  cholam:   { n: 'ֹ', name: 'חוֹלָם', ipa: 'o' },
  kubutz:   { n: 'ֻ', name: 'קֻבּוּץ', ipa: 'u' },
  
  // תנועות עם אם קריאה
  chirik_m: { n: 'ִי', name: 'חִירִיק מָלֵא', ipa: 'i' },
  cholam_m: { n: 'וֹ', name: 'חוֹלָם מָלֵא', ipa: 'o' },
  shuruk:   { n: 'וּ', name: 'שׁוּרוּק', ipa: 'u' },
  
  // שווא וחטפים
  shva:     { n: 'ְ', name: 'שְׁוָא', ipa: 'ə', ipa_silent: '' },
  hataf_a:  { n: 'ֲ', name: 'חֲטַף פַּתַח', ipa: 'a' },
  hataf_e:  { n: 'ֱ', name: 'חֲטַף סֶגוֹל', ipa: 'ɛ' },
  hataf_o:  { n: 'ֳ', name: 'חֲטַף קָמָץ', ipa: 'o' },
  
  // דגש ונקודות
  dagesh:   { n: 'ּ', name: 'דָּגֵשׁ', mod: 'double/hard' },
  shin_dot: { n: 'ׁ', name: 'נְקֻדַּת שִׁין', ipa: 'ʃ' },
  sin_dot:  { n: 'ׂ', name: 'נְקֻדַּת שִׂין', ipa: 's' },
  rafe:     { n: 'ֿ', name: 'רָפֶה', mod: 'soft' },
  
  // V5 ADDITION: מַפִּיק
  mappiq:   { n: 'ּ', name: 'מַפִּיק', mod: 'pronounced ה', note: 'הּ' },
};

// ═══════════════════════════════════════════════════════════════════════════════
// HEBREW LETTERS (from HTML + V5 additions)
// ═══════════════════════════════════════════════════════════════════════════════

const HE_LETTERS = {
  // Regular letters with IPA
  'א': { ipa: 'ʔ', name: 'אָלֶף', type: 'guttural', silent_end: true, en: "'" },
  'ב': { ipa: 'v', ipa_dagesh: 'b', name: 'בֵּית', type: 'bgdkpt', en: 'v', en_dagesh: 'b' },
  'ג': { ipa: 'g', name: 'גִּימֶל', type: 'bgdkpt', en: 'g', classical: 'ɣ' },
  'ד': { ipa: 'd', name: 'דָּלֶת', type: 'bgdkpt', en: 'd', classical: 'ð' },
  'ה': { ipa: 'h', name: 'הֵא', type: 'guttural', silent_end: true, en: 'h', mappiq: 'הּ' },
  'ו': { ipa: 'v', name: 'וָו', type: 'weak', en: 'v', vowel_marker: true },
  'ז': { ipa: 'z', name: 'זַיִן', en: 'z' },
  'ח': { ipa: 'ħ', ipa_modern: 'x', name: 'חֵית', type: 'guttural', en: "ḥ'", en_alt: "ch'" },
  'ט': { ipa: 't', name: 'טֵית', type: 'emphatic', en: 't' },
  'י': { ipa: 'j', name: 'יוֹד', type: 'weak', en: 'y', vowel_marker: true },
  'כ': { ipa: 'x', ipa_dagesh: 'k', name: 'כַּף', type: 'bgdkpt', en: "kh'", en_dagesh: 'k', final: 'ך' },
  'ל': { ipa: 'l', name: 'לָמֶד', en: 'l' },
  'מ': { ipa: 'm', name: 'מֵם', en: 'm', final: 'ם' },
  'נ': { ipa: 'n', name: 'נוּן', en: 'n', final: 'ן' },
  'ס': { ipa: 's', name: 'סָמֶך', en: 's' },
  'ע': { ipa: 'ʕ', ipa_modern: 'ʔ', name: 'עַיִן', type: 'guttural', en: "'" },
  'פ': { ipa: 'f', ipa_dagesh: 'p', name: 'פֵּא', type: 'bgdkpt', en: 'f', en_dagesh: 'p', final: 'ף' },
  'צ': { ipa: 'ts', name: 'צָדִי', type: 'emphatic', en: "ts'", final: 'ץ' },
  'ק': { ipa: 'k', name: 'קוֹף', type: 'emphatic', en: "k'" },
  'ר': { ipa: 'ʁ', name: 'רֵישׁ', type: 'guttural', en: "r'" },
  'ש': { ipa: 'ʃ', ipa_sin: 's', name: 'שִׁין/שִׂין', type: 'shin', en: 'sh', en_sin: 's' },
  'ת': { ipa: 't', name: 'תָּו', type: 'bgdkpt', en: 't', classical: 'θ' },
  
  // Final letters (סופיות)
  'ך': { ipa: 'x', base: 'כ', name: 'כַּף סוֹפִית', final: true, en: "kh'" },
  'ם': { ipa: 'm', base: 'מ', name: 'מֵם סוֹפִית', final: true, en: 'm' },
  'ן': { ipa: 'n', base: 'נ', name: 'נוּן סוֹפִית', final: true, en: 'n' },
  'ף': { ipa: 'f', base: 'פ', name: 'פֵּא סוֹפִית', final: true, en: 'f' },
  'ץ': { ipa: 'ts', base: 'צ', name: 'צָדִי סוֹפִית', final: true, en: "ts'" },
};

// ═══════════════════════════════════════════════════════════════════════════════
// IPA VOWELS (from HTML + V5 quality additions)
// ═══════════════════════════════════════════════════════════════════════════════

const IPA_VOWELS = {
  // Front vowels - EXACT MATCHES
  'i': { name: 'close front', he: 'חִירִיק', nikud: 'ִ', male: 'ִי', en: 'ee/ea', 
         quality: 'exact', ex: { he: 'מִי', en: 'see' } },
  'e': { name: 'close-mid front', he: 'צֵירֵי', nikud: 'ֵ', male: 'ֵי', en: 'ay/ei', 
         quality: 'exact', ex: { he: 'בֵּית', en: 'say' } },
  'ɛ': { name: 'open-mid front', he: 'סֶגוֹל', nikud: 'ֶ', en: 'e', 
         quality: 'exact', ex: { he: 'אֶת', en: 'bed' } },
  
  // Front vowels - APPROXIMATIONS
  'ɪ': { name: 'near-close front', he: 'חִירִיק קצר', nikud: 'ִ', en: 'i', 
         quality: 'approx', warning: '⚠️ קירוב', ex: { he: 'אִם', en: 'sit' } },
  'æ': { name: 'near-open front', he: '—', nikud: 'ֶ', alt: 'ַ', en: 'a', 
         quality: 'approx', warning: '⚠️ לא קיים בעברית', ex: { he: '—', en: 'cat' } },
  
  // Central vowels
  'ə': { name: 'schwa', he: 'שְׁוָא נָע', nikud: 'ְ', en: 'a/u', 
         quality: 'exact', ex: { he: 'בְּ', en: 'about' } },
  'ɐ': { name: 'near-open central', he: 'פַּתַח', nikud: 'ַ', en: 'u', 
         quality: 'near-exact', ex: { he: 'אַ', en: 'cup' } },
  
  // Back vowels - EXACT MATCHES
  'u': { name: 'close back', he: 'שׁוּרוּק/קוּבּוּץ', nikud: 'ֻ', male: 'וּ', en: 'oo/u', 
         quality: 'exact', ex: { he: 'הוּא', en: 'moon' } },
  'o': { name: 'close-mid back', he: 'חוֹלָם', nikud: 'ֹ', male: 'וֹ', en: 'o/oa', 
         quality: 'exact', ex: { he: 'לֹא', en: 'go' } },
  'a': { name: 'open', he: 'פַּתַח', nikud: 'ַ', en: 'a', 
         quality: 'exact', ex: { he: 'אַב', en: 'father' } },
  
  // Back vowels - APPROXIMATIONS  
  'ʊ': { name: 'near-close back', he: 'קוּבּוּץ קצר', nikud: 'ֻ', en: 'oo', 
         quality: 'approx', warning: '⚠️ קירוב', ex: { he: 'קֻם', en: 'book' } },
  'ɔ': { name: 'open-mid back', he: 'קָמָץ קָטָן', nikud: 'ָ', en: 'aw/au', 
         quality: 'approx', ex: { he: 'כָּל', en: 'law' } },
  'ɑ': { name: 'open back', he: 'קָמָץ/פַּתַח', nikud: 'ָ', en: 'a/ar', 
         quality: 'near-exact', ex: { he: 'אָב', en: 'father' } },
  'ʌ': { name: 'open-mid back unrounded', he: 'פַּתַח', nikud: 'ַ', en: 'u', 
         quality: 'approx', warning: '⚠️ קירוב', ex: { he: '—', en: 'cup' } },
  'ɜː': { name: 'open-mid central', he: 'סֶגוֹל', nikud: 'ֶ', alt: 'ְ', en: 'er/ir/ur', 
          quality: 'approx', warning: '⚠️⚠️ קירוב גס', ex: { he: '—', en: 'bird' } },
  'ɒ': { name: 'open back rounded', he: 'קָמָץ', nikud: 'ָ', alt: 'ֹ', en: 'o', 
         quality: 'approx', warning: '⚠️ קירוב', ex: { he: '—', en: 'hot' } },
  
  // Diphthongs - NEAR-EXACT
  'aɪ': { name: 'diphthong', he: 'פַּתַח+יוֹד', nikud: 'ַי', he_ipa: 'aj', en: 'i/igh/y', 
          quality: 'near-exact', ex: { he: 'בַּיִת', en: 'my' } },
  'aʊ': { name: 'diphthong', he: 'פַּתַח+וָו', nikud: 'ָאוּ', he_ipa: 'aw', en: 'ou/ow', 
          quality: 'near-exact', ex: { he: 'אָו', en: 'now' } },
  'ɔɪ': { name: 'diphthong', he: 'חוֹלָם+יוֹד', nikud: 'וֹי', he_ipa: 'oj', en: 'oi/oy', 
          quality: 'near-exact', ex: { he: 'גּוֹי', en: 'boy' } },
  'eɪ': { name: 'diphthong', he: 'צֵירֵי+יוֹד', nikud: 'ֵי', he_ipa: 'ej', en: 'ay/ai/ei', 
          quality: 'near-exact', ex: { he: 'יֵשׁ', en: 'day' } },
  'oʊ': { name: 'diphthong', he: 'חוֹלָם+וָו', nikud: 'וֹ', en: 'o/ow/oa', 
          quality: 'approx', note: 'Hebrew pure, English glides', ex: { he: 'אוֹ', en: 'go' } },
};

// ═══════════════════════════════════════════════════════════════════════════════
// IPA CONSONANTS (from HTML + V5 geresh additions)
// ═══════════════════════════════════════════════════════════════════════════════

const IPA_CONSONANTS = {
  // Stops
  'p': { name: 'voiceless bilabial', he: 'פּ', en: 'p', ex: { he: 'פֹּה', en: 'pen' } },
  'b': { name: 'voiced bilabial', he: 'בּ', en: 'b', ex: { he: 'בַּיִת', en: 'bat' } },
  't': { name: 'voiceless alveolar', he: 'ת/ט', en: 't', ex: { he: 'תַּם', en: 'top' } },
  'd': { name: 'voiced alveolar', he: 'ד', en: 'd', ex: { he: 'דָּג', en: 'dog' } },
  'k': { name: 'voiceless velar', he: 'כּ/ק', en: 'k/c', ex: { he: 'כֹּל', en: 'cat' } },
  'g': { name: 'voiced velar', he: 'ג', en: 'g', ex: { he: 'גַּם', en: 'go' } },
  'ʔ': { name: 'glottal stop', he: 'א/ע', en: '—', ex: { he: 'אָב', en: 'uh-oh' } },
  
  // Fricatives
  'f': { name: 'voiceless labiodental', he: 'פ', en: 'f/ph', ex: { he: 'סוֹף', en: 'fan' } },
  'v': { name: 'voiced labiodental', he: 'ב/ו', en: 'v', ex: { he: 'טוֹב', en: 'van' } },
  's': { name: 'voiceless alveolar', he: 'ס/שׂ', en: 's/c', ex: { he: 'סוּס', en: 'sun' } },
  'z': { name: 'voiced alveolar', he: 'ז', en: 'z/s', ex: { he: 'זֶה', en: 'zoo' } },
  'ʃ': { name: 'voiceless postalveolar', he: 'שׁ', en: 'sh', ex: { he: 'שָׁם', en: 'she' } },
  'ʒ': { name: 'voiced postalveolar', he: 'ז׳', en: 'si/su', ex: { he: 'ז׳וּק', en: 'vision' }, geresh: true },
  'h': { name: 'voiceless glottal', he: 'ה', en: 'h', ex: { he: 'הוּא', en: 'hat' } },
  
  // V5 ADDITION: Unique sounds with geresh
  'θ': { name: 'voiceless dental', he: 'ת׳', en: 'th', ex: { he: '—', en: 'thin' },
         geresh: true, warning: '⚠️ NO Hebrew equivalent!' },
  'ð': { name: 'voiced dental', he: 'ד׳', en: 'th', ex: { he: '—', en: 'the' },
         geresh: true, warning: '⚠️ NO Hebrew equivalent!' },
  'tʃ': { name: 'voiceless postalveolar affricate', he: 'צ׳', en: 'ch', ex: { he: 'צ׳יפּ', en: 'chat' },
          geresh: true },
  'dʒ': { name: 'voiced postalveolar affricate', he: 'ג׳', en: 'j/g', ex: { he: 'ג׳ינס', en: 'jam' },
          geresh: true },
  
  // Hebrew unique (need geresh in English transcription)
  'x': { name: 'voiceless velar fricative', he: 'כ/ח', en: "kh'", ex: { he: 'לֶךְ', en: 'loch' },
         he_unique: true },
  'ħ': { name: 'voiceless pharyngeal', he: 'ח', en: "ḥ'", ex: { he: 'חַם', en: '—' },
         he_unique: true, note: 'deeper than כ' },
  'ʕ': { name: 'voiced pharyngeal', he: 'ע', en: "'", ex: { he: 'עַם', en: '—' },
         he_unique: true },
  'ts': { name: 'voiceless alveolar affricate', he: 'צ', en: "ts'", ex: { he: 'צַד', en: 'cats' },
          he_unique: true },
  'ʁ': { name: 'uvular fricative', he: 'ר', en: "r'", ex: { he: 'רֹאשׁ', en: '—' },
         he_unique: true, note: 'guttural R' },
  
  // Nasals
  'm': { name: 'bilabial nasal', he: 'מ', en: 'm', ex: { he: 'מָה', en: 'man' } },
  'n': { name: 'alveolar nasal', he: 'נ', en: 'n', ex: { he: 'נָא', en: 'no' } },
  'ŋ': { name: 'velar nasal', he: 'נג', en: 'ng', ex: { he: '—', en: 'sing' } },
  
  // Approximants
  'l': { name: 'alveolar lateral', he: 'ל', en: 'l', ex: { he: 'לֹא', en: 'let' } },
  'r': { name: 'alveolar approximant', he: 'ר׳', en: 'r', ex: { he: '—', en: 'red' },
         note: 'English R', different_he: true },
  'j': { name: 'palatal approximant', he: 'י', en: 'y', ex: { he: 'יָד', en: 'yes' } },
  'w': { name: 'labio-velar', he: 'ו׳', he_alt: 'וו', en: 'w', ex: { he: '—', en: 'we' },
         geresh: true, warning: 'W ≠ V!' },
};

// ═══════════════════════════════════════════════════════════════════════════════
// V5 ADDITION: GERESH MARKING
// ═══════════════════════════════════════════════════════════════════════════════

const GERESH = {
  // Hebrew sounds that need ' in English transcription
  he_to_en: {
    'צ': { ipa: 'ts', en: "ts'", note: 'צִפּוֹר→ts\'ipor' },
    'ח': { ipa: 'ħ', en: "ḥ'", alt: "ch'", note: 'חַיִים→ḥ\'ayim' },
    'כ': { ipa: 'x', en: "kh'", note: 'מֶלֶךְ→melekh\'' },
    'ע': { ipa: 'ʕ', en: "'", note: 'עַיִן→\'ayin' },
    'ר': { ipa: 'ʁ', en: "r'", note: 'רֹאשׁ→r\'osh (guttural R)' },
    'ק': { ipa: 'k', en: "k'", note: 'קוֹל→k\'ol (emphatic)' },
  },
  
  // English sounds that need ׳ in Hebrew transcription
  en_to_he: {
    'θ': { en: 'th (voiceless)', he: 'ת׳', ex: ['think→ת׳ִינק', 'three→ת׳רִי', 'bath→בֶּת׳'] },
    'ð': { en: 'th (voiced)', he: 'ד׳', ex: ['the→ד׳ָה', 'this→ד׳ִיס', 'that→ד׳ֶט'] },
    'tʃ': { en: 'ch', he: 'צ׳', ex: ['chair→צ׳ֵיר', 'cheese→צ׳ִיז', 'child→צ׳ַיילד'] },
    'dʒ': { en: 'j', he: 'ג׳', ex: ['jam→ג׳ֶם', 'job→ג׳וֹב', 'judge→ג׳ַדג׳'] },
    'ʒ': { en: 'zh/si', he: 'ז׳', ex: ['vision→וִיז׳ָן', 'measure→מֶז׳ֶר'] },
    'w': { en: 'w', he: 'ו׳', alt: 'וו', ex: ['water→ווֹטֶר', 'want→ווֹנט'] },
  },
};

// ═══════════════════════════════════════════════════════════════════════════════
// V5 ADDITION: SILENT LETTERS
// ═══════════════════════════════════════════════════════════════════════════════

const SILENT = {
  // Hebrew אותיות אֵם קְרִיאָה
  hebrew: {
    mater_lectionis: {
      'א': { when: 'סוף מילה', ex: ['הוּא', 'קָרָא', 'מָצָא'], voiced_ex: ['אָב', 'אֶת'] },
      'ה': { when: 'ה סיומת', ex: ['יָפָה', 'גְּדוֹלָה'], voiced: 'הּ (מַפִּיק)' },
      'ו': { when: 'וֹ/וּ', ex: ['אוֹר', 'שׁוּק', 'טוֹב'] },
      'י': { when: 'ִי/ֵי', ex: ['שִׁיר', 'בֵּית', 'עִיר'] },
    },
    shva: {
      na: { mark: 'ְ', ipa: 'ə', when: 'beginning/after vowel', ex: ['בְּ', 'שְׁמוֹ'] },
      nach: { mark: 'ְ', ipa: '', when: 'end of syllable', ex: ['מֶלֶךְ', 'כָּתְבָה'] },
    },
  },
  
  // English silent patterns
  english: {
    'kn': { silent: 'k', ipa: '/n/', ex: ['knight', 'knife', 'know'], he: ['נַייט', 'נַייף', 'נוֹ'] },
    'gn': { silent: 'g', ipa: '/n/', ex: ['gnome', 'sign', 'design'], he: ['נוֹם', 'סַיין', 'דִיזַיין'] },
    'wr': { silent: 'w', ipa: '/r/', ex: ['write', 'wrong', 'wrap'], he: ['רַייט', 'רוֹנג', 'רֶפּ'] },
    'mb': { silent: 'b', ipa: '/m/', ex: ['lamb', 'climb', 'bomb'], he: ['לֶם', 'קְלַיים', 'בּוֹם'] },
    'bt': { silent: 'b', ipa: '/t/', ex: ['debt', 'doubt'], he: ['דֶט', 'דַאוּט'] },
    'igh': { silent: 'gh', ipa: '/aɪ/', ex: ['night', 'light', 'high'], he: ['נַייט', 'לַייט', 'הַיי'] },
    'ough': { silent: 'gh', ipa: 'varies', ex: ['though', 'through', 'thought'], he: ['ד׳וֹ', 'ת׳רוּ', 'ת׳וֹט'] },
    'alk': { silent: 'l', ipa: '/ɔːk/', ex: ['walk', 'talk', 'chalk'], he: ['ווֹק', 'טוֹק', 'צ׳וֹק'] },
    'alm': { silent: 'l', ipa: '/ɑːm/', ex: ['calm', 'palm', 'psalm'], he: ['קָאם', 'פָּאם', 'סָאם'] },
    'ps': { silent: 'p', ipa: '/s/', ex: ['psychology', 'psalm'], he: ['סַייקוֹלוֹג׳י', 'סָאם'] },
    'sten': { silent: 't', ipa: '/sn/', ex: ['listen', 'fasten'], he: ['לִיסֶן', 'פֶסֶן'] },
    'stle': { silent: 't', ipa: '/sl/', ex: ['castle', 'whistle'], he: ['קֶסֶל', 'ווִיסֶל'] },
  },
};

// ═══════════════════════════════════════════════════════════════════════════════
// V5 ADDITION: MAPPIQ
// ═══════════════════════════════════════════════════════════════════════════════

const MAPPIQ = {
  mark: 'הּ',
  ipa: 'h',
  rule: 'ה נשמעת בסוף מילה (סמיכות, כינויים)',
  
  examples: {
    with_mappiq: [
      { he: 'שֶׁלָּהּ', en: "shela'", meaning: 'שלה (נקבה)', ipa: 'ʃelaʰ' },
      { he: 'גָּבֹהַּ', en: "gavoah'", meaning: 'גבוה', ipa: 'gavoaʰ' },
      { he: 'בֵּיתָהּ', en: "beta'", meaning: 'ביתה (שלה)', ipa: 'betaʰ' },
      { he: 'אֱלוֹהַּ', en: "eloah'", meaning: 'אלוה', ipa: 'eloaʰ' },
    ],
    without_mappiq: [
      { he: 'שֶׁלָּה', en: 'shela', meaning: 'שלה (מקום)', note: 'ה silent' },
      { he: 'יָפָה', en: 'yafa', meaning: 'יפה', note: 'ה silent' },
      { he: 'גְּדוֹלָה', en: 'gdola', meaning: 'גדולה', note: 'ה silent' },
      { he: 'בַּיְתָה', en: 'bayta', meaning: 'הביתה (כיוון)', note: 'ה silent' },
    ],
  },
  
  usage: ['סמיכות (construct state)', 'כינויים (possessive suffixes)'],
};

// ═══════════════════════════════════════════════════════════════════════════════
// V5 ADDITION: DAGESH BGDKPT RULES
// ═══════════════════════════════════════════════════════════════════════════════

const BGDKPT = {
  'ב': { 
    with: { letter: 'בּ', ipa: 'b', en: 'b', ex: 'בַּיִת→bayit' },
    without: { letter: 'ב', ipa: 'v', en: 'v', ex: 'אָב→av' },
  },
  'ג': { 
    with: { letter: 'גּ', ipa: 'g', en: 'g', ex: 'גַּם→gam' },
    without: { letter: 'ג', ipa: 'g', en: 'g', ex: 'same in modern' },
    classical: { without_ipa: 'ɣ' },
  },
  'ד': { 
    with: { letter: 'דּ', ipa: 'd', en: 'd', ex: 'דָּג→dag' },
    without: { letter: 'ד', ipa: 'd', en: 'd', ex: 'same in modern' },
    classical: { without_ipa: 'ð', note: 'like English "the"' },
  },
  'כ': { 
    with: { letter: 'כּ', ipa: 'k', en: 'k', ex: 'כֵּן→ken' },
    without: { letter: 'כ', ipa: 'x', en: "kh'", ex: 'מֶלֶךְ→melekh\'' },
  },
  'פ': { 
    with: { letter: 'פּ', ipa: 'p', en: 'p', ex: 'פֹּה→po' },
    without: { letter: 'פ', ipa: 'f', en: 'f', ex: 'סוֹפֵר→sofer' },
  },
  'ת': { 
    with: { letter: 'תּ', ipa: 't', en: 't', ex: 'תַּם→tam' },
    without: { letter: 'ת', ipa: 't', en: 't', ex: 'same in modern' },
    classical: { without_ipa: 'θ', note: 'like English "think"' },
  },
};

// ═══════════════════════════════════════════════════════════════════════════════
// IPA TO NIKUD MAPPING (from HTML)
// ═══════════════════════════════════════════════════════════════════════════════

const IPA_TO_NIKUD = {
  'a': ['patach', 'kamatz', 'hataf_a'],
  'e': ['tzere', 'segol', 'hataf_e'],
  'ɛ': ['segol', 'hataf_e'],
  'i': ['chirik', 'chirik_m'],
  'o': ['cholam', 'cholam_m', 'kamatz', 'hataf_o'],
  'u': ['kubutz', 'shuruk'],
  'ə': ['shva'],
  // V5 additions
  'ɪ': ['chirik'],
  'ʊ': ['kubutz'],
  'æ': ['segol', 'patach'],
  'ʌ': ['patach'],
  'ɔ': ['kamatz'],
  'ɑ': ['kamatz'],
};

// ═══════════════════════════════════════════════════════════════════════════════
// API FUNCTIONS
// ═══════════════════════════════════════════════════════════════════════════════

function getNikudForIpa(ipa) {
  return IPA_TO_NIKUD[ipa] || [];
}

function getIpaVowelInfo(ipa) {
  return IPA_VOWELS[ipa] || null;
}

function getIpaConsonantInfo(ipa) {
  return IPA_CONSONANTS[ipa] || null;
}

function isGuttural(letter) {
  const info = HE_LETTERS[letter];
  return info && info.type === 'guttural';
}

function needsGeresh(sound, direction) {
  if (direction === 'he_to_en') {
    return !!GERESH.he_to_en[sound];
  }
  if (direction === 'en_to_he') {
    return !!GERESH.en_to_he[sound];
  }
  return false;
}

function getGereshForm(sound, direction) {
  if (direction === 'he_to_en') {
    return GERESH.he_to_en[sound]?.en || sound;
  }
  if (direction === 'en_to_he') {
    return GERESH.en_to_he[sound]?.he || sound;
  }
  return sound;
}

function getSilentPattern(pattern) {
  return SILENT.english[pattern] || null;
}

function isExactMatch(ipa) {
  return IPA_VOWELS[ipa]?.quality === 'exact';
}

function isApproximate(ipa) {
  return IPA_VOWELS[ipa]?.quality === 'approx';
}

function applyNikud(letter, ipa, options = {}) {
  // Get nikud options
  const nikudOptions = getNikudForIpa(ipa);
  if (!nikudOptions.length) return letter;
  
  // Use hataf for gutturals with schwa
  if (isGuttural(letter) && ipa === 'ə') {
    return letter + NIKUD.hataf_a.n;
  }
  
  // Use male form if requested
  const nikudKey = nikudOptions[0];
  const nikudInfo = NIKUD[nikudKey];
  
  if (options.male && IPA_VOWELS[ipa]?.male) {
    return letter + IPA_VOWELS[ipa].male;
  }
  
  return letter + nikudInfo.n;
}

function getBgdkptSound(letter, hasDagesh) {
  const info = BGDKPT[letter];
  if (!info) return null;
  return hasDagesh ? info.with : info.without;
}

// ═══════════════════════════════════════════════════════════════════════════════
// TESTS
// ═══════════════════════════════════════════════════════════════════════════════

console.log('═══════════════════════════════════════════════════════════════════════════════════');
console.log('UNIKEY V5 - COMPLETE MERGED');
console.log('═══════════════════════════════════════════════════════════════════════════════════\n');

console.log('DATA COUNTS:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('HE_LETTERS:', Object.keys(HE_LETTERS).length);
console.log('NIKUD:', Object.keys(NIKUD).length);
console.log('IPA_VOWELS:', Object.keys(IPA_VOWELS).length);
console.log('IPA_CONSONANTS:', Object.keys(IPA_CONSONANTS).length);
console.log('GERESH.he_to_en:', Object.keys(GERESH.he_to_en).length);
console.log('GERESH.en_to_he:', Object.keys(GERESH.en_to_he).length);
console.log('SILENT.english:', Object.keys(SILENT.english).length);
console.log('BGDKPT:', Object.keys(BGDKPT).length);

console.log('\nVOWEL QUALITY EXAMPLES:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
['a', 'i', 'æ', 'ʌ', 'aɪ'].forEach(ipa => {
  const info = IPA_VOWELS[ipa];
  if (info) {
    console.log(`/${ipa}/ → ${info.nikud || info.he} [${info.quality}] ${info.warning || ''}`);
  }
});

console.log('\nGERESH EXAMPLES:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('צ →', GERESH.he_to_en['צ']?.en);
console.log('ח →', GERESH.he_to_en['ח']?.en);
console.log('th /θ/ →', GERESH.en_to_he['θ']?.he);
console.log('th /ð/ →', GERESH.en_to_he['ð']?.he);
console.log('ch →', GERESH.en_to_he['tʃ']?.he);

console.log('\nBGDKPT EXAMPLES:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('בּ:', BGDKPT['ב'].with.ipa, '/', BGDKPT['ב'].with.en);
console.log('ב:', BGDKPT['ב'].without.ipa, '/', BGDKPT['ב'].without.en);
console.log('כּ:', BGDKPT['כ'].with.ipa, '/', BGDKPT['כ'].with.en);
console.log('כ:', BGDKPT['כ'].without.ipa, '/', BGDKPT['כ'].without.en);

console.log('\nMAPPIQ EXAMPLES:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
MAPPIQ.examples.with_mappiq.slice(0, 2).forEach(ex => {
  console.log(`${ex.he} → ${ex.en} (${ex.meaning})`);
});

console.log('\nSILENT LETTER EXAMPLES:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('kn-:', SILENT.english['kn'].ex.join(', '));
console.log('igh:', SILENT.english['igh'].ex.join(', '));

console.log('\nAPPLY NIKUD:');
console.log('─────────────────────────────────────────────────────────────────────────────────────');
console.log('ב + /a/ →', applyNikud('ב', 'a'));
console.log('ב + /i/ →', applyNikud('ב', 'i'));
console.log('ב + /u/ (male) →', applyNikud('ב', 'u', { male: true }));
console.log('ח + /ə/ →', applyNikud('ח', 'ə'), '(hataf for guttural)');

console.log('\n═══════════════════════════════════════════════════════════════════════════════════');

// Export
if (typeof module !== 'undefined' && module.exports) {
  module.exports = {
    UniKey,
    NIKUD,
    HE_LETTERS,
    IPA_VOWELS,
    IPA_CONSONANTS,
    GERESH,
    SILENT,
    MAPPIQ,
    BGDKPT,
    IPA_TO_NIKUD,
    getNikudForIpa,
    getIpaVowelInfo,
    getIpaConsonantInfo,
    isGuttural,
    needsGeresh,
    getGereshForm,
    getSilentPattern,
    isExactMatch,
    isApproximate,
    applyNikud,
    getBgdkptSound,
  };
}
