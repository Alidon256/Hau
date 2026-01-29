package org.hau.project.ui.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hau.project.ui.components.SettingsHeader
import org.hau.project.ui.components.SettingsInfoItem
import org.hau.project.ui.components.SettingsSwitchItem
import org.hau.project.ui.components.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A comprehensive screen for configuring app-wide notification behaviors.
 *
 * Users can toggle conversation tones, reminders, high-priority notifications,
 * and reaction alerts. It also provides deep-dive settings for message, group,
 * call, and status notification specificities like tones and vibration patterns.
 *
 * @param onBack Callback invoked when the user navigates back.
 */
@Composable
fun NotificationsSettingsScreen(onBack: () -> Unit) {
    var conversationTones by remember { mutableStateOf(true) }
    var reminders by remember { mutableStateOf(true) }
    var highPriority by remember { mutableStateOf(true) }
    var reactionNotifications by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            SettingsTopAppBar(
                "Notifications",
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
               SettingsSwitchItem(
                    title = "Conversation tones",
                    subtitle = "Play sounds for incoming and outgoing messages.",
                    checked = conversationTones,
                    onCheckedChange = { conversationTones = it }
                )
                Spacer(Modifier.height(8.dp))
                SettingsSwitchItem(
                    title = "Reminders",
                    subtitle = "Get occasional reminders about messages, calls or status updates you haven't seen",
                    checked = reminders,
                    onCheckedChange = { reminders = it }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Messages")
                SettingsInfoItem(
                    "Notification tone",
                    "Default (Spaceline)"
                ) {}
                SettingsInfoItem(
                    "Vibrate",
                    "Default"
                ) {}
                SettingsInfoItem(
                    "Popup notification",
                    "Not available"
                ) {}
                SettingsInfoItem(
                    "Light",
                    "White"
                ) {}
                Spacer(Modifier.height(16.dp))
                SettingsSwitchItem(
                    "Use high priority notifications",
                    "Show previews of notifications at the top of the screen",
                    highPriority
                ) { highPriority = it }
                SettingsSwitchItem(
                    "Reaction notifications",
                    "Show notifications for reactions to messages you send",
                    reactionNotifications
                ) { reactionNotifications = it }
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Calls")
                SettingsInfoItem(
                    "Ringtone",
                    "Default (Galaxy Bells)"
                ) {}
                SettingsInfoItem(
                    "Vibrate",
                    "Default"
                ) {}
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Status")
                SettingsInfoItem(
                    "Notification tone",
                    "Default (Spaceline)"
                ) {}
                SettingsInfoItem(
                    "Vibrate",
                    "Default"
                ) {}
            }
        }
    }
}

@Preview(name = "Notifications (Sky Dark)", showBackground = true)
@Composable
private fun NotificationsSettingsScreenDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        Surface {
            NotificationsSettingsScreen {}
        }
    }
}

@Preview(name = "Notifications (Sky Light)", showBackground = true)
@Composable
private fun NotificationsSettingsScreenLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            NotificationsSettingsScreen {}
        }
    }
}
