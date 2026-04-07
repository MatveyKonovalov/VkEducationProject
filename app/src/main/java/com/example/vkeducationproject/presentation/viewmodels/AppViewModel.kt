package com.example.vkeducationproject.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkeducationproject.domain.models.AppInMarket
import com.example.vkeducationproject.domain.usecases.GetAppsUseCase
import com.example.vkeducationproject.domain.usecases.OnLogoClickUseCase
import com.example.vkeducationproject.domain.usecases.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase,
    private val onLogoClickUseCase: OnLogoClickUseCase
) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppInMarket>>(emptyList())
    val apps: StateFlow<List<AppInMarket>> = _apps.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadApps()
    }

    fun onLogoClick() {
        viewModelScope.launch {
            val message = onLogoClickUseCase()
            _snackbar.emit(message)
        }
    }

    fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getAppsUseCase()) {
                is Result.Success -> {
                    _apps.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _isLoading.value = false
                    Log.d("Error", "Ошибка при загрузке приложения: ${result.message}")
                    _snackbar.emit("Ошибка загрузки: ${result.message}")
                }
                is Result.Loading -> {

                }
            }
        }
    }
}