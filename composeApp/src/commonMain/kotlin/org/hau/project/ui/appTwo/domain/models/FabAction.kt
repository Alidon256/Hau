package org.hau.project.ui.appTwo.domain.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class FabAction(
    val icon: ImageVector,
    val label: String,
    val backgroundColor: Color,
    val onClick: () -> Unit
)