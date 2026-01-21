package org.hau.project.ui.appTwo.ui.screens.chats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import kotlinx.coroutines.Job
import org.hau.project.ui.appTwo.data.repositories.ChatRepository
import org.hau.project.ui.appTwo.domain.models.Message
import org.hau.project.ui.appTwo.domain.models.MessageSender
import org.hau.project.ui.appTwo.domain.models.MessageStatus
import org.hau.project.ui.appTwo.ui.components.Avatar
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
    navController: NavController? = null
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Avatar(
                            name = chat?.userName ?: "...",
                            size = 40.dp,
                            avatarUrl = chat?.profileRes // You can get this from the 'chat' object if available
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
@Composable
private fun MessageBubble(message: Message, onLongPress: () -> Unit, showMeta: Boolean = true) {    val isMine = message.sender == MessageSender.Me

    val incomingColor = Color(0xFF202C33) // Dark Grey
    val readTickColor = Color(0xFF53BDEB) // Bright Blue

    val outgoingColor = MaterialTheme.colorScheme.primary

    val bubbleColor = if (isMine) outgoingColor else incomingColor
    val textColor = if (isMine) MaterialTheme.colorScheme.onPrimary else Color.White

    // Enhanced bubble shapes for better visual hierarchy
    val bubbleShape = if (isMine) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 0.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp)
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
        ) {
        Box(
            modifier = Modifier
                .widthIn(min = 80.dp, max = 280.dp) // Constrain bubble width
                .clip(bubbleShape)
                .background(bubbleColor)
                .combinedClickable(onClick = {}, onLongClick = onLongPress)
                .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp)
        ) {
            // Use a Column to stack the main text and the metadata
            Column {
                // This is a common trick for this layout:
                // We create a Box that will hold the text and the metadata, allowing them to overlap.
                Box(contentAlignment = Alignment.BottomEnd) {
                    // Spacer with invisible text to reserve space for the metadata,
                    // ensuring the main text wraps correctly above it.
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        // Add another small spacer for the status icon
                        if (isMine) {
                            Spacer(Modifier.width(20.dp))
                        }
                    }

                    // The actual message text
                    Text(
                        text = message.text ?: "",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 22.sp // Slightly larger line height for readability
                        ),
                        color = textColor,
                        modifier = Modifier.padding(bottom = 4.dp) // Padding to avoid overlapping with metadata
                    )
                }

                // The actual metadata row, drawn at the bottom of the Column
                Row(
                    modifier = Modifier
                        .offset(x = 4.dp)
                        .align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                    if (isMine) {
                        Spacer(Modifier.width(4.dp))
                        val statusIcon = when (message.status) {
                            MessageStatus.READ -> Icons.Filled.DoneAll to readTickColor
                            MessageStatus.DELIVERED -> Icons.Filled.DoneAll to MaterialTheme.colorScheme.onSurface
                            MessageStatus.SENT -> Icons.Filled.Done to MaterialTheme.colorScheme.primary
                            null -> null
                        }
                        statusIcon?.let { (icon, color) ->
                            Icon(
                                imageVector = icon,
                                contentDescription = "Message Status",
                                tint = color,
                                modifier = Modifier.size(18.dp) // Slightly larger icon
                            )
                        }
                    }
                }
            }
        }
            }
    }
}

// --- PREVIEWS ---

@Preview(name = "Full Chat Detail Screen")
@Composable
fun ChatDetailScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)
    // We pass a valid ID that exists in the repository for the preview
    DetailScreen(
        viewModel = previewViewModel,
        chatId = "1", // Use "1" for Mugumya Ali's conversation
        onBack = {}
    )
}

@Preview(name = "My Message Bubble")
@Composable
private fun MyMessageBubblePreview() {
    Box(Modifier.padding(16.dp)) {
        MessageBubble(
            message = Message("p1", MessageSender.Me, "Hey, how's it going? This is a slightly longer message to test wrapping.", "10:30 AM", status = MessageStatus.READ),
            onLongPress = {},
            showMeta = true
        )
    }
}

@Preview(name = "Their Message Bubble")
@Composable
private fun TheirMessageBubblePreview() {
    Box(Modifier.padding(16.dp)) {
        MessageBubble(
            message = Message("p2", MessageSender.Them, "I'm doing great,", "10:31 AM"),
            onLongPress = {},
            showMeta = true
        )
    }
}

// The following previews for media items are kept for completeness,
// assuming they might be used in the future. They are not used in the main bubble for now.
@Composable
private fun ImagePreviewPlaceholder(imageUrl: String? = null) {
    Box(
        modifier = Modifier
            .widthIn(max = 280.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    error = painterResource(Res.drawable.grattitude)
                ),
                contentDescription = "Attached image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.grattitude),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
@Preview
fun ImagePreviewPlaceholderPreview() {
    ImagePreviewPlaceholder()
}

@Composable
private fun DocumentPreview(
    name: String?,
    size: String?,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "📄", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.width(10.dp))
        Column {
            if (!name.isNullOrBlank()) {
                Text(text = name, style = MaterialTheme.typography.bodyMedium, color = titleColor)
            }
            if (!size.isNullOrBlank()) {
                Text(
                    text = size,
                    style = MaterialTheme.typography.labelSmall,
                    color = subtitleColor
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DocumentPreviewStudio() {
    DocumentPreview(
        name = "Project_Brief.pdf",
        size = "45 KB",
        titleColor = MaterialTheme.colorScheme.onSurface,
        subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun VideoPreviewPlaceholder(videoUrl: String? = null) {
    var isPlaying by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .widthIn(max = 280.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
            .clickable { isPlaying = !isPlaying },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = "Play/Pause",
            tint = Color.White,
            modifier = Modifier
                .size(48.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(8.dp)
        )
    }
}

