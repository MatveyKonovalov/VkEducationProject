package com.example.vkeducationproject.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vkeducationproject.domain.models.App

@Dao
interface AppDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appDetailsEntity: AppDetailsEntity): Long // id новой записи

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg apps: AppDetailsEntity): List<Long>

    @Update
    suspend fun updateApp(app: AppDetailsEntity): Int

    @Query("SELECT * FROM app_details WHERE id = :id")
    suspend fun getApp(id: String): List<AppDetailsEntity>
}