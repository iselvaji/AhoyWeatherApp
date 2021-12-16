package com.ahoy.weatherapp.data.local.datasource

import com.ahoy.weatherapp.data.local.database.dao.FavCitiesDao
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val favCitiesDao: FavCitiesDao) {

    fun getFavCities(): Flow<List<FavCitiesEntity>> = favCitiesDao.getCities()

    fun insertCity(city : FavCitiesEntity) {
        favCitiesDao.insertCity(city)
    }

    fun getCityWeatherByName(cityName : String) = favCitiesDao.getCityByName(cityName)

}