package ru.gureva.ebookreader.feature.booklist.usecase

interface DownloadBookUseCase {
    suspend operator fun invoke(userId: String, fileUrl: String)
}
