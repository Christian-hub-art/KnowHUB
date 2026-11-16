package com.example.knowhub.repository

import android.net.Uri
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ReviewRepositoryTest {

    private val reviewRemoteDataSource: ReviewFirestoreDataSourceImpl = mockk()
    private val userRemoteDataSource: UserFirestoreDataSourceImpl = mockk()
    private val authRemoteDataSource: AuthRemoteDataSource = mockk()
    private val commentRemoteDataSource: CommentFirestoreDataSourceImpl = mockk()

    private lateinit var reviewRepository: ReviewRepository

    @Before
    fun setUp() {
        reviewRepository = ReviewRepository(
            reviewRemoteDataSource = reviewRemoteDataSource,
            userRemoteDataSource = userRemoteDataSource,
            authRemoteDataSource = authRemoteDataSource,
            commentRemoteDataSource = commentRemoteDataSource
        )
    }

    @Test
    fun `al obtener todas las reseñas se realiza el mapeo de DTO a objeto de dominio Review`() = runTest {
        val reviewDtoList = listOf(
            ReviewDto(
                id = "rev123",
                nombreAsignatura = "Física I",
                nombreProfesor = "Dr. Perez",
                calificacion = 5,
                user = ReviewUserDto(userName = "juanp")
            )
        )
        coEvery { reviewRemoteDataSource.getAllReviews() } returns reviewDtoList

        val result = reviewRepository.getReviews()

        Truth.assertThat(result.isSuccess).isTrue()
        val review = result.getOrNull()?.firstOrNull()
        Truth.assertThat(review?.id).isEqualTo("rev123")
        Truth.assertThat(review?.nombreAsignatura).isEqualTo("Física I")
        Truth.assertThat(review?.nombreEstudiante).isEqualTo("juanp")
    }

    @Test
    fun `al obtener reseña por id se consulta el data source remoto y se mapea la reseña`() = runTest {
        coEvery { authRemoteDataSource.currentUser?.uid } returns "1"
        val reviewDto = ReviewDto(
            id = "rev99",
            nombreAsignatura = "Cálculo II",
            nombreProfesor = "Dra. Gomez",
            user = ReviewUserDto(userName = "mariag")
        )
        coEvery { reviewRemoteDataSource.getReviewById("rev99", "1") } returns reviewDto

        val result = reviewRepository.getReviewById("rev99")

        Truth.assertThat(result.isSuccess).isTrue()
        val review = result.getOrNull()
        Truth.assertThat(review?.id).isEqualTo("rev99")
        Truth.assertThat(review?.nombreProfesor).isEqualTo("Dra. Gomez")
    }

    @Test
    fun `al obtener reseñas filtradas por usuario se mapean correctamente las reseñas asociadas`() = runTest {
        val reviewDtoList = listOf(
            ReviewDto(
                id = "rev1",
                userId = "user10",
                nombreAsignatura = "Química",
                user = ReviewUserDto(userName = "carlosr")
            )
        )
        coEvery { reviewRemoteDataSource.getReviewsByUser("user10") } returns reviewDtoList

        val result = reviewRepository.getReviewsByUser("user10")

        Truth.assertThat(result.isSuccess).isTrue()
        val reviews = result.getOrNull()
        Truth.assertThat(reviews).hasSize(1)
        Truth.assertThat(reviews?.first()?.userId).isEqualTo("user10")
    }

    @Test
    fun `al crear reseña copia la foto de Firebase Authentication como la implementacion original`() = runTest {
        val photoUrl = "https://example.com/auth-profile.jpg"
        val reviewSlot = slot<CreateReviewDto>()
        val currentUser = mockk<FirebaseUser>()
        val profileUri = mockk<Uri>()
        every { authRemoteDataSource.currentUser } returns currentUser
        every { currentUser.photoUrl } returns profileUri
        every { profileUri.toString() } returns photoUrl
        coEvery { userRemoteDataSource.getUserById("user10") } returns UserProfileDto(
            id = "user10",
            userName = "carlosr"
        )
        coEvery { reviewRemoteDataSource.createReview(capture(reviewSlot)) } returns Unit

        val result = reviewRepository.createReview(
            userId = "user10",
            clase = "101",
            nombreAsignatura = "Desarrollo web",
            nombreProfesor = "Profesor",
            descripcion = "Reseña de prueba"
        )

        assertThat(result.isSuccess).isTrue()
        assertThat(reviewSlot.captured.user.profileImage).isEqualTo(photoUrl)
        coVerify(exactly = 1) { reviewRemoteDataSource.createReview(any()) }
    }
}
