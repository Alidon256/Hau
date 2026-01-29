package org.hau.project.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Android-specific implementation of [SystemAppearance].
 *
 * Uses the Accompanist SystemUiController to set the status and navigation bar 
 * colors to transparent, while adjusting the icon contrast based on the current 
 * theme (light vs. dark).
 */
@Composable
actual fun SystemAppearance(isDark: Boolean) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(systemUiController, isDark) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDark,
            isNavigationBarContrastEnforced = false
        )
    }
}
