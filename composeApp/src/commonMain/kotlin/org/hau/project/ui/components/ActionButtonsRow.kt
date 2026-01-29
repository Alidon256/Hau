package org.hau.project.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A horizontal row of primary action buttons typically used in Profile or Channel headers.
 *
 * This component provides two equally weighted actions:
 * 1. A filled [Button] indicating a primary state (e.g., "Following").
 * 2. An [OutlinedButton] for secondary actions (e.g., "Share").
 *
 * The buttons automatically expand to fill the available width while maintaining
 * consistent spacing and alignment.
 */
@Composable
fun ActionButtonsRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        // Primary action: Following status
        Button(
            onClick = { /* TODO: Handle follow/unfollow toggle */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Following",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Following")
        }

        // Secondary action: Share channel/profile
        OutlinedButton(
            onClick = { /* TODO: Handle share action */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Share")
        }
    }
}

@Preview
@Composable
fun ActionButtonsRowLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            ActionButtonsRow()
        }
    }
}

@Preview
@Composable
fun ActionButtonsRowDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            ActionButtonsRow()
        }
    }
}