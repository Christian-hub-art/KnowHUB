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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R

@Composable
fun CajaReview(
    Fecha: String,
    Codigo: String,
    Materia: String,
    Profesor: String,
    Reseña: String, // Modificar a dataclass
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                2.dp,
                colorResource(R.color.NegroKnowHUB)
            )
            .background(
                colorResource(R.color.blancoKnowHUB)
            )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.AzulKnowHUB))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Codigo,
                color = colorResource(R.color.blancoKnowHUB),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = Fecha,
                color = colorResource(R.color.blancoKnowHUB),
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.padding(10.dp)
        ) {


            Text(
                text = Materia,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(6.dp))


            Text(
                text = "👨‍🏫 $Profesor",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "★★★★★",
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
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.AmarilloKnowHUB)
                    )

                ) {
                    Text(
                        stringResource(R.string.editar),
                        color = colorResource(R.color.NegroKnowHUB))
                }
            }
        }
    }
}
