package org.hau.project.viewModels

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.hau.project.data.repositories.ChatRepository
import org.hau.project.models.CallActions
import org.hau.project.models.Channels
import org.hau.project.models.Chat
import org.hau.project.models.Message
import org.hau.project.models.MessageItem
import org.hau.project.models.NewContacts
import org.hau.project.models.RecentCalls
import org.hau.project.models.RecommendedChannels

// UI State for the Chat List Screen
data class ChatListUiState(
    val chats: List<Chat> = emptyList(),
    val newContacts: List<NewContacts> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// UI State for the Chat Detail Screen
data class ChatDetailUiState(
    val messages: List<Message> = emptyList(),
    val messagesNewContacts: List<Message> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val callActions: List<CallActions> = emptyList(),
    val recentCalls: List<RecentCalls> = emptyList(),
    val channels: List<Channels> = emptyList(),
    val channelInfo: Channels? = null,
    val channelMessages: List<MessageItem> = emptyList(),
    val recommendedChannels: List<RecommendedChannels> = emptyList(),
    val currentChannel: Channels? = null,// To display user info in the TopAppBar
    val currentChat: Chat? = null // To display user info in the TopAppBar
)

class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chatListState = MutableStateFlow(ChatListUiState())
    val chatListState: StateFlow<ChatListUiState> = _chatListState.asStateFlow()

    private val _chatDetailState = MutableStateFlow(ChatDetailUiState())
    val chatDetailState: StateFlow<ChatDetailUiState> = _chatDetailState.asStateFlow()

    private val _channelsState = MutableStateFlow(ChatDetailUiState())
    val channelState: StateFlow<ChatDetailUiState> = _channelsState.asStateFlow()

    private val _recommendedState = MutableStateFlow(ChatDetailUiState())
    val recommendedState: StateFlow<ChatDetailUiState> = _recommendedState.asStateFlow()

    private val _callActionsState = MutableStateFlow(ChatDetailUiState())
    val callActionsState: StateFlow<ChatDetailUiState> = _callActionsState.asStateFlow()

    private val _recentCallsState = MutableStateFlow(ChatDetailUiState())
    val recentCallsState: StateFlow<ChatDetailUiState> = _recentCallsState.asStateFlow()

    private val _newContactsState = MutableStateFlow(ChatListUiState())
    val newContactsState: StateFlow<ChatListUiState> = _newContactsState.asStateFlow()

    private val _channelDetailState = MutableStateFlow(ChatDetailUiState())
    val channelDetailState: StateFlow<ChatDetailUiState> = _channelDetailState.asStateFlow()


    init {
        // Load the initial list of chats when the ViewModel is created
        loadChats()
        loadChannels()
        loadRecommendedChannels()
        loadCallActions()
        loadRecentCalls()
        loadNewContacts()
    }

    // Public function to load the list of chats
    fun loadChats() {
        viewModelScope.launch {
            _chatListState.value = _chatListState.value.copy(isLoading = true)
            chatRepository.getChats()
                .catch { e ->
                    _chatListState.value = _chatListState.value.copy(isLoading = false, error = e.message)
                }
                .collect { chats ->
                    _chatListState.value = _chatListState.value.copy(isLoading = false, chats = chats)
                }
        }
    }

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            _chatDetailState.value = _chatDetailState.value.copy(isLoading = true)

            if (_chatDetailState.value.currentChat?.id != chatId) {
                val chat = _chatListState.value.chats.find { it.id == chatId }
                _chatDetailState.value = _chatDetailState.value.copy(currentChat = chat)
            }
            //val chat = _chatListState.value.chats.find { it.id == chatId }
            //_chatDetailState.value = _chatDetailState.value.copy(currentChat = chat)

            chatRepository.getMessagesForChat(chatId)
                .catch { e ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, error = e.message)
                }
                .collect { messages ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, messages = messages)
                }
        }
    }
    fun loadNewContactMessages(chatId: String) {
        viewModelScope.launch {
            _chatDetailState.value = _chatDetailState.value.copy(isLoading = true)

            if (_chatDetailState.value.currentChat?.id != chatId) {
                val chat = _chatListState.value.chats.find { it.id == chatId }
                _chatDetailState.value = _chatDetailState.value.copy(currentChat = chat)
            }
            //val chat = _chatListState.value.chats.find { it.id == chatId }
            //_chatDetailState.value = _chatDetailState.value.copy(currentChat = chat)

            chatRepository.getMessagesForChat(chatId)
                .catch { e ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, error = e.message)
                }
                .collect { messages ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, messages = messages)
                }
        }
    }

    fun loadChannels(){
        viewModelScope.launch {
            _channelsState.value = _channelsState.value.copy(isLoading = true)

            chatRepository.getChannels()
                .catch { e ->
                    _channelsState.value = _channelsState.value.copy(isLoading = false, error = e.message)
                }
                .collect { channels ->
                    _channelsState.value = _channelsState.value.copy(isLoading = false, channels = channels)
                }
        }
    }

    fun loadRecommendedChannels(){
        viewModelScope.launch {
            _recommendedState.value = _recommendedState.value.copy(isLoading = true)

            chatRepository.getRecommended()
                .catch { e ->
                    _recommendedState.value = _recommendedState.value.copy(isLoading = false, error = e.message)
                }
                .collect { 
                    _recommendedState.value = _recommendedState.value.copy(isLoading = false,recommendedChannels = it)
                }
        }
    }

    fun loadCallActions(){
        viewModelScope.launch {
            _callActionsState.value = _callActionsState.value.copy(isLoading = true)

            chatRepository.getCallActions()
                .catch { e ->
                    _callActionsState.value = _callActionsState.value.copy(isLoading = false, error = e.message)
                }
                .collect { callActions ->
                    _callActionsState.value = _callActionsState.value.copy(isLoading = false, callActions = callActions)
                }
        }
    }

    fun loadRecentCalls(){
        viewModelScope.launch {
            _recentCallsState.value = _recentCallsState.value.copy(isLoading = true)

            chatRepository.getRecentCalls()
                .catch { e ->
                    _recentCallsState.value = _recentCallsState.value.copy(isLoading = false, error = e.message)
                }
                .collect { recent ->
                    _recentCallsState.value = _recentCallsState.value.copy(isLoading = false, recentCalls = recent)
                }
        }
    }

    fun loadNewContacts(){
        viewModelScope.launch {
            _newContactsState.value = _newContactsState.value.copy(isLoading = true)
            chatRepository.getNewContacts()
                .catch { e ->
                    _newContactsState.value =
                        _newContactsState.value.copy(isLoading = false, error = e.message)
                }
                .collect { newContacts ->
                    _newContactsState.value = _newContactsState.value.copy(isLoading = false, newContacts = newContacts)
                }
        }
    }

    fun loadChannelDetails(channelId: String) {
        viewModelScope.launch {
            _channelDetailState.value =
                _root_ide_package_.org.hau.project.viewModels.ChatDetailUiState(isLoading = true)

            // In a real app, you'd fetch both from the repository based on channelId
            val channelInfo = chatRepository.getChannels().first().find { it.id == channelId }
            val channelMessages = chatRepository.getChannelMessages(channelId).first()

            _channelDetailState.value =
                ChatDetailUiState(
                    isLoading = false,
                    channelInfo = channelInfo,
                    channelMessages = channelMessages
                )
        }
    }

    fun startChatWithNewContact(contactId: String) {
        viewModelScope.launch {
            // Find the contact details from the newContacts list
            val contact = _newContactsState.value.newContacts.find { it.id == contactId }

            if (contact != null) {
                // Create a new Chat object for the UI
                val newChat = Chat(
                    id = contact.id,
                    userName = contact.contactName ?: "Unknown",
                    profileRes = contact.contactRes,
                    lastMessage = "Start a new conversation", // Placeholder
                    timestamp = "",
                    unreadCount = 0,
                    isOnline = true, // Assume online for a new chat
                    isSent = true,
                    hasSeen = true
                )

                // Set this as the current chat for the detail screen
                _chatDetailState.value = _chatDetailState.value.copy(
                    currentChat = newChat,
                    messages = emptyList(), // Start with no messages
                    isLoading = false
                )
                // In a real app, you would also persist this new chat session
                // to your repository and backend here.
            } else {
                // Handle case where contact is not found
                _chatDetailState.value = _chatDetailState.value.copy(error = "Contact not found.")
            }
        }
    }

}
