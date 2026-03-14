package com.example.vkeducationproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkeducationproject.data.models.AgeRatings
import com.example.vkeducationproject.data.models.App
import com.example.vkeducationproject.data.models.Category
import com.example.vkeducationproject.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _apps = MutableStateFlow<List<App>>(emptyList())
    val apps: StateFlow<List<App>> = _apps.asStateFlow()

    private val _currentApp = MutableStateFlow(getDefaultApp())
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
                val app = appRepository.getAppById(id)
                if (app == null){
                    _currentApp.value = getDefaultApp()
                } else{
                    _currentApp.value = app
                }


            } catch(e: Exception){
                throw Exception("Ошибка при поиске")
            }
        }
    }
    fun changeDescriptionStatus(){
        _showDescription.value = !_showDescription.value
    }
    private fun getDefaultApp(): App = App(
        name = "Гильдия Героев: Экшен ММО РПГ",
        developer = "VK Play",
        category = Category.GAME,
        ageRating = AgeRatings.PEGI12,
        size = 223.7f,
        screenshotUrlList = listOf(
            "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp",
            "https://static.rustore.ru/imgproxy/dZCvNtRKKFpzOmGlTxLszUPmwi661IhXynYZGsJQvLw/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg@webp",
            "https://static.rustore.ru/imgproxy/g5whSI1uNqaL2TUO7TFfM8M63vXpWXNCm2vlX4Ahvc4/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg@webp",
            "https://static.rustore.ru/imgproxy/TjeurtC7BczOVJt74XhjGYuQnG1l4rx6zpDqyMb00GY/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/08318f76-7a9c-43aa-b4a7-1aa878d00861.jpg@webp",
        ),
        iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
        description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и зразите мастера подземелья!",
        id="1",
    )

}