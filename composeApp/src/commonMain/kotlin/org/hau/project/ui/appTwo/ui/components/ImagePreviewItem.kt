package org.hau.project.ui.appTwo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImagePreviewItem(imageUrl: String? = null) {
    Box(
        modifier = Modifier
            .widthIn(max = 280.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    error = painterResource(Res.drawable.grattitude)
                ),
                contentDescription = "Attached image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.grattitude),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
@Preview
fun ImagePreviewPlaceholderPreview() {
    ImagePreviewItem()
}
