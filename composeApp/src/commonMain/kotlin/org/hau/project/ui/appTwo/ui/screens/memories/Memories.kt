/*package org.hau.project.ui.appTwo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.outlined.Attachment
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import hau.composeapp.generated.resources.image_two
import hau.composeapp.generated.resources.story_1
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import hau.composeapp.generated.resources.story_4
import hau.composeapp.generated.resources.story_5
import org.hau.project.ui.appTwo.data.repositories.ChatRepository
import org.hau.project.ui.appTwo.data.repositories.formatCount
import org.hau.project.ui.appTwo.domain.models.AttachmentType
import org.hau.project.ui.appTwo.domain.models.Channels
import org.hau.project.ui.appTwo.domain.models.RecommendedChannels
import org.hau.project.ui.appTwo.viewModels.ChatViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoriesScreen(
    viewModel: ChatViewModel,
    onChannelClick: ()-> Unit,
    onAddMemoryClick: ()-> Unit
){
    val uiChannelState by viewModel.channelState.collectAsState()
    val uiRecommendedChannels by viewModel.recommendedState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Memories",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                actions = {
                    IconButton(
                        onClick = {}
                    ){
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        onClick = {}
                    ){
                        Icon(
                            Icons.Outlined.MoreVert,
                            contentDescription = "More Options",
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .clickable{
                        onAddMemoryClick()
                    }
                    .background(MaterialTheme.colorScheme.primary,CircleShape)
                    .size(64.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Outlined.AddAPhoto,
                    contentDescription = "Add Memory",
                    tint = Color.White
                )
            }
        },
        containerColor = Color.White
    ){paddingValues ->
       if (uiChannelState.isLoading && uiRecommendedChannels.isLoading){
           Box(
               modifier = Modifier
                   .fillMaxSize(),
               contentAlignment = Alignment.Center
           ){
               CircularProgressIndicator()
           }
       } else if (uiChannelState.error != null ){
           Box(
               modifier = Modifier
                   .fillMaxSize(),
               contentAlignment = Alignment.Center
           ){
               Text(
                   text = "Error: ${uiChannelState.error}",
                   color = MaterialTheme.colorScheme.error
               )
           }
       } else if (uiRecommendedChannels.error != null){
           Box(
               modifier = Modifier
                   .fillMaxSize(),
               contentAlignment = Alignment.Center
           ){
               Text(
                   text = "Error: ${uiRecommendedChannels.error}",
                   color = MaterialTheme.colorScheme.error
               )
           }
    } else{
        
       }

    }
}
@Composable
@Preview
fun StatusScreenPreview(){
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    MemoriesScreen(
        viewModel = previewViewModel,
        onChannelClick = {},
        onAddMemoryClick = {}
    )
}

@Composable
fun MemoriesActionItem(
    callItem: CallActions
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.Black.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ){
            if (callItem.isCommunity){
                Image(
                    painter = painterResource(Res.drawable.grattitude),
                    contentDescription = "Community",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Icon(
                callItem.icon,
                contentDescription = callItem.actionText,
                tint = Color.Black
            )
        }
        Text(
            text = callItem.actionText,

            )
    }
}

@Composable
fun MemoriesBanner(
    isWide: Boolean = false,
    onClick: () -> Unit = {}
) {

    val bannerHeight = if (isWide) 120.dp else 100.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
                .padding(20.dp),
        ){
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd
                ){
                    Image(
                        painter = painterResource(Res.drawable.story_3),
                        contentDescription = "Story Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape).size(56.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .offset(y= -1.dp, x = 2.dp)
                            .background(MaterialTheme.colorScheme.primary,CircleShape),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Column(Modifier.weight(1f)) {
                    Text(
                        "Add Memories",
                        style = MaterialTheme.typography.titleMedium.copy(
                            //fontSize = titleFontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "Disappears after 24 hours",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

    }
}

@Composable
fun ChannelItem(channels: Channels){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        Color.White.copy(alpha = 0.3f),
                        CircleShape
                    )
            ){
                Image(
                    painter = painterResource(channels.channelRes),
                    contentDescription = "Profile Image",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = channels.channelName,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = channels.timestamp,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        maxLines = 1,
                        color = if(!channels.isRead)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End
                ){
                    when(channels.attachmentType){
                        AttachmentType.IMAGE -> Icon(
                            Icons.Outlined.Photo,
                            contentDescription = channels.channelName,
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.Black.copy(alpha = 0.5f)
                        )
                        AttachmentType.AUDIO -> Icon(
                            Icons.Outlined.Audiotrack,
                            contentDescription = channels.channelName,
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.Black.copy(alpha = 0.5f)
                        )
                        AttachmentType.VIDEO -> Icon(
                            Icons.Outlined.Videocam,
                            contentDescription = channels.channelName,
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.Black.copy(alpha = 0.5f)
                        )
                        else -> Icon(
                            Icons.Outlined.Attachment,
                            contentDescription = channels.channelName,
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.Black.copy(alpha = 0.5f)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = channels.message,
                            fontSize = 18.sp,
                            color = Color.Black.copy(alpha = 0.5f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Normal,
                            //modifier = Modifier.weight(1f, fill = false).padding(end = 8.dp)
                        )
                    }

                    if (channels.unreadMessages>0){
                        Box(
                            modifier = Modifier
                                //.size(20.dp)
                                .background(Color(0xFF703EFF),CircleShape),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = channels.unreadMessages.toString(),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(4.dp),
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendedChannelsItem(
    recommended: RecommendedChannels
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                    color = Color.Black.copy(alpha = 0.6f),
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
@Composable
@Preview(showBackground = true)
fun RecommendedChannelsItemPreview(){
    RecommendedChannelsItem(
        RecommendedChannels(
            channelName = "Gratitude",
            channelRes = Res.drawable.grattitude,
            isVerified = true,
            followerCount = 123000
        )
    )
}
@Composable
@Preview(showBackground = true)
fun MemoriesBannerPreview(){
    MemoriesBanner(
        isWide = false,
        onClick = {}
    )
}

@Composable
@Preview(showBackground = true)
fun ChannelsItemPreview(){
    ChannelItem(
        Channels(
            channelName = "Mugumya Ali",
            channelRes = Res.drawable.story_3,
            timestamp = "Just now",
            attachmentType = AttachmentType.AUDIO,
            message = "I just found out that the best way of making your self better is to keep going",
            unreadMessages = 126,
            isRead = false
        ),
    )
}
@Composable
@Preview(showBackground = true)
fun MemoriesPreview(){
    MemoriesActionItem(
        CallActions( Icons.Outlined.FavoriteBorder,"Favourites", isCommunity = false)
    )
}


*/

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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                        Text(
                            "Recent updates",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    items(uiChannelState.channels.filter { !it.isRead }) { channel ->
                        ChannelItem(channel = channel, onClick = {onChannelClick})
                    }

                    item {
                        Text(
                            "Viewed updates",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    items(uiChannelState.channels.filter { it.isRead }) { channel ->
                        ChannelItem(channel = channel, onClick = {onChannelClick}, isViewed = true)
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
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
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
            .padding(vertical = 8.dp),
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
                    color = Color.Black.copy(alpha = 0.6f),
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

// Main screen preview remains the same
@Preview(name = "Full Updates Screen")
@Composable
fun MemoriesScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)

    // AppTheme {
    MemoriesScreen(
        viewModel = previewViewModel,
        onChannelClick = {},
        onAddMemoryClick = {}
    )
    // }
}

