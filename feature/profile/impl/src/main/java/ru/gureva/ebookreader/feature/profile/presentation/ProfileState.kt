package ru.gureva.ebookreader.feature.profile.presentation

import android.net.Uri
import ru.gureva.ebookreader.core.util.AppLanguage

data class ProfileState(
    val username: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val isEditMode: Boolean = false,
    val editedUsername: String = "",
    val editedEmail: String = "",
    val editedImageUri: Uri? = null,
    val selectedLanguage: AppLanguage,
)
