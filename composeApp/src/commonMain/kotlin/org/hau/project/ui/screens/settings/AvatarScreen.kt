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
import org.hau.project.ui.components.SettingsTopAppBar
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A screen that introduces the "Avatars" feature, allowing users to create
 * personalized digital representations of themselves.
 *
 * It features a hero image, a descriptive title, and an action button to initiate
 * the avatar creation process.
 *
 * @param onBack Callback invoked when the user navigates back.
 */
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
                    onClick = { /* TODO: Launch Avatar Creator */ },
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
                contentDescription = "Avatar Showcase",
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


@Preview(name = "Avatar Screen (Sky Dark)", showBackground = true)
@Composable
private fun AvatarScreenDarkPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        AvatarScreen {}
    }
}

@Preview(name = "Avatar Screen (Sky Light)", showBackground = true)
@Composable
private fun AvatarScreenLightPreview() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        AvatarScreen {}
    }
}
