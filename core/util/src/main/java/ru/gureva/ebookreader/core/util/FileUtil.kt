package ru.gureva.ebookreader.core.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

class FileUtil(
    private val context: Context
) {
    fun getFileName(uri: Uri): String? {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        }
    }

    fun getFileBytesFromUri(uri: Uri): ByteArray? {
        return context.contentResolver.openInputStream(uri)?.readBytes()
    }
}
