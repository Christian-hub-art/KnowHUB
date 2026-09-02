package com.example.knowhub.ui.screens.CreateReviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.ui.screens.CreateReviews.components.CuadroResenas
import com.example.knowhub.ui.screens.CreateReviews.components.InformaciónUsuario
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabelBig
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun CreateReviewsScreen(
    createReviewsViewModel: CreateReviewsViewModel,
    modifier: Modifier = Modifier
) {
    val state by createReviewsViewModel.uiState.collectAsState()
    Box(modifier = modifier.fillMaxSize()) {
        BackgroundImage()
        BodyCreateReviewsScreen(
            clase = state.clase,
            tituloMateria = state.tituloMateria,
            nombreProfesor = state.nombreProfesor,
            resena = state.resena,
            onClaseChange = { createReviewsViewModel.updateClase(it) },
            onTituloMateriaChange = { createReviewsViewModel.updateTituloMateria(it) },
            onNombreProfesorChange = { createReviewsViewModel.updateNombreProfesor(it) },
            onResenaChange = { createReviewsViewModel.updateResena(it) },
            onClick = {}
        )
    }
}

@Composable
fun BodyCreateReviewsScreen(
    clase: String,
    tituloMateria: String,
    nombreProfesor: String,
    resena: String,
    onClaseChange: (String) -> Unit,
    onTituloMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onResenaChange: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        AppLabelBig(
            texto = stringResource(R.string.crea_nueva_resena),
            colorTexto = primaryLight,
            color = tertiaryContainerLight,
            modifier = Modifier
                .width(350.dp)
                .height(45.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Usuario Info
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
            onClick = onClick
        )

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
