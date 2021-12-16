package com.ahoy.weatherapp.data.remote

import com.ahoy.weatherapp.BuildConfig
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherAppService: WeatherAppService
): BaseDataSource() {

    suspend fun getForecastWeatherList(lattitude: Double, longitude : Double) = getResult {
        weatherAppService.getForecastWeather(latitude = lattitude,
            longitude = longitude, appId = BuildConfig.api_key, units = "metric")
    }

    suspend fun getWeatherbyPlaceName(placeName : String) = getResult {
        weatherAppService.getWeatherbyPlaceName(placeName = placeName, appId = BuildConfig.api_key,  units = "metric")
    }
}