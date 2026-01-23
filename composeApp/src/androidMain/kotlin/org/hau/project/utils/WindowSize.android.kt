package org.hau.project.utils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
actual fun rememberWindowSize(): WindowSize {
    val context = LocalContext.current
    val activity = context.findActivity()

    // Android calculates width internally, so we use its own values
    val windowWidth = LocalConfiguration.current.screenWidthDp.dp
    val windowSizeClass = if (activity != null) calculateWindowSizeClass(activity).widthSizeClass else WindowWidthSizeClass.Compact

    return when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> WindowSize.Compact
        WindowWidthSizeClass.Medium -> WindowSize.Medium
        WindowWidthSizeClass.Expanded -> {
            // Refine based on dp width for larger screens (tablets, foldables)
            when {
                windowWidth < 1200.dp -> WindowSize.Expanded
                windowWidth < 1600.dp -> WindowSize.Large
                else -> WindowSize.ExtraLarge
            }
        }
        else -> WindowSize.Expanded
    }
}
@Composable
actual fun Int.dpToPx(): Dp = with(LocalDensity.current) { this@dpToPx.toDp() }

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

@Composable
actual fun rememberWindowWidth(): Dp {
    return LocalConfiguration.current.screenWidthDp.dp
}
