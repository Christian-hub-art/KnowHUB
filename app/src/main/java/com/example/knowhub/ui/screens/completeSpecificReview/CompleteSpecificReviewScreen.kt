package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.Comment as CommentData
import com.example.knowhub.data.Review as ReviewData
import com.example.knowhub.ui.screens.completeSpecificReview.components.Comment
import com.example.knowhub.ui.screens.completeSpecificReview.components.Review as ReviewComponent
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun CompleteSpecificReviewScreen(reviewId: String, completeSpecificReviewViewModel: CompleteSpecificReviewViewModel, modifier: Modifier = Modifier) {
    val state by completeSpecificReviewViewModel.uiState.collectAsState()
    LaunchedEffect(reviewId) { completeSpecificReviewViewModel.loadData(reviewId) }
    Box(modifier) {
        BackgroundImage()
        BodyCompleteSpecificReviewScreen(state.review, state.comments, state.isLoading, state.error)
    }
}

@Composable
fun BodyCompleteSpecificReviewScreen(review: ReviewData, comments: List<CommentData>, isLoading: Boolean = false, error: String? = null, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(vertical = 28.dp)) {
        item {
            ReviewComponent(review, Modifier.width(350.dp))
            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.background(tertiaryContainerLight).height(2.dp).width(65.dp))
                Spacer(Modifier.width(10.dp))
                Text(stringResource(R.string.rese_as), fontSize = 18.sp, fontFamily = BangersFont)
                Spacer(Modifier.width(10.dp))
                Box(Modifier.background(tertiaryContainerLight).height(2.dp).width(65.dp))
            }
            Spacer(Modifier.height(20.dp))
            if (isLoading) CircularProgressIndicator()
            error?.let { Text(it, Modifier.padding(16.dp), color = tertiaryContainerLight) }
            if (!isLoading && error == null && comments.isEmpty()) Text("Todavía no hay comentarios.", Modifier.padding(16.dp))
        }
        items(comments.size) { index ->
            val comment = comments[index]
            Comment(comment.fecha, comment.estudiante, comment.comentario, comment.likes, comment.cantidadComentarios, Modifier.width(350.dp))
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable @Preview(showBackground = true)
fun CompleteSpecificReviewScreenPreview() {
    CompleteSpecificReviewScreen("5", viewModel())
}

