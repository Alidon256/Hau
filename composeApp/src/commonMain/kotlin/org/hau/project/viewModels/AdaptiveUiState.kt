package org.hau.project.viewModels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdaptiveUiState {
    // This now holds the ID of the selected item (chat or channel)
    private val _selectedDetailId = MutableStateFlow<String?>(null)
    val selectedDetailId: StateFlow<String?> = _selectedDetailId

    // This holds the ID for the profile pane
    private val _selectedProfileId = MutableStateFlow<String?>(null)
    val selectedProfileId: StateFlow<String?> = _selectedProfileId

    // A new state to track IF the current selection is a channel.
    private val _isChannelSelected = MutableStateFlow(false)
    val isChannelSelected: StateFlow<Boolean> = _isChannelSelected

    fun selectChat(chatId: String) {
        _selectedDetailId.value = chatId
        _selectedProfileId.value = chatId
        _isChannelSelected.value = false // Mark selection as a private chat
    }

    fun selectChannel(channelId: String) {
        _selectedDetailId.value = channelId
        _selectedProfileId.value = channelId
        _isChannelSelected.value = true // Mark selection as a channel
    }

    fun selectProfile(profileId: String, isChannel: Boolean) {
        _selectedProfileId.value = profileId
        _isChannelSelected.value = isChannel
    }

    fun clearSelections() {
        _selectedDetailId.value = null
        _selectedProfileId.value = null
        _isChannelSelected.value = false
    }
}
