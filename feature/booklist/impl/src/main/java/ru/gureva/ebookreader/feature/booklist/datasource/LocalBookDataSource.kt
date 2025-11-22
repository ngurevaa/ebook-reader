package ru.gureva.ebookreader.feature.booklist.datasource

import android.content.Context
import java.io.File
import java.io.FileOutputStream

class LocalBookDataSource(
    private val context: Context
) {
    fun getBooks(): List<String> {
        val booksDir = File(context.filesDir, LOCAL_DIRECTORY_NAME)
        if (!booksDir.exists() || !booksDir.isDirectory) return emptyList()

        return booksDir.listFiles()
            ?.filter { it.isFile }
            ?.map { it.name }
            ?: emptyList()
    }

    fun deleteBook(fileName: String) {
        val booksDir = File(context.filesDir, LOCAL_DIRECTORY_NAME)
        val fileToDelete = File(booksDir, fileName)

        fileToDelete.delete()
    }

    fun saveBook(data: ByteArray, fileName: String) {
        val booksDir = File(context.filesDir, LOCAL_DIRECTORY_NAME).apply { mkdirs() }
        val outputFile = File(booksDir, fileName)

        FileOutputStream(outputFile).use { output ->
            output.write(data)
        }
    }

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
    }
}
