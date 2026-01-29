package org.hau.project

import org.hau.project.di.SettingsFactory

/**
 * Core interface representing the specific host platform (Android, iOS, Desktop, etc.).
 *
 * This interface is used to bridge common code with platform-specific implementations,
 * providing access to system-level identifiers and specialized factories required for
 * cross-platform functionality.
 */
interface Platform {
    /**
     * The human-readable name of the current platform.
     * 
     * Implementations typically return values like "Android 34", "iOS 17.2", etc.
     */
    val name: String

    /**
     * A factory instance used to produce platform-specific [Settings] or persistence objects.
     * This allows the common module to handle data storage in a platform-agnostic way.
     */
    val settingsFactory: SettingsFactory
}

/**
 * Retrieves the specific [Platform] implementation for the current environment.
 *
 * This is an expected function that must be defined in the common module and
 * implemented in each platform-specific source set (e.g., `androidMain`, `iosMain`).
 *
 * @return The [Platform] instance corresponding to the host environment.
 */
expect fun getPlatform(): Platform
