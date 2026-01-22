package org.hau.project.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// 1. Define Color Palettes
private val WhatsappLightPalette = lightColorScheme(
    primary = WhatsappGreen,
    secondary = WhatsappLightGreen,
    background = WhatsappLight,
    surface = WhatsappLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = WhatsappOnLight,
    onSurface = WhatsappOnLight
)

private val WhatsappDarkPalette = darkColorScheme(
    primary = WhatsappLightGreen,
    secondary = WhatsappGreen,
    background = WhatsappDark,
    surface = WhatsappDarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = WhatsappOnDark,
    onSurface = WhatsappOnDark
)

private val TwitterLightPalette = lightColorScheme(
    primary = TwitterBlue,
    secondary = TwitterDarkGray,
    background = TwitterLightSurface,
    surface = TwitterWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TwitterBlack,
    onSurface = TwitterBlack
)

private val TwitterDarkPalette = darkColorScheme(
    primary = TwitterBlue,
    secondary = TwitterLightGray,
    background = TwitterBlack,
    surface = Color(0xFF1A1A1A),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = TwitterWhite,
    onSurface = TwitterWhite
)

private val InstagramLightPalette = lightColorScheme(
    primary = InstagramPurple,
    secondary = InstagramPink,
    background = InstagramLight,
    surface = InstagramLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val InstagramDarkPalette = darkColorScheme(
    primary = InstagramPink,
    secondary = InstagramOrange,
    background = InstagramDark,
    surface = InstagramDarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val SnapchatLightPalette = lightColorScheme(
    primary = SnapchatYellow,
    secondary = SnapchatBlue,
    background = SnapchatWhite,
    surface = SnapchatWhite,
    onPrimary = SnapchatBlack,
    onSecondary = Color.White,
    onBackground = SnapchatBlack,
    onSurface = SnapchatBlack
)

private val SnapchatDarkPalette = darkColorScheme(
    primary = SnapchatYellow,
    secondary = SnapchatBlue,
    background = SnapchatBlack,
    surface = SnapchatDarkSurface,
    onPrimary = SnapchatBlack,
    onSecondary = Color.White,
    onBackground = SnapchatWhite,
    onSurface = SnapchatWhite
)

// 2. Enum to represent the Social Themes
enum class SocialTheme {
    WhatsApp, Twitter, Instagram, Snapchat
}

// 3. CompositionLocal to provide the current theme
val LocalSocialTheme = staticCompositionLocalOf { SocialTheme.WhatsApp }

// 4. The Main Custom Theme Composable
@Composable
fun AppTheme(
    theme: SocialTheme = LocalSocialTheme.current,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        SocialTheme.WhatsApp -> if (useDarkTheme) WhatsappDarkPalette else WhatsappLightPalette
        SocialTheme.Twitter -> if (useDarkTheme) TwitterDarkPalette else TwitterLightPalette
        SocialTheme.Instagram -> if (useDarkTheme) InstagramDarkPalette else InstagramLightPalette
        SocialTheme.Snapchat -> if (useDarkTheme) SnapchatDarkPalette else _root_ide_package_.org.hau.project.ui.theme.SnapchatLightPalette
    }

    SystemAppearance(useDarkTheme)

    CompositionLocalProvider(LocalSocialTheme provides theme) {
        MaterialTheme(
            colorScheme = colors,
            // You can also define typography and shapes here
            content = content
        )
    }
}
