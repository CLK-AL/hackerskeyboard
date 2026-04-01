// ═══════════════════════════════════════════════════════════════════════════════
// UNIKEY - IPA AS UNIVERSAL MAPPING LAYER
// ═══════════════════════════════════════════════════════════════════════════════
//
//  IPA = The bridge between ALL languages and keyboard keys
//
//  ┌─────────────────────────────────────────────────────────────────────────┐
//  │                                                                         │
//  │                         ┌─────────┐                                     │
//  │                         │   IPA   │                                     │
//  │                         │ UniKey  │                                     │
//  │                         └────┬────┘                                     │
//  │                              │                                          │
//  │         ┌────────────────────┼────────────────────┐                     │
//  │         │                    │                    │                     │
//  │         ▼                    ▼                    ▼                     │
//  │    ┌─────────┐         ┌─────────┐         ┌─────────┐                  │
//  │    │ Hebrew  │         │ English │         │  Other  │                  │
//  │    │ +Nikud  │         │+Spelling│         │Languages│                  │
//  │    │ +Hataf  │         │+Diphtng │         │         │                  │
//  │    └─────────┘         └─────────┘         └─────────┘                  │
//  │                                                                         │
//  └─────────────────────────────────────────────────────────────────────────┘
//
// ═══════════════════════════════════════════════════════════════════════════════

const IPA_UNIVERSAL_MAP = {
  
  // ═══════════════════════════════════════════════════════════════════════════
  // CONSONANTS - IPA → All languages
  // ═══════════════════════════════════════════════════════════════════════════
  
  consonants: {
    'b':  { he: ['בּ'], en: ['b', 'bb'] },
    'v':  { he: ['ב', 'ו'], en: ['v'] },
    'g':  { he: ['ג'], en: ['g', 'gg'] },
    'd':  { he: ['ד'], en: ['d', 'dd'] },
    'h':  { he: ['ה'], en: ['h'] },
    'z':  { he: ['ז'], en: ['z', 'zz', 's'] },
    'x':  { he: ['ח', 'כ'], en: ['ch', 'kh'] },
    't':  { he: ['ט', 'ת'], en: ['t', 'tt'] },
    'j':  { he: ['י'], en: ['y'] },
    'k':  { he: ['כּ', 'ק'], en: ['k', 'c', 'ck', 'q'] },
    'l':  { he: ['ל'], en: ['l', 'll'] },
    'm':  { he: ['מ'], en: ['m', 'mm'] },
    'n':  { he: ['נ'], en: ['n', 'nn'] },
    's':  { he: ['ס', 'שׂ'], en: ['s', 'ss', 'c', 'sc'] },
    'ʔ':  { he: ['א', 'ע'], en: [] },
    'p':  { he: ['פּ'], en: ['p', 'pp'] },
    'f':  { he: ['פ'], en: ['f', 'ff', 'ph', 'gh'] },
    'ts': { he: ['צ'], en: ['ts', 'tz'] },
    'ʁ':  { he: ['ר'], en: ['r', 'rr'] },
    'ʃ':  { he: ['שׁ'], en: ['sh', 'ti', 'ci', 'ssi'] },
  },
  
  // ═══════════════════════════════════════════════════════════════════════════
  // VOWELS - IPA → Hebrew nikud (16) + English spellings
  // ═══════════════════════════════════════════════════════════════════════════
  
  vowels: {
    // A-sounds
    'a':  { he: ['ַ'], name: 'פַּתַח', en: ['a'] },
    'ɑ':  { he: ['ָ'], name: 'קָמָץ', en: ['a', 'o'] },
    'ă':  { he: ['ֲ'], name: 'חֲטַף פַּתַח', en: [], guttural: true },
    
    // E-sounds  
    'ɛ':  { he: ['ֶ'], name: 'סֶגוֹל', en: ['e', 'ea'] },
    'e':  { he: ['ֵ'], name: 'צֵירֵי', en: ['ay', 'ai', 'ei'] },
    'ĕ':  { he: ['ֱ'], name: 'חֲטַף סֶגוֹל', en: [], guttural: true },
    
    // I-sounds
    'i':  { he: ['ִ'], name: 'חִירִיק', en: ['i', 'ee', 'ea'] },
    'iː': { he: ['ִי'], name: 'חִירִיק מָלֵא', en: ['ee', 'ea', 'ie'] },
    
    // O-sounds
    'o':  { he: ['ֹ'], name: 'חוֹלָם', en: ['o', 'oa'] },
    'oː': { he: ['וֹ'], name: 'חוֹלָם מָלֵא', en: ['o', 'oa', 'ow'] },
    'ɔ':  { he: ['ָ'], name: 'קָמָץ קָטָן', en: ['o', 'aw', 'au'] },
    'ŏ':  { he: ['ֳ'], name: 'חֲטַף קָמָץ', en: [], guttural: true },
    
    // U-sounds
    'u':  { he: ['ֻ'], name: 'קֻבּוּץ', en: ['u', 'oo'] },
    'uː': { he: ['וּ'], name: 'שׁוּרוּק', en: ['oo', 'ue', 'ew'] },
    
    // Schwa
    'ə':  { he: ['ְ'], name: 'שְׁוָא', en: ['a', 'e', 'i', 'o', 'u'] },
    
    // English diphthongs (no Hebrew equivalent)
    'eɪ': { he: [], en: ['a_e', 'ai', 'ay'], diphthong: true },
    'aɪ': { he: [], en: ['i_e', 'igh', 'y'], diphthong: true },
    'ɔɪ': { he: [], en: ['oi', 'oy'], diphthong: true },
    'aʊ': { he: [], en: ['ou', 'ow'], diphthong: true },
    'oʊ': { he: [], en: ['o', 'oa', 'ow'], diphthong: true },
  },
  
  // ═══════════════════════════════════════════════════════════════════════════
  // MG PAIRS - IPA of each gender form
  // ═══════════════════════════════════════════════════════════════════════════
  
  mg_pairs: {
    'POSS_3S':    { m_ipa: 'o', f_ipa: 'a', he_m: 'וֹ', he_f: 'הּ', en_m: 'his', en_f: 'her' },
    'PLURAL':     { m_ipa: 'im', f_ipa: 'ot', he_m: 'ִים', he_f: 'וֹת', en_m: '-s', en_f: '-s' },
    'PRONOUN_3S': { m_ipa: 'hu', f_ipa: 'hi', he_m: 'הוּא', he_f: 'הִיא', en_m: 'he', en_f: 'she' },
    'PRONOUN_3P': { m_ipa: 'hem', f_ipa: 'hen', he_m: 'הֵם', he_f: 'הֵן', en_m: 'they', en_f: 'they' },
  },
  
  // ═══════════════════════════════════════════════════════════════════════════
  // SYLLABLES - IPA CV combinations
  // ═══════════════════════════════════════════════════════════════════════════
  
  syllables: {
    // Each syllable = Consonant IPA + Vowel IPA
    'ʃa': { he: 'שַׁ', en: 'sha' },
    'ʃi': { he: 'שִׁ', en: 'shi/she' },
    'ʃo': { he: 'שֹׁ', en: 'sho' },
    'ma': { he: 'מַ', en: 'ma' },
    'mi': { he: 'מִ', en: 'me/mi' },
    'no': { he: 'נֹ', en: 'no' },
    'la': { he: 'לַ', en: 'la' },
    'ba': { he: 'בַּ', en: 'ba' },
    'bi': { he: 'בִּ', en: 'be/bee' },
    'da': { he: 'דַ', en: 'da' },
    'ta': { he: 'תַ', en: 'ta' },
    'ka': { he: 'כַּ', en: 'ca/ka' },
    'sa': { he: 'סַ', en: 'sa' },
    'na': { he: 'נַ', en: 'na' },
    'ra': { he: 'רַ', en: 'ra' },
  },
};

