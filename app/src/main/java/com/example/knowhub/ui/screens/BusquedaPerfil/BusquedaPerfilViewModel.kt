package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
//ViewModel que administra la carga de datos y el estado de la pantalla de perfil.
@HiltViewModel
class BusquedaPerfilViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(BusquedaPerfilState())
    val uiState: StateFlow<BusquedaPerfilState> = _uiState.asStateFlow()

    init {
        loadReviews()
    }
//Carga las reseñas publicadas desde la fuente de datos local.
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