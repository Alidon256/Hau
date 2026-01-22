package org.hau.project

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.hau.project.ui.components.BottomNavigation
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme

@Composable
@Preview
fun App() {
    // State to hold the currently selected theme. Default to Verdant.
    // In a real app, you might save this to user preferences.
    var currentTheme by remember { mutableStateOf(SocialTheme.Sky) }

    // Use our custom AppTheme
    AppTheme(theme = currentTheme) {
        // You can pass a theme switcher down to your UI if needed
        // For example: BottomNavigation(onThemeChange = { newTheme -> currentTheme = newTheme })
        BottomNavigation()
    }
}