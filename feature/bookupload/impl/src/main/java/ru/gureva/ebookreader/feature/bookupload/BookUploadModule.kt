package ru.gureva.ebookreader.feature.bookupload

import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.bookupload.background.BookUploadWorker
import ru.gureva.ebookreader.feature.bookupload.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.bookupload.datasource.RemoteSupabaseDataSource
import ru.gureva.ebookreader.feature.bookupload.presentation.BookUploadViewModel
import ru.gureva.ebookreader.feature.bookupload.repository.BookRepository
import ru.gureva.ebookreader.feature.bookupload.repository.BookRepositoryImpl
import ru.gureva.ebookreader.feature.bookupload.usecase.UploadBookUseCase
import ru.gureva.ebookreader.feature.bookupload.usecase.UploadBookUseCaseImpl

val bookUploadModule = module {
    viewModel { BookUploadViewModel() }

    factory<UploadBookUseCase> { UploadBookUseCaseImpl(get()) }

    factory<BookRepository> { BookRepositoryImpl(get(), get(), get()) }

    factory { LocalBookDataSource(get()) }
    factory { RemoteSupabaseDataSource(get()) }
    factory { RemoteFirestoreDataSource() }

    worker { BookUploadWorker(get(), get(), get(), get()) }
}
