package ru.gureva.ebookreader.feature.bookupload.datasource

import android.content.Context
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import java.io.File

class LocalBookDataSource(
    val context: Context
) {
    fun saveBook(bookMetadata: BookMetadata, fileName: String) {
        val booksDir = File(context.filesDir, LOCAL_DIRECTORY_NAME).apply { mkdirs() }

        val tempFile = File(bookMetadata.filePath)
        val outputFile = File(booksDir, fileName)

        tempFile.copyTo(outputFile, overwrite = true)
        tempFile.delete()
    }

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
    }
}
