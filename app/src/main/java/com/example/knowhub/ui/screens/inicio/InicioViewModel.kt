package com.example.knowhub.ui.screens.inicio

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class InicioViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InicioState())
    val uiState: StateFlow<InicioState> = _uiState

    init {
        loadMaterias()
    }

    private fun loadMaterias() {
        val allMaterias = localGeneralReviewProvider.generalReviews
        _uiState.update {
            it.copy(
                allGeneralReviews = allMaterias
            )
        }
    }
}
