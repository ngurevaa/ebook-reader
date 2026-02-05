package ru.gureva.ebookreader.feature.booklist.datasource

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.gureva.ebookreader.database.dao.BookDao
import ru.gureva.ebookreader.database.entity.BookEntity
import java.io.File
import java.io.FileOutputStream

class LocalBookDataSource(
    private val context: Context,
    private val bookDao: BookDao
) {
    fun getBooks(): Flow<List<BookEntity>> = bookDao.getAll()

    suspend fun getBooksOnce(): List<BookEntity> {
        return bookDao.getAllOnce()
    }

    suspend fun upsertBooks(books: List<BookEntity>) {
        bookDao.upsertAll(books)
    }

    suspend fun deleteMissingBooks(ids: List<String>) {
        bookDao.deleteMissing(ids)
    }

    fun deleteBook(fileName: String) {
        val booksDir = File(context.filesDir, LOCAL_DIRECTORY_NAME)
        val fileToDelete = File(booksDir, fileName)

        fileToDelete.delete()
    }

    suspend fun updateIsLocal(fileName: String, isLocal: Boolean) {
        bookDao.updateIsLocalByFileName(fileName, isLocal)
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
