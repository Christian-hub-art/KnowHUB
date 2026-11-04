package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.datasource.repository.GeneralReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//ViewModel encargado de gestionar la lógica de negocio y el estado de la pantalla de búsqueda.

@HiltViewModel
class BusquedaViewModel @Inject constructor(
    private val generalReviewRepository: GeneralReviewRepository
): ViewModel() {

    // Encapsulamiento: Mutable privado, Inmutable expuesto
    private val _uiState = MutableStateFlow(BusquedaState())
    val uiState: StateFlow<BusquedaState> = _uiState.asStateFlow()

    private var allReviews: List<GeneralReview> = emptyList()

    init {// Carga inicial de las reseñas al instanciar el ViewModel
        loadGeneralReviews()
    }

    private fun loadGeneralReviews() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            generalReviewRepository.getGeneralReviewsLive()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mostrarMensajeError = true,
                            errorMessage = e.message ?: "No se pudieron cargar las materias"
                        )
                    }
                }
                .collect { materias ->
                    allReviews = materias
                    _uiState.update { currentState ->
                        currentState.copy(
                            reviews = filterReviews(allReviews, currentState.filtro),
                            isLoading = false,
                            mostrarMensajeError = false
                        )
                    }
                }
        }
    }

    // Función de evento para actualizar el texto de búsqueda y filtrar
    fun onFiltroChange(nuevoFiltro: String) {
        _uiState.update { currentState ->
            currentState.copy(
                filtro = nuevoFiltro,
                reviews = filterReviews(allReviews, nuevoFiltro)
            )
        }
    }

    private fun filterReviews(reviews: List<GeneralReview>, filtro: String): List<GeneralReview> {
        val query = filtro.trim()
        if (query.isEmpty()) return reviews

        if (query == "#") {
            return reviews.filter { it.Hashtags.isNotEmpty() }
        }

        val searchTerms = query.split("\\s+".toRegex()).filter { it.isNotEmpty() }

        return reviews.filter { review ->
            searchTerms.all { term ->
                val cleanTerm = term.removePrefix("#")

                val matchesNombreMateria = review.nombreMateria.contains(term, ignoreCase = true) ||
                                           (cleanTerm.isNotEmpty() && review.nombreMateria.contains(cleanTerm, ignoreCase = true))
                val matchesNombreProfesor = review.nombreProfesor.contains(term, ignoreCase = true) ||
                                            (cleanTerm.isNotEmpty() && review.nombreProfesor.contains(cleanTerm, ignoreCase = true))
                val matchesIdMateria = (cleanTerm.isNotEmpty() && review.codigoAsignatura.toString().contains(cleanTerm, ignoreCase = true)) ||
                                       (cleanTerm.isNotEmpty() && review.id.toString().contains(cleanTerm, ignoreCase = true))
                val matchesHashtag = review.Hashtags.any { tag ->
                    tag.contains(term, ignoreCase = true) ||
                    (cleanTerm.isNotEmpty() && tag.removePrefix("#").contains(cleanTerm, ignoreCase = true))
                }

                matchesNombreMateria || matchesNombreProfesor || matchesIdMateria || matchesHashtag
            }
        }
    }
}