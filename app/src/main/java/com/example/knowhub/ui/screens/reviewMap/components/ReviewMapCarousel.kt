package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont

//Tarjetas deslizables con las reseñas del marcador seleccionado.
@Composable
fun ReviewMapCarousel(
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
                        text = stringResource(R.string.mapa_desliza),
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
