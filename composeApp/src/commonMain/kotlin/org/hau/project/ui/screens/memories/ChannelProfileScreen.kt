package org.hau.project.ui.screens.memories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import kotlinx.coroutines.launch
import org.hau.project.models.AttachmentType
import org.hau.project.models.Channels
import org.hau.project.ui.components.ChannelProfileTopBar
import org.hau.project.ui.components.DangerSettingsRow
import org.hau.project.ui.theme.AppTheme
import org.hau.project.ui.theme.SocialTheme
import org.hau.project.viewModels.ProfileUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

// --- STATE & DATA CLASSES ---

/**
 * Represents all possible actions from the UI, decoupling the UI from business logic.
 */
sealed interface ProfileAction {
    data object NavigateBack : ProfileAction
    data object ToggleMute : ProfileAction
    data object ViewMedia : ProfileAction
    data object Unfollow : ProfileAction
    data object Report : ProfileAction
}

/**
 * Represents the types of bottom sheets that can be shown.
 */
enum class BottomSheetType {
    PRIVACY, VERIFIED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelProfileScreen(
    uiState: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var activeBottomSheet by remember { mutableStateOf<org.hau.project.ui.screens.memories.BottomSheetType?>(null) }
    val modalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Derived state for calculating scroll-based animations efficiently.
    val scrollOffset by remember {
        derivedStateOf {
            if (lazyListState.firstVisibleItemIndex == 0) lazyListState.firstVisibleItemScrollOffset.toFloat() else Float.MAX_VALUE
        }
    }

    val bannerHeight = 220.dp
    val avatarInitialSize = 120.dp

    // Determines if the header is collapsed for the TopAppBar animation.
    val isHeaderCollapsed = with(LocalDensity.current) {
        val collapseThreshold = bannerHeight.toPx() - TopAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding().toPx()
        scrollOffset > collapseThreshold
    }

    Scaffold(
        topBar = {
            ChannelProfileTopBar(
                channelName = uiState.channel?.channelName ?: "",
                avatarUrl = uiState.channel?.channelRes,
                isCollapsed = isHeaderCollapsed,
                onNavigateBack = { onAction(ProfileAction.NavigateBack) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text(uiState.error, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
                }
            }
            uiState.channel != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding()),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // --- HEADER: BANNER & AVATAR ---
                    item {
                        _root_ide_package_.org.hau.project.ui.components.ProfileHeader(
                            bannerUrl = uiState.bannerUrl,
                            bannerHeight = bannerHeight,
                            avatarInitialSize = avatarInitialSize,
                            scrollOffset = scrollOffset,
                            avatarUrl = uiState.channel.channelRes
                        )
                    }

                    // --- INFO: NAME, FOLLOWERS, ACTIONS ---
                    item {
                        _root_ide_package_.org.hau.project.ui.components.ChannelInfoSection(
                            channel = uiState.channel,
                            onShowVerified = {
                                activeBottomSheet =
                                    _root_ide_package_.org.hau.project.ui.screens.memories.BottomSheetType.VERIFIED
                            }
                        )
                    }

                    item {
                        _root_ide_package_.org.hau.project.ui.components.ActionButtonsRow()
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 8.dp)
                    }

                    // --- SETTINGS & INFO LIST ---
                    item {
                        _root_ide_package_.org.hau.project.ui.screens.memories.SettingsSection(
                            mediaCount = uiState.mediaCount,
                            isMuted = uiState.isMuted,
                            onAction = onAction,
                            onShowPrivacy = {
                                activeBottomSheet =
                                    _root_ide_package_.org.hau.project.ui.screens.memories.BottomSheetType.PRIVACY
                            }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 8.dp)
                    }

                    // --- DANGEROUS ACTIONS ---
                    item {
                        _root_ide_package_.org.hau.project.ui.screens.memories.DangerChannelZoneSection(
                            onAction
                        )
                    }
                }
            }
        }

        // --- BOTTOM SHEET HANDLER ---
        if (activeBottomSheet != null) {
            ModalBottomSheet(
                onDismissRequest = { activeBottomSheet = null },
                sheetState = modalSheetState,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                when (activeBottomSheet) {
                    _root_ide_package_.org.hau.project.ui.screens.memories.BottomSheetType.VERIFIED -> _root_ide_package_.org.hau.project.ui.components.VerifiedInfoBottomSheet(
                        onClose = {
                            coroutineScope.launch { modalSheetState.hide() }
                                .invokeOnCompletion { activeBottomSheet = null }
                        })
                    _root_ide_package_.org.hau.project.ui.screens.memories.BottomSheetType.PRIVACY -> _root_ide_package_.org.hau.project.ui.components.PrivacyInfoBottomSheet(
                        onClose = {
                            coroutineScope.launch { modalSheetState.hide() }
                                .invokeOnCompletion { activeBottomSheet = null }
                        })
                    null -> {}
                }
            }
        }
    }
}


