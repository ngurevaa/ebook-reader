package ru.gureva.ebookreader.feature.bookupload.datasource

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata

class RemoteSupabaseDatasource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun uploadBookToStorage(bookMetadata: BookMetadata): String {
        val storagePath = "${bookMetadata.userId}/${System.currentTimeMillis()}${bookMetadata.fileName}"
        supabaseClient.storage
            .from(STORAGE_NAME)
            .upload(
                path = storagePath,
                data = bookMetadata.data
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
