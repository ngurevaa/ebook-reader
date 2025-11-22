package ru.gureva.ebookreader.feature.booklist.datasource

import android.content.Context
import java.io.File

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

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
    }
}
