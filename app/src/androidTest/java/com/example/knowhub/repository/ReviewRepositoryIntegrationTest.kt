package com.example.knowhub.repository

import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class ReviewRepositoryIntegrationTest {

    private val db = Firebase.firestore
    private lateinit var repository: ReviewRepository

    private fun generateReview(i: Int): ReviewDto = ReviewDto(
        id = "review_$i",
        userId = "user_$i",
        clase = "MAT10$i",
        nombreAsignatura = "Asignatura $i",
        nombreProfesor = "Profesor $i",
        descripcion = "Reseña $i",
        calificacion = (i % 5) + 1,
        likes = i,
        cantidadComentarios = 0,
        fechaPublicacion = "2025-01-01",
        user = ReviewUserDto(name = "User $i", userName = "user_$i"),
        generalReviewId = i
    )

    @Before
    fun setup() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
        }

        val reviewDataSource = ReviewFirestoreDataSourceImpl(db)
        val userDataSource = UserFirestoreDataSourceImpl(db)
        val authDataSource = AuthRemoteDataSource(FirebaseAuth.getInstance())
        val commentDataSource = CommentFirestoreDataSourceImpl(db)

        repository = ReviewRepository(reviewDataSource, userDataSource, authDataSource, commentDataSource)

        // Limpiar colecciones
        val reviews = db.collection("reviews").get().await()
        for (doc in reviews) doc.reference.delete().await()

        val users = db.collection("users").get().await()
        for (doc in users) doc.reference.delete().await()

        // Cargar usuarios de prueba
        val userBatch = db.batch()
        repeat(10) { i ->
            val uDto = UserProfileDto(
                id = "user_${i + 1}",
                name = "User ${i + 1}",
                userName = "user_${i + 1}"
            )
            userBatch.set(db.collection("users").document(uDto.id), uDto)
        }
        userBatch.commit().await()

        // Cargar 10 reseñas de prueba usando batch
        val reviewBatch = db.batch()
        repeat(10) { i ->
            val review = generateReview(i + 1)
            reviewBatch.set(db.collection("reviews").document(review.id), review)
        }
        reviewBatch.commit().await()
    }

    @After
    fun tearDown() = runTest {
        try {
            val reviews = db.collection("reviews").get().await()
            for (doc in reviews) doc.reference.delete().await()

            val users = db.collection("users").get().await()
            for (doc in users) doc.reference.delete().await()

            FirebaseAuth.getInstance().currentUser?.delete()?.await()
            FirebaseAuth.getInstance().signOut()
        } catch (e: Exception) {
        }
    }

    @Test
    fun getReviews_returnsSuccessWithReviews() = runTest {
        val result = repository.getReviews()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).hasSize(10)
    }

    @Test
    fun getReviewById_validId_returnsSuccessWithReview() = runTest {
        val reviewId = "review_1"
        val result = repository.getReviewById(reviewId)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.id).isEqualTo(reviewId)
        Truth.assertThat(result.getOrNull()?.nombreAsignatura).isEqualTo("Asignatura 1")
    }

    @Test
    fun getReviewsByUser_returnsSuccessWithUserReviews() = runTest {
        val userId = "user_2"
        val result = repository.getReviewsByUser(userId)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotEmpty()
    }

    @Test
    fun createReview_validData_returnsSuccess() = runTest {
        val result = repository.createReview(
            userId = "user_1",
            clase = "MAT200",
            nombreAsignatura = "Calculo",
            nombreProfesor = "Prof. Smith",
            descripcion = "Muy buena",
            generalReviewId = 10,
            calificacion = 5
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val reviews = repository.getReviews().getOrNull()
        Truth.assertThat(reviews).hasSize(11)
        val created = reviews?.find { it.nombreAsignatura == "Calculo" }
        Truth.assertThat(created).isNotNull()
        Truth.assertThat(created?.nombreProfesor).isEqualTo("Prof. Smith")
        Truth.assertThat(created?.calificacion).isEqualTo(5)
        Truth.assertThat(created?.descripcion).isEqualTo("Muy buena")
        Truth.assertThat(created?.userId).isEqualTo("user_1")
    }

    @Test
    fun updateReview_validData_returnsSuccess() = runTest {
        val reviewId = "review_1"
        val result = repository.updateReview(
            reviewId = reviewId,
            clase = "MAT101-MOD",
            nombreAsignatura = "Asignatura Modificada",
            nombreProfesor = "Prof. Modificado",
            descripcion = "Descripción modificada",
            calificacion = 4
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val updatedReviewResult = repository.getReviewById(reviewId)
        Truth.assertThat(updatedReviewResult.isSuccess).isTrue()
        val updatedReview = updatedReviewResult.getOrNull()
        Truth.assertThat(updatedReview?.nombreAsignatura).isEqualTo("Asignatura Modificada")
        Truth.assertThat(updatedReview?.nombreProfesor).isEqualTo("Prof. Modificado")
        Truth.assertThat(updatedReview?.descripcion).isEqualTo("Descripción modificada")
        Truth.assertThat(updatedReview?.calificacion).isEqualTo(4)
    }

    @Test
    fun deleteReview_validId_returnsSuccess() = runTest {
        val reviewId = "review_1"
        val result = repository.deleteReview(reviewId)
        Truth.assertThat(result.isSuccess).isTrue()

        // Verificar que al buscar la reseña eliminada ya no exista
        val getResult = repository.getReviewById(reviewId)
        Truth.assertThat(getResult.isFailure).isTrue()

        // Verificar que el total de reseñas disminuyó a 9
        val reviews = repository.getReviews().getOrNull()
        Truth.assertThat(reviews).hasSize(9)
    }

    @Test
    fun sendOrDeleteReviewLike_validId_returnsSuccess() = runTest {
        val reviewId = "review_1"
        val userId = "user_2"

        val initialReview = repository.getReviewById(reviewId).getOrNull()
        val initialLikes = initialReview?.likes ?: 0

        val result = repository.sendOrDeleteReviewLike(reviewId, userId)
        Truth.assertThat(result.isSuccess).isTrue()

        // Verificar que los likes aumentaron y se marcó como liked
        val updatedReview = repository.getReviewById(reviewId).getOrNull()
        Truth.assertThat(updatedReview?.likes).isEqualTo(initialLikes + 1)
        Truth.assertThat(updatedReview?.liked).isTrue()
    }

    @Test
    fun getReviewById_nonExistentId_returnsFailure() = runTest {
        val result = repository.getReviewById("review_non_existent")
        Truth.assertThat(result.isFailure).isTrue()
    }
}
