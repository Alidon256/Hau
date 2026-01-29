package org.hau.project.ui.theme

import androidx.compose.runtime.Composable

/**
 * An expected composable function used to control the appearance of system-level
 * UI components such as the status bar and navigation bar.
 *
 * Implementations vary by platform:
 * - **Android**: Uses SystemUiController to set colors and icon contrast.
 * - **Web/Desktop**: May be a no-op or interact with window/browser headers.
 *
 * @param isDark Whether the system UI should be optimized for a dark theme (e.g., light icons).
 */
@Composable
expect fun SystemAppearance(isDark: Boolean)
