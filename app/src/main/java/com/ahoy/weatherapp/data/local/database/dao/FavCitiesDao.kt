package com.ahoy.weatherapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahoy.weatherapp.data.local.database.DatabaseConfig
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavCitiesDao {

    @Query("SELECT * FROM ${DatabaseConfig.TableName.FAV_CITIES}")
    fun getCities(): Flow<List<FavCitiesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(citiesEntity: FavCitiesEntity)

    @Query("DELETE FROM ${DatabaseConfig.TableName.FAV_CITIES}")
    fun deleteCities()

    @Query("Select count(*) from ${DatabaseConfig.TableName.FAV_CITIES}")
    fun getCount(): Int

    @Query("SELECT * FROM ${DatabaseConfig.TableName.FAV_CITIES} WHERE cityName =:cityName")
    fun getCityByName(cityName: String? = ""): Flow<List<FavCitiesEntity>>
}