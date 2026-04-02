package al.clk.key.ui

import al.clk.key.*
import al.clk.key.proto.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

/**
 * UI state for real-time collaboration features.
 * Integrates YKT (Yjs-Kotlin), WebDAV, and MCP protocols with UI.
 */
@Serializable
data class CollaborationState(
    val connectionStatus: ConnectionStatus = ConnectionStatus.DISCONNECTED,
    val session: SessionInfo? = null,
    val participants: List<ParticipantInfo> = emptyList(),
    val pendingChanges: Int = 0,
    val lastSyncTime: Long = 0,
    val error: CollaborationError? = null
) {
    val isConnected: Boolean get() = connectionStatus == ConnectionStatus.CONNECTED
    val hasError: Boolean get() = error != null
    val participantCount: Int get() = participants.size

    companion object {
        fun initial() = CollaborationState()
    }
}

@Serializable
enum class ConnectionStatus {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    RECONNECTING,
    ERROR
}

@Serializable
data class SessionInfo(
    val sessionId: String,
    val documentUri: String,
    val clientId: String,
    val createdAt: Long,
    val format: String = "json"
) {
    companion object {
        fun from(session: YktSession) = SessionInfo(
            sessionId = session.id.value,
            documentUri = session.documentUri.value,
            clientId = session.clientId.value,
            createdAt = session.createdAt
        )
    }
}

@Serializable
data class ParticipantInfo(
    val clientId: String,
    val name: String,
    val color: String,
    val cursor: CursorInfo? = null,
    val isLocal: Boolean = false,
    val lastActive: Long = 0
)

@Serializable
data class CursorInfo(
    val position: Int,
    val selectionStart: Int? = null,
    val selectionEnd: Int? = null
)

@Serializable
data class CollaborationError(
    val code: Int,
    val message: String,
    val recoverable: Boolean = true
)

/**
 * Actions for collaboration state management.
 */
sealed class CollaborationAction {
    data class Connect(val documentUri: String, val format: WireFormat = WireFormat.JSON) : CollaborationAction()
    data object Disconnect : CollaborationAction()
    data class JoinSession(val sessionId: String, val userName: String) : CollaborationAction()
    data object LeaveSession : CollaborationAction()
    data class UpdateCursor(val position: Int, val selectionStart: Int? = null, val selectionEnd: Int? = null) : CollaborationAction()
    data class ApplyPatch(val ops: List<JsonPatchOp>) : CollaborationAction()
    data class ReceiveEvent(val event: YktEvent) : CollaborationAction()
    data class SetError(val error: CollaborationError?) : CollaborationAction()
    data object Sync : CollaborationAction()
}

/**
 * State manager for collaboration UI.
 * Handles YKT events and updates UI state.
 */
class CollaborationManager {
    private var _state = CollaborationState.initial()
    val state: CollaborationState get() = _state

    private var localClientId: String = ClientId.generate().value
    private var vectorClock = ProtoVectorClock()

    /** Process action and update state */
    fun dispatch(action: CollaborationAction): CollaborationState {
        _state = reduce(_state, action)
        return _state
    }

