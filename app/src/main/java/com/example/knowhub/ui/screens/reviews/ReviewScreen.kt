package com.example.knowhub.ui.screens.reviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.reviews.components.CajaReview
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BarraArriba

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier=modifier) {
        BackgroundImage()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            BarraArriba(
                R.drawable.iconomenu,
                ""
            )
            BodyReviewScreen()
        }
    }
}

@Composable
fun BodyReviewScreen(
    modifier: Modifier = Modifier
){
    Spacer(modifier = modifier.height(35.dp))

    AppButton(
        stringResource(R.string.tus_rese_as),
        colorResource(R.color.blancoKnowHUB),
        colorResource(R.color.NegroKnowHUB),
        modifier = Modifier
            .height(40.dp)
            .width(320.dp)
    )

    Spacer(modifier = Modifier.height(10.dp))
    Row{

        AppButton("Leo6767",
            colorResource(R.color.NegroKnowHUB),
            colorResource(R.color.blancoKnowHUB))

        Spacer(modifier = Modifier.width(20.dp))

        AppButton("3" + " " + stringResource(R.string.rese_as),
            colorResource(R.color.NegroKnowHUB),
            colorResource(R.color.blancoKnowHUB))
        Spacer(modifier = Modifier.width(120.dp))

    }
    Column(
        modifier = Modifier
            .width(325.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        CajaReview(
            "15 Nov 2026",
            "1342",
            "Desarrollo Movil",
            "Angarita",
            "Es una muy buena clase.",
            5,
            colorResource(id = R.color.AzulKnowHUB),
            colorResource(id = R.color.blancoKnowHUB)
        )
        Spacer(modifier = Modifier.height(15.dp))
        CajaReview(
            "15 Nov 2026",
            "1342",
            "Desarrollo Movil",
            "Angarita",
            "Es una muy buena clase.",
            2,
            colorResource(id = R.color.AmarilloKnowHUB),
            colorResource(id = R.color.NegroKnowHUB)
        )
        Spacer(modifier = Modifier.height(15.dp))
        CajaReview(
            "15 Nov 2026",
            "1342",
            "Desarrollo Movil",
            "Angarita",
            "Es una muy buena clase.",
            1,
            colorResource(id = R.color.AzulKnowHUB),
            colorResource(id = R.color.blancoKnowHUB)
        )
    }
}



@Composable
@Preview
fun ReviewScreenPreview(){
    ReviewScreen()
}