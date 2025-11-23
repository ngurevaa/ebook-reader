package ru.gureva.ebookreader.feature.reader.datasource

import android.content.Context
import nl.siegmann.epublib.epub.EpubReader
import java.io.File
import java.io.FileInputStream

class LocalBookDataSource(
    private val context: Context
) {
    fun readTxt(fileName: String): String {
        val bookFile = File(context.filesDir, "$LOCAL_DIRECTORY_NAME/$fileName")
        return bookFile.readText()
    }

    fun readEpub(fileName: String): String {
        val bookFile = File(context.filesDir, "$LOCAL_DIRECTORY_NAME/$fileName")

        return FileInputStream(bookFile).use { epubInputStream ->
            val book = EpubReader().readEpub(epubInputStream)

            val textContent = StringBuilder()

            book.spine.spineReferences.forEachIndexed { index, spineRef ->
                if (index > 0) {
                    spineRef.resource.reader.use { reader ->
                        val htmlContent = reader.readText()

                        val cleanText = htmlContent
                            .replace(Regex("<p.*?>"), "\n")
                            .replace(Regex("<br.*?>"), "\n")
                            .replace(Regex("<div.*?>"), "\n")
                            .replace(Regex("</p>|</div>"), "\n")
                            .replace(Regex("<[^>]*>"), "")
                            .replace(Regex("\\n\\s*\\n"), "\n\n")
                            .trim()

                        if (cleanText.isNotBlank()) {
                            textContent.append(cleanText).append("\n\n")
                        }
                    }
                }
            }

            textContent.toString()
        }
    }

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
    }
}
