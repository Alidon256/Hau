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

// State now directly uses the 'Channels' model
data class ProfileUiState(
    val isLoading: Boolean = true,
    val channel: Channels? = null, // Use Channels model directly
    val isMuted: Boolean = false,
    val mediaCount: Int = 0,
    val bannerUrl: String = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
    val error: String? = null
)

data class UserProfileUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val isMuted: Boolean = false,
    val mediaCount: Int = 0,
    val bannerUrl: String = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
    val error: String? = null
)

class ProfileViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(
        ProfileUiState(
            isLoading = true
        )
    )
    val profileUiState = _profileUiState.asStateFlow()

    private val _userProfileUiState = MutableStateFlow(UserProfileUiState())
    val userProfileUiState = _userProfileUiState.asStateFlow()

    fun loadChannelProfile(channelId: String) {
        // Prevent reloading if we already have the correct channel's data
        if (_profileUiState.value.channel?.id == channelId && !_profileUiState.value.isLoading) {
            return
        }

        viewModelScope.launch {
            _profileUiState.value =
                ProfileUiState(isLoading = true)
            try {
                // Fetch the specific channel from the repository
                val channel = repository.getChannels().first().find { it.id == channelId }

                if (channel != null) {
                    _profileUiState.value =
                        ProfileUiState(
                            isLoading = false,
                            // No need for adaptation anymore, just pass the channel object.
                            channel = channel,
                            mediaCount = 123, // Placeholder
                            isMuted = false    // Placeholder
                        )
                } else {
                    _profileUiState.value =
                        ProfileUiState(error = "Channel not found.")
                }
            } catch (e: Exception) {
                _profileUiState.value =
                    ProfileUiState(error = "Failed to load profile: ${e.message}")
            }
        }
    }
    fun loadUserProfile(userId: String) {
        if (_userProfileUiState.value.user?.id == userId && !_userProfileUiState.value.isLoading) {
            return
        }
        viewModelScope.launch {
            _userProfileUiState.value =
                UserProfileUiState(isLoading = true)
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
                    _userProfileUiState.value =
                        UserProfileUiState(
                            isLoading = false,
                            user = user
                        )
                } else {
                    _userProfileUiState.value =
                        UserProfileUiState(error = "User not found.")
                }
            } catch (e: Exception) {
                _userProfileUiState.value =
                    UserProfileUiState(error = "Failed to load user profile: ${e.message}")
            }
        }
    }

    fun toggleMute() {
        val currentState = _profileUiState.value
        _profileUiState.value = currentState.copy(isMuted = !currentState.isMuted)
        // In a real app, you would persist this change via the repository
    }
}