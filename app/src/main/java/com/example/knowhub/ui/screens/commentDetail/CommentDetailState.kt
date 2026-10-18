package com.example.knowhub.ui.screens.commentDetail

import com.example.knowhub.data.Comment

//Estado de la pantalla que muestra un comentario y sus respuestas.
data class CommentDetailState(
    val comment: Comment? = null,
    val replies: List<Comment> = emptyList(),
    val currentUserId: String = "",
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false,
    val nuevoComentario: String = "",
    val isSending: Boolean = false,
    val errorMessageComentario: String = "",
    val mostrarMensajeErrorComentario: Boolean = false,
    val navigateBack: Boolean = false
)
