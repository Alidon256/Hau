package org.hau.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import hau.composeapp.generated.resources.story_2
import hau.composeapp.generated.resources.story_3
import org.hau.project.models.NewContacts
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A list item representing a contact available to start a new chat.
 *
 * This component displays the contact's avatar, their name (falling back to their
 * contact identifier if the name is missing), a brief description, and an
 * optional indicator if the contact is the current user.
 *
 * @param contacts The [NewContacts] data model containing user identity and metadata.
 */
@Composable
fun NewContactsItem(contacts: NewContacts) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile Image.
        Image(
            painter = painterResource(contacts.contactRes),
            contentDescription = contacts.contactName,
            modifier = Modifier.clip(CircleShape).size(50.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Display name or phone number fallback.
                Text(
                    text = if (contacts.contactName.isNullOrEmpty()) contacts.contact else contacts.contactName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                // Appends "(You)" if the contact entry represents the current user profile.
                if (contacts.isOwner) {
                    Text(
                        text = "(You)",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            // User description or status snippet.
            Text(
                text = contacts.contactDesc,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun NewContactsItemLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface {
            NewContactsItem(
                NewContacts("1", hau.composeapp.generated.resources.Res.drawable.story_3, "Mugumya Ali", "Developer", "+256...", true)
            )
        }
    }
}

@Preview
@Composable
fun NewContactsItemDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface {
            NewContactsItem(
                NewContacts("2", hau.composeapp.generated.resources.Res.drawable.story_2, null, "Available", "+256...", false)
            )
        }
    }
}