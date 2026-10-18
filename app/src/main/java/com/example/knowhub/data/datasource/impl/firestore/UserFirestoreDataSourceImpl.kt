package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.UserRemoteDataSource
import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
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

    override suspend fun getUserById(id: String): UserProfileDto {
        val docRef = db.collection("users").document(id)
        val snapshot = docRef.get().await()
        return snapshot.toObject(UserProfileDto::class.java) ?: throw Exception("No se pudo obetenr el usuario")
    }

    override suspend fun updateUserProfileImage(userId: String, photoUrl: String) {
        db.collection("users").document(userId).update("foto", photoUrl).await()
    }

}