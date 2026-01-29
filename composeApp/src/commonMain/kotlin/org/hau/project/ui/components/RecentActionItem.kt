package org.hau.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import org.hau.project.models.CallType
import org.hau.project.models.RecentCalls
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A component representing a single entry in the recent calls list.
 *
 * It displays the caller's avatar, name (with optional call count), 
 * call direction (incoming/outgoing/missed), timestamp, and a call action button.
 *
 * @param recentCall The [RecentCalls] data object containing call details.
 */
@Composable
fun RecentCallsItem(recentCall: RecentCalls) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {} // Make item clickable
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(recentCall.callerImageRes),
            contentDescription = recentCall.callerName,
            modifier = Modifier.clip(CircleShape).size(56.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Determine text color based on if the call was missed (callTimes == 0 indicates missed in this context)
                val nameColor = if (recentCall.callTimes == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                Text(
                    text = recentCall.callerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = nameColor
                )
                if (recentCall.callTimes > 1) {
                    Text(
                        text = "(${recentCall.callTimes})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nameColor
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = "Call direction",
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(if (recentCall.isSender) -45f else 135f),
                    tint = if (recentCall.isSender) Color(0xFF00C853) else MaterialTheme.colorScheme.error
                )
                Text(
                    text = recentCall.timestamp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        IconButton(onClick = { /* TODO: Initiate call */ }) {
            val callIcon = when (recentCall.callType) {
                CallType.AUDIO -> Icons.Outlined.Call
                CallType.VIDEO -> Icons.Outlined.Videocam
            }
            Icon(
                imageVector = callIcon,
                contentDescription = "Call ${recentCall.callerName}",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(name = "Recent Call - Outgoing")
@Composable
fun RecentCallsItemOutgoingPreview() {
    AppTheme {
        Surface {
            RecentCallsItem(
                RecentCalls(
                    callerName = "Mugumya Ali",
                    callerImageRes = Res.drawable.story_3,
                    callTimes = 2,
                    timestamp = "Just now",
                    isSender = true,
                    callType = CallType.VIDEO
                ),
            )
        }
    }
}

@Preview(name = "Recent Call - Missed")
@Composable
fun RecentCallsItemMissedPreview() {
    AppTheme {
        Surface {
            RecentCallsItem(
                RecentCalls(
                    callerName = "Jane Doe",
                    callerImageRes = Res.drawable.story_2,
                    callTimes = 0,
                    timestamp = "15 minutes ago",
                    isSender = false,
                    callType = CallType.AUDIO
                ),
            )
        }
    }
}
