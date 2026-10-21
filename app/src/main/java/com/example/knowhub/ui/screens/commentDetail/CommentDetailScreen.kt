package com.example.knowhub.ui.screens.commentDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.completeSpecificReview.components.Comment
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.FormTextField

@Composable
fun CommentDetailScreen(
    commentId: String,
    commentDetailViewModel: CommentDetailViewModel,
    modifier: Modifier = Modifier,
    commentPressed: (String) -> Unit = {}
) {
    val state by commentDetailViewModel.uiState.collectAsState()

    LaunchedEffect(commentId) {
        commentDetailViewModel.loadData(commentId)
    }

    Box(modifier = modifier) {
        BackgroundImage()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(35.dp))
                if (state.mostrarMensajeError) {
                    Text(state.errorMessage)
                }
                val comment = state.comment
                if (comment != null) {
                    Comment(
                        Fecha = comment.fecha,
                        Estudiante = comment.estudiante,
                        Comentario = comment.comentario,
                        Likes = comment.likes,
                        cantidadComentarios = comment.cantidadComentarios,
                        modifier = Modifier.width(350.dp),
                        mostrarEliminar = comment.userId.isNotEmpty() && comment.userId == state.currentUserId,
                        onEliminarClick = { commentDetailViewModel.deleteComment(comment.id, commentId) },
                        liked = comment.liked,
                        onLikeClick = { commentDetailViewModel.sendOrDeleteCommentLike(comment.id) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier.width(350.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        FormTextField(
                            value = state.nuevoComentario,
                            onValueChange = { commentDetailViewModel.updateNuevoComentario(it) },
                            placeholder = stringResource(R.string.escribe_tu_respuesta)
                        )
                        if (state.mostrarMensajeErrorComentario) {
                            Text(state.errorMessageComentario)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        AppButton(
                            stringResource(R.string.responder),
                            primaryLight,
                            primaryContainerLight,
                            onClick = { commentDetailViewModel.responderButtonPressed(commentId) },
                            modifier = Modifier
                                .height(40.dp)
                                .width(150.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(tertiaryContainerLight)
                            .height(2.5.dp)
                            .width(120.dp)
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
                            .width(120.dp)
                    ) {}
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(state.replies) { reply ->
                Comment(
                    Fecha = reply.fecha,
                    Estudiante = reply.estudiante,
                    Comentario = reply.comentario,
                    Likes = reply.likes,
                    cantidadComentarios = reply.cantidadComentarios,
                    modifier = Modifier.width(350.dp),
                    onClick = { commentPressed(reply.id) },
                    mostrarEliminar = reply.userId.isNotEmpty() && reply.userId == state.currentUserId,
                    onEliminarClick = { commentDetailViewModel.deleteComment(reply.id, commentId) },
                    liked = reply.liked,
                    onLikeClick = { commentDetailViewModel.sendOrDeleteCommentLike(reply.id) }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
