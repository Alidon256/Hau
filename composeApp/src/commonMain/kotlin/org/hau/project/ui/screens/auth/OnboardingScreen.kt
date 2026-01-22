package org.hau.project.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.copy_two
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OnboardingUsage(){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ){paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier
                    .weight(0.7f),
                painter = painterResource(Res.drawable.copy_two),
                contentDescription = "Image Background",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
                    .offset(y= (-20).dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(34.dp,34.dp)
                    )
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Enjoy the new experience of \n chatting with global friends",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Connect the people around the world for free",
                        fontWeight = FontWeight.Normal,
                        color = Color.Black.copy(alpha = 0.4f),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ElevatedButton(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(85.dp)
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF703EFF)
                        )
                    ){
                        Text(
                            text = "Get Started",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                      Text(
                          text = "Powered by",
                          color = Color.Black
                      )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Outlined.Explore,
                            contentDescription = "Usage Icon",
                            tint = Color.Black
                        )
                        Text(
                            text = "Usage",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun OnboardingUsagePreview(){
    _root_ide_package_.org.hau.project.ui.screens.auth.OnboardingUsage()
}
