package org.hau.project.ui.screens.memories

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import hau.composeapp.generated.resources.image_large
import org.hau.project.data.repositories.ChatRepository
import org.hau.project.data.repositories.formatCount
import org.hau.project.models.Channels
import org.hau.project.models.MessageItem
import org.hau.project.models.Poll
import org.hau.project.models.PollOption
import org.hau.project.ui.theme.AppTheme
import org.hau.project.utils.WindowSize
import org.hau.project.utils.rememberWindowSize
import org.hau.project.viewModels.ChatViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelDetailScreen(
    onBack: () -> Unit,
    viewModel: ChatViewModel,
    channelId: String,
    onChannelInfoClick: (channelId: String) -> Unit
) {
    val windowSize = rememberWindowSize()
    val isLargeScreen = windowSize >= WindowSize.Expanded

    LaunchedEffect(channelId) {
        viewModel.loadChannelDetails(channelId)
    }

    val uiState by viewModel.channelDetailState.collectAsState()
    var messages by remember(uiState.channelMessages) { mutableStateOf(uiState.channelMessages) }
    
    // --- FULLSCREEN IMAGE STATE ---
    var fullscreenImageUrl by remember { mutableStateOf<String?>(null) }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                ChannelTopBar(
                    channelInfo = uiState.channelInfo,
                    onBack = onBack,
                    isLargeScreen = isLargeScreen,
                    onChannelInfoClick = {
                        uiState.channelInfo?.id?.let { id -> onChannelInfoClick(id) }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 2.dp,
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "You're following this channel",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        ) { paddingValues ->
            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    reverseLayout = true
                ) {
                    items(messages.reversed(), key = { it.id }) { message ->
                        ChannelMessageWrapper(message) {
                            when {
                                message.isPoll -> PollMessageBubble(
                                    poll = message.poll,
                                    onVote = { optionId ->
                                        messages = messages.map { currentMessage ->
                                            if (currentMessage.id == message.id) {
                                                val newOptions = currentMessage.poll?.options?.map { option ->
                                                    if (option.id == optionId) {
                                                        PollOption(option.id, option.text, option.icon, option.votes, !option.isSelected)
                                                    } else option
                                                }
                                                currentMessage.copy(poll = currentMessage.poll?.copy(options = newOptions ?: emptyList()))
                                            } else currentMessage
                                        }
                                    }
                                )
                                message.text?.contains("December") == true -> DateDivider(message.text!!)
                                message.image != null -> ImageMessageBubble(
                                    message,
                                    onImageClick = { fullscreenImageUrl = it }
                                )
                                else -> TextMessageBubble(message)
                            }
                        }
                    }
                }
            }
        }

        // --- MODERN FULLSCREEN IMAGE VIEWER ---
        AnimatedVisibility(
            visible = fullscreenImageUrl != null,
            enter = fadeIn() + scaleIn(initialScale = 0.9f),
            exit = fadeOut() + scaleOut(targetScale = 0.9f)
        ) {
            fullscreenImageUrl?.let { url ->
                FullscreenImageViewer(
                    imageUrl = url,
                    onClose = { fullscreenImageUrl = null }
                )
            }
        }
    }
}

@Composable
private fun FullscreenImageViewer(imageUrl: String, onClose: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f)
                    offset += pan
                }
            }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                ),
            contentScale = ContentScale.Fit
        )

        // Close Button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .align(Alignment.TopStart)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }
        
        // Bottom Actions (Modern touch)
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) { Icon(Icons.Default.Share, null, tint = Color.White) }
            Spacer(Modifier.width(32.dp))
            IconButton(onClick = {}) { Icon(Icons.Default.Download, null, tint = Color.White) }
        }
    }
}

@Composable
private fun ChannelMessageWrapper(message: MessageItem, content: @Composable () -> Unit) {
    if (message.text?.contains("December") == true) {
        content()
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(modifier = Modifier.widthIn(max = 400.dp)) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChannelTopBar(
    channelInfo: Channels?,
    onBack: () -> Unit,
    isLargeScreen: Boolean,
    onChannelInfoClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            if (!isLargeScreen) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onSurface)
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.clickable(onClick = onChannelInfoClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(channelInfo?.channelRes ?: Res.drawable.grattitude),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            channelInfo?.channelName ?: "Loading...",
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(Icons.Default.Verified, null, tint = Color(0xFF00A884), modifier = Modifier.size(16.dp))
                    }
                    Text(
                        "${formatCount(channelInfo?.followerCount ?: 0)} followers",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Outlined.NotificationsOff, "Muted", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, "More", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}

@Composable
private fun TextMessageBubble(message: MessageItem) {
    Column(horizontalAlignment = Alignment.Start) {
        Box(
            modifier = Modifier
                .shadow(0.5.dp, RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp)
        ) {
            Column {
                val annotatedString = buildAnnotatedString {
                    message.text?.let { append(it) }
                    if (message.link != null) {
                        append("\n\n")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                            append(message.link)
                        }
                    }
                }
                Text(annotatedString, style = MaterialTheme.typography.bodyLarge)
                Text(
                    message.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                )
            }
        }
        Reactions(message.reactions)
    }
}

@Composable
private fun PollMessageBubble(poll: Poll?, onVote: (optionId: Int) -> Unit) {
    if (poll == null) return
    val totalVotes = poll.options.sumOf { it.votes }

    Box(
        modifier = Modifier
            .shadow(0.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Column {
            Text(poll.question, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text("Select one or more", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))

            poll.options.forEach { option ->
                PollOptionItem(option, totalVotes) { onVote(option.id) }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun PollOptionItem(option: PollOption, totalVotes: Int, onVote: () -> Unit) {
    val progress = if (totalVotes > 0) option.votes.toFloat() / totalVotes.toFloat() else 0f
    
    Column(modifier = Modifier.fillMaxWidth().clickable(onClick = onVote)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = option.isSelected, onClick = onVote)
            Text(option.text, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Text(option.votes.toString(), style = MaterialTheme.typography.labelMedium)
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun ImageMessageBubble(message: MessageItem, onImageClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .shadow(0.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { message.image?.let { onImageClick(it) } }
    ) {
        Column {
            AsyncImage(
                model = message.image,
                contentDescription = null,
                error = painterResource(Res.drawable.grattitude),
                placeholder = painterResource(Res.drawable.image_large),
                modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp),
                contentScale = ContentScale.Crop
            )
            if (message.text != null) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(message.text, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        message.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Reactions(reactions: Map<String, Int>) {
    val visibleReactions = reactions.filter { it.value > 0 }.keys.joinToString("")
    if (visibleReactions.isNotEmpty()) {
        Surface(
            modifier = Modifier.offset(y = (-8).dp, x = 8.dp),
            shape = CircleShape,
            tonalElevation = 2.dp,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(visibleReactions, fontSize = 12.sp)
                Text(reactions.values.sum().toString(), style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun DateDivider(date: String) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Text(date, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), style = MaterialTheme.typography.labelSmall)
        }
    }
}
