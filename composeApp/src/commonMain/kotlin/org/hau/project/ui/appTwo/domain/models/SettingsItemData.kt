package org.hau.project.ui.appTwo.domain.models

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingItemData(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit
)