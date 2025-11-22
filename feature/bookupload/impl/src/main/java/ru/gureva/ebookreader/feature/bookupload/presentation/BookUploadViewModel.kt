package ru.gureva.ebookreader.feature.bookupload.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.FileUtil
import ru.gureva.ebookreader.core.util.NetworkUtil
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.bookupload.R
import ru.gureva.ebookreader.feature.bookupload.exception.EmptyFileException
import ru.gureva.ebookreader.feature.bookupload.exception.NotAuthenticatedException
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import ru.gureva.ebookreader.feature.bookupload.usecase.UploadBookUseCase

class BookUploadViewModel : ContainerHost<BookUploadState, BookUploadSideEffect>, ViewModel(), KoinComponent {
    override val container = container<BookUploadState, BookUploadSideEffect>(BookUploadState())

    private val fileUtil: FileUtil by inject()
    private val networkUtil: NetworkUtil by inject()
    private val resourceManager: ResourceManager by inject()
    private val uploadBookUseCase: UploadBookUseCase by inject()

    fun dispatch(event: BookUploadEvent) {
        when (event) {
            is BookUploadEvent.SelectFile -> selectFile(event.uri)
            is BookUploadEvent.ChangeBookName -> changeBookName(event.name)
            is BookUploadEvent.ChangeBookAuthor -> changeBookAuthor(event.author)
            BookUploadEvent.UploadBook -> uploadBook()
        }
    }

    private fun uploadBook() = intent {
        if (!networkUtil.isNetworkAvailable()) {
            postSideEffect(BookUploadSideEffect.ShowSnackbarWithRetryButton(
                resourceManager.getString(R.string.network_error))
            )
            return@intent
        }

        val uri = state.fileUri ?: return@intent
        val bookBytes = fileUtil.getFileBytesFromUri(uri)
            ?: throw EmptyFileException()
        val userId = Firebase.auth.currentUser?.uid
            ?: throw NotAuthenticatedException()

        runCatching {
            reduce { state.copy(isLoading = true) }
            uploadBookUseCase(
                BookMetadata(
                    data = bookBytes,
                    fileName = state.fileName ?: "",
                    title = state.bookName,
                    author = state.bookAuthor,
                    userId = userId
            )
        ) }
            .onSuccess {
                reduce { state.copy(isLoading = false) }
                postSideEffect(BookUploadSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.book_downloaded_successfully)
                ))
            }
            .onFailure {
                reduce { state.copy(isLoading = false) }
                postSideEffect(BookUploadSideEffect.ShowSnackbarWithRetryButton(
                    resourceManager.getString(R.string.download_error))
                )
            }
    }

    private fun changeBookAuthor(author: String) = intent {
        reduce { state.copy(bookAuthor = author) }
    }

    private fun changeBookName(name: String) = intent {
        reduce { state.copy(bookName = name) }
    }

    private fun selectFile(uri: Uri) = intent {
        reduce { state.copy(
            fileUri = uri,
            fileName = fileUtil.getFileName(uri)
        ) }
    }
}
