package com.example.vkeducationproject.domain.models

data class AppInMarket(
    val name: String,
    val id: String,
    val description: String,
    val iconUrl: String,
    val category: Category
){
    fun getWayForPage(): String = "app_page/$id"
}