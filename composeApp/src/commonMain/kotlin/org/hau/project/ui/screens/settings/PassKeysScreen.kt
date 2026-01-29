package org.hau.project.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.hau.project.ui.components.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A screen introducing "Passkeys" as a secure and modern alternative to traditional
 * passwords.
 *
 * It educates users on the benefits of passkeys, such as biometric login (face/fingerprint)
 * and safe storage in password managers, and provides a prominent action to create one.
 *
 * @param onBack Callback invoked when the user navigates back.
 */
@Composable
fun PasskeysScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
           SettingsTopAppBar(
                "Passkeys",
                onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Create passkey", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Passkey Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(100.dp)
                // In a real implementation, you would overlay the key icon here
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Log in securely and protect your account",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(32.dp))

            PasskeyBenefitItem(
                Icons.Default.CheckCircle,
                "Create a passkey for a secure, easy way to log into your account."
            )
            PasskeyBenefitItem(
                Icons.Default.Fingerprint,
                "Log into Verdant with your face, fingerprint or screen lock."
            )
            PasskeyBenefitItem(
                Icons.Default.Key,
                "Your passkey is stored safely in your password manager."
            )
        }
    }
}

/**
 * A reusable row for displaying a benefit or feature of passkeys with an icon.
 */
@Composable
private fun PasskeyBenefitItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(name = "Passkeys (Sky Dark)", showBackground = true)
@Composable
private fun PasskeysScreenDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        PasskeysScreen {}
    }
}

@Preview(name = "Passkeys (Sky Light)", showBackground = true)
@Composable
private fun PasskeysScreenLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        PasskeysScreen {}
    }
}
