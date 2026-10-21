package com.example.knowhub.ui.screens.connections

import com.example.knowhub.data.datasource.dtos.UserSummaryDto

data class ConnectionsState(
    val users: List<UserSummaryDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
