package com.example.vkeducationproject.domain.models

import androidx.annotation.StringRes
import com.example.vkeducationproject.R

enum class Category(@StringRes val categoryId: Int){
    APP(R.string.category_app),
    GAME(R.string.category_game),
    PRODUCTIVITY(R.string.category_productivity),
    SOCIAL(R.string.category_social),
    EDUCATION(R.string.category_education),
    ENTERTAINMENT(R.string.category_entertainment),
    MUSIC(R.string.category_music),
    VIDEO(R.string.category_video),
    PHOTOGRAPHY(R.string.category_photography),
    HEALTH(R.string.category_health),
    SPORTS(R.string.category_sports),
    NEWS(R.string.category_news),
    BOOKS(R.string.category_books),
    BUSINESS(R.string.category_business),
    FINANCE(R.string.category_finance),
    TRAVEL(R.string.category_travel),
    MAPS(R.string.category_maps),
    FOOD(R.string.category_food),
    SHOPPING(R.string.category_shopping),
    UTILITIES(R.string.category_utilities),
    OTHER(R.string.category_other),

    HEALTH_FITNESS(R.string.category_health_fitness),
    PHOTO_VIDEO(R.string.category_photo_video),
    FOOD_DRINK(R.string.category_food_drink),
    LIFESTYLE(R.string.category_lifestyle),
    NAVIGATION(R.string.category_navigation),
    COMMUNICATION(R.string.category_communication),
    WEATHER(R.string.category_weather),
    BOOKS_REFERENCE(R.string.category_books_reference),


}


