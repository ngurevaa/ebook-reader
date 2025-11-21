package ru.gureva.ebookreader.feature.bookupload.presentation

import android.net.Uri

data class BookUploadState(
    val fileUri: Uri? = null,
    val fileName: String? = null,
    val bookName: String = "",
    val bookAuthor: String = "",
    val isLoading: Boolean = false,
) {
    val isUploadingEnabled: Boolean
        get() = !isLoading
                && fileUri != null
                && bookName.isNotBlank()
                && bookAuthor.isNotBlank()
}
