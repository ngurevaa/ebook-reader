package ru.gureva.ebookreader.feature.profile.presentation

data class ProfileState(
    val username: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val isEditMode: Boolean = false,
    val editedUsername: String = "",
    val editedEmail: String = ""
)
