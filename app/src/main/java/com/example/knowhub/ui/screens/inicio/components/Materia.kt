package com.example.knowhub.ui.screens.inicio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.generarEstrellas

@Composable
fun Materia(
    calificacion: Int,
    nombreMateria: String,
    profesor: String,
    numeroResenas: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(180.dp)
            .drawBehind {
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
                    size = size
                )
            }
            .border(2.dp, Color.Black)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = nombreMateria,
            color = tertiaryContainerLight,
            fontFamily = BangersFont
        )
        Text(
            text = profesor,
            color = tertiaryContainerLight,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(8.dp))

        val estrellas = generarEstrellas(calificacion)
        Text(
            text = estrellas,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$numeroResenas reseñas",
            color = tertiaryContainerLight,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = ArvoFont
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
@Preview
fun MateriaPreview() {
    Materia(
        calificacion = 5,
        nombreMateria = "Desarrollo Móvil",
        profesor = "Angarita",
        numeroResenas = 18,
    )
}
