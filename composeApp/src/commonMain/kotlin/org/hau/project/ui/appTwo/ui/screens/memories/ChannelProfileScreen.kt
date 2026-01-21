/*package org.hau.project.ui.appTwo.ui.screens.memories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Dialpad
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.ForwardToInbox
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import org.hau.project.ui.appOne.BackButton
import org.hau.project.ui.appTwo.data.repositories.formatCount
import org.hau.project.ui.appTwo.domain.models.RecommendedChannels
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenCompact(
    onSignOut: () -> Unit,
    onNavigateBack: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
) {
    val channels = RecommendedChannels(
        channelRes = Res.drawable.grattitude,
        channelName = "Gratitude",
        followerCount = 242345235,
        isVerified = true
    )
    var isBottomPrivacySheet by remember{ mutableStateOf(false) }
    var isBottomVerifiedSheet by remember{ mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val lazyListState = rememberLazyListState()
    val bannerHeight = 220.dp
    val bannerHeightPx = with(LocalDensity.current) { bannerHeight.toPx() }
    val avatarInitialSize = 120.dp
    val avatarFinalSize = 40.dp

    //var isBottomSheet by remember { mutableStateOf(true) }

    if (isBottomPrivacySheet){
        ModalBottomSheet(
            onDismissRequest = { },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            PrivacyBottonSheet(
                onClick = { isBottomPrivacySheet  }
            )
        }
    }
    if (isBottomVerifiedSheet){
        ModalBottomSheet(
            onDismissRequest = { },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            VerifiedInfoBottomSheet(
                onClose = { isBottomVerifiedSheet }
            )
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        // This Box is responsible for the banner height + half the avatar height
                        .height(bannerHeight + avatarInitialSize / 2)
                ) {
                    // --- Parallax Banner ---
                    AsyncImage(
                        model = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
                        contentDescription = "Profile banner",
                        error = painterResource(Res.drawable.grattitude),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(bannerHeight)
                            .graphicsLayer {
                                val scrollOffset = if (lazyListState.firstVisibleItemIndex == 0) lazyListState.firstVisibleItemScrollOffset.toFloat() else bannerHeightPx
                                translationY = scrollOffset * 0.5f
                                alpha = 1f - (scrollOffset / bannerHeightPx).coerceIn(0f, 1f)
                            }
                    )
                    Box(modifier = Modifier
                        .matchParentSize()
                        .background(Brush.verticalGradient(listOf(Color.Black.copy(0.4f), Color.Transparent), endY = 200f))){
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.Start
                        ){
                            BackButton(onBackClick = {onNavigateBack}, modifier = Modifier)
                        }
                    }

                    // --- Animated Avatar (Correctly Positioned) ---
                    val scrollOffset = if (lazyListState.firstVisibleItemIndex == 0) lazyListState.firstVisibleItemScrollOffset.toFloat() else bannerHeightPx
                    val collapsedPercentage = (scrollOffset / (bannerHeightPx - (avatarInitialSize.value / 2))).coerceIn(0f, 1f)
                    val avatarSize = lerp(avatarInitialSize, avatarFinalSize, collapsedPercentage)
                    // The Y offset starts at the bottom of the banner minus half the avatar's size
                    val avatarY = (bannerHeight - (avatarInitialSize / 2))

                    Box(
                        Modifier
                            .padding(top = avatarY)
                            .size(avatarSize)
                            .align(Alignment.TopCenter)
                            .graphicsLayer {
                                // We don't need to move this Box itself, its content changes size.
                                // It will be covered by the sticky header.
                            }
                    ) {
                        ProfileAvatar(channels.channelName, "Profile Picture")
                    }
                }
            }
            stickyHeader {
                val scrollOffset = if (lazyListState.firstVisibleItemIndex == 0) lazyListState.firstVisibleItemScrollOffset.toFloat() else bannerHeightPx

                // Correctly calculate pixel values within a Density scope
                val (collapseStartOffset, isCollapsed) = with(LocalDensity.current) {
                    // Collapse starts when avatar is about to go under the sticky header
                    val start = bannerHeightPx - avatarInitialSize.toPx() / 2 - 8.dp.toPx()
                    val collapsed = lazyListState.firstVisibleItemIndex > 0 || scrollOffset > start
                    start to collapsed // Return both calculated values
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = if(isCollapsed)
                        MaterialTheme.colorScheme.background
                    else
                        Color.Transparent,
                    shadowElevation = if (isCollapsed) 4.dp else 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(
                            visible = isCollapsed,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(avatarFinalSize)) {
                                    ProfileAvatar("${channels.channelName}", "Profile Picture")
                                }
                                Spacer(Modifier.width(16.dp))
                                Text(
                                    text = channels.channelName?: "Ali Stack",
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }
                    }
                }
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.clickable{
                            isBottomVerifiedSheet = true
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = channels.channelName ?: "Ali Stack",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if(channels.isVerified){
                            Icon(
                                Icons.Filled.Verified,
                                contentDescription = channels.channelName,
                                tint = Color.Blue
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Channel ∙ ${formatCount(channels.followerCount)} followers",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    items(getChannelActions()){channelActions->
                        ChannelDescActionItem(channelActions)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    4.dp,
                    color = Color.Black.copy(alpha = 0.2f)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .height(40.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        text = "Created on 01/09/2023"
                    )
                }
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    4.dp,
                    color = Color.Black.copy(alpha = 0.2f)
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Media and links",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "3,567",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Outlined.ArrowForwardIos,
                        contentDescription = "Arrow Forward",
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    4.dp,
                    color = Color.Black.copy(alpha = 0.2f)
                )
            }
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.Black.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Mute Notifications",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Switch(
                        checked = checked,
                        onCheckedChange = onCheckedChange,
                    )
                }
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    4.dp,
                    color = Color.Black.copy(alpha = 0.2f)
                )
            }
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(46.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        Icons.Outlined.Public,
                        contentDescription = "Public",
                        tint = Color.Black.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier,
                            text = "Public channel",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier,
                            text = "Anyone can find this channel and see what's been shared",
                            fontSize = 17.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            isBottomPrivacySheet = true
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        Icons.Outlined.Dialpad,
                        contentDescription = "Profile Privacy",
                        tint = Color.Black.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier,
                            text = "Profile privacy",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier,
                            text = "This channel has added privacy for your profile and phone number. Tap to learn more.",
                            fontSize = 17.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    4.dp,
                    color = Color.Black.copy(alpha = 0.2f)
                )
            }
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        Icons.Outlined.Logout,
                        contentDescription = "Unfollow",
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        modifier = Modifier,
                        text = "Unfollow channel",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Red
                        )
                    )
                }
            }
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp)
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        Icons.Outlined.ThumbDown,
                        contentDescription = "Report channel",
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        modifier = Modifier,
                        text = "Report channel",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Red
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileAvatar(
    model: String?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
    ) {
        AsyncImage(
            model = model ?: "https://images.pexels.com/photos/1065084/pexels-photo-1065084.jpeg",
            contentDescription = contentDescription,
            error = painterResource(Res.drawable.grattitude),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
    }
}

@Composable
fun ChannelDescActionItem(
    channelAction: ChannelDescAction
){
    Box(
        modifier = Modifier
            .height(60.dp)
            .border(1.dp,Color.Black.copy(.4f),RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .width(100.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                channelAction.icon,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = channelAction.descAction
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = channelAction.descAction
            )
        }
    }
}
@Composable
fun PrivacyBottonSheet(
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Added privacy for your profile and phone number",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.height(46.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                Icons.Outlined.VisibilityOff,
                contentDescription = "Follower Privacy",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier,
                    text = "You are not visible to other followers",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier,
                    text = "Followers can't see when you follow or interact in a channel.",
                    fontSize = 17.sp,
                    maxLines = 3,
                    color = Color.Black.copy(alpha = 0.6f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.height(46.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.Dialpad,
                contentDescription = "Number privacy",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "Your Phone Number is protected",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier,
                    text = "Admins can't see your full phone number, unless they've saved you as a contact.",
                    fontSize = 17.sp,
                    maxLines = 3,
                    color = Color.Black.copy(alpha = 0.6f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(46.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    Icons.Outlined.PersonPinCircle,
                    contentDescription = "Person Privacy",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        modifier = Modifier,
                        text = "Admins can see your profile name",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier,
                        text = "Admins may also see your profile photo based on your privacy settings",
                        fontSize = 17.sp,
                        maxLines = 3,
                        color = Color.Black.copy(alpha = 0.6f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        Button(
            onClick = {},
            modifier = Modifier
                .padding(top =16.dp, end = 16.dp, start = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ){
            Text(
                "OK"
            )
        }
        TextButton(
            onClick = {}
        ){
            Text(
                text = "Learn more",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun VerifiedInfoBottomSheet(
    onClose: ()-> Unit
){
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.Black.copy(alpha = .10f),CircleShape),
            contentAlignment = Alignment.Center
        ){
            Icon(
                Icons.Filled.Verified,
                tint = Color.Blue,
                contentDescription = "Verified Icon"
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Verified",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Hau's parent company Alidon has verified this account based on their activity across our products and information or documents they provide.",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = .6f)
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            )
        ){
            Text(
                text = "Learn more about verified accounts"
            )
         }
    }
}

@Composable
@Preview(showBackground = true)
fun VerifiedInfoBottomSheetPreview(){
    VerifiedInfoBottomSheet(
        onClose = {}
    )
}
@Composable
@Preview(showBackground = true)
fun PrivacyBottomSheetPreview(){
    PrivacyBottonSheet(
        onClick = {}
    )
}
@Composable
@Preview(showBackground = true)
fun ChannelDescActionItemPreview(){
    ChannelDescActionItem(
        ChannelDescAction(
            Icons.Outlined.Share,
            "Share"
        )
    )
}
data class ChannelDescAction(
    val icon: ImageVector,
    val descAction: String
)

fun getChannelActions() = listOf(
    ChannelDescAction(
        icon = Icons.Outlined.Done,
        descAction = "Following"
    ),
    ChannelDescAction(
        icon = Icons.Outlined.ForwardToInbox,
        descAction = "Forward"
    ),
    ChannelDescAction(
        icon = Icons.Outlined.Share,
        descAction = "Share"
    )
)

@Composable
@Preview(showBackground = true)
fun ProfileScreenCompactPreview(){
    ProfileScreenCompact(
        onSignOut = {},
        onNavigateBack = {},
        onCheckedChange = {},
        checked = true,
    )
}



*/
package org.hau.project.ui.appTwo.ui.screens.memories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import kotlinx.coroutines.launch
import org.hau.project.ui.appTwo.data.repositories.formatCount
import org.hau.project.ui.appTwo.domain.models.RecommendedChannels
import org.hau.project.ui.appTwo.ui.theme.AppTheme
import org.hau.project.ui.appTwo.ui.theme.SocialTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// --- STATE & DATA CLASSES ---

