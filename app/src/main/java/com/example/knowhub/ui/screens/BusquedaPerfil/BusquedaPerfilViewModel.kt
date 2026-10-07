package com.example.knowhub.ui.screens.BusquedaPerfil

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.Usuario
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.cacheGet

//ViewModel que administra la carga de datos y el estado de la pantalla de perfil.
@HiltViewModel
class BusquedaPerfilViewModel @Inject constructor(
    private val userRepository : UserRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(BusquedaPerfilState())
    val uiState: StateFlow<BusquedaPerfilState> = _uiState.asStateFlow()

    init {
    }


    fun getUserProfile(userId: String){
        viewModelScope.launch {
            val result = userRepository.getUserById(userId)
            if(result.isSuccess){
                val userProfileInfo = result.getOrNull()
                if(userProfileInfo != null) {
                    _uiState.value = _uiState.value.copy(
                        user = userProfileInfo
                    )
                }
            }
        }
    }

    fun getUserReviews(userId: String) {
        viewModelScope.launch {
            val result = userRepository.getUserReview(userId)

            if (result.isSuccess) {
                val userProfileInfo = result.getOrNull() ?: emptyList()

                _uiState.value = _uiState.value.copy(
                    reviews = userProfileInfo
                )
            }
        }
    }

}