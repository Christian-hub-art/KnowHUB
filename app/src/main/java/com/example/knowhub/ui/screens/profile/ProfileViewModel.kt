package com.example.knowhub.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.StorageRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//ViewModel encargado de gestionar la lógica de negocio y el estado del perfil de usuario.
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState(
        profileImageUrl = authRepository.currentUser?.photoUrl?.toString()
    ))

    val uiState: StateFlow<ProfileState> = _uiState

    init {
        getUser()
    }

    fun getUser() {
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(mostrarMensajeErrorCarga = true, errorMessageCarga = "No hay un usuario autenticado") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeErrorCarga = false) }
            val result = userRepository.getUserById(userId)
            if (result.isSuccess) {
                val user = result.getOrNull()
                _uiState.update {
                    it.copy(
                        userId = userId,
                        nombre = user?.userName ?: "",
                        profileImageUrl = user?.foto?.ifBlank { null } ?: it.profileImageUrl,
                        cantidadSeguidos = user?.followingCount ?: 0,
                        cantidadSeguidores = user?.followersCount ?: 0,
                        isLoading = false
                    )
                }
                viewModelScope.launch {
                    reviewRepository.getReviewsByUserLive(userId)
                        .catch { error ->
                            _uiState.update {
                                it.copy(
                                    mostrarMensajeErrorCarga = true,
                                    errorMessageCarga = error.message
                                        ?: "No se pudieron actualizar las materias reseñadas"
                                )
                            }
                        }
                        .collect { reviews ->
                            val materiasResenadas = reviews.map { review ->
                                review.nombreAsignatura
                            }
                            val materiasUnicas = materiasResenadas.distinct()
                            val cantidadMateriasResenadas = materiasUnicas.size
                            _uiState.update {
                                it.copy(cantidadMateriasResenadas = cantidadMateriasResenadas)
                            }
                        }
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeErrorCarga = true,
                        errorMessageCarga = result.exceptionOrNull()?.message ?: "No se pudo obtener el usuario"
                    )
                }
            }
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
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)
            if(result.isSuccess){
                _uiState.update { it.copy(profileImageUrl = result.getOrNull()) }

                // También se guarda en Firestore para que otros usuarios vean la foto
                val photoUrl = result.getOrNull()
                val userId = authRepository.currentUser?.uid
                if (photoUrl != null && userId != null) {
                    userRepository.updateUserProfileImage(userId, photoUrl)
                }
            }

        }
    }
}