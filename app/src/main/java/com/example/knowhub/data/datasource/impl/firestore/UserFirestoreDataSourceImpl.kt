package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.UserRemoteDataSource
import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
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

    override suspend fun getUserById(id: String, currentUserId: String  ): UserProfileDto {
        val docRef = db.collection("users").document(id)
        val respuesta  = docRef.get().await()
        val user = respuesta.toObject(UserProfileDto::class.java) ?: throw Exception("No se pudo obetenr el usuario")

        if (currentUserId.isNotEmpty()) {
            val followerDoc = db.collection("users").document(id).collection("followers").document(currentUserId).get().await()

            val exist = followerDoc.exists()

            user.followed = exist
        }

        return user


    }

    override suspend fun updateUserProfileImage(userId: String, photoUrl: String) {
        db.collection("users").document(userId).update("foto", photoUrl).await()
    }

    override suspend fun followOrUnfollowUser(
        currentUserId: String,
        targetUserId: String
    ) {
        val currentUserRef = db.collection("users").document(currentUserId)
        val targetUserRef = db.collection("users").document(targetUserId)

        val followinRef = currentUserRef.collection("following").document(targetUserId)
        val followerRef = targetUserRef.collection("followers").document(currentUserId)

        db.runTransaction { transaction ->
            val followingDoc = transaction.get(followinRef)

            if(followingDoc.exists()){

                transaction.delete(followinRef)
                transaction.delete(followerRef)
                transaction.update(currentUserRef, "followinCount", FieldValue.increment(-1))
                transaction.update(targetUserRef, "followersCount", FieldValue.increment(-1))

            }else{
                transaction.set(followinRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.set(followerRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(currentUserRef, "followinCount", FieldValue.increment(1))
                transaction.update(targetUserRef, "followersCount", FieldValue.increment(1))
            }
        }.await()
    }

}