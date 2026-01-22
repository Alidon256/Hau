package org.hau.project.models


import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

/**
 * Represents a single chat conversation in a user's chat list.
 *
 * @param id The unique identifier for the chat.
 * @param userName The name of the other user in the chat.
 * @param profileRes The drawable resource for the user's profile picture.
 * @param lastMessage The content of the most recent message in the chat.
 * @param timestamp The time the last message was sent.
 * @param unreadCount The number of unread messages in the chat.
 * @param isOnline Indicates if the user is currently online.
 * @param isSent Indicates if the last message was sent by the current user.
 * @param hasSeen Indicates if the current user has seen the last message.
 */
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

/**
 * Identifies the sender of a message, distinguishing between the current user (`Me`)
 * and the other participant (`Them`).
 */
enum class MessageSender {
    Me, Them
}


/**
 * Data class for displaying recommended channels to the user.
 *
 * @param id The unique ID of the channel.
 * @param channelRes The resource ID for the channel's display image.
 * @param channelName The name of the recommended channel.
 * @param followerCount The number of followers the channel has.
 * @param isVerified True if the channel is verified, false otherwise.
 */
data class RecommendedChannels(
    val id: String,
    val channelRes: DrawableResource,
    val channelName: String,
    val followerCount: Long,
    val isVerified: Boolean
)

/**
 * Represents a communication channel, which can be a group or a broadcast list.
 *
 * @param id The unique identifier for the channel.
 * @param channelRes The drawable resource for the channel's avatar.
 * @param message The last message sent in the channel.
 * @param attachmentType The type of attachment in the last message, if any.
 * @param timestamp The time the last message was sent.
 * @param unreadMessages The number of unread messages in the channel.
 * @param channelName The name of the channel.
 * @param isRead Whether the last message has been read.
 * @param followerCount The number of followers or subscribers to the channel.
 * @param isVerified Whether the channel is officially verified.
 */
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

/**
 * Specifies the type of media attachment in a message.
 */
enum class AttachmentType{
    VIDEO,AUDIO,IMAGE
}

/**
 * Holds information about a recent call, either incoming or outgoing.
 *
 * @param callerName The name of the person who was on the call.
 * @param callerImageRes The resource ID for the caller's profile image.
 * @param callTimes The number of times the call was made or received.
 * @param timestamp The time the call took place.
 * @param isSender True if the current user initiated the call, false if they received it.
 * @param callType The type of call (AUDIO or VIDEO).
 */
data class  RecentCalls(
    val callerName: String,
    val callerImageRes: DrawableResource,
    val callTimes: Int,
    val timestamp: String,
    val isSender: Boolean,
    val callType: org.hau.project.models.CallType
)

/**
 * Defines the type of a call.
 */
enum class CallType{
    AUDIO,VIDEO
}

/**
 * Represents an action that can be taken from the call screen, like starting a new call or creating a community.
 *
 * @param icon The icon representing the action.
 * @param actionText The text label for the action.
 * @param isCommunity True if the action is related to a community, false otherwise.
 */
data class CallActions(
    val icon: ImageVector,
    val actionText: String,
    val isCommunity: Boolean
)

/**
 * Represents a single message within a chat.
 * This class is serializable to allow for easy storage or transmission.
 *
 * @param id The unique identifier for the message.
 * @param sender The sender of the message, either `Me` or `Them`.
 * @param text The textual content of the message.
 * @param time The timestamp of the message.
 * @param imageUrl The URL of an image attachment, if any.
 * @param videoUrl The URL of a video attachment, if any.
 * @param audioUrl The URL of an audio attachment, if any.
 * @param documentUrl The URL of a document attachment, if any.
 * @param documentName The name of the attached document.
 * @param documentSize The size of the attached document.
 * @param status The delivery status of the message.
 */
@Serializable
data class Message(
    val id: String,
    val sender: org.hau.project.models.MessageSender,
    val text: String? = null,
    val time: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val audioUrl: String? = null,
    val documentUrl: String? = null,
    val documentName: String? = null,
    val documentSize: String? = null,
    val status: org.hau.project.models.MessageStatus? = null
)


/**
 * Represents the delivery status of a message.
 */
enum class MessageStatus { SENT, DELIVERED, READ }

/**
 * A summarized view of a chat, suitable for a preview list.
 *
 * @param id The unique identifier for the chat.
 * @param user The user associated with the chat.
 * @param lastMessage The last message exchanged.
 * @param time The timestamp of the last message.
 * @param unreadCount The number of unread messages.
 * @param isVerified Whether the user is verified.
 * @param isMuted Whether the chat is muted.
 * @param isPinned Whether the chat is pinned.
 * @param isArchived Whether the chat is archived.
 * @param mediaType The type of media in the last message (e.g., "photo", "video").
 * @param mediaUrl The URL of the media in the last message.
 * @param mediaDuration The duration of the media, if applicable.
 */
data class ChatPreview(
    val id: String = "",
    val user: org.hau.project.models.StoryUser = _root_ide_package_.org.hau.project.models.StoryUser(),
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
    constructor() : this("",
        _root_ide_package_.org.hau.project.models.StoryUser(), "", "", 0, false, false, false, false, null, null, null)
}

/**
 * Represents a user, particularly in the context of stories or statuses.
 *
 * @param id The unique identifier for the user.
 * @param name The user's display name.
 * @param avatarUrl The URL for the user's avatar image.
 * @param hasStory Whether the user has an active story.
 * @param status The user's current status message.
 */
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

/**
 * Represents a new contact that can be added to the user's chat list.
 *
 * @param id The unique ID for the contact.
 * @param contactRes The drawable resource for the contact's avatar.
 * @param contactName The name of the contact.
 * @param contactDesc A short description or status for the contact.
 * @param contact The contact information (e.g., phone number or username).
 * @param isOwner Whether the contact is the owner of a group or channel.
 */
data class NewContacts(
    val id: String,
    val contactRes: DrawableResource,
    val contactName: String?,
    val contactDesc: String,
    val contact: String,
    val isOwner: Boolean
)

/**
 * Represents a single option in a poll.
 *
 * @param id The unique identifier for the option.
 * @param text The text of the poll option.
 * @param icon A string identifier for an optional icon.
 * @param votes The number of votes this option has received.
 * @param isSelected Whether the current user has selected this option.
 */
class PollOption(
    val id: Int,
    val text: String,
    val icon: String,
    val votes: Int,
    val isSelected: Boolean = false
)

/**
 * Represents an item in a channel's message feed. This can be a text message, a link, an image, or a poll.
 *
 * @param id The unique identifier for the message item.
 * @param text The text content of the message, if any.
 * @param link A URL included in the message, if any.
 * @param time The timestamp of the message.
 * @param reactions A map of reaction emojis to their counts.
 * @param isPoll Whether this message item is a poll.
 * @param poll The poll data, if this item is a poll.
 * @param image The URL of an image in the message, if any.
 */
data class MessageItem(
    val id: String,
    val text: String? = null,
    val link: String? = null,
    val time: String = "",
    val reactions: Map<String, Int> = emptyMap(),
    val isPoll: Boolean = false,
    val poll: org.hau.project.models.Poll? = null,
    val image: String? = null
)

/**
 * Represents a poll within a message.
 *
 * @param question The question being asked in the poll.
 * @param options The list of [PollOption]s that users can vote on.
 */
data class Poll(
    val question: String,
    val options: List<org.hau.project.models.PollOption>
)
