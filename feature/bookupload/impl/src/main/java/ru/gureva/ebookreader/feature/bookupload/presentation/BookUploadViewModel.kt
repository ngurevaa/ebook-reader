package ru.gureva.ebookreader.feature.bookupload.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.FileUtil
import ru.gureva.ebookreader.core.util.NetworkUtil
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.bookupload.R
import ru.gureva.ebookreader.feature.bookupload.background.BookUploadWorker
import ru.gureva.ebookreader.feature.bookupload.constants.BookUploadWorkerParams
import java.util.UUID

class BookUploadViewModel : ContainerHost<BookUploadState, BookUploadSideEffect>, ViewModel(), KoinComponent {
    override val container = container<BookUploadState, BookUploadSideEffect>(BookUploadState())

    private val fileUtil: FileUtil by inject()
    private val networkUtil: NetworkUtil by inject()
    private val resourceManager: ResourceManager by inject()
    private val context: Context by inject()

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
        reduce { state.copy(isLoading = true) }

        val file = fileUtil.copyUriToTempFile(state.fileUri!!)
        val userId = Firebase.auth.currentUser?.uid!!

        val data = workDataOf(
            BookUploadWorkerParams.FILE_PATH to file.absolutePath,
            BookUploadWorkerParams.TITLE to state.bookName.trim(),
            BookUploadWorkerParams.AUTHOR to state.bookAuthor.trim(),
            BookUploadWorkerParams.USER_ID to userId,
        )
        val request = OneTimeWorkRequestBuilder<BookUploadWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
        observeWork(request.id)
    }

    private fun observeWork(workId: UUID) {
        viewModelScope.launch {
            WorkManager
                .getInstance(context)
                .getWorkInfoByIdFlow(workId)
                .collect { info ->
                    when (info?.state) {
                        WorkInfo.State.SUCCEEDED -> intent {
                            reduce { state.copy(isLoading = false) }
                            postSideEffect(BookUploadSideEffect.ShowSnackbar(
                                resourceManager.getString(R.string.book_downloaded_successfully)
                            ))
                        }

                        WorkInfo.State.FAILED -> intent {
                            reduce { state.copy(isLoading = false) }
                            postSideEffect(BookUploadSideEffect.ShowSnackbar(
                                resourceManager.getString(R.string.download_error)
                            ))
                        }

                        else -> Unit
                    }
                }
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
