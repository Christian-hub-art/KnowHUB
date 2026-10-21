package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.Notificacion
import com.example.knowhub.data.datasource.NotificationRemoteDataSource
import com.example.knowhub.data.datasource.dtos.NotificationDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NotificationFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : NotificationRemoteDataSource {
    override fun listenNotifications(userId: String): Flow<List<Notificacion>> = callbackFlow {
        if (userId.isBlank()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val registration = db.collection("users")
            .document(userId)
            .collection("notifications")
            .orderBy("createdAt")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                } else if (snapshot != null) {
                    val notifications = snapshot.documents.mapNotNull { document ->
                        document.toObject(NotificationDto::class.java)
                            ?.toNotification(document.id)
                    }.sortedByDescending(Notificacion::createdAt)
                    trySend(notifications)
                }
            }
        awaitClose { registration.remove() }
    }
}
