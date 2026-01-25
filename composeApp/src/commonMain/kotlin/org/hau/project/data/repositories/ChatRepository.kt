package org.hau.project.data.repositories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardCommandKey
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.alidon
import hau.composeapp.generated.resources.grattitude
import hau.composeapp.generated.resources.image_last
import hau.composeapp.generated.resources.image_now
import hau.composeapp.generated.resources.image_three
import hau.composeapp.generated.resources.image_two
import hau.composeapp.generated.resources.mentalhealth
import hau.composeapp.generated.resources.story_1
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import hau.composeapp.generated.resources.story_4
import hau.composeapp.generated.resources.story_5
import hau.composeapp.generated.resources.stronghope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.hau.project.models.AttachmentType
import org.hau.project.models.CallActions
import org.hau.project.models.CallType
import org.hau.project.models.Channels
import org.hau.project.models.Chat
import org.hau.project.models.Message
import org.hau.project.models.MessageItem
import org.hau.project.models.MessageSender
import org.hau.project.models.MessageStatus
import org.hau.project.models.NewContacts
import org.hau.project.models.Poll
import org.hau.project.models.PollOption
import org.hau.project.models.RecentCalls
import org.hau.project.models.RecommendedChannels
import kotlin.math.round

/**
 * A repository responsible for providing chat-related data from a simulated data source.
 *
 * In a real-world application, this class would be responsible for fetching data from a remote server,
 * a local database, or a combination of both. It abstracts the data source from the ViewModel,
 * allowing for easier testing and maintenance.
 *
 * This repository uses `Flow` to expose data, enabling reactive updates in the UI when the
 * underlying data changes.
 */
open class ChatRepository {

    /**
     * Retrieves a flow of all chat conversations.
     *
     * This method simulates fetching a list of [Chat] objects from a data source and wrapping it
     * in a [Flow]. In a real application, this would likely query a database or an API endpoint.
     *
     * @return A [Flow] emitting a list of [Chat]s.
     */
    open fun getChats(): Flow<List<Chat>> {
        return flowOf(
            listOf(
                Chat(
                    "1",
                    "Mugumya Ali",
                    Res.drawable.story_3,
                    "See you tomorrow!",
                    "11:30 AM",
                    2,
                    true,
                    true,
                    false
                ),
                Chat(
                    "2",
                    "Jane Doe",
                    Res.drawable.image_two,
                    "Okay, that really sounds good. Hope  to see you once again",
                    "10:15 AM",
                    0,
                    false,
                    true,
                    true
                ),
                Chat(
                    "3",
                    "John Smith",
                    Res.drawable.image_three,
                    "Can you send me the file?",
                    "Yesterday",
                    5,
                    true,
                    true,
                    false
                ),
                Chat(
                    "4",
                    "Caroline Varsaha",
                    Res.drawable.story_4,
                    "I've sent the report. You can check it out in your Email",
                    "Yesterday",
                    0,
                    true,
                    true,
                    true
                ),
                Chat(
                    "5",
                    "Sebastian Rudiger",
                    Res.drawable.story_1,
                    "Let's catch up later.",
                    "Monday",
                    1,
                    false,
                    true,
                    false
                ),
                Chat(
                    "6",
                    "Really Rinah",
                    Res.drawable.story_2,
                    "Working on it now! Will be done soon.",
                    "Monday",
                    0,
                    true,
                    true,
                    true
                ),
                Chat(
                    "7",
                    "Darshan Patelchi",
                    Res.drawable.grattitude,
                    "Happy Birthday! 🎉",
                    "Sunday",
                    0,
                    false,
                    true,
                    true
                ),
                Chat(
                    "8",
                    "Mohammed Arnold",
                    Res.drawable.image_two,
                    "Check out this link: ...",
                    "01/12/2026",
                    0,
                    false,
                    true,
                    true
                ),
                Chat(
                    "9",
                    "Tamara Schipchinskaya",
                    Res.drawable.grattitude,
                    "typing...",
                    "11:35 AM",
                    0,
                    true,
                    false,
                    false
                ),
                Chat(
                    "10",
                    "Ariana Amberline",
                    Res.drawable.image_last,
                    "Can we reschedule our meeting?",
                    "10:55 AM",
                    3,
                    true,
                    true,
                    false
                ),
                Chat(
                    "11",
                    "Jerry Johnson",
                    Res.drawable.story_5,
                    "Thanks for your help!",
                    "01/11/2026",
                    0,
                    false,
                    true,
                    true
                )
            )
        )
    }

