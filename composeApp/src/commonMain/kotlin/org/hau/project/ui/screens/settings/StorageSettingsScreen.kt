package org.hau.project.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsHeader
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsInfoItem
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsSwitchItem
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StorageSettingsScreen(onBack: () -> Unit) {
    var useLessData by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SettingsTopAppBar(
                "Storage and data", onBack
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
                    "Manage storage",
                    "1.7 GB"
                ) {}
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsInfoItem(
                    "Network usage",
                    "4.0 GB sent • 7.1 GB received"
                ) {}
                SettingsSwitchItem(
                    "Use less data for calls",
                    "",
                    useLessData
                ) { useLessData = it }
                SettingsInfoItem(
                    "Proxy",
                    "Off"
                ) {}
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Media auto-download")
                SettingsInfoItem(
                    "Media upload quality",
                    "Standard quality"
                ) { /* TODO: Show Picker */ }
                SettingsInfoItem(
                    "Auto-download quality",
                    "Choose..."
                ) { /* TODO: Show Picker */ }
                SettingsInfoItem(
                    "Media auto-download",
                    "Voice messages are always automatically downloaded"
                ) {}
                SettingsInfoItem(
                    "When using mobile data",
                    "Photos"
                ) {}
                SettingsInfoItem(
                    "When connected on Wi-Fi",
                    "All media"
                ) {}
                SettingsInfoItem(
                    "When roaming",
                    "No media"
                ) {}
            }
        }
    }
}

@Preview(name = "Storage Settings (Dark)", showBackground = true)
@Composable
private fun StorageSettingsScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        StorageSettingsScreen {}
    }
}

@Preview(name = "Storage Settings (Light)", showBackground = true)
@Composable
private fun StorageSettingsScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        StorageSettingsScreen {}
    }
}
