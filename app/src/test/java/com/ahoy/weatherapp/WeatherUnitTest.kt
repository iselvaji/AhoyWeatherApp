package com.ahoy.weatherapp

import com.ahoy.weatherapp.utils.WeatherUtils
import org.junit.Test

class WeatherUnitTest {

    @Test
    fun testCelciusToFahrenheit() {

        val inputCelcius = 100.0
        val expectedFarenheit = 212.0
        val actualFarenheit = WeatherUtils.celciusToFahrenheit(inputCelcius)
        assert(expectedFarenheit == actualFarenheit)
    }

    @Test
    fun testFahrenheitToCelcius() {

        val inputFarenheit = 50.0
        val expectedCelcius = 10.0
        val actualCelcius = WeatherUtils.fahrenheitToCelcius(inputFarenheit)
        assert(expectedCelcius == actualCelcius)
    }
}