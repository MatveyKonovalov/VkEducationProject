package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.domain.models.Category
import java.util.Locale
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toDomain(category: String): Category{
        return when(category){
            "Финансы" -> Category.FINANCE
            "Инструменты" -> Category.TOOLS
            "Игры" -> Category.GAME
            "Транспорт" -> Category.TRANSPORT
            else -> throw IllegalArgumentException("Неизвестная категория")
        }
    }
}