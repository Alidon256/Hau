/*package org.hau.project.ui.appTwo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.AddIcCall
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardCommandKey
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import hau.composeapp.generated.resources.image_last
import hau.composeapp.generated.resources.image_three
import hau.composeapp.generated.resources.image_two
import hau.composeapp.generated.resources.story_1
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import hau.composeapp.generated.resources.story_4
import hau.composeapp.generated.resources.story_5
import org.hau.project.ui.appTwo.domain.models.CallActions
import org.hau.project.ui.appTwo.domain.models.CallType
import org.hau.project.ui.appTwo.domain.models.RecentCalls
import org.hau.project.ui.appTwo.viewModels.ChatViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallsScreen(
    viewModel: ChatViewModel,
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Calls",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                actions = {
                    IconButton(
                        onClick = {}
                    ){
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        onClick = {}
                    ){
                        Icon(
                            Icons.Outlined.MoreVert,
                            contentDescription = "More Options",
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary,CircleShape)
                    .size(64.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Outlined.AddIcCall,
                    contentDescription = "Add Call",
                    tint = Color.White
                )
            }
        },
        containerColor = Color.White
    ){paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                  items(getCallActions()){actions ->
                      CallsActionItem( actions)
                  }
                }
            }
            item {
                Text(
                    text = "Recent",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(getRecentCalls()){ recentCalls ->
                RecentCallsItem(recentCalls)

            }
        }

  }
}
@Composable
@Preview
fun CallsScreenPreview(){
    CallsScreen()
}

@Composable
fun CallsActionItem(
    callItem: CallActions
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.Black.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ){
            if (callItem.isCommunity){
                Image(
                    painter = painterResource(Res.drawable.grattitude),
                    contentDescription = "Community",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Icon(
                callItem.icon,
                contentDescription = callItem.actionText,
                tint = Color.Black
            )
        }
        Text(
            text = callItem.actionText,

        )
    }
}

@Composable
fun RecentCallsItem(
    recentCalls: RecentCalls
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(recentCalls.callerImageRes),
            contentDescription = recentCalls.callerName,
            modifier = Modifier.clip(CircleShape).size(60.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = recentCalls.callerName,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "(${recentCalls.callTimes})",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = recentCalls.callerName,
                    modifier = Modifier
                        .rotate(
                            if (recentCalls.isSender){
                                -45f
                            }else{
                                135f
                            }
                        ),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = recentCalls.timestamp,
                    color = Color.Black.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        IconButton(
            onClick = {},
        ){
            when(recentCalls.callType){
                CallType.AUDIO -> Icon(
                    Icons.Outlined.Call,
                    contentDescription = recentCalls.callerName,
                    tint = Color.Black.copy(alpha = .7f)
                )
                CallType.VIDEO -> Icon(
                    Icons.Outlined.Videocam,
                    contentDescription = recentCalls.callerName,
                    tint = Color.Black.copy(alpha = .7f)
                )
                else ->
                    Icon(
                        Icons.Outlined.Videocam,
                        contentDescription = recentCalls.callerName,
                        tint = Color.Black
                    )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RecentCallsItemPreview(){
    RecentCallsItem(
        RecentCalls(
            callerName = "Mugumya Ali",
            callerImageRes = Res.drawable.story_3,
            callTimes = 2,
            timestamp = "Just now",
            isSender = true,
            callType = CallType.VIDEO
        ),
    )
}
@Composable
@Preview(showBackground = true)
fun CallActionsPreview(){
    CallsActionItem(
        CallActions( Icons.Outlined.FavoriteBorder,"Favourites", isCommunity = false)
    )
}*/

