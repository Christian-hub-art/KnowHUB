package com.example.knowhub.ui.screens.CreateReviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.CreateReviews.components.CuadroResenas
import com.example.knowhub.ui.screens.CreateReviews.components.InformaciónUsuario
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabelBig
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun CreateReviewsScreen(
    createReviewsViewModel: CreateReviewsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    reviewId: String? = null
) {
    val state by createReviewsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (!reviewId.isNullOrBlank()) {
            createReviewsViewModel.getReviewById(reviewId)
        }
    }

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            onBack()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        BackgroundImage()
        BodyCreateReviewsScreen(
            clase = state.clase,
            tituloMateria = state.tituloMateria,
            nombreProfesor = state.nombreProfesor,
            resena = state.resena,
            isEditing = !reviewId.isNullOrBlank(),
            onClaseChange = { createReviewsViewModel.updateClase(it) },
            onTituloMateriaChange = { createReviewsViewModel.updateTituloMateria(it) },
            onNombreProfesorChange = { createReviewsViewModel.updateNombreProfesor(it) },
            onResenaChange = { createReviewsViewModel.updateResena(it) },
            onClick = { createReviewsViewModel.createReview(reviewId = reviewId) },
            onCancelarClick = onBack
        )
    }
}

@Composable
fun BodyCreateReviewsScreen(
    clase: String,
    tituloMateria: String,
    nombreProfesor: String,
    resena: String,
    isEditing: Boolean = false,
    onClaseChange: (String) -> Unit,
    onTituloMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onResenaChange: (String) -> Unit,
    onClick: () -> Unit,
    onCancelarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        AppLabelBig(
            texto = if (isEditing) stringResource(R.string.editar_resena_title) else stringResource(R.string.crea_nueva_resena),
            colorTexto = primaryLight,
            color = tertiaryContainerLight,
            modifier = Modifier
                .width(350.dp)
                .height(45.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        InformaciónUsuario()

        Spacer(modifier = Modifier.height(15.dp))

        CuadroResenas(
            clase = clase,
            tituloMateria = tituloMateria,
            nombreProfesor = nombreProfesor,
            resena = resena,
            onClaseChange = onClaseChange,
            onTituloMateriaChange = onTituloMateriaChange,
            onNombreProfesorChange = onNombreProfesorChange,
            onResenaChange = onResenaChange,
            onClick = onClick,
            onCancelarClick = onCancelarClick,
            botonTexto = if (isEditing) stringResource(R.string.guardar_btn) else stringResource(R.string.publicar)
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}
