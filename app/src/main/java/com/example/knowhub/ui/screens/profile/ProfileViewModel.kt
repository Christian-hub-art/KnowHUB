package com.example.knowhub.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState

    fun updateNombre(input: String) {
        _uiState.update { it.copy(nombre = input) }
    }

    fun guardarBottonPressed() {
        if (_uiState.value.nombre.isNullOrEmpty()) {
            _uiState.update { it.copy(mostrarMensajeErrorGuardar = true, errorMessageGuardar = "No ha puesto un nombre nuevo") }
        }
    }

    fun cancelarBottonPressed() {
    }

    fun eliminarcuentaBottonPressed() = Unit
    fun cambiarcontraseñaBottonPressed() = Unit
    fun cambiairCorreoBottonPressed() = Unit

    fun subirfotoBottonPressed(imageUri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(mostrarMensajeErrorFoto = false) }
            val result = storageRepository.uploadProfileImage(imageUri)
            _uiState.update {
                it.copy(
                    profileImageUrl = result.getOrNull() ?: it.profileImageUrl,
                    mostrarMensajeErrorFoto = result.isFailure,
                    errorMessageFoto = result.exceptionOrNull()?.localizedMessage ?: "Error al subir la imagen"
                )
            }
        }
    }
}
