package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.Comment as CommentData
import com.example.knowhub.data.local.localReviewProvider
import com.example.knowhub.ui.screens.completeSpecificReview.components.Comment
import com.example.knowhub.ui.screens.completeSpecificReview.components.Review as ReviewComponent
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun CompleteSpecificReviewScreen(
    reviewId: Int,
    completeSpecificReviewViewModel: CompleteSpecificReviewViewModel,
    modifier: Modifier = Modifier
){
    val state by completeSpecificReviewViewModel.uiState.collectAsState()

    LaunchedEffect(reviewId) {
        completeSpecificReviewViewModel.loadData(reviewId)
    }

    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyCompleteSpecificReviewScreen(
            reviewId = reviewId,
            comments = state.comments
        )
    }

}

@Composable
fun BodyCompleteSpecificReviewScreen(
    reviewId: Int,
    comments: List<CommentData>,
    modifier: Modifier = Modifier
){
    val review = localReviewProvider.Reviews.find { it.id == reviewId }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {

                Spacer(modifier = Modifier.height(35.dp))
                if (review != null) {
                    ReviewComponent(
                        review,
                        Modifier.width(350.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row {
                    Box(
                        modifier = Modifier
                            .background(tertiaryContainerLight)
                            .height(2.5.dp)
                            .width(150.dp)
                    ) {}
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.rese_as),
                        fontSize = 17.sp,
                        fontFamily = BangersFont
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .background(tertiaryContainerLight)
                            .height(2.5.dp)
                            .width(150.dp)
                    ) {}
                }
                Spacer(modifier = Modifier.height(20.dp))

                Column(modifier = Modifier.width(350.dp)) {
                    comments.forEach { comment ->
                        Comment(
                            Fecha = comment.fecha,
                            Estudiante = comment.estudiante,
                            Comentario = comment.comentario,
                            Likes = comment.likes,
                            cantidadComentarios = comment.cantidadComentarios
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompleteSpecificReviewScreenPreview(){
    CompleteSpecificReviewScreen(
        reviewId = 5,
        completeSpecificReviewViewModel = viewModel()
    )
}
