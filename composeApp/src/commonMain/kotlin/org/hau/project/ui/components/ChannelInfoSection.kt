package org.hau.project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.hau.project.data.repositories.formatCount
import org.hau.project.models.Channels

/**
 * A "Top Notch" UI component that displays the primary branding and metadata for a Channel.
 *
 * This section includes:
 * - The Channel's display name with a high-visibility bold weight.
 * - An optional Verified Badge with interactive feedback.
 * - Followers count formatted for professional readability.
 *
 * @param channel The [Channels] model containing the name, verification status, and follower count.
 * @param onShowVerified Callback triggered when the user taps the name/badge area,
 * typically used to show a "Verified Info" bottom sheet or dialog.
 */
@Composable
fun ChannelInfoSection(
    channel: Channels,
    onShowVerified: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp) // Optimized spacing for modern header layouts
    ) {
        // --- NAME & VERIFIED BADGE ROW ---
        Row(
            modifier = Modifier
                .clickable(
                    onClick = onShowVerified,
                    indication = null, // Clean interaction without distracting ripples
                    interactionSource = remember { MutableInteractionSource() }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = channel.channelName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (channel.isVerified) {
                Spacer(Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified Badge",
                    // Using a dedicated Teal/Green for verified checkmarks common in chat apps
                    tint = Color(0xFF00A884),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        // --- SUBTITLE: FOLLOWER COUNT ---
        Text(
            text = buildString {
                append("Channel ∙ ")
                append(formatCount(channel.followerCount))
                append(" followers")
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Standardized spacing before the action buttons row
        Spacer(Modifier.height(28.dp))
    }
}