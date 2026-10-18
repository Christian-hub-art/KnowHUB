package com.example.knowhub.data.datasource.dtos

import com.example.knowhub.data.Comment

// Lo que se lee de Firestore
data class CommentDto(
    val id: String = "",              // id del documento en Firestore
    val reviewId: String = "",        // reseña a la que responde
    val userId: String = "",
    val comentario: String = "",
    val fecha: String = "",
    val likes: Int = 0,
    val user: ReviewUserDto = ReviewUserDto()
)

// Lo que se envía al crear una respuesta
data class CreateCommentDto(
    val reviewId: String = "",
    val userId: String = "",
    val comentario: String = "",
    val fecha: String = "",
    val user: ReviewUserDto? = null
)

// Convierte la respuesta de Firestore al modelo que usan las pantallas
fun CommentDto.toComment(): Comment {
    return Comment(
        id = id,
        fecha = fecha,
        estudiante = user.userName,
        comentario = comentario,
        likes = likes,
        cantidadComentarios = 0
    )
}
