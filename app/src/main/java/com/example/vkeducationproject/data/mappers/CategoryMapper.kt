package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.domain.models.Category
import java.util.Locale
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toDomain(category: String): Category{
        return when (category.lowercase().trim()) {
            // Основные категории
            "приложения" -> Category.APP
            "игры" -> Category.GAME
            "производительность" -> Category.PRODUCTIVITY
            "социальные сети" -> Category.SOCIAL
            "образование" -> Category.EDUCATION
            "развлечения" -> Category.ENTERTAINMENT
            "музыка" -> Category.MUSIC
            "видео" -> Category.VIDEO
            "фотография" -> Category.PHOTOGRAPHY
            "здоровье" -> Category.HEALTH
            "спорт" -> Category.SPORTS
            "новости" -> Category.NEWS
            "книги" -> Category.BOOKS
            "бизнес" -> Category.BUSINESS
            "финансы" -> Category.FINANCE
            "путешествия" -> Category.TRAVEL
            "карты" -> Category.MAPS
            "еда" -> Category.FOOD
            "покупки" -> Category.SHOPPING
            "утилиты" -> Category.UTILITIES

            "здоровье и фитнес" -> Category.HEALTH_FITNESS
            "фото и видео" -> Category.PHOTO_VIDEO
            "еда и напитки" -> Category.FOOD_DRINK
            "образ жизни" -> Category.LIFESTYLE
            "навигация" -> Category.NAVIGATION
            "общение" -> Category.COMMUNICATION
            "погода" -> Category.WEATHER
            "книги и справочники" -> Category.BOOKS_REFERENCE

            else -> Category.OTHER

        }
    }
}