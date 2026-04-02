package al.clk.key.proto

import kotlinx.serialization.*
import kotlinx.serialization.json.*

// ═══════════════════════════════════════════════════════════════════════════════
// PROTOCOL ABSTRACTION - Unified wire protocol for MCP/WebDAV/Ykt
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Wire format for protocol messages.
 * Supports content negotiation via Accept header.
 */
@Serializable
enum class WireFormat {
    @SerialName("json") JSON,
    @SerialName("msgpack") MESSAGE_PACK,
    @SerialName("cbor") CBOR,
    @SerialName("xml") XML,
    @SerialName("ydoc") YDOC,           // Yjs/YDoc CRDT text format
    @SerialName("segments") SEGMENTS;    // HTTP Range partial content (206)

    /** MIME content type for this format */
    fun toContentType(): String = when (this) {
        JSON -> "application/json"
        MESSAGE_PACK -> "application/msgpack"
        CBOR -> "application/cbor"
        XML -> "application/xml"
        YDOC -> "application/x-ydoc"
        SEGMENTS -> "multipart/byteranges"
    }

    companion object {
        /** Parse Accept header to determine wire format */
        fun fromAccept(accept: String): WireFormat = when {
            accept.contains("application/json") -> JSON
            accept.contains("application/msgpack") -> MESSAGE_PACK
            accept.contains("application/cbor") -> CBOR
            accept.contains("application/xml") || accept.contains("text/xml") -> XML
            accept.contains("application/x-ydoc") -> YDOC
            accept.contains("multipart/byteranges") -> SEGMENTS
            else -> JSON // default
        }

        /** Parse Content-Type header to determine wire format */
        fun fromContentType(contentType: String): WireFormat = fromAccept(contentType)
    }
}

/**
 * HTTP Range segment for partial content (206 Partial Content).
 * Represents byte ranges per RFC 7233.
 */
@Serializable
data class Range(
    val start: Long,
    val end: Long,
    val total: Long? = null
) {
    val length get() = end - start + 1
    fun contains(pos: Long) = pos in start..end

    /** Format as Content-Range header value */
    fun toContentRange(unit: String = "bytes") =
        "$unit $start-$end${total?.let { "/$it" } ?: "/*"}"

    /** Format as Range header value */
    fun toRangeHeader(unit: String = "bytes") = "$unit=$start-$end"

    companion object {
        /** Parse Range header: "bytes=0-499" */
        fun parseRange(header: String): Range? {
            val match = Regex("""(\w+)=(\d+)-(\d*)""").find(header) ?: return null
            val start = match.groupValues[2].toLongOrNull() ?: return null
            val end = match.groupValues[3].toLongOrNull() ?: Long.MAX_VALUE
            return Range(start, end)
        }

        /** Parse Content-Range header: "bytes 0-499/1000" */
        fun parseContentRange(header: String): Range? {
            val match = Regex("""(\w+) (\d+)-(\d+)/(\d+|\*)""").find(header) ?: return null
            val start = match.groupValues[2].toLongOrNull() ?: return null
            val end = match.groupValues[3].toLongOrNull() ?: return null
            val total = match.groupValues[4].toLongOrNull()
            return Range(start, end, total)
        }

        fun bytes(start: Long, end: Long, total: Long? = null) = Range(start, end, total)
    }
}


// ═══════════════════════════════════════════════════════════════════════════════
// VALUE CLASSES - Zero allocation wrappers for IDs
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Value class for session IDs - zero allocation wrapper.
 */
@Serializable
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
value class ResourceUri(val value: String) {
    override fun toString() = value
    val scheme get() = value.substringBefore("://", "")
    val path get() = value.substringAfter("://", value)
}

/**
 * Value class for JSON Pointer paths (RFC 6901).
 */
@Serializable
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
 * Range codec for HTTP partial content (206).
 */
object RangeCodec {
    /** Encode multipart/byteranges response */
    fun encodeMultipart(data: ByteArray, segments: List<Range>, boundary: String, contentType: String): ByteArray {
        val result = StringBuilder()
        segments.forEach { seg ->
            val slice = data.copyOfRange(seg.start.toInt(), minOf(seg.end.toInt() + 1, data.size))
            result.append("--$boundary\r\n")
            result.append("Content-Type: $contentType\r\n")
            result.append("Content-Range: ${seg.toContentRange()}\r\n")
            result.append("\r\n")
            result.append(slice.decodeToString())
            result.append("\r\n")
        }
        result.append("--$boundary--\r\n")
        return result.toString().encodeToByteArray()
    }

