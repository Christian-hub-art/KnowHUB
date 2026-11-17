package com.example.knowhub

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.knowhub.ui.Navegation.AppNavegation
import com.example.knowhub.ui.Navegation.Screens
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.options.components.MenuOpciones
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun KnowHUBApp (){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId: String = currentUser?.uid ?: ""

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted){
                Log.d("NotificationPermission", "Permission granted")
            }else{
                Log.d("NotificationPermission", "Permission denied")
            }

        }
    )
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val showTopBar = currentRoute !in listOf(
        Screens.Start.route,
        Screens.Register.route,
        Screens.RecuperarContrasena.route,
        Screens.Splash.route
    )

    val showDrawer = currentRoute !in listOf(
        Screens.Start.route,
        Screens.Register.route,
        Screens.RecuperarContrasena.route
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
                    // Cierra el menú y navega a la pantalla de reseñas en el mapa.
                    mapButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.ReviewMap.route)
                    },
                    cerrarSesionButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Start.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    isAuthenticated = currentUser != null,
                    iniciarSesionButtonPressed = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Start.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        ) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
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
                // imePadding: cuando sube el teclado la pantalla se achica para no tapar los campos
                AppNavegation(
                    navController = navController,
                    modifier = Modifier.padding(it).imePadding()
                )
            }
        }

    } else {

        AppNavegation(
            navController = navController,
            modifier = Modifier.imePadding()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnowhubTopAppBar(
    onMenuClick: () -> Unit
){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        title = {

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
                    .testTag("menuIconButton")
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
