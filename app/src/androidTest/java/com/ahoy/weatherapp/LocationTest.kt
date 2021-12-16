package com.ahoy.weatherapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ahoy.weatherapp.utils.LocationUtils
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationTest {

    @Test
    fun testCityFromCoordinates() {

        val lat = 25.2048
        val lng = 55.2708
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val expectedCity = "Dubai,United Arab Emirates"
        val cityName = LocationUtils.getCityFromLocation(lat, lng, appContext)

        assertEquals("City name from location coordinates", expectedCity, cityName)
    }
}