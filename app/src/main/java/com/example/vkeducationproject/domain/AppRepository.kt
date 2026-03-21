package com.example.vkeducationproject.domain

import com.example.vkeducationproject.domain.models.App

interface AppRepository{
    fun getApps(): List<App>
    fun getAppById(id: String): App

}