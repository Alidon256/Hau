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

/**
 * Represents the UI state for the main chat list screen.
 *
 * @param chats The list of active [Chat] conversations.
 * @param newContacts The list of [NewContacts] that can be started.
 * @param isLoading `true` if the chat list is currently being loaded, `false` otherwise.
 * @param error An optional error message if loading fails.
 */
data class ChatListUiState(
    val chats: List<Chat> = emptyList(),
    val newContacts: List<NewContacts> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * Represents the UI state for the detailed view of a single chat, channel, or call history.
 *
 * This state is shared across multiple screens that show detailed information.
 *
 * @param messages The list of [Message]s in a private chat.
 * @param messagesNewContacts The messages for a newly started chat.
 * @param isLoading `true` when data for the screen is being loaded.
 * @param error An optional error message if data loading fails.
 * @param callActions A list of [CallActions] available to the user.
 * @param recentCalls A list of [RecentCalls] made or received by the user.
 * @param channels A list of [Channels] the user is part of.
 * @param channelInfo Information about a specific channel being viewed.
 * @param channelMessages The messages within a specific channel.
 * @param recommendedChannels A list of [RecommendedChannels] to suggest to the user.
 * @param currentChannel The currently viewed channel, used to display info in the TopAppBar.
 * @param currentChat The currently viewed chat, used to display user info in the TopAppBar.
 */
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

/**
 * The main ViewModel for the chat functionality of the application.
 *
 * This ViewModel is responsible for loading and managing the state for chat lists, individual chats,
 * channels, calls, and contacts. It uses a [ChatRepository] to fetch data and exposes the UI state
 * through various [StateFlow]s.
 *
 * @param chatRepository The repository providing access to all chat-related data.
 */
class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chatListState = MutableStateFlow(ChatListUiState())
    /**
     * StateFlow for the main chat list UI.
     */
    val chatListState: StateFlow<ChatListUiState> = _chatListState.asStateFlow()

    private val _chatDetailState = MutableStateFlow(ChatDetailUiState())
    /**
     * StateFlow for the chat detail screen, including messages and user info.
     */
    val chatDetailState: StateFlow<ChatDetailUiState> = _chatDetailState.asStateFlow()

    private val _channelsState = MutableStateFlow(ChatDetailUiState())
    /**
     * StateFlow for the list of channels.
     */
    val channelState: StateFlow<ChatDetailUiState> = _channelsState.asStateFlow()

    private val _recommendedState = MutableStateFlow(ChatDetailUiState())
    /**
     * StateFlow for recommended channels.
     */
    val recommendedState: StateFlow<ChatDetailUiState> = _recommendedState.asStateFlow()

    private val _callActionsState = MutableStateFlow(ChatDetailUiState())
    /**
     * StateFlow for available call actions.
     */
    val callActionsState: StateFlow<ChatDetailUiState> = _callActionsState.asStateFlow()

    private val _recentCallsState = MutableStateFlow(ChatDetailUiState())
    /**
     * StateFlow for the list of recent calls.
     */
    val recentCallsState: StateFlow<ChatDetailUiState> = _recentCallsState.asStateFlow()

    private val _newContactsState = MutableStateFlow(ChatListUiState())
    /**
     * StateFlow for the list of new contacts.
     */
    val newContactsState: StateFlow<ChatListUiState> = _newContactsState.asStateFlow()

    private val _channelDetailState = MutableStateFlow(ChatDetailUiState())
    /**
     * StateFlow for the details of a specific channel.
     */
    val channelDetailState: StateFlow<ChatDetailUiState> = _channelDetailState.asStateFlow()


    init {
        // Load all initial data when the ViewModel is created
        loadChats()
        loadChannels()
        loadRecommendedChannels()
        loadCallActions()
        loadRecentCalls()
        loadNewContacts()
    }

    /**
     * Asynchronously loads the list of chats from the repository and updates the UI state.
     */
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

    /**
     * Loads the messages for a specific chat and updates the detail UI state.
     *
     * @param chatId The ID of the chat to load messages for.
     */
    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            _chatDetailState.value = _chatDetailState.value.copy(isLoading = true)

            if (_chatDetailState.value.currentChat?.id != chatId) {
                val chat = _chatListState.value.chats.find { it.id == chatId }
                _chatDetailState.value = _chatDetailState.value.copy(currentChat = chat)
            }

            chatRepository.getMessagesForChat(chatId)
                .catch { e ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, error = e.message)
                }
                .collect { messages ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, messages = messages)
                }
        }
    }

    /**
     * Loads messages for a new contact that has just been engaged in a chat.
     *
     * @param chatId The ID of the new contact's chat.
     */
    fun loadNewContactMessages(chatId: String) {
        viewModelScope.launch {
            _chatDetailState.value = _chatDetailState.value.copy(isLoading = true)

            if (_chatDetailState.value.currentChat?.id != chatId) {
                val chat = _chatListState.value.chats.find { it.id == chatId }
                _chatDetailState.value = _chatDetailState.value.copy(currentChat = chat)
            }

            chatRepository.getMessagesForChat(chatId)
                .catch { e ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, error = e.message)
                }
                .collect { messages ->
                    _chatDetailState.value = _chatDetailState.value.copy(isLoading = false, messages = messages)
                }
        }
    }

    /**
     * Loads the list of all channels and updates the corresponding UI state.
     */
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

    /**
     * Loads the list of recommended channels to suggest to the user.
     */
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

    /**
     * Loads the set of actions a user can take related to calls (e.g., start a new call).
     */
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

    /**
     * Loads the user's recent call history.
     */
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

    /**
     * Loads the list of new contacts that the user can start a conversation with.
     */
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

    /**
     * Loads the details for a specific channel, including its messages.
     *
     * @param channelId The ID of the channel to load.
     */
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

    /**
     * Initiates a new chat session with a contact from the 'new contacts' list.
     *
     * @param contactId The ID of the contact to start a chat with.
     */
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