    private fun reduce(state: CollaborationState, action: CollaborationAction): CollaborationState {
        return when (action) {
            is CollaborationAction.Connect -> {
                state.copy(
                    connectionStatus = ConnectionStatus.CONNECTING,
                    error = null
                )
            }

            is CollaborationAction.Disconnect -> {
                state.copy(
                    connectionStatus = ConnectionStatus.DISCONNECTED,
                    session = null,
                    participants = emptyList()
                )
            }

            is CollaborationAction.JoinSession -> {
                val participant = ParticipantInfo(
                    clientId = localClientId,
                    name = action.userName,
                    color = generateColor(localClientId),
                    isLocal = true,
                    lastActive = currentTimeMillis()
                )
                state.copy(
                    connectionStatus = ConnectionStatus.CONNECTED,
                    participants = state.participants + participant
                )
            }

            is CollaborationAction.LeaveSession -> {
                state.copy(
                    session = null,
                    participants = emptyList(),
                    connectionStatus = ConnectionStatus.DISCONNECTED
                )
            }

            is CollaborationAction.UpdateCursor -> {
                val updatedParticipants = state.participants.map { p ->
                    if (p.clientId == localClientId) {
                        p.copy(
                            cursor = CursorInfo(
                                position = action.position,
                                selectionStart = action.selectionStart,
                                selectionEnd = action.selectionEnd
                            ),
                            lastActive = currentTimeMillis()
                        )
                    } else p
                }
                state.copy(participants = updatedParticipants)
            }

            is CollaborationAction.ApplyPatch -> {
                vectorClock = vectorClock.tick(ClientId(localClientId))
                state.copy(
                    pendingChanges = state.pendingChanges + action.ops.size,
                    lastSyncTime = currentTimeMillis()
                )
            }

            is CollaborationAction.ReceiveEvent -> {
                handleEvent(state, action.event)
            }

            is CollaborationAction.SetError -> {
                state.copy(
                    error = action.error,
                    connectionStatus = if (action.error != null) ConnectionStatus.ERROR else state.connectionStatus
                )
            }

            is CollaborationAction.Sync -> {
                state.copy(
                    pendingChanges = 0,
                    lastSyncTime = currentTimeMillis()
                )
            }
        }
    }

    private fun handleEvent(state: CollaborationState, event: YktEvent): CollaborationState {
        return when (event) {
            is YktEvent.Join -> {
                val participant = ParticipantInfo(
                    clientId = event.clientId,
                    name = event.user["name"] ?: "Anonymous",
                    color = generateColor(event.clientId),
                    isLocal = event.clientId == localClientId,
                    lastActive = event.timestamp
                )
                if (state.participants.none { it.clientId == event.clientId }) {
                    state.copy(participants = state.participants + participant)
                } else state
            }

            is YktEvent.Leave -> {
                state.copy(
                    participants = state.participants.filter { it.clientId != event.clientId }
                )
            }

            is YktEvent.Awareness -> {
                val updatedParticipants = state.participants.map { p ->
                    if (p.clientId == event.clientId) {
                        p.copy(
                            cursor = event.cursor?.let { pos ->
                                CursorInfo(
                                    position = pos,
                                    selectionStart = event.selection?.start,
                                    selectionEnd = event.selection?.end
                                )
                            },
                            lastActive = event.timestamp
                        )
                    } else p
                }
                state.copy(participants = updatedParticipants)
            }

            is YktEvent.Patch -> {
                state.copy(
                    pendingChanges = maxOf(0, state.pendingChanges - event.ops.size),
                    lastSyncTime = event.timestamp
                )
            }

            is YktEvent.Sync -> {
                state.copy(
                    pendingChanges = 0,
                    lastSyncTime = event.timestamp
                )
            }

            is YktEvent.Ack -> {
                state.copy(lastSyncTime = event.timestamp)
            }
        }
    }

    /** Generate consistent color for client ID */
    private fun generateColor(clientId: String): String {
        val colors = listOf(
            "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4",
            "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F"
        )
        val hash = clientId.hashCode().let { if (it < 0) -it else it }
        return colors[hash % colors.size]
    }

    /** Create YktEvent.Join for current user */
    fun createJoinEvent(sessionId: String, userName: String): YktEvent.Join {
        return YktEvent.Join(
            sessionId = sessionId,
            clientId = localClientId,
            timestamp = currentTimeMillis(),
            user = mapOf("name" to userName)
        )
    }

    /** Create YktEvent.Awareness for cursor update */
    fun createAwarenessEvent(sessionId: String, position: Int, selection: YktSelection? = null): YktEvent.Awareness {
        return YktEvent.Awareness(
            sessionId = sessionId,
            clientId = localClientId,
            timestamp = currentTimeMillis(),
            cursor = position,
            selection = selection,
            user = mapOf()
        )
    }

