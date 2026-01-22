package org.hau.project.ui.screens.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dialpad
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PermMedia
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import kotlinx.coroutines.launch
import org.hau.project.data.repositories.formatCount
import org.hau.project.ui.components.ActionButtonsRow
import org.hau.project.ui.components.PrivacyInfoBottomSheet
import org.hau.project.ui.components.ProfileHeader
import org.hau.project.ui.components.SettingsRow
import org.hau.project.ui.components.UserInfoSection
import org.hau.project.ui.components.UserProfileTopAppBar
import org.hau.project.ui.components.VerifiedInfoBottomSheet
import org.hau.project.ui.screens.memories.BottomSheetType
import org.hau.project.ui.screens.memories.DangerChannelZoneSection
import org.hau.project.ui.screens.memories.ProfileAction
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.hau.project.models.User
import org.hau.project.viewModels.UserProfileUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    uiState: UserProfileUiState,
    onAction: (ProfileAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var activeBottomSheet by remember { mutableStateOf<BottomSheetType?>(null) }
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
            UserProfileTopAppBar(
                userName = uiState.user?.name ?: "",
                avatarUrl = uiState.user?.avatarRes,
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
            uiState.user != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding()),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // --- HEADER: BANNER & AVATAR ---
                    item {
                        ProfileHeader(
                            bannerUrl = uiState.bannerUrl,
                            bannerHeight = bannerHeight,
                            avatarInitialSize = avatarInitialSize,
                            scrollOffset = scrollOffset,
                            avatarUrl = uiState.user.avatarRes
                        )
                    }

                    // --- INFO: NAME, FOLLOWERS, ACTIONS ---
                    item {
                        UserInfoSection(
                            user = uiState.user,
                            onShowVerified = {
                                activeBottomSheet =
                                    BottomSheetType.VERIFIED
                            }
                        )
                    }


                    item {
                        ActionButtonsRow()
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 8.dp)
                    }

                    // --- SETTINGS & INFO LIST ---
                    item {
                        SettingsSection(
                            mediaCount = uiState.mediaCount,
                            isMuted = uiState.isMuted,
                            onAction = onAction,
                            onShowPrivacy = {
                                activeBottomSheet =
                                    BottomSheetType.PRIVACY
                            }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 8.dp)
                    }

                    // --- DANGEROUS ACTIONS ---
                    item {
                        DangerChannelZoneSection(
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
                    BottomSheetType.VERIFIED -> VerifiedInfoBottomSheet(
                        onClose = {
                            coroutineScope.launch { modalSheetState.hide() }
                                .invokeOnCompletion { activeBottomSheet = null }
                        })
                    BottomSheetType.PRIVACY -> PrivacyInfoBottomSheet(
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
fun SettingsSection(
    mediaCount: Int,
    isMuted: Boolean,
    onAction: (ProfileAction) -> Unit,
    onShowPrivacy: () -> Unit
) {
    Column {
        SettingsRow(
            icon = Icons.Outlined.PermMedia,
            text = "Media, Links, and Docs",
            trailingText = formatCount(
                mediaCount.toLong()
            ),
            onClick = { onAction(ProfileAction.ViewMedia) }
        )
        SettingsRow(
            icon = Icons.Outlined.Notifications,
            text = "Mute Notifications",
            isToggle = true,
            checked = isMuted,
            onCheckedChange = { onAction(ProfileAction.ToggleMute) }
        )
        SettingsRow(
            icon = Icons.Outlined.Public,
            text = "Your Profile",
            description = "No one can find profile and see what's been shared.",
            onClick = {} // Non-interactive for now
        )
        SettingsRow(
            icon = Icons.Outlined.Dialpad,
            text = "Profile Privacy",
            description = "This profile has provided privacy for your profile and phone number.",
            onClick = onShowPrivacy
        )
    }
}

@Preview
@Composable
fun UserProfileScreenPreview() {
    val sampleUser = User(
        id = "1",
        name = "John Doe",
        handle = "@johndoe",
        avatarRes = Res.drawable.grattitude,
        bannerUrl = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
        followerCount = 1234,
        isVerified = true,
        bio = "This is a sample bio for the user profile screen."
    )
    val uiState = UserProfileUiState(
        isLoading = false,
        user = sampleUser,
        isMuted = false,
        mediaCount = 42,
        bannerUrl = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
        error = null
    )
    UserProfileScreen(
        uiState = uiState,
        onAction = {}
    )
}
