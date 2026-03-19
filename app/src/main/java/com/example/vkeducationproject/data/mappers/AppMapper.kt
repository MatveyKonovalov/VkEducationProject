package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.data.dtomodels.AppDto
import com.example.vkeducationproject.domain.models.App
import org.koin.core.component.getScopeId
import javax.inject.Inject

// Пусть с бекенда возраст передаётся числом и категория передаётся строкой
class AppMapper @Inject constructor(
    private val ageRatingMapper: AgeRatingMapper,
    private val categoryMapper: CategoryMapper
) {
    fun toDomain(dto: AppDto): App = App(
        name = dto.name,
        developer = dto.developer,
        category = categoryMapper.toDomain(dto.category),
        ageRating = ageRatingMapper.toDomain(dto.ageRating),
        size = dto.size,
        iconUrl = dto.iconUrl,
        screenshotUrlList = dto.screenshotUrlList,
        description = dto.description,
        id = dto.id
    )
}