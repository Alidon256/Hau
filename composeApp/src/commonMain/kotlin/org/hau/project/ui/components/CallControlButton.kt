package org.hau.project.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A specialized circular button used for call control actions (e.g., mute, hang up).
 *
 * This component wraps an icon in a circular [Surface], allowing for custom sizing,
 * colors, and standard click handling. It is primarily used within the [ModernCallPanel].
 *
 * @param icon The [ImageVector] to display in the center of the button.
 * @param containerColor The background color of the circular surface.
 * @param contentColor The tint color applied to the icon.
 * @param size The diameter of the circular button. Defaults to 52.dp.
 * @param iconSize The size of the icon within the button. Defaults to 24.dp.
 * @param onClick The callback triggered when the button is pressed.
 */
@Composable
fun CallControlButton(
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    size: Dp = 52.dp,
    iconSize: Dp = 24.dp,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(size),
        shape = CircleShape,
        color = containerColor,
        contentColor = contentColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}
