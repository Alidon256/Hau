package org.hau.project

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.hau.project.ui.components.BottomNavigation // Mobile UI
import org.hau.project.ui.screens.large.AdaptiveUi // New Large Screen UI
import org.hau.project.ui.screens.auth.SplashScreen
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.hau.project.utils.WindowSize
import org.hau.project.utils.rememberWindowSize
import org.hau.project.viewModels.SplashViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val splashViewModel: SplashViewModel = viewModel { SplashViewModel() }
    val isLoading by splashViewModel.isLoading.collectAsState()
    val windowSize = rememberWindowSize()

    var currentTheme by remember { mutableStateOf(SocialTheme.Sky) }

    AppTheme(theme = currentTheme) {
        if (isLoading) {
            SplashScreen(onAnimationFinished = { splashViewModel.setLoading(false) })
        } else {
            // THE KEY CHANGE: Choose the UI based on screen size
            when (windowSize) {
                WindowSize.Compact, WindowSize.Medium -> {
                    // On smaller screens, use the existing BottomNavigation
                    BottomNavigation()
                }
                WindowSize.Expanded, WindowSize.Large, WindowSize.ExtraLarge -> {
                    // On larger screens, use the new Adaptive UI
                    AdaptiveUi()
                }
            }
        }
    }
}
