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
    override suspend fun downloadBookFromSupabase(userId: String, fileName: String): ByteArray {
        return withContext(Dispatchers.IO) {
            remoteSupabaseDataSource.downloadBookFromStorage(userId, fileName)
        }
    }

    override suspend fun deleteLocalBook(fileName: String) {
        withContext(Dispatchers.IO) {
            localBookDataSource.deleteBook(fileName)
        }
        localBookDataSource.updateIsLocal(fileName, false)
    }

    override suspend fun saveBookLocal(data: ByteArray, fileName: String) {
        withContext(Dispatchers.IO) {
            localBookDataSource.saveBook(data, fileName)
        }
        localBookDataSource.updateIsLocal(fileName, true)
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
        val localBooks = localBookDataSource.getBooksOnce()
        val localBooksByFileName = localBooks.associateBy { it.fileName }

        val mergedBooks = cloudBooks.map { cloudBook ->
            val localVersion = localBooksByFileName[cloudBook.fileName]
            cloudBook.copy(isLocal = localVersion?.isLocal ?: false)
        }

        localBookDataSource.upsertBooks(mergedBooks)
        localBookDataSource.deleteMissingBooks(mergedBooks.map { it.fileName })
    }
}
