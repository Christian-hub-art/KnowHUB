package com.example.knowhub.ui.screens.options.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.utils.AppButton

import com.example.knowhub.ui.utils.CuadroTexto


@Composable
fun CuadroInformaciónPersonal(
    modifier: Modifier = Modifier
) {
    val colorSombra = colorResource(R.color.NegroKnowHUB)

    Box(
        modifier = modifier
            .drawBehind {
                drawRect(
                    color = colorSombra,
                    topLeft = Offset(
                        5.dp.toPx(),
                        5.dp.toPx()
                    ),
                    size = size
                )
            }
            .background(colorResource(R.color.blancoKnowHUB))
            .border(
                2.dp,
                colorResource(R.color.NegroKnowHUB)
            )
            .height(320.dp)
            .width(360.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(18.dp)
        ) {

            // TÍTULO
            AppButton(
                "👤 INFORMACIÓN PERSONAL",
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.NegroKnowHUB),
                modifier = Modifier
                    .height(35.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            // NOMBRE DE USUARIO
            Text(
                "Nombre de usuario",
                fontFamily = ArvoFont
            )

            Spacer(modifier = Modifier.height(6.dp))

            CuadroTexto(
                "Santiago Perez",
                colorResource(R.color.blancoKnowHUB),
                alto = 40.dp,
                ancho = 275.dp
            )

            Spacer(modifier = Modifier.height(15.dp))

            // GUARDAR Y CANCELAR
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {

                AppButton(
                    "💾 GUARDAR CAMBIOS",
                    colorResource(R.color.NegroKnowHUB),
                    colorResource(R.color.blancoKnowHUB),
                    modifier = Modifier
                        .height(35.dp)
                        .width(150.dp)
                )

                AppButton(
                    "X CANCELAR",
                    colorResource(R.color.NegroKnowHUB),
                    colorResource(R.color.blancoKnowHUB),
                    modifier = Modifier
                        .height(35.dp)
                        .width(150.dp)
                )
            }

            Spacer(modifier = Modifier.height(23.dp))

            // CAMBIAR CONTRASEÑA
            AppButton(
                "CAMBIAR CONTRASEÑA",
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.AzulKnowHUB),
                modifier = Modifier
                    .height(35.dp)
                    .width(150.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // CAMBIAR CORREO
            AppButton(
                "CAMBIAR CORREO ELECTRÓNICO",
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.AzulKnowHUB),
                modifier = Modifier
                    .height(35.dp)
                    .width(200.dp)
            )
        }
    }
}
