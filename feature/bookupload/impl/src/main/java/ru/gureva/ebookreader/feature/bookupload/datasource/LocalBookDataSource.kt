package ru.gureva.ebookreader.feature.bookupload.datasource

import android.content.Context
import java.io.File
import java.io.FileOutputStream

class LocalBookDataSource(
    val context: Context
) {
    fun saveBookLocally(data: ByteArray, fileName: String) {
        val booksDir = File(context.filesDir, LOCAL_DIRECTORY_NAME).apply { mkdirs() }
        val outputFile = File(booksDir, "${System.currentTimeMillis()}$fileName")

        FileOutputStream(outputFile).use { output ->
            output.write(data)
        }
    }

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
    }
}
