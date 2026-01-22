package org.hau.project.ui.appTwo.ui.screens.calls

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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun VideoCallScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image (The Doctor)
        Image(
            painter = painterResource(Res.drawable.image_large),
            contentDescription = "Doctor on call",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Top Bar Items
        TopBarContent(
            onBackClick = onBack,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )

        // 3. Main Content Overlay (Bottom Sheet)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            // This spacer pushes the content up from the bottom
            Spacer(modifier = Modifier.weight(1f))

            // Glassmorphism Bottom UI
            GlassmorphismContent()
        }
    }
}

@Composable
private fun TopBarContent(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Back Button
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
                contentDescription = "Go back"
            )
        }

        // Patient's Self-View
        Image(
            painter = painterResource(Res.drawable.image_small),
            contentDescription = "My video feed",
            modifier = Modifier
                .size( 120.dp)
                .border(
                    1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun GlassmorphismContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(
                // This brush creates the semi-transparent "glass" effect
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
        // Caller Info Bar
        CallerInfoBar()
        Spacer(modifier = Modifier.height(24.dp))
        // Action Buttons
        CallActionButtons()
    }
}

@Composable
private fun CallerInfoBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.image_large),
            contentDescription = "Doctor Avatar",
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
        // Call Timer
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

@Composable
private fun CallActionButtons() {
    var isMuted by remember { mutableStateOf(false) }
    var isVideoOff by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mute Button
        ActionButton(
            icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
            contentDescription = if (isMuted) "Unmute" else "Mute",
            onClick = { isMuted = !isMuted }
        )

        // Video Off Button
        ActionButton(
            icon = if (isVideoOff) Icons.Default.VideocamOff else Icons.Default.Videocam,
            contentDescription = if (isVideoOff) "Turn Video On" else "Turn Video Off",
            onClick = { isVideoOff = !isVideoOff }
        )

        // End Call Button (Featured)
        IconButton(
            onClick = { /* End Call */ },
            modifier = Modifier.size(64.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Default.CallEnd, contentDescription = "End Call", modifier = Modifier.size(32.dp))
        }

        // Speaker Button
        ActionButton(
            icon = Icons.Outlined.VolumeUp,
            contentDescription = "Speaker",
            onClick = { /* Toggle Speaker */ }
        )

        // Flip Camera Button
        ActionButton(
            icon = Icons.Default.FlipCameraAndroid, // You need to add this vector asset
            contentDescription = "Flip Camera",
            onClick = { /* Flip Camera */ }
        )
    }
}

@Composable
private fun ActionButton(
    icon: Any, // Can be ImageVector or Painter
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
    MaterialTheme {
        VideoCallScreen(onBack = {})
    }
}
