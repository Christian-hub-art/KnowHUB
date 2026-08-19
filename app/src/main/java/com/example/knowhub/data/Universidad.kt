package com.example.knowhub.data

data class Universidad(
    val idUniversidad: Int,
    val nombre: String,

    // Relación 1 a * con Facultad
    val facultades: List<Facultad> = emptyList()
)
