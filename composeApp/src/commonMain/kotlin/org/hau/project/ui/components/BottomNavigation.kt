package org.hau.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Stream
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
sealed interface NavDestinaton{
    val routePattern: String
}

@Serializable
object Routes{
    @Serializable data object HOME: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "HOME" }
    @Serializable data object MEMORIES: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "MEMORIES" }
    @Serializable data object CALLS: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "CALLS" }
    @Serializable data object SETTINGS: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "SETTINGS" }
    @Serializable data class CHANNEL_DETAIL(val channelId: String):
        org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "CHANNEL_DETAIL" }
    @Serializable data class DETAIL(val chatId: String):
        org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "DETAIL" }
    @Serializable data class CHANNEL_PROFILE(val channelId: String):
        org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "CHANNEL_PROFILE" }
    @Serializable data class USER_PROFILE(val userId: String):
        org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "USER_PROFILE" }
    @Serializable data object NEW_CONTACTS: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "NEW_CONTACTS" }
    @Serializable data object NEW_GROUPS: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "NEW_GROUPS" }
    @Serializable data object SECURITY_NOTIFICATION: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "SECURITY_NOTIFICATION" }
    @Serializable data object VIDEO_CALL: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "VIDEO_CALL" }
    @Serializable data object AUDIO_CALL: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "AUDIO_CALL" }
    @Serializable data object SCHEDULE_CALL: org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "SCHEDULE_CALL" }
    @Serializable data object PRIVACY : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "PRIVACY" }
    @Serializable data object MESSAGING : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "MESSAGING" }
    @Serializable data object NOTIFICATIONS : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "NOTIFICATIONS" }
    @Serializable data object STORAGE : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "STORAGE" }
    @Serializable data object LANGUAGE : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "LANGUAGE" }
    @Serializable data object HELP : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "HELP" }
    @Serializable data object INVITE : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "INVITE" }
    @Serializable data object PASSKEYS : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "PASSKEYS" }
    @Serializable data object EMAIL_ADDRESS : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "EMAIL_ADDRESS" }
    @Serializable data object DELETE_ACCOUNT : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "DELETE_ACCOUNT" }
    @Serializable data object REQUEST_INFO : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "REQUEST_INFO" }
    @Serializable data object AVATAR : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "AVATAR" }
    @Serializable data object ACCOUNT : org.hau.project.ui.components.NavDestinaton { override val routePattern: String = "ACCOUNT" }
}


data class BottomNavItem(
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val destination: org.hau.project.ui.components.NavDestinaton
)

