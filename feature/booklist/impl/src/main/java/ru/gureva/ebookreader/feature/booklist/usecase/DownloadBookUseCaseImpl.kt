package ru.gureva.ebookreader.feature.booklist.usecase

import ru.gureva.ebookreader.feature.booklist.repository.BookRepository

class DownloadBookUseCaseImpl(
    private val bookRepository: BookRepository
) : DownloadBookUseCase {
    override suspend operator fun invoke(userId: String, fileName: String) {
        val fileData = bookRepository.downloadBookFromSupabase(userId, fileName)
        bookRepository.saveBookLocal(fileData, fileName)
    }
}
