package org.hau.project

import org.hau.project.di.SettingsFactory

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val settingsFactory: SettingsFactory = SettingsFactory()
}

actual fun getPlatform(): Platform = JVMPlatform()
