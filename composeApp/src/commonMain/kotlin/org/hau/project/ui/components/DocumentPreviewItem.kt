package org.hau.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A summary component used to display a preview of an attached document file within a chat message.
 *
 * @param name The filename of the document (e.g., "Project_Report.pdf").
 * @param size The human-readable file size (e.g., "2.4 MB").
 * @param titleColor The [Color] applied to the document name text.
 * @param subtitleColor The [Color] applied to the file size text.
 */
@Composable
fun DocumentPreviewItem(
    name: String?,
    size: String?,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Document Icon Representation
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
            // Document Name
            if (!name.isNullOrBlank()) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = titleColor
                )
            }
            // File Size Metadata
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

@Preview
@Composable
fun DocumentPreviewLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.padding(16.dp)) {
            DocumentPreviewItem(name = "Annual_Budget.xlsx", size = "1.2 MB")
        }
    }
}

@Preview
@Composable
fun DocumentPreviewDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.padding(16.dp)) {
            DocumentPreviewItem(name = "Design_Mockups.fig", size = "45 KB")
        }
    }
}