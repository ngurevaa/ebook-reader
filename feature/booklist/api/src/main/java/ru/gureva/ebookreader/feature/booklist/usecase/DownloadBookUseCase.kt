package ru.gureva.ebookreader.feature.booklist.usecase

interface DownloadBookUseCase {
    suspend operator fun invoke(fileUrl: String)
}