    /**
     * Fetches the messages for a specific chat conversation.
     *
     * This function simulates a lookup in a message database based on the provided `chatId`.
     * If the chat ID is not found, it returns a default or empty list of messages.
     *
     * @param chatId The unique identifier of the chat to retrieve messages for.
     * @return A [Flow] emitting a list of [Message]s for the specified chat.
     */
    fun getMessagesForChat(chatId: String): Flow<List<Message>> {
        // Here you would have logic to return messages based on the chatId
        val messagesDatabase = mapOf(
            "1" to listOf( // Conversation with Mugumya Ali
                Message(
                    "m1_1",
                    sender = MessageSender.Them,
                    text = "Hey, are we still on for lunch tomorrow?",
                    time = "10:05 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m1_2",
                    sender = MessageSender.Me,
                    text = "Absolutely! Where are we meeting?",
                    time = "10:06 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m1_3",
                    sender = MessageSender.Them,
                    text = "Let's go to that new Italian place downtown. I've heard great things.",
                    time = "10:07 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m1_4",
                    sender = MessageSender.Them,
                    text = "I'll send you the location.",
                    time = "10:07 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m1_5",
                    sender = MessageSender.Me,
                    text = "Perfect! What time works for you?",
                    time = "10:08 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m1_6",
                    sender = MessageSender.Them,
                    text = "How about 12:30 PM?",
                    time = "10:09 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m1_7",
                    sender = MessageSender.Me,
                    text = "Sounds great. I'll put it in my calendar.",
                    time = "10:10 AM",
                    status = MessageStatus.DELIVERED
                ),
                Message(
                    "m1_8",
                    sender = MessageSender.Me,
                    text = "See you tomorrow!",
                    time = "11:30 AM",
                    status = MessageStatus.SENT
                )
            ),
            "2" to listOf( // Conversation with Jane Doe
                Message(
                    "m2_1",
                    sender = MessageSender.Them,
                    text = "Did you get the project update I sent?",
                    time = "9:30 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m2_2",
                    sender = MessageSender.Me,
                    text = "Yes, I just saw it. Looks good, thanks for the hard work!",
                    time = "9:45 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m2_3",
                    sender = MessageSender.Them,
                    text = "Great! Let me know if you have any feedback.",
                    time = "9:46 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m2_4",
                    sender = MessageSender.Me,
                    text = "Will do. I'll review it in detail this afternoon.",
                    time = "9:50 AM",
                    status = MessageStatus.READ
                ),
                Message(
                    "m2_5",
                    sender = MessageSender.Them,
                    text = "Okay, sounds good.",
                    time = "10:15 AM",
                    status = MessageStatus.READ
                )
            ),
            "3" to listOf(
                // Conversation with John Smith
                Message(
                    "m3_1",
                    sender = MessageSender.Them,
                    text = "Hey, quick question.",
                    time = "Yesterday",
                    status = MessageStatus.READ
                ),
                Message(
                    "m3_2",
                    sender = MessageSender.Me,
                    text = "Sure, what's up?",
                    time = "Yesterday",
                    status = MessageStatus.READ
                ),
                Message(
                    "m3_3",
                    sender = MessageSender.Them,
                    text = "I'm having trouble with the login API. It keeps giving me a 401 error.",
                    time = "Yesterday",
                    status = MessageStatus.READ
                ),
                Message(
                    "m3_4",
                    sender = MessageSender.Me,
                    text = "Ah, did you remember to include the new auth token in the header?",
                    time = "Yesterday",
                    status = MessageStatus.READ
                ),
                Message(
                    "m3_5",
                    sender = MessageSender.Me,
                    text = "We updated the security protocol last week.",
                    time = "Yesterday",
                    status = MessageStatus.READ
                ),
                Message(
                    "m3_6",
                    sender = MessageSender.Them,
                    text = "Oh, that's probably it! I completely forgot.",
                    time = "Yesterday",
                    status = MessageStatus.READ
                ),
                Message(
                    "m3_7",
                    sender = MessageSender.Them,
                    text = "Can you send me the file with the new token details?",
                    time = "Yesterday",
                    status = MessageStatus.READ
                ),
            ),
            "default" to listOf(
                Message(
                    "def_1",
                    sender = MessageSender.Them,
                    text = "Hello!",
                    time = "1:00 PM",
                    status = MessageStatus.READ
                ),
                Message(
                    "def_2",
                    sender = MessageSender.Me,
                    text = "Hi there! How can I help you?",
                    time = "1:01 PM",
                    status = MessageStatus.SENT
                )
            )
        )
        // Return the list for the given chatId, or a default list if not found.
        return flowOf(messagesDatabase[chatId] ?: messagesDatabase["default"] ?: emptyList())

    }

