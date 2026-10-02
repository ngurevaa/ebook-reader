package ru.gureva.ebookreader.feature.booklist.repository

import com.google.firebase.firestore.DocumentSnapshot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify
import ru.gureva.ebookreader.database.entity.BookEntity
import ru.gureva.ebookreader.feature.booklist.datasource.LocalBookDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteFirestoreDataSource
import ru.gureva.ebookreader.feature.booklist.datasource.RemoteSupabaseDataSource
import ru.gureva.ebookreader.feature.booklist.mapper.mapToBook
import java.util.Date

class BookRepositoryImplTest {
    private val userId = "id"
    private val fileName = "book.pdf"
    private val bytes = byteArrayOf(1, 2, 3)

    private val localBookDataSource = mock<LocalBookDataSource>()
    private val remoteSupabaseDataSource = mock<RemoteSupabaseDataSource>()
    private val remoteFirestoreDataSource = mock<RemoteFirestoreDataSource>()
    private val bookRepository = BookRepositoryImpl(
        remoteFirestoreDataSource,
        remoteSupabaseDataSource,
        localBookDataSource
    )

    private val book1 = BookEntity(
        fileName = "pride.epub",
        title = "Pride and Prejudice",
        author = "Jane Austen",
        creationDate = Date(1_700_000_000_000L),
        isLocal = true
    )

    private val book2 = BookEntity(
        fileName = "piece_and_war.pdf",
        title = "Piece and War",
        author = "Lev Tolstoy",
        creationDate = Date(1_600_000_000_000L),
        isLocal = false
    )

    private val document1 = mock<DocumentSnapshot> {
        on { getString("fileName") } doReturn "pride.epub"
        on { getString("title") } doReturn "Pride and Prejudice"
        on { getString("author") } doReturn "Jane Austen"
        on { getDate("creationDate") } doReturn Date(1_700_000_000_000L)
        on { getBoolean("isLocal") } doReturn true
    }

    private val document2 = mock<DocumentSnapshot> {
        on { getString("fileName") } doReturn "piece_and_war.pdf"
        on { getString("title") } doReturn "Piece and War"
        on { getString("author") } doReturn "Lev Tolstoy"
        on { getDate("creationDate") } doReturn Date(1_600_000_000_000L)
        on { getBoolean("isLocal") } doReturn true
    }

    @Test
    fun `downloads book from supabase`() = runTest {
        remoteSupabaseDataSource.stub {
            on { downloadBookFromStorage(userId, fileName) } doReturn bytes
        }

        val result = bookRepository.downloadBookFromSupabase(userId, fileName)

        assertEquals(bytes, result)
        verify(remoteSupabaseDataSource).downloadBookFromStorage(userId, fileName)
    }

    @Test
    fun `deletes book from internal storage and updates local status`() = runTest {
        bookRepository.deleteLocalBook(fileName)

        inOrder(localBookDataSource) {
            verify(localBookDataSource).deleteBook(fileName)
            verify(localBookDataSource).updateIsLocal(fileName, false)
        }
    }

    @Test
    fun `throws exception when deleting book fails`() = runTest {
        localBookDataSource.stub {
            on { deleteBook(fileName) } doThrow IOException()
        }

        assertThrows(IOException::class.java) {
            runBlocking {
                bookRepository.deleteLocalBook(fileName)
            }
        }

        verify(localBookDataSource).deleteBook(fileName)
        verify(localBookDataSource, never()).updateIsLocal(fileName, false)
    }

    @Test
    fun `save book to internal storage and update local status`() = runTest {
        bookRepository.saveBookLocal(bytes, fileName)

        inOrder(localBookDataSource) {
            verify(localBookDataSource).saveBook(bytes, fileName)
            verify(localBookDataSource).updateIsLocal(fileName, true)
        }
    }

    @Test
    fun `throws exception when saving book fails`() = runTest {
        localBookDataSource.stub {
            on { saveBook(bytes, fileName) } doThrow IOException()
        }

        assertThrows(IOException::class.java) {
            runBlocking {
                bookRepository.saveBookLocal(bytes, fileName)
            }
        }

        verify(localBookDataSource).saveBook(bytes, fileName)
        verify(localBookDataSource, never()).updateIsLocal(fileName, true)
    }

    @Test
    fun `get all books from internal storage`() = runTest {
        val books = listOf(book1, book2)
        localBookDataSource.stub {
            on { getBooks() } doReturn flowOf(books)
        }

        val result = bookRepository.getAllBooks().first()

        val expected = books.map { it.mapToBook() }
        assertEquals(expected, result)
    }

    @Test
    fun `does not emit duplicate books`() = runTest {
        val books = listOf(book1, book2)

        localBookDataSource.stub {
            on { getBooks() } doReturn flowOf(books, books)
        }

        val result = bookRepository.getAllBooks().toList()

        assertEquals(1, result.size)
        assertEquals(books.map { it.mapToBook() }, result[0])
    }

    @Test
    fun `syncs local books with firebase`() = runTest {
        remoteFirestoreDataSource.stub {
            on { getUserBooks(userId) } doReturn listOf(document1, document2)
        }
        localBookDataSource.stub {
            on { getBooksOnce() } doReturn listOf(book1, book2)
        }

        bookRepository.syncBooksFromFirebase(userId)

        verify(localBookDataSource).upsertBooks(
            listOf(book1, book2)
        )
        verify(localBookDataSource).deleteMissingBooks(
            listOf(book1.fileName, book2.fileName)
        )
    }

    @Test
    fun `sets book as not local when it is missing locally`() = runTest {
        remoteFirestoreDataSource.stub {
            on { getUserBooks(userId) } doReturn listOf(document1)
        }

        localBookDataSource.stub {
            on { getBooksOnce() } doReturn listOf(book2)
        }

        bookRepository.syncBooksFromFirebase(userId)

        verify(localBookDataSource).upsertBooks(
            listOf(book1.copy(isLocal = false))
        )
        verify(localBookDataSource).deleteMissingBooks(
            listOf(book1.fileName)
        )
    }

    @Test
    fun `syncs empty book list from Firebase`() = runTest {
        remoteFirestoreDataSource.stub {
            on { getUserBooks(userId) } doReturn emptyList()
        }

        localBookDataSource.stub {
            on { getBooksOnce() } doReturn listOf(book1, book2)
        }

        bookRepository.syncBooksFromFirebase(userId)

        verify(localBookDataSource).upsertBooks(emptyList())
        verify(localBookDataSource).deleteMissingBooks(emptyList())
    }
}
