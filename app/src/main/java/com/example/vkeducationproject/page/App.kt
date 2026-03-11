package com.example.vkeducationproject.page
import com.example.vkeducationproject.Category
import com.example.vkeducationproject.navigate.AppViewModel

data class App(
    val name: String,
    val developer: String = "Undefined",
    // Решил взять свою реализацию Category(файл DataModels),
    // так как она покрывает больше категорий
    val category: Category,
    val ageRating: Int = 0,
    val size: Float = 0f,
    val iconUrl: String,
    val screenshotUrlList: List<String> = listOf(iconUrl),
    val description: String,
)

// В будущем будет измененно, когда перейдём к api
data class AppWithId(
    val appData: App,
    val id: String
)