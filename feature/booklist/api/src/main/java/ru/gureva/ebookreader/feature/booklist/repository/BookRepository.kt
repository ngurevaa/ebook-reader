package ru.gureva.ebookreader.feature.booklist.repository

import ru.gureva.ebookreader.feature.booklist.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun downloadBookFromSupabase(userId: String, fileName: String): ByteArray
    suspend fun deleteLocalBook(fileName: String)
    suspend fun saveBookLocal(data: ByteArray, fileName: String)
    fun getAllBooks(): Flow<List<Book>>
    suspend fun syncBooksFromFirebase(userId: String)
}
