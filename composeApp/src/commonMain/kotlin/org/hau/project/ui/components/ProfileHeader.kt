package org.hau.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

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
            Image(
                painter = painterResource(avatarUrl?: Res.drawable.grattitude),
                contentDescription = "Profile Picture",
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