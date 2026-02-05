package ru.gureva.ebookreader.feature.bookupload.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.database.entity.BookEntity
import ru.gureva.ebookreader.feature.bookupload.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteSupabaseDataSource
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import java.util.Date

class BookRepositoryImpl(
    private val remoteSupabaseDatasource: RemoteSupabaseDataSource,
    private val remoteFirestoreDatasource: RemoteFirestoreDataSource,
    private val localBookDatasource: LocalBookDataSource
) : BookRepository {
    override suspend fun uploadBookToRemoteStorage(userId: String, filePath: String): String {
        return withContext(Dispatchers.IO) {
            remoteSupabaseDatasource.uploadFileToStorage(userId, filePath)
        }
    }

    override suspend fun saveBookMetadataToRemoteStorage(userId: String, bookMetadata: BookMetadata) {
        withContext(Dispatchers.IO) {
            remoteFirestoreDatasource.saveBookMetadata(userId, bookMetadata)
        }
    }

    override suspend fun saveBookToLocalStorage(filePath: String, bookMetadata: BookMetadata) {
        withContext(Dispatchers.IO) {
            localBookDatasource.saveBook(filePath, bookMetadata.fileName)

            val bookEntity = BookEntity(
                fileName = bookMetadata.fileName,
                author = bookMetadata.author,
                title = bookMetadata.title,
                creationDate = Date()
            )
            localBookDatasource.saveBookMetadata(bookEntity)
        }
    }
}
