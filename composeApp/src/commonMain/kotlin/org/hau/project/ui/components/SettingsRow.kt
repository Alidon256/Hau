package org.hau.project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A highly versatile settings row component used in the application's settings hierarchy.
 *
 * This component supports various configurations:
 * - Basic text row with an icon.
 * - Row with a multi-line description.
 * - Navigation row with trailing text and a chevron.
 * - Toggle row with a [Switch].
 *
 * @param icon The [ImageVector] to display at the beginning of the row.
 * @param text The main title of the setting.
 * @param description Optional secondary text providing more details about the setting.
 * @param trailingText Optional text displayed at the end of the row (usually with a chevron).
 * @param isToggle If `true`, a [Switch] is displayed instead of navigation elements.
 * @param checked The state of the toggle, if [isToggle] is `true`.
 * @param onCheckedChange Callback invoked when the toggle state changes.
 * @param onClick Callback invoked when the row is clicked. If null, the row is not clickable.
 */
@Composable
fun SettingsRow(
    icon: ImageVector,
    text: String,
    description: String? = null,
    trailingText: String? = null,
    isToggle: Boolean = false,
    checked: Boolean = false,
    onCheckedChange: () -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = onClick ?: {})
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
            if (description != null) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
        }
        if (trailingText != null) {
            Text(
                text = trailingText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (isToggle) {
            Switch(checked = checked, onCheckedChange = { onCheckedChange() })
        }
    }
}

@Preview(name = "Settings Row - Basic")
@Composable
fun SettingsRowBasicPreview() {
    AppTheme {
        Surface {
            SettingsRow(
                icon = Icons.Default.Notifications,
                text = "Notifications",
                onClick = {}
            )
        }
    }
}

@Preview(name = "Settings Row - Toggle")
@Composable
fun SettingsRowTogglePreview() {
    AppTheme {
        Surface {
            SettingsRow(
                icon = Icons.Default.Notifications,
                text = "Show Notifications",
                description = "Enable or disable all app notifications",
                isToggle = true,
                checked = true
            )
        }
    }
}
