package org.hau.project.ui.appTwo.data.repositories

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
import org.hau.project.ui.appTwo.domain.models.AttachmentType
import org.hau.project.ui.appTwo.domain.models.CallActions
import org.hau.project.ui.appTwo.domain.models.CallType
import org.hau.project.ui.appTwo.domain.models.Channels
import org.hau.project.ui.appTwo.domain.models.Chat
import org.hau.project.ui.appTwo.domain.models.Message
import org.hau.project.ui.appTwo.domain.models.MessageItem
import org.hau.project.ui.appTwo.domain.models.MessageSender
import org.hau.project.ui.appTwo.domain.models.MessageStatus
import org.hau.project.ui.appTwo.domain.models.NewContacts
import org.hau.project.ui.appTwo.domain.models.Poll
import org.hau.project.ui.appTwo.domain.models.PollOption
import org.hau.project.ui.appTwo.domain.models.RecentCalls
import org.hau.project.ui.appTwo.domain.models.RecommendedChannels
import kotlin.math.round

// In a real app, this would be injected using a dependency injection framework like Koin or Hilt.
open class ChatRepository {

    // Simulates fetching a list of all chats from a data source.
    open fun getChats(): Flow<List<Chat>> {
        return flowOf(
            listOf(
                Chat("1", "Mugumya Ali", Res.drawable.story_3, "See you tomorrow!", "11:30 AM", 2, true, true, false),
                Chat("2", "Jane Doe", Res.drawable.image_two, "Okay, that really sounds good. Hope  to see you once again", "10:15 AM", 0, false, true, true),
                Chat("3", "John Smith", Res.drawable.image_three, "Can you send me the file?", "Yesterday", 5, true, true, false),
                Chat("4", "Caroline Varsaha", Res.drawable.story_4, "I've sent the report. You can check it out in your Email", "Yesterday", 0, true, true, true),
                Chat("5", "Sebastian Rudiger", Res.drawable.story_1, "Let's catch up later.", "Monday", 1, false, true, false),
                Chat("6", "Really Rinah", Res.drawable.story_2, "Working on it now! Will be done soon.", "Monday", 0, true, true, true),
                Chat("7", "Darshan Patelchi", Res.drawable.grattitude, "Happy Birthday! 🎉", "Sunday", 0, false, true, true),
                Chat("8", "Mohammed Arnold", Res.drawable.image_two, "Check out this link: ...", "01/12/2026", 0, false, true, true),
                Chat("9", "Tamara Schipchinskaya", Res.drawable.grattitude, "typing...", "11:35 AM", 0, true, false, false),
                Chat("10", "Ariana Amberline", Res.drawable.image_last, "Can we reschedule our meeting?", "10:55 AM", 3, true, true, false),
                Chat("11", "Jerry Johnson", Res.drawable.story_5, "Thanks for your help!", "01/11/2026", 0, false, true, true)
            )
        )
    }

