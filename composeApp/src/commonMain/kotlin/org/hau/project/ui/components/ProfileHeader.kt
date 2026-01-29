package org.hau.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A highly immersive header component for User and Channel profiles.
 *
 * It features a parallax banner effect and a reactive avatar that shrinks
 * and fades based on the screen's scroll position. This creates a premium,
 * modern app feel similar to Twitter or WhatsApp profiles.
 *
 * @param bannerUrl The remote URL for the wide background banner.
 * @param bannerHeight The initial vertical height of the banner area.
 * @param avatarInitialSize The diameter of the avatar when fully expanded.
 * @param scrollOffset The current pixel scroll position from the parent list.
 * @param avatarUrl The local [DrawableResource] for the user's profile picture.
 */
@Composable
fun ProfileHeader(
    bannerUrl: String,
    bannerHeight: Dp,
    avatarInitialSize: Dp,
    scrollOffset: Float,
    avatarUrl: DrawableResource?
) {
    val bannerHeightPx = with(LocalDensity.current) { bannerHeight.toPx() }
    val avatarYPosition = bannerHeight - (avatarInitialSize / 2)

    Box(
        modifier = Modifier.fillMaxWidth().height(bannerHeight + avatarInitialSize / 2),
        contentAlignment = Alignment.TopCenter
    ) {
        // --- PARALLAX BANNER ---
        AsyncImage(
            model = bannerUrl,
            contentDescription = "Profile banner",
            error = painterResource(Res.drawable.grattitude),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(bannerHeight).graphicsLayer {
                translationY = scrollOffset * 0.5f // Moves slower than scroll for parallax
                alpha = 1f - (scrollOffset / bannerHeightPx).coerceIn(0f, 1f) // Fades out as it hits top
            }
        )

        // Contrast Gradient for status bar icons.
        Box(
            modifier = Modifier.fillMaxWidth().height(80.dp).background(
                Brush.verticalGradient(colors = listOf(Color.Black.copy(0.5f), Color.Transparent))
            )
        )

        // --- ANIMATED AVATAR ---
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = avatarYPosition)
                .size(avatarInitialSize)
                .graphicsLayer {
                    // Calculate collapse based on scroll distance relative to banner height.
                    val collapsePercentage = (scrollOffset / (bannerHeightPx - avatarInitialSize.toPx() / 2)).coerceIn(0f, 1f)
                    val scale = lerp(1f.sp, 0f.sp, collapsePercentage).value
                    scaleX = scale
                    scaleY = scale
                    alpha = 1f - collapsePercentage
                }
        ) {
            Image(
                painter = painterResource(avatarUrl ?: Res.drawable.grattitude),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .border(4.dp, MaterialTheme.colorScheme.background, CircleShape)
            )
        }
    }
}

@Preview
@Composable
fun ProfileHeaderLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            ProfileHeader("", 200.dp, 100.dp, 0f, null)
        }
    }
}

@Preview
@Composable
fun ProfileHeaderDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            ProfileHeader("", 200.dp, 100.dp, 50f, null)
        }
    }
}