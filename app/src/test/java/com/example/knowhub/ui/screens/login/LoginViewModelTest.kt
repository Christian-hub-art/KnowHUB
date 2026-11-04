package com.example.knowhub.ui.screens.login

import com.example.knowhub.MainDispatcherRule
import com.example.knowhub.data.datasource.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        authRepository = mockk()
        viewModel = LoginViewModel(authRepository)
    }

    @Test
    fun `inicio de sesion con campos vacios muestra error sin llamar al repositorio`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            // El estado inicial tiene las credenciales vacías.

            // Act
            viewModel.inciarSesionButtonPressed()

            // Assert
            assertTrue(viewModel.uiState.value.mostrarMensajeError)
            assertEquals("Todos los campos son obligatorios", viewModel.uiState.value.errorMessage)
            coVerify(exactly = 0) { authRepository.signIn(any(), any()) }
        }

    @Test
    fun `inicio de sesion exitoso actualiza estado y llama al repositorio`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            coEvery { authRepository.signIn("ana@example.com", "clave") } returns Result.success(Unit)
            viewModel.updateNombreOCorreo("ana@example.com")
            viewModel.updateContrasena("clave")

            // Act
            viewModel.inciarSesionButtonPressed()
            advanceUntilIdle()

            // Assert
            assertTrue(viewModel.uiState.value.navigateInicio)
            assertFalse(viewModel.uiState.value.mostrarMensajeError)
            coVerify(exactly = 1) { authRepository.signIn("ana@example.com", "clave") }
        }

    @Test
    fun `fallo de inicio de sesion muestra el mensaje del resultado`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            coEvery { authRepository.signIn(any(), any()) } returns
                Result.failure(IllegalArgumentException("Credenciales incorrectas"))
            viewModel.updateNombreOCorreo("ana@example.com")
            viewModel.updateContrasena("incorrecta")

            // Act
            viewModel.inciarSesionButtonPressed()
            advanceUntilIdle()

            // Assert
            assertTrue(viewModel.uiState.value.mostrarMensajeError)
            assertEquals("Credenciales incorrectas", viewModel.uiState.value.errorMessage)
            assertFalse(viewModel.uiState.value.navigateInicio)
            coVerify(exactly = 1) { authRepository.signIn("ana@example.com", "incorrecta") }
        }

    @Test
    fun `botones de registro e invitado actualizan sus destinos de navegacion`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Arrange
            // Se usa el estado inicial del ViewModel.

            // Act
            viewModel.registerButtonPressed()
            viewModel.continuarButtonPressed()

            // Assert
            assertTrue(viewModel.uiState.value.navigateRegister)
            assertTrue(viewModel.uiState.value.navigateContinuar)
            coVerify(exactly = 0) { authRepository.signIn(any(), any()) }
        }
}