    // Simulates fetching messages for a specific chat ID.
    fun getMessagesForChat(chatId: String): Flow<List<Message>> {
        // Here you would have logic to return messages based on the chatId
        val messagesDatabase = mapOf(
            "1" to listOf( // Conversation with Mugumya Ali
                Message("m1_1", sender = MessageSender.Them, text = "Hey, are we still on for lunch tomorrow?", time = "10:05 AM", status = MessageStatus.READ),
                Message("m1_2", sender = MessageSender.Me, text = "Absolutely! Where are we meeting?", time = "10:06 AM", status = MessageStatus.READ),
                Message("m1_3", sender = MessageSender.Them, text = "Let's go to that new Italian place downtown. I've heard great things.", time = "10:07 AM", status = MessageStatus.READ),
                Message("m1_4", sender = MessageSender.Them, text = "I'll send you the location.", time = "10:07 AM", status = MessageStatus.READ),
                Message("m1_5", sender = MessageSender.Me, text = "Perfect! What time works for you?", time = "10:08 AM", status = MessageStatus.READ),
                Message("m1_6", sender = MessageSender.Them, text = "How about 12:30 PM?", time = "10:09 AM", status = MessageStatus.READ),
                Message("m1_7", sender = MessageSender.Me, text = "Sounds great. I'll put it in my calendar.", time = "10:10 AM", status = MessageStatus.DELIVERED),
                Message("m1_8", sender = MessageSender.Me, text = "See you tomorrow!", time = "11:30 AM", status = MessageStatus.SENT)
            ),
            "2" to listOf( // Conversation with Jane Doe
                Message("m2_1", sender = MessageSender.Them, text = "Did you get the project update I sent?", time = "9:30 AM", status = MessageStatus.READ),
                Message("m2_2", sender = MessageSender.Me, text = "Yes, I just saw it. Looks good, thanks for the hard work!", time = "9:45 AM", status = MessageStatus.READ),
                Message("m2_3", sender = MessageSender.Them, text = "Great! Let me know if you have any feedback.", time = "9:46 AM", status = MessageStatus.READ),
                Message("m2_4", sender = MessageSender.Me, text = "Will do. I'll review it in detail this afternoon.", time = "9:50 AM", status = MessageStatus.READ),
                Message("m2_5", sender = MessageSender.Them, text = "Okay, sounds good.", time = "10:15 AM", status = MessageStatus.READ)
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
                Message("def_1", sender = MessageSender.Them, text = "Hello!", time = "1:00 PM", status = MessageStatus.READ),
                Message("def_2", sender = MessageSender.Me, text = "Hi there! How can I help you?", time = "1:01 PM", status = MessageStatus.SENT)
            )
        )
        // Return the list for the given chatId, or a default list if not found.
        return flowOf(messagesDatabase[chatId] ?: messagesDatabase["default"] ?: emptyList())

    }

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
                ),
                Channels(channelName = "Really Rinah", channelRes = Res.drawable.story_2,  timestamp = "01/22/2026", attachmentType = null, message = "Today the World remembers the ocassion that happened when the people rejected the Presidents decision", unreadMessages = 10,isRead = false,followerCount = 323000L,id = "ab"),
                Channels(channelName = "Sebastian Rudiger", channelRes = Res.drawable.story_1,  timestamp = "01/21/2026", attachmentType = AttachmentType.AUDIO, unreadMessages = 167, message = "Announcing the new Update of Mindset Pulse💕",isRead = false,followerCount = 723000L,id = "ac"),
                Channels(channelName = "Caroline Varsaha", channelRes = Res.drawable.story_4,  timestamp = "01/21/2026",attachmentType = null, unreadMessages = 0, message = "A new Report by the world health organisation says that people who ovr smile always have there life time lifted",isRead = true,followerCount = 193000L,id = "all"),
                Channels(channelName = "Darshan Patelchi", channelRes = Res.drawable.story_5,  timestamp = "01/20/2026",attachmentType = AttachmentType.IMAGE, unreadMessages = 234, message = "The Stake Holders of Tesla today had a general meeting and decided that they were gonna give elon a new pack of $1T",isRead = false,followerCount = 183000,id = "ag"),
                Channels(channelName = "Mohammed Arnold", channelRes = Res.drawable.image_two, timestamp = "01/19/2026",attachmentType = AttachmentType.VIDEO, unreadMessages = 0, message = "You will have to truely believ me on some facts in a way that attaining success in most cases it always have to come with had work." ,isRead = true,followerCount = 0L,id = "ax"),
            )
        )

    }
    fun getRecommended() : Flow<List<RecommendedChannels>>{
        return flowOf(
            listOf(
                RecommendedChannels(channelName = "Gratitude", channelRes = Res.drawable.grattitude, isVerified = false, followerCount = 145087),
                RecommendedChannels(channelName = "Mental Health", channelRes = Res.drawable.mentalhealth, isVerified = true, followerCount = 100000),
                RecommendedChannels(channelName = "Strong Hope", channelRes = Res.drawable.stronghope, isVerified = false, followerCount = 150489),
                RecommendedChannels(channelName = "Caroline Varsaha", channelRes = Res.drawable.story_4, isVerified = true, followerCount = 12000),
            )
        )
    }
    fun getCallActions() : Flow<List<CallActions>>{
        return flowOf(
            listOf(
                CallActions(Icons.Outlined.Call, "Call", isCommunity = false),
                CallActions( Icons.Outlined.CalendarMonth,"Schedule", isCommunity = false),
                CallActions( Icons.Outlined.KeyboardCommandKey,"Keypad", isCommunity = false),
                CallActions( Icons.Outlined.KeyboardCommandKey,"Gratitude", isCommunity = true),
                CallActions( Icons.Outlined.FavoriteBorder,"Favourites", isCommunity = false)
            )
        )
    }
    fun getRecentCalls() : Flow<List<RecentCalls>>{
        return flowOf(
            listOf(
                RecentCalls(callerName = "Mugumya Ali", callerImageRes = Res.drawable.story_3, callTimes = 2, timestamp = "Just now", isSender = true, callType = CallType.VIDEO),
                RecentCalls(callerName = "Really Rinah", callerImageRes = Res.drawable.story_2, callTimes = 0, timestamp = "7rd, January, 02:11", isSender = false, callType = CallType.AUDIO),
                RecentCalls(callerName = "Sebastian Rudiger", callerImageRes = Res.drawable.story_1, callTimes = 5, timestamp = "6rd January, 01:54", isSender = true, callType = CallType.AUDIO),
                RecentCalls(callerName = "Caroline Varsaha", callerImageRes = Res.drawable.story_4, callTimes = 2, timestamp = "6rd January, 03:08", isSender = false, callType = CallType.VIDEO),
                RecentCalls(callerName = "Darshan Patelchi", callerImageRes = Res.drawable.story_5, callTimes = 0, timestamp = "5rd January, 04:00", isSender = false, callType = CallType.AUDIO),
                RecentCalls(callerName = "Mohammed Arnold", callerImageRes = Res.drawable.image_two, callTimes = 0, timestamp = "4rd January, 05:45", isSender = true, callType = CallType.VIDEO),
                RecentCalls(callerName = "Tamara Schipchinskaya", callerImageRes = Res.drawable.image_three, callTimes = 2, timestamp = "4rd January, 06:04", isSender = false, callType = CallType.AUDIO),
                RecentCalls(callerName = "Ariana Amberline", callerImageRes = Res.drawable.image_last, callTimes = 3, timestamp = "3rd January, 05:36", isSender = true, callType = CallType.VIDEO),
                RecentCalls(callerName = "Ali Stack", callerImageRes = Res.drawable.story_3, callTimes = 1, timestamp = "2rd January, 01:47", isSender = false, callType = CallType.AUDIO),
                RecentCalls(callerName = "Jerry Johnson", callerImageRes = Res.drawable.story_5, callTimes = 2, timestamp = "1rd January, 01:09", isSender = true, callType = CallType.VIDEO)
            )
        )
    }
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
                NewContacts(contactName = "Really Rinah", contactRes = Res.drawable.story_2, isOwner = false, contactDesc = "Just always keep moving",contact = "+256 755935663",id = "2"),
                NewContacts(contactName = null, contactRes = Res.drawable.story_1, isOwner = false, contactDesc = "All yours 🥰",contact = "+256 784337244",id = "3"),
                NewContacts(contactName = "Caroline Varsaha", contactRes = Res.drawable.story_4, isOwner = false, contactDesc = "Keep going!",contact = "+253 785000244",id = "4"),
                NewContacts(contactName = null, contactRes = Res.drawable.image_now, isOwner = false, contactDesc = "When its just meant for you",contact = "+111 744430244",id = "5"),
                NewContacts(contactName = "Caroline Varsaha", contactRes = Res.drawable.image_two, isOwner = false, contactDesc = "Staying just outside the baber shop sooner or later you'll get your hair cut!",contact = "+234 777730244",id = "6"),
            )
        )
    }
    // In ChatRepository.kt

    fun getChannelMessages(channelId: String): Flow<List<MessageItem>> {
        // Mock data - in a real app, this would be a specific query
        val pollOptions = listOf(
            PollOption(1, "Emissions reduction", "⚡️", 401),
            PollOption(2, "Carbon removal", "🌱", 262),
            PollOption(3, "Tech investment", "🏢", 656, isSelected = true),
            PollOption(4, "Government policy", "🏛️", 360)
        )
        val messages = listOf(
            MessageItem(
                id = "msg1",
                text = "Both steep emissions reductions and large-scale carbon removal. Which approach is most critical to keeping AI growth compatible with climate targets?",
                link = "https://weforum.org/stories/2025/10/ai-carbon-debt-carbon-removal/...",
                time = "19:40",
                reactions = mapOf("👍" to 38, "❤️" to 12, "🙏" to 5)
            ),
            MessageItem(
                id = "poll1",
                isPoll = true,
                poll = Poll("What is the key to scaling AI sustainably?", pollOptions),
                time = "19:40",
                reactions = mapOf("❤️" to 101, "👍" to 23, "😂" to 4)
            ),
            MessageItem(
                id = "date_divider",
                text = "8 December 2025",
                time = "19:40",
                reactions = mapOf("👍" to 38, "❤️" to 12, "🙏" to 5)
            ),
            MessageItem(
                id = "img1",
                image = "https://picsum.photos/seed/picsum2/800/600", // Placeholder image
                time = "12:30",
                reactions = mapOf("😮" to 77)
            ),
            MessageItem(
                id = "msg2",
                text = "Our latest quarterly report is now available for review. Please find the summary of key findings attached.",
                time = "12:31",
                reactions = mapOf("👍" to 15, "🚀" to 8)
            ),
            MessageItem(
                id = "msg3",
                text = "Following up on the report, this image outlines our projected growth for the next fiscal year. We welcome your feedback.",
                image = "https://picsum.photos/seed/growth/800/600",
                time = "12:32",
                reactions = mapOf("📈" to 25, "😮" to 7)
            ),
            MessageItem(
                id = "msg4",
                text = "Reminder: The all-hands meeting is scheduled for 3:00 PM today. Please ensure you have reviewed the agenda beforehand.",
                time = "12:33",
                reactions = mapOf("🙏" to 12)
            ),
            MessageItem(
                id = "msg5",
                text = "Sharing a snapshot from our recent successful team-building event. Great to see everyone collaborating effectively!",
                image = "https://picsum.photos/seed/teamevent/800/600",
                time = "12:34",
                reactions = mapOf("❤️" to 34, "👏" to 19)
            )

        )
        return flowOf(messages)
    }

}
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
