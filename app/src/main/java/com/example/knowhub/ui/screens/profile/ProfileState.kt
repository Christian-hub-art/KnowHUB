package com.example.knowhub.ui.screens.profile
//Representa el estado de la interfaz de usuario para la pantalla de Perfil.
data class ProfileState(
    val nombre: String = "",
    val profileImageUrl: String? = null,
    val errorMessageGuardar: String = "",
    val mostrarMensajeErrorGuardar: Boolean = false,
    val errorMessageCancelar: String = "",
    val mostrarMensajeErrorCancelar: Boolean = false,
    val errorMessageCambiar: String = "",
    val mostrarMensajeErrorCambiar: Boolean = false,
    val errorMessageFoto: String = "",
    val mostrarMensajeErrorFoto: Boolean = false
)
