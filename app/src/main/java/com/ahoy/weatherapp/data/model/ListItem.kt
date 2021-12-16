package com.ahoy.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class ListItem (

	@SerializedName("dt")
	val dt : Long,
	@SerializedName("main")
	val main : Main,
	@SerializedName("weather")
	val weather : List<Weather>,
	@SerializedName("clouds")
	val clouds : Clouds,
	@SerializedName("wind")
	val wind : Wind,
	@SerializedName("visibility")
	val visibility : Int,
	@SerializedName("pop")
	val pop : Double,
	@SerializedName("sys")
	val sys : Sys,
	@SerializedName("dt_txt")
	val dtTxt : String
)