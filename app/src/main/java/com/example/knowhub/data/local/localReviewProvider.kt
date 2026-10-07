package com.example.knowhub.data.local

import com.example.knowhub.data.Review

object localReviewProvider {

    val Reviews = listOf(
        Review(
            id = "1",
            idAsignatura = "101",
            idUsuario = "1",
            nombreEstudiante = "Dana Trujillo",
            nombreProfesor = "Yesid Lemus",
            nombreAsignatura = "Ecuaciones diferenciales",
            descripcion = "Explica super bien y califica suave!!",
            fechaPublicacion = "15 nov 2026",
            calificacion = "4",
            likes = "5",
            cantidadComentarios = "10",
            parentReviewId = ""
        ),

        Review(
            id = "2",
            idAsignatura = "102",
            idUsuario = "2",
            nombreEstudiante = "Santiago Moreno",
            nombreProfesor = "Laura Gómez",
            nombreAsignatura = "Cálculo diferencial",
            descripcion = "Tiene mucha paciencia y explica los temas de manera clara.",
            fechaPublicacion = "18 nov 2026",
            calificacion = "0",
            likes = "2",
            cantidadComentarios = "9",
            parentReviewId = ""
        ),

        Review(
            id = "3",
            idAsignatura = "103",
            idUsuario = "3",
            nombreEstudiante = "Valentina Rojas",
            nombreProfesor = "Andrés Cárdenas",
            nombreAsignatura = "Programación",
            descripcion = "Muy buen profesor, los ejemplos ayudan bastante a entender.",
            fechaPublicacion = "20 nov 2026",
            calificacion = "4",
            likes = "4",
            cantidadComentarios = "8",
            parentReviewId = ""
        ),

        Review(
            id = "4",
            idAsignatura = "104",
            idUsuario = "4",
            nombreEstudiante = "Mateo Vargas",
            nombreProfesor = "Camila Torres",
            nombreAsignatura = "Álgebra lineal",
            descripcion = "Explica paso a paso y responde todas las preguntas.",
            fechaPublicacion = "22 nov 2026",
            calificacion = "3",
            likes = "5",
            cantidadComentarios = "10",
            parentReviewId = ""
        ),

        Review(
            id = "5",
            idAsignatura = "105",
            idUsuario = "5",
            nombreEstudiante = "Mariana Pérez",
            nombreProfesor = "Felipe Ramírez",
            nombreAsignatura = "Física mecánica",
            descripcion = "Las clases son dinámicas y se nota que domina el tema.",
            fechaPublicacion = "25 nov 2026",
            calificacion = "4",
            likes = "5",
            cantidadComentarios = "9",
            parentReviewId = ""
        )
    )
}