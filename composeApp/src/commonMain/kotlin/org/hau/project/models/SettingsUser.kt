package org.hau.project.models

import org.jetbrains.compose.resources.DrawableResource

// Data class to hold user information for the settings screen
data class SettingsUser(
    val contactName: String,
    val contactRes: DrawableResource,
    val contactId: String,
    val contactDesc: String,
    val contact: String
)