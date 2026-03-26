package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.data.dtomodels.AppInMarketDto
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.AppInMarket
import javax.inject.Inject

class AppInMarketMapper @Inject constructor(
    private val categoryMapper: CategoryMapper
){
    fun toDomain(appDto: AppInMarketDto): AppInMarket{
        return AppInMarket(
            name = appDto.name,
            id = appDto.id,
            description = appDto.description,
            iconUrl = appDto.iconUrl,
            category = categoryMapper.toDomain(appDto.category)
        )
    }
}