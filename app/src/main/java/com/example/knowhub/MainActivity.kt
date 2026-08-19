package com.example.knowhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.knowhub.ui.screens.CreateReviews.CreateReviewsScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.login.LoginScreen
import com.example.knowhub.ui.screens.notifications.NotificatonsScreen
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.theme.KnowHUBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KnowHUBTheme() {
                Scaffold(
                    topBar = {KnowhubTopAppBar()}
                ) {

                    CompleteReviewsScreen(
                        modifier = Modifier.padding(it)
                    )
                }

            }
        }
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
