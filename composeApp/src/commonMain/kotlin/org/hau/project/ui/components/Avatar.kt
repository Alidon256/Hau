package org.hau.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * A highly customizable circular avatar component.
 *
 * This component supports:
 * 1.  **Dynamic Sizing**: Easily adjustable via the [size] parameter.
 * 2.  **Status Indicators**: Visual borders to signify active stories or special states.
 * 3.  **Placeholder Logic**: Intelligent fallback to a default avatar or a "Add Story" icon.
 * 4.  **Multi-layer Clipping**: Ensures perfect circularity even with complex internal layouts.
 *
 * @param name The name associated with the avatar (used for accessibility and future initials fallback).
 * @param size The total diameter of the avatar.
 * @param avatarUrl Optional [DrawableResource] for the profile image.
 * @param hasStory Whether to show a themed border indicating an active story.
 * @param isStoryPlaceholder Whether this avatar acts as a button to add a new story.
 * @param modifier Modifier to be applied to the outer container.
 */
@Composable
fun Avatar(
    name: String,
    size: Dp,
    avatarUrl: DrawableResource? = null,
    hasStory: Boolean = false,
    isStoryPlaceholder: Boolean = false,
    modifier: Modifier = Modifier
) {
    // Logic to determine the border visual style.
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
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (borderWidth > 0.dp) {
                    Modifier.border(width = borderWidth, color = borderColor, shape = CircleShape)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        // Adjust the inner content size to account for the border width and a small gap.
        val innerSize = if (borderWidth > 0.dp) size - (borderWidth * 2) - 2.dp else size
        
        Box(
            modifier = Modifier
                .size(innerSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            when {
                avatarUrl != null && !isStoryPlaceholder -> {
                    Image(
                        painter = painterResource(avatarUrl),
                        contentDescription = "$name's avatar",
                        modifier = Modifier.matchParentSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                isStoryPlaceholder -> {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Story",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(size * 0.5f)
                    )
                }
                else -> {
                    // Default fallback image
                    Image(
                        painter = painterResource(Res.drawable.story_3),
                        contentDescription = "Default avatar",
                        modifier = Modifier.matchParentSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
