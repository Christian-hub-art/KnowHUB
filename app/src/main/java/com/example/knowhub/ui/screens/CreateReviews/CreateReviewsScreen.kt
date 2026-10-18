package com.example.knowhub.ui.screens.CreateReviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.ui.screens.CreateReviews.components.CuadroResenas
import com.example.knowhub.ui.screens.CreateReviews.components.InformaciónUsuario
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabelBig
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.R

@Composable
fun CreateReviewsScreen(
    createReviewsViewModel: CreateReviewsViewModel,
    modifier: Modifier = Modifier,
    reviewId: String? = null
) {
    val state by createReviewsViewModel.uiState.collectAsState()

    // Modo edición: carga la reseña para llenar el formulario
    LaunchedEffect(reviewId) {
        if (reviewId != null) {
            createReviewsViewModel.loadReview(reviewId)
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        BackgroundImage()
        BodyCreateReviewsScreen(
            clase = state.clase,
            tituloMateria = state.tituloMateria,
            nombreProfesor = state.nombreProfesor,
            resena = state.resena,
            userName = state.userName,
            errorMessage = state.errorMessage,
            mostrarMensajeError = state.mostrarMensajeError,
            editando = reviewId != null,
            onClaseChange = { createReviewsViewModel.updateClase(it) },
            onTituloMateriaChange = { createReviewsViewModel.updateTituloMateria(it) },
            onNombreProfesorChange = { createReviewsViewModel.updateNombreProfesor(it) },
            onResenaChange = { createReviewsViewModel.updateResena(it) },
            onClick = { createReviewsViewModel.publicarButtonPressed() },
            onCancelarClick = { createReviewsViewModel.cancelarButtonPressed() }
        )
    }
}

@Composable
fun BodyCreateReviewsScreen(
    clase: String,
    tituloMateria: String,
    nombreProfesor: String,
    resena: String,
    userName: String,
    errorMessage: String,
    mostrarMensajeError: Boolean,
    editando: Boolean = false,
    onClaseChange: (String) -> Unit,
    onTituloMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onResenaChange: (String) -> Unit,
    onClick: () -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        AppLabelBig(
            texto = stringResource(if (editando) R.string.editar_resena else R.string.crea_nueva_resena),
            colorTexto = primaryLight,
            color = tertiaryContainerLight,
            modifier = Modifier
                .width(350.dp)
                .height(45.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Usuario Info
        InformaciónUsuario(userName = userName)

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
            textoPublicar = stringResource(if (editando) R.string.guardar else R.string.publicar)
        )

        if (mostrarMensajeError) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(errorMessage)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CreateReviewsPreview() {
    CreateReviewsScreen(
        createReviewsViewModel = viewModel()
    )
}
