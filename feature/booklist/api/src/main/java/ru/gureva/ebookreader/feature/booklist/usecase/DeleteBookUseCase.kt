package ru.gureva.ebookreader.feature.booklist.usecase

interface DeleteBookUseCase {
    suspend operator fun invoke(fileName: String)
}
