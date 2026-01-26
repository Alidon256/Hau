package org.hau.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Poll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun AttachmentDropdownMenu(expanded: Boolean, onDismiss: () -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        offset = DpOffset(0.dp, (-280).dp), // Position it upwards from the icon
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .clip(RoundedCornerShape(20.dp))
    ) {
        DropdownMenuItem(
            text = { Text("Document") },
            leadingIcon = { Icon(Icons.Outlined.Description, null, tint = Color(0xFF7F66FF)) },
            onClick = onDismiss
        )
        DropdownMenuItem(
            text = { Text("Photos & Videos") },
            leadingIcon = { Icon(Icons.Outlined.PhotoLibrary, null, tint = Color(0xFF007AFF)) },
            onClick = onDismiss
        )
        DropdownMenuItem(
            text = { Text("Camera") },
            leadingIcon = { Icon(Icons.Outlined.PhotoCamera, null, tint = Color(0xFFFF2D55)) },
            onClick = onDismiss
        )
        DropdownMenuItem(
            text = { Text("Contact") },
            leadingIcon = { Icon(Icons.Outlined.Person, null, tint = Color(0xFF007AFF)) },
            onClick = onDismiss
        )
        DropdownMenuItem(
            text = { Text("Poll") },
            leadingIcon = { Icon(Icons.Outlined.Poll, null, tint = Color(0xFFFFBC38)) },
            onClick = onDismiss
        )
    }
}
