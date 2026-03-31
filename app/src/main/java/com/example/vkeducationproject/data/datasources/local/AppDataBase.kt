package com.example.vkeducationproject.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AppDetailsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase(){
    abstract fun appDetailsDao(): AppDetailsDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
