package com.ahoy.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.data.model.ForecastResponse
import com.ahoy.weatherapp.databinding.ListItemWeatherBinding
import com.ahoy.weatherapp.utils.DataUtils
import javax.inject.Inject

class WeatherAdapter @Inject constructor() :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){

    private var data = ArrayList<String>()
    private var response : ForecastResponse? = null
    private var unit : String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemWeatherBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(data[position], unit)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateResults(newResponse: ForecastResponse, tempratureUnit: String?) {

        unit = tempratureUnit
        response = newResponse
        data.clear()
        val newList = DataUtils.getDataForUI(newResponse).keys
        data.addAll(newList)
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(private var binding: ListItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root){

        private var childAdapter = WeatherItemChildAdapter()

        init {
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(binding.recyclerviewWeatherItem.context,
                    LinearLayoutManager.HORIZONTAL, false)

            binding.recyclerviewWeatherItem. layoutManager = layoutManager
            binding.recyclerviewWeatherItem.apply {
                setHasFixedSize(true)
                adapter = childAdapter
            }
        }

        fun bind(dateToDisplay: String, unit: String?) {
            binding.dateToDisplay.text = dateToDisplay
            response?.let { it ->
                childAdapter.updateResults(it, dateToDisplay, unit)
            }
        }
    }

    inner class DiffCallback(private val oldList: ArrayList<String>, private val newList: List<String>) : DiffUtil.Callback() {

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