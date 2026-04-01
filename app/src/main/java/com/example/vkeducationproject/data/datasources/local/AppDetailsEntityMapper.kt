package com.example.vkeducationproject.data.datasources.local

import com.example.vkeducationproject.data.mappers.AgeRatingMapper
import com.example.vkeducationproject.domain.models.App
import javax.inject.Inject

class AppDetailsEntityMapper @Inject constructor(
    private val ageMapper: AgeRatingMapper
) {

    fun toEntity(domain: App): AppDetailsEntity = AppDetailsEntity(
        id = domain.id,
        name = domain.name,
        developer = domain.developer,
        category = domain.category,
        ageRating = ageMapper.toEntity(domain.ageRating),
        size = domain.size,
        iconUrl = domain.iconUrl,
        screenshots = null,
        description = domain.description,
        isInWishlist = domain.isInWishList
    )

    fun toDomain(entity: AppDetailsEntity): App = App(
        id = entity.id,
        name = entity.name,
        developer = entity.developer,
        category = entity.category,
        ageRating = ageMapper.toDomain(entity.ageRating),
        size = entity.size,
        iconUrl = entity.iconUrl,
        screenshotUrlList = emptyList(),
        description = entity.description,
        isInWishList = entity.isInWishlist
    )
}