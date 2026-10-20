package com.example.knowhub.ui.screens.commentDetail

import com.example.knowhub.data.Comment

data class CommentDetailState(
    val comment: Comment? = null,
    val replies: List<Comment> = emptyList(),
    val currentUserId: String = "",
    val isUpdatingLike: Boolean = false,
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false,
    val nuevoComentario: String = "",
    val isSending: Boolean = false,
    val errorMessageComentario: String = "",
    val mostrarMensajeErrorComentario: Boolean = false,
    val navigateBack: Boolean = false
)
