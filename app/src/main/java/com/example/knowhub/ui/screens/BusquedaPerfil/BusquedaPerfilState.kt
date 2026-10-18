package com.example.knowhub.ui.screens.BusquedaPerfil

import com.example.knowhub.data.Review

//Estado que representa los datos expuestos para la pantalla de perfil buscado.
data class BusquedaPerfilState(
    val userName: String = "",
    val fotoPerfil: String? = null,
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false
)
