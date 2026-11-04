package com.example.knowhub.e2e

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.semantics.SemanticsProperties
import com.example.knowhub.MainActivity
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.GeneralReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.GeneralReviewRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginAndFollowFlowE2ETest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    // Declaración lateinit de Repositorios
    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var reviewRepository: ReviewRepository
    private lateinit var generalReviewRepository: GeneralReviewRepository
    private val fixtureId = 900001
    private val fixtureMateria = "Cálculo I"
    private val fixtureReview = "Las clases son claras y los ejemplos ayudan a entender cada tema."
    private val loginEmail = "e2e.login@knowhub.com"
    private val targetEmail = "e2e.target@knowhub.com"
    private val loginUsername = "sofia_herrera"
    private val targetUsername = "andres_molina"
    private val testPassword = "123456"
    private var fixtureReviewId: String? = null

    @Before
    fun setUp() = runBlocking {
        hiltRule.inject()

        // Inicialización manual de DataSources
        val authRemoteDataSource = AuthRemoteDataSource(FirebaseAuth.getInstance())
        val userRemoteDataSource = UserFirestoreDataSourceImpl(Firebase.firestore)
        val reviewRemoteDataSource = ReviewFirestoreDataSourceImpl(Firebase.firestore)
        val commentRemoteDataSource = CommentFirestoreDataSourceImpl(Firebase.firestore)
        val generalReviewRemoteDataSource = GeneralReviewFirestoreDataSourceImpl(Firebase.firestore)

        // Inicialización de Repositorios con sus DataSources
        authRepository = AuthRepository(authRemoteDataSource)
        userRepository = UserRepository(userRemoteDataSource, authRemoteDataSource)
        reviewRepository = ReviewRepository(reviewRemoteDataSource, userRemoteDataSource, authRemoteDataSource, commentRemoteDataSource)
        generalReviewRepository = GeneralReviewRepository(generalReviewRemoteDataSource, reviewRemoteDataSource, commentRemoteDataSource)

        // PRECONDICIONES
        authRepository.signOut()

        generalReviewRepository.createGeneralReview(
            nombreMateria = fixtureMateria,
            nombreProfesor = "Ana Torres",
            codigoAsignatura = fixtureId,
            dificultadMedia = "Media",
            hashtags = listOf("#calculo")
        ).getOrThrow()
        authRepository.signUp(loginEmail, testPassword).getOrThrow()
        val currentUid = checkNotNull(authRepository.currentUser?.uid) {
            "No se pudo crear el usuario de prueba"
        }
        userRepository.registerUser("Sofía Herrera", loginUsername, currentUid).getOrThrow()
        authRepository.signOut()

        authRepository.signUp(targetEmail, testPassword).getOrThrow()
        val targetUid = checkNotNull(authRepository.currentUser?.uid) {
            "No se pudo crear el usuario destino de prueba"
        }
        userRepository.registerUser("Andrés Molina", targetUsername, targetUid).getOrThrow()
        reviewRepository.createReview(
            userId = targetUid,
            clase = "Grupo 02",
            nombreAsignatura = fixtureMateria,
            nombreProfesor = "Ana Torres",
            descripcion = fixtureReview,
            generalReviewId = fixtureId,
            calificacion = 5
        ).getOrThrow()
        fixtureReviewId = reviewRepository.getReviewsByUser(targetUid).getOrThrow()
            .single { it.descripcion == fixtureReview }.id
        authRepository.signOut()
    }

    @After
    fun tearDown() = runBlocking { clearFirebaseData() }

    @Test
    fun flujoLoginSeguirUsuarioYVerificarPublicacionesEnSeguidos() {
        // 1. Un usuario ya registrado realiza login en la aplicación
        waitForTag("loginEmailOrUserField", timeout = 10_000)

        composeRule.onNodeWithTag("loginEmailOrUserField").performTextInput(loginEmail)
        composeRule.onNodeWithTag("loginPasswordField")
            .performTextInput(testPassword)
        composeRule.onNodeWithTag("loginButton").performClick()

        // 2. Navegar desde Inicio -> Ver más -> Buscar -> Reseñas completas
        waitForTag("verMasButton", timeout = 15_000)
        composeRule.onAllNodesWithTag("verMasButton").onFirst().performClick()

        waitForTag("cajaBusquedaCard")
        composeRule.onNodeWithTag("cajaBusquedaCard").performClick()

        // 3. Ir al perfil de un usuario seleccionando la fila del autor de la reseña
        waitForTag("reviewAuthorRow")
        composeRule.onNodeWithTag("reviewAuthorRow").performClick()

        // 4. Verificar que la información del usuario sea correcta (Seguidores iniciales = 0)
        waitForTag("seguidoresMiniCuadro")
        composeRule.onNodeWithTag("seguidoresMiniCuadro")
            .assertTextContains(substring = true, value = "0")

        // 5. Dar Follow y verificar que aumenta la cantidad de seguidores a 1
        waitForTag("followButton")
        composeRule.onNodeWithTag("followButton").performClick()

        waitForText("seguidoresMiniCuadro", "1")

        // 6. El usuario vuelve al Home usando el drawer de navegación
        composeRule.onNodeWithTag("menuIconButton").performClick()
        waitForTag("drawerInicioOption")
        composeRule.onNodeWithTag("drawerInicioOption").performClick()

        // 7. Va a la sección de publicaciones de seguidos (Ver más -> Completa -> Pestaña Seguidos)
        waitForTag("verMasButton")
        composeRule.onAllNodesWithTag("verMasButton").onFirst().performClick()

        waitForTag("cajaBusquedaCard")
        composeRule.onNodeWithTag("cajaBusquedaCard").performClick()

        waitForTag("siguiendoTabButton")
        composeRule.onNodeWithTag("siguiendoTabButton").performClick()

        // 8. Verificar que aparezca al menos una publicación del usuario que acabó de seguir
        waitForTag("reviewCard")
        composeRule.onNodeWithTag("reviewDescriptionText", useUnmergedTree = true)
            .assertTextContains(substring = true, value = fixtureReview)
    }

    private fun waitForTag(tag: String, timeout: Long = 5_000) {
        composeRule.waitUntil(timeoutMillis = timeout) {
            composeRule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun waitForText(tag: String, text: String, timeout: Long = 5_000) {
        composeRule.waitUntil(timeoutMillis = timeout) {
            composeRule.onAllNodesWithTag(tag).fetchSemanticsNodes().any { node ->
                node.config.getOrElse(SemanticsProperties.Text) { emptyList() }
                    .any { text in it.text }
            }
        }
    }

    private suspend fun clearFirebaseData() {
        val db = Firebase.firestore
        val auth = Firebase.auth
        auth.signOut()

        fixtureReviewId?.let { reviewId ->
            val reviewRef = db.collection("reviews").document(reviewId)
            clearSubcollection(reviewRef, "likes")
            val comments = db.collection("comments")
                .whereEqualTo("reviewId", reviewId)
                .get()
                .await()
            for (comment in comments.documents) {
                clearSubcollection(comment.reference, "likes")
                comment.reference.delete().await()
            }
            reviewRef.delete().await()
        }

        db.collection("materias").document(fixtureId.toString()).delete().await()
        for (email in listOf(loginEmail, targetEmail)) {
            if (auth.fetchSignInMethodsForEmail(email).await().signInMethods.orEmpty().isNotEmpty()) {
                val user = auth.signInWithEmailAndPassword(email, testPassword).await().user
                user?.uid?.let { uid ->
                    val userRef = db.collection("users").document(uid)
                    for (subcollection in listOf("followers", "following", "notificaciones")) {
                        clearSubcollection(userRef, subcollection)
                    }
                    userRef.delete().await()
                }
                user?.delete()?.await()
            }
        }
        auth.signOut()
    }

    private suspend fun clearSubcollection(
        document: com.google.firebase.firestore.DocumentReference,
        subcollection: String
    ) {
        val documents = document.collection(subcollection).get().await()
        for (child in documents.documents) {
            child.reference.delete().await()
        }
    }
}
