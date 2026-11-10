package com.example.knowhub.ui.screens.reviewMap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation

@OptIn(MapboxExperimental::class)
@Composable
fun ReviewMapScreen(
    viewModel: ReviewMapViewModel,
    onReviewPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val mapViewportState = rememberMapViewportState()
    val mapboxToken = stringResource(R.string.mapbox_access_token)

    LaunchedEffect(Unit) { viewModel.refresh() }
    LaunchedEffect(state.reviews) {
        val latitude = state.reviews.mapNotNull { it.latitude }.average()
        val longitude = state.reviews.mapNotNull { it.longitude }.average()
        if (latitude.isFinite() && longitude.isFinite()) {
            mapViewportState.setCameraOptions(
                cameraOptions {
                    center(Point.fromLngLat(longitude, latitude))
                    zoom(10.0)
                }
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (mapboxToken.isBlank()) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Configura MAPBOX_PUBLIC_TOKEN en las propiedades locales de Gradle para mostrar el mapa.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            MapboxMap(
                modifier = Modifier.fillMaxSize(),
                mapViewportState = mapViewportState
            ) {
                state.reviews.forEach { review ->
                    val latitude = review.latitude ?: return@forEach
                    val longitude = review.longitude ?: return@forEach
                    CircleAnnotation(
                        point = Point.fromLngLat(longitude, latitude)
                    ) {
                        circleRadius = 9.0
                        circleColor = Color(0xFF2457A7)
                        circleStrokeWidth = 2.0
                        circleStrokeColor = Color.White
                        interactionsState.onClicked {
                            viewModel.selectReview(review)
                            true
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Reseñas publicadas en las últimas 24 horas",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Mapas © Mapbox · Datos © OpenStreetMap",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            if (state.errorMessage != null) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "No se pudieron cargar las reseñas: ${state.errorMessage}",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else if (state.reviews.isEmpty() && !state.isLoading) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Aún no hay reseñas con ubicación en este periodo.",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
            if (state.isLoading) CircularProgressIndicator()
            Button(onClick = viewModel::refresh, enabled = !state.isLoading) {
                Text("Actualizar")
            }
        }

        state.selectedReview?.let { review ->
            ReviewMapInfo(
                review = review,
                onDismiss = { viewModel.selectReview(null) },
                onOpenReview = { onReviewPressed(review.id) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}

@Composable
private fun ReviewMapInfo(
    review: Review,
    onDismiss: () -> Unit,
    onOpenReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(review.nombreAsignatura, style = MaterialTheme.typography.titleMedium)
            Text("Profesor: ${review.nombreProfesor}")
            Text("Autor: ${review.nombreEstudiante}")
            Text("Calificación: ${review.calificacion}/5")
            Text(review.descripcion)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onOpenReview) { Text("Ver reseña") }
                Button(onClick = onDismiss) { Text("Cerrar") }
            }
        }
    }
}
