package ru.gureva.ebookreader.feature.bookupload.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.feature.bookupload.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteFirestoreDatasource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteSupabaseDatasource
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import ru.gureva.ebookreader.feature.bookupload.model.FirestoreBookMetadata

class BookRepositoryImpl(
    private val remoteSupabaseDatasource: RemoteSupabaseDatasource,
    private val remoteFirestoreDatasource: RemoteFirestoreDatasource,
    private val localBookDatasource: LocalBookDataSource
) : BookRepository {
    override suspend fun uploadBook(bookMetadata: BookMetadata) {
        withContext(Dispatchers.IO) {
            val fileUrl = remoteSupabaseDatasource.uploadBookToStorage(bookMetadata)

            remoteFirestoreDatasource.saveBookMetadata(
                FirestoreBookMetadata(bookMetadata.title, bookMetadata.author, fileUrl, bookMetadata.userId)
            )

            localBookDatasource.saveBookLocally(bookMetadata.data, bookMetadata.fileName)
        }
    }
}
