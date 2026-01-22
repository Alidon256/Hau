package org.hau.project.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCode
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_3
import org.hau.project.models.SettingItemData
import org.hau.project.ui.components.Routes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


// Data class to hold user information for the settings screen
data class SettingsUser(
    val contactName: String,
    val contactRes: DrawableResource,
    val contactId: String,
    val contactDesc: String,
    val contact: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController? = null
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
            Icons.Outlined.Key,
            "Account",
            "Security notifications, change number"
        ) { navController?.navigate(Routes.ACCOUNT) },
        SettingItemData(
            Icons.Outlined.Lock,
            "Privacy",
            "Block contacts, disappearing messages"
        ) { navController?.navigate(Routes.PRIVACY) },
        SettingItemData(
            Icons.Outlined.Person,
            "Avatar",
            "Create, edit, profile photo"
        ) { navController?.navigate(Routes.AVATAR) },
        SettingItemData(
            Icons.Outlined.Forum,
            "Chats",
            "Theme, wallpapers, chat history"
        ) { navController?.navigate(Routes.MESSAGING) },
        SettingItemData(
            icon = Icons.Outlined.Notifications,
            title = "Notifications",
            subtitle = "Message, group & call tones"
        ) { navController?.navigate(Routes.NOTIFICATIONS) },
        SettingItemData(
            icon = Icons.Outlined.Storage,
            title = "Storage and data",
            subtitle = "Network usage, auto-download"
        ) { navController?.navigate(Routes.STORAGE) },
        SettingItemData(
            icon = Icons.Outlined.Language,
            title = "App language",
            subtitle = "English (device's language)"
        ) { navController?.navigate(Routes.LANGUAGE) },
        SettingItemData(
            icon = Icons.Outlined.Help,
            title = "Help",
            subtitle = "Help center, contact us, privacy policy"
        ) { navController?.navigate(Routes.HELP) },
        SettingItemData(
            icon = Icons.Outlined.Group,
            title = "Invite a friend",
            subtitle = "Invite your friends and family"
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
                    /*navigationIcon = {
                        IconButton(onClick = { /* TODO: Handle navigation back */ }) {
                            Icon(
                                Icons.Outlined.ArrowBack,
                                contentDescription = "Navigate Back"
                            )
                        }
                    },*/
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

@Composable
fun SettingsUserItem(settingsUser: SettingsUser) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Navigate to Profile */ }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(settingsUser.contactRes),
            contentDescription = "User Profile",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = settingsUser.contactName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface // Use onSurface for primary text
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = settingsUser.contactDesc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Use onSurfaceVariant for secondary text
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Outlined.QrCode,
            contentDescription = "QR Code",
            tint = MaterialTheme.colorScheme.primary, // Tint with primary color
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun SettingRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurfaceVariant, // Use for icons
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface // Use for titles
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Use for subtitles
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    // You can wrap previews in your AppTheme to see how they look
    // import org.hau.project.ui.theme.AppTheme
    // AppTheme {
    SettingsScreen()
    // }
}
