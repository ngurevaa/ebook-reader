package ru.gureva.ebookreader.feature.booklist.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.feature.booklist.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.booklist.model.FirestoreBookMetadata

class BookRepositoryImpl(
    private val remoteFirestoreDataSource: RemoteFirestoreDataSource,
    private val localBookDataSource: LocalBookDataSource
) : BookRepository {
    override suspend fun getBooksFromFirestore(userId: String): List<FirestoreBookMetadata> {
        return withContext(Dispatchers.IO) {
            remoteFirestoreDataSource.getUserBooks(userId)
        }
    }

    override suspend fun getLocalBooks(): List<String> {
        return withContext(Dispatchers.IO) {
            localBookDataSource.getBooks()
        }
    }
}
