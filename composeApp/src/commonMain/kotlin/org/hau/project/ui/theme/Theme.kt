package org.hau.project.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// 1. Define Color Palettes
private val VerdantLightPalette = lightColorScheme(
    primary = VerdantGreen,
    secondary = CrispLime,
    background = PureWhite,
    surface = PureWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextOnLight,
    onSurface = TextOnLight
)

private val VerdantDarkPalette = darkColorScheme(
    primary = CrispLime,
    secondary = VerdantGreen,
    background = RichBlack,
    surface = DeepCharcoal,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = TextOnDark,
    onSurface = TextOnDark
)

private val SkyLightPalette = lightColorScheme(
    primary = SkyBlue,
    secondary = SlateGray,
    background = LightSurface,
    surface = BrightWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = NearBlack,
    onSurface = NearBlack
)

private val SkyDarkPalette = darkColorScheme(
    primary = SkyBlue,
    secondary = StoneGray,
    background = NearBlack,
    surface = Color(0xFF1A1A1A),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = BrightWhite,
    onSurface = BrightWhite
)

private val TwilightLightPalette = lightColorScheme(
    primary = TwilightPurple,
    secondary = SunsetPink,
    background = SoftWhite,
    surface = SoftWhite,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val TwilightDarkPalette = darkColorScheme(
    primary = SunsetPink,
    secondary = VibrantOrange,
    background = MidnightBlack,
    surface = TwilightSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val ElectricLightPalette = lightColorScheme(
    primary = ElectricYellow,
    secondary = ElectricBlue,
    background = StarkWhite,
    surface = StarkWhite,
    onPrimary = TrueBlack,
    onSecondary = Color.White,
    onBackground = TrueBlack,
    onSurface = TrueBlack
)

private val ElectricDarkPalette = darkColorScheme(
    primary = ElectricYellow,
    secondary = ElectricBlue,
    background = TrueBlack,
    surface = GraphiteSurface,
    onPrimary = TrueBlack,
    onSecondary = Color.White,
    onBackground = StarkWhite,
    onSurface = StarkWhite
)

/**
 * Enum representing the available themes in the application.
 * Each theme corresponds to a distinct color palette.
 */
enum class SocialTheme {
    Verdant, Sky, Twilight, Electric
}

/**
 * A `CompositionLocal` that provides the currently selected [SocialTheme].
 * This allows descendent Composables to easily access and react to the current theme.
 */
val LocalSocialTheme = staticCompositionLocalOf { SocialTheme.Verdant }

/**
 * The main theme composable for the application.
 *
 * This function applies a [MaterialTheme] based on the selected [SocialTheme] and system settings (dark/light mode).
 * It wraps the provided content in a [CompositionLocalProvider] to make the current theme available to all child composables.
 *
 * @param theme The desired [SocialTheme] to apply. Defaults to the value provided by [LocalSocialTheme].
 * @param useDarkTheme Whether to use the dark color palette. Defaults to the system setting.
 * @param content The composable content to be themed.
 */
@Composable
fun AppTheme(
    theme: SocialTheme = LocalSocialTheme.current,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        SocialTheme.Verdant -> if (useDarkTheme) VerdantDarkPalette else VerdantLightPalette
        SocialTheme.Sky -> if (useDarkTheme) SkyDarkPalette else SkyLightPalette
        SocialTheme.Twilight -> if (useDarkTheme) TwilightDarkPalette else TwilightLightPalette
        SocialTheme.Electric -> if (useDarkTheme) ElectricDarkPalette else _root_ide_package_.org.hau.project.ui.theme.ElectricLightPalette
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
