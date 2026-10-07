package com.example.knowhub.data.dtos

import com.example.knowhub.data.Review

data class UserDto(
    val id: String = "",
    val username: String = "",
    val name: String = "",
    val profileImage: String? = null
) {
    constructor() : this("", "", "", null)
}
data class AsignaturaRelacionDto(
    val idAsignatura: String = "",
    val nomAsignatura: String = ""
)
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
    val id: String = "",
    val idAsignatura: String = "",
    val idUsuario: String = "",
    val nombreEstudiante: String = "",
    val nombreProfesor: String = "",
    val nombreAsignatura: String = "",
    val descripcion: String = "",
    val fechaPublicacion: String = "",
    val calificacion: Int = 0,
    val likes: Int = 0,
    val cantidadComentarios: Int = 0,
    val parentReviewId: String? = null,
    val user: UserDto? = null,
    val asignatura: AsignaturaRelacionDto? = null
) {
    constructor() : this("", "", "", "", "", "", "", "", 0, 0, 0, null, null)
}

fun ReviewDto.toReview(): Review {
    return Review(
        id = id.toIntOrNull() ?: 0,
        nombreEstudiante = nombreEstudiante.ifBlank { user?.name ?: "" },
        nombreProfesor = nombreProfesor,
        nombreAsignatura = nombreAsignatura,
        descripcion = descripcion,
        fechaPublicacion = fechaPublicacion,
        calificacion = calificacion,
        likes = likes,
        cantidadComentarios = cantidadComentarios
    )
}


