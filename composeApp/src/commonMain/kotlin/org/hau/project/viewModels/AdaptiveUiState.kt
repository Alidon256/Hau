package org.hau.project.viewModels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages the UI state for the Adaptive (Multi-pane) layout.
 *
 * This state holder coordinates which content is shown in the Detail and Info panes
 * when the application is running on large screens (tablets, desktops). It allows
 * for seamless switching between Private Chats and Channels while maintaining
 * the correct profile information in the side panels.
 */
class AdaptiveUiState {
    
    // The ID of the item (Chat or Channel) currently visible in the middle Detail Pane.
    private val _selectedDetailId = MutableStateFlow<String?>(null)
    val selectedDetailId: StateFlow<String?> = _selectedDetailId

    // The ID of the user or channel currently visible in the right-most Info Pane.
    private val _selectedProfileId = MutableStateFlow<String?>(null)
    val selectedProfileId: StateFlow<String?> = _selectedProfileId

    // Flag to determine if the selected item is a Channel (true) or a Private Chat (false).
    private val _isChannelSelected = MutableStateFlow(false)
    val isChannelSelected: StateFlow<Boolean> = _isChannelSelected

    /**
     * Updates state to show a private chat in the Detail Pane.
     * @param chatId The unique identifier for the selected chat.
     */
    fun selectChat(chatId: String) {
        _selectedDetailId.value = chatId
        _selectedProfileId.value = chatId
        _isChannelSelected.value = false
    }

    /**
     * Updates state to show a channel feed in the Detail Pane.
     * @param channelId The unique identifier for the selected channel.
     */
    fun selectChannel(channelId: String) {
        _selectedDetailId.value = channelId
        _selectedProfileId.value = channelId
        _isChannelSelected.value = true
    }

    /**
     * Specifically updates the Info Pane content without changing the Detail Pane.
     * Used when clicking on a user's avatar within a chat feed.
     */
    fun selectProfile(profileId: String, isChannel: Boolean) {
        _selectedProfileId.value = profileId
        _isChannelSelected.value = isChannel
    }

    /**
     * Resets all selections, returning the Detail and Info panes to their placeholder states.
     */
    fun clearSelections() {
        _selectedDetailId.value = null
        _selectedProfileId.value = null
        _isChannelSelected.value = false
    }
}