    /** Parse Range header into segments */
    fun parseRangeHeader(header: String): List<Range> {
        val match = Regex("""(\w+)=(.+)""").find(header) ?: return emptyList()
        val ranges = match.groupValues[2].split(",").map { it.trim() }
        return ranges.mapNotNull { range ->
            val parts = range.split("-")
            when {
                parts.size == 2 && parts[0].isNotEmpty() && parts[1].isNotEmpty() ->
                    Range(parts[0].toLong(), parts[1].toLong())
                parts.size == 2 && parts[0].isNotEmpty() ->
                    Range(parts[0].toLong(), Long.MAX_VALUE)
                parts.size == 2 && parts[1].isNotEmpty() ->
                    Range(-parts[1].toLong(), -1)
                else -> null
            }
        }
    }

    /** Generate Content-Range header */
    fun contentRangeHeader(range: Range, unit: String = "bytes") = range.toContentRange(unit)
}


/**
 * YDoc codec for Yjs CRDT text format.
 * Encodes/decodes YDoc state vectors and updates.
 */
object YDocCodec {
    /**
     * Encode YDoc state as update binary.
     * Format: [clientId:4][clock:4][content...]
     */
    fun encodeUpdate(clientId: String, clock: Long, content: String): ByteArray {
        val clientBytes = clientId.hashCode().toBytes()
        val clockBytes = clock.toInt().toBytes()
        val contentBytes = content.encodeToByteArray()
        return clientBytes + clockBytes + contentBytes
    }

    /**
     * Decode YDoc update binary.
     */
    fun decodeUpdate(data: ByteArray): Triple<Int, Int, String>? {
        if (data.size < 8) return null
        val clientHash = data.copyOfRange(0, 4).toInt()
        val clock = data.copyOfRange(4, 8).toInt()
        val content = data.copyOfRange(8, data.size).decodeToString()
        return Triple(clientHash, clock, content)
    }

    /**
     * Encode state vector (client -> clock mapping).
     */
    fun encodeStateVector(clocks: Map<String, Long>): ByteArray {
        val buffer = mutableListOf<Byte>()
        buffer.addAll(clocks.size.toBytes().toList())
        clocks.forEach { (client, clock) ->
            buffer.addAll(client.hashCode().toBytes().toList())
            buffer.addAll(clock.toInt().toBytes().toList())
        }
        return buffer.toByteArray()
    }

    /**
     * Decode state vector.
     */
    fun decodeStateVector(data: ByteArray): Map<Int, Long> {
        if (data.size < 4) return emptyMap()
        val count = data.copyOfRange(0, 4).toInt()
        val result = mutableMapOf<Int, Long>()
        var offset = 4
        repeat(count) {
            if (offset + 8 <= data.size) {
                val clientHash = data.copyOfRange(offset, offset + 4).toInt()
                val clock = data.copyOfRange(offset + 4, offset + 8).toInt().toLong()
                result[clientHash] = clock
                offset += 8
            }
        }
        return result
    }

    private fun Int.toBytes() = byteArrayOf(
        (this shr 24).toByte(),
        (this shr 16).toByte(),
        (this shr 8).toByte(),
        this.toByte()
    )

    private fun ByteArray.toInt() =
        ((this[0].toInt() and 0xFF) shl 24) or
        ((this[1].toInt() and 0xFF) shl 16) or
        ((this[2].toInt() and 0xFF) shl 8) or
        (this[3].toInt() and 0xFF)
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
    @PublishedApi internal const val MSGPACK_FIXARRAY = 0x92.toByte()

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
        WireFormat.YDOC -> json.encodeToString(value).encodeToByteArray() // YDoc state as JSON
        WireFormat.SEGMENTS -> json.encodeToString(value).encodeToByteArray()
    }

    inline fun <reified T> decode(data: ByteArray, format: WireFormat = WireFormat.JSON): T = when (format) {
        WireFormat.JSON -> json.decodeFromString(data.decodeToString())
        WireFormat.MESSAGE_PACK -> MessagePack.decode(data)
        WireFormat.CBOR -> json.decodeFromString(data.decodeToString())
        WireFormat.XML -> json.decodeFromString(data.decodeToString())
        WireFormat.YDOC -> json.decodeFromString(data.decodeToString())
        WireFormat.SEGMENTS -> json.decodeFromString(data.decodeToString())
    }

    inline fun <reified T> encodeToString(value: T): String = json.encodeToString(value)
    inline fun <reified T> decodeFromString(data: String): T = json.decodeFromString(data)

    /** Encode multipart byte ranges (206 Partial Content) */
    fun encodeSegments(data: ByteArray, segments: List<Range>, boundary: String = "segment_boundary", contentType: String = "application/octet-stream"): ByteArray =
        RangeCodec.encodeMultipart(data, segments, boundary, contentType)

    /** Parse Range header */
    fun parseRangeHeader(header: String): List<Range> =
        RangeCodec.parseRangeHeader(header)

    /** Encode YDoc update */
    fun encodeYDocUpdate(clientId: String, clock: Long, content: String): ByteArray =
        YDocCodec.encodeUpdate(clientId, clock, content)

    /** Decode YDoc update */
    fun decodeYDocUpdate(data: ByteArray) = YDocCodec.decodeUpdate(data)
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
