package com.ahoy.weatherapp.data.remote

import com.ahoy.weatherapp.data.model.ForecastResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAppService {

    @GET("/data/2.5/forecast?")
    suspend fun getForecastWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Response<ForecastResponse>

    @GET("/data/2.5/forecast?")
    suspend fun getWeatherbyPlaceName(
        @Query("q") placeName: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Response<ForecastResponse>

}
