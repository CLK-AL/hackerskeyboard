package al.clk.key

/**
 * Language codes with script association.
 * This is the single source of truth for language identification in forms maps.
 */
enum class Lang(val code: String, val script: Script, val baseLang: Lang? = null) {
    // Base languages
    HE("he", Script.HEBREW),
    EN("en", Script.LATIN),
    AR("ar", Script.ARABIC),
    EL("el", Script.GREEK),
    RU("ru", Script.CYRILLIC),
    HI("hi", Script.DEVANAGARI),
    JA("ja", Script.HIRAGANA),
    KO("ko", Script.HANGUL),
    ZH("zh", Script.CJK),
    // Latin-script languages
    DA("da", Script.LATIN),
    DE("de", Script.LATIN),
    ES("es", Script.LATIN),
    FI("fi", Script.LATIN),
    FR("fr", Script.LATIN),
    IT("it", Script.LATIN),
    MS("ms", Script.LATIN),
    NL("nl", Script.LATIN),
    NO("no", Script.LATIN),
    PL("pl", Script.LATIN),
    PT("pt", Script.LATIN),
    SV("sv", Script.LATIN),
    SW("sw", Script.LATIN),
    TR("tr", Script.LATIN),
    // Regional variants
    EN_GB("en-gb", Script.LATIN, EN),
    EN_AU("en-au", Script.LATIN, EN),
    EN_CA("en-ca", Script.LATIN, EN),
    EN_IN("en-in", Script.LATIN, EN),
    PT_BR("pt-br", Script.LATIN, PT),
    ES_419("es-419", Script.LATIN, ES);

    /** Is this a regional variant? */
    val isVariant: Boolean get() = baseLang != null

    /**
     * Get the IPA for a word ending using this language's spelling patterns.
     * Returns null if no pattern matches or language has no spelling patterns.
     */
    fun matchEndingIpa(word: String): String? = LangPatterns.matchEnding(this, word)

    companion object {
        private val byCode = entries.associateBy { it.code }
        fun fromCode(code: String): Lang? = byCode[code.lowercase()]

        /** Get base languages only (no regional variants) */
        val baseLanguages: List<Lang> get() = entries.filter { !it.isVariant }
    }
}

/**
 * Centralized pattern matching for all languages.
 * This is the single source of truth for language→pattern enum mappings.
 * Pattern enums (XXXPattern.entries) are the truth center for IPA data.
 */
object LangPatterns {
    /**
     * Match word ending using the appropriate pattern enum for this language.
     * Pattern enums use entries.associateBy { it.ipa } internally.
     */
    fun matchEnding(lang: Lang, word: String): String? {
        // Use base language for variants
        val baseLang = lang.baseLang ?: lang
        return when (baseLang) {
            // Latin-script languages with dedicated pattern enums
            Lang.EN -> EnglishPattern.matchEnding(word)?.ipa
            Lang.DE -> GermanPattern.matchEnding(word)?.ipa
            Lang.FR -> FrenchPattern.matchEnding(word)?.ipa
            Lang.ES -> SpanishPattern.matchEnding(word)?.ipa
            Lang.IT -> ItalianPattern.matchEnding(word)?.ipa
            Lang.PT -> PortuguesePattern.matchEnding(word)?.ipa
            Lang.NL -> DutchPattern.matchEnding(word)?.ipa
            Lang.PL -> PolishPattern.matchEnding(word)?.ipa
            Lang.TR -> TurkishPattern.matchEnding(word)?.ipa
            Lang.DA -> DanishPattern.matchEnding(word)?.ipa
            Lang.FI -> FinnishPattern.matchEnding(word)?.ipa
            Lang.NO -> NorwegianPattern.matchEnding(word)?.ipa
            Lang.SV -> SwedishPattern.matchEnding(word)?.ipa
            Lang.MS -> MalayPattern.matchEnding(word)?.ipa
            Lang.SW -> SwahiliPattern.matchEnding(word)?.ipa
            // Non-Latin scripts with dedicated pattern enums
            Lang.EL -> GreekPattern.matchEnding(word)?.ipa
            Lang.RU -> RussianPattern.matchEnding(word)?.ipa
            Lang.ZH -> PinyinPattern.matchEnding(word)?.ipa
            // Scripts using letter-based IPA (no spelling patterns needed)
            Lang.HE -> null  // Uses HebrewLetter.entries directly
            Lang.AR -> null  // Uses ArabicLetter.entries directly
            Lang.HI -> null  // Uses DevanagariKey.entries directly
            Lang.JA -> null  // Uses HiraganaKey.entries directly
            Lang.KO -> null  // Uses HangulInitial/Vowel/Final directly
            // Variants handled by baseLang above
            else -> null
        }
    }

    /**
     * Check if a language has dedicated spelling patterns.
     */
    fun hasSpellingPatterns(lang: Lang): Boolean {
        val baseLang = lang.baseLang ?: lang
        return baseLang in listOf(
            Lang.EN, Lang.DE, Lang.FR, Lang.ES, Lang.IT, Lang.PT, Lang.NL, Lang.PL, Lang.TR,
            Lang.DA, Lang.FI, Lang.NO, Lang.SV, Lang.MS, Lang.SW,
            Lang.EL, Lang.RU, Lang.ZH
        )
    }
}

/**
 * Script types for keyboard layouts
 */
