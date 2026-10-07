package com.example.knowhub.data.remote

import com.example.knowhub.data.dto.AsignaturaDto
import com.example.knowhub.data.dto.ReviewDto
import com.example.knowhub.data.Comment
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Review

fun AsignaturaDto.toUiModel(reviews: List<ReviewDto>) = GeneralReview(
    id = idAsignatura.toString(),
    nombreMateria = nomAsignatura,
    nombreProfesor = "",
    codigoAsignatura = idAsignatura.toString(),
    cantidadReviews = reviews.count { it.calificacion != null },
    calificacionMedia = reviews.mapNotNull { it.calificacion }.average().takeIf { it.isFinite() }?.toInt() ?: 0,
    dificultadMedia = "Semestre $semestreActual",
    Hashtags = listOf("${numCreditos.toInt()} créditos")
)

fun ReviewDto.toReview(commentCount: Int = 0): Review {
    return Review(
        id = idReview.toString(),
        idAsignatura = idAsignatura.toString(),
        idUsuario = idUsuario.toString(),
        nombreEstudiante = usuario?.nomUsuario ?: "Usuario $idUsuario",
        nombreProfesor = "Docente $idDocente",
        nombreAsignatura = asignatura?.nomAsignatura.orEmpty(),
        descripcion = descripcion,
        fechaPublicacion = createdAt?.substringBefore('T') ?: "",
        calificacion = (calificacion ?: 0).toString(),
        likes = numMegusta.toString(),
        cantidadComentarios = commentCount.toString(),
        parentReviewId = idReviewPadre?.toString() ?: ""
    )
}

fun ReviewDto.toComment(replyCount: Int = 0) = Comment(
    id = idReview.toString(),
    fecha = createdAt?.substringBefore('T') ?: "",
    estudiante = usuario?.nomUsuario ?: "Usuario $idUsuario",
    comentario = descripcion,
    likes = numMegusta,
    cantidadComentarios = replyCount
)