@Composable
fun BottomNavigation(){
    val chatRepository = remember { _root_ide_package_.org.hau.project.data.repositories.ChatRepository() }
    val chatViewModel: org.hau.project.viewModels.ChatViewModel = viewModel {
        _root_ide_package_.org.hau.project.viewModels.ChatViewModel(
            chatRepository
        )
    }
    val profileViewModel: org.hau.project.viewModels.ProfileViewModel = viewModel {
        _root_ide_package_.org.hau.project.viewModels.ProfileViewModel(
            chatRepository
        )
    }
    val navController: NavHostController = rememberNavController()
    val startDestination: org.hau.project.ui.components.NavDestinaton = _root_ide_package_.org.hau.project.ui.components.Routes.HOME

    val bottomNavItems = listOf(
        _root_ide_package_.org.hau.project.ui.components.BottomNavItem(
            Icons.Outlined.Forum,
            Icons.Default.Forum,
            _root_ide_package_.org.hau.project.ui.components.Routes.HOME
        ),
        _root_ide_package_.org.hau.project.ui.components.BottomNavItem(
            Icons.Outlined.Stream,
            Icons.Filled.Stream,
            _root_ide_package_.org.hau.project.ui.components.Routes.MEMORIES
        ),
        _root_ide_package_.org.hau.project.ui.components.BottomNavItem(
            Icons.Outlined.Call,
            Icons.Filled.Call,
            _root_ide_package_.org.hau.project.ui.components.Routes.CALLS
        ),
        _root_ide_package_.org.hau.project.ui.components.BottomNavItem(
            Icons.Outlined.Settings,
            Icons.Filled.Settings,
            _root_ide_package_.org.hau.project.ui.components.Routes.SETTINGS
        )
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarRoutePatterns = remember { bottomNavItems.map { it.destination.routePattern } }
    val isBottomBarVisible = currentDestination?.hierarchy?.any {
        val routeName = it.route ?: ""
        bottomBarRoutePatterns.any { pattern -> routeName.contains(pattern,ignoreCase = true) }
    } == true

    //val isBottomBarVisible = bottomNavItems.any { it.destination.routePattern == currentDestination?.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible){
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                    containerColor = MaterialTheme.colorScheme.background,
                    tonalElevation = 8.dp
                ){
                    bottomNavItems.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any {
                            it.route?.contains(
                                item.destination.routePattern,
                                ignoreCase = true
                            ) == true
                        } == true

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.destination){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    "Icon"
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }

        }
    ){paddingValues ->
        if (startDestination == null){
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.fillMaxSize().padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
            ){
                composable<org.hau.project.ui.components.Routes.HOME> {
                    _root_ide_package_.org.hau.project.ui.screens.chats.ChatScreen(
                        viewModel = chatViewModel,
                        onChatClick = { selectedChatId ->
                            navController.navigate(
                                _root_ide_package_.org.hau.project.ui.components.Routes.DETAIL(
                                    chatId = selectedChatId
                                )
                            )
                        },
                        onNewContactClick = { navController.navigate(_root_ide_package_.org.hau.project.ui.components.Routes.NEW_CONTACTS) })
                }
                composable<org.hau.project.ui.components.Routes.SETTINGS>{
                    _root_ide_package_.org.hau.project.ui.screens.settings.SettingsScreen(
                        navController
                    )
                }
                composable<org.hau.project.ui.components.Routes.MEMORIES> {
                    _root_ide_package_.org.hau.project.ui.screens.memories.MemoriesScreen(
                        viewModel = chatViewModel,
                        onChannelClick = { selectedChannelId ->
                            navController.navigate(
                                _root_ide_package_.org.hau.project.ui.components.Routes.CHANNEL_DETAIL(
                                    channelId = selectedChannelId
                                )
                            )
                        },
                        onAddMemoryClick = {})
                }
                composable<org.hau.project.ui.components.Routes.CALLS>{
                    _root_ide_package_.org.hau.project.ui.screens.calls.CallsScreen(
                        viewModel = chatViewModel
                    )
                }
                composable<org.hau.project.ui.components.Routes.DETAIL> { navBackStackEntry ->
                    val route: org.hau.project.ui.components.Routes.DETAIL = navBackStackEntry.toRoute()
                    _root_ide_package_.org.hau.project.ui.screens.chats.DetailScreen(
                        chatId = route.chatId,
                        onBack = { navController.popBackStack() },
                        viewModel = chatViewModel,
                        navController = navController,
                        onUserInfoClick = { userId ->
                            navController.navigate(
                                _root_ide_package_.org.hau.project.ui.components.Routes.USER_PROFILE(
                                    userId = userId
                                )
                            )
                        }
                    )
                }

            composable<org.hau.project.ui.components.Routes.USER_PROFILE> { backStackEntry ->
                val args: org.hau.project.ui.components.Routes.USER_PROFILE= backStackEntry.toRoute()
                val uiState by profileViewModel.userProfileUiState.collectAsState()

                LaunchedEffect(args.userId) {
                    profileViewModel.loadUserProfile(args.userId)
                }

                // You would have a UserProfileScreen composable here
                _root_ide_package_.org.hau.project.ui.screens.chats.UserProfileScreen(
                    uiState = uiState,
                    onAction = { action ->
                        when (action) {
                            is org.hau.project.ui.screens.memories.ProfileAction.NavigateBack -> navController.popBackStack()
                            // Handle other actions like ToggleMute, Unfollow, etc. by calling viewModel methods
                            is org.hau.project.ui.screens.memories.ProfileAction.ToggleMute -> profileViewModel.toggleMute()
                            else -> {}
                        }
                    }
                )
            }

            composable<org.hau.project.ui.components.Routes.CHANNEL_DETAIL> { backStackEntry ->
                    val route: org.hau.project.ui.components.Routes.CHANNEL_DETAIL = backStackEntry.toRoute()
                _root_ide_package_.org.hau.project.ui.screens.memories.ChannelDetailScreen(
                    channelId = route.channelId,
                    onBack = { navController.popBackStack() },
                    viewModel = chatViewModel,
                    onChannelInfoClick = {
                        navController.navigate(
                            _root_ide_package_.org.hau.project.ui.components.Routes.CHANNEL_PROFILE(
                                channelId = route.channelId
                            )
                        )
                    }
                )
                }
                composable<org.hau.project.ui.components.Routes.CHANNEL_PROFILE> { backStackEntry ->
                    val args = backStackEntry.toRoute<org.hau.project.ui.components.Routes.CHANNEL_PROFILE>()
                    val uiState by profileViewModel.profileUiState.collectAsState()

                    // Use a LaunchedEffect to load data when the composable enters the screen
                    LaunchedEffect(args.channelId) {
                        profileViewModel.loadChannelProfile(args.channelId)
                    }

                    _root_ide_package_.org.hau.project.ui.screens.memories.ChannelProfileScreen(
                        uiState = uiState,
                        onAction = { action ->
                            when (action) {
                                is org.hau.project.ui.screens.memories.ProfileAction.NavigateBack -> navController.popBackStack()
                                // Handle other actions like ToggleMute, Unfollow, etc. by calling viewModel methods
                                is org.hau.project.ui.screens.memories.ProfileAction.ToggleMute -> profileViewModel.toggleMute()
                                else -> {}
                            }
                        }
                    )
                }

                composable<org.hau.project.ui.components.Routes.NEW_CONTACTS> {
                    _root_ide_package_.org.hau.project.ui.screens.chats.NewContactScreen(
                        onBack = navController::popBackStack,
                        viewModel = chatViewModel,
                        onContactClick = { selectedContactId ->
                            chatViewModel.startChatWithNewContact(selectedContactId)
                            navController.navigate(
                                _root_ide_package_.org.hau.project.ui.components.Routes.DETAIL(
                                    chatId = selectedContactId
                                )
                            )
                        },
                        navController = navController
                    )
                }
                composable<org.hau.project.ui.components.Routes.NEW_GROUPS>{ _root_ide_package_.org.hau.project.ui.screens.chats.NewGroupScreen() }
                composable<org.hau.project.ui.components.Routes.SECURITY_NOTIFICATION>{
                    _root_ide_package_.org.hau.project.ui.screens.settings.SecurityNotificationsScreen(
                        onBack = navController::popBackStack
                    )
                }
                composable<org.hau.project.ui.components.Routes.VIDEO_CALL> {
                    _root_ide_package_.org.hau.project.ui.screens.calls.VideoCallScreen(
                        onBack = navController::popBackStack
                    )
                }
                composable<org.hau.project.ui.components.Routes.AUDIO_CALL> {
                    _root_ide_package_.org.hau.project.ui.screens.calls.AudioCallScreen(
                        onBack = navController::popBackStack
                    )
                }
                composable<org.hau.project.ui.components.Routes.SCHEDULE_CALL> {
                    _root_ide_package_.org.hau.project.ui.screens.memories.ScheduleCallScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.PRIVACY> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.PrivacySettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.MESSAGING> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.ChatSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.NOTIFICATIONS> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.SecurityNotificationsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.STORAGE> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.StorageSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.LANGUAGE> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.LanguageSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.HELP> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.HelpSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.INVITE> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.InviteFriendScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.PASSKEYS> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.PasskeysScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.EMAIL_ADDRESS> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.EmailAddressScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.DELETE_ACCOUNT> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.DeleteAccountScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.REQUEST_INFO> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.RequestAccountInfoScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.AVATAR> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.AvatarScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<org.hau.project.ui.components.Routes.ACCOUNT> {
                    _root_ide_package_.org.hau.project.ui.screens.settings.AccountScreen(
                        navController = navController,
                        onBack = { navController.popBackStack() })
                }
            }
        }

    }
}
@Composable
@Preview(showBackground = true)
fun BottomNavigationPreview(){
    _root_ide_package_.org.hau.project.ui.theme.AppTheme(
        useDarkTheme = true,
        theme = _root_ide_package_.org.hau.project.ui.theme.SocialTheme.Verdant
    ) {
        _root_ide_package_.org.hau.project.ui.components.BottomNavigation()
    }
}
@Composable
@Preview(showBackground = true)
fun BottomNavigationLightPreview(){
    _root_ide_package_.org.hau.project.ui.theme.AppTheme(
        useDarkTheme = false,
        theme = _root_ide_package_.org.hau.project.ui.theme.SocialTheme.Verdant
    ) {
        _root_ide_package_.org.hau.project.ui.components.BottomNavigation()
    }
}