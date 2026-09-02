package com.example.knowhub.ui.screens.inicio

import com.example.knowhub.data.GeneralReview
//Estado que representa los datos expuestos para la pantalla de Inicio.
data class InicioState(
    val allGeneralReviews: List<GeneralReview> = emptyList()
)
