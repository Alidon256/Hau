package org.hau.project.ui.appTwo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hau.project.ui.appTwo.domain.models.Message
import org.hau.project.ui.appTwo.domain.models.MessageSender
import org.hau.project.ui.appTwo.domain.models.MessageStatus
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageBubble(message: Message, onLongPress: () -> Unit, showMeta: Boolean = true) {    val isMine = message.sender == MessageSender.Me

    val incomingColor = Color(0xFF202C33) // Dark Grey
    val readTickColor = Color(0xFF53BDEB) // Bright Blue

    val outgoingColor = MaterialTheme.colorScheme.primary

    val bubbleColor = if (isMine) outgoingColor else incomingColor
    val textColor = if (isMine) MaterialTheme.colorScheme.onPrimary else Color.White

    // Enhanced bubble shapes for better visual hierarchy
    val bubbleShape = if (isMine) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 0.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp)
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = 80.dp, max = 280.dp) // Constrain bubble width
                    .clip(bubbleShape)
                    .background(bubbleColor)
                    .combinedClickable(onClick = {}, onLongClick = onLongPress)
                    .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp)
            ) {
                // Use a Column to stack the main text and the metadata
                Column {
                    // This is a common trick for this layout:
                    // We create a Box that will hold the text and the metadata, allowing them to overlap.
                    Box(contentAlignment = Alignment.BottomEnd) {
                        // Spacer with invisible text to reserve space for the metadata,
                        // ensuring the main text wraps correctly above it.
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            // Add another small spacer for the status icon
                            if (isMine) {
                                Spacer(Modifier.width(20.dp))
                            }
                        }

                        // The actual message text
                        Text(
                            text = message.text ?: "",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                lineHeight = 22.sp // Slightly larger line height for readability
                            ),
                            color = textColor,
                            modifier = Modifier.padding(bottom = 4.dp) // Padding to avoid overlapping with metadata
                        )
                    }

                    // The actual metadata row, drawn at the bottom of the Column
                    Row(
                        modifier = Modifier
                            .offset(x = 4.dp)
                            .align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = message.time,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                        if (isMine) {
                            Spacer(Modifier.width(4.dp))
                            val statusIcon = when (message.status) {
                                MessageStatus.READ -> Icons.Filled.DoneAll to readTickColor
                                MessageStatus.DELIVERED -> Icons.Filled.DoneAll to MaterialTheme.colorScheme.onSurface
                                MessageStatus.SENT -> Icons.Filled.Done to MaterialTheme.colorScheme.primary
                                null -> null
                            }
                            statusIcon?.let { (icon, color) ->
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "Message Status",
                                    tint = color,
                                    modifier = Modifier.size(18.dp) // Slightly larger icon
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Their Message Bubble")
@Composable
private fun TheirMessageBubblePreview() {
    Box(Modifier.padding(16.dp)) {
        MessageBubble(
            message = Message("p2", MessageSender.Them, "I'm doing great,", "10:31 AM"),
            onLongPress = {},
            showMeta = true
        )
    }
}
@Preview(name = "My Message Bubble")
@Composable
private fun MyMessageBubblePreview() {
    Box(Modifier.padding(16.dp)) {
        MessageBubble(
            message = Message("p1", MessageSender.Me, "Hey, how's it going? This is a slightly longer message to test wrapping.", "10:30 AM", status = MessageStatus.READ),
            onLongPress = {},
            showMeta = true
        )
    }
}
