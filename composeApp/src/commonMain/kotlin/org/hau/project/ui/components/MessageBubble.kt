package org.hau.project.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hau.project.models.Message
import org.hau.project.models.MessageSender
import org.hau.project.models.MessageStatus
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A highly polished message bubble component designed for chat interfaces.
 *
 * This component provides a fluid and modern messaging experience with the following features:
 * 1. **Visual Asymmetry**: Distinctive bubble shapes for incoming and outgoing messages, 
 *    helping users quickly identify the sender.
 * 2. **Entrance Animations**: Staggered slide-in and fade-in animations using [Animatable] 
 *    and [spring] physics to create a dynamic and premium feel as the chat list loads.
 * 3. **Themed Styling**: Automatic color adjustment based on the current [MaterialTheme] 
 *    color scheme and message ownership (Mine vs. Theirs).
 * 4. **Metadata Integration**: Neatly displays message timestamps and delivery/read status 
 *    indicators (ticks) within the bubble layout.
 * 5. **Rich Interactivity**: Utilizes [combinedClickable] to support long-press gestures, 
 *    enabling contextual action menus (Copy, Forward, etc.).
 *
 * @param message The [Message] data object containing the text content, sender type, timestamp, and delivery status.
 * @param index The zero-based position of the bubble in the message list, used to calculate staggered animation delays.
 * @param showMeta Whether to display the message metadata (timestamp and status ticks). Defaults to `true`.
 * @param onLongPress Callback triggered when the user performs a long-press or right-click action on the bubble.
 */
@Composable
fun MessageBubble(
    message: Message,
    index: Int = 0,
    showMeta: Boolean = true,
    onLongPress: () -> Unit = {}
) {
    val isMine = message.sender == MessageSender.Me
    val isInspectionMode = LocalInspectionMode.current

    // --- ANIMATION CONFIGURATION ---
    // Messages start with a horizontal offset and slide into their final position.
    val startOffsetX = if (isMine) 150f else -150f
    val slideAnim = remember { Animatable(if (isInspectionMode) 0f else startOffsetX) }
    val alphaAnim = remember { Animatable(if (isInspectionMode) 1f else 0f) }

    LaunchedEffect(message.id) {
        if (isInspectionMode) return@LaunchedEffect
        
        // Apply a staggered delay based on the message index to avoid all bubbles appearing simultaneously.
        delay(index * 50L)
        launch {
            slideAnim.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = 0.75f,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            alphaAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400)
            )
        }
    }

    // Palette resolution based on current theme and sender.
    val incomingColor = MaterialTheme.colorScheme.surfaceVariant
    val outgoingColor = MaterialTheme.colorScheme.primaryContainer
    val readTickColor = Color(0xFF53BDEB) // Standardized bright blue for read receipts.

    val bubbleColor = if (isMine) outgoingColor else incomingColor
    val textColor = if (isMine) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    // Distinctive asymmetrical corners to create a "tail" effect pointing to the sender's side.
    val bubbleShape = if (isMine) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 2.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 2.dp, bottomEnd = 16.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .graphicsLayer {
                translationX = slideAnim.value
                alpha = alphaAnim.value
            },
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier.widthIn(max = 340.dp),
            horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .shadow(0.5.dp, bubbleShape)
                    .clip(bubbleShape)
                    .background(bubbleColor)
                    .combinedClickable(
                        onClick = { /* Optional: handle single-tap selection logic */ },
                        onLongClick = onLongPress
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Column {
                    // Main Message Body
                    Text(
                        text = message.text ?: "",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        ),
                        color = textColor
                    )

                    // Metadata Row: Time and Delivery Status
                    if (showMeta) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = message.time,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = textColor.copy(alpha = 0.6f)
                            )

                            if (isMine) {
                                Spacer(Modifier.width(4.dp))
                                val (icon, color) = when (message.status) {
                                    MessageStatus.READ -> Icons.Default.DoneAll to readTickColor
                                    MessageStatus.DELIVERED -> Icons.Default.DoneAll to textColor.copy(alpha = 0.4f)
                                    MessageStatus.SENT -> Icons.Default.Done to textColor.copy(alpha = 0.4f)
                                    null -> Icons.Default.Done to textColor.copy(alpha = 0.4f)
                                }
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "Message Status",
                                    tint = color,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- PREVIEWS ---

/**
 * Visualizes an outgoing message (sent by the current user) in Light Mode with the Sky theme.
 */
@Preview
@Composable
private fun OutgoingMessageLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            Box(Modifier.padding(16.dp)) {
                MessageBubble(
                    message = Message(
                        id = "1",
                        sender = MessageSender.Me,
                        text = "Hey! This is a documented message bubble.",
                        time = "10:45 AM",
                        status = MessageStatus.READ
                    ),
                    index = 0
                )
            }
        }
    }
}

/**
 * Visualizes an incoming message (received from another user) in Dark Mode with the Sky theme.
 */
@Preview()
@Composable
private fun IncomingMessageDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        Surface {
            Box(Modifier.padding(16.dp)) {
                MessageBubble(
                    message = Message(
                        id = "2",
                        sender = MessageSender.Them,
                        text = "It looks great! The animations and asymmetrical corners are top-notch.",
                        time = "10:46 AM"
                    ),
                    index = 2
                )
            }
        }
    }
}
