package com.example.knowhub.data

data class Carrera(
    val idCarrera: Int,
    val nombre: String,

    // Relación * a 1 con Facultad
    val facultad: Facultad,

    // Relación 1 a * con Asignatura
    val asignaturas: List<Asignatura> = emptyList()
)
