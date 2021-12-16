package com.ahoy.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.data.local.preference.DataStoreManager
import com.ahoy.weatherapp.utils.TempratureUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel@Inject constructor (
    private val dataStorePreferences: DataStoreManager,
    application: Application) : AndroidViewModel(application) {

    val settingsPref = MutableLiveData<String>()

    fun saveSettingsPreference(unit: TempratureUnit) {
        viewModelScope.launch {
            dataStorePreferences.savetoDataStore(unit)
        }
    }

    fun getSettingsFromPreference() {
        viewModelScope.launch {
            dataStorePreferences.tempUnitFlow.collect {
                settingsPref.value = it
            }
        }
    }
}
