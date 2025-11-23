package ru.gureva.ebookreader.feature.reader.repository

interface BookRepository {
    suspend fun readTxt(fileName: String): String
}
