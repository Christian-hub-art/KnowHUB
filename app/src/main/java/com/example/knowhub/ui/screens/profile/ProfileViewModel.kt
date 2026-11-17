package com.example.knowhub.ui.screens.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.R
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.StorageRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//ViewModel encargado de gestionar la lógica de negocio y el estado del perfil de usuario.
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    @ApplicationContext private val context: Context   // para leer los mensajes de strings.xml
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState(
        isAuthenticated = authRepository.currentUser != null,
        profileImageUrl = authRepository.currentUser?.photoUrl?.toString()
    ))

    val uiState: StateFlow<ProfileState> = _uiState


    fun getUser() {
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update {
                it.copy(
                    isAuthenticated = false,
                    mostrarMensajeErrorCarga = false,
                    errorMessageCarga = ""
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isAuthenticated = true,
                    isLoading = true,
                    mostrarMensajeErrorCarga = false
                )
            }
            val result = userRepository.getUserById(userId)
            if (result.isSuccess) {
                val user = result.getOrNull()
                _uiState.update {
                    it.copy(
                        nombre = user?.userName ?: "",
                        userId = userId,
                        name = user?.name ?: "",
                        profileImageUrl = user?.foto?.takeIf { it.isNotBlank() }
                            ?: authRepository.currentUser?.photoUrl?.toString(),
                        correo = authRepository.currentUser?.email ?: "",
                        seguidores = user?.followersCount ?: 0,
                        seguidos = user?.followingCount ?: 0,
                        isLoading = false
                    )
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

    fun getUserReviews() {
        val userId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            val result = reviewRepository.getReviewsByUser(userId)
            if (result.isSuccess) {
                val reviews = result.getOrNull() ?: emptyList()
                _uiState.update { it.copy(materiasResenadas = reviews.map { r -> r.nombreAsignatura }.distinct().size) }
            }
        }
    }
    //Actualiza el valor del nombre de usuario ingresado en la interfaz.
    fun updateNombre (input: String){
        _uiState.update { it.copy( nombre =  input) }
    }
    //Gestiona el evento de eliminación de la cuenta de usuario.
    fun eliminarcuentaBottonPressed () {

    }//Gestiona el flujo de cambio de contraseña.
    fun uploadImageToFirebase(uri: Uri){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isUploadingFoto = true,
                    mostrarMensajeErrorFoto = false,
                    errorMessageFoto = ""
                )
            }

            val result = storageRepository.uploadProfileImage(uri)
            if (result.isFailure) {
                _uiState.update {
                    it.copy(
                        isUploadingFoto = false,
                        mostrarMensajeErrorFoto = true,
                        errorMessageFoto = result.exceptionOrNull()?.message
                            ?: context.getString(R.string.profile_photo_error_generic)
                    )
                }
                return@launch
            }

            val photoUrl = result.getOrNull() ?: return@launch
            _uiState.update {
                it.copy(
                    profileImageUrl = photoUrl,
                    isUploadingFoto = false,
                    errorMessageFoto = ""
                )
            }

            val userId = authRepository.currentUser?.uid
            if (userId == null) {
                _uiState.update {
                    it.copy(
                        mostrarMensajeErrorFoto = true,
                        errorMessageFoto = context.getString(R.string.profile_photo_error_not_authenticated)
                    )
                }
                return@launch
            }

            val saveResult = userRepository.updateUserProfileImage(userId, photoUrl)
            if (saveResult.isFailure) {
                _uiState.update {
                    it.copy(
                        mostrarMensajeErrorFoto = true,
                        errorMessageFoto = context.getString(R.string.profile_photo_error_profile_save)
                    )
                }
            }
        }
    }

}