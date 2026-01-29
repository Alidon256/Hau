package org.hau.project

import org.hau.project.di.SettingsFactory

/**
 * JVM-specific implementation of the [Platform] interface for Desktop applications.
 */
class JVMPlatform : Platform {
    /**
     * Identifies the platform as Java along with the runtime version.
     */
    override val name: String = "Java ${System.getProperty("java.version")}"

    /**
     * Provides a [SettingsFactory] for the JVM environment.
     * Uses platform-specific file system storage (e.g., in the user's local app data folder).
     */
    override val settingsFactory: SettingsFactory = SettingsFactory()
}

/**
 * Platform-specific provider for the [Platform] instance in a JVM (Desktop) environment.
 */
actual fun getPlatform(): Platform = JVMPlatform()
