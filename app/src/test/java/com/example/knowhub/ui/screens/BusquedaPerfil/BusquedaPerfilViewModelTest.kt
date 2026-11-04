package com.example.knowhub.ui.screens.BusquedaPerfil

import com.example.knowhub.MainDispatcherRule
import com.example.knowhub.data.Review
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BusquedaPerfilViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userRepository: UserRepository
    private lateinit var reviewRepository: ReviewRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: BusquedaPerfilViewModel

    @Before
    fun setUp() {
        userRepository = mockk()
        reviewRepository = mockk()
        authRepository = mockk()

        val mockUser: FirebaseUser = mockk()
        every { mockUser.uid } returns "currentUser123"
        every { authRepository.currentUser } returns mockUser

        viewModel = BusquedaPerfilViewModel(userRepository, reviewRepository, authRepository)
    }

    @Test
    fun `getUserProfile obtiene perfil y actualiza el estado correctamente`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            val targetUserId = "user456"
            val userProfile = UserProfileDto(
                id = targetUserId,
                userName = "Santiago",
                followersCount = 15,
                followingCount = 8,
                followed = false
            )
            coEvery { userRepository.getUserById(targetUserId) } returns Result.success(userProfile)

            // Act
            viewModel.getUserProfile(targetUserId)
            advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value
            assertEquals("Santiago", state.userName)
            assertEquals(15, state.seguidores)
            assertEquals(8, state.seguidos)
            assertFalse(state.followed)
            assertFalse(state.isLoading)
        }

    @Test
    fun `getUserReviews obtiene las resenas del usuario y actualiza el estado`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            val targetUserId = "user456"
            val testReviews = listOf(
                Review(
                    id = "rev1",
                    nombreEstudiante = "Santiago",
                    nombreProfesor = "Laura Gómez",
                    nombreAsignatura = "Cálculo I",
                    descripcion = "Excelente clase",
                    fechaPublicacion = "10 nov 2026",
                    calificacion = 5,
                    likes = 3,
                    cantidadComentarios = 1
                )
            )
            coEvery { reviewRepository.getReviewsByUser(targetUserId) } returns Result.success(testReviews)

            // Act
            viewModel.getUserReviews(targetUserId)
            advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value
            assertEquals(1, state.reviews.size)
            assertEquals("Cálculo I", state.reviews[0].nombreAsignatura)
        }
}
