package ru.gureva.ebookreader.feature.bookupload.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.FileUtil

class BookUploadViewModel: ContainerHost<BookUploadState, BookUploadSideEffect>, ViewModel(), KoinComponent {
    override val container = container<BookUploadState, BookUploadSideEffect>(BookUploadState())

    private val fileUtil: FileUtil by inject()

    fun dispatch(event: BookUploadEvent) {
        when (event) {
            is BookUploadEvent.SelectFile -> selectFile(event.uri)
        }
    }

    private fun selectFile(uri: Uri) = intent {
        reduce { state.copy(fileName = fileUtil.getFileName(uri)) }
    }
}
