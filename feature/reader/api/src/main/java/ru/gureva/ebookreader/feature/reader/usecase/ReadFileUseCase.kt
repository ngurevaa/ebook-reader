package ru.gureva.ebookreader.feature.reader.usecase

import kotlinx.coroutines.flow.Flow

interface ReadFileUseCase {
    suspend operator fun invoke(fileName: String): Flow<String>
}
