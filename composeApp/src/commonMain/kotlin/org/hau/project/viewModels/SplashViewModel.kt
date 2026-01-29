package org.hau.project.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the splash screen state and initial application loading.
 *
 * It handles the transition from the splash screen to the main application content by
 * simulating or performing essential startup tasks such as session validation or
 * configuration fetching.
 */
class SplashViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    /**
     * A [StateFlow] indicating whether the application is still in its loading/splash phase.
     * Observed by the UI to determine when to dismiss the splash screen.
     */
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            // Simulate initial bootstrap process (e.g., checking auth, loading local preferences)
            delay(2000) // Maintain splash visibility for a consistent brand experience
            _isLoading.value = false
        }
    }

    /**
     * Manually updates the loading state.
     *
     * @param loading The new loading state to set.
     */
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}
