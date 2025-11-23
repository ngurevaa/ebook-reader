package ru.gureva.ebookreader.feature.profile.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.feature.profile.datasource.RemoteFirebaseDataSource
import ru.gureva.ebookreader.feature.profile.datasource.RemoteSupabaseDataSource
import ru.gureva.ebookreader.feature.profile.model.Photo
import ru.gureva.ebookreader.feature.profile.model.ProfileData

class ProfileRepositoryImpl(
    private val remoteFirebaseDataSource: RemoteFirebaseDataSource,
    private val remoteSupabaseDataSource: RemoteSupabaseDataSource
) : ProfileRepository {
    override suspend fun getData(): ProfileData {
        return withContext(Dispatchers.IO) {
            remoteFirebaseDataSource.getProfileData()
        }
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            remoteFirebaseDataSource.logoutFromProfile()
        }
    }

    override suspend fun updateData(data: ProfileData) {
        withContext(Dispatchers.IO) {
            remoteFirebaseDataSource.updateProfileData(data)
        }
    }

    override suspend fun uploadPhoto(photo: Photo): String {
        return withContext(Dispatchers.IO) {
            remoteSupabaseDataSource.uploadPhotoToStorage(photo)
        }
    }
}
