package com.example.knowhub.ui.screens.CreateMateria
//Estado del formulario para crear o editar una materia.
data class CreateMateriaState(
    val nombreMateria: String = "",
    val nombreProfesor: String = "",
    val codigo: String = "",
    val dificultad: String = "Media",
    val hashtags: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false,
    val navigateBack: Boolean = false,
    val materiaEliminada: Boolean = false,
    val generalReviewId: Int? = null,   // distinto de null cuando se edita una materia
    val creadorId: String = ""         // uid de quien creó la materia que se edita
)
