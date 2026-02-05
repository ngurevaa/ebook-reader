package ru.gureva.ebookreader.feature.booklist.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.feature.booklist.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteSupabaseDataSource
import ru.gureva.ebookreader.feature.booklist.mapper.mapToBook
import ru.gureva.ebookreader.feature.booklist.mapper.mapToBookEntity
import ru.gureva.ebookreader.feature.booklist.model.Book

class BookRepositoryImpl(
    private val remoteFirestoreDataSource: RemoteFirestoreDataSource,
    private val remoteSupabaseDataSource: RemoteSupabaseDataSource,
    private val localBookDataSource: LocalBookDataSource
) : BookRepository {
    override suspend fun downloadBookFromSupabase(fileUrl: String): ByteArray {
        return withContext(Dispatchers.IO) {
            remoteSupabaseDataSource.downloadBookFromStorage(fileUrl)
        }
    }

    override suspend fun deleteLocalBook(fileName: String) {
        withContext(Dispatchers.IO) {
            localBookDataSource.deleteBook(fileName)
        }
    }
    override suspend fun saveBookLocal(data: ByteArray, fileName: String) {
        withContext(Dispatchers.IO) {
            localBookDataSource.saveBook(data, fileName)
        }
    }

    override fun getAllBooks(): Flow<List<Book>> {
        return localBookDataSource.getBooks()
            .map { entities ->
                entities.map { it.mapToBook() }
            }
            .distinctUntilChanged()
    }

    override suspend fun syncBooksFromFirebase(userId: String) {
        val cloudBooks = remoteFirestoreDataSource.getUserBooks(userId)
            .map { documents ->
                documents.mapToBookEntity()
            }
        localBookDataSource.upsertBooks(cloudBooks)
        localBookDataSource.deleteMissingBooks(cloudBooks.map { it.fileName })
    }
}
