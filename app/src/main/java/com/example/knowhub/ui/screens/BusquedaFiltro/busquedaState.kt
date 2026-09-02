package com.example.knowhub.ui.screens.busquedaFiltro
import com.example.knowhub.data.GeneralReview
data class busquedaState(
    val filtro: String = "",
    val reviews: List<GeneralReview> = emptyList(),
    val isLoading: Boolean = false
)