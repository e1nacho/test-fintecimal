package com.test_fintecimal

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [LocationEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun locationDao(): LocationDao?
}