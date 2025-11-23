package ru.gureva.ebookreader.feature.reader.usecase

interface ReadFileUseCase {
    suspend operator fun invoke(fileName: String): String
}
