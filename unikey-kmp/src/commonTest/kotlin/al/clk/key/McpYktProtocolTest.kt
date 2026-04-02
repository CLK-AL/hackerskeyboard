package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlinx.serialization.json.*

/**
 * MCP/YKT Protocol Tests with Markdown Table Coverage
 *
 * YKT = Yjs-Kotlin (extends Yjs CRDT)
 *
 * ## Test Coverage Matrix
 *
 * | Component | Unit | Integration | Wire | Total |
 * |-----------|------|-------------|------|-------|
 * | MCP       | =5   | =2          | =3   | =SUM(B2:D2) |
 * | WebDAV    | =4   | =1          | =2   | =SUM(B3:D3) |
 * | YKT       | =6   | =2          | =3   | =SUM(B4:D4) |
 * | Ktor      | =3   | =1          | =2   | =SUM(B5:D5) |
 * | **Total** | =SUM(B2:B5) | =SUM(C2:C5) | =SUM(D2:D5) | =SUM(E2:E5) |
 *
 * ## Formula Reference
 * - Cell coverage: `=COUNTIF(tests, "PASS")/COUNT(tests)*100`
 * - Risk score: `=IF(coverage<80, "HIGH", IF(coverage<95, "MEDIUM", "LOW"))`
 */
class McpYktProtocolTest {

    // ═══════════════════════════════════════════════════════════════════════════
    // MCP PROTOCOL TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    fun testMcpToolDefSerialization() {
        val tool = McpToolDef(
            name = "read_file",
            description = "Read a file from disk",
            inputSchema = buildJsonObject {
                put("type", "object")
                putJsonObject("properties") {
                    putJsonObject("path") {
                        put("type", "string")
                    }
                }
            }
        )

        val json = ProtoWire.json.encodeToString(McpToolDef.serializer(), tool)
        assertTrue(json.contains("read_file"))
        assertTrue(json.contains("inputSchema"))

