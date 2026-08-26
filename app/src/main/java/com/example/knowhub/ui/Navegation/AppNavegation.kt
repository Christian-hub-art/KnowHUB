package com.example.knowhub.ui.Navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.knowhub.ui.screens.BusquedaFiltro.BusquedaScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.login.LoginScreen
import com.example.knowhub.ui.screens.register.RegisterScreen


@Composable
fun AppNavegation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = "start",
        modifier = modifier
    ) {
        composable(route = "start"){
            LoginScreen(
            )
        }
        composable(route = "register"){
            RegisterScreen(

            )
        }
        composable ( route = "busqueda" ){
            BusquedaScreen(
                generalReviewPressed = { generalReviewId ->
                    navController.navigate("completeReviews/$generalReviewId")
                }
            )
        }
        composable ( route = "completeReviews/{generalReviewId}",
            arguments = listOf(navArgument("generalReviewId"){type = NavType.IntType})
        ){
            val generalReviewId = it.arguments?.getInt("generalReviewId") ?: 0

            CompleteReviewsScreen(generalReviewId)
        }
        composable ( route = "createReviews" ){

        }
        composable ( route = "notifications" ){

        }
        composable ( route = "profile" ){

        }
        composable ( route = "reviews" ){

        }

    }

}