package org.hau.project.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class representing an action item for a Multi-Action Floating Action Button.
 *
 * @property icon The [ImageVector] to be displayed as the button's icon.
 * @property label The text label describing the action, often used for accessibility or tooltip.
 * @property backgroundColor The [Color] applied to the background of this specific action button.
 * @property onClick The callback function executed when this action is triggered.
 */
data class FabAction(
    val icon: ImageVector,
    val label: String,
    val backgroundColor: Color,
    val onClick: () -> Unit
)
