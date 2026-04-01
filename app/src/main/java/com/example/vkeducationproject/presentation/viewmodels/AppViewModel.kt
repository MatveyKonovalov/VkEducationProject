package com.example.vkeducationproject.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.AppRepository
import com.example.vkeducationproject.domain.models.AppInMarket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppInMarket>>(emptyList())
    val apps: StateFlow<List<AppInMarket>> = _apps.asStateFlow()
    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        loadApps()
    }
    fun onLogoClick() {
        viewModelScope.launch {
            _snackbar.emit("Нажатие на логотип")
        }
    }

    fun loadApps() {
        viewModelScope.launch {
            try {
                _apps.value = appRepository.getApps()
            } catch (e: Exception) {
                Log.d("Error", "Ошибка при загрузке приложения: ${e.message}")
            }
        }
    }

}
