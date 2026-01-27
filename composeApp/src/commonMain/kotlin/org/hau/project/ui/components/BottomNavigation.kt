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
import org.hau.project.data.repositories.ChatRepository
import org.hau.project.ui.screens.calls.AudioCallScreen
import org.hau.project.ui.screens.calls.CallsScreen
import org.hau.project.ui.screens.calls.VideoCallScreen
import org.hau.project.ui.screens.chats.ChatScreen
import org.hau.project.ui.screens.chats.DetailScreen
import org.hau.project.ui.screens.chats.NewContactScreen
import org.hau.project.ui.screens.chats.NewGroupScreen
import org.hau.project.ui.screens.chats.UserProfileScreen
import org.hau.project.ui.screens.memories.ChannelDetailScreen
import org.hau.project.ui.screens.memories.ChannelProfileScreen
import org.hau.project.ui.screens.memories.MemoriesScreen
import org.hau.project.ui.screens.memories.ProfileAction
import org.hau.project.ui.screens.memories.ScheduleCallScreen
import org.hau.project.ui.screens.settings.AccountScreen
import org.hau.project.ui.screens.settings.AvatarScreen
import org.hau.project.ui.screens.settings.ChatSettingsScreen
import org.hau.project.ui.screens.settings.DeleteAccountScreen
import org.hau.project.ui.screens.settings.EmailAddressScreen
import org.hau.project.ui.screens.settings.HelpSettingsScreen
import org.hau.project.ui.screens.settings.SScreen
import org.hau.project.ui.screens.settings.InviteFriendScreen
import org.hau.project.ui.screens.settings.LanguageSettingsScreen
import org.hau.project.ui.screens.settings.PasskeysScreen
import org.hau.project.ui.screens.settings.PrivacySettingsScreen
import org.hau.project.ui.screens.settings.RequestAccountInfoScreen
import org.hau.project.ui.screens.settings.SecurityNotificationsScreen
import org.hau.project.ui.screens.settings.SettingsScreen
import org.hau.project.ui.screens.settings.StorageSettingsScreen
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.hau.project.viewModels.ChatViewModel
import org.hau.project.viewModels.ProfileViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
sealed interface NavDestinaton{
    val routePattern: String
}

@Serializable
object Routes{
    @Serializable data object HOME: NavDestinaton { override val routePattern: String = "HOME" }
    @Serializable data object MEMORIES: NavDestinaton { override val routePattern: String = "MEMORIES" }
    @Serializable data object CALLS: NavDestinaton { override val routePattern: String = "CALLS" }
    @Serializable data object SETTINGS: NavDestinaton { override val routePattern: String = "SETTINGS" }
    @Serializable data object S: NavDestinaton { override val routePattern: String = "S" }
    @Serializable data class CHANNEL_DETAIL(val channelId: String): NavDestinaton { override val routePattern: String = "CHANNEL_DETAIL" }
    @Serializable data class DETAIL(val chatId: String): NavDestinaton { override val routePattern: String = "DETAIL" }
    @Serializable data class CHANNEL_PROFILE(val channelId: String): NavDestinaton { override val routePattern: String = "CHANNEL_PROFILE" }
    @Serializable data class USER_PROFILE(val userId: String): NavDestinaton { override val routePattern: String = "USER_PROFILE" }
    @Serializable data object NEW_CONTACTS: NavDestinaton { override val routePattern: String = "NEW_CONTACTS" }
    @Serializable data object NEW_GROUPS: NavDestinaton { override val routePattern: String = "NEW_GROUPS" }
    @Serializable data object SECURITY_NOTIFICATION: NavDestinaton { override val routePattern: String = "SECURITY_NOTIFICATION" }
    @Serializable data object VIDEO_CALL: NavDestinaton { override val routePattern: String = "VIDEO_CALL" }
    @Serializable data object AUDIO_CALL: NavDestinaton { override val routePattern: String = "AUDIO_CALL" }
    @Serializable data object SCHEDULE_CALL: NavDestinaton { override val routePattern: String = "SCHEDULE_CALL" }
    @Serializable data object PRIVACY : NavDestinaton { override val routePattern: String = "PRIVACY" }
    @Serializable data object MESSAGING : NavDestinaton { override val routePattern: String = "MESSAGING" }
    @Serializable data object NOTIFICATIONS : NavDestinaton { override val routePattern: String = "NOTIFICATIONS" }
    @Serializable data object STORAGE : NavDestinaton { override val routePattern: String = "STORAGE" }
    @Serializable data object LANGUAGE : NavDestinaton { override val routePattern: String = "LANGUAGE" }
    @Serializable data object HELP : NavDestinaton { override val routePattern: String = "HELP" }
    @Serializable data object INVITE : NavDestinaton { override val routePattern: String = "INVITE" }
    @Serializable data object PASSKEYS : NavDestinaton { override val routePattern: String = "PASSKEYS" }
    @Serializable data object EMAIL_ADDRESS : NavDestinaton { override val routePattern: String = "EMAIL_ADDRESS" }
    @Serializable data object DELETE_ACCOUNT : NavDestinaton { override val routePattern: String = "DELETE_ACCOUNT" }
    @Serializable data object REQUEST_INFO : NavDestinaton { override val routePattern: String = "REQUEST_INFO" }
    @Serializable data object AVATAR : NavDestinaton { override val routePattern: String = "AVATAR" }
    @Serializable data object ACCOUNT : NavDestinaton { override val routePattern: String = "ACCOUNT" }
}


