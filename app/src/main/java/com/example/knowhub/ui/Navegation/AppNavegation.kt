package com.example.knowhub.ui.Navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.knowhub.ui.screens.BusquedaFiltro.BusquedaScreen
import com.example.knowhub.ui.screens.CreateReviews.CreateReviewsScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.inicio.InicioScreen
import com.example.knowhub.ui.screens.login.LoginScreen
import com.example.knowhub.ui.screens.notifications.NotificatonsScreen
import com.example.knowhub.ui.screens.options.OptionsScreen
import com.example.knowhub.ui.screens.profile.ProfileScreen
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.screens.register.RegisterViewModel
import com.example.knowhub.ui.screens.reviews.ReviewScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.knowhub.ui.screens.BusquedaPerfil.BusquedaPerfilScreen
import com.example.knowhub.ui.screens.login.LoginViewModel

sealed class Screens(val route: String) {
    object Start : Screens("start")
    object Register : Screens("register")
    object Options : Screens("optionsScreens")
    object Busqueda : Screens("busqueda")
    object CompleteReviews : Screens("completeReviews/{generalReviewId}") {
        fun createRoute(id: Int) = "completeReviews/$id"
    }
    object CreateReviews : Screens("createReviews")
    object Notifications : Screens("notifications")
    object Profile : Screens("profile")
    object Reviews : Screens("reviews")
    object Inicio : Screens("inicio")

    object BusquePerfil : Screens("busquedaPerfil")
}

@Composable
fun AppNavegation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screens.Start.route,
        modifier = modifier
    ) {
        composable(route = Screens.Start.route){
            val loginViewModel: LoginViewModel = viewModel()
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

        composable(route = Screens.Register.route) {
            val registerViewModel: RegisterViewModel = viewModel()
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

        composable(route = Screens.Options.route){
            OptionsScreen()
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

            CompleteReviewsScreen(
                generalReviewId,
                escribirBottonPressed = {
                    navController.navigate(Screens.CreateReviews.route)
                }
            )
        }
        composable ( route = Screens.CreateReviews.route ){
            CreateReviewsScreen()
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
            InicioScreen(
                onSeeAllClick = {
                    navController.navigate(Screens.Busqueda.route)
                }
            )
        }
        composable(route = Screens.BusquePerfil.route){
            BusquedaPerfilScreen(

            )
        }

    }

}