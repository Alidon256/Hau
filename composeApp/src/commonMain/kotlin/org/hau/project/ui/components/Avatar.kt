package org.hau.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add // For placeholder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage // Assuming coil3 is your image loading library
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Avatar(
    name: String,
    size: Dp,
    avatarUrl: DrawableResource? = null,
    hasStory: Boolean = false,
    isStoryPlaceholder: Boolean = false,
    modifier: Modifier = Modifier
) {
    val initials = name.takeIf { it.isNotBlank() }?.split(" ")
        ?.filter { it.isNotBlank() }
        ?.take(2)
        ?.map { it.first().uppercaseChar() }
        ?.joinToString("") ?: ""

    val borderColor = when {
        hasStory && !isStoryPlaceholder -> MaterialTheme.colorScheme.primary
        isStoryPlaceholder -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val borderWidth = if (hasStory || isStoryPlaceholder) 2.dp else 0.dp

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant) // Fallback background
            .then(
                if (borderWidth > 0.dp) {
                    Modifier.border(width = borderWidth, color = borderColor, shape = CircleShape)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        val innerSize = if (borderWidth > 0.dp) size - (borderWidth * 2) - 2.dp else size // Adjust inner size based on border
        Box(
            modifier = Modifier
                .size(innerSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface), // Inner background
            contentAlignment = Alignment.Center
        ) {
            if (avatarUrl != null && !isStoryPlaceholder) {
                Image(
                    painter = painterResource(avatarUrl),
                   // model = avatarUrl,
                    contentDescription = "$name's avatar",
                    //error = painterResource(Res.drawable.story_3),
                    modifier = Modifier
                        .matchParentSize() // Use matchParentSize for the image within the inner box
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else if (isStoryPlaceholder) {
                // Content for story placeholder, e.g., an icon or specific text
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Story",
                    tint = MaterialTheme.colorScheme.primary, // Icon color for placeholder
                    modifier = Modifier.size(size * 0.5f) // Adjust icon size
                )
            }
            else {
                Image(
                    painter = painterResource(Res.drawable.story_3),
                    contentDescription = "$name's avator",
                    modifier = Modifier
                        .matchParentSize() // Use matchParentSize for the image within the inner box
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                /*Text(
                    text = initials,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = (size.value / 2.8f).sp, // Consider making font size relative to innerSize
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )*/
            }
        }
    }
}
    