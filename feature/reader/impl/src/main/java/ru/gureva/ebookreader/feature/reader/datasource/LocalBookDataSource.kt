package ru.gureva.ebookreader.feature.reader.datasource

import android.content.Context
import java.io.File

class LocalBookDataSource(
    private val context: Context
) {
    fun readTxt(fileName: String): String {
        val bookFile = File(context.filesDir, "$LOCAL_DIRECTORY_NAME/$fileName")
        return bookFile.readText()
    }

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
    }
}
