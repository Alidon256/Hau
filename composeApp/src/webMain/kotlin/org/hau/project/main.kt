package org.hau.project

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.hau.project.di.SettingsFactory

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val settingsFactory = SettingsFactory()

    ComposeViewport {
        App(
            settingsFactory = settingsFactory
        )
    }
}