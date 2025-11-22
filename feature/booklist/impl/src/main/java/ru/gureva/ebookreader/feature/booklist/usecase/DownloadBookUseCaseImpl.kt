package ru.gureva.ebookreader.feature.booklist.usecase

import ru.gureva.ebookreader.feature.booklist.repository.BookRepository

class DownloadBookUseCaseImpl(
    private val bookRepository: BookRepository
) : DownloadBookUseCase {
    override suspend operator fun invoke(fileUrl: String) {
        val fileData = bookRepository.downloadBookFromSupabase(fileUrl)
        val fileName = fileUrl.substringAfterLast('/')

        bookRepository.saveBookLocal(fileData, fileName)
    }
}
