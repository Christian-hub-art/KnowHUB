package com.example.knowhub.ui.screens.reviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.generarEstrellas

@Composable
fun CajaReview(
    Fecha: String,
    Codigo: String,
    Materia: String,
    Profesor: String,
    Reseña: String,
    Calificacion: Int,
    colorCaja: Color,
    colorTexto: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .drawBehind {
                drawRect(
                    color = colorCaja,
                    topLeft = Offset(
                        5.dp.toPx(),
                        5.dp.toPx()
                    ),
                    size = size
                )
            }
            .fillMaxWidth()
            .border(
                2.dp,
                tertiaryContainerLight
            )
            .background(
                primaryLight
            )
    ) {
        Row(
            modifier = Modifier
                .background(colorCaja)
                .fillMaxWidth()
                .border(
                    2.dp,
                    tertiaryContainerLight
                )
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Codigo,
                color = colorTexto,
                fontFamily = BangersFont
            )

            Text(
                text = Fecha,
                color = colorTexto,
                fontFamily = BangersFont
            )
        }

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = Materia,
                fontSize = 17.sp,
                fontFamily = BangersFont
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "👨‍🏫 $Profesor",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(5.dp))

            val estrellas = generarEstrellas(Calificacion)
            Text(
                text = estrellas,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Black)
                    .padding(8.dp)
            ) {
                Text(
                    text = "\"$Reseña\"",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AppButton(
                    stringResource(R.string.lapizEditar),
                    colorTexto,
                    colorCaja,
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun CajaReviewPreview(){
    CajaReview(
        "15 Nov 2026",
        "1342",
        "Desarrollo Movil",
        "Angarita",
        "Es una muy buena clase.",
        5,
        primaryContainerLight,
        primaryLight
    )
}