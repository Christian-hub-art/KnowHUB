package com.example.knowhub.ui.screens.CreateMateria

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.knowhub.R
import com.example.knowhub.ui.screens.CreateMateria.components.CuadroMateria
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabelBig
import com.example.knowhub.ui.utils.BackgroundImage
//Pantalla para crear una materia nueva o editar y eliminar una existente.
@Composable
fun CreateMateriaScreen(
    createMateriaViewModel: CreateMateriaViewModel,
    modifier: Modifier = Modifier,
    generalReviewId: Int? = null
) {
    val state by createMateriaViewModel.uiState.collectAsState()

    LaunchedEffect(generalReviewId) {
        if (generalReviewId != null) {
            createMateriaViewModel.loadMateria(generalReviewId)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        BackgroundImage()
        BodyCreateMateriaScreen(
            nombreMateria = state.nombreMateria,
            nombreProfesor = state.nombreProfesor,
            codigo = state.codigo,
            dificultad = state.dificultad,
            hashtags = state.hashtags,
            errorMessage = state.errorMessage,
            mostrarMensajeError = state.mostrarMensajeError,
            editando = generalReviewId != null,
            onNombreMateriaChange = { createMateriaViewModel.updateNombreMateria(it) },
            onNombreProfesorChange = { createMateriaViewModel.updateNombreProfesor(it) },
            onCodigoChange = { createMateriaViewModel.updateCodigo(it) },
            onDificultadChange = { createMateriaViewModel.updateDificultad(it) },
            onHashtagsChange = { createMateriaViewModel.updateHashtags(it) },
            onClick = { createMateriaViewModel.guardarButtonPressed() },
            onCancelarClick = { createMateriaViewModel.cancelarButtonPressed() },
            onEliminarClick = { createMateriaViewModel.eliminarButtonPressed() }
        )
    }
}

@Composable
fun BodyCreateMateriaScreen(
    nombreMateria: String,
    nombreProfesor: String,
    codigo: String,
    dificultad: String,
    hashtags: String,
    errorMessage: String,
    mostrarMensajeError: Boolean,
    editando: Boolean = false,
    onNombreMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onCodigoChange: (String) -> Unit,
    onDificultadChange: (String) -> Unit,
    onHashtagsChange: (String) -> Unit,
    onClick: () -> Unit,
    onCancelarClick: () -> Unit,
    onEliminarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        AppLabelBig(
            texto = stringResource(if (editando) R.string.editar_materia else R.string.crea_nueva_materia),
            colorTexto = primaryLight,
            color = tertiaryContainerLight,
            modifier = Modifier
                .width(350.dp)
                .height(45.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CuadroMateria(
            nombreMateria = nombreMateria,
            nombreProfesor = nombreProfesor,
            codigo = codigo,
            dificultad = dificultad,
            hashtags = hashtags,
            editando = editando,
            onNombreMateriaChange = onNombreMateriaChange,
            onNombreProfesorChange = onNombreProfesorChange,
            onCodigoChange = onCodigoChange,
            onDificultadChange = onDificultadChange,
            onHashtagsChange = onHashtagsChange,
            onClick = onClick,
            onCancelarClick = onCancelarClick
        )

        if (mostrarMensajeError) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(errorMessage)
        }

        if (editando) {
            Spacer(modifier = Modifier.height(20.dp))
            // Elimina la materia junto con sus reseñas
            AppButton(
                textoButon = stringResource(R.string.eliminar_materia),
                colorTexto = tertiaryContainerLight,
                colorBoton = secondaryContainerLight,
                onClick = onEliminarClick,
                modifier = Modifier
                    .height(40.dp)
                    .width(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CreateMateriaPreview() {
    CreateMateriaScreen(
        createMateriaViewModel = viewModel()
    )
}
