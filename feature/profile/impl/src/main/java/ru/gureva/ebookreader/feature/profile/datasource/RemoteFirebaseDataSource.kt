package ru.gureva.ebookreader.feature.profile.datasource

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import ru.gureva.ebookreader.feature.profile.model.ProfileData
import androidx.core.net.toUri

class RemoteFirebaseDataSource {
    val auth = Firebase.auth

    fun getProfileData(): ProfileData {
        val user = auth.currentUser

        return ProfileData(
            username = user?.displayName ?: "",
            email = user?.email ?: "",
            photoUrl = user?.photoUrl.toString()
        )
    }

    fun logoutFromProfile() {
        auth.signOut()
    }

    suspend fun updateProfileData(data: ProfileData) {
        val profileUpdates = userProfileChangeRequest {
            displayName = data.username
            photoUri = data.photoUrl.toUri()
        }

        auth.currentUser
            ?.updateProfile(profileUpdates)
            ?.await()

        auth.currentUser
            ?.updateEmail(data.email)
            ?.await()
    }
}
