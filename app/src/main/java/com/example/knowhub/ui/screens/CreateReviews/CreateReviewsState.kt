package com.example.knowhub.ui.screens.CreateReviews
//Estado del formulario para la creación de una nueva reseña.
data class CreateReviewsState(
    val clase: String = "",
    val tituloMateria: String = "",
    val nombreProfesor: String = "",
    val resena: String = "",
    val userName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false,
    val navigateBack: Boolean = false,
    val reviewId: String? = null      // distinto de null cuando se edita una reseña
)
