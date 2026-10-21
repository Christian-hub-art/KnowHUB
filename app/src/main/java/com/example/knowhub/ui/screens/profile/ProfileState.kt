package com.example.knowhub.ui.screens.profile

import android.net.Uri

//Representa el estado de la interfaz de usuario para la pantalla de Perfil.
data class ProfileState(
    val userId: String = "",
    val nombre: String = "",
    val profileImageUrl: String? = null,
    val cantidadSeguidos: Int = 0,
    val cantidadSeguidores: Int = 0,
    val cantidadMateriasResenadas: Int = 0,
    val isLoading: Boolean = false,
    val errorMessageCarga: String = "",
    val mostrarMensajeErrorCarga: Boolean = false,
    val errorMessageGuardar: String = "",
    val mostrarMensajeErrorGuardar: Boolean = false,
    val errorMessageCancelar: String = "",
    val mostrarMensajeErrorCancelar: Boolean = false,
    val errorMessageCambiar: String = "",
    val mostrarMensajeErrorCambiar: Boolean = false,
    val errorMessageFoto: String = "",
    val mostrarMensajeErrorFoto: Boolean = false
)
