package com.example.vkeducationproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkeducationproject.data.datasources.MakeTestData
import com.example.vkeducationproject.data.mappers.AgeRatingMapper
import com.example.vkeducationproject.data.mappers.AppMapper
import com.example.vkeducationproject.data.mappers.CategoryMapper
import com.example.vkeducationproject.data.repository.AppRepositoryImpl
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.AppRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    private val appRepository: AppRepository = AppRepositoryImpl(
        mapper = AppMapper(
            ageRatingMapper = AgeRatingMapper(),
            categoryMapper = CategoryMapper()
        ),
        data = MakeTestData()
    )
    private val _apps = MutableStateFlow<List<App>>(emptyList())
    val apps: StateFlow<List<App>> = _apps.asStateFlow()

    private val _currentApp = MutableStateFlow(appRepository.getAppById("") as App)
    val currentApp: StateFlow<App> = _currentApp.asStateFlow()

    private val _showDescription = MutableStateFlow(false)
    val showDescription = _showDescription.asStateFlow()

    private val _showToast = MutableStateFlow<String?>(null)
    val showToast = _showToast.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun onLogoClick(){
        viewModelScope.launch{
            _snackbar.emit("Нажатие на логотип")
        }
    }


    fun onShareClick(){
        _showToast.value = "В разработке"
    }
    fun onToastShown(){
        _showToast.value = null
    }
    fun onInstallClick(){
        _showToast.value = "В разработке"
    }
    fun onDeveloperClick(){
        _showToast.value = "В разработке"
    }

    fun loadApps(){
        viewModelScope.launch {
            _apps.value = appRepository.getApps()
        }

    }
    fun loadAppDetails(id: String){
        viewModelScope.launch{
            try{
                val app = appRepository.getAppById(id) as App
                _currentApp.value = app

            } catch(e: Exception){
                _showToast.value = "Ошибка при загрузке приложения"
            }
        }
    }
    fun changeDescriptionStatus(){
        _showDescription.value = !_showDescription.value
    }


}