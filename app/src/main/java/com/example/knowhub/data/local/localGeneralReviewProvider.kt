package com.example.knowhub.data.local

import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Notificacion
import kotlin.Int
import kotlin.String

object localGeneralReviewProvider {

    val generalReviews = listOf(
        GeneralReview(
            1,
            "Desarrollo Movil",
            "Angarita",
            1342,
            40,
            4,
            "Alta",
            listOf("#Computadores"),
        ),
        GeneralReview(
            2,
            "Ecuaciones Diferenciales",
            "Yesid Lemus",
            1355,
            90,
            5,
            "Media",
            listOf("#Integrales", "#Derivadas"),
        ),
        GeneralReview(
            3,
            "Introduccion a la Programación",
            "Eugenio Derbez",
            5655,
            20,
            0,
            "Alta",
            listOf("#Programación"),
        ),
        GeneralReview(
            4,
            "Introduccion a los Sistemas Distribuidos",
            "Lola Molina",
            9101,
            100,
            3,
            "Baja",
            listOf("#Programación","Sistemas"),
        ),
        GeneralReview(
            5,
            "Cálculo Vectorial",
            "Alexander Caviedes",
            8808,
            200,
            5,
            "Media",
            listOf("#Integrales", "#Derivadas"),
        ),

    )
}