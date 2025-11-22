package ru.gureva.ebookreader.feature.booklist.datasource

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage

class RemoteSupabaseDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun downloadBookFromStorage(fileUrl: String): ByteArray {
        val path = fileUrl.substringAfter("$STORAGE_NAME/")

        return supabaseClient.storage
            .from(STORAGE_NAME)
            .downloadPublic(path)
    }

    companion object {
        private const val STORAGE_NAME = "books"
    }
}