data class BottomNavItem(
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val destination: NavDestinaton
)

@Composable
fun BottomNavigation(){
    val chatRepository = remember { ChatRepository() }
    val chatViewModel: ChatViewModel = viewModel {
       ChatViewModel(
            chatRepository
        )
    }
    val profileViewModel: ProfileViewModel = viewModel {
        ProfileViewModel(
            chatRepository
        )
    }
    val navController: NavHostController = rememberNavController()
    val startDestination: NavDestinaton = Routes.HOME

    val bottomNavItems = listOf(
        BottomNavItem(
            Icons.Outlined.Forum,
            Icons.Default.Forum,
            Routes.HOME
        ),
        BottomNavItem(
            Icons.Outlined.Stream,
            Icons.Filled.Stream,
            Routes.MEMORIES
        ),
        BottomNavItem(
            Icons.Outlined.Call,
            Icons.Filled.Call,
            Routes.CALLS
        ),
        BottomNavItem(
            Icons.Outlined.Settings,
            Icons.Filled.Settings,
            Routes.SETTINGS
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
                composable<Routes.HOME> {
                    ChatScreen(
                        viewModel = chatViewModel,
                        onChatClick = { selectedChatId ->
                            navController.navigate(
                                Routes.DETAIL(
                                    chatId = selectedChatId
                                )
                            )
                        },
                        onNewContactClick = { navController.navigate(Routes.NEW_CONTACTS) },
                        onNewGroupClick = {
                            navController.navigate(Routes.NEW_GROUPS)
                        },
                        onSettingsClick = {
                            navController.navigate(Routes.S)
                        }
                    )
                }
                composable<Routes.S>{
                    SScreen(
                        navController,
                        onBackClick = navController::popBackStack
                    )
                }
                composable<Routes.SETTINGS>{
                    SettingsScreen(
                        navController
                    )
                }
                composable<Routes.MEMORIES> {
                    MemoriesScreen(
                        viewModel = chatViewModel,
                        onChannelClick = { selectedChannelId ->
                            navController.navigate(
                                Routes.CHANNEL_DETAIL(
                                    channelId = selectedChannelId
                                )
                            )
                        },
                        onAddMemoryClick = {})
                }
                composable<Routes.CALLS>{
                    CallsScreen(
                        viewModel = chatViewModel
                    )
                }
                composable<Routes.DETAIL> { navBackStackEntry ->
                    val route: Routes.DETAIL = navBackStackEntry.toRoute()
                    DetailScreen(
                        chatId = route.chatId,
                        onBack = { navController.popBackStack() },
                        viewModel = chatViewModel,
                        navController = navController,
                        onUserInfoClick = { userId ->
                            navController.navigate(
                                Routes.USER_PROFILE(
                                    userId = userId
                                )
                            )
                        }
                    )
                }

            composable<Routes.USER_PROFILE> { backStackEntry ->
                val args: Routes.USER_PROFILE= backStackEntry.toRoute()
                val uiState by profileViewModel.userProfileUiState.collectAsState()

                LaunchedEffect(args.userId) {
                    profileViewModel.loadUserProfile(args.userId)
                }

                // You would have a UserProfileScreen composable here
                UserProfileScreen(
                    uiState = uiState,
                    onAction = { action ->
                        when (action) {
                            is ProfileAction.NavigateBack -> navController.popBackStack()
                            // Handle other actions like ToggleMute, Unfollow, etc. by calling viewModel methods
                            is ProfileAction.ToggleMute -> profileViewModel.toggleMute()
                            else -> {}
                        }
                    }
                )
            }

            composable<Routes.CHANNEL_DETAIL> { backStackEntry ->
                    val route: Routes.CHANNEL_DETAIL = backStackEntry.toRoute()
                ChannelDetailScreen(
                    channelId = route.channelId,
                    onBack = { navController.popBackStack() },
                    viewModel = chatViewModel,
                    onChannelInfoClick = {
                        navController.navigate(
                            Routes.CHANNEL_PROFILE(
                                channelId = route.channelId
                            )
                        )
                    }
                )
                }
                composable<Routes.CHANNEL_PROFILE> { backStackEntry ->
                    val args = backStackEntry.toRoute<Routes.CHANNEL_PROFILE>()
                    val uiState by profileViewModel.profileUiState.collectAsState()

                    // Use a LaunchedEffect to load data when the composable enters the screen
                    LaunchedEffect(args.channelId) {
                        profileViewModel.loadChannelProfile(args.channelId)
                    }

                    ChannelProfileScreen(
                        uiState = uiState,
                        onAction = { action ->
                            when (action) {
                                is ProfileAction.NavigateBack -> navController.popBackStack()
                                // Handle other actions like ToggleMute, Unfollow, etc. by calling viewModel methods
                                is ProfileAction.ToggleMute -> profileViewModel.toggleMute()
                                else -> {}
                            }
                        }
                    )
                }

                composable<Routes.NEW_CONTACTS> {
                    NewContactScreen(
                        onBack = navController::popBackStack,
                        viewModel = chatViewModel,
                        onContactClick = { selectedContactId ->
                            chatViewModel.startChatWithNewContact(selectedContactId)
                            navController.navigate(
                                Routes.DETAIL(
                                    chatId = selectedContactId
                                )
                            )
                        },
                        navController = navController
                    )
                }
                composable<Routes.NEW_GROUPS>{ NewGroupScreen(onBack = navController::popBackStack) }
                composable<Routes.SECURITY_NOTIFICATION>{
                    SecurityNotificationsScreen(
                        onBack = navController::popBackStack
                    )
                }
                composable<Routes.VIDEO_CALL> {
                    VideoCallScreen(
                        onBack = navController::popBackStack
                    )
                }
                composable<Routes.AUDIO_CALL> {
                    AudioCallScreen(
                        onBack = navController::popBackStack
                    )
                }
                composable<Routes.SCHEDULE_CALL> {
                    ScheduleCallScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.PRIVACY> {
                    PrivacySettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.MESSAGING> {
                    ChatSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.NOTIFICATIONS> {
                    SecurityNotificationsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.STORAGE> {
                    StorageSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.LANGUAGE> {
                    LanguageSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.HELP> {
                    HelpSettingsScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.INVITE> {
                    InviteFriendScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.PASSKEYS> {
                    PasskeysScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.EMAIL_ADDRESS> {
                    EmailAddressScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.DELETE_ACCOUNT> {
                    DeleteAccountScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.REQUEST_INFO> {
                    RequestAccountInfoScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.AVATAR> {
                    AvatarScreen(
                        onBack = { navController.popBackStack() })
                }
                composable<Routes.ACCOUNT> {
                    AccountScreen(
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
    AppTheme(
        useDarkTheme = true,
        theme = SocialTheme.Verdant
    ) {
        BottomNavigation()
    }
}
@Composable
@Preview(showBackground = true)
fun BottomNavigationLightPreview(){
    AppTheme(
        useDarkTheme = false,
        theme = SocialTheme.Verdant
    ) {
        BottomNavigation()
    }
}