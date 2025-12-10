package ru.gureva.ebookreader.feature.bookupload.datasource

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.UploadData
import io.github.jan.supabase.storage.storage
import io.ktor.utils.io.jvm.javaio.toByteReadChannel
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import java.io.File
import java.io.FileInputStream

class RemoteSupabaseDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun uploadBookToStorage(bookMetadata: BookMetadata): String {
        val storagePath = "${bookMetadata.userId}/${System.currentTimeMillis()}${bookMetadata.fileName}"

        val file = File(bookMetadata.filePath)

        val size = file.length()
        val stream = FileInputStream(file).toByteReadChannel()
        val uploadData = UploadData(stream, size)

        supabaseClient.storage
            .from(STORAGE_NAME)
            .upload(
                path = storagePath,
                data = uploadData
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

