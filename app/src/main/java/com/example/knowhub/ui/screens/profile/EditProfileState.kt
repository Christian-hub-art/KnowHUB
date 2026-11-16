package com.example.knowhub.ui.screens.profile

data class EditProfileState(
    val nombre: String = "",
    val correo: String = "",
    val contrasenaActual: String = "",
    val nuevaContrasena: String = "",
    val confirmarContrasena: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val navigateBack: Boolean = false
)
