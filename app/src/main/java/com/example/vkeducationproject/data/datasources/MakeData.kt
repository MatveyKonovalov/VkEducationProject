package com.example.vkeducationproject.data.datasources

import com.example.vkeducationproject.data.dtomodels.AppDto
import com.example.vkeducationproject.data.dtomodels.AppInMarketDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class MakeTestData @Inject constructor(){
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://185.103.109.134/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService = retrofit.create(ApiService::class.java)
    suspend fun makeData(): List<AppInMarketDto>
    {
        return try {
            println("Вызов API: http://185.103.109.134/catalog")
            val apps = apiService.getAllApps()
            println("Успешно загружено ${apps.size} приложений")
            apps
        } catch (e: Exception) {
            println("Ошибка при вызове API: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }
    suspend fun getAppById(id: String): AppDto{
        return apiService.getAppById(id)
    }
}