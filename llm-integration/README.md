# LLM Integration Module

Local LLM integration for bilingual poetry translation, supporting multiple backends including DictaLM3 for Hebrew.

## Supported Backends

| Backend | Description | Best For |
|---------|-------------|----------|
| **LM Studio** | GUI-based local LLM runner with OpenAI-compatible API | Development, easy model management |
| **LangChain4j** | Java LLM orchestration framework | Chain composition, RAG |
| **Apache Camel AI** | Enterprise integration patterns for AI | Batch processing, routing |
| **JLama** | Pure Java GGUF inference | Portability, no native deps |
| **DJL** | Deep Java Library (PyTorch/ONNX) | Embeddings, small models |
| **Ollama** | CLI-based model runner | Simple deployment |

## Recommended Models

### Hebrew Translation
- **DictaLM3** (`dicta-il/dictalm2.0-instruct-GGUF`) - Best for Hebrew
- **Qwen2 7B** - Strong multilingual including Hebrew
- **Aya-23 8B** - Cohere's 23-language model

### General Translation
- **Llama 3.1 8B** - Meta's latest multilingual
- **Mistral 7B v0.3** - Fast and capable

## Quick Start

### Using LM Studio (Recommended)

1. Download and install [LM Studio](https://lmstudio.ai/)
2. Download DictaLM3 model: `dicta-il/dictalm2.0-instruct-GGUF`
3. Load the model and start the server (localhost:1234)

```kotlin
val service = TranslationService.withLmStudio(
    modelConfig = ModelConfig.DICTA_LM3
)

val result = service.translateLine(
    sourceLine = "בַּזֶּלֶת הַשְּׁחַרְחוֹרֶת",
    sourceLanguage = Language.HEBREW,
    targetLanguage = Language.ENGLISH
)
println(result.text) // "Darkened basalt"
```

### Using JLama (Pure Java)

```kotlin
val service = TranslationService.withJLama(
    modelConfig = ModelConfig.DICTA_LM3.copy(
        modelPath = "/path/to/dictalm3-q4_k_m.gguf"
    )
)
```

### Generate Translation Paths

```kotlin
val paths = service.generatePaths(
    stanza = listOf(
        "בַּזֶּלֶת הַשְּׁחַרְחוֹרֶת,",
        "תּוֹהָה, לֹא מְדַבֶּרֶת,"
    ),
    sourceLanguage = Language.HEBREW,
    targetLanguage = Language.ENGLISH
)

// Returns 6 translation strategies:
// 1. IPA Echo - Maximum sound fidelity
// 2. Literal - Denotative accuracy
// 3. Cultural - Source language register
// 4. Emotional - Match emotional arc
// 5. Idiom - Natural target language
// 6. Compress - Shortest, punchiest
```

## Configuration

```kotlin
val config = LlmConfig(
    defaultBackend = LlmBackend.LM_STUDIO,
    lmStudio = LmStudioConfig(
        baseUrl = "http://localhost:1234",
        defaultModel = "dictalm3"
    ),
    enableGpu = true,
    threads = 8
)
```

## GGUF Quantization

| Level | Bits | Size (7B) | Quality |
|-------|------|-----------|---------|
| Q4_K_M | 4.5 | ~4 GB | Recommended |
| Q5_K_M | 5.5 | ~5 GB | Better quality |
| Q6_K | 6.5 | ~6 GB | High quality |
| Q8_0 | 8 | ~8 GB | Near-lossless |

## Dependencies

- LangChain4j 0.35.0
- Apache Camel 4.4.0
- JLama 0.4.0
- DJL 0.27.0
- Ktor Client 2.3.9
