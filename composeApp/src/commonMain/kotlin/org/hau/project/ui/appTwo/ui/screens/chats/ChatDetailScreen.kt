package org.hau.project.ui.appTwo.ui.screens.chats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import kotlinx.coroutines.Job
import org.hau.project.ui.appTwo.data.repositories.ChatRepository
import org.hau.project.ui.appTwo.domain.models.Message
import org.hau.project.ui.appTwo.domain.models.MessageSender
import org.hau.project.ui.appTwo.domain.models.MessageStatus
import org.hau.project.ui.appTwo.ui.components.Avatar
import org.hau.project.ui.appTwo.ui.components.DocumentPreviewItem
import org.hau.project.ui.appTwo.ui.components.MessageBubble
import org.hau.project.ui.appTwo.ui.components.MultiActionFloatingButton
import org.hau.project.ui.appTwo.ui.components.Routes
import org.hau.project.ui.appTwo.viewModels.ChatViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: ChatViewModel,
    chatId: String? = null,
    onBack: () -> Unit = {},
    navController: NavController? = null,
    onUserInfoClick: (String)-> Unit
) {
    var showActions by remember { mutableStateOf(false) }
    var showAttachmentSheet by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }
    var uploadProgress by remember { mutableStateOf<Float?>(null) }
    var uploadJob by remember { mutableStateOf<Job?>(null) }
    var isExpanded by remember { mutableStateOf(false) }
    var isFabExpanded by remember { mutableStateOf(false) }

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

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Row(
                        modifier = Modifier.clickable{chat?.id?.let { userId -> onUserInfoClick(userId) }},
                        verticalAlignment = Alignment.CenterVertically) {
                        Avatar(
                            name = chat?.userName ?: "...",
                            size = 40.dp,
                            avatarUrl = chat?.profileRes
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                chat?.userName ?: "Loading...",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
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
                    IconButton(
                        onClick = {
                            navController?.navigate(Routes.VIDEO_CALL)
                        }
                    ) {
                        Icon(
                            Icons.Default.Videocam,
                            contentDescription = "Video Chat",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = {
                        navController?.navigate(Routes.AUDIO_CALL)
                    }) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "Audio Chat",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box {
                        IconButton(onClick = { isExpanded = true }) {
                            Icon(
                                Icons.Outlined.MoreVert,
                                contentDescription = "More Options",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant // Use for icons on surface
                            )
                        }
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                            content = {
                                val itemColors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onSurface // Use onSurface for text
                                )
                                DropdownMenuItem(
                                    text = { Text("New Group") },
                                    onClick = { /* Handle click */ isExpanded = false },
                                    colors = itemColors,
                                )
                                DropdownMenuItem(
                                    text = { Text("New Community") },
                                    onClick = { /* Handle click */ isExpanded = false },
                                    colors = itemColors,
                                )
                                DropdownMenuItem(
                                    text = { Text("Starred") },
                                    onClick = { /* Handle click */ isExpanded = false },
                                    colors = itemColors,
                                )
                                DropdownMenuItem(
                                    text = { Text("Read all") },
                                    onClick = { /* Handle click */ isExpanded = false },
                                    colors = itemColors,
                                )
                                DropdownMenuItem(
                                    text = { Text("Settings") },
                                    onClick = { /* Handle click */ isExpanded = false },
                                    colors = itemColors,
                                )
                            }
                        )
                    }
                }
            )

            if (uploadProgress != null) {
                LinearProgressIndicator(
                    progress = { uploadProgress!! },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        },
        floatingActionButton = {
            MultiActionFloatingButton(
                onStateChange = { expanded ->
                    isFabExpanded = expanded
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
                val borderColor = if (isFocused) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .clip(RoundedCornerShape(28.dp))
                ) {
                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("Type a message...") },
                        leadingIcon = {
                            IconButton(onClick = { showAttachmentSheet = true }) {
                                Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { /* TODO: Send message */ },
                        enabled = uploadProgress == null
                    ) {
                        if (uploadProgress == null) {
                            Icon(Icons.Default.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                        }
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(innerPadding), Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true // Show latest messages at the bottom
            ) {
                // Use the messages from the ViewModel state
                items(uiState.messages.reversed()) { message ->
                    MessageBubble(
                        message = message,
                        onLongPress = { showActions = true },
                        showMeta = true // Set to true to show time and status
                    )
                }
                item {
                    // Add a spacer at the top of the list (which is the bottom of the screen)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        AnimatedVisibility(
            visible = isFabExpanded,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(
                        // Allow clicking the backdrop to close the menu
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { isFabExpanded = false }
                    )
            )
        }
    }
}

@Preview(name = "Full Chat Detail Screen")
@Composable
fun ChatDetailScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)
    // We pass a valid ID that exists in the repository for the preview
    DetailScreen(
        viewModel = previewViewModel,
        chatId = "1", // Use "1" for Mugumya Ali's conversation
        onBack = {},
        navController = rememberNavController(),
        onUserInfoClick = {}
    )
}

