package com.example.knowhub.ui.screens.inicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.Asignatura
import com.example.knowhub.data.MateriaResumida
import com.example.knowhub.data.datasource.services.AsignaturaRetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class InicioViewModel @Inject constructor(
    private val asignaturaService: AsignaturaRetrofitService
) : ViewModel() {

    private val _uiState = MutableStateFlow(InicioState(isLoading = true))
    val uiState: StateFlow<InicioState> = _uiState

    init {
        loadMaterias()
    }

    fun loadMaterias() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }

        try {
            val materias = asignaturaService.getAsignatura()
            _uiState.update {
                it.copy(
                    asignaturas = materias,
                    categories = mapCategories(materias),
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

    private fun mapCategories(items: List<Asignatura>): List<Pair<String, List<MateriaResumida>>> =
        items.groupBy { it.semestre }
            .toSortedMap()
            .map { (semestre, materias) ->
                semestre to materias.map { asignatura ->
                    MateriaResumida(
                        id = asignatura.idAsignatura,
                        calificacion = 0,
                        nombreMateria = asignatura.nombre,
                        profesor = "",
                        numeroResenas = 0
                    )
                }
            }
}