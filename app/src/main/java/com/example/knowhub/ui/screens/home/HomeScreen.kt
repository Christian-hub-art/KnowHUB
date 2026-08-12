package com.example.knowhub.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.ui.utils.BarraArriba
import com.example.knowhub.R
import com.example.knowhub.ui.screens.home.components.BotonesHomeScreen
import com.example.knowhub.ui.utils.BackgroundImage


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box() {
        BackgroundImage()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            BarraArriba(
                R.drawable.inciotarea,
                stringResource(R.string.inicio)
            )
            bodyHomeScreen()
        }
    }
}
@Composable
fun bodyHomeScreen(
    modifier: Modifier = Modifier

){

    Image(
        painter = painterResource(R.drawable.menuimagen),
        contentDescription = "Imagen Menu",
        modifier = modifier
            .border(2.dp, colorResource(R.color.NegroKnowHUB))
            .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
    )

    Spacer(modifier = Modifier.padding(30.dp))
    BotonesHomeScreen()


}



@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen()
}