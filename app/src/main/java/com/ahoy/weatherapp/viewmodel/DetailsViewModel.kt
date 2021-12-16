package com.ahoy.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import com.ahoy.weatherapp.data.local.preference.DataStoreManager
import com.ahoy.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor (
    private val repository: WeatherRepository,
    application: Application,
    dataStorePreferences: DataStoreManager
) : BaseViewModel(dataStorePreferences, application) {

    var response: MutableLiveData<List<FavCitiesEntity>> = MutableLiveData()

    fun getWeatherDetails(cityName: String) = viewModelScope.launch {
        repository.getCityWeatherByName(cityName).collect {
            response.postValue(it)
        }
    }
}