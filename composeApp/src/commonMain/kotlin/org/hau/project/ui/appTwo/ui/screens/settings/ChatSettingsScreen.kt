package org.hau.project.ui.appTwo.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hau.project.ui.appTwo.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatSettingsScreen(onBack: () -> Unit) {
    var enterIsSend by remember { mutableStateOf(false) }
    var mediaVisibility by remember { mutableStateOf(true) }
    var keepArchived by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { SettingsTopAppBar("Chats", onBack) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                SettingsHeader("Display")
                SettingsInfoItem("Theme", "Dark") { /* TODO: Show Theme Picker */ }
                SettingsInfoItem("Default chat theme", "") { /* TODO: Navigate to theme chooser */ }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Chat settings")
                SettingsSwitchItem("Enter is send", "Enter key will send your message", enterIsSend) { enterIsSend = it }
                SettingsSwitchItem("Media visibility", "Show newly downloaded media in your device's gallery", mediaVisibility) { mediaVisibility = it }
                SettingsInfoItem("Font size", "Medium") { /* TODO: Show Font Size Picker */ }
                SettingsInfoItem("Voice message transcripts", "Read new voice messages") {}
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Archived chats")
                SettingsSwitchItem("Keep chats archived", "Archived chats will remain archived when you receive a new message", keepArchived) { keepArchived = it }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsInfoItem("Chat backup", "") {}
                SettingsInfoItem("Chat history", "") {}
            }
        }
    }
}

@Preview(name = "Chat Settings (Dark)", showBackground = true)
@Composable
private fun ChatSettingsScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        ChatSettingsScreen {}
    }
}

@Preview(name = "Chat Settings (Light)", showBackground = true)
@Composable
private fun ChatSettingsScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        ChatSettingsScreen {}
    }
}
