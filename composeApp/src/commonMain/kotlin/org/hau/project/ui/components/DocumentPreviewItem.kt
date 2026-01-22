package org.hau.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DocumentPreviewItem(
    name: String?,
    size: String?,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "📄", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.width(10.dp))
        Column {
            if (!name.isNullOrBlank()) {
                Text(text = name, style = MaterialTheme.typography.bodyMedium, color = titleColor)
            }
            if (!size.isNullOrBlank()) {
                Text(
                    text = size,
                    style = MaterialTheme.typography.labelSmall,
                    color = subtitleColor
                )
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun DocumentPreviewStudio() {
    _root_ide_package_.org.hau.project.ui.components.DocumentPreviewItem(
        name = "Project_Brief.pdf",
        size = "45 KB",
        titleColor = MaterialTheme.colorScheme.onSurface,
        subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}