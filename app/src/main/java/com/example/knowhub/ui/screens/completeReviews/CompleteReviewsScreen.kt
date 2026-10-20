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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.data.Review
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
    reviewPressed: (String) -> Unit,
    escribirBottonPressed: () -> Unit,
    modifier: Modifier = Modifier,
    autorPressed: (String) -> Unit = {}
){
    val state by completeReviewsViewModel.uiState.collectAsState()

    LaunchedEffect(generalReviewId) {
        completeReviewsViewModel.loadData(generalReviewId)
    }

    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyCompleteReviewsScreen(
            generalReviewId = generalReviewId,
            allReviews = state.visibleReviews,
            showFollowingOnly = state.showFollowingOnly,
            followingErrorMessage = state.followingErrorMessage,
            errorMessage = state.errorMessage,
            mostrarMensajeError = state.mostrarMensajeError,
            reviewPressed = reviewPressed,
            escribirBottonPressed = escribirBottonPressed,
            autorPressed = autorPressed,
            onRateReview = { reviewId, rating ->
                completeReviewsViewModel.rateReview(reviewId, rating)
            },
            onShowAll = completeReviewsViewModel::showAllReviews,
            onShowFollowing = completeReviewsViewModel::showFollowedUsersReviews
        )
    }
}
//Estructura de la interfaz para la vista de reseñas completas.
@Composable
fun BodyCompleteReviewsScreen(
    generalReviewId: Int,
    allReviews: List<Review>,
    errorMessage: String,
    mostrarMensajeError: Boolean,
    reviewPressed: (String) -> Unit,
    escribirBottonPressed: () -> Unit,
    modifier: Modifier = Modifier,
    autorPressed: (String) -> Unit = {},
    onRateReview: (String, Int) -> Unit = { _, _ -> },
    showFollowingOnly: Boolean = false,
    followingErrorMessage: String = "",
    onShowAll: () -> Unit = {},
    onShowFollowing: () -> Unit = {}
){
    val generalReview = localGeneralReviewProvider.generalReviews.find { it.id == generalReviewId }
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
                        if (!showFollowingOnly) primaryContainerLight else tertiaryContainerLight,
                        onClick = onShowAll,
                        modifier = Modifier.height(40.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    AppButton(
                        stringResource(R.string.siguiendo),
                        primaryLight,
                        if (showFollowingOnly) primaryContainerLight else secondaryContainerLight,
                        onClick = onShowFollowing,
                        modifier = Modifier.height(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
            if (mostrarMensajeError) {
                item { Text(errorMessage) }
            }
            if (showFollowingOnly && followingErrorMessage.isNotBlank()) {
                item { Text(followingErrorMessage) }
            }
            // Listado de reseñas
            items(allReviews.size) { index ->
                ReviewComponent(
                    allReviews[index],
                    reviewPressed = reviewPressed,
                    modifier = Modifier.width(350.dp),
                    autorPressed = autorPressed,
                    onRateReview = onRateReview
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
