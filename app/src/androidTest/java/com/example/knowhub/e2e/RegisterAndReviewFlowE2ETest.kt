package com.example.knowhub.e2e

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.semantics.SemanticsProperties
import com.example.knowhub.MainActivity
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.GeneralReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.CommentRepository
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
class RegisterAndReviewFlowE2ETest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    // Declaración lateinit de Repositorios
    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var reviewRepository: ReviewRepository
    private lateinit var commentRepository: CommentRepository
    private lateinit var generalReviewRepository: GeneralReviewRepository
    private val fixtureId = 900001
    private val fixtureMateria = "Cálculo I"
    private val fixtureReview = "Las clases son claras y los ejemplos ayudan a entender cada tema."
    private val setupEmail = "e2e.setup@knowhub.com"
    private val registerEmail = "e2e.register@knowhub.com"
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
        commentRepository = CommentRepository(commentRemoteDataSource, userRemoteDataSource, authRemoteDataSource)
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
        authRepository.signUp(setupEmail, testPassword).getOrThrow()
        val currentUid = checkNotNull(authRepository.currentUser?.uid) {
            "No se pudo crear el usuario de datos E2E"
        }
        userRepository.registerUser("Mateo Silva", "mateo_silva", currentUid).getOrThrow()
        reviewRepository.createReview(
            userId = currentUid,
            clase = "Grupo 02",
            nombreAsignatura = fixtureMateria,
            nombreProfesor = "Ana Torres",
            descripcion = fixtureReview,
            generalReviewId = fixtureId,
            calificacion = 5
        ).getOrThrow()

        fixtureReviewId = reviewRepository.getReviewsByUser(currentUid).getOrThrow()
            .single { it.descripcion == fixtureReview }.id
        commentRepository.createComment(
            reviewId = checkNotNull(fixtureReviewId),
            userId = currentUid,
            comentario = "La explicación del último tema fue muy útil."
        ).getOrThrow()
        authRepository.signOut()
    }

    @After
    fun tearDown() = runBlocking { clearFirebaseData() }

    @Test
    fun flujoRegistroErroneoYExitosoDetalleResenaYLikeComentario() {
        val nombreUser = "Daniela Ríos"
        val correoUser = registerEmail
        val usernameUser = "daniela_rios"

        // 1. Un usuario nuevo ingresa a la aplicación (esperar a que el Splash termine)
        waitForTag("goToRegisterButton", timeout = 10_000)

        // Ingresar al registro desde el Login
        composeRule.onNodeWithTag("goToRegisterButton").performClick()
        waitForTag("registrarseButton")

        // 2. Ingresar todos sus datos con contraseña 1234
        composeRule.onNodeWithTag("nombreRegisterField").performTextInput(nombreUser)
        composeRule.onNodeWithTag("correoRegisterField").performTextInput(correoUser)
        composeRule.onNodeWithTag("usernameRegisterField").performTextInput(usernameUser)
        composeRule.onNodeWithTag("passwordRegisterField").performTextInput("1234")
        composeRule.onNodeWithTag("confirmPasswordRegisterField").performTextInput("1234")

        // Intentar registrarse
        composeRule.onNodeWithTag("registrarseButton").performClick()

        // 3. Verificar el mensaje de error de registro
        waitForTag("registerErrorMessage")

        // 4. El usuario corrige la contraseña poniendo 123456 usando performTextClearance
        composeRule.onNodeWithTag("passwordRegisterField").performTextClearance()
        composeRule.onNodeWithTag("passwordRegisterField")
            .performTextInput(testPassword)
        composeRule.onNodeWithTag("confirmPasswordRegisterField").performTextClearance()
        composeRule.onNodeWithTag("confirmPasswordRegisterField")
            .performTextInput(testPassword)

        // Presionar registrarse e ingresar a la aplicación
        composeRule.onNodeWithTag("registrarseButton").performClick()

        // 5. El usuario ingresa a la primera publicación (Inicio -> Ver más -> Materia -> Reseña)
        waitForTag("verMasButton", timeout = 15_000)
        composeRule.onAllNodesWithTag("verMasButton").onFirst().performClick()

        waitForTag("cajaBusquedaCard")
        composeRule.onNodeWithTag("cajaBusquedaCard").performClick()

        waitForTag("reviewCard")
        composeRule.onNodeWithTag("reviewCard").performClick()

        // 6. Verificar que la información de detalle de la publicación sea correcta
        waitForTag("likeCommentButton")
        composeRule.onNodeWithTag("reviewProfessorText", useUnmergedTree = true)
            .assertTextContains(substring = true, value = "Ana Torres")
        composeRule.onNodeWithTag("reviewDescriptionText", useUnmergedTree = true)
            .assertTextContains(substring = true, value = fixtureReview)

        // 7. Verificar cantidad inicial de likes del comentario
        waitForText("commentLikesText", "0")

        // Dar Like al primer comentario y verificar que aumente la cantidad de likes
        composeRule.onAllNodesWithTag("likeCommentButton").onFirst().performClick()
        waitForText("commentLikesText", "1")

        // 8. El usuario vuelve usando la barra superior con el drawer de navegación
        composeRule.onNodeWithTag("menuIconButton").performClick()
        waitForTag("drawerInicioOption")
        composeRule.onNodeWithTag("drawerInicioOption").performClick()

        // Vuelve a seleccionar la publicación
        waitForTag("verMasButton")
        composeRule.onAllNodesWithTag("verMasButton").onFirst().performClick()

        waitForTag("cajaBusquedaCard")
        composeRule.onNodeWithTag("cajaBusquedaCard").performClick()

        waitForTag("reviewCard")
        composeRule.onNodeWithTag("reviewCard").performClick()

        // Quitar el Like y verificar que la cantidad de likes disminuya
        waitForTag("likeCommentButton")
        composeRule.onAllNodesWithTag("likeCommentButton").onFirst().performClick()
        waitForText("commentLikesText", "0")
    }

    private fun waitForTag(tag: String, timeout: Long = 5_000) {
        composeRule.waitUntil(timeoutMillis = timeout) {
            composeRule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun waitForText(tag: String, text: String, timeout: Long = 5_000) {
        composeRule.waitUntil(timeoutMillis = timeout) {
            composeRule.onAllNodesWithTag(tag, useUnmergedTree = true).fetchSemanticsNodes().any { node ->
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
        for (email in listOf(setupEmail, registerEmail)) {
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
