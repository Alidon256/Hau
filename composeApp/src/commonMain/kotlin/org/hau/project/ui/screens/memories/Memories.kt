package org.hau.project.ui.screens.memories

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import hau.composeapp.generated.resources.story_3
import org.hau.project.data.repositories.formatCount
import org.hau.project.models.Channels
import org.hau.project.models.RecommendedChannels
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.hau.project.viewModels.ChatDetailUiState
import org.hau.project.viewModels.ChatViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The primary entry point for the Memories feature.
 *
 * This screen facilitates navigation between followed "Channels" and a high-quality "Gallery"
 * of media. It manages the tab state and handles the visibility of the fullscreen
 * interactive image viewer.
 *
 * @param viewModel The [ChatViewModel] used to observe channel and recommendation states.
 * @param onChannelClick Callback invoked when a channel item is selected.
 * @param onAddMemoryClick Callback invoked when the user initiates adding a new memory.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoriesScreen(
    viewModel: ChatViewModel,
    onChannelClick: (channelId: String) -> Unit,
    onAddMemoryClick: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Channels", "Gallery")

    val uiChannelState by viewModel.channelState.collectAsState()
    val uiRecommendedChannels by viewModel.recommendedState.collectAsState()
    
    // Using high-quality Unsplash images for the "Top Notch" feel
    val galleryImages = remember {
        listOf(
            "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&q=80&w=1200",
            "https://images.unsplash.com/photo-1518005020251-6fb201b287dd?auto=format&fit=crop&q=80&w=1000",
            "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&q=80&w=1000",
            "https://images.unsplash.com/photo-1497215728101-856f4ea42174?auto=format&fit=crop&q=80&w=1000",
            "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&q=80&w=1000",
            "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&q=80&w=1000"
        )
    }

    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Column(Modifier.background(MaterialTheme.colorScheme.background)) {
                    TopAppBar(
                        title = { Text("Memories", fontWeight = FontWeight.Bold) },
                        actions = {
                            IconButton(onClick = {}) { Icon(Icons.Outlined.Search, null) }
                            IconButton(onClick = {}) { Icon(Icons.Outlined.MoreVert, null) }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                    )
                    
                    SecondaryTabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        divider = {},
                        indicator = {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(title, fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal) },
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                selectedContentColor = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                if (selectedTabIndex == 0) {
                    FloatingActionButton(
                        onClick = onAddMemoryClick,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Outlined.AddAPhoto, null)
                    }
                }
            }
        ) { paddingValues ->
            Box(Modifier.padding(paddingValues)) {
                if (selectedTabIndex == 0) {
                    ChannelsListView(
                        uiChannelState = uiChannelState,
                        uiRecommendedChannels = uiRecommendedChannels,
                        onChannelClick = onChannelClick,
                        onAddMemoryClick = onAddMemoryClick
                    )
                } else {
                    MemoriesGalleryView(
                        images = galleryImages,
                        onImageClick = { index -> selectedImageIndex = index }
                    )
                }
            }
        }

        // --- FULLSCREEN INTERACTIVE VIEWER ---
        AnimatedVisibility(
            visible = selectedImageIndex != null,
            enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
        ) {
            selectedImageIndex?.let { index ->
                FullscreenGalleryViewer(
                    images = galleryImages,
                    initialIndex = index,
                    onClose = { selectedImageIndex = null }
                )
            }
        }
    }
}

/**
 * A grid-based view that displays media in a staggered layout.
 *
 * @param images A list of image URLs to be displayed.
 * @param onImageClick Callback providing the index of the clicked image.
 */
@Composable
private fun MemoriesGalleryView(images: List<String>, onImageClick: (Int) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(160.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        itemsIndexed(images) { index, url ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onImageClick(index) }
                    .border(0.5.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    contentScale = ContentScale.FillWidth,
                    error = painterResource(Res.drawable.grattitude)
                )
            }
        }
    }
}

/**
 * An immersive, fullscreen image viewer designed for high-fidelity media inspection.
 *
 * Features:
 * - Smooth crossfade transitions between images.
 * - Interactive tap zones for left/right navigation.
 * - Top-bar controls for sharing, downloading, and closing.
 *
 * @param images The collection of image URLs to browse.
 * @param initialIndex The index of the image to display first.
 * @param onClose Callback to dismiss the viewer.
 */
