package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BusquedaPerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BusquedaPerfilState())
    val uiState: StateFlow<BusquedaPerfilState> = _uiState.asStateFlow()

    init {
        loadReviews()
    }

    private fun loadReviews() {
        val lista = localGeneralReviewProvider.generalReviews

        _uiState.update { currentState ->
            currentState.copy(
                reviews = lista,
                isLoading = false
            )
        }
    }
}