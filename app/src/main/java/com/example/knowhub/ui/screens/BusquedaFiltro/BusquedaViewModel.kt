package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.services.AsignaturaRetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BusquedaViewModel @Inject constructor(
    private val asignaturaService: AsignaturaRetrofitService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusquedaState(isLoading = true))
    val uiState: StateFlow<BusquedaState> = _uiState

    init {
        val semestre: String = savedStateHandle.get<String>("semestre") ?: ""
        _uiState.update { it.copy(semestreSeleccionado = semestre) }
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }

        try {
            val materias = asignaturaService.getAsignatura()
            _uiState.update {
                it.copy(
                    asignaturas = materias,
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = "No se pudo cargar el catálogo: ${e.message}"
                )
            }
        }
    }

    fun onFiltroChange(nuevoFiltro: String) {
        _uiState.update { it.copy(filtro = nuevoFiltro) }
    }
}