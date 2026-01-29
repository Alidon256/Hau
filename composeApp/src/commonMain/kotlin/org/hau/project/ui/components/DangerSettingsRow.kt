package org.hau.project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A specialized settings row designed for destructive or "dangerous" actions.
 *
 * This component uses the theme's error color for both the icon and the text to
 * visually warn the user about the nature of the action (e.g., Delete Account, Logout).
 *
 * @param icon The [ImageVector] to display at the start of the row.
 * @param text The label describing the dangerous action.
 * @param onClick Callback triggered when the row is tapped.
 */
@Composable
fun DangerSettingsRow(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
fun DangerSettingsRowLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            DangerSettingsRow(icon = Icons.Default.Delete, text = "Delete History", onClick = {})
        }
    }
}

@Preview
@Composable
fun DangerSettingsRowDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            DangerSettingsRow(icon = Icons.Default.Delete, text = "Remove Account", onClick = {})
        }
    }
}