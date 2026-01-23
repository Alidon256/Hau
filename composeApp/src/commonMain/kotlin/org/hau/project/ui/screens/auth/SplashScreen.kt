package org.hau.project.ui.screens.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.ic_launcher_playstore
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "Splash Alpha"
    )

    // This effect triggers the animation and the callback
    LaunchedEffect(Unit) {
        startAnimation = true
        // Allow time for the animation to play before finishing.
        kotlinx.coroutines.delay(2500)
        onAnimationFinished()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Use a Column to position the indicator below the logo
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Your Logo
            Image(
                painter = painterResource(Res.drawable.ic_launcher_playstore),
                contentDescription = "Hau App Logo",
                modifier = Modifier
                    .size(160.dp)
                    .alpha(alphaAnimation.value)
            )

            // The Pulsing Loading Indicator
            PulsingLoadingIndicator(
                modifier = Modifier
                    .size(80.dp) // A smaller size suitable for being under the logo
                    .alpha(alphaAnimation.value)
            )
        }
    }
}


/**
 * A sleek, futuristic loading indicator featuring a central dot with three
 * orbiting ripple rings that animate outward in smooth, rhythmic pulses.
 */
@Composable
private fun PulsingLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    centralDotSize: Float = 12f
) {
    val transition = rememberInfiniteTransition(label = "Ripple Transition")
    val ripples = 3

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Render each ripple with a different animation delay
        for (i in 0 until ripples) {
            Ripple(
                transition = transition,
                color = color,
                delay = i * 500 // Stagger the start of each ripple's animation
            )
        }

        // Central Dot
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = color,
                radius = centralDotSize / 2
            )
        }
    }
}

@Composable
private fun Ripple(
    transition: InfiniteTransition,
    color: Color,
    delay: Int
) {
    val animationValue by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Ripple Animation"
    )

    // Delay the effective animation value for each ripple
    val effectiveValue = (animationValue - (delay / 2000f)).let {
        if (it < 0) it + 1 else it
    }

    val easedValue = FastOutSlowInEasing.transform(effectiveValue)
    val animatedRadius = easedValue * 40f // Max radius based on the smaller size
    val animatedStrokeWidth = (1f - easedValue) * 6f // Stroke gets thinner as it expands
    val animatedAlpha = (1f - easedValue) * 0.8f // Ripple fades out as it expands

    val brush = Brush.radialGradient(
        colors = listOf(color.copy(alpha = 0f), color.copy(alpha = animatedAlpha))
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            brush = brush,
            radius = animatedRadius,
            style = Stroke(width = animatedStrokeWidth)
        )
    }
}


// --- PREVIEWS ---

@Preview(name = "Splash Screen - Light", showBackground = true)
@Composable
private fun SplashScreenPreviewLight() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = false) {
        SplashScreen(onAnimationFinished = {})
    }
}

@Preview(name = "Splash Screen - Dark", showBackground = true)
@Composable
private fun SplashScreenPreviewDark() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) {
        SplashScreen(onAnimationFinished = {})
    }
}
