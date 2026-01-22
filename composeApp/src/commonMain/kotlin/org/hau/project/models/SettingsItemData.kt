package org.hau.project.models

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingItemData(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit
)