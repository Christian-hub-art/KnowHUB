package com.example.knowhub.ui.screens.recuperarContrasena

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//ViewModel que envía el correo para restablecer la contraseña.
@HiltViewModel
class RecuperarContrasenaViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecuperarContrasenaState())
    val uiState: StateFlow<RecuperarContrasenaState> = _uiState

    fun updateCorreo(input: String) {
        _uiState.update { it.copy(correo = input) }
    }

    //Envía al correo escrito un enlace para cambiar la contraseña.
    fun enviarButtonPressed() {
        val correo = _uiState.value.correo.trim()
        if (correo.isEmpty()) {
            _uiState.update { it.copy(mostrarMensaje = true, mensaje = "Escribe tu correo para recuperar la contraseña") }
            return
        }
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensaje = false) }
            val result = authRepository.sendPasswordResetEmail(correo)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensaje = true,
                        mensaje = "Te enviamos un correo a $correo para cambiar tu contraseña"
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensaje = true,
                        mensaje = result.exceptionOrNull()?.message ?: ""
                    )
                }
            }
        }
    }

    fun volverButtonPressed() {
        _uiState.update { it.copy(navigateBack = true) }
    }
}
