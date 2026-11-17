package com.example.knowhub.ui.utils

import android.content.Context
import android.content.Intent
import com.example.knowhub.R
import com.example.knowhub.data.Review

fun generarEstrellas(numero: Int): String {
    var resultado = ""
    for (i in 1..5) {
        resultado += if (i <= numero) "⭐" else "★"
    }
    return resultado
}

// Abre el menú de compartir de Android con el texto de la reseña
fun compartirResena(context: Context, review: Review) {
    val texto = context.getString(
        R.string.compartir_resena_texto,
        review.nombreAsignatura,
        review.nombreProfesor,
        generarEstrellas(review.calificacion),
        review.descripcion,
        review.nombreEstudiante
    )
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, texto)
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.compartir_con)))
}
