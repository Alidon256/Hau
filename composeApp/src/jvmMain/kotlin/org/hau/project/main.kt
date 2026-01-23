package org.hau.project

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.ic_launcher_playstore
import org.hau.project.utils.LocalWindowWidth
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    val state = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hau",
        state = state,
        alwaysOnTop = true,
        icon = painterResource(Res.drawable.ic_launcher_playstore)
    ) {
        CompositionLocalProvider(LocalWindowWidth provides state.size.width) {
            App()
        }
    }
}