package org.hau.project.ui.appTwo.ui.screens.calls

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.AddIcCall
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.Res
import hau.composeapp.generated.resources.grattitude
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import org.hau.project.ui.appTwo.data.repositories.ChatRepository
import org.hau.project.ui.appTwo.domain.models.CallActions
import org.hau.project.ui.appTwo.domain.models.CallType
import org.hau.project.ui.appTwo.domain.models.RecentCalls
import org.hau.project.ui.appTwo.viewModels.ChatViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallsScreen(viewModel: ChatViewModel) {
    val callActionsState by viewModel.callActionsState.collectAsState()
    val recentCallsState by viewModel.recentCallsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Calls",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Search, "Search Icon")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.MoreVert, "More Options")
                    }
                }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .size(64.dp)
                    .clickable {}, // Make it clickable
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.AddIcCall,
                    contentDescription = "Add Call",
                    tint = MaterialTheme.colorScheme.onPrimary // Use theme color
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background // Use theme background
    ) { paddingValues ->

        val isLoading = callActionsState.isLoading || recentCallsState.isLoading
        val error = callActionsState.error ?: recentCallsState.error

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(callActionsState.callActions) { action ->
                                CallsActionItem(action)
                            }
                        }
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                    }

                    item {
                        Text(
                            text = "Recent",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    items(recentCallsState.recentCalls) { recentCall ->
                        RecentCallsItem(recentCall)
                    }
                }
            }
        }
    }
}

@Composable
fun CallsActionItem(callItem: CallActions) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant), // Use theme color
            contentAlignment = Alignment.Center
        ) {
            if (callItem.isCommunity) {
                Image(
                    painter = painterResource(Res.drawable.grattitude),
                    contentDescription = "Community",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    callItem.icon,
                    contentDescription = callItem.actionText,
                    tint = MaterialTheme.colorScheme.primary // Use primary color for icons
                )
            }
        }
        Text(
            text = callItem.actionText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Use theme color
        )
    }
}

@Composable
fun RecentCallsItem(recentCall: RecentCalls) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {} // Make item clickable
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(recentCall.callerImageRes),
            contentDescription = recentCall.callerName,
            modifier = Modifier.clip(CircleShape).size(56.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Determine text color based on if the call was missed
                val nameColor = if (recentCall.callTimes == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                Text(
                    text = recentCall.callerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = nameColor
                )
                if (recentCall.callTimes > 1) {
                    Text(
                        text = "(${recentCall.callTimes})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nameColor
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = "Call direction",
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(if (recentCall.isSender) -45f else 135f),
                    tint = if (recentCall.isSender) Color(0xFF00C853) else MaterialTheme.colorScheme.error // Green for outgoing, red for incoming/missed
                )
                Text(
                    text = recentCall.timestamp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, // Use theme color
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        IconButton(onClick = { /* TODO: Initiate call */ }) {
            val callIcon = when (recentCall.callType) {
                CallType.AUDIO -> Icons.Outlined.Call
                CallType.VIDEO -> Icons.Outlined.Videocam
            }
            Icon(
                imageVector = callIcon,
                contentDescription = "Call ${recentCall.callerName}",
                tint = MaterialTheme.colorScheme.primary // Use primary theme color
            )
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Full Calls Screen")
@Composable
fun CallsScreenPreview() {
    val fakeRepository = ChatRepository()
    val previewViewModel = ChatViewModel(fakeRepository)
    // AppTheme {
    CallsScreen(viewModel = previewViewModel)
    // }
}

@Preview(name = "Recent Call - Outgoing")
@Composable
fun RecentCallsItemOutgoingPreview() {
    // AppTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        RecentCallsItem(
            RecentCalls(
                callerName = "Mugumya Ali",
                callerImageRes = Res.drawable.story_3,
                callTimes = 2,
                timestamp = "Just now",
                isSender = true, // Outgoing call
                callType = CallType.VIDEO
            ),
        )
    }
    // }
}

@Preview(name = "Recent Call - Missed")
@Composable
fun RecentCallsItemMissedPreview() {
    // AppTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        RecentCallsItem(
            RecentCalls(
                callerName = "Jane Doe",
                callerImageRes = Res.drawable.story_2,
                callTimes = 0, // Missed call
                timestamp = "15 minutes ago",
                isSender = false,
                callType = CallType.AUDIO
            ),
        )
    }
    // }
}

@Preview(name = "Call Action Item")
@Composable
fun CallActionsPreview() {
    // AppTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
        CallsActionItem(
            CallActions(Icons.Outlined.Call, "Call Link", isCommunity = false)
        )
    }
    // }
}
