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
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_3
import org.hau.project.data.repositories.formatCount
import org.hau.project.models.User
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A profile header section displaying key user information.
 *
 * This component presents the user's primary identity details including:
 * - Display Name
 * - Verification status (via a badge)
 * - Formatted follower count
 *
 * The identity row (Name + Badge) is interactive and triggers the [onShowVerified] 
 * callback, allowing users to discover more about the verification status.
 *
 * @param user The [User] model containing profile data to display.
 * @param onShowVerified Callback triggered when the user clicks the verification area.
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
                indication = null, // Maintaining a clean, non-distracting header aesthetic
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
                    contentDescription = "Verified Account",
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

/**
 * Preview for [UserInfoSection] in Light Mode using the Sky theme.
 */
@Preview(name = "User Info (Sky Light)")
@Composable
fun UserInfoSectionPreviewLight() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            UserInfoSection(
                user = User(
                    id = "1",
                    name = "Jane Doe",
                    handle = "@janedoe",
                    avatarRes = Res.drawable.story_3,
                    followerCount = 12500,
                    isVerified = true,
                    bio = "Living life one day at a time."
                ),
                onShowVerified = {}
            )
        }
    }
}

/**
 * Preview for [UserInfoSection] in Dark Mode using the Sky theme.
 */
@Preview(name = "User Info (Sky Dark)")
@Composable
fun UserInfoSectionPreviewDark() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        Surface {
            UserInfoSection(
                user = User(
                    id = "1",
                    name = "Jane Doe",
                    handle = "@janedoe",
                    avatarRes = Res.drawable.story_3,
                    followerCount = 12500,
                    isVerified = true,
                    bio = "Living life one day at a time."
                ),
                onShowVerified = {}
            )
        }
    }
}
