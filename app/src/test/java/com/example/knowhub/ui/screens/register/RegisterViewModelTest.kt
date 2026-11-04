package com.example.knowhub.ui.screens.register

import com.example.knowhub.MainDispatcherRule
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        authRepository = mockk()
        userRepository = mockk()
        viewModel = RegisterViewModel(authRepository, userRepository)
    }

    @Test
    fun `registro con campos vacios muestra error sin llamar a repositorios`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            // El formulario comienza vacío.

            // Act
            viewModel.registrarseSesionButtonPressed()

            // Assert
            assertTrue(viewModel.uiState.value.mostrarMensajeError)
            assertEquals("Todos los campos son obligatorios", viewModel.uiState.value.errorMessage)
            coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
            coVerify(exactly = 0) { userRepository.registerUser(any(), any(), any()) }
        }

    @Test
    fun `registro con contrasenas diferentes muestra error sin registrar`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            viewModel.updateNombre("Ana")
            viewModel.updateCorreoElectronico("ana@example.com")
            viewModel.updateNombreUsuario("ana")
            viewModel.updateContrasena("clave-1")
            viewModel.updateConfirmarContrasena("clave-2")

            // Act
            viewModel.registrarseSesionButtonPressed()

            // Assert
            assertTrue(viewModel.uiState.value.mostrarMensajeError)
            assertEquals("Las contraseñas no coinciden", viewModel.uiState.value.errorMessage)
            coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
            coVerify(exactly = 0) { userRepository.registerUser(any(), any(), any()) }
        }

    @Test
    fun `registro exitoso crea la cuenta y registra el perfil`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            val firebaseUser = mockk<FirebaseUser>()
            every { authRepository.currentUser } returns firebaseUser
            every { firebaseUser.uid } returns "user-123"
            coEvery { authRepository.signUp("ana@example.com", "clave") } returns Result.success(Unit)
            coEvery { userRepository.registerUser("Ana", "ana", "user-123") } returns
                Result.success(Unit)
            fillValidForm()

            // Act
            viewModel.registrarseSesionButtonPressed()
            advanceUntilIdle()

            // Assert
            assertTrue(viewModel.uiState.value.navigateInicio)
            assertFalse(viewModel.uiState.value.mostrarMensajeError)
            coVerify(exactly = 1) { authRepository.signUp("ana@example.com", "clave") }
            coVerify(exactly = 1) { userRepository.registerUser("Ana", "ana", "user-123") }
        }

    @Test
    fun `fallo de registro muestra error y no registra el perfil`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            val authRemoteDataSource = mockk<AuthRemoteDataSource>()
            coEvery { authRemoteDataSource.signUp("ana@example.com", "clave") } throws
                IllegalStateException("El correo ya se encuentra registrado")
            viewModel = RegisterViewModel(AuthRepository(authRemoteDataSource), userRepository)
            fillValidForm()

            // Act
            viewModel.registrarseSesionButtonPressed()
            advanceUntilIdle()

            // Assert
            assertTrue(viewModel.uiState.value.mostrarMensajeError)
            assertEquals("Error al registrar usuario", viewModel.uiState.value.errorMessage)
            assertFalse(viewModel.uiState.value.navigateInicio)
            coVerify(exactly = 1) { authRemoteDataSource.signUp("ana@example.com", "clave") }
            coVerify(exactly = 0) { userRepository.registerUser(any(), any(), any()) }
        }

    private fun fillValidForm() {
        viewModel.updateNombre("Ana")
        viewModel.updateCorreoElectronico("ana@example.com")
        viewModel.updateNombreUsuario("ana")
        viewModel.updateContrasena("clave")
        viewModel.updateConfirmarContrasena("clave")
    }
}
