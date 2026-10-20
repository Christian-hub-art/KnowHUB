package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BusquedaPerfilViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusquedaPerfilState())
    val uiState: StateFlow<BusquedaPerfilState> = _uiState.asStateFlow()

    fun getUserProfile(userId: String) {
        val currentUserId = authRepository.currentUser?.uid.orEmpty()
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    mostrarMensajeError = false,
                    currentUserId = currentUserId
                )
            }
            val result = userRepository.getUserById(userId, currentUserId)
            if (result.isSuccess) {
                val user = result.getOrNull()
                _uiState.update {
                    it.copy(
                        userName = user?.userName ?: "",
                        fotoPerfil = user?.foto?.ifBlank { null } ?: it.fotoPerfil,
                        followersCount = user?.followersCount ?: 0,
                        followingCount = user?.followingCount ?: 0,
                        isFollowed = user?.followed ?: false,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar el perfil"
                    )
                }
            }
        }
    }

    fun toggleFollow(userId: String) {
        val currentUserId = authRepository.currentUser?.uid
        if (currentUserId.isNullOrBlank()) {
            _uiState.update {
                it.copy(
                    mostrarMensajeError = true,
                    errorMessage = "Inicia sesión para seguir a este usuario."
                )
            }
            return
        }
        if (currentUserId == userId || _uiState.value.isUpdatingFollow) return

        viewModelScope.launch {
            _uiState.update { it.copy(isUpdatingFollow = true, mostrarMensajeError = false) }
            val result = userRepository.followOrUnfollowUser(currentUserId, userId)
            if (result.isSuccess) {
                val profileResult = userRepository.getUserById(userId, currentUserId)
                if (profileResult.isSuccess) {
                    val profile = profileResult.getOrThrow()
                    _uiState.update {
                        it.copy(
                            isFollowed = profile.followed,
                            followersCount = profile.followersCount,
                            followingCount = profile.followingCount,
                            isUpdatingFollow = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isUpdatingFollow = false,
                            mostrarMensajeError = true,
                            errorMessage = profileResult.exceptionOrNull()?.message
                                ?: "No se pudo actualizar el perfil."
                        )
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        isUpdatingFollow = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo actualizar el seguimiento."
                    )
                }
            }
        }
    }

    fun rateReview(reviewId: String, rating: Int, profileUserId: String) {
        viewModelScope.launch {
            val result = reviewRepository.rateReview(reviewId, rating)
            if (result.isSuccess) {
                getUserReviews(profileUserId)
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo guardar la calificación."
                    )
                }
            }
        }
    }

    fun getUserReviews(userId: String) {
        viewModelScope.launch {
            val result = reviewRepository.getReviewsByUser(userId)
            if (result.isSuccess) {
                val reviews = result.getOrNull() ?: emptyList()
                _uiState.update {
                    it.copy(
                        reviews = reviews,
                        // Si el perfil aún no tiene foto guardada, se usa la de sus reseñas
                        fotoPerfil = it.fotoPerfil ?: reviews.firstNotNullOfOrNull { r -> r.fotoAutor }
                    )
                }
            }
        }
    }
}
