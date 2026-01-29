package org.hau.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import org.hau.project.di.SettingsFactory
import org.hau.project.ui.components.BottomNavigation // Mobile UI
import org.hau.project.ui.screens.large.AdaptiveUi // New Large Screen UI
import org.hau.project.ui.screens.auth.SplashScreen
import org.hau.project.ui.theme.AppTheme
import org.hau.project.utils.WindowSize
import org.hau.project.utils.rememberWindowSize
import org.hau.project.viewModels.LocalThemeViewModel
import org.hau.project.viewModels.SplashViewModel
import org.hau.project.viewModels.ThemeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The root Composable function for the Hau application.
 *
 * This function serves as the central orchestration point for the app's UI. It performs
 * several key tasks:
 * 1.  State Initialization: Initializes core ViewModels ([ThemeViewModel], [SplashViewModel])
 *     and collects their states.
 * 2.  Adaptive Layout Management: Uses [rememberWindowSize] to determine the current
 *     window size class and switch between mobile ([BottomNavigation]) and desktop/tablet
 *     ([AdaptiveUi]) layouts.
 * 3.  Theme Injection: Provides the [ThemeViewModel] to the entire application tree via
 *     [LocalThemeViewModel] and applies the global [AppTheme].
 * 4.  Loading State Handling: Displays a [SplashScreen] while initial data is being
 *     "loaded" (simulated in [SplashViewModel]).
 *
 * @param settingsFactory A factory for creating platform-specific settings for persistence.
 */
@Composable
@Preview
fun App(settingsFactory: SettingsFactory) {

    // Initialize the primary Theme ViewModel with platform-specific persistence
    val themeViewModel: ThemeViewModel = viewModel(
        factory = ThemeViewModel.createFactory(settingsFactory)
    )
    val themeUiState by themeViewModel.uiState.collectAsState()
    
    val splashViewModel: SplashViewModel = viewModel { SplashViewModel() }
    val isLoading by splashViewModel.isLoading.collectAsState()
    val windowSize = rememberWindowSize()

    CompositionLocalProvider(LocalThemeViewModel provides themeViewModel) {
        AppTheme(
            theme = themeUiState.theme,
            useDarkTheme = themeUiState.isDarkMode
        ) {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading) {
                    SplashScreen(onAnimationFinished = { splashViewModel.setLoading(false) })
                } else {
                    // Adaptive Navigation Routing based on screen size
                    when (windowSize) {
                        WindowSize.Compact, WindowSize.Medium -> {
                            BottomNavigation()
                        }
                        WindowSize.Expanded, WindowSize.Large, WindowSize.ExtraLarge -> {
                            AdaptiveUi()
                        }
                    }
                }
            }
        }
    }
}
