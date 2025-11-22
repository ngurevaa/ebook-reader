package ru.gureva.ebookreader.feature.booklist.repository

import ru.gureva.ebookreader.feature.booklist.model.FirestoreBookMetadata

interface BookRepository {
    suspend fun getBooksFromFirestore(userId: String): List<FirestoreBookMetadata>
    suspend fun downloadBookFromSupabase(fileUrl: String): ByteArray
    suspend fun getLocalBooks(): List<String>
    suspend fun deleteLocalBook(fileName: String)
    suspend fun saveBookLocal(data: ByteArray, fileName: String)
}
