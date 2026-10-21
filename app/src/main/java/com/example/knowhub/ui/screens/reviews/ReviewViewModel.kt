package com.example.knowhub.ui.screens.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
//ViewModel encargado de la gestión de datos y estado para la pantalla de Reseñas.
@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // Encapsulamiento: Mutable privado, Inmutable expuesto
    private val _uiState = MutableStateFlow(ReviewState())
    val uiState: StateFlow<ReviewState> = _uiState.asStateFlow()
    private var reviewsListenerJob: Job? = null

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(reviewId)
            if (result.isFailure) {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo eliminar la reseña"
                    )
                }
            }
        }
    }

    fun loadReviews() {
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "No hay un usuario autenticado") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }

            val userResult = userRepository.getUserById(userId)
            if (userResult.isSuccess) {
                _uiState.update { it.copy(usuario = userResult.getOrNull()?.userName ?: "") }
            }

            if (reviewsListenerJob?.isActive == true) return@launch
            reviewsListenerJob = viewModelScope.launch {
                reviewRepository.getReviewsByUserLive(userId)
                    .catch { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mostrarMensajeError = true,
                                errorMessage = error.message ?: "No se pudieron actualizar las reseñas"
                            )
                        }
                    }
                    .collect { reviews ->
                        _uiState.update {
                            it.copy(
                                reviews = reviews,
                                isLoading = false,
                                mostrarMensajeError = false,
                                errorMessage = ""
                            )
                        }
                    }
            }
        }
    }
}
