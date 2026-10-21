package com.example.knowhub.data.datasource.dtos

import com.example.knowhub.data.Comentario
import com.example.knowhub.data.Like
import com.example.knowhub.data.Notificacion
import com.example.knowhub.data.Review
import com.example.knowhub.data.Usuario


data class UserProfileDto(
    val name: String = "",
    val userName: String = "",
    val nombreUsuario: String = "",
    val idUsuario: Int = 0,
    val correo: String = "",
    val contrasena: String = "",
    val foto: String = "",
    val notificacion: Notificacion? = null,
    val reviewsCreadas: List<Review> = emptyList(),
    val likes: List<Like> = emptyList(),
    val comentarios: List<Comentario> = emptyList(),
    val followed: Boolean = false,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val following: List<Usuario> = emptyList()
)
