package com.example.knowhub.ui.screens.inicio

import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.MateriaResumida

data class InicioState(
    val allGeneralReviews: List<GeneralReview> = emptyList(),
    val categories: List<Pair<String, List<MateriaResumida>>> = emptyList()
)