/**
 * Represents all possible actions from the UI, decoupling the UI from business logic.
 */
sealed interface ProfileAction {
    data object NavigateBack : ProfileAction
    data object ToggleMute : ProfileAction
    data object ViewMedia : ProfileAction
    data object Unfollow : ProfileAction
    data object Report : ProfileAction
}

/**
 * Represents the types of bottom sheets that can be shown.
 */
private enum class BottomSheetType {
    PRIVACY, VERIFIED
}

/**
 * A comprehensive, single source of truth for the profile screen's UI state.
 */
data class ProfileUiState(
    val isLoading: Boolean = false,
    val channel: RecommendedChannels? = null,
    val isMuted: Boolean = false,
    val mediaCount: Int = 0,
    val bannerUrl: String = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
    val error: String? = null
)

// --- MAIN SCREEN COMPOSABLE ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelProfileScreen(
    uiState: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var activeBottomSheet by remember { mutableStateOf<BottomSheetType?>(null) }
    val modalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Derived state for calculating scroll-based animations efficiently.
    val scrollOffset by remember {
        derivedStateOf {
            if (lazyListState.firstVisibleItemIndex == 0) lazyListState.firstVisibleItemScrollOffset.toFloat() else Float.MAX_VALUE
        }
    }

    val bannerHeight = 220.dp
    val avatarInitialSize = 120.dp

    // Determines if the header is collapsed for the TopAppBar animation.
    val isHeaderCollapsed = with(LocalDensity.current) {
        val collapseThreshold = bannerHeight.toPx() - TopAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding().toPx()
        scrollOffset > collapseThreshold
    }

    Scaffold(
        topBar = {
            ProfileTopAppBar(
                channelName = uiState.channel?.channelName ?: "",
                avatarUrl = uiState.channel?.channelRes,
                isCollapsed = isHeaderCollapsed,
                onNavigateBack = { onAction(ProfileAction.NavigateBack) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text(uiState.error, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
                }
            }
            uiState.channel != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding()),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // --- HEADER: BANNER & AVATAR ---
                    item {
                        ProfileHeader(
                            bannerUrl = uiState.bannerUrl,
                            bannerHeight = bannerHeight,
                            avatarInitialSize = avatarInitialSize,
                            scrollOffset = scrollOffset,
                            avatarUrl = uiState.channel.channelRes
                        )
                    }

                    // --- INFO: NAME, FOLLOWERS, ACTIONS ---
                    item {
                        ChannelInfoSection(
                            channel = uiState.channel,
                            onShowVerified = { activeBottomSheet = BottomSheetType.VERIFIED }
                        )
                    }

                    item {
                        ActionButtonsRow()
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 8.dp)
                    }

                    // --- SETTINGS & INFO LIST ---
                    item {
                        SettingsSection(
                            mediaCount = uiState.mediaCount,
                            isMuted = uiState.isMuted,
                            onAction = onAction,
                            onShowPrivacy = { activeBottomSheet = BottomSheetType.PRIVACY }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 8.dp)
                    }

                    // --- DANGEROUS ACTIONS ---
                    item {
                        DangerZoneSection(onAction)
                    }
                }
            }
        }

        // --- BOTTOM SHEET HANDLER ---
        if (activeBottomSheet != null) {
            ModalBottomSheet(
                onDismissRequest = { activeBottomSheet = null },
                sheetState = modalSheetState,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                when (activeBottomSheet) {
                    BottomSheetType.VERIFIED -> VerifiedInfoBottomSheet(onClose = {
                        coroutineScope.launch { modalSheetState.hide() }.invokeOnCompletion { activeBottomSheet = null }
                    })
                    BottomSheetType.PRIVACY -> PrivacyInfoBottomSheet(onClose = {
                        coroutineScope.launch { modalSheetState.hide() }.invokeOnCompletion { activeBottomSheet = null }
                    })
                    null -> {}
                }
            }
        }
    }
}


