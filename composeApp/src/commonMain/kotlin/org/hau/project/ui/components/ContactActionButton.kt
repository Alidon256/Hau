package org.hau.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A standardized row item for contact-related actions (e.g., "New Group", "New Contact").
 *
 * This component features a prominent themed circular icon followed by a bold title.
 * It is commonly used in list headers or action menus where clear, actionable triggers are needed.
 *
 * @param icon The [ImageVector] to be displayed in the primary circular background.
 * @param title The label for the action.
 * @param onClick Callback triggered when the entire row is clicked.
 * @param trailingContent Optional Composable to be rendered at the end of the row.
 */
@Composable
fun ContactActionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Primary Action Icon with Themed Background
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Action Title
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )

        // Optional slot for extra UI elements (e.g., badge or arrow)
        if (trailingContent != null) {
            Spacer(modifier = Modifier.weight(1f))
            trailingContent()
        }
    }
}

@Preview
@Composable
fun ContactActionItemLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            ContactActionItem(
                icon = Icons.Default.Group,
                title = "New Group",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun ContactActionItemDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            ContactActionItem(
                icon = Icons.Default.PersonAdd,
                title = "New Contact",
                onClick = {}
            )
        }
    }
}