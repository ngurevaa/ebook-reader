package ru.gureva.ebookreader.feature.booklist.usecase

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import ru.gureva.ebookreader.feature.booklist.repository.BookRepository

class DownloadBookUseCaseTest {
    private val userId = "id"
    private val fileName = "file.pdf"
    private val bytes = byteArrayOf(1, 2, 3)

    @Test
    fun `downloads book and saves it locally`() = runTest {
        val bookRepository = mock<BookRepository> {
            on { downloadBookFromSupabase(userId, fileName) } doReturn bytes
        }
        val sut = DownloadBookUseCaseImpl(bookRepository)

        sut(userId, fileName)

        inOrder(bookRepository) {
            verify(bookRepository).downloadBookFromSupabase(userId, fileName)
            verify(bookRepository).saveBookLocal(bytes, fileName)
        }
    }

    @Test
    fun `does not save book locally when download fails`() = runTest {
        val bookRepository = mock<BookRepository> {
            on { downloadBookFromSupabase(userId, fileName) } doThrow IOException()
        }
        val sut = DownloadBookUseCaseImpl(bookRepository)

        assertThrows(IOException::class.java) {
            runBlocking {
                sut(userId, fileName)
            }
        }

        verify(bookRepository).downloadBookFromSupabase(userId, fileName)
        verify(bookRepository, never()).saveBookLocal(any(), any())
    }

    @Test
    fun `throws exception when saving downloaded book fails`() = runTest {
        val bookRepository = mock<BookRepository> {
            on { downloadBookFromSupabase(userId, fileName) } doReturn bytes
            on { saveBookLocal(bytes, fileName) } doThrow IOException()
        }
        val sut = DownloadBookUseCaseImpl(bookRepository)

        assertThrows(IOException::class.java) {
            runBlocking {
                sut(userId, fileName)
            }
        }

        inOrder(bookRepository) {
            verify(bookRepository).downloadBookFromSupabase(userId, fileName)
            verify(bookRepository).saveBookLocal(bytes, fileName)
        }
    }
}
