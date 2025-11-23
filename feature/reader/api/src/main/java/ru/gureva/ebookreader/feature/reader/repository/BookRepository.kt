package ru.gureva.ebookreader.feature.reader.repository

interface BookRepository {
    suspend fun readTxt(fileName: String): String
    suspend fun readEpub(fileName: String): String
}
