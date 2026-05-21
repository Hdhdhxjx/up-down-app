package com.updown.app.data

data class RunningDownload(
    val id: String,
    val title: String,
    val sizeMb: Int,
    val quality: String,
    val progressPercent: Int,
    val speedText: String,
    val source: String,
    val thumbnailUrl: String
)

data class LibraryItem(
    val id: String,
    val title: String,
    val sizeText: String,
    val format: String,
    val quality: String,
    val dateText: String,
    val durationText: String,
    val thumbnailUrl: String
)

data class UserProfile(
    val name: String,
    val email: String,
    val rank: Int,
    val points: Int,
    val successfulInvites: Int,
    val streakDays: Int,
    val planBadge: String
)

data class LeaderboardEntry(
    val rank: Int,
    val name: String,
    val points: Int,
    val invites: Int,
    val isElite: Boolean = false,
    val isCurrentUser: Boolean = false
)

data class SettingOption(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val highlight: Boolean = false
)

data class ResolutionOption(
    val label: String,
    val sizeText: String
)
