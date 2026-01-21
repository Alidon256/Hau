package org.hau.project.ui.appOne

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import hau.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FeedScreen() {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(clip = true)
                .zIndex(1f)
                .blur(radius = 20.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1E2A0B),
                            Color.Black.copy(0.85f)
                        ),
                        center = Offset(0.5f, 0.5f),
                        radius = 1200f
                    )
                )
        )
        Image(
            painter = painterResource(Res.drawable.bg),
            contentDescription = "Onboarding Background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
           // alignment = Alignment.CenterStart
        )

        LazyColumn(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item { FeedTopBar() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { StoriesSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }

            items(getFeedPosts()) { post ->
                FeedPostItem(post = post)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Floating Bottom Navigation Bar
       BottomNavigation(
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.BottomCenter)
       )
    }
}

@Composable
private fun BottomNavigation(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .clip(CircleShape)
            .padding(bottom = 32.dp, start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF77A328), Color(0xFFE9D409)) // GreenYellow to LimeGreen
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        clip = true,
                        shape = CircleShape
                    )
                    .blur(radius = 250.dp)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
            )
            Icon(
                Icons.Outlined.Home, contentDescription = "Home", tint = Color.White, modifier = Modifier.size(24.dp)
            )
        }

        Box(
            modifier = modifier
                .background(
                    Color.Black.copy(alpha = 0.3f),
                    CircleShape
                )
                .background(
                    Color.White.copy(alpha = 0.15f),CircleShape
                )
                .border(1.dp,Color.White.copy(alpha = 0.2f),CircleShape),
                //.height(64.dp),
            contentAlignment = Alignment.Center,
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationItem(Icons.Outlined.Videocam, label = "Search", tint = Color.White, bgColor = Color.White,modifier)
                NavigationItem(Icons.Outlined.ChatBubbleOutline, label = "Messages", tint = Color.White, bgColor = Color.White,modifier)
                NavigationItem(Icons.Outlined.PersonOutline, label = "Profile", tint = Color.White, bgColor = Color.White,modifier)
            }
        }
    }

}

@Composable
@Preview
fun BottomNavigationPreview(){
    BottomNavigation(modifier = Modifier)
}
@Composable
fun NavigationItem(
    icon: ImageVector,
    label: String,
    tint: Color,
    bgColor :  Color,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .border(1.dp, bgColor.copy(alpha = 0.2f), CircleShape)
            .background(tint.copy(alpha = 0.2f), CircleShape),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    clip = true,
                    shape = CircleShape
                )
                .blur(radius = 250.dp)
                .background(Color.Black.copy(alpha = 0.4f))
                .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
        )
      Icon(
          icon, contentDescription = label, tint = tint, modifier = Modifier.size(24.dp)
      )
    }
}

@Composable
fun FeedTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Image(
                painter = painterResource(Res.drawable.story_3),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(
                        2.dp,
                        Brush.verticalGradient(listOf(Color(0xFFADFF2F), Color(0xFF32CD32))),
                        CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    "Heyz 👋",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Text(
                    "Maya Quinn",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        NotificationItem()
    }
}
@Preview
@Composable
fun NotificationItem(){
    Row(
       modifier = Modifier
           .background(Color.Black.copy(alpha = 0.15f),
               CircleShape
           )
           .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
    ){
        Box(
            modifier = Modifier
                .size(50.dp),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            Icon(
                Icons.Outlined.Notifications, contentDescription = "Home", tint = Color.White, modifier = Modifier.size(24.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                .background(Color.White.copy(alpha = 0.15f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        clip = true,
                        shape = CircleShape
                    )
                    .blur(radius = 250.dp)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
            )
            Icon(
                Icons.Outlined.Message, contentDescription = "Home", tint = Color.White, modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun StoriesSection() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StoryItem(name = "Add Story", imageRes = null, onAddClick = {})
        }
        items(getStories()) { story ->
            StoryItem(name = story.name, imageRes = story.imageRes)
        }
    }
}

@Composable
fun StoryItem(name: String, imageRes: DrawableResource?, onAddClick: (() -> Unit)? = null) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(64.dp)) {
        Box(
            modifier = Modifier
                .size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            if (onAddClick != null) {
                // Add Story Button
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.DarkGray.copy(alpha = 0.5f))
                        .border(1.dp, Color.Gray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Story", tint = Color.White)
                }
            } else {
                // Story with Gradient Border
                Image(
                    painter = painterResource(imageRes!!),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            2.dp,
                            Brush.verticalGradient(listOf(Color(0xFFADFF2F), Color(0xFF32CD32))),
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(name, color = Color.White, fontSize = 12.sp, maxLines = 1)
    }
}

@Composable
fun FeedPostItem(post: Post) {
    // Frosted Glass Post Card
    Box(
        modifier = Modifier
            .height(400.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.BottomCenter

    ) {
        Box(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ){
            SideBar(post = post, modifier = Modifier)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(16.dp))
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
                .fillMaxSize()
                //.fillMaxWidth()
                //.height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop

        )

        Column(
            modifier = Modifier
                .zIndex(2f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Post Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(post.authorImage),
                    contentDescription = post.author,
                    modifier = Modifier
                        .size(40.dp)
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
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Posted ${post.time}",
                        color = Color.White,
                        fontSize = 12.sp
                    )
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
        }
    }
}

data class Story(val name: String, val imageRes: DrawableResource)
data class Post(val author: String, val authorImage: DrawableResource, val time: String, val content: String, val postImage: DrawableResource, val likes: String, val comments: String, val shares: String)

fun getStories() = listOf(
    Story("Julien", Res.drawable.story_1),
    Story("Samera", Res.drawable.story_2),
    Story("Mariane", Res.drawable.story_3),
    Story("Elmania", Res.drawable.story_4),
    Story("Jaki S", Res.drawable.story_5)
)

fun getFeedPosts() = listOf(
    Post("Gabriella Collins", Res.drawable.story_3, "1h ago", "Life reflects what we show, a smile brings the brightest results", Res.drawable.image_two, "1,253k", "1,253k", "100"),
    Post("Maya Quinn", Res.drawable.story_1, "3h ago", "", Res.drawable.story_2, "1,392k", "842", "241")
)


@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}