    /**
     * Retrieves a list of all available channels.
     *
     * @return A [Flow] emitting a list of [Channels].
     */
    fun getChannels() : Flow<List<Channels>>{
        return flowOf(
            listOf(
                Channels(
                    channelName = "Alidon",
                    channelRes = Res.drawable.alidon,
                    timestamp = "Just now",
                    message = "Today the United States of America started its operation in Yemen",
                    attachmentType = null,
                    unreadMessages = 234,
                    isRead = false,
                    followerCount = 123000L,
                    id = "aa",
                    isVerified = true,
                ),
                Channels(
                    channelName = "Really Rinah",
                    channelRes = Res.drawable.story_2,
                    timestamp = "01/22/2026",
                    attachmentType = null,
                    message = "Today the World remembers the ocassion that happened when the people rejected the Presidents decision",
                    unreadMessages = 10,
                    isRead = false,
                    followerCount = 323000L,
                    id = "ab",
                    isVerified = false,
                ),
                Channels(
                    channelName = "Sebastian Rudiger",
                    channelRes = Res.drawable.story_1,
                    timestamp = "01/21/2026",
                    attachmentType = AttachmentType.AUDIO,
                    unreadMessages = 167,
                    message = "Announcing the new Update of Mindset Pulse💕",
                    isRead = false,
                    followerCount = 723000L,
                    id = "ac",
                    isVerified = true,
                ),
                Channels(
                    channelName = "Caroline Varsaha",
                    channelRes = Res.drawable.story_4,
                    timestamp = "01/21/2026",
                    attachmentType = null,
                    unreadMessages = 0,
                    message = "A new Report by the world health organisation says that people who ovr smile always have there life time lifted",
                    isRead = true,
                    followerCount = 193000L,
                    id = "all",
                    isVerified = false,
                ),
                Channels(
                    channelName = "Darshan Patelchi",
                    channelRes = Res.drawable.story_5,
                    timestamp = "01/20/2026",
                    attachmentType = AttachmentType.IMAGE,
                    unreadMessages = 234,
                    message = "The Stake Holders of Tesla today had a general meeting and decided that they were gonna give elon a new pack of $1T",
                    isRead = false,
                    followerCount = 183000,
                    id = "ag",
                    isVerified = true,
                ),
                Channels(
                    channelName = "Mohammed Arnold",
                    channelRes = Res.drawable.image_two,
                    timestamp = "01/19/2026",
                    attachmentType = AttachmentType.VIDEO,
                    unreadMessages = 0,
                    message = "You will have to truely believ me on some facts in a way that attaining success in most cases it always have to come with had work.",
                    isRead = true,
                    followerCount = 0L,
                    id = "ax",
                    isVerified = false,
                ),
            )
        )

    }

    /**
     * Retrieves a list of recommended channels for the user to join.
     *
     * @return A [Flow] emitting a list of [RecommendedChannels].
     */
    fun getRecommended() : Flow<List<RecommendedChannels>>{
        return flowOf(
            listOf(
                RecommendedChannels(
                    channelName = "Gratitude",
                    channelRes = Res.drawable.grattitude,
                    isVerified = false,
                    followerCount = 145087,
                    id = "1"
                ),
                RecommendedChannels(
                    channelName = "Mental Health",
                    channelRes = Res.drawable.mentalhealth,
                    isVerified = true,
                    followerCount = 100000,
                    id = "2"
                ),
                RecommendedChannels(
                    channelName = "Strong Hope",
                    channelRes = Res.drawable.stronghope,
                    isVerified = false,
                    followerCount = 150489,
                    id = "3"
                ),
                RecommendedChannels(
                    channelName = "Caroline Varsaha",
                    channelRes = Res.drawable.story_4,
                    isVerified = true,
                    followerCount = 12000,
                    id = "4"
                ),
            )
        )
    }

    /**
     * Retrieves a list of actions that can be performed related to calls.
     *
     * @return A [Flow] emitting a list of [CallActions].
     */
    fun getCallActions() : Flow<List<CallActions>>{
        return flowOf(
            listOf(
                CallActions(
                    Icons.Outlined.Call,
                    "Call",
                    isCommunity = false
                ),
                CallActions(
                    Icons.Outlined.CalendarMonth,
                    "Schedule",
                    isCommunity = false
                ),
                CallActions(
                    Icons.Outlined.KeyboardCommandKey,
                    "Keypad",
                    isCommunity = false
                ),
                CallActions(
                    Icons.Outlined.KeyboardCommandKey,
                    "Gratitude",
                    isCommunity = true
                ),
                CallActions(
                    Icons.Outlined.FavoriteBorder,
                    "Favourites",
                    isCommunity = false
                )
            )
        )
    }

