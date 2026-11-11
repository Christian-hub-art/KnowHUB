package com.example.knowhub.ui.screens.reviewMap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.data.Review
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.ProfileAsyncImage
import com.example.knowhub.ui.utils.generarEstrellas
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation

@Composable
fun ReviewMapScreen(
    viewModel: ReviewMapViewModel,
    onReviewPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Observa el estado emitido por el ViewModel.
    val state by viewModel.uiState.collectAsState()
    // Solicita las reseñas recientes cuando se muestra la pantalla.
    LaunchedEffect(Unit) { viewModel.refresh() }

    BodyReviewMapScreen(
        state = state,
        onReviewPressed = onReviewPressed,
        onRefresh = viewModel::refresh,
        onReviewSelected = viewModel::selectReview,
        modifier = modifier
    )
}

@OptIn(MapboxExperimental::class)
@Composable
fun BodyReviewMapScreen(
    state: ReviewMapState,
    onReviewPressed: (String) -> Unit,
    onRefresh: () -> Unit,
    onReviewSelected: (Review?) -> Unit,
    modifier: Modifier = Modifier
) {
    // La cámara y el mapa son parte de la presentación de Mapbox.
    // Crea y conserva el estado de cámara de Mapbox durante la composición.
    val mapViewportState = rememberMapViewportState()

    // Aplica a Mapbox la posición de cámara calculada y publicada en el estado de pantalla.
    LaunchedEffect(state.cameraLatitude, state.cameraLongitude, state.cameraZoom) {
        val latitude = state.cameraLatitude
        val longitude = state.cameraLongitude
        if (latitude != null && longitude != null) {
            mapViewportState.setCameraOptions(
                cameraOptions {
                    center(Point.fromLngLat(longitude, latitude))
                    zoom(state.cameraZoom)
                }
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (state.mapboxToken.isBlank()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(24.dp)
                    .mapPanelStyle()
            ) {
                Text(
                    text = "No se pudo mostrar el mapa.",
                    modifier = Modifier.padding(16.dp),
                    fontFamily = ArvoFont,
                    color = tertiaryContainerLight
                )
            }
        } else {
            // Mapbox muestra el mapa base y un marcador por cada reseña geolocalizada.
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
                            onReviewSelected(review)
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().mapPanelStyle()) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(primaryContainerLight)
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "MAPA DE RESEÑAS",
                                color = primaryLight,
                                fontFamily = BangersFont,
                                fontSize = 23.sp
                            )
                            Text(
                                text = "Publicadas en las últimas 24 horas",
                                color = primaryLight,
                                fontFamily = ArvoFont,
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            text = "${state.reviews.size}",
                            modifier = Modifier
                                .background(secondaryContainerLight, CircleShape)
                                .padding(horizontal = 11.dp, vertical = 5.dp),
                            color = tertiaryContainerLight,
                            fontFamily = BangersFont,
                            fontSize = 18.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 6.dp, top = 5.dp, bottom = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Mapas © Mapbox",
                            color = tertiaryContainerLight,
                            fontFamily = ArvoFont,
                            fontSize = 9.sp
                        )
                        IconButton(
                            onClick = onRefresh,
                            enabled = !state.isLoading,
                            modifier = Modifier.size(38.dp)
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = primaryContainerLight,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Actualizar reseñas",
                                    tint = primaryContainerLight,
                                    modifier = Modifier.size(21.dp)
                                )
                            }
                        }
                    }
                }
            }
            if (state.errorMessage != null) {
                Column(modifier = Modifier.fillMaxWidth().mapPanelStyle()) {
                    Text(
                        text = "No se pudieron cargar las reseñas: ${state.errorMessage}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(primaryLight)
                            .padding(12.dp),
                        color = Color(0xFFBA1A1A),
                        fontFamily = ArvoFont,
                        fontSize = 12.sp
                    )
                }
            } else if (state.reviews.isEmpty() && !state.isLoading) {
                Column(modifier = Modifier.fillMaxWidth().mapPanelStyle()) {
                    Text(
                        text = "Aún no hay reseñas ubicadas en este periodo. ¡Sé el primero en compartir una!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(primaryLight)
                            .padding(12.dp),
                        color = tertiaryContainerLight,
                        fontFamily = ArvoFont,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Al tocar un marcador, muestra aquí el detalle de la reseña seleccionada.
        state.selectedReview?.let { review ->
            ReviewMapInfo(
                review = review,
                onDismiss = { onReviewSelected(null) },
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
    // Panel con los datos de la reseña seleccionada y acceso a su pantalla completa.
    Column(modifier = modifier.mapPanelStyle()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryContainerLight)
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileAsyncImage(
                    profileImage = review.fotoAutor,
                    size = 38,
                    modifier = Modifier.border(2.dp, primaryLight, CircleShape)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = review.nombreEstudiante,
                        color = primaryLight,
                        fontFamily = BangersFont,
                        fontSize = 18.sp
                    )
                    Text(
                        text = review.nombreAsignatura,
                        color = primaryLight,
                        fontFamily = ArvoFont,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                    Text("×", color = primaryLight, fontSize = 26.sp)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryLight)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Prof. ${review.nombreProfesor}",
                        color = tertiaryContainerLight,
                        fontFamily = ArvoFont,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = generarEstrellas(review.calificacion),
                        fontSize = 15.sp
                    )
                }
                Text(
                    text = review.descripcion,
                    color = tertiaryContainerLight,
                    fontFamily = ArvoFont,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                AppButton(
                    textoButon = "VER RESEÑA",
                    colorTexto = primaryLight,
                    colorBoton = primaryContainerLight,
                    onClick = onOpenReview,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp)
                )
            }
        }
    }
}

private fun Modifier.mapPanelStyle(): Modifier =
    this
        .drawBehind {
            drawRect(
                color = secondaryContainerLight,
                topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
                size = size
            )
        }
        .background(primaryLight)
        .border(2.dp, tertiaryContainerLight)
