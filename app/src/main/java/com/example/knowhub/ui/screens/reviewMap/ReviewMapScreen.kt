package com.example.knowhub.ui.screens.reviewMap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
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
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import kotlin.math.roundToInt

private const val LIGHT_MAP_STYLE = "mapbox://styles/mapbox/streets-v12"
private const val DARK_MAP_STYLE = "mapbox://styles/mapbox/dark-v11"
private val REVIEW_HEADER_BLUE = Color(0xFF0252E7)

private data class ReviewLocationGroup(
    val latitude: Double,
    val longitude: Double,
    val reviews: List<Review>
)

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
            title = { Text("Activa la ubicación") },
            text = {
                Text("Enciende la ubicación del dispositivo para centrar el mapa en tu posición.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showLocationSettingsDialog = false
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }) {
                    Text("Abrir ajustes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLocationSettingsDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (locationPermissionDenied) {
        AlertDialog(
            onDismissRequest = { locationPermissionDenied = false },
            title = { Text("Permiso de ubicación") },
            text = { Text("Permite el acceso a la ubicación para centrar el mapa en tu posición.") },
            confirmButton = {
                TextButton(onClick = { locationPermissionDenied = false }) {
                    Text("Entendido")
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
    onZoomIn: (com.mapbox.maps.extension.compose.animation.viewport.MapViewportState) -> Unit,
    onZoomOut: (com.mapbox.maps.extension.compose.animation.viewport.MapViewportState) -> Unit,
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
                text = "No se pudo mostrar el mapa. Configura el token público de Mapbox.",
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
            Surface(
                color = panelColor(isDarkMode),
                contentColor = panelTextColor(isDarkMode),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 8.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(primaryContainerLight)
                            .padding(start = 16.dp, end = 8.dp, top = 10.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
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
                        IconButton(
                            onClick = onToggleDarkMode,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (isDarkMode) "Cambiar a modo claro" else "Cambiar a modo oscuro",
                                tint = if (isDarkMode) Color(0xFFFFD166) else primaryLight
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 14.dp, end = 6.dp, top = 2.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Mapas © Mapbox",
                            color = panelTextColor(isDarkMode).copy(alpha = 0.65f),
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
                                    color = if (isDarkMode) Color(0xFF8BB8FF) else primaryContainerLight,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Actualizar reseñas",
                                    tint = if (isDarkMode) Color(0xFF8BB8FF) else primaryContainerLight,
                                    modifier = Modifier.size(21.dp)
                                )
                            }
                        }
                    }
                }
            }
            if (state.errorMessage != null) {
                MapMessage(
                    text = "No se pudieron cargar las reseñas: ${state.errorMessage}",
                    isDarkMode = isDarkMode,
                    isError = true
                )
            } else if (state.reviews.isEmpty() && !state.isLoading) {
                MapMessage(
                    text = "Aún no hay reseñas ubicadas en este periodo. ¡Sé el primero en compartir una!",
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
                label = "Acercar mapa",
                isDarkMode = isDarkMode,
                onClick = { onZoomIn(mapViewportState) }
            )
            MapControlButton(
                icon = Icons.Default.Remove,
                label = "Alejar mapa",
                isDarkMode = isDarkMode,
                onClick = { onZoomOut(mapViewportState) }
            )
            MapControlButton(
                icon = Icons.Default.MyLocation,
                label = "Centrar en mi ubicación",
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

@Composable
private fun ReviewMapMarker(
    group: ReviewLocationGroup,
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val authorImage = group.reviews.firstNotNullOfOrNull { review ->
        review.fotoAutor?.takeIf { it.isNotBlank() }
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(if (isDarkMode) Color(0xFF1F3553) else Color.White)
            .border(2.dp, if (isDarkMode) Color(0xFF8BB8FF) else Color(0xFF315FA5), RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (authorImage == null) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                contentDescription = "Reseña de materia",
                tint = if (isDarkMode) Color(0xFFFFD166) else Color(0xFF315FA5),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .size(25.dp)
            )
        } else {
            ProfileAsyncImage(
                profileImage = authorImage,
                size = 34,
                modifier = Modifier.border(1.dp, Color.White, CircleShape)
            )
        }
        if (group.reviews.size > 1) {
            Text(
                text = "${group.reviews.size}",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(Color(0xFFE85D75))
                    .padding(horizontal = 5.dp, vertical = 1.dp),
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun MapControlButton(
    icon: ImageVector,
    label: String,
    isDarkMode: Boolean,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = panelColor(isDarkMode),
        shadowElevation = 6.dp
    ) {
        IconButton(onClick = onClick, enabled = !isLoading, modifier = Modifier.size(48.dp)) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = if (isDarkMode) Color.White else Color(0xFF315FA5),
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (isDarkMode) Color.White else Color(0xFF315FA5)
                )
            }
        }
    }
}

@Composable
private fun ReviewMapCarousel(
    reviews: List<Review>,
    isDarkMode: Boolean,
    onDismiss: () -> Unit,
    onOpenReview: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { reviews.size })
    Surface(
        modifier = modifier,
        color = panelColor(isDarkMode),
        contentColor = panelTextColor(isDarkMode),
        shape = RoundedCornerShape(22.dp),
        shadowElevation = 12.dp
    ) {
        Column {
            HorizontalPager(
                state = pagerState,
                key = { index -> reviews[index].id }
            ) { page ->
                ReviewMapInfo(
                    review = reviews[page],
                    isDarkMode = isDarkMode,
                    onDismiss = onDismiss,
                    onOpenReview = { onOpenReview(reviews[page].id) }
                )
            }
            if (reviews.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Desliza para ver las reseñas de este lugar",
                        fontFamily = ArvoFont,
                        fontSize = 10.sp,
                        color = panelTextColor(isDarkMode).copy(alpha = 0.7f)
                    )
                    Text(
                        text = "  ${pagerState.currentPage + 1}/${reviews.size}",
                        fontFamily = BangersFont,
                        fontSize = 14.sp,
                        color = if (isDarkMode) Color(0xFFFFD166) else Color(0xFF315FA5)
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewMapInfo(
    review: Review,
    isDarkMode: Boolean,
    onDismiss: () -> Unit,
    onOpenReview: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
                .background(REVIEW_HEADER_BLUE)
                .padding(horizontal = 14.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAsyncImage(
                profileImage = review.fotoAutor,
                size = 42,
                modifier = Modifier.border(2.dp, Color.White, CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = review.nombreEstudiante,
                    color = Color.White,
                    fontFamily = BangersFont,
                    fontSize = 18.sp
                )
                Text(
                    text = review.nombreAsignatura,
                    color = Color.White.copy(alpha = 0.85f),
                    fontFamily = ArvoFont,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                Text("×", color = Color.White, fontSize = 26.sp)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                    color = panelTextColor(isDarkMode),
                    fontFamily = ArvoFont,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(text = generarEstrellas(review.calificacion), fontSize = 15.sp)
            }
            Text(
                text = review.descripcion,
                color = panelTextColor(isDarkMode),
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

@Composable
private fun MapMessage(
    text: String,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = panelColor(isDarkMode),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 5.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(14.dp),
            color = if (isError) Color(0xFFBA1A1A) else panelTextColor(isDarkMode),
            fontFamily = ArvoFont,
            fontSize = 12.sp
        )
    }
}

private fun panelColor(isDarkMode: Boolean) =
    if (isDarkMode) Color(0xFF182333) else primaryLight

private fun panelTextColor(isDarkMode: Boolean) =
    if (isDarkMode) Color(0xFFE8EEF8) else tertiaryContainerLight
