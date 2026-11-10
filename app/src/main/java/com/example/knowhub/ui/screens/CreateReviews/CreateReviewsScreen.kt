package com.example.knowhub.ui.screens.CreateReviews

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
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
    reviewId: String? = null,
    generalReviewId: Int? = null
) {
    val state by createReviewsViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var compartirUbicacion by remember { mutableStateOf(false) }
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val publishWithLocation = {
        locationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            if (location != null) {
                createReviewsViewModel.publicarButtonPressed(location.latitude, location.longitude)
            } else {
                Toast.makeText(context, "No se obtuvo la ubicación; se publicará sin ella.", Toast.LENGTH_LONG).show()
                createReviewsViewModel.publicarButtonPressed()
            }
        }.addOnFailureListener { error ->
            Toast.makeText(context, "No se pudo obtener la ubicación: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
            createReviewsViewModel.publicarButtonPressed()
        }
    }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            publishWithLocation()
        } else {
            Toast.makeText(context, "Se publicará la reseña sin ubicación.", Toast.LENGTH_LONG).show()
            createReviewsViewModel.publicarButtonPressed()
        }
    }

    LaunchedEffect(generalReviewId) {
        if (generalReviewId != null) {
            createReviewsViewModel.loadGeneralReview(generalReviewId)
        }
    }

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
            calificacion = state.calificacion,
            userName = state.userName,
            errorMessage = state.errorMessage,
            mostrarMensajeError = state.mostrarMensajeError,
            editando = reviewId != null,
            compartirUbicacion = compartirUbicacion,
            onCompartirUbicacionChange = { compartirUbicacion = it },
            onClaseChange = { createReviewsViewModel.updateClase(it) },
            onTituloMateriaChange = { createReviewsViewModel.updateTituloMateria(it) },
            onNombreProfesorChange = { createReviewsViewModel.updateNombreProfesor(it) },
            onResenaChange = { createReviewsViewModel.updateResena(it) },
            onCalificacionChange = { createReviewsViewModel.updateCalificacion(it) },
            onClick = {
                if (!compartirUbicacion) {
                    createReviewsViewModel.publicarButtonPressed()
                } else if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    publishWithLocation()
                } else {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
            },
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
    calificacion: Int,
    userName: String,
    errorMessage: String,
    mostrarMensajeError: Boolean,
    editando: Boolean = false,
    compartirUbicacion: Boolean = false,
    onCompartirUbicacionChange: (Boolean) -> Unit = {},
    onClaseChange: (String) -> Unit,
    onTituloMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onResenaChange: (String) -> Unit,
    onCalificacionChange: (Int) -> Unit,
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
            calificacion = calificacion,
            onClaseChange = onClaseChange,
            onTituloMateriaChange = onTituloMateriaChange,
            onNombreProfesorChange = onNombreProfesorChange,
            onResenaChange = onResenaChange,
            onCalificacionChange = onCalificacionChange,
            compartirUbicacion = compartirUbicacion,
            onCompartirUbicacionChange = onCompartirUbicacionChange,
            mostrarCompartirUbicacion = !editando,
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
