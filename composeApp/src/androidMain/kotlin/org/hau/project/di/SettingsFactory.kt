package org.hau.project.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

/**
 * The 'actual' implementation for Android. It uses the app's Context
 * to create a SharedPreferences-backed Settings instance.
 */
actual class SettingsFactory(private val context: Context) {
    actual fun createSettings(): Settings {
        return SharedPreferencesSettings(
            delegate = context.getSharedPreferences("hau_settings", Context.MODE_PRIVATE)
        )
    }
}
