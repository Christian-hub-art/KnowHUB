package com.example.knowhub.ui.screens.connections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowhub.R
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.ProfileAsyncImage

@Composable
fun ConnectionsScreen(
    userId: String,
    followers: Boolean,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConnectionsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(userId, followers) {
        viewModel.load(userId, followers)
    }
    Box(modifier = modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            AppLabel(
                stringResource(if (followers) R.string.seguidores else R.string.seguidos),
                primaryLight,
                tertiaryContainerLight,
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            when {
                state.isLoading -> CircularProgressIndicator()
                state.errorMessage.isNotBlank() -> Text(state.errorMessage)
                state.users.isEmpty() -> Text("Todavía no hay usuarios en esta lista.")
                else -> LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.users, key = { it.userId }) { user ->
                        androidx.compose.foundation.layout.Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onUserClick(user.userId) }
                                .padding(horizontal = 24.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfileAsyncImage(profileImage = user.foto, size = 48)
                            androidx.compose.foundation.layout.Column(
                                modifier = Modifier.padding(start = 12.dp)
                            ) {
                                Text(user.name.ifBlank { user.userName })
                                if (user.userName.isNotBlank()) Text("@${user.userName}")
                            }
                        }
                    }
                }
            }
        }
    }
}
