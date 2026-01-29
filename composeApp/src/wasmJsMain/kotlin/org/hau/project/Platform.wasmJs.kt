package org.hau.project

import org.hau.project.di.SettingsFactory

/**
 * Wasm-specific implementation of the [Platform] interface for Kotlin/Wasm.
 */
class WasmPlatform : Platform {
    /**
     * Identifies the platform as Kotlin/Wasm Web.
     */
    override val name: String = "Web with Kotlin/Wasm"

    /**
     * Provides a [SettingsFactory] for the Wasm environment.
     * Interacts with browser-based storage through Wasm-JS interop.
     */
    override val settingsFactory: SettingsFactory = SettingsFactory()
}

/**
 * Platform-specific provider for the [Platform] instance in a WebAssembly (Wasm) environment.
 */
actual fun getPlatform(): Platform = WasmPlatform()
