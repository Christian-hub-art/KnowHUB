package com.example.knowhub.ui.screens.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.profile.bodyProfileScreen
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.backgroundImage
import com.example.knowhub.ui.utils.barraArriba

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier
) {
    Box() {
        backgroundImage()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            barraArriba(
                R.drawable.casa,
                ""
            )
            bodyReviewScreen()
        }
    }
}

@Composable
fun bodyReviewScreen(
    modifier: Modifier = Modifier
){
    AppButton("Tus reseñas")
    Spacer(modifier= Modifier.padding(9.dp))
    Row{
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.NegroKnowHUB)
            ),
            modifier = modifier
        ) {
            Row{

                Icon(
                    painter = painterResource(R.drawable.perfil),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    "Alejandro Sanchez",
                    fontFamily = FontFamily.Monospace
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        AppButton("3 Reseñas")

    }

    Spacer(modifier= Modifier.padding(23.dp))
    CajaReview("Desarrollo Movil", "Angarita")
    Spacer(modifier= Modifier.padding(23.dp))
    CajaReview("Ecuaciones Diferenciales", "Tole")

}

@Composable
fun CajaReview(
        Materia: String,
        Profesor: String,
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
                    text = "CLASE: 3455",
                    color = colorResource(R.color.blancoKnowHUB),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "15 NOV 2025",
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
                        text = "\"Los parciales son imposibles.\"",
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
                        Text("EDITAR",
                            color = colorResource(R.color.NegroKnowHUB))
                    }
                }
            }
        }
    }


@Composable
@Preview
fun ReviewScreenPreview(){
    ReviewScreen()
}