package ru.gureva.ebookreader.feature.booklist.datasource

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage

class RemoteSupabaseDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun downloadBookFromStorage(userId: String, fileName: String): ByteArray {
        val storagePath = "$userId/$fileName"

        return supabaseClient.storage
            .from(STORAGE_NAME)
            .downloadPublic(storagePath)
    }

    companion object {
        private const val STORAGE_NAME = "books"
    }
}
