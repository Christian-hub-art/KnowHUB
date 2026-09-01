package com.example.knowhub.ui.screens.profile

data class ProfileState(
    val nombre: String = "",
    val errorMessageGuardar: String = "",
    val mostrarMensajeErrorGuardar: Boolean = false,
    val errorMessageCancelar: String = "",
    val mostrarMensajeErrorCancelar: Boolean = false,
    val errorMessageCambiar: String = "",
    val mostrarMensajeErrorCambiar: Boolean = false
)
