package ru.gureva.ebookreader.feature.booklist.usecase

import ru.gureva.ebookreader.feature.booklist.repository.BookRepository

class SyncBooksUseCaseImpl(
    private val bookRepository: BookRepository
) : SyncBooksUseCase {
    override suspend operator fun invoke(userId: String) {
        bookRepository.syncBooksFromFirebase(userId)
    }
}
