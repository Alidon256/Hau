package org.hau.project.ui.components

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hau.project.models.FabAction
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * An animated, speed-dial style Floating Action Button (FAB).
 *
 * When clicked, the main button rotates and expands vertically to reveal a list
 * of sub-actions. This is ideal for screens requiring multiple related actions
 * (e.g., attaching different media types) without cluttering the UI.
 *
 * @param modifier The modifier to be applied to the root container.
 * @param onStateChange Callback invoked when the expansion state changes.
 * Useful for blurring backgrounds or dimming the main content.
 */
@Composable
fun MultiActionFloatingButton(
    modifier: Modifier = Modifier,
    onStateChange: (isExpanded: Boolean) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Configuration for the sub-actions shown upon expansion.
    val actions = listOf(
        FabAction(Icons.Default.Photo, "Gallery", Color(0xFF8E44AD), {}),
        FabAction(Icons.Default.CameraAlt, "Camera", Color(0xFFE74C3C), {}),
        FabAction(Icons.Default.Headphones, "Audio", Color(0xFFF39C12), {}),
        FabAction(Icons.Default.Description, "Document", Color(0xFF2980B9), {}),
        FabAction(Icons.Default.Person, "Contact", Color(0xFF2ECC71), {})
    )

    // Smooth rotation animation for the primary FAB icon (+ to X).
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 45f else 0f,
        animationSpec = tween(durationMillis = 200)
    )

    LaunchedEffect(isExpanded) {
        onStateChange(isExpanded)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Revealed list of sub-actions with fade and vertical expansion.
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

        // The primary trigger FAB.
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
                contentDescription = "Toggle Actions",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

/**
 * A horizontal row representing a single sub-action in the FAB speed-dial.
 *
 * @param action The data model containing the icon, label, and click handler.
 */
@Composable
private fun ActionItem(action: FabAction) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Action Label with elevated background for readability.
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

        // Circular Action Button.
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

@Preview
@Composable
fun MultiActionFloatingButtonLightPreview() {
    AppTheme(useDarkTheme = false) {
        Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.BottomEnd) {
            MultiActionFloatingButton()
        }
    }
}

@Preview
@Composable
fun MultiActionFloatingButtonDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.BottomEnd) {
            MultiActionFloatingButton()
        }
    }
}