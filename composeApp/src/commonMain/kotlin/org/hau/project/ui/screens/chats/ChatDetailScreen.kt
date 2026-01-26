package org.hau.project.ui.screens.chats

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.hau.project.ui.components.AttachmentDropdownMenu
import org.hau.project.ui.components.Avatar
import org.hau.project.ui.components.MessageActionMenu
import org.hau.project.ui.components.MessageBubble
import org.hau.project.ui.components.ModernCallPanel
import org.hau.project.ui.components.Routes
import org.hau.project.utils.WindowSize
import org.hau.project.utils.rememberWindowSize
import org.hau.project.viewModels.ChatViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class CallUIState { IDLE, CALLING, ACTIVE }
enum class CallType { AUDIO, VIDEO }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: ChatViewModel,
    chatId: String? = null,
    onBack: () -> Unit = {},
    navController: NavController? = null,
    onUserInfoClick: (String) -> Unit
) {
    val windowSize = rememberWindowSize()
    val isLargeScreen = windowSize >= WindowSize.Expanded

    var messageText by remember { mutableStateOf("") }
    var isTopMenuExpanded by remember { mutableStateOf(false) }
    var isAttachmentMenuExpanded by remember { mutableStateOf(false) }

    // --- CALL STATE ---
    var callState by remember { mutableStateOf(CallUIState.IDLE) }
    var callType by remember { mutableStateOf(CallType.AUDIO) }

    if (chatId == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: Chat ID not provided.", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    LaunchedEffect(chatId) {
        viewModel.loadMessages(chatId)
    }

    val uiState by viewModel.chatDetailState.collectAsState()
    val chat = uiState.currentChat

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        if (!isLargeScreen) {
                            IconButton(onClick = { onBack() }) {
                                Icon(Icons.Default.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    title = {
                        Row(
                            modifier = Modifier.clickable { chat?.id?.let { userId -> onUserInfoClick(userId) } },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Avatar(name = chat?.userName ?: "...", size = 40.dp, avatarUrl = chat?.profileRes)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    chat?.userName ?: "Loading...",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                                if (chat?.isOnline == true) {
                                    Text(
                                        "Online",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            if (isLargeScreen) {
                                callType = CallType.VIDEO
                                callState = CallUIState.CALLING
                            } else {
                                navController?.navigate(Routes.VIDEO_CALL)
                            }
                        }) {
                            Icon(Icons.Default.Videocam, "Video Chat", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        IconButton(onClick = {
                            if (isLargeScreen) {
                                callType = CallType.AUDIO
                                callState = CallUIState.CALLING
                            } else {
                                navController?.navigate(Routes.AUDIO_CALL)
                            }
                        }) {
                            Icon(Icons.Default.Phone, "Audio Chat", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Box {
                            IconButton(onClick = { isTopMenuExpanded = true }) {
                                Icon(Icons.Outlined.MoreVert, "More", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            DropdownMenu(
                                expanded = isTopMenuExpanded,
                                onDismissRequest = { isTopMenuExpanded = false },
                                modifier = Modifier.clip(RoundedCornerShape(16.dp))
                            ) {
                                DropdownMenuItem(text = { Text("Contact info") }, onClick = { isTopMenuExpanded = false })
                                DropdownMenuItem(text = { Text("Select messages") }, onClick = { isTopMenuExpanded = false })
                                DropdownMenuItem(text = { Text("Mute notifications") }, onClick = { isTopMenuExpanded = false })
                                DropdownMenuItem(text = { Text("Clear chat") }, onClick = { isTopMenuExpanded = false })
                                DropdownMenuItem(text = { Text("Delete chat") }, onClick = { isTopMenuExpanded = false })
                            }
                        }
                    }
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val interaction = remember { MutableInteractionSource() }
                    val isFocused by interaction.collectIsFocusedAsState()

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(28.dp)
                            )
                            .clip(RoundedCornerShape(28.dp))
                    ) {
                        TextField(
                            value = messageText,
                            onValueChange = { messageText = it },
                            placeholder = { Text("Type a message...") },
                            leadingIcon = {
                                Box {
                                    IconButton(onClick = { isAttachmentMenuExpanded = true }) {
                                        Icon(
                                            Icons.Default.AttachFile,
                                            "Attach",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    AttachmentDropdownMenu(
                                        expanded = isAttachmentMenuExpanded,
                                        onDismiss = { isAttachmentMenuExpanded = false }
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            interactionSource = interaction,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            )
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { /* Send */ }) {
                            Icon(Icons.Default.Send, "Send", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize().padding(innerPadding), Alignment.Center) { CircularProgressIndicator() }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    reverseLayout = true
                ) {
                    itemsIndexed(uiState.messages.reversed()) { index, message ->
                        var isMenuExpanded by remember { mutableStateOf(false) }
                        
                        Box(Modifier.fillMaxWidth()) {
                            MessageBubble(
                                message = message,
                                index = index,
                                showMeta = true,
                                onLongPress = { isMenuExpanded = true }
                            )
                            
                            // Anchor Message Actions locally to the bubble
                            MessageActionMenu(
                                expanded = isMenuExpanded,
                                onDismiss = { isMenuExpanded = false }
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            }
        }

        // --- MODERN IN-APP CALL OVERLAY ---
        AnimatedVisibility(
            visible = callState != CallUIState.IDLE,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        ) {
            ModernCallPanel(
                userName = chat?.userName ?: "Unknown",
                avatarUrl = chat?.profileRes,
                callType = callType,
                callState = callState,
                onEndCall = { callState = CallUIState.IDLE },
                onAcceptCall = { callState = CallUIState.ACTIVE }
            )
        }
    }
}
