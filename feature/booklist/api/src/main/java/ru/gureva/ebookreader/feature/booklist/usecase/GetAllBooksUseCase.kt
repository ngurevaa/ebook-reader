package ru.gureva.ebookreader.feature.booklist.usecase

import ru.gureva.ebookreader.feature.booklist.model.Book

interface GetAllBooksUseCase {
    suspend operator fun invoke(userId: String): List<Book>
}
