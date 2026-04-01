package com.example.vkeducationproject.domain

import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.AppInMarket
import kotlinx.coroutines.flow.Flow

interface AppRepository{
    suspend fun getApps(): List<AppInMarket>
    suspend fun getAppById(id: String): App

    fun getDefaultApp(): App
    suspend fun toggleWishlist(id: String)
}