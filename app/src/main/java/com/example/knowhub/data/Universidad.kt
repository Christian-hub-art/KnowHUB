package com.example.knowhub.data

data class Universidad(
    val idUniversidad: Int,
    val nombre: String,

    val facultades: List<Facultad> = emptyList()
)
