package org.hau.project.models

import org.jetbrains.compose.resources.DrawableResource

/**
 * Data class representing the user information displayed on the settings screen.
 *
 * @property contactName The display name of the user.
 * @property contactRes The [DrawableResource] for the user's profile picture.
 * @property contactId The unique identifier for the user.
 * @property contactDesc A short description or status for the user (e.g., "Available").
 * @property contact The user's contact information, such as a phone number.
 */
data class SettingsUser(
    val contactName: String,
    val contactRes: DrawableResource,
    val contactId: String,
    val contactDesc: String,
    val contact: String
)
