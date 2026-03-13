package com.example.vkeducationproject

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AppDetails {
    data object Loading: AppDetails
    data object Yes: AppDetails
    class Content: AppDetails
}


class AppDetailsModel: ViewModel(){
    private val _state = MutableStateFlow<AppDetails>(AppDetails.Loading)

    // Делаем публичное поле (неизменяемое)
    val state: StateFlow<AppDetails> = _state.asStateFlow()

    fun loadApp(){
        // Изменение через value
        viewModelScope.launch {
            _state.value = AppDetails.Yes
            delay(200L)
            _state.value = AppDetails.Content()

        }



    }
}