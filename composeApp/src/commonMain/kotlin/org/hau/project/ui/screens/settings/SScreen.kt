package org.hau.project.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_3
import org.hau.project.models.SettingItemData
import org.hau.project.models.SettingsUser
import org.hau.project.ui.components.Routes
import org.hau.project.ui.components.SettingRow
import org.hau.project.ui.components.SettingsUserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SScreen(
    navController: NavController? = null,
    onBackClick: () -> Unit
) {
    val settingsUser = SettingsUser(
        contactName = "Mugumya Ali",
        contactRes = Res.drawable.story_3,
        contactId = "hdfddhdf",
        contactDesc = "Alidon!",
        contact = "+256 785330244"
    )

    val settingItems = listOf(
        SettingItemData(
            icon = Icons.Outlined.Key,
            title = "Account & Security",
            subtitle = "Passkeys, security alerts, and number migration"
        ) { navController?.navigate(Routes.ACCOUNT) },

        SettingItemData(
            icon = Icons.Outlined.Lock,
            title = "Privacy Control",
            subtitle = "Blocked users, disappearing messages, and visibility"
        ) { navController?.navigate(Routes.PRIVACY) },

        SettingItemData(
            icon = Icons.Outlined.Person,
            title = "Digital Identity",
            subtitle = "Avatar customization and profile visibility"
        ) { navController?.navigate(Routes.AVATAR) },

        SettingItemData(
            icon = Icons.Outlined.Forum,
            title = "Messaging & Chats",
            subtitle = "Themes, rich wallpapers, and conversation backup"
        ) { navController?.navigate(Routes.MESSAGING) },

        SettingItemData(
            icon = Icons.Outlined.Notifications,
            title = "Alerts & Sounds",
            subtitle = "Manage message, group, and priority call tones"
        ) { navController?.navigate(Routes.NOTIFICATIONS) },

        SettingItemData(
            icon = Icons.Outlined.Storage,
            title = "Data & Connectivity",
            subtitle = "Network usage monitoring and media auto-download"
        ) { navController?.navigate(Routes.STORAGE) },

        SettingItemData(
            icon = Icons.Outlined.Language,
            title = "Regional & Language",
            subtitle = "Switch app language (System Default: English)"
        ) { navController?.navigate(Routes.LANGUAGE) },

        SettingItemData(
            icon = Icons.Outlined.Help,
            title = "Support & Legal",
            subtitle = "Help center, privacy policy, and live support"
        ) { navController?.navigate(Routes.HELP) },

        SettingItemData(
            icon = Icons.Outlined.Group,
            title = "Spread the Word",
            subtitle = "Invite your network to join the Hau community"
        ) { navController?.navigate(Routes.INVITE) }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background, // Use theme background
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background, // Use surface color
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    title = {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackClick() }) {
                            Icon(
                                Icons.Outlined.ArrowBack,
                                contentDescription = "Navigate Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Outlined.Search,
                                contentDescription = "Search In Settings"
                            )
                        }
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant // Use theme color for dividers
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SettingsUserItem(settingsUser = settingsUser)
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            }

            items(settingItems) { item ->
                SettingRow(
                    icon = item.icon,
                    title = item.title,
                    subtitle = item.subtitle,
                    onClick = item.onClick
                )
            }
        }
    }
}