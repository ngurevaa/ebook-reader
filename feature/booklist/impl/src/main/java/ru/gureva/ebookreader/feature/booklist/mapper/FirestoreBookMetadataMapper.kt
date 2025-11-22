package ru.gureva.ebookreader.feature.booklist.mapper

import com.google.firebase.firestore.DocumentSnapshot
import ru.gureva.ebookreader.feature.booklist.model.FirestoreBookMetadata

fun DocumentSnapshot.toFirestoreBookMetadata(): FirestoreBookMetadata {
    return FirestoreBookMetadata(
        title = getString("title") ?: "",
        author = getString("author") ?: "",
        fileUrl = getString("fileUrl") ?: "",
        userId = getString("userId") ?: ""
    )
}
