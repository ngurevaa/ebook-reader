package ru.gureva.ebookreader.feature.bookupload.presentation

import android.net.Uri

sealed interface BookUploadEvent {
    data class SelectFile(val uri: Uri): BookUploadEvent
}
