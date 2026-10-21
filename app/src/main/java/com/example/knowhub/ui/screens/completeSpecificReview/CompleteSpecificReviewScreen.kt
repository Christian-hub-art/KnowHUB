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
import com.example.knowhub.data.Review
import com.example.knowhub.ui.screens.completeSpecificReview.components.Comment
import com.example.knowhub.ui.screens.completeSpecificReview.components.Review as ReviewComponent
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.FormTextField

@Composable
fun CompleteSpecificReviewScreen(
    reviewId: String,
    completeSpecificReviewViewModel: CompleteSpecificReviewViewModel,
    modifier: Modifier = Modifier,
    autorPressed: (String) -> Unit = {},
    commentPressed: (String) -> Unit = {}
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
            review = state.review,
            comments = state.comments,
            errorMessage = state.errorMessage,
            mostrarMensajeError = state.mostrarMensajeError,
            nuevoComentario = state.nuevoComentario,
            errorMessageComentario = state.errorMessageComentario,
            mostrarMensajeErrorComentario = state.mostrarMensajeErrorComentario,
            onNuevoComentarioChange = { completeSpecificReviewViewModel.updateNuevoComentario(it) },
            responderButtonPressed = { completeSpecificReviewViewModel.responderButtonPressed(reviewId) },
            autorPressed = autorPressed,
            currentUserId = state.currentUserId,
            commentPressed = commentPressed,
            eliminarReviewPressed = { completeSpecificReviewViewModel.deleteReview(reviewId) },
            onLikeReview = { id -> completeSpecificReviewViewModel.toggleReviewLike(id) },
            isUpdatingReviewLike = state.isUpdatingReviewLike,
            eliminarCommentPressed = { commentId -> completeSpecificReviewViewModel.deleteComment(commentId, reviewId) },
            onCommentLikeClick = { commentId ->
                completeSpecificReviewViewModel.toggleCommentLike(commentId, reviewId)
            },
            isUpdatingCommentLike = state.isUpdatingCommentLike
        )
    }

}

@Composable
fun BodyCompleteSpecificReviewScreen(
    review: Review?,
    comments: List<CommentData>,
    errorMessage: String = "",
    mostrarMensajeError: Boolean = false,
    nuevoComentario: String = "",
    errorMessageComentario: String = "",
    mostrarMensajeErrorComentario: Boolean = false,
    onNuevoComentarioChange: (String) -> Unit = {},
    responderButtonPressed: () -> Unit = {},
    modifier: Modifier = Modifier,
    autorPressed: (String) -> Unit = {},
    currentUserId: String = "",
    commentPressed: (String) -> Unit = {},
    onLikeReview: (String) -> Unit = {},
    isUpdatingReviewLike: Boolean = false,
    eliminarReviewPressed: () -> Unit = {},
    eliminarCommentPressed: (String) -> Unit = {},
    onCommentLikeClick: (String) -> Unit = {},
    isUpdatingCommentLike: Boolean = false
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {

                Spacer(modifier = Modifier.height(35.dp))
                if (mostrarMensajeError) {
                    Text(errorMessage)
                }
                if (review != null) {
                    ReviewComponent(
                        review,
                        Modifier.width(350.dp),
                        autorPressed = autorPressed,
                        mostrarEliminar = review.userId.isNotEmpty() && review.userId == currentUserId,
                        onEliminarClick = eliminarReviewPressed,
                        onLikeClick = onLikeReview,
                        isUpdatingLike = isUpdatingReviewLike
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier.width(350.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        FormTextField(
                            value = nuevoComentario,
                            onValueChange = onNuevoComentarioChange,
                            placeholder = stringResource(R.string.escribe_tu_respuesta)
                        )
                        if (mostrarMensajeErrorComentario) {
                            Text(errorMessageComentario)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        AppButton(
                            stringResource(R.string.responder),
                            primaryLight,
                            primaryContainerLight,
                            onClick = responderButtonPressed,
                            modifier = Modifier
                                .height(40.dp)
                                .width(150.dp)
                        )
                    }
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
                        text = stringResource(R.string.respuestas),
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
                            cantidadComentarios = comment.cantidadComentarios,
                            onClick = { commentPressed(comment.id) },
                            mostrarEliminar = comment.userId.isNotEmpty() && comment.userId == currentUserId,
                            onEliminarClick = { eliminarCommentPressed(comment.id) },
                            likedByCurrentUser = comment.likedByCurrentUser,
                            likeEnabled = !isUpdatingCommentLike,
                            onLikeClick = { onCommentLikeClick(comment.id) }
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
        reviewId = "",
        completeSpecificReviewViewModel = viewModel()
    )
}
