package org.hau.project.ui.appTwo.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.hau.project.ui.appTwo.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountScreen(onBack: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }

    Scaffold(
        topBar = { SettingsTopAppBar("Delete this account", onBack) },
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete account", color = MaterialTheme.colorScheme.onError)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Warning, contentDescription = "Warning", tint = MaterialTheme.colorScheme.error)
                Text(
                    "If you delete this account:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(16.dp))
            Column(modifier = Modifier.padding(start = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("• The account will be deleted from WhatsApp and all your devices", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• Your message history will be erased", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• You will be removed from all your WhatsApp groups", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• Your Google storage backup will be deleted", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• Any channels you created will be deleted", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.height(32.dp))

            Text("Change number instead?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Change number")
            }

            Spacer(Modifier.height(32.dp))
            Text("To delete your account, confirm your country code and enter your phone number.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = "Uganda",
                onValueChange = {},
                label = { Text("Country") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, "Select Country") }
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                OutlinedTextField(
                    value = "+256",
                    onValueChange = {},
                    modifier = Modifier
                        .width(100.dp)
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone number") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Preview(name = "Delete Account (Dark)", showBackground = true)
@Composable
private fun DeleteAccountScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        DeleteAccountScreen {}
    }
}

@Preview(name = "Delete Account (Light)", showBackground = true)
@Composable
private fun DeleteAccountScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        DeleteAccountScreen {}
    }
}
