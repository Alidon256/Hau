package org.hau.project.ui.screens.large

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Stream
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hau.project.ui.components.BottomNavItem
import org.hau.project.ui.components.NavDestinaton
import org.hau.project.ui.components.Routes

@Composable
fun NavigationRail(
    selectedDestination: NavDestinaton,
    onDestinationSelected: (NavDestinaton) -> Unit,
    modifier: Modifier = Modifier
) {
    val topItems = listOf(
        BottomNavItem(Icons.Outlined.Forum, Icons.Filled.Forum, Routes.HOME),
        BottomNavItem(Icons.Outlined.Stream, Icons.Filled.Stream, Routes.MEMORIES),
        BottomNavItem(Icons.Outlined.Call, Icons.Filled.Call, Routes.CALLS),
    )

    val bottomItems = listOf(
        BottomNavItem(Icons.Outlined.Settings, Icons.Filled.Settings, Routes.SETTINGS)
    )

    Surface(
        modifier = modifier.width(64.dp).fillMaxHeight(),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        tonalElevation = 1.dp
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    topItems.forEach { item ->
                        RailItem(
                            item = item,
                            isSelected = selectedDestination == item.destination,
                            onClick = { onDestinationSelected(item.destination) }
                        )
                    }
                }

                // Bottom Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    bottomItems.forEach { item ->
                        RailItem(
                            item = item,
                            isSelected = selectedDestination == item.destination,
                            onClick = { onDestinationSelected(item.destination) }
                        )
                    }
                }
            }
            
            // Subtle Vertical Divider
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            )
        }
    }
}

@Composable
private fun RailItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
    } else {
        Color.Transparent
    }
    
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = null,
            modifier = Modifier.size(24.dp), // Standard WhatsApp size
            tint = contentColor
        )
    }
}
