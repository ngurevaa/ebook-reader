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
            val fileName = book.fileUrl.substringAfterLast("/")
            val local = localBooks.contains(fileName)

            books.add(
                Book(
                    fileName = fileName,
                    fileUrl = book.fileUrl,
                    title = book.title,
                    author = book.author,
                    local = local
                )
            )
        }
        return books
    }
}
