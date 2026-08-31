package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.data.local.localReviewProvider
import com.example.knowhub.ui.screens.completeReviews.BodyCompleteReviewsScreen
import com.example.knowhub.ui.screens.completeReviews.CompleteReviewsScreen
import com.example.knowhub.ui.screens.completeSpecificReview.components.Comment
import com.example.knowhub.ui.screens.completeSpecificReview.components.Review
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppButtonBig
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun CompleteSpecificReviewScreen(
    ReviewId: Int,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyCompleteSpecificReviewScreen(ReviewId)
    }

}

@Composable
fun BodyCompleteSpecificReviewScreen(
    ReviewId: Int,
    modifier: Modifier = Modifier
){
    val review = localReviewProvider.Reviews.find { it.id == ReviewId }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {

                Spacer(modifier = modifier.height(35.dp))
                if (review != null) {
                    Review(
                        review,
                        modifier.width(350.dp)
                    )
                }
                Spacer(modifier = modifier.height(30.dp))
                Row() {
                    Box(
                        modifier = modifier
                            .background(tertiaryContainerLight)
                            .height(2.5F.dp)
                            .width(150.dp)
                    ) {}
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.rese_as),
                        fontSize = 17.sp,
                        fontFamily = BangersFont
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = modifier
                            .background(tertiaryContainerLight)
                            .height(2.5F.dp)
                            .width(150.dp)
                    ) {}
                }
                Spacer(modifier = modifier.height(20.dp))

                Column(modifier = Modifier.width(350.dp)) {
                    Comment(
                        "30 Nov 2026",
                        "Sebastian Gaibor",
                        "Materia pesada, es importante llevar la calculadora SIEMPRE",
                        30,
                        2
                    )
                    Spacer(modifier = modifier.height(20.dp))
                    Comment(
                        "1 Dic 2026",
                        "Andrés Cano",
                        "Yo no la pude pasar a la primera :(",
                        1,
                        0
                    )
                    Spacer(modifier = modifier.height(20.dp))
                    Comment(
                        "19 Dic 2026",
                        "Dana Trujillo",
                        "A mi tambie me parecio muy bien profesor",
                        15,
                        5
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompleteSpecificReviewScreenPreview(){
    CompleteSpecificReviewScreen(5)
}
