package al.clk.key.proto

import kotlinx.serialization.*
import kotlinx.serialization.json.*

// ═══════════════════════════════════════════════════════════════════════════════
// PROTOCOL ABSTRACTION - Unified wire protocol for MCP/WebDAV/Mushi
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Wire format for protocol messages.
 */
@Serializable
enum class WireFormat {
    @SerialName("json") JSON,
    @SerialName("msgpack") MESSAGE_PACK,
    @SerialName("cbor") CBOR,
    @SerialName("xml") XML,
    @SerialName("text") TEXT,
    @SerialName("lines") LINE_RANGES
}

/**
 * Line range for text-based wire format.
 */
@Serializable
data class LineRange(
    val start: Int,
    val end: Int,
    val content: String? = null
) {
    val length get() = end - start + 1
    fun contains(line: Int) = line in start..end
    fun toIntRange() = start..end

    companion object {
        fun single(line: Int, content: String? = null) = LineRange(line, line, content)
        fun parse(spec: String): LineRange? {
            val parts = spec.split("-", limit = 2)
            return when (parts.size) {
                1 -> parts[0].toIntOrNull()?.let { single(it) }
                2 -> {
                    val start = parts[0].toIntOrNull() ?: return null
                    val end = parts[1].toIntOrNull() ?: return null
                    LineRange(start, end)
                }
                else -> null
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// VALUE CLASSES - Zero allocation wrappers for IDs
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Value class for session IDs - zero allocation wrapper.
 */
@Serializable
@JvmInline
value class SessionId(val value: String) {
    override fun toString() = value
    companion object {
        fun generate() = SessionId("sess_${randomId(12)}")
    }
}

/**
 * Value class for client IDs - zero allocation wrapper.
 */
@Serializable
@JvmInline
value class ClientId(val value: String) {
    override fun toString() = value
    companion object {
        fun generate() = ClientId("client_${randomId(8)}")
    }
}

/**
 * Value class for request IDs - zero allocation wrapper.
 */
@Serializable
@JvmInline
value class RequestId(val value: String) {
    override fun toString() = value
    companion object {
        private var counter = 0L
        fun next() = RequestId("req_${++counter}")
    }
}

/**
 * Value class for resource URIs - zero allocation wrapper.
 */
@Serializable
@JvmInline
value class ResourceUri(val value: String) {
    override fun toString() = value
    val scheme get() = value.substringBefore("://", "")
    val path get() = value.substringAfter("://", value)
}

/**
 * Value class for JSON Pointer paths (RFC 6901).
 */
@Serializable
@JvmInline
value class JsonPointer(val value: String) {
    override fun toString() = value
    val segments get() = value.split("/").drop(1).map { it.replace("~1", "/").replace("~0", "~") }
    companion object {
        fun of(vararg parts: String) = JsonPointer("/" + parts.joinToString("/") {
            it.replace("~", "~0").replace("/", "~1")
        })
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// WIRE CODECS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Text line codec for LINE_RANGES format.
 */
object TextLineCodec {
    fun encode(lines: List<String>, ranges: List<LineRange>? = null): String {
        return if (ranges != null) {
            ranges.mapNotNull { r ->
                val selected = lines.subList(
                    maxOf(0, r.start - 1),
                    minOf(lines.size, r.end)
                )
                if (selected.isNotEmpty()) {
                    "@@ -${r.start},${r.length} @@\n${selected.joinToString("\n")}"
                } else null
            }.joinToString("\n\n")
        } else {
            lines.joinToString("\n")
        }
    }

    fun decode(text: String): Pair<List<String>, List<LineRange>> {
        val lines = mutableListOf<String>()
        val ranges = mutableListOf<LineRange>()
        val chunks = text.split(Regex("""@@ -(\d+),(\d+) @@\n?"""))

        if (chunks.size == 1) {
            return text.lines() to listOf(LineRange(1, text.lines().size))
        }

        val rangePattern = Regex("""@@ -(\d+),(\d+) @@""")
        rangePattern.findAll(text).forEach { match ->
            val start = match.groupValues[1].toInt()
            val length = match.groupValues[2].toInt()
            ranges.add(LineRange(start, start + length - 1))
        }

        chunks.drop(1).forEach { chunk ->
            lines.addAll(chunk.trimStart('\n').lines())
        }

        return lines to ranges
    }
}

/**
 * XML codec for XML wire format.
 */
object XmlCodec {
    inline fun <reified T> encode(value: T, rootTag: String = "root"): String {
        val jsonStr = ProtoWire.json.encodeToString(value)
        return jsonToXml(jsonStr, rootTag)
    }

    fun jsonToXml(json: String, rootTag: String = "root"): String {
        val element = ProtoWire.json.parseToJsonElement(json)
        return buildString {
            append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            appendElement(rootTag, element)
        }
    }

    private fun StringBuilder.appendElement(tag: String, element: JsonElement) {
        when (element) {
            is JsonPrimitive -> {
                append("<$tag>")
                append(escapeXml(element.content))
                append("</$tag>")
            }
            is JsonArray -> {
                element.forEach { item ->
                    appendElement(tag, item)
                    append("\n")
                }
            }
            is JsonObject -> {
                append("<$tag>")
                element.forEach { (key, value) ->
                    append("\n  ")
                    appendElement(key, value)
                }
                append("\n</$tag>")
            }
            is JsonNull -> append("<$tag/>")
        }
    }

    private fun escapeXml(s: String) = s
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
}

/**
 * MessagePack codec (simplified - full impl needs library).
 */
object MessagePack {
    private const val MSGPACK_FIXARRAY = 0x92.toByte()

    inline fun <reified T> encode(value: T): ByteArray {
        val jsonBytes = ProtoWire.json.encodeToString(value).encodeToByteArray()
        return byteArrayOf(MSGPACK_FIXARRAY) + jsonBytes
    }

    inline fun <reified T> decode(data: ByteArray): T {
        val jsonBytes = if (data.isNotEmpty() && data[0] == MSGPACK_FIXARRAY)
            data.copyOfRange(1, data.size) else data
        return ProtoWire.json.decodeFromString(jsonBytes.decodeToString())
    }
}

/**
 * Unified protocol wire codec.
 */
object ProtoWire {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        classDiscriminator = "type"
        isLenient = true
    }

    inline fun <reified T> encode(value: T, format: WireFormat = WireFormat.JSON): ByteArray = when (format) {
        WireFormat.JSON -> json.encodeToString(value).encodeToByteArray()
        WireFormat.MESSAGE_PACK -> MessagePack.encode(value)
        WireFormat.CBOR -> json.encodeToString(value).encodeToByteArray()
        WireFormat.XML -> XmlCodec.encode(value).encodeToByteArray()
        WireFormat.TEXT, WireFormat.LINE_RANGES -> json.encodeToString(value).encodeToByteArray()
    }

    inline fun <reified T> decode(data: ByteArray, format: WireFormat = WireFormat.JSON): T = when (format) {
        WireFormat.JSON -> json.decodeFromString(data.decodeToString())
        WireFormat.MESSAGE_PACK -> MessagePack.decode(data)
        WireFormat.CBOR -> json.decodeFromString(data.decodeToString())
        WireFormat.XML, WireFormat.TEXT, WireFormat.LINE_RANGES -> json.decodeFromString(data.decodeToString())
    }

    inline fun <reified T> encodeToString(value: T): String = json.encodeToString(value)
    inline fun <reified T> decodeFromString(data: String): T = json.decodeFromString(data)

    fun encodeLines(lines: List<String>, ranges: List<LineRange>? = null): ByteArray =
        TextLineCodec.encode(lines, ranges).encodeToByteArray()

    fun decodeLines(data: ByteArray): Pair<List<String>, List<LineRange>> =
        TextLineCodec.decode(data.decodeToString())
}

// ═══════════════════════════════════════════════════════════════════════════════
// PROTOCOL INTERFACES
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Protocol message interface - common to all protocols.
 */
interface ProtoMessage {
    val timestamp: Long get() = 0L
}

/**
 * Protocol event interface - for event-driven protocols.
 */
interface ProtoEvent : ProtoMessage {
    val eventType: String
}

/**
 * Protocol request/response interface - for RPC protocols.
 */
interface ProtoRpc : ProtoMessage {
    val id: RequestId
}

/**
 * Common result type for protocol operations.
 */
@Serializable
sealed class ProtoResult<out T> {
    @Serializable @SerialName("ok")
    data class Ok<T>(val value: T) : ProtoResult<T>()
    @Serializable @SerialName("err")
    data class Err(val code: Int, val message: String, val data: JsonElement? = null) : ProtoResult<Nothing>()

    val isOk get() = this is Ok
    val isErr get() = this is Err
    fun getOrNull(): T? = (this as? Ok)?.value
    fun getOrThrow(): T = (this as? Ok)?.value ?: error((this as Err).message)

    companion object {
        fun <T> ok(value: T): ProtoResult<T> = Ok(value)
        fun err(code: Int, message: String, data: JsonElement? = null): ProtoResult<Nothing> = Err(code, message, data)
    }
}

/**
 * Common version tracking for CRDT operations.
 */
@Serializable
data class ProtoVersion(
    val clientId: ClientId,
    val clock: Long,
    val timestamp: Long = 0L
) {
    val formatted get() = "${clientId.value}:$clock"

    companion object {
        fun parse(s: String): ProtoVersion? {
            val parts = s.split(":")
            return if (parts.size == 2) {
                ProtoVersion(ClientId(parts[0]), parts[1].toLongOrNull() ?: return null)
            } else null
        }
    }
}

/**
 * Common vector clock for CRDT sync.
 */
@Serializable
data class ProtoVectorClock(
    val clocks: Map<String, Long> = emptyMap()
) {
    operator fun get(clientId: String) = clocks[clientId] ?: 0L
    operator fun get(clientId: ClientId) = clocks[clientId.value] ?: 0L

    fun tick(clientId: ClientId): ProtoVectorClock {
        val current = this[clientId]
        return copy(clocks = clocks + (clientId.value to current + 1))
    }

    fun merge(other: ProtoVectorClock): ProtoVectorClock {
        val merged = (clocks.keys + other.clocks.keys).associateWith { key ->
            maxOf(clocks[key] ?: 0L, other.clocks[key] ?: 0L)
        }
        return ProtoVectorClock(merged)
    }

    fun happensBefore(other: ProtoVectorClock): Boolean =
        clocks.all { (k, v) -> v <= (other.clocks[k] ?: 0L) } &&
        clocks.any { (k, v) -> v < (other.clocks[k] ?: 0L) }
}

// Utility
private fun randomId(len: Int): String = (1..len).map { "abcdefghijklmnopqrstuvwxyz0123456789".random() }.joinToString("")
