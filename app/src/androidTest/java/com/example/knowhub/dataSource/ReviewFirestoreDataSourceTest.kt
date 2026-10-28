package com.example.knowhub.dataSource

import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class ReviewFirestoreDataSourceTest {

    private val db = Firebase.firestore
    private lateinit var datasource: ReviewFirestoreDataSourceImpl

    private fun generateReview(i: Int): ReviewDto = ReviewDto(
        id = "review_$i",
        userId = "user_$i",
        clase = "MAT10$i",
        nombreAsignatura = "Asignatura $i",
        nombreProfesor = "Profesor $i",
        descripcion = "Reseña de prueba $i",
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

        datasource = ReviewFirestoreDataSourceImpl(db)

        // Limpiar colecciones
        val reviews = db.collection("reviews").get().await()
        for (doc in reviews) {
            val likes = doc.reference.collection("likes").get().await()
            for (like in likes) like.reference.delete().await()
            doc.reference.delete().await()
        }

        val users = db.collection("users").get().await()
        for (doc in users) {
            val followers = doc.reference.collection("followers").get().await()
            for (f in followers) f.reference.delete().await()
            val following = doc.reference.collection("following").get().await()
            for (f in following) f.reference.delete().await()
            doc.reference.delete().await()
        }

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

        // Cargar 10 reseñas de prueba
        val batch = db.batch()
        repeat(10) { i ->
            val review = generateReview(i + 1)
            batch.set(db.collection("reviews").document(review.id), review)
        }
        batch.commit().await()
    }

    @After
    fun tearDown() = runTest {
        try {
            val reviews = db.collection("reviews").get().await()
            for (doc in reviews) {
                val likes = doc.reference.collection("likes").get().await()
                for (like in likes) like.reference.delete().await()
                doc.reference.delete().await()
            }

            val users = db.collection("users").get().await()
            for (doc in users) {
                val followers = doc.reference.collection("followers").get().await()
                for (f in followers) f.reference.delete().await()
                val following = doc.reference.collection("following").get().await()
                for (f in following) f.reference.delete().await()
                doc.reference.delete().await()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getAllReviews_returnsListOfReviews() = runTest {
        val reviews = datasource.getAllReviews()
        Truth.assertThat(reviews).hasSize(10)
    }

    @Test
    fun getReviewById_validId_returnsReview() = runTest {
        val reviewId = "review_5"
        val review = datasource.getReviewById(reviewId, "")
        Truth.assertThat(review).isNotNull()
        Truth.assertThat(review.id).isEqualTo(reviewId)
        Truth.assertThat(review.nombreAsignatura).isEqualTo("Asignatura 5")
    }

    @Test
    fun getReviewsByUser_returnsUserReviews() = runTest {
        val userId = "user_3"
        val userReviews = datasource.getReviewsByUser(userId)
        Truth.assertThat(userReviews).isNotEmpty()
        Truth.assertThat(userReviews[0].userId).isEqualTo(userId)
    }

    @Test
    fun createReview_storesDocument_documentExists() = runTest {
        val newReviewDto = CreateReviewDto(
            userId = "user_new",
            clase = "PHY101",
            nombreAsignatura = "Física",
            nombreProfesor = "Prof. Newton",
            descripcion = "Muy buena física",
            calificacion = 5,
            generalReviewId = 99,
            user = ReviewUserDto(name = "User New", userName = "user_new")
        )
        datasource.createReview(newReviewDto)

        val reviews = datasource.getAllReviews()
        Truth.assertThat(reviews).hasSize(11)

        val createdReview = reviews.find { it.userId == "user_new" }
        Truth.assertThat(createdReview).isNotNull()
        Truth.assertThat(createdReview?.clase).isEqualTo("PHY101")
    }

    @Test
    fun updateReview_updatesFieldsSuccessfully() = runTest {
        val reviewId = "review_1"
        val updatedDto = CreateReviewDto(
            clase = "MAT999",
            nombreAsignatura = "Asignatura Actualizada",
            nombreProfesor = "Prof. Updated",
            descripcion = "Desc actualizada",
            calificacion = 3
        )
        datasource.updateReview(reviewId, updatedDto)

        val review = datasource.getReviewById(reviewId, "")
        Truth.assertThat(review.nombreAsignatura).isEqualTo("Asignatura Actualizada")
        Truth.assertThat(review.calificacion).isEqualTo(3)
    }

    @Test
    fun deleteReview_removesDocument() = runTest {
        val reviewId = "review_1"
        datasource.deleteReview(reviewId)

        val reviews = datasource.getAllReviews()
        Truth.assertThat(reviews).hasSize(9)
    }

    @Test
    fun sendOrDeleteReviewLike_addsLikeAndIncrementsCount() = runTest {
        val reviewId = "review_1"
        val userId = "user_2"

        datasource.sendOrDeleteReviewLike(reviewId, userId)

        val review = datasource.getReviewById(reviewId, userId)
        Truth.assertThat(review.likes).isGreaterThan(0)
        Truth.assertThat(review.liked).isTrue()
    }

    @Test
    fun sendOrDeleteReviewLike_removesLikeAndDecrementsCount() = runTest {
        val reviewId = "review_1"
        val userId = "user_2"

        datasource.sendOrDeleteReviewLike(reviewId, userId)
        datasource.sendOrDeleteReviewLike(reviewId, userId)

        val review = datasource.getReviewById(reviewId, userId)
        Truth.assertThat(review.liked).isFalse()
    }
}
