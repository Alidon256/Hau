package org.hau.project.di

import com.russhwolf.settings.Settings

/**
 * An 'expect' declaration defines a contract that platform-specific
 * code MUST implement. This allows our shared ViewModel to request a 'Settings'
 * instance without knowing the platform-specific storage details.
 */
expect class SettingsFactory() {
    fun createSettings(): Settings
}
