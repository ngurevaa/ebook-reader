package ru.gureva.ebookreader.feature.booklist.usecase

import ru.gureva.ebookreader.feature.booklist.model.Book
import ru.gureva.ebookreader.feature.booklist.repository.BookRepository

class GetAllBooksUseCaseImpl(
    private val bookRepository: BookRepository
) : GetAllBooksUseCase {
    override suspend fun invoke(userId: String): List<Book> {
        val localBooks = bookRepository.getLocalBooks()
        val allBooks = bookRepository.getBooksFromFirestore(userId)

        val books = mutableListOf<Book>()
        for (book in allBooks) {
            val local = localBooks.contains(book.fileUrl.substringAfterLast("/"))

            books.add(
                Book(
                    title = book.title,
                    author = book.author,
                    local = local
                )
            )
        }
        return books
    }
}
