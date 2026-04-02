package al.clk.key

// ═══════════════════════════════════════════════════════════════════════════════
// NORDIC & NON-LATIN SCRIPT SPELLING PATTERNS
// Each enum implements ISpellingPattern with IPA-based lookups
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Swedish spelling patterns (grapheme to IPA mappings)
 */
enum class SwedishPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // SJ-sound (retroflex sibilant)
    SJ("sj", "ɧ", "sj", listOf("sjö", "sjunga", "själv")),
    SKJ("skj", "ɧ", "skj", listOf("skjorta", "skjuta")),
    STJ("stj", "ɧ", "stj", listOf("stjärna", "stjäla")),
    SK_SOFT("sk", "ɧ", "sk-soft", listOf("sked", "skina", "skön")), // Before e, i, y, ä, ö

    // TJ-sound (palatal)
    TJ("tj", "ɕ", "tj", listOf("tjugo", "tjock", "tjäna")),
    KJ("kj", "ɕ", "kj", listOf("kjol", "kjortel")),
    K_SOFT("k", "ɕ", "k-soft", listOf("köpa", "kär", "kö")), // Before e, i, y, ä, ö

    // Retroflex sounds
    RS("rs", "ʂ", "rs-retro", listOf("torsdag", "kors", "fors")),
    RD("rd", "ɖ", "rd-retro", listOf("bord", "ord", "gård")),
    RN("rn", "ɳ", "rn-retro", listOf("barn", "horn", "torn")),
    RT("rt", "ʈ", "rt-retro", listOf("kort", "svart", "sort")),
    RL("rl", "ɭ", "rl-retro", listOf("Karl", "pärla")),

    // Vowels
    A_RING("å", "oː", "a-ring", listOf("år", "gå", "bråka")),
    A_UMLAUT("ä", "ɛː", "a-umlaut", listOf("äta", "väg", "läsa")),
    O_UMLAUT("ö", "øː", "o-umlaut", listOf("öra", "röd", "göra")),
    Y("y", "yː", "y", listOf("by", "ny", "syster")),
    U("u", "ʉː", "u", listOf("du", "hus", "bus")),

    // NG
    NG("ng", "ŋ", "ng", listOf("kung", "lång", "sång")),
    NK("nk", "ŋk", "nk", listOf("tänka", "dricka")),

    // Silent letters
    D_SILENT("d", "", "d-silent", listOf("hjäl", "fjärd", "gård"), PatternPosition.END),
    G_SILENT("g", "", "g-silent", listOf("dag", "väg", "mig"), PatternPosition.END),

    // Common endings
    TION("tion", "ʃuːn", "tion", listOf("nation", "station", "information"), PatternPosition.END),
    IGHET("ighet", "iːhɛt", "ighet", listOf("möjlighet", "svårighet"), PatternPosition.END),
    LIGEN("ligen", "liːɛn", "ligen", listOf("verkligen", "egentligen"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<SwedishPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<SwedishPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): SwedishPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Norwegian spelling patterns (grapheme to IPA mappings)
 */
enum class NorwegianPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // SJ/SKJ sounds
    SJ("sj", "ʃ", "sj", listOf("sjø", "sjel", "sjelden")),
    SKJ("skj", "ʃ", "skj", listOf("skjorte", "skjønn", "skjære")),

    // KJ sounds
    KJ("kj", "ç", "kj", listOf("kjøpe", "kjenne", "kjære")),
    HJ("hj", "j", "hj", listOf("hjerte", "hjelpe", "hjemme")),

    // Retroflex
    RS("rs", "ʂ", "rs-retro", listOf("torsk", "norsk", "først")),
    RD("rd", "ɖ", "rd-retro", listOf("bord", "ord", "jord")),
    RN("rn", "ɳ", "rn-retro", listOf("barn", "horn")),
    RT("rt", "ʈ", "rt-retro", listOf("kort", "svart")),
    RL("rl", "ɭ", "rl-retro", listOf("perle", "jærlig")),

    // Vowels
    A_RING("å", "oː", "a-ring", listOf("år", "gå", "båt")),
    AE("æ", "æː", "ae", listOf("være", "lærer", "dæven")),
    O_SLASH("ø", "øː", "o-slash", listOf("øye", "før", "søster")),
    Y("y", "yː", "y", listOf("by", "ny", "syk")),

    // EI/AI
    EI("ei", "æi", "ei", listOf("stein", "rein", "vei")),
    AI("ai", "ai", "ai", listOf("hai", "mai")),

    // NG
    NG("ng", "ŋ", "ng", listOf("lang", "ring", "sang")),

    // Common endings
    SJON("sjon", "ʃuːn", "sjon", listOf("nasjon", "stasjon"), PatternPosition.END),
    HET("het", "heːt", "het", listOf("mulighet", "sikkerhet"), PatternPosition.END),
    ELSE("else", "ɛlsə", "else", listOf("forståelse", "følelse"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<NorwegianPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<NorwegianPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): NorwegianPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Danish spelling patterns (grapheme to IPA mappings)
 */
enum class DanishPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Soft D (stød)
    D_SOFT("d", "ð", "d-soft", listOf("mad", "side", "gade")),

    // Silent letters
    G_SILENT("g", "", "g-silent", listOf("dag", "sige", "lige"), PatternPosition.END),
    D_SILENT("d", "", "d-silent", listOf("mand", "land", "vand"), PatternPosition.END),

    // Vowels
    A_RING("å", "ɔː", "a-ring", listOf("år", "gå", "både")),
    AE("æ", "ɛː", "ae", listOf("være", "læse", "mænd")),
    O_SLASH("ø", "øː", "o-slash", listOf("før", "grøn", "høre")),
    Y("y", "yː", "y", listOf("ny", "by", "system")),

    // Diphthongs
    EJ("ej", "ai", "ej", listOf("hej", "vej", "nej")),
    AJ("aj", "ai", "aj", listOf("maj")),
    OJ("oj", "ɔi", "oj", listOf("oj", "drøj")),

    // NG
    NG("ng", "ŋ", "ng", listOf("lang", "sang", "ting")),

    // HV (silent h)
    HV("hv", "v", "hv", listOf("hvad", "hvor", "hvem"), PatternPosition.START),
    HJ("hj", "j", "hj", listOf("hjælp", "hjerte", "hjem"), PatternPosition.START),

    // Common endings
    TION("tion", "ʃoːn", "tion", listOf("nation", "station"), PatternPosition.END),
    ELSE("else", "əlsə", "else", listOf("forståelse", "følelse"), PatternPosition.END),
    HED("hed", "heð", "hed", listOf("mulighed", "sandhed"), PatternPosition.END),
    IGHED("ighed", "iːəð", "ighed", listOf("vigtighed", "rigtighed"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<DanishPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<DanishPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): DanishPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Finnish spelling patterns (grapheme to IPA mappings)
 */
enum class FinnishPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Double consonants (gemination)
    KK("kk", "kː", "kk-gem", listOf("kukka", "takki", "pakko")),
    PP("pp", "pː", "pp-gem", listOf("kuppi", "oppia", "kauppa")),
    TT("tt", "tː", "tt-gem", listOf("katto", "letti", "matto")),
    SS("ss", "sː", "ss-gem", listOf("kissa", "missa", "vessa")),
    LL("ll", "lː", "ll-gem", listOf("kello", "pallo", "tulla")),
    MM("mm", "mː", "mm-gem", listOf("kämmen", "ammatti")),
    NN("nn", "nː", "nn-gem", listOf("kunnon", "mennä", "onnea")),
    RR("rr", "rː", "rr-gem", listOf("kurraus")),

    // Double vowels
    AA("aa", "aː", "aa-long", listOf("saada", "kaada", "maailma")),
    EE("ee", "eː", "ee-long", listOf("teen", "meen", "peeli")),
    II("ii", "iː", "ii-long", listOf("siisti", "viisi", "kiitos")),
    OO("oo", "oː", "oo-long", listOf("koota", "toomio")),
    UU("uu", "uː", "uu-long", listOf("suuri", "kuuma", "muutos")),
    YY("yy", "yː", "yy-long", listOf("syy", "hyy")),
    AA_UMLAUT("ää", "æː", "aa-umlaut", listOf("määrä", "pää", "sää")),
    OO_UMLAUT("öö", "øː", "oo-umlaut", listOf("yö", "työ")),

    // Special letters
    A_UMLAUT("ä", "æ", "a-umlaut", listOf("äiti", "käsi", "pälä")),
    O_UMLAUT("ö", "ø", "o-umlaut", listOf("työ", "pöytä", "köyhä")),
    Y("y", "y", "y", listOf("syy", "työ", "kylä")),

    // NK/NG (assimilation)
    NK("nk", "ŋk", "nk", listOf("kenkä", "pankki", "vankka")),
    NG("ng", "ŋː", "ng", listOf("lanki", "rengas")),

    // Common endings
    INEN("inen", "inɛn", "inen", listOf("suomalainen", "ihminen"), PatternPosition.END),
    INEN_GEN("isen", "isɛn", "isen-gen", listOf("suomalaisen"), PatternPosition.END),
    SSA("ssa", "sːa", "ssa-iness", listOf("talossa", "kotissa"), PatternPosition.END),
    STA("sta", "sta", "sta-elat", listOf("talosta", "kotista"), PatternPosition.END),
    LLE("lle", "lːɛ", "lle-allat", listOf("talolle", "kotille"), PatternPosition.END),
    LTA("lta", "lta", "lta-ablat", listOf("talolta", "kotilta"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<FinnishPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<FinnishPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): FinnishPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Greek spelling patterns (modern Greek grapheme to IPA mappings)
 */
enum class GreekPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Diphthongs
    AI("αι", "e", "ai", listOf("και", "μαι", "παιδί")),
    EI("ει", "i", "ei", listOf("είναι", "θείο", "κείμενο")),
    OI("οι", "i", "oi", listOf("οι", "ποιος", "μοίρα")),
    UI("υι", "i", "ui", listOf("υιός")),
    AU_V("αυ", "av", "au-v", listOf("αυτός", "αύριο")),
    AU_F("αυ", "af", "au-f", listOf("αυτί", "παύση")),
    EU_V("ευ", "ev", "eu-v", listOf("Ευρώπη", "ευχαριστώ")),
    EU_F("ευ", "ef", "eu-f", listOf("ευτυχία")),
    OU("ου", "u", "ou", listOf("που", "ούτε", "μου")),

    // Double consonants
    MP("μπ", "b", "mp-b", listOf("μπαμπάς", "μπορώ")),
    MP_MB("μπ", "mb", "mp-mb", listOf("κόμπος", "λάμπω")),
    NT("ντ", "d", "nt-d", listOf("ντομάτα", "ντύνω")),
    NT_ND("ντ", "nd", "nt-nd", listOf("πέντε", "άντρας")),
    GK("γκ", "g", "gk-g", listOf("γκαράζ", "γκολ")),
    GK_NG("γκ", "ŋg", "gk-ng", listOf("αγκάθι", "άγκυρα")),

    // Combinations with gamma
    GG("γγ", "ŋg", "gg", listOf("άγγελος", "αγγλικά")),
    GCH("γχ", "ŋx", "gch", listOf("έλεγχος", "άγχος")),
    GX("γξ", "ŋks", "gx", listOf("σφίγξ")),

    // Special letters
    THETA("θ", "θ", "theta", listOf("θέλω", "θάλασσα", "αθλητής")),
    CHI("χ", "x", "chi", listOf("χέρι", "χρόνος", "πολύχρωμος")),

    // Double letters
    PSI("ψ", "ps", "psi", listOf("ψωμί", "ψυχή", "λέψης")),
    XI("ξ", "ks", "xi", listOf("ξέρω", "αξία", "τάξη")),

    // Common endings
    OS("ος", "os", "os", listOf("κόσμος", "άνθρωπος"), PatternPosition.END),
    IS("ις", "is", "is", listOf("πόλις", "δύναμις"), PatternPosition.END),
    A("α", "a", "a", listOf("θάλασσα", "γλώσσα"), PatternPosition.END),
    I("ι", "i", "i", listOf("παιδί", "ψωμί"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<GreekPattern> = byPattern[p] ?: emptyList()
        fun fromIpa(ipa: String): List<GreekPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): GreekPattern? {
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { word.endsWith(it.pattern) }
        }
    }
}

/**
 * Russian spelling patterns (Cyrillic grapheme to IPA mappings)
 */
enum class RussianPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Soft sign palatalization
    SOFT_T("ть", "tʲ", "soft-t", listOf("мать", "путь", "есть"), PatternPosition.END),
    SOFT_D("дь", "dʲ", "soft-d", listOf("ведь", "медь")),
    SOFT_N("нь", "ɲ", "soft-n", listOf("конь", "день", "семь")),
    SOFT_L("ль", "lʲ", "soft-l", listOf("соль", "моль", "боль")),
    SOFT_S("сь", "sʲ", "soft-s", listOf("весь", "высь")),

    // Iotated vowels at word start
    YA_INIT("я", "ja", "ya-init", listOf("яблоко", "язык", "ясно"), PatternPosition.START),
    YE_INIT("е", "je", "ye-init", listOf("еда", "если", "ещё"), PatternPosition.START),
    YO_INIT("ё", "jo", "yo-init", listOf("ёлка", "ёж"), PatternPosition.START),
    YU_INIT("ю", "ju", "yu-init", listOf("юг", "юмор", "юный"), PatternPosition.START),

    // Iotated vowels after soft sign
    YA_SOFT("ья", "ʲja", "ya-soft", listOf("семья", "статья")),
    YE_SOFT("ье", "ʲje", "ye-soft", listOf("счастье", "здоровье")),
    YO_SOFT("ьё", "ʲjo", "yo-soft", listOf("питьё", "бритьё")),
    YU_SOFT("ью", "ʲju", "yu-soft", listOf("семью", "статью")),

    // Hard sign separation
    HARD_YA("ъя", "ja", "hard-ya", listOf("объяснить", "въявь")),
    HARD_YE("ъе", "je", "hard-ye", listOf("съесть", "объект")),

    // Voiced/voiceless pairs (final devoicing)
    B_FINAL("б", "p", "b-devoice", listOf("хлеб", "дуб", "зуб"), PatternPosition.END),
    V_FINAL("в", "f", "v-devoice", listOf("кровь", "любовь"), PatternPosition.END),
    G_FINAL("г", "k", "g-devoice", listOf("друг", "снег", "враг"), PatternPosition.END),
    D_FINAL("д", "t", "d-devoice", listOf("год", "сад", "город"), PatternPosition.END),
    Z_FINAL("з", "s", "z-devoice", listOf("раз", "глаз", "мороз"), PatternPosition.END),
    ZH_FINAL("ж", "ʂ", "zh-devoice", listOf("нож", "муж", "пляж"), PatternPosition.END),

    // Special sounds
    SHCH("щ", "ɕː", "shch", listOf("щи", "ищу", "ещё")),
    TS("ц", "ts", "ts", listOf("цена", "птица", "конец")),
    CH("ч", "tɕ", "ch", listOf("чай", "человек", "ночь")),

    // Genitive ending -ого/-его
    OGO("ого", "ovo", "ogo", listOf("нового", "большого"), PatternPosition.END),
    EGO("его", "jevo", "ego", listOf("синего", "моего"), PatternPosition.END),

    // Common endings
    TION("ция", "tsɨja", "tion", listOf("информация", "ситуация"), PatternPosition.END),
    NOST("ность", "nostʲ", "nost", listOf("возможность", "способность"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<RussianPattern> = byPattern[p] ?: emptyList()
        fun fromIpa(ipa: String): List<RussianPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): RussianPattern? {
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { word.endsWith(it.pattern) }
        }
    }
}

/**
 * Chinese Pinyin patterns (romanization to IPA mappings)
 */
enum class PinyinPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Retroflex initials
    ZH("zh", "ʈʂ", "zh-retro", listOf("zhōng", "zhī", "zhù")),
    CH("ch", "ʈʂʰ", "ch-retro", listOf("chī", "chá", "chū")),
    SH("sh", "ʂ", "sh-retro", listOf("shì", "shū", "shān")),
    R("r", "ʐ", "r-retro", listOf("rén", "rì", "ròu")),

    // Palatal initials
    J("j", "tɕ", "j-palatal", listOf("jiā", "jīn", "jiù")),
    Q("q", "tɕʰ", "q-palatal", listOf("qī", "qù", "qián")),
    X("x", "ɕ", "x-palatal", listOf("xī", "xiè", "xuě")),

    // Alveolar sibilants
    Z("z", "ts", "z-alveolar", listOf("zài", "zǒu", "zuò")),
    C("c", "tsʰ", "c-alveolar", listOf("cái", "cóng", "cuò")),
    S("s", "s", "s-alveolar", listOf("sān", "sì", "shuō")),

    // Special vowel after sibilants
    ZHI("zhi", "ʈʂɨ", "zhi", listOf("zhī", "zhǐ", "zhì")),
    CHI("chi", "ʈʂʰɨ", "chi", listOf("chī", "chǐ", "chì")),
    SHI("shi", "ʂɨ", "shi", listOf("shī", "shí", "shì")),
    RI("ri", "ʐɨ", "ri", listOf("rì")),
    ZI("zi", "tsɨ", "zi", listOf("zì", "zǐ")),
    CI("ci", "tsʰɨ", "ci", listOf("cì", "cí")),
    SI("si", "sɨ", "si", listOf("sì", "sī")),

    // Finals
    IA("ia", "ja", "ia", listOf("jiā", "xià")),
    IE("ie", "je", "ie", listOf("jiě", "xiě")),
    IU("iu", "joʊ", "iu", listOf("jiù", "liù")),
    UO("uo", "wo", "uo", listOf("duō", "guò")),
    UE("üe", "ɥe", "ue", listOf("xuě", "jué")),
    UA("ua", "wa", "ua", listOf("huā", "guà")),
    UI("ui", "weɪ", "ui", listOf("huí", "duì")),
    UN("un", "wən", "un", listOf("lún", "dùn")),
    UAN("uan", "wan", "uan", listOf("huán", "guān")),

    // Nasal finals
    AN("an", "an", "an", listOf("ān", "fàn")),
    EN("en", "ən", "en", listOf("rén", "shén")),
    IN("in", "in", "in", listOf("xīn", "jīn")),
    ANG("ang", "aŋ", "ang", listOf("fáng", "máng")),
    ENG("eng", "əŋ", "eng", listOf("fēng", "shēng")),
    ING("ing", "iŋ", "ing", listOf("tīng", "míng")),
    ONG("ong", "ʊŋ", "ong", listOf("zhōng", "hóng")),

    // Tones (diacritics)
    TONE1("ā", "˥", "tone1-high", listOf("mā", "shī")),
    TONE2("á", "˧˥", "tone2-rising", listOf("má", "shí")),
    TONE3("ǎ", "˨˩˦", "tone3-dip", listOf("mǎ", "shǐ")),
    TONE4("à", "˥˩", "tone4-falling", listOf("mà", "shì"));

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<PinyinPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<PinyinPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): PinyinPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Swahili spelling patterns (grapheme to IPA mappings)
 */
enum class SwahiliPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Prenasalized consonants
    MB("mb", "mb", "mb", listOf("mbwa", "jambo", "simba")),
    ND("nd", "nd", "nd", listOf("ndio", "panda", "mwandishi")),
    NG("ng", "ŋg", "ng", listOf("ngoma", "mwanga", "fungua")),
    NJ("nj", "ndʒ", "nj", listOf("njia", "mwanajeshi")),
    NZ("nz", "nz", "nz", listOf("nzuri", "mwanzilishi")),

    // NG' (velar nasal alone)
    NG_APOSTROPHE("ng'", "ŋ", "ng-velar", listOf("ng'ombe", "kung'oa")),

    // Digraphs
    CH("ch", "tʃ", "ch", listOf("chakula", "mchana", "kucheza")),
    SH("sh", "ʃ", "sh", listOf("shule", "kushukuru", "shamba")),
    DH("dh", "ð", "dh", listOf("dhambi", "dharau")),
    TH("th", "θ", "th", listOf("thelathini", "thamani")),
    GH("gh", "ɣ", "gh", listOf("ghali", "lugha")),

    // NY (palatal nasal)
    NY("ny", "ɲ", "ny", listOf("nyumba", "mnyama", "nyota")),

    // Common noun class prefixes
    M_PREFIX("m-", "m", "m-class", listOf("mtu", "mwalimu"), PatternPosition.START),
    WA_PREFIX("wa-", "wa", "wa-class", listOf("watu", "walimu"), PatternPosition.START),
    KI_PREFIX("ki-", "ki", "ki-class", listOf("kitu", "kitabu"), PatternPosition.START),
    VI_PREFIX("vi-", "vi", "vi-class", listOf("vitu", "vitabu"), PatternPosition.START),

    // Common endings
    A_FINAL("a", "a", "a-final", listOf("soma", "kula", "lala"), PatternPosition.END),
    I_FINAL("i", "i", "i-final", listOf("safi", "mali", "hali"), PatternPosition.END),
    U_FINAL("u", "u", "u-final", listOf("mtu", "siku", "nguvu"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<SwahiliPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<SwahiliPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): SwahiliPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Malay/Indonesian spelling patterns (grapheme to IPA mappings)
 */
enum class MalayPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Digraphs
    NG("ng", "ŋ", "ng", listOf("dengan", "orang", "bunga")),
    NY("ny", "ɲ", "ny", listOf("nyata", "banyak", "menyanyi")),
    SY("sy", "ʃ", "sy", listOf("syarat", "masyarakat", "syukur")),

    // Consonants
    C("c", "tʃ", "c", listOf("cari", "baca", "cuci")),
    J("j", "dʒ", "j", listOf("jalan", "belajar", "meja")),

    // Borrowed sounds (from Arabic)
    KH("kh", "x", "kh", listOf("khusus", "akhir", "khabar")),
    GH("gh", "ɣ", "gh", listOf("maghrib")),

    // Vowels
    E_PEPET("e", "ə", "e-pepet", listOf("empat", "dengan", "emas")),
    E_TALING("é", "e", "e-taling", listOf("bébék", "élok")),

    // Nasal assimilation prefixes (meN-)
    MEM("mem", "məm", "mem", listOf("membuat", "membeli"), PatternPosition.START),
    MEN("men", "mən", "men", listOf("menulis", "menari"), PatternPosition.START),
    MENG("meng", "məŋ", "meng", listOf("mengambil", "mengajar"), PatternPosition.START),
    MENY("meny", "məɲ", "meny", listOf("menyapu", "menyanyi"), PatternPosition.START),

    // Common affixes
    BER("ber-", "bər", "ber", listOf("berjalan", "berbicara"), PatternPosition.START),
    TER("ter-", "tər", "ter", listOf("terbang", "terbuka"), PatternPosition.START),
    KAN("-kan", "kan", "kan", listOf("berikan", "buatkan"), PatternPosition.END),
    I("-i", "i", "i-suffix", listOf("mengerti", "mendekati"), PatternPosition.END),
    AN("-an", "an", "an", listOf("makanan", "minuman"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<MalayPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<MalayPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): MalayPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}
