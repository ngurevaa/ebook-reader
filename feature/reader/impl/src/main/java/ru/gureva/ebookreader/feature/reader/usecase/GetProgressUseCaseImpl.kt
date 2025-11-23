package ru.gureva.ebookreader.feature.reader.usecase

import ru.gureva.ebookreader.feature.reader.repository.BookRepository

class GetProgressUseCaseImpl(
    private val bookRepository: BookRepository
) : GetProgressUseCase {
    override suspend operator fun invoke(fileName: String): Float {
        return bookRepository.getProgress(fileName)
    }
}
