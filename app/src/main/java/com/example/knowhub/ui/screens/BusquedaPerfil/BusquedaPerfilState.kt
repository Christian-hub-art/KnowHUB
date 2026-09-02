package com.example.knowhub.ui.screens.BusquedaPerfil

import com.example.knowhub.data.GeneralReview
//Estado que representa los datos expuestos para la pantalla de perfil buscado.
data class BusquedaPerfilState(
    val reviews: List<GeneralReview> = emptyList(),
    val isLoading: Boolean = false
)