package com.ahoy.weatherapp.viewmodel

import android.app.Application
import com.ahoy.weatherapp.data.local.preference.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    dataStorePreferences: DataStoreManager,
    application: Application
) : BaseViewModel(dataStorePreferences, application) {

}