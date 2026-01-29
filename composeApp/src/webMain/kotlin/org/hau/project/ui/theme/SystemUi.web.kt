package org.hau.project.ui.theme

import androidx.compose.runtime.Composable

/**
 * Web-specific implementation of [SystemAppearance].
 *
 * Current implementation is a no-op as system UI elements like status bars
 * are not directly controllable from the web runtime in the same way they
 * are on mobile platforms.
 */
@Composable
actual fun SystemAppearance(isDark: Boolean) {
    // No-op for Web
}
