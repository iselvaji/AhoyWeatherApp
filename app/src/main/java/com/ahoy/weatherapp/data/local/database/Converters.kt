package com.ahoy.weatherapp.data.local.database

import androidx.room.TypeConverter
import com.ahoy.weatherapp.data.model.ListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun setWeatherListItem(listItems: List<ListItem>): String = Gson().toJson(listItems)

    @TypeConverter
    fun getWeatherList(jsonModel: String): List<ListItem> {
        val listType: Type = object : TypeToken<List<ListItem>>() {}.type
        return Gson().fromJson(jsonModel, listType)
    }
}