// --- REFINED SUB-COMPONENTS ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopAppBar(
    channelName: String,
    avatarUrl: Any?,
    isCollapsed: Boolean,
    onNavigateBack: () -> Unit
) {
    // A separate surface handles the background color transition for a cleaner effect
    Surface(
        color = if (isCollapsed) MaterialTheme.colorScheme.background else Color.Transparent,
        shadowElevation = if (isCollapsed) 2.dp else 0.dp
    ) {
        TopAppBar(
            title = {
                AnimatedVisibility(
                    visible = isCollapsed,
                    enter = fadeIn(animationSpec = tween(200, delayMillis = 100)),
                    exit = fadeOut(animationSpec = tween(100))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = avatarUrl,
                            contentDescription = "Profile Avatar",
                            error = painterResource(Res.drawable.grattitude),
                            modifier = Modifier.size(36.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = channelName,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    // Use a scrim for better visibility on the banner
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                }
            },
            actions = {
                IconButton(onClick = { /* More options */ }) {
                    Icon(Icons.Default.MoreVert, "More Options", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }
}

@Composable
private fun ProfileHeader(
    bannerUrl: String,
    bannerHeight: Dp,
    avatarInitialSize: Dp,
    scrollOffset: Float,
    avatarUrl: Any?
) {
    val bannerHeightPx = with(LocalDensity.current) { bannerHeight.toPx() }
    val avatarYPosition = bannerHeight - (avatarInitialSize / 2)

    Box(
        modifier = Modifier.fillMaxWidth().height(bannerHeight + avatarInitialSize / 2),
        contentAlignment = Alignment.TopCenter
    ) {
        // --- Parallax Banner ---
        AsyncImage(
            model = bannerUrl,
            contentDescription = "Profile banner",
            error = painterResource(Res.drawable.grattitude),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(bannerHeight).graphicsLayer {
                translationY = scrollOffset * 0.5f // Parallax effect
                alpha = 1f - (scrollOffset / bannerHeightPx).coerceIn(0f, 1f) // Fade out
            }
        )
        // --- Gradient Overlay for Top Bar contrast ---
        Box(
            modifier = Modifier.fillMaxWidth().height(80.dp).background(
                Brush.verticalGradient(colors = listOf(Color.Black.copy(0.5f), Color.Transparent))
            )
        )

        // --- Animated Avatar ---
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = avatarYPosition)
                .size(avatarInitialSize)
                .graphicsLayer {
                    // Avatar shrinks as it scrolls up under the toolbar
                    val collapsePercentage = (scrollOffset / (bannerHeightPx - avatarInitialSize.toPx() / 2)).coerceIn(0f, 1f)
                    val scale = lerp(1f.sp, 0f.sp, collapsePercentage).value
                    scaleX = scale
                    scaleY = scale
                    alpha = 1f - collapsePercentage
                }
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Profile Picture",
                error = painterResource(Res.drawable.grattitude),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .border(4.dp, MaterialTheme.colorScheme.background, CircleShape)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
private fun ChannelInfoSection(channel: RecommendedChannels, onShowVerified: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 8.dp) // Added top padding
    ) {
        Row(
            modifier = Modifier.clickable(
                onClick = onShowVerified,
                indication = null, // No ripple effect for this click
                interactionSource = remember { MutableInteractionSource() }
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = channel.channelName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (channel.isVerified) {
                Spacer(Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Channel ∙ ${formatCount(channel.followerCount)} followers",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun ActionButtonsRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Button(onClick = {}, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Check, contentDescription = "Following", modifier = Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Following")
        }
        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
            Icon(Icons.Outlined.Share, contentDescription = "Share", modifier = Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Share")
        }
    }
}

@Composable
private fun SettingsSection(
    mediaCount: Int,
    isMuted: Boolean,
    onAction: (ProfileAction) -> Unit,
    onShowPrivacy: () -> Unit
) {
    Column {
        SettingsRow(
            icon = Icons.Outlined.PermMedia,
            text = "Media, Links, and Docs",
            trailingText = formatCount(mediaCount.toLong()),
            onClick = { onAction(ProfileAction.ViewMedia) }
        )
        SettingsRow(
            icon = Icons.Outlined.Notifications,
            text = "Mute Notifications",
            isToggle = true,
            checked = isMuted,
            onCheckedChange = { onAction(ProfileAction.ToggleMute) }
        )
        SettingsRow(
            icon = Icons.Outlined.Public,
            text = "Public Channel",
            description = "Anyone can find this channel and see what's been shared.",
            onClick = {} // Non-interactive for now
        )
        SettingsRow(
            icon = Icons.Outlined.Dialpad,
            text = "Profile Privacy",
            description = "This channel has added privacy for your profile and phone number.",
            onClick = onShowPrivacy
        )
    }
}

@Composable
private fun DangerZoneSection(onAction: (ProfileAction) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        DangerSettingsRow(
            icon = Icons.AutoMirrored.Filled.Logout,
            text = "Unfollow Channel",
            onClick = { onAction(ProfileAction.Unfollow) }
        )
        DangerSettingsRow(
            icon = Icons.Outlined.ThumbDown,
            text = "Report Channel",
            onClick = { onAction(ProfileAction.Report) }
        )
    }
}


// --- GENERIC & UTILITY COMPOSABLES ---

@Composable
private fun SettingsRow(
    icon: ImageVector,
    text: String,
    description: String? = null,
    trailingText: String? = null,
    isToggle: Boolean = false,
    checked: Boolean = false,
    onCheckedChange: () -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = onClick ?: {})
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(text, style = MaterialTheme.typography.bodyLarge)
            if (description != null) {
                Spacer(Modifier.height(2.dp))
                Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 18.sp)
            }
        }
        if (trailingText != null) {
            Text(trailingText, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        if (isToggle) {
            Switch(checked = checked, onCheckedChange = { onCheckedChange() })
        }
    }
}

@Composable
private fun DangerSettingsRow(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun PrivacyInfoBottomSheet(onClose: () -> Unit) {
    Column(
        modifier = Modifier.navigationBarsPadding().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile & Phone Number Privacy", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Text(
            "Admins can't see your full phone number unless they've saved you as a contact. Other followers can't see your profile at all.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onClose, modifier = Modifier.fillMaxWidth()) {
            Text("OK")
        }
    }
}

@Composable
private fun VerifiedInfoBottomSheet(onClose: () -> Unit) {
    Column(
        modifier = Modifier.navigationBarsPadding().padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Filled.Verified,
            contentDescription = "Verified Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Text("Verified Channel", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(
            "This channel is verified because it's notable in government, news, entertainment, or another designated category.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        TextButton(onClick = { /*TODO: Link to learn more*/ }) {
            Text("Learn More")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onClose, modifier = Modifier.fillMaxWidth()) {
            Text("Done")
        }
    }
}

// --- FULLY THEMED PREVIEWS ---

// Dummy data for previews
private val previewState = ProfileUiState(
    isLoading = false,
    channel = RecommendedChannels(
        channelRes = Res.drawable.grattitude,
        channelName = "Mindful Moments",
        followerCount = 1_234_567,
        isVerified = true
    ),
    mediaCount = 3567,
    isMuted = false
)

@Preview(name = "Light Mode (WhatsApp Theme)")
@Composable
private fun ChannelProfileScreenPreviewLight() {
    AppTheme(theme = SocialTheme.WhatsApp, useDarkTheme = false) {
        Surface {
            ChannelProfileScreen(uiState = previewState, onAction = {})
        }
    }
}

@Preview(name = "Dark Mode (Twitter Theme)")
@Composable
private fun ChannelProfileScreenPreviewDark() {
    AppTheme(theme = SocialTheme.Twitter, useDarkTheme = true) {
        Surface {
            ChannelProfileScreen(uiState = previewState.copy(isMuted = true), onAction = {})
        }
    }
}

@Preview(name = "Loading State")
@Composable
private fun ChannelProfileScreenLoadingPreview() {
    AppTheme {
        Surface {
            ChannelProfileScreen(uiState = ProfileUiState(isLoading = true), onAction = {})
        }
    }
}

@Preview(name = "Error State")
@Composable
private fun ChannelProfileScreenErrorPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            ChannelProfileScreen(
                uiState = ProfileUiState(error = "Could not load channel details. Please check your connection."),
                onAction = {}
            )
        }
    }
}
