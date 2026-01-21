package org.hau.project.ui.appTwo.ui.screens.memories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import hau.composeapp.generated.resources.story_4
import org.hau.project.ui.appTwo.data.repositories.ChatRepository
import org.hau.project.ui.appTwo.data.repositories.formatCount
import org.hau.project.ui.appTwo.domain.models.AttachmentType
import org.hau.project.ui.appTwo.domain.models.Channels
import org.hau.project.ui.appTwo.domain.models.RecommendedChannels
import org.hau.project.ui.appTwo.ui.theme.AppTheme
import org.hau.project.ui.appTwo.ui.theme.SocialTheme
import org.hau.project.ui.appTwo.viewModels.ChatViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoriesScreen(
    viewModel: ChatViewModel,
    onChannelClick: (channelId: String) -> Unit,
    onAddMemoryClick: () -> Unit
) {
    val uiChannelState by viewModel.channelState.collectAsState()
    val uiRecommendedChannels by viewModel.recommendedState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Memories", // Renamed from "Memories" to match WhatsApp
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Search, "Search Icon")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.MoreVert, "More Options")
                    }
                }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .clickable { onAddMemoryClick() }
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .size(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.AddAPhoto,
                    contentDescription = "Add Memory",
                    tint = MaterialTheme.colorScheme.onPrimary // Use theme color
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background // Use theme background
    ) { paddingValues ->
        when {
            uiChannelState.isLoading && uiRecommendedChannels.isLoading -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiChannelState.error != null -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Error: ${uiChannelState.error}", color = MaterialTheme.colorScheme.error)
                }
            }
            uiRecommendedChannels.error != null -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Error: ${uiRecommendedChannels.error}", color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        MemoriesBanner(onClick = onAddMemoryClick)
                    }

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                "Channels",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 8.dp)
                            )
                            TextButton(
                                onClick = {},
                            ){
                                Text(
                                    "Explore",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }

                    items(uiChannelState.channels.filter { !it.isRead }) { channel ->
                        ChannelItem(channel = channel, onClick = {onChannelClick})
                    }

                    item {
                        Text(
                            "More Channels to Follow",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    /*items(uiChannelState.channels.filter { it.isRead }) { channel ->
                        ChannelItem(channel = channel, onClick = {onChannelClick}, isViewed = true)
                    }*/
                    items(uiRecommendedChannels.recommendedChannels) { channel ->
                        RecommendedChannelsItem(recommended = channel, onFollowClick = {onChannelClick})
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MemoriesBanner(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                ),RoundedCornerShape(16.dp))
            .padding(start = 8.dp,top = 8.dp,bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(Res.drawable.story_3),
                contentDescription = "My Status",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Status",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Column(Modifier.weight(1f)) {
            Text(
                "My status",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                "Tap to add status update",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
fun ChannelItem(channel: Channels, onClick: () -> Unit, isViewed: Boolean = false) {
    val contentColor = if (isViewed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
    val borderColor = if (isViewed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box {
            Image(
                painter = painterResource(channel.channelRes),
                contentDescription = "${channel.channelName} Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(2.dp, borderColor, CircleShape)
            )
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(
                    text = channel.channelName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )
                Text(
                    text = channel.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isViewed) contentColor else MaterialTheme.colorScheme.primary
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val icon = when (channel.attachmentType) {
                    AttachmentType.VIDEO -> Icons.Outlined.Videocam
                    AttachmentType.AUDIO -> Icons.Outlined.Audiotrack
                    AttachmentType.IMAGE -> Icons.Outlined.Photo
                    else -> null
                }
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Attachment",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = channel.message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RecommendedChannelsItem(
    recommended: RecommendedChannels,
    onFollowClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(recommended.channelRes),
            contentDescription = recommended.channelName,
            modifier = Modifier.clip(CircleShape).size(60.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = recommended.channelName,
                    style = MaterialTheme.typography.titleMedium,
                )
                if(recommended.isVerified){
                    Icon(
                        Icons.Outlined.Verified,
                        contentDescription = recommended.channelName,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Row {
                Text(
                    text = "${formatCount(recommended.followerCount)} followers",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        IconButton(
            onClick = {},
            modifier = Modifier.width(90.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )
        ){
            Text(
                text = "Follow",
            )

        }
    }
}

@Preview(name = "My Status Banner")
@Composable
private fun MemoriesBannerPreview(){
    // Wrap in your theme to get the correct colors
    // import org.hau.project.ui.theme.AppTheme
    // AppTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
        MemoriesBanner(
            onClick = {}
        )
    }
    // }
}

@Preview(name = "Unread Channel Item")
@Composable
private fun ChannelItemUnreadPreview(){
    // AppTheme {
    Box(Modifier
        .background(MaterialTheme.colorScheme.background).padding(16.dp)) {
        ChannelItem(
            channel = Channels(
                channelName = "WhatsApp",
                channelRes = Res.drawable.story_3, // Replace with a valid resource
                timestamp = "11:38 AM",
                attachmentType = AttachmentType.VIDEO,
                message = "We're making it easier to start calls with...",
                unreadMessages = 0,
                isRead = false,
                followerCount = 34L, // Key for preview
                id = "aa"
            ),
            onClick = {}
        )
    }
    // }
}

@Preview(name = "Viewed Channel Item")
@Composable
private fun ChannelItemViewedPreview(){
    // AppTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
        ChannelItem(
            channel = Channels(
                channelName = "The Verge",
                channelRes = Res.drawable.story_2, // Replace with a valid resource
                timestamp = "Yesterday",
                attachmentType = AttachmentType.IMAGE,
                message = "First look at the new foldable phone",
                unreadMessages = 0,
                isRead = true,
                followerCount = 0L, // Key for preview
                id = "aa"
            ),
            onClick = {},
            isViewed = true // Key for preview
        )
    }
    // }
}

@Preview(name = "Recommended Channel")
@Composable
private fun RecommendedChannelsItemPreview(){
    // AppTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
        RecommendedChannelsItem(
            recommended = RecommendedChannels(
                channelName = "Netflix",
                channelRes = Res.drawable.story_4, // Replace with a valid resource
                isVerified = true,
                followerCount = 291_000_000
            ),
            onFollowClick = {}
        )
    }
    // }
}

@Preview(name = "Green Light")
@Composable
fun MemoriesScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = false
    ){
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}
@Preview(name = "Blue Light")
@Composable
fun MemoriesLightBlueScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = false,
        theme = SocialTheme.Twitter
    ) {
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}

@Preview(name = "Snap Light")
@Composable
fun MemoriesLightSnapScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = false,
        theme = SocialTheme.Snapchat
    ) {
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}
@Preview(name = "Inst Light")
@Composable
fun MemoriesLightInstScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = false,
        theme = SocialTheme.Instagram
    ) {
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}

@Preview(name = "Green Dark")
@Composable
fun MemoriesDarkGreenScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = true
    ) {
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}
@Preview(name = "Blue Dark")
@Composable
fun MemoriesDarkBlueScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = true,
        theme = SocialTheme.Twitter
    ) {
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}
@Preview(name = "Snap Dark")
@Composable
fun MemoriesDarkSnapScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = true,
        theme = SocialTheme.Snapchat
    ) {
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}

@Preview(name = "Inst Dark")
@Composable
fun MemoriesDarkInstScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    AppTheme(
        useDarkTheme = true,
        theme = SocialTheme.Instagram
    ) {
        MemoriesScreen(
            viewModel = previewViewModel,
            onChannelClick = {},
            onAddMemoryClick = {}
        )
    }
}