    /** Create YktEvent.Patch for document changes */
    fun createPatchEvent(sessionId: String, ops: List<YktPatchOp>): YktEvent.Patch {
        vectorClock = vectorClock.tick(ClientId(localClientId))
        return YktEvent.Patch(
            sessionId = sessionId,
            clientId = localClientId,
            timestamp = currentTimeMillis(),
            ops = ops,
            version = ProtoVersion(ClientId(localClientId), vectorClock[localClientId])
        )
    }

    private fun currentTimeMillis(): Long = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
}

/**
 * UI state for document editing with collaboration.
 */
@Serializable
data class DocumentEditorState(
    val documentUri: String = "",
    val content: String = "",
    val cursorPosition: Int = 0,
    val selectionStart: Int? = null,
    val selectionEnd: Int? = null,
    val isModified: Boolean = false,
    val isSaving: Boolean = false,
    val collaboration: CollaborationState = CollaborationState.initial()
) {
    val hasSelection: Boolean get() = selectionStart != null && selectionEnd != null

    companion object {
        fun initial() = DocumentEditorState()
    }
}

/**
 * Actions for document editor.
 */
sealed class DocumentEditorAction {
    data class LoadDocument(val uri: String) : DocumentEditorAction()
    data class UpdateContent(val content: String) : DocumentEditorAction()
    data class SetCursor(val position: Int) : DocumentEditorAction()
    data class SetSelection(val start: Int, val end: Int) : DocumentEditorAction()
    data object ClearSelection : DocumentEditorAction()
    data object Save : DocumentEditorAction()
    data class SaveComplete(val success: Boolean) : DocumentEditorAction()
    data class CollaborationEvent(val action: CollaborationAction) : DocumentEditorAction()
}

/**
 * Combined state manager for document editor with collaboration.
 */
class DocumentEditorManager {
    private var _state = DocumentEditorState.initial()
    val state: DocumentEditorState get() = _state

    private val collaborationManager = CollaborationManager()

    fun dispatch(action: DocumentEditorAction): DocumentEditorState {
        _state = reduce(_state, action)
        return _state
    }

    private fun reduce(state: DocumentEditorState, action: DocumentEditorAction): DocumentEditorState {
        return when (action) {
            is DocumentEditorAction.LoadDocument -> {
                state.copy(
                    documentUri = action.uri,
                    content = "",
                    cursorPosition = 0,
                    isModified = false
                )
            }

            is DocumentEditorAction.UpdateContent -> {
                state.copy(
                    content = action.content,
                    isModified = true
                )
            }

            is DocumentEditorAction.SetCursor -> {
                val newState = state.copy(cursorPosition = action.position)
                // Update collaboration awareness
                if (state.collaboration.isConnected && state.collaboration.session != null) {
                    collaborationManager.dispatch(
                        CollaborationAction.UpdateCursor(action.position)
                    )
                }
                newState.copy(collaboration = collaborationManager.state)
            }

            is DocumentEditorAction.SetSelection -> {
                state.copy(
                    selectionStart = action.start,
                    selectionEnd = action.end,
                    cursorPosition = action.end
                )
            }

            is DocumentEditorAction.ClearSelection -> {
                state.copy(
                    selectionStart = null,
                    selectionEnd = null
                )
            }

            is DocumentEditorAction.Save -> {
                state.copy(isSaving = true)
            }

            is DocumentEditorAction.SaveComplete -> {
                state.copy(
                    isSaving = false,
                    isModified = if (action.success) false else state.isModified
                )
            }

            is DocumentEditorAction.CollaborationEvent -> {
                collaborationManager.dispatch(action.action)
                state.copy(collaboration = collaborationManager.state)
            }
        }
    }

    /** Get collaboration manager for direct event handling */
    fun getCollaborationManager(): CollaborationManager = collaborationManager
}

/**
 * UI state for WebDAV file browser.
 */
@Serializable
data class FileBrowserState(
    val currentPath: String = "/",
    val resources: List<ResourceItem> = emptyList(),
    val selectedResource: ResourceItem? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val viewMode: ViewMode = ViewMode.LIST
) {
    val breadcrumbs: List<String> get() = currentPath.split("/").filter { it.isNotEmpty() }
    val hasParent: Boolean get() = currentPath != "/"

    companion object {
        fun initial() = FileBrowserState()
    }
}

