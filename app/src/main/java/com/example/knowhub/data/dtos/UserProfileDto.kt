package com.example.knowhub.data.dtos

import com.example.knowhub.data.Usuario

data class UserProfileDto(
    val nombreUsuario: String,
    val idUsuario: Int,
    val correo: String,
    val contrasena: Int,
    val foto: String?,
    val likes: Int,
    val siguiendo: Int,
    val seguidores: Int

)

fun UserProfileDto.toUsuarioProfile(): Usuario {
    return Usuario(
        nombreUsuario = nombreUsuario,
        idUsuario = idUsuario.toString(),
        correo = correo,
        contrasena = contrasena.toString(),
        foto = foto ?: "",
        likes = likes.toString(),
        siguiendo = siguiendo.toString(),
        seguidores = seguidores.toString()
    )
}

