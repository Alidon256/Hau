package org.hau.project.ui.screens.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.image_now
import hau.composeapp.generated.resources.image_two
import hau.composeapp.generated.resources.story_1
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import hau.composeapp.generated.resources.story_4
import org.hau.project.ui.components.NewContactsGroupItem
import org.hau.project.ui.components.SearchBar
import org.jetbrains.compose.resources.DrawableResource
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
            items(getFrequentContacts()){ frequentlyContacted->
                NewContactsGroupItem(
                    frequentlyContacted
                )
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
            items(getFrequentContacts()){ frequentlyContacted->
                NewContactsGroupItem(
                    frequentlyContacted
                )
            }
        }

    }
}


@Composable
@Preview(showBackground = true)
fun NewContactsGroupItem(){
    NewContactsGroupItem(
        NewContactInGroup(
            contactName = "Mugumya Ali",
            contactRes = Res.drawable.story_3,
            contactId = "hdfddhdf",
            contactDesc = "Message yourself",
            contact = "+256 785330244",
            isChecked = true
        )
    )
}


@Composable
@Preview
fun NewGroupScreenPreview(){
    _root_ide_package_.org.hau.project.ui.screens.chats.NewGroupScreen()
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
    _root_ide_package_.org.hau.project.ui.screens.chats.NewContactInGroup(
        contactName = "Mugumya Ali",
        contactRes = Res.drawable.story_3,
        contactId = "hdfddhdf",
        contactDesc = "Message yourself",
        contact = "+256 785330244",
        isChecked = false
    ),
    _root_ide_package_.org.hau.project.ui.screens.chats.NewContactInGroup(
        contactName = "Really Rinah",
        contactRes = Res.drawable.story_2,
        contactId = "hdfdkhdf",
        contactDesc = "Just always keep moving",
        contact = "+256 755935663",
        isChecked = false
    ),
    _root_ide_package_.org.hau.project.ui.screens.chats.NewContactInGroup(
        contactName = null,
        contactRes = Res.drawable.story_1,
        contactId = "hdfdlhdf",
        contactDesc = "All yours 🥰",
        contact = "+256 784337244",
        isChecked = true
    ),
    _root_ide_package_.org.hau.project.ui.screens.chats.NewContactInGroup(
        contactName = "Caroline Varsaha",
        contactRes = Res.drawable.story_4,
        contactId = "hdfgdhdf",
        contactDesc = "Keep going!",
        contact = "+253 785000244",
        isChecked = false
    ),
    _root_ide_package_.org.hau.project.ui.screens.chats.NewContactInGroup(
        contactName = null,
        contactRes = Res.drawable.image_now,
        contactId = "hdfdshdf",
        contactDesc = "When its just meant for you",
        contact = "+111 744430244",
        isChecked = true
    ),
    _root_ide_package_.org.hau.project.ui.screens.chats.NewContactInGroup(
        contactName = "Caroline Varsaha",
        contactRes = Res.drawable.image_two,
        contactId = "hdfbdhdf",
        contactDesc = "Staying just outside the baber shop sooner or later you'll get your hair cut!",
        contact = "+234 777730244",
        isChecked = false
    ),
)