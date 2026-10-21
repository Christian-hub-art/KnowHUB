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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    init {
        _uiState.update { it.copy(currentUserId = authRepository.currentUser?.uid ?: "") }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(reviewId)
            if (result.isSuccess) {
                _uiState.update { it.copy(navigateBack = true) }
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo eliminar la reseña"
                    )
                }
            }
        }
    }

    fun deleteComment(commentId: String, reviewId: String) {
        viewModelScope.launch {
            val result = commentRepository.deleteComment(commentId)
            if (result.isSuccess) {
                loadData(reviewId)
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeErrorComentario = true,
                        errorMessageComentario = result.exceptionOrNull()?.message ?: "No se pudo eliminar el comentario"
                    )
                }
            }
        }
    }

    fun loadData(reviewId: String) {
        getReviewById(reviewId)
        getComments(reviewId)
    }

    fun getReviewById(reviewId: String) {
        viewModelScope.launch {
            reviewRepository.getReviewLive(reviewId)
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            mostrarMensajeError = true,
                            errorMessage = error.message ?: "No se pudo actualizar la reseña"
                        )
                    }
                }
                .collect { review ->
                    _uiState.update {
                        it.copy(
                            review = review,
                            mostrarMensajeError = review == null,
                            errorMessage = if (review == null) "Reseña no encontrada" else ""
                        )
                    }
                }
        }
    }

    fun getComments(reviewId: String) {
        viewModelScope.launch {
            val result = commentRepository.getCommentsByReview(reviewId)
            if (result.isSuccess) {
                _uiState.update { it.copy(comments = result.getOrNull() ?: emptyList()) }
            }
        }
    }

    fun toggleCommentLike(commentId: String, reviewId: String) {
        if (_uiState.value.isUpdatingCommentLike) return
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdatingCommentLike = true, mostrarMensajeErrorComentario = false) }
            val result = commentRepository.toggleLike(commentId)
            if (result.isSuccess) {
                val commentsResult = commentRepository.getCommentsByReview(reviewId)
                val refreshError = commentsResult.exceptionOrNull()
                _uiState.update {
                    it.copy(
                        comments = commentsResult.getOrNull() ?: it.comments,
                        isUpdatingCommentLike = false,
                        mostrarMensajeErrorComentario = refreshError != null,
                        errorMessageComentario = refreshError?.message.orEmpty()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isUpdatingCommentLike = false,
                        mostrarMensajeErrorComentario = true,
                        errorMessageComentario = result.exceptionOrNull()?.message
                            ?: "No se pudo actualizar el me gusta."
                    )
                }
            }
        }
    }

    fun rateReview(reviewId: String, rating: Int) {
        if (_uiState.value.isRating) return
        viewModelScope.launch {
            _uiState.update { it.copy(isRating = true, mostrarMensajeError = false) }
            val result = reviewRepository.rateReview(reviewId, rating)
            if (result.isSuccess) {
                _uiState.update { it.copy(isRating = false) }
            } else {
                _uiState.update {
                    it.copy(
                        isRating = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo guardar la calificación."
                    )
                }
            }
        }
    }

    fun toggleReviewLike(reviewId: String) {
        if (_uiState.value.isUpdatingReviewLike) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(isUpdatingReviewLike = true, mostrarMensajeError = false)
            }
            val result = reviewRepository.toggleLike(reviewId)
            _uiState.update {
                it.copy(
                    isUpdatingReviewLike = false,
                    mostrarMensajeError = result.isFailure,
                    errorMessage = result.exceptionOrNull()?.message.orEmpty()
                )
            }
        }
    }

    fun updateNuevoComentario(input: String) {
        _uiState.update { it.copy(nuevoComentario = input, mostrarMensajeErrorComentario = false) }
    }

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
