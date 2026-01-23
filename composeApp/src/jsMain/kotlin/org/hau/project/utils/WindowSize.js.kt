package org.hau.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.browser.window
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget

@Composable
actual fun Int.dpToPx(): Dp = with(LocalDensity.current) { this@dpToPx.toDp() }

/**
 * Returns the current WindowSize based on the browser's inner width.
 */
private fun getWindowSize(): WindowSize {
    val screenWidth = window.innerWidth.dp
    return when {
        screenWidth < 600.dp -> WindowSize.Compact
        screenWidth < 840.dp -> WindowSize.Medium
        screenWidth < 1200.dp -> WindowSize.Expanded // Ideal for 2 panes
        screenWidth < 1600.dp -> WindowSize.Large      // Ideal for 3 panes
        else -> WindowSize.ExtraLarge // Ideal for all 4 panes
    }
}

/**
 * A helper extension function to create a Flow that emits events from a DOM EventTarget.
 */
private fun EventTarget.eventFlow(eventName: String) =
    kotlinx.coroutines.channels.Channel<Event>(kotlinx.coroutines.channels.Channel.CONFLATED).also { channel ->
        val listener: (Event) -> Unit = {
            channel.trySend(it)
        }
        addEventListener(eventName, listener)
        channel.invokeOnClose {
            removeEventListener(eventName, listener)
        }
    }.receiveAsFlow()

/**
 * Creates a flow that specifically listens to the window 'resize' event.
 */
private fun EventTarget.resizeFlow() = eventFlow("resize")

@Composable
actual fun rememberWindowWidth(): Dp {
    val windowWidth by produceState(initialValue = window.innerWidth.dp) {
        window.resizeFlow()
            .map { window.innerWidth.dp }
            .distinctUntilChanged()
            .collect { value = it }
    }
    return windowWidth
}

@Composable
actual fun rememberWindowSize(): WindowSize {
    val windowSize by produceState(initialValue = getWindowSize()) {
        window.resizeFlow()
            .map { getWindowSize() }
            .distinctUntilChanged()
            .collect { value = it }
    }
    return windowSize
}
