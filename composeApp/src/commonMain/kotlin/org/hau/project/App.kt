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

@Composable
@Preview
fun App(settingsFactory: SettingsFactory) {


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
