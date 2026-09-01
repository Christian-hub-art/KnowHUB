package com.example.knowhub.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class LoginState(
    val nombreOCorreo: String = "",
    val contrasena: String = "",
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false,
    val navigateInicio: Boolean = false,
    val navigateRegister: Boolean = false,
    val navigateContinuar: Boolean = false
)
