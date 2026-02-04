package ru.gureva.ebookreader.feature.bookupload.datasource

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.UploadData
import io.github.jan.supabase.storage.storage
import io.ktor.utils.io.jvm.javaio.toByteReadChannel
import java.io.File
import java.io.FileInputStream
import java.util.UUID

class RemoteSupabaseDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun uploadFileToStorage(userId: String, filePath: String): String {
        val file = File(filePath)
        val stream = FileInputStream(file).toByteReadChannel()
        val uploadData = UploadData(stream, file.length())

        val storagePath = "${userId}/${UUID.randomUUID()}.${file.extension}"

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

