package org.hau.project

import org.hau.project.di.SettingsFactory

/**
 * JavaScript-specific implementation of the [Platform] interface for Kotlin/JS (Legacy/IR).
 */
class JsPlatform : Platform {
    /**
     * Identifies the platform as Kotlin/JS Web.
     */
    override val name: String = "Web with Kotlin/JS"

    /**
     * Provides a [SettingsFactory] for the JS environment.
     * Typically uses browser `localStorage` or `indexedDB` via specialized multiplatform libraries.
     */
    override val settingsFactory: SettingsFactory = SettingsFactory()
}

/**
 * Platform-specific provider for the [Platform] instance in a JavaScript environment.
 */
actual fun getPlatform(): Platform = JsPlatform()
