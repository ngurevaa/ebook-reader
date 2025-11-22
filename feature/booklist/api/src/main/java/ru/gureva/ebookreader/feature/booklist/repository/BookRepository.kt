package ru.gureva.ebookreader.feature.booklist.repository

import ru.gureva.ebookreader.feature.booklist.model.FirestoreBookMetadata

interface BookRepository {
    suspend fun getBooksFromFirestore(userId: String): List<FirestoreBookMetadata>
    suspend fun getLocalBooks(): List<String>
}
