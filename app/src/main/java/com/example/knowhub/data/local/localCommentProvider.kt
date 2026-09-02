package com.example.knowhub.data.local

import com.example.knowhub.data.Comment

object localCommentProvider {
    val comments = listOf(
        Comment(
            id = 1,
            fecha = "30 Nov 2026",
            estudiante = "Sebastian Gaibor",
            comentario = "Materia pesada, es importante llevar la calculadora SIEMPRE",
            likes = 30,
            cantidadComentarios = 2
        ),
        Comment(
            id = 2,
            fecha = "1 Dic 2026",
            estudiante = "Andrés Cano",
            comentario = "Yo no la pude pasar a la primera :(",
            likes = 1,
            cantidadComentarios = 0
        ),
        Comment(
            id = 3,
            fecha = "19 Dic 2026",
            estudiante = "Dana Trujillo",
            comentario = "A mi tambie me parecio muy bien profesor",
            likes = 15,
            cantidadComentarios = 5
        )
    )
}
