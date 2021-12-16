package com.ahoy.weatherapp.utils

import android.util.Log

object WeatherUtils {

    fun celciusToFahrenheit(celcius: Double) : Double {
        return (celcius * 9)/5 + 32
    }

    fun fahrenheitToCelcius(fahrenheit: Double) : Double {
        return (fahrenheit - 32)*5/9
    }

    fun kelvinToCelcius(kelvin : Double): Double {
        return kelvin - 273.15
    }

    fun kelvinToFahrenheit(kelvin : Double): Double {
        return (kelvin - 273.15) * 1.8 + 32.0
    }

    fun getTempratureInUnit(unit: String?, temprature : Double?) : String {
        if(unit != null) {
            Log.d("unit : $unit", "temprature : $temprature")
            if (unit == TempratureUnit.FAHRENHEIT.name) {

                temprature?.let {
                    val farenheit = celciusToFahrenheit(it)
                    val formattedVal = String.format("%.2f", farenheit).toDouble()
                    return "$formattedVal F"
                }
            }
        }
        return (temprature.toString() + " C")
    }
}

enum class TempratureUnit {
    FAHRENHEIT,
    CELCIUS
}