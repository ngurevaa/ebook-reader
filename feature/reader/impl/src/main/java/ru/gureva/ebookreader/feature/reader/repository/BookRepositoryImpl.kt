package ru.gureva.ebookreader.feature.reader.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.feature.reader.datasource.LocalBookDataSource

class BookRepositoryImpl(
    private val localBookDataSource: LocalBookDataSource
) : BookRepository {
    override suspend fun readTxt(fileName: String): String {
        return withContext(Dispatchers.IO) {
            localBookDataSource.readTxt(fileName)
        }
    }
}
