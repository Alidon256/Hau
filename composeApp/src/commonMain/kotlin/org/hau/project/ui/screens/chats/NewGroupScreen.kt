package org.hau.project.ui.screens.chats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.*
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme // <-- CORRECTED: Use SocialTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// --- STATE MANAGEMENT & DATA MODELS ---

data class SelectableContact(
    val id: String,
    val resource: DrawableResource,
    val name: String,
    val description: String,
    val isSelected: Boolean = false
)

sealed interface NewGroupAction {
    data class ToggleContact(val contactId: String) : NewGroupAction
}

@Composable
fun NewGroupScreen(
    onBack: () -> Unit ,
    onContinue: (List<SelectableContact>) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var contacts by remember { mutableStateOf(getFakeContacts()) }

    val selectedContacts = remember(contacts) {
        contacts.filter { it.isSelected }
    }

    val onAction: (NewGroupAction) -> Unit = { action ->
        when (action) {
            is NewGroupAction.ToggleContact -> {
                contacts = contacts.map {
                    if (it.id == action.contactId) {
                        it.copy(isSelected = !it.isSelected)
                    } else {
                        it
                    }
                }
            }
        }
    }
    val filteredContacts = remember(searchQuery, contacts) {
        if (searchQuery.isBlank()) {
            contacts
        } else {
            contacts.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            NewGroupTopAppBar(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                onBack = onBack,
                selectedContactCount = selectedContacts.size
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = selectedContacts.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = { onContinue(selectedContacts) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    // Use Material Icon
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Continue")
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // --- SELECTED CONTACTS ROW ---
            AnimatedVisibility(visible = selectedContacts.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(selectedContacts, key = { it.id }) { contact ->
                        SelectedContactChip(
                            contact = contact,
                            onRemove = { onAction(NewGroupAction.ToggleContact(contact.id)) }
                        )
                    }
                }
            }

            // --- CONTACT LIST ---
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    ListHeader("Contacts on Hau")
                }
                items(filteredContacts, key = { it.id }) { contact ->
                    ContactSelectItem(
                        contact = contact,
                        onToggle = { onAction(NewGroupAction.ToggleContact(contact.id)) }
                    )
                }
            }
        }
    }
}


// --- UI SUB-COMPONENTS ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewGroupTopAppBar(
    searchQuery: String,
    onQueryChange: (String)-> Unit,
    onBack: () -> Unit,
    selectedContactCount: Int
) {
    TopAppBar(
        title = {
            Column {
                Text("New group", fontWeight = FontWeight.SemiBold)
                if (selectedContactCount > 0) {
                    Text(
                        "$selectedContactCount of ${getFakeContacts().size} selected",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                // Use Material Icon
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    )
}

@Composable
private fun SelectedContactChip(contact: SelectableContact, onRemove: () -> Unit) {
    Box(contentAlignment = Alignment.TopEnd) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(contact.resource),
                contentDescription = contact.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = contact.name.split(" ").first(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.Filled.Cancel, // Use Material Icon
            contentDescription = "Remove ${contact.name}",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .clickable(onClick = onRemove)
                .padding(2.dp)
        )
    }
}

@Composable
private fun ContactSelectItem(contact: SelectableContact, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(contact.resource),
                contentDescription = contact.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            if (contact.isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle, // Use Material Icon
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(contact.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(contact.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ListHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}


// --- PREVIEWS ---

@Preview(name = "New Group - Light (Verdant)", showBackground = true)
@Composable
private fun NewGroupScreenPreviewLight() {
    AppTheme(theme = SocialTheme.Verdant, useDarkTheme = false) { // Use SocialTheme
        Surface {
            NewGroupScreen(
                onBack = {},
                onContinue = {}
            )
        }
    }
}

@Preview(name = "New Group - Dark (Sky)", showBackground = true)
@Composable
private fun NewGroupScreenPreviewDark() {
    AppTheme(theme = SocialTheme.Sky, useDarkTheme = true) { // Use SocialTheme
        Surface {
            NewGroupScreen(
                onBack = {},
                onContinue = {}
            )
        }
    }
}

@Preview(name = "Selected Chip", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SelectedContactChipPreview() {
    AppTheme {
        SelectedContactChip(
            contact = getFakeContacts().first(),
            onRemove = {}
        )
    }
}

@Preview(name = "Contact Item - Selected", showBackground = true)
@Composable
private fun ContactSelectItemPreviewSelected() {
    AppTheme {
        Surface {
            // Create a fake state where one contact is selected to preview the UI
            val contacts = remember { mutableStateOf(getFakeContacts()) }
            val selectedContact = contacts.value.first().copy(isSelected = true)
            ContactSelectItem(
                contact = selectedContact,
                onToggle = {}
            )
        }
    }
}

@Preview(name = "Contact Item - Unselected", showBackground = true)
@Composable
private fun ContactSelectItemPreviewUnselected() {
    AppTheme {
        Surface {
            ContactSelectItem(
                contact = getFakeContacts().last(),
                onToggle = {}
            )
        }
    }
}


// --- FAKE DATA FOR PREVIEW & DEVELOPMENT ---

private fun getFakeContacts() = listOf(
    SelectableContact(
        id = "1", name = "Mugumya Ali", resource = Res.drawable.story_3,
        description = "Message yourself"
    ),
    SelectableContact(
        id = "2", name = "Really Rinah", resource = Res.drawable.story_2,
        description = "Just always keep moving"
    ),
    SelectableContact(
        id = "3", name = "Angella", resource = Res.drawable.story_1,
        description = "All yours 🥰"
    ),
    SelectableContact(
        id = "4", name = "Caroline Varsaha", resource = Res.drawable.story_4,
        description = "Keep going!"
    ),
    SelectableContact(
        id = "5", name = "Brenda", resource = Res.drawable.image_now,
        description = "When its just meant for you"
    ),
    SelectableContact(
        id = "6", name = "David Tech", resource = Res.drawable.image_two,
        description = "Building the future, one line at a time."
    ),
)
