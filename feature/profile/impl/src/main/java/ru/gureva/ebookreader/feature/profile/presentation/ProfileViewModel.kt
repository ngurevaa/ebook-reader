package ru.gureva.ebookreader.feature.profile.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.FileUtil
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.profile.R
import ru.gureva.ebookreader.feature.profile.model.Photo
import ru.gureva.ebookreader.feature.profile.model.ProfileData
import ru.gureva.ebookreader.feature.profile.usecase.GetProfileDataUseCase
import ru.gureva.ebookreader.feature.profile.usecase.LogoutUseCase
import ru.gureva.ebookreader.feature.profile.usecase.UpdateProfileDataUseCase
import ru.gureva.ebookreader.feature.profile.usecase.UploadPhotoUseCase

class ProfileViewModel : ContainerHost<ProfileState, ProfileSideEffect>, ViewModel(), KoinComponent {
    override val container = container<ProfileState, ProfileSideEffect>(ProfileState())

    private val resourceManager: ResourceManager by inject()
    private val fileUtil: FileUtil by inject()
    private val getProfileDataUseCase: GetProfileDataUseCase by inject()
    private val logoutUseCase: LogoutUseCase by inject()
    private val updateProfileDataUseCase: UpdateProfileDataUseCase by inject()
    private val uploadPhotoUseCase: UploadPhotoUseCase by inject()

    fun dispatch(event: ProfileEvent) {
        when (event) {
            ProfileEvent.EditProfile -> editProfile()
            ProfileEvent.Logout -> logout()
            ProfileEvent.SaveChanges -> saveChanges()
            is ProfileEvent.UpdateUsername -> updateUsername(event.username)
            is ProfileEvent.UpdateEmail -> updateEmail(event.email)
            ProfileEvent.LoadProfileData -> loadProfileData()
            is ProfileEvent.SelectImage -> selectImage(event.image)
        }
    }

    private fun selectImage(uri: Uri) = intent {
        reduce { state.copy(editedImageUri = uri) }
    }

    private fun logout() = intent {
        runCatching { logoutUseCase() }
            .onSuccess { postSideEffect(ProfileSideEffect.NavigateToLogin) }
            .onFailure { postSideEffect(ProfileSideEffect.ShowSnackbar(
                resourceManager.getString(R.string.logout_error)
            )) }
    }

    private fun saveChanges() = intent {
        val uri = state.editedImageUri
        val userId = Firebase.auth.currentUser?.uid

        val photoUrl = if (uri != null && userId != null) {
            val photo = Photo(
                data = fileUtil.getFileBytesFromUri(uri) ?: return@intent,
                userId = userId,
                fileName = fileUtil.getFileName(uri) ?: return@intent
            )
            runCatching { uploadPhotoUseCase(photo) }
                .fold(
                    onSuccess = { it },
                    onFailure = { state.imageUrl }
                )
        } else {
            state.imageUrl
        }

        runCatching {
            updateProfileDataUseCase(ProfileData(state.editedUsername, state.editedEmail, photoUrl))
        }
            .onSuccess {
                reduce { state.copy(
                    username = state.editedUsername,
                    email = state.editedEmail,
                    isEditMode = false,
                    imageUrl = photoUrl
                ) }
            }
            .onFailure {
                reduce { state.copy(isEditMode = false) }
                postSideEffect(ProfileSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.data_updating_error)
                ))
            }
    }

    private fun updateUsername(username: String) = intent {
        reduce { state.copy(editedUsername = username) }
    }

    private fun updateEmail(email: String) = intent {
        reduce { state.copy(editedEmail = email) }
    }

    private fun editProfile() = intent {
        reduce { state.copy(
            editedUsername = state.username,
            editedEmail = state.email,
            isEditMode = true
        ) }
    }

    private fun loadProfileData() = intent {
        runCatching { getProfileDataUseCase() }
            .onSuccess { data ->
                reduce { state.copy(
                    username = data.username,
                    email = data.email,
                    imageUrl = data.photoUrl
                ) }
            }
            .onFailure {
                postSideEffect(ProfileSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.data_loading_error)
                ))
            }
    }
}
