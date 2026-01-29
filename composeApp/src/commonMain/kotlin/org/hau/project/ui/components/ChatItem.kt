package org.hau.project.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_3
import org.hau.project.models.Chat
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A highly polished list item component used to display a summary of a chat conversation.
 *
 * This component is designed for use within a vertical list (e.g., [LazyColumn]) on the main chat screen.
 * It presents key information at a glance, including:
 * - **Identity**: User's profile picture and name.
 * - **Availability**: A real-time online status indicator overlaid on the avatar.
 * - **Continuity**: The snippet of the last message and its relative timestamp.
 * - **Urgency**: A themed badge indicating the number of unread messages.
 *
 * The component utilizes a [Card] with a background color that respects the current [MaterialTheme],
 * ensuring it blends perfectly into both light and dark mode environments.
 *
 * @param chat The [Chat] data model instance containing the state to be rendered.
 */
@Composable
fun ChatItem(chat: Chat) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background // Respects theme-specific background
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- AVATAR & STATUS SECTION ---
            Box(
                modifier = Modifier.size(56.dp)
            ) {
                Image(
                    painter = painterResource(chat.profileRes),
                    contentDescription = "Profile image of ${chat.userName}",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                // Online Status Indicator
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .background(
                            if (chat.isOnline) Color(0xFF4CAF50) else MaterialTheme.colorScheme.surfaceVariant,
                            CircleShape
                        )
                        .border(BorderStroke(2.dp, MaterialTheme.colorScheme.surface), CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }

            // --- MESSAGE CONTENT SECTION ---
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = chat.userName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = chat.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.lastMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    // Unread Message Badge
                    if (chat.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chat.unreadCount.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- PREVIEWS ---

/**
 * Preview of [ChatItem] in Light Mode using the Sky theme.
 */
@Preview
@Composable
fun ChatItemLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            ChatItem(
                chat = Chat(
                    id = "1",
                    userName = "Mugumya Ali",
                    profileRes = Res.drawable.story_3,
                    lastMessage = "See you tomorrow at the office!",
                    timestamp = "11:30 AM",
                    unreadCount = 2,
                    isOnline = true,
                    isSent = false,
                    hasSeen = false
                )
            )
        }
    }
}

/**
 * Preview of [ChatItem] in Dark Mode using the Verdant theme.
 */
@Preview
@Composable
fun ChatItemDarkPreview() {
    AppTheme(theme = SocialTheme.Verdant, useDarkTheme = true) {
        Surface {
            ChatItem(
                chat = Chat(
                    id = "2",
                    userName = "Jane Doe",
                    profileRes = Res.drawable.story_3,
                    lastMessage = "Thanks for the feedback.",
                    timestamp = "Yesterday",
                    unreadCount = 0,
                    isOnline = false,
                    isSent = true,
                    hasSeen = true
                )
            )
        }
    }
}
