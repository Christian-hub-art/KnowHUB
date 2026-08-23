package com.example.knowhub.ui.Navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        composable ( route = "completeReviews" ){
            CompleteReviewsScreen(

            )

        }
        composable ( route = "createReviews" ){
            CompleteReviewsScreen(

            )
        }
        composable ( route = "notifications" ){
            CompleteReviewsScreen(

            )
        }
        composable ( route = "profile" ){
            CompleteReviewsScreen(

            )
        }
        composable ( route = "reviews" ){
            CompleteReviewsScreen(

            )
        }

    }

}