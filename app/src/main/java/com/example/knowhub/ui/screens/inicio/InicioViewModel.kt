package com.example.knowhub.ui.screens.inicio

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de la lógica de negocio y carga inicial de reseñas generales
class InicioViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InicioState())
    val uiState: StateFlow<InicioState> = _uiState

    init {
        loadMaterias()
    }
//Carga la lista inicial de reseñas generales desde el proveedor local de datos.
    private fun loadMaterias() {
        val allMaterias = localGeneralReviewProvider.generalReviews
        _uiState.update {
            it.copy(
                allGeneralReviews = allMaterias
            )
        }
    }
}
