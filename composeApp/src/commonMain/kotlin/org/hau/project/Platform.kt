package org.hau.project

import org.hau.project.di.SettingsFactory

interface Platform {
    val name: String
    val settingsFactory: SettingsFactory
}

expect fun getPlatform(): Platform
