package com.example.knowhub.ui.screens.connections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.ProfileAsyncImage

@Composable
fun ConnectionsScreen(
    userId: String,
    followers: Boolean,
    connectionsViewModel: ConnectionsViewModel,
    modifier: Modifier = Modifier,
    userPressed: (String) -> Unit = {}
) {
    val state by connectionsViewModel.uiState.collectAsState()

    LaunchedEffect(userId, followers) {
        connectionsViewModel.loadData(userId, followers)
    }

    Box(modifier = modifier) {
        BackgroundImage()
        BodyConnectionsScreen(
            followers = followers,
            users = state.users,
            errorMessage = state.errorMessage,
            mostrarMensajeError = state.mostrarMensajeError,
            userPressed = userPressed
        )
    }
}

@Composable
fun BodyConnectionsScreen(
    followers: Boolean,
    users: List<UserProfileDto>,
    errorMessage: String = "",
    mostrarMensajeError: Boolean = false,
    userPressed: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(35.dp))
            AppLabel(
                stringResource(if (followers) R.string.seguidores else R.string.seguidos),
                primaryLight,
                tertiaryContainerLight,
                modifier = Modifier
                    .height(40.dp)
                    .width(320.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (mostrarMensajeError) {
                Text(errorMessage)
            }
        }

        items(users) { user ->
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .background(primaryLight)
                    .border(2.dp, tertiaryContainerLight)
                    .clickable { userPressed(user.id) }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileAsyncImage(
                    profileImage = user.foto.ifBlank { null },
                    size = 45
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = user.userName,
                        fontSize = 17.sp,
                        fontFamily = BangersFont
                    )
                    Text(
                        text = user.name,
                        fontSize = 13.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}
