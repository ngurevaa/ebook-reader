package ru.gureva.ebookreader.feature.bookupload

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.bookupload.presentation.BookUploadViewModel

val bookUploadModule = module {
    viewModel { BookUploadViewModel() }
}
