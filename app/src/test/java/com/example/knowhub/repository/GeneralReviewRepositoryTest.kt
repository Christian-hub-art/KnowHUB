package com.example.knowhub.repository

import com.example.knowhub.data.datasource.dtos.GeneralReviewDto
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.GeneralReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.repository.GeneralReviewRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GeneralReviewRepositoryTest {

    private val generalReviewRemoteDataSource: GeneralReviewFirestoreDataSourceImpl = mockk()
    private val reviewRemoteDataSource: ReviewFirestoreDataSourceImpl = mockk()
    private val commentRemoteDataSource: CommentFirestoreDataSourceImpl = mockk()

    private lateinit var generalReviewRepository: GeneralReviewRepository

    @Before
    fun setUp() {
        generalReviewRepository = GeneralReviewRepository(
            generalReviewRemoteDataSource = generalReviewRemoteDataSource,
            reviewRemoteDataSource = reviewRemoteDataSource,
            commentRemoteDataSource = commentRemoteDataSource
        )
    }

    @Test
    fun `al obtener todas las materias generales se mapean a objetos GeneralReview`() = runTest {
        val generalReviewDtos = listOf(
            GeneralReviewDto(
                id = 50,
                nombreMateria = "Programación Móvil",
                nombreProfesor = "Ing. Rodriguez",
                codigoAsignatura = 5050
            )
        )
        coEvery { generalReviewRemoteDataSource.getAllGeneralReviews() } returns generalReviewDtos

        val result = generalReviewRepository.getGeneralReviews()

        Truth.assertThat(result.isSuccess).isTrue()
        val item = result.getOrNull()?.firstOrNull()
        Truth.assertThat(item?.id).isEqualTo(50)
        Truth.assertThat(item?.nombreMateria).isEqualTo("Programación Móvil")
        Truth.assertThat(item?.codigoAsignatura).isEqualTo(5050)
    }

    @Test
    fun `al obtener una materia por su codigo se realiza el mapeo a GeneralReview`() = runTest {
        val dto = GeneralReviewDto(
            id = 101,
            nombreMateria = "Estructuras de Datos",
            codigoAsignatura = 2020
        )
        coEvery { generalReviewRemoteDataSource.getGeneralReviewByCodigo(2020) } returns dto

        val result = generalReviewRepository.getGeneralReviewByCodigo(2020)

        Truth.assertThat(result.isSuccess).isTrue()
        val generalReview = result.getOrNull()
        Truth.assertThat(generalReview?.nombreMateria).isEqualTo("Estructuras de Datos")
        Truth.assertThat(generalReview?.codigoAsignatura).isEqualTo(2020)
    }
}
