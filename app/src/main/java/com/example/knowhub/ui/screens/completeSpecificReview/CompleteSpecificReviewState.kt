package com.example.knowhub.ui.screens.completeSpecificReview

import com.example.knowhub.data.Comment
import com.example.knowhub.data.Review

data class CompleteSpecificReviewState(
    val review: Review = Review(
        id = "",
        nombreEstudiante = "",
        nombreProfesor = "",
        nombreAsignatura = "",
        descripcion = "",
        fechaPublicacion = "",
        calificacion = 0,
        likes = 0,
        cantidadComentarios = 0
    ),
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)