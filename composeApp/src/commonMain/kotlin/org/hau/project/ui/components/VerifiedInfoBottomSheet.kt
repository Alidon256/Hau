package org.hau.project.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A bottom sheet component that provides information about verified channels or profiles.
 *
 * This UI informs the user why a specific entity has the verified badge, typically
 * associated with notable status in categories like news, government, or entertainment.
 *
 * @param onClose Callback function invoked when the "Done" button is clicked to dismiss the sheet.
 */
@Composable
fun VerifiedInfoBottomSheet(onClose: () -> Unit) {
    Column(
        modifier = Modifier.navigationBarsPadding().padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Filled.Verified,
            contentDescription = "Verified Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "Verified Channel",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "This channel is verified because it's notable in government, news, entertainment, or another designated category.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        TextButton(onClick = { /*TODO: Link to learn more*/ }) {
            Text("Learn More")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onClose, modifier = Modifier.fillMaxWidth()) {
            Text("Done")
        }
    }
}

@Preview
@Composable
fun VerifiedInfoBottomSheetLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            VerifiedInfoBottomSheet(onClose = {})
        }
    }
}

@Preview
@Composable
fun VerifiedInfoBottomSheetDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            VerifiedInfoBottomSheet(onClose = {})
        }
    }
}
