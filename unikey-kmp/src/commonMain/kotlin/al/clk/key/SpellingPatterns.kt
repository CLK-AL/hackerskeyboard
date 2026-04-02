package al.clk.key

// ═══════════════════════════════════════════════════════════════════════════════
// LATIN-SCRIPT LANGUAGE SPELLING PATTERNS
// Each enum implements ISpellingPattern with IPA-based lookups
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * German spelling patterns (grapheme to IPA mappings)
 */
enum class GermanPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Consonant clusters
    SCH("sch", "ʃ", "sch", listOf("Schule", "schön", "Fisch")),
    TSCH("tsch", "tʃ", "tsch", listOf("Deutsch", "Tschechien", "Kutsche")),
    CH_ACH("ch", "x", "ch-ach", listOf("Bach", "Dach", "machen")),
    CH_ICH("ch", "ç", "ch-ich", listOf("ich", "nicht", "Mädchen")),
    CHS("chs", "ks", "chs", listOf("sechs", "wachsen", "Lachs")),
    CK("ck", "k", "ck", listOf("Brücke", "Stück", "Glück")),
    PF("pf", "pf", "pf", listOf("Pferd", "Apfel", "Kopf")),

    // Initial clusters
    SP_INITIAL("sp", "ʃp", "sp-initial", listOf("Sprache", "spielen", "Sport"), PatternPosition.START),
    ST_INITIAL("st", "ʃt", "st-initial", listOf("Straße", "stehen", "Stuhl"), PatternPosition.START),

    // Consonants
    Z("z", "ts", "z", listOf("Zeit", "zehn", "Herz")),
    V("v", "f", "v-f", listOf("Vater", "viel", "von")),
    W("w", "v", "w", listOf("Wasser", "Welt", "wir")),
    S_VOICED("s", "z", "s-voiced", listOf("Sonne", "sehr", "lesen"), PatternPosition.START),
    SS("ß", "s", "eszett", listOf("Straße", "groß", "heiß")),

    // Umlauts
    AE("ä", "ɛ", "a-umlaut", listOf("Mädchen", "Käse", "Bär")),
    OE("ö", "œ", "o-umlaut", listOf("schön", "Köln", "böse")),
    UE("ü", "y", "u-umlaut", listOf("grün", "für", "Tür")),

    // Vowel combinations
    IE("ie", "iː", "ie-long", listOf("Liebe", "Bier", "hier")),
    EI("ei", "aɪ", "ei", listOf("mein", "Stein", "zwei")),
    EU("eu", "ɔʏ", "eu", listOf("Freund", "heute", "neu")),
    AEU("äu", "ɔʏ", "aeu", listOf("Häuser", "Bäume", "träumen")),
    AU("au", "aʊ", "au", listOf("Haus", "Baum", "Frau")),

    // Long vowels with h
    AH("ah", "aː", "ah-long", listOf("fahren", "Jahr", "Bahn")),
    EH("eh", "eː", "eh-long", listOf("sehr", "mehr", "nehmen")),
    IH("ih", "iː", "ih-long", listOf("ihm", "ihr", "Vieh")),
    OH("oh", "oː", "oh-long", listOf("Sohn", "Ohr", "wohnen")),
    UH("uh", "uː", "uh-long", listOf("Uhr", "Kuh", "Schuh")),

    // Common endings
    TION("tion", "tsioːn", "tion", listOf("Nation", "Station", "Information"), PatternPosition.END),
    IG("ig", "ɪç", "ig", listOf("fertig", "wenig", "ruhig"), PatternPosition.END),
    LICH("lich", "lɪç", "lich", listOf("herzlich", "wirklich", "möglich"), PatternPosition.END),
    KEIT("keit", "kaɪt", "keit", listOf("Möglichkeit", "Freiheit"), PatternPosition.END),
    HEIT("heit", "haɪt", "heit", listOf("Gesundheit", "Sicherheit"), PatternPosition.END),
    SCHAFT("schaft", "ʃaft", "schaft", listOf("Freundschaft", "Gesellschaft"), PatternPosition.END),
    UNG("ung", "ʊŋ", "ung", listOf("Zeitung", "Wohnung", "Übung"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<GermanPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<GermanPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): GermanPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * French spelling patterns (grapheme to IPA mappings)
 */
enum class FrenchPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Vowel combinations
    EAU("eau", "o", "eau", listOf("beau", "eau", "gâteau")),
    AU("au", "o", "au", listOf("aussi", "autre", "faute")),
    OI("oi", "wa", "oi", listOf("moi", "roi", "trois")),
    OU("ou", "u", "ou", listOf("pour", "jour", "nous")),
    EU_CLOSED("eu", "ø", "eu-closed", listOf("deux", "jeu", "peu")),
    EU_OPEN("eu", "œ", "eu-open", listOf("peur", "heure", "seul")),
    OEU("œu", "œ", "oeu", listOf("cœur", "sœur", "œuf")),

    // Nasal vowels
    AN("an", "ɑ̃", "an-nasal", listOf("dans", "enfant", "grand")),
    EN("en", "ɑ̃", "en-nasal", listOf("vent", "enfant", "comment")),
    AIN("ain", "ɛ̃", "ain-nasal", listOf("pain", "main", "train"), PatternPosition.END),
    EIN("ein", "ɛ̃", "ein-nasal", listOf("plein", "sein"), PatternPosition.END),
    IN("in", "ɛ̃", "in-nasal", listOf("vin", "fin", "matin")),
    ON("on", "ɔ̃", "on-nasal", listOf("bon", "son", "maison")),
    UN("un", "œ̃", "un-nasal", listOf("un", "brun", "parfum")),

    // Consonants
    CH("ch", "ʃ", "ch", listOf("chat", "chose", "chercher")),
    GN("gn", "ɲ", "gn", listOf("montagne", "campagne", "signe")),
    QU("qu", "k", "qu", listOf("que", "qui", "quoi")),
    PH("ph", "f", "ph", listOf("photo", "philosophie", "pharmacie")),

    // Soft consonants
    C_SOFT("c", "s", "c-soft", listOf("ce", "cela", "merci")),
    G_SOFT("g", "ʒ", "g-soft", listOf("âge", "rouge", "manger")),

    // Combinations with i/y
    ILL("ill", "ij", "ill", listOf("fille", "famille", "brillant")),
    AIL("ail", "aj", "ail", listOf("travail", "soleil"), PatternPosition.END),
    EIL("eil", "ɛj", "eil", listOf("soleil", "réveil"), PatternPosition.END),

    // Silent letters
    H_SILENT("h", "", "h-silent", listOf("homme", "heure", "histoire"), PatternPosition.START),
    ENT_SILENT("ent", "", "ent-silent", listOf("parlent", "mangent", "aiment"), PatternPosition.END),

    // Common endings
    TION("tion", "sjɔ̃", "tion", listOf("nation", "action", "information"), PatternPosition.END),
    SION("sion", "zjɔ̃", "sion", listOf("télévision", "décision"), PatternPosition.END),
    EUR("eur", "œʁ", "eur", listOf("douleur", "docteur", "fleur"), PatternPosition.END),
    MENT("ment", "mɑ̃", "ment", listOf("vraiment", "moment", "comment"), PatternPosition.END),
    IQUE("ique", "ik", "ique", listOf("musique", "politique", "unique"), PatternPosition.END),
    OIRE("oire", "waʁ", "oire", listOf("histoire", "mémoire", "victoire"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<FrenchPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<FrenchPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): FrenchPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Spanish spelling patterns (grapheme to IPA mappings)
 */
enum class SpanishPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Consonants
    LL("ll", "ʎ", "ll", listOf("llamar", "calle", "ella")),
    LL_YEISMO("ll", "ʝ", "ll-yeismo", listOf("llamar", "calle", "ella")), // Latin American
    RR("rr", "r", "rr-trill", listOf("perro", "carro", "tierra")),
    CH("ch", "tʃ", "ch", listOf("chico", "noche", "mucho")),
    NY("ñ", "ɲ", "enye", listOf("niño", "año", "España")),

    // Soft consonants (Castilian)
    C_THETA("c", "θ", "c-theta", listOf("cena", "cinco", "gracias")),
    Z_THETA("z", "θ", "z-theta", listOf("zapato", "azul", "luz")),
    // Latin American seseo
    C_SESEO("c", "s", "c-seseo", listOf("cena", "cinco", "gracias")),
    Z_SESEO("z", "s", "z-seseo", listOf("zapato", "azul", "luz")),

    // J and G
    J("j", "x", "jota", listOf("jugar", "trabajo", "rojo")),
    G_SOFT("g", "x", "g-soft", listOf("gente", "elegir", "página")),

    // GU combinations
    GUE("gue", "ge", "gue", listOf("guerra", "juguete", "llegue")),
    GUI("gui", "gi", "gui", listOf("guitarra", "águila", "guisante")),
    GUE_UMLAUT("güe", "gwe", "gue-umlaut", listOf("vergüenza", "bilingüe")),
    GUI_UMLAUT("güi", "gwi", "gui-umlaut", listOf("pingüino", "lingüística")),

    // QU combinations
    QUE("que", "ke", "que", listOf("que", "porque", "parque")),
    QUI("qui", "ki", "qui", listOf("quiero", "aquí", "pequeño")),

    // Silent H
    H_SILENT("h", "", "h-silent", listOf("hola", "hacer", "ahora")),

    // Vowels
    Y_VOWEL("y", "i", "y-vowel", listOf("y", "muy", "hay")),

    // Common endings
    CION("ción", "sjon", "cion", listOf("nación", "acción", "información"), PatternPosition.END),
    DAD("dad", "ðað", "dad", listOf("ciudad", "verdad", "universidad"), PatternPosition.END),
    MENTE("mente", "mente", "mente", listOf("realmente", "normalmente"), PatternPosition.END),
    ERO("ero", "eɾo", "ero", listOf("dinero", "primero", "extranjero"), PatternPosition.END),
    ERA("era", "eɾa", "era", listOf("manera", "primera", "carrera"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<SpanishPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<SpanishPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): SpanishPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Italian spelling patterns (grapheme to IPA mappings)
 */
enum class ItalianPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // SC combinations
    SCE("sce", "ʃe", "sce", listOf("scena", "scelta", "pesce")),
    SCI("sci", "ʃi", "sci", listOf("scienza", "uscire", "piscina")),
    SCIA("scia", "ʃa", "scia", listOf("lasciare", "sciare", "fascia")),
    SCIO("scio", "ʃo", "scio", listOf("uscio", "liscio")),
    SCIU("sciu", "ʃu", "sciu", listOf("sciupare", "asciutto")),
    SCH("sch", "sk", "sch", listOf("schema", "scherzo", "fischio")),

    // GL combinations
    GLI("gli", "ʎi", "gli", listOf("famiglia", "figlio", "meglio")),

    // GN combination
    GN("gn", "ɲ", "gn", listOf("bagno", "sogno", "montagna")),

    // C combinations
    CE("ce", "tʃe", "ce", listOf("cena", "voce", "pace")),
    CI("ci", "tʃi", "ci", listOf("città", "cinema", "amici")),
    CIA("cia", "tʃa", "cia", listOf("ciao", "pancia", "camicia")),
    CIO("cio", "tʃo", "cio", listOf("bacio", "calcio", "ufficio")),
    CIU("ciu", "tʃu", "ciu", listOf("ciuffo", "fanciullo")),
    CH("ch", "k", "ch", listOf("che", "chi", "chiaro")),

    // G combinations
    GE("ge", "dʒe", "ge", listOf("gente", "gelato", "leggere")),
    GI("gi", "dʒi", "gi", listOf("giorno", "giro", "oggi")),
    GIA("gia", "dʒa", "gia", listOf("già", "spiaggia", "viaggio")),
    GIO("gio", "dʒo", "gio", listOf("giovane", "maggio", "orologio")),
    GIU("giu", "dʒu", "giu", listOf("giugno", "giusto", "giù")),
    GH("gh", "g", "gh", listOf("ghetto", "spaghetti", "funghi")),

    // QU combination
    QU("qu", "kw", "qu", listOf("questo", "quale", "quattro")),

    // Z variants
    Z_TS("z", "ts", "z-ts", listOf("pizza", "stanza", "grazie")),
    Z_DZ("z", "dz", "z-dz", listOf("zero", "zona", "zanzara")),

    // Double consonants (gemination)
    LL("ll", "lː", "ll-gem", listOf("bello", "quello", "cappello")),
    NN("nn", "nː", "nn-gem", listOf("anno", "donna", "penna")),
    TT("tt", "tː", "tt-gem", listOf("tutto", "notte", "detto")),
    PP("pp", "pː", "pp-gem", listOf("troppo", "gruppo", "cappuccino")),

    // Common endings
    ZIONE("zione", "tsjone", "zione", listOf("azione", "nazione", "stazione"), PatternPosition.END),
    MENTE("mente", "mente", "mente", listOf("veramente", "solamente"), PatternPosition.END),
    EZZA("ezza", "etːsa", "ezza", listOf("bellezza", "certezza"), PatternPosition.END),
    ERIA("eria", "eˈria", "eria", listOf("pizzeria", "pasticceria"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<ItalianPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<ItalianPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): ItalianPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Portuguese spelling patterns (grapheme to IPA mappings)
 */
enum class PortuguesePattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Digraphs
    LH("lh", "ʎ", "lh", listOf("filho", "olho", "trabalho")),
    NH("nh", "ɲ", "nh", listOf("minha", "senhor", "ninho")),
    CH("ch", "ʃ", "ch", listOf("chave", "chocolate", "fechar")),

    // X variants
    X_SH("x", "ʃ", "x-sh", listOf("xadrez", "caixa", "baixo")),
    X_KS("x", "ks", "x-ks", listOf("táxi", "fixo", "máximo")),
    X_Z("x", "z", "x-z", listOf("exame", "exemplo", "exercício")),
    X_S("x", "s", "x-s", listOf("próximo", "auxílio")),

    // Nasal vowels
    A_TIL("ã", "ɐ̃", "a-til", listOf("irmã", "manhã", "maçã")),
    O_TIL("õ", "õ", "o-til", listOf("limões", "corações")),
    AO("ão", "ɐ̃w̃", "ao-nasal", listOf("não", "pão", "irmão"), PatternPosition.END),
    OES("ões", "õjʃ", "oes-nasal", listOf("limões", "nações"), PatternPosition.END),
    AES("ães", "ɐ̃jʃ", "aes-nasal", listOf("pães", "mães", "cães"), PatternPosition.END),

    // RR
    RR("rr", "ʁ", "rr", listOf("carro", "terra", "arroz")),
    R_INITIAL("r", "ʁ", "r-initial", listOf("rato", "rio", "rua"), PatternPosition.START),

    // SS
    SS("ss", "s", "ss", listOf("pássaro", "pessoa", "assunto")),

    // C cedilla
    C_CEDILLA("ç", "s", "c-cedilla", listOf("cabeça", "açúcar", "coração")),

    // QU combinations
    QUE("que", "ke", "que", listOf("que", "porque", "parque")),
    QUI("qui", "ki", "qui", listOf("aqui", "quilo", "máquina")),

    // GU combinations
    GUE("gue", "ge", "gue", listOf("guerra", "segue")),
    GUI("gui", "gi", "gui", listOf("guitarra", "seguir")),

    // Common endings
    CAO("ção", "sɐ̃w̃", "cao", listOf("ação", "informação", "nação"), PatternPosition.END),
    DADE("dade", "daðɨ", "dade", listOf("cidade", "verdade", "universidade"), PatternPosition.END),
    MENTE("mente", "mẽtɨ", "mente", listOf("realmente", "certamente"), PatternPosition.END),
    AGEM("agem", "aʒẽj", "agem", listOf("viagem", "mensagem", "coragem"), PatternPosition.END),
    EIRO("eiro", "ejɾu", "eiro", listOf("brasileiro", "primeiro", "dinheiro"), PatternPosition.END),
    EIRA("eira", "ejɾa", "eira", listOf("maneira", "primeira", "bandeira"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<PortuguesePattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<PortuguesePattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): PortuguesePattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Dutch spelling patterns (grapheme to IPA mappings)
 */
enum class DutchPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Guttural sounds
    G("g", "x", "g-guttural", listOf("goed", "gaan", "zeggen")),
    CH("ch", "x", "ch-guttural", listOf("acht", "nacht", "lachen")),
    SCH("sch", "sx", "sch", listOf("school", "schrijven", "schip")),

    // IJ/EI
    IJ("ij", "ɛi", "ij", listOf("ijs", "wijn", "mijn")),
    EI("ei", "ɛi", "ei", listOf("klein", "trein", "geen")),

    // UI
    UI("ui", "œy", "ui", listOf("huis", "buiten", "fruit")),

    // EU
    EU("eu", "ø", "eu", listOf("neus", "deur", "keuken")),

    // OE
    OE("oe", "u", "oe", listOf("boek", "goed", "moeite")),

    // OU/AU
    OU("ou", "ɑu", "ou", listOf("oud", "koud", "houden")),
    AU("au", "ɑu", "au", listOf("auto", "blauw")),

    // Long vowels (doubled)
    AA("aa", "aː", "aa-long", listOf("naam", "straat", "gaan")),
    EE("ee", "eː", "ee-long", listOf("been", "twee", "veel")),
    OO("oo", "oː", "oo-long", listOf("groot", "rood", "ook")),
    UU("uu", "yː", "uu-long", listOf("uur", "muur", "duur")),
    IE("ie", "i", "ie", listOf("niet", "drie", "brief")),

    // W/V
    W("w", "ʋ", "w", listOf("water", "waar", "weten")),
    V("v", "v", "v", listOf("van", "veel", "voor")),

    // Common endings
    LIJK("lijk", "lək", "lijk", listOf("eigenlijk", "mogelijk", "moeilijk"), PatternPosition.END),
    HEID("heid", "hɛit", "heid", listOf("mogelijkheid", "vrijheid"), PatternPosition.END),
    ISCH("isch", "is", "isch", listOf("historisch", "praktisch"), PatternPosition.END),
    TIE("tie", "tsi", "tie", listOf("informatie", "situatie"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<DutchPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<DutchPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): DutchPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Polish spelling patterns (grapheme to IPA mappings)
 */
enum class PolishPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Sibilant clusters
    SZ("sz", "ʂ", "sz", listOf("szkoła", "pisz", "masz")),
    RZ("rz", "ʐ", "rz", listOf("rzeka", "morze", "przez")),
    CZ("cz", "tʂ", "cz", listOf("czas", "czarny", "człowiek")),
    DZ("dz", "dz", "dz", listOf("dzień", "bardzo")),
    DZ_SOFT("dź", "dʑ", "dz-soft", listOf("niedźwiedź", "dźwięk")),
    DZ_RETRO("dż", "dʐ", "dz-retro", listOf("dżem", "dżungla")),

    // Soft consonants
    S_SOFT("ś", "ɕ", "s-soft", listOf("świat", "dziś", "prosić")),
    Z_SOFT("ź", "ʑ", "z-soft", listOf("źródło", "późno")),
    C_SOFT("ć", "tɕ", "c-soft", listOf("ćma", "być", "robić")),
    N_SOFT("ń", "ɲ", "n-soft", listOf("koń", "dzień")),

    // Special letters
    L_STROKE("ł", "w", "l-stroke", listOf("mały", "szkoła", "łódź")),
    A_OGONEK("ą", "ɔ̃", "a-ogonek", listOf("są", "mąż", "rąk")),
    E_OGONEK("ę", "ɛ̃", "e-ogonek", listOf("się", "ręka", "będę")),
    O_ACUTE("ó", "u", "o-acute", listOf("mój", "który", "wróg")),

    // CH
    CH("ch", "x", "ch", listOf("chcieć", "chłopiec", "chodzić")),

    // Soft consonants with i
    CI("ci", "tɕi", "ci", listOf("ciało", "cichy", "ciekawy")),
    SI("si", "ɕi", "si", listOf("siedem", "siostra", "księżyc")),
    ZI("zi", "ʑi", "zi", listOf("zima", "zielony")),
    NI("ni", "ɲi", "ni", listOf("nie", "nic", "nigdy")),

    // Common endings
    OWOSC("owość", "ovɔɕtɕ", "owosc", listOf("możliwość", "pewność"), PatternPosition.END),
    ACJA("acja", "atsja", "acja", listOf("informacja", "sytuacja"), PatternPosition.END),
    SKI("ski", "ski", "ski", listOf("polski", "miejski"), PatternPosition.END),
    OWSKI("owski", "ɔfski", "owski", listOf("warszawski", "krakowski"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<PolishPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<PolishPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): PolishPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}

/**
 * Turkish spelling patterns (grapheme to IPA mappings)
 */
enum class TurkishPattern(
    override val pattern: String,
    override val ipa: String,
    override val displayName: String,
    override val examples: List<String>,
    override val position: PatternPosition = PatternPosition.ANY
) : ISpellingPattern {
    // Special consonants
    S_CEDILLA("ş", "ʃ", "s-cedilla", listOf("şimdi", "güneş", "şehir")),
    C_CEDILLA("ç", "tʃ", "c-cedilla", listOf("çok", "çocuk", "geçmek")),
    C_SOFT("c", "dʒ", "c-soft", listOf("cam", "cevap", "gece")),
    G_BREVE("ğ", "", "g-breve", listOf("dağ", "soğuk", "oğlu")), // Lengthens vowel

    // Dotted/dotless i
    I_DOTLESS("ı", "ɯ", "i-dotless", listOf("sıcak", "kış", "altı")),
    I_DOTTED("i", "i", "i-dotted", listOf("bir", "gitmek", "ilk")),

    // Umlauts
    O_UMLAUT("ö", "ø", "o-umlaut", listOf("göz", "köy", "söylemek")),
    U_UMLAUT("ü", "y", "u-umlaut", listOf("gün", "üç", "güzel")),

    // Consonant combinations
    SH("sh", "ʃ", "sh", listOf("şu", "şeker")), // Borrowed

    // Common endings (vowel harmony variants)
    LIK("lik", "lik", "lik", listOf("güzellik", "mutluluk"), PatternPosition.END),
    LUK("luk", "luk", "luk", listOf("çokluk", "soğukluk"), PatternPosition.END),
    LIK_I("lık", "lɯk", "lik-i", listOf("sıcaklık", "açıklık"), PatternPosition.END),

    CI("ci", "dʒi", "ci", listOf("çaycı", "gazeteci"), PatternPosition.END),
    CU("cu", "dʒu", "cu", listOf("yolcu", "kahveci"), PatternPosition.END),
    CI_I("cı", "dʒɯ", "ci-i", listOf("balıkçı", "kapıcı"), PatternPosition.END),

    LAR("lar", "laɾ", "lar", listOf("evler", "adamlar"), PatternPosition.END),
    LER("ler", "leɾ", "ler", listOf("eller", "gözler"), PatternPosition.END),

    DA("da", "da", "da", listOf("evde", "okulda"), PatternPosition.END),
    DE("de", "de", "de", listOf("elde", "evde"), PatternPosition.END),
    TA("ta", "ta", "ta", listOf("sokakta"), PatternPosition.END),
    TE("te", "te", "te", listOf("üstte"), PatternPosition.END);

    override val char: String get() = pattern

    companion object {
        private val byPattern = entries.groupBy { it.pattern }
        private val byIpa = entries.groupBy { it.ipa }

        fun fromPattern(p: String): List<TurkishPattern> = byPattern[p.lowercase()] ?: emptyList()
        fun fromIpa(ipa: String): List<TurkishPattern> = byIpa[ipa] ?: emptyList()

        fun matchEnding(word: String): TurkishPattern? {
            val w = word.lowercase()
            return entries
                .filter { it.position != PatternPosition.START }
                .sortedByDescending { it.pattern.length }
                .firstOrNull { w.endsWith(it.pattern) }
        }
    }
}
