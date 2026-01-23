package org.hau.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// A CompositionLocal to provide the window width from the root (main.kt)
// to any composable down the tree.
val LocalWindowWidth = compositionLocalOf { 0.dp }

/**
 * The desktop-specific implementation of rememberWindowWidth.
 * It reads the width provided by the Window scope via a CompositionLocal.
 */
@Composable
actual fun rememberWindowWidth(): Dp {
    return LocalWindowWidth.current
}

@Composable
actual fun Int.dpToPx(): Dp {
    return with(LocalDensity.current) {
        this@dpToPx.toDp()
    }
}

@Composable
actual fun rememberWindowSize(): WindowSize {
    val windowWidth = LocalWindowWidth.current
    return when {
        windowWidth < 600.dp -> WindowSize.Compact
        windowWidth < 840.dp -> WindowSize.Medium
        windowWidth < 1200.dp -> WindowSize.Expanded
        windowWidth < 1600.dp -> WindowSize.Large
        else -> WindowSize.ExtraLarge
    }
}
