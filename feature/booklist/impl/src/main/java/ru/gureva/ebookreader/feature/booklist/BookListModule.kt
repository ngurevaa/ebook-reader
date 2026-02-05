package ru.gureva.ebookreader.feature.booklist

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.booklist.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteSupabaseDataSource
import ru.gureva.ebookreader.feature.booklist.presentation.BookListViewModel
import ru.gureva.ebookreader.feature.booklist.repository.BookRepository
import ru.gureva.ebookreader.feature.booklist.repository.BookRepositoryImpl
import ru.gureva.ebookreader.feature.booklist.usecase.DeleteBookUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.DeleteBookUseCaseImpl
import ru.gureva.ebookreader.feature.booklist.usecase.DownloadBookUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.DownloadBookUseCaseImpl
import ru.gureva.ebookreader.feature.booklist.usecase.GetAllBooksUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.GetAllBooksUseCaseImpl
import ru.gureva.ebookreader.feature.booklist.usecase.SyncBooksUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.SyncBooksUseCaseImpl

val bookListModule = module {
    viewModel { BookListViewModel() }

    factory<GetAllBooksUseCase> { GetAllBooksUseCaseImpl(get()) }
    factory<DeleteBookUseCase> { DeleteBookUseCaseImpl(get()) }
    factory<DownloadBookUseCase> { DownloadBookUseCaseImpl(get()) }
    factory<SyncBooksUseCase> { SyncBooksUseCaseImpl(get()) }

    factory<BookRepository> { BookRepositoryImpl(get(), get(), get()) }

    factory { RemoteFirestoreDataSource() }
    factory { LocalBookDataSource(get(), get()) }
    factory { RemoteSupabaseDataSource(get()) }
}
