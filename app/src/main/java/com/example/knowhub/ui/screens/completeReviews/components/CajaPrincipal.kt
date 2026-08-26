package com.example.knowhub.ui.screens.completeReviews.components

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
import androidx.compose.foundation.layout.width
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
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel

import com.example.knowhub.ui.utils.generarEstrellas

@Composable
fun CajaPrincipal(
    generalReview: GeneralReview,
    modifier: Modifier = Modifier
) {
    val colorCaja= primaryContainerLight
    val colorTexto= primaryLight

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
                text = ""+generalReview.codigoAsignatura+"",
                color = colorTexto,
                fontFamily = BangersFont
            )
        }

        Column(
            modifier = Modifier.padding(10.dp)
        ) {


            Text(
                text = generalReview.nombreMateria,
                fontSize = 17.sp,
                fontFamily = BangersFont
            )

            Spacer(modifier = Modifier.height(6.dp))


            Text(
                text = "👨‍🏫 ${generalReview.nombreProfesor}",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(5.dp))

            val estrellas= generarEstrellas(generalReview.calificacionMedia)
            Text(
                text = estrellas,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))


            Text(
                text = "Basado en ${generalReview.cantidadReviews} reseñas",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                generalReview.Hashtags.forEach {
                    AppLabel(
                        it,
                        tertiaryContainerLight,
                        secondaryContainerLight,
                        modifier = Modifier.height(40.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                AppLabel(
                    "${generalReview.dificultadMedia} dificultad",
                    tertiaryContainerLight,
                    primaryLight,
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}


@Composable
@Preview
fun CajaPrincipalPreview(){
    GeneralReview(
        5,
        "Cálculo Vectorial",
        "Alexander Caviedes",
        8808,
        200,
        5,
        "Media",
        listOf("#Integrales", "#Derivadas"),
    )
}