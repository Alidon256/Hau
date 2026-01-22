package org.hau.project.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HelpSettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            SettingsTopAppBar(
                "Help",
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
                SettingsInfoHelpItem(
                    icon = Icons.Outlined.HelpOutline,
                    title = "Help centre",
                    subtitle = "Get help, contact us"
                ) {}
            }
            item {
                SettingsInfoHelpItem(
                    icon = Icons.Outlined.Feedback,
                    title = "Send feedback",
                    subtitle = "Report technical issues"
                ) {}
            }
            item {
                SettingsInfoHelpItem(
                    icon = Icons.Outlined.Description,
                    title = "Terms and Privacy Policy",
                    subtitle = ""
                ) {}
            }
            item {
                SettingsInfoHelpItem(
                    icon = Icons.Outlined.Report,
                    title = "Channel reports",
                    subtitle = ""
                ) {}
            }
            item {
                SettingsInfoHelpItem(
                    icon = Icons.Outlined.Info,
                    title = "App info",
                    subtitle = ""
                ) {}
            }
        }
    }
}

@Composable
fun SettingsInfoHelpItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
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
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(name = "Help (Dark)", showBackground = true)
@Composable
private fun HelpSettingsScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        HelpSettingsScreen {}
    }
}

@Preview(name = "Help (Light)", showBackground = true)
@Composable
private fun HelpSettingsScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        HelpSettingsScreen {}
    }
}
