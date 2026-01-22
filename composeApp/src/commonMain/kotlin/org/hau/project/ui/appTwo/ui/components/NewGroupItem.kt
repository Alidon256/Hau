package org.hau.project.ui.appTwo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.hau.project.ui.appTwo.ui.screens.chats.NewContactInGroup
import org.jetbrains.compose.resources.painterResource

@Composable
fun NewContactsGroupItem(
    contacts: NewContactInGroup
){
    var checked by remember{ mutableStateOf(false)}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(contacts.contactRes),
            contentDescription = contacts.contactName,
            modifier = Modifier.clip(CircleShape).size(60.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = if (contacts.contactName.isNullOrEmpty()) contacts.contact else contacts.contactName,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row {
                Text(
                    text = contacts.contactDesc,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.titleMedium

                )
            }
        }
        if (contacts.isChecked){
            Checkbox(
                checked = true,
                onCheckedChange = {checked = it},
                modifier = Modifier
                    .clip(CircleShape),
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
        }else{
            Checkbox(
                checked = checked,
                onCheckedChange = {checked = it},
                modifier = Modifier
                    .clip(CircleShape),
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
        }

    }
}

