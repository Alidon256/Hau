package org.hau.project.ui.appTwo.ui.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.image_now
import hau.composeapp.generated.resources.image_two
import hau.composeapp.generated.resources.story_1
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import hau.composeapp.generated.resources.story_4
import org.hau.project.ui.appTwo.ui.components.SearchBar
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NewGroupScreen(){
    var searchQuery by remember{ mutableStateOf("") }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {},
                isExpanded = false,
                onToggleExpanded = {},
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                placeholderText = "Search name or contact..."
            )
        },
        containerColor = Color.White
    ){paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ){
            item {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        text = "Frequently contacted",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Black.copy(alpha = 0.6f)
                        )
                    )
                }
            }
            items(getFrequentContacts()){frequentlyContacted->
                NewContactsGroupItem(frequentlyContacted)
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        text = "Contacts on Hau",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Black.copy(alpha = 0.6f)
                        )
                    )
                }
            }
            items(getFrequentContacts()){frequentlyContacted->
                NewContactsGroupItem(frequentlyContacted)
            }
        }

    }
}

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

@Composable
@Preview(showBackground = true)
fun NewContactsGroupItem(){
    NewContactsGroupItem(
        NewContactInGroup(contactName = "Mugumya Ali", contactRes = Res.drawable.story_3, contactId = "hdfddhdf", contactDesc = "Message yourself", contact = "+256 785330244",isChecked = true))
}


@Composable
@Preview
fun NewGroupScreenPreview(){
    NewGroupScreen()
}
data class NewContactInGroup(
    val contactRes: DrawableResource,
    val contactName: String?,
    val contactDesc: String,
    val contact: String,
    val contactId: String,
    val isChecked: Boolean
)
fun getFrequentContacts() = listOf(
    NewContactInGroup(contactName = "Mugumya Ali", contactRes = Res.drawable.story_3, contactId = "hdfddhdf", contactDesc = "Message yourself", contact = "+256 785330244",isChecked = false),
    NewContactInGroup(contactName = "Really Rinah", contactRes = Res.drawable.story_2,contactId = "hdfdkhdf", contactDesc = "Just always keep moving",contact = "+256 755935663",isChecked = false),
    NewContactInGroup(contactName = null, contactRes = Res.drawable.story_1,contactId = "hdfdlhdf", contactDesc = "All yours 🥰",contact = "+256 784337244",isChecked = true),
    NewContactInGroup(contactName = "Caroline Varsaha", contactRes = Res.drawable.story_4, contactId = "hdfgdhdf", contactDesc = "Keep going!",contact = "+253 785000244",isChecked = false),
    NewContactInGroup(contactName = null, contactRes = Res.drawable.image_now, contactId = "hdfdshdf", contactDesc = "When its just meant for you",contact = "+111 744430244",isChecked = true),
    NewContactInGroup(contactName = "Caroline Varsaha", contactRes = Res.drawable.image_two, contactId = "hdfbdhdf", contactDesc = "Staying just outside the baber shop sooner or later you'll get your hair cut!",contact = "+234 777730244",isChecked = false),
)