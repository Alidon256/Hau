package org.hau.project.ui.appTwo.domain.models


import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

// Represents a single chat in the chat list
data class Chat(
    val id: String,
    val userName: String,
    val profileRes: DrawableResource,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int,
    val isOnline: Boolean,
    val isSent: Boolean,
    val hasSeen: Boolean
)

enum class MessageSender {
    Me, Them
}


data class RecommendedChannels(
    val id: String,
    val channelRes: DrawableResource,
    val channelName: String,
    val followerCount: Long,
    val isVerified: Boolean
)

data class Channels(
    val id: String,
    val channelRes: DrawableResource,
    val message: String,
    val attachmentType: AttachmentType?,
    val timestamp: String,
    val unreadMessages: Int,
    val channelName:String,
    val isRead:Boolean,
    val followerCount: Long,
    val isVerified: Boolean
)

enum class AttachmentType{
    VIDEO,AUDIO,IMAGE
}
data class  RecentCalls(
    val callerName: String,
    val callerImageRes: DrawableResource,
    val callTimes: Int,
    val timestamp: String,
    val isSender: Boolean,
    val callType: CallType
)

enum class CallType{
    AUDIO,VIDEO
}

data class CallActions(
    val icon: ImageVector,
    val actionText: String,
    val isCommunity: Boolean
)

@Serializable
data class Message(
    val id: String,
    val sender: MessageSender,
    val text: String? = null,
    val time: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val audioUrl: String? = null,
    val documentUrl: String? = null,
    val documentName: String? = null,
    val documentSize: String? = null,
    val status: MessageStatus? = null
)


enum class MessageStatus { SENT, DELIVERED, READ }

data class ChatPreview(
    val id: String = "",
    val user: StoryUser = StoryUser(),
    val lastMessage: String = "",
    val time: String = "",
    val unreadCount: Int = 0,
    val isVerified: Boolean = false,
    val isMuted: Boolean = false,
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val mediaType: String? = null,
    val mediaUrl: String? = null,
    val mediaDuration: Long? = null
) {
    // Secondary constructor for Firestore
    constructor() : this("", StoryUser(), "", "", 0, false, false, false, false, null, null, null)
}
data class StoryUser(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    val hasStory: Boolean = false,
    val status: String? = null
) {
    // Secondary constructor for Firestore
    constructor() : this("", "", null, false, null)
}

data class NewContacts(
    val id: String,
    val contactRes: DrawableResource,
    val contactName: String?,
    val contactDesc: String,
    val contact: String,
    val isOwner: Boolean
)

class PollOption(
    val id: Int,
    val text: String,
    val icon: String,
    val votes: Int,
    val isSelected: Boolean = false
)

data class MessageItem(
    val id: String,
    val text: String? = null,
    val link: String? = null,
    val time: String = "",
    val reactions: Map<String, Int> = emptyMap(),
    val isPoll: Boolean = false,
    val poll: Poll? = null,
    val image: String? = null
)

data class Poll(
    val question: String,
    val options: List<PollOption>
)