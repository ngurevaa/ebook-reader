package ru.gureva.ebookreader.feature.bookupload.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.feature.bookupload.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteSupabaseDataSource
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import ru.gureva.ebookreader.feature.bookupload.model.FirestoreBookMetadata

class BookRepositoryImpl(
    private val remoteSupabaseDatasource: RemoteSupabaseDataSource,
    private val remoteFirestoreDatasource: RemoteFirestoreDataSource,
    private val localBookDatasource: LocalBookDataSource
) : BookRepository {
    override suspend fun uploadBookToRemoteStorage(bookMetadata: BookMetadata): String {
        return withContext(Dispatchers.IO) {
            remoteSupabaseDatasource.uploadBookToStorage(bookMetadata)
        }
    }

    override suspend fun saveBookMetadataToRemoteStorage(bookMetadata: BookMetadata, fileUrl: String) {
        withContext(Dispatchers.IO) {
            remoteFirestoreDatasource.saveBookMetadata(
                bookMetadata.userId,
                FirestoreBookMetadata(bookMetadata.title, bookMetadata.author, fileUrl)
            )
        }
    }

    override suspend fun saveBookToLocalStorage(bookMetadata: BookMetadata, fileUrl: String) {
        withContext(Dispatchers.IO) {
            localBookDatasource.saveBook(bookMetadata, fileUrl.substringAfterLast('/'))
        }
    }
}
