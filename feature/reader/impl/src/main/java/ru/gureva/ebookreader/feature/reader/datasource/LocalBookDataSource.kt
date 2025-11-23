package ru.gureva.ebookreader.feature.reader.datasource

import android.content.Context
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.siegmann.epublib.domain.SpineReference
import nl.siegmann.epublib.epub.EpubReader
import java.io.File
import java.io.FileInputStream
import androidx.core.content.edit

class LocalBookDataSource(
    private val context: Context
) {
    fun readTxt(fileName: String): String {
        val bookFile = File(context.filesDir, "$LOCAL_DIRECTORY_NAME/$fileName")
        return bookFile.readText()
    }

    fun readEpub(fileName: String): Flow<String> = flow {
        val bookFile = File(context.filesDir, "$LOCAL_DIRECTORY_NAME/$fileName")

        FileInputStream(bookFile).use { epubInputStream ->
            val book = EpubReader().readEpub(epubInputStream)
            val spineRefs = book.spine.spineReferences.filterIndexed { index, _ -> index > 0 }

            val batchSize = 10
            for (batchStart in spineRefs.indices step batchSize) {
                val batchEnd = minOf(batchStart + batchSize, spineRefs.size)
                val batchText = processEpubBatch(spineRefs, batchStart, batchEnd)

                if (batchText.isNotBlank()) {
                    emit(batchText)
                    delay(100)
                }
            }
        }
    }

    private fun processEpubBatch(
        spineRefs: List<SpineReference>,
        batchStart: Int,
        batchEnd: Int
    ): String {
        val batchText = StringBuilder()

        for (i in batchStart until batchEnd) {
            spineRefs[i].resource.reader.use { reader ->
                val htmlContent = reader.readText()
                val cleanText = cleanHtmlContent(htmlContent)

                if (cleanText.isNotBlank()) {
                    batchText.append(cleanText).append("\n\n")
                }
            }
        }

        return batchText.toString()
    }

    private fun cleanHtmlContent(htmlContent: String): String {
        return htmlContent
            .replace(Regex("<p.*?>"), "\n")
            .replace(Regex("<br.*?>"), "\n")
            .replace(Regex("<div.*?>"), "\n")
            .replace(Regex("</p>|</div>"), "\n")
            .replace(Regex("<[^>]*>"), "")
            .replace(Regex("\\n\\s*\\n"), "\n\n")
            .trim()
    }

    fun readPdf(fileName: String): Flow<String> = flow {
        val bookFile = File(context.filesDir, "$LOCAL_DIRECTORY_NAME/$fileName")
        val document = PDDocument.load(bookFile)

        val batchSize = 10
        for (batchStart in 1..document.numberOfPages step batchSize) {
            val batchEnd = minOf(batchStart + batchSize - 1, document.numberOfPages)

            val textStripper = PDFTextStripper().apply {
                startPage = batchStart
                endPage = batchEnd
            }
            val batchText = textStripper.getText(document)
            emit(batchText)
            delay(100)
        }
        document.close()
    }

    private val prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveProgressToSharedPreferences(fileName: String, progress: Float) {
        prefs.edit() {
            putFloat("progress_$fileName", progress)
        }
    }

    fun getProgressFromSharedPreferences(fileName: String): Float {
        return prefs.getFloat("progress_$fileName", 0f)
    }

    companion object {
        private const val LOCAL_DIRECTORY_NAME = "books"
        private const val SHARED_PREFERENCES_NAME = "progress"
    }
}
