# YDoc Protocol Implementation

Kotlin implementation extending Yjs CRDT for real-time collaboration.

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      YKT (Yjs-Kotlin)                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐        │
│  │    QUERY     │   │    RANGE     │   │    MUTATE    │        │
│  ├──────────────┤   ├──────────────┤   ├──────────────┤        │
│  │ JsonPath     │   │ HTTP 206     │   │ JsonPatchOp  │        │
│  │ XPath        │   │ Content-Range│   │ RFC 6902     │        │
│  │ CssSelector  │   │ multipart    │   │ add/remove   │        │
│  └──────────────┘   └──────────────┘   └──────────────┘        │
│         │                  │                  │                 │
│         └──────────────────┼──────────────────┘                 │
│                            ▼                                    │
│                 ┌────────────────────┐                          │
│                 │   ProtoVectorClock │                          │
│                 │   CRDT Merge       │                          │
│                 └────────────────────┘                          │
│                            │                                    │
│         ┌──────────────────┼──────────────────┐                 │
│         ▼                  ▼                  ▼                 │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐        │
│  │     SSE      │   │     WSS      │   │   WebDAV     │        │
│  │  (audit)     │   │  (realtime)  │   │  (storage)   │        │
│  └──────────────┘   └──────────────┘   └──────────────┘        │
│                                                                 │
├─────────────────────────────────────────────────────────────────┤
│  WireFormat: JSON | MSGPACK | YDOC | SEGMENTS | XML            │
└─────────────────────────────────────────────────────────────────┘
```

## Wire Formats

| Format | Use Case | Encoding |
|--------|----------|----------|
| `JSON` | Default, human-readable | kotlinx.serialization |
| `MSGPACK` | Binary, compact | MessagePack header + JSON |
| `YDOC` | Yjs state vectors | Binary state encoding |
| `SEGMENTS` | HTTP 206 partial | Range byte slices |
| `XML` | Legacy systems | JSON-to-XML conversion |

## Core Types

### Value Classes (Zero Allocation)

```kotlin
@JvmInline value class SessionId(val value: String)
@JvmInline value class ClientId(val value: String)
@JvmInline value class RequestId(val value: String)
@JvmInline value class ResourceUri(val value: String)
@JvmInline value class JsonPointer(val value: String)
```

### CRDT Types

```kotlin
data class ProtoVersion(
    val clientId: ClientId,
    val clock: Long,
    val timestamp: Long = 0L
)

data class ProtoVectorClock(
    val clocks: Map<String, Long> = emptyMap()
) {
    fun tick(clientId: ClientId): ProtoVectorClock
    fun merge(other: ProtoVectorClock): ProtoVectorClock
    fun happensBefore(other: ProtoVectorClock): Boolean
}
```

### HTTP Range (RFC 7233)

```kotlin
data class Range(
    val start: Long,
    val end: Long,
    val total: Long? = null
) {
    fun toContentRange(unit: String = "bytes"): String
    fun toRangeHeader(unit: String = "bytes"): String
    
    companion object {
        fun parseRange(header: String): Range?      // "bytes=0-499"
        fun parseContentRange(header: String): Range? // "bytes 0-499/1000"
    }
}
```

## Query + Mutate Flow

```
Document ──┬── JsonPath.query("$.users[*].name") ──► Results
           │
           ├── XPath.query("//user/@id") ──────────► Results
           │
           └── CssSelector.query("div.user") ──────► Results
                              │
                              ▼
                    JsonPatchOp.Replace("/users/0/name", "New")
                              │
                              ▼
                    ProtoVectorClock.tick(clientId)
                              │
                              ▼
                    Broadcast via WSS / Log via SSE
```

## Protocol Stack

### MCP (Model Context Protocol)

```kotlin
sealed class McpMessage {
    data class Request(id: String, method: String, params: JsonObject?)
    data class Response(id: String, result: JsonElement?, error: McpError?)
    data class Notification(method: String, params: JsonObject?)
}
```

### WebDAV (RFC 4918)

```kotlin
enum class WebDavMethod {
    OPTIONS, GET, HEAD, PUT, DELETE, 
    MKCOL, COPY, MOVE, PROPFIND, PROPPATCH, LOCK, UNLOCK
}

data class DavLock(
    val token: String,
    val owner: String,
    val scope: DavLockScope,
    val depth: DavDepth
)
```

### Real-time Events

```kotlin
sealed class YktEvent : ProtoEvent {
    data class Patch(ops: List<YktPatchOp>, version: ProtoVersion)
    data class Awareness(cursor: Int?, selection: YktSelection?)
    data class Join(user: Map<String, String>)
    data class Leave(...)
    data class Sync(document: JsonObject, version: ProtoVectorClock)
    data class Ack(ackedVersion: ProtoVersion)
}
```

## Transport

| Transport | Direction | Use |
|-----------|-----------|-----|
| **WSS** | Bidirectional | Live collaboration |
| **SSE** | Server→Client | Audit trail, read-only |
| **WebDAV** | Request/Response | Document storage |

## Codecs

```kotlin
object ProtoWire {
    fun <T> encode(value: T, format: WireFormat): ByteArray
    fun <T> decode(data: ByteArray, format: WireFormat): T
    fun encodeSegments(data: ByteArray, segments: List<Range>): ByteArray
    fun encodeYDocUpdate(clientId: String, clock: Long, content: String): ByteArray
}

object YDocCodec {
    fun encodeUpdate(clientId: String, clock: Long, content: String): ByteArray
    fun decodeUpdate(data: ByteArray): Triple<Int, Int, String>?
    fun encodeStateVector(clocks: Map<String, Long>): ByteArray
    fun decodeStateVector(data: ByteArray): Map<Int, Long>
}

object RangeCodec {
    fun encodeMultipart(data: ByteArray, segments: List<Range>, ...): ByteArray
    fun parseRangeHeader(header: String): List<Range>
}
```

## Example: Collaborative Edit

```kotlin
// Client joins session
val client = YktClient(transport, ClientId.generate())
val session = client.join("/docs/readme.md")

// Apply local edit
client.applyPatch(listOf(
    YktPatchOp.Replace(JsonPointer("/content"), JsonPrimitive("Updated"))
))

// Update cursor awareness
client.updateAwareness(cursor = 42, selection = YktSelection(10, 20))

// Process incoming events
client.onEvent { event ->
    when (event) {
        is YktEvent.Patch -> applyRemotePatch(event.ops)
        is YktEvent.Awareness -> updateCursors(event)
        is YktEvent.Sync -> resetDocument(event.document)
    }
}
```