@Composable
private fun FullscreenGalleryViewer(
    images: List<String>,
    initialIndex: Int,
    onClose: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Image with Crossfade for smooth navigation
        Crossfade(targetState = images[currentIndex], animationSpec = tween(400)) { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        // --- INTERACTIVE TAP NAVIGATION ZONES ---
        Row(Modifier.fillMaxSize()) {
            // Previous Zone (Left 40%)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (currentIndex > 0) currentIndex-- else onClose()
                    }
            )
            // Center Zone (Optional middle 20% for toggling UI)
            Box(modifier = Modifier.fillMaxHeight().weight(0.2f))
            
            // Next Zone (Right 40%)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (currentIndex < images.size - 1) currentIndex++
                    }
            )
        }

        // Top Controls Header
        Surface(
            color = Color.Black.copy(alpha = 0.4f),
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier.statusBarsPadding().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, "Close", tint = Color.White)
                    }
                    Spacer(Modifier.width(16.dp))
                    Text(
                        "${currentIndex + 1} of ${images.size}",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = {}) { Icon(Icons.Default.Share, null, tint = Color.White) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Download, null, tint = Color.White) }
                }
            }
        }
    }
}

/**
 * A scrollable list of followed and recommended spaces.
 *
 * @param uiChannelState State containing followed channels.
 * @param uiRecommendedChannels State containing suggested channels to follow.
 * @param onChannelClick Navigation callback for followed channels.
 * @param onAddMemoryClick Callback for the banner's add action.
 */
@Composable
private fun ChannelsListView(
    uiChannelState: ChatDetailUiState,
    uiRecommendedChannels: ChatDetailUiState,
    onChannelClick: (String) -> Unit,
    onAddMemoryClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(Modifier.height(8.dp))
            MemoriesBanner(onClick = onAddMemoryClick)
        }
        item {
            Text("Spaces", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
        }
        items(uiChannelState.channels) { channel ->
            ChannelItem(channel = channel, onClick = { onChannelClick(channel.id) })
        }
        item {
            Text("More Spaces to Follow", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 16.dp))
        }
        items(uiRecommendedChannels.recommendedChannels) { channel ->
            RecommendedChannelsItem(recommended = channel, onFollowClick = {})
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

/**
 * A prominent header component used to encourage users to share new memory updates.
 * Features a gradient background and a circular avatar with an 'add' badge.
 *
 * @param onClick Callback invoked when the banner is clicked.
 */
@Composable
fun MemoriesBanner(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), Color.Transparent)), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(Res.drawable.story_3),
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(CircleShape).border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop
            )
            Box(Modifier.size(20.dp).background(MaterialTheme.colorScheme.primary, CircleShape).border(2.dp, MaterialTheme.colorScheme.surface, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Add, null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(14.dp))
            }
        }
        Column {
            Text("My update", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Share a new memory update", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

/**
 * Represents an individual followed channel row with avatar, name, last message, and timestamp.
 *
 * @param channel The [Channels] model containing data for the row.
 * @param onClick Callback invoked when the item is clicked.
 */
@Composable
fun ChannelItem(channel: Channels, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(channel.channelRes),
            contentDescription = null,
            modifier = Modifier.size(56.dp).clip(CircleShape).border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(Modifier.weight(1f)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(channel.channelName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(channel.timestamp, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            Text(channel.message, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

/**
 * Represents a suggested channel item with follow action and verification badges.
 *
 * @param recommended The [RecommendedChannels] model containing suggestion data.
 * @param onFollowClick Callback invoked when the follow button is clicked.
 */
@Composable
fun RecommendedChannelsItem(recommended: RecommendedChannels, onFollowClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(recommended.channelRes),
            contentDescription = null,
            modifier = Modifier.size(60.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(Modifier.weight(1f).padding(start = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(recommended.channelName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                if (recommended.isVerified) {
                    Icon(Icons.Outlined.Verified, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 4.dp).size(16.dp))
                }
            }
            Text("${formatCount(recommended.followerCount)} followers", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Button(onClick = onFollowClick, shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)) {
            Text("Follow")
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Memories Banner (Sky Light)")
@Composable
private fun MemoriesBannerPreviewLight() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            MemoriesBanner(onClick = {})
        }
    }
}

@Preview(name = "Memories Banner (Sky Dark)")
@Composable
private fun MemoriesBannerPreviewDark() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        Surface {
            MemoriesBanner(onClick = {})
        }
    }
}

@Preview(name = "Channel Item (Sky Light)")
@Composable
private fun ChannelItemPreviewLight() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            ChannelItem(
                channel = Channels(
                    id = "1",
                    channelName = "Sky Watchers",
                    channelRes = Res.drawable.grattitude,
                    message = "Clear skies expected tonight!",
                    timestamp = "12:45 PM",
                    followerCount = 1200,
                    isVerified = true,
                    isRead = false,
                    unreadMessages = 0,
                    attachmentType = null
                ),
                onClick = {}
            )
        }
    }
}

@Preview(name = "Gallery View (Sky Light)")
@Composable
private fun GalleryPreviewLight() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            MemoriesGalleryView(
                images = listOf(
                    "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&q=80&w=1200",
                    "https://images.unsplash.com/photo-1518005020251-6fb201b287dd?auto=format&fit=crop&q=80&w=1000"
                ),
                onImageClick = {}
            )
        }
    }
}
