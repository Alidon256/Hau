package org.hau.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.viewmodel.compose.viewModel
import org.hau.project.di.SettingsFactory
import org.hau.project.ui.theme.AppTheme
import org.hau.project.utils.LocalWindowWidth
import org.hau.project.viewModels.ThemeViewModel

fun main() =application {
        val state = rememberWindowState()
        val settingsFactory = SettingsFactory()

        Window(
            onCloseRequest = ::exitApplication,
            title = "Hau",
            state = state,
            alwaysOnTop = true,
            icon = painterResource("icon.png"),
            undecorated = true
        ) {
            val themeViewModel: ThemeViewModel = viewModel(
                factory = ThemeViewModel.createFactory(settingsFactory)
            )
            val themeUiState by themeViewModel.uiState.collectAsState()

            AppTheme(
                theme = themeUiState.theme,
                useDarkTheme = themeUiState.isDarkMode
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(LocalWindowWidth provides state.size.width) {
                        Column(Modifier.fillMaxSize()) {
                            // --- CUSTOM TITLE BAR ---
                            WindowDraggableArea {
                                CustomTitleBar(
                                    title = "Hau",
                                    onMinimize = { state.isMinimized = true },
                                    onMaximize = {
                                        state.placement =
                                            if (state.placement == WindowPlacement.Maximized) {
                                                WindowPlacement.Floating
                                            } else {
                                                WindowPlacement.Maximized
                                            }
                                    },
                                    onClose = { exitApplication() }
                                )
                            }

                            // --- APP CONTENT ---
                            Box(Modifier.weight(1f)) {
                                App(settingsFactory)
                            }
                        }
                    }
                }
            }
        }
    }


@Composable
fun CustomTitleBar(
    title: String,
    onMinimize: () -> Unit,
    onMaximize: () -> Unit,
    onClose: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        modifier = Modifier.fillMaxWidth().height(40.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource("icon.png"),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            
            // Window Control Buttons
            TitleBarButton(Icons.Default.Remove, "Minimize", onMinimize)
            TitleBarButton(Icons.Default.CropSquare, "Maximize", onMaximize)
            TitleBarButton(
                icon = Icons.Default.Close, 
                contentDescription = "Close", 
                onClick = onClose,
                isClose = true
            )
        }
    }
}

@Composable
fun TitleBarButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    isClose: Boolean = false
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(36.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp), // Slightly larger for better centering visual
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
