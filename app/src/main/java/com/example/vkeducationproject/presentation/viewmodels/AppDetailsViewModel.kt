package com.example.vkeducationproject.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkeducationproject.domain.AppRepository
import com.example.vkeducationproject.domain.models.App
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val appRepository: AppRepository
): ViewModel(){
    private val _showDescription = MutableStateFlow(false)
    val showDescription: StateFlow<Boolean> = _showDescription.asStateFlow()

    private val _currentApp = MutableStateFlow<App>(appRepository.getDefaultApp())
    val currentApp: StateFlow<App> = _currentApp.asStateFlow()

    private val _showToast = MutableStateFlow<String?>(null)
    val showToast: StateFlow<String?> = _showToast.asStateFlow()

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun loadAppDetails(id: String) {
        viewModelScope.launch {
            try {
                val app = appRepository.getAppById(id)
                _currentApp.value = app
                _isFavorite.value = app.isInWishList
            } catch (e: Exception) {
                Log.d("Error", "Ошибка при загрузке приложения: ${e.message}")
            }
        }
    }

    fun changeDescriptionStatus() {
        _showDescription.update { !it }
    }

    fun onShareClick() {
        _showToast.value = "В разработке"
    }

    fun onToastShown() {
        _showToast.value = null
    }

    fun onInstallClick() {
        _showToast.value = "В разработке"
    }

    fun onDeveloperClick() {
        _showToast.value = "В разработке"
    }
    fun onWishClick(): Unit{
        viewModelScope.launch{
            _isFavorite.value = !_isFavorite.value
            withContext(Dispatchers.IO){
                appRepository.toggleWishlist(currentApp.value.id)
            }
        }
    }
}