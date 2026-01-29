package org.hau.project.ui.theme

import androidx.compose.runtime.Composable

/**
 * JVM-specific implementation of [SystemAppearance] for Desktop applications.
 *
 * Current implementation is a no-op as the desktop window appearance is 
 * managed through the custom title bar and window state in `main.kt`.
 */
@Composable
actual fun SystemAppearance(isDark: Boolean) {
    // No-op for JVM/Desktop
}
