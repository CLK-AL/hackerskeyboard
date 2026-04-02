package al.clk.llm.provider

import al.clk.llm.*
import al.clk.llm.config.LlmConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.apache.camel.CamelContext
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.ProducerTemplate

/**
 * Apache Camel AI integration provider.
 * Uses Camel routes for LLM orchestration with support for:
 * - LangChain4j components
 * - HTTP-based LLM APIs
 * - Message transformation and routing
 */
class CamelAiProvider(
    override val modelConfig: ModelConfig,
    private val config: LlmConfig = LlmConfig.DEFAULT
) : LlmProvider {

    override val backend = LlmBackend.CAMEL_AI

    private var camelContext: CamelContext? = null
    private var producerTemplate: ProducerTemplate? = null

    override suspend fun isReady(): Boolean = camelContext?.isStarted == true

    override suspend fun initialize() = withContext(Dispatchers.IO) {
        val context = DefaultCamelContext()

        // Add routes for LLM communication
        context.addRoutes(createLlmRoutes())

        context.start()
        camelContext = context
        producerTemplate = context.createProducerTemplate()
    }

    private fun createLlmRoutes(): RouteBuilder = object : RouteBuilder() {
        override fun configure() {
            // Route for chat completions via LM Studio
            from("direct:lmstudio-chat")
                .setHeader("Content-Type", constant("application/json"))
                .to("http://${config.lmStudio.baseUrl.removePrefix("http://")}" +
                    "${config.lmStudio.apiPath}/chat/completions" +
                    "?httpMethod=POST")

            // Route for chat completions via Ollama
            from("direct:ollama-chat")
                .setHeader("Content-Type", constant("application/json"))
                .to("http://${config.ollama.baseUrl.removePrefix("http://")}" +
                    "/api/chat?httpMethod=POST")

            // Translation-specific route with retry
            from("direct:translate")
                .errorHandler(defaultErrorHandler()
                    .maximumRedeliveries(3)
                    .redeliveryDelay(1000))
                .choice()
                    .`when`(header("backend").isEqualTo("lmstudio"))
                        .to("direct:lmstudio-chat")
                    .`when`(header("backend").isEqualTo("ollama"))
                        .to("direct:ollama-chat")
                .end()

            // Batch translation route
            from("direct:translate-batch")
                .split(body())
                .to("direct:translate")
                .aggregate(header("batchId"))
                .completionSize(header("batchSize"))
                .to("direct:batch-complete")
        }
    }

    override suspend fun complete(request: CompletionRequest): CompletionResponse =
        withContext(Dispatchers.IO) {
            val template = producerTemplate
                ?: throw IllegalStateException("Provider not initialized")

            try {
                val requestBody = buildChatRequestBody(request)

                val response = template.requestBodyAndHeader(
                    "direct:translate",
                    requestBody,
                    "backend",
                    if (config.defaultBackend == LlmBackend.LM_STUDIO) "lmstudio" else "ollama"
                ) as String

                parseCompletionResponse(response)
            } catch (e: Exception) {
                CompletionResponse(
                    content = "Camel route error: ${e.message}",
                    finishReason = CompletionResponse.FinishReason.ERROR
                )
            }
        }

    private fun buildChatRequestBody(request: CompletionRequest): String {
        val messagesJson = request.messages.joinToString(",") { msg ->
            """{"role":"${msg.role.name.lowercase()}","content":"${escapeJson(msg.content)}"}"""
        }

        return """{
            "model": "${modelConfig.modelId}",
            "messages": [$messagesJson],
            "max_tokens": ${request.maxTokens},
            "temperature": ${request.temperature},
            "top_p": ${request.topP},
            "stream": false
        }"""
    }

    private fun escapeJson(text: String): String {
        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
    }

    private fun parseCompletionResponse(json: String): CompletionResponse {
        // Simple JSON parsing for the response
        val contentRegex = """"content"\s*:\s*"([^"]*(?:\\.[^"]*)*)"""".toRegex()
        val match = contentRegex.find(json)
        val content = match?.groupValues?.get(1)
            ?.replace("\\n", "\n")
            ?.replace("\\\"", "\"")
            ?.replace("\\\\", "\\")
            ?: "No content in response"

        return CompletionResponse(
            content = content,
            finishReason = CompletionResponse.FinishReason.STOP
        )
    }

    override fun completeStream(request: CompletionRequest): Flow<String> = flow {
        val response = complete(request)
        emit(response.content)
    }

    override suspend fun shutdown() = withContext(Dispatchers.IO) {
        producerTemplate?.stop()
        camelContext?.stop()
        camelContext = null
        producerTemplate = null
    }

    companion object {
        /** Create Camel AI provider with default config */
        fun create(
            modelConfig: ModelConfig = ModelConfig.DICTA_LM3,
            config: LlmConfig = LlmConfig.DEFAULT
        ) = CamelAiProvider(modelConfig, config)
    }
}
