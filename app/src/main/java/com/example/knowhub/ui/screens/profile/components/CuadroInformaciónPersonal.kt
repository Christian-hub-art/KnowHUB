package com.example.knowhub.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.utils.AppButton

@Composable
fun CuadroInformaciónPersonal(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .background(colorResource(R.color.blancoKnowHUB))
            .border(
                4.dp,
                colorResource(R.color.NegroKnowHUB)
            )
            .padding(20.dp)

    ) {

        Column(

        ) {

            AppButton("INFORMACIÓN PERSONAL",
                colorResource(R.color.blancoKnowHUB))

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Nombre de usuario",
                fontFamily = FontFamily.Monospace
            )
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blancoKnowHUB)
                ), modifier = modifier


            ) {
                Text(
                    "Alejandro Sanchez",
                    fontFamily = FontFamily.Cursive,
                    color = Color.Black
                )
            }
            Row {

                AppButton("💾 GUARDAR CAMBIOS",
                    colorResource(R.color.blancoKnowHUB))
                AppButton(" CANCELAR", colorResource(R.color.blancoKnowHUB))
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.AzulKnowHUB)
                ), modifier = modifier


            ) {
                Text(
                    "CAMBIAR CONTRASEÑA",
                    fontFamily = FontFamily.Monospace
                )
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.AzulKnowHUB)
                ), modifier = modifier


            ) {
                Text(
                    "CAMBIAR CORREO ELECTRÓNICO",
                    fontFamily = FontFamily.Monospace
                )
            }

        }
    }
}