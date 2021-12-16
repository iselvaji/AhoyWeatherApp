package com.ahoy.weatherapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.comman.Resource
import com.ahoy.weatherapp.data.LocationLiveData
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import com.ahoy.weatherapp.data.local.preference.DataStoreManager
import com.ahoy.weatherapp.data.model.ForecastResponse
import com.ahoy.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val repository: WeatherRepository,
    application: Application,
    dataStorePreferences: DataStoreManager,
) : BaseViewModel(dataStorePreferences, application) {

    var forecastResponse: MutableLiveData<Resource<ForecastResponse>> = MutableLiveData()
    var response: MutableLiveData<Resource<ResponseBody>> = MutableLiveData()

    fun getForecastWeatherList(latitude: Double, longitude : Double) = viewModelScope.launch {
        repository.getForecastWeatherList(latitude, longitude).collect {
            Log.d("forecastResponse : ", it.status.toString())
            forecastResponse.value = it
        }
    }

    fun getWeatherbyPlaceName(placeName : String) = viewModelScope.launch {
        repository.getWeatherbyPlaceName(placeName).collect {
            Log.d("weather place resp : ", it.status.toString())
            forecastResponse.value = it
        }
    }

    fun saveFavCity(favCityEntity: FavCitiesEntity) = viewModelScope.launch {
        repository.addFavoriteCity(favCityEntity)
    }

    val locationData = LocationLiveData(application)

    fun getLocationDetails() = locationData
}