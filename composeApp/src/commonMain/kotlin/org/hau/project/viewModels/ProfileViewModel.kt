package org.hau.project.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.hau.project.data.repositories.ChatRepository
import org.hau.project.models.Channels
import org.hau.project.models.User

/**
 * Represents the UI state for a channel's profile screen.
 *
 * @param isLoading Whether the profile data is currently being loaded.
 * @param channel The [Channels] object containing the profile information. Can be null if not yet loaded.
 * @param isMuted Whether the user has muted notifications for this channel.
 * @param mediaCount The number of media items (photos, videos, etc.) shared in the channel.
 * @param bannerUrl The URL for the channel's banner image.
 * @param error An optional error message if data loading fails.
 */
data class ProfileUiState(
    val isLoading: Boolean = true,
    val channel: Channels? = null, // Use Channels model directly
    val isMuted: Boolean = false,
    val mediaCount: Int = 0,
    val bannerUrl: String = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
    val error: String? = null
)

/**
 * Represents the UI state for a user's profile screen.
 *
 * @param isLoading Indicates if the profile data is being loaded.
 * @param user The [User] object containing the profile details. Null if not loaded.
 * @param isMuted True if the current user has muted this user.
 * @param mediaCount The count of media shared with this user.
 * @param bannerUrl The URL for the user's profile banner.
 * @param error An optional error message if loading fails.
 */
data class UserProfileUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val isMuted: Boolean = false,
    val mediaCount: Int = 0,
    val bannerUrl: String = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
    val error: String? = null
)

/**
 * ViewModel responsible for managing the state and logic for both user and channel profiles.
 *
 * This ViewModel fetches profile data from the [ChatRepository] and exposes it to the UI
 * through [StateFlow]s, encapsulated in [ProfileUiState] and [UserProfileUiState].
 *
 * @param repository The repository for fetching chat, user, and channel data.
 */
class ProfileViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState(isLoading = true))
    /**
     * The UI state for the currently viewed channel profile.
     */
    val profileUiState = _profileUiState.asStateFlow()

    private val _userProfileUiState = MutableStateFlow(UserProfileUiState())
    /**
     * The UI state for the currently viewed user profile.
     */
    val userProfileUiState = _userProfileUiState.asStateFlow()

    /**
     * Asynchronously loads the profile information for a specific channel.
     *
     * It avoids reloading data if the requested channel's profile is already loaded.
     * Updates the [profileUiState] with loading, success, or error states.
     *
     * @param channelId The unique identifier of the channel to load.
     */
    fun loadChannelProfile(channelId: String) {
        // Prevent reloading if we already have the correct channel's data
        if (_profileUiState.value.channel?.id == channelId && !_profileUiState.value.isLoading) {
            return
        }

        viewModelScope.launch {
            _profileUiState.value = ProfileUiState(isLoading = true)
            try {
                // Fetch the specific channel from the repository
                val channel = repository.getChannels().first().find { it.id == channelId }

                if (channel != null) {
                    _profileUiState.value = ProfileUiState(
                        isLoading = false,
                        // No need for adaptation anymore, just pass the channel object.
                        channel = channel,
                        mediaCount = 123, // Placeholder
                        isMuted = false    // Placeholder
                    )
                } else {
                    _profileUiState.value = ProfileUiState(error = "Channel not found.")
                }
            } catch (e: Exception) {
                _profileUiState.value =
                    ProfileUiState(error = "Failed to load profile: ${e.message}")
            }
        }
    }

    /**
     * Asynchronously loads the profile for a specific user.
     *
     * Avoids reloading if the same user's profile is already present.
     * It fetches data from the repository and updates the [userProfileUiState].
     *
     * @param userId The unique identifier of the user to load.
     */
    fun loadUserProfile(userId: String) {
        if (_userProfileUiState.value.user?.id == userId && !_userProfileUiState.value.isLoading) {
            return
        }
        viewModelScope.launch {
            _userProfileUiState.value = UserProfileUiState(isLoading = true)
            try {
                // In a real app, you would fetch a User from a different repository function
                // For now, we adapt a "Chat" contact to a "User" profile
                val chatContact = repository.getChats().first().find { it.id == userId }

                if (chatContact != null) {
                    val user = User(
                        id = chatContact.id,
                        name = chatContact.userName,
                        handle = "@${chatContact.userName.replace(" ", "").lowercase()}",
                        avatarRes = chatContact.profileRes,
                        bannerUrl = "",
                        followerCount = 0, // Regular users might not have followers
                        isVerified = false,
                        bio = "Hey there! I am using Hau." // Placeholder bio
                    )
                    _userProfileUiState.value = UserProfileUiState(
                        isLoading = false,
                        user = user
                    )
                } else {
                    _userProfileUiState.value = UserProfileUiState(error = "User not found.")
                }
            } catch (e: Exception) {
                _userProfileUiState.value =
                    UserProfileUiState(error = "Failed to load user profile: ${e.message}")
            }
        }
    }

    /**
     * Toggles the mute state for the current channel profile.
     *
     * This is a local operation in the current implementation but would typically
     * involve a repository call to persist the change.
     */
    fun toggleMute() {
        val currentState = _profileUiState.value
        _profileUiState.value = currentState.copy(isMuted = !currentState.isMuted)
        // In a real app, you would persist this change via the repository
    }
}
