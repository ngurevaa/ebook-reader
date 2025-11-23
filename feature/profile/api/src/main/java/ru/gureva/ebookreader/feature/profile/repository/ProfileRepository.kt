package ru.gureva.ebookreader.feature.profile.repository

import ru.gureva.ebookreader.feature.profile.model.Photo
import ru.gureva.ebookreader.feature.profile.model.ProfileData

interface ProfileRepository {
    suspend fun getData(): ProfileData
    suspend fun logout()
    suspend fun updateData(data: ProfileData)
    suspend fun uploadPhoto(photo: Photo): String
}
