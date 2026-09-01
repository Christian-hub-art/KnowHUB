package com.example.knowhub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.knowhub.ui.Navegation.AppNavegation
import com.example.knowhub.ui.Navegation.Screens
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.options.components.MenuOpciones
import kotlinx.coroutines.launch

@Composable
fun KnowHUBApp (){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showTopBar = currentRoute in listOf(
        Screens.Inicio.route,
        Screens.Profile.route,
        Screens.Reviews.route,
        Screens.Notifications.route,
        Screens.CreateReviews.route,
        Screens.Busqueda.route,
        Screens.CompleteReviews.route,
        Screens.BusquePerfil.route
    )

    val showDrawer = currentRoute !in listOf(
        Screens.Start.route,
        Screens.Register.route
    )


    if (showDrawer) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                MenuOpciones(
                    inicioButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Inicio.route)
                    },
                    profileButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Profile.route)
                    },
                    previewButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Reviews.route)
                    },
                    notificationsButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Notifications.route)
                    },
                    createReviewsButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.CreateReviews.route)
                    },
                    buscarButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Busqueda.route)
                    },
                    cerrarSesionButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Start.route)
                    }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    if (showTopBar) {
                        KnowhubTopAppBar(
                            onMenuClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )
                    }
                }
            ) {
                AppNavegation(
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        }

    } else {

        AppNavegation(
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnowhubTopAppBar(
    onMenuClick: () -> Unit
){
    TopAppBar(
        title = {
            //Logo alineado a la izquierda
            Image(
                painter = painterResource(R.drawable.logoknowhub),
                contentDescription = "Logo KnowHUB",
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 16.dp)
            )
        },
        actions = {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.iconomenu),
                    contentDescription = "Icono",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}
