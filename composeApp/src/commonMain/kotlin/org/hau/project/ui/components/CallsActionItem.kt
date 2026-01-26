package org.hau.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import org.hau.project.models.CallActions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CallsActionItem(callItem: CallActions) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant), // Use theme color
            contentAlignment = Alignment.Center
        ) {
            if (callItem.isCommunity) {
                Image(
                    painter = painterResource(Res.drawable.grattitude),
                    contentDescription = "Community",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    callItem.icon,
                    contentDescription = callItem.actionText,
                    tint = MaterialTheme.colorScheme.primary // Use primary color for icons
                )
            }
        }
        Text(
            text = callItem.actionText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Use theme color
        )
    }
}


@Preview(name = "Call Action Item")
@Composable
fun CallActionsPreview() {
    // AppTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
        CallsActionItem(
            CallActions(
                Icons.Outlined.Call,
                "Call Link",
                isCommunity = false
            )
        )
    }
    // }
}
