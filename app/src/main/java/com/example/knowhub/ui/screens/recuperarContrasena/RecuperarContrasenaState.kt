package com.example.knowhub.ui.screens.recuperarContrasena

//Estado de la pantalla para recuperar la contraseña.
data class RecuperarContrasenaState(
    val correo: String = "",
    val mensaje: String = "",
    val mostrarMensaje: Boolean = false,
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false
)
