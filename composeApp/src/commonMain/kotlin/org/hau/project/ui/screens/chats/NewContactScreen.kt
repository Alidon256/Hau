package org.hau.project.ui.screens.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.hau.project.data.repositories.ChatRepository
import org.hau.project.ui.components.ContactActionItem
import org.hau.project.ui.components.NewContactsItem
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.hau.project.viewModels.ChatViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewContactScreen(
    onBack: ()-> Unit,
    viewModel: ChatViewModel,
    onContactClick: (String) -> Unit,
    navController: NavController
){
    val uiState by viewModel.newContactsState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Column {
                        Text(
                            text = "Select Contact",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        // Dynamic contact count
                        val contactCount = uiState.newContacts.size
                        Text(
                            text = "$contactCount Contacts",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}){
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Search Contacts",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More Options",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background // Use theme background
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    // Refactored into a reusable component
                   ContactActionItem(
                        icon = Icons.Outlined.People,
                        title = "New group",
                        onClick = { navController?.navigate(org.hau.project.ui.components.Routes.NEW_GROUPS) }
                    )
                    ContactActionItem(
                        icon = Icons.Outlined.PersonAdd,
                        title = "New contact",
                        onClick = { navController.navigate(org.hau.project.ui.components.Routes.NEW_GROUPS) },
                        trailingContent = {
                            Icon(
                                Icons.Outlined.QrCode,
                                contentDescription = "Scan QR Code",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                    ContactActionItem(
                        icon = Icons.Outlined.GroupAdd,
                        title = "New community",
                        onClick = { navController?.navigate(org.hau.project.ui.components.Routes.NEW_GROUPS) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                }
                item {
                    Text(
                        text = "Contacts on Hau",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp
                        )
                    )
                }
                items(uiState.newContacts) { contact ->
                    Box(modifier = Modifier.clickable {
                        onContactClick(contact.id)
                    }){
                        NewContactsItem(contact)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun NewContactScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel =
        ChatViewModel(fakeRepository)
    AppTheme(
        useDarkTheme = false,
        theme = SocialTheme.WhatsApp
    ) {
        NewContactScreen(
            onBack = {},
            viewModel = previewViewModel,
            onContactClick = {},
            navController = rememberNavController()
        )
    }
}
