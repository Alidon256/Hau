package org.hau.project.ui.appOne

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.image_three
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShortScreen(
    post: Post,
    onBackClick: () -> Unit
    ) {
    // Frosted Glass Post Card
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter

    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .zIndex(1f)
                .height(200.dp)
                //.graphicsLayer(clip = true)
                .blur(radius = 20.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // Start transparent at the top of the box
                            Color.Transparent, // Start transparent at the top of the box
                            Color.Black.copy(alpha = 0.4f), // End with a subtle, transparent green tint
                            Color.Black.copy(alpha = 0.4f), // End with a subtle, transparent green tint
                        ),
                        startY = 0f,
                        endY = 500f // Extend the gradient to soften the transition
                    )
                )
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // Start transparent at the top of the box
                            Color(0xFFADFF2F).copy(alpha = 0.2f), // End with a subtle, transparent green tint
                        ),
                        startY = 0f,
                        endY = 400f // Extend the gradient to soften the transition
                    )
                )
        )
        Image(
            painter = painterResource(post.postImage),
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop

        )
        Column (
            modifier = Modifier
                .zIndex(2f)
        ){
            FollowBar(
                isSelected = false,
                onBackClick = onBackClick
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                SideBar(post = post, modifier = Modifier)
            }
            Spacer(modifier = Modifier.weight(0.1f))
            ShortsDetailsItem(post = post)
        }

    }
}

@Composable
fun OptionsItem(
    icon: ImageVector,
    label: String,
    tint: Color,
    bgColor :  Color
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(30.dp)
            .clip(CircleShape)
            .border(1.dp, bgColor.copy(alpha = 0.2f), CircleShape)
            .background(tint.copy(alpha = 0.1f), CircleShape),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                //.fillMaxSize()
                .padding(8.dp)
                .graphicsLayer(
                    clip = true,
                    shape = CircleShape
                )
                .blur(radius = 250.dp)
                .background(Color.Black.copy(alpha = 0.4f))
                .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                icon, contentDescription = label, tint = tint, modifier = Modifier.size(24.dp)
            )
            Text(
                label,
                color = tint
            )
        }

    }
}

@Composable
fun BackButton(
    onBackClick: ()-> Unit,
    modifier: Modifier
){
    Box(
        modifier = modifier
            .size(56.dp)
            .background(
                Color.White.copy(alpha = 0.15f),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ){
        Icon(
            Icons.Outlined.ArrowBackIosNew,
            contentDescription = "Back Arrow",
            tint = Color.White
        )
    }
}
@Composable
fun FollowBar(
    isSelected: Boolean,
    onBackClick: () -> Unit
){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        BackButton(
            onBackClick = onBackClick,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(0.2f))
        Row(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Following",
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            VerticalDivider(
                modifier = Modifier
                    .height(16.dp),
                thickness = 2.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "For You",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(0.25f))
    }

}

@Composable
fun ShortsDetailsItem(post: Post){
    Column(
        modifier = Modifier
            .zIndex(2f)
            .padding(16.dp)
    ) {
        // Post Header
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(post.authorImage),
                contentDescription = post.author,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    post.author,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                Color.White.copy(alpha = 0.15f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center

                    ){
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location",
                            tint = Color.White
                        )
                    }
                    Text(
                        "USA, Carifonia",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

            }
            /*IconButton(
                onClick = { /* TODO */ }
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.White
                )
            }*/
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Post Content
        if (post.content.isNotBlank()) {
            Text(
                post.content,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            OptionsItem(Icons.Outlined.MusicNote,"Music",Color.White,Color.White)
            OptionsItem(Icons.Outlined.Videocam,"Camera",Color.White,Color.White)
            OptionsItem(Icons.Outlined.Star,"Sport",Color.White,Color.White)
        }
    }
}

@Composable
fun SideBar(post: Post,modifier: Modifier){
    Box(
        modifier = modifier
            .background(
                Color.Black.copy(alpha = 0.3f),
                RoundedCornerShape(16.dp,0.dp,0.dp,16.dp)
            )
            .background(
                Color.White.copy(alpha = 0.15f),
                RoundedCornerShape(16.dp,0.dp,0.dp,16.dp)
            )
    ){
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PostActionButton(Icons.Outlined.FavoriteBorder, contentDescription = "Like", text = post.likes, modifier = Modifier)
            PostActionButton(Icons.Outlined.ChatBubbleOutline, contentDescription = "Comment", text = post.comments, modifier = Modifier)
            PostActionButton(Icons.Outlined.BookmarkBorder, contentDescription = "Save", text = "Save", modifier = Modifier)
            PostActionButton(Icons.Outlined.Send, contentDescription = "Share", text = post.shares, modifier = Modifier.rotate(-30f))
        }
    }
}

@Composable
fun PostActionButton(
    icon: ImageVector,
    text: String,
    contentDescription:String,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(38.dp)
                .border(
                    1.dp,
                    Color.White.copy(alpha = 0.3f),
                    CircleShape
                )
                .background(
                    Color.White.copy(alpha = 0.15f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ){
            Icon(
                icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = modifier.zIndex(1f)
            )
        }
        Text(
            text,
            color = Color.White,
            fontSize = 12.sp
        )
    }

}
@Preview
@Composable
fun SideBarPreview(){
    SideBar(post = Post(
        "Gabriella Collins",
        Res.drawable.image_three,
        "1h ago",
        "Life reflects what we show, a smile brings the brightest results",
        Res.drawable.image_three,
        "1,253k",
        "1,253k",
        "100"),
        modifier = Modifier
    )
}
@Preview
@Composable
fun FollowBarPreview(){
    FollowBar(
        isSelected = true,
        onBackClick = {}
    )
}
@Preview
@Composable
fun BackButtonPreview(){
    BackButton(
        onBackClick = {},
        modifier = Modifier
    )
}
@Preview
@Composable
fun OptionsItemPreview(){
    OptionsItem(
        icon = Icons.Outlined.MusicNote,
        label = "Music",
        tint = Color.White,
        bgColor = Color.White
    )
}
@Preview
@Composable
fun ShortScreenPreview(){
    ShortScreen(
        Post(
            "Gabriella Collins",
            Res.drawable.image_three,
            "1h ago",
            "Life reflects what we show, a smile brings the brightest results",
            Res.drawable.image_three,
            "1,253k",
            "1,253k",
            "100"),
        onBackClick = {}
    )
}
