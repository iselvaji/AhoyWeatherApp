package com.ahoy.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse (

	@SerializedName("cod")
	val cod : String,
	@SerializedName("message")
	val message : Double,
	@SerializedName("cnt")
	val cnt : Int,
	@SerializedName("list")
	val listItem : List<ListItem>,
	@SerializedName("city")
	val city : City
)