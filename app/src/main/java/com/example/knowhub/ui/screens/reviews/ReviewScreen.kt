package com.example.knowhub.ui.screens.reviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.reviews.components.CajaReview
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.backgroundImage
import com.example.knowhub.ui.utils.barraArriba

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier
) {
    Box() {
        backgroundImage()
        bodyReviewScreen()
    }
}

@Composable
fun bodyReviewScreen(
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        barraArriba(
            R.drawable.casa,
            ""
        )
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
        CajaReview("15 Nov 2026","1342","Desarrollo Movil", "Angarita", "Es una muy buena clase.")
        Spacer(modifier= Modifier.padding(23.dp))
        CajaReview("14 AGOS 206","5830","Ecuaciones Diferenciales", "Tole", "Los parciales son imposibles")

    }
}



@Composable
@Preview
fun ReviewScreenPreview(){
    ReviewScreen()
}