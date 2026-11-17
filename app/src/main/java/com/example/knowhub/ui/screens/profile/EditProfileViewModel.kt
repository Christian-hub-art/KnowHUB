package com.example.knowhub.ui.screens.profile

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.dtos.UpdateUserDto
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        EditProfileState(correo = authRepository.currentUser?.email.orEmpty())
    )
    val uiState: StateFlow<EditProfileState> = _uiState

    init {
        loadUserName()
    }

    fun updateNombre(value: String) = _uiState.update { it.copy(nombre = value) }
    fun updateCorreo(value: String) = _uiState.update { it.copy(correo = value) }
    fun updateContrasenaActual(value: String) = _uiState.update { it.copy(contrasenaActual = value) }
    fun updateNuevaContrasena(value: String) = _uiState.update { it.copy(nuevaContrasena = value) }
    fun updateConfirmarContrasena(value: String) = _uiState.update { it.copy(confirmarContrasena = value) }

    fun saveChanges() {
        val state = _uiState.value
        val user = authRepository.currentUser
        if (user == null) {
            showError("Tu sesión no está activa. Inicia sesión de nuevo.")
            return
        }

        val nombre = state.nombre.trim()
        val correo = state.correo.trim()
        val emailChanged = correo != user.email.orEmpty()
        val passwordChanged = state.nuevaContrasena.isNotBlank() ||
            state.confirmarContrasena.isNotBlank()

        if (nombre.isBlank()) {
            showError("Escribe tu nombre.")
            return
        }
        if (correo.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            showError("Escribe un correo electrónico válido.")
            return
        }
        if (passwordChanged && state.nuevaContrasena.length < MIN_PASSWORD_LENGTH) {
            showError("La nueva contraseña debe tener al menos 6 caracteres.")
            return
        }
        if (passwordChanged && state.nuevaContrasena != state.confirmarContrasena) {
            showError("La confirmación no coincide con la nueva contraseña.")
            return
        }
        if ((emailChanged || passwordChanged) && state.contrasenaActual.isBlank()) {
            showError("Escribe tu contraseña actual para cambiar el correo o la contraseña.")
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }

            if (emailChanged || passwordChanged) {
                val reauthResult = authRepository.reauthenticate(state.contrasenaActual)
                if (reauthResult.isFailure) {
                    finishWithError(reauthResult.exceptionOrNull()?.message)
                    return@launch
                }
            }

            if (emailChanged) {
                val emailResult = authRepository.updateEmail(correo)
                if (emailResult.isFailure) {
                    finishWithError(emailResult.exceptionOrNull()?.message)
                    return@launch
                }
            }

            if (passwordChanged) {
                val passwordResult = authRepository.updatePassword(state.nuevaContrasena)
                if (passwordResult.isFailure) {
                    finishWithError(passwordResult.exceptionOrNull()?.message)
                    return@launch
                }
            }

            val nameResult = userRepository.updateUserProfile(
                user.uid,
                UpdateUserDto(userName = nombre)
            )
            if (nameResult.isFailure) {
                finishWithError("No se pudo guardar el nombre. Inténtalo de nuevo.")
                return@launch
            }

            // Si cambió el correo se queda en la pantalla para que el usuario lea que debe confirmarlo
            _uiState.update {
                it.copy(
                    isSaving = false,
                    successMessage = if (emailChanged) {
                        "Te enviamos un correo a $correo. Tu correo cambiará cuando lo confirmes."
                    } else {
                        "Tus datos se actualizaron correctamente."
                    },
                    navigateBack = !emailChanged
                )
            }
        }
    }

    fun onNavigatedBack() {
        _uiState.update { it.copy(navigateBack = false) }
    }

    private fun loadUserName() {
        val userId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            val result = userRepository.getUserById(userId)
            result.onSuccess { user ->
                _uiState.update { it.copy(nombre = user.userName) }
            }.onFailure {
                showError("No se pudo cargar tu nombre. Inténtalo de nuevo.")
            }
        }
    }

    private fun showError(message: String) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    private fun finishWithError(message: String?) {
        _uiState.update {
            it.copy(
                isSaving = false,
                errorMessage = message ?: "No se pudieron guardar los cambios."
            )
        }
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}
