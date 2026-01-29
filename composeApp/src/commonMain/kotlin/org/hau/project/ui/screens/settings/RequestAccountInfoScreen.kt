package org.hau.project.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
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
 * A screen that allows users to request a formal report of their account information
 * and channel activity.
 *
 * It provides options for manual requests and automated monthly report generation,
 * emphasizing that personal messages are not included in these exports.
 *
 * @param onBack Callback invoked when the user navigates back.
 */
@Composable
fun RequestAccountInfoScreen(onBack: () -> Unit) {
    var autoAccountReport by remember { mutableStateOf(false) }
    var autoChannelsReport by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
           SettingsTopAppBar(
                "Request account info",
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
                SettingsHeader("Account information")
                SettingsInfoItem(
                    title = "Request account report",
                    subtitle = "Create a report of your Verdant account information and settings, which you can access or port to another app. This report does not include your messages."
                ) {}
               SettingsSwitchItem(
                    title = "Create reports automatically",
                    subtitle = "A new report will be created every month.",
                    checked = autoAccountReport,
                    onCheckedChange = { autoAccountReport = it }
                )
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Channels activity")
                SettingsInfoItem(
                    title = "Request Channels report",
                    subtitle = "Create a report of your Channel updates and information, which you can access or port to another app."
                ) {}
                SettingsSwitchItem(
                    title = "Create reports automatically",
                    subtitle = "A new report will be created every month.",
                    checked = autoChannelsReport,
                    onCheckedChange = { autoChannelsReport = it }
                )
            }
        }
    }
}

@Preview(name = "Request Account Info (Sky Dark)")
@Composable
private fun RequestAccountInfoScreenDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        Surface {
            RequestAccountInfoScreen {}
        }
    }
}

@Preview(name = "Request Account Info (Sky Light)")
@Composable
private fun RequestAccountInfoScreenLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            RequestAccountInfoScreen {}
        }
    }
}