    /**
     * Retrieves a list of recent calls made or received by the user.
     *
     * @return A [Flow] emitting a list of [RecentCalls].
     */
    fun getRecentCalls() : Flow<List<RecentCalls>>{
        return flowOf(
            listOf(
                RecentCalls(
                    callerName = "Mugumya Ali",
                    callerImageRes = Res.drawable.story_3,
                    callTimes = 2,
                    timestamp = "Just now",
                    isSender = true,
                    callType = CallType.VIDEO
                ),
                RecentCalls(
                    callerName = "Really Rinah",
                    callerImageRes = Res.drawable.story_2,
                    callTimes = 0,
                    timestamp = "7rd, January, 02:11",
                    isSender = false,
                    callType = CallType.AUDIO
                ),
                RecentCalls(
                    callerName = "Sebastian Rudiger",
                    callerImageRes = Res.drawable.story_1,
                    callTimes = 5,
                    timestamp = "6rd January, 01:54",
                    isSender = true,
                    callType = CallType.AUDIO
                ),
                RecentCalls(
                    callerName = "Caroline Varsaha",
                    callerImageRes = Res.drawable.story_4,
                    callTimes = 2,
                    timestamp = "6rd January, 03:08",
                    isSender = false,
                    callType = CallType.VIDEO
                ),
                RecentCalls(
                    callerName = "Darshan Patelchi",
                    callerImageRes = Res.drawable.story_5,
                    callTimes = 0,
                    timestamp = "5rd January, 04:00",
                    isSender = false,
                    callType = CallType.AUDIO
                ),
                RecentCalls(
                    callerName = "Mohammed Arnold",
                    callerImageRes = Res.drawable.image_two,
                    callTimes = 0,
                    timestamp = "4rd January, 05:45",
                    isSender = true,
                    callType = CallType.VIDEO
                ),
                RecentCalls(
                    callerName = "Tamara Schipchinskaya",
                    callerImageRes = Res.drawable.image_three,
                    callTimes = 2,
                    timestamp = "4rd January, 06:04",
                    isSender = false,
                    callType = CallType.AUDIO
                ),
                RecentCalls(
                    callerName = "Ariana Amberline",
                    callerImageRes = Res.drawable.image_last,
                    callTimes = 3,
                    timestamp = "3rd January, 05:36",
                    isSender = true,
                    callType = CallType.VIDEO
                ),
                RecentCalls(
                    callerName = "Ali Stack",
                    callerImageRes = Res.drawable.story_3,
                    callTimes = 1,
                    timestamp = "2rd January, 01:47",
                    isSender = false,
                    callType = CallType.AUDIO
                ),
                RecentCalls(
                    callerName = "Jerry Johnson",
                    callerImageRes = Res.drawable.story_5,
                    callTimes = 2,
                    timestamp = "1rd January, 01:09",
                    isSender = true,
                    callType = CallType.VIDEO
                )
            )
        )
    }

    /**
     * Retrieves a list of new contacts that the user can start a conversation with.
     *
     * @return A [Flow] emitting a list of [NewContacts].
     */
    fun getNewContacts() : Flow<List<NewContacts>> {
        return flowOf(
            listOf(
                NewContacts(
                    contactName = "Mugumya Ali",
                    contactRes = Res.drawable.story_3,
                    isOwner = true,
                    contactDesc = "Message yourself",
                    contact = "+256 785330244",
                    id = "1"
                ),
                NewContacts(
                    contactName = "Really Rinah",
                    contactRes = Res.drawable.story_2,
                    isOwner = false,
                    contactDesc = "Just always keep moving",
                    contact = "+256 755935663",
                    id = "2"
                ),
                NewContacts(
                    contactName = null,
                    contactRes = Res.drawable.story_1,
                    isOwner = false,
                    contactDesc = "All yours 🥰",
                    contact = "+256 784337244",
                    id = "3"
                ),
                NewContacts(
                    contactName = "Caroline Varsaha",
                    contactRes = Res.drawable.story_4,
                    isOwner = false,
                    contactDesc = "Keep going!",
                    contact = "+253 785000244",
                    id = "4"
                ),
                NewContacts(
                    contactName = null,
                    contactRes = Res.drawable.image_now,
                    isOwner = false,
                    contactDesc = "When its just meant for you",
                    contact = "+111 744430244",
                    id = "5"
                ),
                NewContacts(
                    contactName = "Caroline Varsaha",
                    contactRes = Res.drawable.image_two,
                    isOwner = false,
                    contactDesc = "Staying just outside the baber shop sooner or later you'll get your hair cut!",
                    contact = "+234 777730244",
                    id = "6"
                ),
            )
        )
    }

