package ru.gureva.ebookreader.feature.bookupload.datasource

import android.content.Context
import ru.gureva.ebookreader.database.dao.BookDao
import ru.gureva.ebookreader.database.entity.BookEntity
import java.io.File

class LocalBookDataSource(
    val context: Context,
    val bookDao: BookDao
) {
    fun saveBook(filePath: String, fileName: String) {
        val booksDir = File(context.filesDir, LOCAL_DIRECTORY_NAME).apply { mkdirs() }

        val tempFile = File(filePath)
        val outputFile = File(booksDir, fileName)

        tempFile.copyTo(outputFile, overwrite = true)
        tempFile.delete()
    }

    fun saveBookMetadata(bookEntity: BookEntity) {
        bookDao.insert(bookEntity)
    }

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
    }
}
