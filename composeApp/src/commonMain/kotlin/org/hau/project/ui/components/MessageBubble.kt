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

@Composable
fun MessageBubble(
    message: Message,
    index: Int = 0,
    showMeta: Boolean = true,
    onLongPress: () -> Unit = {}
) {
    val isMine = message.sender == MessageSender.Me

    // --- ANIMATION LOGIC FROM GIST ---
    val startOffsetX = if (isMine) 150f else -150f
    val slideAnim = remember { Animatable(startOffsetX) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(message.id) {
        // Stagger the animation based on index
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

    val incomingColor = MaterialTheme.colorScheme.surfaceVariant
    val outgoingColor = MaterialTheme.colorScheme.primaryContainer
    val readTickColor = Color(0xFF53BDEB)

    val bubbleColor = if (isMine) outgoingColor else incomingColor
    val textColor = if (isMine) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

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
                        onClick = { },
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
                                    contentDescription = null,
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
