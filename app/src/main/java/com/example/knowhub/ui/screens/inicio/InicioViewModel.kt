package com.example.knowhub.ui.screens.inicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.MateriaResumida
import com.example.knowhub.data.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class InicioViewModel @Inject constructor(private val repository: CatalogRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(InicioState(isLoading = true))
    val uiState: StateFlow<InicioState> = _uiState
    init { loadMaterias() }

    fun loadMaterias() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }
        repository.asignaturas().fold(
            onSuccess = { materias ->
                _uiState.update {
                    it.copy(allGeneralReviews = materias, categories = categories(materias), isLoading = false)
                }
            },
            onFailure = { error ->
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudo cargar el catálogo: ${error.message}")
                }
            }
        )
    }

    private fun categories(items: List<GeneralReview>): List<Pair<String, List<MateriaResumida>>> =
        items.groupBy { it.dificultadMedia }.toSortedMap().map { (semester, materias) ->
            semester to materias.map { MateriaResumida(it.id, it.calificacionMedia, it.nombreMateria, it.nombreProfesor, it.cantidadReviews) }
        }
}


