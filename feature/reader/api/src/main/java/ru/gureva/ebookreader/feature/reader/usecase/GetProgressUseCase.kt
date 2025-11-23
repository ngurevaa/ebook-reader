package ru.gureva.ebookreader.feature.reader.usecase

interface GetProgressUseCase {
    suspend operator fun invoke(fileName: String): Float
}
