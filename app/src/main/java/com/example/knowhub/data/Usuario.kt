package com.example.knowhub.data

import androidx.annotation.DrawableRes

data class Usuario(
    val nombreUsuario: String,
    val idUsuario: Int,
    val correo: String,
    val contrasena: String,
    @DrawableRes val foto: Int,
    val notificacion: Notificacion,
    val reviewsCreadas: MutableList<Review> = mutableListOf(),
    val likes: MutableList<Like> = mutableListOf(),
    val comentarios: MutableList<Comentario> = mutableListOf(),
    val siguiendo: MutableList<Usuario> = mutableListOf(),
    val seguidores: MutableList<Usuario> = mutableListOf()
)
