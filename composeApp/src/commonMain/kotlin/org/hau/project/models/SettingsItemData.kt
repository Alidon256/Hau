package org.hau.project.models

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class representing a generic row item in the settings list.
 *
 * @property icon The [ImageVector] to be displayed at the start of the row.
 * @property title The primary text for the setting item (e.g., "Account").
 * @property subtitle The secondary text providing additional details or current status.
 * @property onClick The callback function executed when the entire row is clicked.
 */
data class SettingItemData(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit
)
