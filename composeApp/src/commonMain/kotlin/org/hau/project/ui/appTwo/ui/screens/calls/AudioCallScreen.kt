package org.hau.project.ui.appTwo.ui.screens.calls

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
import hau.composeapp.generated.resources.image_large // Using the same doctor image
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AudioCallScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Blurred Background Image
        Image(
            painter = painterResource(Res.drawable.image_large),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 24.dp), // Apply a strong blur effect
            contentScale = ContentScale.Crop
        )
        // Dark overlay for better text contrast
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // 2. Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top action bar
            AudioTopBar(onBackClick = onBack)
            Spacer(modifier = Modifier.height(64.dp))

            // Large Avatar
            Image(
                painter = painterResource(Res.drawable.image_large),
                contentDescription = "Dr. Fresh Smile",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Caller Name and Status
            Text(
                "Dr. Fresh Smile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Ringing...", // Or show the call timer "15:20"
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.8f)
            )

            // This spacer pushes the controls to the bottom
            Spacer(modifier = Modifier.weight(1f))

            // Action Buttons
            AudioCallActionButtons()
        }
    }
}

@Composable
private fun AudioTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        IconButton(
            onClick = onBackClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.2f),
                contentColor = Color.White
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
        }

        // Add Person Button
        IconButton(
            onClick = { /* Add person to call */ },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.2f),
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Outlined.PersonAdd, contentDescription = "Add person")
        }
    }
}

@Composable
private fun AudioCallActionButtons() {
    var isMuted by remember { mutableStateOf(false) }
    var isSpeakerOn by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp), // Add padding to lift it from the very bottom
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mute Button
        AudioActionButton(
            icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
            text = "Mute",
            onClick = { isMuted = !isMuted }
        )

        // End Call Button (Featured)
        IconButton(
            onClick = { /* End Call */ },
            modifier = Modifier.size(72.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Default.CallEnd, contentDescription = "End Call", modifier = Modifier.size(36.dp))
        }

        // Speaker Button
        AudioActionButton(
            icon = Icons.Default.VolumeUp,
            text = "Speaker",
            onClick = { isSpeakerOn = !isSpeakerOn },
            // Make it visually active or inactive
            backgroundColor = if (isSpeakerOn) Color.White.copy(alpha = 0.3f) else Color.Transparent,
            borderColor = if(isSpeakerOn) Color.Transparent else Color.White.copy(alpha = 0.5f)
        )
    }
}

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
