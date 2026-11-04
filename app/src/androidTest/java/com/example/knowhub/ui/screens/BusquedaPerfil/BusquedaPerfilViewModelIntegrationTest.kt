package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de integración reales para BusquedaPerfilViewModel.
 * Conecta el ViewModel con las instancias reales de UserRepository, ReviewRepository y Firestore.
 * SIN USO DE MOCKS (MockK).
 */
@RunWith(AndroidJUnit4::class)
class BusquedaPerfilViewModelIntegrationTest {

    private val db = Firebase.firestore
    private lateinit var userRepository: UserRepository
    private lateinit var reviewRepository: ReviewRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: BusquedaPerfilViewModel

    @Before
    fun setup() {
        runBlocking {
            // Instancias reales de Fuentes de Datos y Repositorios
            val reviewDataSource = ReviewFirestoreDataSourceImpl(db)
            val userDataSource = UserFirestoreDataSourceImpl(db)
            val authDataSource = AuthRemoteDataSource(FirebaseAuth.getInstance())
            val commentDataSource = CommentFirestoreDataSourceImpl(db)

            userRepository = UserRepository(userDataSource, authDataSource)
            reviewRepository = ReviewRepository(reviewDataSource, userDataSource, authDataSource, commentDataSource)
            authRepository = AuthRepository(authDataSource)

            // Instancia real del ViewModel integrado con los repositorios reales
            viewModel = BusquedaPerfilViewModel(userRepository, reviewRepository, authRepository)

            // Insertar datos reales de prueba en Firestore
            val userDto = UserProfileDto(
                id = "user_integration_100",
                userName = "Santiago",
                name = "Santiago Moreno",
                followersCount = 10,
                followingCount = 5
            )
            db.collection("users").document(userDto.id).set(userDto).await()

            val reviewDto = ReviewDto(
                id = "review_integration_100",
                userId = "user_integration_100",
                clase = "MAT101",
                nombreAsignatura = "Cálculo I",
                nombreProfesor = "Laura Gómez",
                descripcion = "Excelente clase integrativa",
                calificacion = 5,
                user = ReviewUserDto(name = "Santiago Moreno", userName = "Santiago")
            )
            db.collection("reviews").document(reviewDto.id).set(reviewDto).await()
        }
    }

    @After
    fun tearDown() {
        runBlocking {
            try {
                db.collection("users").document("user_integration_100").delete().await()
                db.collection("reviews").document("review_integration_100").delete().await()
            } catch (e: Exception) {
                // Ignorar errores en limpieza
            }
        }
    }

    @Test
    fun getUserProfile_integracionConRepositorioRealYFirestore_actualizaEstadoExitosamente() = runBlocking {
        withContext(Dispatchers.Main) {
            // Ejecución real: El ViewModel consulta el UserRepository real que lee desde Firestore
            viewModel.getUserProfile("user_integration_100")
        }

        // Dar un breve tiempo para procesar la respuesta de Firestore
        delay(1000)

        // Verificación de integración en el estado del ViewModel
        val state = viewModel.uiState.value
        assertThat(state.userName).isEqualTo("Santiago")
        assertThat(state.seguidores).isEqualTo(10)
        assertThat(state.seguidos).isEqualTo(5)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun getUserReviews_integracionConRepositorioRealYFirestore_actualizaEstadoConResenasReales() = runBlocking {
        withContext(Dispatchers.Main) {
            // Ejecución real: El ViewModel consulta el ReviewRepository real que lee desde Firestore
            viewModel.getUserReviews("user_integration_100")
        }

        // Dar un breve tiempo para procesar la respuesta de Firestore
        delay(1000)

        // Verificación de integración en el estado del ViewModel
        val state = viewModel.uiState.value
        assertThat(state.reviews).isNotEmpty()
        assertThat(state.reviews[0].nombreAsignatura).isEqualTo("Cálculo I")
        assertThat(state.reviews[0].nombreProfesor).isEqualTo("Laura Gómez")
    }
}
