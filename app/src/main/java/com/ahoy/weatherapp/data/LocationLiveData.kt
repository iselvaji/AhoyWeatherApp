package com.ahoy.weatherapp.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(context: Context) : LiveData<LocationLiveData.LocationModel>()  {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: android.location.Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: android.location.Location) {
        value = LocationModel(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    data class LocationModel(
        val longitude: Double,
        val latitude: Double
    )


}