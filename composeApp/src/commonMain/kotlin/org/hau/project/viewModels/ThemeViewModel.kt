package org.hau.project.viewModels

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.hau.project.di.SettingsFactory
import org.hau.project.ui.theme.SocialTheme
import kotlin.reflect.KClass

/**
 * Settings UI State representing the theme preferences.
 */
data class ThemeUiState(
    val theme: SocialTheme = SocialTheme.Sky,
    val isDarkMode: Boolean = false
)

/**
 * ThemeViewModel responsible for persisting and managing the application's visual state.
 * Uses Multiplatform Settings for cross-target persistence.
 */
class ThemeViewModel(private val settings: Settings) : ViewModel() {

    companion object {
        private const val KEY_THEME = "app_theme_palette"
        private const val KEY_DARK_MODE = "app_dark_mode"

        /**
         * A factory to create the ThemeViewModel with a platform-specific Settings instance.
         */
        fun createFactory(settingsFactory: SettingsFactory): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return ThemeViewModel(settingsFactory.createSettings()) as T
            }
        }
    }

    private val _uiState = MutableStateFlow(
        ThemeUiState(
            theme = try {
                SocialTheme.valueOf(settings.getString(KEY_THEME, SocialTheme.Sky.name))
            } catch (e: Exception) {
                SocialTheme.Sky
            },
            isDarkMode = settings.getBoolean(KEY_DARK_MODE, false)
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateTheme(theme: SocialTheme) {
        settings[KEY_THEME] = theme.name
        _uiState.update { it.copy(theme = theme) }
    }

    fun toggleDarkMode(isDark: Boolean) {
        settings[KEY_DARK_MODE] = isDark
        _uiState.update { it.copy(isDarkMode = isDark) }
    }
}

/**
 * CompositionLocal to provide the ThemeViewModel globally.
 */
val LocalThemeViewModel = compositionLocalOf<ThemeViewModel> {
    error("No ThemeViewModel provided")
}
