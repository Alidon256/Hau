package org.hau.project.ui.appTwo.ui.screens.memories

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