@Serializable
enum class ViewMode { LIST, GRID, TREE }

@Serializable
data class ResourceItem(
    val name: String,
    val path: String,
    val isCollection: Boolean,
    val contentType: String? = null,
    val contentLength: Long? = null,
    val lastModified: String? = null,
    val isLocked: Boolean = false
) {
    val isFile: Boolean get() = !isCollection
    val extension: String get() = name.substringAfterLast('.', "")
    val icon: String get() = when {
        isCollection -> "folder"
        extension in setOf("md", "txt") -> "document"
        extension in setOf("json", "xml", "yaml") -> "code"
        extension in setOf("png", "jpg", "gif") -> "image"
        else -> "file"
    }

    companion object {
        fun from(resource: DavResource) = ResourceItem(
            name = resource.displayName ?: resource.href.substringAfterLast('/'),
            path = resource.href,
            isCollection = resource.isCollection,
            contentType = resource.contentType,
            contentLength = resource.contentLength,
            lastModified = resource.lastModified,
            isLocked = false
        )
    }
}

/**
 * Actions for file browser.
 */
sealed class FileBrowserAction {
    data class Navigate(val path: String) : FileBrowserAction()
    data object NavigateUp : FileBrowserAction()
    data class SelectResource(val resource: ResourceItem?) : FileBrowserAction()
    data class SetResources(val resources: List<ResourceItem>) : FileBrowserAction()
    data class SetLoading(val loading: Boolean) : FileBrowserAction()
    data class SetError(val error: String?) : FileBrowserAction()
    data class SetViewMode(val mode: ViewMode) : FileBrowserAction()
    data class CreateFolder(val name: String) : FileBrowserAction()
    data class DeleteResource(val path: String) : FileBrowserAction()
    data class RenameResource(val oldPath: String, val newName: String) : FileBrowserAction()
}

/**
 * State manager for WebDAV file browser.
 */
class FileBrowserManager {
    private var _state = FileBrowserState.initial()
    val state: FileBrowserState get() = _state

    fun dispatch(action: FileBrowserAction): FileBrowserState {
        _state = reduce(_state, action)
        return _state
    }

    private fun reduce(state: FileBrowserState, action: FileBrowserAction): FileBrowserState {
        return when (action) {
            is FileBrowserAction.Navigate -> {
                state.copy(
                    currentPath = action.path,
                    selectedResource = null,
                    isLoading = true,
                    error = null
                )
            }

            is FileBrowserAction.NavigateUp -> {
                val parentPath = state.currentPath.substringBeforeLast('/', "/")
                state.copy(
                    currentPath = if (parentPath.isEmpty()) "/" else parentPath,
                    selectedResource = null,
                    isLoading = true
                )
            }

            is FileBrowserAction.SelectResource -> {
                state.copy(selectedResource = action.resource)
            }

            is FileBrowserAction.SetResources -> {
                state.copy(
                    resources = action.resources.sortedWith(
                        compareBy({ !it.isCollection }, { it.name.lowercase() })
                    ),
                    isLoading = false
                )
            }

            is FileBrowserAction.SetLoading -> {
                state.copy(isLoading = action.loading)
            }

            is FileBrowserAction.SetError -> {
                state.copy(error = action.error, isLoading = false)
            }

            is FileBrowserAction.SetViewMode -> {
                state.copy(viewMode = action.mode)
            }

            is FileBrowserAction.CreateFolder -> {
                // Handled by transport layer
                state.copy(isLoading = true)
            }

            is FileBrowserAction.DeleteResource -> {
                state.copy(
                    resources = state.resources.filter { it.path != action.path },
                    selectedResource = if (state.selectedResource?.path == action.path) null else state.selectedResource
                )
            }

            is FileBrowserAction.RenameResource -> {
                val updatedResources = state.resources.map { r ->
                    if (r.path == action.oldPath) {
                        val newPath = action.oldPath.substringBeforeLast('/') + "/" + action.newName
                        r.copy(name = action.newName, path = newPath)
                    } else r
                }
                state.copy(resources = updatedResources)
            }
        }
    }

