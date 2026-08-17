package com.example.knowhub.ui.utils

fun generarEstrellas(numero: Int): String {
    var resultado = ""
    for (i in 1..5) {
        resultado += if (i <= numero) "⭐" else "★"
    }
    return resultado
}