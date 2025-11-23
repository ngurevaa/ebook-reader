package ru.gureva.ebookreader.feature.bookupload.presentation

import android.net.Uri

sealed interface BookUploadEvent {
    data class SelectFile(val uri: Uri) : BookUploadEvent
    data class ChangeBookName(val name: String) : BookUploadEvent
    data class ChangeBookAuthor(val author: String) : BookUploadEvent
    data object UploadBook : BookUploadEvent
}
