package org.hau.project

import android.content.Context
import org.hau.project.di.SettingsFactory

class AndroidPlatform(private val context: Context) : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

   override val settingsFactory: SettingsFactory = run {
        SettingsFactory.setContext(context)
        SettingsFactory()
    }
}

actual fun getPlatform(): Platform = error("Provide context via Platform initialization in MainActivity")