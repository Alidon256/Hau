package org.hau.project.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.avator
import org.hau.project.ui.appTwo.ui.screens.settings.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AvatarScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            SettingsTopAppBar(
                "Avatar",
                onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Create your Avatar", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.avator),
                contentDescription = "Avatars",
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Say more with Avatars now on Verdant",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Learn more",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {}
            )
        }
    }
}


@Preview(name = "Avatar Screen (Dark)", showBackground = true)
@Composable
private fun AvatarScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        AvatarScreen {}
    }
}

@Preview(name = "Avatar Screen (Light)", showBackground = true)
@Composable
private fun AvatarScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        AvatarScreen {}
    }
}
