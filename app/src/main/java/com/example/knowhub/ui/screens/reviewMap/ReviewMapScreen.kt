package com.example.knowhub.ui.screens.reviewMap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.ui.screens.reviewMap.components.MapControlButton
import com.example.knowhub.ui.screens.reviewMap.components.MapMessage
import com.example.knowhub.ui.screens.reviewMap.components.ReviewLocationGroup
import com.example.knowhub.ui.screens.reviewMap.components.ReviewMapCarousel
import com.example.knowhub.ui.screens.reviewMap.components.ReviewMapHeader
import com.example.knowhub.ui.screens.reviewMap.components.ReviewMapMarker
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import kotlin.math.roundToInt

private const val LIGHT_MAP_STYLE = "mapbox://styles/mapbox/streets-v12"
private const val DARK_MAP_STYLE = "mapbox://styles/mapbox/dark-v11"

//Pantalla con el mapa de las reseñas publicadas en las últimas 24 horas.
@Composable
fun ReviewMapScreen(
    viewModel: ReviewMapViewModel,
    onReviewPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    var isDarkMode by remember { mutableStateOf(false) }
    var showLocationSettingsDialog by remember { mutableStateOf(false) }
    var locationPermissionDenied by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val locationManager = context.getSystemService(LocationManager::class.java)
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            ) {
                viewModel.locateMe()
            } else {
                showLocationSettingsDialog = true
            }
        } else {
            locationPermissionDenied = true
        }
    }

    LaunchedEffect(Unit) { viewModel.refresh() }

    BodyReviewMapScreen(
        state = state,
        isDarkMode = isDarkMode,
        onToggleDarkMode = { isDarkMode = !isDarkMode },
        onReviewPressed = onReviewPressed,
        onRefresh = viewModel::refresh,
        onReviewsSelected = viewModel::selectReviews,
        onClearSelectedReviews = viewModel::clearSelectedReviews,
        onZoomIn = { viewport -> viewport.scaleBy(2.0) },
        onZoomOut = { viewport -> viewport.scaleBy(0.5) },
        onLocateMe = {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            } else {
                val locationManager = context.getSystemService(LocationManager::class.java)
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                ) {
                    viewModel.locateMe()
                } else {
                    showLocationSettingsDialog = true
                }
            }
        },
        modifier = modifier
    )

    if (showLocationSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showLocationSettingsDialog = false },
            title = { Text(stringResource(R.string.mapa_activa_ubicacion_titulo)) },
            text = { Text(stringResource(R.string.mapa_activa_ubicacion_texto)) },
            confirmButton = {
                TextButton(onClick = {
                    showLocationSettingsDialog = false
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }) {
                    Text(stringResource(R.string.mapa_abrir_ajustes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLocationSettingsDialog = false }) {
                    Text(stringResource(R.string.mapa_cancelar))
                }
            }
        )
    }

    if (locationPermissionDenied) {
        AlertDialog(
            onDismissRequest = { locationPermissionDenied = false },
            title = { Text(stringResource(R.string.mapa_permiso_titulo)) },
            text = { Text(stringResource(R.string.mapa_permiso_texto)) },
            confirmButton = {
                TextButton(onClick = { locationPermissionDenied = false }) {
                    Text(stringResource(R.string.mapa_entendido))
                }
            }
        )
    }
}

@OptIn(MapboxExperimental::class)
@Composable
private fun BodyReviewMapScreen(
    state: ReviewMapState,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onReviewPressed: (String) -> Unit,
    onRefresh: () -> Unit,
    onReviewsSelected: (List<Review>) -> Unit,
    onClearSelectedReviews: () -> Unit,
    onZoomIn: (MapViewportState) -> Unit,
    onZoomOut: (MapViewportState) -> Unit,
    onLocateMe: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mapViewportState = rememberMapViewportState()
    val locationGroups = remember(state.reviews) {
        state.reviews
            .filter { it.latitude != null && it.longitude != null }
            .groupBy { review ->
                (review.latitude!! * 10_000).roundToInt() to (review.longitude!! * 10_000).roundToInt()
            }
            .values
            .map { reviews ->
                ReviewLocationGroup(
                    latitude = reviews.first().latitude!!,
                    longitude = reviews.first().longitude!!,
                    reviews = reviews
                )
            }
    }

    LaunchedEffect(state.cameraUpdateId) {
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
            MapMessage(
                text = stringResource(R.string.mapa_sin_token),
                isDarkMode = isDarkMode,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            MapboxMap(
                modifier = Modifier.fillMaxSize(),
                mapViewportState = mapViewportState,
                compass = {},
                scaleBar = {},
                style = {
                    MapStyle(style = if (isDarkMode) DARK_MAP_STYLE else LIGHT_MAP_STYLE)
                }
            ) {
                locationGroups.forEach { group ->
                    val point = Point.fromLngLat(group.longitude, group.latitude)
                    ViewAnnotation(
                        options = viewAnnotationOptions {
                            geometry(point)
                            allowOverlap(true)
                        }
                    ) {
                        ReviewMapMarker(
                            group = group,
                            isDarkMode = isDarkMode,
                            onClick = { onReviewsSelected(group.reviews) }
                        )
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
            ReviewMapHeader(
                totalReviews = state.reviews.size,
                isLoading = state.isLoading,
                isDarkMode = isDarkMode,
                onToggleDarkMode = onToggleDarkMode,
                onRefresh = onRefresh
            )
            if (state.errorMessage != null) {
                MapMessage(
                    text = stringResource(R.string.mapa_error_resenas, state.errorMessage),
                    isDarkMode = isDarkMode,
                    isError = true
                )
            } else if (state.reviews.isEmpty() && !state.isLoading) {
                MapMessage(
                    text = stringResource(R.string.mapa_sin_resenas),
                    isDarkMode = isDarkMode
                )
            }
            state.locationErrorMessage?.let {
                MapMessage(text = it, isDarkMode = isDarkMode, isError = true)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MapControlButton(
                icon = Icons.Default.Add,
                label = stringResource(R.string.mapa_acercar),
                isDarkMode = isDarkMode,
                onClick = { onZoomIn(mapViewportState) }
            )
            MapControlButton(
                icon = Icons.Default.Remove,
                label = stringResource(R.string.mapa_alejar),
                isDarkMode = isDarkMode,
                onClick = { onZoomOut(mapViewportState) }
            )
            MapControlButton(
                icon = Icons.Default.MyLocation,
                label = stringResource(R.string.mapa_centrar),
                isDarkMode = isDarkMode,
                isLoading = state.isLocating,
                onClick = onLocateMe
            )
        }

        if (state.selectedReviews.isNotEmpty()) {
            ReviewMapCarousel(
                reviews = state.selectedReviews,
                isDarkMode = isDarkMode,
                onDismiss = onClearSelectedReviews,
                onOpenReview = onReviewPressed,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}
