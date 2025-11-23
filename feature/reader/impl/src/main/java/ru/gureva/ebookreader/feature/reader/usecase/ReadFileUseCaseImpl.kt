package ru.gureva.ebookreader.feature.reader.usecase

import android.util.Log
import ru.gureva.ebookreader.feature.reader.repository.BookRepository

class ReadFileUseCaseImpl(
    private val bookRepository: BookRepository
) : ReadFileUseCase {
    override suspend operator fun invoke(fileName: String): String {
        return when (fileName.substringAfterLast('.')) {
            "txt" -> {
                bookRepository.readTxt(fileName)
            }
            else -> ""
        }
    }
}
