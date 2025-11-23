package ru.gureva.ebookreader.feature.reader.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.gureva.ebookreader.feature.reader.repository.BookRepository

class ReadFileUseCaseImpl(
    private val bookRepository: BookRepository
) : ReadFileUseCase {
    override suspend operator fun invoke(fileName: String): Flow<String> {
        return when (fileName.substringAfterLast('.')) {
            "txt" -> {
                flow { emit(bookRepository.readTxt(fileName)) }
            }
            "epub" -> {
                bookRepository.readEpub(fileName)
            }
            "pdf" -> {
                bookRepository.readPdf(fileName)
            }
            else -> flow { emit("") }
        }
    }
}
