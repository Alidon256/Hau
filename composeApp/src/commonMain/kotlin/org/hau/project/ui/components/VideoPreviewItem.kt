package org.hau.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A lightweight video preview component designed for chat messages or media galleries.
 *
 * It displays a placeholder with a play/pause toggle. In a real implementation, 
 * this would likely integrate with a video player like ExoPlayer or a similar KMP-compatible 
 * media library.
 *
 * @param videoUrl The URL of the video to preview. Currently used as a placeholder.
 */
@Composable
fun VideoPreviewItem(videoUrl: String? = null) {
    var isPlaying by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .widthIn(max = 280.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
            .clickable { isPlaying = !isPlaying },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = "Play/Pause",
            tint = Color.White,
            modifier = Modifier
                .size(48.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun VideoPreviewItemPreview() {
    AppTheme {
        Surface {
            Box(Modifier.padding(16.dp)) {
                VideoPreviewItem()
            }
        }
    }
}
