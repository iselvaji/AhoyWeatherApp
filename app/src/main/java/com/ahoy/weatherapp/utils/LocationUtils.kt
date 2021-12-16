package com.ahoy.weatherapp.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import java.io.IOException
import java.util.*

object LocationUtils {

    fun getCityFromLocation(latitude: Double, longitude: Double, context: Context) : String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        var result = ""
        try {
            val addressList = geoCoder.getFromLocation(latitude, longitude, 1)
            if ((addressList != null && addressList.size > 0)) {
                val address = addressList[0]
                val sb = StringBuilder()
                address?.locality?.let{
                    sb.append(it).append(",")
                }
                address?.countryName?.let {
                    sb.append(it)
                }
                result = sb.toString()
                Log.d("city from location :", result)
            }
        } catch (e: IOException) {
            Log.e("Geocode error", e.localizedMessage)
        }
        return result
    }
}