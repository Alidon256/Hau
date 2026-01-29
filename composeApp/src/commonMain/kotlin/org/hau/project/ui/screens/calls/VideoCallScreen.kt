package org.hau.project.ui.screens.calls

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.image_large
import hau.composeapp.generated.resources.image_small
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A high-fidelity video call screen featuring immersive background video, 
 * a "glassmorphism" control panel, and a picture-in-picture style self-view.
 *
 * This screen demonstrates advanced Compose UI techniques including:
 * 1. **Immersive Media**: Full-screen background image representing the remote caller.
 * 2. **Glassmorphism**: Semi-transparent UI elements with vertical gradients to create 
 *    depth and maintain context.
 * 3. **Interactive Controls**: Real-time state management for mute, video, and speaker toggles.
 * 4. **Self-View**: A floating, rounded-corner window for the local user's camera feed.
 *
 * @param onBack Callback triggered when the user navigates back from the call.
 */
@Composable
fun VideoCallScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image: Represents the main incoming video stream.
        Image(
            painter = painterResource(Res.drawable.image_large),
            contentDescription = "Incoming Video Stream",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Top Navigation and Self-View
        TopBarContent(
            onBackClick = onBack,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )

        // 3. Control Panel Layer
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Glassmorphism Control Section
            GlassmorphismContent()
        }
    }
}

/**
 * Top bar layout containing the back navigation and the local user's video preview.
 */
@Composable
private fun TopBarContent(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Circular Back Button with subtle border
        IconButton(
            onClick = onBackClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Black.copy(alpha = 0.3f),
                contentColor = Color.White
            ),
            modifier = Modifier.border(
                1.dp,
                color = Color.White.copy(alpha = 0.2f),
                CircleShape)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "End call and go back"
            )
        }

        // Picture-in-Picture: The local user's camera feed.
        Image(
            painter = painterResource(Res.drawable.image_small),
            contentDescription = "My camera preview",
            modifier = Modifier
                .size(120.dp)
                .border(
                    1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * A semi-transparent bottom container for call metadata and interaction buttons.
 */
@Composable
private fun GlassmorphismContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.White.copy(alpha = 0.1f)
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CallerInfoBar()
        Spacer(modifier = Modifier.height(24.dp))
        CallActionButtons()
    }
}

/**
 * Displays basic info about the remote participant and an active call timer.
 */
@Composable
private fun CallerInfoBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.image_large),
            contentDescription = "Caller Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Dr. Fresh Smile",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
            )
            Text(
                text = "Pediatric",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
        // Active Status Indicator and Timer
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.Red, CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "15:20",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * A row of controls for managing the active video call session.
 */
@Composable
private fun CallActionButtons() {
    var isMuted by remember { mutableStateOf(false) }
    var isVideoOff by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
            contentDescription = if (isMuted) "Turn on microphone" else "Mute microphone",
            onClick = { isMuted = !isMuted }
        )

        ActionButton(
            icon = if (isVideoOff) Icons.Default.VideocamOff else Icons.Default.Videocam,
            contentDescription = if (isVideoOff) "Turn on camera" else "Turn off camera",
            onClick = { isVideoOff = !isVideoOff }
        )

        // Prominent End Call Action
        IconButton(
            onClick = { /* TODO: Terminate call session */ },
            modifier = Modifier.size(64.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Default.CallEnd, contentDescription = "End Call", modifier = Modifier.size(32.dp))
        }

        ActionButton(
            icon = Icons.Outlined.VolumeUp,
            contentDescription = "Toggle speakerphone",
            onClick = { /* TODO: Toggle audio route */ }
        )

        ActionButton(
            icon = Icons.Default.FlipCameraAndroid,
            contentDescription = "Switch camera",
            onClick = { /* TODO: Flip camera sensor */ }
        )
    }
}

/**
 * A reusable, stylized action button for the call control panel.
 */
@Composable
private fun ActionButton(
    icon: Any, 
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.White.copy(alpha = 0.2f),
            contentColor = Color.White
        )
    ) {
        when (icon) {
            is ImageVector -> Icon(icon, contentDescription)
            is Painter-> Icon(icon, contentDescription)
        }
    }
}

@Preview
@Composable
private fun VideoCallScreenPreview() {
    AppTheme {
        Surface {
            VideoCallScreen(onBack = {})
        }
    }
}
