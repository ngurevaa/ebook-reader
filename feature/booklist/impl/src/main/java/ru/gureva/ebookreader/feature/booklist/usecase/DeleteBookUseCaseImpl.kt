package ru.gureva.ebookreader.feature.booklist.usecase

import ru.gureva.ebookreader.feature.booklist.repository.BookRepository

class DeleteBookUseCaseImpl(
    private val bookRepository: BookRepository
) : DeleteBookUseCase {
    override suspend operator fun invoke(fileName: String) {
        bookRepository.deleteLocalBook(fileName)
    }
}
