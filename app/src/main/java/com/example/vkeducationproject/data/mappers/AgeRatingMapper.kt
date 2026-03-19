package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.domain.models.AgeRatings
import javax.inject.Inject

class AgeRatingMapper @Inject constructor() {
    fun toDomain(age: Int): AgeRatings {
        return when (age){
            in 3 until 7 -> AgeRatings.PEGI3
            in 7 until 12-> AgeRatings.PEGI7
            in 12 until 16 -> AgeRatings.PEGI12
            in 16 until 18 -> AgeRatings.PEGI16
            else -> AgeRatings.PEGI18
        }
    }
}