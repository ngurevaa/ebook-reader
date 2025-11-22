package ru.gureva.ebookreader.feature.booklist

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.booklist.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.booklist.presentation.BookListViewModel
import ru.gureva.ebookreader.feature.booklist.repository.BookRepository
import ru.gureva.ebookreader.feature.booklist.repository.BookRepositoryImpl
import ru.gureva.ebookreader.feature.booklist.usecase.GetAllBooksUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.GetAllBooksUseCaseImpl

val bookListModule = module {
    viewModel { BookListViewModel() }

    factory<GetAllBooksUseCase> { GetAllBooksUseCaseImpl(get()) }

    factory<BookRepository> { BookRepositoryImpl(get(), get()) }

    factory { RemoteFirestoreDataSource() }
    factory { LocalBookDataSource(get()) }
}
