package org.hau.project.ui.screens.settings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.Brightness7
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsHeader
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsInfoItem
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsSwitchItem
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.hau.project.viewModels.LocalThemeViewModel
import org.hau.project.viewModels.ThemeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatSettingsScreen(
    onBack: () -> Unit
) {
    val themeViewModel = LocalThemeViewModel.current
    val themeUiState by themeViewModel.uiState.collectAsState()
    
    var enterIsSend by remember { mutableStateOf(false) }
    var mediaVisibility by remember { mutableStateOf(true) }
    var keepArchived by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            SettingsTopAppBar("Chats", onBack)
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
                SettingsHeader("Display & Theme")

                // --- MODERN THEME SELECTOR ---
                ThemeSelectorCard(
                    currentTheme = themeUiState.theme,
                    onThemeSelected = { themeViewModel.updateTheme(it) },
                    isDarkMode = themeUiState.isDarkMode,
                    onToggleDarkMode = { themeViewModel.toggleDarkMode(it) }
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Chat settings")
                SettingsSwitchItem(
                    "Enter is send",
                    "Enter key will send your message",
                    enterIsSend
                ) { enterIsSend = it }
                SettingsSwitchItem(
                    "Media visibility",
                    "Show newly downloaded media in your device's gallery",
                    mediaVisibility
                ) { mediaVisibility = it }
                SettingsInfoItem("Font size", "Medium") { }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }

            item {
                SettingsHeader("Archived chats")
                SettingsSwitchItem(
                    "Keep chats archived",
                    "Archived chats will remain archived when you receive a new message",
                    keepArchived
                ) { keepArchived = it }
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

@Composable
fun ThemeSelectorCard(
    currentTheme: SocialTheme,
    onThemeSelected: (SocialTheme) -> Unit,
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.ColorLens,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "App Appearance",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Dark Mode Switch with Custom Visuals
                IconButton(
                    onClick = { onToggleDarkMode(!isDarkMode) },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Outlined.Brightness4 else Icons.Outlined.Brightness7,
                        contentDescription = "Toggle Dark Mode",
                        tint = if (isDarkMode) Color(0xFFFFD600) else Color(0xFFFF9800)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Accent Color",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(SocialTheme.entries) { theme ->
                    ThemeSwatch(
                        theme = theme,
                        isSelected = currentTheme == theme,
                        onClick = { onThemeSelected(theme) }
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeSwatch(
    theme: SocialTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(if (isSelected) 1.2f else 1f, tween(300))
    val borderColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        tween(300)
    )

    // Define simple gradient for each theme
    val gradient = when (theme) {
        SocialTheme.Verdant -> Brush.linearGradient(listOf(Color(0xFF075E54), Color(0xFF25D366)))
        SocialTheme.Sky -> Brush.linearGradient(listOf(Color(0xFF1DA1F2), Color(0xFF657786)))
        SocialTheme.Twilight -> Brush.linearGradient(listOf(Color(0xFF833AB4), Color(0xFFE1306C)))
        SocialTheme.Electric -> Brush.linearGradient(listOf(Color(0xFFFFFC00), Color(0xFF3CB2E2)))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(gradient)
                .border(3.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = theme.name,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(name = "Chat Settings Modern")
@Composable
private fun ChatSettingsScreenPreview() {
    AppTheme {
        ChatSettingsScreen({})
    }
}