    /**
     * Fetches the messages for a specific channel.
     *
     * This is a mock implementation that returns a static list of [MessageItem]s, including text,
     * images, and a poll. In a real app, this would query a data source for the channel's content.
     *
     * @param channelId The ID of the channel to fetch messages for.
     * @return A [Flow] emitting a list of [MessageItem]s.
     */
    /**
     * Fetches the messages for a specific channel.
     * High-quality "Top Notch" implementation with rich media and detailed engagement.
     */
    fun getChannelMessages(channelId: String): Flow<List<MessageItem>> {
        val pollOptions = listOf(
            PollOption(1, "Emissions reduction", "⚡️", 1204),
            PollOption(2, "Carbon removal", "🌱", 842),
            PollOption(3, "Tech investment", "🏢", 2156, isSelected = true),
            PollOption(4, "Policy reform", "🏛️", 940)
        )

        val messages = listOf(
            MessageItem(
                id = "msg_high_tech",
                text = "The convergence of Quantum Computing and Generative AI is expected to redefine cybersecurity by 2026. Here is a look at the projected hardware infrastructure.",
                image = "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&q=80&w=1200",
                time = "14:20",
                reactions = mapOf("🚀" to 142, "🧠" to 89, "🔥" to 45)
            ),
            MessageItem(
                id = "msg_community",
                text = "Community check-in: How are we feeling about the new 'Hau' interface design? Cast your vote below! 👇",
                time = "14:15",
                reactions = mapOf("✨" to 56)
            ),
            MessageItem(
                id = "poll_sustainability",
                isPoll = true,
                poll = Poll(
                    "Which area should our dev team prioritize for the Q1 2026 Sustainability Roadmap?",
                    pollOptions
                ),
                time = "14:10",
                reactions = mapOf("❤️" to 312, "🙌" to 44)
            ),

            MessageItem(
                id = "divider_today",
                text = "Today",
                time = "12:00"
            ),
            MessageItem(
                id = "msg_architecture",
                text = "Visualizing the new HQ expansion. We are aiming for a carbon-neutral footprint using modular timber construction.",
                image = "https://images.unsplash.com/photo-1518005020251-6fb201b287dd?auto=format&fit=crop&q=80&w=1000",
                time = "11:45",
                reactions = mapOf("🏗️" to 92, "🌿" to 120, "😍" to 34)
            ),
            MessageItem(
                id = "msg_team",
                text = "A quick snapshot from the global engineering summit in Tokyo. The energy here is incredible! 🇯🇵",
                image = "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&q=80&w=1000",
                time = "09:30",
                reactions = mapOf("🤝" to 215, "🍱" to 12)
            ),

            MessageItem(
                id = "divider_yesterday",
                text = "Yesterday",
                time = "23:59"
            ),
            MessageItem(
                id = "msg_report_link",
                text = "Our 'Future of Work 2026' whitepaper is officially live. Download the PDF to see how remote-first cultures are evolving.",
                link = "https://hau.project.io/reports/future-of-work-2026",
                time = "18:20",
                reactions = mapOf("📄" to 45, "📈" to 33)
            ),
            MessageItem(
                id = "msg_workspace_inspo",
                text = "Deep work setup of the week. Minimalist, ergonomic, and highly functional.",
                image = "https://images.unsplash.com/photo-1497215728101-856f4ea42174?auto=format&fit=crop&q=80&w=1000",
                time = "16:45",
                reactions = mapOf("💻" to 188, "☕" to 67, "👏" to 22)
            ),
            MessageItem(
                id = "msg_motivational",
                text = "Success is not final, failure is not fatal: it is the courage to continue that counts.",
                time = "08:15",
                reactions = mapOf("💯" to 540, "🙏" to 120)
            )
        )
        return flowOf(messages)
    }
}

/**
 * Formats a given number into a more readable string representation.
 *
 * This function converts large numbers into a shortened format with a suffix (K for thousands,
 * M for millions). For numbers less than 1,000, it returns the number as a string.
 *
 * @param count The numeric value to be formatted.
 * @return A formatted string (e.g., "1.2K", "5.8M") or the original number as a string.
 */
fun formatCount(count: Long): String {
    fun oneDecimal(value: Float): String {
        val rounded = round(value * 10f) / 10f
        return rounded.toString()
    }
    return when {
        count >= 1_000_000 -> "${oneDecimal(count / 1_000_000f)}M"
        count >= 1_000 -> "${oneDecimal(count / 1_000f)}K"
        else -> count.toString()
    }
}
