package com.example.knowhub.ui.screens.register

import android.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.knowhub.data.Usuario
//Representa el estado de la interfaz de usuario para la pantalla de Registro.
data class RegisterState(
    val nombre: String = "",
    val correoElectronico: String = "",
    val nombreUsuario: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false,
    val navigateInicio: Boolean = false,
    val navigateLogin: Boolean = false
)
