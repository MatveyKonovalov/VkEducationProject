package com.example.vkeducationproject.domain

import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.AppInMarket

interface AppRepository{
    fun getApps(): List<AppInMarket>
    fun getAppById(id: String): App

}