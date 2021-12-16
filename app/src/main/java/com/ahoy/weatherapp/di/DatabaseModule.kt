package com.ahoy.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.ahoy.weatherapp.data.local.database.DatabaseConfig
import com.ahoy.weatherapp.data.local.database.WeatherDatabase
import com.ahoy.weatherapp.data.local.database.dao.FavCitiesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, DatabaseConfig.DB_NAME).build()

    @Provides
    @Singleton
    fun provideFavCitiesDao(db: WeatherDatabase): FavCitiesDao = db.favCitiesDao()
}