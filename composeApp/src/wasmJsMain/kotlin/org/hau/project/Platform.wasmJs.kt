package org.hau.project

import org.hau.project.di.SettingsFactory

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"

    override val settingsFactory: SettingsFactory = SettingsFactory()
}

actual fun getPlatform(): Platform = WasmPlatform()