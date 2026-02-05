package ru.gureva.ebookreader.feature.booklist.mapper

import com.google.firebase.firestore.DocumentSnapshot
import ru.gureva.ebookreader.database.entity.BookEntity
import ru.gureva.ebookreader.feature.booklist.model.Book
import java.util.Date

fun BookEntity.mapToBook(): Book {
    return Book(
        fileName = fileName,
        title = title,
        author = author,
        isLocal = isLocal,
        isLoading = false
    )
}

fun DocumentSnapshot.mapToBookEntity(): BookEntity {
    return BookEntity(
        fileName = getString("fileName") ?: "",
        title = getString("title") ?: "",
        author = getString("author") ?: "",
        creationDate = getDate("creationDate") ?: Date(),
        isLocal = getBoolean("isLocal") ?: false
    )
}
