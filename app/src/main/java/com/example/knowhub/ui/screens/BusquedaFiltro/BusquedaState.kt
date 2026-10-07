package com.example.knowhub.ui.screens.BusquedaFiltro

import com.example.knowhub.data.Asignatura

data class BusquedaState(
    val semestreSeleccionado: String = "",
    val filtro: String = "",
    val asignaturas: List<Asignatura> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val asignaturasFiltradas: List<Asignatura>
        get() = asignaturas.filter { materia ->
            val coincideSemestre = semestreSeleccionado.isBlank() ||
                    materia.semestre.equals(semestreSeleccionado, ignoreCase = true)

            val coincideTexto = filtro.isBlank() ||
                    materia.nombre.contains(filtro, ignoreCase = true) ||
                    materia.descripcion.contains(filtro, ignoreCase = true)

            coincideSemestre && coincideTexto
        }
}