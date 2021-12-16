package com.ahoy.weatherapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahoy.weatherapp.data.local.database.dao.FavCitiesDao
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity

@Database(entities = [FavCitiesEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun favCitiesDao(): FavCitiesDao
}