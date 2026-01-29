package org.hau.project.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A standard disclosure bottom sheet used to explain privacy policies.
 *
 * Specifically designed to inform users about the visibility of their phone numbers
 * and profiles within Channels or Groups.
 *
 * @param onClose Callback triggered when the "OK" confirmation button is pressed.
 */
@Composable
fun PrivacyInfoBottomSheet(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sheet Header
        Text(
            text = "Profile & Phone Number Privacy",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        // Privacy Detail Text
        Text(
            text = "Admins can't see your full phone number unless they've saved you as a contact. Other followers can't see your profile at all.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        // Confirmation Button
        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("OK")
        }
    }
}

@Preview
@Composable
fun PrivacyInfoBottomSheetLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            PrivacyInfoBottomSheet(onClose = {})
        }
    }
}

@Preview
@Composable
fun PrivacyInfoBottomSheetDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            PrivacyInfoBottomSheet(onClose = {})
        }
    }
}