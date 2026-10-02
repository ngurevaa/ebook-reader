package ru.gureva.ebookreader.feature.booklist.mapper

import com.google.firebase.firestore.DocumentSnapshot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import ru.gureva.ebookreader.database.entity.BookEntity
import java.util.Date

class BookMapperTest {
    private val bookEntity = BookEntity(
        fileName = "pride.epub",
        title = "Pride and Prejudice",
        author = "Jane Austen",
        creationDate = Date(1_700_000_000_000L),
        isLocal = true
    )

    @Test
    fun `maps all book entity fields to book`() {
        val book = bookEntity.mapToBook()

        assertEquals(bookEntity.fileName, book.fileName)
        assertEquals(bookEntity.title, book.title)
        assertEquals(bookEntity.author, book.author)
        assertEquals(bookEntity.isLocal, book.isLocal)
        assertEquals(false, book.isLoading)
    }

    @Test
    fun `maps document snapshot fields to book entity`() {
        val document = mock<DocumentSnapshot> {
            on { getString("fileName") } doReturn "pride.epub"
            on { getString("title") } doReturn "Pride and Prejudice"
            on { getString("author") } doReturn "Jane Austen"
            on { getDate("creationDate") } doReturn Date(1_700_000_000_000L)
            on { getBoolean("isLocal") } doReturn true
        }

        val bookEntity = document.mapToBookEntity()

        assertEquals("pride.epub", bookEntity.fileName)
        assertEquals("Pride and Prejudice", bookEntity.title)
        assertEquals("Jane Austen", bookEntity.author)
        assertEquals(true, bookEntity.isLocal)
        assertEquals(Date(1_700_000_000_000L), bookEntity.creationDate)
    }

    @Test
    fun `maps missing document snapshot fields to default values`() {
        val document = mock<DocumentSnapshot> {}

        val bookEntity = document.mapToBookEntity()

        assertEquals("", bookEntity.fileName)
        assertEquals("", bookEntity.title)
        assertEquals("", bookEntity.author)
        assertEquals(false, bookEntity.isLocal)
        assertNotNull(bookEntity.creationDate)
    }
}
