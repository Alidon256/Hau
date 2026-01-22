package org.hau.project.ui.appTwo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.story_3
import org.hau.project.ui.appTwo.ui.screens.chats.NewContactInGroup
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SelectedContactItem(
    selectedContact: NewContactInGroup
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .4f),CircleShape),
            contentAlignment = Alignment.BottomEnd
        ){
            Image(
                painter = painterResource(selectedContact.contactRes),
                contentDescription = "Selected Contact Image",
                modifier = Modifier.clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .background(Color.Gray,CircleShape)
                    .border(1.dp,Color.White,CircleShape)
                    .size(30.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Outlined.Clear,
                    contentDescription = "Clear",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if(selectedContact.contactName.isNullOrEmpty())
                selectedContact.contact
            else
                selectedContact.contactName,
            style = MaterialTheme.typography.titleMedium.copy(
                Color.Black.copy(alpha = 0.6f)
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
@Composable
@Preview(showBackground = true)
fun SelectedContactItemPreview(){
    SelectedContactItem(
        NewContactInGroup(contactName = "Mugumya Ali", contactRes = Res.drawable.story_3, contactId = "hdfddhdf", contactDesc = "Message yourself", contact = "+256 785330244",isChecked = true)
    )
}
