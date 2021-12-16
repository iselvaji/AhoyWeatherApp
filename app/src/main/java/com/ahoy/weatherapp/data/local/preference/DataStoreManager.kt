package com.ahoy.weatherapp.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ahoy.weatherapp.utils.TempratureUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(context: Context) {

    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "WeatherAppPref")
    private val prefDataStore = context.preferencesDataStore

    private val _UNIT = stringPreferencesKey("Unit")

    suspend fun savetoDataStore(unit: TempratureUnit) {
        prefDataStore.edit {
            it[_UNIT] = unit.name
        }
    }

    val tempUnitFlow: Flow<String> = prefDataStore.data.map {
        it[_UNIT] ?: ""
    }
}