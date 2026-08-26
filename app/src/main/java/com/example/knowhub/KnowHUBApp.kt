package com.example.knowhub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.knowhub.ui.Navegation.AppNavegation
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen

@Composable
fun KnowHUBApp (){

    Scaffold(
        topBar = {KnowhubTopAppBar()}
    ) {
        val navController = rememberNavController()
        AppNavegation(
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnowhubTopAppBar(){
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
                onClick = { },
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
