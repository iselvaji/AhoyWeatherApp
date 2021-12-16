package com.ahoy.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Sys (

	@SerializedName("pod")
	val pod : String
)