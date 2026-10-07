package com.example.knowhub.data.remote

import com.example.knowhub.data.Comment
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Review
import com.example.knowhub.data.dtos.AsignaturaDto
import com.example.knowhub.data.dtos.ReviewDto

// Mapeo para la tarjeta general de la asignatura
fun AsignaturaDto.toUiModel(reviews: List<ReviewDto>): GeneralReview {
    val calificaciones = reviews.map { it.calificacion }
    val promedio = if (calificaciones.isNotEmpty()) calificaciones.average().toInt() else 0

    return GeneralReview(
        id = idAsignatura.toString(),
        nombreMateria = nomAsignatura,
        nombreProfesor = "",
        codigoAsignatura = idAsignatura.toString(),
        cantidadReviews = reviews.size,
        calificacionMedia = promedio,
        dificultadMedia = "Semestre $semestreActual",
        Hashtags = listOf("${numCreditos.toInt()} créditos")
    )
}

// Mapeo adaptado a las propiedades reales de tu ReviewDto actual
fun ReviewDto.toReviewModel(commentCount: Int = 0) = Review(
    id = id.toIntOrNull() ?: 0,
    nombreEstudiante = nombreEstudiante.ifBlank { user?.name ?: user?.username ?: "Usuario $idUsuario" },
    nombreProfesor = nombreProfesor,
    nombreAsignatura = nombreAsignatura.ifBlank { asignatura?.nomAsignatura.orEmpty() },
    descripcion = descripcion,
    fechaPublicacion = fechaPublicacion,
    calificacion = calificacion,
    likes = likes,
    cantidadComentarios = if (commentCount > 0) commentCount else cantidadComentarios
)

// Mapeo de ReviewDto hacia el modelo Comment
fun ReviewDto.toComment(replyCount: Int = 0) = Comment(
    id = id,
    fecha = fechaPublicacion,
    estudiante = nombreEstudiante.ifBlank { user?.name ?: user?.username ?: "Usuario $idUsuario" },
    comentario = descripcion,
    likes = likes,
    cantidadComentarios = if (replyCount > 0) replyCount else cantidadComentarios
)