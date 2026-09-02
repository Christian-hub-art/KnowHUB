package com.example.knowhub.ui.screens.login.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.knowhub.R
import com.example.knowhub.ui.utils.Message

@Composable
fun LoginMessage(){
    Message(stringResource(R.string.login_header))
}