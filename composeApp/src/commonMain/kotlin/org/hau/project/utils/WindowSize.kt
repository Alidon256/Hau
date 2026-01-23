package org.hau.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Represents the different window size classes to build adaptive UIs.
 */
enum class WindowSize {
    Compact,    // Phones (< 600dp)
    Medium,     // Tablets in portrait, large phones (< 840dp)
    Expanded,   // Tablets in landscape (< 1200dp)
    Large,      // Desktops with enough space for 3 panes
    ExtraLarge  // Wide desktops with space for all 4 panes
}

/**
 * A composable that provides the current [WindowSize] class based on screen width.
 * This is the cornerstone of building adaptive layouts.
 *
 * @return The current [WindowSize].
 */
@Composable
expect fun rememberWindowSize(): WindowSize

/**
 * A composable that provides the current [WindowSize] class based on screen width.
 * This is the cornerstone of building adaptive layouts.
 *
 * @param width The current width of the window or screen.
 * @return The current [WindowSize].
 */
@Composable
expect fun rememberWindowWidth(): Dp

// A helper function to get Dp value from Int
@Composable
expect fun Int.dpToPx(): Dp
