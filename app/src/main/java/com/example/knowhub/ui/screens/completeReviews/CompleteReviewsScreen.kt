package com.example.knowhub.ui.screens.completeReviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.ui.screens.completeReviews.components.CajaPrincipal
import com.example.knowhub.ui.screens.completeReviews.components.Review
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppButtonBig
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BarraArriba

@Composable
fun CompleteReviewsScreen(
    generalReviewId: Int,
    escribirBottonPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyCompleteReviewsScreen(generalReviewId,
            escribirBottonPressed = escribirBottonPressed)
    }
}

@Composable
fun BodyCompleteReviewsScreen(
    generalReviewId: Int,
    escribirBottonPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    val generalReview = localGeneralReviewProvider.generalReviews.find { it.id == generalReviewId }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {

                Spacer(modifier = modifier.height(35.dp))
                if (generalReview != null) {
                    CajaPrincipal(
                        generalReview,
                        modifier = Modifier.width(350.dp)
                    )
                }
                Spacer(modifier = modifier.height(30.dp))
                Row() {
                    Box(
                        modifier = modifier
                            .background(tertiaryContainerLight)
                            .height(2.5F.dp)
                            .width(150.dp)
                    ) {}
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.rese_as),
                        fontSize = 17.sp,
                        fontFamily = BangersFont
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = modifier
                            .background(tertiaryContainerLight)
                            .height(2.5F.dp)
                            .width(150.dp)
                    ) {}
                }
                Spacer(modifier = modifier.height(20.dp))
                Row(
                    modifier = Modifier.width(350.dp)
                ) {
                    AppButton(
                        stringResource(R.string.para_ti),
                        primaryLight,
                        primaryContainerLight,
                        modifier = Modifier.height(40.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    AppButton(
                        stringResource(R.string.siguiendo),
                        tertiaryContainerLight,
                        secondaryContainerLight,
                        modifier = Modifier.height(40.dp)
                    )
                }
                Spacer(modifier = modifier.height(20.dp))

                Column(modifier = Modifier.width(350.dp)) {
                    Review(
                        "15 Nov 2026",
                        "Dana Trujillo",
                        4,
                        "Explica super y califica suave!!!",
                        7,
                        5
                    )
                    Spacer(modifier = modifier.height(20.dp))
                    Review(
                        "20 Nov 2026",
                        "Sebastian Gaibor",
                        2,
                        "Materia pesada, es importante llevar la calculadora SIEMPRE",
                        30,
                        6
                    )
                }

                Spacer(modifier = modifier.height(100.dp))
                AppButtonBig(
                    stringResource(R.string.escribe_tu_rese_a),
                    secondaryContainerLight,
                    tertiaryContainerLight,
                    onClick = {
                        escribirBottonPressed()
                              },
                    modifier = Modifier
                        .width(300.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompleteReviewsScreenPreview(){
    CompleteReviewsScreen(5,
        escribirBottonPressed = {})
}
