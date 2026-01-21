package org.hau.project.ui.appTwo.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hau.project.ui.appTwo.domain.models.FabAction


/**
 * An animated, multi-action Floating Action Button (FAB) that expands
 * to show several sub-actions in a speed-dial fashion.
 *
 * @param onStateChange Callback for when the button is expanded or collapsed.
 */
@Composable
fun MultiActionFloatingButton(
    modifier: Modifier = Modifier,
    onStateChange: (isExpanded: Boolean) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Define the list of actions
    val actions = listOf(
        FabAction(Icons.Default.Photo, "Gallery", Color(0xFF8E44AD), {}),
        FabAction(Icons.Default.CameraAlt, "Camera", Color(0xFFE74C3C), {}),
        FabAction(Icons.Default.Headphones, "Audio", Color(0xFFF39C12), {}),
        FabAction(Icons.Default.Description, "Document", Color(0xFF2980B9), {}),
        FabAction(Icons.Default.Person, "Contact", Color(0xFF2ECC71), {})
    )

    // Animate the rotation of the main FAB icon (+ to x)
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 45f else 0f,
        animationSpec = tween(durationMillis = 200)
    )

    // When the state changes, invoke the callback
    LaunchedEffect(isExpanded) {
        onStateChange(isExpanded)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // The expanding list of actions
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                actions.forEach { action ->
                    ActionItem(action = action)
                }
            }
        }

        // The main FAB that controls the expansion
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { isExpanded = !isExpanded },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Attachment",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
private fun ActionItem(action: FabAction) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Label with a semi-transparent background
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = action.label,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Circular action button
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(action.backgroundColor)
                .clickable(onClick = action.onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.label,
                tint = Color.White
            )
        }
    }
}
