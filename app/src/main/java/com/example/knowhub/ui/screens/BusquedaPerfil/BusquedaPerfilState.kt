package com.example.knowhub.ui.screens.BusquedaPerfil

import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Review
import com.example.knowhub.data.Usuario

//Estado que representa los datos expuestos para la pantalla de perfil buscado.
data class BusquedaPerfilState(
    val user: Usuario = Usuario("", "", "", "", "", "", "", ""),
    val userId: String = "",
    val currentUserId: String = "",
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false
)