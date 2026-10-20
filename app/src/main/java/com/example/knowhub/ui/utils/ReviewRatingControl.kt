package com.example.knowhub.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.data.Review
import com.example.knowhub.ui.theme.BangersFont

@Composable
fun ReviewRatingControl(
    review: Review,
    onRate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        (1..5).forEach { rating ->
            Text(
                text = if (rating <= review.userRating) "★" else "☆",
                fontSize = 22.sp,
                fontFamily = BangersFont,
                modifier = Modifier
                    .clickable { onRate(rating) }
                    .semantics { contentDescription = "Calificar con $rating estrellas" }
                    .padding(horizontal = 1.dp)
            )
        }
        Text(
            text = " ${"%.1f".format(review.ratingAverage)} (${review.ratingCount})",
            fontSize = 14.sp,
            fontFamily = BangersFont
        )
    }
}
