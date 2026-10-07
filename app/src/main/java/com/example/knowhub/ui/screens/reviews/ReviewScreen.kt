package com.example.knowhub.ui.screens.reviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.ui.screens.reviews.components.CajaReview
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage

// Pantalla que despliega el historial de reseñas publicadas por el usuario.
@Composable
fun ReviewScreen(
    onEditClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
    ) {
        BackgroundImage()

        BodyReviewScreen(
            usuario = uiState.usuario.ifBlank { stringResource(R.string.usuario_default) },
            reviews = uiState.reviews,
            onEditClick = onEditClick,
            onDeleteClick = { reviewId -> viewModel.deleteReview(reviewId) }
        )
    }
}
// Maquetación principal y renderizado de la lista de reseñas.
@Composable
fun BodyReviewScreen(
    modifier: Modifier = Modifier,
    usuario: String = stringResource(R.string.laura),
    reviews: List<Review> = localReviewProvider.Reviews,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: (String) -> Unit = {}
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(35.dp))

        AppLabel(
            stringResource(R.string.tus_rese_as),
            primaryLight,
            tertiaryContainerLight,
            modifier = Modifier
                .height(40.dp)
                .width(320.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {

            AppLabel(
                usuario,
                tertiaryContainerLight,
                primaryLight
            )

            Spacer(modifier = Modifier.width(20.dp))

            AppLabel(
                stringResource(
                    R.string.num_resenas_label,
                    reviews.size
                ),
                tertiaryContainerLight,
                primaryLight
            )

            Spacer(modifier = Modifier.width(150.dp))
        }

        LazyColumn(
            modifier = Modifier.width(325.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            itemsIndexed(reviews) { index, review ->

                val colorCaja =
                    if (index % 2 == 0) {
                        primaryContainerLight
                    } else {
                        secondaryContainerLight
                    }

                val colorTexto =
                    if (index % 2 == 0) {
                        primaryLight
                    } else {
                        tertiaryContainerLight
                    }

                CajaReview(
                    Fecha = review.fechaPublicacion,
                    Codigo = review.id,
                    Materia = review.nombreAsignatura,
                    Profesor = review.nombreProfesor,
                    Reseña = review.descripcion,
                    Calificacion = review.calificacion.toIntOrNull() ?: 0,
                    colorCaja = colorCaja,
                    colorTexto = colorTexto,
                    onEditClick = { onEditClick(review.id.toString()) },
                    onDeleteClick = { onDeleteClick(review.id.toString()) }
                )

                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}
