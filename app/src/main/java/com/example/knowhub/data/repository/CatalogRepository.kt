package com.example.knowhub.data.repository

import com.example.knowhub.data.Comment
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Review
import com.example.knowhub.data.datasource.CatalogRemoteDataSource
import com.example.knowhub.data.remote.toComment
import com.example.knowhub.data.remote.toReview
import com.example.knowhub.data.remote.toUiModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

class CatalogRepository @Inject constructor(
    private val remote: CatalogRemoteDataSource
) {
    suspend fun asignaturas(): Result<List<GeneralReview>> = try {
        val materias = remote.asignaturas()
        val reviews = remote.reviews()
        Result.success(
            materias.map { materia ->
                materia.toUiModel(reviews.filter { it.idAsignatura == materia.idAsignatura })
            }
        )
    } catch (error: CancellationException) {
        throw error
    } catch (error: HttpException) {
        Result.failure(RepositoryException("Error HTTP ${error.code()}: ${error.message()}"))
    } catch (error: IOException) {
        Result.failure(RepositoryException(error.message ?: "No fue posible conectar con el backend"))
    } catch (error: Exception) {
        Result.failure(RepositoryException(error.message ?: "Ocurrió un error al consultar el catálogo"))
    }

    suspend fun asignaturaConResenas(id: String): Result<Pair<GeneralReview, List<Review>>> = try {
        val materia = remote.asignatura(id)
        val reviews = remote.reviewsDeAsignatura(id)
        val reviewsCalificadas = reviews.filter { it.calificacion != null }
        val reviewsUi = reviewsCalificadas.map { review ->
            val comentarios = remote.respuestas(review.idReview.toString())
            review.toReview(comentarios.size)
        }
        Result.success(materia.toUiModel(reviews) to reviewsUi)
    } catch (error: CancellationException) {
        throw error
    } catch (error: HttpException) {
        Result.failure(RepositoryException("Error HTTP ${error.code()}: ${error.message()}"))
    } catch (error: IOException) {
        Result.failure(RepositoryException(error.message ?: "No fue posible conectar con el backend"))
    } catch (error: Exception) {
        Result.failure(RepositoryException(error.message ?: "Ocurrió un error al cargar la asignatura"))
    }

    suspend fun reviewConComentarios(id: String): Result<Pair<Review, List<Comment>>> = try {
        val review = remote.review(id)
        val comentarios = remote.respuestas(id)
        Result.success(review.toReview(comentarios.size) to comentarios.map { it.toComment() })
    } catch (error: CancellationException) {
        throw error
    } catch (error: HttpException) {
        Result.failure(RepositoryException("Error HTTP ${error.code()}: ${error.message()}"))
    } catch (error: IOException) {
        Result.failure(RepositoryException(error.message ?: "No fue posible conectar con el backend"))
    } catch (error: Exception) {
        Result.failure(RepositoryException(error.message ?: "Ocurrió un error al cargar los comentarios"))
    }
}

private class RepositoryException(message: String) : Exception(message)
