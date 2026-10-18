package com.example.knowhub.ui.screens.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    //Carga desde Firestore las reseñas publicadas por el usuario autenticado.
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

            val result = reviewRepository.getReviewsByUser(userId)
            if (result.isSuccess) {
                _uiState.update { it.copy(reviews = result.getOrNull() ?: emptyList(), isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudieron cargar las reseñas"
                    )
                }
            }
        }
    }
}
