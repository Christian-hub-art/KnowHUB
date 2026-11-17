package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.ui.utils.ProfileAsyncImage

// Reseñas publicadas en el mismo punto del mapa
data class ReviewLocationGroup(
    val latitude: Double,
    val longitude: Double,
    val reviews: List<Review>
)

//Marcador del mapa con la foto del autor y la cantidad de reseñas del lugar.
@Composable
fun ReviewMapMarker(
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
                contentDescription = stringResource(R.string.mapa_icono_resena),
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
