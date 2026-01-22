package org.hau.project.ui.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material.icons.outlined.LockPerson
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PhoneForwarded
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsTopAppBar
import org.hau.project.ui.components.NavDestinaton
import org.hau.project.ui.components.Routes
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

// Data class to structure the settings items for clarity and maintainability
private data class AccountSettingsItem(
    val icon: ImageVector,
    val title: String,
    val destination: NavDestinaton
)

@Composable
fun AccountScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    // Define the list of settings items and their corresponding navigation routes
    val accountItems = listOf(
        AccountSettingsItem(
            Icons.Outlined.Security,
            "Security notifications",
            Routes.SECURITY_NOTIFICATION
        ),
        AccountSettingsItem(
            Icons.Outlined.LockPerson,
            "Passkeys",
            Routes.PASSKEYS
        ),
        AccountSettingsItem(
            Icons.Outlined.Email,
            "Email address",
            Routes.EMAIL_ADDRESS
        ),
        AccountSettingsItem(
            Icons.Outlined.Password,
            "Two-step verification",
            Routes.SETTINGS
        ), // Placeholder, can lead to its own screen
        AccountSettingsItem(
            Icons.Outlined.PhoneForwarded,
            "Change number",
            Routes.SETTINGS
        ), // Placeholder
        AccountSettingsItem(
            Icons.Outlined.FileOpen,
            "Request account info",
            Routes.REQUEST_INFO
        ),
        AccountSettingsItem(
            Icons.Outlined.PersonAdd,
            "Add account",
            Routes.SETTINGS
        ), // Placeholder
        AccountSettingsItem(
            Icons.Outlined.DeleteOutline,
            "Delete account",
            Routes.DELETE_ACCOUNT
        )
    )

    Scaffold(
        topBar = {
            SettingsTopAppBar(
                "Account",
                onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(accountItems) { item ->
                SettingsInfoHelpItem(
                    icon = item.icon,
                    title = item.title,
                    subtitle = "", // Account screen items don't have subtitles
                    onClick = { navController.navigate(item.destination) }
                )
            }
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Account Screen (Dark)", showBackground = true)
@Composable
private fun AccountScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        AccountScreen(
            navController = rememberNavController(),
            onBack = {})
    }
}

@Preview(name = "Account Screen (Light)", showBackground = true)
@Composable
private fun AccountScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        AccountScreen(
            navController = rememberNavController(),
            onBack = {})
    }
}
