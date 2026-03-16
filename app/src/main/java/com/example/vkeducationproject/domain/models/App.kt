package com.example.vkeducationproject.domain.models

data class App(
    val name: String,
    val developer: String = "Undefined",
    // Решил взять свою реализацию Category(файл DataModels),
    // так как она покрывает больше категорий
    val category: Category,
    val ageRating: AgeRatings = AgeRatings.PEGI3,
    val size: Float = 0f,
    val iconUrl: String,
    val screenshotUrlList: List<String> = emptyList(),
    val description: String,
    val id: String
){
    init{
        require(name.isNotBlank()){"App name cannot be blank"}
        require(size >= 0){"App size cannot be negative"}
        require((iconUrl.isNotBlank())){"Icon URL cannot be blank"}
    }
    fun getWayForPage(): String = "app_page/$id"

    fun getAgeRating(): String {
        return when (ageRating){
            AgeRatings.PEGI3 -> "3+"
            AgeRatings.PEGI7 -> "7+"
            AgeRatings.PEGI12 -> "12+"
            AgeRatings.PEGI16 -> "16+"
            AgeRatings.PEGI18 -> "18+"
        }
    }
}