        val decoded = ProtoWire.json.decodeFromString(McpToolDef.serializer(), json)
        assertEquals("read_file", decoded.name)
    }

    @Test
    fun testMcpContentTypes() {
        val textContent: McpContent = McpContent.Text("Hello world")
        val imageContent: McpContent = McpContent.Image("base64data", "image/png")
        val resourceContent: McpContent = McpContent.Resource("file://test.txt", "text/plain")

        assertTrue(textContent is McpContent.Text)
        assertTrue(imageContent is McpContent.Image)
        assertTrue(resourceContent is McpContent.Resource)
    }

    @Test
    fun testMcpToolResultSerialization() {
        val success = McpToolResult.Success(listOf(McpContent.Text("result")))
        val error = McpToolResult.Error("Not found", -32601)

        val successJson = ProtoWire.json.encodeToString(McpToolResult.serializer(), success)
        assertTrue(successJson.contains("success"))

        val errorJson = ProtoWire.json.encodeToString(McpToolResult.serializer(), error)
        assertTrue(errorJson.contains("error"))
    }

    @Test
    fun testMcpMessageRequest() {
        val request = McpMessage.Request(
            id = "req-1",
            method = "tools/call",
            params = buildJsonObject {
                put("name", "read_file")
                putJsonObject("arguments") {
                    put("path", "/test.txt")
                }
            }
        )

        assertEquals("req-1", request.id)
        assertEquals("tools/call", request.method)
        assertNotNull(request.params)
    }

    @Test
    fun testMcpMessageResponse() {
        val response = McpMessage.Response(
            id = "req-1",
            result = JsonPrimitive("success")
        )

        assertEquals("req-1", response.id)
        assertNull(response.error)
        assertNotNull(response.result)
    }

    @Test
    fun testMcpCapabilities() {
        val caps = McpCapabilities(
            tools = true,
            resources = true,
            prompts = false,
            logging = true
        )

        assertTrue(caps.tools)
        assertTrue(caps.resources)
        assertFalse(caps.prompts)
    }

    @Test
    fun testMcpInitializeResult() {
        val result = McpInitializeResult(
            protocolVersion = "2024-11-05",
            serverInfo = McpServerInfo("test-server", "1.0.0"),
            capabilities = McpCapabilities()
        )

        assertEquals("2024-11-05", result.protocolVersion)
        assertEquals("test-server", result.serverInfo.name)
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // WEBDAV PROTOCOL TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    fun testWebDavMethodSerialization() {
        val method = WebDavMethod.PROPFIND
        val json = ProtoWire.json.encodeToString(WebDavMethod.serializer(), method)
        assertTrue(json.contains("PROPFIND"))
    }

    @Test
    fun testDavPropertySerialization() {
        val prop = DavProperty(
            namespace = "DAV:",
            name = "displayname",
            value = "test.txt"
        )

        val json = ProtoWire.json.encodeToString(DavProperty.serializer(), prop)
        val decoded = ProtoWire.json.decodeFromString(DavProperty.serializer(), json)
        assertEquals("displayname", decoded.name)
    }

    @Test
    fun testDavResourceSerialization() {
        val resource = DavResource(
            href = "/files/test.txt",
            displayName = "test.txt",
            contentType = "text/plain",
            contentLength = 1024,
            isCollection = false
        )

        val json = ProtoWire.json.encodeToString(DavResource.serializer(), resource)
        assertTrue(json.contains("test.txt"))
        assertFalse(json.contains("isCollection\":true"))
    }

    @Test
    fun testDavLockSerialization() {
        val lock = DavLock(
            token = "opaquelocktoken:abc123",
            owner = "user@example.com",
            scope = DavLockScope.EXCLUSIVE,
            type = DavLockType.WRITE,
            depth = DavDepth.INFINITY,
            timeout = 3600
        )

        val json = ProtoWire.json.encodeToString(DavLock.serializer(), lock)
        assertTrue(json.contains("abc123"))
    }

    @Test
    fun testDavDepthSerialization() {
        assertEquals("\"0\"", ProtoWire.json.encodeToString(DavDepth.serializer(), DavDepth.ZERO))
        assertEquals("\"1\"", ProtoWire.json.encodeToString(DavDepth.serializer(), DavDepth.ONE))
        assertEquals("\"infinity\"", ProtoWire.json.encodeToString(DavDepth.serializer(), DavDepth.INFINITY))
    }

    @Test
    fun testDavRequestBuilder() {
        val propfind = DavRequest.propfind("/files", DavDepth.ONE)
        assertEquals(WebDavMethod.PROPFIND, propfind.method)
        assertEquals("/files", propfind.path)

        val put = DavRequest.put("/files/test.txt", "content", "text/plain")
        assertEquals(WebDavMethod.PUT, put.method)
        assertEquals("content", put.body)

        val lock = DavRequest.lock("/files/test.txt")
        assertEquals(WebDavMethod.LOCK, lock.method)
        assertNotNull(lock.toXml())
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // YKT COLLABORATION TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    fun testYktSessionSerialization() {
        val session = YktSession(
            id = SessionId("sess_abc123"),
            documentUri = ResourceUri("/docs/test.md"),
            clientId = ClientId("client_xyz"),
            createdAt = 1234567890L
        )

        val json = ProtoWire.json.encodeToString(YktSession.serializer(), session)
        assertTrue(json.contains("sess_abc123"))

        val decoded = ProtoWire.json.decodeFromString(YktSession.serializer(), json)
        assertEquals("sess_abc123", decoded.id.value)
    }

    @Test
    fun testYktPatchOpSerialization() {
        val add: YktPatchOp = YktPatchOp.Add("/title", JsonPrimitive("New Title"))
        val remove: YktPatchOp = YktPatchOp.Remove("/deprecated")
        val replace: YktPatchOp = YktPatchOp.Replace("/count", JsonPrimitive(42))

        val addJson = ProtoWire.json.encodeToString(YktPatchOp.serializer(), add)
        assertTrue(addJson.contains("add"))
        assertTrue(addJson.contains("/title"))
    }

    @Test
    fun testProtoVersionSerialization() {
        val version = ProtoVersion(clientId = ClientId("client_a"), clock = 5)
        val json = ProtoWire.json.encodeToString(ProtoVersion.serializer(), version)

        val decoded = ProtoWire.json.decodeFromString(ProtoVersion.serializer(), json)
        assertEquals("client_a", decoded.clientId.value)
        assertEquals(5L, decoded.clock)
    }

    @Test
    fun testProtoVectorClockSerialization() {
        val clock = ProtoVectorClock(clocks = mapOf("a" to 3L, "b" to 5L, "c" to 1L))
        val json = ProtoWire.json.encodeToString(ProtoVectorClock.serializer(), clock)

        val decoded = ProtoWire.json.decodeFromString(ProtoVectorClock.serializer(), json)
        assertEquals(3L, decoded.clocks["a"])
        assertEquals(5L, decoded.clocks["b"])
    }

    @Test
    fun testProtoVectorClockMerge() {
        val clock1 = ProtoVectorClock(mapOf("a" to 3L, "b" to 1L))
        val clock2 = ProtoVectorClock(mapOf("a" to 1L, "b" to 5L, "c" to 2L))
        val merged = clock1.merge(clock2)

        assertEquals(3L, merged["a"])
        assertEquals(5L, merged["b"])
        assertEquals(2L, merged["c"])
    }

    @Test
    fun testYktEventPatchSerialization() {
        val patch = YktEvent.Patch(
            sessionId = "sess_1",
            clientId = "client_a",
            timestamp = 1234567890L,
            ops = listOf(
                YktPatchOp.Replace("/content", JsonPrimitive("updated"))
            ),
            version = ProtoVersion(ClientId("client_a"), 1)
        )

        val json = ProtoWire.encodeEvent(patch)
        assertTrue(json.contains("patch"))
        assertTrue(json.contains("sess_1"))

        val decoded = ProtoWire.decodeEvent(json)
        assertTrue(decoded is YktEvent.Patch)
    }

    @Test
    fun testYktEventAwarenessSerialization() {
        val awareness = YktEvent.Awareness(
            sessionId = "sess_1",
            clientId = "client_a",
            timestamp = 1234567890L,
            cursor = 42,
            selection = YktSelection(10, 20),
            user = mapOf("name" to "Alice", "color" to "#ff0000")
        )

        val json = ProtoWire.encodeEvent(awareness)
        assertTrue(json.contains("awareness"))
        assertTrue(json.contains("Alice"))
    }

    @Test
    fun testYktEventJoinLeaveSerialization() {
        val join = YktEvent.Join(
            sessionId = "sess_1",
            clientId = "client_a",
            timestamp = 1234567890L,
            user = mapOf("name" to "Bob")
        )

        val leave = YktEvent.Leave(
            sessionId = "sess_1",
            clientId = "client_a",
            timestamp = 1234567891L
        )

        val joinJson = ProtoWire.encodeEvent(join)
        val leaveJson = ProtoWire.encodeEvent(leave)

        assertTrue(joinJson.contains("join"))
        assertTrue(leaveJson.contains("leave"))
    }

    @Test
    fun testYktEventSyncSerialization() {
        val sync = YktEvent.Sync(
            sessionId = "sess_1",
            clientId = "server",
            timestamp = 1234567890L,
            document = buildJsonObject {
                put("title", "Test Doc")
                put("content", "Hello world")
            },
            version = ProtoVectorClock(mapOf("a" to 1L, "b" to 2L))
        )

        val json = ProtoWire.encodeEvent(sync)
        assertTrue(json.contains("sync"))
        assertTrue(json.contains("Test Doc"))
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // VALUE CLASS TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    fun testSessionIdValueClass() {
        val id = SessionId("sess_test123")
        assertEquals("sess_test123", id.value)
        assertEquals("sess_test123", id.toString())

        val generated = SessionId.generate()
        assertTrue(generated.value.startsWith("sess_"))
    }

    @Test
    fun testClientIdValueClass() {
        val id = ClientId("client_abc")
        assertEquals("client_abc", id.value)

        val generated = ClientId.generate()
        assertTrue(generated.value.startsWith("client_"))
    }

    @Test
    fun testResourceUriValueClass() {
        val uri = ResourceUri("file://docs/test.md")
        assertEquals("file", uri.scheme)
        assertEquals("docs/test.md", uri.path)
    }

    @Test
    fun testJsonPointerValueClass() {
        val ptr = JsonPointer("/users/0/name")
        assertEquals(listOf("users", "0", "name"), ptr.segments)

        val built = JsonPointer.of("data", "items", "value")
        assertEquals("/data/items/value", built.value)
    }

    @Test
    fun testRangeParse() {
        // HTTP Range header format
        val range = Range.parseRange("bytes=0-499")
        assertNotNull(range)
        assertEquals(0L, range.start)
        assertEquals(499L, range.end)
        assertEquals(500L, range.length)

        // Content-Range header format
        val contentRange = Range.parseContentRange("bytes 0-499/1000")
        assertNotNull(contentRange)
        assertEquals(0L, contentRange.start)
        assertEquals(499L, contentRange.end)
        assertEquals(1000L, contentRange.total)

        // toContentRange output
        val r = Range.bytes(100, 199, 1000)
        assertEquals("bytes 100-199/1000", r.toContentRange())
    }

    @Test
    fun testYktEventToSse() {
        val patch = YktEvent.Patch(
            sessionId = "sess_1",
            clientId = "client_a",
            timestamp = 1234567890L,
            ops = listOf(YktPatchOp.Add("/new", JsonPrimitive("value"))),
            version = ProtoVersion(ClientId("client_a"), 1)
        )

        val sse = patch.toSse()
        assertTrue(sse.startsWith("event: patch\n"))
        assertTrue(sse.contains("data: "))
        assertTrue(sse.endsWith("\n\n"))
    }

    @Test
    fun testYktAuditLog() {
        val auditLog = YktAuditLog()

        val event1 = YktEvent.Join("sess_1", "client_a", 100L)
        val event2 = YktEvent.Patch(
            "sess_1", "client_a", 200L,
            listOf(YktPatchOp.Add("/x", JsonPrimitive(1))),
            ProtoVersion(ClientId("client_a"), 1)
        )
        val event3 = YktEvent.Leave("sess_1", "client_a", 300L)

        auditLog.append(event1)
        auditLog.append(event2)
        auditLog.append(event3)

        val replayed = auditLog.replay(150L).toList()
        assertEquals(2, replayed.size) // event2 and event3

        val sseStream = auditLog.toSseStream(0L)
        assertTrue(sseStream.contains("event: join"))
        assertTrue(sseStream.contains("event: patch"))
        assertTrue(sseStream.contains("event: leave"))
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // KTOR/WIRE PROTOCOL TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    fun testKtorRequestSerialization() {
        val req = KtorRequest(
            method = "POST",
            url = "https://api.example.com/data",
            headers = mapOf("Content-Type" to "application/json"),
            body = """{"key": "value"}""",
            timeout = 5000
        )

        val json = ProtoWire.json.encodeToString(KtorRequest.serializer(), req)
        assertTrue(json.contains("POST"))
        assertTrue(json.contains("api.example.com"))
    }

    @Test
    fun testKtorResponseSerialization() {
        val resp = KtorResponse(
            status = 200,
            headers = mapOf("Content-Type" to listOf("application/json")),
            body = """{"result": "ok"}"""
        )

        val json = ProtoWire.json.encodeToString(KtorResponse.serializer(), resp)
        assertEquals(200, ProtoWire.json.decodeFromString(KtorResponse.serializer(), json).status)
    }

    @Test
    fun testSseEventSerialization() {
        val event = SseEvent(
            event = "message",
            data = """{"type": "update"}""",
            id = "evt-123",
            retry = 3000
        )

        val json = ProtoWire.json.encodeToString(SseEvent.serializer(), event)
        val decoded = ProtoWire.json.decodeFromString(SseEvent.serializer(), json)
        assertEquals("message", decoded.event)
        assertEquals("evt-123", decoded.id)
    }

    @Test
    fun testWireFormatJson() {
        val data = McpCapabilities(tools = true, resources = false)
        val encoded = ProtoWire.encode(data, WireFormat.JSON)
        val decoded = ProtoWire.decode<McpCapabilities>(encoded, WireFormat.JSON)

        assertTrue(decoded.tools)
        assertFalse(decoded.resources)
    }

    @Test
    fun testWireFormatMessagePack() {
        val data = McpCapabilities(tools = true, resources = true)
        val encoded = ProtoWire.encode(data, WireFormat.MESSAGE_PACK)

        // MessagePack has header byte
        assertTrue(encoded.isNotEmpty())
        assertTrue(encoded[0] == 0x92.toByte())

        val decoded = ProtoWire.decode<McpCapabilities>(encoded, WireFormat.MESSAGE_PACK)
        assertTrue(decoded.tools)
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // INTEGRATION TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    fun testMcpServerToolRegistration() {
        val server = McpServer("test-server", "1.0.0")

        val tool = McpTool(
            def = McpToolDef(
                name = "echo",
                description = "Echo input",
                inputSchema = buildJsonObject { put("type", "object") }
            ),
            handler = { args ->
                val input = args["input"]?.jsonPrimitive?.content ?: ""
                McpToolResult.Success(listOf(McpContent.Text(input)))
            }
        )

        server.registerTool(tool)

        val tools = server.listTools()
        assertEquals(1, tools.size)
        assertEquals("echo", tools[0].name)
    }

    @Test
    fun testMcpServerResourceRegistration() {
        val server = McpServer("test-server", "1.0.0")

        val resource = McpResource(
            uri = "file://config.json",
            name = "Configuration",
            mimeType = "application/json"
        )

        server.registerResource(resource) { """{"setting": true}""" }

        val resources = server.listResources()
        assertEquals(1, resources.size)
        assertEquals("file://config.json", resources[0].uri)
    }

    @Test
    fun testYktServerSessionManagement() {
        val server = YktServer()

        val session = server.createSession("/docs/test.md")
        assertNotNull(session)
        assertTrue(session.id.startsWith("sess_"))

        val retrieved = server.getSession(session.id)
        assertNotNull(retrieved)
        assertEquals(session.id, retrieved.id)

        server.closeSession(session.id)
        assertNull(server.getSession(session.id))
    }
}
