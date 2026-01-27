package org.hau.project.ui.screens.large

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.ic_launcher_playstore
import org.hau.project.data.repositories.ChatRepository
import org.hau.project.ui.components.NavDestinaton
import org.hau.project.ui.components.ProfilePlaceholder
import org.hau.project.ui.components.Routes
import org.hau.project.ui.components.SettingsDetailPlaceholder
import org.hau.project.ui.screens.calls.CallsScreen
import org.hau.project.ui.screens.chats.ChatScreen
import org.hau.project.ui.screens.chats.DetailScreen
import org.hau.project.ui.screens.chats.UserProfileScreen
import org.hau.project.ui.screens.memories.ChannelDetailScreen
import org.hau.project.ui.screens.memories.ChannelProfileScreen
import org.hau.project.ui.screens.memories.MemoriesScreen
import org.hau.project.ui.screens.settings.*
import org.hau.project.utils.WindowSize
import org.hau.project.utils.rememberWindowSize
import org.hau.project.viewModels.AdaptiveUiState
import org.hau.project.viewModels.ChatViewModel
import org.hau.project.viewModels.ProfileViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun AdaptiveUi() {
    val windowSize = rememberWindowSize()
    val adaptiveUiState = remember { AdaptiveUiState() }
    val chatRepository = remember { ChatRepository() }
    val chatViewModel: ChatViewModel = viewModel { ChatViewModel(chatRepository) }
    val profileViewModel: ProfileViewModel = viewModel { ProfileViewModel(chatRepository) }

    val selectedDetailId by adaptiveUiState.selectedDetailId.collectAsState()
    val selectedProfileId by adaptiveUiState.selectedProfileId.collectAsState()
    val isChannelSelected by adaptiveUiState.isChannelSelected.collectAsState()

    val middlePaneNavController = rememberNavController()
    val settingsNavController = rememberNavController()
    val navBackStackEntry by middlePaneNavController.currentBackStackEntryAsState()
    var selectedDestination by remember { mutableStateOf<NavDestinaton>(Routes.HOME) }

    Row(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))) {
        // --- Pane 1: Navigation Rail ---
        NavigationRail(
            selectedDestination = selectedDestination,
            onDestinationSelected = { destination ->
                selectedDestination = destination
                middlePaneNavController.navigate(destination) {
                    popUpTo(Routes.HOME) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
                if (destination !is Routes.HOME && destination !is Routes.MEMORIES) {
                    adaptiveUiState.clearSelections()
                }
            },
            modifier = Modifier.width(64.dp).fillMaxHeight()
        )

        // --- Pane 2: List Pane ---
        NavHost(
            navController = middlePaneNavController,
            startDestination = Routes.HOME,
            modifier = Modifier.width(350.dp).fillMaxHeight()
        ) {
            composable<Routes.HOME> {
                ChatScreen(
                    viewModel = chatViewModel,
                    onChatClick = { chatId -> adaptiveUiState.selectChat(chatId) },
                    onNewContactClick = {},
                    onNewGroupClick = {},
                    onSettingsClick = {}
                )
            }
            composable<Routes.MEMORIES> {
                MemoriesScreen(
                    viewModel = chatViewModel,
                    onChannelClick = { channelId -> adaptiveUiState.selectChannel(channelId) },
                    onAddMemoryClick = {}
                )
            }
            composable<Routes.CALLS> { CallsScreen(viewModel = chatViewModel) }
            composable<Routes.SETTINGS> { SettingsScreen(navController = settingsNavController) }
        }

        // --- Pane 3: Detail Pane ---
        if (windowSize >= WindowSize.Expanded) {
            // Divider between List and Detail
            VerticalPaneDivider()

            Box(modifier = Modifier.weight(1.8f).fillMaxHeight()) {
                val currentRoute = navBackStackEntry?.destination?.route ?: ""
                if (currentRoute.contains(Routes.SETTINGS.routePattern, ignoreCase = true)) {
                    NavHost(navController = settingsNavController, startDestination = "settings_root") {
                        composable("settings_root") { SettingsDetailPlaceholder() }
                        composable<Routes.ACCOUNT> { AccountScreen(navController = settingsNavController, onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.AVATAR> { AvatarScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.PRIVACY> { PrivacySettingsScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.SECURITY_NOTIFICATION> { SecurityNotificationsScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.PASSKEYS> { PasskeysScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.EMAIL_ADDRESS> { EmailAddressScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.DELETE_ACCOUNT> { DeleteAccountScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.NOTIFICATIONS> { NotificationsSettingsScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.MESSAGING> { ChatSettingsScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.STORAGE> { StorageSettingsScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.LANGUAGE> { LanguageSettingsScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.HELP> { HelpSettingsScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.INVITE> { InviteFriendScreen(onBack = { settingsNavController.navigateUp() }) }
                        composable<Routes.REQUEST_INFO> { RequestAccountInfoScreen(onBack = { settingsNavController.navigateUp() }) }
                    }
                } else if (selectedDetailId != null) {
                    if (isChannelSelected) {
                        ChannelDetailScreen(
                            viewModel = chatViewModel,
                            channelId = selectedDetailId!!,
                            onBack = { adaptiveUiState.clearSelections() },
                            onChannelInfoClick = { channelId ->
                                adaptiveUiState.selectProfile(channelId, isChannel = true)
                            }
                        )
                    } else {
                        DetailScreen(
                            viewModel = chatViewModel,
                            chatId = selectedDetailId!!,
                            onBack = { adaptiveUiState.clearSelections() },
                            onUserInfoClick = { userId ->
                                adaptiveUiState.selectProfile(userId, isChannel = false)
                            }
                        )
                    }
                } else {
                    ChatDetailPlaceholder()
                }
            }
        }


        // --- Pane 4: Info Pane ---
        if (windowSize >= WindowSize.Large && selectedProfileId != null) {
            // Divider between Detail and Info
            VerticalPaneDivider()

            Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                LaunchedEffect(selectedProfileId, isChannelSelected) {
                    if (isChannelSelected) {
                        profileViewModel.loadChannelProfile(selectedProfileId!!)
                    } else {
                        profileViewModel.loadUserProfile(selectedProfileId!!)
                    }
                }

                if (isChannelSelected) {
                    val channelProfileState by profileViewModel.profileUiState.collectAsState()
                    ChannelProfileScreen(
                        uiState = channelProfileState,
                        onAction = { /* ... */ }
                    )
                } else {
                    val userProfileState by profileViewModel.userProfileUiState.collectAsState()
                    UserProfileScreen(
                        uiState = userProfileState,
                        onAction = { /* ... */ }
                    )
                }
            }
        } else if (windowSize >= WindowSize.Large) {
            // Divider for placeholder pane
            VerticalPaneDivider()
            Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                ProfilePlaceholder()
            }
        }
    }
}

@Composable
fun VerticalPaneDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
    )
}

/**
 * A placeholder composable shown in the chat detail pane before a chat is selected.
 */
@Composable
private fun ChatDetailPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_launcher_playstore),
            contentDescription = "Hau Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Select a chat to start messaging",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Your conversations will appear here. Choose a contact from the list to read messages or send a new one.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 300.dp)
        )
    }
}

