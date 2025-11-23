package ru.gureva.ebookreader.feature.reader.usecase

import ru.gureva.ebookreader.feature.reader.repository.BookRepository

class SaveProgressUseCaseImpl(
    private val bookRepository: BookRepository
) : SaveProgressUseCase {
    override suspend operator fun invoke(fileName: String, progress: Float) {
        bookRepository.saveProgress(fileName, progress)
    }
}
