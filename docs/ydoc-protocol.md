# YKT Protocol Specification

Extends [Yjs CRDT](https://github.com/yjs/yjs) with query selectors and HTTP range support.

## References

| Spec | Use |
|------|-----|
| [Yjs Internals](https://github.com/yjs/yjs/blob/main/INTERNALS.md) | CRDT structure, state vectors |
| [y-protocols](https://github.com/yjs/y-protocols) | Sync, awareness, auth |
| [RFC 6902](https://datatracker.ietf.org/doc/html/rfc6902) | JSON Patch operations |
| [RFC 9535](https://datatracker.ietf.org/doc/html/rfc9535) | JSONPath query |
| [RFC 7233](https://datatracker.ietf.org/doc/html/rfc7233) | HTTP Range requests |
| [RFC 4918](https://datatracker.ietf.org/doc/html/rfc4918) | WebDAV |

## Yjs Core Concepts

### State Vector
```
StateVector = Map<ClientID, Clock>
```
Each client maintains monotonic clock. Vector comparison determines causal order.

### Update Format
```
Update = [ClientID, Clock, Content, OriginLeft, OriginRight, Parent, ParentSub]
```
CRDT item with unique ID and origin pointers for conflict-free merge.

### Sync Protocol (y-protocols)
```
Step 1: Client → Server: SyncStep1(stateVector)
Step 2: Server → Client: SyncStep2(stateVector, update)
Step 3: Client → Server: SyncStep2(update)
```

## YKT Extensions

### 1. Query Selectors

Yjs operates on known paths. YKT adds query-based targeting:

```
┌─────────────────────────────────────────────────┐
│                  YKT QUERY LAYER                │
├─────────────────────────────────────────────────┤
│  JsonPath   │  $.users[?(@.active)].name       │
│  XPath      │  //user[@role='admin']           │
│  CSS        │  div.user[data-active]           │
├─────────────────────────────────────────────────┤
│                      ▼                          │
│              Resolved Paths                     │
│         /users/0/name, /users/2/name           │
├─────────────────────────────────────────────────┤
│                      ▼                          │
│             Yjs Y.Map / Y.Array                 │
└─────────────────────────────────────────────────┘
```

### 2. HTTP Range Integration

Yjs uses binary encoding. YKT adds HTTP 206 partial content:

```
GET /doc/readme.md
Range: bytes=0-1023

HTTP/1.1 206 Partial Content
Content-Range: bytes 0-1023/4096
```

Maps to Yjs state:
```kotlin
data class Range(
    val start: Long,   // byte offset
    val end: Long,     // byte offset  
    val total: Long?   // total size
)
```

### 3. JsonPatch Operations

Yjs uses internal ops. YKT exposes RFC 6902 interface:

| Yjs Op | JsonPatch Op | YKT Mapping |
|--------|--------------|-------------|
| Y.Map.set | add/replace | `YktPatchOp.Add`, `YktPatchOp.Replace` |
| Y.Map.delete | remove | `YktPatchOp.Remove` |
| Y.Array.insert | add | `YktPatchOp.Add` with array index |
| Y.Array.delete | remove | `YktPatchOp.Remove` with array index |
| Y.Text.insert | add | `YktPatchOp.Add` with text offset |
| Y.Text.delete | remove | `YktPatchOp.Remove` with text offset |

## Wire Protocol

### Message Types

Extends y-protocols message types:

| Type | y-protocols | YKT Extension |
|------|-------------|---------------|
| 0 | sync step 1 | + query selector |
| 1 | sync step 2 | + range header |
| 2 | update | JsonPatch ops |
| 3 | awareness | cursor, selection |

### Frame Format

```
┌────────┬────────┬────────────────────────────────┐
│ Type   │ Length │ Payload                        │
│ 1 byte │ varint │ ...                            │
└────────┴────────┴────────────────────────────────┘
```

### YKT Event Types

```kotlin
sealed class YktEvent : ProtoEvent {
    // Yjs sync step 2 + JsonPatch
    data class Patch(
        ops: List<YktPatchOp>,
        version: ProtoVersion  // clientId:clock
    )
    
    // Yjs awareness protocol
    data class Awareness(
        cursor: Int?,
        selection: YktSelection?,
        user: Map<String, String>
    )
    
    // Session lifecycle
    data class Join(user: Map<String, String>)
    data class Leave()
    
    // Full state sync (Yjs encodeStateAsUpdate)
    data class Sync(
        document: JsonObject,
        version: ProtoVectorClock
    )
    
    // Ack for reliable delivery
    data class Ack(ackedVersion: ProtoVersion)
}
```

## State Vector Encoding

### Yjs Binary Format
```
[numClients: varint]
[clientId: varint, clock: varint]*
```

### YKT JSON Format
```json
{
  "clocks": {
    "client_abc": 42,
    "client_xyz": 17
  }
}
```

### YKT Binary Format (YDocCodec)
```
┌──────────────┬──────────────┬──────────────┐
│ count: u32   │ clientHash   │ clock: u32   │
│              │   : u32      │              │
└──────────────┴──────────────┴──────────────┘
                     × count
```

## Conflict Resolution

Follows Yjs CRDT rules:

1. **Total Order**: Items ordered by `(clientId, clock)`
2. **Origin Pointers**: Left/right origins determine insert position
3. **Tombstones**: Deleted items marked, not removed
4. **GC**: Tombstones collected after all clients acknowledge

YKT preserves these semantics through `ProtoVectorClock`:

```kotlin
data class ProtoVectorClock(val clocks: Map<String, Long>) {
    fun merge(other: ProtoVectorClock): ProtoVectorClock
    fun happensBefore(other: ProtoVectorClock): Boolean
    fun tick(clientId: ClientId): ProtoVectorClock
}
```

## Transport Bindings

### WebSocket (y-websocket compatible)

```
wss://server/ykt/{docId}

← Binary: Yjs update
→ Binary: Yjs update
← JSON: YktEvent.Awareness
→ JSON: YktEvent.Awareness
```

### SSE (audit/read-only)

```
GET /ykt/{docId}/events
Accept: text/event-stream

event: patch
data: {"ops":[...],"version":"client_a:42"}

event: awareness  
data: {"cursor":100,"user":{"name":"Alice"}}
```

### WebDAV (storage)

```
PROPFIND /ykt/{docId}
→ State vector, metadata

GET /ykt/{docId}
Range: bytes=0-1023
→ 206 Partial Content

PUT /ykt/{docId}
← Yjs update binary
```

## Example Flow

```
Client A                    Server                    Client B
    │                          │                          │
    │──── Join ───────────────►│                          │
    │◄─── Sync(doc, clock) ────│                          │
    │                          │                          │
    │                          │◄──────── Join ───────────│
    │                          │───── Sync(doc, clock) ──►│
    │                          │                          │
    │── Patch([add /x]) ──────►│                          │
    │◄───────── Ack ───────────│                          │
    │                          │─── Patch([add /x]) ─────►│
    │                          │                          │
    │◄── Awareness(cursor) ────│◄── Awareness(cursor) ────│
    │                          │                          │
```

## Compatibility

| Feature | Yjs | YKT |
|---------|-----|-----|
| Y.Map | native | via JsonPatch |
| Y.Array | native | via JsonPatch |
| Y.Text | native | via JsonPatch |
| Y.XmlFragment | native | via XPath/CSS |
| Awareness | y-protocols | YktEvent.Awareness |
| Auth | y-protocols | MCP tools |
| Persistence | y-indexeddb | WebDAV |
| Network | y-websocket | WSS + SSE |