enum class Script {
    HEBREW, LATIN, ARABIC, GREEK, DEVANAGARI, CYRILLIC, HANGUL, HIRAGANA, CJK, UNKNOWN
}

/**
 * Common interface for keyboard keys with phonetic properties.
 * Enums implement this to be the single source of truth for key data.
 */
interface ILayoutKey {
    val char: String
    val ipa: String
    val displayName: String

    /** Key to use when shift is pressed (default: uppercase char) */
    val shiftKey: ILayoutKey? get() = null

    /** Key to use when ctrl is pressed */
    val ctrlKey: ILayoutKey? get() = null

    /** Key to use when alt is pressed */
    val altKey: ILayoutKey? get() = null

    /** Get key for modifier */
    fun withModifier(mod: Modifier): ILayoutKey? = when (mod) {
        Modifier.SHIFT -> shiftKey
        Modifier.CTRL -> ctrlKey
        Modifier.ALT -> altKey
        else -> null  // Combined modifiers not supported by default
    }
}

/**
 * Interface for spelling pattern enums across all languages.
 * Extends ILayoutKey to integrate with keyboard layouts.
 */
interface ISpellingPattern : ILayoutKey {
    /** The written spelling (grapheme) */
    val pattern: String

    /** Example words demonstrating this pattern */
    val examples: List<String>

    /** Where in a word this pattern typically occurs */
    val position: PatternPosition

    /** The char property defaults to pattern */
    override val char: String get() = pattern
}

/**
 * Hebrew spelling pattern supporting male (full), haser (defective), and unpointed variants.
 * Nikud male is the base/canonical form since it contains the most information.
 */
interface IHebrewPattern : ISpellingPattern {
    /** Base form with nikud male (full spelling with mater lectionis) */
    val male: String

    /** Haser form (defective spelling without mater lectionis) */
    val haser: String

    /** Unpointed form (consonants only, no nikud) */
    val unpointed: String

    /** Default pattern is male form (canonical) */
    override val pattern: String get() = male
}

/**
 * Arabic spelling pattern supporting forms with and without harakat (vowel marks).
 */
interface IArabicPattern : ISpellingPattern {
    /** Form with full harakat (vowel diacritics) */
    val withHarakat: String

    /** Form without harakat (consonants only) */
    val withoutHarakat: String

    /** Default pattern is with harakat (canonical) */
    override val pattern: String get() = withHarakat
}

/**
 * Form of Hebrew nikud spelling
 */
enum class NikudForm {
    MALE,      // Full spelling with mater lectionis (שָׁלוֹם)
    HASER,     // Defective without mater lectionis (שָׁלֹם)
    UNPOINTED  // No nikud marks (שלום)
}

/**
 * Generic pattern category for spelling patterns across all languages.
 */
enum class PatternCategory {
    VOWEL,      // Vowel patterns (nikud, harakat, diphthongs)
    CONSONANT,  // Consonant combinations (clusters, digraphs)
    PREFIX,     // Prefix patterns
    SUFFIX,     // Suffix patterns
    ROOT,       // Root/stem patterns (verb forms, etc.)
    WORD        // Common word patterns
}

/**
 * Simple implementation of ILayoutKey for ad-hoc keys (punctuation, numbers)
 */
data class SimpleKey(
    override val char: String,
    override val ipa: String,
    override val displayName: String,
    override val shiftKey: ILayoutKey? = null
) : ILayoutKey

/**
 * Currency symbols by region for keyboard layouts
 */
enum class Currency(
    val symbol: String,
    val displayName: String,
    val langs: List<Lang>
) {
    DOLLAR("$", "dollar", listOf(Lang.EN, Lang.EN_AU, Lang.EN_CA, Lang.ES_419)),
    POUND("£", "pound", listOf(Lang.EN_GB)),
    EURO("€", "euro", listOf(Lang.DE, Lang.EL, Lang.ES, Lang.FI, Lang.FR, Lang.IT, Lang.NL, Lang.PT)),
    RUPEE("₹", "rupee", listOf(Lang.HI, Lang.EN_IN)),
    SHEKEL("₪", "shekel", listOf(Lang.HE)),
    YEN("¥", "yen", listOf(Lang.JA, Lang.ZH)),
    WON("₩", "won", listOf(Lang.KO)),
    RUBLE("₽", "ruble", listOf(Lang.RU)),
    LIRA("₺", "lira", listOf(Lang.TR)),
    ZLOTY("zł", "zloty", listOf(Lang.PL)),
    KRONE("kr", "krone", listOf(Lang.DA, Lang.NO, Lang.SV)),
    REAL("R$", "real", listOf(Lang.PT_BR)),
    RINGGIT("RM", "ringgit", listOf(Lang.MS)),
    SHILLING("TSh", "shilling", listOf(Lang.SW));

    companion object {
        private val byLang = entries.flatMap { c -> c.langs.map { it to c } }.toMap()

        /** Get currency for a Lang (preferred) */
        fun forLang(lang: Lang): Currency = byLang[lang] ?: DOLLAR

        /** Get currency by language code string */
        @Deprecated("Use forLang(Lang) instead", ReplaceWith("forLang(Lang.fromCode(code)!!)"))
        fun forRegion(code: String): Currency = Lang.fromCode(code)?.let { byLang[it] } ?: DOLLAR
    }
}
