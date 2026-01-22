package org.hau.project.ui.appTwo.domain.models

import org.jetbrains.compose.resources.DrawableResource

/**
 * A centralized data class representing a user's profile information.
 * This can be used for both standard user profiles and channel-like profiles.
 */
data class User(
    val id: String,
    val name: String,
    val handle: String, // e.g., @MindfulMoments
    val avatarRes: DrawableResource,
    val bannerUrl: String? = "https://images.pexels.com/photos/1051075/pexels-photo-1051075.jpeg",
    val followerCount: Long = 0,
    val isVerified: Boolean = false,
    val creationDate: String? = null,
    val bio: String? = null
)
