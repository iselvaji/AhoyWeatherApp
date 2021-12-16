package com.ahoy.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.data.model.ForecastResponse
import com.ahoy.weatherapp.data.model.WeatherInfo
import com.ahoy.weatherapp.databinding.ListItemWeatherInfoBinding
import com.ahoy.weatherapp.utils.DataUtils.getDataForUI
import com.ahoy.weatherapp.utils.WeatherUtils
import javax.inject.Inject

class WeatherItemChildAdapter @Inject constructor() :
    RecyclerView.Adapter<WeatherItemChildAdapter.WeatherItemViewHolder>(){

    private var unit : String? = null
    private var data = ArrayList<WeatherInfo>()
    private var response : ForecastResponse? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemWeatherInfoBinding.inflate(inflater, parent, false)
        return WeatherItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateResults(newResponse: ForecastResponse, dateToDisplay: String, unit: String?) {

        this.unit = unit
        response = newResponse
        data.clear()
        val newList = getDataForUI(newResponse).get(dateToDisplay)

        if (newList != null) {
            data.addAll(newList)
        }
        notifyDataSetChanged()
    }

    inner class WeatherItemViewHolder(private var binding: ListItemWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(weather: WeatherInfo) {
            weather.apply {
                binding.weatherDesc.text = weatherDesc
                binding.temprature.text = WeatherUtils.getTempratureInUnit(unit, temprature)
                binding.humidity.text = humidity.toString()
                binding.time.text = timeToDisplay
            }
        }
    }

    inner class DiffCallback(private val oldList: ArrayList<WeatherInfo>, private val newList: List<WeatherInfo>) : DiffUtil.Callback() {

        override fun getOldListSize() : Int = oldList.size

        override fun getNewListSize() : Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            val oldVersion = if (oldItemPosition < oldList.size) oldList[oldItemPosition] else null
            val newVersion = if (newItemPosition < newList.size) newList[newItemPosition] else null

            return oldVersion == newVersion
        }
    }
}