// ═══════════════════════════════════════════════════════════════════════════════
// KEYBOARD LAYOUT - Physical keys map to IPA
// ═══════════════════════════════════════════════════════════════════════════════

const KEYBOARD_TO_IPA = {
  // Each physical key has an IPA value
  // The IPA then resolves to Hebrew OR English based on mode
  
  // Example: Key 'S' in IPA mode
  's': {
    ipa: 's',
    resolves_to: {
      he: ['ס', 'שׂ'],  // Both make /s/ sound
      en: ['s', 'c'],   // Both can make /s/ sound
    }
  },
  
  // Example: Key 'SH' in IPA mode (shifted or combo)
  'sh': {
    ipa: 'ʃ',
    resolves_to: {
      he: ['שׁ'],       // Only shin makes /ʃ/
      en: ['sh'],       // sh digraph
    }
  },
};

// ═══════════════════════════════════════════════════════════════════════════════
// OUTPUT
// ═══════════════════════════════════════════════════════════════════════════════

console.log('═══════════════════════════════════════════════════════════════════════════════════');
console.log('IPA = UNIVERSAL MAPPING LAYER');
console.log('═══════════════════════════════════════════════════════════════════════════════════\n');

console.log('                         ┌─────────────┐');
console.log('                         │  KEYBOARD   │');
console.log('                         │    KEY      │');
console.log('                         └──────┬──────┘');
console.log('                                │');
console.log('                                ▼');
console.log('                         ┌─────────────┐');
console.log('                         │     IPA     │');
console.log('                         │   UniKey    │');
console.log('                         └──────┬──────┘');
console.log('                                │');
console.log('              ┌─────────────────┼─────────────────┐');
console.log('              │                 │                 │');
console.log('              ▼                 ▼                 ▼');
console.log('        ┌───────────┐    ┌───────────┐    ┌───────────┐');
console.log('        │  Hebrew   │    │  English  │    │   Other   │');
console.log('        │  16 vowels│    │ 17 vowels │    │ Languages │');
console.log('        │  +nikud   │    │ +spelling │    │           │');
console.log('        │  +hataf   │    │ +diphthng │    │           │');
console.log('        └───────────┘    └───────────┘    └───────────┘');

