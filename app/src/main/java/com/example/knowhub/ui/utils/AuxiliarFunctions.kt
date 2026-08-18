package com.example.knowhub.ui.utils

//Ejemplo para 3: resultado = "⭐⭐⭐★★"
fun generarEstrellas(numero: Int): String {
    var resultado = ""
    for (i in 1..5) {
        resultado += if (i <= numero) "⭐" else "★"
    }
    return resultado
}