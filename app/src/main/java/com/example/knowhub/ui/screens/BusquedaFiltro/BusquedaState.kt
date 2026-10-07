package com.example.knowhub.ui.screens.BusquedaFiltro

import com.example.knowhub.data.GeneralReview

data class BusquedaState(
    val semestreSeleccionado: String = "",
    val filtro: String = "",
    val reviews: List<GeneralReview> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val reviewsFiltradas: List<GeneralReview>
        get() = reviews.filter { review ->
            // Filtra primero por semestre si viene un semestre asignado
            val coincideSemestre = semestreSeleccionado.isBlank() ||
                    review.dificultadMedia.equals(semestreSeleccionado, ignoreCase = true)

            // Filtra adicionalmente si el usuario escribe algo en la barra de texto
            val coincideTexto = filtro.isBlank() ||
                    review.nombreMateria.contains(filtro, ignoreCase = true) ||
                    review.nombreProfesor.contains(filtro, ignoreCase = true)

            coincideSemestre && coincideTexto
        }
}