package com.example.knowhub.data

data class Asignatura(
    val idAsignatura: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val creditos: Int,
    val semestre: String,
    val estado: String
)
