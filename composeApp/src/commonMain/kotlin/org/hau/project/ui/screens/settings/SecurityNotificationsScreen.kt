package org.hau.project.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.hau.project.ui.components.SettingsSwitchItem
import org.hau.project.ui.components.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A screen explaining the application's security and encryption measures.
 *
 * It provides details on end-to-end encryption for various media types and allows
 * users to toggle security-related notifications.
 *
 * @param onBack Callback invoked when the user navigates back.
 */
@Composable
fun SecurityNotificationsScreen(onBack: () -> Unit) {
    var showNotifications by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            SettingsTopAppBar(
                "Security notifications",
                onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Security",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Your chats and calls are private",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "End-to-end encryption keeps your personal messages and calls between you and the people you choose. No one outside of the chat, not even Hau, can read, listen to, or share them. This includes your:",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                SecurityDetailItem(
                    Icons.Default.Forum,
                    "Text and voice messages"
                )
                SecurityDetailItem(
                    Icons.Default.Call,
                    "Audio and video calls"
                )
                SecurityDetailItem(
                    Icons.Default.Photo,
                    "Photos, videos and documents"
                )
                SecurityDetailItem(
                    Icons.Default.LocationOn,
                    "Location sharing"
                )
                SecurityDetailItem(
                    Icons.Default.Stream,
                    "Status updates"
                )
            }

            Text(
                "Learn more",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .clickable {}
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(Modifier.height(16.dp))

            SettingsSwitchItem(
                title = "Show security notifications on this device",
                subtitle = "Get notified when your security code changes for a contact's phone in an end-to-end encrypted chat.",
                checked = showNotifications,
                onCheckedChange = { showNotifications = it }
            )
        }
    }
}

/**
 * A simple row item used to list specific encrypted data types.
 */
@Composable
private fun SecurityDetailItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(name = "Security Notifications (Sky Dark)", showBackground = true)
@Composable
private fun SecurityNotificationsScreenDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        SecurityNotificationsScreen {}
    }
}

@Preview(name = "Security Notifications (Sky Light)", showBackground = true)
@Composable
private fun SecurityNotificationsScreenLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        SecurityNotificationsScreen {}
    }
}
