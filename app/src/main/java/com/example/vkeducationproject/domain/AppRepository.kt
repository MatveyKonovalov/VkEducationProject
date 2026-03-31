package com.example.vkeducationproject.domain

import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.AppInMarket

interface AppRepository{
    suspend fun getApps(): List<AppInMarket>
    suspend fun getAppById(id: String): App

    fun getDefaultApp(): App

}