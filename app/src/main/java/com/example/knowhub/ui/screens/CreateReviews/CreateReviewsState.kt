package com.example.knowhub.ui.screens.CreateReviews

data class CreateReviewsState(
    val clase: String = "",
    val tituloMateria: String = "",
    val nombreProfesor: String = "",
    val resena: String = "",
    val calificacion: String = "5",
    val navigateBack: Boolean = false,
    val error: String? = null
)
