package com.example.knowhub.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.repository.AuthRepository
import com.example.knowhub.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//ViewModel encargado de gestionar la lógica de negocio y el estado del perfil de usuario.
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState(
        profileImageUrl = authRepository.currentUser?.photoUrl?.toString() ?: ""
    ))

    val uiState: StateFlow<ProfileState> = _uiState

    init {
        cargarFotoDePerfil()
    }

    private fun cargarFotoDePerfil() {
        viewModelScope.launch {
            authRepository.refreshProfileImage().fold(
                onSuccess = { url ->
                    if (!url.isNullOrBlank()) {
                        _uiState.update { it.copy(profileImageUrl = url) }
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(errorMessageFoto = error.message ?: "No se pudo cargar la foto de perfil.",
                            mostrarMensajeErrorFoto = true, fotoActualizada = false)
                    }
                }
            )
        }
    }
    //Actualiza el valor del nombre de usuario ingresado en la interfaz.
    fun updateNombre (input: String){
        _uiState.update { it.copy( nombre =  input) }
    }
    //Valida y procesa la acción de guardar los cambios del perfil.
    fun guardarBottonPressed () {
        if (_uiState.value.nombre.isNullOrEmpty()
        ) {
            _uiState.update { it.copy(mostrarMensajeErrorGuardar = true, errorMessageGuardar = "No ha puesto un nombre nuevo") }
        }

    }
    //Cancela las ediciones realizadas y restablece los valores
    fun cancelarBottonPressed() {

    }
    //Gestiona el evento de eliminación de la cuenta de usuario.
    fun eliminarcuentaBottonPressed () {

    }//Gestiona el flujo de cambio de contraseña.
    fun cambiarcontraseñaBottonPressed (){

    }// Gestiona el flujo de cambio de correo electrónico.
    fun cambiairCorreoBottonPressed (){

    }

    fun uploadImageToFirebase(uri: Uri){
        _uiState.update { it.copy(errorMessageFoto = "", mostrarMensajeErrorFoto = false, estaSubiendoFoto = true) }
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)
            result.fold(
                onSuccess = { url ->
                    _uiState.update {
                        it.copy(profileImageUrl = url, estaSubiendoFoto = false,
                            errorMessageFoto = "Foto de perfil actualizada.",
                            mostrarMensajeErrorFoto = true, fotoActualizada = true)
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(estaSubiendoFoto = false,
                            errorMessageFoto = error.message ?: "No se pudo actualizar la foto de perfil.",
                            mostrarMensajeErrorFoto = true, fotoActualizada = false)
                    }
                }
            )
        }
    }




}
