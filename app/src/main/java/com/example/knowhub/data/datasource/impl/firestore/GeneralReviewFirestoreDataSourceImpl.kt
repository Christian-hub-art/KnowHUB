package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.GeneralReviewRemoteDataSource
import com.example.knowhub.data.datasource.dtos.GeneralReviewDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GeneralReviewFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : GeneralReviewRemoteDataSource {

    override suspend fun getAllGeneralReviews(): List<GeneralReviewDto> {
        val snapshot = db.collection("materias").get().await()
        return snapshot.documents.map { doc ->
            doc.toObject(GeneralReviewDto::class.java) ?: throw Exception("Materia no encontrada")
        }.sortedBy { it.id }
    }

    override suspend fun getGeneralReviewById(id: Int): GeneralReviewDto? {
        val snapshot = db.collection("materias").document(id.toString()).get().await()
        return snapshot.toObject(GeneralReviewDto::class.java)
    }

    override suspend fun getGeneralReviewByCodigo(codigoAsignatura: Int): GeneralReviewDto? {
        val snapshot = db.collection("materias")
            .whereEqualTo("codigoAsignatura", codigoAsignatura).get().await()
        return snapshot.documents.firstOrNull()?.toObject(GeneralReviewDto::class.java)
    }

    override fun listenAllGeneralReviews(): Flow<List<GeneralReviewDto>> = callbackFlow {
        val listener = db.collection("materias").addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val materias = snapshot.documents.map { doc ->
                    doc.toObject(GeneralReviewDto::class.java) ?: throw Exception("Materia no encontrada")
                }.sortedBy { it.id }
                trySend(materias).isSuccess
            }
        }

        awaitClose { listener.remove() }
    }
}
