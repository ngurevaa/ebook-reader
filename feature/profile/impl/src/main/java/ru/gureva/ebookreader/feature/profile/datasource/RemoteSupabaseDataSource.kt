package ru.gureva.ebookreader.feature.profile.datasource

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import ru.gureva.ebookreader.feature.profile.model.Photo

class RemoteSupabaseDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun uploadPhotoToStorage(photo: Photo): String {
        val storagePath = "${photo.userId}/${System.currentTimeMillis()}${photo.fileName}"
        supabaseClient.storage
            .from(STORAGE_NAME)
            .upload(
                path = storagePath,
                data = photo.data
            )

        val fileUrl = supabaseClient.storage
            .from(STORAGE_NAME)
            .publicUrl(storagePath)
        return fileUrl
    }

    companion object {
        private const val STORAGE_NAME = "books"
    }
}
