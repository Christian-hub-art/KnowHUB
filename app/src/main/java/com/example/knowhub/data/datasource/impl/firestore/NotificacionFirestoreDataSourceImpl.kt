package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.NotificacionRemoteDataSource
import com.example.knowhub.data.datasource.dtos.NotificacionDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NotificacionFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : NotificacionRemoteDataSource {

    override fun listenNotificaciones(userId: String): Flow<List<NotificacionDto>> = callbackFlow {
        val listener = db.collection("users").document(userId).collection("notificaciones")
            .orderBy("fecha", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val notificaciones = snapshot.documents.map { doc ->
                        val notificacion = doc.toObject(NotificacionDto::class.java)
                        notificacion?.copy(id = doc.id) ?: throw Exception("Notificación no encontrada")
                    }
                    trySend(notificaciones).isSuccess
                }
            }

        awaitClose { listener.remove() }
    }
}
