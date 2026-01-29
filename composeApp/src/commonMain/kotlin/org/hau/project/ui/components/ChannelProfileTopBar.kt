package org.hau.project.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import org.hau.project.utils.WindowSize
import org.hau.project.utils.rememberWindowSize
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * A specialized [TopAppBar] for the Channel Profile screen that features a collapsible header effect.
 *
 * This component intelligently manages its visibility based on the scroll state of the screen:
 * 1.  **Expanded State**: Background is transparent, allowing the profile banner to show through.
 * 2.  **Collapsed State**: Fades in a solid background and reveals the channel's avatar and name
 *     next to the back button for persistent context during browsing.
 *
 * @param channelName The name of the channel to display when the bar is collapsed.
 * @param avatarUrl The [DrawableResource] for the channel avatar.
 * @param isCollapsed Whether the scroll position has passed the threshold to collapse the bar.
 * @param onNavigateBack Callback for the back navigation action.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelProfileTopBar(
    channelName: String,
    avatarUrl: DrawableResource?,
    isCollapsed: Boolean,
    onNavigateBack: () -> Unit
) {
    val windowSize = rememberWindowSize()
    val isLargeScreen = windowSize >= WindowSize.Expanded

    // A separate surface handles the background color transition for a cleaner visual effect.
    Surface(
        color = if (isCollapsed) MaterialTheme.colorScheme.background else Color.Transparent,
        shadowElevation = if (isCollapsed) 2.dp else 0.dp
    ) {
        TopAppBar(
            title = {
                // Reveal the channel identity only when the large header avatar has scrolled away.
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
                // The back button is suppressed on large screens where rail-based navigation is used.
                if(!isLargeScreen) {
                    IconButton(onClick = onNavigateBack) {
                        // White tint ensures visibility over dark or colorful banner images.
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                }
            },
            actions = {
                if(!isLargeScreen) {
                    IconButton(onClick = { /* Handle overflow actions */ }) {
                        Icon(Icons.Default.MoreVert, "More Options", tint = Color.White)
                    }
                }
            },
            // The top bar itself remains transparent to allow the underlying Surface to control the tint.
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }
}
