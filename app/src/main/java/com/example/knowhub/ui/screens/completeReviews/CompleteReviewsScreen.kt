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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.data.local.localReviewProvider
import com.example.knowhub.ui.screens.completeReviews.components.CajaPrincipal
import com.example.knowhub.ui.screens.completeReviews.components.Review as ReviewComponent
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppButtonBig
import com.example.knowhub.ui.utils.BackgroundImage
//Pantalla que despliega el detalle completo de una reseña general y sus comentarios asociados
@Composable
fun CompleteReviewsScreen(
    generalReviewId: Int,
    completeReviewsViewModel: CompleteReviewsViewModel,
    reviewPressed: (Int) -> Unit,
    escribirBottonPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    LaunchedEffect(generalReviewId) {
        completeReviewsViewModel.loadData(generalReviewId)
    }

    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyCompleteReviewsScreen(
            generalReviewId = generalReviewId,
            reviewPressed = reviewPressed,
            escribirBottonPressed = escribirBottonPressed
        )
    }
}
//Estructura de la interfaz para la vista de reseñas completas.
@Composable
fun BodyCompleteReviewsScreen(
    generalReviewId: Int,
    reviewPressed: (Int) -> Unit,
    escribirBottonPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    val generalReview = localGeneralReviewProvider.generalReviews.find { it.id == generalReviewId }
    val allReviews = localReviewProvider.Reviews
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {

                Spacer(modifier = Modifier.height(35.dp))
                if (generalReview != null) {
                    CajaPrincipal(
                        generalReview,
                        modifier = Modifier.width(350.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row() {
                    Box(
                        modifier = Modifier
                            .background(tertiaryContainerLight)
                            .height(2.5.dp)
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
                        modifier = Modifier
                            .background(tertiaryContainerLight)
                            .height(2.5.dp)
                            .width(150.dp)
                    ) {}
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.width(350.dp)
                ) {// Filtros de navegación
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
                Spacer(modifier = Modifier.height(20.dp))
            }
            // Listado de reseñas
            items(allReviews.size) { index ->
                ReviewComponent(
                    allReviews[index],
                    reviewPressed = reviewPressed,
                    modifier = Modifier.width(350.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            // Botón para agregar una nueva reseña
            item{
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
    CompleteReviewsScreen(
        generalReviewId = 5,
        completeReviewsViewModel = viewModel(),
        reviewPressed = {},
        escribirBottonPressed = {}
    )
}
