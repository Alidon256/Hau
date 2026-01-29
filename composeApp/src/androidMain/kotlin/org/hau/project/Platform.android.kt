package org.hau.project

import android.content.Context
import org.hau.project.di.SettingsFactory

/**
 * Android-specific implementation of the [Platform] interface.
 * 
 * Provides information about the Android environment, including the SDK version,
 * and initializes the [SettingsFactory] with the required Android [Context].
 * 
 * @property context The Android [Context] used for initializing platform-specific services.
 */
class AndroidPlatform(private val context: Context) : Platform {
    /**
     * The name of the platform including the Android SDK version.
     */
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    /**
     * Initializes and provides the [SettingsFactory] for Android.
     * The [context] is injected into the factory to enable SharedPreferences-based storage.
     */
    override val settingsFactory: SettingsFactory = run {
        SettingsFactory.setContext(context)
        SettingsFactory()
    }
}

/**
 * Platform-specific provider for the [Platform] instance on Android.
 * 
 * **Note:** This implementation currently throws an [Error] because it requires
 * an Android [Context] which should be provided via platform initialization 
 * in the `MainActivity`.
 */
actual fun getPlatform(): Platform = error("Provide context via Platform initialization in MainActivity")
