package ru.gureva.ebookreader.feature.booklist.usecase

interface SyncBooksUseCase {
    suspend operator fun invoke(userId: String)
}
