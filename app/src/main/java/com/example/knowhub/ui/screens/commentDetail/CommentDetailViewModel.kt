package com.example.knowhub.ui.screens.commentDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//ViewModel del detalle de un comentario (equivale a TweetDetailViewModel cuando se abre una respuesta).
@HiltViewModel
class CommentDetailViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CommentDetailState())
    val uiState: StateFlow<CommentDetailState> = _uiState

    init {
        _uiState.update { it.copy(currentUserId = authRepository.currentUser?.uid ?: "") }
    }

    //Carga el comentario y sus respuestas directas.
    fun loadData(commentId: String) {
        viewModelScope.launch {
            val result = commentRepository.getCommentById(commentId)
            if (result.isSuccess) {
                _uiState.update { it.copy(comment = result.getOrNull(), mostrarMensajeError = false) }
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar el comentario"
                    )
                }
            }
        }
        viewModelScope.launch {
            val result = commentRepository.getReplies(commentId)
            if (result.isSuccess) {
                _uiState.update { it.copy(replies = result.getOrNull() ?: emptyList()) }
            }
        }
    }

    fun updateNuevoComentario(input: String) {
        _uiState.update { it.copy(nuevoComentario = input, mostrarMensajeErrorComentario = false) }
    }

    //Publica una respuesta a este comentario.
    fun responderButtonPressed(commentId: String) {
        val texto = _uiState.value.nuevoComentario
        val comment = _uiState.value.comment ?: return
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
            val result = commentRepository.createComment(
                reviewId = comment.reviewId,
                userId = userId,
                comentario = texto.trim(),
                parentCommentId = commentId
            )
            if (result.isSuccess) {
                _uiState.update { it.copy(isSending = false, nuevoComentario = "") }
                loadData(commentId)
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

    //Borra un comentario con su hilo. Si es el comentario abierto, vuelve atrás.
    fun deleteComment(idToDelete: String, shownCommentId: String) {
        viewModelScope.launch {
            val result = commentRepository.deleteComment(idToDelete)
            if (result.isSuccess) {
                if (idToDelete == shownCommentId) {
                    _uiState.update { it.copy(navigateBack = true) }
                } else {
                    loadData(shownCommentId)
                }
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
}
