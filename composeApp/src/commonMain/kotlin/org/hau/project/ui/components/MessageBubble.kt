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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hau.project.models.Message
import org.hau.project.models.MessageSender
import org.hau.project.models.MessageStatus
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A polished message bubble component.
 *
 * This component features:
 * 1.  Entrance Animation: Each bubble slides in from the side with a staggered spring animation
 *     based on its [index] in the list.
 * 2.  Adaptive Shape: Asymmetrical rounded corners that clearly distinguish between
 *     outgoing (mine) and incoming (theirs) messages.
 * 3.  Metadata Integration: Neatly displays the message timestamp and delivery status
 *     (ticks) within the bubble.
 * 4.  Interactive Actions: Supports long-press/right-click via [combinedClickable] to
 *     trigger the parent screen's action menu.
 *
 * @param message The data model containing message text, sender info, time, and status.
 * @param index The zero-based position of the message in the current list view, used for staggered delays.
 * @param showMeta Whether to display the timestamp and status icons. Defaults to true.
 * @param onLongPress Callback triggered when the bubble is long-clicked (mobile) or right-clicked (desktop).
 */
@Composable
fun MessageBubble(
    message: Message,
    index: Int = 0,
    showMeta: Boolean = true,
    onLongPress: () -> Unit = {}
) {
    val isMine = message.sender == MessageSender.Me

    // --- ANIMATION CONFIGURATION ---
    // Start with a slight horizontal offset and slide into the final position.
    val startOffsetX = if (isMine) 150f else -150f
    val slideAnim = remember { Animatable(startOffsetX) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(message.id) {
        // Apply a staggered delay so messages appear one after another.
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

    // Color definitions based on the current theme and sender.
    val incomingColor = MaterialTheme.colorScheme.surfaceVariant
    val outgoingColor = MaterialTheme.colorScheme.primaryContainer
    val readTickColor = Color(0xFF53BDEB)

    val bubbleColor = if (isMine) outgoingColor else incomingColor
    val textColor = if (isMine) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    // Distinctive asymmetrical shape for messaging continuity.
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
                        onClick = { /* Optional: handle single-tap selection */ },
                        onLongClick = onLongPress
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Column {
                    Text(
                        text = message.text ?: "",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        ),
                        color = textColor
                    )

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
                                    contentDescription = "Status",
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
