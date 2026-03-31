package com.example.vkeducationproject.data.datasources.local

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule{
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase{
        return AppDataBase.getInstance(context)
    }

    @Provides
    fun provideAppDetailsDao(dataBase: AppDataBase): AppDetailsDao{
        return dataBase.appDetailsDao()
    }
}