package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.ui.graphics.Color
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight

// Colores compartidos por los paneles del mapa en modo claro y oscuro
val REVIEW_HEADER_BLUE = Color(0xFF0252E7)

fun panelColor(isDarkMode: Boolean) =
    if (isDarkMode) Color(0xFF182333) else primaryLight

fun panelTextColor(isDarkMode: Boolean) =
    if (isDarkMode) Color(0xFFE8EEF8) else tertiaryContainerLight
