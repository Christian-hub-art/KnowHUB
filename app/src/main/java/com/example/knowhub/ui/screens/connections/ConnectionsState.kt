package com.example.knowhub.ui.screens.connections

import com.example.knowhub.data.datasource.dtos.UserProfileDto

data class ConnectionsState(
    val users: List<UserProfileDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false
)
