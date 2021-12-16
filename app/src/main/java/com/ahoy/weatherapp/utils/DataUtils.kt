package com.ahoy.weatherapp.utils

import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import com.ahoy.weatherapp.data.model.ForecastResponse
import com.ahoy.weatherapp.data.model.ListItem
import com.ahoy.weatherapp.data.model.WeatherInfo

object DataUtils {

    fun getDataForUI(response: ForecastResponse) : HashMap<String, ArrayList<WeatherInfo>> {
        return getDataForUI(response.listItem)
    }

    fun getDataForUI(listItem: List<ListItem>) : HashMap<String, ArrayList<WeatherInfo>> {
        val weatherMapResult = HashMap<String, ArrayList<WeatherInfo>>()

        listItem.let {
            for(item in it) {
                val dateTimePair = getDateTimeFromString(item.dtTxt)

                if (!weatherMapResult.containsKey(dateTimePair?.first)) {
                    dateTimePair?.first?.let { it1 ->
                        weatherMapResult.put(it1, ArrayList())
                    }
                }

                val weatherInfo = WeatherInfo(
                    dateTimePair?.second,
                    item.main.temp_max,
                    item.main.humidity,
                    item.weather[0].description
                )
                weatherMapResult[(dateTimePair?.first)]?.add(weatherInfo)
            }
        }
        return weatherMapResult
    }


    fun getDataForStorage(response: ForecastResponse): FavCitiesEntity {
        var cityName: String? = null
        var lat: Double? = null
        var lng: Double? = null
        var country: String? = null
        var listItems: List<ListItem>? = null

        response.apply {
            city.let {
                cityName = it.name
                lat = it.coord.lat
                lng = it.coord.lon
                country = it.country
            }
            listItem.let {
                listItems = it
            }
        }
        return FavCitiesEntity(
            cityName = cityName,
            country = country,
            latitude = lat,
            longitude = lng,
            listItems = listItems
        )
    }

    private fun getDateTimeFromString(dateTimeText : String) : Pair<String, String>? {
        var dateTime: Pair<String, String>? = null

        // example format 2021-12-16 12:00:00
        dateTimeText.let {
            val strArr = it.split(" ")
            if(!strArr.isNullOrEmpty()) {
                dateTime = Pair(strArr[0], strArr[1])
            }
        }
        return dateTime
    }
}