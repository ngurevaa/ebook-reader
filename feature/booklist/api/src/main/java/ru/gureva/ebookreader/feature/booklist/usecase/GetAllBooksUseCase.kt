package ru.gureva.ebookreader.feature.booklist.usecase

import kotlinx.coroutines.flow.Flow
import ru.gureva.ebookreader.feature.booklist.model.Book

interface GetAllBooksUseCase {
    suspend operator fun invoke(): Flow<List<Book>>
}
