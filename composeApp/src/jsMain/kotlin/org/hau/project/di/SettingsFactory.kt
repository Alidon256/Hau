package org.hau.project.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import kotlinx.browser.localStorage

/**
  * The 'actual' implementation for Web (JS).
  * This uses the StorageSettings wrapper around the browser's localStorage.
  */
actual class SettingsFactory actual constructor() {
    actual fun createSettings(): Settings {
        return StorageSettings(localStorage)
    }
}
