package org.hau.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A contextual action menu displayed when a user long-presses or right-clicks a message bubble.
 *
 * Provides standard messaging utility actions including Reply, Copy, Forward, Star, and Delete.
 * The menu is styled with modern rounded corners and uses the `surfaceContainerHigh` background.
 *
 * @param expanded Controls the visibility of the dropdown menu.
 * @param onDismiss Callback to handle menu closure (e.g., user taps outside or selects an action).
 */
@Composable
fun MessageActionMenu(expanded: Boolean, onDismiss: () -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .clip(RoundedCornerShape(16.dp))
    ) {
        DropdownMenuItem(
            text = { Text("Reply") },
            leadingIcon = { Icon(Icons.AutoMirrored.Filled.Reply, null) },
            onClick = onDismiss
        )
        DropdownMenuItem(
            text = { Text("Copy") },
            leadingIcon = { Icon(Icons.Outlined.ContentCopy, null) },
            onClick = onDismiss
        )
        DropdownMenuItem(
            text = { Text("Forward") },
            leadingIcon = { Icon(Icons.AutoMirrored.Filled.Reply, null) },
            onClick = onDismiss
        )
        DropdownMenuItem(
            text = { Text("Star") },
            leadingIcon = { Icon(Icons.Outlined.StarOutline, null) },
            onClick = onDismiss
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        DropdownMenuItem(
            text = { Text("Delete") },
            leadingIcon = { Icon(Icons.Outlined.Delete, null, tint = MaterialTheme.colorScheme.error) },
            onClick = onDismiss
        )
    }
}

@Preview
@Composable
fun MessageActionMenuLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            Box(Modifier.padding(16.dp)) {
                MessageActionMenu(expanded = true, onDismiss = {})
            }
        }
    }
}

@Preview
@Composable
fun MessageActionMenuDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            Box(Modifier.padding(16.dp)) {
                MessageActionMenu(expanded = true, onDismiss = {})
            }
        }
    }
}