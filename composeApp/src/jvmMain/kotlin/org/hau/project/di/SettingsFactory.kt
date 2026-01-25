package org.hau.project.di

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

/**
 * The 'actual' implementation for JVM (Desktop).
 * This uses the java.util.prefs.Preferences API for persistence.
 */
actual class SettingsFactory {
    actual fun createSettings(): Settings {
        val delegate = Preferences.userRoot().node("org.hau.project.settings")
        return PreferencesSettings(delegate)
    }
}
