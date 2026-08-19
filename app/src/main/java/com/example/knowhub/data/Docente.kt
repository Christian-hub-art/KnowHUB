package com.example.knowhub.data

data class Docente(
    val idDocente: Int,
    val nombre: String,

    // Relación * a * con Asignatura
    val asignaturas: List<Asignatura> = emptyList()
)
