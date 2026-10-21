package com.example.knowhub.ui.Navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.knowhub.ui.screens.BusquedaFiltro.BusquedaScreen
import com.example.knowhub.ui.screens.CreateReviews.CreateReviewsScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.completeSpecificReview.CompleteSpecificReviewScreen
import com.example.knowhub.ui.screens.commentDetail.CommentDetailScreen
import com.example.knowhub.ui.screens.commentDetail.CommentDetailViewModel
import com.example.knowhub.ui.screens.inicio.InicioScreen
import com.example.knowhub.ui.screens.login.LoginScreen
import com.example.knowhub.ui.screens.notifications.NotificatonsScreen
import com.example.knowhub.ui.screens.profile.ProfileScreen
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.screens.register.RegisterViewModel
import com.example.knowhub.ui.screens.reviews.ReviewScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowhub.ui.screens.BusquedaPerfil.BusquedaPerfilScreen
import com.example.knowhub.ui.screens.CreateReviews.CreateReviewsViewModel
import com.example.knowhub.ui.screens.Splash.SplashScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsViewModel
import com.example.knowhub.ui.screens.completeSpecificReview.CompleteSpecificReviewViewModel
import com.example.knowhub.ui.screens.inicio.InicioViewModel
import com.example.knowhub.ui.screens.login.LoginViewModel

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object Start : Screens("start")
    object Register : Screens("register")
    object Busqueda : Screens("busqueda")
    object CompleteReviews : Screens("completeReviews/{generalReviewId}") {
        fun createRoute(id: Int) = "completeReviews/$id"
    }
    object CreateReviews : Screens("createReviews")
    object CommentDetail : Screens("commentDetail/{commentId}") {
        fun createRoute(commentId: String) = "commentDetail/$commentId"
    }
    object EditReview : Screens("createReviews/{reviewId}") {
        fun createRoute(reviewId: String) = "createReviews/$reviewId"
    }
    object Notifications : Screens("notifications")
    object Profile : Screens("profile")
    object Reviews : Screens("reviews")
    object Inicio : Screens("inicio")
    object BusquePerfil : Screens("busquedaPerfil/{userId}") {
        fun createRoute(userId: String) = "busquedaPerfil/$userId"
    }
    object Connections : Screens("connections/{userId}/{followers}") {
        fun createRoute(userId: String, followers: Boolean) = "connections/$userId/$followers"
    }

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

        composable ( route = Screens.Busqueda.route ){
            BusquedaScreen(
                generalReviewPressed = { generalReviewId ->
                    navController.navigate(Screens.CompleteReviews.createRoute(generalReviewId))
                }
            )
        }
        composable ( route = Screens.CompleteReviews.route,
            arguments = listOf(navArgument("generalReviewId"){type = NavType.IntType})
        ){
            val generalReviewId = it.arguments?.getInt("generalReviewId") ?: 0
            val viewModel: CompleteReviewsViewModel = hiltViewModel()

           CompleteReviewsScreen(
                generalReviewId = generalReviewId,
                completeReviewsViewModel = viewModel,
               reviewPressed = { reviewId ->
                   navController.navigate(Screens.CompleteSpecificReview.createRoute(reviewId))
               },
                escribirBottonPressed = {
                    navController.navigate(Screens.CreateReviews.route)
                },
                autorPressed = { userId ->
                    navController.navigate(Screens.BusquePerfil.createRoute(userId))
                }
            )
        }
        composable ( route = Screens.CreateReviews.route ){
            val viewModel: CreateReviewsViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            LaunchedEffect(state.navigateBack) {
                if (state.navigateBack) {
                    navController.popBackStack()
                }
            }
            CreateReviewsScreen(
                createReviewsViewModel = viewModel
            )
        }
        composable ( route = Screens.EditReview.route,
            arguments = listOf(navArgument("reviewId"){type = NavType.StringType})
        ){
            val reviewId = it.arguments?.getString("reviewId") ?: ""
            val viewModel: CreateReviewsViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            LaunchedEffect(state.navigateBack) {
                if (state.navigateBack) {
                    navController.popBackStack()
                }
            }
            CreateReviewsScreen(
                createReviewsViewModel = viewModel,
                reviewId = reviewId
            )
        }
        composable ( route = Screens.Notifications.route ){
            NotificatonsScreen()
        }
        composable ( route = Screens.Profile.route ){
            ProfileScreen(
                onConnectionsClick = { idUsuario, esListaSeguidores ->
                    navController.navigate(
                        Screens.Connections.createRoute(idUsuario, esListaSeguidores)
                    )
                }
            )
        }
        composable ( route = Screens.Reviews.route ){
            ReviewScreen(
                viewModel = hiltViewModel(),
                reviewPressed = { reviewId ->
                    navController.navigate(Screens.CompleteSpecificReview.createRoute(reviewId))
                },
                editarPressed = { reviewId ->
                    navController.navigate(Screens.EditReview.createRoute(reviewId))
                }
            )
        }
        composable ( route = Screens.Inicio.route ){
            val viewModel: InicioViewModel = hiltViewModel()
            InicioScreen(
                inicioViewModel = viewModel,
                onSeeAllClick = {
                    navController.navigate(Screens.Busqueda.route)
                }
            )
        }
        composable ( route = Screens.CompleteSpecificReview.route,
            arguments = listOf(navArgument("reviewId"){type = NavType.StringType})
        ){
            val reviewId = it.arguments?.getString("reviewId") ?: ""
            val viewModel: CompleteSpecificReviewViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            LaunchedEffect(state.navigateBack) {
                if (state.navigateBack) {
                    navController.popBackStack()
                }
            }
            CompleteSpecificReviewScreen(
                reviewId = reviewId,
                completeSpecificReviewViewModel = viewModel,
                autorPressed = { userId ->
                    navController.navigate(Screens.BusquePerfil.createRoute(userId))
                },
                commentPressed = { commentId ->
                    navController.navigate(Screens.CommentDetail.createRoute(commentId))
                }
            )
        }
        composable ( route = Screens.CommentDetail.route,
            arguments = listOf(navArgument("commentId"){type = NavType.StringType})
        ){
            val commentId = it.arguments?.getString("commentId") ?: ""
            val viewModel: CommentDetailViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            LaunchedEffect(state.navigateBack) {
                if (state.navigateBack) {
                    navController.popBackStack()
                }
            }
            CommentDetailScreen(
                commentId = commentId,
                commentDetailViewModel = viewModel,
                commentPressed = { replyId ->
                    navController.navigate(Screens.CommentDetail.createRoute(replyId))
                }
            )
        }

        composable(route = Screens.BusquePerfil.route,
            arguments = listOf(navArgument("userId"){type = NavType.StringType})
        ){
            val userId = it.arguments?.getString("userId") ?: ""
            BusquedaPerfilScreen(
                userId = userId,
                viewModel = hiltViewModel(),
                reviewPressed = { reviewId ->
                    navController.navigate(Screens.CompleteSpecificReview.createRoute(reviewId))
                },
                onConnectionsClick = { idUsuario, esListaSeguidores ->
                    navController.navigate(
                        Screens.Connections.createRoute(idUsuario, esListaSeguidores)
                    )
                }
            )

        }
        composable(
            route = Screens.Connections.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("followers") { type = NavType.BoolType }
            )
        ) { entry ->
            val userId = entry.arguments?.getString("userId").orEmpty()
            val followers = entry.arguments?.getBoolean("followers") ?: false
            com.example.knowhub.ui.screens.connections.ConnectionsScreen(
                userId = userId,
                followers = followers,
                onUserClick = { connectedUserId ->
                    navController.navigate(Screens.BusquePerfil.createRoute(connectedUserId))
                }
            )
        }

    }

}
