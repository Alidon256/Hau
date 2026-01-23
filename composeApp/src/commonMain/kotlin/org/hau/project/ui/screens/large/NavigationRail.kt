package org.hau.project.ui.screens.large

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.ic_launcher_playstore
import org.hau.project.ui.components.BottomNavItem
import org.hau.project.ui.components.NavDestinaton
import org.hau.project.ui.components.Routes
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavigationRail(
    selectedDestination: NavDestinaton,
    onDestinationSelected: (NavDestinaton) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(Icons.Default.Forum, Icons.Filled.Forum, Routes.HOME),
        BottomNavItem(Icons.Default.Stream, Icons.Filled.Stream, Routes.MEMORIES),
        BottomNavItem(Icons.Default.Call, Icons.Filled.Call, Routes.CALLS),
        BottomNavItem(Icons.Default.Settings, Icons.Filled.Settings, Routes.SETTINGS)
    )

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        // Your App Logo
        Icon(
            painter = painterResource(Res.drawable.ic_launcher_playstore),
            contentDescription = "Hau Logo",
            modifier = Modifier.size(48.dp).clip(CircleShape),
            //tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(16.dp))

        // Navigation Items
        items.forEach { item ->
            NavigationRailItem(
                icon = {
                    Icon(
                        if (selectedDestination == item.destination) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedDestination == item.destination,
                onClick = { onDestinationSelected(item.destination) },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
