package com.example.vkeducationproject.data.repository


import com.example.vkeducationproject.data.datasources.MakeTestData
import com.example.vkeducationproject.data.models.App

interface AppRepository{
    fun getApps(): List<App>
    fun getAppById(id: String): App?
}

class AppRepositoryImpl: AppRepository {
    private val appsRepository = mutableMapOf<String, App>()

    init{
        loadData()
    }

    private fun loadData(){
        try{
            MakeTestData.makeData().forEach { app ->
                appsRepository[app.id] = app}
        } catch (e: Exception){
            println("Failed to load apps: ${e.message}")
        }
    }

    override fun getApps(): List<App> {
        return appsRepository.values.toList()
    }

    override fun getAppById(id: String): App? {
        return appsRepository[id]
    }

}