package org.hau.project.ui.appTwo.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.hau.project.ui.appTwo.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PrivacySettingsScreen(onBack: () -> Unit) {
    var readReceiptsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { SettingsTopAppBar("Privacy", onBack) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                PrivacyCheckupBanner()
                Spacer(modifier = Modifier.height(16.dp))
                SettingsHeader("Who can see my personal info")
            }

            item {
                SettingsInfoItem("Last seen and online", "My contacts, Everyone") {}
                SettingsInfoItem("Profile picture", "Everyone") {}
                SettingsInfoItem("About", "Everyone") {}
                SettingsInfoItem("Links", "Everyone") {}
                SettingsInfoItem("Status", "2 contacts excluded") {}
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                SettingsSwitchItem(
                    title = "Read receipts",
                    subtitle = "If turned off, you won't send or receive Read receipts. Read receipts are always sent for group chats.",
                    checked = readReceiptsEnabled,
                    onCheckedChange = { readReceiptsEnabled = it }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsInfoItem("Disappearing messages", "Off") {}
                SettingsInfoItem("Default message timer", "Start new chats with disappearing messages set to your timer") {}
            }
        }
    }
}

@Composable
private fun PrivacyCheckupBanner() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            Icons.Default.Lock,
            contentDescription = "Privacy Checkup",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Privacy checkup",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            val annotatedString = buildAnnotatedString {
                append("Control your privacy and choose the right settings for you. ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Start checkup")
                }
            }
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Icon(
            Icons.Default.Close,
            contentDescription = "Dismiss",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(20.dp).clickable {}
        )
    }
}

@Preview(name = "Privacy Settings (Dark)", showBackground = true)
@Composable
private fun PrivacySettingsScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        PrivacySettingsScreen {}
    }
}

@Preview(name = "Privacy Settings (Light)", showBackground = true)
@Composable
private fun PrivacySettingsScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        PrivacySettingsScreen {}
    }
}
