package com.example.vkeducationproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<App>>(emptyList())
    val apps: StateFlow<List<App>> = _apps.asStateFlow()

    private val _currentApp = MutableStateFlow<App>(appRepository.getAppById(""))
    val currentApp: StateFlow<App> = _currentApp.asStateFlow()

    private val _showDescription = MutableStateFlow(false)
    val showDescription: StateFlow<Boolean> = _showDescription.asStateFlow()

    private val _showToast = MutableStateFlow<String?>(null)
    val showToast: StateFlow<String?> = _showToast.asStateFlow()

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

    fun loadApps() {
        viewModelScope.launch {
            try {
                _apps.value = appRepository.getApps()
            } catch (e: Exception) {
                _showToast.value = "Ошибка загрузки: ${e.message}"
            }
        }
    }

    fun loadAppDetails(id: String) {
        viewModelScope.launch {
            try {
                val app = appRepository.getAppById(id)
                _currentApp.value = app
            } catch (e: Exception) {
                _showToast.value = "Ошибка при загрузке приложения"
            }
        }
    }

    fun changeDescriptionStatus() {
        _showDescription.update { !it }
    }

}