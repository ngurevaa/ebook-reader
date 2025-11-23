package ru.gureva.ebookreader.feature.reader.usecase

interface SaveProgressUseCase {
    suspend operator fun invoke(fileName: String, progress: Float)
}