    /** Create DavRequest for current path */
    fun createPropfindRequest(format: WireFormat = WireFormat.JSON): DavRequest {
        return DavRequest.propfind(_state.currentPath, DavDepth.ONE, format)
    }
}

/**
 * UI state for MCP tool execution.
 */
@Serializable
data class McpToolState(
    val availableTools: List<McpToolInfo> = emptyList(),
    val selectedTool: McpToolInfo? = null,
    val inputValues: Map<String, String> = emptyMap(),
    val isExecuting: Boolean = false,
    val lastResult: McpToolResultInfo? = null,
    val error: String? = null
) {
    companion object {
        fun initial() = McpToolState()
    }
}

@Serializable
data class McpToolInfo(
    val name: String,
    val description: String,
    val parameters: List<McpParameterInfo> = emptyList()
) {
    companion object {
        fun from(def: McpToolDef): McpToolInfo {
            val params = def.inputSchema["properties"]?.jsonObject?.entries?.map { (name, schema) ->
                McpParameterInfo(
                    name = name,
                    type = schema.jsonObject["type"]?.jsonPrimitive?.content ?: "string",
                    description = schema.jsonObject["description"]?.jsonPrimitive?.content,
                    required = def.inputSchema["required"]?.jsonArray?.any {
                        it.jsonPrimitive.content == name
                    } ?: false
                )
            } ?: emptyList()

            return McpToolInfo(
                name = def.name,
                description = def.description,
                parameters = params
            )
        }
    }
}

@Serializable
data class McpParameterInfo(
    val name: String,
    val type: String,
    val description: String? = null,
    val required: Boolean = false
)

@Serializable
data class McpToolResultInfo(
    val isSuccess: Boolean,
    val content: String,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

/**
 * Actions for MCP tool UI.
 */
sealed class McpToolAction {
    data class SetTools(val tools: List<McpToolInfo>) : McpToolAction()
    data class SelectTool(val tool: McpToolInfo?) : McpToolAction()
    data class UpdateInput(val param: String, val value: String) : McpToolAction()
    data object Execute : McpToolAction()
    data class SetResult(val result: McpToolResultInfo) : McpToolAction()
    data class SetError(val error: String?) : McpToolAction()
    data object ClearResult : McpToolAction()
}

/**
 * State manager for MCP tool execution UI.
 */
class McpToolManager {
    private var _state = McpToolState.initial()
    val state: McpToolState get() = _state

    fun dispatch(action: McpToolAction): McpToolState {
        _state = reduce(_state, action)
        return _state
    }

    private fun reduce(state: McpToolState, action: McpToolAction): McpToolState {
        return when (action) {
            is McpToolAction.SetTools -> {
                state.copy(availableTools = action.tools)
            }

            is McpToolAction.SelectTool -> {
                state.copy(
                    selectedTool = action.tool,
                    inputValues = emptyMap(),
                    lastResult = null,
                    error = null
                )
            }

            is McpToolAction.UpdateInput -> {
                state.copy(
                    inputValues = state.inputValues + (action.param to action.value)
                )
            }

            is McpToolAction.Execute -> {
                state.copy(isExecuting = true, error = null)
            }

            is McpToolAction.SetResult -> {
                state.copy(
                    isExecuting = false,
                    lastResult = action.result,
                    error = if (!action.result.isSuccess) action.result.errorMessage else null
                )
            }

            is McpToolAction.SetError -> {
                state.copy(
                    isExecuting = false,
                    error = action.error
                )
            }

            is McpToolAction.ClearResult -> {
                state.copy(lastResult = null, error = null)
            }
        }
    }

    /** Create MCP request for current tool */
    fun createToolRequest(): McpMessage.Request? {
        val tool = _state.selectedTool ?: return null
        return McpMessage.Request(
            id = RequestId.next().value,
            method = "tools/call",
            params = buildJsonObject {
                put("name", tool.name)
                putJsonObject("arguments") {
                    _state.inputValues.forEach { (key, value) ->
                        put(key, value)
                    }
                }
            }
        )
    }
}
