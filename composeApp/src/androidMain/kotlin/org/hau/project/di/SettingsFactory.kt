package org.hau.project.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsFactory actual constructor() {

    actual fun createSettings(): Settings {
        val context = companionContext
            ?: throw IllegalStateException("Context not initialized. Call SettingsFactory.setContext(context) in MainActivity.")

        return SharedPreferencesSettings(
            delegate = context.getSharedPreferences("hau_settings", Context.MODE_PRIVATE)
        )
    }

    companion object {
        private var companionContext: Context? = null

        fun setContext(context: Context) {
            companionContext = context.applicationContext
        }
    }
}