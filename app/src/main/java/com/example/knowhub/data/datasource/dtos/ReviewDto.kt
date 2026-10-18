package com.example.knowhub.data.datasource.dtos

// Datos del autor copiados dentro de la reseña
data class ReviewUserDto(
    val name: String = "",
    val userName: String = "",
    val profileImage: String? = null
)

// Lo que se lee de Firestore
data class ReviewDto(
    val id: String = "",              // id del documento en Firestore
    val userId: String = "",          // uid del autor
    val clase: String = "",
    val nombreAsignatura: String = "",
    val nombreProfesor: String = "",
    val descripcion: String = "",
    val calificacion: Int = 0,
    val likes: Int = 0,
    val cantidadComentarios: Int = 0,
    val fechaPublicacion: String = "",
    val user: ReviewUserDto = ReviewUserDto()
)

// Lo que se envía al crear una reseña
data class CreateReviewDto(
    val userId: String = "",
    val clase: String = "",
    val nombreAsignatura: String = "",
    val nombreProfesor: String = "",
    val descripcion: String = "",
    val user: ReviewUserDto? = null
)