package com.example.knowhub.ui.screens.register.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.example.knowhub.R
import com.example.knowhub.ui.utils.Message

@Composable
fun SignUPMessage(
    modifier: Modifier = Modifier
){
    Message(
        stringResource(R.string.sign_up)
    )

}