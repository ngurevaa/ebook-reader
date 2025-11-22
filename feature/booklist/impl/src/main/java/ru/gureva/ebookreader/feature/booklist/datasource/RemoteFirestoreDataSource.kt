package ru.gureva.ebookreader.feature.booklist.datasource

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import ru.gureva.ebookreader.feature.booklist.model.FirestoreBookMetadata
import ru.gureva.ebookreader.feature.booklist.mapper.toFirestoreBookMetadata

class RemoteFirestoreDataSource {
    suspend fun getUserBooks(userId: String): List<FirestoreBookMetadata> {
        return Firebase.firestore
            .collection(COLLECTION_NAME)
            .document(userId)
            .collection(COLLECTION_NAME)
            .get()
            .await()
            .documents
            .mapNotNull { it.toFirestoreBookMetadata() }
    }

    companion object {
        private const val COLLECTION_NAME = "books"
    }
}