@Composable
private fun SettingsSection(
    mediaCount: Int,
    isMuted: Boolean,
    onAction: (ProfileAction) -> Unit,
    onShowPrivacy: () -> Unit
) {
    Column {
        _root_ide_package_.org.hau.project.ui.components.SettingsRow(
            icon = Icons.Outlined.PermMedia,
            text = "Media, Links, and Docs",
            trailingText = _root_ide_package_.org.hau.project.data.repositories.formatCount(
                mediaCount.toLong()
            ),
            onClick = { onAction(_root_ide_package_.org.hau.project.ui.screens.memories.ProfileAction.ViewMedia) }
        )
        _root_ide_package_.org.hau.project.ui.components.SettingsRow(
            icon = Icons.Outlined.Notifications,
            text = "Mute Notifications",
            isToggle = true,
            checked = isMuted,
            onCheckedChange = { onAction(_root_ide_package_.org.hau.project.ui.screens.memories.ProfileAction.ToggleMute) }
        )
        _root_ide_package_.org.hau.project.ui.components.SettingsRow(
            icon = Icons.Outlined.Public,
            text = "Public Channel",
            description = "Anyone can find this channel and see what's been shared.",
            onClick = {} // Non-interactive for now
        )
        _root_ide_package_.org.hau.project.ui.components.SettingsRow(
            icon = Icons.Outlined.Dialpad,
            text = "Profile Privacy",
            description = "This channel has added privacy for your profile and phone number.",
            onClick = onShowPrivacy
        )
    }
}

@Composable
fun DangerChannelZoneSection(onAction: (ProfileAction) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        DangerSettingsRow(
            icon = Icons.AutoMirrored.Filled.Logout,
            text = "Unfollow Channel",
            onClick = { onAction(ProfileAction.Unfollow) }
        )
        DangerSettingsRow(
            icon = Icons.Outlined.ThumbDown,
            text = "Report Channel",
            onClick = { onAction(ProfileAction.Report) }
        )
    }
}


// Dummy data for previews
private val previewChannel = Channels(
    id = "preview_id",
    channelRes = Res.drawable.grattitude,
    channelName = "Mindful Moments",
    followerCount = 1234567,
    isVerified = true,
    message = "",
    attachmentType = AttachmentType.AUDIO,
    timestamp = "",
    unreadMessages = 3,
    isRead = false
)

private val previewState = ProfileUiState(
    isLoading = false,
    channel = previewChannel,
    mediaCount = 3567,
    isMuted = false
)

@Preview(name = "Light Mode (Verdant Theme)")
@Composable
private fun ChannelProfileScreenPreviewLight() {
    AppTheme(
        theme = SocialTheme.Verdant,
        useDarkTheme = false
    ) {
        Surface {
            ChannelProfileScreen(
                uiState = previewState,
                onAction = {})
        }
    }
}

@Preview(name = "Dark Mode (Sky Theme)")
@Composable
private fun ChannelProfileScreenPreviewDark() {
    AppTheme(
        theme = SocialTheme.Sky,
        useDarkTheme = true
    ) {
        Surface {
            ChannelProfileScreen(
                uiState = previewState.copy(
                    isMuted = true
                ), onAction = {})
        }
    }
}

@Preview(name = "Loading State")
@Composable
private fun ChannelProfileScreenLoadingPreview() {
    AppTheme {
        Surface {
            ChannelProfileScreen(
                uiState = ProfileUiState(
                    isLoading = true
                ), onAction = {})
        }
    }
}

@Preview(name = "Error State")
@Composable
private fun ChannelProfileScreenErrorPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            ChannelProfileScreen(
                uiState = ProfileUiState(error = "Could not load channel details. Please check your connection."),
                onAction = {}
            )
        }
    }
}
