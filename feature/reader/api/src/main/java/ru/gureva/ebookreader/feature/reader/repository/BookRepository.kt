package ru.gureva.ebookreader.feature.reader.repository

import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun readTxt(fileName: String): String
    suspend fun readEpub(fileName: String): Flow<String>
    suspend fun readPdf(fileName: String): Flow<String>
    suspend fun saveProgress(fileName: String, progress: Float)
    suspend fun getProgress(fileName: String): Float
}
