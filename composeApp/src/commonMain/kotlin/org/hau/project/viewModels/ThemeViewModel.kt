package org.hau.project.viewModels

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.hau.project.di.SettingsFactory
import org.hau.project.ui.theme.SocialTheme
import kotlin.reflect.KClass

/**
 * Represents the visual configuration state of the application.
 *
 * @property theme The current color palette ([SocialTheme]) being applied.
 * @property isDarkMode Whether the application is currently in dark mode.
 */
data class ThemeUiState(
    val theme: SocialTheme = SocialTheme.Sky,
    val isDarkMode: Boolean = false
)

/**
 * The core engine for managing and persisting the application's theme and dark mode settings.
 *
 * This ViewModel is responsible for:
 * 1.  **Persistence**: Saving and loading user preferences across app restarts using
 *     the Multiplatform Settings library.
 * 2.  **Reactive Updates**: Exposing a [ThemeUiState] via [StateFlow] so the entire UI
 *     tree can react instantly to theme changes.
 * 3.  **Platform Independence**: Utilizing a [Settings] instance provided by a platform-specific
 *     factory to abstract away storage implementation details (e.g., SharedPreferences vs. LocalStorage).
 *
 * @param settings The underlying storage delegate for persisting preferences.
 */
class ThemeViewModel(private val settings: Settings) : ViewModel() {

    companion object {
        private const val KEY_THEME = "app_theme_palette"
        private const val KEY_DARK_MODE = "app_dark_mode"

        /**
         * Creates a [ViewModelProvider.Factory] to instantiate this ViewModel with
         * the correct platform-specific settings.
         *
         * @param settingsFactory The factory responsible for creating the [Settings] instance.
         */
        fun createFactory(settingsFactory: SettingsFactory): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return ThemeViewModel(settingsFactory.createSettings()) as T
            }
        }
    }

    // Holds the current UI state, initialized by loading values from persistent storage.
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
    
    /**
     * An observable stream of the current theme state.
     */
    val uiState = _uiState.asStateFlow()

    /**
     * Updates the application's primary color palette and persists the choice.
     *
     * @param theme The new [SocialTheme] to apply.
     */
    fun updateTheme(theme: SocialTheme) {
        settings[KEY_THEME] = theme.name
        _uiState.update { it.copy(theme = theme) }
    }

    /**
     * Toggles between light and dark mode and persists the choice.
     *
     * @param isDark `true` to enable dark mode, `false` for light mode.
     */
    fun toggleDarkMode(isDark: Boolean) {
        settings[KEY_DARK_MODE] = isDark
        _uiState.update { it.copy(isDarkMode = isDark) }
    }
}

/**
 * A `CompositionLocal` used to provide the [ThemeViewModel] globally throughout the Composable tree.
 * This avoids manual parameter passing and allows any UI component to easily trigger theme changes.
 */
val LocalThemeViewModel = compositionLocalOf<ThemeViewModel> {
    error("No ThemeViewModel provided. Ensure it is wrapped in a CompositionLocalProvider at the root.")
}
