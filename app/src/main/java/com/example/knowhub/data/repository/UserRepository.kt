package com.example.knowhub.data.repository

import com.example.knowhub.data.Review
import com.example.knowhub.data.Usuario
import com.example.knowhub.data.datasource.impl.UserRetrofitDataSourceImpl
import com.example.knowhub.data.dtos.toReview
import com.example.knowhub.data.dtos.toUsuarioProfile
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val UserRemoteDataSource: UserRetrofitDataSourceImpl
) {
    suspend fun getUserById(id: String): Result<Usuario> {
        return try {
            val usuario = UserRemoteDataSource.getUserById(id)
            val userProfileInfo = usuario.toUsuarioProfile()
            Result.success(userProfileInfo)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserReview(userId: String): Result <List<Review>> {
        return  try {
            val review = UserRemoteDataSource.getUserReview(userId)
            val reviewInfo = review.map { it.toReview() }
            Result.success(reviewInfo)
        } catch (e: Exception){
            Result.failure(e)
        }
    }



}