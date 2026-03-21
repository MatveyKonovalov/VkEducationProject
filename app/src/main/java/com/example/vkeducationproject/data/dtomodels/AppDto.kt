package com.example.vkeducationproject.data.dtomodels




data class AppDto(
    val name: String,
    val developer: String = "Undefined",
    val category: String,
    val ageRating: Int,
    val size: Float = 0f,
    val iconUrl: String,
    val screenshotUrlList: List<String> = emptyList(),
    val description: String,
    val id: String
)