package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.UserRemoteDataSource
import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.dtos.UserSummaryDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class UserFirestoreDataSourceImpl @Inject constructor (private val db: FirebaseFirestore):
    UserRemoteDataSource {
    override suspend fun registerUser(
        registerUserDto: RegisterUserDto,
        userId: String
    ) {
        val docRef = db.collection("users").document(userId)
        docRef.set(registerUserDto).await()


    }

    override suspend fun getUserById(id: String, currentUserId: String): UserProfileDto {
        val userRef = db.collection("users").document(id)
        val snapshot = userRef.get().await()
        val user = snapshot.toObject(UserProfileDto::class.java)
            ?: throw NoSuchElementException("No se pudo obtener el usuario $id")
        val followers = userRef.collection("followers").get().await().size()
        val following = userRef.collection("following").get().await().size()
        val followed = currentUserId.isNotBlank() &&
            currentUserId != id &&
            db.collection("users").document(currentUserId)
                .collection("following").document(id).get().await().exists()

        return user.copy(
            followed = followed,
            followersCount = followers,
            followingCount = following
        )
    }

    override suspend fun updateUserProfileImage(userId: String, photoUrl: String) {
        db.collection("users").document(userId).update("foto", photoUrl).await()
    }

    override suspend fun followOrUnfollowUser(currentUserId: String, targetUserId: String) {
        require(currentUserId.isNotBlank()) { "Se requiere un usuario autenticado para seguir." }
        require(targetUserId.isNotBlank()) { "El usuario objetivo no es válido." }
        require(currentUserId != targetUserId) { "No puedes seguirte a ti mismo." }

        val currentUserRef = db.collection("users").document(currentUserId)
        val targetUserRef = db.collection("users").document(targetUserId)

        val followingRef = currentUserRef.collection("following").document(targetUserId)
        val followersRef = targetUserRef.collection("followers").document(currentUserId)
        val notificationRef = targetUserRef.collection("notifications").document()

        db.runTransaction { transaction ->
            val currentUser = transaction.get(currentUserRef)
            val targetUser = transaction.get(targetUserRef)
            val followingDoc = transaction.get(followingRef)
            transaction.get(followersRef)
            check(currentUser.exists()) { "El usuario actual no existe." }
            check(targetUser.exists()) { "El usuario que intentas seguir no existe." }

            if (followingDoc.exists()) {
                transaction.delete(followingRef)
                transaction.delete(followersRef)
            } else {
                transaction.set(followingRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.set(followersRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.set(
                    notificationRef,
                    mapOf(
                        "type" to "follower",
                        "title" to "Nuevo seguidor",
                        "body" to "${currentUser.getString("userName") ?: "Un usuario"} empezó a seguirte.",
                        "actorId" to currentUserId,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "read" to false
                    )
                )
            }
        }.await()
    }

    override suspend fun getConnections(userId: String, followers: Boolean): List<UserSummaryDto> {
        val relationCollection = if (followers) "followers" else "following"
        val relationSnapshot = db.collection("users")
            .document(userId)
            .collection(relationCollection)
            .get()
            .await()

        return relationSnapshot.documents.mapNotNull { it.id }.map { relatedUserId ->
            val profile = db.collection("users").document(relatedUserId).get().await()
            val user = profile.toObject(UserSummaryDto::class.java)
                ?: throw NoSuchElementException("No se encontró el usuario $relatedUserId")
            user.copy(userId = relatedUserId)
        }
    }

    override suspend fun getFollowingIds(userId: String): Set<String> =
        db.collection("users")
            .document(userId)
            .collection("following")
            .get()
            .await()
            .documents
            .mapTo(mutableSetOf()) { it.id }

}