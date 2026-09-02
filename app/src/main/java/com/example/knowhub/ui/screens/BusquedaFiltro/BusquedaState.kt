package com.example.knowhub.ui.screens.busquedaFiltro
import com.example.knowhub.data.GeneralReview
//Estado inmutable que representa los datos necesarios para renderizar la pantalla de búsqueda.
data class BusquedaState(
    val filtro: String = "",
    val reviews: List<GeneralReview> = emptyList(),
    val isLoading: Boolean = false
)