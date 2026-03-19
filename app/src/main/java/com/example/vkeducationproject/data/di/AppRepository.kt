package com.example.vkeducationproject.data.di

import com.example.vkeducationproject.data.datasources.MakeTestData
import com.example.vkeducationproject.data.mappers.AgeRatingMapper
import com.example.vkeducationproject.data.mappers.AppMapper
import com.example.vkeducationproject.data.mappers.CategoryMapper
import com.example.vkeducationproject.data.repository.AppRepositoryImpl
import com.example.vkeducationproject.domain.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMakeTestData(): MakeTestData {
        return MakeTestData()
    }

    @Provides
    @Singleton
    fun provideAppMapper(): AppMapper {
        return AppMapper(
            ageRatingMapper = AgeRatingMapper(),
            categoryMapper = CategoryMapper()
        )
    }

    @Provides
    @Singleton
    fun provideAgeRatingMapper(): AgeRatingMapper {
        return AgeRatingMapper()
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        makeTestData: MakeTestData,
        appMapper: AppMapper,
        ageRatingMapper: AgeRatingMapper
    ): AppRepository {
        return AppRepositoryImpl(
            makeTestData,
            appMapper,
            ageRatingMapper
        )
    }
}