package com.example.knowhub.ui.screens.inicio

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.MateriaResumida
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
    fun loadMaterias() {
        val allMaterias = localGeneralReviewProvider.generalReviews
        val categories = processCategories(allMaterias)
        _uiState.update {
            it.copy(
                allGeneralReviews = allMaterias,
                categories = categories
            )
        }
    }

    fun processCategories(allGeneralReviews: List<GeneralReview>): List<Pair<String, List<MateriaResumida>>> {
        // Conjunto de palabras no significativas
        val stopWords = setOf("a", "de", "la", "en", "el", "los", "las", "y", "o", "con", "por", "para", "un", "una", "i", "ii", "iii")

        // Map "calculo" -> [Calculo I, Calculo II]
        val wordsToMaterias = mutableMapOf<String, MutableList<GeneralReview>>()

        allGeneralReviews.forEach { review ->
            // Toma el nombre de la materia y lo separa por espacios: Calculo I -> ["calculo", "I"]
            val words = review.nombreMateria.split(" ")
                // Elimina los caracteres que no sean letras o numeros válidos
                .map { it.lowercase().filter { c -> c.isLetterOrDigit() } }
                // Descartar palabras vacias o no significativas
                .filter { it.isNotEmpty() && it !in stopWords }

            /* Cada palabra nueva se guarda en el map como "calculo" -> emptyList()
               el review se guarda en la categoria que corresponda  */
            words.forEach { word ->
                wordsToMaterias.getOrPut(word) { mutableListOf() }.add(review)
            }
        }

        // Descartar categorias que aparecen en menos de 2 materias

        // El Map wordsToMaterias se covierte en un List<Pair<String, List<MateriaResumida>>> y se retorna
        return wordsToMaterias.filter { it.value.size >= 2 }
            .map { (word, reviews) ->
                word.uppercase() to reviews.distinctBy { it.id }.map { review ->
                    MateriaResumida(
                        calificacion = review.calificacionMedia,
                        nombreMateria = review.nombreMateria,
                        profesor = review.nombreProfesor,
                        numeroResenas = review.cantidadReviews
                    )
                }
            }
            //ordenar categorias alfabeticamente
            .sortedBy { it.first }
    }
}
