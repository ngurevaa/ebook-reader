package ru.gureva.ebookreader.feature.profile.presentation

import android.net.Uri

sealed interface ProfileEvent {
    data object LoadProfileData : ProfileEvent
    data object Logout : ProfileEvent
    data object EditProfile : ProfileEvent
    data object SaveChanges : ProfileEvent
    data class UpdateUsername(val username: String) : ProfileEvent
    data class UpdateEmail(val email: String): ProfileEvent
    data class SelectImage(val image: Uri) : ProfileEvent
}
