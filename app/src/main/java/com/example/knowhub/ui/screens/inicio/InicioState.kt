package com.example.knowhub.ui.screens.inicio

import com.example.knowhub.data.Asignatura
import com.example.knowhub.data.MateriaResumida

data class InicioState(
    val asignaturas: List<Asignatura> = emptyList(),
    val categories: List<Pair<String, List<MateriaResumida>>> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)