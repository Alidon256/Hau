package org.hau.project.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.hau.project.ui.components.SettingsHeader
import org.hau.project.ui.components.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A screen that facilitates inviting friends to the platform.
 *
 * It provides a way to share a general invite link and lists local contacts
 * who are not yet on the app, allowing users to send individual invitations.
 *
 * @param onBack Callback invoked when the user navigates back.
 */
@Composable
fun InviteFriendScreen(onBack: () -> Unit) {
    val contactsToInvite = remember {
        listOf(
            "+256755246175" to "+256 755 246175",
            "~Ivan" to "+256 747 490279",
            "Abdumakik" to "+256 777 448448",
            "Achilla Abel" to "+256 762 505558",
            "Actous" to "+256 706 622493"
        )
    }

    Scaffold(
        topBar = {
            SettingsTopAppBar(
                "Invite a friend",
                onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                SettingsInfoItem(
                    icon = Icons.Default.Share,
                    title = "Share link",
                    onClick = {}
                )
                SettingsHeader("From contacts")
            }

            items(contactsToInvite) { (name, number) ->
                InviteContactItem(
                    name = name,
                    number = number
                )
            }
        }
    }
}

/**
 * An individual contact entry in the invite list.
 */
@Composable
private fun InviteContactItem(name: String, number: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                number,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        TextButton(onClick = { /* TODO: Invite */ }) {
            Text("Invite", color = MaterialTheme.colorScheme.primary)
        }
    }
}

/**
 * A reusable row for displaying an action with an icon.
 */
@Composable
private fun SettingsInfoItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Preview(name = "Invite Friend (Sky Dark)", showBackground = true)
@Composable
private fun InviteFriendScreenDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        InviteFriendScreen {}
    }
}

@Preview(name = "Invite Friend (Sky Light)", showBackground = true)
@Composable
private fun InviteFriendScreenLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        InviteFriendScreen {}
    }
}
