package org.hau.project.models


import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

/**
 * Represents a single chat conversation in a user's chat list.
 *
 * @property id The unique identifier for the chat.
 * @property userName The name of the other user in the chat.
 * @property profileRes The [DrawableResource] for the user's profile picture.
 * @property lastMessage The content of the most recent message in the chat.
 * @property timestamp The time the last message was sent.
 * @property unreadCount The number of unread messages in the chat.
 * @property isOnline Indicates if the user is currently online.
 * @property isSent Indicates if the last message was sent by the current user.
 * @property hasSeen Indicates if the current user has seen the last message.
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
 * @property id The unique ID of the channel.
 * @property channelRes The [DrawableResource] for the channel's display image.
 * @property channelName The name of the recommended channel.
 * @property followerCount The number of followers the channel has.
 * @property isVerified True if the channel is verified, false otherwise.
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
 * @property id The unique identifier for the channel.
 * @property channelRes The [DrawableResource] for the channel's avatar.
 * @property message The last message sent in the channel.
 * @property attachmentType The type of attachment in the last message, if any.
 * @property timestamp The time the last message was sent.
 * @property unreadMessages The number of unread messages in the channel.
 * @property channelName The name of the channel.
 * @property isRead Whether the last message has been read.
 * @property followerCount The number of followers or subscribers to the channel.
 * @property isVerified Whether the channel is officially verified.
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
 * @property callerName The name of the person who was on the call.
 * @property callerImageRes The [DrawableResource] for the caller's profile image.
 * @property callTimes The number of times the call was made or received.
 * @property timestamp The time the call took place.
 * @property isSender True if the current user initiated the call, false if they received it.
 * @property callType The type of call ([CallType.AUDIO] or [CallType.VIDEO]).
 */
data class RecentCalls(
    val callerName: String,
    val callerImageRes: DrawableResource,
    val callTimes: Int,
    val timestamp: String,
    val isSender: Boolean,
    val callType: CallType
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
 * @property icon The [ImageVector] representing the action.
 * @property actionText The text label for the action.
 * @property isCommunity True if the action is related to a community, false otherwise.
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
 * @property id The unique identifier for the message.
 * @property sender The sender of the message, either [MessageSender.Me] or [MessageSender.Them].
 * @property text The textual content of the message.
 * @property time The timestamp of the message.
 * @property imageUrl The URL of an image attachment, if any.
 * @property videoUrl The URL of a video attachment, if any.
 * @property audioUrl The URL of an audio attachment, if any.
 * @property documentUrl The URL of a document attachment, if any.
 * @property documentName The name of the attached document.
 * @property documentSize The size of the attached document.
 * @property status The delivery status of the message.
 */
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


/**
 * Represents the delivery status of a message.
 */
enum class MessageStatus { SENT, DELIVERED, READ }

/**
 * A summarized view of a chat, suitable for a preview list.
 *
 * @property id The unique identifier for the chat.
 * @property user The user associated with the chat.
 * @property lastMessage The last message exchanged.
 * @property time The timestamp of the last message.
 * @property unreadCount The number of unread messages.
 * @property isVerified Whether the user is verified.
 * @property isMuted Whether the chat is muted.
 * @property isPinned Whether the chat is pinned.
 * @property isArchived Whether the chat is archived.
 * @property mediaType The type of media in the last message (e.g., "photo", "video").
 * @property mediaUrl The URL of the media in the last message.
 * @property mediaDuration The duration of the media, if applicable.
 */
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
    constructor() : this("",
        StoryUser(), "", "", 0, false, false, false, false, null, null, null)
}

/**
 * Represents a user, particularly in the context of stories or statuses.
 *
 * @property id The unique identifier for the user.
 * @property name The user's display name.
 * @property avatarUrl The URL for the user's avatar image.
 * @property hasStory Whether the user has an active story.
 * @property status The user's current status message.
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
 * @property id The unique ID for the contact.
 * @property contactRes The [DrawableResource] for the contact's avatar.
 * @property contactName The name of the contact.
 * @property contactDesc A short description or status for the contact.
 * @property contact The contact information (e.g., phone number or username).
 * @property isOwner Whether the contact is the owner of a group or channel.
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
 * @property id The unique identifier for the option.
 * @property text The text of the poll option.
 * @property icon A string identifier for an optional icon.
 * @property votes The number of votes this option has received.
 * @property isSelected Whether the current user has selected this option.
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
 * @property id The unique identifier for the message item.
 * @property text The text content of the message, if any.
 * @property link A URL included in the message, if any.
 * @property time The timestamp of the message.
 * @property reactions A map of reaction emojis to their counts.
 * @property isPoll Whether this message item is a poll.
 * @property poll The [Poll] data, if this item is a poll.
 * @property image The URL of an image in the message, if any.
 */
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

/**
 * Represents a poll within a message.
 *
 * @property question The question being asked in the poll.
 * @property options The list of [PollOption]s that users can vote on.
 */
data class Poll(
    val question: String,
    val options: List<PollOption>
)
