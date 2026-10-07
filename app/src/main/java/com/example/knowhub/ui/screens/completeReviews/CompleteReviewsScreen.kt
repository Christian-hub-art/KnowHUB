package com.example.knowhub.ui.screens.completeReviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.ui.screens.completeReviews.components.CajaPrincipal
import com.example.knowhub.ui.screens.completeReviews.components.Review as ReviewComponent
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppButtonBig
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun CompleteReviewsScreen(generalReviewId: String, completeReviewsViewModel: CompleteReviewsViewModel, reviewPressed: (String) -> Unit, escribirBottonPressed: () -> Unit, modifier: Modifier = Modifier) {
    val state by completeReviewsViewModel.uiState.collectAsState()
    LaunchedEffect(generalReviewId) { completeReviewsViewModel.loadData(generalReviewId) }
    Box(modifier) {
        BackgroundImage()
        BodyCompleteReviewsScreen(state, reviewPressed, escribirBottonPressed)
    }
}

@Composable
fun BodyCompleteReviewsScreen(state: CompleteReviewsState, reviewPressed: (String) -> Unit, escribirBottonPressed: () -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(vertical = 24.dp)) {
        item {
            CajaPrincipal(state.generalReview, Modifier.width(350.dp))
            Spacer(Modifier.height(20.dp))
            Text(stringResource(R.string.rese_as), fontSize = 20.sp, fontFamily = BangersFont)
            Row(Modifier.width(350.dp)) {
                AppButton(stringResource(R.string.para_ti), primaryLight, primaryContainerLight, modifier = Modifier.height(40.dp))
                Spacer(Modifier.width(6.dp))
                AppButton(stringResource(R.string.siguiendo), tertiaryContainerLight, secondaryContainerLight, modifier = Modifier.height(40.dp))
            }
            Spacer(Modifier.height(16.dp))
            if (state.isLoading) CircularProgressIndicator()
            state.error?.let { Text(it, Modifier.padding(16.dp), color = tertiaryContainerLight) }
        }
        items(state.allReviews, key = { it.id }) { review ->
            ReviewComponent(review, reviewPressed, Modifier.width(350.dp))
            Spacer(Modifier.height(20.dp))
        }
        item {
            AppButtonBig(stringResource(R.string.escribe_tu_rese_a), secondaryContainerLight, tertiaryContainerLight, onClick = escribirBottonPressed, modifier = Modifier.width(300.dp))
        }
    }
}

@Composable @Preview(showBackground = true)
fun CompleteReviewsScreenPreview() {
    CompleteReviewsScreen("5", viewModel(), {}, {})
}


