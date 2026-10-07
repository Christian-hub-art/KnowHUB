package com.example.knowhub.data.dto

data class AsignaturaDto(
    val idAsignatura: Int = 0,
    val nomAsignatura: String = "",
    val semestreActual: String = "",
    val estado: String? = null,
    val descripcion: String? = null,
    val numCreditos: Double = 0.0,
    val idUniversidad: Int = 0,
    val idCarrera: Int = 0
)

data class ReviewDto(
    val idReview: Int = 0,
    val descripcion: String = "",
    val calificacion: Int? = null,
    val numMegusta: Int = 0,
    val idAsignatura: Int = 0,
    val idUsuario: Int = 0,
    val idDocente: Int = 0,
    val idReviewPadre: Int? = null,
    val createdAt: String? = null,
    val usuario: UsuarioDto? = null,
    val asignatura: AsignaturaRelacionDto? = null
)
data class UsuarioDto(val idUsuario: Int = 0, val nomUsuario: String = "Usuario", val fotoPerfil: String? = null)
data class AsignaturaRelacionDto(val idAsignatura: Int = 0, val nomAsignatura: String = "", val idUniversidad: Int = 0)
