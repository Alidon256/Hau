package org.hau.project.ui.appTwo.ui.screens.memories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import org.hau.project.ui.appTwo.data.repositories.ChatRepository
import org.hau.project.ui.appTwo.data.repositories.formatCount
import org.hau.project.ui.appTwo.domain.models.Channels
import org.hau.project.ui.appTwo.domain.models.MessageItem
import org.hau.project.ui.appTwo.domain.models.Poll
import org.hau.project.ui.appTwo.domain.models.PollOption
import org.hau.project.ui.appTwo.ui.theme.AppTheme
import org.hau.project.ui.appTwo.viewModels.ChatViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// Main Screen Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelDetailScreen(
    onBack: () -> Unit,
    viewModel: ChatViewModel,
    channelId: String,
    onChannelInfoClick: () -> Unit
) {
    LaunchedEffect(channelId) {
        viewModel.loadChannelDetails(channelId)
    }

    val uiState by viewModel.channelDetailState.collectAsState()

    var messages by remember(uiState.channelMessages) { mutableStateOf(uiState.channelMessages) }

    Scaffold(
        topBar = {
            ChannelTopBar(
                channelInfo = uiState.channelInfo,
                onBack = onBack,
                onChannelInfoClick = onChannelInfoClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true
            ) {
                items(messages.reversed(), key = { it.id }) { message ->
                    when {
                        message.isPoll -> PollMessageBubble(
                            poll = message.poll,
                            // The state update logic now lives here, at a higher level
                            onVote = { optionId ->
                                messages = messages.map { currentMessage ->
                                    if (currentMessage.id == message.id) {
                                        // Found the message with the poll that was voted on
                                        val newOptions = currentMessage.poll?.options?.map { option ->
                                            if (option.id == optionId) {
                                                // --- THE DEFINITIVE, FOOLPROOF FIX ---
                                                // Manually create a new PollOption object.
                                                // This completely avoids the problematic .copy() method.
                                                PollOption(
                                                    id = option.id,
                                                    text = option.text,
                                                    icon = option.icon,
                                                    votes = option.votes,
                                                    isSelected = !option.isSelected // The only value that changes
                                                )
                                            } else {
                                                option
                                            }
                                        }
                                        // Create a new message with the updated poll
                                        currentMessage.copy(poll = currentMessage.poll?.copy(options = newOptions ?: emptyList()))
                                    } else {
                                        // This is not the message that was interacted with, return it as is
                                        currentMessage
                                    }
                                }
                            }
                        )
                        message.text?.contains("December") == true -> DateDivider(message.text!!)
                        message.image != null -> ImageMessageBubble(message)
                        else -> TextMessageBubble(message)
                    }
                }
            }
        }
    }
}

// --- UI COMPONENTS ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChannelTopBar(
    channelInfo: Channels?,
    onBack: () -> Unit,
    onChannelInfoClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onSurface)
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(channelInfo?.channelRes ?: Res.drawable.grattitude),
                    contentDescription = "Channel Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            channelInfo?.channelName ?: "Loading...",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            Icons.Default.Verified, "Verified",
                            tint = Color(0xFF00A884), // WhatsApp's specific green check
                            modifier = Modifier.size(16.dp)
                        )
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.NotificationsOff, "Muted", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.MoreVert, "More", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
    )
}

@Composable
private fun TextMessageBubble(message: MessageItem) {
    Box(modifier = Modifier.fillMaxWidth().padding(start = 32.dp)) {
        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    val annotatedString = buildAnnotatedString {
                        message.text?.let { append(it) }
                        if (message.link != null) {
                            append("\n\n")
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(message.link)
                            }
                        }
                    }
                    Text(annotatedString, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
                    Text(message.time, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
                }
            }
            Reactions(message.reactions)
        }
    }
}

@Composable
private fun PollMessageBubble(
    poll: Poll?,
    onVote: (optionId: Int) -> Unit
) {
    if (poll == null) return

    val totalVotes = poll.options.sumOf { it.votes }

    Box(modifier = Modifier.fillMaxWidth().padding(start = 32.dp)) {
        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(12.dp)
            ) {
                Column {
                    Text(poll.question, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                    Text("Select one or more", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))

                    poll.options.forEach { option ->
                        PollOptionItem(
                            option = option,
                            totalVotes = totalVotes,
                            onVote = { onVote(option.id) }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PollOptionItem(option: PollOption, totalVotes: Int, onVote: () -> Unit) {
    val progress = if (totalVotes > 0) {
        option.votes.toFloat() / totalVotes.toFloat()
    } else {
        0f
    }
    val progressColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onVote),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = option.isSelected,
            onClick = onVote,
            colors = RadioButtonDefaults.colors(
                selectedColor = progressColor,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(option.icon, fontSize = 16.sp)
            Text(option.text, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
        }
        Text(option.votes.toString(), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
    }
    Box(
        modifier = Modifier
            .height(6.dp)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .height(6.dp)
                .fillMaxWidth(progress)
                .clip(CircleShape)
                .background(progressColor)
        )
    }
}

@Composable
private fun ImageMessageBubble(message: MessageItem) {
    Box(modifier = Modifier.fillMaxWidth().padding(start = 32.dp)) {
        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column {
                    // Display the image if one exists
                    if (message.image != null) {
                        AsyncImage(
                            model = message.image,
                            contentDescription = "Channel Image",
                            error = painterResource(Res.drawable.grattitude),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 240.dp) // Use heightIn for flexible height
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Display text and time, but only if text is not null
                    if (message.text != null) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = message.text,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f, fill = false) // Prevents text from pushing time
                            )
                            Text(
                                text = message.time,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    } else {
                        // If there's no text, just show the time over the image
                        Text(
                            text = message.time,
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(8.dp)
                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Reactions(message.reactions)
        }
    }
}


@Composable
private fun Reactions(reactions: Map<String, Int>) {
    val visibleReactions = reactions.filter { it.value > 0 }.keys.joinToString("")
    if (visibleReactions.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .offset(y = (-12).dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(visibleReactions, fontSize = 14.sp)
            Text(reactions.values.sum().toString(), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            Icon(Icons.AutoMirrored.Filled.Reply, "Reply", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun DateDivider(date: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(date, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Channel Screen (Dark Mode)", showBackground = true)
@Composable
private fun ChannelDetailScreenDarkPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)
    AppTheme(useDarkTheme = true) {
        ChannelDetailScreen(
            onBack = {}, viewModel = previewViewModel, channelId = "1",
            onChannelInfoClick = {},
        )
    }
}

@Preview(name = "Channel Screen (Light Mode)", showBackground = true)
@Composable
private fun ChannelDetailScreenLightPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)
    AppTheme(useDarkTheme = false) {
        ChannelDetailScreen(
            onBack = {}, viewModel = previewViewModel, channelId = "1",
            onChannelInfoClick = {}
        )
    }
}
