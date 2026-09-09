package com.example.knowhub.ui.screens.options.components

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logOut() {
        authRepository.signOut()
    }
}