console.log('\n═══════════════════════════════════════════════════════════════════════════════════');
console.log('IPA MAPS EVERYTHING:');
console.log('═══════════════════════════════════════════════════════════════════════════════════\n');

console.log('┌──────────────────────────────────────────────────────────────────────────────────┐');
console.log('│                                                                                  │');
console.log('│  1. CONSONANTS:  /ʃ/ → שׁ (Hebrew) | sh (English) | ch (French) | sch (German) │');
console.log('│                                                                                  │');
console.log('│  2. VOWELS:      /a/ → ַ פַּתַח (Hebrew) | a (English)                           │');
console.log('│                  /ɑ/ → ָ קָמָץ (Hebrew) | a/o (English)                          │');
console.log('│                  /ă/ → ֲ חֲטַף (Hebrew, gutturals only!)                         │');
console.log('│                                                                                  │');
console.log('│  3. SYLLABLES:   /ʃa/ → שַׁ (Hebrew) | sha (English)                            │');
console.log('│                  /mi/ → מִ (Hebrew) | me (English)                              │');
console.log('│                                                                                  │');
console.log('│  4. MG PAIRS:    /o/ → וֹ (m) | /a/ → הּ (f)                                     │');
console.log('│                  /im/ → ִים (m) | /ot/ → וֹת (f)                                │');
console.log('│                                                                                  │');
console.log('└──────────────────────────────────────────────────────────────────────────────────┘');

console.log('\n═══════════════════════════════════════════════════════════════════════════════════');
console.log('ONE KEY → IPA → ALL REPRESENTATIONS:');
console.log('═══════════════════════════════════════════════════════════════════════════════════\n');

const examples = [
  { key: 'S', ipa: 's', he: 'ס / שׂ', en: 's / c / ss' },
  { key: 'SH', ipa: 'ʃ', he: 'שׁ', en: 'sh / ti / ci' },
  { key: 'K', ipa: 'k', he: 'כּ / ק', en: 'k / c / ck / q' },
  { key: 'KH', ipa: 'x', he: 'כ / ח', en: 'ch (loch)' },
  { key: 'A', ipa: 'a/ɑ', he: 'ַ / ָ / ֲ', en: 'a' },
  { key: 'E', ipa: 'e/ɛ', he: 'ֵ / ֶ / ֱ', en: 'e / ea' },
  { key: 'I', ipa: 'i', he: 'ִ / ִי', en: 'i / ee / ea' },
  { key: 'O', ipa: 'o/ɔ', he: 'ֹ / וֹ / ֳ', en: 'o / oa / aw' },
  { key: 'U', ipa: 'u', he: 'ֻ / וּ', en: 'u / oo / ue' },
];

console.log('Key     IPA       Hebrew              English');
console.log('─────────────────────────────────────────────────────────────');
examples.forEach(ex => {
  console.log(`[${ex.key.padEnd(3)}]   /${ex.ipa.padEnd(5)}/   ${ex.he.padEnd(18)}  ${ex.en}`);
});

console.log('\n═══════════════════════════════════════════════════════════════════════════════════');
console.log('THE COMPLETE PICTURE:');
console.log('═══════════════════════════════════════════════════════════════════════════════════\n');

console.log('  ╔═══════════════════════════════════════════════════════════════════════════════╗');
console.log('  ║                                                                               ║');
console.log('  ║   IPA is the UNIVERSAL KEY that maps:                                         ║');
console.log('  ║                                                                               ║');
console.log('  ║   • 20 consonant phonemes → Hebrew letters + English spellings               ║');
console.log('  ║   • 16 Hebrew vowels (nikud + hataf) → IPA vowel symbols                     ║');
console.log('  ║   • 17 English vowels (pure + diphthongs) → IPA vowel symbols                ║');
console.log('  ║   • CV syllables in both languages → IPA combinations                        ║');
console.log('  ║   • MG gender pairs → IPA of each form                                       ║');
console.log('  ║                                                                               ║');
console.log('  ║   ONE keyboard. ONE sound system. TWO+ languages. INFINITE possibilities.    ║');
console.log('  ║                                                                               ║');
console.log('  ║   מקלדת אחת. מערכת צלילים אחת. שתי שפות+. אפשרויות אינסופיות.                   ║');
console.log('  ║                                                                               ║');
console.log('  ╚═══════════════════════════════════════════════════════════════════════════════╝');
