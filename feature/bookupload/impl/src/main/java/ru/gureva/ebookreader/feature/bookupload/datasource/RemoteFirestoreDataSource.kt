package ru.gureva.ebookreader.feature.bookupload.datasource

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import ru.gureva.ebookreader.feature.bookupload.model.FirestoreBookMetadata

class RemoteFirestoreDataSource {
    suspend fun saveBookMetadata(userId: String, metadata: FirestoreBookMetadata) {
        Firebase.firestore
            .collection(COLLECTION_NAME)
            .document(userId)
            .collection(COLLECTION_NAME)
            .document()
            .set(metadata)
            .await()
    }

    companion object {
        private const val COLLECTION_NAME = "books"
    }
}
