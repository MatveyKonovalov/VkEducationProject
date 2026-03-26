package com.example.vkeducationproject.data.datasources

import com.example.vkeducationproject.data.dtomodels.AppDto
import com.example.vkeducationproject.data.dtomodels.AppInMarketDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService{
    @GET("catalog")
    suspend fun getAllApps(): List<AppInMarketDto>

    @GET("catalog/{id}")
    suspend fun getAppById(
        @Path("id") id: String
    ): AppDto
}
