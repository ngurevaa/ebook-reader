package ru.gureva.ebookreader.feature.reader.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    override suspend fun readEpub(fileName: String): Flow<String> {
        return withContext(Dispatchers.IO) {
            localBookDataSource.readEpub(fileName)
        }
    }

    override suspend fun readPdf(fileName: String): Flow<String> {
        return withContext(Dispatchers.IO) {
            localBookDataSource.readPdf(fileName)
        }
    }

    override suspend fun saveProgress(fileName: String, progress: Float) {
        withContext(Dispatchers.IO) {
            localBookDataSource.saveProgressToSharedPreferences(fileName, progress)
        }
    }

    override suspend fun getProgress(fileName: String): Float {
        return withContext(Dispatchers.IO) {
            localBookDataSource.getProgressFromSharedPreferences(fileName)
        }
    }
}
