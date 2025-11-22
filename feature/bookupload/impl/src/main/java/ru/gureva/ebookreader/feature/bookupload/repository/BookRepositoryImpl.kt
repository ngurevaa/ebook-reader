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
    override suspend fun uploadBook(bookMetadata: BookMetadata) {
        withContext(Dispatchers.IO) {
            val fileUrl = remoteSupabaseDatasource.uploadBookToStorage(bookMetadata)

            remoteFirestoreDatasource.saveBookMetadata(
                FirestoreBookMetadata(bookMetadata.title, bookMetadata.author, fileUrl, bookMetadata.userId)
            )

            localBookDatasource.saveBook(bookMetadata.data, fileUrl.substringAfterLast('/'))
        }
    }
}
