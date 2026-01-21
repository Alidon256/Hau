package org.hau.project.ui.appOne

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.curve_icon
import hau.composeapp.generated.resources.image_now_now_now

@Composable
fun OnboardingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.image_now_now_now),
            contentDescription = "Onboarding Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.CenterStart
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color.Black.copy(alpha = 0.3f),
                            0.5f to Color.Transparent,
                            0.85f to Color(0xFFADFF2F).copy(alpha = 0.15f),
                            1.0f to Color(0xFFADFF2F).copy(alpha = 0.45f)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(200.dp)
                .graphicsLayer(clip = true)
                .blur(radius = 50.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // Start transparent at the top of the box
                            Color.Transparent, // Start transparent at the top of the box
                            Color.Black.copy(alpha = 0.85f), // End with a subtle, transparent green tint
                            Color.Black.copy(alpha = 0.85f), // End with a subtle, transparent green tint
                        ),
                    startY = 0f,
                    endY = 500f // Extend the gradient to soften the transition
                  )
                )
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // Start transparent at the top of the box
                            Color(0xFFADFF2F).copy(alpha = 0.15f), // End with a subtle, transparent green tint
                        ),
                        startY = 0f,
                        endY = 400f // Extend the gradient to soften the transition
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            ){
                Column {
                    Text(
                        text = "Discover,",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 48.sp,
                    )
                    Text(
                        text = "Share, Follow",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 48.sp,
                    )
                    Text(
                        text = "Your World",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 48.sp,
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Skip",
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            OnboardingSlider(modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* TODO: Handle Get Started click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, )
                    .height(56.dp),
                shape = RoundedCornerShape(50), // Fully rounded corners
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // Container is transparent
                ),
                contentPadding = PaddingValues() // No default padding
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF77A328), Color(0xFFE9D409)) // GreenYellow to LimeGreen
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Get Started",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Get Started",
                            tint = Color.White,
                            modifier = Modifier
                                .rotate(-45f)
                                .size(18.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(  16.dp))
        }
    }
}

@Composable
fun OnboardingSlider(modifier: Modifier = Modifier) {
    Box(modifier = modifier.width(100.dp).height(300.dp)) {
        Image(
            painter = painterResource(Res.drawable.curve_icon),
            contentDescription = null,
            modifier = Modifier.size(400.dp).align(Alignment.TopStart)
        )
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}
