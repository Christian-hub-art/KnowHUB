package com.example.knowhub.repository

import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CommentDto
import com.example.knowhub.data.datasource.dtos.ReviewUserDto
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.repository.CommentRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CommentRepositoryTest {

    private val commentRemoteDataSource: CommentFirestoreDataSourceImpl = mockk()
    private val userRemoteDataSource: UserFirestoreDataSourceImpl = mockk()
    private val authRemoteDataSource: AuthRemoteDataSource = mockk()

    private lateinit var commentRepository: CommentRepository

    @Before
    fun setUp() {
        commentRepository = CommentRepository(
            commentRemoteDataSource = commentRemoteDataSource,
            userRemoteDataSource = userRemoteDataSource,
            authRemoteDataSource = authRemoteDataSource
        )
    }

    @Test
    fun `al obtener comentarios por reseña se mapean los elementos de CommentDto a Comment`() = runTest {
        coEvery { authRemoteDataSource.currentUser?.uid } returns "1"
        val commentDtos = listOf(
            CommentDto(
                id = "c100",
                reviewId = "rev1",
                comentario = "Excelente aporte al tema",
                user = ReviewUserDto(userName = "anag")
            )
        )
        coEvery { commentRemoteDataSource.getCommentsByReview("rev1", "1") } returns commentDtos

        val result = commentRepository.getCommentsByReview("rev1")

        Truth.assertThat(result.isSuccess).isTrue()
        val comment = result.getOrNull()?.firstOrNull()
        Truth.assertThat(comment?.id).isEqualTo("c100")
        Truth.assertThat(comment?.comentario).isEqualTo("Excelente aporte al tema")
        Truth.assertThat(comment?.estudiante).isEqualTo("anag")
    }

    @Test
    fun `al obtener un comentario por id se mapea a la entidad Comment`() = runTest {
        coEvery { authRemoteDataSource.currentUser?.uid } returns "1"
        val commentDto = CommentDto(
            id = "c200",
            reviewId = "rev1",
            comentario = "Comentario individual",
            user = ReviewUserDto(userName = "pedro1")
        )
        coEvery { commentRemoteDataSource.getCommentById("c200", "1") } returns commentDto

        val result = commentRepository.getCommentById("c200")

        Truth.assertThat(result.isSuccess).isTrue()
        val comment = result.getOrNull()
        Truth.assertThat(comment?.id).isEqualTo("c200")
        Truth.assertThat(comment?.comentario).isEqualTo("Comentario individual")
    }

    @Test
    fun `al obtener las respuestas de un comentario se mapean las respuestas a objetos Comment`() = runTest {
        coEvery { authRemoteDataSource.currentUser?.uid } returns "1"
        val replies = listOf(
            CommentDto(
                id = "c201",
                parentCommentId = "c200",
                comentario = "Respuesta simulada",
                user = ReviewUserDto(userName = "luisg")
            )
        )
        coEvery { commentRemoteDataSource.getReplies("c200", "1") } returns replies

        val result = commentRepository.getReplies("c200")

        Truth.assertThat(result.isSuccess).isTrue()
        val list = result.getOrNull()
        Truth.assertThat(list).hasSize(1)
        Truth.assertThat(list?.first()?.comentario).isEqualTo("Respuesta simulada")
    }
}
