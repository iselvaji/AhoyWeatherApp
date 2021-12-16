package com.ahoy.weatherapp.data.model

data class WeatherInfo(
    val timeToDisplay: String?,
    val temprature: Double?,
    val humidity: Int,
    val weatherDesc: String?
)
