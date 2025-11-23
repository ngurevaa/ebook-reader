package ru.gureva.ebookreader.feature.reader

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.reader.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.reader.presentation.ReaderViewModel
import ru.gureva.ebookreader.feature.reader.repository.BookRepository
import ru.gureva.ebookreader.feature.reader.repository.BookRepositoryImpl
import ru.gureva.ebookreader.feature.reader.usecase.ReadFileUseCase
import ru.gureva.ebookreader.feature.reader.usecase.ReadFileUseCaseImpl

val readerModule = module {
    viewModel { ReaderViewModel() }

    factory<ReadFileUseCase> { ReadFileUseCaseImpl(get()) }

    factory<BookRepository> { BookRepositoryImpl(get()) }

    factory { LocalBookDataSource(get()) }
}
