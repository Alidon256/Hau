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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.hau.project.data.repositories.formatCount
import org.hau.project.models.User
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A profile header section displaying key user information.
 *
 * It shows the user's display name, a verified badge if applicable, 
 * and their follower count. The name and badge area is clickable to show 
 * more verification info.
 *
 * @param user The [User] data model containing name, verification status, and follower count.
 * @param onShowVerified Callback invoked when the user clicks on the name/verification area.
 */
@Composable
fun UserInfoSection(
    user: User,
    onShowVerified: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.clickable(
                onClick = onShowVerified,
                indication = null, // No ripple effect for this click to maintain a clean header look
                interactionSource = remember { MutableInteractionSource() }
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (user.isVerified) {
                Spacer(Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "User ∙ ${formatCount(user.followerCount)} followers",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Preview
@Composable
fun UserInfoSectionPreview() {
    AppTheme {
        Surface {
            UserInfoSection(
                user = User(
                    id = "1",
                    name = "Jane Doe",
                    isVerified = true,
                    followerCount = 12500,
                    imageRes = null,
                    about = "Living life one day at a time.",
                    phoneNumber = "+123456789",
                    isMuted = false
                ),
                onShowVerified = {}
            )
        }
    }
}
