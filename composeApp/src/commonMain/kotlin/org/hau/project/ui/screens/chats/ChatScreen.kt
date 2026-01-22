package org.hau.project.ui.screens.chats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hau.project.data.repositories.ChatRepository
import org.hau.project.models.Chat
import org.hau.project.ui.components.ChatItem
import org.hau.project.ui.components.SearchBar
import org.hau.project.ui.theme.AppTheme
import org.hau.project.viewModels.ChatViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatScreen(
    viewModel: org.hau.project.viewModels.ChatViewModel,
    onChatClick: (String) -> Unit,
    onNewContactClick: () -> Unit
) {
    val uiState by viewModel.chatListState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background, // Use theme background
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .clickable { onNewContactClick() }
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .size(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.PersonAdd,
                    contentDescription = "Add Contact",
                    tint = MaterialTheme.colorScheme.onPrimary // Use onPrimary for content on primary color
                )
            }
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error // Use error color
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        _root_ide_package_.org.hau.project.ui.components.SearchBar(
                            query = searchQuery,
                            onQueryChange = { searchQuery = it },
                            onSearch = {},
                            isExpanded = false,
                            onToggleExpanded = {},
                            modifier = Modifier
                                .offset(x = (10).dp)
                                .weight(1f),
                            placeholderText = "Search Chats..."
                        )
                        Box {
                            IconButton(onClick = { isExpanded = true }) {
                                Icon(
                                    Icons.Outlined.MoreVert,
                                    contentDescription = "More Options",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant // Use for icons on surface
                                )
                            }
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false },
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                content = {
                                    val itemColors = MenuDefaults.itemColors(
                                        textColor = MaterialTheme.colorScheme.onSurface // Use onSurface for text
                                    )
                                    DropdownMenuItem(
                                        text = { Text("New Group") },
                                        onClick = { /* Handle click */ isExpanded = false },
                                        colors = itemColors,
                                    )
                                    DropdownMenuItem(
                                        text = { Text("New Community") },
                                        onClick = { /* Handle click */ isExpanded = false },
                                        colors = itemColors,
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Starred") },
                                        onClick = { /* Handle click */ isExpanded = false },
                                        colors = itemColors,
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Read all") },
                                        onClick = { /* Handle click */ isExpanded = false },
                                        colors = itemColors,
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Settings") },
                                        onClick = { /* Handle click */ isExpanded = false },
                                        colors = itemColors,
                                    )
                                }
                            )
                        }
                    }
                }
                items(uiState.chats) { chat ->
                    Box(modifier = Modifier.clickable { onChatClick(chat.id) }) {
                        _root_ide_package_.org.hau.project.ui.components.ChatItem(chat)
                    }
                }
            }
        }
    }
}


/**
 * Completed Preview for ChatScreen.
 */
@Preview
@Composable
fun ChatScreenLightPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel =
        ChatViewModel(fakeRepository)
    AppTheme(useDarkTheme = false) {
        ChatScreen(
            viewModel = previewViewModel,
            onChatClick = { chatId -> println("Chat clicked: $chatId") },
            onNewContactClick = { println("New contact clicked") }
        )
    }
}
@Preview
@Composable
fun ChatScreenDarkPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel =
        ChatViewModel(fakeRepository)
    AppTheme(useDarkTheme = true) {
        ChatScreen(
            viewModel = previewViewModel,
            onChatClick = { chatId -> println("Chat clicked: $chatId") },
            onNewContactClick = { println("New contact clicked") }
        )
    }
}
