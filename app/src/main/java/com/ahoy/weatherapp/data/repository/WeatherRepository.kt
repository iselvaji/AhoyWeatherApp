package com.ahoy.weatherapp.data.repository

import com.ahoy.weatherapp.comman.Resource
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import com.ahoy.weatherapp.data.local.datasource.LocalDataSource
import com.ahoy.weatherapp.data.model.ForecastResponse
import com.ahoy.weatherapp.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource) {

    fun getForecastWeatherList(lattitude: Double, longitude : Double) : Flow<Resource<ForecastResponse>> {
        return flow {
            emit(remoteDataSource.getForecastWeatherList(lattitude, longitude))
        }.flowOn(Dispatchers.IO)
    }

    fun getWeatherbyPlaceName(placeName : String) : Flow<Resource<ForecastResponse>> {
        return flow {
            emit(remoteDataSource.getWeatherbyPlaceName(placeName))
        }.flowOn(Dispatchers.IO)
    }

    // Database operations
    fun addFavoriteCity(favCity : FavCitiesEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertCity(favCity)
        }
    }

    fun getFavoriteCities(): Flow<List<FavCitiesEntity>> {
        return localDataSource.getFavCities()
    }

    fun getCityWeatherByName(cityName: String): Flow<List<FavCitiesEntity>> {
        return localDataSource.getCityWeatherByName(cityName)
    }
}