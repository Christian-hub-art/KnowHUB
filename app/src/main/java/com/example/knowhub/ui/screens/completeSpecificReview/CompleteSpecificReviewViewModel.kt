package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.CommentRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CompleteSpecificReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val commentRepository: CommentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteSpecificReviewState())
    val uiState: StateFlow<CompleteSpecificReviewState> = _uiState

    //Carga la reseña y sus respuestas desde Firestore.
    fun loadData(reviewId: String) {
        getReviewById(reviewId)
        getComments(reviewId)
    }

    //Obtiene la reseña usando el id del documento.
    fun getReviewById(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.getReviewById(reviewId)
            if (result.isSuccess) {
                _uiState.update { it.copy(review = result.getOrNull(), mostrarMensajeError = false) }
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar la reseña"
                    )
                }
            }
        }
    }

    //Obtiene las respuestas de la reseña (equivale a getTweetRespose del profesor).
    fun getComments(reviewId: String) {
        viewModelScope.launch {
            val result = commentRepository.getCommentsByReview(reviewId)
            if (result.isSuccess) {
                _uiState.update { it.copy(comments = result.getOrNull() ?: emptyList()) }
            }
        }
    }

    //Actualiza el texto de la respuesta que se está escribiendo.
    fun updateNuevoComentario(input: String) {
        _uiState.update { it.copy(nuevoComentario = input, mostrarMensajeErrorComentario = false) }
    }

    //Publica la respuesta y recarga la reseña y sus respuestas.
    fun responderButtonPressed(reviewId: String) {
        val texto = _uiState.value.nuevoComentario
        if (texto.isBlank()) {
            _uiState.update { it.copy(mostrarMensajeErrorComentario = true, errorMessageComentario = "Escribe una respuesta") }
            return
        }
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(mostrarMensajeErrorComentario = true, errorMessageComentario = "No hay un usuario autenticado") }
            return
        }
        if (_uiState.value.isSending) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSending = true) }
            val result = commentRepository.createComment(reviewId, userId, texto.trim())
            if (result.isSuccess) {
                _uiState.update { it.copy(isSending = false, nuevoComentario = "") }
                loadData(reviewId)
            } else {
                _uiState.update {
                    it.copy(
                        isSending = false,
                        mostrarMensajeErrorComentario = true,
                        errorMessageComentario = result.exceptionOrNull()?.message ?: "No se pudo publicar la respuesta"
                    )
                }
            }
        }
    }
}
