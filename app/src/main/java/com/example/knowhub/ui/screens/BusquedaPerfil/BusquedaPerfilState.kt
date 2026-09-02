package com.example.knowhub.ui.screens.BusquedaPerfil

import com.example.knowhub.data.GeneralReview

data class BusquedaPerfilState(
    val reviews: List<GeneralReview> = emptyList(),
    val isLoading: Boolean = false
)