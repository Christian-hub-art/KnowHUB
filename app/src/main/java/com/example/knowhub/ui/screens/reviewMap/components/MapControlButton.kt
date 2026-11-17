package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

//Botón redondo de los controles del mapa (acercar, alejar y ubicarme).
@Composable
fun MapControlButton(
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
