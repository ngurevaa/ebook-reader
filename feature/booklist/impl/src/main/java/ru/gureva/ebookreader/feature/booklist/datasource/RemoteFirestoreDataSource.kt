package ru.gureva.ebookreader.feature.booklist.datasource

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class RemoteFirestoreDataSource {
    suspend fun getUserBooks(userId: String): List<DocumentSnapshot> {
        return Firebase.firestore
            .collection(COLLECTION_NAME)
            .document(userId)
            .collection(COLLECTION_NAME)
            .get()
            .await()
            .documents
    }

    companion object {
        private const val COLLECTION_NAME = "books"
    }
}
