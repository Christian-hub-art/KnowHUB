package com.example.knowhub.ui.Navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.knowhub.ui.screens.BusquedaFiltro.BusquedaScreen
import com.example.knowhub.ui.screens.BusquedaPerfil.BusquedaPerfilScreen
import com.example.knowhub.ui.screens.BusquedaPerfil.BusquedaPerfilViewModel
import com.example.knowhub.ui.screens.CreateReviews.CreateReviewsScreen
import com.example.knowhub.ui.screens.CreateReviews.CreateReviewsViewModel
import com.example.knowhub.ui.screens.Splash.SplashScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsViewModel
import com.example.knowhub.ui.screens.completeSpecificReview.CompleteSpecificReviewScreen
import com.example.knowhub.ui.screens.completeSpecificReview.CompleteSpecificReviewViewModel
import com.example.knowhub.ui.screens.inicio.InicioScreen
import com.example.knowhub.ui.screens.inicio.InicioViewModel
import com.example.knowhub.ui.screens.login.LoginScreen
import com.example.knowhub.ui.screens.login.LoginViewModel
import com.example.knowhub.ui.screens.notifications.NotificatonsScreen
import com.example.knowhub.ui.screens.profile.ProfileScreen
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.screens.register.RegisterViewModel
import com.example.knowhub.ui.screens.reviews.ReviewScreen

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object Start : Screens("start")
    object Register : Screens("register")

    // Se configura con un parámetro opcional "semestre"
    object Busqueda : Screens("busqueda?semestre={semestre}") {
        fun createRoute(semestre: String = "") = "busqueda?semestre=$semestre"
    }

    object CompleteReviews : Screens("completeReviews/{generalReviewId}") {
        fun createRoute(id: String) = "completeReviews/$id"
    }
    object CreateReviews : Screens("createReviews")
    object Notifications : Screens("notifications")
    object Profile : Screens("profile")
    object Reviews : Screens("reviews")
    object Inicio : Screens("inicio")
    object BusquePerfil : Screens("busquedaPerfil")

    object CompleteSpecificReview : Screens("completeSpecificReviews/{reviewId}") {
        fun createRoute(id: String) = "completeSpecificReviews/$id"
    }
}

@Composable
fun AppNavegation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
        modifier = modifier
    ) {
        composable(route = Screens.Start.route){
            val loginViewModel: LoginViewModel = hiltViewModel()
            val state by loginViewModel.uiState.collectAsState()
            if(state.navigateInicio){
                navController.navigate(Screens.Inicio.route){
                    popUpTo(0)
                }
            }
            if(state.navigateRegister){
                navController.navigate(Screens.Register.route)
            }
            if(state.navigateContinuar){
                navController.navigate(Screens.Inicio.route){
                    popUpTo(0)
                }
            }
            LoginScreen(
                loginViewModel = loginViewModel
            )
        }

        composable (route = Screens.Splash.route){
            SplashScreen(
                navigateToHome = {
                    navController.navigate(Screens.Inicio.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                navigateToStart = {
                    navController.navigate(Screens.Start.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                splashViewModel = hiltViewModel()
            )
        }

        composable(route = Screens.Register.route) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            val state by registerViewModel.uiState.collectAsState()
            if (state.navigateInicio) {
                navController.navigate(Screens.Inicio.route) {
                    popUpTo(0)
                }
            }
            if(state.navigateLogin){
                navController.navigate(Screens.Start.route)
            }
            RegisterScreen(
                registerViewModel = registerViewModel
            )
        }

        // Definición de la ruta de Búsqueda soportando el argumento del semestre
        composable(
            route = Screens.Busqueda.route,
            arguments = listOf(
                navArgument("semestre") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) {
            BusquedaScreen(
                generalReviewPressed = { generalReviewId ->
                    navController.navigate(Screens.CompleteReviews.createRoute(generalReviewId))
                }
            )
        }

        composable ( route = Screens.CompleteReviews.route,
            arguments = listOf(navArgument("generalReviewId"){type = NavType.StringType})
        ){
            val generalReviewId = it.arguments?.getString("generalReviewId") ?: ""
            val viewModel: CompleteReviewsViewModel = hiltViewModel()

            CompleteReviewsScreen(
                generalReviewId = generalReviewId,
                completeReviewsViewModel = viewModel,
                reviewPressed = { reviewId ->
                    navController.navigate(Screens.CompleteSpecificReview.createRoute(reviewId))
                },
                escribirBottonPressed = {
                    navController.navigate(Screens.CreateReviews.route)
                }
            )
        }

        composable ( route = Screens.CreateReviews.route ){
            val viewModel: CreateReviewsViewModel = hiltViewModel()
            CreateReviewsScreen(
                createReviewsViewModel = viewModel
            )
        }

        composable ( route = Screens.Notifications.route ){
            NotificatonsScreen()
        }

        composable ( route = Screens.Profile.route ){
            ProfileScreen()
        }

        composable ( route = Screens.Reviews.route ){
            ReviewScreen()
        }

        composable ( route = Screens.Inicio.route ){
            val viewModel: InicioViewModel = hiltViewModel()
            InicioScreen(
                inicioViewModel = viewModel,
                onSeeAllClick = { semestre ->
                    navController.navigate(Screens.Busqueda.createRoute(semestre))
                },
                onMateriaClick = { id -> navController.navigate(Screens.CompleteReviews.createRoute(id)) }
            )
        }

        composable ( route = Screens.CompleteSpecificReview.route,
            arguments = listOf(navArgument("reviewId"){type = NavType.StringType})
        ){
            val reviewId = it.arguments?.getString("reviewId") ?: ""
            val viewModel: CompleteSpecificReviewViewModel = hiltViewModel()
            CompleteSpecificReviewScreen(
                reviewId = reviewId,
                completeSpecificReviewViewModel = viewModel
            )
        }

        composable(route = Screens.BusquePerfil.route) {

            val busquedaPerfilViewModel: BusquedaPerfilViewModel = hiltViewModel()

            BusquedaPerfilScreen(
                userId = "1",
                busquedaPerfilViewModel = busquedaPerfilViewModel
            )
        }
    }
}