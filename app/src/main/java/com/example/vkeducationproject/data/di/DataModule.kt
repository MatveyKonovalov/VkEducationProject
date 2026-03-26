package com.example.vkeducationproject.data.di

import com.example.vkeducationproject.data.mappers.AgeRatingMapper
import com.example.vkeducationproject.data.mappers.AppInMarketMapper
import com.example.vkeducationproject.data.mappers.AppMapper
import com.example.vkeducationproject.data.mappers.CategoryMapper
import com.example.vkeducationproject.data.repository.AppRepositoryImpl
import com.example.vkeducationproject.domain.AppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{
    companion object{

        @Provides
        @Singleton
        fun provideAgeRatingMapper(): AgeRatingMapper = AgeRatingMapper()

        @Provides
        @Singleton
        fun provideCategoryMapper(): CategoryMapper = CategoryMapper()

        @Provides
        @Singleton
        fun provideAppMapper(
            ageRatingMapper: AgeRatingMapper,
            categoryMapper: CategoryMapper
        ): AppMapper = AppMapper(ageRatingMapper, categoryMapper)

        @Provides
        @Singleton
        fun provideAppInMarket(
            categoryMapper: CategoryMapper
        ): AppInMarketMapper = AppInMarketMapper(categoryMapper)
    }


    @Binds
    @Singleton
    abstract fun provideRepository(
        impl: AppRepositoryImpl
    ): AppRepository
}