package ru.gureva.ebookreader.feature.booklist.usecase

import kotlinx.coroutines.flow.Flow
import ru.gureva.ebookreader.feature.booklist.model.Book
import ru.gureva.ebookreader.feature.booklist.repository.BookRepository

class GetAllBooksUseCaseImpl(
    private val bookRepository: BookRepository
) : GetAllBooksUseCase {
    override suspend fun invoke(): Flow<List<Book>> {
        return bookRepository.getAllBooks()
    }
}
