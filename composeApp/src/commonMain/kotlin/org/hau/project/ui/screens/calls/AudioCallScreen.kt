package org.hau.project.ui.screens.calls

import androidx.compose.foundation.border
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.image_large
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A screen representing an active or ringing audio call.
 *
 * It features a blurred background of the caller's image for a premium look, 
 * a central large avatar, and prominently displayed call controls at the bottom.
 *
 * @param onBack Callback invoked when the user navigates back from the call screen.
 */
@Composable
fun AudioCallScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Blurred Background Image for aesthetic depth
        Image(
            painter = painterResource(Res.drawable.image_large),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 24.dp),
            contentScale = ContentScale.Crop
        )
        // Semi-transparent overlay to ensure text legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // 2. Main UI Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AudioTopBar(onBackClick = onBack)
            Spacer(modifier = Modifier.height(64.dp))

            // Focus Point: Caller's Avatar
            Image(
                painter = painterResource(Res.drawable.image_large),
                contentDescription = "Caller Avatar",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Caller Metadata
            Text(
                "Dr. Fresh Smile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Ringing...", 
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Interaction layer: Mute, End Call, Speaker
            AudioCallActionButtons()
        }
    }
}

/**
 * Top bar for the audio call screen containing navigation and "add person" actions.
 */
@Composable
private fun AudioTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.2f),
                contentColor = Color.White
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
        }

        IconButton(
            onClick = { /* TODO: Implement multi-party call logic */ },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.2f),
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Outlined.PersonAdd, contentDescription = "Add person")
        }
    }
}

/**
 * Bottom control panel for managing active audio call states.
 */
@Composable
private fun AudioCallActionButtons() {
    var isMuted by remember { mutableStateOf(false) }
    var isSpeakerOn by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AudioActionButton(
            icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
            text = "Mute",
            onClick = { isMuted = !isMuted }
        )

        // The "End Call" button is emphasized with a larger size and distinct color
        IconButton(
            onClick = { /* TODO: End call session */ },
            modifier = Modifier.size(72.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Default.CallEnd, contentDescription = "End Call", modifier = Modifier.size(36.dp))
        }

        AudioActionButton(
            icon = Icons.Default.VolumeUp,
            text = "Speaker",
            onClick = { isSpeakerOn = !isSpeakerOn },
            backgroundColor = if (isSpeakerOn) Color.White.copy(alpha = 0.3f) else Color.Transparent,
            borderColor = if (isSpeakerOn) Color.Transparent else Color.White.copy(alpha = 0.5f)
        )
    }
}

/**
 * A reusable action button used in the audio call control panel.
 */
@Composable
private fun AudioActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.White.copy(alpha = 0.2f),
    borderColor: Color = Color.Transparent
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(64.dp)
                .border(1.dp, borderColor, CircleShape),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = backgroundColor,
                contentColor = Color.White
            )
        ) {
            Icon(icon, contentDescription = text, modifier = Modifier.size(28.dp))
        }
        Text(text = text, color = Color.White.copy(alpha = 0.8f))
    }
}

@Preview
@Composable
private fun AudioCallScreenPreview() {
    MaterialTheme {
        AudioCallScreen(onBack = {})
    }
}
