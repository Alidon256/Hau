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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.hau.project.ui.components.SettingsTopAppBar
import org.hau.project.ui.components.NavDestinaton
import org.hau.project.ui.components.Routes
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Represents an individual entry in the account settings list.
 */
private data class AccountSettingsItem(
    val icon: ImageVector,
    val title: String,
    val destination: NavDestinaton
)

/**
 * A screen for managing account-level security and administrative tasks.
 *
 * It provides a list of options including security notifications, passkeys,
 * email management, two-step verification, and account deletion.
 *
 * @param navController The navigation controller used to route to specific account features.
 * @param onBack Callback invoked when the user navigates back.
 */
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

@Preview(name = "Account Screen (Sky Dark)", showBackground = true)
@Composable
private fun AccountScreenDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        Surface {
            AccountScreen(
                navController = rememberNavController(),
                onBack = {})
        }
    }
}

@Preview(name = "Account Screen (Sky Light)", showBackground = true)
@Composable
private fun AccountScreenLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        Surface {
            AccountScreen(
                navController = rememberNavController(),
                onBack = {})
        }
    }
}
