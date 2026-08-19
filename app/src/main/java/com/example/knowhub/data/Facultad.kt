package com.example.knowhub.data

data class Facultad(
    val idFacultad: Int,
    val nombre: String,

    // Relación * a 1 con Universidad
    val universidad: Universidad,

    // Relación 1 a * con Carrera
    val carreras: List<Carrera> = emptyList()
)
