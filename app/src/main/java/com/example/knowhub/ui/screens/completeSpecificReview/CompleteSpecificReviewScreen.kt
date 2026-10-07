package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowhub.R
import com.example.knowhub.data.Comment as CommentData
import com.example.knowhub.data.Review as ReviewData
import com.example.knowhub.ui.screens.completeSpecificReview.components.Comment
import com.example.knowhub.ui.screens.completeSpecificReview.components.Review as ReviewComponent
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun CompleteSpecificReviewScreen(
    reviewId: String,
    modifier: Modifier = Modifier,
    viewModel: CompleteSpecificReviewViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(reviewId) {
        viewModel.loadData(reviewId)
    }

    Box(modifier = modifier.fillMaxSize()) {
        BackgroundImage()
        BodyCompleteSpecificReviewScreen(
            review = state.review,
            comments = state.comments,
            isLoading = state.isLoading,
            error = state.error
        )
    }
}

@Composable
fun BodyCompleteSpecificReviewScreen(
    review: ReviewData,
    comments: List<CommentData>,
    isLoading: Boolean = false,
    error: String? = null,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 28.dp)
    ) {
        item {
            ReviewComponent(
                review = review,
                modifier = Modifier.width(350.dp)
            )

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .background(tertiaryContainerLight)
                        .height(2.dp)
                        .width(65.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.rese_as),
                    fontSize = 18.sp,
                    fontFamily = BangersFont
                )
                Spacer(Modifier.width(10.dp))
                Box(
                    Modifier
                        .background(tertiaryContainerLight)
                        .height(2.dp)
                        .width(65.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            if (isLoading) {
                CircularProgressIndicator(color = tertiaryContainerLight)
            }

            error?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp),
                    color = tertiaryContainerLight
                )
            }

            if (!isLoading && error == null && comments.isEmpty()) {
                Text(
                    text = "Todavía no hay comentarios.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        if (!isLoading && error == null) {
            items(
                items = comments,
                key = { comment -> comment.id }
            ) { comment ->
                Comment(
                    comment.fecha,
                    comment.estudiante,
                    comment.comentario,
                    comment.likes,
                    comment.cantidadComentarios,
                    Modifier.width(350.dp)
                )
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompleteSpecificReviewScreenPreview() {
    BodyCompleteSpecificReviewScreen(
        review = ReviewData(
            id = "1",
            nombreEstudiante = "Estudiante Demo",
            nombreProfesor = "Profesor Demo",
            nombreAsignatura = "Asignatura Demo",
            descripcion = "Descripción de prueba",
            fechaPublicacion = "2026-09-01",
            calificacion = 5,
            likes = 10,
            cantidadComentarios = 2
        ),
        comments = emptyList()
    )
}