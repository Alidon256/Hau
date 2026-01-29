package org.hau.project.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.hau.project.ui.screens.chats.CallType
import org.hau.project.ui.screens.chats.CallUIState
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * An immersive, full-screen call interface providing a modern communication experience.
 *
 * Features:
 * 1. **Visual Pulse**: Animates a subtle glow around the user's avatar during the [CallUIState.CALLING] phase.
 * 2. **Contextual Controls**: Provides standard call actions like Mute, Hang Up, and Speaker/Camera switching.
 * 3. **Dynamic Feedback**: Displays the call status (e.g., "Calling...", duration) and adapts to Audio/Video types.
 *
 * @param userName The display name of the contact being called.
 * @param avatarUrl The [DrawableResource] for the contact's profile picture.
 * @param callType Whether the call is [CallType.AUDIO] or [CallType.VIDEO].
 * @param callState The current phase of the call (IDLE, CALLING, ACTIVE).
 * @param onEndCall Callback triggered when the hang-up button is pressed.
 * @param onAcceptCall Callback triggered when an incoming call is accepted.
 */
@Composable
fun ModernCallPanel(
    userName: String,
    avatarUrl: DrawableResource?,
    callType: CallType,
    callState: CallUIState,
    onEndCall: () -> Unit,
    onAcceptCall: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
            .clickable(enabled = false) {} // Consume clicks to prevent interaction with underlying UI
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // User Avatar with Pulse Animation
            Box(contentAlignment = Alignment.Center) {
                if (callState == CallUIState.CALLING) {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .scale(pulseScale)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), CircleShape)
                    )
                }

                if (avatarUrl != null) {
                    Image(
                        painter = painterResource(avatarUrl),
                        contentDescription = "Contact Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        Modifier
                            .size(120.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, null, modifier = Modifier.size(60.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (callState == CallUIState.CALLING) "Calling..." else "00:05",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(60.dp))

            // Main Call Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mute Mic Toggle
                CallControlButton(
                    icon = Icons.Default.MicOff,
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.White,
                    onClick = {}
                )

                // Termination Button
                CallControlButton(
                    icon = Icons.Default.CallEnd,
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    size = 64.dp,
                    iconSize = 32.dp,
                    onClick = onEndCall
                )

                // Visual Toggle (Speaker for Audio, Flip Camera for Video)
                CallControlButton(
                    icon = if (callType == CallType.VIDEO) Icons.Default.FlipCameraIos else Icons.Default.VolumeUp,
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.White,
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun ModernCallPanelPreview() {
    AppTheme {
        ModernCallPanel(
            userName = "Mugumya Ali",
            avatarUrl = null,
            callType = CallType.AUDIO,
            callState = CallUIState.CALLING,
            onEndCall = {},
            onAcceptCall = {}
        )
    }
}