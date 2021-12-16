package com.ahoy.weatherapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahoy.weatherapp.data.local.database.DatabaseConfig
import com.ahoy.weatherapp.data.model.ListItem

@Entity(tableName = DatabaseConfig.TableName.FAV_CITIES)
data class FavCitiesEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id : Int = 0,

    @ColumnInfo(name = "cityName")
    var cityName: String?,

    @ColumnInfo(name = "country")
    var country: String?,

    @ColumnInfo(name = "latitude")
    val latitude: Double?,

    @ColumnInfo(name = "longitude")
    val longitude: Double?,

    @ColumnInfo(name = "listItem")
    var listItems: List<ListItem>?

)